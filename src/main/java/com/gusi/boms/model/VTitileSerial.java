package com.gusi.boms.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-06-09 16:36)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class VTitileSerial implements Serializable {
    private String  serialName;
    private String titleDegree;

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getTitleDegree() {
        return titleDegree;
    }

    public void setTitleDegree(String titleDegree) {
        this.titleDegree = titleDegree;
    }
}
