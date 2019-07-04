package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.AsyncHttpClientUtil;
import com.miner.disco.basic.util.Encrypt;
import com.miner.disco.basic.util.HttpClientUtil;
import com.miner.disco.mch.consts.Const;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: wz1016_vip@163.com  2019/7/4
 */
@RestController
public class BetaTester {

    /**
     * 测试异步回调
     * 1.访问该接口
     * @return
     */
    @PostMapping(value = "/zaki/test",headers = Const.API_VERSION_1_0_0)
    public ViewData test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String callbackUrl = getPath(request)+"/zaki/doSth";
        TreeMap<String, String> tree = new TreeMap<>();
        tree.put("w","this is w value");
        tree.put("z","this is z value");
        try {
            AsyncHttpClientUtil.startHttpClient();
            AsyncHttpClientUtil.StringFutureCallback stringFutureCallback = System.out::println;
            Future<HttpResponse> http = AsyncHttpClientUtil.http(HttpMethod.POST.name(), callbackUrl, tree, stringFutureCallback);
            HttpResponse httpResponse = http.get();
            HttpEntity entity = httpResponse.getEntity();

            System.out.println("entity>>>>>>" + EntityUtils.toString(entity, Charset.forName("UTF-8")));
        }  finally {
            AsyncHttpClientUtil.closeHttpClient();
        }
        return ViewData.builder().message("ok").build();
    }


    @PostMapping(value = "/zaki/doSth",headers = Const.API_VERSION_1_0_0)
    public void doSomething(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String w = request.getParameter("z");
        response.getWriter().write(">>>>>>>>>this is response from ./zaki/doSth");
        System.out.println(w);
    }

    private String getPath(HttpServletRequest request) {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }





}
