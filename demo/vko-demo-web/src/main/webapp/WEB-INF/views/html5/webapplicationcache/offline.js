/*
 * 记录window.applicationCache 触发的每个事件
 */
window.applicationCache.onchecking = function(e){
	log("Checking for application update");
}

window.applicationCache.onupdate = function(e){
	log("No application update found");
}

window.applicationCache.onupdateready = function(e){
	log("Application update ready");
}

window.applicationCache.onobsolete = function(e){
	log("Application obsolete");
}

window.applicationCache.ondownloading = function(e){
	log("Downloading application update");
}

window.applicationCache.oncached = function(e){
	log("Application cached");
}

window.applicationCache.onerror = function(e){
	log("Application cache error");
}

// 添加离线事件处理程序
window.addEventListener('online', function(){
	log("Online");
}, true);

window.addEventListener('offline', function(){
	log("Offline");
}, true);

/*
 *  将applicationCache 状态代码转换成消息
 */
showCacheStatus = function(n){
	statusMessage = ["Uncahed", "Idle", "Checking", "Downloading", "Update ready", "Obsolete"];
	return statusMessage[n];
}

install = function (){
	log("Checking for updates");
	try{
		window.applicationCache.update();
	} catch (e){
		applicationCache.onerror();
	}
}

onload = function (e){
	// 检测所需功能的浏览器支持情况
	if(!window.applicationCache){
		log("HTML5 Offline Applications are not supported in your browser.");
		return;
	}
	
	if(!window.navigator.geolocation){
		log("HTML5 Geolocation is not supported in your browser.");
		return;
	}
	
	if(!window.localStorage){
		log("HTML5 Local Storage not supported in your browser.");
		return;
	}
	
	log("Initial cache status: " + showCacheStatus(window.applicationCache.status));
	document.getElementById("installButton").onclick = checkFor;
}

