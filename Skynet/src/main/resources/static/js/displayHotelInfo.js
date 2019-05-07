$(window).on("load",function(){
	if (window.location.href.match('users-hotelProfile.html') != null){
		$("#hotelInfo").empty();
		$("#roomsDisp").empty();
		displayHotelProfile();
		findRooms();
	}
	else if (window.location.href.match('roomInfo.html') != null){
		var image = localStorage.getItem("image");
		var beds = localStorage.getItem("beds");
		var price = localStorage.getItem("price");
		var desc = localStorage.getItem("desc");
    	var tr1=$('<tr></tr>');
        tr1.append('<td><img src="'+image+'" class="image" /></td>'
        		+'<td>'+
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
        var tr2=$('<tr></tr>');
        tr2.append('<td><table class="info_table"><tr><td><b>Price per night: '
        		+price+'$</b></td></tr>'
        		+'<tr><td><b>Number of beds: '+beds+'</b></td></tr>'
        		+'<tr><td rowspan="2">'+desc+'</td></tr></table></td>');
        $('#roomInfo').append(tr1);
        $('#roomInfo').append(tr2);
	}
})

function displayHotelProfile(){
	var id = localStorage.getItem("hotelId1");
	$.ajax({
        type: 'GET',
        url: '/api/hotels/'+id,
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
		url:'/api/getRooms/'+id,
		dataType:'json',
		contentType: 'text/plain',
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
        url: '/api/getRoom/'+id,
        contentType: 'text/plain',
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
    });
});

$(document).on('click','#moreInfoHotel',function(e){
	var id=$(this).attr("name");
	localStorage.setItem("hotelId1", id);
	window.location.href = "users-hotelProfile.html";
});
