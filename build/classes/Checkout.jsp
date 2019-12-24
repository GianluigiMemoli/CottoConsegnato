<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>


<%!
	RestaurantBean currentRestaurant;
	CartBean currentCart;
	double rand = 2 * Math.random();
	
	private Hashtable<ItemBean, Integer> mapProductWithQuantity() {
		//Mapping every item only a time if it's repeated, with his relative quantity
		ArrayList<ItemBean> products = currentCart.getProducts();
		Hashtable<ItemBean, Integer> productsMapped = new Hashtable<ItemBean, Integer>();
		for (ItemBean product : products) {
			if (productsMapped.containsKey(product)) {
				productsMapped.put(product, productsMapped.get(product) + 1);
				log(product.toString() + "is in table");
			} else {
				productsMapped.put(product, 1);
				log(product.toString() + "not in table");
			}
		}		
		return productsMapped;
	}

	public String makeTableRows() {
		//Making a string with a row foreach itembean contained in productsQuantity
		Hashtable<ItemBean, Integer> productsQuantity = mapProductWithQuantity();
		
		String rows = "";
		Set<ItemBean> productsInTable = productsQuantity.keySet();
		for(ItemBean product : productsInTable){
			rows += "<tr>";
			rows += "<td>" + product.getName() + "</td>";
			rows += "<td>" + product.getPrice() + "&euro;</td>";
			rows += "<td>" + productsQuantity.get(product) + "</td>";
			rows += "</tr>";
		
		}
		return rows;
	}
	
	public String getTotal(){
		return currentCart.getTotal() + "";
	}
%>

<%
	if ((currentRestaurant = (RestaurantBean) session.getAttribute("currentRestaurant")) == null) {
		response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
		return;
	}
	if ((currentCart = (CartBean) session.getAttribute("currentCart")) == null) {
		response.sendRedirect(request.getContextPath() + "/Home.jsp");
		return;
	}
	if (currentCart.getProducts().size() == 0) {
		response.sendRedirect(request.getContextPath() + "/Restaurant.jsp");
		return;
	}
%>
<jsp:include page="header.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/checkout.css?ver=<%=rand%>" />

<div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class="col-sm-12 col-md-6">
			<div class="card">
				<div class="card-header">Il tuo ordine</div>
				<p class="card-title lead mt-3 ml-4">Riepilogo</p>
				<div class="card-body" id="product-container">
					<table class="table table-sm table-borderless"> 
						<thead>
							<tr>
								<th scope="col">Prodotto</th>
								<th scope="col">Prezzo</th>
								<th scope="col">Quantit&agrave;</th>
							</tr>
						</thead>
						<tbody>
							<%=makeTableRows()%>
						</tbody>
						<thead>
							<tr>
								<th scope="col">Totale</th>
							</tr>
						</thead>
						<tbody>
							<td><%=getTotal()%> &euro;</td>
						</tbody>
					</table>
					<!-- Indirizzo di consegna -->
					<p class="card-title lead">Indirizzo consegna</p>
					
						<p id="spedition-address"></p>
					 
					<!-- Pagamenti -->
					<p class="lead">Scegli il metodo di pagamento</p>
					<table class="table table-sm table-borderless">
						<thead>
							<tr>
								<th scope="col">Numero</th>
								<th scope="col">Scadenza</th>
								<th scope="col"></th>
							</tr>
						</thead>
						<tbody id="payment_table_body">
						</tbody>
					</table>
					<button class="btn btn-primary" onclick="completeCheckout()">Completa ordine</button>
				</div>
			</div>
		</div>
		<div class="col-sm-12  col-md-6 d-flex">
			<div class="container-fluid">
				<div class="card">
					<div class="card-header">Aggiungi nuovo metodo</div>
					<div class="card-body">
						<form name="newPaymentMethod" id="new-payment-method"
							method="POST" onsubmit="return false;">
							<p class="lead">Nuovo metodo di pagamento</p>
							<div class="row">
								<div class="col">
									<input type="text" class="form-control" name="fullname"
										placeholder="Nome completo">
									<div class="invalid-feedback">Nome non valido</div>
								</div>
								<div class="col">
									<input type="number" class="form-control"
										placeholder="Numero carta" name="cardNumber">
									<div class="invalid-feedback">Numero non valido</div>
								</div>
							</div>
							<div class="row mt-2">
								<div class="col-sm-6">
									<input type="number" name="cvv" class="form-control"
										placeholder="cvv" maxlength="3">
								</div>
								<div class="col-sm-3">
									<input type="number" name="expirationMonth"
										class="form-control" placeholder="Mese scadenza" maxlength="2">
									<div class="invalid-feedback">Data non valida</div>
								</div>
								<div class="col-sm-3">
									<input type="number" name="expirationYear" class="form-control"
										placeholder="Anno scadenza" maxlength="2">
									<div class="invalid-feedback">Data non valido</div>
								</div>
							</div>
							<div class="row d-flex justify-content-end">
								<button class="btn btn-primary mt-2"
									onclick="addNewPaymentMethod()">
									<i class="fas fa-plus"></i>
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script
	src="${pageContext.request.contextPath}/script/validator.js?ver=<%=rand%>"> </script>
<script
	src="${pageContext.request.contextPath}/script/payment_methods.js?ver=<%=rand%>"> </script>
<script
	src="${pageContext.request.contextPath}/script/checkout.js?ver=<%=rand%>"> </script>

<jsp:include page="footer.jsp"></jsp:include>
