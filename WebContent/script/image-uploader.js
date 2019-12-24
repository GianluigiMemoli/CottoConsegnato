function getFileFromForm(){
	let form = document.forms["imageForm"];	
	let fileField = form["file-uploaded"];
	return fileField.files[0];	
}

function encodeAsBASE64(image){
	let reader = new FileReader(); 
	reader.onloadend = function () {
		/*let preresult = reader.result.split(',');
		let result = preresult[1];*/
		let result = reader.result;
		image = {
			type: getType(image),
			encoded: result 
		};
		toServlet(image);
	}
	reader.readAsDataURL(image);
}

function toServlet(request){
	$.ajax({
		method: "POST", 
		data: JSON.stringify(request), 
		dataType: "text", 
		url: "RestaurantImageSaver",
		success: () => {
			console.log("uploaded successfully");
			alert("Successo");
		},
		error: (xhr) => {
			alert(xhr.responseText);
		}
	});
}

function getType(file){
	let type = file.type; 
	let splitted = type.split("/");
	return splitted[1]; 
}


function isImage(file){
	if(file === undefined)
		return false;
	let mime = file.type; 
	let splitted = mime.split("/");
	return splitted[0] == 'image'; 
}

function uploadPressed(){
	let file = getFileFromForm();
	if(isImage(file)){
		encodeAsBASE64(file);
	} else {
		alert("Non è un file immagine");
		return;
	}
}
