<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	session.setAttribute("language", "EN");

	String currentpage = (String) session.getAttribute("currentPage");

	currentpage += ".jsp";

	response.sendRedirect(currentpage);
%>
