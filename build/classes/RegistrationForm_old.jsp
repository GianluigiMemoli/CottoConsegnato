<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Registrazione</title>
	<link rel="stylesheet" type="text/css" href="styles/main.css">	
</head>
    
<body>			
	<h1>Registrazione</h1>
	<form id="registration_form" name="registrationForm" action="Registration" onsubmit="return validateForm();" method="post">
		<p>Nome:</p>
		<input type="text" name="name" placeholder="yourName" required/>
		<p>Cognome:</p>
		<input type="text" name="surname" placeholder="yourSurname" required/>
		<p>Email:</p>
		<input type="email" name="email" placeholder="yourEmail" required/>
		<p>Password:</p>
		<input type="password" name="password" placeholder="yourPassword" required/>
		<p>Data Nascita:</p>
		<input type="date" name="birthDate"required/> <br>
		<input type="radio" name="type" value="ristoratore"/> Ristoratore <br>
		<input type="radio" name="type" value="cliente" checked/> Cliente <br>
		<input type="submit" value="Registrati"/>
	</form>
	<script src="scripts/regValidation.js"></script>
	
</body>
</html>