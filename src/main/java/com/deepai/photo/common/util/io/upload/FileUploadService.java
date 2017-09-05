package com.deepai.photo.common.util.io.upload;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Service;

import com.deepai.photo.common.listener.UploadProgressListener;

@Service
/**
 * 文件上传的功能已经实现，可同时上传多个文件，之后待扩充
 * 访问路径  http://IP:8080/wenjin/file/upload.do
 * 客户端关键代码如下：
 * PostMethod filePost = new PostMethod ( url ) ;
 * Part[] parts ={ new FilePart("myfile", new File(file))};
 * filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams() ));
 * int status = new HttpClient().executeMethod(filePost);
 * @author zhangzhizhi
 * @version
 */
public class FileUploadService {
	public List < FileItem > upload(HttpServletRequest request) {
		List < FileItem > items = null;
		try {
//			request.getServletContext();
			int maxFileSize = 500 * 1024 * 1024;
			int maxMemSize = 400 * 1024 * 1024;
			String contentType = request.getContentType();
			if ((contentType.indexOf("multipart/form-data") >= 0)) {
				DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
				diskFileItemFactory.setSizeThreshold(maxMemSize);
				diskFileItemFactory.setRepository(new File("Tmp"));
				ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
				servletFileUpload.setSizeMax(maxFileSize);
				UploadProgressListener progressListener = new UploadProgressListener();
				//放入session中，下次访问可以取出，获取文件进度
		        request.getSession().setAttribute("progress", progressListener);
		        servletFileUpload.setProgressListener(progressListener);

				items = servletFileUpload.parseRequest(new ServletRequestContext(request));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
}
