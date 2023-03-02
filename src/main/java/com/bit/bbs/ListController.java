package com.bit.bbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bit.model.bbs.BbsDao;
import com.bit.model.bbs.BbsVo;

@WebServlet("/api/list")
public class ListController extends HttpServlet {
	Logger log = Logger.getLogger(this.getClass().getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin","*");
		resp.setHeader("Access-Control-Allow-Methods","GET");
		resp.setContentType("application/json; charset=utf8");	
		ServletContext context = req.getServletContext();
		String url=context.getInitParameter("url");
		String user=context.getInitParameter("user");
		String password=context.getInitParameter("password");
		int pageNumber = 1;
		
		try {
			BbsDao dao = new BbsDao(url, user, password);
			PrintWriter out = resp.getWriter();
			out.print("{\"bbs\":");
			out.print(dao.getList(pageNumber));
			out.print("}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin","*");
		resp.setHeader("Access-Control-Allow-Methods","POST");
		resp.setContentType("application/json; charset=utf8");
		ServletContext context = req.getServletContext();
		String url=context.getInitParameter("url");
		String user=context.getInitParameter("user");
		String password=context.getInitParameter("password");
		BbsVo bean=new BbsVo();
		BbsDao dao = new BbsDao(url, user, password);
		
		String bbsTitle = req.getParameter("bbsTitle");
		String bbsContent = req.getParameter("bbsContent");
		String userID = req.getParameter("userID");
		
		resp.setStatus(HttpServletResponse.SC_CREATED);
		try {
			HttpSession session = req.getSession();
			userID = (String)session.getAttribute("userID");
			dao.insertOne(bbsTitle,userID, bbsContent);
			log.info(bbsTitle+","+bbsContent+","+userID);
			resp.getWriter().print("{}");
		} catch (SQLException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().print("{}");
		}
		
	}
}
