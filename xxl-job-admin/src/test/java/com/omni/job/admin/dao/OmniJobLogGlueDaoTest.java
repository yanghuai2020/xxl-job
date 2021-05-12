package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobLogGlue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniJobLogGlueDaoTest {

    @Resource
    private OmniJobLogGlueDao omniJobLogGlueDao;

    @Test
    public void test(){
        OmniJobLogGlue logGlue = new OmniJobLogGlue();
        logGlue.setJobId(1);
        logGlue.setGlueType("1");
        logGlue.setGlueSource("1");
        logGlue.setGlueRemark("1");

        logGlue.setAddTime(new Date());
        logGlue.setUpdateTime(new Date());
        int ret = omniJobLogGlueDao.save(logGlue);

        List<OmniJobLogGlue> list = omniJobLogGlueDao.findByJobId(1);

        int ret2 = omniJobLogGlueDao.removeOld(1, 1);

        int ret3 = omniJobLogGlueDao.deleteByJobId(1);
    }

}
