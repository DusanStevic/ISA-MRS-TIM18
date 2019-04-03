/**
 * 
 */

var update_user_url="api/airlines";

$(document).on('submit', "#updateAirlineForm", function(e){
	e.preventDefault();
	var id = $('#identifikator').val();
	var name=$('#ime').val();
	var address=$('#adresa').val();
	var description=$('#opis').val();
	$.ajax({
		type:'PUT',
		url:update_user_url+"/"+id,
		contentType:'application/json',
		dataType:'text',
		data:airlineToJSON(id,name,address,description),
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
			window.location.replace("airlineAdministrator.html");
		}
			, error: function(XMLHttpRequest,textStatus, errorThrown) 
				{alert("Ne postoji Airline sa unetim ID"+errorThrown);
			}
	});
});

function airlineToJSON(id,name,address,description){
	return JSON.stringify({
		"id":id,
		"name":name,
		"address":address,
		"description":description
	});
}