//开发者：廖星星

//js获取url传递过来的参数
function GetQueryString(name){
	 var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	 var r = window.location.search.substr(1).match(reg);
	 if(r!=null)return  unescape(r[2]); return null;
}

//不足17位的vin码自动填充*号
function addsign(shortcode){
	if(shortcode.length<17){
		for(var a=0;a<(shortcode.length-17);a++){
			shortcode += "*";
		}
	}
	return shortcode;
}
