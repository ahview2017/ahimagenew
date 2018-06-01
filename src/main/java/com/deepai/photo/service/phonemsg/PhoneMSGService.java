package com.deepai.photo.service.phonemsg;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpMassSMSRecord;
import com.deepai.photo.bean.CpPhoneMsg;
import com.deepai.photo.bean.CpPhoneMsgExample;
import com.deepai.photo.bean.CpPhoneMsgExample.Criteria;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.util.io.excel.ExcelReader;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.util.PhoneMSGUtils;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpMassSMSRecordMapper;
import com.deepai.photo.mapper.CpPhoneMsgMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.CpUserRoleMapper;
import com.deepai.photo.mapper.EnGroupManagementMapper;
import com.deepai.photo.service.admin.SysConfigService;

/**
 * * @author huqiankai: *
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class PhoneMSGService {
	private Logger logger=Logger.getLogger(PhoneMSGService.class) ;
	@Autowired
	private CpPhoneMsgMapper cpPhoneMsgMapper;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpUserRoleMapper cpUserRoleMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
    private CpMassSMSRecordMapper cpMassSMSRecordMapper;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private EnGroupManagementMapper groupManagementMapper;
	@Autowired
    private PhoneMSGUtils phoneMSGUtils;
    
	@Value("${phonseUrl}")
	private String phoneUrl;
	
	public void add(CpPhoneMsg cpPhoneMsg2) {
		cpPhoneMsgMapper.insertSelective(cpPhoneMsg2);
	}
	

	public CpPhoneMsg detail(int id) {
		return cpPhoneMsgMapper.selectByPrimaryKey(id);
	}

	public void delete(int id) {
		cpPhoneMsgMapper.deleteByPrimaryKey(id);
	}

	public List<CpPhoneMsg> show() {
		CpPhoneMsgExample example = new CpPhoneMsgExample();
		Criteria criteria = example.createCriteria();
		List<CpPhoneMsg> list = cpPhoneMsgMapper.selectByExample(example);
		return list;
	}

	public List<CpPhoneMsg> search(String search,String sender,Integer status,String timeFrom,String timeTo) throws ParseException{
		CpPhoneMsgExample example = new CpPhoneMsgExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(search)) {
			search="%"+search+"%";
			criteria.andContentLike(search);
		}
		if (StringUtils.isNotBlank(sender)) {
			sender="%"+sender+"%";
			criteria.andSenderLike(sender);
		}
		if(status!=null){
			criteria.andStatusEqualTo(status);
		}
		if (StringUtils.isNotBlank(timeFrom)&&StringUtils.isNotBlank(timeTo)) {
			 SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
			 Date from = sim.parse(timeFrom);
			 Date to = sim.parse(timeTo);
			 Calendar calendar = new GregorianCalendar(); 
		     calendar.setTime(to); 
		     calendar.add(calendar.DATE,1);
		     to=calendar.getTime();   
			 criteria.andCreateTimeBetween(from, to);
		 }
		return cpPhoneMsgMapper.selectByExample(example);
	}

	public  String sendSMS(String strmobiles,String strContent,String account,String password) throws UnsupportedEncodingException  {
		HttpClient client = new HttpClient();
		
		String url= phoneUrl;
		String mobiles = strmobiles;//手机号码，多个号码使用","分割
		String content = URLEncoder.encode(strContent,"utf-8");;//短信内容
		String needstatus = "true";//是否需要状态报告，需要true，不需要false
		String product = "";//产品ID
		String extno = "";//扩展码
		
		String result="";
//		PostMethod post = new PostMethod(url);
		PostMethod post = new PostMethod("http://192.168.18.158:10001/services/invokenEmsService");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8"); 
	  
		NameValuePair[] data = {
				new NameValuePair("account", account),
				new NameValuePair("pswd", password),
				new NameValuePair("mobile", mobiles),
				new NameValuePair("msg", content),
				new NameValuePair("needstatus", needstatus),
				new NameValuePair("product", product),
				new NameValuePair("extno", extno) };
		
		try {
			post.setRequestBody(data);
			client.executeMethod(post);
			

			result = new String(post.getResponseBodyAsString());

		} catch (IOException e) {
			 
			e.printStackTrace();
		}

		post.releaseConnection();
		
		return result;
		
	}

	
	/**
	 * 上传文件
	 * @return 文件路径
	 */
	public String upOneFile(String filename,int siteId,MultipartFile picFile) {
		//原图存放路径
		String oriAllPath = "";
		try {
			
			filename = filename.substring(0, filename.lastIndexOf("."))+"_"+new SimpleDateFormat("HHmmss").format(new Date())+filename.substring(filename.lastIndexOf("."),filename.length());
			
			String oriPath = sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_FILE_PATH, siteId);
			logger.info("文件oriPath:"+oriPath);
			logger.info("文件filename:"+filename);
			oriPath=initFullPathNoFile(oriPath, filename);
			logger.info("处理过的文件oriPath:"+oriPath);
			//上传原图并返回全途径
			oriAllPath=FileUpload.fileUpName(picFile, oriPath, filename);
			logger.info("生成文件的全路径oriAllPath:"+oriAllPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oriAllPath;
	}
	
	
	/**
	 * 生成文件全路径
	 * @author xiayunan
	 * @date 2018年5月22日
	 * @param root 文件夹路径
	 * @return 文件路径
	 */
	public String initFullPathNoFile(String root,String fileName) {
		String tempp = root;
		Calendar now = Calendar.getInstance();  
        String year = now.get(Calendar.YEAR)+"";   
        String month =String.format("%02d",  now.get(Calendar.MONTH) + 1)+"";   
        String day =String.format("%02d",  now.get(Calendar.DAY_OF_MONTH))+"";  
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			if (tempp.lastIndexOf(CommonConstant.doubleSprit) != tempp.length() - 1) {
				tempp += CommonConstant.doubleSprit;
			}
			tempp=tempp + year + CommonConstant.doubleSprit
					+ month + CommonConstant.doubleSprit+ day + CommonConstant.doubleSprit ;
		}else{
			if (tempp.lastIndexOf(CommonConstant.oneSprit) != tempp.length() - 1) {
				tempp += CommonConstant.oneSprit;
			}
			tempp=tempp + year + CommonConstant.oneSprit
					+ month + CommonConstant.oneSprit+ day + CommonConstant.oneSprit ;
		}
		return tempp;
	}
	
	 /**
	  * 获取短信群发记录中用户，用户组，号码簿的去重用户号码集合
	  * @param recordId
	  * @return
	 * @throws Exception 
	  */
	 public Set<String> getPhoneNumSet(Integer recordId) throws Exception {
		 List<Map<String, Object>> userList = null;
         List<Map<String, Object>> groupUserList = null;
         String[] columnDataArr = null;
         Set<String> phoneNumSet = new HashSet<String>();//用户手机号总容器
         logger.info("开始发送短信!");
         CpMassSMSRecord cpMassSMSRecord =  cpMassSMSRecordMapper.selectMassSMSById(recordId);
         //查询用户号码簿
         String userNumFilePath =  cpMassSMSRecord.getFilePath();
         logger.info("<<<开始查询电话簿号码");
         logger.info("<<<userNumFilePath："+userNumFilePath);
         if(!StringUtil.isBlank(userNumFilePath)){
	         ExcelReader reader = new ExcelReader(userNumFilePath);
	         reader.getAllData(0);
	         if(reader.getSheetCount()>0){
	     		columnDataArr = reader.getColumnData(0,1);
     			for(int i=1;i<columnDataArr.length;i++){
     				logger.info("号码："+columnDataArr[i]);
     				phoneNumSet.add(columnDataArr[i]);
     			}
     		 }
     	 }
     	 //将接收用户加入群发用户ID总容器
     	 String recerverUserNames = cpMassSMSRecord.getPhoneReceiverUser();
     	 
     	 logger.info("<<<recerverUserNames："+recerverUserNames);
     	 
     	 if(!StringUtil.isBlank(recerverUserNames)){
     		String[] recerverUserArr  = recerverUserNames.split(",");
     		 logger.info("<<<recerverUserArr.length："+recerverUserArr.length);
        	 for(int i = 0;i<recerverUserArr.length;i++){
        		 String iUserName = recerverUserArr[i];
        		 logger.info("iUserName:"+iUserName);
        		 Map<String, Object> param = new HashMap<String, Object>();
        		 if(!StringUtil.isBlank(iUserName)){
        			param.put("userName", iUserName);
        			userList = basicMapper.getUserAll(param);
        		 }
        		 logger.info("userList.size():"+userList.size());
        		 if(userList.size()>0){
        			 for(Map<String, Object> map:userList){
        				//userSet.add((Integer)(map.get("ID")));
        				phoneNumSet.add((String)map.get("TEL_BIND"));
        			 }
        		 }
        	 }
     	 }
     	 
     	 
     	 //将群组中所有的用户加入群发用户ID总容器
     	 logger.info("userGroups:"+cpMassSMSRecord.getPhoneReceiverGroup());
     	 String recerverGroupsIds = cpMassSMSRecord.getPhoneReceiverGroup();
     	 if(!StringUtil.isBlank(recerverGroupsIds)){
     		String[] recerverGroupsArr  = recerverGroupsIds.split(",");
     		logger.info("recerverGroupsArr.size:"+recerverGroupsArr.length);
        	 for(String groupId:recerverGroupsArr){
        		groupUserList = groupManagementMapper.getGroupManagementUser(Integer.valueOf(groupId), "");
        		logger.info("groupUserList.size:"+groupUserList.size());
        		if(groupUserList.size()>0){
       			 for(Map<String, Object> map:groupUserList){
       				//userSet.add((Integer)(map.get("ID")));
       				phoneNumSet.add((String)map.get("TEL_BIND"));
       			 }
       		 }
        	 }
     	 }
     	
     	logger.info("用户手机号集合："+phoneNumSet.toString());
     	
     	return phoneNumSet;
	 }
	 
	 
	 /**
	  * 发送短信
	  * @param recordId
	  * @param phoneNumSet
	  * @return 发送状态码
	  */
	 public boolean sendMassSMS(Integer recordId,Set<String> phoneNumSet){
		 CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
		 CpMassSMSRecord cpMassSMSRecord =  cpMassSMSRecordMapper.selectMassSMSById(recordId);
		 String massSmsContent = cpMassSMSRecord.getMsgContent();
	     logger.info("<<<massSmsContent:"+massSmsContent);
 		 //TODO 群发短信
 		 for (String phone : phoneNumSet) {
 			try {
 				cpPhoneMsg.setSender(cpMassSMSRecord.getSender());
     			cpPhoneMsg.setContent(massSmsContent);
                String msgResult = phoneMSGUtils.send(phone, massSmsContent);
                logger.info("<<<msgResult:"+msgResult);
                if (msgResult.equals("1")) {
                    cpPhoneMsg.setStatus(0); // 发送成功
                    add(cpPhoneMsg);
                } else {
                    cpPhoneMsg.setStatus(2); // 发送失败
                    add(cpPhoneMsg);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
 		 }
	     return true;
	 }
	 
	 
	 


	/**
	 * 101	无此用户
	 * 102	密码错
	 * 103	提交过快（提交速度超过流速限制）
	 * 104	系统忙（因平台侧原因，暂时无法处理提交的短信）
	 * 105	敏感短信（短信内容包含敏感词）
	 * 106	消息长度错（>536或<=0）
	 * 107	包含错误的手机号码
	 * 108	手机号码个数错（群发>50000或<=0;单发>200或<=0）
	 * 109	无发送额度（该用户可用短信数已使用完）
	 * 110	不在发送时间内
	 * 111	超出该账户当月发送额度限制
	 * 112	无此产品，用户没有订购该产品
	 * 113	extno格式错（非数字或者长度不对）
	 * 115	自动审核驳回
	 * 116	签名不合法，未带签名（用户必须带签名的前提下）
	 * 117	IP地址认证错,请求调用的IP地址不是系统登记的IP地址
	 * 118	用户没有相应的发送权限
	 * 119	用户已过期
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
//        PhoneMSGService service = new PhoneMSGService();
//        service.sendSMS("13770784187", "测试", "photo", "Ahrb9265_");
		
//		String str = "号码簿.xlsx";
//		
//		System.out.println(str.substring(0, str.lastIndexOf(".")));
//		System.out.println( str.substring(str.lastIndexOf("."),str.length()));
//		System.out.println(str.substring(0, str.lastIndexOf("."))+"_"+new SimpleDateFormat("HHmmss").format(new Date())+str.substring(str.lastIndexOf("."),str.length()));
		
		try{  
			System.out.println("开始进行HTTP请求...");
            URL url = new URL("http://60.173.0.46:8088/temp/checkText");  
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  
            // 设置doOutput属性为true表示将使用此urlConnection写入数据  
            urlConnection.setDoOutput(true);  
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型  
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
            // 得到请求的输出流对象  
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());  
            // 把数据写入请求的Body  
            out.write("中华人民大师的萨达撒大所撒大所多法轮功");  
            out.flush();  
            out.close(); 
            // 从服务器读取响应  
            InputStream inputStream = urlConnection.getInputStream();  
            String encoding = urlConnection.getContentEncoding();  
            String body = IOUtils.toString(inputStream, encoding);  
            if(urlConnection.getResponseCode()==200){
            	System.out.println("数据返回成功");
            	System.out.println("body:"+body);
            }else{
            	System.out.println("数据返回异常");
            }
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
	
}
