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

function uploadImage(file) {
	$.ajax({
		url : "/uploadImage",
		type : 'POST',
		contentType : 'multipart/form-data',
		data : file,
		processData : false,
		success : function() {

		}
	})
}

$(document).on('submit', "#addAirlineForm", function(e){
	var name = $('#name').val();
	var address = $('#adress').val();
	var desc = $('#promo').val();
	var image = "images/airline1.jpg";
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
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            alert("Airline successfully added!");
        }
    })
})

$(document).on('submit', "#addRACForm", function(e){
	var name = $('#name').val();
	var address = $('#adress').val();
	var desc = $('#promo').val();
	var image ="images/rent2.jpg";
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
	var image = "images/hotel9.jpg";
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