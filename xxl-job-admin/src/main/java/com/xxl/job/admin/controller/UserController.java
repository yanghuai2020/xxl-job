package com.xxl.job.admin.controller;

import com.jd.common.web.LoginContext;
import com.xxl.job.admin.constants.StaticDataInfo;
import com.xxl.job.admin.controller.annotation.PermissionLimit;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.dao.XxlJobUserDao;
import com.xxl.job.admin.service.LoginService;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuxueli 2019-05-04 16:39:50
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private XxlJobUserDao xxlJobUserDao;
    @Resource
    private XxlJobGroupDao xxlJobGroupDao;

    @RequestMapping
    @PermissionLimit(adminuser = true)
    public String index(Model model) {
        // 执行器列表
        List<XxlJobGroup> groupList = null;
        if (StaticDataInfo.ADMIN_PIN.equalsIgnoreCase(LoginContext.getLoginContext().getPin())) {
            groupList = xxlJobGroupDao.findAllJobGroup();
        } else {
            groupList = xxlJobGroupDao.findAll(LoginContext.getLoginContext().getPin());
        }


        model.addAttribute("groupList", groupList);

        return "user/user.index";
    }





    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Map<String, Object> pageList(Long jobGroupId) {
        List<XxlJobUser> list = xxlJobUserDao.listUserPin(jobGroupId);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("data", list);  					// 分页列表
        return maps;
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public ReturnT<String> add(XxlJobUser xxlJobUser) {
        xxlJobUserDao.save(xxlJobUser);
        return ReturnT.SUCCESS;
    }


    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(XxlJobUser xxlJobUser) {
        xxlJobUserDao.delete(xxlJobUser);
        return ReturnT.SUCCESS;
    }


}
