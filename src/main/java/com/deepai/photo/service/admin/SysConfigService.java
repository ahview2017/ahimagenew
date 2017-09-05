package com.deepai.photo.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.bean.CpSystemConfig;
import com.deepai.photo.bean.CpSystemConfigExample;
import com.deepai.photo.bean.CpSystemConfigExample.Criteria;
import com.deepai.photo.common.StringUtil;
import com.deepai.photo.common.constant.CommonConstant;
import com.deepai.photo.common.constant.SysConfigConstant;
import com.deepai.photo.common.listener.SpringContextUtil;
import com.deepai.photo.common.redis.RedisClientTemplate;
import com.deepai.photo.common.util.SessionUtils;
import com.deepai.photo.mapper.CpBasicMapper;
import com.deepai.photo.mapper.CpSystemConfigMapper;


/**
 * @author guoyanhong
 * 系统配置
 *
 */
@Service
@Transactional(readOnly=false,rollbackFor=Exception.class)
public class SysConfigService {
	
	private final String hyphen="-";
	
	@Autowired
	private CpSystemConfigMapper systemCofigMapper;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private CpBasicMapper basicMapper;
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 获取系统默认配置值
	 * @param code
	 */
	public String getDbSysConfig(String code,int siteId)throws Exception{
		Integer langType=SessionUtils.geLangType(request);
		//获取redis配置
		String value=redisClientTemplate.get(code+hyphen+siteId+hyphen+langType);
		/*//获取数据库配置值
		if(StringUtil.isBlank(value)){
			CpSystemConfigExample example=new CpSystemConfigExample();
			example.createCriteria().andSiteIdEqualTo(siteId).andConfigCodeEqualTo(code).andDeleteFlagEqualTo(CommonConstant.BYTE0);
			List<CpSystemConfig> list=systemCofigMapper.selectByExample(example);
			if(list!=null&&list.size()>0){
				value=list.get(0).getConfigValue();
			}
		}*/
		//获取内存配置值
		if(StringUtil.isBlank(value)){
			value=SysConfigConstant.config.get(code);
		}
		return value;
	}
	
	/**
	 * 根据图片名得到图片在服务器上的存放路径
	 * @param filename
	 * @param site
	 * @return
	 */
	public String getWaterMarkPicturePathOnServer(String filename,int siteId)throws Exception{
		StringBuffer sb=new StringBuffer();
		if(StringUtil.isNotBlank(filename)){
			sb.append(getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PATH, siteId));
		}else{
			sb.append(getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PATH, siteId));
			sb.append(getDbSysConfig(SysConfigConstant.DEFAULT_WATERMARK_PIC, siteId));
		}
//		FileUpload.makeDirectory(sb.toString());
		return sb.toString();
	}
	
	/**
	 * 新增修改时，同步至redis
	 * @param code
	 * @param siteId
	 * @param value
	 */
	public void setToRedis(String code,int siteId,String value)throws Exception{
		  int langtype = SessionUtils.geLangType(request);
		String key=code+hyphen+siteId+hyphen+langtype;
		redisClientTemplate.set(key, value);
	}
	/**
	 * 删除配置并同步删除redis
	 * @param code
	 * @param siteId
	 * @param value
	 * @throws Exception 
	 */
	public void delConfigsByIds(String configIds,int siteId) throws Exception{
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("ids", configIds.split(","));
		basicMapper.delConfigsByIds(param);
		String ids[]=configIds.split(",");
		String key=null;
		for (int i = 0; i < ids.length; i++) {
			CpSystemConfig config=systemCofigMapper.selectByPrimaryKey(Integer.valueOf(ids[i]));
			if(config!=null){
				key=config.getConfigCode()+hyphen+siteId;
				redisClientTemplate.del(key);
			}
		}
	}
	
	/**
	 * 从数据库获取所有配置项
	 */
	public void setDbConfigToRedis()throws Exception{
		//从spring容器中获取bean对象
		CpSystemConfigMapper systemCofigMapper=(CpSystemConfigMapper)SpringContextUtil.getBean("cpSystemConfigMapper");
		
		CpSystemConfigExample example=new CpSystemConfigExample();
		example.createCriteria().andDeleteFlagEqualTo(CommonConstant.BYTE0);
		List<CpSystemConfig> list=systemCofigMapper.selectByExample(example);
		StringBuffer key=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			CpSystemConfig config=list.get(i);
			key.setLength(0);
			key.append(config.getConfigCode()).append(hyphen).append(config.getSiteId()).append(hyphen).append(config.getLangType());
			redisClientTemplate.set(key.toString(), config.getConfigValue());
		}
	}

	public List<String> findEmail(String emailadd, String email_password) {
		List<String>list=new ArrayList<String>();
		CpSystemConfigExample example = new  CpSystemConfigExample();
		Criteria criteria = example.createCriteria();
		criteria.andConfigCodeEqualTo(emailadd);
		list.add(systemCofigMapper.selectByExample(example).get(0).getConfigValue());
		example.clear();
		Criteria criteria1 = example.createCriteria();
		criteria1.andConfigCodeEqualTo(email_password );
		List<CpSystemConfig> selectByExample = systemCofigMapper.selectByExample(example);
		if (selectByExample!=null&&selectByExample.size()>0) {
			list.add(selectByExample.get(0).getConfigValue());
		}
		return list;
	}
	public String testpath(String filename)throws Exception{
		StringBuffer sb=new StringBuffer();
		CpSystemConfigExample example = new  CpSystemConfigExample();
		Criteria criteria = example.createCriteria();
		criteria.andConfigCodeEqualTo(filename);
		sb.append(systemCofigMapper.selectByExample(example).get(0).getConfigValue());
		return sb.toString();
	}
}
