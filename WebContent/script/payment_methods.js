let newPaymentMethod = {
	fullname: "", 
	cardNumber: "",
	cvc: "",
	expirationMonth: "",
	expirationYear: ""
};

let paymentID;

function getTwoLastYearDigits(year){
	year = year + ""; 
    let lastTwoDigits = year[year.length-2] + year[year.length-1];
    return Number.parseInt(lastTwoDigits);
}


function validatePaymentMethod(){
	let valid = true;
	let methodForm = document.forms["newPaymentMethod"];
	resetState(methodForm.querySelectorAll("input"));
	let fullnamePattern = /^\s*([A-z-'a-zÀ-ÿ]+\s*)+$/;
	if((valid = testField(fullnamePattern, [methodForm["fullname"]]) && valid)){
		//if is valid, check if there are at least two distinct strings
		let fullname = methodForm["fullname"].value
						.split(" ")
						.filter(string => {if(string != "") return string});
		if(fullname.length < 2){
			setInvalidClass(methodForm["fullname"]);
			valid = false;
		}
		else{
			newPaymentMethod.fullname = "";
			console.log("fullname: ", fullname);
			let i=0;
			for(name of fullname){
					console.log(`name${++i}`, name);
					newPaymentMethod.fullname += name +" ";
			}			
			//removing last space
			newPaymentMethod.fullname = newPaymentMethod.fullname.substring(0, newPaymentMethod.fullname.lastIndexOf(" "));
		}
	}

	let cardNumberPattern = /^[^A-Z^a-z]?\d{10,256}$/i;
	if((valid = testField(cardNumberPattern, [methodForm["cardNumber"]]) && valid)){
		newPaymentMethod.cardNumber = methodForm["cardNumber"].value;
	};

	let cvcPattern = /^[\d]{3}$/;
	if((valid = testField(cvcPattern, [methodForm["cvv"]]) && valid)){
		newPaymentMethod.cvc = methodForm["cvv"].value;
	}

	let datePattern = /^[\d]{2}$/;
	if((valid = testField(datePattern, [methodForm["expirationMonth"]]) && valid)){
		var monthNumber = Number.parseInt(methodForm["expirationMonth"].value);
		if(!(monthNumber > 0 && monthNumber < 13)){
			setInvalidClass(methodForm["expirationMonth"]);
			valid = false;
		}
	}

	if((valid = testField(datePattern, [methodForm["expirationYear"]]) && valid)){
		let currentDate = new Date();
		let currentFullYear = currentDate.getFullYear(); 
		let currentYearShort = getTwoLastYearDigits(currentFullYear);
		let expirationYear = Number.parseInt(methodForm["expirationYear"].value);		
		console.log("currentFullYear", currentFullYear)
		console.log("currentYearShort", currentYearShort)
		console.log("expirationYear", expirationYear)
		
		if(expirationYear < currentYearShort || (expirationYear == currentYearShort && monthNumber <= currentDate.getMonth())){
			setInvalidClass(methodForm["expirationYear"]);
			setInvalidClass(methodForm["expirationMonth"]);
			valid = false;
		}else {
			newPaymentMethod.expirationYear = expirationYear.toString();
			newPaymentMethod.expirationMonth = monthNumber.toString();			
		}

	}

	return valid;
}

function addNewPaymentMethod(){
	if(validatePaymentMethod()){
		let request = {
			"action": "put", 
			"paymentMethod": newPaymentMethod
		}

		$.ajax({
			method: "post",
			url: "PaymentMethodManagement", 
			data: JSON.stringify(request),
			dataType: "text",
			success: () =>{
				alert("aggiunto correttamente");
				getPaymentMethods();
			},
			error: (xhr) => alert(xhr.responseText)
		});
	}
}

function getPaymentMethods(){
	let request = {
		"action": "get",		
	}

	$.ajax({
		method: "post",
		url: "PaymentMethodManagement",
		data: JSON.stringify(request),
		dataType: "text",
		success: (data) => fillPaymentMethodsTable(JSON.parse(data)),
		error: (xhr) => alert(xhr.responseText)
	});
}	

function fillPaymentMethodsTable(methods){
	let tbody = document.querySelector("#payment_table_body");
	let rows = tbody.querySelectorAll("tr");
	console.log(methods);
	if(rows.length > 0){
		console.log(rows);
		rows.forEach(row => row.remove());
	}
	methods.forEach(method => {		
		let row = document.createElement("tr");
		let numberTD = document.createElement("td");
		let expirationTD = document.createElement("td");
		let radioButtonTD = document.createElement("td");
		let radioButton = document.createElement("input");
		radioButton.setAttribute("class", "form-check-input");
		radioButton.setAttribute("type", "radio");
		radioButton.setAttribute("value", `${method.id}`);
		radioButton.setAttribute("name", "card_id");
		radioButton.setAttribute("onclick", "setId(this)");
		

		numberTD.innerHTML = method.cardNumber;
		expirationTD.innerHTML = method.expirationDate;
		radioButtonTD.appendChild(radioButton);

		row.appendChild(numberTD);
		row.appendChild(expirationTD);
		row.appendChild(radioButtonTD);

		tbody.appendChild(row);

	});
}

function setId(radioButton){
	paymentID = radioButton.value;	
}