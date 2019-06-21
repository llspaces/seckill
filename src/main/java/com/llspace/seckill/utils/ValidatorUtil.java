package com.llspace.seckill.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
/**
 * <p>@filename ValidatorUtil</p>
 * <p>
 * <p>@description 自定义Validator工具方法类</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 17:04
 **/
public class ValidatorUtil {
	
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	public static boolean isMobile(String src) {
		if(StringUtils.isEmpty(src)) {
			return false;
		}
		Matcher m = mobile_pattern.matcher(src);
		return m.matches();
	}

}
