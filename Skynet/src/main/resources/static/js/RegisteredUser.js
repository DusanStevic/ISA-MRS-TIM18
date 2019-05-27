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
		forma.append('<input type = "hidden" value="' + let_.id +'">');
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


/*DODAVANJE NOVE REZERVACIJE*/

/*$(document).on('submit', '#filtriranje', function(e){
	e.preventDefault();
	var filter = $(this).find('select[name=filter]').val();
	var adresa = '../Projekat/rest/letovi/filtriranje/' + filter;
	alert(filter);
	$.ajax({
		type : 'GET',
		url : adresa,
		dataType: 'json',
		success : prikazLetova,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})*/


$(document).on('submit', '#rezervacija', function(e){	
	e.preventDefault();
	var brLeta = $(this).find('input[type=hidden]').val();
	alert("OVO JE BROJ LETA KOJI HOCEMO DA REZERVISEMO: " + brLeta);
	//var adresa = '../Projekat/rest/letovi/pronadjiLet/' + brLeta;
	var adresa = "http://localhost:8080/api/getFlight/" + brLeta;
	
	
	
	$.ajax({
        type : 'GET',
        url : adresa,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        dataType: 'json',
        success: prikazLetaZaRezervaciju,
        error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
})



var firstSeatLabel = 1;
function prikazLetaZaRezervaciju(data){
	var tabela = $('<table></table>');
	//tabela.append('<tr><td> Broj leta:</td><td>'+ data.brLeta + '</td></tr>');
	
	tabela.append('<tr><td> Pocetna destinacija:</td><td>'+ data.startDestination + '</td></tr>');
	tabela.append('<tr><td> Krajnja destinacija:</td><td>'+ data.endDestination + '</td></tr>');
	/*tabela.append('<tr><td> Cena:</td><td>'+ data.cenaKarte + '</td></tr>');
	tabela.append('<tr><td> Datum:</td><td>'+ data.datumLeta + '</td></tr>');
	tabela.append('<tr><td> Model aviona:</td><td>'+ data.modelAviona + '</td></tr>');
	tabela.append('<tr><td> Klasa aviona:</td><td>'+ data.klasaAviona + '</td></tr>');*/
	tabela.append('<tr><td> Broj putnika:</td><td>' +  '<input type = "number" name = "brPutnika"> </td></tr>');
	tabela.append('<tr><td> Klasa leta:</td> <td><select name = "klasa">'
			+ '<option>Prva</option>'
			+ '<option>Biznis</option>'
			+ '<option>Ekonomska</option>'
			+'</select></td></tr>');
	tabela.append('<tr><td></td><td>' +  '<input type = "submit" value = "Posalji" ></td></tr>');
	var forma = $('<form id = "posaljiPodatkeZaRezervaciju"></form>');
	forma.append(tabela);
	$('.main').empty();
	$('.main').append('<h1>Rezervacija leta:</h1>')
	$('.main').append(forma)
	$('.main').append('<div class="container">'+
			'<h3 id="relacija-leta"></h3>'+
			'<div id="seat-map">'+
				'<div class="front-indicator">Front</div>'+

			'</div>'+
			'<div class="booking-details">'+
				'<h2>Booking Details</h2>'+

				'<h3>'+
					'Selected Seats (<span id="counter">0</span>):'+
				'</h3>'+
				'<ul id="selected-seats"></ul>'+

				'Total: <b>$<span id="total">0</span></b>'+

				'<button class="checkout-button"'+
					'onclick="pokupiRezervisanaSedista()">Checkout &raquo;</button>'+
				'<button class=\'next-button\' onclick="$(\'#hotels-tab\').click()">Next &raquo;</button>'+
				'<br><br><br>'+
				'<div id="legend"></div>'+
			'</div>'+
		'</div>');
	 var $cart = $('#selected-seats'),
     $counter = $('#counter'),
     $total = $('#total'),
     sc = $('#seat-map').seatCharts({
     map: [
       'ff_ff',
       'ff_ff',
       'ee_ee',
       'ee_ee',
       'ee___',
       'ee_ee',
       'ee_ee',
       'ee_ee',
       'eeeee',
     ],
     seats: {
       f: {
         price   : 100,
         classes : 'first-class', //your custom CSS class
         category: 'First Class'
       },
       e: {
         price   : 40,
         classes : 'economy-class', //your custom CSS class
         category: 'Economy Class'
       }         
     
     },
     naming : {
       top : false,
       getLabel : function (character, row, column) {
         return firstSeatLabel++;
       },
     },
     legend : {
       node : $('#legend'),
         items : [
         [ 'f', 'available',   'First Class' ],
         [ 'e', 'available',   'Economy Class'],
         [ 'f', 'unavailable', 'Already Booked']
         ]         
     },
     click: function () {
       if (this.status() == 'available') {
         //let's create a new <li> which we'll add to the cart items
         $('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
           .attr('id', 'cart-item-'+this.settings.id)
           .data('seatId', this.settings.id)
           .appendTo($cart);
         
         /*
          * Lets up<a href="https://www.jqueryscript.net/time-clock/">date</a> the counter and total
          *
          * .find function will not find the current seat, because it will change its stauts only after return
          * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
          */
         $counter.text(sc.find('selected').length+1);
         $total.text(recalculateTotal(sc)+this.data().price);
         
         return 'selected';
       } else if (this.status() == 'selected') {
         //update the counter
         $counter.text(sc.find('selected').length-1);
         //and total
         $total.text(recalculateTotal(sc)-this.data().price);
       
         //remove the item from our cart
         $('#cart-item-'+this.settings.id).remove();
       
         //seat has been vacated
         return 'available';
       } else if (this.status() == 'unavailable') {
         //seat has been already booked
         return 'unavailable';
       } else {
         return this.style();
       }
     }
   });

   //this will handle "[cancel]" link clicks
   $('#selected-seats').on('click', '.cancel-cart-item', function () {
     //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
     sc.get($(this).parents('li:first').data('seatId')).click();
   });

   //let's pretend some seats have already been booked
   sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');

};

function recalculateTotal(sc) {
 var total = 0;

 //basically find every selected seat and sum its price
 sc.find('selected').each(function () {
   total += this.data().price;
 });
 
 return total;
}
	






/*$(document).on('submit','#posaljiPodatkeZaRezervaciju', function(e){
	e.preventDefault();
	var brPutnika = $(this).find("input[name=brPutnika]").val();
	var klasa = $(this).find("select[name=klasa]").val();
	if (brPutnika == "" || klasa == ""){
		alert("Morate popuniti sva polja!");
		return false;
	}
	if (parseInt(brPutnika) <= 0 ){
		alert("Morate uneti pozitivan broj veci od nule!");
		return false;
	}
	$.ajax({
		type : 'POST',
		url : '../Projekat/rest/letovi/rezervacija',
		contentType: 'application/json',
		dataType : 'text',
		data: formToJSON_rez(brPutnika, klasa),
		success : function(data){
			if (data == "uspesno"){
				$('.main').empty();
	        	$('.main').append('<p>Uspesno ste rezervisali let.</p>');
			}
			else
			{
				alert(data)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});		
});



function formToJSON_pretraga(pocetna,krajnja,datum,drzava){
	return JSON.stringify({
		"pocetnaDest" : pocetna,
		"krajnjaDest" : krajnja,
		"datumLeta" : datum,
		"drzava": drzava
	});
}

function formToJSON_rez(brPutnika, klasa) {
	return JSON.stringify({
		"brPutnika" : brPutnika,
		"klasa" : klasa.toUpperCase()
	});
}*/



/*PRIKAZ REZERVACIJA REGISTERED USERA*/
/*$(document).on('click', '#mojeRezervacije_btn', function(e){
	e.preventDefault();
	$.ajax({
		type : 'GET',
		url : '../Projekat/rest/korisnici/korisnikoveRezervacije',
		dataType: 'json',
		success : prikazRezervacija,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
})

function prikazRezervacija(data){
	if (data == null){
		$('.main').empty();
		$('.main').append('Vas nalog je blokiran');
	}
	else{
		$('.main').empty();
		$('.main').append('<h1>Vase rezervacije: <h1>');
		var tabela = $('<table class = "mainTable" border = "1"></table>')
		var tr_h = $('<tr></tr>');
			tr_h.append('<th>Broj rezervacije</th>');
			tr_h.append('<th>Datum rezervacije</th>');
			tr_h.append('<th>Klasa</th>');
			tr_h.append('<th>Broj putnika</th>');
			tr_h.append('<th>Pocetna destinacija:</th>');
			tr_h.append('<th>Krajnja destinacija:</th>');
			tr_h.append('<th>Datum leta:</th>');
			tr_h.append('<th></th>');
		tabela.append(tr_h);
		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
		$.each(list, function(index, rezervacija){
			var tr = $('<tr></tr>');
			tr.append('<td>'+ rezervacija.brRezervacije + '</td>');
			tr.append('<td>'+ rezervacija.datumIvreme + '</td>');
			tr.append('<td>'+ rezervacija.klasa + '</td>');
			tr.append('<td>'+ rezervacija.brPutnika + '</td>');
			tr.append('<td>'+ rezervacija.pocetnaDest + '</td>');
			tr.append('<td>'+ rezervacija.krajnjaDest + '</td>');
			tr.append('<td>'+ rezervacija.datumLeta + '</td>');
			var forma = $('<form class = "otkazivanjeRezervacije"></form>')
			forma.append('<input type = "hidden" value="' + rezervacija.brRezervacije +'">');
			forma.append('<input type = "submit" value = "Otkazi">')
			var td = $('<td></td>');
			td.append(forma);
			tr.append(td);
			tabela.append(tr);
		});
		$('.main').append(tabela);
	}
}*/

/*BRISANJE REZERVACIJA REGISTERED USERA*/
/*$(document).on('submit', '.otkazivanjeRezervacije', function(e){
	e.preventDefault();
	var brRez = $(this).find('input[type=hidden]').val();
	var adresa = '../Projekat/rest/letovi/otkaziRezervaciju/' + brRez;
	$.ajax({
		type : 'GET',
		url : adresa,
        dataType: 'text',
        success: function(data){
        	if (data == 'uspesno'){
        		$('.main').empty();
            	$('.main').append('<p>Uspesno ste otkazali rezervaciju.</p>');
        	}
        	else{
        		$('.main').empty();
            	$('.main').append('<p>' + data + '</p>');
        	}
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
})*/




