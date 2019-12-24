/*START Ingredient manager section*/
let ingredientsList = {}
let categories = new Set();
function hasComposition(obj) {
    for(var key in obj) {
        if(obj.hasOwnProperty(key))
            return true;
    }
    return false;
}
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}

function removeEntry(node, ingredient){
	console.log(`remove ${ingredient}`)
	console.log(node)
	console.log(node.parentNode)
	node.parentNode.remove();
	ingredient = getIngredientFromElement(node)
	delete ingredientsList[ingredient];
	console.log("ingredientList now")
	console.log(JSON.stringify(ingredientsList))
}

function getIngredientFromInput(){
	//will return the value of ingredient name field, than reset its current value
	let field = document.querySelector("#ingredientName");
	fieldValue = field.value;
	field.value = "";
	return fieldValue;		
}

function getIngredientFromElement(element){
	let name = element.parentNode.querySelector(".ingredient-name"); 
	return name.innerHTML;
}


function getIsAllergen(){
	let checkbox = document.querySelector("#allergeneCheck"); 
	let checked  = checkbox.checked
	checkbox.checked = false; 
	return checked; 	
}

function add(item){
	console.log('add('+item+')')
	ingredientsList[item] = {isAllergen: getIsAllergen()}
	console.log("ingredientList now")
	console.log(JSON.stringify(ingredientsList))
}

function editMode(element){
	ingredient = getIngredientFromElement(element)
	document.querySelector("#allergeneCheck").checked = ingredientsList[ingredient].isAllergen;
	document.querySelector("#ingredientName").value = ingredient; 	
	//changing button add behaviour
	let addBtn = document.querySelector("#addIngredient");
	oldOnclick = addBtn.onclick
	addBtn.onclick = function(){save(element, addBtn, oldOnclick)}
}

function save(element, addBtn, oldOnclick){
	let newIngredientName = document.querySelector("#ingredientName").value;
	let oldIngredient = getIngredientFromElement(element)
	delete ingredientsList[oldIngredient]
	add(newIngredientName)
	//restore addbtn function
	addBtn.onclick = oldOnclick;
	//changing list item
	let listName = element.parentNode.querySelector(".ingredient-name");
	listName.innerHTML = newIngredientName;
	//reset value field
	document.querySelector("#ingredientName").value = ""
}

function createListEntry(){	
	let ingredientName = getIngredientFromInput();
	if(!isBlank(ingredientName) && !(ingredientName in ingredientsList)){
		//if ingredient field is not already in and not blank will set the ingredient to the ingredients list
		let listItem = document.createElement("li");
		listItem.setAttribute("id", "ingredients-list-item");
		listItem.setAttribute("class", "list-group-item");
		
		let ingredientNameSpan = document.createElement("span");
		ingredientNameSpan.setAttribute("class", "ingredient-name");
		ingredientNameSpan.innerHTML = ingredientName;
		
		let removeBtn = document.createElement("button");
		removeBtn.setAttribute("class", "btn btn-default float-right");
		removeBtn.setAttribute("onclick", "removeEntry(this)");
		
		let removeIcon = document.createElement("span");
		removeIcon.setAttribute("class", "fas fa-minus");
		
		let editBtn = document.createElement("button");
		editBtn.setAttribute("class", "btn btn-default float-right");
		editBtn.setAttribute("onclick", "editMode(this)");
		
		
		let editIcon = document.createElement("span");
		editIcon.setAttribute("class", "far fa-edit");
		
		//appending items
		removeBtn.appendChild(removeIcon);
		editBtn.appendChild(editIcon);
		listItem.appendChild(ingredientNameSpan);
		listItem.appendChild(editBtn);
		listItem.appendChild(removeBtn);		
		
		//appending to DOM
		let list = document.querySelector("#ingredients-list");
		list.appendChild(listItem);
		//adding to ingredientsList object 
		add(ingredientName);
	}
}

/*END Ingredient manager section*/

/*START Sending section*/
function resetItemFieldState(fields){
    fields.forEach(field=>{
        if(field.classList.contains("is-invalid"))
            field.classList.remove("is-invalid");
    });
}


function invalidateItemField(field){
    field.classList.add("is-invalid"); 
}

function validateItem(form){
	let nameField = form["itemName"];
	let categoryField = form["itemCategory"];
	let priceField = form["itemPrice"];
	let valid = true;
	resetItemFieldState([nameField, categoryField, priceField]);
	//test fields
	if(isBlank(nameField.value)){
		invalidateItemField(nameField);
		valid = false; 
	}
	if(isBlank(categoryField.value)){
		invalidateItemField(categoryField);
		valid = false;
	}
	
	if(isBlank(priceField.value)){
		invalidateItemField(priceField);
		valid = false; 
	}
	if (valid){
		let isPlate = hasComposition(ingredientsList);		
		let json = {
				"name": 	nameField.value,
				"category": categoryField.value,
				"price":	Number.parseFloat(priceField.value),
				"hasComposition": isPlate				
			};
		if(isPlate)
			json.composition = ingredientsList;
		
		return json;		
	}
	
	return null; 
}
function getItemAttributes(){
	let form = document.forms["menuForm"]; 
	let item = validateItem(form);
	if(item != null){
		let itemJSON = JSON.stringify(item); 
		console.log(itemJSON);		
		 $.ajax({
		        type: "POST",
		        url: "ItemSaver",
		        data: itemJSON,		        	
		        contentType: "application/json; charset=UTF-8",
		        dataType: "text",
		        success: function(data){
					updateMenu();
					cleanForm(form);
					cleanList();
					console.log("success insertion");
				},
		        error: function(xhr) {
		            alert(xhr.responseText);
		        }
		  });
		 
		 
	}
}

