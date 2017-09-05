package com.deepai.photo.service.enColumn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepai.photo.bean.CpSensitiveWord;
import com.deepai.photo.bean.CpSensitiveWordExample;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpSensitiveWordMapper;
import com.deepai.photo.mapper.NumOrderMapper;
import com.deepai.photo.service.admin.NumOrderService;

@Service
public class EnSensitiveWordManageService {

	@Autowired
	private NumOrderService orderService;
	
	@Resource
	private CpSensitiveWordMapper cpSensitiveWordMapper;
	//将MultipartFile 转换为File
	public Map<String, Object> saveFileFromInputStream(HttpServletRequest request, InputStream stream,String path,String savefile) throws IOException{      
	       FileOutputStream fs=new FileOutputStream( path + "/"+ savefile);
	       byte[] buffer =new byte[1024*1024];
	       int bytesum = 0;
	       int byteread = 0; 
	       while ((byteread=stream.read(buffer))!=-1)
	       {
	          bytesum+=byteread;
	          fs.write(buffer,0,byteread);
	          fs.flush();
	       } 
	       fs.close();
	       stream.close();  
	       
	       
	       String encoding="GBK";
           File file=new File(path + "/"+ savefile);
           Map<String, Object> map = new HashMap<String,Object>();
           if(file.isFile() && file.exists()){ 
        	   Integer i = 0;
        	   Integer j = 0;
               InputStreamReader inRead = new InputStreamReader(
               new FileInputStream(file),encoding);
               BufferedReader bufferedReader = new BufferedReader(inRead);
               String lineTxt = null;
               while((lineTxt = bufferedReader.readLine()) != null){
            	   CpSensitiveWordExample example = new CpSensitiveWordExample();
            	   example.createCriteria().andWordContentEqualTo(lineTxt.trim());
            	   List<CpSensitiveWord> selectByExample = cpSensitiveWordMapper.selectByExample(example);
            	   if(selectByExample.isEmpty()){
            		   insertSensitiveWord(lineTxt,request);
            		   i++;
            	   }else{
            		   j++;
            	   }
               }
               map.put("successCount", i);
               map.put("failCount", j);
               inRead.close();
           }
		return map;
	}  
	public void insertSensitiveWord(String lineTxt,HttpServletRequest request) {
		CpUser user=SessionUtils.getUser(request);
		CpSensitiveWord sW=new CpSensitiveWord();
	    sW.setWordContent(lineTxt);
	    sW.setMemo(lineTxt);
	    sW.setWordStatus((byte) 0);
		sW.setCreateUser(user.getUserName());
		sW.setCreateTime(new Date());
		sW.setSiteId(SessionUtils.getSiteId(request));
		sW.setUpdateUser(user.getUserName());
		sW.setUpdateTime(new Date());
		orderService.insertSstv(sW);
	}
	public List<CpSensitiveWord> selectAllSensitiveWord() {
		CpSensitiveWordExample example = null;
		// TODO Auto-generated method stub
		return cpSensitiveWordMapper.selectByExample(example);
	}

}
