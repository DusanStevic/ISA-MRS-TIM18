var TOKEN_KEY = 'jwtToken';
$(document).on('click','#hotelSearch',function(){
	localStorage.setItem("page", "hotel");
});

$(document).on('click','#airlineSearch',function(){
	localStorage.setItem("page", "airline");
});

$(document).on('click','#racSearch',function(){
	localStorage.setItem("page", "rac");
});

$(document).ready(function(){
	var token = getJwtToken(TOKEN_KEY);
	if(token){
		generateMenu();
	}
	var page = localStorage.getItem("page");
	if(page == "hotel"){
		localStorage.setItem("tab", "");
		localStorage.setItem('searchCriteria', "");
		var admin = localStorage.setItem("isAdmin", "");
		generateHotelSearch();
	}
	else if(page == "airline"){
		generateAirlineSearch();
	}
	else if(page == "rac"){
		generateRACSearch();
		//findRACs();
	}
	//i ako je strana companies display
	else if(page == "searchHotels"){
		searchHotels();
	}
	
	else if(page == "searchAirlines"){
		searchAirlines();
	}
	else if(page == "searchRacs"){
		//searchRacs();
	}
});

function generateHotelSearch(){
	$('#searchDiv').empty();
	var form = '<form id="hotelSearchForm">'
    	+'<table class="ombre_table">'
    	+'<tr><th><h1>Search for hotel:</h1></th></tr>'
    	+'<tr><td>Enter hotel name (optional)</td></tr>'
    	+'<tr><td><input type="text" id="name" /></td></tr>'
    	+'<tr><td>*Hotel address</td></tr>'
    	+'<tr><td><input type="text" id="address" /></td></tr>'
    	+'<tr><td>*Check-in:</td></tr>'
    	+'<tr><td><input type="date" id="checkin" /></td></tr>'
    	+'<tr><td>*Check-out:</td></tr>'
    	+'<tr><td><input type="date" id="checkout" /></td></tr>'
    	+'<tr><td>*Guests:</td></tr>'
    	+'<tr><td><input type="number" id="guests" step="1" /></td></tr>'
    	+'<tr><td><input type="submit" value="Search"></td></tr>'
    	+'</table>'
    	+'</form>';
	$('#searchDiv').append(form);
}

function generateRACSearch(){
	$('#searchDiv').empty();
	var form = '<form id="racSearchForm">'
    	+'<table class="ombre_table">'
    	+'<tr><th><h1>Search for Rent-A-Car service:</h1></th></tr>'
    	+'<tr><td>Enter Rent-A-Car name (optional)</td></tr>'
    	+'<tr><td><input type="text" id="name" /></td></tr>'
    	+'<tr><td>*Rent-A-Car address</td></tr>'
    	+'<tr><td><input type="text" id="address" /></td></tr>'
    	+'<tr><td>*Start date:</td></tr>'
    	+'<tr><td><input type="date" id="checkin" /></td></tr>'
    	+'<tr><td>*End date:</td></tr>'
    	+'<tr><td><input type="date" id="checkout" /></td></tr>'
    	+'<tr><td><input type="submit" value="Search"></td></tr>'
    	+'</table>'
    	+'</form>';
	$('#searchDiv').append(form);
}

function generateAirlineSearch(){
	$('#searchDiv').empty();
	var form = '<form id="airlineSearchForm">'
    	+'<table class="ombre_table">'
    	+'<tr><th><h1>Search for airline:</h1></th></tr>'
    	+'<tr><td>Enter airline name (optional)</td></tr>'
    	+'<tr><td><input type="text" id="name" /></td></tr>'
    	+'<tr><td>*Start destination</td></tr>'
    	+'<tr><td><input type="text" id="startaddress" /></td></tr>'
    	+'<tr><td>*End destination</td></tr>'
    	+'<tr><td><input type="text" id="endaddress" /></td></tr>'
    	+'<tr><td>*Date of departure:</td></tr>'
    	+'<tr><td><input type="date" id="checkin" /></td></tr>'
    	+'<tr><td>Date of return (optional):</td></tr>'
    	+'<tr><td><input type="date" id="checkout" /></td></tr>'
    	+'<tr><td>*Passangers:</td></tr>'
    	+'<tr><td><input type="number" id="guests" step="1" /></td></tr>'
    	+'<tr><td><input type="submit" value="Search"></td></tr>'
    	+'</table>'
    	+'</form>';
	$('#searchDiv').append(form);
}

