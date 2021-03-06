/*
 *	History				Who				What
 *  2017年9月4日			liujinfeng			Created.
 */
package com.deepai.photo.common.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.deepai.photo.bean.CpPicGroup;
import com.deepai.photo.bean.CpPicture;
import com.deepai.photo.bean.CpUser;
import com.deepai.photo.common.util.date.DateUtils;

/**
 * Title: TRS 内容协作平台（TRS WCM） <BR>
 * Description: <BR>
 * 签报生存xml文件 <BR>
 * Copyright: Copyright (c) 2004-2017 北京拓尔思信息技术股份有限公司 <BR>
 * Company: www.trs.com.cn <BR>
 * 
 * @author liu.jinfeng
 * @version 1.0
 */
public class XMLUtils {
  private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
        .getLogger(XMLUtils.class);

    /**
     * 生成xml文件
     * @Description: TODO <BR>
     * @author liu.jinfeng
     * @date 2017年9月4日 下午4:55:39
     * @param group
     * @param pic
     * @param type 类型0:新华社图片；1:报社图片
     * @param user 
     * @return
     */
    public static Document createDoc(CpPicGroup group, CpPicture pic,
            Integer type, CpUser user) {
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("News");

        Element eAuthor = DocumentHelper.createElement("Author");
        
        logger.info("作者："+group.getTruename());
        eAuthor.setText(group.getTruename());//add by xiayunan@20180223 作者名改成真实姓名

        Element eTitle = DocumentHelper.createElement("Title");
        eTitle.setText(group.getTitle());
        Element eType = DocumentHelper.createElement("Type");
        if(type==1){
            eType.setText("报社TRS图片");
        }else{
            eType.setText("新华社图片");
        }
        
        Element eTime = DocumentHelper.createElement("Time");
        String sNow = "";
        try {
            sNow = DateUtils.getNowPlusTime();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        eTime.setText(sNow);
        
        Element eBody = DocumentHelper.createElement("Body");
        eBody.setText(deleteAllHTMLTag(group.getTitle())+"\r\n    "+deleteAllHTMLTag(group.getMemo())+"\r\n    "+deleteAllHTMLTag(pic.getMemo())+"\r\n    "+deleteAllHTMLTag(group.getRemark()));//add by xiayunan@20171201
        Element eSender = DocumentHelper.createElement("Sender");
//        eSender.setText(user.getUserName());
        eSender.setText(user.getTureName());

        root.add(eAuthor);
        root.add(eTime);
        root.add(eTitle);
        root.add(eBody);
        root.add(eSender);
        root.add(eType);

        document.add(root);
        return document;
    }
    
    /**
     * 生成XMl文件
     * @param doc
     * @param targetFile
     * @throws IOException
     */
    public static void writeXML(Document doc, String targetFile)
            throws IOException {
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("GBK");
        of.setIndent(true);
        of.setTrimText(false);//add by xiayunan@20171201
        of.setNewlines(true);
        org.dom4j.io.XMLWriter xw = new org.dom4j.io.XMLWriter(
        		new  FileOutputStream(targetFile), of);
        xw.write(doc);
        //xw.flush();
        xw.close();//add by xiayunan@20180223
    }
    
    /**
	  * 删除所有的HTML标签
	  *
	  * @param source 需要进行除HTML的文本
	  * @return
	  */
	 public static String deleteAllHTMLTag(String source) {
		  if(source == null) {
		       return "";
		  }
		  String s = source;
		  /** 删除普通标签  */
		  s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
		  /** 删除转义字符 */
		  s = s.replaceAll("&.{2,6}?;", "");
		  return s;
	 }
    

}
