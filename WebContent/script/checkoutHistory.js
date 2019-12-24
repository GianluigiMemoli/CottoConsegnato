function getCheckouts() {
	$.ajax({
		method: "post",
		dataType: "JSON",
		url: "CheckoutHistory",
		success: (data) => insertCheckoutElements(data),
		error: (xhr) => console.log("unexpected error " + xhr.responseText)
	});
}

function insertCheckoutElements(checkouts) {
	const media = '<div class="media border-bottom mt-4 mb-4"><img onload="correctImage(this)" id="restaurant_img" src="" class="mr-3" alt="..."><div class="media-body"><h5 class="mt-0" id="order_n"></h5><p id="date"></p><p id="order_status"></p><p class="lead">Ordine comprensivo di:</p><table><thead><tr><th scope="col">Prodotto</th><th scope="col"></th></tr></thead><tbody><tr><th scope="row">Totale</th><td id="tdata_total"></td></tr></tbody></table><p class="lead"><p id="restaurant_address"></p></p><p class="lead" ><p id="delivery_address"></p></p></div></div>';

	for(checkout of checkouts){
		let element = $.parseHTML(media);
		element[0].querySelector("#restaurant_img").setAttribute("src", checkout.restaurant.imgsrc);
		element[0].querySelector("#order_n").innerHTML = "#" + checkout.number;
		element[0].querySelector("#date").innerHTML = "Effettuato il " + checkout.orderDate;
		element[0].querySelector("#order_status").innerHTML = "Stato: "+checkout.status;
		for(item of checkout.items){
			let row = "<tr><td>"+item.itemName+"</td>"+ "<td>X "+ item.quantity +"</td></tr>";
			row = $.parseHTML(row);
			$(element[0].querySelector("tbody")).prepend(row[0]);
		}
		element[0].querySelector("#tdata_total").innerHTML = checkout.amount + "&euro;";
		element[0].querySelector("#restaurant_address").innerHTML = "<br>"+checkout.restaurant.name+"<br>"+ checkout.restaurant.address;
		element[0].querySelector("#delivery_address").innerHTML = "Consegna in:<br>"+checkout.deliveryAddress;
		$("#checkout_list").append(element);
	}
}


