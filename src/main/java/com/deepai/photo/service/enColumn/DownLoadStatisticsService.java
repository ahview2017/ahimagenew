package com.deepai.photo.service.enColumn;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepai.photo.bean.CpPictureDownloadrecord;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.controller.enColumn.EnColumnController;
import com.deepai.photo.mapper.ClientPictureMapper;

@Service
public class DownLoadStatisticsService {
	@Autowired
	private ClientPictureMapper clientPictureMapper;
	private Logger log = Logger.getLogger(EnColumnController.class);

	/**
	 * 下载统计查询
	 * 
	 * @param cpPictureDownloadrecord
	 * @return
	 */
	public List<CpPictureDownloadrecord> downLoadStatisticsList(CpPictureDownloadrecord cpPictureDownloadrecord) {
		List<CpPictureDownloadrecord> downloadrecordByQuery = null;
		if(StringUtil.blank(cpPictureDownloadrecord.getAuthorLoginName())){
			cpPictureDownloadrecord.setAuthorLoginName(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getEditUser())){
			cpPictureDownloadrecord.setEditUser(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getOrderByCase())){
			cpPictureDownloadrecord.setOrderByCase(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getPictureTitle())){
			cpPictureDownloadrecord.setPictureTitle(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getPictureFileName())){
			cpPictureDownloadrecord.setPictureFileName(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getUserName())){
			cpPictureDownloadrecord.setUserName(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getDownStartTime())){
			cpPictureDownloadrecord.setDownStartTime(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getDownEndTime())){
			cpPictureDownloadrecord.setDownEndTime(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getSignStartTime())){
			cpPictureDownloadrecord.setSignStartTime(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getSignEndTime())){
			cpPictureDownloadrecord.setSignEndTime(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getPictureGroup())){
			cpPictureDownloadrecord.setPictureGroup(null);
		}
		if(StringUtil.blank(cpPictureDownloadrecord.getPictureAuthor())){
			cpPictureDownloadrecord.setPictureAuthor(null);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		if (cpPictureDownloadrecord.getSelectType() == 0) {
			cpPictureDownloadrecord.setIsInteriorDownLoad(0);
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		if (cpPictureDownloadrecord.getSelectType() == 1) {// 今日下载
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date downStartTime = cal.getTime();
			Date downEndTime = new Date();
			cpPictureDownloadrecord.setIsInteriorDownLoad(0);
			cpPictureDownloadrecord.setDownStartTime(sdf.format(downStartTime));
			cpPictureDownloadrecord.setDownEndTime(sdf.format(downEndTime));
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		if (cpPictureDownloadrecord.getSelectType() == 2) {// 左日下载
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.add(Calendar.DATE, -1);
			Date downStartTime = cal.getTime();
			Date downEndTime = new Date();
			cpPictureDownloadrecord.setIsInteriorDownLoad(0);
			cpPictureDownloadrecord.setDownStartTime(sdf.format(downStartTime));
			cpPictureDownloadrecord.setDownEndTime(sdf.format(downEndTime));
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		if (cpPictureDownloadrecord.getSelectType() == 3) {// 本周下载
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date downStartTime = cal.getTime();
			Date downEndTime = new Date();
			cpPictureDownloadrecord.setDownStartTime(sdf.format(downStartTime));
			cpPictureDownloadrecord.setDownEndTime(sdf.format(downEndTime));
			cpPictureDownloadrecord.setIsInteriorDownLoad(0);
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		if (cpPictureDownloadrecord.getSelectType() == 4) {// 本月下载
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date downStartTime = cal.getTime();
			Date downEndTime = new Date();
			cpPictureDownloadrecord.setDownStartTime(sdf.format(downStartTime));
			cpPictureDownloadrecord.setDownEndTime(sdf.format(downEndTime));
			cpPictureDownloadrecord.setIsInteriorDownLoad(0);
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		if (cpPictureDownloadrecord.getSelectType() == 5) {// 上月下载
			// 第一天
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date downStartTime = cal.getTime();
			// 最后一天
			int month = cal.get(Calendar.MONTH);
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date downEndTime = cal.getTime();
			cpPictureDownloadrecord.setIsInteriorDownLoad(0);
			cpPictureDownloadrecord.setDownStartTime(sdf.format(downStartTime));
			cpPictureDownloadrecord.setDownEndTime(sdf.format(downEndTime));
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		if (cpPictureDownloadrecord.getSelectType() == 6) {// 内部下载
			cpPictureDownloadrecord.setIsInteriorDownLoad(1);
			downloadrecordByQuery = clientPictureMapper.downloadrecordByQuery(cpPictureDownloadrecord);
		}
		return downloadrecordByQuery;
	}

	/*
	 * 导出下载记录
	 */
	public HSSFWorkbook exportExcel(List<CpPictureDownloadrecord> downLoadStatistics) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		BufferedImage bufferImg = null;
		HSSFSheet sheet = workbook.createSheet("用户信息");
		// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		// 设置列宽
		sheet.setColumnWidth(0, 25 * 100);
		sheet.setColumnWidth(1, 70 * 100);
		sheet.setColumnWidth(2, 70 * 100);
		sheet.setColumnWidth(3, 70 * 100);
		sheet.setColumnWidth(4, 70 * 100);
		sheet.setColumnWidth(5, 70 * 100);
		sheet.setColumnWidth(6, 70 * 100);
		sheet.setColumnWidth(7, 70 * 100);
		sheet.setColumnWidth(8, 70 * 100);
		sheet.setColumnWidth(9, 70 * 100);
		sheet.setColumnWidth(10, 70 * 100);
		sheet.setColumnWidth(11, 70 * 100);
		// 在sheet中添加表头第0行
		HSSFRow row = sheet.createRow(0);
		// 创建单元格，并设置表头，设置表头居中
		HSSFCellStyle style = workbook.createCellStyle();
		// 创建一个居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 带边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		// 字体增粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 字体大小
		font.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style.setFont(font);
		style.setWrapText(true);// 自动换行

		// 标题样式
		HSSFCellStyle titeStyle = workbook.createCellStyle();
		// 创建一个居中格式
		titeStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font2 = workbook.createFont();
		// 字体增粗
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 字体大小
		font2.setFontHeightInPoints((short) 24);
		// 把字体应用到当前的样式
		titeStyle.setFont(font2);
		// 背景颜色
		titeStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		HSSFCell createCell = row.createCell(0);
		// 合并单元格
		// sheet.addMergedRegion(new Region(0, (short) (0), 0, (short) (a -
		// 1)));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (12 - 1)));
		sheet.setDefaultColumnStyle(0, style);
		createCell.setCellValue("下载统计");
		createCell.setCellStyle(titeStyle);
		// 单独设置整列居中或居左
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setWrapText(true);// 自动换行
		/* row.createCell(0).setCellStyle(style1); */
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row1.createCell(1);
		cell.setCellValue("标题");
		cell.setCellStyle(style);

		cell = row1.createCell(2);
		cell.setCellValue("作者");
		cell.setCellStyle(style);

		cell = row1.createCell(3);
		cell.setCellValue("编辑");
		cell.setCellStyle(style);

		cell = row1.createCell(4);
		cell.setCellValue("文件名");
		cell.setCellStyle(style);

		cell = row1.createCell(5);
		cell.setCellValue("签发时间");
		cell.setCellStyle(style);

		cell = row1.createCell(6);
		cell.setCellValue("下载时间");
		cell.setCellStyle(style);

		cell = row1.createCell(7);
		cell.setCellValue("下载用户");
		cell.setCellStyle(style);

		cell = row1.createCell(8);
		cell.setCellValue("订户类型");
		cell.setCellStyle(style);

		cell = row1.createCell(9);
		cell.setCellValue("IP");
		cell.setCellStyle(style);

		cell = row1.createCell(10);
		cell.setCellValue("下载价格");
		cell.setCellStyle(style);

		cell = row1.createCell(11);
		cell.setCellValue("税前收入");
		cell.setCellStyle(style);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义时间格式

		for (int i = 0; i < downLoadStatistics.size(); i++) {
			try {
				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
				bufferImg = ImageIO.read(new File(downLoadStatistics.get(i).getPictureFilePath()));
				ImageIO.write(bufferImg, "jpg", byteArrayOut);
				row = sheet.createRow(i + 2);
				HSSFCell c1 = row.createCell(0);
				c1.setCellStyle(style1);
				c1.setCellValue(i + 1);

				HSSFCell c2 = row.createCell(1);
				c2.setCellStyle(style1);
				c2.setCellValue(downLoadStatistics.get(i).getPictureTitle());

				HSSFCell c3 = row.createCell(2);
				c3.setCellStyle(style1);
				c3.setCellValue(downLoadStatistics.get(i).getPictureAuthor());

				HSSFCell c4 = row.createCell(3);
				c4.setCellStyle(style1);
				c4.setCellValue(downLoadStatistics.get(i).getEditUser());

				HSSFCell c5 = row.createCell(4);
				c5.setCellStyle(style1);
				// anchor主要用于设置图片的属性
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) (i + 3), i + 4);
				patriarch.createPicture(anchor,
						workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

				HSSFCell c6 = row.createCell(5);
				c6.setCellStyle(style1);
				c6.setCellValue(sdf.format(downLoadStatistics.get(i).getSignEndTime()));

				HSSFCell c7 = row.createCell(6);
				c7.setCellStyle(style1);
				c7.setCellValue(sdf.format(downLoadStatistics.get(i).getDownloadTime()));

				HSSFCell c8 = row.createCell(7);
				c8.setCellStyle(style1);
				Integer type = downLoadStatistics.get(i).getSubscriberType();
				if (type == 1) {
					c8.setCellValue("图书");
				}
				if (type == 2) {
					c8.setCellValue("杂志");
				}
				if (type == 3) {
					c8.setCellValue("报纸");
				}
				if (type == 4) {
					c8.setCellValue("网络");
				}
				if (type == 5) {
					c8.setCellValue("影视");
				}
				if (type == 6) {
					c8.setCellValue("广告");
				}
				if (type == 7) {
					c8.setCellValue("其他");
				}

				HSSFCell c9 = row.createCell(8);
				c9.setCellStyle(style1);
				c9.setCellValue(downLoadStatistics.get(i).getUserIP());

				HSSFCell c10 = row.createCell(9);
				c10.setCellStyle(style1);
				c10.setCellValue(downLoadStatistics.get(i).getPicturePrice());

				HSSFCell c11 = row.createCell(10);
				c11.setCellStyle(style1);
				c11.setCellValue(downLoadStatistics.get(i).getIncomeBefore());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("生成Excel表格异常：" + e.getMessage());
			}

		}
		return workbook;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteDownLoadStatistics(String id) {
		String[] split = id.split(",");
		for (String string : split) {
			clientPictureMapper.delete(Integer.parseInt(string));
		}

	}

}
