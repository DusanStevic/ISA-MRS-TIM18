$(window).on("load",function(){
	if (window.location.href.match('index.html') != null) {
		findHotels();
	}
	else if(window.location.href.match('users-hotelProfile.html') != null){
		$("#hotelInfo").empty();
		$("#roomsDisp").empty();
		displayHotelProfile();
		findRooms();
	}
	else{
		findHotels();
	}
})

function findHotels(){
	$.ajax({
        type: 'GET',
        url: '/getAllHotels',
        contentType: 'application/json',
        success: displayHotels
    })
}

function findAirlines(){
	$.ajax({
        type: 'GET',
        url: '/getAirlines',
        contentType: 'application/json',
        success: displayData
    })
}

function findRACs(){
	$.ajax({
        type: 'GET',
        url: '/getRACs',
        contentType: 'application/json',
        success: displayData
    })
}

function displayHotels(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, data){
		var tr1=$('<tr></tr>');
		tr1.append('<td><img src="'+data.image+'" class="small_image"/></td>'
				+'<td><table class="min"><tr><td><h3>'+data.name+'</h3></td></tr>'
				+'<tr><td><h4>' + data.address+'</h4></td></tr>'
				+'<tr><td><a href="#" id="moreInfoHotel" name="'+data.id+'">More informations</a></td></tr>'
				+'<tr><td><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span></td></tr></table></td>');
		$('#companiesInfo').append(tr1);
	})
}

function displayData(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, data){
		var tr1=$('<tr></tr>');
		tr1.append('<td><img src="'+data.image+'" class="small_image"/></td>'
				+'<td><table class="min"><tr><td><h3>'+data.name+'</h3></td></tr>'
				+'<tr><td><h4>' + data.address+'</h4></td></tr>'
				+'<tr><td><a href="#" id="moreInfoCompany" name="'+data.id+'">More informations</a></td></tr>'
				+'<tr><td><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span><span class="fa fa-star"></span></td></tr></table></td>');
		$('#companiesInfo').append(tr1);
	})
}

function displayHotelProfile(){
	var id = localStorage.getItem("hotelId1");
	$.ajax({
        type: 'GET',
        url: '/getHotelID',
        contentType: 'text/plain',
        data:{'id': id },
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, hotel){
            	var tr1=$('<tr></tr>');
        		tr1.append('<td><h2>' + hotel.name + '</h2></td>');
        		var tr2=$('<tr></tr>');
        		tr2.append('<td><h4>'+ hotel.address +'</h4></td>');
        		var tr3=$('<tr></tr>');
        		tr3.append('<td colspan="2"><div class="middle_container"><p>'+ hotel.description +'</p></div></td>');
        		var tr4=$('<tr></tr>');
        		tr4.append('<td><div><img src='+ hotel.image + ' class="image" /></div></td>');
        		tr4.append('<td>'+
                        '<div class="rating">' +
                        '<span class="heading">User Rating</span>'+
                        '<span class="fa fa-star"></span>' +
                        '<span class="fa fa-star"></span>' +
                        '<span class="fa fa-star"></span>' +
                        '<span class="fa fa-star"></span>' +
                        '<span class="fa fa-star"></span>' +
                        '<p>0 average based on 0 reviews.</p>' +
                        '<hr style="border:3px solid #f1f1f1">' +
                        '<div class="row">' +
                         '   <div class="side">' +
                          '      <div>5 star</div>' +
                           ' </div>' +
                            '<div class="middle">' +
                             '   <div class="bar-container">' +
                              '      <div class="bar-1"></div>' +
                               ' </div>' +
                            '</div>' +
                            '<div class="side right">' +
                             '   <div>0</div>' +
                            '</div>' +
                            '<div class="side">' +
                             '   <div>4 star</div>' +
                            '</div>' +
                            '<div class="middle">' +
                             '   <div class="bar-container">' +
                              '      <div class="bar-1"></div>' +
                               ' </div>' +
                            '</div>' +
                            '<div class="side right">' +
                             '   <div>0</div>' +
                            '</div>' +
                            '<div class="side">' +
                             '   <div>3 star</div>' +
                            '</div>' +
                            '<div class="middle">' +
                            '    <div class="bar-container">' +
                             '       <div class="bar-1"></div>' +
                             '   </div>' +
                            '</div>' +
                            '<div class="side right">' +
                             '   <div>0</div>' +
                            '</div>' +
                            '<div class="side">' +
                             '   <div>2 star</div>' +
                            '</div>' +
                            '<div class="middle">' +
                            '    <div class="bar-container">' +
                             '       <div class="bar-1"></div>' +
                             '   </div>' +
                            '</div>' +
                            '<div class="side right">' +
                             '   <div>0</div>' +
                            '</div>' +
                            '<div class="side">' +
                             '   <div>1 star</div>' +
                            '</div>' +
                            '<div class="middle">' +
                             '   <div class="bar-container"> '+
                              '      <div class="bar-1"></div>' +
                               ' </div>' +
                            '</div>' +
                            '<div class="side right">' +
                             '   <div>0</div>' +
                            '</div>' +
                        '</div>' +
                    '</div>' +
                '</td>');
        		$('#hotelInfo').append(tr1);
        		$('#hotelInfo').append(tr2);
        		$('#hotelInfo').append(tr3);
        		$('#hotelInfo').append(tr4);
            })
        }
    })
}

function findRooms(){
	var id = localStorage.getItem("hotelId1");
	$.ajax({
		type:'GET',
		url:'/getRooms',
		dataType:'json',
		contentType: 'text/plain',
        data:{'id': id },
		success:displayRooms
	})
}

function displayRooms(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, room){
		var tr=$('<tr></tr>');
		tr.append('<td><img src='+room.image+' class="room_display"/></td>');
		tr.append('<td><table><tr><td><h3>Price per night: '+room.price+' </h3></td></tr>'+'<tr><td><h4>Beds: '+room.bedNumber+'</h4></td></tr>'+
		'<tr><td><a href="#" id="viewRoom" name="'+room.id+'">More details</a></td></tr>');
		$('#roomsDisp').append(tr);
	})
}

$(document).on('click','#viewRoom',function(e){
	var hid = localStorage.getItem("hotelId1");
	var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/getRoomID',
        contentType: 'text/plain',
        data:{'room_id':id,'hotel_id':hid },
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, room){
            	localStorage.setItem("image", room.image);
            	localStorage.setItem("beds", room.bedNumber);
            	localStorage.setItem("price", room.price);
            	localStorage.setItem("desc", room.description);
            })
            window.location.href = "roomInfo.html";
        }
    })
})

$(document).on('click', '[name="type"]', function() {
	$("#companiesInfo").empty();
    if (this.value == 'hotel') {
    	findHotels();
    }
    else if (this.value == 'airline') {
    	findAirlines();
    }
    else{
    	findRACs();
    }
})

$(document).on('click','#moreInfoHotel',function(e){
	var id=$(this).attr("name");
	localStorage.setItem("hotelId1", id);
	window.location.href = "users-hotelProfile.html";
})



