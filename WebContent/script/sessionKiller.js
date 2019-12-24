function invalidateSession(){
	$.ajax({
		url: "SessionKiller",
		method: "POST",
		contentType: "text/plain", 
		success: () => {
			location.reload();
		}, 
		error: (xhr) => {
			alert(xhr.responseText);
		}
	});
}