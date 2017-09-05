<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form  action="<%= basePath %>/oldPhotoUpload/photoExcelUpload.do" method="post" enctype="multipart/form-data" >
<table>
	<tr>
		<td>头像</td>
		<td><input type="file" name="zipFile"></td>
	</tr>
	<tr>
		<td><button type="submit">tijiao</button></td>
	</tr>
</table>
</form>

</body>
</html>