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
