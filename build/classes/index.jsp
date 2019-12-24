<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"></jsp:include>
 <!--index-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/index.css?ver=<%=Math.random()*2%>">

 
<div class="container-fluid h-100 justify-content-center d-flex" id="index_container">
	<div class="row w-100">
		<div class=" col-sm-12 align-self-center mx-auto">   
			<p class="display-1">Cotto &amp;<br> Consegnato</p> 
			<div class="row d-flex justify-content-center">
			<div class="col-sm-6 float-left">
				<button id="btnr" type="button" class="btn btn-outline-success" onclick="location.href='RegistrationForm.jsp;'">Registrati</button>
			</div>  
			<div class="col-sm-6 float-right">	
				<button id="btnl" type="button" class="btn btn-outline-success"  onclick="location.href='LoginForm.jsp;' ">Login</button>
			</div>								
			</div>
		</div>  
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
  