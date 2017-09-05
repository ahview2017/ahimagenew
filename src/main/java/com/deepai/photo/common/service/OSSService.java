//package com.deepai.photo.common.service;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//
//import javax.imageio.ImageIO;
//
//import net.coobird.thumbnailator.Thumbnails;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.ObjectMetadata;
//import com.deepai.photo.common.util.MyStringUtil;
//import com.deepai.photo.common.util.html.UrlUtil;
//
//public class OSSService {
//	private OSSClient client;
//
//	public OSSClient getClient() {
//		return client;
//	}
//
//	public void setClient(OSSClient client) {
//		this.client = client;
//	}
//
//	/**
//	 * 保存对象
//	 * 
//	 * @param bucketName
//	 *            名称
//	 * @param inputStream
//	 *            输入流
//	 * @param key
//	 *            键值
//	 * @return
//	 */
//	public String putObject(String bucketName, InputStream inputStream, String key) {
//		try {
//			ObjectMetadata objectMeta = new ObjectMetadata();
//			objectMeta.setContentLength(inputStream.available());
//			client.putObject(bucketName, key, inputStream, objectMeta);
//			String url = "http://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + key;
//			return url;
//		} catch (OSSException oe) {
//			oe.printStackTrace();
//		    System.out.println("Caught an OSSException, which means your request made it to OSS, "
//		            + "but was rejected with an error response for some reason.");
//		    System.out.println("Error Message: " + oe.getErrorCode());
//		    System.out.println("Error Code:       " + oe.getErrorCode());
//		    System.out.println("Request ID:      " + oe.getRequestId());
//		    System.out.println("Host ID:           " + oe.getHostId());
//		    return null;
//		} catch (ClientException ce) {
//			ce.printStackTrace();
//		    System.out.println("Caught an ClientException, which means the client encountered "
//		            + "a serious internal problem while trying to communicate with OSS, "
//		            + "such as not being able to access the network.");
//		    System.out.println("Error Message: " + ce.getMessage());
//		    return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 上传图片到OSS，同时生成指定尺寸缩略图，缩略图名称为源文件前添加s_
//	 * 
//	 * @param bucketName
//	 * @param inputStream
//	 * @param key
//	 * @param width
//	 * @param height
//	 * @return
//	 */
//	public String putObjectWithThumbnail(String bucketName, InputStream inputStream, String key, int width, int height) {
//		String url = this.putObject(bucketName, inputStream, key);
//		if (MyStringUtil.isNotEmpty(url)) {
//			String type = ".png.jpg.jpeg.bmp";
//			int p = key.indexOf(".");
//			if (p > 0) {
//				String ext = key.substring(p + 1);
//				if (type.contains(ext.toLowerCase())) {
//					int index = key.lastIndexOf("/");
//					String keyT;
//					if (index >= 0){
//						keyT = key.substring(0,index+1)+"s_" + key.substring(index+1);
//					}
//					else{
//						keyT = "s_" + key;
//					}
//					URL u = UrlUtil.getUrl(url);
//					if (u != null) {
//						try {
//							BufferedImage image = Thumbnails.of(u).size(width, height).asBufferedImage();
//							ByteArrayOutputStream os = new ByteArrayOutputStream();
//							ImageIO.write(image, ext, os);
//							InputStream thumbnailIs = new ByteArrayInputStream(os.toByteArray());
//							this.putObject(bucketName, thumbnailIs, keyT);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//		return url;
//	}
//
//	/**
//	 * 删除oss上保存的对象
//	 * 
//	 * @param bucketName
//	 * @param key
//	 */
//	public void deleteObject(String bucketName, String key) {
//		client.deleteObject(bucketName, key);
//	}
//
//	/**
//	 * 删除oss上保存的对象
//	 * 
//	 * @param bucketName
//	 * @param key
//	 */
//	public void deleteObjectByUrl(String url) {
//		if (url != null) {
//			try {
//				String bucketName = url.substring(7, url.indexOf('.'));
//				String key = url.substring(url.indexOf(".com/") + 5);
//				client.deleteObject(bucketName, key);
//			} catch (Exception e) {
//			}
//		}
//	}
//
//	/**
//	 * 删除oss上含有缩略图的图片，同时删除
//	 * 
//	 * @param bucketName
//	 * @param key
//	 */
//	public void deletePicWithThumbnialByUrl(String url) {
//		this.deleteObjectByUrl(url);
//		// 删除缩略图
//		int p = url.lastIndexOf("/");
//		if (p > 0) {
//			url = url.substring(0, p + 1) + "s_" + url.substring(p + 1);
//			this.deleteObjectByUrl(url);
//		}
//	}
//
//}
