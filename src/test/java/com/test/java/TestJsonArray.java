package com.test.java;

import org.json.JSONArray;

import org.json.JSONObject;

public class TestJsonArray {
	public static void main(String[] args) {
		String jsonArrStr = "[{\"type\":\"vod\",\"masId\":\"5\"},{\"type\":\"vod\",\"masId\":\"3\"}]";
		JSONArray jsonArray = new JSONArray(jsonArrStr);
		int size = jsonArray.length();
		String masid = "";
		if(size>0){
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				masid = (String) jsonObj.get("masId");
				System.out.println(jsonObj.get("masId"));
		}else{
			
		}
	}
}
