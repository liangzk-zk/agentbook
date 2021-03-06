package com.ab.app.agentbook.common.tree.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于向客户端传递树节点数据的容器
 */
public class TreeNode implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7556111218207968669L;
    private String id;
    private String text;
    private boolean leaf;
    private boolean expandable;
    private String nodeStatus;
    private Map<String, String> props = new HashMap<String, String>();

    /**
     * 树节点id
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 树节点id
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 树节点文本
     * 
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 树节点文本
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 是否是叶子节点
     * 
     * @return
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * 是否是叶子节点
     * 
     * @param leaf
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * 获取节点状态
     * 
     * @return
     */
    public String getNodeStatus() {
        return nodeStatus;
    }

    /**
     * 设置节点状态
     * 
     * @param nodeStatus
     */
    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> attributes) {
        this.props = attributes;
    }

    public void setAttribute(String name, String value) {
        this.props.put(name, value);
    }
}
