var system ={
	win : false,
	mac : false,
	xll : false
};
var p = navigator.platform;
system.win = p.indexOf("Win") == 0;
system.mac = p.indexOf("Mac") == 0;
system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
//跳转语句
var url=document.location.href;
var pcurl="/index";
var moblieurl="/index_m";
var tourl="";

if(system.win||system.mac||system.xll){//跳转到pc
	tourl=url.replace(moblieurl,pcurl);
}else{
	//跳转到手机
	tourl=url.replace(pcurl,moblieurl);
	window.location.href=tourl;
}