<link href="css/materialize.min.css" rel="stylesheet">
<div class="navbar-fixed">
	<nav>
		<div class="nav-wrapper">
			<a href="index.jsp" class="brand-logo right">MealReview</a>
			<ul id="nav-mobile" class="left hide-on-med-and-down">
				<li><a href="crew.jsp"><%=lang.getString("generic.aboutus.crew")%></a></li>
				<%
					if (session.getAttribute("userId") == null) {
				%>
				<li><a href="login.jsp"><%=lang.getString("generic.login")%></a>
				<li>|<li>
				<li><a href="registration.jsp"><%=lang.getString("generic.register") %></a></li>
					<%
						} else {
					%>
				<li><a href="logout"><%=lang.getString("generic.logout")%></a></li>
				<%
					}
				%>
				<li><a href="setLangToEN.jsp">EN</a>
				<li>|</li>
				<li><a href="setLangToFR.jsp">FR</a>
				<%
					if (session.getAttribute("name") != null) {
				%>
				<li>|</li>
				<li><a href="#"><%=session.getAttribute("name")%></a> 
				<%
				 	}
 				%></li>
			</ul>
		</div>
	</nav>
</div>