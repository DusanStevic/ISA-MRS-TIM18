/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/airlines',
		dataType:'json',
		success:viewUser
	})
}

function viewUser(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, airline){
		var tr=$('<tr></tr>');
		tr.append('<td>' + airline.id + '</td>'+'<td>' + airline.name + '</td>' + '<td>'
				+ airline.address + '</td>'+ '<td>'+ airline.description + '</td>');
		$('#airline').append(tr);
		
	})
}