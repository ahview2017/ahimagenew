package com.deepai.photo.controller.phonemsg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpMassSMSRecord;
import com.deepai.photo.bean.CpMsgTimer;
import com.deepai.photo.bean.CpPhoneMsg;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.bean.EnGroupManagement;
import com.deepai.photo.common.annotation.LogInfo;
import com.deepai.photo.common.annotation.SkipAuthCheck;
import com.deepai.photo.common.annotation.SkipLoginCheck;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.exception.InvalidHttpArgumentException;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.MyStringUtil;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.encrypt.Coder;
import com.deepai.photo.common.util.encrypt.MD5Util2;
import com.deepai.photo.common.validation.CommonValidation;
import com.deepai.photo.controller.util.PhoneMSGUtils;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpMassSMSRecordMapper;
import com.deepai.photo.mapper.CpMsgTimerMapper;
import com.deepai.photo.mapper.CpUserMapper;
import com.deepai.photo.mapper.EnGroupManagementMapper;
import com.deepai.photo.service.admin.SysConfigService;
import com.deepai.photo.service.admin.UserRoleRightService;
import com.deepai.photo.service.phonemsg.PhoneMSGService;

import net.sf.json.JSONObject;

/**
 * * @author huqiankai: *
 */
@Controller
@RequestMapping("/phonemsg")
public class PhoneMSGController {
    private Logger log = Logger.getLogger(PhoneMSGController.class);

    @Autowired
    private UserRoleRightService userRoleRightService;

    @Autowired
    private PhoneMSGService phoneMSGService;

    @Autowired
    private SysConfigService configService;

    @Autowired
    private CpUserMapper cpUserMapper;
    
    @Autowired
    private CpMassSMSRecordMapper cpMassSMSRecordMapper;

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Autowired
    private PhoneMSGUtils phoneMSGUtils;
    @Autowired
	private CpBasicMapper basicMapper;
    
    @Autowired
	private EnGroupManagementMapper groupManagementMapper;
    
    @Autowired
	private CpMsgTimerMapper cpMsgTimerMapper;
    

    private String result2 = "false";

    private int[] uid;

    private int[] teamId;

