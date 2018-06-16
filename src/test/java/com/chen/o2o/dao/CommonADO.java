package com.chen.o2o.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonADO {
	private String DBDriver = null;
	private String url =null;
	private String user =null;
	private String password = null;
	private Connection conn=null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private static final CommonADO commonADO = new CommonADO();
	
	private CommonADO(){
		
		DBDriver = "com.mysql.jdbc.Driver";
		
		
		url = "jdbc:mysql://localhost:3306/o2o?useUnicode=true&characterEncoding=UTF-8"; 
		
		user ="root";
		//password="Chenyaowu15@gmail.com";
		password="";
		try {
			Class.forName(DBDriver);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static CommonADO getCommonADO(){
		return commonADO;
	}
	public ResultSet executeSelect(String sql){
		if(sql.toLowerCase().indexOf("select")!=-1){
			try {
				rs=stmt.executeQuery(sql);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	public int executeUpdate(String sql){
		int result=0;
		sql=sql.toLowerCase();
		if(sql.indexOf("insert")!=-1|sql.indexOf("delete")!=-1|sql.indexOf("update")!=-1){
			try {
				result=stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	public Connection getConn() {
		return conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void closeDB(){
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String args[]){
		CommonADO con = CommonADO.getCommonADO();
	}
}
