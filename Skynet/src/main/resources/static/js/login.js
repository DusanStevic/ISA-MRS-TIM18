var TOKEN_KEY = 'jwtToken';

$(document).on('submit', "#loginForm", function(e){
	console.log('login called');

	var username = $("#username").val();
	var password = $("#password").val();
	
	var next_window;

	$.ajax({
		dataType : 'json',
		url : '/auth/login',
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
					window.location.href = "hotelAdmin-home.html";
				} else if (data.userRoleName == "ROLE_AIRLINE_ADMIN") {
					//strana za avio
				} else if (data.userRoleName == "ROLE_RENTACAR_ADMIN") {
					console.log('Role is rentacardmin');
					window.location.href = "racAdmin-home.html";
				} 
				else if (data.userRoleName=="ROLE_USER"){
					window.location.href = "user-home.html";
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

