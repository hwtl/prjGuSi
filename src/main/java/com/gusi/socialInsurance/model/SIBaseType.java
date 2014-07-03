package com.gusi.socialInsurance.model;

/**
 * @Author: fdj
 * @Since: 2014-06-11 15:18
 * @Description: BaseTpye
 */
public class SIBaseType {

    /**
     * 上海
     */
    public static final int PAYMENT_LOCATION_ID_SH = 1;
    /**
     * 深圳
     */
    public static final int PAYMENT_LOCATION_ID_SZ = 2;
    /**
     * 宁波
     */
    public static final int PAYMENT_LOCATION_ID_NB = 3;

    /**
     * 新进
     */
    public static final int CHANGE_TYPE_ID_NEW = 0;
    /**
     * 退出
     */
    public static final int CHANGE_TYPE_ID_QUIT = 1;

    /**
     * 公司代缴
     * @since: 2014-06-13 13:01:11
     */
    public static final int PAYMENT_TYPE_ID_COMPANY = 0;

    /**
     * 员工自缴
     * @since: 2014-06-13 13:01:17
     */
    public static final int PAYMENT_TYPE_ID_SELF = 1;

    /**
     * 委托代缴
     * @since: 2014-06-13 13:01:17
     */
    public static final int PAYMENT_TYPE_ID_INTRUST = 2;


    private int id;
    private String name;
    private Integer status;

    /**
     * 构造方法不带参数
     * @since: 2014-06-12 09:33:42
     */
    public SIBaseType() {
    }

    /**
     * 构造方法带参数
     * @since: 2014-06-12 09:34:06
     * @param id
     * @param name
     */
    public SIBaseType(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SIBaseType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
