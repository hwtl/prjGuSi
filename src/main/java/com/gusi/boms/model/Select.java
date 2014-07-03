package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-28 19:38)
 * Description: To change this template use File | Settings | File Templates.
 */
public class Select extends BaseEntity {

    private static final long serialVersionUID = 4954907180484607360L;
    private String key;
    private String value;
    private String expand;

    public Select() {
    }

    public Select(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }
}
