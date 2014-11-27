// Worker与页面的通讯功能
function sendStatus(statusText){
	postMessage({"type":"status", "statusText":statusText});
}

function messageHandler(e){
	var messageType = e.data.type;
	switch(messageType){
	case ("blur"):
		sendStatus("Worker started blur on data in range: "+ d.data.startX+"-"+(e.data.startX+e.data.width));
		var imageData = e.data.imageData;
		imageData = boxBlur(imageData, e.data.width, e.data.height, e.data.startX);
		
		postMessage({"type": "process", "imageData": imageData, "widht":e.data.widht, "height":e.data.height, "startX":e.data.startX});
		
		sendStatus("Finished blur on in range: "+ e.data.startX + "="+ (e.data.width+e.data.startX));
		break;
	default:
		sendStatus("Worker got message: "+ e.data);
			
	}
}

addEventListener('message', messageHandler,true);

