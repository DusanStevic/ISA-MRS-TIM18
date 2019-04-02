function registrujAdmina(){
	var root;
	if (document.getElementById("hotel").checked == true) {
		root = "http://localhost:8080/addHotelAdmin";
	}
	else if(document.getElementById("rentacar").checked == true){
		root = "http://localhost:8080/addRACAdmin";
	}
	else if(document.getElementById("airline").checked == true){
		root = "http://localhost:8080/addAirlineAdmin";
	}
	else{
		alert("Morate odabrati tip kompanije koja se dodaje.");
		return;
	}
	
	var ime = document.getElementById("name").value;
    var prezime = document.getElementById("surname").value;
    var lozinka = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    var kime = document.getElementById("username").value;
    var kompanija = document.getElementById("companyName").value;
    
    $.ajax({
        url: root,
        type : 'POST',
        contentType : 'application/json',
		dataType : "json",
		data : unosToJSON(ime, prezime, kime, lozinka, email, kompanija),
        success: function (data) {
            alert("Uspesno dodato!");
        },
        error: function (XMLHttpRequest) {

        }
    })
    
}

function unosToJSON(ime, prezime, korisnickoIme, lozinka, email, kompanija) {
	return JSON.stringify({
		"name" : ime,
		"surname" : prezime,
		"username" : korisnickoIme,
		"password" : lozinka,
		"email" : email,
		"company" : kompanija,
	})
}

$(document).on("submit",".admin", function(){
	registrujAdmina();
})