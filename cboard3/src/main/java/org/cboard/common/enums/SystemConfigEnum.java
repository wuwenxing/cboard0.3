package org.cboard.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：系统配置参数类型定义
 * @author wayne
 */
public enum SystemConfigEnum implements EnumIntf {
	// 与config.properties对应
	adminUserId("超级管理员ID", "admin_user_id")
	;
	
	private final String value;
	private final String labelKey;
	SystemConfigEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SystemConfigEnum> getList(){
		List<SystemConfigEnum> result = new ArrayList<SystemConfigEnum>();
		for(SystemConfigEnum ae : SystemConfigEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
