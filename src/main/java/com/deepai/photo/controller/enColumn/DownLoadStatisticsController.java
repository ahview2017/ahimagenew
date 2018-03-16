package com.deepai.photo.controller.enColumn;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepai.photo.bean.CpPictureDownloadrecord;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.pagehelper.PageHelper;
import com.deepai.photo.common.pagehelper.PageInfo;
import com.deepai.photo.common.pojo.ResponseMessage;
import com.deepai.photo.service.enColumn.DownLoadStatisticsService;

@Controller
@RequestMapping("/downLoadStatisticsCtrl")
public class DownLoadStatisticsController {
	private Logger log = Logger.getLogger(EnColumnController.class);
	@Autowired
	private DownLoadStatisticsService downLoadStatisticsService;

	@ResponseBody
	@RequestMapping("/showDownLoadStatistics")
	public Object downLoadStatistics(CpPictureDownloadrecord cpPictureDownloadrecord) {
		ResponseMessage result = new ResponseMessage();
		Integer page = cpPictureDownloadrecord.getPage();
		Integer rows = cpPictureDownloadrecord.getRows();
		page = page == null ? 1 : page;
		rows = rows == null ? 20 : rows;
		PageHelper.startPage(page, rows);
		try {
			List<CpPictureDownloadrecord> downLoadStatistics = downLoadStatisticsService.downLoadStatisticsList(cpPictureDownloadrecord);
			PageInfo pageInfo = new PageInfo(downLoadStatistics);
			int c = (int) pageInfo.getTotal();
			int p = pageInfo.getPages();
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
			result.setData(downLoadStatistics);
			result.setPage(p);
			result.setOther(c);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("下载统计查询失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		
		return result;
	}
	@ResponseBody
	@RequestMapping("/deleteDownLoadStatistics")
	public Object deleteDownLoadStatistics(HttpServletRequest request, String id){
		ResponseMessage result = new ResponseMessage();
		try {
			downLoadStatisticsService.deleteDownLoadStatistics(id);
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("下载统计删除失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}
		
		return result;
	}
	//导出excel
	@ResponseBody
	@RequestMapping("/exportExcel")
	public Object exportExcel(HttpServletRequest request,HttpServletResponse response, CpPictureDownloadrecord cpPictureDownloadrecord){
		ResponseMessage result = new ResponseMessage();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try { 
			List<CpPictureDownloadrecord> downLoadStatistics = downLoadStatisticsService.downLoadStatisticsList(cpPictureDownloadrecord);
			HSSFWorkbook hssfWorkbook = downLoadStatisticsService.exportExcel(downLoadStatistics);//生成excel表格
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 定义文件名格式
			String msg = new String(("下载统计_" + format.format(new Date()) + ".xls").getBytes(), "ISO-8859-1");
			// 以导出时间作为文件名
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename=" + msg);
			hssfWorkbook.write(os);
			hssfWorkbook.write(response.getOutputStream());
			result.setCode(CommonConstant.SUCCESSCODE);
			result.setMsg(CommonConstant.SUCCESSSTRING);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("下载统计导出失败：" + e.getMessage());
			result.setCode(CommonConstant.EXCEPTIONCODE);
			result.setMsg(CommonConstant.EXCEPTIONMSG);
		}	
		return result;
	}


}
