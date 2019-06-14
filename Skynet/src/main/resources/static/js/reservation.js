var totalPrice;

$(document).on('click','#reserveRoom',function(e){
	e.preventDefault();
	var hid = localStorage.getItem("hotelId1");
	var id=$(this).attr("name");
	localStorage.setItem("roomID", id);
    $.ajax({
        type: 'GET',
        url: '/api/getRoom/'+id,
        contentType: 'text/plain',
        success: displayRoomReservation
    });
});

$(document).on('click','#makeRes',function(e){
	var offers = [];
	$("input:checkbox[name=offerChoice]:checked").each(function(){
		var x = parseInt(this.id);
		offers.push(x);
	});
	console.log(offers);
	var days = 10;
	var resID = 5;
	var roomID = localStorage.getItem("roomID");
	$.ajax({
		type:'POST',
		url:'/api/roomReservation',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToRoomReservation(offers, days, roomID, resID),
		success:function(data){
			if(data == null){
				alert("Reservation could not be added in cart. Please check if you are trying to make another reservation until you have not completed current one.");
			}
			else{
				alert("Reservation added!");
			}
		},
		error:function(){
			alert("Reservation could not be added in cart. Please check if you are trying to make another reservation until you have not completed current one.");
		}
	});
});


$(document).on('click','.custom_checkbox',function(e){
	var price = parseFloat(this.value);
	if(this.checked == true){
		totalPrice = totalPrice + price;
	}
	else{
		totalPrice = totalPrice - price;
	}
	$('#submitReservation').empty();
	$('#TotPrice').empty();
	$('#TotPrice').append('<tr><td><h4><b>Total price: '+totalPrice+'</b></h4></td></tr>');
    $('#submitReservation').append('<tr><td><input type="button" id="makeRes" value = "Reserve Room" class="greenButton"/></td></tr>');
});

function displayRoomReservation(room){
	totalPrice=room.price;
	$('#roomShow').empty();
	$('#submitReservation').empty();
	$('#TotPrice').empty();
	var tr2=$('<tr></tr>');
    tr2.append('<td><img src="'+room.image+'" class="modal_picture"/></td>');
    tr2.append('<td align="top"><table id="roomResInfo"></table></td>');
    $('#roomShow').append(tr2);
    $('#roomResInfo').append('<tr><td><b>Beds:&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp'+room.bedNumber+'</b></td></tr>');

    $.ajax({
        type: 'POST',
        url: '/api/getReservation',
        contentType: 'text/plain',
        success: displayResInfo
    });

    dispHotelOffers();
    
    
    
    var modal = document.getElementById('modal2');
	var span = document.getElementById("close");
	modal.style.display = "block";
	span.onclick = function () {
        modal.style.display = "none";
    }
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}

function displayResInfo(res){
	totalPrice = totalPrice * res.days;
    $('#roomResInfo').append('<tr><td><b>Checkin:&nbsp &nbsp &nbsp'+res.startDate+'</b></td></tr>');
    $('#roomResInfo').append('<tr><td><b>Checkout:&nbsp &nbsp'+res.endDate+'</b></td></tr>');
    $('#roomResInfo').append('<tr><td><b>Price:&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp'+totalPrice+'</b></td></tr>');
    $('#TotPrice').append('<tr><td><h4><b>Total price: '+totalPrice+'</b></h4></td></tr>');
    $('#submitReservation').append('<tr><td><input type="button" id="makeRes" value = "Reserve Room" class="greenButton"/></td></tr>');
}

function dispHotelOffers(){
	$('#offersShow').empty();
	var id = localStorage.getItem("hotelId1");
	$.ajax({
		type:'GET',
		url:'/api/getHotelOffers/'+id,
		contentType:'application/json',
		dataType:'json',
		success:function(data){
			if(data != null){
				$('#offersShow').append('<tr><td><table id="offersTab" class="reservation_table"></table></td></tr>');
				$('#offersTab').append('<tr><th>Offer name</th><th>Price</th></tr>');
				var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
				$.each(list, function(index, offer){
					$('#offersTab').append('<tr><td>'+offer.name+'</td><td>'+offer.price+'<input type="checkbox" class="custom_checkbox" value="'+offer.price+'" id="'+offer.id+'" name="offerChoice" /></td></tr>');
				});
			}
		}
	});
}

function inputToRoomReservation(offers, days, roomID, resID){
	return JSON.stringify({
		"hotelOffers":offers,
		"days": days,
		"roomId":roomID,
		"reservationId":resID,
		"checkin":localStorage.getItem("checkin"),
		"checkout":localStorage.getItem("checkout"),
	})
}

