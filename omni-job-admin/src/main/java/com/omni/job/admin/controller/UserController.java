package com.omni.job.admin.controller;

import com.jd.common.web.LoginContext;
import com.omni.job.admin.dao.OmniJobGroupDao;
import com.omni.job.admin.dao.OmniJobUserDao;
import com.omni.job.admin.constants.StaticDataInfo;
import com.omni.job.admin.controller.annotation.PermissionLimit;
import com.omni.job.admin.core.model.OmniJobGroup;
import com.omni.job.admin.core.model.OmniJobUser;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    private OmniJobUserDao omniJobUserDao;

    @Resource
    private OmniJobGroupDao omniJobGroupDao;

    @RequestMapping
    @PermissionLimit(adminuser = true)
    public String index(Model model) {
        // 执行器列表
        List<OmniJobGroup> groupList = null;
        if (StaticDataInfo.ADMIN_PIN.equalsIgnoreCase(LoginContext.getLoginContext().getPin())) {
            groupList = omniJobGroupDao.findAllJobGroup();
        } else {
            groupList = omniJobGroupDao.findAll(LoginContext.getLoginContext().getPin());
        }


        model.addAttribute("groupList", groupList);

        return "user/user.index";
    }





    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Map<String, Object> pageList(Long jobGroupId) {
        List<OmniJobUser> list = omniJobUserDao.listUserPin(jobGroupId);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("data", list);  					// 分页列表
        return maps;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> add(OmniJobUser omniJobUser) {
        omniJobUser.setPin(omniJobUser.getPin());
        omniJobUserDao.save(omniJobUser);
        return ReturnT.SUCCESS;
    }


    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> remove(OmniJobUser omniJobUser) {
        omniJobUserDao.delete(omniJobUser);
        return ReturnT.SUCCESS;
    }


}
