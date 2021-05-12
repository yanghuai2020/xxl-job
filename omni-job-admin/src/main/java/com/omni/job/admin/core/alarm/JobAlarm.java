package com.omni.job.admin.core.alarm;

import com.omni.job.admin.core.model.OmniJobInfo;
import com.omni.job.admin.core.model.OmniJobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(OmniJobInfo info, OmniJobLog jobLog);

}
