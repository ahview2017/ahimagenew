package com.deepai.photo.controller.enColumn;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpSensitiveWord;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.controller.admin.ClassificationController;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.enColumn.EnSensitiveWordManageService;
/**
 * 
 * @author clong
 *	敏感词批量上传下载
 */
@Controller
@RequestMapping("enSensitiveWordManage")
public class EnSensitiveWordManageController {

	private Logger log = Logger.getLogger(ClassificationController.class);
	@Autowired
	private SysConfigService sysConfigService;
	
	@Resource
	private EnSensitiveWordManageService enSensitiveWordManageService;
	/**
	 * 通过txt文件批量上传敏感词
	 * @param request
	 * @param cgFile
	 * @return
	 */
	@RequestMapping("upFile")
	@ResponseBody
	public ResponseMessage upFile(HttpServletRequest request, @RequestParam(required = false,value="cgFile")MultipartFile cgFile){
		ResponseMessage result = new ResponseMessage();
		try {
			if (cgFile != null && cgFile.getOriginalFilename().trim().endsWith(".txt")) {
				System.out.println(cgFile.getName()+"--"+cgFile.getSize());
				String filename=cgFile.getOriginalFilename(); 
				Map<String,Object> map = enSensitiveWordManageService.saveFileFromInputStream(request,cgFile.getInputStream(),sysConfigService.getDbSysConfig(SysConfigConstant.ROOT_PIC_PATH,SessionUtils.getSiteId(request)),filename);//保存到服务器的路径
				result.setCode(CommonConstant.SUCCESSCODE);
				result.setMsg(CommonConstant.SUCCESSSTRING);
				result.setOther(map);
			}else{
				result.setCode(CommonConstant.EXCEPTIONCODE);
				result.setMsg("请上传以txt文件");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("批量上传敏感词失败，" + e1.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		return result;
		
	}
	/**
	 * 下载全部敏感词
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("downFile")
	@ResponseBody
	public void downFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String format = dateFormat.format(date);
		 // 1.获取要下载的文件的绝对路径
        String realPath = sysConfigService.getDbSysConfig(SysConfigConstant.SENSITIVEWORD,SessionUtils.getSiteId(request))+ "SensitiveWord-"+format+".txt";
        File file = new File(realPath);
        if (file.exists()&&file.isFile()) {
        	file.delete();
        }
        //生成txt文件
        List<CpSensitiveWord> sensitiveWords = enSensitiveWordManageService.selectAllSensitiveWord();
		BufferedWriter inOut = null;
		try {
			inOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(realPath, true)));
			for (CpSensitiveWord cpSensitiveWord : sensitiveWords) {
				inOut.write(cpSensitiveWord.getWordContent()+ "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        // 2.获取要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
        // 3.设置content-disposition响应头控制浏览器弹出保存框，若没有此句则浏览器会直接打开并显示文件。中文名要经过URLEncoder.encode编码，否则虽然客户端能下载但显示的名字是乱码
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        // 4.获取要下载的文件输入流
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        // 5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        // 6.通过response对象获取OutputStream流
        OutputStream out = response.getOutputStream();
        // 7.将FileInputStream流写入到buffer缓冲区
        while ((len = in.read(buffer)) > 0) {
            // 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            out.write(buffer, 0, len);
        }
        in.close();
		
	}
	
}
