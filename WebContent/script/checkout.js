function setAddress(){
	let address = JSON.parse(sessionStorage.getItem("userAddress"));	
	document.querySelector("#spedition-address").innerHTML = address["street"] + ", "+address["province"] + ", "+address["CAP"];
}

function completeCheckout(){
	//paymentID is a global variable from payment_methods.js
	if(paymentID != undefined){
		let request = {
			"paymentMethod": paymentID,
			"address": JSON.parse(sessionStorage.getItem("userAddress"))
		}
		$.ajax({
			method: "POST",
			url: "CompleteCheckout",
			contentType: "text/plain",
			data: JSON.stringify(request),
			success: () => alert("Ordine inviato"),
			error: (xhr) => alert(xhr.responseText)
		});
	} else {
		alert("Seleziona un metodo di pagamento.");
	}
}

setAddress();
getPaymentMethods();