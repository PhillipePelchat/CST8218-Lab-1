<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="java.util.ResourceBundle, java.util.Locale, java.util.ArrayList, com.amzi.dao.*, com.amzi.models.Review, com.amzi.models.Restaurant, java.sql.ResultSet"%>
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
	int id = Integer.parseInt(request.getParameter("id"));
	Restaurant r = RestaurantDao.getRestaurant(id);
	if (r == null) {
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}
	ArrayList<Review> list = ReviewDao.getReviewResultSetByRestaurant(id);
%>
<title>MealReview ::: <%=r.getName()%></title>
</head>
<body>
	<div>
		<!-- Restaurant contact info goes here -->
		<div class="row">
			<div class="col s5 m5">
				<div class="card col s12">
					<div class="card-content">
						<span class="card-title"><%=r.getName()%></span>
						<h5><%=lang.getString("restaurant.address")%></h5>
						<p><%=r.getAddress()%></p>
						<h5><%=lang.getString("restaurant.phone")%></h5>
						<p><%=r.getPhoneNum()%></p>
						<h5><%=lang.getString("restaurant.website")%></h5>
						<p>
							<a href="http://<%=r.getWebsite()%>" target="_blank"><%=r.getWebsite()%></a>
						</p>
						<h5><%=lang.getString("restaurant.rating")%></h5>
						<p><%=r.getUpVote()%>
							<%=lang.getString("restaurant.upvote")%>
						</p>
						<p><%=r.getDownVote()%>
							<%=lang.getString("restaurant.downvote")%>
						</p>
					</div>
					<%
						if (session.getAttribute("userId") != null) {
					%>
					<div class="card-action center">
						<a href="./VotingServlet?restaurantId=<%=r.getId()%>&type=1"><%=lang.getString("restaurant.like")%></a>
						<a href="./VotingServlet?restaurantId=<%=r.getId()%>&type=-1"><%=lang.getString("restaurant.dislike")%></a>
					</div>
					<%
						}
					%>
				</div>
				<%
					if (session.getAttribute("userId") != null) {
				%>
				<form class="col s12" action="textReviewServlet" method="post">
					<div class="row">
						<div class="input-field col s12">
							<input type="hidden" name="restaurantId" value="<%=r.getId()%>" />
							<textarea
								placeholder="<%=lang.getString("restaurant.reviewfield")%>"
								name="body" id="textarea" class="materialize-textarea"
								required="required"></textarea>
							<button class="btn orange" type="submit"><%=lang.getString("restaurant.reviewsubmit")%></button>
						</div>
					</div>
				</form>
				<%
					}
				%>
			</div>

			<!-- Review column goes here -->
			<div class="col s7 m7">
				<%
					if (list == null || list.isEmpty()) {
				%>
				<h2><%=lang.getString("restaurant.NoReview")%></h2>
				<%
					} else {
						for (Review review : r.getReviews()) {
				%>
				<div class="row">
					<div class="card col s12">
						<div class="card-content">
							<span class="card-title"> <%=lang.getString("restaurant.reviewby") + " " + review.getAuthorName()%>
							</span>
							</h5>
							<p><%=review.getBody()%></p>
						</div>
						<%
							if ((session.getAttribute("userId") != null && (int) session.getAttribute("userLevel") > 0)) {
						%>
						<div class="card-action center">
							<a
								href="./DeleteReviewServlet?reviewId=<%=review.getReviewId()%>"><%=lang.getString("restaurant.delete")%></a>
						</div>
						<%
							}
						%>
					</div>
				</div>
				<%
					}
					}
				%>

			</div>
		</div>
	</div>
</body>
</html>