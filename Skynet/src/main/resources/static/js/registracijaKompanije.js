function hotelRegistration() {
	var root;
	if (document.getElementById("hotel").checked == true) {
		root = "http://localhost:8080/addHotel";
	}
	else if(document.getElementById("rentacar").checked == true){
		root = "http://localhost:8080/addRentCar";
	}
	else if(document.getElementById("airline").checked == true){
		root = "http://localhost:8080/addAirline";
	}
	else{
		alert("Morate odabrati tip kompanije koja se dodaje.");
		return;
	}
	
    var name = document.getElementById("name").value;
    var adress = document.getElementById("adress").value;
    var desc = document.getElementById("description").value;

    $.ajax({
        url: root,
        type : 'POST',
        contentType : 'application/json',
		dataType : "json",
		data : textToJSON(name, adress, desc),
        success: function (data) {
            alert("Uspesno dodato!");
        },
        error: function (XMLHttpRequest) {

        }
    })
}

function textToJSON(name, adress, description) {
	return JSON.stringify({
		"name" : name,
		"adress" : adress,
		"description" : description,
	})
}

$(document).on("submit",".regForm", function(){
	hotelRegistration();
})

