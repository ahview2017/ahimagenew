package com.deepai.photo.service.enColumn;

import java.text.SimpleDateFormat;
import java.util.Iterator;
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
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class ExportUserInfo {
	public HSSFWorkbook exportUser(JSONObject jsonObject, List<Map<String, Object>> userList) {
		int i = 1;
		int a = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应excel文件中的sheet
		HSSFSheet sheet = workbook.createSheet("用户信息");

		// 设置列宽
		sheet.setColumnWidth(0, 25 * 100);
		if (jsonObject.containsKey("userName")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("tureName")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("QQ")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("WeChat")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("address")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}

		if (jsonObject.containsKey("zipcode")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("emailBind")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("emailContact")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("telBind")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("telContact")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("uploadCount")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("pubCount")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("keepCount")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("applyDate")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("regDate")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("downloadLevel")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("isDownload")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("downloadType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractBasePerrice")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractStartTime")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractEndTime")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractLimitType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractLimitNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractLimitDlNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractBuyNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractAllNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("account")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balancePerrice")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balanceBasePerrice")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balanceSale")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balanceLimitType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balacnceRevenue")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balanceLimitNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balanceLimitDlNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("balanceAllNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("contractPerrice")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("threelimitType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("threelimitNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("threeLimitDlNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("downlineNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("onLineNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("allDownloadNum")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("unitname")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("unitAddress")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("unitTel")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("unitFax")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("memo")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("authorName")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("sunscriberType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("isPubllish")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("feeType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("bankAccount")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("bankuserName")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("bankName")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("bankIdCard")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("mallAddress")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("mallUsername")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("mallIdCard")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("mallZipCode")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("disCount")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("photographyDirection")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("subscriptionType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("ZONE")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("province")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("city")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("idCard")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("photographerLevel")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("userStarts")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("isUnit")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("payType")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
/*		if (jsonObject.containsKey("siteId")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}*/
		if (jsonObject.containsKey("usertype")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		if (jsonObject.containsKey("userDivide")) {
			sheet.setColumnWidth(a++, 70 * 100);
		}
		sheet.setColumnWidth(a++, 70 * 100);
		sheet.setColumnWidth(a++, 70 * 100);

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

		//标题样式
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
//		sheet.addMergedRegion(new Region(0, (short) (0), 0, (short) (a - 1)));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (a-1)));
		sheet.setDefaultColumnStyle(0, style);
		createCell.setCellValue("用户信息");
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

		if (jsonObject.containsKey("userName")) {
			cell = row1.createCell(i++);
			cell.setCellValue("登录名");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("tureName")) {
			cell = row1.createCell(i++);
			cell.setCellValue("真实姓名");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("address")) {
			cell = row1.createCell(i++);
			cell.setCellValue("地址");
			cell.setCellStyle(style);
		}

		if (jsonObject.containsKey("zipcode")) {
			cell = row1.createCell(i++);
			cell.setCellValue("用户邮编");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("emailBind")) {
			cell = row1.createCell(i++);
			cell.setCellValue("绑定邮箱");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("emailContact")) {
			cell = row1.createCell(i++);
			cell.setCellValue("联系邮箱");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("telBind")) {
			cell = row1.createCell(i++);
			cell.setCellValue("绑定电话");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("QQ")) {
			cell = row1.createCell(i++);
			cell.setCellValue("QQ号");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("WeChat")) {
			cell = row1.createCell(i++);
			cell.setCellValue("微信号");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("telContact")) {
			cell = row1.createCell(i++);
			cell.setCellValue("联系电话");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("uploadCount")) {
			cell = row1.createCell(i++);
			cell.setCellValue("上传图片数");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("pubCount")) {
			cell = row1.createCell(i++);
			cell.setCellValue("发布图片数");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("keepCount")) {
			cell = row1.createCell(i++);
			cell.setCellValue("留资图片数");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("applyDate")) {
			cell = row1.createCell(i++);
			cell.setCellValue("申请时间");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("regDate")) {
			cell = row1.createCell(i++);
			cell.setCellValue("注册时间");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("downloadLevel")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载级别");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("isDownload")) {
			cell = row1.createCell(i++);
			cell.setCellValue("是否允许下载");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("downloadType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载方式");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractPerrice")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同协议价格");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractBasePerrice")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同分成基价");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractStartTime")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载开始时间");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractEndTime")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载结束时间");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractLimitType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同下载类型");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractLimitNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载限制数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractLimitDlNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("已下载量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractBuyNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同期购买数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同期已下载量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("contractAllNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同下载总数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("account")) {
			cell = row1.createCell(i++);
			cell.setCellValue("账户余额");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balancePerrice")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额-协议价格");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balanceBasePerrice")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额-普通图-分成基价");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balanceSale")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额-特殊图打折系数");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balanceLimitType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额下载限制类型");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balacnceRevenue")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额-税收系数");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balanceLimitNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额内下载限制数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balanceLimitDlNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载限制内已下载数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("balanceAllNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("余额下载总数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("threelimitType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同和余额下载限制类型");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("threelimitNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("合同和余额下载限制数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("threeLimitDlNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("下载限制内已下载数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("downlineNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("线下下载总数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("onLineNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("(在线支付)交易下载数量");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("allDownloadNum")) {
			cell = row1.createCell(i++);
			cell.setCellValue("用户总下载图片数");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("unitname")) {
			cell = row1.createCell(i++);
			cell.setCellValue("单位名称");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("unitAddress")) {
			cell = row1.createCell(i++);
			cell.setCellValue("单位地址");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("unitTel")) {
			cell = row1.createCell(i++);
			cell.setCellValue("单位电话");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("unitFax")) {
			cell = row1.createCell(i++);
			cell.setCellValue("单位传真");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("memo")) {
			cell = row1.createCell(i++);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("authorName")) {
			cell = row1.createCell(i++);
			cell.setCellValue("笔名/联系人");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("sunscriberType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("订户类型");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("isPubllish")) {
			cell = row1.createCell(i++);
			cell.setCellValue("是否公布个人信息");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("feeType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("接收稿费方式");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("bankAccount")) {
			cell = row1.createCell(i++);
			cell.setCellValue("电汇-邮政");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("bankuserName")) {
			cell = row1.createCell(i++);
			cell.setCellValue("开户名");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("bankName")) {
			cell = row1.createCell(i++);
			cell.setCellValue("银行名称");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("bankIdCard")) {
			cell = row1.createCell(i++);
			cell.setCellValue("银行卡号");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("mallAddress")) {
			cell = row1.createCell(i++);
			cell.setCellValue("邮寄-通信地址");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("mallUsername")) {
			cell = row1.createCell(i++);
			cell.setCellValue("邮寄-收稿费人姓名");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("mallIdCard")) {
			cell = row1.createCell(i++);
			cell.setCellValue("邮寄-身份证号");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("mallZipCode")) {
			cell = row1.createCell(i++);
			cell.setCellValue("邮寄-邮政编码");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("disCount")) {
			cell = row1.createCell(i++);
			cell.setCellValue("无折扣-免费");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("photographyDirection")) {
			cell = row1.createCell(i++);
			cell.setCellValue("摄影方向");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("subscriptionType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("订阅类型");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("ZONE")) {
			cell = row1.createCell(i++);
			cell.setCellValue("所属地域");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("province")) {
			cell = row1.createCell(i++);
			cell.setCellValue("所属省");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("city")) {
			cell = row1.createCell(i++);
			cell.setCellValue("所属市");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("idCard")) {
			cell = row1.createCell(i++);
			cell.setCellValue("身份证号");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("photographerLevel")) {
			cell = row1.createCell(i++);
			cell.setCellValue("摄影师等级");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("userStarts")) {
			cell = row1.createCell(i++);
			cell.setCellValue("用户状态");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("isUnit")) {
			cell = row1.createCell(i++);
			cell.setCellValue("是否是企业用户");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("payType")) {
			cell = row1.createCell(i++);
			cell.setCellValue("支付方式");
			cell.setCellStyle(style);
		}
/*		if (jsonObject.containsKey("siteId")) {
			cell = row1.createCell(i++);
			cell.setCellValue("子库");
			cell.setCellStyle(style);
		}*/
		if (jsonObject.containsKey("usertype")) {
			cell = row1.createCell(i++);
			cell.setCellValue("用户类型");
			cell.setCellStyle(style);
		}
		if (jsonObject.containsKey("userDivide")) {
			cell = row1.createCell(i++);
			cell.setCellValue("摄影师分成系数");
			cell.setCellStyle(style);
		}
		cell = row1.createCell(i++);
		cell.setCellValue("创建人");
		cell.setCellStyle(style);
		cell = row1.createCell(i++);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义时间格式
		for (int s = 0; s < userList.size(); s++) {
			row = sheet.createRow(s + 2);
			int b = 1;// 单元格的列
			Map<String, Object> map = userList.get(s); // 创建单元格，并设置值 // 编号列居左
			HSSFCell c1 = row.createCell(0);
			c1.setCellStyle(style1);
			c1.setCellValue(s + 1);
			if (jsonObject.containsKey("userName")) {
				HSSFCell c2 = row.createCell(b++);
				c2.setCellStyle(style1);
				if (map.get("USER_NAME") != null) {
					c2.setCellValue(map.get("USER_NAME").toString());
				} else {
					c2.setCellValue("");
				}

			}
			if (jsonObject.containsKey("tureName")) {
				HSSFCell c3 = row.createCell(b++);
				c3.setCellStyle(style1);
				if (map.get("TURE_NAME") != null) {
					c3.setCellValue(map.get("TURE_NAME").toString());
				} else {
					c3.setCellValue("");
				}

			}
			if (jsonObject.containsKey("address")) {
				HSSFCell c4 = row.createCell(b++);
				c4.setCellStyle(style1);
				if (map.get("ADDRESS") != null) {
					c4.setCellValue(map.get("ADDRESS").toString());
				} else {
					c4.setCellValue("");
				}

			}
			if (jsonObject.containsKey("zipcode")) {
				HSSFCell c5 = row.createCell(b++);
				c5.setCellStyle(style1);
				if (map.get("ZIPCODE") != null) {
					c5.setCellValue(map.get("ZIPCODE").toString());
				} else {
					c5.setCellValue("");
				}

			}
			if (jsonObject.containsKey("emailBind")) {
				HSSFCell c6 = row.createCell(b++);
				c6.setCellStyle(style1);
				if (map.get("EMAIL_BIND") != null) {
					c6.setCellValue(map.get("EMAIL_BIND").toString());
				} else {
					c6.setCellValue("");
				}

			}
			if (jsonObject.containsKey("emailContact")) {
				HSSFCell c7 = row.createCell(b++);
				c7.setCellStyle(style1);
				if (map.get("EMAIL_CONTACT") != null) {
					c7.setCellValue(map.get("EMAIL_CONTACT").toString());
				} else {
					c7.setCellValue("");
				}

			}
			if (jsonObject.containsKey("telBind")) {
				HSSFCell c8 = row.createCell(b++);
				c8.setCellStyle(style1);
				if (map.get("TEL_BIND") != null) {
					c8.setCellValue(map.get("TEL_BIND").toString());
				} else {
					c8.setCellValue("");
				}

			}
			if (jsonObject.containsKey("telContact")) {
				HSSFCell c9 = row.createCell(b++);
				c9.setCellStyle(style1);
				if (map.get("TEL_CONTACT") != null) {
					c9.setCellValue(map.get("TEL_CONTACT").toString());
				} else {
					c9.setCellValue("");
				}

			}
			if (jsonObject.containsKey("QQ")) {
				HSSFCell c8 = row.createCell(b++);
				c8.setCellStyle(style1);
				if (map.get("STANDBY1") != null) {
					c8.setCellValue(map.get("STANDBY1").toString());
				} else {
					c8.setCellValue("");
				}

			}
			if (jsonObject.containsKey("WeChat")) {
				HSSFCell c9 = row.createCell(b++);
				c9.setCellStyle(style1);
				if (map.get("STANDBY2") != null) {
					c9.setCellValue(map.get("STANDBY2").toString());
				} else {
					c9.setCellValue("");
				}

			}
			if (jsonObject.containsKey("uploadCount")) {
				HSSFCell c10 = row.createCell(b++);
				c10.setCellStyle(style1);
				if (map.get("UPLOAD_COUNT") != null) {
					c10.setCellValue(map.get("UPLOAD_COUNT").toString());
				} else {
					c10.setCellValue("");
				}

			}
			if (jsonObject.containsKey("pubCount")) {
				HSSFCell c11 = row.createCell(b++);
				c11.setCellStyle(style1);
				if (map.get("PUB_COUNT") != null) {
					c11.setCellValue(map.get("PUB_COUNT").toString());
				} else {
					c11.setCellValue("");
				}

			}
			if (jsonObject.containsKey("keepCount")) {
				HSSFCell c12 = row.createCell(b++);
				c12.setCellStyle(style1);
				if (map.get("KEEP_COUNT") != null) {
					c12.setCellValue(map.get("KEEP_COUNT").toString());
				} else {
					c12.setCellValue("");
				}

			}
			if (jsonObject.containsKey("applyDate")) {
				HSSFCell c13 = row.createCell(b++);
				c13.setCellStyle(style1);
				if (map.get("APPLY_DATE") != null) {
					c13.setCellValue(sdf.format(map.get("APPLY_DATE")));
				} else {
					c13.setCellValue("");
				}

			}
			if (jsonObject.containsKey("regDate")) {
				HSSFCell c14 = row.createCell(b++);
				c14.setCellStyle(style1);
				if (map.get("REG_DATE") != null) {
					c14.setCellValue(sdf.format(map.get("REG_DATE")));
				} else {
					c14.setCellValue("");
				}

			}
			if (jsonObject.containsKey("downloadLevel")) {
				HSSFCell c15 = row.createCell(b++);
				c15.setCellStyle(style1);
				if (map.get("DOWNLOAD_LEVEL") != null) {
					c15.setCellValue(map.get("DOWNLOAD_LEVEL").toString());
				} else {
					c15.setCellValue("");
				}

			}
			if (jsonObject.containsKey("isDownload")) {
				HSSFCell c16 = row.createCell(b++);
				c16.setCellStyle(style1);
				if (map.get("IS_DOWNLOAD") != null) {
					if (Integer.parseInt(map.get("IS_DOWNLOAD").toString()) == 1) {
						c16.setCellValue("允许下载");
					} else {
						c16.setCellValue("不能下载");
					}

				} else {
					c16.setCellValue("");
				}
			}
			if (jsonObject.containsKey("downloadType")) {
				HSSFCell c16 = row.createCell(b++);
				c16.setCellStyle(style1);
				if (map.get("DOWNLOAD_TYPE") != null) {
					if (Integer.parseInt(map.get("DOWNLOAD_TYPE").toString()) == 0) {
						c16.setCellValue("充值");
					} else if (Integer.parseInt(map.get("DOWNLOAD_TYPE").toString()) == 1) {
						c16.setCellValue("合同");
					} else if (Integer.parseInt(map.get("DOWNLOAD_TYPE").toString()) == 2) {
						c16.setCellValue("充值与合同");
					}
				} else {
					c16.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractPerrice")) {
				HSSFCell c17 = row.createCell(b++);
				c17.setCellStyle(style1);
				if (map.get("CONTRACT_PERPRICE") != null) {
					c17.setCellValue(map.get("CONTRACT_PERPRICE").toString());
				} else {
					c17.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractBasePerrice")) {
				HSSFCell c18 = row.createCell(b++);
				c18.setCellStyle(style1);
				if (map.get("CONTRACT_BASE_PERPRICE") != null) {
					c18.setCellValue(map.get("CONTRACT_BASE_PERPRICE").toString());
				} else {
					c18.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractStartTime")) {
				HSSFCell c19 = row.createCell(b++);
				c19.setCellStyle(style1);
				if (map.get("CONTRACT_START_TIME") != null) {
					c19.setCellValue(sdf.format(map.get("CONTRACT_START_TIME")));
				} else {
					c19.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractEndTime")) {
				HSSFCell c20 = row.createCell(b++);
				c20.setCellStyle(style1);
				if (map.get("CONTRACT_END_TIME") != null) {
					c20.setCellValue(sdf.format(map.get("CONTRACT_END_TIME")));
				} else {
					c20.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractLimitType")) {
				HSSFCell c21 = row.createCell(b++);
				c21.setCellStyle(style1);
				if (map.get("CONTRACT_LIMIT_TYPE") != null) {
					if (Integer.parseInt(map.get("CONTRACT_LIMIT_TYPE").toString()) == 0) {
						c21.setCellValue("每天");
					} else if (Integer.parseInt(map.get("CONTRACT_LIMIT_TYPE").toString()) == 1) {
						c21.setCellValue("每月");
					} else if (Integer.parseInt(map.get("CONTRACT_LIMIT_TYPE").toString()) == 2) {
						c21.setCellValue("每年");
					} else if (Integer.parseInt(map.get("CONTRACT_LIMIT_TYPE").toString()) == 2) {
						c21.setCellValue("总共");
					}
				} else {
					c21.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractLimitNum")) {
				HSSFCell c22 = row.createCell(b++);
				c22.setCellStyle(style1);
				if (map.get("CONTRACT_LIMIT_NUM") != null) {
					c22.setCellValue(map.get("CONTRACT_LIMIT_NUM").toString());
				} else {
					c22.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractLimitDlNum")) {
				HSSFCell c23 = row.createCell(b++);
				c23.setCellStyle(style1);
				if (map.get("CONTRACT_LIMIT_DL_NUM") != null) {
					c23.setCellValue(map.get("CONTRACT_LIMIT_DL_NUM").toString());
				} else {
					c23.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractBuyNum")) {
				HSSFCell c24 = row.createCell(b++);
				c24.setCellStyle(style1);
				if (map.get("CONTRACT_BUY_NUM") != null) {
					if (map.get("CONTRACT_BUY_NUM").toString().equals("-99")) {
						c24.setCellValue("不限制");
					} else {
						c24.setCellValue(map.get("CONTRACT_BUY_NUM").toString());
					}
				} else {
					c24.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractNum")) {
				HSSFCell c25 = row.createCell(b++);
				c25.setCellStyle(style1);
				if (map.get("CONTRACT_NUM") != null) {
					c25.setCellValue(map.get("CONTRACT_NUM").toString());
				} else {
					c25.setCellValue("");
				}
			}
			if (jsonObject.containsKey("contractAllNum")) {
				HSSFCell c26 = row.createCell(b++);
				c26.setCellStyle(style1);
				if (map.get("CONTRACT_ALL_NUM") != null) {
					c26.setCellValue(map.get("CONTRACT_ALL_NUM").toString());
				} else {
					c26.setCellValue("");
				}
			}
			if (jsonObject.containsKey("account")) {
				HSSFCell c27 = row.createCell(b++);
				c27.setCellStyle(style1);
				if (map.get("ACCOUNT") != null) {
					c27.setCellValue(map.get("ACCOUNT").toString());
				} else {
					c27.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balancePerrice")) {
				HSSFCell c28 = row.createCell(b++);
				c28.setCellStyle(style1);
				if (map.get("BALANCE_PERPRICE") != null) {
					c28.setCellValue(map.get("BALANCE_PERPRICE").toString());
				} else {
					c28.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balanceBasePerrice")) {
				HSSFCell c29 = row.createCell(b++);
				c29.setCellStyle(style1);
				if (map.get("BALANCE_BASE_PERPRICE") != null) {
					c29.setCellValue(map.get("BALANCE_BASE_PERPRICE").toString());
				} else {
					c29.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balanceSale")) {
				HSSFCell c30 = row.createCell(b++);
				c30.setCellStyle(style1);
				if (map.get("BALANCE_SALE") != null) {
					if (map.get("BALANCE_SALE").toString().equals("100%")) {
						c30.setCellValue("不打折");
					}
					c30.setCellValue(map.get("BALANCE_SALE").toString());
				} else {
					c30.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balanceLimitType")) {
				HSSFCell c31 = row.createCell(b++);
				c31.setCellStyle(style1);
				if (map.get("BALANCE_LIMIT_TYPE") != null) {
					if (Integer.parseInt(map.get("BALANCE_LIMIT_TYPE").toString()) == 0) {
						c31.setCellValue("每天");
					} else if (Integer.parseInt(map.get("BALANCE_LIMIT_TYPE").toString()) == 1) {
						c31.setCellValue("每月");
					} else if (Integer.parseInt(map.get("BALANCE_LIMIT_TYPE").toString()) == 2) {
						c31.setCellValue("每年");
					} else if (Integer.parseInt(map.get("BALANCE_LIMIT_TYPE").toString()) == 2) {
						c31.setCellValue("总共");
					}
				} else {
					c31.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balacnceRevenue")) {
				HSSFCell c32 = row.createCell(b++);
				c32.setCellStyle(style1);
				if (map.get("BALANCE_REVENUE") != null) {
					c32.setCellValue(map.get("BALANCE_REVENUE").toString());
				} else {
					c32.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balanceLimitNum")) {
				HSSFCell c33 = row.createCell(b++);
				c33.setCellStyle(style1);
				if (map.get("BALANCE_LIMIT_NUM") != null) {
					c33.setCellValue(map.get("BALANCE_LIMIT_NUM").toString());
				} else {
					c33.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balanceLimitDlNum")) {
				HSSFCell c34 = row.createCell(b++);
				c34.setCellStyle(style1);
				if (map.get("BALANCE_LIMIT_DL_NUM") != null) {
					c34.setCellValue(map.get("BALANCE_LIMIT_DL_NUM").toString());
				} else {
					c34.setCellValue("");
				}
			}
			if (jsonObject.containsKey("balanceAllNum")) {
				HSSFCell c35 = row.createCell(b++);
				c35.setCellStyle(style1);
				if (map.get("BALANCE_ALL_NUM") != null) {
					c35.setCellValue(map.get("BALANCE_ALL_NUM").toString());
				} else {
					c35.setCellValue("");
				}
			}
			if (jsonObject.containsKey("threelimitType")) {
				HSSFCell c36 = row.createCell(b++);
				c36.setCellStyle(style1);
				if (map.get("THREE_LIMIT_TYPE") != null) {
					if (Integer.parseInt(map.get("THREE_LIMIT_TYPE").toString()) == 0) {
						c36.setCellValue("每天");
					} else if (Integer.parseInt(map.get("THREE_LIMIT_TYPE").toString()) == 1) {
						c36.setCellValue("每月");
					} else if (Integer.parseInt(map.get("THREE_LIMIT_TYPE").toString()) == 2) {
						c36.setCellValue("每年");
					} else if (Integer.parseInt(map.get("THREE_LIMIT_TYPE").toString()) == 2) {
						c36.setCellValue("总共");
					}
				} else {
					c36.setCellValue("");
				}
			}
			if (jsonObject.containsKey("threelimitNum")) {
				HSSFCell c37 = row.createCell(b++);
				c37.setCellStyle(style1);
				if (map.get("THREE_LIMIT_NUM") != null) {
					c37.setCellValue(map.get("THREE_LIMIT_NUM").toString());
				} else {
					c37.setCellValue("");
				}
			}
			if (jsonObject.containsKey("threeLimitDlNum")) {
				HSSFCell c38 = row.createCell(b++);
				c38.setCellStyle(style1);
				if (map.get("THREE_LIMIT_DL_NUM") != null) {
					c38.setCellValue(map.get("THREE_LIMIT_DL_NUM").toString());
				} else {
					c38.setCellValue("");
				}
			}
			if (jsonObject.containsKey("downlineNum")) {
				HSSFCell c39 = row.createCell(b++);
				c39.setCellStyle(style1);
				if (map.get("DOWN_LINE_NUM") != null) {
					c39.setCellValue(map.get("DOWN_LINE_NUM").toString());
				} else {
					c39.setCellValue("");
				}
			}
			if (jsonObject.containsKey("onLineNum")) {
				HSSFCell c40 = row.createCell(b++);
				c40.setCellStyle(style1);
				if (map.get("ON_LINE_NUM") != null) {
					c40.setCellValue(map.get("ON_LINE_NUM").toString());
				} else {
					c40.setCellValue("");
				}
			}
			if (jsonObject.containsKey("allDownloadNum")) {
				HSSFCell c41 = row.createCell(b++);
				c41.setCellStyle(style1);
				if (map.get("ALL_DOWNLOAD_NUM") != null) {
					c41.setCellValue(map.get("ALL_DOWNLOAD_NUM").toString());
				} else {
					c41.setCellValue("");
				}
			}
			if (jsonObject.containsKey("unitname")) {
				HSSFCell c42 = row.createCell(b++);
				c42.setCellStyle(style1);
				if (map.get("UNIT_NAME") != null) {
					c42.setCellValue(map.get("UNIT_NAME").toString());
				} else {
					c42.setCellValue("");
				}
			}
			if (jsonObject.containsKey("unitAddress")) {
				HSSFCell c43 = row.createCell(b++);
				c43.setCellStyle(style1);
				if (map.get("UNIT_ADDRESS") != null) {
					c43.setCellValue(map.get("UNIT_ADDRESS").toString());
				} else {
					c43.setCellValue("");
				}
			}
			if (jsonObject.containsKey("unitTel")) {
				HSSFCell c44 = row.createCell(b++);
				c44.setCellStyle(style1);
				if (map.get("UNIT_TEL") != null) {
					c44.setCellValue(map.get("UNIT_TEL").toString());
				} else {
					c44.setCellValue("");
				}
			}
			if (jsonObject.containsKey("unitFax")) {
				HSSFCell c45 = row.createCell(b++);
				c45.setCellStyle(style1);
				if (map.get("UNIT_FAX") != null) {
					c45.setCellValue(map.get("UNIT_FAX").toString());
				} else {
					c45.setCellValue("");
				}
			}
			if (jsonObject.containsKey("memo")) {
				HSSFCell c46 = row.createCell(b++);
				c46.setCellStyle(style1);
				if (map.get("MEMO") != null) {
					c46.setCellValue(map.get("MEMO").toString());
				} else {
					c46.setCellValue("");
				}
			}
			if (jsonObject.containsKey("authorName")) {
				HSSFCell c47 = row.createCell(b++);
				c47.setCellStyle(style1);
				if (map.get("AUTHOR_NAME") != null) {
					c47.setCellValue(map.get("AUTHOR_NAME").toString());
				} else {
					c47.setCellValue("");
				}
			}
			if (jsonObject.containsKey("sunscriberType")) {
				HSSFCell c48 = row.createCell(b++);
				c48.setCellStyle(style1);
				if (map.get("SUBSCRIBER_TYPE") != null) {
					if (map.get("SUBSCRIBER_TYPE").toString().equals("0")) {
						c48.setCellValue("个人");
					} else {
						c48.setCellValue("企业");
					}
				} else {
					c48.setCellValue("");
				}
			}
			if (jsonObject.containsKey("isPubllish")) {
				HSSFCell c49 = row.createCell(b++);
				c49.setCellStyle(style1);
				if (map.get("IS_PUBLISH") != null) {
					if (map.get("IS_PUBLISH").toString().equals("0")) {
						c49.setCellValue("是");
					} else {
						c49.setCellValue("否");
					}
				} else {
					c49.setCellValue("");
				}
			}
			if (jsonObject.containsKey("feeType")) {
				HSSFCell c50 = row.createCell(b++);
				c50.setCellStyle(style1);
				if (map.get("FEE_TYPE") != null) {
					if (map.get("FEE_TYPE").toString().equals("0")) {
						c50.setCellValue("邮寄");
					} else {
						c50.setCellValue("电汇");
					}
				} else {
					c50.setCellValue("");
				}
			}
			if (jsonObject.containsKey("bankAccount")) {
				HSSFCell c51 = row.createCell(b++);
				c51.setCellStyle(style1);
				if (map.get("BANK_ACCOUNT") != null) {
					c51.setCellValue(map.get("BANK_ACCOUNT").toString());
				} else {
					c51.setCellValue("");
				}
			}
			if (jsonObject.containsKey("bankuserName")) {
				HSSFCell c52 = row.createCell(b++);
				c52.setCellStyle(style1);
				if (map.get("BANK_USERNAME") != null) {
					c52.setCellValue(map.get("BANK_USERNAME").toString());
				} else {
					c52.setCellValue("");
				}
			}
			if (jsonObject.containsKey("bankName")) {
				HSSFCell c53 = row.createCell(b++);
				c53.setCellStyle(style1);
				if (map.get("BANK_NAME") != null) {
					c53.setCellValue(map.get("BANK_NAME").toString());
				} else {
					c53.setCellValue("");
				}
			}
			if (jsonObject.containsKey("bankIdCard")) {
				HSSFCell c54 = row.createCell(b++);
				c54.setCellStyle(style1);
				if (map.get("BANK_ID_CARD") != null) {
					c54.setCellValue(map.get("BANK_ID_CARD").toString());
				} else {
					c54.setCellValue("");
				}
			}
			if (jsonObject.containsKey("mallAddress")) {
				HSSFCell c55 = row.createCell(b++);
				c55.setCellStyle(style1);
				if (map.get("MAIL_ADDRESS") != null) {
					c55.setCellValue(map.get("MAIL_ADDRESS").toString());
				} else {
					c55.setCellValue("");
				}
			}
			if (jsonObject.containsKey("mallUsername")) {
				HSSFCell c56 = row.createCell(b++);
				c56.setCellStyle(style1);
				if (map.get("MAIL_USERNAME") != null) {
					c56.setCellValue(map.get("MAIL_USERNAME").toString());
				} else {
					c56.setCellValue("");
				}
			}
			if (jsonObject.containsKey("mallIdCard")) {
				HSSFCell c57 = row.createCell(b++);
				c57.setCellStyle(style1);
				if (map.get("MAIL_ID_CARD") != null) {
					c57.setCellValue(map.get("MAIL_ID_CARD").toString());
				} else {
					c57.setCellValue("");
				}
			}
			if (jsonObject.containsKey("mallZipCode")) {
				HSSFCell c58 = row.createCell(b++);
				c58.setCellStyle(style1);
				if (map.get("MAIL_ZIP_CODE") != null) {
					c58.setCellValue(map.get("MAIL_ZIP_CODE").toString());
				} else {
					c58.setCellValue("");
				}
			}
			if (jsonObject.containsKey("disCount")) {
				HSSFCell c59 = row.createCell(b++);
				c59.setCellStyle(style1);
				if (map.get("DISCOUNT") != null) {
					c59.setCellValue(map.get("DISCOUNT").toString());
				} else {
					c59.setCellValue("");
				}
			}
			if (jsonObject.containsKey("photographyDirection")) {
				HSSFCell c60 = row.createCell(b++);
				c60.setCellStyle(style1);
				if (map.get("PHOTOGRAPHY_DIRECTION") != null) {
					String str="";
					String string = map.get("PHOTOGRAPHY_DIRECTION").toString();
					
					
					for(int c = 0; c < string.length(); c++){
						char index='1';
						if(c==0){
							char ss = string.charAt(c);
							if(ss==index){
								str +="时政"+",";
							}
						}
						if(c==1){
							char ss = string.charAt(c);
							if(ss==index){
								str +="财经"+",";
							}
						}
						if(c==2){
							char ss = string.charAt(c);
							if(ss==index){
								str +="娱乐"+",";
							}
							
						}
						if(c==3){
							char ss = string.charAt(c);
							if(ss==index){
								str +="体育"+",";
							}
							
						}
						if(c==4){
							char ss = string.charAt(c);
							if(ss==index){
								str +="社会"+",";
							}
						}
						if(c==5){
							char ss = string.charAt(c);
							if(ss==index){
								str +="风光"+",";
							}						
						}
						if(c==6){
							char ss = string.charAt(c);
							if(ss==index){
								str +="图标漫画"+",";
							}	
						}
						if(c==7){
							char ss = string.charAt(c);
							if(ss==index){
								str +="其他";
							}	
						}
					}
				
					c60.setCellValue(str);
				
			}
			}
			if (jsonObject.containsKey("subscriptionType")) {
				HSSFCell c61 = row.createCell(b++);
				c61.setCellStyle(style1);
				if (map.get("SUBSCRIPTION_TYPE") != null) {
					c61.setCellValue(map.get("SUBSCRIPTION_TYPE").toString());
				} else {
					c61.setCellValue("");
				}
			}
			if (jsonObject.containsKey("ZONE")) {
				HSSFCell c62 = row.createCell(b++);
				c62.setCellStyle(style1);
				if (map.get("ZONE") != null) {
					c62.setCellValue(map.get("ZONE").toString());
				} else {
					c62.setCellValue("");
				}
			}
			if (jsonObject.containsKey("province")) {
				HSSFCell c63 = row.createCell(b++);
				c63.setCellStyle(style1);
				if (map.get("PROVINCE") != null) {
					c63.setCellValue(map.get("PROVINCE").toString());
				} else {
					c63.setCellValue("");
				}
			}
			if (jsonObject.containsKey("city")) {
				HSSFCell c64 = row.createCell(b++);
				c64.setCellStyle(style1);
				if (map.get("CITY") != null) {
					c64.setCellValue(map.get("CITY").toString());
				} else {
					c64.setCellValue("");
				}
			}
			if (jsonObject.containsKey("idCard")) {
				HSSFCell c65 = row.createCell(b++);
				c65.setCellStyle(style1);
				if (map.get("ID_CARD") != null) {
					c65.setCellValue(map.get("ID_CARD").toString());
				} else {
					c65.setCellValue("");
				}
			}
			if (jsonObject.containsKey("photographerLevel")) {
				HSSFCell c66 = row.createCell(b++);
				c66.setCellStyle(style1);
				if (map.get("PHOTOGRAPHER_LEVEL") != null) {
					if (map.get("PHOTOGRAPHER_LEVEL").toString().equals("0")) {
						c66.setCellValue("未设");
					} else {
						c66.setCellValue(map.get("PHOTOGRAPHER_LEVEL").toString() + "级");
					}

				} else {
					c66.setCellValue("");
				}
			}
			if (jsonObject.containsKey("userStarts")) {
				HSSFCell c67 = row.createCell(b++);
				c67.setCellStyle(style1);
				if (map.get("USER_STATUS") != null) {
					if (map.get("USER_STATUS").toString().equals("0")) {
						c67.setCellValue("未开通");
					}
					if (map.get("USER_STATUS").toString().equals("1")) {
						c67.setCellValue("正申请");
					}
					if (map.get("USER_STATUS").toString().equals("2")) {
						c67.setCellValue("已删除");
					}
					if (map.get("USER_STATUS").toString().equals("3")) {
						c67.setCellValue("已禁用");
					}
				} else {
					c67.setCellValue("");
				}
			}
			if (jsonObject.containsKey("isUnit")) {
				HSSFCell c68 = row.createCell(b++);
				c68.setCellStyle(style1);
				if (map.get("IS_UNIT") != null) {
					if (map.get("IS_UNIT").toString().equals("0")) {
						c68.setCellValue("个人");
					}
					if (map.get("IS_UNIT").toString().equals("1")) {
						c68.setCellValue("企业");
					} else {
						c68.setCellValue("订户");
					}

				} else {
					c68.setCellValue("");
				}
			}
			if (jsonObject.containsKey("payType")) {
				HSSFCell c69 = row.createCell(b++);
				c69.setCellStyle(style1);
				if (map.get("PAY_TYPE") != null) {
					if (map.get("PAY_TYPE").toString().equals("0")) {
						c69.setCellValue("下载管理方式");
					}
					if (map.get("PAY_TYPE").toString().equals("1")) {
						c69.setCellValue("账号余额支付");
					}
				} else {
					c69.setCellValue("");
				}
			}
	/*		if (jsonObject.containsKey("siteId")) {
				HSSFCell c70 = row.createCell(b++);
				c70.setCellStyle(style1);
				if (map.get("SITE_ID") != null) {
					c70.setCellValue(map.get("SITE_ID").toString());
				} else {
					c70.setCellValue("");
				}
			}*/
			if (jsonObject.containsKey("usertype")) {
				HSSFCell c71 = row.createCell(b++);
				c71.setCellStyle(style1);
				if (map.get("USER_TYPE") != null) {
					if (map.get("USER_TYPE").toString().equals("0")) {
						c71.setCellValue("普通用户");
					}
					if (map.get("USER_TYPE").toString().equals("1")) {
						c71.setCellValue("");
					}
				} else {
					c71.setCellValue("");
				}
			}
			if (jsonObject.containsKey("userDivide")) {
				HSSFCell c72 = row.createCell(b++);
				c72.setCellStyle(style1);
				if (map.get("USER_DIVIDE") != null) {

					c72.setCellValue(map.get("USER_DIVIDE").toString());

				} else {
					c72.setCellValue("");
				}
			}
			HSSFCell c73 = row.createCell(b++);
			c73.setCellStyle(style1);
			if (map.get("CREATE_USER") != null) {
				c73.setCellValue(map.get("CREATE_USER").toString());
			}
			c73.setCellValue("");
			HSSFCell c74 = row.createCell(b++);
			c74.setCellStyle(style1);
			if (map.get("CREATE_TIME") != null) {
				c74.setCellValue(sdf.format(map.get("CREATE_TIME")));
			} else {
				c74.setCellValue("");
			}

		}

		return workbook;
	}

}
