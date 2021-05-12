package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobLog;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniJobLogDaoTest {

    @Resource
    private OmniJobLogDao omniJobLogDao;

    @Test
    public void test(){
        List<OmniJobLog> list = omniJobLogDao.pageList(0, 10, 1, 1, null, null, 1,"");
        int list_count = omniJobLogDao.pageListCount(0, 10, 1, 1, null, null, 1,"");

        OmniJobLog log = new OmniJobLog();
        log.setJobGroup(1);
        log.setJobId(1);

        long ret1 = omniJobLogDao.save(log);
        OmniJobLog dto = omniJobLogDao.load(log.getId());

        log.setTriggerTime(new Date());
        log.setTriggerCode(1);
        log.setTriggerMsg("1");
        log.setExecutorAddress("1");
        log.setExecutorHandler("1");
        log.setExecutorParam("1");
        ret1 = omniJobLogDao.updateTriggerInfo(log);
        dto = omniJobLogDao.load(log.getId());


        log.setHandleTime(new Date());
        log.setHandleCode(2);
        log.setHandleMsg("2");
        ret1 = omniJobLogDao.updateHandleInfo(log);
        dto = omniJobLogDao.load(log.getId());


        List<Long> ret4 = omniJobLogDao.findClearLogIds(1, 1, new Date(), 100, 100);

        int ret2 = omniJobLogDao.delete(log.getJobId());

    }

}
