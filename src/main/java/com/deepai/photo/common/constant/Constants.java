package com.deepai.photo.common.constant;

public interface Constants {

	// 公有属性------开始
	// 文件编码格式
	public final static String ENCODE = "UTF-8";
	// 空字符串
	public final static String STR = "";
	// 空字符串
	public final static String STRNULL = String.valueOf("null");
	// .英文句号
	public final static String DOT = ".";
	// ,英文逗号
	public final static String COMMA = ",";
	// 英文分号;
	public final static String SEMICOLON = ";";
	// _英文下划线
	public final static String BAR = "-";
	// /英文路径符号
	public final static String BAT = "/";
	public final static String AT = "@";
	// 数字0
	public final static int ZERO = 0;
	// 数字1
	public final static int ONE = 1;
	// 数字2
	public final static int TWO = 2;
	// 用户登录名
	public final static String LOGINNAME = "loginname";
	// 用户id
	public final static String USER_ID = "user_id";
	// WCM服务器上新闻id
	public final static String WCMNID = "wcmnid";
	// 和新闻相关表id
	public final static String NID = "nid";
	// 和广告相关表id
	public final static String AID = "aid";
	// 外键新闻id
	public final static String NEWSID = "newsId";
	// WCM服务器上新闻内容URL
	public final static String URL = "url";
	// 版本号
	public final static String APPKEY = "appkey";
	// 操作类型
	public final static String TYPE = "type";
	// 用户密码
	public final static String PASSWD = "passwd";
	// 用户ip
	public final static String IP = "ip";
	public final static String UUID = "UUID";
	// 用户GPS信息
	public final static String LATITUDE = "latitude";// 纬度
	public final static String LONGITUDE = "longitude";// 经度
	public final static int PERICITION = 10;// GPS精度
	// 全局常量
	public final static String ID = "id";
	public final static String CREATETIME = "createTime";
	public final static String HASDELETE = "hasDelete";
	public final static String PAGE = "page";
	public final static int PAGENUMBER = 5;
	public final static String SEARCHCONTENT = "searchcontent";
	// 失败原因
	public final static String REASON = "reason";
	// token
	public final static String TOKEN = "token";
	// 过期时间戳
	public final static String EXPIRE = "expire";
	// token失效天数为7天
	public final static int FAILUREDAYS = 7;
	// token失效秒数
	public final static int FAILUREMILLS = 604800;
	// 注册中的常量
	public final static String PHONE = "phone";// 注册手机号
	public final static String MAIL = "mail";// 注册邮箱
	public final static String IDENTIFY = "identify";// 注册下发到手机的验证码


	// 状态
	public final static String CODE = "code";
	// 成功
	public final static Object SUCCESS = "success";
	// 失败
	public final static Object FAIL = "fail";
	// 描述
	public static final String MSG = "msg";


	// 返回的数据
	public final static String DATA = "data";


	// 用户来源渠道
	public final static String REGTYPE = "regType";
	// 用户昵称
	public static final String NAME = "name";
	// 用户id
	public static final String USERID = "userid";
	// 用户头像
	public static final String HEADIMGURL = "headimgurl";
	// 用户第三方登陆唯一标识
	public static final String THIRDUID = "thirduid";
}
