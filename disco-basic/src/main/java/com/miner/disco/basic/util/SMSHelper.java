package com.miner.disco.basic.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/8/2
 */
public class SMSHelper {

    public static boolean sendInternationalMessage(String content, final String phone) throws IOException {
        content = "【麦罗佛伦】" + content;
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://dx.ipyy.net/I18NSms.aspx");

        NameValuePair[] data = {
                new BasicNameValuePair("action", "send"),
                new BasicNameValuePair("userid", ""),
                new BasicNameValuePair("account", "hualian"),
                new BasicNameValuePair("password", "hualian0282"),
                new BasicNameValuePair("mobile", phone),
                new BasicNameValuePair("code", "8"),
                new BasicNameValuePair("content", encodeHexStr(8,content)),
                new BasicNameValuePair("sendTime", ""),
                new BasicNameValuePair("extno", "")
        };
        List<NameValuePair> list = new ArrayList<>(Arrays.asList(data));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        String rseult = EntityUtils.toString(response.getEntity());
        return rseult.contains("Success");
    }

    public static boolean sendChinaMessage(String content, final String phone) throws IOException {
        content = "【麦罗佛伦】" + content;
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://dx.ipyy.net/sms.aspx");

        NameValuePair[] data = {
                new BasicNameValuePair("action", "send"),
                new BasicNameValuePair("userid", ""),
                new BasicNameValuePair("account", "hualian"),
                new BasicNameValuePair("password", "hualian0282"),
                new BasicNameValuePair("mobile", phone),
                new BasicNameValuePair("code", "8"),
                new BasicNameValuePair("content", content),
                new BasicNameValuePair("sendTime", ""),
                new BasicNameValuePair("extno", "")
        };
        List<NameValuePair> list = new ArrayList<>(Arrays.asList(data));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        String rseult = EntityUtils.toString(response.getEntity());
        return rseult.contains("Success");
    }

    private static String encodeHexStr(final int dataCoding, final String realStr) {
        String strhex = "";
        try {
            byte[] bytSource = null;
            if (dataCoding == 15) {
                bytSource = realStr.getBytes("GBK");
            } else if (dataCoding == 3) {
                bytSource = realStr.getBytes("ISO-8859-1");
            } else if (dataCoding == 8) {
                bytSource = realStr.getBytes("UTF-16BE");
            } else {
                bytSource = realStr.getBytes("ASCII");
            }
            strhex = bytesToHexString(bytSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strhex;
    }

    private static String bytesToHexString(final byte[] bArray) {
        final StringBuilder sb = new StringBuilder(bArray.length);
        for (byte aBArray : bArray) {
            final String sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2) {
                sb.append("0");
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
        //System.out.println("Verification Code:789248.If you do not send it, please ignore this message.".length());
        SMSHelper.sendInternationalMessage("Verification Code:666487.If you do not send it, please ignore this message.", "18286623419");
//        SMSHelper.sendChinaMessage("Verification Code:653946.If you do not send it, please ignore this message.", "18782215442");
    }

}
