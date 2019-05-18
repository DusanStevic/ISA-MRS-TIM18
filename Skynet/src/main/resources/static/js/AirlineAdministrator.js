/**
 * 
 */
var TOKEN_KEY = 'jwtToken';

$(document).on('click', '#logout', function(e) {
	e.preventDefault();
	removeJwtToken(TOKEN_KEY);
	window.location.href = "index.html";
})

$(document)
		.on(
				"submit",
				"#form4",
				function(e) {
					e.preventDefault();
					console.log('ULETEO SAM U JS');
					var username = document
							.getElementById("airlineAdminUsernameEdit").value;
					var password1 = document
							.getElementById("airlineAdminPassword1Edit").value;
					var password2 = document
							.getElementById("airlineAdminPassword2Edit").value;
					var firstName = document
							.getElementById("airlineAdminFirstNameEdit").value;
					var lastName = document
							.getElementById("airlineAdminLastNameEdit").value;
					var email = document
							.getElementById("airlineAdminEmailEdit").value;
					
					if (username == "" || password1 == "" || password2 == ""
							|| firstName == "" || lastName == "" || email == ""
						) {
						alert('At least one field is blank, please fill it up with proper information!');
					} else if (password1 != password2) {
						alert("Password must match, try again!");
					} else {
						$
								.ajax({
									type : 'PUT',
									url : "http://localhost:8080/api/editUser",
									headers : createAuthorizationTokenHeader(TOKEN_KEY),
									contentType : 'application/json',
									dataType : "json",
									data : airlineAdminEditToJSON(username,
											password1, firstName, lastName,
											email),
									success : function(data) {
										console.log('ULETEO SAM U SUCCESS');
										if (data) {
											setJwtToken(TOKEN_KEY,
													data.accessToken);
											alert("Successful editing, congratulations!");
										} else {
											alert("Error while editing!");
										}

									},
									error : function(jqXHR, textStatus,
											errorThrown) {
										alert(jqXHR.status);
										alert(textStatus);
										alert(errorThrown);

									}
								})
					}
				});

$(document).on('click', '#profil_btn', function(e){
	e.preventDefault();
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/api/getAirlineAdmin",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType: 'json',
		success : prikazProfila,		
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})


function prikazProfila(korisnik){
	
	
		$('.main').empty();
		//$('.main').append('<img src = "'+ korisnik.slika + '" width= "409px" height= "318">');
		$('.main').append('<h1>' + korisnik.name + " " + korisnik.surname + '</h1>' );
		var tabela1 = $('<table></table>');
		tabela1.append('<tr><td> Korisnicko ime:</td><td>' +  korisnik.username +'</td></tr>');
		//tabela1.append('<tr><td> Telefon:</td><td>' +  korisnik.telefon +'</td></tr>');
		tabela1.append('<tr><td> Email:</td><td>' +  korisnik.email +'</td></tr>');
		//tabela1.append('<tr><td> Stanje:</td><td>' +  korisnik.stanje +'</td></tr>');
		$('.main').append(tabela1);
	
}

/*DODAVANJE NOVE DESTINACJE*/
$(document).on('click', '#dodajDest_btn', function(e){
	e.preventDefault();
	var tabela = $('<table></table>');
	tabela.append('<tr><td> Naziv destinacije:</td><td>' +  '<input type = "text" name = "name" ></td></tr>');
	tabela.append('<tr><td> Koordinate destinacije:</td><td>' +  '<input type = "text" name = "coordinates" ></td></tr>');
	tabela.append('<tr><td> Opis destinacije:</td><td>' +  '<input type = "text" name = "description" ></td></tr>');
	
	
	/*tabela.append('<tr><td> Naziv destinacije:</td><td>' +  '<input type = "text" name = "nazivDestinacije" ></td></tr>');
	tabela.append('<tr><td> Drzava:</td><td>' +  '<input type = "text" name = "drzava"></td></tr>');
	tabela.append('<tr><td> Naziv aerodorma:</td><td>' +  '<input type = "text" name = "nazivAerodroma" ></td></tr>');
	tabela.append('<tr><td> Kod aerodroma:</td><td>' +  '<input type = "text" name = "kodAerodroma"> </td></tr>');
	tabela.append('<tr><td> Lokacija:</td><td>' +  '<input type = "text" name = "lokacija"> </td></tr>');
	tabela.append('<tr><td> Stanje destinacije:</td> <td><select name = "stanjeDestinacije">'
			+ '<option>Aktivna</option>'
			+ '<option>Arhivirana</option>'
			+'</select></td></tr>');
	tabela.append( '<tr><td> Slika destinacije:</td>' + '<td><input type="file" name = "slika" id = "slika" accept="image/*"> </td></tr>');*/
	tabela.append('<tr><td></td><td>' +  '<input type = "submit" value = "Posalji" ></td></tr>');
	var forma = $('<form id = "dodajDestForma" enctype="multipart/form-data"></form>');
	forma.append(tabela);
	$('.main').empty();
	$('.main').append('<h1>Nova destinacija</h1>')
	$('.main').append(forma)
})

