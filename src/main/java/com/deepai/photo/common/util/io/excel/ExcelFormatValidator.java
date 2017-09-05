package com.deepai.photo.common.util.io.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.common.util.io.file.FileUtil;

/**
 * 验证EXCEL文件格式正确性
 * EXCEL第一行为表关，默认从第二行开始验证数据
 * @Author:songyunhu
 * @CreateDate:2015-11-5 下午2:46:28
 */
public class ExcelFormatValidator {
	public static Long MAX_FILE_SIZE = 5 * 1024 * 1024l;
	
	private MultipartFile excelFile; // excel源文件
	private int[] cellTypeArray; // 单元格类型数组
	private Boolean valid = false;
	private String errorInfo; // 检测错误信息
	private List<Object[]> dataList = new ArrayList<Object[]>(); // 检测正确后得到的EXCEL内容数据
	
	public ExcelFormatValidator(int[] cellTypeArray, MultipartFile excelFile) throws Exception {
		this.cellTypeArray = cellTypeArray;
		this.excelFile = excelFile;
		this.valid = checkExcelFile();
	}
	
	/**
	 * 检测结果
	 * @return
	 */
	public Boolean isValid() {
		return valid;
	}
	
	/**
	 * 检测错误信息
	 * @return
	 */
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * EXCEL文件数据内容
	 * @return
	 */
	public List<Object[]> getDataList() {
		return dataList;
	}
	
	/**
	 * 检验数据内容并提取数据（只识别第一个工作表）
	 * @return
	 * @throws Exception
	 */
	private Boolean checkExcelFile() throws Exception {
		// 检验文件大小
		if (excelFile == null || excelFile.getSize() == 0 || excelFile.getSize() > MAX_FILE_SIZE) {
			errorInfo = "文件为空或大小超过5M";
			return false;
		}
		
		// 检验文件类型
		String postfix = FileUtil.getPostfix(excelFile.getOriginalFilename());
		if (!postfix.equals("xls") && !postfix.equals("xlsx")) {
			errorInfo = "请上传正确的EXCEL文件，扩展名只能是xls或xlsx";
			return false;
		}
		
		InputStream inputStream = excelFile.getInputStream();
		if ("xls".equals(postfix)) {
			POIFSFileSystem fileStream = new POIFSFileSystem(inputStream);
			HSSFWorkbook workbook = new HSSFWorkbook(fileStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				errorInfo = "EXCEL内容为空，请检查文件内容";
				return false;
			}
			HSSFRow row;
			for (int i = 1; i <= rowNum; i++) {
				row = sheet.getRow(i);
				if (row.getLastCellNum() < cellTypeArray.length) {
					errorInfo = "EXCEL文件的格式不正确！（行" + (i + 1) + "至少" + cellTypeArray.length + "列）";
					return false;
				}
				
				Object[] rowdata = new Object[cellTypeArray.length];
				for (int index = 0; index < cellTypeArray.length; index++) {
					if (cellTypeArray[index] != row.getCell(index).getCellType()) {
						errorInfo = "EXCEL文件(行:" + (i + 1) + ",列:" + (index + 1) + ")数据类型不正确";
						return false;
					}
					rowdata[index] = getCellData(row.getCell(index));
				}
				
				dataList.add(rowdata);
			}
		} else if ("xlsx".equals(postfix)) {
			XSSFWorkbook xWorkbook = new XSSFWorkbook(inputStream);
			XSSFSheet xSheet = xWorkbook.getSheetAt(0);
			int rowNum = xSheet.getLastRowNum();
			if (rowNum < 1) {
				errorInfo = "EXCEL内容为空，请检查文件内容";
				return false;
			}
			XSSFRow xrow;
			for (int i = 1; i <= rowNum; i++) {
				xrow = xSheet.getRow(i);
				if (xrow.getLastCellNum() < cellTypeArray.length) {
					errorInfo = "EXCEL文件的格式不正确！（行" + (i + 1) + "至少两列）";
					return false;
				}
				
				Object[] rowdata = new Object[cellTypeArray.length];
				for (int index = 0; index < cellTypeArray.length; index++) {
					if (cellTypeArray[index] != xrow.getCell(index).getCellType()) {
						errorInfo = "EXCEL文件(行:" + (i + 1) + ",列:" + (index + 1) + ")数据类型不正确";
						return false;
					}
					rowdata[index] = getCellData(xrow.getCell(index));
				}
				
				dataList.add(rowdata);
			}
		}

		return true;
	}
	
	/**
	 * 获取单元格数据
	 * @param cell
	 * @return
	 */
	private Object getCellData(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		default:
			return null;
		}
	}
	
}
