package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobLogReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * job log
 * @author xuxueli 2019-11-22
 */
@Mapper
public interface OmniJobLogReportDao {

	public int save(OmniJobLogReport omniJobLogReport);

	public int update(OmniJobLogReport omniJobLogReport);

	public List<OmniJobLogReport> queryLogReport(@Param("triggerDayFrom") Date triggerDayFrom,
                                                 @Param("triggerDayTo") Date triggerDayTo);

	public OmniJobLogReport queryLogReportTotal();

}
