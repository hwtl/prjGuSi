package com.gusi.boms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-11 下午4:22
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class LevelTree implements Serializable {

    private static final long serialVersionUID = -8931321347935551365L;
    private String id;
    private String text;
    private List<LevelTree> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LevelTree> getChildren() {
        return children;
    }

    public void setChildren(List<LevelTree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "LevelTree{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", children=" + children +
                '}';
    }
}
