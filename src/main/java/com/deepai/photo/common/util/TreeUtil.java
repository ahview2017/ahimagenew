package com.deepai.photo.common.util;

import java.util.ArrayList;
import java.util.List;

import com.deepai.photo.bean.CpCategory;

public class TreeUtil {
	private List<CpCategory> treeNodeList = new ArrayList<CpCategory>();

	public TreeUtil(List<CpCategory> list) {
		treeNodeList = list;
	}

	/**
	 * 
	 * @param nodeId
	 * @return
	 */
	public CpCategory getNodeById(int nodeId) {
		CpCategory treeNode = new CpCategory();
		for (CpCategory item : treeNodeList) {
			if (item.getId() == nodeId) {
				treeNode = item;
				break;
			}
		}
		return treeNode;
	}

	/**
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<CpCategory> getChildrenNodeById(int nodeId) {
		List<CpCategory> childrenTreeNode = new ArrayList<CpCategory>();
		for (CpCategory item : treeNodeList) {
			if (item.getParentId() == nodeId) {
				childrenTreeNode.add(item);
			}
		}
		return childrenTreeNode;
	}

	/**
	 * 递归生成Tree结构数据
	 * 
	 * @param rootId
	 * @return
	 */
	public CpCategory generateTreeNode(int rootId) {
		CpCategory root = this.getNodeById(rootId);
		List<CpCategory> childrenTreeNode = this.getChildrenNodeById(rootId);
		for (CpCategory item : childrenTreeNode) {
			CpCategory node = this.generateTreeNode(item.getId());
			root.getCategories().add(node);
		}
		return root;
	}
}
