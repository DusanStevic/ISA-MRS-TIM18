/**
 * 
 */
var TOKEN_KEY = 'jwtToken';

/*LOGOUT REGISTERED USERA*/
$(document).on('click', '#logout', function(e) {
	e.preventDefault();
	removeJwtToken(TOKEN_KEY);
	window.location.href = "index.html";
})

/*PRIKAZ PROFILA REGISTERED USERA*/
$(document).on('click', '#viewUserProfile_button', function(e){
	e.preventDefault();
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/api/viewUserProfile",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType: 'json',
		success : viewUserProfile,		
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})


function viewUserProfile(user){
	
	
		$('.main').empty();
		//$('.main').append('<img src = "'+ korisnik.slika + '" width= "409px" height= "318">');
		$('.main').append('<h1>' +"View user profile"+ '</h1>' );
		var tabela1 = $('<table></table>');
		tabela1.append('<tr><td> Name:</td><td>' +  user.name +'</td></tr>');
		tabela1.append('<tr><td> Surname:</td><td>' +  user.surname +'</td></tr>');
		tabela1.append('<tr><td> Username:</td><td>' +  user.username +'</td></tr>');
		tabela1.append('<tr><td> Email:</td><td>' +  user.email +'</td></tr>');
		
		$('.main').append(tabela1);
	
}

/*UPDATE PROFILA REGISTERED USERA*/
$(document).on('click', '#izmenaProfila_btn', function(e){
	e.preventDefault();
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/api/viewUserProfile",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType: 'json',
		success : prikazPodatakaZaIzmenu,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})

function prikazPodatakaZaIzmenu(data){
	var tabela = $('<table></table>');
	tabela.append('<tr><td> Ime:</td><td>' +  '<input type = "text" name = "name" value = "'+ data.name + '"</td></tr>');
	tabela.append('<tr><td> Prezime:</td><td>' +  '<input type = "text" name = "surname" value = "'+ data.surname + '"></td></tr>');
	
	//tabela.append('<tr><td> Korisnicko ime:</td><td>' +  '<input type = "text" name = "username" value = "'+ data.username + '"></td></tr>');
	tabela.append('<tr><td> Lozinka:</td><td>' +  '<input type = "password" name = "password" value = "'+ data.password + '"></td></tr>');
	
	tabela.append('<tr><td> Email:</td><td>' +  '<input type = "text" name = "email" value = "'+ data.email + '"></td></tr>');
	//tabela.append( '<tr><td> Nova slika:</td>' + '<td><input type="file" name = "slika" id = "slika" accept="image/*"> </td></tr>');
	tabela.append('<tr><td></td><td>' +  '<input type = "submit" value = "Posalji izmene" ></td></tr>');
	var forma = $('<form class = "posaljiIzmeneZaProfil"></form>');
	//forma.append('<input type = "hidden" value="' + data.id +'">');
	forma.append(tabela);
	$('.main').empty();
	//$('.main').append('<img src = "'+ data.slika + '" width= "411px" height= "321">');
	$('.main').append('<h1>Izmena profila</h1>')
	$('.main').append(forma)
	
	
	
	
	
}

$(document).on('submit', '.posaljiIzmeneZaProfil', function(e){
	e.preventDefault();
	//var id = $(this).find('input[type=hidden]').val(); 
	//var username = $(this).find("input[name = username]").val();
	var password = $(this).find("input[name = password]").val();
	var name = $(this).find("input[name = name]").val();
	var surname = $(this).find("input[name = surname]").val();
	var email = $(this).find("input[name = email]").val();
	//var file = $("#slika")[0].files[0];
	if ( password == "" || name == "" || surname == "" ||  email == "" ){
		alert("Morate popuniti sva polja!");
		return false;
	}	
	$.ajax({
		type : 'PUT',
		url : 'http://localhost:8080/api/updateUserProfile',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType: 'application/json',
		data: formToJSON_profilIZ(password,name,surname,  email),
		success : function(data){
			$('.main').empty();
			setJwtToken(TOKEN_KEY, data.accessToken);
        	$('.main').append('<p>Uspesno ste izmenili podatke.</p>');
			/*if (file == undefined){
				$('.main').empty();
	        	$('.main').append('<p>Uspesno ste izmenili podatke.</p>');
			}
			else{
				uploadImage(file);
			}	*/
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: NISAM ULETEO U UPDATE KORISNIKA" + errorThrown);
		}
	});	
})

