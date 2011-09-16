<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'   'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<title>KITCampusGuide Login</title>
<style type="text/css">
body {
	font-family: sans-serif;
	font-size: 12px;
}

h1 {
	font-size: 18px;
	font-weight: normal;
}

.formElement {
	clear: both;
}

.formElement label {
	width: 140px;
	float: left;
	display: block;
}
</style>
</head>
<body onload='document.loginForm.j_username.focus();'>
	<h1>Bitte melden Sie sich mit Ihrem Benutzernamen und Passwort an.</h1>
	<form name='loginForm' action='/poimanager/j_spring_security_check' method='post'>
		<div class='formElement username'>
			<label for='j_username'>Benutzername</label> <input type='text' name='j_username' value='student'>
		</div>
		<div class='formElement password'>
			<label for='j_password'>Passwort</label> <input type='password' name='j_password' value='student'/>
		</div>
		<div class='formElement rememberMe'>
			<label for='_spring_security_remember_me'>Auf diesem Computer
				angemeldet bleiben.</label> <input type='checkbox' name='_spring_security_remember_me'/>
		</div>
		<div class='formElement submit'>
			<input name="submit" type="submit" value="Login"/>
		</div>
	</form>

</body>
</html>
