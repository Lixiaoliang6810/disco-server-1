package com.miner.disco.front.controller;

import com.alipay.api.AlipayApiException;
import com.aliyuncs.exceptions.ClientException;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.alipay.support.model.request.AlipayPreorderRequest;
import com.miner.disco.alipay.support.model.response.AlipayPreorderResponse;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.Environment;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.DateTime;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.dao.MerchantMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.*;
import com.miner.disco.front.model.response.*;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.OrdersService;
import com.miner.disco.front.service.impl.SmsServiceImpl;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.Orders;
import com.miner.disco.wxpay.support.WxpayService;
import com.miner.disco.wxpay.support.model.request.WxPayPreOrderRequest;
import com.miner.disco.wxpay.support.model.response.WxpayPreorderResponse;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Slf4j
@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private WxpayService wxpayService;

    @Autowired
    private MerchantMapper merchantMapper;

    private final SmsServiceImpl smsService;
    @Autowired
    public OrdersController(SmsServiceImpl smsService){
        this.smsService = smsService;
    }


    @Value("${server.environment}")
    private Environment environment;

    @Value("${server.payment-callback-url}")
    private String paymentCallbackUrl;

    /**
     * 预定
     * 点击‘立即支付’ 时生成订单，状态为 未支付：WAIT_PAYMENT
     *
     *
     */
    @PostMapping(value = "/orders/purchase", headers = Const.API_VERSION_1_0_0)
    public ViewData purchase(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication, HttpServletRequest servletRequest,
                             OrdersPurchaseRequest request) {
        //根据请求头的名字获取对应的请求头的值
        UserAgent userAgent = UserAgent.parseUserAgentString(servletRequest.getHeader("User-Agent"));
        //获取浏览器
        Browser browser = userAgent.getBrowser();
        //获取操作系统
        OperatingSystem os = userAgent.getOperatingSystem();
        request.setChannel(browser, os);
        //获取当前登录用户id
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        Long ordersId = ordersService.purchase(request);
        return ViewData.builder().data(ordersId).message("下单成功").build();
    }

    /**
     * 点击‘立即支付’ 完成后，订单变为 已支付
     */
    @PostMapping(value = "/orders/payment", headers = Const.API_VERSION_1_0_0)
    public ViewData payment(HttpServletRequest servletRequest, OrdersPaymentRequest request) throws IOException, ClientException, ParseException {
        Orders orders = ordersService.queryByPrimaryKey(request.getOrdersId());
        Assert.notNull(orders, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        if (orders.getStatus().intValue() != Orders.STATUS.WAIT_PAYMENT.getKey()) {
            return ViewData.builder().data(BusinessExceptionCode.ORDERS_REPEATED_PAYMENT.getCode()).message("请勿重复支付").build();
        }
        String callbackParam = URLEncoder.encode(String.format("ordersId=%s&env=%s", orders.getId(), environment), BasicConst.UTF_8.displayName());
        /*短信内容*/
        String mobile = orders.getMobile();
        String arrivalTime = DateTime.format(orders.getArrivalTime(),DateTime.PATTERN.MM_DD_HH_MM.value());
        int assembleSeatsCount = (orders.getAssembleSeatsCount()==null|| orders.getAssembleSeatsCount()==0)?1:orders.getAssembleSeatsCount()+1;
        String amount = Integer.toString(assembleSeatsCount);
        // 查店家手机
        Long sellerId = orders.getSeller();
        Merchant merchant = merchantMapper.queryByPrimaryKeyFroUpdate(sellerId);
        String merchantMobile = merchant.getMobile();
        switch (request.getPm()) {
            case ALIPAY:
                AlipayPreorderResponse appResponse = alipay(servletRequest, orders.getNo(), orders.getTotalMoney(), callbackParam, request.getPm());
                /*短信服务*/
//                SMSHelper.sendChinaMessage(content,merchantMobile);
                smsService.newOrderSmsNotify(merchantMobile,mobile,arrivalTime,amount);
                return ViewData.builder().data(appResponse).message("支付宝预支付").build();
            case WXPAY:
                WxpayPreorderResponse response = wxpay(servletRequest, orders.getNo(), orders.getTotalMoney(), callbackParam, request.getPm());
//                SMSHelper.sendChinaMessage(content,merchantMobile);
                smsService.newOrderSmsNotify(merchantMobile,mobile,arrivalTime,amount);
                return ViewData.builder().data(response).message("0").build();
            case WAP_ALIPAY:
                AlipayPreorderResponse wapResponse = alipay(servletRequest, orders.getNo(), orders.getTotalMoney(), callbackParam, request.getPm());
//                SMSHelper.sendChinaMessage(content,merchantMobile);
                smsService.newOrderSmsNotify(merchantMobile,mobile,arrivalTime,amount);
                return ViewData.builder().data(wapResponse).message("支付宝网页预支付").build();
            default:
                return ViewData.builder().message("请选择支付方式").build();
        }
    }

    @GetMapping(value = "/orders/details", headers = Const.API_VERSION_1_0_0)
    public ViewData details(@RequestParam("ordersId") Long ordersId) {
        OrdersDetailsResponse response = ordersService.details(ordersId);
        return ViewData.builder().data(response).message("订单详情").build();
    }

    @PostMapping(value = "/orders/refund", headers = Const.API_VERSION_1_0_0)
    public ViewData refund(@RequestParam("ordersId") Long ordersId) {
        ordersService.refund(ordersId);
        return ViewData.builder().message("退款成功").build();
    }

    @GetMapping(value = "/orders/list", headers = Const.API_VERSION_1_0_0)
    public ViewData list(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                         OrdersListRequest request) {
        request.setUserId(((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId());
        List<OrdersListResponse> responses = ordersService.list(request);
        return ViewData.builder().data(responses).message("订单列表").build();
    }

    @PostMapping(value = "/orders/evaluate", headers = Const.API_VERSION_1_0_0)
    public ViewData evaluate(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                             OrdersEvaluateRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        ordersService.evaluate(request);
        return ViewData.builder().message("评论成功").build();
    }

    @PostMapping(value = "/orders/assemble/launch", headers = Const.API_VERSION_1_0_0)
    public ViewData launchAssemble(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                   LaunchOrdersAssembleRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        ordersService.launchAssemble(request);
        return ViewData.builder().message("发起成功").build();
    }

    @GetMapping(value = "/orders/assemble/list", headers = Const.API_VERSION_1_0_0)
    public ViewData assembleList(AssembleOrdersListRequest request) {
        List<AssembleOrdersListResponse> responses = ordersService.assembleList(request);
        return ViewData.builder().data(responses).message("拼座列表").build();
    }

    @PostMapping(value = "/orders/assemble/apply", headers = Const.API_VERSION_1_0_0)
    public ViewData applyAssemble(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                  @RequestParam("ordersId") Long ordersId) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        ordersService.applyAssemble(uid, ordersId);
        return ViewData.builder().message("申请成功").build();
    }

    @GetMapping(value = "/orders/assemble/details", headers = Const.API_VERSION_1_0_0)
    public ViewData assembleDetails(@RequestParam("ordersId") Long ordersId) {
        OrdersAssembleDetailsResponse response = ordersService.assembleDetails(ordersId);
        return ViewData.builder().data(response).message("拼座详情").build();
    }

    @PostMapping(value = "/orders/assemble/agree", headers = Const.API_VERSION_1_0_0)
    public ViewData agreeAssemble(@RequestParam("ordersInvitationId") Long ordersInvitationId) {
        ordersService.agreeAssemble(ordersInvitationId);
        return ViewData.builder().build();
    }

    @PostMapping(value = "/orders/assemble/refuse", headers = Const.API_VERSION_1_0_0)
    public ViewData refuseAssemble(@RequestParam("ordersInvitationId") Long ordersInvitationId) {
        ordersService.refuseAssemble(ordersInvitationId);
        return ViewData.builder().build();
    }

    @GetMapping(value = "/orders/assemble/apply/list", headers = Const.API_VERSION_1_0_0)
    public ViewData applyAssembleList(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                      ApplyAssembleOrdersListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<ApplyAssembleOrdersListResponse> responses = ordersService.applyAssembleList(request);
        return ViewData.builder().data(responses).message("申请拼座列表").build();
    }

    @GetMapping(value = "/orders/assemble/launch/list", headers = Const.API_VERSION_1_0_0)
    public ViewData launchAssembleList(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                       LaunchAssembleOrdersListRequest request) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        request.setUserId(uid);
        List<LaunchAssembleOrdersListResponse> responses = ordersService.launchAssembleList(request);
        return ViewData.builder().data(responses).message("发起拼座列表").build();
    }

    private AlipayPreorderResponse alipay(HttpServletRequest servletRequest, String sn, BigDecimal amount, String callbackParam, OrdersPaymentRequest.PAYMENT_METHOD paymentMethod) throws BusinessException {
        AlipayPreorderRequest request = new AlipayPreorderRequest();
        request.setBody("麦罗佛伦");
        request.setSubject("线上预定");
        request.setOutTradeNo(sn);
        request.setTotalAmount(environment == Environment.RELEASE ? amount.toPlainString() : "0.01");
        request.setCallbackParam(callbackParam);
        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        request.setCallbackUrl(String.format("%s%s", callbackUrl, "/alipay/orders/notify"));
        AlipayPreorderResponse response = null;
        try {
            response = paymentMethod == OrdersPaymentRequest.PAYMENT_METHOD.ALIPAY ?
                    alipayService.appPreorder(request) : alipayService.wapPreorder(request);
        } catch (AlipayApiException e) {
            log.error("call alipay sdk error.", e);
        }
        return response;
    }

    private WxpayPreorderResponse wxpay(HttpServletRequest servletRequest, String sn, BigDecimal amount, String callbackParam, OrdersPaymentRequest.PAYMENT_METHOD paymentMethod) {
        WxPayPreOrderRequest wxpayPreorderRequest = new WxPayPreOrderRequest();
        wxpayPreorderRequest.setBody("麦罗佛伦");
        wxpayPreorderRequest.setDetail("线上预定");
        wxpayPreorderRequest.setOutTradeNo(sn);
        //  URLEncoder.encode(String.format("ordersId=%s&env=%s", orders.getId(), environment), BasicConst.UTF_8.displayName());
        wxpayPreorderRequest.setCallbackParam(callbackParam);
        wxpayPreorderRequest.setTotalFee(environment == Environment.RELEASE ? amount.multiply(BigDecimal.valueOf(100)).toPlainString() : "1");
        String callbackUrl = (environment == Environment.RELEASE) ? getPath(servletRequest) : paymentCallbackUrl;
        wxpayPreorderRequest.setCallbackUrl(String.format("%s%s", callbackUrl, "/wxpay/orders/notify"));
        return wxpayService.preorder(wxpayPreorderRequest);
    }

    private String getPath(HttpServletRequest request) {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }

}
