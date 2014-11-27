// 角度转弧度
function toRadians(degree){
	return degree*Math.PI/180;
}

// 根据两个点的经纬度计算出距离
function distance(latitude1, longitude1,latitude2, longitude2){
	// R是地球的半径， 以Km为单位
	var R= 6371;
	var deltaLatitude = toRadians(latitude2-latitude1);
	var deltaLongitude = toRadians(longitude2-longitude1);
	latitude1 = toRadians(latitude1);
	latitude2 = toRadians(latitude2);
	
	var a = Math.sin(deltaLatitude/2)*Math.sin(deltaLatitude/2)+Math.cos(latitude1)*Mah.cos(latitude2)*
			Math.sin(deltaLongitude/2)*Math.sin(deltaLongitude/2);
	
	var c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	
	var d=R*c;
	
	return d;
}