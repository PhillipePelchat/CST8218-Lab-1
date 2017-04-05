<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="java.util.ResourceBundle, java.util.Locale, java.util.ArrayList, com.amzi.dao.ReviewDao, com.amzi.models.Review, java.sql.ResultSet"%>
<html>
<head>
<link href="css/materialize.min.css" rel="stylesheet">
<script src="js/materialize.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<title>Meal Review</title>

</head>
<body>
	<div class="container row">
		<h1 class="center">Meal Review</h1>
		<div class="section content col s12">
			<%-- <p>
				Language:
				<%=session.getAttribute("language")%></p> --%>
			<%
				if (session.getAttribute("name") != null) {
			%>
			<%=lang.getString("generic.welcome") + ", " + session.getAttribute("name") + "!"%>
			<%
				}
			%>
		</div>

		<!-- User's Posts -->
		<div class="col s12 content">
			<%
				if (session.getAttribute("userId") != null) {
					int id = (int) session.getAttribute("userId");

					ArrayList<Review> r = null;
					r = ReviewDao.getReviewResultSetByUser(id);
					if (r != null) {
			%>
			<h5>Here are your latest public reviews:</h5>
			<%
				for (Review review : r) {
			%>
			<div class="card col s12">
				<h5>
					<a href="Restaurant.jsp?id=<%=review.getRestaurantId()%>">
						Review by <%=review.getAuthorName()%>
					</a>
				</h5>
				<p><%=review.getBody()%></p>
			</div>
			<%
				}
					} else {
			%>
			<!--  TODO: TRANSLATE THIS -->
			<p class="col s12">You have no Reviews in your account :(</p>

			<%
				}
				}
			%>
		</div>
		<div class="divider"></div>
		<div class="card col s12">
			<h1>Latest Reviews</h1>
			<%
				ArrayList<Review> rs2;
				rs2 = ReviewDao.getReviewResultSet();
				if (rs2 != null) {
					for (Review r : rs2) {
			%>
			<h5>
				Restaurant ID:
				<%=r.getRestaurantId()%></h5>
			<h6>
				By:
				<%=r.getAuthorName()%></h3>
				<p><%=r.getBody()%></p>
				<div class="divider"></div>
				<%
					}
					} else {
				%>
				<!--  TODO: TRANSLATE THIS -->
				<p class="" col s12>No Reviews here :(</p>

				<%
					}
				%>
			
		</div>
	</div>
</body>
</html>