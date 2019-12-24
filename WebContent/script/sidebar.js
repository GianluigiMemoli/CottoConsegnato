	const sidebar = $("#col-collapsable"); 
	const col = $("#collapsed-on-sm");
	function hideMenu() {
		if (document.documentElement.clientWidth < 768) { 
			col.removeClass("d-none d-md-block"); 
			if(sidebar.hasClass("show-menu"))
				sidebar.removeClass("show-menu"); 
			sidebar.addClass("hide-menu"); 
			}
		}
	
	function showMenu() { 
		if (document.documentElement.clientWidth < 768) {
			sidebar.removeClass("hide-menu"); 
			sidebar.addClass("show-menu");
			sidebar.on("transitionend", () => {
				col.addClass("d-none d-md-block");
				sidebar.unbind();
				});
			}
		}
	