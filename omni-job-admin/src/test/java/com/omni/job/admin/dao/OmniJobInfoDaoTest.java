package com.omni.job.admin.dao;

import com.omni.job.admin.core.scheduler.MisfireStrategyEnum;
import com.omni.job.admin.core.scheduler.ScheduleTypeEnum;
import com.omni.job.admin.core.model.OmniJobInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmniJobInfoDaoTest {
	
	@Resource
	private OmniJobInfoDao omniJobInfoDao;
	
	@Test
	public void pageList(){
		List<OmniJobInfo> list = omniJobInfoDao.pageList(0, 20, 0, -1, null, null, null);
		int list_count = omniJobInfoDao.pageListCount(0, 20, 0, -1, null, null, null);
		
		System.out.println(list);
		System.out.println(list_count);

		List<OmniJobInfo> list2 = omniJobInfoDao.getJobsByGroup(1);
	}
	
	@Test
	public void save_load(){
		OmniJobInfo info = new OmniJobInfo();
		info.setJobGroup(1);
		info.setJobDesc("desc");
		info.setAuthor("setAuthor");
		info.setAlarmEmail("setAlarmEmail");
		info.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
		info.setScheduleConf(String.valueOf(33));
		info.setMisfireStrategy(MisfireStrategyEnum.DO_NOTHING.name());
		info.setExecutorRouteStrategy("setExecutorRouteStrategy");
		info.setExecutorHandler("setExecutorHandler");
		info.setExecutorParam("setExecutorParam");
		info.setExecutorBlockStrategy("setExecutorBlockStrategy");
		info.setGlueType("setGlueType");
		info.setGlueSource("setGlueSource");
		info.setGlueRemark("setGlueRemark");
		info.setChildJobId("1");

		info.setAddTime(new Date());
		info.setUpdateTime(new Date());
		info.setGlueUpdatetime(new Date());

		int count = omniJobInfoDao.save(info);

		OmniJobInfo info2 = omniJobInfoDao.loadById(info.getId());
		info.setScheduleType(ScheduleTypeEnum.FIX_RATE.name());
		info.setScheduleConf(String.valueOf(44));
		info.setMisfireStrategy(MisfireStrategyEnum.FIRE_ONCE_NOW.name());
		info2.setJobDesc("desc2");
		info2.setAuthor("setAuthor2");
		info2.setAlarmEmail("setAlarmEmail2");
		info2.setExecutorRouteStrategy("setExecutorRouteStrategy2");
		info2.setExecutorHandler("setExecutorHandler2");
		info2.setExecutorParam("setExecutorParam2");
		info2.setExecutorBlockStrategy("setExecutorBlockStrategy2");
		info2.setGlueType("setGlueType2");
		info2.setGlueSource("setGlueSource2");
		info2.setGlueRemark("setGlueRemark2");
		info2.setGlueUpdatetime(new Date());
		info2.setChildJobId("1");

		info2.setUpdateTime(new Date());
		int item2 = omniJobInfoDao.update(info2);

		omniJobInfoDao.delete(info2.getId());

		List<OmniJobInfo> list2 = omniJobInfoDao.getJobsByGroup(1);

		int ret3 = omniJobInfoDao.findAllCount();

	}

}
