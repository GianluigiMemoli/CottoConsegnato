<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="alertShower.jsp"></jsp:include>      
 
<div id="pIvaModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="modalForRestaurateur" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalForRestaurateur">Richiesta
					partita IVA per validazione</h5>
				<button type="button" class="close" data-dismiss="modal"
					data-target="#modalForRrstaurateur" aria-label="close"></button>
			</div>
			<div class="modal-body">
				<p>
					È pregato di compilare il form sottostante ed attendere la verifica
					degli amministratori.<br> A verifica effetuata le sarà
					possibile inserire i dati riguardanti il ristorante ed iniziare a
					ricevere gli ordini!
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-lg-4 col-sm-4"></div>
	<div class="col-lg-4 col-sm-4">
		<form action="${pageContext.request.contextPath}/AuthorizationRequest" method="post">
			<div class="form-group">
				<label for="partita iva">Partita IVA</label> <input type="text"
					class="form-control" pattern="[0-9]{11}" name="pIVA">
			</div>
			<input type="submit" class="btn btn-primary ml-1" value="Invia">
		</form>
	</div> 
</div>
 
<jsp:include page="footer.jsp"></jsp:include>


<%!	
	boolean modal = false;
%>
<% 
	String modalParameter = request.getParameter("modal");
	if(modalParameter != null && modalParameter.equals("1")){
		modal = true;		
	}
%>
<script> 
function firstAccess(isFirst){
	if(isFirst){
		$(document).ready(function() {
			$("#pIvaModal").modal('show'); 
		});
	}
}
firstAccess(<%=modal%>);
</script>



