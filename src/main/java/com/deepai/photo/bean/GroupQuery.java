package com.deepai.photo.bean;

import java.text.ParseException;
import java.util.Date;

import com.deepai.photo.common.util.date.DateUtils;


/**
 * 稿件高级检索
 * @author Administrator
 *
 */
public class GroupQuery {

	private Integer id;//编号
    private String title;//标题
    private String author;//作者
    private String place;//地点
    private String editor;//编辑人
    private String memo;//内容
    private String fileName;//文件名
    private Date beginTime;//开始时间
    private Date endTime;//结束时间
    private Integer langType;//语言
    
    public String getParamStr1() {
		return paramStr1;
	}
	public void setParamStr1(String paramStr1) {
		this.paramStr1 = paramStr1;
	}
	public String getParamStr2() {
		return paramStr2;
	}
	public void setParamStr2(String paramStr2) {
		this.paramStr2 = paramStr2;
	}
	private String paramStr;//简单查询 
    private String paramStr1;//简单查询1 
    private String paramStr2;//简单查询 2
    private String paramStr3;//简单查询3 
    private String paramStr4;//简单查询 4
    
	public String getParamStr() {
		return paramStr;
	}
	public String getParamStr3() {
		return paramStr3;
	}
	public void setParamStr3(String paramStr3) {
		this.paramStr3 = paramStr3;
	}
	public String getParamStr4() {
		return paramStr4;
	}
	public void setParamStr4(String paramStr4) {
		this.paramStr4 = paramStr4;
	}
	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getLangType() {
		return langType;
	}
	public void setLangType(Integer langType) {
		this.langType = langType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		if(author.isEmpty()){
			author=null;
		}
		this.author = author;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		if(place.isEmpty()){
			place=null;
		}
		this.place = place;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		if(editor.isEmpty()){
			editor=null;
		}
		this.editor = editor;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		if(memo.isEmpty()){
			memo=null;
		}
		this.memo = memo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		if(fileName.isEmpty()){
			fileName=null;
		}
		this.fileName = fileName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) throws ParseException {
		if(beginTime!=null&&!beginTime.isEmpty()){
			this.beginTime = DateUtils.fromStringToDate("yyyy-MM-dd HH:mm:ss",beginTime);
		}else{
			this.beginTime =null;
		}		
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) throws ParseException {
		if(endTime!=null&&!endTime.isEmpty()){
			this.endTime =  DateUtils.fromStringToDate("yyyy-MM-dd HH:mm:ss",endTime);
		}else{
			this.beginTime =null;
		}	
	}
    
    
}