$(document).on('submit','#hotelSearchForm',function(e){
	e.preventDefault();
	var date1 = $(this).find("input[id = checkin]").val();
	var date2 = $(this).find("input[id = checkout]").val();
	var guests = $('#guests').val();
	var name = $('#name').val();
	var address = $('#address').val();
	
	if(date1 == "" || date2 == "" || guests == "" || address == ""){
		alert("You did not filled all required fields.");
		return;
	}
	
	if(isNaN(guests)){
		alert("Please enter valid number of guests.");
		return;
	}
	else if(guests < 1){
		alert("Minimal number of guests is 1.");
		return;
	}
	
	localStorage.setItem("checkin",date1);
	localStorage.setItem("checkout",date2);
	localStorage.setItem("guests",guests);
	if(name != ""){
		localStorage.setItem("name",name);
	}
	localStorage.setItem("address",address);
	localStorage.setItem("page","searchHotels");
	window.location.replace("companiesDisplay.html");
});

$(document).on('submit','#airlineSearchForm',function(e){
	e.preventDefault();
	var date1 = $(this).find("input[id = checkin]").val();
	var date2 = $(this).find("input[id = checkout]").val();
	var guests = $('#guests').val();
	var name = $('#name').val();
	var start = $('#startaddress').val();
	var end = $('#endaddress').val();
	
	if(date1 == "" || guests == "" || start == "" || end == ""){
		alert("You did not filled all required fields.");
		return;
	}
	
	if(isNaN(guests)){
		alert("Please enter valid number of passangers.");
		return;
	}
	else if(guests < 1){
		alert("Minimal number of passangers is 1.");
		return;
	}
	
	localStorage.setItem("checkin",date1);
	localStorage.setItem("checkout",date2);
	localStorage.setItem("guests",guests);
	if(name != ""){
		localStorage.setItem("name",name);
	}
	localStorage.setItem("start",start);
	localStorage.setItem("end",end);
	localStorage.setItem("page","searchAirlines");
	window.location.replace("companiesDisplay.html");
});

$(document).on('submit','#racSearchForm',function(e){
	e.preventDefault();
	var date1 = $(this).find("input[id = checkin]").val();
	var date2 = $(this).find("input[id = checkout]").val();
	var guests = "2";
	var name = $('#name').val();
	var address = $('#address').val();
	
	if(date1 == "" || date2 == "" || guests == "" || address == ""){
		alert("You did not filled all required fields.");
		return;
	}
	
	if(isNaN(guests)){
		alert("Please enter valid number of guests.");
		return;
	}
	else if(guests < 1){
		alert("Minimal number of guests is 1.");
		return;
	}
	
	localStorage.setItem("checkin",date1);
	localStorage.setItem("checkout",date2);
	localStorage.setItem("guests",guests);
	if(name != ""){
		localStorage.setItem("name",name);
	}
	localStorage.setItem("address",address);
	localStorage.setItem("page","searchRacs");
	window.location.replace("companiesDisplay.html");
});

function searchHotels(){
	var date1 = localStorage.getItem("checkin");
	var date2 = localStorage.getItem("checkout");
	var guests = localStorage.getItem("guests");
	var address = localStorage.getItem("address");
	var name = null;
	if(localStorage.getItem("name") != null && localStorage.getItem("name") != undefined){
		name = localStorage.getItem("name");
	}
	$.ajax({
		type : 'POST',
		url : "/api/searchedHotels",
		contentType: 'application/json',
		data: searchToHotel(name,date1,date2,guests,address),
		dataType: 'json',
		success : displayData,
		error : function() {
			alert("ERROR OCCURRED!!!");
		}
	});	
}

function searchAirlines(){
	var date1 = localStorage.getItem("checkin");
	var date2 = localStorage.getItem("checkout");
	var guests = localStorage.getItem("guests");
	var start = localStorage.getItem("start");
	var end = localStorage.getItem("end");
	var name = null;
	if(localStorage.getItem("name") != null && localStorage.getItem("name") != undefined){
		name = localStorage.getItem("name");
	}
	$.ajax({
		type : 'POST',
		url : "/api/searchedAirlines",
		contentType: 'application/json',
		data: searchToAirline(name,date1,date2,guests,start,end),
		dataType: 'json',
		success : displayData,
		error : function() {
			alert("ERROR OCCURRED!!!");
		}
	});	
}

function searchRacs(){
	var date1 = localStorage.getItem("checkin");
	var date2 = localStorage.getItem("checkout");
	var guests = localStorage.getItem("guests");
	var address = localStorage.getItem("address");
	var name = null;
	if(localStorage.getItem("name") != null && localStorage.getItem("name") != undefined){
		name = localStorage.getItem("name");
	}
	$.ajax({
		type : 'POST',
		url : "/api/searchedRacs",
		contentType: 'application/json',
		data: searchToHotel(name,date1,date2,guests,address),
		dataType: 'json',
		success : displayData,
		error : function() {
			alert("ERROR OCCURRED!!!");
		}
	});	
}

