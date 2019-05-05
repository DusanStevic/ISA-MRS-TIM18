var TOKEN_KEY = 'jwtToken';

$(window).on("load",function(){
	if (window.location.href.match('hotelAdmin-hotelProfile.html') != null) {
		getHotel();
	}
	else if (window.location.href.match('hotelAdmin-home.html') != null) {
		checkFirstTime();
	}
	else{
		checkFirstTime();
	}
})

function getHotelAdmin() {
	var token = getJwtToken(TOKEN_KEY);
	if (token) {
		$.ajax({
			type : 'GET',
			url : "/api/getRegUser",
			headers : createAuthorizationTokenHeader(TOKEN_KEY),
			dataType : "json",
			success : function(data) {
				if (data == null) {
					alert('Some error occurred. Please try again later.');
				} else {
					displayAdmin(data);
				}
			},
			error : function() {
				alert('Some error occurred. Please try again later.');
			}
	
		})
	}
}

function getHotel() {
	var token = getJwtToken(TOKEN_KEY);
	if (token) {
		$.ajax({
			type : 'GET',
			url : "/api/getHotel",
			headers : createAuthorizationTokenHeader(TOKEN_KEY),
			dataType : "json",
			success : function(data) {
				if (data == null) {
					alert('Error while finding loged one!');
				} else {
					displayHotel(data);
					getRooms();
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.status);
				alert(textStatus);
				alert(errorThrown);
			}
	
		})
	}
}

function getRooms() {
	var token = getJwtToken(TOKEN_KEY);
	if (token) {
		$.ajax({
			type : 'GET',
			url : "/api/getRooms",
			headers : createAuthorizationTokenHeader(TOKEN_KEY),
			dataType : "json",
			success : function(data) {
				if (data == null) {
					alert('Error while finding loged one!');
				} else {
					displayRooms(data);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.status);
				alert(textStatus);
				alert(errorThrown);
			}
	
		})
	}
}

function displayAdmin(data){
	
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
		var tr5=$('<tr></tr>');
		tr5.append('<td><div class="edit_input"><input type="button" value="Upload image" id="uploadImage" /></div></td>');
		$('#hotelInfo').append(tr1);
		$('#hotelInfo').append(tr2);
		$('#hotelInfo').append(tr3);
		$('#hotelInfo').append(tr4);
		$('#hotelInfo').append(tr5);
		
	})
}

function displayRooms(data){
	if(data != null && data != undefined){
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
}

$(document).on('submit', "#newRoomForm", function(e){
	e.preventDefault();
	var token = getJwtToken(TOKEN_KEY);
	var image = "images/room20.jpg";
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
	$.ajax({
		type:'POST',
		url:'/api/room',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToHotelRoom(image, beds, price, description),
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

$(document).on('submit','#editRoomForm',function(){
	var token = getJwtToken(TOKEN_KEY);
	var id = $('#editId').val();
	var beds = $('#editBeds').val();
	var price = $('#editPrice').val();
	var desc = $('#editRoomDesc').val();
	var image = "a";
	if(isNaN(beds) || isNaN(price)){
		alert("Number of beds and price must be numbers!");
		return;
	}
	$.ajax({
		type:'POST',
		url:'/api/editRoom',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToHotelRoomID(id, image, beds, price, desc),
		success:function(data){
			alert("Room successfully edited!");
			location.reload();
		}
	})
})

$(document).on('click','#editRoom',function(e){
	var token = getJwtToken(TOKEN_KEY);
    var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/api/getRoom/'+id,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        contentType: 'text/plain',
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

$(document).on('click','#uploadImage',function(e){
    var modal = document.getElementById('modal3');
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
    var id=$(this).attr("name");
    $.ajax({
        type: 'DELETE',
        url: '/api/deleteRoom/'+id,
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            location.reload();
            e.preventDefault();
        }
    })
})

$(document).on('click','#viewRoom',function(e){
	var token = getJwtToken(TOKEN_KEY);
	var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/api/getRoom/'+id,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
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

$(document).on('click','#edit',function(e){
	var token = getJwtToken(TOKEN_KEY);
    $.ajax({
		type:'GET',
		url:'/api/getHotel',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType:'json',
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, hotel){
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
	var token = getJwtToken(TOKEN_KEY);
	var name = $('#hotelName').val();
	var adress = $('#hotelAdress').val();
	var desc = $('#hotelDesc').val();
	var image = $('#hotelImg').val();
	if(name == "" || adress == "" || desc == ""){
		alert("All fields must be filled!");
	}
	$.ajax({
		type:'PUT',
		url:'/api/editHotel',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToCompany(name, adress, desc, image),
		success:function(data){
			alert("Hotel info successfully edited!");
			location.reload();
		}
	})
})

$(document).on('submit', "#uploadImageForm", function(event){
	 event.preventDefault();
	 var formElement = this;
	 var formData = new FormData(formElement);
	 uploadImage(formElement, formData, "/api/editHotelImage");
	 event.preventDefault();
})



function inputToHotelRoom(image, beds, price, desc){
	return JSON.stringify({
		"image":image,
		"bedNumber":beds,
		"price":price,
		"description":desc,
	})
}

function inputToHotelRoomID(id, image, beds, price, desc){
	return JSON.stringify({
		"id" : id,
		"image":image,
		"beds":beds,
		"price":price,
		"description":desc,
	})
}

function hotelToJSON(name, adress, desc, image){
	return JSON.stringify({
		"name":name,
		"address":adress,
		"description":desc,
		"image": image,
	})
}

function inputToCompany(name, adress, desc, image){
	return JSON.stringify({
		"name":name,
		"adress":adress,
		"description":desc,
		"image": image,
	})
}
