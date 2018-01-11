package com.deepai.photo.common.util.TrsSearch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pojo.TRSResult;
import com.deepai.photo.common.util.MyStringUtil;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.date.DateTimeUtil;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.validation.CommonValidation;
import com.eprobiti.trs.TRSConstant;
import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;
/**
 * 全文检索工具类
 * 部署时将lib下的trsbean.jar放到%JAVA_HOME%/jre/lib/ext
 * windows操作系统trsbean.dll放到%JAVA_HOME%/jre/bin
 * linux下将 libtrsbean.so放在$JAVA_HOME中libjava.*(libjava.so/libjava.a/libjava.sl)
 * 否则会抛出找不到trs类异常
 * */
public class TrsSearchConf {
	private static Logger log=Logger.getLogger(TrsSearchConf.class);
	
	private static TRSConnection conn = null;
	private static TRSResultSet rs = null;
	private static String ip="192.168.81.9";//全文检索数据库ip  192.168.180.126
	private static String prot="8888";//端口
	private static String user="system";//用户名
	private static String password="manager";//密码
//	private static String tableName="trsSearch";//检索的表
	private static String tableName="trsSearchCNS";//检索的表
	private static String enTableName="trsSearchEn";//英文版  检索表
	public static TRSConnection getConn() throws TRSException{
		if(conn==null||!conn.isClosed()){
			conn = new TRSConnection();
			conn.connect(ip, prot, user, password);
		}
		return conn;
	}
	
