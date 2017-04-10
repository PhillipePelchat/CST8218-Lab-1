package com.amzi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzi.dao.ReviewDao;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;

/**
 * Servlet implementation class VotingServlet
 */
public class VotingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VotingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		int voterId = (int) request.getSession().getAttribute("userId");
		int value = Integer.parseInt(request.getParameter("type"));
		
		ReviewDao.doVote(value, voterId, restaurantId);
		RequestDispatcher rd = request.getRequestDispatcher("Restaurant.jsp?id="+restaurantId);
		rd.include(request, response);
	}

}
