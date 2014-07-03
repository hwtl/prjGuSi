package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-15 11:25)
 * Description:职系实体
 */
public class TitleSerial extends BaseEntity {
    private static final long serialVersionUID = 9046415081203365316L;
    private int id;                 //主键Id

    private String serialName;      //职系名称

    private Date createTime;        //创建时间

    private int creator;        //创建人

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "TitleSerial{" +
                "id=" + id +
                ", serialName='" + serialName + '\'' +
                ", createTime=" + createTime +
                ", creator=" + creator +
                '}';
    }
}