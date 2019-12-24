<%@page import="model.UserBean"%>
<%@page import="model.CheckoutBean"%>
<%@page import="model.CheckoutBeanDAO"%>
<%@page import="model.CheckoutItemBean"%>
<%@page import="model.CheckoutItemBeanDAO"%>
<%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>


<%
	//Redirecting to login if there isn't any user instance in session
	if (session.getAttribute("currentUser") == null) {
		response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
		log("no user in session!");
		return;
	} else {
		if(!((UserBean)session.getAttribute("currentUser")).getType().equals("ristoratore")){
			response.sendRedirect(request.getContextPath() + "/Home.jsp");
			return;
		}
	}
%>


<%!double rand = Math.random() * 2;%>
<%
	Cookie[] cookies = request.getCookies();
	for (Cookie currentCookie : cookies) {
		if (currentCookie.getName().equals("session")) {
			log(currentCookie.getValue());
		}
	}
	log("session can last " + session.getMaxInactiveInterval());
%>

<%!
	private ArrayList<CheckoutBean> getCheckouts(HttpSession session){
		String owner = ((UserBean)session.getAttribute("currentUser")).getEmail();
		ArrayList<CheckoutBean> checkouts;
		CheckoutBeanDAO checkoutsRetriever = new CheckoutBeanDAO();
		checkouts = checkoutsRetriever.doRetrieveByRestaurateur(owner);		
		return checkouts;		
	}

	private String getCheckoutItems(int number){
		CheckoutItemBeanDAO itemRetriever = new CheckoutItemBeanDAO();		
		ArrayList<CheckoutItemBean> items = itemRetriever.doRetrieveByCheckout(number);
		String thead = "<thead><tr><th scope=\"col\">Prodotto</th><th scope=\"col\"></th></tr></thead>"; 
		for(CheckoutItemBean item : items){
			thead += "<tr><td>"+item.getItemName()+"</td><td> X  "+item.getQuantity()+"</td></tr>";
		}
		return thead;
	}

	private String printNotConvalidatedRows(HttpSession session){
		ArrayList<CheckoutBean> checkouts = getCheckouts(session);
		final String thead = "<thead class=\"mb-4\"><tr><th class=\"scope\">#</th><th class=\"scope\">Data</th><th class=\"scope\">Email utente</th><th class=\"scope\">Indirizzo di consegna</th><th class=\"scope\">Totale</th><th scope=\"col\"></th></tr></thead>";
		String row = "";
		for(CheckoutBean checkout : checkouts){			
			if(checkout.isWaiting()){
				row += thead;
				row += "<tr><td>"+checkout.getNumber()+"</td><td>"+checkout.getOrderDateAsString()+"</td><td>"+checkout.getClient()+"</td><td>"+checkout.getCompleteAddress()+"</td>"+"<td>"+checkout.getAmount()+"&euro;</td><td><input class='form-check-input' type='radio' name='checkout_number' value='"+checkout.getNumber()+"' onclick='setNumber(this)'/></td></tr>";			
				row += getCheckoutItems(checkout.getNumber());
			}
		}
		return row;
	}

private String printConvalidatedRows(HttpSession session){
	ArrayList<CheckoutBean> checkouts = getCheckouts(session);
	String row = "";
	for(CheckoutBean checkout : checkouts){
		if(checkout.isAccepted()){
			row += "<tr><td>"+checkout.getNumber()+"</td><td>"+checkout.getOrderDateAsString()+"</td><td>"+checkout.getClient()+"</td><td>"+checkout.getCompleteAddress()+"</td>"+"<td>"+checkout.getAmount()+"&euro;</td><tr>";			
			row += getCheckoutItems(checkout.getNumber());
		}
	}
	return row;
}

