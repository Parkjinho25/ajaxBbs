package com.bit.user;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.model.user.UserDao;

@WebServlet("/user/login")
public class LoginController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
		rd.forward(req, resp);
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
		
		UserDao dao = new UserDao(url, user, password);
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		
		try {
			int result = dao.login(userID, userPassword);
			if(result ==1) {
				HttpSession session=req.getSession();
				session.setAttribute("userID", userID);
				System.out.println("로그인 성공");
				resp.getWriter().write("{\"result\": \"success\"}");
			}
			else {
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().write("{\"result\": \"fail\"}");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
