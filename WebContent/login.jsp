<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ResourceBundle, java.util.Locale"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/materialize.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
body {
	display: flex;
	min-height: 100vh;
	flex-direction: column;
}

main {
	flex: 1 0 auto;
}
</style>
<%
	session.setAttribute("currentPage", "login");
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
<title>Login to MealReview</title>
</head>
<body>
	<div class="section"></div>
	<!-- Padding to push the form down -->
	<div class="section"></div>
	<div class="section">
		<center>
			<h5><%=lang.getString("login_page.h2.loginM") %></h5>
			<div class="section"></div>
			<div class="container">
				<div class="z-depth-1 grey lighten-4 row"
					style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">
					<form class="col s12" action="loginServlet" method="post">
						<div class="row">
							<div class="input-field col s12">
								<input placeholder="<%=lang.getString("login_page.label.uname")%>" type="text" name="username"
									required="required" />
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12">
								<input placeholder="<%=lang.getString("login_page.label.password")%>" type="password" name="password"
									required="required" />
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12">
								<input class="waves-effect waves-light btn" type="submit"
									value=<%=lang.getString("login_page.button.login")%> />
							</div>
						</div>
					</form>
				</div>
			</div>
			<h6>
				<a href="registration.jsp"><%=lang.getString("reg_page.h2.regM") %></a>
			</h6>
			<h6><a href="#"><%=lang.getString("recoveryPage.title") %></a>
			</h6>
		</center>
	</div>

</body>

<script src="js/materialize.min.js"></script>
</html>