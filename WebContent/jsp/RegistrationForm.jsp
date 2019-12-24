<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="alertShower.jsp"></jsp:include>


	<div class="row">
		<div
			class="col-lg-6 mx-auto justify-content-center align-items-center">
			<div class="card">
				<div class="card-body">
					<h3 class="card-title text-center">Registrazione</h3>
					<div id="alertPlaceHolder"></div>
					<form method="post" name="registrationForm"
						onsubmit="return validateForm();" action="${pageContext.request.contextPath}/Registration">
						<div class="form-group">
							<div class="form-check form-check-inline">
								<label class="form-check-label"> <input type="radio"
									class="form-check-input" name="type"
									id="accountTypeField" value="cliente" onclick="$('#pivaGroup').hide('200', 'linear')" checked> Cliente
								</label>
								</div>
								<div class="form-check form-check-inline">
								<label class="form-check-label"> <input type="radio"
									class="form-check-input" name="type"
									id="accountTypeField" value="ristoratore" onclick="$('#pivaGroup').show('200', 'swing')"> Ristoratore
								</label>
							</div>
							<br>
							<label for="name">Nome</label> <input type="text"
								class="form-control" name="name" id="nameField"
								aria-describedby="helpId" >
							<div class="invalid-feedback">Nome deve essere lungo almeno
								3 caratteri, non deve contenere caratteri speciali e numeri.</div>
						</div>
						<div class="form-group">
							<label for="surname">Cognome</label> <input type="text"
								class="form-control" name="surname" id="surnameField"
								aria-describedby="helpId">
							<div class="invalid-feedback">Cognome deve essere lungo
								almeno 3 caratteri, non deve contenere caratteri speciali e
								numeri.</div>
						</div>
						<div class="form-group">
							<label for="email">Email</label> <input type="email" name="email"
								id="emailField" class="form-control" placeholder="">
							<div class="invalid-feedback">Email malformata. Deve essere
								lunga meno di 320 caratteri!</div>
						</div>
						
						<div class="form-group">
							<label for="password">Password</label> <input type="password"
								class="form-control" name="password" id="passwordField"
								placeholder="">
							<div class="invalid-feedback">Password lunga almeno 8
								caratteri, al massimo 20.Deve contenere almeno un carattere
								speciale, un numero e una maiuscola.</div>
						</div>
						<div class="form-group" id="pivaGroup">
							<label for="partita IVA">Partita IVA</label> <input type="text" name="piva"
								id="pivaField" class="form-control" placeholder="" maxlength="11">
							<div class="invalid-feedback">Partita IVA malformata. Deve essere
								lunga 11 cifre!</div>
						</div>
						
						<div class="form-group">
							<label for="birthDate">Data di nascita</label> <input type="date"
								class="form-control" name="birthDate" id="birthDateField"
								aria-describedby="helpId" placeholder="">
						</div>
						<input type="submit" class="btn btn-primary" value="Registrati">
					</form>

				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/script/regValidation.js?noCache=<%=Math.random()*2%>"></script>
	<jsp:include page="footer.jsp">
		<jsp:param name="username" value="as"/>
		<jsp:param name="userType" value="noAuth"/>
	</jsp:include>