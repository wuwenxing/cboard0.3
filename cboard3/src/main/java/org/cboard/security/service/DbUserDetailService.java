package org.cboard.security.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cboard.common.context.Constants;
import org.cboard.common.enums.CompanyEnum;
import org.cboard.common.enums.SystemConfigEnum;
import org.cboard.common.utils.AesUtil;
import org.cboard.common.utils.DateUtil;
import org.cboard.common.utils.SignUtil;
import org.cboard.common.utils.SystemConfigUtil;
import org.cboard.dto.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * Created by yfyuan on 2016/12/14.
 */
public class DbUserDetailService extends JdbcDaoImpl {

    protected List<UserDetails> loadUsersByUsername(String username) {
    	String timestamp = "";
    	String companyId = "";
    	// 加密串结尾一致，则从业务系统跳转过来
    	if(StringUtils.isNotBlank(username) && username.endsWith(Constants.END_WITH_STR)){
    		username = username.substring(0, username.length() - Constants.END_WITH_STR.length());
    		byte[] decryptFrom = AesUtil.parseHexStr2Byte(username);
    		username = new String(AesUtil.decrypt(decryptFrom));
        	if(StringUtils.isNotBlank(username)){
        		String[] tempAry = username.split(",");
        		if(tempAry.length >= 3){
        			username = tempAry[0];
            		timestamp = tempAry[1];
            		companyId = tempAry[2];
                    // 需有效期校验,超过24*60分钟失效
                	if(StringUtils.isNotBlank(timestamp)){
            			int minutes = -24*60;
            			Date requestTime = DateUtil.stringToDate(timestamp, "yyyyMMddHHmmss");
            			Date nowDate = DateUtil.addMinutes(new Date(), minutes);
            			if (nowDate.after(requestTime)) {
            				logger.error("超过有效期");
            				return new ArrayList<UserDetails>();
            			}
                	}
        		}
        	}
    	}
    	final String usernameF = username;
    	final String timestampF = timestamp;
    	final String companyIdF = companyId;
    	return this.getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[]{usernameF}, new RowMapper() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String userId = rs.getString(1);
                String username = rs.getString(2);
                String loginname = rs.getString(3);
                String password = rs.getString(4);
                boolean enabled = rs.getBoolean(5);
                String companyIds = rs.getString(6);
                // 如果是从业务系统跳转过来，需对password进行MD5加密；
            	if(StringUtils.isNotBlank(timestampF)){
            		password = SignUtil.getSign(timestampF, loginname, password);
            	}
                User user = new User(loginname, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
                user.setUserId(userId);
                user.setName(username);
                // 如果是从业务系统跳转过来，需设置companyId
            	if(StringUtils.isNotBlank(companyIdF)){
                    user.setCompany(companyIdF);
                    user.setCompanyName(CompanyEnum.format(companyIdF));
            	}else{
            		if(SystemConfigUtil.getProperty(SystemConfigEnum.adminUserId).equals(userId)){
            			// 超级管理员默认设置fx
            			user.setCompany(CompanyEnum.fx.getLabelKey());
            			user.setCompanyName(CompanyEnum.fx.getValue());
            		}else{
                		// 默认设置用户第一个业务权限
                		if(StringUtils.isNotBlank(companyIds)){
                			user.setCompany(companyIds.split(",")[0]);
                			user.setCompanyName(CompanyEnum.format(companyIds.split(",")[0]));
                		}
            		}
            	}
                user.setCompanyIds(companyIds);
                return user;
            }
        });
    }

    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        return userFromUserQuery;
    }

}
