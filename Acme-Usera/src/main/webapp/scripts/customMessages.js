var PhoneInput = document.getElementById('phone');
if(PhoneInput != null){
	PhoneInput.oninvalid = function(event) {
		event.target.setCustomValidity(message);
		$("#phone").val('');
		setTimeout(function(){
			event.target.setCustomValidity('');
		}, 700);
	};
}
var brandInput = document.getElementById('brand');
if(brandInput != null){
	brandInput.oninvalid = function(event) {
		event.target.setCustomValidity(message);
		$("#brand").val('');
		setTimeout(function(){
			event.target.setCustomValidity('');
		}, 700);
	};
}	





