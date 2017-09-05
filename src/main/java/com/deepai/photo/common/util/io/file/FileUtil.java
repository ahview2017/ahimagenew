package com.deepai.photo.common.util.io.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.common.StringUtil;

public class FileUtil {
	public static String separa = "/";
	
	public static void moveSmallFileFast(File srcFile, File targetFile) {
		Assert.notNull(srcFile);
		Assert.notNull(targetFile);
		copySmallFileFast(srcFile, targetFile);
		srcFile.delete();
	}

	public static void moveFile(File srcFile, File targetFile) {
		Assert.notNull(srcFile);
		Assert.notNull(targetFile);
		copyFile(srcFile, targetFile);
		srcFile.delete();
	}

	public static void moveFile(File srcFile, File targetFile, int bufferSize) {
		Assert.notNull(srcFile);
		Assert.notNull(targetFile);
		copyFile(srcFile, targetFile, bufferSize);
		srcFile.delete();
	}

	public static void copySmallFileFast(File srcFile, File targetFile) {
		Assert.notNull(srcFile);
		Assert.notNull(targetFile);

		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;

		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		try {
			inputStream = new BufferedInputStream(new FileInputStream(srcFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(
					targetFile));
			byte[] dateBuffer = new byte[inputStream.available()];
			try {
				outputStream.close();
				outputStream = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (outputStream != null)
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	public static void copyFile(File srcFile, File targetFile) {
		Assert.notNull(srcFile);
		Assert.notNull(targetFile);
		copyFile(srcFile, targetFile, 1024);
	}

	public static void copyFile(File srcFile, File targetFile, int bufferSize) {
		Assert.notNull(srcFile);
		Assert.notNull(targetFile);

		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;

		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		try {
			inputStream = new BufferedInputStream(new FileInputStream(srcFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(
					targetFile));
			int readCount = 0;
			byte[] dataBuffer = new byte[bufferSize];
			while ((readCount = inputStream.read(dataBuffer)) != -1) {
				outputStream.write(dataBuffer, 0, readCount);
			}

			try {
				outputStream.close();
				outputStream = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (outputStream != null)
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	public static String readTextFile(File file) {
		Assert.notNull(file);

		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		String line = null;
		String lineSeparator = System.getProperty("line.separator");
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			line = reader.readLine();
			while (line != null) {
				buffer.append(line + lineSeparator);
				line = reader.readLine();
			}

			try {
				reader.close();
				reader = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}

				reader = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return buffer.toString();
	}

	public static void writeTextFile(File file, String content) {
		Assert.notNull(file);
		writeTextFile(file, content, true);
	}

	public static void writeTextFile(File file, String content, boolean append) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, append));
			if (append) {
				writer.newLine();
			}

			try {
				if (writer != null) {
					writer.close();
					writer = null;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (IOException e) {
		} finally {
			try {
				if (writer != null) {
					writer.close();
					writer = null;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void createDir(File directory) {
		Assert.notNull(directory);
		if ((directory.exists()) || (directory.mkdirs()))
			return;
		throw new RuntimeException("锟斤拷锟斤拷目录失锟斤拷!");
	}

	public static void delete(File file) {
		Assert.notNull(file);
		if (file.exists())
			if (file.isDirectory())
				deleteDir(file);
			else
				file.delete();
	}

	public static void deleteFiles(List<File> files) {
		for (File file : files)
			delete(file);
	}

	public static void deleteDir(File directory) {
		Assert.notNull(directory);
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				deleteDir(file);
			else {
				file.delete();
			}
		}
		if (!directory.delete())
			throw new RuntimeException("删锟斤拷目录失锟杰ｏ拷");
	}

	public static void copyDir(File srcDir, File targetDir, FileFilter filter,
			int bufferSize) {
		if (!srcDir.exists()) {
			throw new RuntimeException("Source file directory is not exist...");
		}
		if (!srcDir.isDirectory()) {
			throw new RuntimeException(
					"Source abstract file is not a directory...");
		}

		if (!targetDir.exists()) {
			createDir(targetDir);
		}

		File[] files = null;
		if (filter == null)
			files = srcDir.listFiles();
		else {
			files = srcDir.listFiles(filter);
		}

		for (File file : files)
			if (file.isDirectory())
				copyDir(file, new File(targetDir, file.getName()), filter,
						bufferSize);
			else
				copyFile(file, new File(targetDir, file.getName()), 1024);
	}

	public static void moveDir(File srcDir, File targetDir, FileFilter filter,
			int bufferSize) {
		if (!srcDir.exists()) {
			throw new RuntimeException("Source file is not exist...");
		}
		if (!srcDir.isDirectory()) {
			throw new RuntimeException(
					"Source abstract file is not a directory...");
		}

		if (!targetDir.exists()) {
			createDir(targetDir);
		}

		if (srcDir.compareTo(targetDir) == 0) {
			throw new RuntimeException(
					"Source directory and the aimed directory is same.Don't move each other");
		}

		File[] files = null;
		if (filter == null)
			files = srcDir.listFiles();
		else {
			files = srcDir.listFiles(filter);
		}

		for (File file : files)
			if (file.isDirectory()) {
				moveDir(file, new File(targetDir, file.getName()), filter,
						bufferSize);
				deleteDir(file);
			} else {
				moveFile(file, new File(targetDir, file.getName()), bufferSize);
			}
	}

	public static InputStream getFileAsStream(File file) {
		Assert.notNull(file);
		try {
			return file.toURL().openStream();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeFile(File file, byte[] bytes, boolean append) {
		Assert.notNull(file);
		BufferedOutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(new FileOutputStream(file,
					append));
			try {
				outputStream.close();
				outputStream = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (outputStream != null)
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	public static void writeFile(File file, ByteArrayOutputStream outputStream,
			boolean append) {
		Assert.notNull(file);
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file, append));
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (out != null)
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	public static Properties getProperties(File file) {
		Assert.notNull(file);
		Properties properties = new Properties();
		InputStream inputStream = getFileAsStream(file);
		try {
			properties.load(inputStream);
			inputStream.close();
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static File getFileByRelativePath(String relativePath)
			throws URISyntaxException {
		Assert.notNull(relativePath);
		return new File(new File(Thread.currentThread().getContextClassLoader()
				.getResource("").toURI()), relativePath);
	}

	public static void writeFile(MultipartFile file, String url)
			throws Exception {
		// String fileEncode = System.getProperty("file.encoding");
		OutputStream bos = new FileOutputStream(url);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		InputStream stream = file.getInputStream();
		System.out.println("filewrite begin:" + url);
		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		System.out.println("filewrite over:" + url);
		bos.close();
		stream.close();

	}

	public static void mkdir(String fileUrl) {
		File dirPath = new File(fileUrl);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	public static void main(String[] args) throws Exception {
		// FileUtil.mkTarget("D:\\file\\56162\\source\\lsp椤圭洰鍒楄〃.xls", "56162",
		// "lsp椤圭洰鍒楄〃.xls");

//		String url = "http://192.168.2.248:83/index.php?action=fileAnalyze";
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("file_name", "len.txt");
//		map.put("source_language", "en");
//		map.put("target_language", "zh");
//		map.put("id_engine_tm", "100,123,126");
//		map.put("id_glossary", "100,123,126");
//		map.put("id_save_las", "100,123,126");
//		map.put("id_save_glossary", "100,123,126");
		

		//System.err.println("淇℃伅锛�" + executeHttpPost(url, map));
		
		//String url = "http://192.168.2.248:83/index.php?action=tmsFileSplit&fid=13442677&num=3";
		//Map<String, String> map = new HashMap<String, String>();
		//System.err.println("淇℃伅锛�" + executeHttpPost(url, map));
		
		//System.err.println(getXliffName("D:/zzj/zz"));
		
		
		String url = "http://192.168.2.102:81/wordCount.php";
		String jsonStr = "{\"file_name\":\"/home/ubuntu/tmsnfs1/file/20150310115753349_1215W/source/test.txt.xlf\"}";
		System.out.println(executePost(url,jsonStr));

	}
	
	
	public static String executePost(String url,String jsonStr)throws Exception{
		if(url==null){
			return null;
		}
		Map<String, String> map1 = new HashMap<String, String>();
		if(jsonStr!=null){
			JSONObject json = JSONObject.fromObject(jsonStr);
			for(Object k : json.keySet()){
				if(k==null || k=="null"){
					k="";
				}
				Object v = json.get(k);
				if(v==null || v=="null"){
					v="";
				}
				map1.put(k+"", v+"");
			}
		}
		return executeHttpPost(url,map1);
	}
	

	public static String executeHttpPost(String url, Map<String, String> params)
			throws Exception {
		
		BufferedReader in = null;
		try {
			// 瀹氫箟HttpClient
			HttpClient client = new ContentEncodingHttpClient();

			// 瀹炰緥鍖朒TTP鏂规硶
			HttpPost request = new HttpPost(url);

			// 鍒涘缓鍚�/鍊肩粍鍒楄〃
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}

			// 鍒涘缓UrlEncodedFormEntity瀵硅薄
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
			
			request.setEntity(formEntiry);
			// 鎵ц璇锋眰
			HttpResponse response = client.execute(request);
 
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			String result = sb.toString();
			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String translation(String text){
		if(text == null){
			return text;
		}else{
		   return text.replaceAll("<", "&#60;").replaceAll(">", "&#62;")
		   .replaceAll("&","&#38;").replaceAll("\"","&#34;").replaceAll("'", "&#39;");
		}
	}
	
	/**
	 * 生成xliff文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getXliffName(String fileName) throws Exception{
	 
		if(fileName == null){
			return null;
		}
//		int index = fileName.lastIndexOf(".");
//		if(index!=-1){
//			return fileName.subSequence(0, index) + ".xlf";
//		}
		return fileName+".xlf";
	}
	
	/**
	 * 判断文件是否存在。
	 * @param fileName
	 * @return
	 */
	public static boolean IsExist(String fileName) throws Exception{
		if(fileName == null){
			return false;
		}
		File file = new File(fileName);
		if(file.exists()){
			return true;
		}
		return false;
	}
	
	// 使用yyyyMMddHHMMssSSS + "_" + 100000以内随机数
	public static String getNewFileUrl(String url) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMssSSS");
		String addUrlStr = sdf.format(new Date());
		Random r = new Random();
		addUrlStr = addUrlStr + "_" + r.nextInt(100000);
		if (url.indexOf(".") == -1) {
			url = addUrlStr;
		} else {
			// String beginsStr = url.substring(0,url.indexOf("."));
			String end = url.substring(url.indexOf("."));
			url = addUrlStr + end;
		}
		return url;
	}
	
	/**
	 * 获取文件后缀
	 * @param filePath
	 * @return
	 */
	public static String getPostfix(String filePath) {
		if (StringUtil.blank(filePath)) {
			return "";
		}
		if (filePath.contains(".")) {
			return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		}
		return "";
	}
	
	public static void main1(String[] args)throws Exception {
		
		String url = "http://192.168.2.248:81/index.php?action=fileAnalyze";
		String jsonStr = "{\"file_name\":\"/home/ubuntu/tmsnfs1/file/20150310115753349_1215W/source/test.txt.xlf\",\"source_language\":\"zh\",\"target_language\":\"en\"}";
		executePost(url,jsonStr);
		
	}

}