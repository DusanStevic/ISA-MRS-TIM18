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
		findHotels();
	}
	else if(page == "airline"){
		generateHotelSearch();
		findAirlines();
	}
	else if(page == "rac"){
		generateHotelSearch();
		findRACs();
	}
});

function generateHotelSearch(){
	var tirkiz_content ='<td>'
	    +'<div class="tirkiz_table_content">'
	    +'<table>'
        +'<tr>'
        +'<td>'
        +'<table><tr><td><h3 class="white">For better search, fill out the offered parameters:</h3></td></tr></table>'
        +'</td>'
        +'</tr>'
        +'<tr>'
        +'<td>'
        +'<table><tr><td><h4 class="white"><b>Location: </b></h4></td><td><input type="text" id="address" value="Enter location of hotel..." /></td></tr></table>'
        +'</td>'
        +'<td>'
        +'<table>'
        +'<tr>'
        +'<td><h4 class="white"><b>Sort by: </b></h4></td>'
        +'<td>'
        +'<select>'
        +'<option id="star">Most rated</option>'
        +'<option id="cheap">Price(cheapest)</option>'
        +'<option id="expensive">Price(most expensive)</option>'
        +'</select>'
        +'</td>'
        +'</tr>'
        +'</table>'
        +'</td>'
        +'</tr>'
        +'</table>'
	    +'</div>'
		+'</td>'
		+'</tr>';
	var tr=$('<tr></tr>');
	tr.append(tirkiz_content);
	$('#tirkiz_display').append(tr);
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
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, data){
		var tr1=$('<tr></tr>');
		tr1.append('<td><img src="'+data.image+'" class="small_image"/></td>'
				+'<td><table class="min"><tr><td><h3>'+data.name+'</h3></td></tr>'
				+'<tr><td><h4>' + data.address+'</h4></td></tr>'
				+'<tr><td><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span></td></tr></table></td>'
				+'<td><table><tr><td><h3>Price from:<b> 30$</b></h3>(Per night)<td></tr><tr><td><a href="#" id="moreInfoHotel" name="'+data.id+'">More informations</a></td></tr></table></td>');
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