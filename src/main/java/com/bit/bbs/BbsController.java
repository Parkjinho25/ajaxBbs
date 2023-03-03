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

import com.bit.model.bbs.BbsDao;
import com.bit.model.bbs.BbsVo;

@WebServlet("/api/list/*")
public class BbsController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String password = context.getInitParameter("password");
		BbsDao dao = new BbsDao(url, user, password);
		
		String[] arr = req.getRequestURI().split("/");
		int bbsID = Integer.parseInt(arr[arr.length	-1]);
		System.out.println(req.getParameter("bbsID"));
		resp.setHeader("Access-Control-Allow-Origin", "*");	
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods","GET");
		resp.setContentType("application/json; charset=utf8");
		try {
			BbsVo bean = dao.getOne(bbsID);
			PrintWriter out = resp.getWriter();
			out.print("{\"bbs\":");
			out.print(bean);
			req.setAttribute("dto", bean);
			out.print("}");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		String url = context.getInitParameter("url");
		String user = context.getInitParameter("user");
		String password = context.getInitParameter("password");
		BbsDao dao = new BbsDao(url, user, password);
		
		String[] arr = req.getRequestURI().split("/");
		int bbsID = Integer.parseInt(arr[arr.length	-1]);
		String bbsTitle = req.getParameter("bbsTitle");
		String bbsContent = req.getParameter("bbsContent");
		String userID = req.getParameter("userID");
		
		resp.setHeader("Access-Control-Allow-Origin", "*");	
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods","POST");
		req.setCharacterEncoding("UTF-8");
		try {
			HttpSession session = req.getSession();
			userID = (String)session.getAttribute("userID");
			dao.editOne(bbsID, bbsTitle, bbsContent, userID);
			resp.sendRedirect("/bbsproject/detail.jsp?bbsID="+bbsID);
		} catch (SQLException e) {
			resp.sendError(resp.SC_BAD_REQUEST);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		String url=context.getInitParameter("url");
		String user=context.getInitParameter("user");
		String password=context.getInitParameter("password");
		BbsDao dao = new BbsDao(url, user, password);
		String[] arr = req.getRequestURI().split("/");
		int bbsID = Integer.parseInt(arr[arr.length	-1]);
		String userID = req.getParameter("userID");
		resp.setHeader("Access-Control-Allow-Origin","*");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods","DELETE");
		try {
			BbsVo dto =dao.getOne(bbsID);
			HttpSession session = req.getSession();
			userID = (String)session.getAttribute("userID");
			dao.deleteOne(bbsID);
			resp.setContentType("text/html; charset=UTF-8");
			resp.sendRedirect("/bbsproject/list.jsp");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
