let menuHomeClient = document.querySelector("#navigation-container");
let innerlistMenu = menuHomeClient.querySelector("ul");
window.addEventListener("resize", () => setResponsiveness());

setResponsiveness();

function setResponsiveness() {
	if (window.innerWidth < 768) {
		if (!innerlistMenu.classList.value.includes("list-group-horizontal")) {
			innerlistMenu.classList.value += " list-group-horizontal";
		}
	}
	else {
		if (innerlistMenu.classList.value.includes("list-group-horizontal")) {
			innerlistMenu.classList.value = innerlistMenu.classList.value.replace(" list-group-horizontal", "");
		}
	}


}