$(document).on('submit', '#dodajDestForma', function(e){
	e.preventDefault();
	var name = $(this).find("input[name = name]").val();
	var coordinates = $(this).find("input[name = coordinates]").val();
	var description = $(this).find("input[name = description]").val();
	
	/*var nazivDestinacije = $(this).find("input[name = nazivDestinacije]").val();
	var drzava = $(this).find("input[name = drzava]").val();
	var nazivAerodroma = $(this).find("input[name = nazivAerodroma]").val();
	var kodAerodoma = $(this).find("input[name = kodAerodroma]").val();
	var lokacija = $(this).find("input[name = lokacija]").val();
	var stanjeDestinacije = $(this).find("select[name = stanjeDestinacije]").val();
	var file = $("#slika")[0].files[0];
	if (nazivDestinacije == "" || drzava == "" || nazivAerodroma == "" || kodAerodoma == "" || lokacija == ""){
		alert("Morate popuniti sva polja!");
		return false;
	}*/
	
	
	if (name == "" || coordinates == "" || description == "" ){
		alert("Morate popuniti sva polja!");
		return false;
	}
	$.ajax({
		type : 'POST',
		url : "http://localhost:8080/api/addDestination",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		//url : '../Projekat/rest/admini/dodajDestinaciju',
		contentType: 'application/json',
		dataType : 'json',
		data: formToJSON_dest(name, coordinates,  description),
		success : function(data){
			alert("Successfully added destination.");
			/*if (data != null){
				if (file == undefined){
					$('.main').empty();
		        	$('.main').append('<p>Uspesno ste dodali novu destinaciju.</p>');
				}
				else{
					uploadImageDest(file);
				}
				
			}
			else{
				alert("destinacija sa tim nazivom vec postoji!");
			}*/
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("ULETEO SAM U AJAX ERROR: " + errorThrown);
		}
	});	
	
})

function formToJSON_dest(name, coordinates,  description){
	return JSON.stringify({
		"name" : name,
		"coordinates" : coordinates,
		"description" : description
	});
}

/*PRIKAZ DESTINACIJA*/
$(document).on('click', '#destinacije_btn', function(e){
	e.preventDefault();
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/api/getDestinations",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType: 'json',
		success : prikazDestinacija,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})

