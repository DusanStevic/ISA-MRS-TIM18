var TOKEN_KEY = 'jwtToken';

$(window).on("load",function(){
	if (window.location.href.match('racAdmin-racProfile.html') != null) {
		getHotel();
	}
	else if (window.location.href.match('racAdmin-home.html') != null) {
		checkFirstTime();
	}
	else{
		checkFirstTime();
	}
})

function getRACAdmin() {
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

function getRAC() {
	var token = getJwtToken(TOKEN_KEY);
	if (token) {
		$.ajax({
			type : 'GET',
			url : "/api/getRAC",
			headers : createAuthorizationTokenHeader(TOKEN_KEY),
			dataType : "json",
			success : function(data) {
				if (data == null) {
					alert('Error while finding loged one!');
				} else {
					displayRAC(data);
					getBranch();
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

function getBranch() {
	var token = getJwtToken(TOKEN_KEY);
	if (token) {
		$.ajax({
			type : 'GET',
			url : "/api/getBranchs",
			headers : createAuthorizationTokenHeader(TOKEN_KEY),
			dataType : "json",
			success : function(data) {
				if (data == null) {
					alert('Error while finding loged one!');
				} else {
					displayBranch(data);
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

function getCar() {
	var token = getJwtToken(TOKEN_KEY);
	if (token) {
		$.ajax({
			type : 'GET',
			url : "/api/getCars",
			headers : createAuthorizationTokenHeader(TOKEN_KEY),
			dataType : "json",
			success : function(data) {
				if (data == null) {
					alert('Error while finding loged one!');
				} else {
					displayCar(data);
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

function displayRAC(data){
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$.each(list, function(index, rac){
		localStorage.setItem("racid", rac.id);
		var tr1=$('<tr></tr>');
		tr1.append('<td><h2>' + rac.name + '</h2></td>');
		tr1.append('<td><div class="edit_input"><input type="button" value="Edit info" id="edit" /></div></td><td></td>');
		var tr2=$('<tr></tr>');
		tr2.append('<td><h4>'+ rac.address +'</h4></td>');
		var tr3=$('<tr></tr>');
		tr3.append('<td colspan="2"><div class="middle_container"><p>'+ rac.description +'</p></div></td>');
		var tr4=$('<tr></tr>');
		tr4.append('<td><div><img src='+ rac.image + ' class="image" /></div></td>');
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
		$('#racInfo').append(tr1);
		$('#racInfo').append(tr2);
		$('#racInfo').append(tr3);
		$('#racInfo').append(tr4);
		$('#racInfo').append(tr5);
		
	})
}

function displayBranch(data){
	if(data != null && data != undefined){
		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
		$.each(list, function(index, branch){
			var tr=$('<tr></tr>');
			tr.append('<td><table><tr><td><h3>Name: '+branch.name+' </h3></td></tr>'+'<tr><td><h4>Address: '+branch.address+'</h4></td></tr>'+
			'<tr><td><a href="#" id="viewBranch" name="'+branch.id+'">More details</a></td></tr>'+
			'<tr><td><a href="#" id="editBranch" name="'+branch.id+'">Edit branch</a></td></tr>'+
			'<tr><td><a href="#" id="deleteBranch" name="'+branch.id+'">Delete branch</a></td></tr></table></td>');
			$('#branchDisp').append(tr);
		})
	}
}

function displayCar(data){
	if(data != null && data != undefined){
		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
		$.each(list, function(index, car){
			var tr=$('<tr></tr>');
			tr.append('<td><table><tr><td><h3>Registration number: '+car.registrationNumber+' </h3></td></tr>'+'<tr><td><h4>Type: '+car.type+'</h4></td></tr>'+'<tr><td><h4>Gear: '+car.gear+'</h4></td></tr>'+
			'<tr><td><a href="#" id="viewCar" name="'+car.id+'">More details</a></td></tr>'+
			'<tr><td><a href="#" id="editCar" name="'+car.id+'">Edit car</a></td></tr>'+
			'<tr><td><a href="#" id="deleteCar" name="'+car.id+'">Delete car</a></td></tr></table></td>');
			$('#carDisp').append(tr);
		})
	}
}



$(document).on('submit', "#newBranchForm", function(e){
	e.preventDefault();
	var token = getJwtToken(TOKEN_KEY);
	var name = $('#name').val();
	var address = $('#address').val();
	if(name == "" || address == ""){
		alert("All fields must be filled!");
		return;
	}
	$.ajax({
		type:'POST',
		url:'/api/branch',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToRACBranch(name, address),
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$.each(list, function(index, branch){
				var tr=$('<tr></tr>');
				tr.append('<td><table><tr><td><h3>Name: '+branch.name+' </h3></td></tr>'+'<tr><td><h4>Address: '+branch.address+'</h4></td></tr>'+
				'<tr><td><a href="#" id="viewBranch" name="'+branch.id+'">More details</a></td></tr>'+
				'<tr><td><a href="#" id="editBranch" name="'+branch.id+'">Edit branch</a></td></tr>'+
				'<tr><td><a href="#" id="deleteBranch" name="'+branch.id+'">Delete branch</a></td></tr></table></td>');
				$('#branchDisp').append(tr);
			})
		}
	})
})


$(document).on('submit', "#newCarForm", function(e){
	e.preventDefault();
	var token = getJwtToken(TOKEN_KEY);
	var registrationNumber = $('#registrationNumber').val();
	var type = $('#type').val();
	var gear = $('#gear').val();
	if(registrationNumber == "" || type == "" || gear == ""){
		alert("All fields must be filled!");
		return;
	}
	$.ajax({
		type:'POST',
		url:'/api/car',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToBranchCar(registrationNumber, type, gear),
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
			$.each(list, function(index, car){
				var tr=$('<tr></tr>');
				tr.append('<td><table><tr><td><h3>Registration number: '+car.registrationNumber+' </h3></td></tr>'+'<tr><td><h4>Type: '+car.type+'</h4></td></tr>'+'<tr><td><h4>Gear: '+car.gear+'</h4></td></tr>'+
				'<tr><td><a href="#" id="viewCar" name="'+car.id+'">More details</a></td></tr>'+
				'<tr><td><a href="#" id="editCar" name="'+car.id+'">Edit car</a></td></tr>'+
				'<tr><td><a href="#" id="deleteCar" name="'+car.id+'">Delete car</a></td></tr></table></td>');
				$('#carDisp').append(tr);
			})
		}
	})
})

$(document).on('submit','#editBranchForm',function(){
	var token = getJwtToken(TOKEN_KEY);
	var id = $('#editId').val();
	var name = $('#editName').val();
	var address = $('#editAddress').val();
	$.ajax({
		type:'POST',
		url:'/api/editBranch',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToRACBranchID(id, name, address),
		success:function(data){
			alert("Branch successfully edited!");
			location.reload();
		}
	})
})

$(document).on('click','#editBranch',function(e){
	var token = getJwtToken(TOKEN_KEY);
    var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/api/getBranch/'+id,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, branch){
            	$('#editId').val(branch.id);
            	$('#editName').val(branch.name);
            	$('#editAddress').val(branch.address);
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


$(document).on('submit','#editCarForm',function(){
	var token = getJwtToken(TOKEN_KEY);
	var id = $('#editId').val();
	var registrationNumber = $('#editRegistrationNumber').val();
	var type = $('#editType').val();
	var gear = $('#editGear').val();
	$.ajax({
		type:'POST',
		url:'/api/editCar',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToBranchCarID(id, name, address),
		success:function(data){
			alert("Car successfully edited!");
			location.reload();
		}
	})
})


$(document).on('click','#editCar',function(e){
	var token = getJwtToken(TOKEN_KEY);
    var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/api/getCar/'+id,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, car){
            	$('#editId').val(branch.id);
            	$('#editRegistrationNumber').val(car.registrationNumber);
            	$('#editType').val(car.type);
            	$('#editGear').val(car.gear);

            })
        }
    })
    var modal = document.getElementById('modal4');
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

$(document).on('click','#deleteBranch',function(e){
    var id=$(this).attr("name");
    $.ajax({
        type: 'DELETE',
        url: '/api/deleteBranch/'+id,
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            location.reload();
            e.preventDefault();
        }
    })
})

$(document).on('click','#deleteCar',function(e){
    var id=$(this).attr("name");
    $.ajax({
        type: 'DELETE',
        url: '/api/deleteCar/'+id,
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            location.reload();
            e.preventDefault();
        }
    })
})

$(document).on('click','#viewBranch',function(e){
	var token = getJwtToken(TOKEN_KEY);
	var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/api/getBranch/'+id,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, branch){
            	localStorage.setItem("name", branch.name);
            	localStorage.setItem("address", branch.address);
            })
            window.location.href = "branchInfo.html";
        }
    })
})

$(document).on('click','#viewCar',function(e){
	var token = getJwtToken(TOKEN_KEY);
	var id=$(this).attr("name");
    $.ajax({
        type: 'GET',
        url: '/api/getCar/'+id,
        headers : createAuthorizationTokenHeader(TOKEN_KEY),
        contentType: 'text/plain',
        success: function(data){
            var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, car){
            	localStorage.setItem("registrationNumber", car.registrationNumber);
            	localStorage.setItem("type", car.type);
            	localStorage.setItem("gear", car.gear);
            })
            window.location.href = "carInfo.html";
        }
    })
})

$(window).on("load",function(){
	if (window.location.href.match('branchInfo.html') != null) {
		var name = localStorage.getItem("name");
		var address = localStorage.getItem("address");
    	var tr1=$('<tr></tr>');
        tr1.append('<td>'+
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
        tr2.append('<td><table class="info_table"><tr><td><b>Name: '
        		+name+'$</b></td></tr>'
        		+'<tr><td><b>Address: '+address+'</b></td></tr>'
        		+'</table></td>');
        $('#branchInfo').append(tr1);
        $('#branchInfo').append(tr2);
	}
})


$(window).on("load",function(){
	if (window.location.href.match('carInfo.html') != null) {
		var registrationNumber = localStorage.getItem("registrationNumber");
		var type = localStorage.getItem("type");
		var gear = localStorage.getItem("gear");
    	var tr1=$('<tr></tr>');
        tr1.append('<td>'+
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
        tr2.append('<td><table class="info_table"><tr><td><b>Registration number: '
        		+registrationNumber+'$</b></td></tr>'
        		+'<tr><td><b>Type: '+type+'</b></td></tr>'
        		+'<tr><td><b>Gear: '+gear+'</b></td></tr>'
        		+'</table></td>');
        $('#carInfo').append(tr1);
        $('#carInfo').append(tr2);
	}
})


$(document).on('click','#edit',function(e){
	var token = getJwtToken(TOKEN_KEY);
    $.ajax({
		type:'GET',
		url:'/api/getRAC',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		dataType:'json',
		success:function(data){
			var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
            $.each(list, function(index, rac){
            	$('#racName').val(rac.name);
            	$('#racAdress').val(rac.address);
            	$('#racDesc').val(rac.description);
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

$(document).on('submit','#editRACForm',function(){
	var token = getJwtToken(TOKEN_KEY);
	var name = $('#racName').val();
	var adress = $('#racAdress').val();
	var desc = $('#racDesc').val();
	var image = $('#racImg').val();
	if(name == "" || adress == "" || desc == ""){
		alert("All fields must be filled!");
	}
	$.ajax({
		type:'PUT',
		url:'/api/editRAC',
		headers : createAuthorizationTokenHeader(TOKEN_KEY),
		contentType:'application/json',
		dataType:'json',
		data:inputToCompany(name, adress, desc, image),
		success:function(data){
			alert("Rent-a-car info successfully edited!");
			location.reload();
		}
	})
})

$(document).on('submit', "#uploadImageForm", function(event){
	 event.preventDefault();
	 var formElement = this;
	 var formData = new FormData(formElement);
	 uploadImage(formElement, formData, "/api/editRACImage");
	 event.preventDefault();
})



function inputToRACBranch(name, address){
	return JSON.stringify({
		"name":name,
		"address":address,
	})
}

function inputToRACBranchID(id, name, address){
	return JSON.stringify({
		"id" : id,
		"name":name,
		"address":address,
	})
}

function inputToBranchCar(registrationNumber, type, gear){
	return JSON.stringify({
		"registrationNumber":registrationNumber,
		"type":type,
		"gear":gear,
	})
}

function inputToBranchCarID(id, registrationNumber, type, gear){
	return JSON.stringify({
		"id" : id,
		"registrationNumber":registrationNumber,
		"type":type,
		"gear":gear,
	})
}

function racToJSON(name, adress, desc, image){
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
