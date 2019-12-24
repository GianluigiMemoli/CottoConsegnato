let cart = {};

function getProductList(){
	//categories is a set of category
	let categories;
	//in organizedProducts will be a propriety for product category that will point to an array of products
	//belonging to that category
	let organizedProducts = {};

	$.ajax({
		method: "POST", 
		url: "MenuGetter",
		dataType: "json",
		success: (data) =>{
			categories = getCategories(data);
			organizedProducts = organizeProducts(categories, data);
			showProducts(organizedProducts);
		},
		error: () => alert("Error")
	});
}

function getCategories(products){
	let categories = new Set();
	products.forEach(product => categories.add(product["itemCategory"]));
	return categories;
}

function organizeProducts(categories, products){
	let organizedProducts = {};
	categories.forEach(category => organizedProducts[category] = []);
	products.forEach(product => organizedProducts[product["itemCategory"]].push(product));
	return organizedProducts;
}

function getIngredientList(composition){
	let list = "";
	for(ingredient in composition){
				//if ingredient is allergen make it bold
				if(composition[ingredient]){
					list += " <b>" + ingredient + "</b>,"
				} else list += " "+ingredient + ",";
			}			
			list = list.substring(0, list.lastIndexOf(','));
			return list;
}

function showProducts(products){
	let categoryNames; 
	for(category in products){
		//creating row with category name
			let rowName = document.createElement("div");
			rowName.setAttribute("class", "row");
			let colName = document.createElement("div");
			colName.setAttribute("class","col-sm-12 mt-4");
			let categoryTitle = document.createElement("h4");
			categoryTitle.setAttribute("class", "card-title");
			categoryTitle.innerHTML = category;
			colName.appendChild(categoryTitle);
			rowName.appendChild(colName);
			
			products[category].forEach((product)=>{			
				//creating product
				let colProduct = document.createElement("div");
				colProduct.setAttribute("class", "col-sm-12 col-md-6 mt-2 animate-appearing click-cursor");
				let media = document.createElement("div");
				media.setAttribute("class", "media border");
				media.setAttribute("id", "product");
				let mediaBody = document.createElement("div");
				mediaBody.setAttribute("class", "media-body");
				let productName = document.createElement("p");
				productName.setAttribute("class", "lead");
				productName.innerHTML = product.itemName;

				mediaBody.appendChild(productName);
				if(product.hasComposition){
					let ingredientList = getIngredientList(product.composition);
					let productComposition = document.createElement("p");
					productComposition.setAttribute("class", "text-muted");
					productComposition.innerHTML = ingredientList;
					mediaBody.appendChild(productComposition);
				}				
				let productPrice = document.createElement("p");
				productPrice.innerHTML =  product.itemPrice + "&euro;" ;				
				mediaBody.appendChild(productPrice);
				media.appendChild(mediaBody);				
				colProduct.appendChild(media);
				let anchor = document.createElement("a");
				anchor.setAttribute("class", "stretched-link");
				anchor.setAttribute("onclick", `addToCart(${JSON.stringify(product)})`);
				colProduct.appendChild(anchor);					
				rowName.appendChild(colProduct);
		});
		
		document.querySelector("#product-container").appendChild(rowName);
	}
}

function removeBlanks(string){
	return string.replace(/\s/g, '');
}

function addToCartList(cart){
	let list = document.querySelector("#cart-list");
	for(name in cart){
		if(document.querySelector("#product-named-"+removeBlanks(name)) == null){
			let listItem = document.createElement("li");
			listItem.setAttribute("class", "list-group-item");
			listItem.setAttribute("id", `product-named-${removeBlanks(name)}`);
			listItem.innerHTML = name;
			let spanQuantity = document.createElement("span");
			spanQuantity.setAttribute("id", `quantity-${removeBlanks(name)}`);
			spanQuantity.setAttribute("class", "text-muted ml-2");
			spanQuantity.innerHTML = cart[name];
			let anchorAddQty = document.createElement("a");
			anchorAddQty.setAttribute("onclick", `removeFromCart("${name}")`);
			let minusIcon = document.createElement("i");
			minusIcon.setAttribute("class", "fas fa-minus float-right");
			anchorAddQty.appendChild(minusIcon);
			listItem.appendChild(spanQuantity);
			listItem.appendChild(anchorAddQty);
			list.appendChild(listItem); 
		} else {
			document.querySelector(`#quantity-${removeBlanks(name)}`).innerHTML = cart[name];
		}
	}
}

function removeFromCartList(name){
	let listItem = document.querySelector("#product-named-"+removeBlanks(name));
	if(!(name in cart)){
		listItem.remove();
	} else {
		listItem.querySelector(`#quantity-${removeBlanks(name)}`).innerHTML = cart[name];
	}
}

function removeFromCart(productName){
	cart[productName]--;
	if(cart[productName] == 0){
		delete cart[productName];
	} 
	removeFromCartList(productName);
	sendActionToUpdateTotal("remove", productName);

}

function addToCart(product){
	if(!(product.itemName in cart)){
		cart[product.itemName] = 1;
	} else cart[product.itemName]++;
	addToCartList(cart);
	sendActionToUpdateTotal("add", product.itemName);
}

function sendActionToUpdateTotal(actionType, productName){
	let action = {
		"action": actionType,
		"product": productName
	};
	$.ajax({
		url: "CartManager",
		method: "POST",
		contentType: "json",
		data: JSON.stringify(action),
		success: (data) =>{			
			updateTotal(data["total"]);
		}, 
		error: () => alert("Unhandled error")
	});
}

function sendActionForOldCart(){
		let action = {
		"action": "get"
	}
	;
	$.ajax({
		url: "CartManager",
		method: "POST",
		contentType: "json",
		data: JSON.stringify(action),
		success: (data) =>{			
			console.log("data", data);			
			updateTotal(data["total"]);
			delete data["total"];
			cart = data;
			addToCartList(cart);
		}, 
		error: () => alert("Unhandled error")
	})
}

function updateTotal(total){
	let span = document.querySelector("#total");
	span.innerHTML = "Totale: "+ total +" &euro;";
}

function initCart() {
	sendActionForOldCart();	
}