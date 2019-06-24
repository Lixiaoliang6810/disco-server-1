package com.miner.disco.front.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.alipay.support.model.request.AlipayRefundRequest;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.basic.util.UidMaskUtils;
import com.miner.disco.front.component.SerialNoGenerator;
import com.miner.disco.front.dao.*;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.*;
import com.miner.disco.front.model.response.*;
import com.miner.disco.front.service.OrdersService;
import com.miner.disco.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private MerchantGoodsMapper merchantGoodsMapper;

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private MerchantEvaluateMapper merchantEvaluateMapper;

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Autowired
    private MemberBillMapper memberBillMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private CallbackNotifyMapper callbackNotifyMapper;

    @Resource(name = "redisTemplate")

    private ValueOperations<String, String> vOps;

    @Autowired
    private SerialNoGenerator serialNoGenerator;

    @Autowired
    private OrdersInvitationMapper ordersInvitationMapper;

    @Override
    public Orders queryByPrimaryKey(Long ordersId) throws BusinessException {
        return ordersMapper.queryByPrimaryKey(ordersId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long purchase(OrdersPurchaseRequest request) throws BusinessException {
        //判断商品是否存在
        MerchantGoods merchantGoods = merchantGoodsMapper.queryByPrimaryKey(request.getGoodsId());
        Assert.notNull(merchantGoods, BusinessExceptionCode.OBJECT_NOT_FOUND.getCode(),"商品不存在");
        Orders orders = (Orders) DtoTransition.trans(Orders.class, request);
        Assert.notNull(orders, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        String no = orderNo(request.getChannel(), request.getUserId(), merchantGoods.getMerchantId(), merchantGoods.getId());
        orders.setNo(no);
        orders.setBuyer(request.getUserId());
        orders.setSeller(merchantGoods.getMerchantId());
        orders.setStatus(Orders.STATUS.WAIT_PAYMENT.getKey());
        orders.setAssembleSeats(BooleanStatus.NO.getKey());
        orders.setAssembleSeatsCount(0);
        orders.setAssembleSeatsSurplus(0);
        orders.setAssembleSeatsMoney(BigDecimal.ZERO);
        orders.setEarnestMoney(merchantGoods.getPrice());
        orders.setTailMoney(merchantGoods.getPrice());
        orders.setTotalMoney(merchantGoods.getPrice());
        orders.setCreateDate(new Date());
        orders.setUpdateDate(new Date());
        ordersMapper.insert(orders);
        return orders.getId();
    }

    private String orderNo(Integer channel, Long userId, Long merchantId, Long goodsId) {
        String seq = String.format("%02d", serialNoGenerator.generateSeq(SerialNoGenerator.SerialKey.ORDERS_SEQ));
        String uid = String.format("%04d", userId);
        String mid = String.format("%02d", merchantId);
        String gid = String.format("%02d", goodsId);
        uid = uid.substring(uid.length() - 4);
        mid = mid.substring(mid.length() - 2);
        gid = gid.substring(gid.length() - 2);
        Date d = new Date();
        String data = DateUtils.formatDate(d, "MMdd");
        String time = String.valueOf(d.getTime()).substring(String.valueOf(d.getTime()).length() - 4);
        return String.format("%s%s%s%s%s%s%s", channel, data, time, gid, mid, uid, seq);
    }

    @Override
    public void payment(OrdersPaymentRequest request) throws BusinessException {

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void paymentCallback(OrdersPaymentNotifyRequest request) throws BusinessException {
        CallbackNotify callbackNotify = callbackNotifyMapper.queryBySnAndType(request.getNotifyId(), request.getPaymentMethod().getKey());
        if (callbackNotify != null) {
            log.info("{} sn {} repeated callbacks", request.getPaymentMethod().getValue(), request.getNotifyId());
            return;
        }
        Orders orders = ordersMapper.queryByPrimaryKey(request.getOrdersId());
        if (orders == null) {
            log.error("illegal callbacks, metadata `{}`", JsonParser.serializeToJson(request.getMetadata()));
            return;
        }
        //更新订单信息
        Orders saveOrders = new Orders();
        saveOrders.setId(orders.getId());
        saveOrders.setStatus(Orders.STATUS.WAIT_CONSUMPTION.getKey());
        saveOrders.setUpdateDate(new Date());
        saveOrders.setPaymentDate(new Date());
        ordersMapper.updateByPrimaryKey(saveOrders);

        //更新商品信息
        MerchantGoods merchantGoods = merchantGoodsMapper.queryByPrimaryKey(orders.getGoodsId());
        MerchantGoods saveMerchantGoods = new MerchantGoods();
        saveMerchantGoods.setId(orders.getGoodsId());
        saveMerchantGoods.setSalesVolume(merchantGoods.getSalesVolume() == null ? 1 : merchantGoods.getSalesVolume() + 1);
        merchantGoodsMapper.updateByPrimaryKey(saveMerchantGoods);

        //更新商户余额
        Merchant merchant = merchantMapper.queryByPrimaryKeyFroUpdate(orders.getSeller());
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setFrozenBalance(merchant.getFrozenBalance().add(orders.getTotalMoney()));
        merchantMapper.updateByPrimaryKey(saveMerchant);

        //记录回调记录
        callbackNotify = new CallbackNotify();
        callbackNotify.setCallbackSn(request.getNotifyId());
        callbackNotify.setCallbackType(request.getPaymentMethod().getKey());
        callbackNotify.setMetadata(JsonParser.serializeToJson(request.getMetadata()));
        callbackNotify.setCreateDate(new Date());
        callbackNotify.setUpdateDate(new Date());
        callbackNotifyMapper.insert(callbackNotify);

        //记录用户流水
        String sno = String.format("%s%s%s",
                UidMaskUtils.idToCode(merchant.getId()),
                UidMaskUtils.idToCode(orders.getBuyer()),
                System.currentTimeMillis());
        MemberBill memberBill = new MemberBill();
        memberBill.setSerialNo(sno);
        memberBill.setAmount(orders.getTailMoney());
        memberBill.setUserId(orders.getBuyer());
        memberBill.setType(MemberBill.TYPE.OUT.getKey());
        memberBill.setTradeType(MemberBill.TRADE_TYPE.ONLINE_PURCHASE.getKey());
        memberBill.setRemark(MemberBill.TRADE_TYPE.ONLINE_PURCHASE.getValue());
        memberBill.setSource(merchantGoods.getName());
        memberBill.setSourceId(orders.getId());
        memberBill.setCreateDate(new Date());
        memberBill.setUpdateDate(new Date());
        memberBillMapper.insert(memberBill);

        //记录商户流水
//        MerchantBill merchantBill = new MerchantBill();
//        merchantBill.setAmount(orders.getTotalMoney());
//        merchantBill.setMerchantId(orders.getSeller());
//        merchantBill.setSourceId(orders.getId());
//        merchantBill.setCreateDate(new Date());
//        merchantBill.setUpdateDate(new Date());
//        merchantBill.setType(MerchantBill.STATUS.IN.getKey());
//        merchantBill.setTradeType(MerchantBill.TRADE_STATUS.ON_LINE.getKey());
//        merchantBill.setRemark(MerchantBill.TRADE_STATUS.ON_LINE.getValue());
//        merchantBillMapper.insert(merchantBill);

        vOps.increment(String.format(BasicConst.REDIS_CACHE_MERCHANT_RESERVE_ORDERS, orders.getSeller()), 1);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdersDetailsResponse details(Long ordersId) throws BusinessException {
        return ordersMapper.queryById(ordersId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<OrdersListResponse> list(OrdersListRequest request) throws BusinessException {

        List<OrdersListResponse> list = ordersMapper.queryByUserId(request);
        for (OrdersListResponse orders : list) {
            if (orders.getStatus().equals(1)) {
                //订单创建时间
                Long creationTime  = orders.getCreateDate().getTime();
                //当前系统时间
                Long currentTime  = new Date().getTime();
                Long l = currentTime - creationTime;
                Long aLong = (long) (15 * 60 * 1000);
                if (l-aLong>0){
                    ordersMapper.deleteOrders(orders.getNo());

                }
            }

        }
        return ordersMapper.queryByUserId(request);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void refund(Long ordersId) throws BusinessException {
        Orders orders = ordersMapper.queryByPrimaryKey(ordersId);
        Assert.notNull(orders, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        if (orders.getStatus().longValue() != Orders.STATUS.WAIT_CONSUMPTION.getKey()) {
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "订单无法进行退款");
        }
        BigDecimal refundMoney = orders.getTotalMoney();
        AlipayRefundRequest refundRequest = new AlipayRefundRequest();
        refundRequest.setOutTradeNo(orders.getNo());
        refundRequest.setRefundAmount(refundMoney.toPlainString());
        try {
            AlipayTradeRefundResponse refundApplyResponse = alipayService.refund(refundRequest);
            if (!refundApplyResponse.isSuccess()) {
                throw new BusinessException(BusinessExceptionCode.ORDERS_REFUND_FAILURE.getCode(), "退款失败");
            }
            log.info("alipay trade refund response {}", refundApplyResponse.getBody());
            Orders saveOrders = new Orders();
            saveOrders.setId(ordersId);
            saveOrders.setStatus(Orders.STATUS.REFUND.getKey());
            saveOrders.setRefundMoney(refundMoney);
            saveOrders.setRefundDate(new Date());
            saveOrders.setUpdateDate(new Date());
            ordersMapper.updateByPrimaryKey(saveOrders);

            //记录商户流水
            MerchantBill merchantBill = new MerchantBill();
            merchantBill.setAmount(orders.getTotalMoney());
            merchantBill.setMerchantId(orders.getSeller());
            merchantBill.setSourceId(orders.getId());
            merchantBill.setCreateDate(new Date());
            merchantBill.setUpdateDate(new Date());
            merchantBill.setType(MerchantBill.STATUS.OUT.getKey());
            merchantBill.setTradeType(MerchantBill.TRADE_STATUS.REFUND.getKey());
            merchantBill.setRemark(MerchantBill.TRADE_STATUS.REFUND.getValue());
            merchantBillMapper.insert(merchantBill);

            //更新商户余额
            Merchant merchant = merchantMapper.queryByPrimaryKeyFroUpdate(orders.getSeller());
            Merchant saveMerchant = new Merchant();
            saveMerchant.setId(merchant.getId());
            saveMerchant.setFrozenBalance(merchant.getFrozenBalance().subtract(orders.getTotalMoney()));
            merchantMapper.updateByPrimaryKey(saveMerchant);

            // 更新当前订单对应的拼桌详情（包括发起人和受邀人的拼桌信息）为已过期
//            ordersInvitationMapper.updateByPrimaryKey();

        } catch (AlipayApiException e) {
            log.error("call ali pay sdk error", e);
            throw new BusinessException(BusinessExceptionCode.ORDERS_REFUND_FAILURE.getCode(), "退款失败");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void evaluate(OrdersEvaluateRequest request) throws BusinessException {
        Orders orders = ordersMapper.queryByPrimaryKey(request.getOrdersId());
        Assert.notNull(orders, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        if (orders.getStatus().intValue() != Orders.STATUS.WAIT_EVALUATE.getKey() || request.getUserId().longValue() != orders.getBuyer()) {
            throw new BusinessException(BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        }
        MerchantEvaluate merchantEvaluate = (MerchantEvaluate) DtoTransition.trans(MerchantEvaluate.class, request);
        Assert.notNull(merchantEvaluate, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        merchantEvaluate.setMerchantId(orders.getSeller());
        merchantEvaluate.setCreateDate(new Date());
        merchantEvaluate.setUpdateDate(new Date());
        merchantEvaluateMapper.insert(merchantEvaluate);

        double avg = merchantEvaluateMapper.calcAverage(orders.getSeller());
        if (avg > 0) {
            Merchant saveMerchant = new Merchant();
            saveMerchant.setId(orders.getSeller());
            saveMerchant.setScore(avg);
            merchantMapper.updateByPrimaryKey(saveMerchant);
        }

        Orders saveOrders = new Orders();
        saveOrders.setId(request.getOrdersId());
        saveOrders.setStatus(Orders.STATUS.COMPLETE.getKey());
        ordersMapper.updateByPrimaryKey(saveOrders);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void launchAssemble(LaunchOrdersAssembleRequest request) throws BusinessException {
        Orders orders = ordersMapper.queryByPrimaryKey(request.getOrdersId());
        Assert.notNull(orders, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        Orders saveOrders = new Orders();
        saveOrders.setId(orders.getId());
        saveOrders.setAssembleSeats(BooleanStatus.YES.getKey());
        saveOrders.setAssembleSeatsCount(request.getAssembleSeatsCount());
        saveOrders.setAssembleSeatsSurplus(request.getAssembleSeatsCount());
        ordersMapper.updateByPrimaryKey(saveOrders);

        OrdersInvitation ordersInvitation = new OrdersInvitation();
        ordersInvitation.setOwner(BooleanStatus.YES.getKey());
        ordersInvitation.setOrdersId(orders.getId());
        ordersInvitation.setMemberId(orders.getBuyer());
        ordersInvitation.setCreateDate(new Date());
        ordersInvitation.setUpdateDate(new Date());
        ordersInvitation.setStatus(OrdersInvitation.STATUS.AGREE_JOIN.getKey());
        ordersInvitationMapper.insert(ordersInvitation);
    }

    @Override
    public List<AssembleOrdersListResponse> assembleList(AssembleOrdersListRequest request) throws BusinessException {
        List<AssembleOrdersListResponse> responses = ordersMapper.queryAssembleList(request);
        // 只显示拼桌状态未结束的列表--退款时。订单完成时，更新拼桌状态为已结束
        responses.forEach(o -> {
            List<OrdersInvitationResponse> invitationResponses = ordersInvitationMapper.queryJoinedByOrdersId(o.getOrdersId(),
                    String.valueOf(OrdersInvitation.STATUS.AGREE_JOIN.getKey()));
            o.setAssembleMembers(invitationResponses);
        });

        return responses;
    }

    @Override
    public List<ApplyAssembleOrdersListResponse> applyAssembleList(ApplyAssembleOrdersListRequest request) throws BusinessException {
        return ordersMapper.queryApplyAssembleList(request);
    }

    @Override
    public List<LaunchAssembleOrdersListResponse> launchAssembleList(LaunchAssembleOrdersListRequest request) throws BusinessException {
        List<LaunchAssembleOrdersListResponse> responses = ordersMapper.queryLaunchAssembleList(request);
        responses.forEach(o -> {
            List<OrdersInvitationResponse> invitationResponses = ordersInvitationMapper.queryJoinedByOrdersId(o.getOrdersId(),
                    String.format("%s,%s,%s", OrdersInvitation.STATUS.WAIT_ACCEPT.getKey(),
                            OrdersInvitation.STATUS.AGREE_JOIN.getKey(),
                            OrdersInvitation.STATUS.REFUSE_JOIN.getKey()));
            o.setAssembleMembers(invitationResponses);
        });
        return responses;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void applyAssemble(Long userId, Long ordersId) throws BusinessException {
        Orders orders = ordersMapper.queryByPrimaryKey(ordersId);
        Assert.notNull(orders, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        if (orders.getAssembleSeats().intValue() != BooleanStatus.YES.getKey()) {
            throw new BusinessException(BusinessExceptionCode.ORDERS_NONSUPPORT_ASSEMBLE.getCode(), "订单不支持拼座");
        }
        if (userId.longValue() == orders.getBuyer().longValue()) {
            throw new BusinessException(BusinessExceptionCode.CANNOT_JOIN_MYSELF_ASSEMBLE.getCode(), "不能加入自己发起的拼座");
        }
        OrdersInvitation ordersInvitation = ordersInvitationMapper.queryJoinedByUserIdAndOrdersId(userId, ordersId);
        Assert.isNull(ordersInvitation, BusinessExceptionCode.REPEAT_APPLY_ASSEMBLE.getCode(), "该订单已申请，请勿重复申请");

        orders = ordersMapper.queryByPrimaryKeyForUpdate(ordersId);
        if (orders.getAssembleSeatsSurplus() <= 0) {
            throw new BusinessException(BusinessExceptionCode.ORDER_ASSEMBLE_SEATS_INSUFFICIENT.getCode(), "剩余座位不足");
        }

        ordersInvitation = new OrdersInvitation();
        ordersInvitation.setOwner(BooleanStatus.NO.getKey());
        ordersInvitation.setOrdersId(orders.getId());
        ordersInvitation.setMemberId(userId);
        ordersInvitation.setCreateDate(new Date());
        ordersInvitation.setUpdateDate(new Date());
        ordersInvitation.setStatus(OrdersInvitation.STATUS.WAIT_ACCEPT.getKey());
        ordersInvitationMapper.insert(ordersInvitation);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdersAssembleDetailsResponse assembleDetails(Long ordersId) throws BusinessException {
        OrdersDetailsResponse ordersDetailsResponse = ordersMapper.queryById(ordersId);
        OrdersAssembleDetailsResponse response = (OrdersAssembleDetailsResponse) DtoTransition.trans(OrdersAssembleDetailsResponse.class, ordersDetailsResponse);
        Assert.notNull(response, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        List<OrdersInvitationResponse> invitationResponses = ordersInvitationMapper.queryJoinedByOrdersId(ordersId,
                String.valueOf(OrdersInvitation.STATUS.AGREE_JOIN.getKey()));
        response.setAssembleMembers(invitationResponses);
        return response;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void agreeAssemble(Long ordersInvitationId) throws BusinessException {
        OrdersInvitation ordersInvitation = ordersInvitationMapper.queryByPrimaryKey(ordersInvitationId);
        Assert.notNull(ordersInvitation, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        Orders orders = ordersMapper.queryByPrimaryKeyForUpdate(ordersInvitation.getOrdersId());
        if (orders.getAssembleSeatsSurplus() <= 0) {
            throw new BusinessException(BusinessExceptionCode.ORDER_ASSEMBLE_SEATS_INSUFFICIENT.getCode(), "剩余座位不足");
        }
        Orders saveOrders = new Orders();
        saveOrders.setId(orders.getId());
        saveOrders.setAssembleSeatsSurplus(orders.getAssembleSeatsSurplus() - 1);
        ordersMapper.updateByPrimaryKey(saveOrders);

        OrdersInvitation saveOrdersInvitation = new OrdersInvitation();
        saveOrdersInvitation.setId(ordersInvitation.getId());
        saveOrdersInvitation.setStatus(OrdersInvitation.STATUS.AGREE_JOIN.getKey());
        ordersInvitationMapper.updateByPrimaryKey(saveOrdersInvitation);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void refuseAssemble(Long ordersInvitationId) throws BusinessException {
        OrdersInvitation ordersInvitation = ordersInvitationMapper.queryByPrimaryKey(ordersInvitationId);
        Assert.notNull(ordersInvitation, BusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");

        OrdersInvitation saveOrdersInvitation = new OrdersInvitation();
        saveOrdersInvitation.setId(ordersInvitation.getId());
        saveOrdersInvitation.setStatus(OrdersInvitation.STATUS.REFUSE_JOIN.getKey());
        ordersInvitationMapper.updateByPrimaryKey(saveOrdersInvitation);
    }

}
