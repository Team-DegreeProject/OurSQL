package com.ucd.oursql.sql.driver;

import com.ucd.oursql.sql.login.account;
import com.ucd.oursql.sql.login.loginFunc;

import java.sql.*;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class OurSqlDriver implements Driver{
	static {
	     try {
			DriverManager.registerDriver(new OurSqlDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	@Override
	public boolean acceptsURL(String arg0) throws SQLException {
		System.out.println("check url");
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(arg0, ":");
		if(st.hasMoreTokens()) {
			String jdbc=st.nextToken();
			if(jdbc.equals("jdbc")) {
				if(st.hasMoreTokens()) {
					jdbc=st.nextToken();
					if(jdbc.equals("OurSql")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Connection connect(String arg0, Properties arg1) throws SQLException {
		System.out.println("connect");
		// TODO Auto-generated method stub
		if(!acceptsURL(arg0)) {
			return null;
		}
		account a=new account((String)arg1.get("user"),(String)arg1.get("password"));
		if(loginFunc.login(a)){
			Connection connection = null;
			connection = new OurSqlConnection();
			return connection;
		}else{
			System.out.println("do not exit user");
			return null;
		}
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		try {
			throw new SQLFeatureNotSupportedException();
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		try {
			throw new SQLFeatureNotSupportedException();
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		// TODO Auto-generated method stub
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		try {
			throw new SQLFeatureNotSupportedException();
		} catch (SQLFeatureNotSupportedException e) {
			e.printStackTrace();
		}
		return false;
	}

}
