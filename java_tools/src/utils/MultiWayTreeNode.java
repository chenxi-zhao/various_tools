package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多叉树节点
 *
 */
public class MultiWayTreeNode<T> {
	/** 节点Id */
	private long nodeId;
	/** 父节点Id */
	private long parentId;
	/** 节点内容 */
	private T data;
	/** 子树集合 */
	private List<MultiWayTreeNode<T>> childList;
	
	public MultiWayTreeNode() {
		super();
	}
	public MultiWayTreeNode(long nodeID) {
		
		this.nodeId = nodeID;
		this.childList = new ArrayList<MultiWayTreeNode<T>>();
	}
	
	public MultiWayTreeNode(long nodeId, long parentId) {
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.childList = new ArrayList<MultiWayTreeNode<T>>();
	}

	public MultiWayTreeNode(long nodeId, long parentId, T data) {
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.data = data;
		this.childList = new ArrayList<MultiWayTreeNode<T>>();
	}

	public MultiWayTreeNode(T data) {
		this.data = data;
		this.childList = new ArrayList<MultiWayTreeNode<T>>();
	}

	public long getNodeId() {
		return nodeId;
	}


	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}


	public long getParentId() {
		return parentId;
	}


	public void setParentId(long parentId) {
		this.parentId = parentId;
	}


	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}


	public List<MultiWayTreeNode<T>> getChildList() {
		return childList;
	}


	public void setChildList(List<MultiWayTreeNode<T>> childList) {
		this.childList = childList;
	}
	@Override
	public String toString() {
		return data.toString();
	}
	
	

}