function cleanForm(form){	
	form.reset();
}

function cleanList(){
	let list = document.querySelectorAll("ul#ingredients-list > li");
	if(list.length > 0){
		list.forEach(ingredient => ingredient.remove());
	} 
}

function sendItem(){
	getItemAttributes();
}
/*END Sending section*/

/*START Menu Retrieving section*/
function makeRow(name, category, composition, price){
	if(composition === null)
		composition = "" ;

	//creating row nodes
	let tableRow = document.createElement("tr");
	let nameData = document.createElement("td");
	//adding id attribute to name, it will be useful in removeItem()
	nameData.setAttribute("id", "item-name");
	let categoryData = document.createElement("td");
	let compositionData = document.createElement("td");
	let priceData = document.createElement("td");
	let removeBtn = document.createElement("button");
	removeBtn.setAttribute("class", "btn btn-default");
	removeBtn.setAttribute("onclick", "removeItem(this)");
	let removeIcon = document.createElement("span");
	removeIcon.setAttribute("class", "fas fa-minus");
	//adding icon to btn
	removeBtn.appendChild(removeIcon);
	//adding data to cols
	nameData.innerHTML = name;
	categoryData.innerHTML = category,
	compositionData.innerHTML = composition; 
	priceData.innerHTML = price;
	//adding cols to row
	tableRow.appendChild(nameData);	
	tableRow.appendChild(categoryData);
	tableRow.appendChild(compositionData);
	tableRow.appendChild(priceData);
	tableRow.appendChild(removeBtn);
	//getting table
	let tableBody = document.querySelector("#menu-body");
	tableBody.appendChild(tableRow);
}
function getMenu(){
	let menu = null; 
	$.post("MenuGetter", (data, status) => {
        console.log(data)
		console.log(status)
		cleanCategories();
		data.forEach(item => categories.add(item.itemCategory));
		console.log("categories: ", categories);
		fillCategoryList(categories);
		fillTable(data);        
	}); 
	
}

function fillTable(items){	
	items.forEach(item => {
		let name = item.itemName;
		let category = item.itemCategory;		 
		let price = item.itemPrice;
		let composition = null;
		if(item.hasComposition){
			composition = "";
			for(ingredient in item.composition){
				//if ingredient is allergen make it bold
				if(item.composition[ingredient]){
					composition += " <b>" + ingredient + "</b>,"
				} else composition += " "+ingredient + ",";
			}			
			composition = composition.substring(0, composition.lastIndexOf(','));			
		}
		makeRow(name, category, composition, price);
	});
}

function updateMenu(){
	console.log("updating menu");
	let rows = document.querySelectorAll("#menu-body > tr");
	rows.forEach(row => row.remove());
	getMenu();	
}

/*END Menu Retrieving section*/

/*START Removing Menu Item section*/

function removeItem(triggeredFrom){
	let itemName = {"name": getItemName(triggeredFrom)};

	$.ajax({
		        type: "POST",
		        url: "ItemRemover",
		        data: JSON.stringify(itemName),		        	
		        contentType: "application/json; charset=UTF-8",
		        dataType: "text",
		        success: function(data){
					updateMenu();
					console.log("success removing");
				},
		        error: function(xhr) {					
					alert(xhr.responseText);
					console.log("Error removing");
		        }
		  });
}

function getItemName(btnPressed){
	let row = btnPressed.parentNode;
	let itemName = row.querySelector("#item-name").innerHTML;
	console.log("Triggered: " + itemName);
	return itemName;	
}


/*END Removing Menu Item section*/

/*START Category filler section*/
function cleanCategories() {
	let alreadyMade = document.querySelectorAll("#category-list > option");
	if (alreadyMade instanceof NodeList) {
		alreadyMade.forEach(made => made.remove());
	}
}
function makeOptionInDatalist(category){
	console.log("making");
	let datalist = document.querySelector("#category-list");
	let option = document.createElement("option");
	option.setAttribute("value", category);
	option.innerHTML = category;
	datalist.appendChild(option);
}

function fillCategoryList(){	
		console.log("prefillin");

	if(categories.size > 0){
			console.log("fillin");

		categories.forEach(category => makeOptionInDatalist(category));
	}
}
/*END Category filler section*/