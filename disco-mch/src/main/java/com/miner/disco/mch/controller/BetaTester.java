package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: wz1016_vip@163.com  2019/7/4
 */
@RestController
public class BetaTester {

    @PostMapping(value = "/zaki/test",headers = Const.API_VERSION_1_0_0)
    public ViewData test(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return ViewData.builder().message("ok").build();
    }


    @PostMapping(value = "/zaki/doSth",headers = Const.API_VERSION_1_0_0)
    public void doSomething(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String w = request.getParameter("z");
        response.getWriter().write(">>>>>>>>>this is response from ./zaki/doSth");
        System.out.println(w);
    }



    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 接受新连接线程，主要负责创建新连接
        NioEventLoopGroup boos = new NioEventLoopGroup();
        // 读取数据以及业务逻辑
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boos, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8000);
    }






}
