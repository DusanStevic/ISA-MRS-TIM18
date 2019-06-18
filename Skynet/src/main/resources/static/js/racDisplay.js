var TOKEN_KEY = 'jwtToken';

$(window).on("load",function(){
	if (window.location.href.match('users-RACProfile.html') != null){
		var token = getJwtToken(TOKEN_KEY);
		if(token){
			//generateMenu();
		}
		$("#racInfo").empty();
		$("#carDisp").empty();
		
		displayRACProfile();
		displayCars();
		displayFastReservations();
		displayBranches();
	}
})

function displayRACProfile(){
	var id = localStorage.getItem("racId1");
	$.ajax({
        type: 'GET',
        url: '/api/racs/'+id,
        contentType: 'text/plain',
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
        		$('#racInfo').append(tr1);
        		$('#racInfo').append(tr2);
        		$('#racInfo').append(tr3);
        		$('#racInfo').append(tr4);
            })
        },error: function(){
        	alert("NE VALJA");
        }
    })
}

function displayCars(){
	
}

function displayFastReservations(){
	
}

function displayBranches(){
	/*
	var id = localStorage.getItem("racId1");
	$.ajax({
		type : 'GET',
		url : "api/getConcreteBranches/" + id,
		contentType: 'text/plain',
		success : function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$.each(list, function(index, destinacija){
				var tr1 = $('<tr></tr>');
				tr1.append('<td><h2>'+destinacija.city+'</h2></td><td></td>');
				var tr2 = $('<tr></tr>');
				tr2.append('<td><p>'+destinacija.address+'</p></td>');
				var tr4=$('<tr></tr>');
				tr4.append('<td><hr /></td><td><hr /></td>');
				$('#branches').append(tr1);
				$('#branches').append(tr2);
				$('#branches').append(tr4);
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});	
	*/
}