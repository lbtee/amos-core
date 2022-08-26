package org.amos.core.basic.node;

import java.util.List;

/**
 * @author admin
 */
public interface TreeNode<Id, E extends TreeNode<Id, E>> {
	/**
	 * 获取节点id
	 *
	 * @return 树节点id
	 */
	Id id();

	/**
	 * 获取该节点的父节点id
	 *
	 * @return 父节点id
	 */
	Id parentId();

	/**
	 * 是否是根节点
	 *
	 * @return true：根节点
	 */
	boolean root();

	/**
	 * 设置节点的子节点列表
	 *
	 * @param children 子节点
	 */
	void setChildren(List<E> children);

	/**
	 * 获取所有子节点
	 *
	 * @return 子节点列表
	 */
	List<E> getChildren();
}
