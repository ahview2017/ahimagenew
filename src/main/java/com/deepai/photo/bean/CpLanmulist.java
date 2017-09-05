package com.deepai.photo.bean;

import java.util.List;

/**
 * * @author huqiankai: * @date 创建时间：2017年1月21日 下午5:50:38 * @version 1.0
 * 
 */
public class CpLanmulist {
	private List<CpLanmu> cpLanmu;
  private  List<CpLanmuPicture> cpLanmuPictures;
  private List<CpNotice> noticelist;
  
	public List<CpNotice> getNoticelist() {
	return noticelist;
}

public void setNoticelist(List<CpNotice> noticelist) {
	this.noticelist = noticelist;
}

	public List<CpLanmu> getCpLanmu() {
		return cpLanmu;
	}

	public void setCpLanmu(List<CpLanmu> cpLanmu) {
		this.cpLanmu = cpLanmu;
	}

	public List<CpLanmuPicture> getCpLanmuPictures() {
		return cpLanmuPictures;
	}

	public void setCpLanmuPictures(List<CpLanmuPicture> cpLanmuPictures) {
		this.cpLanmuPictures = cpLanmuPictures;
	}

}
