<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ResourceBundle, java.util.Locale"%>
<!DOCTYPE HTML>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	//this will be used in SetLangToEN and SetLangToFR.jsp.
	session.setAttribute("currentPage", "crew");

	if (session.getAttribute("language") == null) {
		session.setAttribute("language", "EN");
	}

	String clientLang = request.getLocale().getLanguage();

	//set the english as deafult
	ResourceBundle lang = ResourceBundle.getBundle("indexText");

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
<title><%=lang.getString("generic.aboutus.crew")%></title>
</head>
<body>
	<%@ include file="shared/navbar.jsp"%>
	<!-- Crew Info -->
	<div class="container" id="content">

		<div class="row">
			<h2 class="col s12 center"><%=lang.getString("generic.aboutus.crew")%></h2>
			<div class="container col s4">
				<img src="images/image.png" height=150px
					class="img-responsive center-block img-rounded circle"
					alt="Haider picture">
				<h3 class="center">Haider Ali</h3>
				<p>
					<!-- Nothing here yet -->
				</p>
			</div>
			<div class="container col s4">
				<img src="images/phillipe.jpg" height=150px
					class="img-responsive center-block img-rounded circle"
					alt="Phillipe picture">
				<h3 class="center">Phillipe Pelchat</h3>
				<p>
					<!-- Nothing here yet -->
				</p>
			</div>
			<div class="container col s4">
				<img src="images/victor.png" height=150px
					class="img-responsive center-block img-rounded circle "
					alt="Victor picture">
				<h3 class="center">Victor Fernandes</h3>
				<p>
					<!-- Nothing here yet -->
				</p>
			</div>
		</div>
	</div>
	<div class="section">
	<h3 class="center"><%=lang.getString("generic.aboutus")%></h3>
		<p class="flow-text"><%=lang.getString("welcome_page.p.firstParagraph")%></p>
<%-- 		<p class="flow-text"><%=lang.getString("welcome_page.p.secondParagraph")%></p>
 --%>		<p class="flow-text"><%=lang.getString("welcome_page.p.thirdParagraph")%></p>
	</div>

</body>
</html>
