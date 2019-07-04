package com.miner.disco.basic.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author: wz1016_vip@163.com  2019/7/4
 */
@Slf4j
public class AsyncHttpClientUtil {
    private static CloseableHttpAsyncClient httpClient;
    private static volatile boolean isClientStart;

    /**
     * 创建CloseableHttpAsyncClient
     * @return
     */
    private static CloseableHttpAsyncClient createCustomAsyncClient()
    {
        Preconditions.checkState(!isClientStart, "客户端HttpAsyncClient已经建立过了");
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setConnectTimeout(60000)
                .setSoTimeout(60000)
                .build();
        // 设置超时时间 毫秒为单位
        RequestConfig requestConfig = RequestConfig
                .copy(RequestConfig.DEFAULT)
                .setConnectTimeout(60000)
                .build();
        return HttpAsyncClients
                .custom()
                .setDefaultIOReactorConfig(ioReactorConfig)
                .setDefaultRequestConfig(requestConfig)
                .build();

    }


    public static void startHttpClient()
    {
        httpClient = createCustomAsyncClient();
        httpClient.start();
        isClientStart = true;
    }

    public static void closeHttpClient()
    {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isClientStart = false;
    }

    public static void http(String method,
                            String url,
                            Map<String, String> parameters,
                            StringFutureCallback callback)
    {
        Preconditions.checkNotNull(method);
        if (HttpMethod.GET.name().equalsIgnoreCase(method))
        {
            doGet(url, callback);
        }
        else if (HttpMethod.POST.name().equalsIgnoreCase(method))
        {
            doPost(url, parameters, callback);
        }
    }


    public static void doGet(String url, StringFutureCallback callback)
    {
        Preconditions.checkArgument(isClientStart, "还没有建立Http Client");
        HttpUriRequest request = new HttpGet(url);
        httpClient.execute(request, new DefaultFutureCallback(callback));
    }

    public static void doPost(String url, Map<String, String> parameters, StringFutureCallback callback)
    {
        Preconditions.checkArgument(isClientStart, "还没有建立Http Client");
        HttpPost httpPost = new HttpPost(url);
        if (parameters != null)
        {
            List<BasicNameValuePair> pairs = Lists.newArrayList();
            parameters.forEach((k, v) -> pairs.add(new BasicNameValuePair(k,v)));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
        }
        httpClient.execute(httpPost, new DefaultFutureCallback(callback));
    }


    /**
     * 字符串类型结果回调
     */
    @FunctionalInterface
    public interface StringFutureCallback
    {
        void success(String content);
    }


    public static class DefaultFutureCallback implements FutureCallback<HttpResponse>
    {
        private StringFutureCallback callback;
        public DefaultFutureCallback(StringFutureCallback callback)
        {
            this.callback = callback;
        }
        @Override
        public void completed(HttpResponse httpResponse)
        {
            HttpEntity entity = httpResponse.getEntity();
            String content = "";
            try
            {
                content = EntityUtils.toString(entity, "UTF-8");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            callback.success(content);
        }

        @Override
        public void failed(Exception e)
        {
            e.printStackTrace();
        }

        @Override
        public void cancelled()
        {
            log.debug("http request cancelled");
        }
    }
}