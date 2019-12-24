function resetState(fields){
    fields.forEach(field=>{
        if(field.classList.contains("is-invalid"))
            field.classList.remove("is-invalid");
    });
}

function setInvalidClass(field){
    field.classList.add("is-invalid"); 
}

function testField(regex, fields){
    let valid = true;    
    fields.forEach(field =>{
    	console.log(field)	
        if(regex.exec(field.value) === null){
            console.log("invalid")
            setInvalidClass(field);
            valid = false;
        }
    });
    return valid;
}
