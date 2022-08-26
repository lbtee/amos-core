package org.amos.core.basic.node;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder<T extends TreeNode2> {

    private final static Long ROOT_NODE_VALUE = 0L;

    private List<T> nodeList = new ArrayList<>();

    public TreeNodeBuilder(List<T> nodeList) {
        this.nodeList = nodeList;
    }

    /**
     * 构建树形结构
     * @return
     */
    public List<T> build() {
        List<T> treeNodes = new ArrayList<>();
        for (T node : getRootNode()) {
            node = buildChildrenNode(node);
            treeNodes.add(node);
        }
        return treeNodes;
    }

    /**
     * 构建树形结构(动态构建)
     * @return
     */
    public List<T> buildDynamic() {
        List<T> treeNodes = new ArrayList<>();
        for (T node : getDynamicRootNode()) {
            node = buildChildrenNode(node);
            treeNodes.add(node);
        }
        return treeNodes;
    }

    /**
     * 递归构建子树结构
     * @param pNode
     * @return
     */
    private T buildChildrenNode(T pNode) {
        List<T> childrenNodes = new ArrayList<>();
        for (T node : nodeList) {
            if (node.getPid().equals(pNode.getId())) {
                childrenNodes.add(buildChildrenNode(node));
            }
        }
        pNode.setChildren(childrenNodes);
        return pNode;
    }

    /**
     * 获取根节点数据
     * @return
     */
    private List<T> getRootNode() {
        List<T> rootNodes = new ArrayList<>();
        for (T node : nodeList) {
            if (node.getPid().equals(ROOT_NODE_VALUE)) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    /**
     * 动态获取根节点数据
     * @return
     */
    private List<T> getDynamicRootNode() {
        List<T> rootNodes = new ArrayList<>();
        for (T node : nodeList) {
            boolean isHasParent = false;
            for (T in : nodeList) {
                if ((in.getId()).equals(node.getPid())) {
                    isHasParent = true;
                    break;
                }
            }
            if (!isHasParent){
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }
}
