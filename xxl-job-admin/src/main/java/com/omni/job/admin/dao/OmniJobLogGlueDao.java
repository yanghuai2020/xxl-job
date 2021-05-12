package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobLogGlue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * job log for glue
 * @author xuxueli 2016-5-19 18:04:56
 */
@Mapper
public interface OmniJobLogGlueDao {
	
	public int save(OmniJobLogGlue omniJobLogGlue);
	
	public List<OmniJobLogGlue> findByJobId(@Param("jobId") int jobId);

	public int removeOld(@Param("jobId") int jobId, @Param("limit") int limit);

	public int deleteByJobId(@Param("jobId") int jobId);
	
}
