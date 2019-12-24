
function getImgDimensions(img, divHeight, divWidth){
	//forcing style
	divHeight = divWidth = 100;
	let originalHeight = img.naturalHeight; 
	let originalWidth = img.naturalWidth;
	console.log(`original width:${originalWidth} original height: ${originalHeight}`);

	let ratio; 	
	
	if(originalWidth > originalHeight)
		ratio = Math.round(originalWidth / originalHeight);
	else 
		ratio = Math.round(originalHeight / originalWidth);
	console.log(`ratio: ${ratio}`);

	let width = ratio * divHeight; 	
	console.log(`new width ${width}`);

	let left;
	if(originalHeight != originalWidth)
		left = -1 * ((width - divWidth) / 2);
	else left = 0;

	console.log(`left: ${left}`);
	

	return {width: width, left: left}; 
}

function correctImage(img){	
	let div = img.parentNode;
	console.log("parent div");
	console.log(div); 
	console.log(`div width: ${div.offsetWidth}, div height: ${div.offsetHeight}`);
	let adjustaments = getImgDimensions(img, div.offsetWidth, div.offsetHeight);
	console.log(adjustaments); 
	img.style.width = adjustaments.width + 'px';
	img.style.left = adjustaments.left + 'px';
}

function run(){
	let imgs = document.querySelectorAll("img");
	imgs.forEach(img => {
		correctImage(img);
	});
}

run();