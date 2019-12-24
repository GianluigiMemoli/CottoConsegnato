function validateForm(){
     console.log("in validation");
    //Getting <input type="" ... > elements from the form
    const inputEmail = document.forms["loginForm"]["email"];
    const inputPassword = document.forms["loginForm"]["password"];
    const pwdPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~])/ ;
    const emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/; 
    let invalidFields = []; 

    if(!emailPattern.exec(inputEmail.value)){
        invalidFields.push(inputEmail)
    }
    
    if(!pwdPattern.exec(inputPassword.value)){
        invalidFields.push(inputPassword);
    }

    if(invalidFields.length > 0){
        setInvalid(invalidFields)
        return false; 
    }

    return true;
}

function setInvalid(fields){
    //It sets "is-invalid" class to field
    fields.forEach(field => {
        field.className += " is-invalid";    
    });
    
}
