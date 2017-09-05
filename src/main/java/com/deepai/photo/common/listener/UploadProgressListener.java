package com.deepai.photo.common.listener;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 文件进度监控
 * @author zhangzhizhi
 * @version 1.0
 */
public class UploadProgressListener implements ProgressListener{
	private int process;

	public void update(long pBytesRead, long pContentLength, int pItems) {
		process = (int)(pBytesRead * 100.0 / pContentLength);
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}
}
