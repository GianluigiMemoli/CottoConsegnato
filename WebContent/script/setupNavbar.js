function loadNavbar(userType, username){
    //Init the dropdown items, based on userType
    const dropdownMenu = $(".dropdown-menu");
    const listItemTemplate = "<a class=\"dropdown-item\" href=\"\"></a>";
    var fields = [];
    if(userType == "cliente"){
        console.log("Usertype: "+userType);
        fields.push($(listItemTemplate).attr("href", "#").text("Profilo"));
        fields.push($(listItemTemplate).attr("href", "#").text("Ordini"));
        $("#favi-user").after(username);
    } else if(userType == "ristoratore") {
        fields.push($(listItemTemplate).attr("href", "#").text("Profilo ristorante"));
        fields.push($(listItemTemplate).attr("href", "#").text("Ordini ricevuti (0)"));   
        $("#favi-user").after(username);     
    } else if(userType == "admin") {
        fields.push($(listItemTemplate).attr("href", "#").text("Amministrazione"));    
        $("#favi-user").after(username);    
    } else if(userType == "noAuth") {
        fields.push($(listItemTemplate).attr("href", "#").text("Registrazione"));
        fields.push($(listItemTemplate).attr("href", "#").text("Login"));   
        $("#favi-user").after("Non loggato");  
    }
    if(userType != "noAuth")
        fields.push($(listItemTemplate).attr("href", "#").text("Logout"));
   $(dropdownMenu).append(fields);
}

