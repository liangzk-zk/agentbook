package com.ab.app.agentbook.data.crud.join;

public class EntityJoin extends Join {
    public EntityJoin() {
    }

    private String name;
    private String alias;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public EntityJoin(String name, String alias) {
        super();
        this.name = name;
        this.alias = alias;
    }

    public String toString() {
        return String.format("%s name=%s alias=%s", getClass().getSimpleName(), getName(), getAlias());
    }
}
