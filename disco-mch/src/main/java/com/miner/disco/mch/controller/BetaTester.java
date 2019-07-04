package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.AsyncHttpClientUtil;
import com.miner.disco.basic.util.HttpClientUtil;
import com.miner.disco.mch.consts.Const;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;

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
    public ViewData test(HttpServletRequest request, HttpServletResponse response){
        System.out.println("start test >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        String callbackUrl = "http://192.168.3.86:8007/zaki/doSth";
        String s = request.getRequestURL().toString();
        TreeMap<String, String> tree = new TreeMap<>();
        tree.put("w","this is w value");
        tree.put("z","this is z value");
        try {
            HttpClientUtil.doPost(callbackUrl,tree);
            AsyncHttpClientUtil.startHttpClient();
            AsyncHttpClientUtil.StringFutureCallback stringFutureCallback = System.out::println;
            AsyncHttpClientUtil.http(HttpMethod.POST.name(),s,tree,stringFutureCallback);
        }finally {
            AsyncHttpClientUtil.closeHttpClient();
        }

        return ViewData.builder().message("ok").build();
    }


    @PostMapping(value = "/zaki/doSth",headers = Const.API_VERSION_1_0_0)
    public void doSomething(HttpServletRequest request, HttpServletResponse response){
        System.out.println("start doSomething <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        String w = request.getParameter("w");
        System.out.println(w);
    }
}
