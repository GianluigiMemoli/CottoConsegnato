<%@ page import="model.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UserBean currentUsr = (UserBean) session.getAttribute("currentUser");
	double rand = Math.random() * 2;
%>

<jsp:include page="header.jsp"></jsp:include>
<%
	if (session.getAttribute("currentUser") == null) {
		response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
		log("no user in session!");
		return;
	} else {
		if(!((UserBean)session.getAttribute("currentUser")).getType().equals("cliente")){
			response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
			return;
		}
	}
%>
<!-- Home styling -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/home.css?ver=<%=rand%>">
 
<script
	src="${pageContext.request.contextPath}/script/restaurantFetcher.js?ver="
	<%=rand%>></script>

<script
	src="${pageContext.request.contextPath}/script/img-resizer.js?ver="
	<%=rand%>></script>

<div class="container-fluid">
	<div class="row d-flex justify-content-center mb-4 home" id="research-row">
		<div class="col-sm-8 border-bottom">
			<p class="p-2">Indirizzo di consegna</p>
			<div class="input-group">
				<input class="form-control" type="text"
					placeholder="Via, Provincia, CAP" name="input-address">
				<div class="input-group-append mb-4">
					<button class="btn btn-primary" type="button"
						onclick="searchPressed()">Cerca</button>
				</div>
			</div>
		</div>
	</div>
	<div class="row d-flex" id="flex-container">
		<div id="navigation-container" class="col-xs-12 col-md-3 mb-4">
			<ul class="list-group align-self-center click-cursor">
				<li class="list-group-item active"><a href="Home.jsp"><span class="click-cursor">Ricerca ristoranti</span></a></li>    
				<li class="list-group-item"><a href="ClientProfile.jsp">Profilo</a></li>
				<li class="list-group-item"><a onclick="invalidateSession()">Logout</a></li>
			</ul>
		</div>
	
		
		
		<div id="rest-container" class="col-xs-12 col-md-6 align-self-center home">
		</div>
		
		<div id="filters-container"
			class="col-xs-12 col-md-3 align-self-start mb-4 home">
			<p class="lead">
				<b>Filtro tipo cucina</b>
			</p>
		</div>
		
		
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
<script
	src="${pageContext.request.contextPath}/script/homeResponsiver.js?ver="
	<%=rand%>></script>
