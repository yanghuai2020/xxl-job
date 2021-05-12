package com.omni.job.admin.service;

import com.jd.common.web.cookie.CookieUtils;
import com.jd.ssa.utils.SSOHelper;
import com.omni.job.admin.core.util.CookieUtil;
import com.omni.job.admin.core.util.JacksonUtil;
import com.omni.job.admin.dao.OmniJobUserDao;
import com.omni.job.admin.core.model.OmniJobUser;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */
@Configuration
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    @Resource
    private OmniJobUserDao omniJobUserDao;


    private String makeToken(OmniJobUser omniJobUser){
        String tokenJson = JacksonUtil.writeValueAsString(omniJobUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private OmniJobUser parseToken(String tokenHex){
        OmniJobUser omniJobUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            omniJobUser = JacksonUtil.readValue(tokenJson, OmniJobUser.class);
        }
        return omniJobUser;
    }


    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember){


        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.remove(request, response, "sso.jd.com");
        CookieUtils cookieUtils = new CookieUtils();
        cookieUtils.deleteCookie(response, "sso.jd.com");
        SSOHelper.logout(response, "sso.jd.com");
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public OmniJobUser ifLogin(HttpServletRequest request, HttpServletResponse response){
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            OmniJobUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }

        }
        return null;
    }


}
