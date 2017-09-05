package com.test.java;

import org.junit.Test;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.common.listener.SpringContextUtil;
import com.deepai.photo.mapper.AboutPictureMapper;

public class TestGetGroupPics {
	
	@Test
	public void test1(){
		AboutPictureMapper aboutPictureMapper = (AboutPictureMapper)SpringContextUtil.getBean("aboutPictureMapper");
		try {
			CpPicGroup group= aboutPictureMapper.selectGroupPics(7369);
			System.out.println(group);
			System.out.println("MasvideoSign:"+group.getMasvideoSign());
			System.out.println("videoID:"+group.getVideoId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
