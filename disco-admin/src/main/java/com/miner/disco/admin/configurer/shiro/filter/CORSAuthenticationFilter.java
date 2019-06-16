package com.miner.disco.admin.configurer.shiro.filter;

import com.miner.disco.admin.exception.AuthErrorCode;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.JsonParser;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: chencx
 * @create: 2019-01-09
 **/
public class CORSAuthenticationFilter extends FormAuthenticationFilter {
    public CORSAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        ViewData viewData = ViewData.builder().status(AuthErrorCode.NO_SESSION.getCode()).message(AuthErrorCode.NO_SESSION.getMessage()).build();
        writer.write(JsonParser.serializeToJson(viewData));
        writer.close();
        return false;
    }
}
