<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.UserBean"%>
<%!
	double rand = Math.random()*2;
	UserBean currentUser;
	String username = "Non loggato";
%>

<%
	
	if((currentUser = (UserBean) session.getAttribute("currentUser"))!= null)
		username = currentUser.getName();
%>
<!DOCTYPE html >
<html>
<head>
<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/bootstrap-4.0.0-dist/css/bootstrap.min.css">
<!-- Awesome Font -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/fontawesome-free-5.9.0-web/css/all.css">
<!-- Navbar styling -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/navbar.css?ver=<%=rand%>">

<!--  RegistrationForm styling-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/registration-form.css?ver=<%=rand%>">
<!-- JQuery import -->
<script
	src="${pageContext.request.contextPath}/script/jquery-3.4.1.min.js"></script>
<!--Sidebar css-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/sidebar.css?ver=<%=rand%>">
<!--Restaurant Form css-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/restaurant-form.css?ver=<%=rand%>">


<!-- Global styling -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/global.css?ver=<%=rand%>">

<!-- Restaurant styling -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/restaurant.css?ver=<%=rand%>">

<!-- Restaurant styling -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/tableValidation.css?ver=<%=rand%>">

</head>
<body>
	<!-- Nav -->
	<nav class="navbar navbar-expand-lg border-bottom border-custom">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsable" aria-controls="navbarItemsList"
			aria-expanded="false">
			<span class="nav-toggler-icon"></span>
		</button>
		<a class="navbar-brand light-text ml-5" href="#"><span id="name">Cotto</span>
			& <span id="name">Consegnato</span></a>

		<div class="collapse navbar-collapse" id="collapsable">
			<ul class="navbar-nav ml-auto mr-5 mt-lg-0">
				<li class="nav-item dropdown"><a href="#"
					class="nav-link light-text" id="navDropdown" aria-haspopup="true"
					role="button" data-toggle="dropdown" aria-expanded="false"> <i
						id="favi-user" class="fas fa-user fa-lg mr-2" aria-hidden="true"></i>
						<%=username%>
				</a></li>
			</ul>
		</div>
	</nav>