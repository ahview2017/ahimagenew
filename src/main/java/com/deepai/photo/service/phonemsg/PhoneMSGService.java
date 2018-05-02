package com.deepai.photo.service.phonemsg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpEmail;
import com.deepai.photo.bean.CpMassSMSRecord;
import com.deepai.photo.bean.CpPhoneMsg;
import com.deepai.photo.bean.CpPhoneMsgExample;
import com.deepai.photo.bean.CpPhoneMsgExample.Criteria;
import com.deepai.photo.mapper.CpPhoneMsgMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.CpUserRoleMapper;

/**
 * * @author huqiankai: *
 */
@Service
@Transactional(readOnly = false, rollbackFor=Exception.class)
public class PhoneMSGService {
	@Autowired
	private CpPhoneMsgMapper cpPhoneMsgMapper;
	@Autowired
	private CpUserMapper cpUserMapper;
	@Autowired
	private CpUserRoleMapper cpUserRoleMapper;
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
        PhoneMSGService service = new PhoneMSGService();
        service.sendSMS("13770784187", "测试", "photo", "Ahrb9265_");
    }
	
}
