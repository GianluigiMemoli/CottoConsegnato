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
<!-- Restaurant styling -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/client-profile.css?ver=<%=rand%>">

<div class="container-fluid">

	<div class="row d-flex" id="flex-container">
		<div id="navigation-container" class="col-xs-12 col-md-3 mb-4">
			<ul class="list-group list-group-horizontal align-self-center click-cursor">
				<li class="list-group-item"><a href="Home.jsp"><span
						class="click-cursor">Ricerca ristoranti</span></a></li>
				<li class="list-group-item active"><a href="ClientProfile.jsp">Profilo</a></li>
				<li class="list-group-item"><a onclick="invalidateSession()">Logout</a></li>
			</ul>
		</div>



		<div id="client-settings-container"
			class="col-xs-12 col-md-6 align-self-center">
			<div class="card mx-auto">
				<div class="card-header">Gestione Profilo</div>
				<div class="card-body">
					<h3 class="card-title">Modifica anagrafiche</h3>
					<form name="profileForm" action="#" onsubmit="return false;">
						<div class="form-row">
							<div class="col">
								<label for="nome">Nome</label> <input type="text" name="name"
									class="form-control" id="client-name" />
								<div class="invalid-feedback">Ammessi solo caratteri
									dell'alfabeto</div>
							</div>
							<div class="col">
								<label for="cognome">Cognome</label> <input type="text"
									name="surname" class="form-control" id="client-surname" />
								<div class="invalid-feedback">Ammessi solo caratteri
									dell'alfabeto</div>
							</div>
						</div>
						<div class="form-row">
							<div class="col">
								<label for="email">Email</label> <input type="email"
									name="email" class="form-control" id="client-email" required />
								<div class="invalid-feedback">Email malformata</div>
							</div>
							<div class="col">
								<label for="data nascita">Data di nascita</label> <input
									type="date" name="birthdate" class="form-control"
									id="client-birthdate" />
								<div class="invalid-feedback">Data non valida</div>
							</div>
						</div>
						<div class="form-row d-flex justify-content-end mt-4">
							<button class="btn btn-success" onclick="editDetails()">
								<i class="fas fa-user-edit"></i>
							</button>
						</div>
					</form>
					<h3 class="card-title">Modifica password</h3>
					<form name="passwordForm" action="#" onsubmit="return false;"
						class="ml-2 mr-2 mt-2 mb-2">
						<div class="form-row">
							<div class="col">
								<label for="old password">Vecchia password</label> <input
									type="password" name="oldPassword" class="form-control"
									id="old-password" />
								<div class="invalid-feedback">Password non valida</div>
							</div>
							<div class="col">
								<label for="new password">Nuova password</label> <input
									type="password" name="newPassword" class="form-control"
									id="new-password" />
								<div class="invalid-feedback">Password non valida</div>
							</div>
						</div>

						<div class="form-row d-flex justify-content-end mt-4">
							<button class="btn btn-success" onclick="changePassword()">
								<i class="fas fa-user-edit"></i>
							</button>
						</div>
					</form>
				</div>
			</div>
			<!-- Ordini -->
			<div class="card mx-auto mt-4"> 
				<div class="card-header">Storico ordini</div>
				<div class="card-body">
					<div class="container-fluid">
						<div id="checkout_list"class="row d-flex">
						<!-- 	<div class="media border-bottom mt-4 mb-4">
								<img src="img/default" class="mr-3" alt="...">
								<div class="media-body">
									<h5 class="mt-0">Ordine #2</h5>
									<p>In attesa di conferma</p>
									<p class="lead">Ordine comprensivo di:</p>
									<table>
									<thead>
										<tr>
											<th scope="col">Prodotto</th>
											<th scope="col"></th>											
										</tr>											
									</thead>
									<tbody>
										<tr>
											<td>Fanta</td>
											<td>x2</td>
										</tr>
										<tr>
											<td>Acqua</td>
											<td>x2</td>
										</tr>
										<tr>
											<th scope="row">Totale</th>
											<td> 12 &euro;</td>
										</tr>									
									</tbody>
									</table>
									<p class="lead">Presso: (Nome Ristorante, Via)</p>
									<p class="lead">Indirizzo consegna: (Nome Ristorante, Via)</p> 									
								</div>
								
							</div> -->
							
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>



<jsp:include page="footer.jsp"></jsp:include>
<script
	src="${pageContext.request.contextPath}/script/homeResponsiver.js?ver="
	<%=rand%>></script>
<script
	src="${pageContext.request.contextPath}/script/validator.js?ver="
	<%=rand%>></script>

<script
	src="${pageContext.request.contextPath}/script/client-editor.js?ver="
	<%=rand%>></script>

<script
	src="${pageContext.request.contextPath}/script/img-resizer.js?ver="
	<%=rand%>></script>

<script
	src="${pageContext.request.contextPath}/script/checkoutHistory.js?ver="
	<%=rand%>></script>
<script>getCheckouts()</script>
