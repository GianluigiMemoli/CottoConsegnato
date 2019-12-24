<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
    
<jsp:include page="header.jsp"></jsp:include> 
 <jsp:include page="alertShower.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row d-flex align-items-center justify-content-center">
        <div class="col-md-6 justify-content-center align-items-center">
            <div class="card">
                <div class="card-body">                
                    <h3 class="card-title text-center">Login</h3>
                    <form method="POST" name="loginForm" onsubmit="return validateForm();" action="${pageContext.request.contextPath}/Login">
                        <div class="form-group">
                          <label for="email">Email</label>
                          <input type="email" name="email" id="emailField" class="form-control" placeholder="">                          
                        </div>
                        <div class="form-group">
                          <label for="password">Password</label>
                          <input type="password" class="form-control" name="password" id="passwordField" placeholder="">
                        </div>
                        <input type="submit" class="btn btn-primary" value="Login">
                    </form>
                </div>
            </div>
        </div>
    </div>
 </div>
      	<script src="${pageContext.request.contextPath}/script/login.js"></script>
    
    <jsp:include page="footer.jsp"></jsp:include>

 
