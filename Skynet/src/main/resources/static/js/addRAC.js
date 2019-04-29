var add_rac_url="api/racs";

$(document).on('submit', "#addRACForm", function(e){
	e.preventDefault();
	var name=$('#name').val();
	var address=$('#address').val();
	var description=$('#description').val();
	
	$.ajax({
		type:'POST',
		url:add_rac_url,
		contentType:'application/json',
		dataType:'text',
		data:adminToJSON(name, address, description),
		success:function(data){
			window.location.replace("index.html");
		}
	});
})


function adminToJSON(name, address, description){
	return JSON.stringify({
		"name":name,
		"address":address
		"description":description

	});
}