function findHotels(){
	$.ajax({
        type: 'GET',
        url: '/api/hotels',
        contentType: 'application/json',
        success: displayData
    })
}

function findAirlines(){
	$.ajax({
        type: 'GET',
        url: '/api/airlines',
        contentType: 'application/json',
        success: displayData
    })
}

function findRACs(){
	$.ajax({
        type: 'GET',
        url: '/api/racs',
        contentType: 'application/json',
        success: displayData1
    })
}

function displayData(data){
	$('#display').empty();
	$('#display').append('<table id="dataDisplay"></table>');
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, data){
		var tr1=$('<tr></tr>');
		tr1.append('<td><img src="'+data.image+'" class="small_image"/></td>'
				+'<td><table class="min"><tr><td><h3>'+data.name+'</h3></td></tr>'
				+'<tr><td><h4>' + data.address+'</h4></td></tr>'
				+'<tr><td><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span></td></tr></table></td>'
				+'<td><table><tr><td><a href="#" id="showLocation" name="'+data.address+'"><i class="fa fa-map-marker" style="font-size:36px" color:red"></i> Show Location</a></td></tr><tr><td align="center"><input type="button" id="moreInfoHotel" name="'+data.id+'" value = "View profile and offers" class="blueButton"/></td></tr></table></td>');
		var tr2=$('<tr></tr>');
		tr2.append('<td><hr /></td><td><hr /></td><td><hr /></td>');
		$('#dataDisplay').append(tr1);
		$('#dataDisplay').append(tr2);
	})
}

function displayData1(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, data){
		var tr1=$('<tr></tr>');
		tr1.append('<td><img src="'+data.image+'" class="small_image"/></td>'
				+'<td><table class="min"><tr><td><h3>'+data.name+'</h3></td></tr>'
				+'<tr><td><h4>' + data.address+'</h4></td></tr>'
				+'<tr><td><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span></td></tr></table></td>'
				+'<td><a href="#" id="moreInfoHotel" name="'+data.id+'">More informations</a></td></tr></table></td>');
		var tr2=$('<tr></tr>');
		tr2.append('<td><hr /></td><td><hr /></td><td><hr /></td>');
		$('#dataDisplay').append(tr1);
		$('#dataDisplay').append(tr2);
	})
}

$(document).on('click','#showLocation',function(e){
	e.preventDefault();
	var modal = document.getElementById('mapModal');
	modal.style.display = "block";
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    var addr=$(this).attr("name");
    var coordinates;
    $.ajax({
		type : 'GET',
		url : "https://geocode-maps.yandex.ru/1.x/?apikey=18116907-79b6-47b3-97aa-0db7c335b7e0&format=json&geocode="
				+ addr + "&lang=en_US",
		dataType : "json",
		async : false,
		success : function(data) {
			var found = data.response.GeoObjectCollection.featureMember;
			if (found.length != 0) {
				coordinates = found[0].GeoObject.Point.pos.split(" ");
				ymaps.ready(init(coordinates));
			} else {
				coordinates = [ -1, -1 ];
				alert("Location could not be found. Maybe this address does not exist?");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status);
			alert(textStatus);
			alert(errorThrown);
		}

	})
})

function init(coordinates){
	
	var coordinate1 = coordinates[1];
	var coordinate2 = coordinates[0];
	var myMap = new ymaps.Map("map", {
        center: [coordinate1, coordinate2],
        zoom: 7
    });
	
	myPlacemark = new ymaps.Placemark([ coordinate1, coordinate2 ], {
		hintContent : 'Location',
		balloonContent : 'Location of company'
	});
	
	myMap.geoObjects.add(myPlacemark);
}

function generateMenu(){
	$('#menubar').empty();
	$('#menubar').append('<div class="container-fluid">'+
            '<div class="navbar-header">'+
      			'<a class="navbar-brand" href="RegisteredUser.html"><span class="glyphicon glyphicon-plane"></span> SKYNET</a>'+
    		'</div>'+
		    ' <ul class="nav navbar-nav navbar-right">'+
      			'<li> <a id = "logout" href = ""><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>'+
    		'</ul>'+
        '</div>');
}

function searchToHotel(name,checkin,checkout,beds, address){
	return JSON.stringify({
		"name":name,
		"checkin":checkin,
		"checkout":checkout,
		"beds":beds,
		"address":address,
	})
}

function searchToAirline(name,date1,date2,guests,start,end){
	return JSON.stringify({
		"name":name,
		"departure":checkin,
		"arrival":checkout,
		"passangers":beds,
		"startDestination":start,
		"endDestination":end,
	})
}