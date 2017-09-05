<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.deepai.photo.bean.CpPicGroup" %> 
<%@ page import="com.deepai.photo.common.listener.SpringContextUtil" %> 
<%@ page import="com.deepai.photo.mapper.AboutPictureMapper" %> 
<%
	AboutPictureMapper aboutPictureMapper = (AboutPictureMapper)SpringContextUtil.getBean("aboutPictureMapper");
	try {
		CpPicGroup group= aboutPictureMapper.selectGroupPics(7369);
		System.out.println(group);
		System.out.println("id:"+group.getId());
		System.out.println("MasvideoSign:"+group.getMasvideoSign());
		System.out.println("videoID:"+group.getVideoId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
%>

