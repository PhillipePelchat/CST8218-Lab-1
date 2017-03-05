package com.amzi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amzi.dao.LoginDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String pass = request.getParameter("password");

		HttpSession session = request.getSession(false);

		if (LoginDao.signUp(username, email, pass)) {
			if (session != null)
				session.setAttribute("name", username);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		} else {
			out.print("<br/><br/><br/><div class=\"alert alert-danger\"><strong>Sorry database connection error</strong></div>");
			RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
			rd.include(request, response);
		}

		out.close();
	}
	
//	public static String encyrptedPassword(String plainPassword){
//		String encryptedPW = "";
//		MessageDigest md = null;
//		try {
//			md = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException e) {}
//
//		synchronized (md) {
//
//			md.reset();
//			byte[] hash = null;
//			try {
//				hash = md.digest(plainPassword.getBytes("CP1252"));
//			} catch (UnsupportedEncodingException e) {}
//
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < hash.length; ++i) {
//				sb.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
//			}
//			encryptedPW = sb.toString();
//		}
//		
//		return encryptedPW;
//	}
}
