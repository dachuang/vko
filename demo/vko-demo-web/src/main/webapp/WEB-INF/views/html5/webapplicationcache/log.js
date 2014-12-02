/*
 * 记录日志
 */
log = function(){
	var p = document.getElementById('p');
	var message = Array.prototype.join.call(arguments, " ");
	p.innnerHTML = message;
	document.getElementById('info').appendChild(p);
}

