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

