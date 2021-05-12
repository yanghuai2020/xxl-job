package com.omni.job.admin.core.complete;

import com.omni.job.admin.core.conf.OmniJobAdminConfig;
import com.omni.job.admin.core.trigger.TriggerTypeEnum;
import com.omni.job.admin.core.model.OmniJobInfo;
import com.omni.job.admin.core.model.OmniJobLog;
import com.omni.job.admin.core.thread.JobTriggerPoolHelper;
import com.omni.job.admin.core.util.I18nUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author xuxueli 2020-10-30 20:43:10
 */
public class OmniJobCompleter {
    private static Logger logger = LoggerFactory.getLogger(OmniJobCompleter.class);

    /**
     * common fresh handle entrance (limit only once)
     *
     * @param omniJobLog
     * @return
     */
    public static int updateHandleInfoAndFinish(OmniJobLog omniJobLog) {

        // finish
        finishJob(omniJobLog);

        // text最大64kb 避免长度过长
        if (omniJobLog.getHandleMsg().length() > 15000) {
            omniJobLog.setHandleMsg( omniJobLog.getHandleMsg().substring(0, 15000) );
        }

        // fresh handle
        return OmniJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateHandleInfo(omniJobLog);
    }


    /**
     * do somethind to finish job
     */
    private static void finishJob(OmniJobLog omniJobLog){

        // 1、handle success, to trigger child job
        String triggerChildMsg = null;
        if (XxlJobContext.HANDLE_COCE_SUCCESS == omniJobLog.getHandleCode()) {
            OmniJobInfo omniJobInfo = OmniJobAdminConfig.getAdminConfig().getXxlJobInfoDao().loadById(omniJobLog.getJobId());
            if (omniJobInfo !=null && omniJobInfo.getChildJobId()!=null && omniJobInfo.getChildJobId().trim().length()>0) {
                triggerChildMsg = "<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>"+ I18nUtil.getString("jobconf_trigger_child_run") +"<<<<<<<<<<< </span><br>";

                String[] childJobIds = omniJobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    int childJobId = (childJobIds[i]!=null && childJobIds[i].trim().length()>0 && isNumeric(childJobIds[i]))?Integer.valueOf(childJobIds[i]):-1;
                    if (childJobId > 0) {

                        JobTriggerPoolHelper.trigger(childJobId, TriggerTypeEnum.PARENT, -1, null, null, null);
                        ReturnT<String> triggerChildResult = ReturnT.SUCCESS;

                        // add msg
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg1"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode()==ReturnT.SUCCESS_CODE?I18nUtil.getString("system_success"):I18nUtil.getString("system_fail")),
                                triggerChildResult.getMsg());
                    } else {
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg2"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i]);
                    }
                }

            }
        }

        if (triggerChildMsg != null) {
            omniJobLog.setHandleMsg( omniJobLog.getHandleMsg() + triggerChildMsg );
        }

        // 2、fix_delay trigger next
        // on the way

    }

    private static boolean isNumeric(String str){
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
