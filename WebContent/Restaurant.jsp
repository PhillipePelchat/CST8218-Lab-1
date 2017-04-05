<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="java.util.ResourceBundle, java.util.Locale, java.util.ArrayList, com.amzi.dao.ReviewDao, com.amzi.models.Review, com.amzi.models.Restaurant, java.sql.ResultSet"%>
<html>
<head>
<link href="css/materialize.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/materialize.min.js"></script>
<html>
<head>
<%
	//this will be used in SetLangToEN and SetLangToFR.jsp.
	session.setAttribute("currentPage", "index");

	if (session.getAttribute("language") == null) {
		session.setAttribute("language", "EN");
	}

	String clientLang = request.getLocale().getLanguage();

	//set the english as deafult
	ResourceBundle lang = ResourceBundle.getBundle("indexText");
	session.setAttribute("languageResource", lang);

	//if the session language is FR switch to french, otherwise remains english as set above
	if (session.getAttribute("language").toString().equals("FR")) {
		lang = ResourceBundle.getBundle("indexText_FR");
	}

	//if the user clicked change language, set to appropriate language
	if (request.getParameter("language") != null) {
		if (request.getParameter("language").equals("FR")) {

			lang = ResourceBundle.getBundle("indexText_FR");
			session.setAttribute("language", "FR");
		} else {
			lang = ResourceBundle.getBundle("indexText_EN");
			session.setAttribute("language", "EN");
		}
	}
%>
<%@include file="shared/navbar.jsp"%>
<%
	// Get state model
	Restaurant r = (Restaurant) session.getAttribute("restaurant");
%>
<title>MealReview ::: <%=r.getName()%></title>
</head>
<body>
	<!-- Review column goes here -->
	<div class="col s7 content">
		<div class="row">
			<%
				if (r.getReviews().isEmpty()) {
			%>
			<h2><%=lang.getString("restaurant.NoReview")%></h2>
			<%
				} else {
					for (Review review : r.getReviews()) {
			%>
						<div class="card col s12">
							<h5>
								Review by
								<%=review.getAuthorName()%>
							</h5>
							<p><%=review.getBody()%></p>
						</div>
			<%
					}
				}
			%>

		</div>
	</div>
	<!-- Restaurant contact info goes here -->
	<div class="col s5 content">
		<h2>Address</h2>
		<h2>Phone number</h2>
		<h2>Website</h2>
		<h2>Rating</h2>
	</div>
</body>
</html>