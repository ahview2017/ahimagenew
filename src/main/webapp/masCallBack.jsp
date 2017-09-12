<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="org.json.JSONArray" %> 
<%@ page import="org.json.JSONObject" %> 
<%@ page import="com.deepai.photo.common.constant.SysConfigConstant" %> 
<%@ page import="com.deepai.photo.common.listener.SpringContextUtil" %> 
<%@ page import="com.deepai.photo.common.StringUtil" %> 
<%@ page import="com.deepai.photo.service.admin.SysConfigService" %> 
<%
	request.setCharacterEncoding("utf-8");
	String sQueryString = request.getParameter("queryString");
	String sCallBackParam = request.getParameter("callBackParam");
	System.out.println(">>>>>>>>>>>>>>>>>>sCallBackParam:"+sCallBackParam);
	String masId = "0";
	if(sCallBackParam!=null&&!"".equals(sCallBackParam)){
		JSONArray jsonArray = new JSONArray(sCallBackParam);
		int size = jsonArray.length();
		if(size>0){
			JSONObject jsonObj = jsonArray.getJSONObject(0);//如果选中多个视频，只取第一个
			masId = (String)jsonObj.get("masId");
		}
	}
	String appBaseUrl = request.getContextPath();
	//获取系统配置中MAS基础URL
	//SysConfigService sysConfigService =  (SysConfigService)SpringContextUtil.getBean("sysConfigService");
	//System.out.println(">>>>>>>>>>>>>>>>>>sysConfigService:"+sysConfigService);
	//String masBaseUrl = sysConfigService.getDbSysConfig(SysConfigConstant.MAS_BASE_URL,1);
	//String masPlayUrl = "";
	//if(StringUtil.notBlank(masBaseUrl)){
		//masPlayUrl = masBaseUrl+"&method=exPlay&type=vod&id="+masId;
	//}
	//System.out.println(">>>>>>>>>>>>>>>>>>masPlayUrl:"+masPlayUrl);

%>
<script src="admin/assets/libs/jquery/jquery-1.11.3.min.js"></script>
<script language="javascript" >
	var sQueryString = "<%=sQueryString%>";
	var masId = "<%=masId%>";
	var appBaseUrl = "<%=appBaseUrl%>";
	if(masId!="0"){
		$.get(appBaseUrl+"/groupPicCtro/getMasBaseUrl.do",function(resp){
			console.log("success");
			var masBaseUrl = resp.data.masBaseUrl;
			var masPlayUrl = masBaseUrl+"&method=exPlay&type=vod&id="+masId;
			
			//显示视频
			var videodivs = window.opener.document.getElementsByClassName("smt-detail-video");
			if(videodivs.length>0){
				videodivs[0].style.display = "block"; 
			}
			window.opener.document.getElementById("mas-video-box").innerHTML = "<iframe id='mas-video-img' src='"+masPlayUrl+"'></iframe>";
			//将视频ID赋值到INPUT标签
			window.opener.document.getElementById("selmasvideo").value=masId;
			window.close();
		});
	}else{
		window.opener.document.getElementById("mas-video-box").innerHTML ="";
		alert("请选择需要上传的视频！");
		window.close();
	}
	
	
		
</script>