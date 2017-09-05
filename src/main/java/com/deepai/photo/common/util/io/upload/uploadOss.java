//package com.deepai.photo.common.util.io.upload;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.deepai.photo.common.service.OSSService;
//import com.deepai.photo.common.util.MyStringUtil;
//
//@Component
//public class uploadOss {
//	private static Logger log = LoggerFactory.getLogger(uploadOss.class);
//
//	@Autowired
//	private OSSService ossService;
//
//	//处理多文件上传并返回urls(多个url用英文逗号分隔)
//	public Map<String, Object> uploadPictures(MultipartFile[] files) {
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("flag", true);
//		String urls = "";
//		try{
//			for (int i = 0; i < files.length; i++) {
//				if(files[i].isEmpty()){
//					return map;
//				}
//				String extName = "";
//				if (files[i].getOriginalFilename().lastIndexOf(".") >= 0){
//					extName = files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf("."));
//				}
//				//huzhu-pic 为阿里分配的图片上传文件夹
//				String url = ossService.putObject("huzhu-pic", files[i].getInputStream(), System.currentTimeMillis()+extName);
//				if(MyStringUtil.isEmpty(url)){
//					log.info("文件上传失败");
//					map.put("flag", false);
//					return map;
//				}
//				if(i == 0){
//					urls += url;
//				} else {
//					urls += ("," + url);
//				}
//			}
//			map.put("urls", urls);
//		}catch(Exception e){
//			map.put("flag", false);
//		}		
//		return map;
//	}
//
//}
