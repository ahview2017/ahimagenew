package com.deepai.photo.service.enColumn;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepai.photo.bean.CpFriendshipLink;
import com.deepai.photo.mapper.CpFriendshipLinkMapper;

@Service
public class FriendshipLinkService {
	@Autowired
	private CpFriendshipLinkMapper cpFriendshipLinkMapper;

	public List<CpFriendshipLink> selectFriendshipLinkList(Integer langType) {
		return cpFriendshipLinkMapper.selectFriendshipLinkList(langType);

	}

	public void addFriendshipLink(String title, Integer orderId, String friendshipLink, Date createTime,
			String userName, Integer id, String remark, Integer langType, Integer state) {
		CpFriendshipLink cpFriendshipLink = new CpFriendshipLink();
		cpFriendshipLink.setState(state);
		cpFriendshipLink.setTitle(title);
		cpFriendshipLink.setLangType(langType);
		cpFriendshipLink.setCreateTime(createTime);
		cpFriendshipLink.setCreateUser(userName);
		cpFriendshipLink.setFriendshipLink(friendshipLink);
		cpFriendshipLink.setCreateUserId(id);
		cpFriendshipLink.setOrderId(orderId);
		cpFriendshipLink.setRemark(remark);
		cpFriendshipLinkMapper.addFriendshipLink(cpFriendshipLink);

	}

	public void deleteFriendshipLink(int id) {
		cpFriendshipLinkMapper.deleteFriendshipLink(id);
		
	}

	public void editFriendshipLink(Integer id, String title, Integer orderId, String friendshipLink, String remark, Integer state) {
		CpFriendshipLink cpFriendshipLink = new CpFriendshipLink();
		cpFriendshipLink.setTitle(title);
		cpFriendshipLink.setState(state);
		cpFriendshipLink.setFriendshipLink(friendshipLink);
		cpFriendshipLink.setId(id);
		cpFriendshipLink.setOrderId(orderId);
		cpFriendshipLink.setRemark(remark);
		cpFriendshipLinkMapper.editFriendshipLink(cpFriendshipLink);
		
	}

}
