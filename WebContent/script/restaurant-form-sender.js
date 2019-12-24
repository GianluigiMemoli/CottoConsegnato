
function validation(){
    //Getting fields from form
    const form          =        document.forms["restaurantForm"]; 
    const nameField     =        form["restaurantName"];        
    const categoryField =        form["restaurantCategory"];
    const streetField   =        form["restaurantStreet"];    
    const capField      =        form["restaurantCap"];
    const provinceField =        form["restaurantProvince"];
    const cityField     =        form["restaurantCity"];   

    resetState([nameField, categoryField, streetField, capField, provinceField, cityField]);   
    //Validating regex
    //matching on: nameField.value, categoryField.value, streetField.value, cityField.value
    const textOnlyPattern = /^([A-z-'a-zÀ-ÿ]+\s*)+$/;
    //for provinceField.value
    const provincePattern = /^[A-z]{2}$/;
    //for capField.value 
    const capPattern = /^\d{5}$/;

    called = true;
    
    let valid = testField(textOnlyPattern, [nameField, categoryField, streetField, cityField]);
    valid = testField(provincePattern, [provinceField]) && valid;
    valid = testField(capPattern, [capField]) && valid;
    return valid;
}

function sendForm(){
    if(validation()){
        let data = $("form[name='restaurantForm'").serialize();
        console.log(data)
        $.ajax({
            type: "POST", 
            url: "RestaurantSaver",
            data: data, 
            dataType: "html", 
            success: (msg) => alert(msg + "Successo"), 
            error: () => alert("unhandled error"),            
        });
    }
}

