<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ResourceBundle"%>
<html>
<head>
<link rel="stylesheet" href="css/materialize.min.css">
<script src="js/materialize.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	//this will be used in SetLangToEN and SetLangToFR.jsp.
	session.setAttribute("currentPage", "home");

	if (session.getAttribute("language") == null) {
		session.setAttribute("language", "EN");
	}

	//set the english as deafult
	ResourceBundle lang = ResourceBundle.getBundle("indextText");

	//if the session language is FR switch to french, otherwise remains english as set above
	if (session.getAttribute("language").toString().equals("FR")) {
		lang = ResourceBundle.getBundle("indextText_FR");
	}

	//if the user clicked change language, set to appropriate language
	if (request.getParameter("language") != null) {
		if (request.getParameter("language").equals("FR")) {

			lang = ResourceBundle.getBundle("indextText_FR");
			session.setAttribute("language", "FR");
		} else {
			lang = ResourceBundle.getBundle("indextText");
			session.setAttribute("language", "EN");
		}
	}
%>
<title>Meal Review</title>
</head>
<body>
	<div class="container row">
		<h1 class="col s12 center">Meal Review</h1>
		<div class="col s12">
			<form class="col s12" action="loginServlet" method="post">
				<fieldset>
					<table>
						<tr>
							<td><input placeholder="Username" type="text"
								name="username" required="required" /></td>
						</tr>
						<tr>
							<td><input placeholder="Password" type="password"
								name="password" required="required" /></td>
						</tr>
						<tr>
							<td><input class="waves-effect waves-light btn col s12"
								type="submit" value="Login" /></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>