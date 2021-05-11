package com.xxl.job.admin.controller.interceptor;

import com.jd.common.web.LoginContext;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.service.LoginService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: xstore-wms-dpmanager
 * @description: 登录验证过滤器
 * @author: yangh
 * @create: 2018-12-28 14:08
 **/
@Configuration
public class SsoOmniInterceptor implements HandlerInterceptor {

    private final static List<String> WHITE_USER = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String reqUrl  = httpServletRequest.getRequestURL() != null ? httpServletRequest.getRequestURL().toString():"";
        if (reqUrl.contains("/omni-job/api/") || reqUrl.endsWith(".js") || reqUrl.endsWith(".css")) {
            return true;
        }

        WHITE_USER.add("yanghuai9");
        boolean login = LoginContext.getLoginContext().getLogin();
        String pin = LoginContext.getLoginContext().getPin();


        if (!login) {
            return false;
        }

        XxlJobUser loginUser = new XxlJobUser();
        loginUser.setUsername(pin);
        loginUser.setId(1L);
        loginUser.setRole(1);
        httpServletRequest.setAttribute(LoginService.LOGIN_IDENTITY_KEY, loginUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
