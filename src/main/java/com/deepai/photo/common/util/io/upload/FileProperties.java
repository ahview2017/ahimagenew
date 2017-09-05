package com.deepai.photo.common.util.io.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("fileProperties")
public class FileProperties {
	@Value("#{filereader[video_uploadtemp]}")
	private String video_uploadtemp;
	@Value("#{filereader[video_upload]}")
	private String video_upload;
	@Value("#{filereader[video_maxsizethreshold]}")
	private String video_maxsizethreshold;
	@Value("#{filereader[video_maxsize]}")
	private String video_maxsize;
	@Value("#{filereader[photo_upload]}")
	private String photo_upload;
	@Value("#{filereader[photo_uploadtemp]}")
	private String photo_uploadtemp;
	@Value("#{filereader[photo_maxsizethreshold]}")
	private String photo_maxsizethreshold;
	@Value("#{filereader[photo_maxsize]}")
	private String photo_maxsize;
	@Value("#{filereader[ad_uploadtemp]}")
	private String ad_uploadtemp;
	@Value("#{filereader[ad_upload]}")
	private String ad_upload;
	@Value("#{filereader[ad_maxsizethreshold]}")
	private String ad_maxsizethreshold;
	@Value("#{filereader[ad_maxsize]}")
	private String ad_maxsize;
	@Value("#{filereader[app_uploadtemp]}")
	private String app_uploadtemp;
	@Value("#{filereader[app_upload]}")
	private String app_upload;
	@Value("#{filereader[app_maxsizethreshold]}")
	private String app_maxsizethreshold;
	@Value("#{filereader[app_maxsize]}")
	private String app_maxsize;

	public String getApp_uploadtemp() {
		return app_uploadtemp;
	}

	public void setApp_uploadtemp(String app_uploadtemp) {
		this.app_uploadtemp = app_uploadtemp;
	}

	public String getApp_upload() {
		return app_upload;
	}

	public void setApp_upload(String app_upload) {
		this.app_upload = app_upload;
	}

	public String getApp_maxsizethreshold() {
		return app_maxsizethreshold;
	}

	public void setApp_maxsizethreshold(String app_maxsizethreshold) {
		this.app_maxsizethreshold = app_maxsizethreshold;
	}

	public String getApp_maxsize() {
		return app_maxsize;
	}

	public void setApp_maxsize(String app_maxsize) {
		this.app_maxsize = app_maxsize;
	}

	public String getAd_uploadtemp() {
		return ad_uploadtemp;
	}

	public void setAd_uploadtemp(String ad_uploadtemp) {
		this.ad_uploadtemp = ad_uploadtemp;
	}

	public String getAd_upload() {
		return ad_upload;
	}

	public void setAd_upload(String ad_upload) {
		this.ad_upload = ad_upload;
	}

	public String getAd_maxsizethreshold() {
		return ad_maxsizethreshold;
	}

	public void setAd_maxsizethreshold(String ad_maxsizethreshold) {
		this.ad_maxsizethreshold = ad_maxsizethreshold;
	}

	public String getAd_maxsize() {
		return ad_maxsize;
	}

	public void setAd_maxsize(String ad_maxsize) {
		this.ad_maxsize = ad_maxsize;
	}

	public String getPhoto_upload() {
		return photo_upload;
	}

	public void setPhoto_upload(String photo_upload) {
		this.photo_upload = photo_upload;
	}

	public String getPhoto_uploadtemp() {
		return photo_uploadtemp;
	}

	public void setPhoto_uploadtemp(String photo_uploadtemp) {
		this.photo_uploadtemp = photo_uploadtemp;
	}

	public String getPhoto_maxsizethreshold() {
		return photo_maxsizethreshold;
	}

	public void setPhoto_maxsizethreshold(String photo_maxsizethreshold) {
		this.photo_maxsizethreshold = photo_maxsizethreshold;
	}

	public String getPhoto_maxsize() {
		return photo_maxsize;
	}

	public void setPhoto_maxsize(String photo_maxsize) {
		this.photo_maxsize = photo_maxsize;
	}

	public String getVideo_uploadtemp() {
		return video_uploadtemp;
	}

	public void setVideo_uploadtemp(String video_uploadtemp) {
		this.video_uploadtemp = video_uploadtemp;
	}

	public String getVideo_upload() {
		return video_upload;
	}

	public void setVideo_upload(String video_upload) {
		this.video_upload = video_upload;
	}

	public String getVideo_maxsizethreshold() {
		return video_maxsizethreshold;
	}

	public void setVideo_maxsizethreshold(String video_maxsizethreshold) {
		this.video_maxsizethreshold = video_maxsizethreshold;
	}

	public String getVideo_maxsize() {
		return video_maxsize;
	}

	public void setVideo_maxsize(String video_maxsize) {
		this.video_maxsize = video_maxsize;
	}
}
