package com.zaki.pay.wx.service.impl;

import com.alibaba.fastjson.util.IOUtils;
import com.zaki.pay.wx.constants.WXPayConstants;
import com.zaki.pay.wx.exception.WXPayApiException;
import com.zaki.pay.wx.model.request.ApplyRefundRequest;
import com.zaki.pay.wx.model.request.WXPayOrderQueryRequest;
import com.zaki.pay.wx.model.request.WXPayUnifiedOrderRequest;
import com.zaki.pay.wx.model.response.ApplyRefundResponse;
import com.zaki.pay.wx.model.response.WXPayOrderQueryResponse;
import com.zaki.pay.wx.model.response.WXPayUnifiedOrderResponse;
import com.zaki.pay.wx.service.WXPayService;
import com.zaki.pay.wx.utils.HttpUtils;
import com.zaki.pay.wx.utils.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * @author: wz1016_vip@163.com @Date: 2019/7/1
 */
@Slf4j
public class WXPayServiceImpl implements WXPayService {

    private String appId;
    private String mchId;
    private String apiSecret;
    private static final String RELATIVE_PATH = "\\disco-support\\disco-wxpay-support2\\src\\main\\resources\\cert\\apiclient_cert.p12";

    public WXPayServiceImpl(){}
    public WXPayServiceImpl(String appId, String mchId, String apiSecret) throws KeyStoreException {
        this.appId = appId;
        this.mchId = mchId;
        this.apiSecret = apiSecret;
    }

    @Override
    public String getCodeUrl(WXPayUnifiedOrderRequest request) {
        return unifiedOrder(request).getCodeUrl();
    }

    /**
     * 获取公共参数
     *
     * @return 公共参数
     */
    private Map<String, String> getPublicParameters() {
        Map<String, String> parameters = new TreeMap<String, String>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", WXPayUtil.generateNonceStr());
        return parameters;
    }

    private String execute(String url, String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(data, Charset.forName("UTF-8"));
        httpPost.addHeader(HTTP.CONTENT_TYPE, "text/xml");
        httpPost.setEntity(postEntity);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse callWeChatResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = callWeChatResponse.getEntity();
        return EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
    }

