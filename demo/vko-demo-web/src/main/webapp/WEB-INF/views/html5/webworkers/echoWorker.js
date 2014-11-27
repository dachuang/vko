function errorHandler(e){
	console.log(e.message,e);
}

addEventListener("message",messageHandler,true);