function prikazDestinacija(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	var tabela = $('<table class = "mainTable"></table>')
	/*zagalvlje tabele*/
	var tr_h = $('<tr></tr>');
		
		tr_h.append('<th>Naziv destinacije</th>');
		tr_h.append('<th>Opis destinacije</th>');
		tr_h.append('<th>Koordinate destinacije</th>');
		tr_h.append('<th></th>');
		
		/*tr_h.append('<th>Naziv destinacije</th>');
		tr_h.append('<th>Drzava</th>');
		tr_h.append('<th>Naziv aerodroma</th>');
		tr_h.append('<th>Kod</th>');
		tr_h.append('<th>Lokacija</th>');
		tr_h.append('<th>Stanje destinacije</th>');
		tr_h.append('<th></th>');
		tr_h.append('<th></th>');*/
	var t_head = $('<thead></thead>');
	t_head.append(tr_h);
	tabela.append(t_head);
	/*telo tabele*/
	var t_body = $('<tbody></tbody>')
	$.each(list, function(index, destinacija){
		var tr = $('<tr></tr>');
		tr.append('<td>'+ destinacija.name + '</td>');
		tr.append('<td>'+ destinacija.description + '</td>');
		tr.append('<td>'+ destinacija.coordinates + '</td>');
		
		/*tr.append('<td>'+ '<img src= "' + destinacija.slikaDestinacije + '" alt = "nisam nasao" width= "261px" height= "121">' + '</td>');
		tr.append('<td>'+ destinacija.nazivDestinacije + '</td>');
		tr.append('<td>'+ destinacija.drzava + '</td>');
		tr.append('<td>'+ destinacija.nazivAerodroma + '</td>');
		tr.append('<td>'+ destinacija.kodAerodroma + '</td>');
		tr.append('<td>'+ destinacija.lokacija + '</td>');
		tr.append('<td id = "stanje_' + destinacija.nazivDestinacije + '">'+ destinacija.stanjeDestinacije + '</td>');
		var forma = $('<form class = "arhiviranje"></form>')
		forma.append('<input type = "hidden" value="' + destinacija.nazivDestinacije +'">');
		if (destinacija.stanjeDestinacije == "ARHIVIRANA"){
			
			forma.append('<input type = "submit" id = "arhiviranje_' + destinacija.nazivDestinacije +'" value = "Aktiviraj">')
		}
		else{
			forma.append('<input type = "submit" id = "arhiviranje_' + destinacija.nazivDestinacije +'" value = "Arhiviraj">')
		}*/
		var forma2 = $('<form class = "izmenaDestinacije"></form>')
		forma2.append('<input type = "hidden" value="' + destinacija.id +'">');
		forma2.append('<input type = "submit" value = "Izmena">')
		//var td = $('<td></td>');
		var td2 = $('<td></td>');
		//td.append(forma);
		td2.append(forma2);
		//tr.append(td);
		tr.append(td2);
		t_body.append(tr);
	});
	tabela.append(t_body);
	$('.main').empty();
	$('.main').append(tabela);
}
/*IZMENA DESTINACIJA*/
$(document).on('submit', '.izmenaDestinacije', function(e){	
	e.preventDefault();
	var id = $(this).find('input[type=hidden]').val(); 
	//var adresa = '../Projekat/rest/admini/pronadjiDestinaciju/' + nazivDest;
	var adresa = "http://localhost:8080/api/getDestination/" + id;
	
	$.ajax({
		type : 'GET',
		url : adresa,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        dataType: 'json',
        success: prikazDestinacijeZaIzmenu,
        error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: NIJE ULETEO U PRIKAZ JEDNE  DESTINACIJE" + errorThrown);
		}
	});
})

function prikazDestinacijeZaIzmenu(data){
	var tabela = $('<table></table>');
	tabela.append('<tr><td> Naziv destinacije:</td><td>' +  '<input type = "text" name = "name" value = "'+ data.name + '"></td></tr>');
	tabela.append('<tr><td> Opis destinacije:</td><td>' +  '<input type = "text" name = "description" value = "'+ data.description + '"</td></tr>');
	tabela.append('<tr><td> Koordinate destinacije:</td><td>' +  '<input type = "text" name = "coordinates" value = "'+ data.coordinates + '"></td></tr>');
	//tabela.append('<tr><td> Kod aerodroma:</td><td>' +  '<input type = "text" name = "kodAerodroma" value = "'+ data.kodAerodroma + '"></td></tr>');
	//tabela.append('<tr><td> Lokacija:</td><td>' +  '<input type = "text" name = "lokacija" value = "'+ data.lokacija + '"></td></tr>');
	//tabela.append( '<tr><td> Nova slika:</td>' + '<td><input type="file" name = "slika" id = "slika" accept="image/*"> </td></tr>');
	tabela.append('<tr><td></td><td>' +  '<input type = "submit" value = "Posalji izmene" ></td></tr>');
	var forma = $('<form class = "posaljiIzmeneZaDestinaciju"></form>');
	forma.append('<input type = "hidden" value="' + data.id +'">');
	forma.append(tabela);
	$('.main').empty();
	//$('.main').append('<img src = "'+ data.slikaDestinacije + '"width= "461px" height= "321">');
	$('.main').append('<h1>Izmena destinacije</h1>')
	$('.main').append(forma)
}

