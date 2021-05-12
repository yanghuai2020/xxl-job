package com.omni.job.admin.controller;

import com.omni.job.admin.core.util.I18nUtil;
import com.omni.job.admin.dao.OmniJobInfoDao;
import com.omni.job.admin.dao.OmniJobLogGlueDao;
import com.omni.job.admin.core.model.OmniJobInfo;
import com.omni.job.admin.core.model.OmniJobLogGlue;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * job code controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobcode")
public class JobCodeController {
	
	@Resource
	private OmniJobInfoDao omniJobInfoDao;
	@Resource
	private OmniJobLogGlueDao omniJobLogGlueDao;

	@RequestMapping
	public String index(HttpServletRequest request, Model model, int jobId) {
		OmniJobInfo jobInfo = omniJobInfoDao.loadById(jobId);
		List<OmniJobLogGlue> jobLogGlues = omniJobLogGlueDao.findByJobId(jobId);

		if (jobInfo == null) {
			throw new RuntimeException(I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
		}
		if (GlueTypeEnum.BEAN == GlueTypeEnum.match(jobInfo.getGlueType())) {
			throw new RuntimeException(I18nUtil.getString("jobinfo_glue_gluetype_unvalid"));
		}

		// valid permission
		JobInfoController.validPermission(request, jobInfo.getJobGroup());

		// Glue类型-字典
		model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());

		model.addAttribute("jobInfo", jobInfo);
		model.addAttribute("jobLogGlues", jobLogGlues);
		return "jobcode/jobcode.index";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ReturnT<String> save(Model model, int id, String glueSource, String glueRemark) {
		// valid
		if (glueRemark==null) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobinfo_glue_remark")) );
		}
		if (glueRemark.length()<4 || glueRemark.length()>100) {
			return new ReturnT<String>(500, I18nUtil.getString("jobinfo_glue_remark_limit"));
		}
		OmniJobInfo exists_jobInfo = omniJobInfoDao.loadById(id);
		if (exists_jobInfo == null) {
			return new ReturnT<String>(500, I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
		}
		
		// update new code
		exists_jobInfo.setGlueSource(glueSource);
		exists_jobInfo.setGlueRemark(glueRemark);
		exists_jobInfo.setGlueUpdatetime(new Date());

		exists_jobInfo.setUpdateTime(new Date());
		omniJobInfoDao.update(exists_jobInfo);

		// log old code
		OmniJobLogGlue omniJobLogGlue = new OmniJobLogGlue();
		omniJobLogGlue.setJobId(exists_jobInfo.getId());
		omniJobLogGlue.setGlueType(exists_jobInfo.getGlueType());
		omniJobLogGlue.setGlueSource(glueSource);
		omniJobLogGlue.setGlueRemark(glueRemark);

		omniJobLogGlue.setAddTime(new Date());
		omniJobLogGlue.setUpdateTime(new Date());
		omniJobLogGlueDao.save(omniJobLogGlue);

		// remove code backup more than 30
		omniJobLogGlueDao.removeOld(exists_jobInfo.getId(), 30);

		return ReturnT.SUCCESS;
	}
	
}
