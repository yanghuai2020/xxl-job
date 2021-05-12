package com.omni.job.admin.execute;


import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Service;

@Service
public class ClearLogJob {

    @XxlJob("clearLogs")
    public void clearLogs() {
        System.out.println("clearLogs..........................");
    }


}
