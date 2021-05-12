package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface OmniJobUserDao {

	public List<OmniJobUser> listUserPin(@Param("jobGroupId") Long jobGroupId);

	public int save(OmniJobUser omniJobUser);

	public int delete(OmniJobUser omniJobUser);

	public int deleteByGroupId(@Param("groupId") int groupId);

}
