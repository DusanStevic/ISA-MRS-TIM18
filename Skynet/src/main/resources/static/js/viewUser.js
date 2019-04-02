/**
 * 
 */
findAll();
function findAll(){
	$.ajax({
		type:'GET',
		url:'api/users',
		dataType:'json',
		success:viewUser
	})
}

function viewUser(data){
	console.log(data);
	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	
	$.each(list, function(index, user){
		var tr=$('<tr></tr>');
		tr.append('<td>' + user.id + '</td>'+'<td>' + user.name + '</td>' + '<td>'
				+ user.surname + '</td>');
		$('#users').append(tr);
		
	})
}