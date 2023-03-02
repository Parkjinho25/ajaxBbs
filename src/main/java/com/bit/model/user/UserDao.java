package com.bit.model.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bit.model.bbs.BbsVo;

public class UserDao { 
	
	private String url, user, password;
	
	public UserDao(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//로그인 영역
	public int login(String userID, String userPassword) throws SQLException {
		String sql = "select userPassword from user where userID = ? AND userPassword = ?";
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
				pstmt.setString(1, userID);
				pstmt.setString(2, userPassword);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
						System.out.println("성공");
						return 1; //로그인 성공
					}else
						return 0; // 비번틀림
				}
	}
	
	public void join(String userID, String userPassword, String userName, String userEmail) throws SQLException{
		  String sql = "insert into user values(?, ?, ?, ?)";
		  try(
				  Connection conn = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt = conn.prepareStatement(sql);
				  ){
			    pstmt.setString(1, userID);
			    pstmt.setString(2, userPassword);
			    pstmt.setString(3, userName);
			    pstmt.setString(4, userEmail);
			    pstmt.executeUpdate();
		  }
	}


	public UserVo userInfo(String userID) throws SQLException{
		String sql = "select * from user where userID = ?";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);	
				){
			pstmt.setString(1, userID);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				UserVo bean = new UserVo();
				bean.setUserID(rs.getString("userID"));
				bean.setUserName(rs.getString("userName"));
				bean.setUserEmail(rs.getString("userEmail"));
				return bean;
			}
		}
		return null;
	}
	
	
}