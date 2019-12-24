let email = null;
function setEmail(radio){
	email = radio.value;
}

function acceptOwner(){
	if(email != null){
		$.get("Authorize?accept="+email, function(data, status){
			  alert(data);
			  location.reload();
		});
	} else alert("Scegli Utente!");
}

function rejectOwner(){
	if(email != null){
		$.get("Authorize?reject="+email, function(data, status){
			  alert(data);
			  location.reload();
		});
	} else alert("Scegli Utente!");
}