function formToJSON_profilIZ(password,name,surname,  email) {
	return JSON.stringify({
		"password" : password,
		"name" : name,
		"surname" : surname,
		"email" : email
	});
}


/*PRETRAGA LETOVA*/
$(document).on('click', '#pretraga_btn', function(e){
	console.log("Kliknuo");
	e.preventDefault();
	alert("USAO JE U PRETRAGU: ");
	var tabela = $('<table></table>');
	tabela.append('<tr><td> Naziv avioprevoznika:</td><td>' +  '<input type = "text" name = "flightCompany" ></td></tr>');
	tabela.append('<tr><td> Pocetna destinacija:</td><td>' +  '<input type = "text" name = "startDestination" ></td></tr>');
	tabela.append('<tr><td> Krajnja destinacija:</td><td>' +  '<input type = "text" name = "endDestination" ></td></tr>');
	
	tabela.append('<tr><td> Datum poletanja:</td><td>' +  '<input type = "date" name = "startDate"> </td></tr>');
	tabela.append('<tr><td> Datum sletanja:</td><td>' +  '<input type = "date" name = "endDate"> </td></tr>');
	tabela.append('<tr><td> Duzina trajanja leta u minutima:</td><td>' +  '<input type = "text" name = "flightDuration" ></td></tr>');
	tabela.append('<tr><td> Duzina leta u kilometrima:</td><td>' +  '<input type = "text" name = "flightLength" ></td></tr>');
	tabela.append('<tr><td> Minimalna cena:</td><td>' +  '<input type = "text" name = "MinPrice" ></td></tr>');
	tabela.append('<tr><td> Maximalna cena:</td><td>' +  '<input type = "text" name = "MaxPrice" ></td></tr>');

	
	tabela.append('<tr><td> Klasa aviona:</td> <td><select name = "klasaAviona">'
			+ '<option>Ekonomska</option>'
			+ '<option>Biznis</option>'
			+ '<option>Prva</option>'
			+'</select></td></tr>');
	
	tabela.append('<tr><td></td><td>' +  '<input type = "submit" value = "Pretrazi" ></td></tr>');
	var forma = $('<form id = "pretragaForma"></form>');
	forma.append(tabela);
	$('.main').empty();
	$('.main').append('<h1>Pretraga</h1>')
	$('.main').append(forma)
	alert("IZSAO JE IZ PRETRAGE: ");
})

