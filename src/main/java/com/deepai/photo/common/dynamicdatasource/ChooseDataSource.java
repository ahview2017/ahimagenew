package com.deepai.photo.common.dynamicdatasource;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据库动态配置，write主库，read随机选择从库
 * 
 * @author lizongjie
 *
 */
public class ChooseDataSource extends AbstractRoutingDataSource {
	private final static Logger log = Logger.getLogger(ChooseDataSource.class);
	private int readDataSourceNum;

	@Override
	protected Object determineCurrentLookupKey() {
		String lookupKey = HandleDataSource.getDataSource();
		// 如果是读操作，随机从配置中选择从库
		if ("read".equals(lookupKey)) {
			lookupKey = lookupKey + (new Random().nextInt(readDataSourceNum) + 1);
			log.debug("读写分离，切换到 " + lookupKey);
		} else {
			lookupKey = "write";
		}
		return lookupKey;
	}

	public void setReadDataSourceNum(int readDataSourceNum) {
		this.readDataSourceNum = readDataSourceNum;
	}

}
