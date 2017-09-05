package com.deepai.photo.service.admin;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpLog;
import com.deepai.photo.bean.CpLogType;
import com.deepai.photo.bean.CpLogTypeExample;
import com.deepai.photo.mapper.CpLogMapper;
import com.deepai.photo.mapper.CpLogTypeMapper;

import net.sf.json.JSONObject;

/**
 * @author guoyanhong 日志相关
 *
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class LogService {
	@Autowired
	private CpLogTypeMapper cpLogTypeMapper;

	@Autowired
	private CpLogMapper cpLogMapper;

	/**
	 * 根据日志类型code获取id
	 * 
	 * @param code
	 */
	public Integer getLogTypeIdByCode(String code) {
		CpLogTypeExample example = new CpLogTypeExample();
		example.createCriteria().andLogCodeEqualTo(code);
		List<CpLogType> list = cpLogTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getId();
		} else {
			return 0;
		}
	}

	/**
	 * 添加日志信息
	 * 
	 * @param log
	 */
	public void addLog(CpLog log) {
		cpLogMapper.insertSelective(log);
	}

	public HSSFWorkbook exportLog(List<Map<String, Object>> logList) {
		int i = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应excel文件中的sheet
		HSSFSheet sheet = workbook.createSheet("系统日志");

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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		sheet.setDefaultColumnStyle(0, style);
		createCell.setCellValue("系统日志信息");
		createCell.setCellStyle(titeStyle);
		// 单独设置整列居中或居左
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setWrapText(true);// 自动换行
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		HSSFCell c1 = row1.createCell(1);
		c1.setCellValue("日志类型");
		c1.setCellStyle(style);
		HSSFCell c2 = row1.createCell(2);
		c2.setCellValue("用户IP");
		c2.setCellStyle(style);
		HSSFCell c3 = row1.createCell(3);
		c3.setCellValue("操作用户");
		c3.setCellStyle(style);
		HSSFCell c4 = row1.createCell(4);
		c4.setCellValue("操作对象ID");
		c4.setCellStyle(style);
		HSSFCell c5 = row1.createCell(5);
		c5.setCellValue("操作内容");
		c5.setCellStyle(style);
		HSSFCell c6 = row1.createCell(6);
		c6.setCellValue("操作时间");
		c6.setCellStyle(style);
		HSSFCell c7 = row1.createCell(7);
		c7.setCellValue("操作类型");
		c7.setCellStyle(style);
		HSSFCell c8 = row1.createCell(8);
		c8.setCellValue("操作结果");
		c8.setCellStyle(style);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 填充数据
		for (int s = 0; s < logList.size(); s++) {
			Map<String, Object> map = logList.get(s);
			row = sheet.createRow(s + 2);
			HSSFCell c0 = row.createCell(0);
			c0.setCellStyle(style1);
			c0.setCellValue(s + 1);
			HSSFCell hssf1 = row.createCell(1);
			hssf1.setCellStyle(style1);
			if (map.get("LOGTYPE_NAME") != null) {
				hssf1.setCellValue(map.get("LOGTYPE_NAME").toString());
			} else {
				hssf1.setCellValue("");
			}
			HSSFCell hssf2 = row.createCell(2);
			hssf2.setCellStyle(style1);
			if (map.get("OPE_IP") != null) {
				hssf2.setCellValue(map.get("OPE_IP").toString());
			} else {
				hssf2.setCellValue("");
			}
			HSSFCell hssf3 = row.createCell(3);
			hssf3.setCellStyle(style1);
			if (map.get("OPE_USER") != null) {
				hssf3.setCellValue(map.get("OPE_USER").toString());
			} else {
				hssf3.setCellValue("");
			}
			HSSFCell hssf4 = row.createCell(4);
			hssf4.setCellStyle(style1);
			if (map.get("OPE_IDS") != null) {
				hssf4.setCellValue(map.get("OPE_IDS").toString());
			} else {
				hssf4.setCellValue("");
			}
			HSSFCell hssf5 = row.createCell(5);
			hssf5.setCellStyle(style1);
			if (map.get("OPE_CONTENT") != null) {
				hssf5.setCellValue(map.get("OPE_CONTENT").toString());
			} else {
				hssf5.setCellValue("");
			}
			HSSFCell hssf6 = row.createCell(6);
			hssf6.setCellStyle(style1);
			if (map.get("OPE_TIME") != null) {
				hssf6.setCellValue(format.format(map.get("OPE_TIME")));
			} else {
				hssf6.setCellValue("");
			}
			HSSFCell hssf7 = row.createCell(7);
			hssf7.setCellStyle(style1);
			if (map.get("OPE_TYPE") != null) {
				if (map.get("OPE_TYPE") != null) {
					if (map.get("OPE_TYPE").toString().equals("0")) {
						hssf7.setCellValue("增");
					}
					if (map.get("OPE_TYPE").toString().equals("1")) {
						hssf7.setCellValue("删");
					}
					if (map.get("OPE_TYPE").toString().equals("2")) {
						hssf7.setCellValue("改");
					}
				} else {
					hssf7.setCellValue("");
				}
				HSSFCell hssf8 = row.createCell(8);
				hssf8.setCellStyle(style1);
				if (map.get("OPE_RESULT") != null) {
					hssf8.setCellValue(map.get("OPE_RESULT").toString());
				} else {
					hssf8.setCellValue("");
				}

			}
		}
		return workbook;
	}
}