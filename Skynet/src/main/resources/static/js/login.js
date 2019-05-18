var TOKEN_KEY = 'jwtToken';

$(document).on('submit', "#loginForm", function(e){
	e.preventDefault();
	var username = $("#username").val();
	var password = $("#password").val();

	$.ajax({
		dataType : 'json',
		url : 'http://localhost:8080/auth/login',
		type : 'POST',
		contentType : 'application/json',
		data : inputToUser(username, password),
		success : function(data) {
			if (data.message != undefined) {
				alert(data.message);
			} else {
				setJwtToken(TOKEN_KEY, data.accessToken);
				console.log(data.userRoleName)
				if (data.userRoleName == "ROLE_HOTEL_ADMIN") {
					window.location.href = "hotelAdmin-hotelProfile.html";
				} else if (data.userRoleName == "ROLE_AIRLINE_ADMIN") {
					window.location.href = "AirlineAdministrator.html"
				} else if (data.userRoleName == "ROLE_RENTACAR_ADMIN") {
					console.log('Role is rentacardmin');
					window.location.href = "racAdmin-home.html";
				} 
				else if (data.userRoleName=="ROLE_USER"){
					window.location.href = "RegisteredUser.html";
				}else{
					window.location.href="sysAdmin-home.html";
				}
			}
		},
		error : function(e) {
			alert('Wrong username or password. Please try again.');
		}
	})
})

function inputToUser(username, password){
	return JSON.stringify({
		"username":username,
		"password":password,
	})
}

