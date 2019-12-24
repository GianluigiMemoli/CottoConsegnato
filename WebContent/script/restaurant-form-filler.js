function isEmpty(obj){
    return (Object.getOwnPropertyNames(obj).length === 0);
}

function getData(){
    $.post("RestaurantGetter", (data, status) => {
        console.log(data)
        console.log(status)
        fillForm(data);
    }); 
}

function fillForm(restaurantData){
    if(!isEmpty(restaurantData)){
        const form = document.forms["restaurantForm"];
        form["restaurantName"].value        = restaurantData["name"];
        form["restaurantCategory"].value    = restaurantData["category"];
        form["restaurantStreet"].value      = restaurantData["street"];
        form["restaurantCap"].value         = restaurantData["cap"];
        form["restaurantProvince"].value    = restaurantData["province"];
        form["restaurantCity"].value        = restaurantData["city"];
    }
}