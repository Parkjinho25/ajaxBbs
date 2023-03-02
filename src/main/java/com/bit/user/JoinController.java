package com.bit.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.model.user.UserDao;
import com.bit.model.user.UserVo;

@WebServlet("/user/join")
public class JoinController extends HttpServlet {
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		String url=context.getInitParameter("url");
		String user=context.getInitParameter("user");
		String password=context.getInitParameter("password");
		UserDao dao = new UserDao(url, user, password);
		
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("userPassword");
		String userName = req.getParameter("userName");
		String userEmail = req.getParameter("userEmail");
		
		resp.setHeader("Access-Control-Allow-Origin", "*");	
		resp.setStatus(resp.SC_CREATED);
		try {
			dao.join(userID,userPassword,userName,userEmail);
		} catch (SQLException e) {
			resp.sendError(resp.SC_BAD_REQUEST);
		}
//		resp.sendRedirect("/bbsproject/user/login.do");
	}
}
