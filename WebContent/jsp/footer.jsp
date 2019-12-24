<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%!
	double rand = Math.random()*2;
%>
	
    <script src="${pageContext.request.contextPath}/script/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>   
    <script src="${pageContext.request.contextPath}/fontawesome-free-5.9.0-web/js/all.js?ver=<%=rand%>"></script>
    <script src="${pageContext.request.contextPath}/script/sessionKiller.js?ver=<%=rand%>"> </script>
    </body>
</html> 