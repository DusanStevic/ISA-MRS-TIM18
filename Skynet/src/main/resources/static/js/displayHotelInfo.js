findHotel();
findRooms();

function findHotel(){
	$.ajax({
		type:'GET',
		url:'/getHotel',
		dataType:'json',
		success:displayHotel
	})
}

function findRooms(){
	var id = localStorage.getItem("hotelid");
	$.ajax({
		type:'GET',
		url:'/getRooms',
		dataType:'json',
		contentType: 'text/plain',
        data:{'id': id },
		success:displayRooms
	})
}

function displayHotel(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, hotel){
		localStorage.setItem("hotelid", hotel.id);
		var tr1=$('<tr></tr>');
		tr1.append('<td><h2>' + hotel.name + '</h2></td>');
		tr1.append('<td><div class="edit_input"><input type="button" value="Edit info" id="edit" /></div></td><td></td>');
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

function displayRooms(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, room){
		var tr=$('<tr></tr>');
		tr.append('<td><img src='+room.image+' class="room_display"/></td>');
		tr.append('<td><table><tr><td><h3>Price per night: '+room.price+' </h3></td></tr>'+'<tr><td><h4>Beds: '+room.bedNumber+'</h4></td></tr>'+
		'<tr><td><a href="#" id="viewRoom" name="'+room.id+'">More details</a></td></tr>'+
		'<tr><td><a href="#" id="editRoom" name="'+room.id+'">Edit room</a></td></tr>'+
		'<tr><td><a href="#" id="deleteRoom" name="'+room.id+'">Delete room</a></td></tr></table></td>');
		$('#roomsDisp').append(tr);
	})
}

$(document).on('submit', "#newRoomForm", function(e){
	e.preventDefault();
	var image = "images//room20.jpg";
	var beds = $('#beds').val();
	var price = $('#price').val();
	var description = $('#roomDesc').val();
	if(image == "" || beds == "" || price == "" || description == ""){
		alert("All fields must be filled!");
		return;
	}
	if(isNaN(beds) || isNaN(price)){
		alert("Number of beds and price must be numbers!");
		return;
	}
	var hid = localStorage.getItem("hotelid");
	$.ajax({
		type:'POST',
		url:'/addRoom',
		contentType:'application/json',
		dataType:'json',
		data:inputToHotelRoom(image, beds, price, description, hid),
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$.each(list, function(index, room){
				var tr=$('<tr></tr>');
				tr.append('<td><img src='+room.image+' class="room_display"/></td>');
				tr.append('<td><table><tr><td><h3>Price per night: '+room.price+' </h3></td></tr>'+'<tr><td><h4>Beds: '+room.bedNumber+'</h4></td></tr>'+
				'<tr><td><a href="#" id="viewRoom" name="'+room.id+'">More details</a></td></tr>'+
				'<tr><td><a href="#" id="editRoom" name="'+room.id+'">Edit room</a></td></tr>'+
				'<tr><td><a href="#" id="deleteRoom" name="'+room.id+'">Delete room</a></td></tr></table></td>');
				$('#roomsDisp').append(tr);
			})
		}
	})
})

$(document).on('click','#edit',function(e){
    $.ajax({
		type:'GET',
		url:'/getHotel',
		dataType:'json',
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, hotel){
            	$('#hotelId').val(hotel.id);
            	$('#hotelName').val(hotel.name);
            	$('#hotelAdress').val(hotel.address);
            	$('#hotelDesc').val(hotel.description);
            })
		}
	})
	var modal = document.getElementById('modal');
	var span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
	span.onclick = function () {
        modal.style.display = "none";
    }
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
})

$(document).on('submit','#editHotelForm',function(){
	var id = $('#hotelId').val();
	var name = $('#hotelName').val();
	var adress = $('#hotelAdress').val();
	var desc = $('#hotelDesc').val();
	var image = $('#hotelImg').val();
	if(name == "" || adress == "" || desc == ""){
		alert("All fields must be filled!");
	}
	$.ajax({
		type:'POST',
		url:'/api/editHotel',
		contentType:'application/json',
		dataType:'json',
		data:hotelIdToJSON(id, name, adress, desc, image),
		success:function(data){
			alert("Hotel info successfully edited!");
			location.reload();
		}
	})
})

$(document).on('submit','#editRoomForm',function(){
	var id = $('#editId').val();
	var beds = $('#editBeds').val();
	var price = $('#editPrice').val();
	var desc = $('#editRoomDesc').val();
	var image = $('#editRoomImage').val();
	if(isNaN(beds) || isNaN(price)){
		alert("Number of beds and price must be numbers!");
		return;
	}
	var hid = localStorage.getItem("hotelid");
	$.ajax({
		type:'POST',
		url:'/editRoom',
		contentType:'application/json',
		dataType:'json',
		data:inputToHotelRoom2(id, image, beds, price, desc, hid),
		success:function(data){
			alert("Room successfully edited!");
			location.reload();
		}
	})
})

$(document).on('click','#viewRoom',function(e){
	var hid = localStorage.getItem("hotelid");
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

$(window).on("load",function(){
	if (window.location.href.match('roomInfo.html') != null) {
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

$(document).on('click','#editRoom',function(e){
	var hid = localStorage.getItem("hotelid");
    var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/getRoomID',
        contentType: 'text/plain',
        data:{'room_id':id, 'hotel_id':hid },
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, room){
            	$('#editId').val(room.id);
            	$('#editBeds').val(room.bedNumber);
            	$('#editPrice').val(room.price);
            	$('#editRoomDesc').val(room.description);
            })
        }
    })
    var modal = document.getElementById('modal2');
	var span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
	span.onclick = function () {
        modal.style.display = "none";
    }
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
})

$(document).on('click','#deleteRoom',function(e){
	var hid = localStorage.getItem("hotelid");
    var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/deleteRoom',
        contentType: 'text/plain',
        data:{'hid':hid,'id':id },
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            location.reload();
            e.preventDefault();
        }
    })
})

function roomIdToJSON(id, image, beds, price, desc){
	return JSON.stringify({
		"id":id,
		"image":image,
		"bedNumber":beds,
		"price":price,
		"description":desc,
	})
}

function roomToJSON(image, beds, price, desc){
	return JSON.stringify({
		"image":image,
		"bedNumber":beds,
		"price":price,
		"description":desc,
	})
}

function inputToHotelRoom(image, beds, price, desc, hid){
	return JSON.stringify({
		"image":image,
		"bedNumber":beds,
		"price":price,
		"description":desc,
		"hotelId": hid,
	})
}

function inputToHotelRoom2(id, image, beds, price, desc, hid){
	return JSON.stringify({
		"id": id,
		"image":image,
		"bedNumber":beds,
		"price":price,
		"description":desc,
		"hotelId": hid,
	})
}

function hotelIdToJSON(id, name, adress, desc, image){
	return JSON.stringify({
		"id":id,
		"name":name,
		"address":adress,
		"description":desc,
		"image": image,
	})
}