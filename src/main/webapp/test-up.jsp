<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-2.0.3.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	$('#click').click(function(){		
		var mobile=$('#mobile').val();
		var password=$('#password').val();
		var UUID=$('#UUID').val();
		//alert(mobile);
		var json={"mobile":mobile,"password":password,"UUID":UUID};
	    $.ajax({ 
	           type:"POST", 
	           url:"http://localhost:8080/mydemo/user.action", 
	           dataType:"json",      
	           contentType:'application/json;charset=utf-8',               
	           data:JSON.stringify(json), 
	           success:function(data){ 
	              alert(data.msg);                     
	           } 
	        });
	});
});	

</script>
</head>

<body>
<form action="${pageContext.request.contextPath }/groupPicCtro/upPic.do" method="post" enctype="multipart/form-data">
<%-- 类型enctype用multipart/form-data，这样可以把文件中的数据作为流式数据上传，不管是什么文件类型，均可上传。--%>
请选择要上传的文件:<input type="file" name="picFiles" multiple>
<input type="submit" value="提交">
</form>
</body>
</html>