%>
<div class="container-fluid h-100">
	<div class="row align-items-stretch">
		<!--left menu-->
		<div
			class="col-md-3 col-sm-12 border border-left-0 border-top-0 border-bottom-0  show"
			id="col-collapsable" aria-expanded="true">
			<div class="card border-0" id="left-menu">
				<div class="card-body" id="card-form">
					<h5
						class="card-title d-flex justify-content-between align-items-center">Menu
						ristoratore</h5>
					<div class="list-group" id="list-tab" role="tablist">
						<a
							class="list-group-item list-group-item-action d-flex justify-content-between align-items-center active"
							id="list-ordini-list" data-toggle="list" href="#list-ordini"
							role="tab" aria-controls="ordini" onclick="hideMenu()">
							Ordini 
						</a> <a
							class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
							id="list-gestione-ristorante-list" data-toggle="list"
							href="#list-gestione-ristorante" role="tab"
							aria-controls="gestione-ristorante"
							onclick="hideMenu(); getData();"> Gestione Ristorante </a> <a
							class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
							id="list-profilo-ristoratore-list" data-toggle="list"
							href="#list-profilo-ristoratore" role="tab"
							aria-controls="profilo-ristoratore" onclick="hideMenu()">
							Profilo Ristoratore </a> <a
							class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
							onclick="hideMenu();invalidateSession();"> Logout </a>
					</div>
				</div>
			</div>
		</div>
		<div id="collapsed-on-sm" class="col-md-9 col-sm-12 d-none d-md-block">
			<div class="show-menu-label d-md-none">
				<a onclick="showMenu()" role="button"><i class="fas fa-bars"></i></a>
			</div>
			<div class="tab-content" id="nav-tabContent">
				<div class="tab-pane fade show active" id="list-ordini"
					role="tabpanel" aria-labelledby="list-ordini-list">
					<div class="card">
						<div class="card-body">
							<div class="container-fluid table-responsive" id="toConvalid">
							<h5 class="card-title">Ordini da convalidare</h5>							
							<table id="validation_table">
								<tbody>
									<%=printNotConvalidatedRows(session)%>
								</tbody>
							</table>
							<div class="form-row mt-4">
									<button class="btn btn-success mr-3" onclick="accept()">Accetta</button>
									<button class="btn btn-danger" onclick="reject()">Rifiuta</button>
							</div>
							</div>
							<div class="container-fluid"">
							<h5 class="card-title">Ordini convalidati</h5>							
							<table id="validation_table">
								<tbody>
									<%=printConvalidatedRows(session)%>
								</tbody>
							</table>
							</div>
						</div>
					</div>
				</div>
				<!-- TAB RISTORANTE START -->
				<div class="tab-pane fade mx-auto" id="list-gestione-ristorante"
					role="tabpanel" aria-labelledby="list-gestione-ristorante-list">
					<div class="card mx-auto" id="card-form">
						<div class="card-body">
							<h3 class="card-title">Gestione ristorante</h3>

							<form name="restaurantForm" action="#" onsubmit="return false;">
								<div class="form-row mt-4">
									<div class="col">
										<label for="nomeRistorante">Nome Ristorante</label> <input
											type="text" name="restaurantName" class="form-control"
											id="nomeRistorante">
										<div class="invalid-feedback">Deve contenere solo
											caratteri alfanumerici</div>
									</div>
									<div class="col">
										<label for="proprietarioRistorante">Proprietario</label> <input
											type="text" name="restaurantOwner" class="form-control"
											id="proprietarioRistorante"
											value="<%=((UserBean) session.getAttribute("currentUser")).getEmail()%>"
											disabled>
									</div>
								</div>
								<div class="form-row mt-4">
									<div class="col">
										<label for="categoriaRistorante">Categoria</label> <input
											type="text" name="restaurantCategory" class="form-control"
											id="categoriaRistorante">
										<div class="invalid-feedback">Deve contenere solo
											caratteri alfanumerici</div>
									</div>
									<div class="col">
										<label for="viaRistorante">Via</label> <input type="text"
											name="restaurantStreet" class="form-control"
											id="viaRistorante">
										<div class="invalid-feedback">Deve contenere solo
											caratteri alfanumerici</div>
									</div>
								</div>
								<div class="form-row mt-4">
									<div class="col">
										<label for="cittaRistorante">Città</label> <input type="text"
											name="restaurantCity" class="form-control"
											id="cittaRistorante">
										<div class="invalid-feedback">Deve contenere solo
											caratteri alfanumerici</div>
									</div>
									<div class="col">
										<label for="capRistorante">Cap</label> <input type="number"
											name="restaurantCap" class="form-control" id="capRistorante">
										<div class="invalid-feedback">Deve contenere 5 cifre</div>
									</div>
									<div class="col">
										<label for="provinciaRistorante">Provincia</label> <input
											type="text" name="restaurantProvince" class="form-control"
											id="provinciaRistorante">
										<div class="invalid-feedback">Deve contenere solo due
											lettere</div>
									</div>
								</div>
								<div class="form-row d-flex justify-content-end">
									<button id="sendFormBtn" class="btn btn-success"
										value="Invia modifiche" onclick="sendForm();">
										<i class="fas fa-check"></i>
									</button>
								</div>
							</form>
						</div>
					</div>
					<!--Caricamento immagine -->
					<div class="card mx-auto" id="card-form">
						<div class="card-body">
							<h3 class="card-title">Immagine profilo ristorante</h3>
							<div class="form-row mt-4">
								<form name="imageForm" action="#" onsubmit="return false;">
									<div class="form-row">
										<div class="input-group">
											<div class="custom-file">
												<input type="file" class="custom-file-input"
													name="file-uploaded" id="file-field"
													aria-describedby="inputGroupFileAddon04"> <label
													class="custom-file-label" for="inputGroupFile04">Scegli
													file</label>
											</div>
											<div class="input-group-append">
												<button class="btn btn-outline-secondary" onclick="uploadPressed()" type="button"
													id="inputGroupFileAddon04">Upload</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>


					<!-- Inserimento piatti -->
					<div class="card mx-auto" id="card-form">
						<div class="card-body">
							<h3 class="card-title">Modifica menu</h3>
							<div class="card-body">
								<form name="menuForm" action="#" onsubmit="return false;">
									<h4 class="card-title">Aggiungi prodotto</h4>
									<div class="row">
										<div class="col-sm-12">
											<label for="nomeProdotto">Nome prodotto</label> <input
												type="text" name="itemName" class="form-control w-50"
												id="itemName" />
											<div class="invalid-feedback">Non può essere vuoto</div>
										</div>
										<div class="col-sm-6">
											<label for="categoria">Categoria</label> <input type="text"
												name="itemCategory" class="form-control"
												list="category-list" />
											<datalist id="category-list">
											</datalist>
										</div>
										<div class="col-sm-6">
											<label for="prezzo">Prezzo</label> <input type="number"
												step="0.1" name="itemPrice" class="form-control w-50"
												id="itemPrice" />
											<div class="invalid-feedback">Non può essere vuoto</div>
										</div>
									</div>
									<div class="row mt-4">
										<div class="col-sm-12">
											<label for="nomeIngrediente">Ingrediente</label> <input
												type="text" name="ingredientName" class="form-control w-50"
												id="ingredientName" />
											<div class="invalid-feedback">Non può essere vuoto</div>
										</div>
										<div class="col-sm-12">
											<div class="form-check form-check-inline" id="allergene-div">
												<input type="checkbox" class="form-check-input"
													id="allergeneCheck" value=""> <label
													class="form-check-label" for="allergenCheck">Allergene</label>
											</div>
											<div class="form-check form-check-inline">
												<button id="addIngredient" class="btn btn-success ml-5"
													value="" onclick="createListEntry()">
													<i class="fa fa-plus"></i>
												</button>
											</div>
										</div>
									</div>
									<ul id="ingredients-list" class="list-group mt-4">
									</ul>
									<div class="form-row d-flex justify-content-end">
										<button id="sendFormBtn" class="btn btn-success" value=""
											onclick="sendItem();">
											<i class="fas fa-check"></i>
										</button>
									</div>
								</form>
							</div>
							<h4 class="card-title">Menu</h4>
							<div class="table-responsive">
								<table id="menu-table" class="table">
									<thead>
										<tr>
											<th id="item-col" scope="col">Prodotto</th>
											<th id="category-col" scope="col">Categoria</th>
											<th id="composition-col" scope="col">Composizione</th>
											<th id="price-col" scope="col">Prezzo</th>
										</tr>
									</thead>
									<tbody id="menu-body">

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

				<!-- TAB RISTORANTE END -->
				<!--  TAB PROFILO START-->
				<div class="tab-pane" id="list-profilo-ristoratore" role="tabpanel"
					aria-labelledby="list-profilo-ristoratore-list">
					<div class="card mx-auto" id="card-form">
						<div class="card-body">
							<h3 class="card-title">Modifica anagrafiche</h3>
							<div class="card-body">
								<form name="profileForm" action="#" onsubmit="return false;">
									<div class="form-row">
										<div class="col">
											<label for="nome">Nome</label> <input type="text" name="name"
												class="form-control" id="restaurateur-name" />
											<div class="invalid-feedback">Ammessi solo caratteri
												dell'alfabeto</div>
										</div>
										<div class="col">
											<label for="cognome">Cognome</label> <input type="text"
												name="surname" class="form-control"
												id="restaurateur-surname" />
											<div class="invalid-feedback">Ammessi solo caratteri
												dell'alfabeto</div>
										</div>
									</div>
									<div class="form-row">
										<div class="col">
											<label for="email">Email</label> <input type="email"
												name="email" class="form-control" id="restaurateur-email"
												required />
											<div class="invalid-feedback">Email malformata</div>
										</div>
										<div class="col">
											<label for="data nascita">Data di nascita</label> <input
												type="date" name="birthdate" class="form-control"
												id="restaurateur-birthdate" />
											<div class="invalid-feedback">Data non valida</div>
										</div>
									</div>
									<div class="form-row d-flex justify-content-end mt-4">
										<button class="btn btn-success" onclick="editDetails()">
											<i class="fas fa-user-edit"></i>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Password recovery -->
					<div class="card mx-auto" id="card-form">
						<div class="card-body">
							<h3 class="card-title">Modifica password</h3>
							<div class="card-body">
								<form name="passwordForm" action="#" onsubmit="return false;">
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

						<!--  TAB PROFILO END-->

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/script/sidebar.js?ver="
	<%= Math.random()*2%>></script>
<script
	src="${pageContext.request.contextPath}/script/validator.js?ver=<%=rand%>"></script>
<script
	src="${pageContext.request.contextPath}/script/restaurant-form-sender.js?ver=<%=rand%>"></script>
<script
	src="${pageContext.request.contextPath}/script/restaurant-form-filler.js?ver=<%=rand%>"></script>
<script
	src="${pageContext.request.contextPath}/script/menu.js?ver=<%=rand%>"></script>
	
<script src="${pageContext.request.contextPath}/script/restaurateur-editor.js?ver=<%=rand%>"></script>

<script src="${pageContext.request.contextPath}/script/image-uploader.js?ver=<%=rand%>"></script>
	

<script
	src="${pageContext.request.contextPath}/script/convalidation.js?ver=<%=rand%>"></script>

<jsp:include page="footer.jsp"></jsp:include>

<script>
	getMenu();
</script>