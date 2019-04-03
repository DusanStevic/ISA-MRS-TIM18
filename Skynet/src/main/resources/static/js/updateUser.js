/**
 * 
 */

var update_user_url="api/users";

$(document).on('submit', "#updateUserForm", function(e){
	e.preventDefault();
	var id = $('#identifikator').val();
	var name=$('#ime').val();
	var surname=$('#prezime').val();
	$.ajax({
		type:'PUT',
		url:update_user_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:userToJSON(id,name,surname),
		success:function(data){
			$('.main').empty();
        	$('.main').append('<p>Uspesno ste izmenili profil.</p>');
        	toastr.options = {
					  "closeButton": true,
					  "debug": false,
					  "newestOnTop": false,
					  "progressBar": false,
					  "positionClass": "toast-top-right",
					  "preventDuplicates": false,
					  "onclick": null,
					  "showDuration": "300",
					  "hideDuration": "1000",
					  "timeOut": "5000",
					  "extendedTimeOut": "1000",
					  "showEasing": "swing",
					  "hideEasing": "linear",
					  "showMethod": "fadeIn",
					  "hideMethod": "fadeOut"
					}
			toastr.info('Uspesno ste izmenili profil');
			window.location.replace("user.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("Ne postoji user sa unetim ID"+errorThrown);
			}
	});
});

function userToJSON(id,name,surname){
	return JSON.stringify({
		"id":id,
		"name":name,
		"surname":surname
	});
}