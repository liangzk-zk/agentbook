package com.ab.app.agentbook.common.tree.bean;

/**
 * 用于向客户端传递树节点数据的容器
 * 相比于TreeNode 增加了子节点
 */
public class TreeNodeWithChild extends TreeNode {
    /**
     * 
     */
    private static final long serialVersionUID = -4429289533557732397L;
    private TreeNodeWithChild[] children;
    private String pId;
    private String name;
    private boolean isParent;
    private String permType;

	/**
	 * 子节点
	 * @return children
	 */
    public TreeNodeWithChild[] getChildren() {
        return children;
    }

    /**
     * 子节点
     * @param children
     */
    public void setChildren(TreeNodeWithChild[] children) {
        this.children = children;
    }
    
    /**
     * 获取父节点ID
     * @return  pId
     */
    public String getpId() {
        return pId;
    }
    /**
     * 设置父节点ID
     * @param pId
     */
    public void setpId(String pId) {
        this.pId = pId;
    }
    /**
     * 获取节点名称
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * 设置节点名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsParent() {
        return isParent;
    }
    
    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getPermType() {
        return permType;
    }

    public void setPermType(String permType) {
        this.permType = permType;
    }
}
