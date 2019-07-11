package com.llspace.seckill.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * GenerateUserTokenUtil 工具连接数据库使用
 *
 * @author llspace
 * @since 2019-07-11
 */
public class DBUtil {
	
	private static Properties props;
	
	static {
		try {
			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.yml");
			props = new Properties();
			props.load(in);
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() throws Exception{
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		String driver = "com.mysql.cj.jdbc.Driver";
		Class.forName(driver);
		return DriverManager.getConnection(url,username, password);
	}
}
