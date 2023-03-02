package com.bit.model.bbs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



public class BbsDao {
	Logger log = Logger.getGlobal();
	private String url, user, password;
	
	public BbsDao(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() throws SQLException {
		String sql = "select now()";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				){
			if(rs.next()) {
				return rs.getString(1);
			}
		}
		 //데이터베이스 오류
		return "";
	}
	
	//게시글 번호 부여 메소드
	public int getNext() throws SQLException {
		//현재 게시글을 내림차순으로 조회하여 가장 마지막 글의 번호를 구한다
		String sql = "select bbsID from bbs order by bbsID desc";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				){
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫 번째 게시물인 경우
		}
	}
	
	public List<BbsVo> getList(int pageNumber) throws SQLException{
		String sql ="select * from bbs where bbsID < ? and bbsAvailable = 1 order by bbsID desc limit 10";
		List<BbsVo> list = new ArrayList<BbsVo>();
		try (
			Connection conn = DriverManager.getConnection(url, user, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			){
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				BbsVo bean = new BbsVo();
				bean.setBbsID(rs.getInt("bbsID"));
				bean.setBbsTitle(rs.getString("bbsTitle"));
				bean.setBbsDate(rs.getString("bbsDate"));
				bean.setUserID(rs.getString("userID"));
				log.info(bean.toString());
				list.add(bean);

			}
		}
		return list;
	}
	public List<BbsVo> selectAll() throws SQLException{
		String sql ="select * from bbs";
		List<BbsVo> list = new ArrayList<BbsVo>();
		try (
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				BbsVo bean = new BbsVo();
				bean.setBbsID(rs.getInt("bbsID"));
				bean.setBbsTitle(rs.getString("bbsTitle"));
				bean.setBbsDate(rs.getString("bbsDate"));
				bean.setUserID(rs.getString("userID"));
				log.info(bean.toString());
				list.add(bean);
				
			}
		}
		return list;
	}

	public void insertOne(String bbsTitle, String bbsContent, String userID) throws SQLException {
		String sql = "insert into bbs values(?,?,?,?,?,?)";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, bbsContent);
			pstmt.setString(4, getDate());
			pstmt.setString(5, userID);
			pstmt.setInt(6, 1);
			pstmt.executeUpdate();
		}
	}


	public BbsVo getOne(int bbsID) throws SQLException{
		String sql = "select * from bbs where bbsID = ?";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
					){
				pstmt.setInt(1, bbsID);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					BbsVo bean = new BbsVo();
					bean.setBbsID(rs.getInt("bbsID"));
					bean.setBbsTitle(rs.getString("bbsTitle"));
					bean.setBbsContent(rs.getString("bbsContent"));
					bean.setBbsDate(rs.getString("bbsDate"));
					bean.setUserID(rs.getString("userID"));
					return bean;
				}
			}
		return null;
	}

	public int editOne(int bbsID, String bbsTitle, String bbsContent, String userID) throws SQLException {
		String sql = "update bbs set bbsTitle=?, bbsContent=? where bbsID=?";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setString(1,bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		}
	}

	public int deleteOne(int bbsID) throws SQLException {
		String sql = "delete from bbs where bbsID=?";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		}
	}
	
	public void reply(String bbsrmsg, String userID) throws SQLException {
		String sql = "insert into reply (bbsID, userID, bbsrmsg) values(?,?,?)";
		try(
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);	
				){
			pstmt.setInt(1, getNext());
			pstmt.setString(2, userID);
			pstmt.setString(3, bbsrmsg);
			pstmt.executeUpdate();
		}
	}
	
//	public ArrayList<ReplyDto> getReply(int bbsID) throws SQLException{
//		String sql = "select *  from s_reply where bbsID=? order by date desc";
//		ArrayList<ReplyDto> rlist = new ArrayList<ReplyDto>();
//		try(
//				Connection conn = DriverManager.getConnection(url, user, password);
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				){
//			pstmt.setInt(1,bbsID);
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				ReplyDto r = new ReplyDto();
//				r.setBbsrid(rs.getInt("bbsrid"));
//				r.setUserID(rs.getString("userID"));
//				r.setBbsrmsg(rs.getString("bbsrmsg"));
//				r.setBbsDate(rs.getString("bbsdate"));
//				rlist.add(r);	
//			}
//		}
//		return rlist;
//	}
	
	//페이징 처리 메소드
	public boolean nextPage(int pageNumber) throws SQLException {
		String sql = "select * from bbs where bbsID < ? and bbsAvailable = 1";
		try (
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}
		return false;
	}


	public List<BbsVo> miniList() throws SQLException {
		String sql = "select * from bbs order by bbsID desc limit 5";
		List<BbsVo> list = null;
		list = new ArrayList<BbsVo>();
		try (
				Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){		
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					BbsVo bean = new BbsVo();
					bean.setBbsID(rs.getInt("bbsID"));
					bean.setBbsTitle(rs.getString("bbsTitle"));
					log.info(bean.toString());
					list.add(bean);
			}
		}
		return list;
	}

}
