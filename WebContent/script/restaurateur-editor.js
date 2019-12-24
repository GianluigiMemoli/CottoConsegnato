function elapsedYears(birthdate){//birthdate is Date
	//Making diff from now date and user birthdate to get user's age	
	let elapsedMsFromBirthdate = Date.now() - birthdate.getTime();
	let msFromEpoch = new Date(elapsedMsFromBirthdate);
	let elapsedYears = Math.abs(msFromEpoch.getUTCFullYear() - 1970);
	console.log(elapsedYears);
	return elapsedYears;
}

function validateBirthdate(dateField){
	let dateFieldValue = dateField.value;
	if(dateFieldValue != undefined){
		let userAge = elapsedYears(new Date(dateFieldValue));
		let isAdult = (userAge >= 18);
		if(!isAdult)
			setInvalidClass(dateField);
		return isAdult;
	}
	setInvalidClass(dateField);
	return false;
}

function validateDetails(){
	let isFormValid = true; 
	let profileForm = document.forms["profileForm"];
	let textOnlyPattern = /^([A-z-'a-zÀ-ÿ]+\s*)+$/;
	const emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	resetState([profileForm["name"], profileForm["surname"], profileForm["birthdate"], profileForm["email"]]);
	isFormValid = testField(textOnlyPattern, [profileForm["name"], profileForm["surname"]]);
	isFormValid = testField(emailPattern, [profileForm["email"]]) && isFormValid;	
	isFormValid = validateBirthdate(profileForm["birthdate"]) && isFormValid;	
	return isFormValid;
}


function editDetails(){
	if(validateDetails()){
		let request = $("form[name='profileForm']").serialize();
		$.ajax({
			method: "POST",
			url: "UserUpdater",
			data: request,
			dataType: "text",
			success: (xhr) => {
				alert("success");
			},
			error: (xhr) => {
				alert(xhr.responseText);
			},
			
		});
	}
}

function validatePassword(oldPasswordField, newPasswordField){
	const pwdPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~])/ ;
	return testField(pwdPattern, [oldPasswordField, newPasswordField]);
}

function changePassword(){
	let passwordForm = document.forms["passwordForm"];
	if(validatePassword(passwordForm["newPassword"], passwordForm["oldPassword"])){
		let request = $("form[name='passwordForm']").serialize();
		$.ajax({
			method: "POST",
			url: "PasswordChanger",
			data: request,
			dataType: "text/plain",
			success: (xhr) => {
				alert("success");
			},
			error: (xhr) => {
				console.log("error");
				alert(xhr.responseText);
			},
			
		});
	}
}