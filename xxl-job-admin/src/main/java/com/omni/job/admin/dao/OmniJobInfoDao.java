package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * job info
 * @author xuxueli 2016-1-12 18:03:45
 */
@Mapper
public interface OmniJobInfoDao {

	public List<OmniJobInfo> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("jobGroup") int jobGroup,
                                      @Param("triggerStatus") int triggerStatus,
                                      @Param("jobDesc") String jobDesc,
                                      @Param("executorHandler") String executorHandler,
                                      @Param("author") String author);
	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("jobGroup") int jobGroup,
							 @Param("triggerStatus") int triggerStatus,
							 @Param("jobDesc") String jobDesc,
							 @Param("executorHandler") String executorHandler,
							 @Param("author") String author);
	
	public int save(OmniJobInfo info);

	public OmniJobInfo loadById(@Param("id") int id);
	
	public int update(OmniJobInfo omniJobInfo);
	
	public int delete(@Param("id") long id);

	public List<OmniJobInfo> getJobsByGroup(@Param("jobGroup") int jobGroup);

	public int findAllCount();

	public List<OmniJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize );

	public int scheduleUpdate(OmniJobInfo omniJobInfo);


}
