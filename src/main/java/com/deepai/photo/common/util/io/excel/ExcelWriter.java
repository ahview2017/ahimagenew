package com.deepai.photo.common.util.io.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {

	private static Logger log = Logger.getLogger(ExcelWriter.class);

	public void exportExcel(String title, String[] headers, List<List<String>> dataset, OutputStream out) throws IOException {

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// 遍历集合数据，产生数据行
		Iterator<List<String>> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			List<String> list = it.next();
			for (int i = 0; i < list.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(list.get(i));
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			log.error("[导出文件错误]" + (new Date()));
			e.printStackTrace();
		}
		finally{
			out.flush();
			out.close();
		}
	}

}
