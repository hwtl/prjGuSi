package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-22 20:02)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class VEmployeeRewardPunishment extends EmployeeRewardPunishment {
    private static final long serialVersionUID = -7284287709711427216L;
	private String typeName;
    private String channelName;
    private String resultName;              //结果
    private String resultValue;
    private String rulesName;               //条例
    private String rulesTitle;

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getRulesTitle() {
        return rulesTitle;
    }

    public void setRulesTitle(String rulesTitle) {
        this.rulesTitle = rulesTitle;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getRulesName() {
        return rulesName;
    }

    public void setRulesName(String rulesName) {
        this.rulesName = rulesName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "VEmployeeRewardPunishment{" +
                "channelName='" + channelName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", resultName='" + resultName + '\'' +
                ", resultValue='" + resultValue + '\'' +
                ", rulesName='" + rulesName + '\'' +
                ", rulesTitle='" + rulesTitle + '\'' +
                '}';
    }
}