	/**
	 * 全文检索工具类
	 * 参数
	 * strWhere 查询条件
	 * page 页码 如果为空默认1
	 * rows 每页显示多少条 如果为空默认10
	 * @param request 
	 * @param trs 
	 * */
	public static Map<String, Object> TrsSearch(String strWhere,String page,String rows, TRSResult trsResult, HttpServletRequest request) throws TRSException{
		if (MyStringUtil.isEmpty(page) || !MyStringUtil.isNumeric(page)
				|| null == page){
			page = "1";
		}
			
		if (MyStringUtil.isEmpty(rows) || !MyStringUtil.isNumeric(rows)
				|| null == page){
			rows ="10";
		}
		Map<String, Object> map =new HashMap<String, Object>();
		List<TRSResult> list =new ArrayList<TRSResult>();
		StringBuffer extrCondition = new StringBuffer();
		extrCondition.append(TrsSearchUtil.handleSqlWhere(strWhere));
		
		
		
		if (StringUtils.isNotBlank(strWhere)) {
			rs = getConn().executeSelect(tableName, extrCondition.toString(), "", "", "*",
					TRSConstant.TCM_IDEOSINGLE, TRSConstant.TCM_IDEOSINGLE,
					false);
		}else{
			StringBuffer sb = new StringBuffer();
			if (StringUtils.isNotBlank(trsResult.getId())) {
				sb.append("id="+trsResult.getId() +" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getTitle())) {
				sb.append("title="+trsResult.getTitle() +" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getPeople())) {
				sb.append( "someOne="+trsResult.getPeople()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getPlace())) {
				sb.append("place="+trsResult.getPlace()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getAuthorName())) {
				sb.append("AUTHOR_NAME="+trsResult.getAuthorName()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getMemo())) {
				sb.append("MEMO="+trsResult.getMemo()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getKeyWords())) {
				sb.append("KeyWords="+trsResult.getKeyWords()+" and ");
			}
			if (trsResult.getCategory_id()!=null) {
				sb.append("category_id="+trsResult.getCategory_id()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getFileName())) {
				sb.append("fileName="+trsResult.getFileName()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getGtitle())) {
				sb.append("gtitle="+trsResult.getGtitle()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getgKEYWORDS())) {
				sb.append("gKEYWORDS="+trsResult.getgKEYWORDS()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getProperties())) {
				sb.append("PROPERTIES="+trsResult.getProperties()+" and ");
			}
			 String staTime = trsResult.getStaTime();
			 String endTime = trsResult.getEndTime();
			if (StringUtils.isNotBlank(staTime)&&StringUtils.isNotBlank(endTime)) {
				staTime=DateTimeUtil.getIntervalDaysLater(staTime, -1);
				endTime=DateTimeUtil.getIntervalDaysLater(endTime, 1);
				sb.append("date=BETWEEN( '"+staTime+"','"+endTime+"')"+" and ");
			}
			String search = sb.toString();
			if (StringUtils.isNotBlank(search)) {
				int LangType = SessionUtils.geLangType(request);
				search=search +"LANG_TYPE="+LangType;
			}else {
				map.put("page", 0);
				return map;
			}
			log.info("全文检索条件--------------------->"+search);
			rs = getConn().executeSelect(tableName,search, "", "", "",
					TRSConstant.TCM_IDEOSINGLE, TRSConstant.TCM_IDEOSINGLE,
					false);
//			getConn().executeSelectQuick(strSources, strWhere, strSortMethod, strDefautCols, lEstimateUnit, lEstimateStops, iSearchOption, iHitPointType)
			
			log.info("全文检索结果（计数）"+rs.getRecordCount());
			log.info("全文检索条件--------------------->"+search);
		}
		
		
		if(rs.getRecordCount()==0){
			map.put("page", 0);
			return map;
		}
		PageUtil pu=new PageUtil(Integer.valueOf(rows),  (int) rs.getRecordCount(),Integer.valueOf(page));
		map.put("page", pu.getPageCount());
		int	start=pu.getFromIndex();
		int	stop=pu.getToIndex();
		for (int i = start;  i < stop; i++) {
			rs.moveTo(0, i);
			TRSResult tr = new TRSResult();
			tr.setId(rs.getString("id"));
			tr.setGroupid(rs.getString("GROUP_ID"));
			tr.setTitle(rs.getString("title"));
			tr.setGtitle(rs.getString("gtitle"));
			
//			tr.setDate(rs.getString("date"));
			tr.setDate(rs.getString("create_time"));
			
			tr.setKeyWords(rs.getString("KEYWORDS"));
			
			tr.setMemo(rs.getString("MEMO"));
			
			tr.setAuthorName(rs.getString("AUTHOR_NAME"));
//			tr.setPeople(rs.getString("someOne"));
			tr.setPeople(rs.getString("people"));
			
			tr.setCategory_id(Integer.parseInt(rs.getString("category_id")));
			
			tr.setFileName(rs.getString("fileName"));
			
			tr.setPlace(rs.getString("place"));
			
			tr.setgKEYWORDS(rs.getString("gKEYWORDS"));
			//首图
//			String cp=rs.getString("coverPic");
			if (StringUtils.isNotBlank(tr.getFileName())) {
//				tr.setCoverPic(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(tr.getFileName(),request));
				tr.setCoverPic(CommonConstant.SMALLHTTPPath+ImgFileUtils.getPathByNameAndSize(tr.getFileName(), request, 2));
			}
			list.add(tr);
		}
		map.put("rows", list);
		map.put("allCount", (int) rs.getRecordCount());
		return map;
	}
	
	public static Map<String, Object> TrsEnSearch(String strWhere,String page,String rows, TRSResult trsResult, HttpServletRequest request) throws TRSException{
		if (MyStringUtil.isEmpty(page) || !MyStringUtil.isNumeric(page)
				|| null == page){
			page = "1";
		}
		if (MyStringUtil.isEmpty(rows) || !MyStringUtil.isNumeric(rows)
				|| null == page){
			rows ="10";
		}
		Map<String, Object> map =new HashMap<String, Object>();
		List<TRSResult> list =new ArrayList<TRSResult>();
		
	
		
		if (StringUtils.isNotBlank(strWhere)) {
			rs = getConn().executeSelect(enTableName, strWhere, "", "", "*",
					TRSConstant.TCM_IDEOSINGLE, TRSConstant.TCM_IDEOSINGLE,
					false);
		}else{
			StringBuffer sb = new StringBuffer();
			if (StringUtils.isNotBlank(trsResult.getTitle())) {
				sb.append("title="+trsResult.getTitle() +" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getPeople())) {
				sb.append( "someOne="+trsResult.getPeople()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getPlace())) {
				sb.append("place="+trsResult.getPlace()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getAuthorName())) {
				sb.append("AUTHOR_NAME="+trsResult.getAuthorName()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getMemo())) {
				sb.append("MEMO="+trsResult.getMemo()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getKeyWords())) {
				sb.append("KeyWords="+trsResult.getKeyWords()+" and ");
			}
			if (trsResult.getCategory_id()!=null) {
				sb.append("category_id="+trsResult.getCategory_id()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getFileName())) {
				sb.append("fileName="+trsResult.getFileName()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getGtitle())) {
				sb.append("gtitle="+trsResult.getGtitle()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getgKEYWORDS())) {
				sb.append("gKEYWORDS="+trsResult.getgKEYWORDS()+" and ");
			}
			if (StringUtils.isNotBlank(trsResult.getProperties())) {
				sb.append("PROPERTIES="+trsResult.getProperties()+" and ");
			}
			 String staTime = trsResult.getStaTime();
			  String endTime = trsResult.getEndTime();
			if (StringUtils.isNotBlank(staTime)&&StringUtils.isNotBlank(endTime)) {
				staTime=DateTimeUtil.getIntervalDaysLater(staTime, -1);
				endTime=DateTimeUtil.getIntervalDaysLater(endTime, 1);
				sb.append("date=BETWEEN( '"+staTime+"','"+endTime+"')"+" and ");
			}
			String search = sb.toString();
			String langTypeParm = request.getParameter("langType");
			if (StringUtils.isNotBlank(search)&&StringUtils.isNotBlank(langTypeParm)) {
				Integer LangType = Integer.parseInt(langTypeParm);
				search=search +"langtype="+LangType;
			}else {
				map.put("page", 0);
				return map;
			}
			rs = getConn().executeSelect(enTableName,search, "", "", "",
					TRSConstant.TCM_IDEOSINGLE, TRSConstant.TCM_IDEOSINGLE,
					false);
		}
		if(rs.getRecordCount()==0){
			map.put("page", 0);
			return map;
		}
		PageUtil pu=new PageUtil(Integer.valueOf(rows),  (int) rs.getRecordCount(),Integer.valueOf(page));
		map.put("page", pu.getPageCount());
		int	start=pu.getFromIndex();
		int	stop=pu.getToIndex();
		for (int i = start;  i < stop; i++) {
			rs.moveTo(0, i);
			TRSResult tr = new TRSResult();
			tr.setId(rs.getString("id"));
			tr.setTitle(rs.getString("title"));
			tr.setGtitle(rs.getString("gtitle"));
			tr.setDate(rs.getString("date"));
			tr.setKeyWords(rs.getString("KEYWORDS"));
			tr.setMemo(rs.getString("MEMO"));
			tr.setAuthorName(rs.getString("AUTHOR_NAME"));
			tr.setPeople(rs.getString("someOne"));
			tr.setCategory_id(Integer.parseInt(rs.getString("category_id")));
			tr.setFileName(rs.getString("fileName"));
			tr.setPlace(rs.getString("place"));
			tr.setgKEYWORDS(rs.getString("gKEYWORDS"));

			if (StringUtils.isNotBlank(tr.getFileName())) {
//				tr.setCoverPic(CommonConstant.SMALLHTTPPath+ImgFileUtils.getSamllPathByName(tr.getFileName(),request));
				tr.setCoverPic(CommonConstant.SMALLHTTPPath+ImgFileUtils.getPathByNameAndSize(tr.getFileName(), request, 2));
			}
			list.add(tr);
		}
		map.put("rows", list);
		map.put("allCount", (int) rs.getRecordCount());
		return map;
	}
	
	
	public static void main(String[] args) {
		TRSResult tr = new TRSResult();
		tr.setId("23010");
//		System.out.println("===================GROUP_ID"+rs.getString("GROUP_ID"));
		tr.setGroupid("7017");
	}
	
}
