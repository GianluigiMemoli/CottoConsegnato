<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.RestaurantBean"%>
<%@ page import="model.UserBean"%>

<%!//Restaurant sample
	RestaurantBean currentRestaurant;
	double rand = Math.random() * 2;	
%>
<%
	if((currentRestaurant=(RestaurantBean) session.getAttribute("currentRestaurant")) == null){
		response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
		return;
	}
%>  

<jsp:include page="header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class="col-sm-12 col-md-9">
			<div class="card">
				<div class="card-header"><%=currentRestaurant.getName()%></div>
				<div class="card-body" id="product-container">
				<!-- 	<div class="row">
						<div class="col-sm-12 border-bottom">
							<h4 class="card-title">Categoria</h4>
						</div>
						<div class="col-sm-12 col-md-6 mt-2">
							<div class="media border" id="product">
								<div class="media-body">
									<p class="lead">Prodotto</p>
									<p class="text-muted">Composizione</p>
									<p>5 &euro;</p>
								</div>
							</div>
						</div>
						<div class="col-sm-12 col-md-6 mt-2">
							<div class="media border" id="product">
								<div class="media-body">
									<p class="lead">Prodotto</p>
									<p class="text-muted">Composizione</p>
									<p>5 &euro;</p>
								</div>
							</div>
						</div>
						<div class="col-sm-12 col-md-6 mt-2">
							<div class="media border" id="product">
								<div class="media-body">
									<p class="lead">Prodotto</p>
									<p class="text-muted">Composizione</p>
									<p>5 &euro;</p>
								</div>
							</div>
						</div>
					</div> -->
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-3">
			<div class="card">
				<div class="card-header"><span>Carrello</span><span id="total" class="float-right"></span></div> 
					<div class="card-body">
						<ul class="list-group" id="cart-list">
						  					  
	   					</ul>
	   				</div>
	   			<div class="card-footer"><a class="btn btn-primary" href="${pageContext.request.contextPath}/Checkout.jsp">Conferma ordine</a></div>	
			</div> 
		</div>  
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/script/clientside-restaurantpage.js?ver=<%=rand%>"> </script>
<script>getProductList()</script>
<script>initCart();</script>
<jsp:include page="footer.jsp"></jsp:include>
