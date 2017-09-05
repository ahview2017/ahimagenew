package com.deepai.photo.common.constant;

import java.util.List;
import java.util.Map;

public class MyCacheConfig {

	public static boolean CACHE_DAO_ON; // 缓存是否开启

	public static int MAXELEMENTSINMEMORY; // 内存缓存最大数目

	public static int MAXELEMENTSONDISK; // 磁盘缓存最大数目

	public static boolean ETERNAL; // 永久缓存

	public static boolean OVERFLOWTODISK; // 溢出到磁盘

	public static int DISKSPOOLBUFFERSIZEMB; // 磁盘缓存大小

	public static int TIMETOIDLESECONDS; // 最大闲置时间

	public static int TIMETOLIVESECONDS; // 最大存活时间

	public static String MEMORYSTOREEVICTIONPOLICY; // 置换策略

	public static List<String> noCacheNames;// 不进行缓存的缓存名字

	public static Map<String, List<String>> collentionMaps;// 不进行缓存的缓存名字

	/**
	 * 预设缓存命名空间
	 */
	public static String AUTH_CACHE_NAMESPACE = "Auth_cache";
	public static String IMPORTSCHOOL_CACHE_NAMESPACE = "Importschool_cache";
	public static String IMPORTSCHOOLWORKER_CACHE_NAMESPACE = "Importschoolworker_cache";
	public static String IMPORTSTUDENT_CACHE_NAMESPACE = "Importstudent_cache";
	public static String IMPORTPARENT_CACHE_NAMESPACE = "Importparent_cache";
	public static String IMPORTTEACHER_CACHE_NAMESPACE = "Importteacher_cache";
	public static String IMPORTSCORE_CACHE_NAMESPACE = "Importscore_cache";

	public static Map<String, List<String>> getCollentionMaps() {
		return collentionMaps;
	}

	public static void setCollentionMaps(Map<String, List<String>> collentionMaps) {
		MyCacheConfig.collentionMaps = collentionMaps;
	}

	public static boolean isCACHE_DAO_ON() {
		return CACHE_DAO_ON;
	}

	public static void setCACHE_DAO_ON(boolean cACHE_DAO_ON) {
		CACHE_DAO_ON = cACHE_DAO_ON;
	}

	public static int getMAXELEMENTSINMEMORY() {
		return MAXELEMENTSINMEMORY;
	}

	public static void setMAXELEMENTSINMEMORY(int mAXELEMENTSINMEMORY) {
		MAXELEMENTSINMEMORY = mAXELEMENTSINMEMORY;
	}

	public static int getMAXELEMENTSONDISK() {
		return MAXELEMENTSONDISK;
	}

	public static void setMAXELEMENTSONDISK(int mAXELEMENTSONDISK) {
		MAXELEMENTSONDISK = mAXELEMENTSONDISK;
	}

	public static boolean isETERNAL() {
		return ETERNAL;
	}

	public static void setETERNAL(boolean eTERNAL) {
		ETERNAL = eTERNAL;
	}

	public static boolean isOVERFLOWTODISK() {
		return OVERFLOWTODISK;
	}

	public static void setOVERFLOWTODISK(boolean oVERFLOWTODISK) {
		OVERFLOWTODISK = oVERFLOWTODISK;
	}

	public static int getDISKSPOOLBUFFERSIZEMB() {
		return DISKSPOOLBUFFERSIZEMB;
	}

	public static void setDISKSPOOLBUFFERSIZEMB(int dISKSPOOLBUFFERSIZEMB) {
		DISKSPOOLBUFFERSIZEMB = dISKSPOOLBUFFERSIZEMB;
	}

	public static int getTIMETOIDLESECONDS() {
		return TIMETOIDLESECONDS;
	}

	public static void setTIMETOIDLESECONDS(int tIMETOIDLESECONDS) {
		TIMETOIDLESECONDS = tIMETOIDLESECONDS;
	}

	public static int getTIMETOLIVESECONDS() {
		return TIMETOLIVESECONDS;
	}

	public static void setTIMETOLIVESECONDS(int tIMETOLIVESECONDS) {
		TIMETOLIVESECONDS = tIMETOLIVESECONDS;
	}

	public static String getMEMORYSTOREEVICTIONPOLICY() {
		return MEMORYSTOREEVICTIONPOLICY;
	}

	public static void setMEMORYSTOREEVICTIONPOLICY(String mEMORYSTOREEVICTIONPOLICY) {
		MEMORYSTOREEVICTIONPOLICY = mEMORYSTOREEVICTIONPOLICY;
	}

	public List<String> getNoCacheNames() {
		return noCacheNames;
	}

	public void setNoCacheNames(List<String> noCacheNames) {
		this.noCacheNames = noCacheNames;
	}

}
