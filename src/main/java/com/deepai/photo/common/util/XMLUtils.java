/*
 *	History				Who				What
 *  2017年9月4日			liujinfeng			Created.
 */
package com.deepai.photo.common.util;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

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
        document.setXMLEncoding("GB2312");
        Element root = DocumentHelper.createElement("News");

        Element eAuthor = DocumentHelper.createElement("Author");
        eAuthor.setText(group.getAuthor());

        Element eTitle = DocumentHelper.createElement("Title");
        eTitle.setText(group.getTitle());
        Element eType = DocumentHelper.createElement("Type");
        if(type==1){
            eType.setText("报社图片");
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
        eBody.setText(pic.getMemo());
        Element eSender = DocumentHelper.createElement("Sender");
        eSender.setText(user.getUserName());

        root.add(eAuthor);
        root.add(eTime);
        root.add(eTitle);
        root.add(eBody);
        root.add(eSender);
        root.add(eType);

        document.add(root);
        return document;
    }

    public static void writeXML(Document doc, String targetFile)
            throws IOException {
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("GB2312");
        of.setIndent(true);
        of.setNewlines(true);
        org.dom4j.io.XMLWriter xw = new org.dom4j.io.XMLWriter(
                new FileWriter(targetFile), of);
        xw.write(doc);
        xw.flush();
    }

}
