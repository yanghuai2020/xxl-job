package com.omni.job.admin.dao;

import com.omni.job.admin.core.model.OmniJobGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface OmniJobGroupDao {

    public List<OmniJobGroup> findAll(@Param("pin") String pin);

    public List<OmniJobGroup> findAllJobGroup();

    public List<OmniJobGroup> findByAddressType(@Param("addressType") int addressType);

    public int save(OmniJobGroup omniJobGroup);

    public int update(OmniJobGroup omniJobGroup);

    public int remove(@Param("id") int id);

    public OmniJobGroup load(@Param("id") int id);

    public List<OmniJobGroup> pageList(@Param("offset") int offset,
                                       @Param("pagesize") int pagesize,
                                       @Param("appname") String appname,
                                       @Param("title") String title,
                                       @Param("pin") String pin);

    public List<OmniJobGroup> pageListAll(@Param("offset") int offset,
                                          @Param("pagesize") int pagesize,
                                          @Param("appname") String appname,
                                          @Param("title") String title);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title,
                             @Param("pin") String pin);


    public int pageListCountAll(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("appname") String appname,
                             @Param("title") String title);

}
