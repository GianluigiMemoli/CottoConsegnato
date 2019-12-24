function setInvalid(fields){
	//It sets "is-invalid" class to field
	console.log("invalidating fuction");
    fields.forEach(field => {
        field.className += " is-invalid";    
    });
} 

function destroyAlert(){
	const alertPlaceHolder = document.getElementById("alertPlaceHolder");
	alertPlaceHolder.setAttribute("class", "");
	alertPlaceHolder.innerHTML = "";
	const failedFields = document.getElementsByClassName("is-invalid");
	for(let i=0; i < failedFields.length; i++)
		failedFields.item(i).classList.remove("is-invalid")
}

function alertMaker(txt, priority){
	console.log("alert creation");
	const alertPlaceHolder = document.getElementById("alertPlaceHolder");
	alertPlaceHolder.setAttribute("class", "alert alert-"+priority);
	alertPlaceHolder.innerHTML = txt;
}

function validateForm(){
	console.log("in validate form func");
	destroyAlert();
	//Fetching data from registration form
	const nameField	    	= document.forms["registrationForm"]["name"];
	const surnameField  	= document.forms["registrationForm"]["surname"];
	const emailField    	= document.forms["registrationForm"]["email"];
	const passwordField		= document.forms["registrationForm"]["password"];
	const birthDateField	= document.forms["registrationForm"]["birthDate"];
	const accountTypeField 	= document.forms["registrationForm"]["type"];
	/* Checking if there is any empty string (it's already checked by the required html attribute,
	 * but in case someone inspecting the form removes it, then it's double checked (triple server-side) 
	 */
	if(nameField.value == "" || surnameField.value == "" || emailField.value == "" || birthDateField.value == "" || passwordField.value == "" ){		
		alertMaker("Vanno compilati tutti i campi", "danger");
		return false;
	}
	
	const name = nameField.value; 
	const surname = surnameField.value; 
	const email = emailField.value;
	const password = passwordField.value;
	const accountType = accountTypeField.value; 
	
	let invalidFields = new Set(); 
	
	const plainTextPattern = /^[A-z]{3,30}$/; 
	const pivaPattern = /[0-9]{11}/ ; 
	
	//Checking values lengths
	if(plainTextPattern.exec(name) == null){
		invalidFields.add(nameField); 		
	}
	console.log(plainTextPattern.exec(name))
	
	if(plainTextPattern.exec(surname) == null){
		invalidFields.add(surnameField);		
	}
	console.log(plainTextPattern.exec(surname))
	
	if(email.length > 320){
		invalidFields.add(emailField);
	}
	
	if(password.length < 8){
		invalidFields.add(passwordField);
	}
	
	if(password.length > 30){
		invalidFields.add(passwordField);
	}
	


	//Checking password content
	const pwdPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~])/ ;
	const match = pwdPattern.exec(password);
	if(match == null){
		invalidFields.add(passwordField);
	}
	
	//Checking if p.iva is malformed 
	if(accountType == "ristoratore") {
		const pivaField = document.forms["registrationForm"]["piva"]; 
		if(pivaPattern.exec(pivaField.value) == null)
			invalidFields.add(pivaField);
	}
		
	if(invalidFields.size == 0)
		return true;
	else {
		setInvalid(invalidFields);
		return false;
	}
		
	
}

/*
	La password deve essere lunga almeno 8 caratteri,
	deve essercene almeno uno caps e uno no,
	almeno un numero,
	e un carattere tra " !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~"

*/


