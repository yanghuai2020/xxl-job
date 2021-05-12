package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * job log
 * @author xuxueli 2016-1-12 18:03:06
 */
@Mapper
public interface OmniJobLogDao {

	// exist jobId not use jobGroup, not exist use jobGroup
	public List<OmniJobLog> pageList(@Param("offset") int offset,
                                     @Param("pagesize") int pagesize,
                                     @Param("jobGroup") int jobGroup,
                                     @Param("jobId") int jobId,
                                     @Param("triggerTimeStart") Date triggerTimeStart,
                                     @Param("triggerTimeEnd") Date triggerTimeEnd,
                                     @Param("logStatus") int logStatus,
                                     @Param("pin") String pin);

    public List<OmniJobLog> pageListAll(@Param("offset") int offset,
                                        @Param("pagesize") int pagesize,
                                        @Param("jobGroup") int jobGroup,
                                        @Param("jobId") int jobId,
                                        @Param("triggerTimeStart") Date triggerTimeStart,
                                        @Param("triggerTimeEnd") Date triggerTimeEnd,
                                        @Param("logStatus") int logStatus);



	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("jobGroup") int jobGroup,
							 @Param("jobId") int jobId,
							 @Param("triggerTimeStart") Date triggerTimeStart,
							 @Param("triggerTimeEnd") Date triggerTimeEnd,
							 @Param("logStatus") int logStatus,
                             @Param("pin") String pin);



    public int pageListCountAll(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("jobGroup") int jobGroup,
                             @Param("jobId") int jobId,
                             @Param("triggerTimeStart") Date triggerTimeStart,
                             @Param("triggerTimeEnd") Date triggerTimeEnd,
                             @Param("logStatus") int logStatus);
	
	public OmniJobLog load(@Param("id") long id);

	public long save(OmniJobLog omniJobLog);

	public int updateTriggerInfo(OmniJobLog omniJobLog);

	public int updateHandleInfo(OmniJobLog omniJobLog);
	
	public int delete(@Param("jobId") int jobId);

	public Map<String, Object> findLogReport(@Param("from") Date from,
											 @Param("to") Date to);

	public List<Long> findClearLogIds(@Param("jobGroup") int jobGroup,
									  @Param("jobId") int jobId,
									  @Param("clearBeforeTime") Date clearBeforeTime,
									  @Param("clearBeforeNum") int clearBeforeNum,
									  @Param("pagesize") int pagesize);
	public int clearLog(@Param("logIds") List<Long> logIds);

	public List<Long> findFailJobLogIds(@Param("pagesize") int pagesize);

	public int updateAlarmStatus(@Param("logId") long logId,
								 @Param("oldAlarmStatus") int oldAlarmStatus,
								 @Param("newAlarmStatus") int newAlarmStatus);

	public List<Long> findLostJobIds(@Param("losedTime") Date losedTime);

}
