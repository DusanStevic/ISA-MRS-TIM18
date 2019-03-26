function registrujHotel() {
    var naziv = document.getElementById("nazivHotela").value;
    var lokacija = document.getElementById("lokacijaHotela").value;
    var email = document.getElementById("email0").value;

    $.ajax({
        url: "http://localhost:8080/registrujHotel",
        contentType : 'application/json',
        dataType: "json",
        data: hotelToJSON(naziv, lokacija),
        success: function (data) {
            alert("Uspesno dodat hotel!");
        },
        error: function (XMLHttpRequest) {
            alert("Error");
        }
    })
}

function hotelToJSON(naziv, lokacija){
	return JSON.stringify({
		"naziv":naziv,
		"lokacija":lokacija
	})
}

$(document).on("submit",".regForm", function(){
	registrujHotel();
})

var i = 1;

function dodajNovog() {
    var tr = "<tr><td> <p><input type=\"text\" id=\"email\"" + i + "/></p></td><td><button class=\"x_button\" type=\"button\" id=\"x\"" + i + ">X</button></td</tr>";
    $("#tabela").append(tr);
    i++;
}