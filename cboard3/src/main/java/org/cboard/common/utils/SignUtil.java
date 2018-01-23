package org.cboard.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名生成与验证工具类
 * @author wayne
 *
 */
public class SignUtil {

	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

	/**
	 * 获取时间戳
	 */
	public static String getTimestamp() {
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		return timestamp;
	}
	
	/**
	 * 生成签名加密串
	 * 加密2次在验证
	 */
	public static String getSign(String timestamp, String username, String password) {
		return DigestUtils.md5Hex(DigestUtils.md5Hex(timestamp + username + password));
	}

	/**
	 * 验证签名
	 */
	public static boolean validate(String timestamp, String username, String password, String sign) throws Exception {
		logger.info("签权验证开始[timestamp=" + timestamp + ", password=" + password + ", username=" + username + ", sign=" + sign + "]");
		if (StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(sign) 
				&& StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			String serverSign = SignUtil.getSign(timestamp, username, password);
			if (sign.equals(serverSign)) {
				logger.info("签权验证成功");
				return true;
			}
		}
		logger.info("签权验证失败");
		return false;
	}
	
	public static void main(String[] args) {
	}
	
}
