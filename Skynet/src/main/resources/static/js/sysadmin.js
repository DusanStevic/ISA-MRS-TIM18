$(window).on("load",function(){
	if (window.location.href.match('sysAdmin-addAirlineAdmin.html') != null) {
		$.ajax({
	        type: 'GET',
	        url: '/api/airlines',
	        contentType: 'application/json',
	        success: fillAirlines
	    })
	}
	else if (window.location.href.match('sysAdmin-addHotelAdmin.html') != null) {
		$.ajax({
	        type: 'GET',
	        url: '/api/hotels',
	        contentType: 'application/json',
	        success: fillHotels
	    })
	}
	else if (window.location.href.match('sysAdmin-addRACAdmin.html') != null) {
		$.ajax({
	        type: 'GET',
	        url: '/api/racs',
	        contentType: 'application/json',
	        success: fillRACs
	    })
	}
	else if (window.location.href.match('sysAdmin-viewAdmins.html') != null) {
		$.ajax({
	        type: 'GET',
	        url: '/api/admins',
	        contentType: 'application/json',
	        success: fillAdmins
	    })
	}
	else{
		
	}
})

function fillHotels(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, airline){
		var opt=$('<option value="'+airline.id+'">'+airline.name+'</option>');
		$('#hotels').append(opt);
	})
}

function fillRACs(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, airline){
		var opt=$('<option value="'+airline.id+'">'+airline.name+'</option>');
		$('#racs').append(opt);
	})
}

function fillAirlines(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, airline){
		var opt=$('<option value="'+airline.id+'">'+airline.name+'</option>');
		$('#airlines').append(opt);
	})
}

function fillAdmins(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, admin){
		var tr=$('<tr></tr>');
		var blocking = "Block";
		if(admin.enabled == false){
			blocking = "Unblock";
		}
		tr.append('<td>'+admin.username+'</td><td>'+admin.name+'</td><td>'+admin.surname+'</td><td>'+admin.email+'</td>'+'<td>'+admin.company+'</td>'+'<td><a href="#" name="'+admin.id+'" id="blockAdmin">'+blocking+'</a></td>')
		$('#adminsInfo').append(tr);
	})
}

$(document).on('click', "#blockAdmin", function(e){
	var id=$(this).attr("name");
	$.ajax({
        type: 'PUT',
        url: '/api/blockAdmin/'+id,
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            if(data != null){
            	if(data.enabled == false){
            		alert("Admin successfully blocked!");
            	}
            	else{
            		alert("Admin successfully unblocked!");
            	}
            	location.reload();
            }
            else{
            	alert("Admin could not be found.");
            }
        }
    })
})

$(document).on('submit', "#addAirlineAdminForm", function(e){
	var email = $('#email').val();
	var name = $('#name').val();
	var surname = $('#surname').val();
	var username = $('#username').val();
	var password = $('#password').val();
	var passwordConfirm = $('#passwordConfirm').val();
	var company = $("#airlines :selected").val();
	if(password == "" || password != passwordConfirm){
		alert("Please enter a password and confirm it!");
		return;
	}
	if(password == "" || name == "" || surname == "" || username == ""){
		alert("All fields must be filled!");
		return;
	}
	$.ajax({
        type: 'POST',
        url: '/auth/addAirlineAdmin',
        contentType: 'application/json',
        data:inputToAdmin(email, name, surname, username, password, company),
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            alert("Admin successfully added!");
        }
    })
})

$(document).on('submit', "#addHotelAdminForm", function(e){
	var email = $('#email').val();
	var name = $('#name').val();
	var surname = $('#surname').val();
	var username = $('#username').val();
	var password = $('#password').val();
	var passwordConfirm = $('#passwordConfirm').val();
	var company = $("#hotels :selected").val();
	if(password == "" || password != passwordConfirm){
		alert("Please enter a password and confirm it!");
		return;
	}
	if(password == "" || name == "" || surname == "" || username == ""){
		alert("All fields must be filled!");
		return;
	}
	$.ajax({
        type: 'POST',
        url: '/auth/addHotelAdmin',
        contentType: 'application/json',
        data:inputToAdmin(email, name, surname, username, password, company),
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            alert("Admin successfully added!");
        }
    })
})

$(document).on('submit', "#addRACAdminForm", function(e){
	var email = $('#email').val();
	var name = $('#name').val();
	var surname = $('#surname').val();
	var username = $('#username').val();
	var password = $('#password').val();
	var passwordConfirm = $('#passwordConfirm').val();
	var company = $("#racs :selected").val();
	if(password == "" || password != passwordConfirm){
		alert("Please enter a password and confirm it!");
		return;
	}
	if(password == "" || name == "" || surname == "" || username == ""){
		alert("All fields must be filled!");
		return;
	}
	$.ajax({
        type: 'POST',
        url: '/auth/addRACAdmin',
        contentType: 'application/json',
        data:inputToAdmin(email, name, surname, username, password, company),
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            alert("Admin successfully added!");
        }
    })
})

$(document).on('submit', "#addAirlineForm", function(e){
	var name = $('#name').val();
	var address = $('#adress').val();
	var desc = $('#promo').val();
	var image = "images/airplane.png";
	if(name == "" || address == "" || desc == "" || image == ""){
		alert("All fields must be filled!");
		return;
	}
	$.ajax({
        type: 'POST',
        url: '/api/airline',
        contentType: 'application/json',
        data:inputToCompany(name, address, desc, image),
        success: function(data){
            alert("Airline successfully added!");
        }
    })
})

$(document).on('submit', "#addRACForm", function(e){
	var name = $('#name').val();
	var address = $('#adress').val();
	var desc = $('#promo').val();
	var image ="images/rac.png";
	$.ajax({
        type: 'POST',
        url: '/api/rac',
        contentType: 'application/json',
        data:inputToCompany(name, address, desc, image),
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            alert("Reant-A-Car service successfully added!");
        }
    })
})

$(document).on('submit', "#addHotelForm", function(e){
	var name = $('#name').val();
	var address = $('#adress').val();
	var desc = $('#promo').val();
	var image = "images/hotel.png";
	$.ajax({
        type: 'POST',
        url: '/api/hotel',
        contentType: 'application/json',
        data:inputToCompany(name, address, desc, image),
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            alert("Airline successfully added!");
        }
    })
})


function inputToCompany(name, adress, desc, image){
	return JSON.stringify({
		"name":name,
		"address":adress,
		"description":desc,
		"image":image,
	})
}

function inputToAdmin(email, name, surname, username, password, company){
	return JSON.stringify({
		"adminId":company,
		"firstName":name,
		"lastName":surname,
		"username":username,
		"password":password,
		"email":email,
	})
}