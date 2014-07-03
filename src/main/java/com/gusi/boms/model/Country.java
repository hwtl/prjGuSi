package com.gusi.boms.model;

import java.io.Serializable;

/**
 * @Author: fdj
 * @Since: 2014-05-20 13:57
 * @Description: Country
 */
public class Country implements Serializable {
    private static final long serialVersionUID = -4139503460813046135L;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
