package com.ab.app.agentbook.data.crud.criteria;

import java.io.Serializable;

/**
 * 查询的排序设置
 * 
 *
 */
public class OrderBy implements Serializable, Cloneable {
    private static final long serialVersionUID = -4909244435880140044L;
    private String name;
    private OrderDirection direction;
    private boolean enabled = false;
    private Integer sequence = 0;
    private boolean caseSensitive = true;
    private boolean alias;

    public boolean isAlias() {
        return alias;
    }

    public void setAlias(boolean alias) {
        this.alias = alias;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public OrderBy() {
    }

    /**
     * 构造针对指定字段的排序对象
     * 
     * @param aName      字段名字
     * @param aDirection 排序方向
     */
    public OrderBy(final String aName, final OrderDirection aDirection) {
        this.name = aName;
        // 原作者为何将_替换为.
//        if (this.name.contains( "_" )) {
//            this.name = name.replace( '_', '.');
//        }
        this.direction = aDirection;
    }

    /**
     * 返回排序设置数组
     * 
     * @param orderBy 构成数组的排序对象
     * @return
     */
    public static OrderBy[] orderBy(OrderBy... orderBy) {
        return orderBy;
    }

    /**
     * 返回针对指定字段的升序排序对象
     * 
     * @param name 排序字段
     * @return
     */
    public static OrderBy asc(String name) {
        return new OrderBy(name, OrderDirection.ASC);
    }

    /**
     * 返回针对指定字段的降序排序对象
     * 
     * @param name 排序字段
     * @return
     */
    public static OrderBy desc(String name) {
        return new OrderBy(name, OrderDirection.DESC);
    }

    /**
     * 返回排序方向
     * 
     * @return
     */
    public OrderDirection getDirection() {
        return direction;
    }

    /**
     * 设置排序方向
     * 
     * @param direction
     */
    public void setDirection(OrderDirection direction) {
        this.direction = direction;
    }

    /**
     * 返回排序字段名字
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 设置排序字段名字
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 返回排序方式是否是降序
     * 
     * @return
     */
    public boolean isDesc() {
        return direction == OrderDirection.DESC;
    }

    /**
     * 设置是否以降序方式进行排序
     * 
     * @param desc
     */
    public void setDesc(boolean desc) {
        if (desc) {
            direction = OrderDirection.DESC;
        } else {
            direction = OrderDirection.ASC;
        }
    }

    /**
     * 返回排序方式是否是升序
     * 
     * @return
     */
    public boolean isAsc() {
        return direction == OrderDirection.ASC;
    }

    /**
     * 设置是否以升序方式进行排序
     * 
     * @param asc
     */
    public void setAsc(boolean asc) {
        if (asc) {
            direction = OrderDirection.ASC;
        } else {
            direction = OrderDirection.DESC;
        }
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    /**
     * 将排序方式设置为升序
     */
    public void asc() {
        direction = OrderDirection.ASC;
    }

    /**
     * 将排序方式设置为降序
     */
    public void desc() {
        direction = OrderDirection.DESC;
    }

    public void toggle() {
        this.enabled = true;
        this.setAsc(!isAsc());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direction == null) ? 0 : direction.name().hashCode());
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderBy other = (OrderBy) obj;
        if (direction == null) {
            if (other.direction != null) {
                return false;
            }
        } else if (!direction.equals(other.direction)) {
            return false;
        }
        if (enabled != other.enabled) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (sequence == null) {
            if (other.sequence != null) {
                return false;
            }
        } else if (!sequence.equals(other.sequence)) {
            return false;
        }
        return true;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

}
