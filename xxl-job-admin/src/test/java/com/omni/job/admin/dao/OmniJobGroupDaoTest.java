package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobGroup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniJobGroupDaoTest {

    @Resource
    private OmniJobGroupDao omniJobGroupDao;

    @Test
    public void test(){
        List<OmniJobGroup> list = omniJobGroupDao.findAll("");

        List<OmniJobGroup> list2 = omniJobGroupDao.findByAddressType(0);

        OmniJobGroup group = new OmniJobGroup();
        group.setAppname("setAppName");
        group.setTitle("setTitle");
        group.setAddressType(0);
        group.setAddressList("setAddressList");
        group.setUpdateTime(new Date());

        int ret = omniJobGroupDao.save(group);

        OmniJobGroup group2 = omniJobGroupDao.load(group.getId());
        group2.setAppname("setAppName2");
        group2.setTitle("setTitle2");
        group2.setAddressType(2);
        group2.setAddressList("setAddressList2");
        group2.setUpdateTime(new Date());

        int ret2 = omniJobGroupDao.update(group2);

        int ret3 = omniJobGroupDao.remove(group.getId());
    }

}
