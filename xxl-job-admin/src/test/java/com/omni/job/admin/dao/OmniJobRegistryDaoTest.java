package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniJobRegistryDaoTest {

    @Resource
    private OmniJobRegistryDao omniJobRegistryDao;

    @Test
    public void test(){
        int ret = omniJobRegistryDao.registryUpdate("g1", "k1", "v1", new Date());
        if (ret < 1) {
            ret = omniJobRegistryDao.registrySave("g1", "k1", "v1", new Date());
        }

        List<OmniJobRegistry> list = omniJobRegistryDao.findAll(1, new Date());

        int ret2 = omniJobRegistryDao.removeDead(Arrays.asList(1));
    }

}
