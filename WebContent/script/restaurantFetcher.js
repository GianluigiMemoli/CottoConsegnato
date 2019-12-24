function storeAddress(address) {
	sessionStorage.setItem("userAddress", JSON.stringify(address));
}
function validateValue(value, pattern) {
	let isValid = !(pattern.exec(value) == null);
	console.log("isvalid: ", isValid);
	return isValid;
}


function isValid(address) {
	let splittedAddress = address.split(",");
	if (splittedAddress.length != 3) {
		alert("Indirizzo incompleto");
		return false;
	}
	if (splittedAddress.length == 3) {
		let addressPattern = /^\s*([A-z-'a-zÀ-ÿ]+\s*)+(\s\d+)\s*$/;
		if (!validateValue(splittedAddress[0], addressPattern)) {
			alert("Indirizzo non valido\nInserire via/piazza seguito da numero civico");
			return false;
		}
		let provincePattern = /^\s*[A-z]{2}\s*$/;
		if (!validateValue(splittedAddress[1], provincePattern)) {
			alert("Provincia non valida");
			return false;
		}
		let zipcodePattern = /^\s*\d{5}\s*$/;
		if (!validateValue(splittedAddress[2], zipcodePattern)) {
			alert("Cap non valido");
			return false;
		}

	}
	let addressOBJ = {
		"street": splittedAddress[0],
		"province": splittedAddress[1],
		"CAP": splittedAddress[2]
	};

	storeAddress(addressOBJ);
	return addressOBJ;

}

function searchPressed() {
	let addressField = document.querySelector("input[name='input-address']");
	let validationResult = isValid(addressField.value);
	if (validationResult != false) {
		getRestaurants(validationResult["province"]);
	}
}

function saveRestaurants(restaurants) {
	sessionStorage.setItem("restaurants", JSON.stringify(restaurants));
}

function getRestaurants(province) {
	province = {
		"province": province.replace(/\s+/g, "")
	};
	let categories = new Set();
	$.ajax({
		method: "POST",
		dataType: "json",
		data: JSON.stringify(province),
		url: "RestaurantByProvince",
		success: (data) => {
			console.log(data);
			saveRestaurants(data);
			cleanRestaurants();
			data.forEach(restaurant => {
				categories.add(restaurant.category.toUpperCase());
				makeRestaurantCard(restaurant);
			});
			createCategoryFilter(categories);
		},
		error: () => alert("error")
	});
}

function cleanRestaurants() {
	document.querySelectorAll("#rest-container > div").forEach(
		element => element.remove()
	);

}

function linkToRestaurant(owner) {
	let href = "Restaurant?owner=" + owner;
	return href;
}

function makeRestaurantCard(restaurantData) {
	//given a restaurant object it will make a card to link the restaurant on the clients' home	
	let card, row, imgcol, img, dataDiv, cardBody, restaurantName, restaurantCategory, anchor;
	console.log(restaurantData);
	card = document.createElement("div");
	card.setAttribute("class", "card sm-4 mb-3 animate-appearing click-cursor");
	row = document.createElement("div");
	row.setAttribute("class", "row no-gutters");

	imgcol = document.createElement("div");
	imgcol.setAttribute("class", "col-xs-6 d-flex img-frame");

	img = document.createElement("img");
	img.setAttribute("class", "card-img icon");
	img.setAttribute("onload", "correctImage(this)");
	img.setAttribute("src", restaurantData["imgsrc"]);

	dataDiv = document.createElement("div");
	dataDiv.setAttribute("class", "col-xs-4");

	cardBody = document.createElement("div");
	cardBody.setAttribute("class", "card-body");

	restaurantName = document.createElement("h5");
	restaurantName.setAttribute("class", "card-title");
	restaurantName.innerHTML = restaurantData["name"];

	restaurantCategory = document.createElement("p");
	restaurantCategory.setAttribute("class", "card-text");
	restaurantCategory.innerHTML = restaurantData["category"];

	anchor = document.createElement("a");
	anchor.setAttribute("class", "stretched-link");
	anchor.setAttribute("href", linkToRestaurant(restaurantData["owner"]));

	imgcol.append(img);

	cardBody.appendChild(restaurantName);
	cardBody.appendChild(restaurantCategory);


	dataDiv.appendChild(cardBody);
	row.appendChild(imgcol);
	row.appendChild(dataDiv);

	card.appendChild(row);
	card.appendChild(anchor);
	document.querySelector("#rest-container").appendChild(card);
}

function beautify(string) {
	let newString = string[0];
	for (let i = 1; i < string.length; i++) {
		newString += string[i].toLowerCase();
	}
	return newString;
}

function createCategoryFilter(categories) {
	//given categories set this function will create a list of radio button to filter the result by kitchen type
	let filterContainer = document.querySelector("div#filters-container");
	cleanFilters();
	categories.forEach(category => {
		console.log(category);
		let radio = document.createElement("input");
		radio.setAttribute("class", "form-check-input");
		radio.setAttribute("type", "checkbox");
		radio.setAttribute("value", beautify(category));
		radio.setAttribute("name", "filters[]");

		radio.setAttribute("onclick", "filter()");
		let div = document.createElement("div");
		div.setAttribute("id", "checkbox-filter-group")
		div.setAttribute("class", "form-check");
		let label = document.createElement("label");
		label.setAttribute("class", "form-check-label to-align");
		label.innerHTML = beautify(category);
		div.appendChild(radio);
		div.appendChild(label);
		filterContainer.appendChild(div);
	});
}

function filter(radio) {
	let selectedCheckboxes = document.querySelectorAll("input[name='filters[]']:checked");
	let restaurants = JSON.parse(sessionStorage.getItem("restaurants"));

	if (selectedCheckboxes.length > 0) {
		let filterValues = [];
		selectedCheckboxes.forEach(checkbox => filterValues.push(checkbox.value.toUpperCase()));
		console.log("from storage", restaurants);
		console.log("filterValues: ", filterValues);

		restaurants = restaurants.filter(restaurant => {
			return filterValues.includes(restaurant["category"].toUpperCase());
		});
		cleanRestaurants();
		console.log("from filter", restaurants);
		restaurants.forEach(restaurant => makeRestaurantCard(restaurant));
	} else {
		cleanRestaurants();
		restaurants.forEach(restaurant => makeRestaurantCard(restaurant));
	}
}

function cleanFilters() {
	let filters = document.querySelectorAll("#checkbox-filter-group");
	if (filters instanceof NodeList) {
		filters.forEach(filter => filter.remove());
		console.log("filters", filters);
	}
}
