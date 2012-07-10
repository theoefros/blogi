String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
}

/**
 * Küsib kasutaja kinnitust postituse kustutamise kohta.
 * @returns {Boolean}
 */
function kinnitaKustutamine() {
	var kinnita = confirm("Oled sa kindel, et soovid postitust kustutada");
	if (kinnita)
		return true;
	else
		return false;
}

/**
 * Kontrollib, et postituse pealkiri ei oleks tühi.
 * @returns {Boolean}
 */
function kontrolliPealkirja() {
	var pealkiri = document.getElementById("pealkiri");
	
	if(pealkiri.value.trim() == ""){
		alert("Pealkiri ei tohi tühi olla!");
		return false;
	}
	
	return true;
	
}
