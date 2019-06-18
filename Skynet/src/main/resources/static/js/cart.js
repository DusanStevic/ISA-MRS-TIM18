var TOKEN_KEY = 'jwtToken';

$(window).on("load",function(){
	if (window.location.href.match('cart.html') != null){
		var token = getJwtToken(TOKEN_KEY);
		if(token){
			var id = localStorage.getItem("reservation");
			if(id == null || id == undefined || id == ""){
				alert("Flight reservation was not completed. Please reserve seat in order to view cart.");
				return;
			}
			displayFlightReservation(id);
			displayRoomReservation(id);
			displayCarReservation(id);
		}
		else{
			alert("You are not logged in!");
		}
	}
})


function displayFlightReservation(id){
	if(id != null && id != undefined && id != ""){
		$.ajax({
	        type: 'GET',
	        url: '/api/getRoomReservations/'+id,
	        headers : createAuthorizationTokenHeader(TOKEN_KEY),
	        contentType: 'text/plain',
	        success: function(data){
	            //TODO printanje rezervacija sedista...
	        	//ukoliko je lista sedista prazna javiti gresku korisniku
	        	//ukoliko nije prikazati dobavljene rezervacije sedista u tabeli flightResDisplay koja se nalazi u cart.html
	        }, error : function(){
	        	alert("Error.");
	        }
	    })
	}
	
}

function displayRoomReservation(id){
	if(id != null && id != undefined && id != ""){
		$.ajax({
	        type: 'GET',
	        url: '/api/getRoomReservations/'+id,
	        headers : createAuthorizationTokenHeader(TOKEN_KEY),
	        contentType: 'text/plain',
	        success: function(data){
	            //TODO printanje rezervacija sobe...
	        }, error : function(){
	        	alert("Error.");
	        }
	    })
	}
}

function displayCarReservation(id){
	if(id != null && id != undefined && id != ""){
		$.ajax({
	        type: 'GET',
	        url: '/api/getRoomReservations/'+id,
	        headers : createAuthorizationTokenHeader(TOKEN_KEY),
	        contentType: 'text/plain',
	        success: function(data){
	            //TODO printanje rezervacija automobila...
	        	//ukoliko je lista rezervacija automobila prazna, dodati dugme u carButtonDisplay tabeli koja se nalazi u cart.html, za prelazak na pretragu rac servisa
	        	//ukoliko nije, prikazati rezervacije automobila u tabeli carResDisplay i NE DODAVATI DUGME za prelazak na pretragu
	        }, error : function(){
	        	alert("Error.");
	        }
	    })
	}
}


//Prikaz poruke da je rezervacija gotova
$(document).on("click", "#finishReservation",function(e){
	var modal = document.getElementById('fency');
	modal.style.display = "block";
	var tr = $('<tr></tr>');
	tr.append('<td>Reservation was completed successfuly! You can find it in your reservations.</td>');
	$("#fencyBody").append(tr);
	var tr2 = $('<tr></tr>');
	tr2.append('<td></td><td><input type="button" id="ok" value="Ok" class="greenButton"/></td><td></td>');
	$("#fencyButtons").append(tr2);
})

$(document).on("click", "#ok",function(e){
	var modal = document.getElementById('fency');
	modal.style.display = "none";
	window.location.replace("RegisteredUser.html");
})