let number = -1;

function setNumber(radio){
	number = radio.value;
}

function reject(){
	if(number != -1){
		$.get("RestaurantOrders?reject="+number, function(data, status){
			  alert("Data: " + data);
			  location.reload();
		});
	} else alert("Scegli ordine!");
}

function accept(){
	if(number != -1){
		$.get("RestaurantOrders?accept="+number, function(data, status){
			  alert("Data: " + data);
			  location.reload();
		});
	} else alert("Scegli ordine!");
}