    @Override
    public WXPayUnifiedOrderResponse unifiedOrder(WXPayUnifiedOrderRequest request) throws WXPayApiException {
        Map<String, String> requestMap = getPublicParameters();

        requestMap.put("sign_type", WXPayConstants.MD5);
        requestMap.put("body", request.getBody());
        requestMap.put("detail", request.getDetail());
        requestMap.put("attach", request.getAttach());
        requestMap.put("out_trade_no", request.getOutTradeNo());
        requestMap.put("total_fee", request.getTotalFee());
        requestMap.put("spbill_create_ip", "127.0.0.1");
        requestMap.put("notify_url", request.getNotifyUrl());
        requestMap.put("trade_type", request.getTradeType());

        try {
            String sign = WXPayUtil.generateSignature(requestMap, apiSecret);
            requestMap.put("sign", sign);

            String data = WXPayUtil.mapToXml(requestMap);
            String url = String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.UNIFIEDORDER_URL_SUFFIX);
            // 请求接口
            String result = this.execute(url, data);

            log.info("WXPayService unifiedOrder result {}", result.replaceAll("\\n", ""));
            // 接口响应
            Map<String, String> responseMap = WXPayUtil.xmlToMap(result);

            if (responseMap.containsKey("return_code") && StringUtils.equals("FAIL", responseMap.get("return_code"))) {
                throw new WXPayApiException("WXPayService unifiedOrder error");
            }

            WXPayUnifiedOrderResponse response = new WXPayUnifiedOrderResponse();
            response.setReturnCode(responseMap.get("return_code"));
            response.setReturnMsg(responseMap.get("return_msg"));

            // 以下字段在return_code为SUCCESS的时候有返回
            response.setAppId(responseMap.get("appid"));
            response.setMchId(responseMap.get("mch_id"));
            response.setNonceStr(responseMap.get("nonce_str"));
            response.setSign(responseMap.get("sign"));
            response.setResultCode(responseMap.get("result_code"));
            /*
             * return_code 和result_code都为SUCCESS的时候有返回
             */
            response.setTradeType(responseMap.get("trade_type"));
            // 预支付交易会话标识
            response.setPrepayId(responseMap.get("prepay_id"));
            // 二维码链接
            response.setCodeUrl(responseMap.get("code_url"));

            return response;
        } catch (Exception e) {
            throw new WXPayApiException("WXPayService unifiedOrder error", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WXPayOrderQueryResponse queryOrder(WXPayOrderQueryRequest request) {
        Map<String, String> requestMap = getPublicParameters();
        requestMap.put("out_trade_no",request.getOutTradeNo());
        WXPayOrderQueryResponse response = new WXPayOrderQueryResponse();
        try {
            String sign = WXPayUtil.generateSignature(requestMap, apiSecret);
            requestMap.put("sign", sign);

            String data = WXPayUtil.mapToXml(requestMap);
            String url = String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.ORDERQUERY_URL_SUFFIX);
            // 请求接口
            String result = this.execute(url, data);
            log.info("WXPayService queryOrder result {}", result.replaceAll("\\n", ""));
            Map<String, String> responseMap = WXPayUtil.xmlToMap(result);
            if (responseMap.containsKey("return_code") && StringUtils.equals("FAIL", responseMap.get("return_code"))) {
                throw new WXPayApiException("queryOrder error");
            }
            response.setReturnCode(responseMap.get("return_code"));
            response.setReturnMsg(responseMap.get("return_msg"));
            // 以下字段在return_code为SUCCESS的时候有返回
            response.setAppId(responseMap.get("appid"));
            response.setMchId(responseMap.get("mch_id"));
            response.setNonceStr(responseMap.get("nonce_str"));
            response.setSign(responseMap.get("sign"));
            response.setResultCode(responseMap.get("result_code"));
            // 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回
            response.setTradeType(responseMap.get("trade_type"));
            response.setTradeState(responseMap.get("trade_state"));
            response.setBankType(responseMap.get("bank_type"));
            response.setTotalFee(responseMap.get("total_fee"));
            response.setCashFee(responseMap.get("cash_fee"));
            response.setTimeEnd(String.valueOf(WXPayUtil.getCurrentTimestamp()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ApplyRefundResponse refund(ApplyRefundRequest request) {
        Map<String, String> requestMap = getPublicParameters();

        String sign = null;
        requestMap.put("out_refund_no", request.getOutRefundNo());
        requestMap.put("out_trade_no", request.getOutTradeNo());
        requestMap.put("refund_fee", request.getRefundFee());
        requestMap.put("total_fee", request.getTotalFee());
        try {
            sign = WXPayUtil.generateSignature(requestMap, apiSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestMap.put("sign", sign);

        ApplyRefundResponse response = new ApplyRefundResponse();
        FileInputStream inputStream = null;
        try {
            String data = WXPayUtil.mapToXml(requestMap);
            String url = String.format("%s%s", WXPayConstants.DOMAIN_API, WXPayConstants.REFUND_URL_SUFFIX);
            // 请求接口
            // 证书--------------------
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            String property =System.getProperty("user.dir")+ RELATIVE_PATH;
            inputStream = new FileInputStream(new File(property));//P12文件目录
            keyStore.load(inputStream,mchId.toCharArray());
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            // 证书--------------------
            HttpResponse callWeChatResponse = httpclient.execute(httpost);
            HttpEntity httpEntity = callWeChatResponse.getEntity();
            String result = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));

            log.info("WXPayService applyRefund result {}", result.replaceAll("\\n", ""));
            Map<String, String> responseMap = WXPayUtil.xmlToMap(result);
            if (responseMap.containsKey("return_code") && StringUtils.equals("FAIL", responseMap.get("return_code"))) {
                throw new WXPayApiException("applyRefund error");
            }
            response.setReturnCode(responseMap.get("return_code"));
            response.setReturnMsg(responseMap.get("return_msg"));
            // 以下字段在return_code为SUCCESS的时候有返回
            response.setResultCode(responseMap.get("result_code"));
            response.setAppId(responseMap.get("appid"));
            response.setMchId(responseMap.get("mch_id"));
            response.setNonceStr(responseMap.get("nonce_str"));
            response.setSign(responseMap.get("sign"));
            response.setTransactionId(responseMap.get("transaction_id"));
            response.setOutTradeNo(responseMap.get("out_trade_no"));
            response.setRefundId(responseMap.get("refund_id"));
            response.setRefundFee(responseMap.get("refund_fee"));
            response.setTotalFee(responseMap.get("total_fee"));
            response.setCashFee(responseMap.get("cash_fee"));
            response.setErrCodeDes(responseMap.get("err_code_des"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    public Object complete(Function function) {
        return null;
    }

    private String getPath() throws IOException {
        String property =System.getProperty("user.dir");
        File file = new File("src/main/resources/cert/apiclient_cert.p12");
        System.out.println(file.getName());
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();//标准的路径 ;
        String author =directory.getAbsolutePath();//绝对路径;
        System.out.println(courseFile);
        System.out.println(author);

        URL resource = this.getClass().getClassLoader().getResource("");
        assert resource != null;
        String path = resource.getPath();
        System.out.println(resource.getContent());
        String fileName = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();//获取文件路径
        System.out.println(fileName);
        String fileUtl = this.getClass().getResource("").getFile();
        System.out.println(fileUtl);
        return fileUtl;
    }
    public static void main(String[] args) throws ParserConfigurationException, Exception {
        WXPayServiceImpl wxPayService = new WXPayServiceImpl();
        String path1 = wxPayService.getPath();
//        ClassLoader classLoader = WXPayServiceImpl.class.getClassLoader();
//        URL resource = classLoader.getResource("E:\\cert\\apiclient_cert.p12");
//        assert resource != null;
//        String path = resource.getPath();
        System.out.println(path1);
    }
//    public  HttpPost payHttps(String url, String data) throws Exception {
//    KeyStore keyStore = KeyStore.getInstance("PKCS12");
//    FileInputStream instream = new FileInputStream(new File("D:\\cert\\apiclient_cert.p12"));//P12文件目录
//		try {
//        keyStore.load(instream,mchId.toCharArray());//这里写密码
//    } finally {
//        instream.close();
//    }
//    SSLContext sslcontext = SSLContexts.custom()
//            .loadKeyMaterial(keyStore, mchId.toCharArray())
//            .build();
//    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//            sslcontext,
//            new String[] { "TLSv1" },
//            null,
//            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//    CloseableHttpClient httpclient = HttpClients.custom()
//            .setSSLSocketFactory(sslsf)
//            .build();
//        HttpPost httpost = new HttpPost(url); // 设置响应头信息
//        httpost.addHeader("Connection", "keep-alive");
//        httpost.addHeader("Accept", "*/*");
//        httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        httpost.addHeader("Host", "api.mch.weixin.qq.com");
//        httpost.addHeader("X-Requested-With", "XMLHttpRequest");
//        httpost.addHeader("Cache-Control", "max-age=0");
//        httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
//        httpost.setEntity(new StringEntity(data, "UTF-8"));
//        return httpost;
//}
}
