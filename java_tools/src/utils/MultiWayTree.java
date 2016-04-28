package utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 多叉树生成、遍历工具
 */
public class MultiWayTree<T> {
	/** 树根 */
	private MultiWayTreeNode<T> root;

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public MultiWayTree() {
		root = new MultiWayTreeNode<T>(0);
		root.setData((T) new Object());
	}

	@SuppressWarnings("unchecked")
	public MultiWayTree(long rootId) {
		root = new MultiWayTreeNode<T>(rootId);
		root.setData((T) new Object());
	}

	/**
	 * 创建一颗多叉树
	 * 
	 * @param rootId
	 *            多叉树根节点编号
	 * @param rootName
	 *            多叉树根节点名称
	 * @param treeNodes
	 *            用于构造多叉树的节点
	 * @return 多叉树
	 */
	public void createTree(List<MultiWayTreeNode<T>> treeNodes) {
		if (treeNodes == null || treeNodes.isEmpty())
			return;

		// MultiWayTree<T> multiWayTree = new MultiWayTree<T>();
		long rootId = this.getRoot().getNodeId();

		// 将所有节点添加到多叉树中
		for (MultiWayTreeNode<T> treeNode : treeNodes) {
			//如果被添加的子节点是根节点的子节点
			if (treeNode.getParentId() == rootId) {
				// 则加入到根的子节点列表childList中去
				this.getRoot().getChildList().add(treeNode);
			} else {
				addChild(this.getRoot(), treeNode);
			}
		}

		return;
	}

	/**
	 * 向指定多叉树节点添加子节点
	 * 
	 * @param ifParentNode
	 *            多叉树节点
	 * @param childNode
	 *            节点
	 */
	public void addChild(MultiWayTreeNode<T> ifParentNode, MultiWayTreeNode<T> childNode) {
		for (MultiWayTreeNode<T> item : ifParentNode.getChildList()) {
			if (item.getNodeId() == childNode.getParentId()) {
				//找到对应的父亲
				item.getChildList().add(childNode);
			} else {
				if (item.getChildList() != null && item.getChildList().size() > 0) {
					addChild(item, childNode);
				}
			}
		}
	}

	public MultiWayTreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(MultiWayTreeNode<T> root) {
		this.root = root;
	}

	/**
	 * 非递归深度优先先序遍历多叉树
	 * 
	 * @param root
	 *      多叉树根节点
	 * @return
	 */
	public String iteratorTreeWithDFS(MultiWayTreeNode<T> root) {
		if (root == null || root.getChildList() == null) {
			return null;
		}

		Stack<MultiWayTreeNode<T>> visited = new Stack<>();

		visited.push(root);

		while (!visited.isEmpty()) {
			MultiWayTreeNode<T> access = visited.pop();

			System.out.println(access.toString());

			if (access.getChildList() != null && access.getChildList().size() > 0) {
				List<MultiWayTreeNode<T>> childList = access.getChildList();
				for (int i = childList.size() - 1; i >= 0; i--) {
					visited.push(childList.get(i));
				}
			}

		}
		return "".toString();
	}
	
	/**
	 * 非递归先序遍历多叉树
	 * 
	 * @param root
	 *      多叉树根节点
	 * @return
	 */
	public String iteratorTreeWithBFS(MultiWayTreeNode<T> root) {
		if (root == null || root.getChildList() == null) {
			return null;
		}

		Queue<MultiWayTreeNode<T>> visited = new LinkedList<>();

		visited.offer(root);

		while (!visited.isEmpty()) {
			MultiWayTreeNode<T> access = visited.poll();

			System.out.println(access.toString());

			if (access.getChildList() != null && access.getChildList().size() > 0) {
				List<MultiWayTreeNode<T>> childList = access.getChildList();
				for (int i = childList.size() - 1; i >= 0; i--) {
					visited.offer(childList.get(i));
				}
			}

		}
		return "".toString();
	}
}
