package com.xxl.job.admin.core.model;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author xuxueli 2019-05-04 16:43:12
 */
@Data
public class XxlJobUser {
	private Long id;
	private Long jobGroupId;
	private String pin;
	private String username;
	private int role;
    private String permission;	// 权限：执行器ID列表，多个逗号分割


    // plugin
    public boolean validPermission(int jobGroup){
        if (this.role == 1) {
            return true;
        } else {
            if (StringUtils.hasText(this.permission)) {
                for (String permissionItem : this.permission.split(",")) {
                    if (String.valueOf(jobGroup).equals(permissionItem)) {
                        return true;
                    }
                }
            }
            return false;
        }

    }
}
