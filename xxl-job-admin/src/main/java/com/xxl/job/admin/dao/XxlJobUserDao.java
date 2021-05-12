package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface XxlJobUserDao {

	public List<XxlJobUser> listUserPin(@Param("jobGroupId") Long jobGroupId);

	public int save(XxlJobUser xxlJobUser);

	public int delete(XxlJobUser xxlJobUser);

	public int deleteByGroupId(@Param("groupId") int groupId);

}
