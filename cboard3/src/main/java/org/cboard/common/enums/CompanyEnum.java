package org.cboard.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum CompanyEnum implements EnumIntf {
	
	fx("外汇", "1"),
	pm("贵金属", "2"),
	hx("恒信", "3"),
	cf("创富", "4")
	;
	
	private final String value;
	private final String labelKey;
	CompanyEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<CompanyEnum> getList(){
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		for(CompanyEnum ae : CompanyEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static List<CompanyEnum> getList(String companyIds){
		List<CompanyEnum> result = new ArrayList<CompanyEnum>();
		if(StringUtils.isNotBlank(companyIds)){
			String[] companyIdAry = companyIds.split(",");
			for(String companyIdStr: companyIdAry){
				CompanyEnum ce = CompanyEnum.find(companyIdStr);
				if(null != ce){
					result.add(ce);
				}
			}
		}
		return result;
	}
	
	public static String getAllCompanyIds(){
		String companyIds = "";
		for(CompanyEnum ae : CompanyEnum.values()){
			companyIds += ae.getLabelKey() + ",";
		}
		if(StringUtils.isNotBlank(companyIds)){
			companyIds = companyIds.substring(0, companyIds.length()-1);
		}
		return companyIds;
	}

	public static CompanyEnum find(String labelKey){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae;
			}
		}
		return null;
	}
	
	public static String format(String labelKey){
		for(CompanyEnum ae : CompanyEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public Long getLabelKeyLong() {
		return Long.parseLong(labelKey);
	}
	
}
