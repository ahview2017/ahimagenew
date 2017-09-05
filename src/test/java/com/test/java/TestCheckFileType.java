package com.test.java;

import java.util.Arrays;
import java.util.List;

public class TestCheckFileType {
	public static void main(String[] args) {
		String fileTypes = "DOC,DOCX,XLS,XLSX,doc,docx,xls,xlsx,pdf,zip,rar,txt,ZIP,RAR,TXT";
		List<String> sList=Arrays.asList(fileTypes.split(",")); 
		String fileType = "jspx";
		System.out.println(!sList.contains(fileType));
		
//		String sfiletxs = "RAR";
//		String sErrorMsg = "";
//		if(!sfiletxs.equals("DOC")||!sfiletxs.equals("DOCX")||!sfiletxs.equals("XLS")||!sfiletxs.equals("XLSX")||!sfiletxs.equals("doc")||!sfiletxs.equals("docx")||!sfiletxs.equals("xls")||!sfiletxs.equals("xlsx")||!sfiletxs.equals("pdf")||!sfiletxs.equals("PDF")||!sfiletxs.equals("zip")||!sfiletxs.equals("ZIP")||!sfiletxs.equals("rar")||!sfiletxs.equals("RAR")||!sfiletxs.equals("TXT")||!sfiletxs.equals("txt")){
//			sErrorMsg	= "只允许上传“doc,docx,xls,xlsx,pdf,zip,rar,txt”!";
//		}
//		System.out.println("sErrorMsg:"+sErrorMsg);
		
	}
}
