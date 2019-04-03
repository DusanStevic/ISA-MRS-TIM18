/**
 * 
 */
var add_admin_url="api/users";

$(document).on('submit', "#addAdminForm", function(e){
	e.preventDefault();
	var name=$('#ime').val();
	var surname=$('#prezime').val();
	
	$.ajax({
		type:'POST',
		url:add_admin_url,
		contentType:'application/json',
		dataType:'text',
		data:adminToJSON(name, surname),
		success:function(data){
			window.location.replace("index.html");
		}
	});
})


function adminToJSON(name, surname){
	return JSON.stringify({
		"name":name,
		"surname":surname

	});
}