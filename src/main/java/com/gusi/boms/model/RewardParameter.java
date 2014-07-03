package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-23 10:32)
 * @Description: 员工奖惩数据查询
 * To change this template use File | Settings | File Templates.
 */
public class RewardParameter extends Parameter {
    private static final long serialVersionUID = -1680884322830912658L;
	public static final String REWARD_TYPE = "1020";                //奖惩类型
    public static final String REWARD_CHANNEL = "1021";             //奖惩渠道
    public static final String REWARD_RESULT_REWARD = "1022";       //奖惩结果-奖
    public static final String REWARD_RESULT_PUNISHMENT = "1023";   //奖惩结果-惩
    /**
     * //惩罚对应的optionCode
     */
    public static final String REWARD_TYPE_PUNISH_OPTIONCODE="102002";
     //新增的类型
    /**
     * since 2.7.1
     * 奖惩渠道
     */
    public static final String CHANNER_TYPE_KEY="1025";
    /**
     * since 2.7.1 奖惩结果类型
     */
    public static final String RESULT_TYPE_KEY="1026";
    /**
     * since 2.7.1 奖惩规则
     */
    public static final String RULE_TYPE_KEY="1027";
    
    private String optionTitle;
    private String optionResult;
    private String parentCode;
    
    public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getOptionResult() {
        return optionResult;
    }

    public void setOptionResult(String optionResult) {
        this.optionResult = optionResult;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    @Override
    public String toString() {
        return "RewardParameter{" +
                "optionResult='" + optionResult + '\'' +
                ", optionTitle='" + optionTitle + '\'' +
                '}';
    }
}