$(document).on('submit', '.posaljiIzmeneZaDestinaciju', function(e){
	e.preventDefault();
	var id = $(this).find('input[type=hidden]').val(); 
	var name = $(this).find("input[name = name]").val();
	var description = $(this).find("input[name = description]").val();
	var coordinates = $(this).find("input[name = coordinates]").val();
	//var kodAerodoma = $(this).find("input[name = kodAerodroma]").val();
	//var lokacija = $(this).find("input[name = lokacija]").val();
	//var file = $("#slika")[0].files[0];
	if (name == "" || description == ""  || coordinates == "" ){
		alert("Nijedno polje ne sme ostati prazno!");
		return false;
	}		
	$.ajax({
		type : 'PUT',
		//url : '../Projekat/rest/admini/izmeniDestinaciju',
		url : "http://localhost:8080/api/updateDestination",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType: 'application/json',
		dataType : 'json',
		data: formToJSON_destIZ(id,name, description,  coordinates),
		success : function(data){
			if (data != null){
				$('.main').empty();
				$('.main').append('<p>Uspesno ste izmenili destinaciju.</p>');
				/*if (file == undefined){
					$('.main').empty();
		        	$('.main').append('<p>Uspesno ste izmenili destinaciju.</p>');
				}
				else{
					changeImageDest(file);
				}	*/
			}
			else{
				alert("Destinacija sa tim nazivom vec postoji!");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})

function formToJSON_destIZ(id,name, description,  coordinates){
	return JSON.stringify({
		"id" : id,
		"name" : name,
		"description" : description,
		"coordinates" : coordinates
	});
}


/*DODAVANJE NOVOG LETA*/
$(document).on('click', '#dodajLet_btn', function(e){
	e.preventDefault();
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/api/getDestinations",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType: 'json',
		success : prikazFormeZaNoviLet,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})


function prikazFormeZaNoviLet(data){

	var startDestinationSelect = $('<select name = "startDestination"></select>');
	var endDestinationSelect = $('<select name = "endDestination"></select>');
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, destinacija){
		startDestinationSelect.append('<option>'+ destinacija.name + '</option>');
		endDestinationSelect.append('<option>'+ destinacija.name + '</option>');
		/*if (destinacija.stanjeDestinacije == "AKTIVNA"){
			pocetnaDestinacijaSelect.append('<option>'+ destinacija.name + '</option>');
			krajnjaDestinacijaSelect.append('<option>'+ destinacija.name + '</option>');
		}*/
	});
	var td1 = $('<td></td>');
	td1.append(startDestinationSelect);
	var tr1 = $('<tr></tr>');
	tr1.append('<td> Pocetna destinacija </td>');
	tr1.append(td1);
	var td2 = $('<td></td>');
	td2.append(endDestinationSelect);
	var tr2 = $('<tr></tr>');
	tr2.append('<td> Krajnja destinacija </td>');
	tr2.append(td2);
	var tabela = $('<table></table>');
	tabela.append(tr1);
	tabela.append(tr2);
	tabela.append('<tr><td> Datum poletanja:</td> <td><input type = "date" name = "startDate" ></td></tr>');
	tabela.append('<tr><td> Vreme poletanja:</td> <td><input type = "time" name = "startDate_time" ></td></tr>');
	tabela.append('<tr><td> Datum sletanja:</td> <td><input type = "date" name = "endDate" ></td></tr>');
	tabela.append('<tr><td> Vreme sletanja:</td> <td><input type = "time" name = "endDate_time" ></td></tr>');
	tabela.append('<tr><td> Trajanje leta u minutima:</td> <td><input type = "text" name = "flightDuration"> </td></tr>');
	tabela.append('<tr><td> Duzina leta u kilometrima:</td> <td><input type = "text" name = "flightLength"> </td></tr>');
	tabela.append('<tr><td> Cena karte u eknomskoj klasi:</td> <td><input type = "text" name = "economicPrice" ></td></tr>');
	tabela.append('<tr><td> Cena karte u biznis klasi:</td> <td><input type = "text" name = "businessPrice" ></td></tr>');
	tabela.append('<tr><td> Cena karte u prvoj klasi:</td> <td><input type = "text" name = "firstClassPrice" ></td></tr>');
	tabela.append('<tr><td> Broj mesta:</td> <td><input type = "text" name = "seats" ></td></tr>');
	
	
	
	/*tabela.append('<tr><td> Broj redova u ekonomskoj klasi:</td> <td><input type = "text" name = "economicCapacity_rows" ></td></tr>');
	tabela.append('<tr><td> Broj kolona u ekonomskoj klasi:</td> <td><input type = "text" name = "economicCapacity_columns" ></td></tr>');
	tabela.append('<tr><td> Broj redova u biznis klasi:</td> <td><input type = "text" name = "buisinesssCapacity_rows"> </td></tr>');
	tabela.append('<tr><td> Broj kolona u biznis klasi:</td> <td><input type = "text" name = "buisinesssCapacity_columns"> </td></tr>');
	tabela.append('<tr><td> Broj redova u prvoj klasi:</td> <td><input type = "text" name = "firstClassCapacity_rows" ></td></tr>');
	tabela.append('<tr><td> Broj kolona u prvoj klasi:</td> <td><input type = "text" name = "firstClassCapacity_columns" ></td></tr>');*/
	
	/*tabela.append('<tr><td> Klasa aviona:</td> <td><select name = "klasaAviona">'
			+ '<option>Regionalni</option>'
			+ '<option>Prekookeanski</option>'
			+ '<option>Carter</option>'
			+'</select></td></tr>');*/
	tabela.append('<tr><td></td><td>' +  '<input type = "submit" value = "Posalji" ></td></tr>');
	var forma = $('<form class = "dodajNoviLet"></form>');
	forma.append(tabela);
	$('.main').empty();
	$('.main').append('<h1>Novi Let</h1>')
	$('.main').append(forma)

}

$(document).on('submit', '.dodajNoviLet', function(e){
	
	e.preventDefault();
	var startDestination = $(this).find("select[name = startDestination]").val();
	var endDestination = $(this).find("select[name = endDestination]").val();
	var economicPrice = $(this).find("input[name = economicPrice]").val();
	var businessPrice = $(this).find("input[name = businessPrice]").val();
	var firstClassPrice = $(this).find("input[name = firstClassPrice]").val();
	var flightDuration = $(this).find("input[name = flightDuration]").val();
	var flightLength = $(this).find("input[name = flightLength]").val();
	var seats = $(this).find("input[name = seats]").val();
	
	/*var firstClassCapacity_rows = $(this).find("input[name = firstClassCapacity_rows]").val();
	var firstClassCapacity_columns = $(this).find("input[name = firstClassCapacity_columns]").val();
	var buisinesssCapacity_rows = $(this).find("input[name = buisinesssCapacity_rows]").val();
	var buisinesssCapacity_columns = $(this).find("input[name = buisinesssCapacity_columns]").val();
	var economicCapacity_rows = $(this).find("input[name = economicCapacity_rows]").val();
	var economicCapacity_columns = $(this).find("input[name = economicCapacity_columns]").val();*/
	
	var startDate = $(this).find("input[name = startDate]").val();
	var startDate_time = $(this).find("input[name = startDate_time]").val();
	var endDate = $(this).find("input[name = endDate]").val();
	var endDate_time = $(this).find("input[name = endDate_time]").val();
	//var klasaAviona = $(this).find("select[name = klasaAviona]").val();
	/*if (economicPrice == "" || businessPrice == "" || flightDuration == "" || flightLength == "" || economicCapacity_rows == "" || economicCapacity_columns == ""|| buisinesssCapacity_rows == ""||  buisinesssCapacity_columns == ""||  firstClassCapacity_rows == ""||  firstClassCapacity_columns == "" || startDate == "" || startDate_time == "" || endDate == "" || endDate_time == ""){
		alert("Morate popuniti sva polja!");
		return false;
	}*/
	
	if (economicPrice == "" || businessPrice == "" || flightDuration == "" || flightLength == "" || seats == ""  || startDate == "" || startDate_time == "" || endDate == "" || endDate_time == ""){
		alert("Morate popuniti sva polja!");
		return false;
	}
	/*if (parseInt(firstClassCapacity_columns) <= 0||parseInt(firstClassCapacity_rows) <= 0||parseInt(buisinesssCapacity_columns) <= 0||parseInt(buisinesssCapacity_rows) <= 0||parseInt(economicCapacity_rows) <= 0 || parseInt(economicCapacity_columns) <= 0 ){
		alert("Morate uneti pozitivan broj veci od nule!");
		return false;
	}*/
	var startDate_str = startDate + " " + startDate_time;
	var endDate_str = endDate + " " + endDate_time;
	/*var economicCapacity = economicCapacity_rows + "|" + economicCapacity_columns;
    var buisinesssCapacity = buisinesssCapacity_rows + "|" + buisinesssCapacity_columns;
    var firstClassCapacity = firstClassCapacity_rows + "|" + firstClassCapacity_columns;*/
	
	alert(startDate_str);
	alert(endDate_str);
	/*alert(economicCapacity);
	alert(buisinesssCapacity);
	alert(firstClassCapacity);*/
	
	$.ajax({
		type : 'POST',
		url : "http://localhost:8080/api/addFlight",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType: 'application/json',
		dataType : 'text',
		//data: formToJSON_let(startDestination, endDestination,  economicPrice, businessPrice,firstClassPrice, flightDuration, flightLength, economicCapacity,  buisinesssCapacity, firstClassCapacity,startDate_str,endDate_str),
		data: formToJSON_let(startDestination, endDestination,  economicPrice, businessPrice,firstClassPrice, flightDuration, flightLength,seats,startDate_str,endDate_str),
		success : function(data){
			$('.main').empty();
        	$('.main').append('<p>Uspesno ste dodali novi let.</p>');
			if (data ==  "uspesno"){
				$('.main').empty();
	        	$('.main').append('<p>Uspesno ste dodali novi let.</p>');
			}
			else{
				alert(data);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
	
})

/*function formToJSON_let(startDestination, endDestination,  economicPrice,businessPrice,firstClassPrice,  flightDuration,flightLength,  economicCapacity,  buisinesssCapacity, firstClassCapacity,startDate_str,endDate_str){
	return JSON.stringify({
		"startDestination" : startDestination,
		"endDestination" : endDestination,
		"economicPrice" : economicPrice,
		"businessPrice" : businessPrice,
		"firstClassPrice" : firstClassPrice,
		"flightDuration" : flightDuration,
		"flightLength" : flightLength,
		"economicCapacity" : economicCapacity,
		"buisinesssCapacity" : buisinesssCapacity,
		"firstClassCapacity" : firstClassCapacity,
		"startDate_str" : startDate_str,
		"endDate_str" : endDate_str
	});
}*/

function formToJSON_let(startDestination, endDestination,  economicPrice,businessPrice,firstClassPrice,  flightDuration,flightLength,seats,startDate_str,endDate_str){
	return JSON.stringify({
		"startDestination" : startDestination,
		"endDestination" : endDestination,
		"economicPrice" : economicPrice,
		"businessPrice" : businessPrice,
		"firstClassPrice" : firstClassPrice,
		"flightDuration" : flightDuration,
		"flightLength" : flightLength,
		"seats" : seats,
		"startDate_str" : startDate_str,
		"endDate_str" : endDate_str
	});
}


/*function formToJSON_dest(nazivDestinacije, drzava,  nazivAerodroma,  kodAerodoma,  lokacija,  stanjeDestinacije){
	return JSON.stringify({
		"nazivDestinacije" : nazivDestinacije,
		"drzava" : drzava,
		"nazivAerodroma" : nazivAerodroma,
		"kodAerodroma" : kodAerodoma,
		"lokacija" : lokacija,
		"stanjeDestinacije" : stanjeDestinacije.toUpperCase()
	});
}*/








function airlineAdminEditToJSON(username, password1, firstName, lastName,
		email) {
	return JSON.stringify({
		"username" : username,
		"password" : password1,
		"firstName" : firstName,
		"lastName" : lastName,
		"email" : email,
		
	})
}