    /**
     * 注册发送短信
     * 
     * @param request
     * @param userName
     *            用户名
     * @param password
     *            密码
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendMessageByUserName")
    @SkipLoginCheck
    public Object sendMessageByUserName(HttpServletRequest request,
            String userName, String password, Integer roleId) {
        CommonValidation.checkParamBlank(userName, "用户名");
        CommonValidation.checkParamBlank(password, "密码");
        ResponseMessage result = new ResponseMessage();
        CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
        try {
            CpUser cpUser = cpUserMapper.findUserByUserName(userName);
            String pwd_db = cpUser.getPassword();// 数据库存的密码
            String pwd_md5 = Coder.reverse(Coder.decryptBASE64(pwd_db));// md5密码

            if (pwd_md5.equals(password)) {
//                String content = "安徽视觉网帐户注册通知！亲爱的用户" + userName + "：您好！ "
//                        + "感谢您注册安徽视觉网的账户，您的注册邮箱号为：" + cpUser.getEmailBind();
//
//                if (roleId != null && roleId == 3) {
//                    // 摄影师
//                    content += ",您的作者名是：" + cpUser.getAuthorName();
//                }
//                content += ",请您核实信息！";
                String sContent = configService
                        .getDbSysConfig(SysConfigConstant.MSG_SUCCESS_CODE, 1);
                sContent = String.format(sContent, userName,cpUser.getEmailBind());
                
//                log.info("发送短信为 ："+sContent);
                // 添加短信信息
                cpPhoneMsg.setSender(userName);
                cpPhoneMsg.setContent(sContent);

                try {
                    String msgResult = phoneMSGUtils
                            .sendSMSMsg(cpUser.getTelBind(), sContent);
                    if (msgResult.equals("1")) {
                        cpPhoneMsg.setStatus(0); // 发送成功
                        phoneMSGService.add(cpPhoneMsg);
                        result.setCode(CommonConstant.SUCCESSCODE);
                        result.setMsg(CommonConstant.SUCCESSSTRING);
                    } else {
                        cpPhoneMsg.setStatus(2); // 发送失败
                        phoneMSGService.add(cpPhoneMsg);
                        result.setCode(CommonConstant.EXCEPTIONCODE);
                        result.setMsg("短信发送失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.setCode(CommonConstant.EXCEPTIONCODE);
                    cpPhoneMsg.setStatus(2); // 发送失败
                    phoneMSGService.add(cpPhoneMsg);
                    log.error("短信发送失败， ", e);
                    result.setMsg("短信发送失败");
                }
            } else {
                result.setCode(CommonConstant.FAILURECODE);
                result.setMsg("该用户不存在或正在申请中");
            }
        } catch (Exception e1) {
            cpPhoneMsg.setStatus(2); // 发送失败
            phoneMSGService.add(cpPhoneMsg);
            e1.printStackTrace();
            log.error("短信发送失败， ", e1);
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 发送短信
     * 
     * @param request
     * @param response
     * @param uid
     * @param content
     * @param teamId
     * @param status
     * @return
     * @throws HttpException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/sendPhoneMSG")
    public Object send(HttpServletRequest request, String uids, String content,
            String teamIds, int status) throws HttpException, IOException {
        ResponseMessage result = new ResponseMessage();
        // 目前前端暂时没有传uids
        try {
            if (StringUtils.isNotBlank(uids)) {
                String[] uid2 = uids.split(",");
                uid = new int[uid2.length];
                for (int i = 0; i < uid.length; i++) {
                    uid[i] = Integer.parseInt(uid2[i]);
                }
            }
            if (StringUtils.isNotBlank(teamIds)) {
                String[] teamId2 = teamIds.split(",");
                teamId = new int[teamId2.length];
                for (int i = 0; i < teamId2.length; i++) {
                    teamId[i] = Integer.parseInt(teamId2[i]);
                }
            }
            if (status == 1) { // 保存
                save(uid, content, teamId, request, status, "");
                result.setCode(CommonConstant.SUCCESSCODE);
                result.setMsg(CommonConstant.SUCCESSSTRING);
                return result;
            }
            String roseid = "";
            String userid = "";
            List<Integer> list2 = new ArrayList<Integer>();
            if (teamId != null && teamId.length > 0) {
                for (int i : teamId) {
                    List<Integer> id = userRoleRightService.findTeamID(i);
                    list2.addAll(id);
                    String roseid1 = userRoleRightService.getRoseName(i);
                    roseid = roseid + "(组)" + roseid1 + ":";
                }
            }
            if (uid != null && uid.length > 0) {
                for (int i : uid) {
                    list2.add(i);
                    String findNameByUid = userRoleRightService
                            .findNameByUid(i);
                    userid = userid + findNameByUid + ":";
                }
            }
            try {
                List<String> phoneN = userRoleRightService
                        .findPhoneByUid(list2);
                // 群发设置，为了防止人数过多，将列表分拆，每组50000，不足的单独处理
                int num = phoneN.size() / 50000;
                if (!(phoneN.size() % 50000 == 0)) {
                    num++;
                }
                // String msgResult = "";
                JSONObject msgResult = new JSONObject();
                String phones = "";
                for (int i = 0; i < num - 1; i++) {
                    phones = StringUtils.join(
                            phoneN.subList(0 + i * 5000, 49999 + i * 50000),
                            ",");
                    // msgResult = phoneMSGService.sendSMS(phones, content,
                    // phoneMSG.get(0), phoneMSG.get(1));
                    msgResult = phoneMSGUtils.sendSMSGroups(phones, content);
                    // log.info("短信发送结果----------> " + msgResult);
                    // msgResult = msgResult.replace('\n', ',');
                    // result2 = msgResult.split(",")[1];
                    result2 = msgResult.getString("code");
                    if ("1".equalsIgnoreCase(result2)) { // 如果发送成功就保存到数据库
                        if (roseid.length() > 1) {
                            roseid = roseid.substring(0, roseid.length() - 1);
                        }
                        if (userid.length() > 1) {
                            userid = userid.substring(0, userid.length() - 1);
                        }

                        // if(msgResult.getString("fail")==null||msgResult.getString("fail")==""){
                        //
                        // }
                        String failsNum = msgResult.getString("fail");

                        CpUser user = SessionUtils.getUser(request);
                        CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
                        cpPhoneMsg.setSender(user.getUserName());
                        cpPhoneMsg.setPhoneReciver(teamIds);// 这里发送的是电话号码（改为teamId）
                        cpPhoneMsg.setPhoneReciverRole(roseid);
                        cpPhoneMsg.setStatus(
                                failsNum.indexOf(user.getTelBind()) < 0 ? 0
                                        : 2); // 发送成功
                        cpPhoneMsg.setContent(content);
                        phoneMSGService.add(cpPhoneMsg);
                        result.setCode(CommonConstant.SUCCESSCODE);
                        result.setMsg(CommonConstant.SUCCESSSTRING);
                    } else {
                        save(uid, content, teamId, request, 2, teamIds); // 发送失败保存
                        result.setCode(CommonConstant.EXCEPTIONCODE);
                        result.setMsg("短信发送失败");
                        break;
                    }
                }

                phones = StringUtils.join(
                        phoneN.subList(0 + (num - 1) * 50000, phoneN.size()),
                        ",");
                // msgResult = phoneMSGService.sendSMS(phones, content,
                // phoneMSG.get(0), phoneMSG.get(1));
                // log.info("短信发送结果----------> " + msgResult);
                // msgResult = msgResult.replace('\n', ',');

                // result2 = msgResult.split(",")[1];
                msgResult = phoneMSGUtils.sendSMSGroups(phones, content);
                result2 = msgResult.getString("code");
                if ("1".equalsIgnoreCase(result2)) { // 如果发送成功就保存到数据库
                    if (roseid.length() > 1) {
                        roseid = roseid.substring(0, roseid.length() - 1);
                    }
                    if (userid.length() > 1) {
                        userid = userid.substring(0, userid.length() - 1);
                    }
                    String failsNum = msgResult.getString("fail");

                    CpUser user = SessionUtils.getUser(request);
                    CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
                    cpPhoneMsg.setSender(user.getUserName());
                    cpPhoneMsg.setPhoneReciver(teamIds);// 这里发送的是电话号码(单发),teamId(群发)
                    cpPhoneMsg.setPhoneReciverRole(roseid);
                    cpPhoneMsg.setStatus(
                            failsNum.indexOf(user.getTelBind()) < 0 ? 0 : 2); // 发送成功
                    cpPhoneMsg.setContent(content);
                    phoneMSGService.add(cpPhoneMsg);
                    result.setCode(CommonConstant.SUCCESSCODE);
                    result.setMsg(CommonConstant.SUCCESSSTRING);
                } else {
                    save(uid, content, teamId, request, 2, teamIds); // 发送失败保存
                    result.setCode(CommonConstant.EXCEPTIONCODE);
                    result.setMsg("短信发送失败");
                }

                /*
                 * for (int i = 0; i <num; i++) { msgResult =
                 * phoneMSGService.sendSMS(phoneN.get(i),
                 * content,phoneMSG.get(0) , phoneMSG.get(1));
                 * msgResult=msgResult.replace('\n', ',');
                 * 
                 * result2 = msgResult.split(",")[1]; if
                 * ("0".equalsIgnoreCase(result2)) { // 如果发送成功就保存到数据库 if
                 * (roseid.length() > 1) { roseid = roseid.substring(0,
                 * roseid.length() - 1); } if (userid.length() > 1) { userid =
                 * userid.substring(0, userid.length() - 1); } CpUser user =
                 * SessionUtils.getUser(request); CpPhoneMsg cpPhoneMsg = new
                 * CpPhoneMsg(); cpPhoneMsg.setSender(user.getUserName());
                 * cpPhoneMsg.setPhoneReciver(phoneN.get(i));//这里发送的是电话号码
                 * cpPhoneMsg.setPhoneReciverRole(roseid);
                 * cpPhoneMsg.setStatus(0); // 发送成功
                 * cpPhoneMsg.setContent(content);
                 * phoneMSGService.add(cpPhoneMsg);
                 * result.setCode(CommonConstant.SUCCESSCODE);
                 * result.setMsg(CommonConstant.SUCCESSSTRING); } else{
                 * save(uid, content, teamId, request, 1,phoneN.get(i)); //
                 * 发送失败保存 result.setCode(CommonConstant.EXCEPTIONCODE);
                 * result.setMsg("短信发送失败"); break; } }
                 */
            } catch (Exception e) {
                e.printStackTrace();
                result.setCode(CommonConstant.EXCEPTIONCODE);
                save(uid, content, teamId, request, 2, ""); // 发送失败保存
                log.error("短信发送失败， ", e);
                result.setMsg("短信发送失败");
            }
        } catch (Exception e1) {
            save(uid, content, teamId, request, 2, ""); // 发送失败保存
            e1.printStackTrace();
            log.error("短信发送失败， ", e1);
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 短信详情
     * 
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/detailPhoneMSG")
    public Object detail(HttpServletRequest request,
            HttpServletResponse response, int id) {
        ResponseMessage result = new ResponseMessage();
        try {
            CpPhoneMsg cpPhoneMsg = phoneMSGService.detail(id);
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(cpPhoneMsg.getCreateTime());
            cpPhoneMsg.setDatetime(datetime);
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(cpPhoneMsg);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("手机短信详情查看失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 删除短信
     * 
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletePhoneMSG")
    @LogInfo(content = "删除短信", opeType = 2, logTypeCode = CommonConstant.PhoneMsg)
    public Object delete(HttpServletRequest request,
            HttpServletResponse response, String id) {
        ResponseMessage result = new ResponseMessage();
        try {
            String[] split = id.split(",");
            for (String i : split) {
                phoneMSGService.delete(Integer.parseInt(i));
            }
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("删除手机短信失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 显示所有短信消息
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/showPhoneMSG")
    @LogInfo(content = "搜索短信", opeType = 2, logTypeCode = CommonConstant.PhoneMsg)
    public Object show(HttpServletRequest request,
            HttpServletResponse response) {
        ResponseMessage result = new ResponseMessage();
        try {
            PageHelper.startPage(request);
            List<CpPhoneMsg> list = phoneMSGService.show();
            for (int i = 0; i < list.size(); i++) {
                String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(list.get(i).getCreateTime());
                list.get(i).setDatetime(datetime);
            }
            PageHelper.addPages(result, list);
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(list);
            PageHelper.addPagesAndTotal(result, list);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("不能短信发送消息，" + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 搜索短信
     * 
     * @param request
     * @param response
     * @param search
     *            消息内容
     * @param sender
     *            发送者
     * @param status
     *            状态
     * @param timeFrom
     *            开始时间
     * @param timeTo
     *            结束时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchPhoneMSG")
    public Object search(HttpServletRequest request,
            HttpServletResponse response, String search, String sender,
            Integer status, String timeFrom, String timeTo) {
        ResponseMessage result = new ResponseMessage();
        try {
            PageHelper.startPage(request);
            List<CpPhoneMsg> list = phoneMSGService.search(search, sender,
                    status, timeFrom, timeTo);
            PageHelper.addPages(result, list);
            for (int i = 0; i < list.size(); i++) {
                String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(list.get(i).getCreateTime());
                list.get(i).setDatetime(datetime);
            }
            String rows = request.getParameter("rows");
            if (MyStringUtil.isEmpty(rows) || !MyStringUtil.isNumeric(rows)) {
                rows = "10";
            }
            int page = list.size() % Integer.parseInt(rows) == 0
                    ? list.size() / Integer.parseInt(rows)
                    : list.size() / Integer.parseInt(rows) + 1;
            result.setPage(page);
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(list);
            PageHelper.addPagesAndTotal(result, list);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("搜索短信失败，" + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    // 加了一个 phoneNum 参数，这是配合如果失败需要记录；可能是电话号码可能是teamId
    // 原来插入数据库的是 userId，但是这个接收者的id，前台可能不会传送
    public void save(int[] uid, String content, int[] teamId,
            HttpServletRequest request, int Status, String phoneNum) {
        String roseid = "";
        String userid = "";
        if (teamId != null && teamId.length > 0) {
            for (int i : teamId) {
                String roseid1 = userRoleRightService.getRoseName(i);
                roseid = roseid + "(组)" + roseid1 + ":";
            }
        }
        if (uid != null && uid.length > 0) {
            for (int i : uid) {
                String findNameByUid = userRoleRightService.findNameByUid(i);
                userid = userid + findNameByUid + ":";
            }
        }
        if (roseid != null && roseid.length() > 1) {
            roseid = roseid.substring(0, roseid.length() - 1);
        }
        if (userid != null && userid.length() > 1) {
            userid = userid.substring(0, userid.length() - 1);
        }
        CpUser user = SessionUtils.getUser(request);
        CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
        cpPhoneMsg.setSender(user.getUserName());
        cpPhoneMsg.setPhoneReciver(phoneNum);
        cpPhoneMsg.setPhoneReciverRole(roseid);
        cpPhoneMsg.setStatus(Status); // 发送成功
        cpPhoneMsg.setContent(content);
        phoneMSGService.add(cpPhoneMsg);
    }

    // 这个接口暂时没有使用，如果要改短信相关的，参考 send，sendVilidate
    @ResponseBody
    @RequestMapping("/findPasswordByPhone")
    @SkipLoginCheck
    @SkipAuthCheck
    public Object findPasswordByPhone(HttpServletRequest request, int userId,
            String newPassword, String userPhone)
            throws HttpException, IOException {
        ResponseMessage result = new ResponseMessage();
        // String title="中新社密码找回";
        // String content="用户的密码是: "+newPassword;
        String content = "";
        int[] teamId = null;
        int[] uid = { userId };
        try {
            List<String> phoneMSG = configService.findEmail(
                    SysConfigConstant.Phone_username,
                    SysConfigConstant.Phone_password);

            content = "中新社密码找回。 用户密码是 ：" + newPassword;

            // SendChit sendChit = new SendChit();
            String sendResult = phoneMSGService.sendSMS(userPhone, content,
                    phoneMSG.get(0), phoneMSG.get(1));
            sendResult = sendResult.replace('\n', ',');

            String[] arrstr = sendResult.split(",");
            if (arrstr.length > 1) {
                if (arrstr[1].equals("0")) {
                    log.info("提交成功，返回值：" + arrstr[2]);
                    result.setCode(CommonConstant.SUCCESSCODE);
                    result.setMsg(CommonConstant.SUCCESSSTRING);
                } else {
                    log.info(userPhone + "提交失败，错误码：" + arrstr[1]);
                    result.setCode(CommonConstant.EXCEPTIONCODE);
                    result.setMsg(CommonConstant.EXCEPTIONMSG);
                    // 错误码 详见接口文档
                }
            } else {
                log.info(userPhone + "提交异常，返回值：" + result);
                result.setCode(CommonConstant.EXCEPTIONCODE);
                result.setMsg(CommonConstant.EXCEPTIONMSG);
            }
            System.out.println("------------>" + sendResult);
            /*
             * HttpClient client = new HttpClient(); PostMethod post = new
             * PostMethod("http://gbk.sms.webchinese.cn");
             * post.addRequestHeader("Content-Type",
             * "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
             * List<String> phone = new ArrayList<String>();
             * phone.add(userPhone); for (int i = 0; i < phone.size(); i++) {
             * NameValuePair[] data = { new NameValuePair("Uid",
             * phoneMSG.get(0)), // 注册的用户名 new NameValuePair("Key",
             * phoneMSG.get(1)), // 注册成功后，登录网站后得到的密钥 new NameValuePair("smsMob",
             * phone.get(i)), // 手机号码 new NameValuePair("smsText", content) };//
             * 短信内容 post.setRequestBody(data); client.executeMethod(post);
             * Header[] headers = post.getResponseHeaders(); int statusCode =
             * post.getStatusCode(); result2 = new
             * String(post.getResponseBodyAsString().getBytes("gbk")); }
             */
            CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
            cpPhoneMsg.setPhoneReciver(userId + "");
            cpPhoneMsg.setStatus(0); // 发送成功
            cpPhoneMsg.setContent(content);
            phoneMSGService.add(cpPhoneMsg);
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
        } catch (Exception e1) {
            save(uid, content, teamId, request, 3, ""); // 发送失败保存
            e1.printStackTrace();
            log.error("短信发送失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 确认邮箱验证码
     * 
     * @param request
     * @param userEmail
     * @param vilidate
     *            确认验证码
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmPhoneVilidate")
    @SkipLoginCheck
    public Object confirmPhoneVilidate(HttpServletRequest request,
            String userName, String vilidate) {
        ResponseMessage result = new ResponseMessage();
        CommonValidation.checkParamBlank(vilidate, "验证码");
        CommonValidation.checkParamBlank(userName, "用户名");
        try {
            if (redisClientTemplate.ttl("PHONE" + userName + vilidate) == -2) {
                result.setCode(CommonConstant.PARAMERROR);
                result.setMsg("验证码已失效！");
            } else {
                CpUser user = cpUserMapper.findUserByUserName(userName);

                String phoneVilidate = redisClientTemplate
                        .get("PHONE" + userName + vilidate);
                if (vilidate.equals(phoneVilidate)) {
                    // 密码找回成功 生成秘钥标示
                    String markStr = UUID.randomUUID().toString().substring(0,
                            8) + user.getUserName();
                    String reverse = Coder.encryptBASE64(
                            Coder.reverse(MD5Util2.convertMD5(markStr)));
                    // request.getSession().setAttribute("mark", reverse);

                    redisClientTemplate.set("mark" + userName, reverse);
                    redisClientTemplate.expire("mark" + userName, 60 * 10);
                    result.setData(reverse);
                    result.setCode(CommonConstant.SUCCESSCODE);
                    result.setMsg(CommonConstant.SUCCESSSTRING);
                } else {
                    result.setCode(CommonConstant.PARAMERROR);
                    result.setMsg("验证码不正确！");
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 找回密码通过手机验证码
     * 
     * @param request
     * @param phoneNum
     * @throws HttpException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/sendPhoneVilidate")
    @SkipLoginCheck
    @SkipAuthCheck
    public Object sendPhoneVilidate(HttpServletRequest request, String userName)
            throws HttpException, IOException {
        ResponseMessage result = new ResponseMessage();
        try {
            CpUser user = cpUserMapper.findUserByUserName(userName);
            // 生成六位验证码发送到手机
            Integer vilidate = (int) ((Math.random() * 9 + 1) * 100000);
            redisClientTemplate.set("PHONE" + userName + vilidate,
                    vilidate + "");
            redisClientTemplate.expire("PHONE" + userName + vilidate, 60 * 2);

            // //清除过期key值
            // String content = "您正在执行中新社密码找回，验证码是: " + vilidate
            // + "。请按页面提示提交验证码，切记请勿将验证码泄露给他人。";
            //
            // String resultCode = phoneMSGUtils.sendSMSMsg(user.getTelBind(),
            // content);
            JSONObject resultObj = phoneMSGUtils.sendMsg(user.getTelBind(),
                    PhoneMSGUtils.TYPE_FORGET_CODE,vilidate);
            if (null == resultObj || resultObj.getString("code").equals("0")) {// 失败
                result.setCode(CommonConstant.EXCEPTIONCODE);
                result.setMsg(CommonConstant.EXCEPTIONMSG);
            } else {
                result.setCode(CommonConstant.SUCCESSCODE);
                result.setMsg(CommonConstant.SUCCESSSTRING);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("短信验证码发送失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/sendVilidate")
    @SkipLoginCheck
    @SkipAuthCheck
    public Object sendVilidate(HttpServletRequest request, String phoneNum)
            throws HttpException, IOException {
        ResponseMessage result = new ResponseMessage();
        try {
            List<CpUser> users = userRoleRightService.findUserByPhone(phoneNum);
            if (users == null || users.size() == 0) {
                result.setMsg("手机号输入错误");
                return result;
            } else if (users.size() > 1) {
                result.setMsg("您输入的手机号对应账户不唯一，请直接联系网站管理员。");
                return result;
            }
            // 生成验证码发送到手机
            String vilidate = UUID.randomUUID().toString().substring(0, 8);
            // HttpSession session = request.getSession();
            request.getSession().setAttribute("vilidate", vilidate);
            // String title="中新社密码找回验证码 \r\n";
//            String content = "您正在执行中新社密码找回，验证码是: " + vilidate
//                    + "。请按页面提示提交验证码，切记请勿将验证码泄露给他人。";

            JSONObject resultCode = phoneMSGUtils.sendMsg(phoneNum, PhoneMSGUtils.TYPE_FORGET_CODE,Integer.valueOf(vilidate));
            if (resultCode == null||resultCode.getString("code").equals("0")) {// 失败
                result.setCode(CommonConstant.EXCEPTIONCODE);
                result.setMsg(CommonConstant.EXCEPTIONMSG);
            } else {
                result.setCode(CommonConstant.SUCCESSCODE);
                result.setMsg(CommonConstant.SUCCESSSTRING);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("短信验证码发送失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }

    /**
     * 注册发送短信验证码
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月6日 下午9:41:37
     * @param request
     * @param phoneNum
     *            手机号码
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendMsgCode")
    @SkipLoginCheck
    @SkipAuthCheck
    public Object sendMsgCode(HttpServletRequest request, String phoneNum) {
        ResponseMessage result = new ResponseMessage();

        CommonValidation.checkParamBlank(phoneNum, "手机号");
        // 正则验证手机号码是否合法
        final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
        if (!Pattern.matches(REGEX_MOBILE, phoneNum)) {
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
            return result;
        }

        // log.info("========"+redisClientTemplate.get("PHONE"+phoneNum));
        if (redisClientTemplate.get("PHONE" + phoneNum) != null) {
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
            return result;
        }

        CpPhoneMsg cpPhoneMsg = new CpPhoneMsg();
        // 添加短信信息
        cpPhoneMsg.setSender("系统管理员");
        cpPhoneMsg.setContent("发送短信验证码");
        JSONObject resultObj = null;
        try {
            resultObj = phoneMSGUtils.sendMsg(phoneNum,
                    PhoneMSGUtils.TYPE_SEND_CODE,null);
        } catch (Exception e) {
            cpPhoneMsg.setStatus(2); // 发送失败
            phoneMSGService.add(cpPhoneMsg);

            log.error(e.getMessage(), e);
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("短信发送失败");
            return result;
        }
        if (null == resultObj || resultObj.getString("code").equals("0")) {
            cpPhoneMsg.setStatus(2); // 发送失败
            phoneMSGService.add(cpPhoneMsg);

            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("短信发送失败");
            return result;
        }

        // 验证码有效期 3分钟
        String vilidata = resultObj.getString("msg");
        redisClientTemplate.set("PHONE" + phoneNum + vilidata, vilidata + "");
        redisClientTemplate.expire("PHONE" + phoneNum + vilidata, 60 * 3);
        // 保存手机号，防止频繁调用接口
        redisClientTemplate.set("PHONE" + phoneNum, vilidata + "");
        redisClientTemplate.expire("PHONE" + phoneNum, 60);

        cpPhoneMsg.setStatus(0); // 发送成功
        phoneMSGService.add(cpPhoneMsg);
        // log.info("PHONE"+phoneNum+vilidata+"==="+redisClientTemplate.get("PHONE"+phoneNum+vilidata));
        result.setCode(CommonConstant.SUCCESSCODE);
        result.setMsg(CommonConstant.SUCCESSSTRING);
        return result;
    }

    /**
     * 前台登录获取验证码
     * 
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月8日 下午2:59:48
     * @param request
     * @param userName
     *            用户名
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPhoneVilidate")
    @SkipLoginCheck
    @SkipAuthCheck
    public Object getPhoneVilidate(HttpServletRequest request,
            String userName) {
        ResponseMessage result = new ResponseMessage();
        CommonValidation.checkParamBlank(userName, "用户名");
        CpUser user = cpUserMapper.findUserByUserName(userName);
        if (null == user) {
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("用户不存在");
            return result;
        }

        Integer status = user.getUserStatus();
        if (status == 3) {
            result.setCode(CommonConstant.FAILURECODE);
            result.setMsg("该用户已禁用");
            return result;
        }

        // 生成六位验证码发送到手机
        Integer vilidate = (int) ((Math.random() * 9 + 1) * 100000);
        redisClientTemplate.set("USERNAME" + userName + vilidate,
                vilidate + "");
        redisClientTemplate.expire("USERNAME" + userName + vilidate, 60 * 2);

        JSONObject resultObj = null;
        try {
            resultObj = phoneMSGUtils.sendMsg(user.getTelBind(),
                    PhoneMSGUtils.TYPE_LOGIN_CODE,vilidate);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取验证码失败", e);
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("获取验证码失败");
            return result;
        }

        if (null == resultObj || resultObj.getString("code").equals("0")) {
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("获取验证码失败");
            return result;
        }

         // 验证码有效期 3分钟
//        String vilidata = resultObj.getString("msg");
//        redisClientTemplate.set("USERNAME" + userName + vilidata,vilidata );
//        redisClientTemplate.expire("USERNAME" + userName + vilidata, 60 * 3);
        
        result.setCode(CommonConstant.SUCCESSCODE);
        result.setMsg(CommonConstant.SUCCESSSTRING);
        return result;
    }
    
    /**
     * 手机端前台登录获取验证码
     * 
     * @Description: TODO <BR>
     * @author xia.yunan
     * @date 2017年10月17日 下午10:18:58
     * @param request
     * @param userName
     *            用户名
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVilidateCodeForMobile")
    @SkipLoginCheck
    @SkipAuthCheck
    public Object getVilidateCodeForMobile(HttpServletRequest request,
            String phoneNum) {
        ResponseMessage result = new ResponseMessage();
        CommonValidation.checkParamBlank(phoneNum, "手机号");
        // 生成六位验证码发送到手机
        Integer vilidate = (int) ((Math.random() * 9 + 1) * 100000);
        
        redisClientTemplate.set("MOBILE" + phoneNum + vilidate, vilidate + "");
        redisClientTemplate.expire("MOBILE" + phoneNum + vilidate, 60*10 );//修改过期时间为10分钟
        JSONObject resultObj = null;
        try {
            resultObj = phoneMSGUtils.sendMsgForNewArticle(phoneNum,vilidate,PhoneMSGUtils.TYPE_LOGIN_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取验证码失败", e);
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("获取验证码失败");
            return result;
        }

        if (null == resultObj || resultObj.getString("code").equals("0")) {
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg("获取验证码失败");
            return result;
        }

        result.setCode(CommonConstant.SUCCESSCODE);
        result.setMsg(CommonConstant.SUCCESSSTRING);
        return result;
    }
    
    /**
     * 短信群发添加
     * @author xiayunan
     * @param request
     * @param cpMassSMSRecord
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMassSMSRecord")
    public Object addMassSMSRecord(HttpServletRequest request,
    		CpMassSMSRecord cpMassSMSRecord,String sendTime) {
    	log.info("<<<<<<<<<开始进行短信群发添加");
        ResponseMessage result = new ResponseMessage();
        try {
	        CommonValidation.checkParamBlank(cpMassSMSRecord.getMsgContent(), "短信内容");
	        CpUser user = (CpUser)SessionUtils.getUser(request);
	        if(user==null){
	        	result.setCode(CommonConstant.NOTLOGINCODE);
	            result.setMsg(CommonConstant.NOTLOGINMSG);
	            return result;
	        }
	        
	        cpMassSMSRecord.setSender(user.getTureName());
	        if(!StringUtil.isBlank(cpMassSMSRecord.getPhoneReceiverUser())){
	        	cpMassSMSRecord.setReceiverType(0);
	        }
	        if(!StringUtil.isBlank(cpMassSMSRecord.getPhoneReceiverGroup())){
	        	cpMassSMSRecord.setReceiverType(1);
	        }
	        cpMassSMSRecord.setCrtime(new Date());
	        cpMassSMSRecordMapper.insertSelective(cpMassSMSRecord);	
	        try {
	        	 if(!StringUtil.isBlank(sendTime)){
	 	        	if(com.deepai.photo.common.util.date.DateTimeUtil.isValidNormalDate(sendTime)){
	 	        		//TODO 插入定时任务表
	 	        		CpMsgTimer cpMsgTimer = new CpMsgTimer();
	 	        		cpMsgTimer.setExecuteTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sendTime) );
	 	        		cpMsgTimer.setClassId(cpMassSMSRecord.getId());
	 	        		cpMsgTimer.setCreateTime(new Date());
	 	        		cpMsgTimer.setCreateUser(user.getTureName());
	 	        		int timerId = cpMsgTimerMapper.insertSelective(cpMsgTimer);
	 	        		log.info("<<<定时表记录插入成功，记录ID为：【"+timerId+"】");
	 	        	}
	 	        }
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入定时任务失败， " + e.getMessage());
	            result.setCode(519);
	            result.setMsg("插入定时任务失败！");
	            return result;
			}
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(cpMassSMSRecord);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("新增短信群发记录失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }
    
    
    /**
     * 显示所有群发短信信息
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/showMassSMS")
    @LogInfo(content = "显示短信群发", opeType = 2, logTypeCode = CommonConstant.PhoneMsg)
    public Object showMassSMS(HttpServletRequest request,
            HttpServletResponse response) {
        ResponseMessage result = new ResponseMessage();
        List<CpUser> receiveUsers = null;
        List<EnGroupManagement> receivegroups = null;
        try {
            PageHelper.startPage(request);
            List<CpMassSMSRecord> lists = cpMassSMSRecordMapper.showMassSMS();
            
            if(lists.size()>0){
            	 for(CpMassSMSRecord cpMassSMSRecord:lists){
            		 String filePath = cpMassSMSRecord.getFilePath();
            		 if(filePath!=null&&!"".equals(filePath)){
            			 filePath = CommonConstant.SMALLHTTPPath+filePath.replace("/trsphoto", "");
            			 cpMassSMSRecord.setFilePath(filePath);
            		 }
            		 String userIds = cpMassSMSRecord.getPhoneReceiverUser();
            		 
            		 if(!StringUtil.isBlank(userIds)){
            			 String[] userIdsArr = userIds.split(",");
            			 receiveUsers = new ArrayList<CpUser>();
            			 for(String userId:userIdsArr){
            				 CpUser cpUser = cpUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
            				 receiveUsers.add(cpUser);
            			 }
            			 cpMassSMSRecord.setUsers(receiveUsers);
            		 }
            		 
            		 String groupIds = cpMassSMSRecord.getPhoneReceiverGroup();
            		 if(!StringUtil.isBlank(groupIds)){
            			 String[] GroupsIdsArr = groupIds.split(",");
            			 receivegroups = new ArrayList<EnGroupManagement>();
            			 for(String groupId:GroupsIdsArr){
            				 EnGroupManagement enGroupManagement =  groupManagementMapper.selectById(Integer.valueOf(groupId));
            				 receivegroups.add(enGroupManagement);
            			 }
            			 cpMassSMSRecord.setGroups(receivegroups);
            		 }
            		 
            		 //如果短信群发记录设有定时任务，则返回定时时间
            		 int id = cpMassSMSRecord.getId();
            		 CpMsgTimer cpMsgTimer = cpMsgTimerMapper.selectByClassId(id);
            		 if(cpMsgTimer!=null){
            			 Date date = cpMsgTimer.getExecuteTime();
            			 String excuteTime = "";
            			 if(date!=null){
            				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            				 excuteTime = sdf.format(date);
            				 cpMassSMSRecord.setExecuteTime(excuteTime);
            			 }
            		 }
                 }
            }
            PageHelper.addPages(result, lists);
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(lists);
            PageHelper.addPagesAndTotal(result, lists);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("无法获取短信列表，" + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }
    
    
    /**
     * 更新短信群发记录
     * @author xiayunan
     * @param request
     * @param cpMassSMSRecord
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMassSMSRecord")
    public Object updateMassSMSRecord(HttpServletRequest request,
    		CpMassSMSRecord cpMassSMSRecord,String sendTime) {
    	log.info("<<<<<<<<<开始更新短信群发记录");
        ResponseMessage result = new ResponseMessage();
        try {
	        CommonValidation.checkParamBlank(cpMassSMSRecord.getMsgContent(), "短信内容");
	        CpUser user = (CpUser)SessionUtils.getUser(request);
	        if(user==null){
	        	result.setCode(CommonConstant.NOTLOGINCODE);
	            result.setMsg(CommonConstant.NOTLOGINMSG);
	            return result;
	        }
	        cpMassSMSRecord.setAuditor(user.getTureName());
	        cpMassSMSRecord.setUpdateTime(new Date());
	        cpMassSMSRecordMapper.updateByPrimaryKeySelective(cpMassSMSRecord);
	        
	        //更新短信群发定时任务更新时间，更新用户
	        
	        log.info("<<<短信群发记录ID："+cpMassSMSRecord.getId());
	        CpMsgTimer cpMsgTimer =  cpMsgTimerMapper.selectByClassId(cpMassSMSRecord.getId());
	        log.info("<<<cpMsgTimer is null "+(cpMsgTimer==null));
	        log.info("<<<cpMsgTimer createUser！"+cpMsgTimer.getCreateUser());
	        if(cpMsgTimer!=null){
	        	log.info("<<<开始更新短信群发定时任务记录！！！");
	        	cpMsgTimer.setUpdateTime(new Date());
		        cpMsgTimer.setUpdateUser(user.getTureName());
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        if(!StringUtil.isBlank(sendTime)&&sdf.format(cpMsgTimer.getExecuteTime())!=sendTime){
		        	cpMsgTimer.setExecuteTime(sdf.parse(sendTime));
	            }
		        cpMsgTimerMapper.updateByPrimaryKey(cpMsgTimer);
		        log.info("<<<更新短信群发定时任务记录成功！！！");
	        }
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
           
            result.setData(cpMassSMSRecord);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("更新短信群发记录失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }
    
    /**
     * 删除短信群发记录
     * @author xiayunan
     * @param request
     * @param cpMassSMSRecord
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteMassSMSRecord")
    public Object deleteMassSMSRecord(HttpServletRequest request,
    		String recordId) {
    	log.info("<<<<<<<<<开始删除短信群发记录");
        ResponseMessage result = new ResponseMessage();
        try {
	        CommonValidation.checkParamBlank(recordId, "短信群发记录ID");
	        String[] split = recordId.split(",");
			for (String i : split) {
				int delResult = cpMassSMSRecordMapper.deleteByPrimaryKey(Integer.parseInt(i));
				if(delResult==1){//删除成功后继续删除定时任务表中的记录
					cpMsgTimerMapper.deleteByClassId(Integer.parseInt(i));
				}
			}
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("删除短信群发记录失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }
    
    /**
     * 查询短信群发记录
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchMassSMSRecordById")
    public Object searchMassSMSRecordById(HttpServletRequest request,
    		String id) {
    	log.info("<<<<<<<<<开始查询短信群发记录");
        ResponseMessage result = new ResponseMessage();
        try {
	        CommonValidation.checkParamBlank(id, "短信群发记录ID");
	        CpMassSMSRecord cpMassSMSRecord = cpMassSMSRecordMapper.selectMassSMSById(Integer.parseInt(id));
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(cpMassSMSRecord);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("查询短信群发记录失败， " + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }
    
   /**
    * @description 搜索群发短信
    * @author xiayunan
    * @param request
    * @param response
    * @param cpMassSMSRecord
    * @param searchParamStr
    * @param timeFrom
    * @param timeTo
    * @return
    */
    @ResponseBody
    @RequestMapping("/searchMassSMS")
    public Object searchMassSMS(HttpServletRequest request,
            HttpServletResponse response, CpMassSMSRecord cpMassSMSRecord,String searchName,String timeFrom,String timeTo) {
        ResponseMessage result = new ResponseMessage();
        try {
        	log.info("开始检索短信群发记录!");
            PageHelper.startPage(request);
            Map<String, Object> map=new HashMap<String, Object>();
    		if (!StringUtil.isBlank(searchName)) {
    			  map.put("searchName", searchName);
    		}
    		if(cpMassSMSRecord.getStatus()!=null){
    			map.put("status", cpMassSMSRecord.getStatus()+"");
    		}
    		if(!StringUtil.isBlank(timeFrom)){
    			map.put("timeFrom", DateUtils.fromStringToDate("yyyy-MM-dd",timeFrom));
    		}
    		if(!StringUtil.isBlank(timeTo)){
    			map.put("timeTo", DateUtils.fromStringToDate("yyyy-MM-dd",timeTo));
    		}
    		if(!StringUtil.isBlank(cpMassSMSRecord.getSender())){
    			map.put("sender", cpMassSMSRecord.getSender());
    		}
            List<CpMassSMSRecord> list = cpMassSMSRecordMapper.search(map);
            PageHelper.addPages(result, list);
            result.setCode(CommonConstant.SUCCESSCODE);
            result.setMsg(CommonConstant.SUCCESSSTRING);
            result.setData(list);
            PageHelper.addPagesAndTotal(result, list);
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error("搜索短信失败，" + e1.getMessage());
            result.setCode(CommonConstant.EXCEPTIONCODE);
            result.setMsg(CommonConstant.EXCEPTIONMSG);
        }
        return result;
    }
    
    /**
     * 群发短信
     * @author xiayunan
     * @date 2018年5月23日
     * @param request
     * @param response
     * @param recordId
     * @return
     */
     @ResponseBody
     @RequestMapping("/sendMassSMS")
     public Object sendMassSMS(HttpServletRequest request,
             HttpServletResponse response, Integer recordId) {
         ResponseMessage result = new ResponseMessage();
         Set<String> phoneNumSet = new HashSet<String>();
         try {
             log.info("开始发送短信!");
         	 CommonValidation.checkParamBlank(recordId+"", "短信群发记录ID");
         	 CpMassSMSRecord cpMassSMSRecord =  cpMassSMSRecordMapper.selectMassSMSById(recordId);
         	 phoneNumSet = phoneMSGService.getPhoneNumSet(recordId);
         	 if(phoneNumSet.size()==0){
         		 result.setCode(518);
                 result.setMsg("接受对象的手机号集合为空，请核对后再发生");
                 return result;
         	 } 
         	 log.info("用户手机号集合："+phoneNumSet.toString());
         	 
         	 String massSmsContent = cpMassSMSRecord.getMsgContent();
         	 if(StringUtil.isBlank(massSmsContent)||massSmsContent.length()>60){
         		 result.setCode(CommonConstant.EXCEPTIONCODE);
                 result.setMsg("短信内容为空或总字数超过60，请核对后再进行发送！");
                 return result;
         	 }
         	 boolean isSuccess = phoneMSGService.sendMassSMS(recordId,phoneNumSet);
         	 if(isSuccess){
         		 result.setCode(CommonConstant.SUCCESSCODE);
                 result.setMsg(CommonConstant.SUCCESSSTRING);
         	 }else{
         		result.setCode(CommonConstant.EXCEPTIONCODE);
                result.setMsg("短信发送失败");
         	 }
         	 
         } catch (Exception e1) {
             e1.printStackTrace();
             log.error("发送短信失败，" + e1.getMessage());
             result.setCode(CommonConstant.EXCEPTIONCODE);
             result.setMsg(CommonConstant.EXCEPTIONMSG);
         }
         return result;
     }
     
     
     /**
      * 
      * 修改用户信息码发送手机验证码
      * @author xiayunan
      * @date 2018年5月7日  
      * @param request
      * @param type 0:更新用户信息 ;1:更新密码;2:更新支付信息
      * @throws HttpException
      * @throws IOException
      */
     @ResponseBody
     @RequestMapping("/sendPhoneVilidateForUpUserInfo")
     @SkipLoginCheck
     @SkipAuthCheck
     public Object sendPhoneVilidateForUpUserInfo(HttpServletRequest request,Integer type)
             throws HttpException, IOException {
         ResponseMessage result = new ResponseMessage();
         try {
        	CpUser user = (CpUser)SessionUtils.getUser(request);
 	        if(user==null){
 	        	result.setCode(CommonConstant.NOTLOGINCODE);
 	            result.setMsg(CommonConstant.NOTLOGINMSG);
 	            return result;
 	        }
 	        String userName = user.getTureName();
            // 生成六位验证码发送到手机
            Integer vilidate = (int) ((Math.random() * 9 + 1) * 100000);
            JSONObject resultObj = null;
            
            log.info("<<<type:"+type);
            log.info("<<<phonenum:"+user.getTelBind());
            switch (type) {
            case 0:
                redisClientTemplate.set("USERINFO" + userName + vilidate,
                         vilidate + "");
                redisClientTemplate.expire("USERINFO" + userName + vilidate, 60 * 2);

                resultObj = phoneMSGUtils.sendMsg(user.getTelBind(),
                         PhoneMSGUtils.UPDATE_USERINFO_CODE,vilidate);
                break;
            case 1:
            	redisClientTemplate.set("USERPWD" + userName + vilidate,
                        vilidate + "");
                redisClientTemplate.expire("USERPWD" + userName + vilidate, 60 * 2);

                resultObj = phoneMSGUtils.sendMsg(user.getTelBind(),
                        PhoneMSGUtils.UPDATE_PASSWORD_CODE,vilidate);
                break;
            case 2:
            	redisClientTemplate.set("PAYINFO" + userName + vilidate,
                        vilidate + "");
                redisClientTemplate.expire("PAYINFO" + userName + vilidate, 60 * 2);

                resultObj = phoneMSGUtils.sendMsg(user.getTelBind(),
                        PhoneMSGUtils.UPDATE_PAYINFO_CODE,vilidate);
                break;
            default:
                break;
            }
            log.info("code:"+resultObj.getString("code"));
            if (null == resultObj || resultObj.getString("code").equals("0")) {// 失败
                 result.setCode(CommonConstant.EXCEPTIONCODE);
                 result.setMsg(CommonConstant.EXCEPTIONMSG);
            } else {
                 result.setCode(CommonConstant.SUCCESSCODE);
                 result.setMsg(CommonConstant.SUCCESSSTRING);
            }
         } catch (Exception e1) {
             e1.printStackTrace();
             log.error("修改用户信息发送短信验证码失败， " + e1.getMessage());
             result.setCode(CommonConstant.EXCEPTIONCODE);
             result.setMsg(CommonConstant.EXCEPTIONMSG);
         }
         return result;
     }
     
     
     
     /** 
 	 * 上传文件
 	 * @author  xiayunan
 	 * @date 2018年5月22日
 	 * @param request
 	 * @param Integer groupId
 	 * @return
 	 */
 	@ResponseBody
 	@RequestMapping("/upFile")
 	public ResponseMessage upFile(HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false, value = "myFile")MultipartFile[]  picFiles){
 		ResponseMessage result=new ResponseMessage();
 		try {
 			log.info("开始进行文件上传...");
 			if(picFiles==null||picFiles.length==0){
 				request.getContentLength();
 				result.setCode(CommonConstant.FAILURECODE);
 				result.setMsg("请上传文件");
 				return result;
 			}
 			if(picFiles.length>1){
 				request.getContentLength();
 				result.setCode(CommonConstant.FAILURECODE);
 				result.setMsg("只能上传一个文件");
 				return result;
 			}
 			
 			if(picFiles[0].getOriginalFilename().indexOf("xlsx")==-1&&picFiles[0].getOriginalFilename().indexOf("xls")==-1){
 				request.getContentLength();
 				result.setCode(CommonConstant.FAILURECODE);
 				result.setMsg("请上传Excel格式的文件！");
 				return result;
 			}
 			
 			String name = picFiles[0].getOriginalFilename();//获取上传文件的文件名
 			log.info("上传文件名为："+name);
 			String fullFilePath = phoneMSGService.upOneFile(name, SessionUtils.getSiteId(request),picFiles[0]);
 			log.info("文件上传成功！上传成功后文件全路径为："+fullFilePath);
 			result.setCode(CommonConstant.SUCCESSCODE);
 			result.setMsg(CommonConstant.SUCCESSSTRING);
 			result.setData(fullFilePath);
 		} catch (InvalidHttpArgumentException e) {
 			result.setCode(e.getCode());
 			result.setMsg(e.getMsg());
 		} catch(Exception e1){
 			e1.printStackTrace();
 			log.error("上传文件出错，"+e1.getMessage());
 			result.setCode(CommonConstant.EXCEPTIONCODE);
 			result.setMsg(CommonConstant.EXCEPTIONMSG);
 		}
 		return result;
 	}
     

    public static void main(String[] args) {
//		Set<Integer> set = new HashSet<Integer>();
//		set.add(4);
//		set.add(4);
//		set.add(5);
//		set.add(6);
//		System.out.println(set.toString());
//		
//		
//		System.out.println("安徽报业集团短消息中安徽报业集团短消息中安徽报业集团短消息中安徽报业集团短消息中安徽报业集团短消息中安徽报业集团短消息中1".length());
    	Pattern p = Pattern.compile("((((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		System.out.println(p.matcher("2018-05-28 10:51:38").matches()); 
		
	}
}
