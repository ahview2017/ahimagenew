package com.deepai.photo.service.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deepai.photo.bean.CpCategory;
import com.deepai.photo.bean.CpCategoryExample;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.common.util.TreeUtil;
import com.deepai.photo.common.util.date.DateUtils;
import com.deepai.photo.common.util.image.ImgFileUtils;
import com.deepai.photo.common.util.io.upload.FileUpload;
import com.deepai.photo.common.util.json.JSONUtils;
import com.deepai.photo.common.util.json.JsonUtil;
import com.deepai.photo.mapper.CpCategoryMapper;

/**
 * 分类
 * 
 * @author zhangshuo
 *
 */
@Service
@Transactional(readOnly = false, noRollbackFor = Exception.class)
public class ClassificationService {
	
	private Logger logger = Logger.getLogger(ClassificationService.class);
	
	@Autowired
	private CpCategoryMapper categoryMapper;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private SysConfigService sysConfigService;
	@Value("#{configProperties['ipAdd']}")
	private  String ipAdd;

	/**
	 * 更新redis里存储的分类
	 * 
	 * @param siteId
	 * @return String
	 */
	private String upredisCpCpCategorys(Integer siteId,Integer langType) {
		CpCategoryExample example = new CpCategoryExample();
		example.or().andDeleteFlagEqualTo(CommonConstant.BYTE0)
				.andSiteIdEqualTo(siteId).andLangTypeEqualTo(langType);
		example.setOrderByClause("CATEGORY_GRADE,CATEGORY_ORDER");
		List<CpCategory> cpCategorys = categoryMapper.selectByExample(example);
		for (CpCategory cpCategory : cpCategorys) {
			if (cpCategory.getParentId()==null) {
				System.out.println(cpCategory.getId() + ":name=" +cpCategory.getCategoryName());
			}
		}
		TreeUtil treeUtil = new TreeUtil(cpCategorys);
		CpCategory category1 = treeUtil.generateTreeNode(0);
		List<CpCategory> cpCategorys1 = category1.getCategories();
		String category = JsonUtil.getString(cpCategorys1);
		redisClientTemplate.set("category", category);
		return category;
	}

	/**
	 * 生成图片名
	 * 
	 * @return String
	 */
	private String getFileName() {
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtils.getFormattedTime(DateUtils.sdfLongTime));
		sb.append(System.currentTimeMillis());
		sb.append("_cg.png");
		return sb.toString();

	}

	/**
	 * 查询分类
	 * @param langType 
	 * 
	 * @return String
	 * @throws Exception
	 */
	public List<Map<String, Object>> selCpCategorys(Integer siteId, Integer langType)
			throws Exception {
		redisClientTemplate.del("category");
		String category = redisClientTemplate.get("category");
		if (StringUtil.isNotEmpty(category) && !"[]".equals(category)) {
			return JSONUtils.jsonToList(category);
		}
		category = upredisCpCpCategorys(siteId, langType);
		redisClientTemplate.set("category", category);
		return JSONUtils.jsonToList(category);
	}

	/**
	 * 修改分类
	 * 
	 * @param category
	 *            ,cgFile
	 * @throws Exception
	 */
	public void upCpCategory(CpCategory category, MultipartFile cgFile,HttpServletRequest request)
			throws Exception {
		String filePath = new String();
		if (!cgFile.isEmpty()) {
			String fileName = getFileName();
			// 上传
			filePath = FileUpload.fileUpName(cgFile, sysConfigService
					.getDbSysConfig(
							SysConfigConstant.DEFAULT_ADVERTISEMENT_PATH,
							category.getSiteId()), fileName);
//			String newfilepath = filePath.replace(
//					"D:/trsphoto/classification/",
//					ipAdd+"/classification/");
			String newfilepath = ImgFileUtils.getReplacePathByName(filePath,sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_ADVERTISEMENT_PATH,SessionUtils.getSiteId(request))
					, ipAdd, "/advertisement/", request);
			category.setImage(newfilepath);
		}
		categoryMapper.updateByPrimaryKeySelective(category);
		upredisCpCpCategorys(category.getSiteId(), category.getLangType());
	}

	/**
	 * 新增分类
	 * 
	 * @param category
	 *            ,cgFile
	 * @throws Exception
	 */
	public void inCpCategory(CpCategory category,MultipartFile cgFile,Integer langType,HttpServletRequest request)
			throws Exception {
		String filePath = new String();
		if (!cgFile.isEmpty()) {
			String fileName = getFileName();
			// 上传
			filePath = FileUpload.fileUpName(cgFile, sysConfigService
					.getDbSysConfig(
							SysConfigConstant.DEFAULT_ADVERTISEMENT_PATH,
							category.getSiteId()), fileName);
			String newfilepath = ImgFileUtils.getReplacePathByName(filePath,sysConfigService.getDbSysConfig(SysConfigConstant.DEFAULT_ADVERTISEMENT_PATH,SessionUtils.getSiteId(request))
					, ipAdd, "/advertisement/", request);
			category.setImage(newfilepath);
		}
		categoryMapper.insertSelective(category);
		upredisCpCpCategorys(category.getSiteId(), langType);
	}
	public void inCpCategoryWithNoPic(CpCategory category) {
		categoryMapper.insertSelective(category);
		upredisCpCpCategorys(category.getSiteId(), category.getLangType());
	}
	public void upCpCategoryWithOutPic(CpCategory category) {
		categoryMapper.updateByPrimaryKeySelective(category);
		upredisCpCpCategorys(category.getSiteId(), category.getLangType());
		
	}

	/**
	 * 删除分类
	 * 
	 * @param category
	 * 
	 */
	public void delCpCategory(CpCategory category) {
		if (category.getParentId()==0) {
			categoryMapper.deleaLL(category.getId());
		}else{
			categoryMapper.delcpcategorys(category.getId());
		}
		upredisCpCpCategorys(category.getSiteId(), category.getLangType());
	}

}