$(document).on('submit', '#pretragaForma', function(e){
	e.preventDefault();
	var minBusiness = 0;
	var minEconomic = 0;
	var minFirstClass = 0;
	var maxBusiness = 0;
	var maxEconomic = 0;
	var maxFirstClass = 0;
	
	 
	
	var flightCompany = $(this).find('input[name=flightCompany]').val();
	var startDestination = $(this).find('input[name=startDestination]').val();
	var endDestination = $(this).find('input[name=endDestination]').val();
	
	var startDate = $(this).find('input[name=startDate]').val();
	var endDate = $(this).find('input[name=endDate]').val();
	var flightDuration = $(this).find('input[name=flightDuration]').val();
	var flightLength = $(this).find('input[name=flightLength]').val();
	var MinPrice = $(this).find('input[name=MinPrice]').val();
	var MaxPrice = $(this).find('input[name=MaxPrice]').val();
	
	var klasaAviona = $(this).find("select[name = klasaAviona]").val();
	
	alert(klasaAviona);
	
	
	if (klasaAviona == "Ekonomska") {
		alert("izabrana je ekonomksa klasa");
		minEconomic = MinPrice;
		maxEconomic = MaxPrice;
		
	}
	else if (klasaAviona == "Biznis"){
		alert("izabrana je biznis klasa");
		minBusiness = MinPrice;
		maxBusiness = MaxPrice;
	}else{
		alert("izabrana je Prva klasa");
		minFirstClass = MinPrice;
		maxFirstClass = MaxPrice;
	}
	
	
	$.ajax({
		type : 'POST',
		url : "http://localhost:8080/api/flightSearch",
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType: 'application/json',
		dataType: 'json',
		data: formToJSON_pretraga(flightCompany,startDestination,endDestination,startDate,endDate,flightDuration,flightLength,minBusiness,minEconomic,minFirstClass,maxBusiness,maxEconomic,maxFirstClass),
		success : prikazLetova,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})


function prikazLetova(data){
	if (data == null){
		$('.main').empty();
		$('.main').append("Nema pronadjenih letova.");
		return;
	}
	$('.main').empty();
	var filter = $('<table></table>')
	filter.append('<tr><td> Filtriraj po:</td> <td><select name = "filter">'
			+ '<option>Datumu</option>'
			+ '<option>Klasi</option>'
			+ '<option>Broju leta</option>'
			+'</select></td></tr>');
	filter.append('<tr><td>' +  '<input type = "submit" value = "Primeni filter" ></td></tr>');
	var formaZaFilter = $('<form id = "filtriranje"></form>')
	formaZaFilter.append(filter);
	$('.main').append(formaZaFilter);
	$('.main').append('<br><br>')
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	var tabela = $('<table class = "mainTable"></table>')
	var tr_h = $('<tr></tr>');
	
		tr_h.append('<th>Pocetna destinacija</th>');
		tr_h.append('<th>Krajnja destinacija</th>');
		tr_h.append('<th>Cena</th>');
		tr_h.append('<th> Model aviona</th>');
		tr_h.append('<th> Datum Polaska</th>');
		tr_h.append('<th> Datum Dolaska</th>');
		tr_h.append('<th>Klasa Aviona</th>');
		tr_h.append('<th></th>');
	var t_head = $('<thead></thead>');
	t_head.append(tr_h);
	tabela.append(t_head);
	var t_body = $('<tbody></tbody>')
	$.each(list, function(index, let_){
		var tr = $('<tr></tr>');
		
		tr.append('<td>'+ let_.startDestination + '</td>');
		tr.append('<td>'+ let_.endDestination + '</td>');
		tr.append('<td>'+ let_.cenaKarte + '</td>');
		tr.append('<td>'+ let_.modelAviona + '</td>');
		tr.append('<td>'+ let_.startDate_str + '</td>');
		tr.append('<td>'+ let_.endDate_str + '</td>');
		tr.append('<td>'+ let_.klasaAviona + '</td>');
		var forma = $('<form id = "rezervacija"></form>')
		forma.append('<input type = "hidden" value="' + let_.brLeta +'">');
		forma.append('<input type = "submit" value = "Rezervisi">')
		var td = $('<td></td>');
		td.append(forma);
		tr.append(td);
		t_body.append(tr);
	});
	tabela.append(t_body);
	$('.main').append(tabela);
}

function formToJSON_pretraga(flightCompany,startDestination,endDestination,startDate,endDate,flightDuration,flightLength,minBusiness,minEconomic,minFirstClass,maxBusiness,maxEconomic,maxFirstClass){
	return JSON.stringify({
		"flightCompany" : flightCompany,
		"startDestination" : startDestination,
		"endDestination" : endDestination,
		"startDate" : startDate,
		"endDate" : endDate,
		"flightDuration": flightDuration,
		"flightLength": flightLength,
		
		"minBusiness" : minBusiness,
		"minEconomic" : minEconomic,
		"minFirstClass" : minFirstClass,
		"maxBusiness" : maxBusiness,
		"maxEconomic" : maxEconomic,
		"maxFirstClass" :maxFirstClass
		
	});
}

