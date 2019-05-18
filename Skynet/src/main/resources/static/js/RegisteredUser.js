/**
 * 
 */
var TOKEN_KEY = 'jwtToken';

$(document).on('click', '#logout', function(e) {
	e.preventDefault();
	removeJwtToken(TOKEN_KEY);
	window.location.href = "index.html";
})
