/*
 *	History				Who				What
 *  2017年9月6日			liujinfeng			Created.
 */
package com.deepai.photo.controller.util;

import java.net.URL;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.service.admin.SysConfigService;
import com.ue.ems.common.service.MD5;
import com.ue.webservice.SendMsgComponent;
import com.ue.webservice.SendMsgComponentLocator;
import com.ue.webservice.SendMsgComponentPortType;

import net.sf.json.JSONObject;

/**
 * Title: TRS 内容协作平台（TRS WCM） <BR>
 * Description: <BR>
 * 短信工具类 <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 * 
 * @author liu.jinfeng
 * @version 1.0
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class PhoneMSGUtils {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(PhoneMSGUtils.class);

    @Value("${phonseUrl}")
    private String phoneUrl;

    @Autowired
    private SysConfigService sysConfigService;

    /** 注册发送验证码 */
    public static final String TYPE_SEND_CODE = "1";

    /** 忘记密码，找回密码 */
    public static final String TYPE_FORGET_CODE = "2";

    /** 登录验证码 */
    public static final String TYPE_LOGIN_CODE = "3";

    /** 注册成功后发送短信 */
    public static final String TYPE_SUCCESS_CODE = "4";

    /**
     * 发送短信通知
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月7日 下午2:35:22
     * @param phone
     * @param content
     * @return
     * @throws Exception
     */
    public String sendSMSMsg(String phone, String content) throws Exception {

        return send(phone, content);
    }

    /**
     * 群发短信
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月7日 下午2:46:21
     * @param phones
     *            多个手机号英文逗号分隔
     * @param content
     * @return
     * @throws Exception
     */
    public JSONObject sendSMSGroups(String phones, String content)
            throws Exception {

        return sendSMS(phones, content);
    }

    /**
     * 发送手机短信
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月6日 下午9:50:58
     * @param phone
     *            手机号码
     * @param type
     *            短信类型 1:注册获取验证码
     * @param vilidate 
     * @return {"code":"1","msg":"内容"} code:0失败，1成功
     * @throws Exception
     */
    public JSONObject sendMsg(String phone, String type, Integer vilidate) throws Exception {
        JSONObject result = null;
        switch (type) {
        case TYPE_SEND_CODE:
            result = sendPhoneCode(phone, TYPE_SEND_CODE,null);
            break;
        case TYPE_LOGIN_CODE:
            result = sendPhoneCode(phone, TYPE_LOGIN_CODE,vilidate);
            break;
        case TYPE_FORGET_CODE:
            result = sendPhoneCode(phone, TYPE_FORGET_CODE,vilidate);
            break;
        default:
            break;
        }
        return result;
    }

    /**
     * @Description: 发送手机验证码 <BR>
     * @author liu.jinfeng
     * @date 2017年9月6日 下午9:54:13
     * @param phone
     *            手机号码
     * @param type
     *            短信类型
     * @param vilidate 
     * @return
     * @throws Exception
     */
    private JSONObject sendPhoneCode(String phone, String type, Integer vilidate)
            throws Exception {
        String sContent = "";
        switch (type) {
        case TYPE_SEND_CODE:
            sContent = sysConfigService
                    .getDbSysConfig(SysConfigConstant.MSG_SEND_CODE, 1);
            break;
        case TYPE_LOGIN_CODE:
            sContent = sysConfigService
                    .getDbSysConfig(SysConfigConstant.MSG_LOGIN_CODE, 1);
            break;
        case TYPE_FORGET_CODE:
            sContent = sysConfigService
                    .getDbSysConfig(SysConfigConstant.MSG_FORGET_CODE, 1);
            break;

        default:
            break;
        }
        // sysConfigService.

        if(vilidate == null){
        // 6位随机验证码
        vilidate = (int) ((Math.random() * 9 + 1) * 100000);
        }
        sContent = String.format(sContent, vilidate);
//        logger.info("发送内容是：" + sContent);
        // 发送信息
        String code = send(phone, sContent);

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("msg", code.equals("1") ? String.valueOf(vilidate) : "发送失败");
        return result;
    }

    /**
     * 
     * @Description:发送短信 <BR>
     * @author liu.jinfeng
     * @date 2017年9月6日 下午9:58:18
     * @param phone
     *            手机号
     * @param content
     *            TODO内容
     * @return 1 成功 ；0 失败
     * @throws Exception
     */
    private String send(String phone, String content) throws Exception {
        URL endpoint = new URL(phoneUrl);

        SendMsgComponent service = new SendMsgComponentLocator();
        SendMsgComponentPortType client = service
                .getSendMsgComponentHttpPort(endpoint);

        // 获取用户名密码
        String[] attr = getPhoneAttr();

        Date date = new Date();
        StringBuffer buff = new StringBuffer();
        buff.append("<?xml version='1.0' encoding='UTF-8'?>");
        buff.append("<ESB>");
        buff.append("<time>" + date.getTime() + "</time>"); // <!-- 时间 -->
        buff.append("<serviceCount>1</serviceCount>");// <!-- 发送总数 -->
        // 按实际情况填写分配给自己的用户名
        buff.append("<userCode>" + attr[0] + "</userCode>");// <!-- 用户名 -->
        // 按实际情况填写分配给自己的密码,并需MD5加密,且英文大写
        buff.append("<password>" + MD5.md5for32(attr[1]) + "</password>");
        buff.append("<dataSign></dataSign>");// <!-- 数字签名 -->
        buff.append("<extend></extend>");// <!-- 扩展字段 -->
        // 个性短信内容也要测试一下
        buff.append("<content>" + content + "</content>");// <!--
        // 填写号码列表,多个号码使用多个NumberSet节点
        buff.append("<NumberSets>");// <!-- 号码列表 -->
        buff.append("<NumberSet>");//
        buff.append("<phone>" + phone + "</phone>");// <!-- 号码 -->
        buff.append("</NumberSet>");//
        buff.append("</NumberSets>");//
        buff.append("<TO>EMS</TO>");// <!-- 到哪个系统 -->
        buff.append("<FROM>视觉安徽网</FROM>");// <!-- 来自哪个系统 -->
        buff.append("</ESB>");//

        String str = client.invokenMsgSend(buff.toString());
        return getCodeSendMsg(str);
    }

    /**
     * 群发短信
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月7日 下午2:50:38
     * @param phones
     * @param content
     * @return
     * @throws Exception
     */
    private JSONObject sendSMS(String phones, String content) throws Exception {
        if (phones == null || phones.trim().length() == 0) {
            return null;
        }
        String[] sPhones = phones.split(",");

        URL endpoint = new URL(phoneUrl);

        SendMsgComponent service = new SendMsgComponentLocator();
        SendMsgComponentPortType client = service
                .getSendMsgComponentHttpPort(endpoint);

        // 获取用户名密码
        String[] attr = getPhoneAttr();

        Date date = new Date();
        StringBuffer buff = new StringBuffer();
        buff.append("<?xml version='1.0' encoding='UTF-8'?>");
        buff.append("<ESB>");
        buff.append("<time>" + date.getTime() + "</time>"); // <!-- 时间 -->
        buff.append("<serviceCount>1</serviceCount>");// <!-- 发送总数 -->
        // 按实际情况填写分配给自己的用户名
        buff.append("<userCode>" + attr[0] + "</userCode>");// <!-- 用户名 -->
        // 按实际情况填写分配给自己的密码,并需MD5加密,且英文大写
        buff.append("<password>" + MD5.md5for32(attr[1]) + "</password>");
        buff.append("<dataSign></dataSign>");// <!-- 数字签名 -->
        buff.append("<extend></extend>");// <!-- 扩展字段 -->
        // 个性短信内容也要测试一下
        buff.append("<content>" + content + "</content>");// <!--
        // 填写号码列表,多个号码使用多个NumberSet节点
        buff.append("<NumberSets>");// <!-- 号码列表 -->

        for (String phone : sPhones) {
            buff.append("<NumberSet>");//
            buff.append("<phone>" + phone + "</phone>");// <!-- 号码 -->
            buff.append("</NumberSet>");//
        }

        buff.append("</NumberSets>");//
        buff.append("<TO>EMS</TO>");// <!-- 到哪个系统 -->
        buff.append("<FROM>视觉安徽网</FROM>");// <!-- 来自哪个系统 -->
        buff.append("</ESB>");//

        String str = client.invokenMsgSend(buff.toString());
        return getCodeSendMsgs(str);
    }

    /**
     * 获取群发返回值
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月7日 下午3:23:11
     * @param xmlCode
     * @return
     * @throws DocumentException
     */
    private JSONObject getCodeSendMsgs(String xmlCode)
            throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlCode);
        Element rootElt = doc.getRootElement(); // 获取根节点

        // 定义返回值
        JSONObject json = new JSONObject();
        if (!(rootElt.elementText("resultCode")).equals("0")) {
            json.put("code", "0");// 失败
            json.put("fail", "");
            return json;
        }

        Element errorElement = rootElt.element("errorSet");
        List<Element> errorElements = errorElement.elements();
        if (errorElements.isEmpty()) {
            json.put("code", "1");// 成功
            json.put("fail", "");
            return json;
        }

        StringBuffer failPhone = new StringBuffer();
        for (Element errorEle : errorElements) {
            failPhone.append(errorEle.getText()).append(",");
        }
        failPhone.setLength(failPhone.length() - 1);

        json.put("code", "1");// 成功
        json.put("fail", failPhone.toString());
        return json;
    }

    private String getCodeSendMsg(String xmlCode) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlCode);
        Element rootElt = doc.getRootElement(); // 获取根节点
        // 0成功 其他失败
        String code = rootElt.elementText("resultCode");
        return code.equals("0") ? "1" : "0";
    }

    private String[] getPhoneAttr() {
        List<String> phoneMSG = sysConfigService.findEmail(
                SysConfigConstant.Phone_username,
                SysConfigConstant.Phone_password);

        String[] attr = new String[2];
        attr[0] = phoneMSG.get(0);// account
        attr[1] = phoneMSG.get(1);// pass

        logger.info(phoneMSG.get(0) + "=====" + phoneMSG.get(1));
        return attr;
    }

    public static void main(String[] args) throws Exception {
        PhoneMSGUtils utils = new PhoneMSGUtils();
        // utils.sendMsg("13770784187", "1");

        // String xml =
        // "<ESB><resultCode>0</resultCode><msg>任务总提交了3条，成功发送了1条，号码重复的0条，号码错误的2条；号码在黑名单的0条；含屏蔽词的0条!</msg><errorSet><errorPhone>17327789013</errorPhone><errorPhone>17370784187</errorPhone></errorSet><blackSet></blackSet><keyWordSet></keyWordSet></ESB>";
        // String xml =
        // "<ESB><resultCode>0</resultCode><msg>任务总提交了3条，成功发送了1条，号码重复的0条，号码错误的2条；号码在黑名单的0条；含屏蔽词的0条!</msg><errorSet></errorSet><blackSet></blackSet><keyWordSet></keyWordSet></ESB>";
        // Object s = utils.getCodeSendMsgs(xml);
        // logger.info(s.toString());

        String[] a = utils.getPhoneAttr();
        for (String s : a) {
            System.out.println(s + "==");
        }
    }
}
