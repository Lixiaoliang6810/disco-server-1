package com.miner.disco.basic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName UrlExplorer
 * @Auther: wz1016_vip@163.com
 * @Date: 2019/6/9 14:32
 * @Description: TODO
 */
public class UrlExplorer {


    /**
     * 拉取网页所有内容
     * @param url
     * @return string
     */
    public static String explore(String url){
        String content = "";
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.toString())));
            content = readByBuffer(rd);
        }catch (Exception ignored){}
//        finally {
//            is.close(); ARM 块会自动关闭流
//        }
        return content;
    }

    public static String readByBuffer(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        //缓冲逐行读取
        while ( (line = reader.readLine()) != null ) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String explore = explore("http://www.baidu.com");
        System.out.println(explore);
    }
}
