<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="stylesheet" href="css/materialize.min.css">
<script src="js/materialize.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
			                    <td><input placeholder="Username" type="text" name="username" required="required" /></td>
			                </tr>
			                <tr>
			                    <td><input placeholder="Password" type="password" name="password" required="required" /></td>
			                </tr>
			                <tr>
			                    <td><input class="waves-effect waves-light btn col s12" type="submit" value="Login" /></td>
			                </tr>
			            </table>
			        </fieldset>
			    </form>
		    </div>
	    </div>
</body>
</html>