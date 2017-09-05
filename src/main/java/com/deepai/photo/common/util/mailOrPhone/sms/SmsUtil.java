package com.deepai.photo.common.util.mailOrPhone.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.deepai.photo.common.util.encrypt.MD5;

public class SmsUtil {
	private static final Logger log = Logger.getLogger(SmsUtil.class);

	public static void sendsms(String mobile_code, String phone) throws Exception {
		String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);

		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

		NameValuePair[] data = { new NameValuePair("account", "cf_haanico"),
				// new NameValuePair("password", "izzz0928"),
				// //密码可以使用明文密码或使用32位MD5加密
				new NameValuePair("password", MD5.getMD5Str("aseraser")), new NameValuePair("mobile", phone), new NameValuePair("content", content), };

		method.setRequestBody(data);
		client.executeMethod(method);
		String SubmitResult = method.getResponseBodyAsString();
		Document doc = DocumentHelper.parseText(SubmitResult);
		Element root = doc.getRootElement();

		String code = root.elementText("code");
		log.debug("send sms result_code:" + code);

		if (code.equals("2")) {
			log.info("send sms to " + phone + " successed");
		} else {
			log.error("send sms to " + phone + " failed");
			throw new Exception("验证码发送失败");
		}
	}
	public static void main(String[] args) {
		try {
			sendsms("kkk","18600749749");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
