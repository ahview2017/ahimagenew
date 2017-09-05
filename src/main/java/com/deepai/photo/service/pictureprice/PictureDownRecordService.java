package com.deepai.photo.service.pictureprice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepai.photo.mapper.CpPictureDownloadrecordMapper;

/**
 * @author zhangShuo
 * 
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class PictureDownRecordService {
	@Autowired
	private CpPictureDownloadrecordMapper downloadrecordMapper;


}
