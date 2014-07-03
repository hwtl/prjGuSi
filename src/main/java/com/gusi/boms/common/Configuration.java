package com.gusi.boms.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.plus.util.GlobalConfigUtil;

/**
 * @ClassName Configuration
 * @Description: 环境相关配置类
 * @author Jail    E -Mail:86455@ dooioo.com
 * @date 2012-12-5 下午1:12:32
 */
public class Configuration {


	private static Configuration config;

	private String env;
	private String appCode;
	private String logoutUrl;
	private String version;
    private int pageSize;
    private String dooiooConnection;
    private String dooiooSshUser;
    private String dooiooSshPwd;
    private int intervalTime;
    private int superAdminUserCode;
    private int picWidth;
    private int picHight;
    private String iderongConnection;
    private String iderongSshUser;
    private String iderongSshPwd;
    private int registerOutDay;
    private String birthdaySelfMsg;
    private String birthdayOtherMsg;
    private String oldEmpSelfMsg;
    private String oldEmpOtherMsg;
    private String olderEmpSelfMsg;
    private String olderEmpOtherMsg;
    private String userPicturePath;

    private String mailHostName;
    private String mailFrom;
    private String mailFromPwd;
    private String mailReceiveUsers;

    private int maxCount;

    private Integer pwdOutOfDate;
    private Integer pwdNoticeDate;

    private String noticeUrl;
    
    private String omsApiReadToken;
    private String omsApiWriteToken;
    
    private int zhongJieBu;
    private int zuLingBu;
    private int xinFangXiaoShouBu;
    private int daiLiXiangMuBu;
    private int daXiQu;
    private int daDongQu;
    private int daNanQu;
    private int daBeiQu;

    private int yingXiaoDaiLiBu;
    private String fyTransferApi;
    private String fyTransferUrl;
    private String kyTransferApi;
    private String kyTransferUrl;

    private String hrmsAppCode;
    
    private long currentVersion;
    private String bomsDesKey;
    private String webMsgUrl;
    private String sendWebMsgType;
    private String sendWebMsgSource;

    private String smsApiUrl;
    private String zpxxReceivers;

    private double branchEmpNum;
    private double empNumWarning1;
    private double empNumWarning2;
    private String empNumWarning1Users;
    private String empNumWarning2Users;

    private String attachPath;
    private String attachPreviewUrl;
    
    private int siPageSize;
    
    public String getAttachPath() {
		return attachPath;
	}

	public String getAttachPreviewUrl(Company comp) {
		if (this.attachPreviewUrl !=null && comp !=null) {
			return attachPreviewUrl.replace(Company.Dooioo.toENLower(),comp.toENLower());
		}
		return attachPreviewUrl;
	}

	public boolean isDevelopment(){
        return GlobalConfigUtil.isDevelopmentEnv();
    }

    public boolean isProduction(){
        return GlobalConfigUtil.isProductionEnv();
    }

    public String getEnvironmental(){
        if(isDevelopment()){
              return "测试版";
        }
        if(isProduction()){
           return "正式版";
        }
        return "";
    }

	public String getBomsDesKey() {
		return bomsDesKey;
	}

	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getEnv() {
		return this.env;
	}
	public String getLogoutUrl() {
		return logoutUrl;
	}
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getVersion() {
			return version;
	}
	public void setVersion(String version) {
			this.version = version;
	}

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getDooiooConnection() {
        return dooiooConnection;
    }

    public void setDooiooConnection(String dooiooConnection) {
        this.dooiooConnection = dooiooConnection;
    }

    public String getDooiooSshUser() {
        return dooiooSshUser;
    }

    public void setDooiooSshUser(String dooiooSshUser) {
        this.dooiooSshUser = dooiooSshUser;
    }

    public String getDooiooSshPwd() {
        return dooiooSshPwd;
    }

    public void setDooiooSshPwd(String dooiooSshPwd) {
        this.dooiooSshPwd = dooiooSshPwd;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getSuperAdminUserCode() {
			return superAdminUserCode;
	}

	public void setSuperAdminUserCode(int superAdminUserCode) {
			this.superAdminUserCode = superAdminUserCode;
	}

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHight() {
        return picHight;
    }

    public void setPicHight(int picHight) {
        this.picHight = picHight;
    }

    public String getIderongConnection() {
        return iderongConnection;
    }

    public void setIderongConnection(String iderongConnection) {
        this.iderongConnection = iderongConnection;
    }

    public String getIderongSshUser() {
        return iderongSshUser;
    }

    public void setIderongSshUser(String iderongSshUser) {
        this.iderongSshUser = iderongSshUser;
    }

    public String getIderongSshPwd() {
        return iderongSshPwd;
    }

    public void setIderongSshPwd(String iderongSshPwd) {
        this.iderongSshPwd = iderongSshPwd;
    }

    public int getRegisterOutDay() {
        return registerOutDay;
    }

    public void setRegisterOutDay(int registerOutDay) {
        this.registerOutDay = registerOutDay;
    }

    public String getBirthdaySelfMsg() {
        return birthdaySelfMsg;
    }

    public void setBirthdaySelfMsg(String birthdaySelfMsg) {
        this.birthdaySelfMsg = birthdaySelfMsg;
    }

    public String getBirthdayOtherMsg() {
        return birthdayOtherMsg;
    }

    public void setBirthdayOtherMsg(String birthdayOtherMsg) {
        this.birthdayOtherMsg = birthdayOtherMsg;
    }

    public String getOldEmpSelfMsg() {
        return oldEmpSelfMsg;
    }

    public void setOldEmpSelfMsg(String oldEmpSelfMsg) {
        this.oldEmpSelfMsg = oldEmpSelfMsg;
    }

    public String getOldEmpOtherMsg() {
        return oldEmpOtherMsg;
    }

    public void setOldEmpOtherMsg(String oldEmpOtherMsg) {
        this.oldEmpOtherMsg = oldEmpOtherMsg;
    }

    public String getOlderEmpSelfMsg() {
        return olderEmpSelfMsg;
    }

    public void setOlderEmpSelfMsg(String olderEmpSelfMsg) {
        this.olderEmpSelfMsg = olderEmpSelfMsg;
    }

    public String getOlderEmpOtherMsg() {
        return olderEmpOtherMsg;
    }

    public void setOlderEmpOtherMsg(String olderEmpOtherMsg) {
        this.olderEmpOtherMsg = olderEmpOtherMsg;
    }

    public String getUserPicturePath() {
        return userPicturePath;
    }

    public void setUserPicturePath(String userPicturePath) {
        this.userPicturePath = userPicturePath;
    }

    public String getMailHostName() {
        return mailHostName;
    }

    public void setMailHostName(String mailHostName) {
        this.mailHostName = mailHostName;
    }

    public String getOmsApiReadToken() {
		return omsApiReadToken;
	}

	public void setOmsApiReadToken(String omsApiReadToken) {
		this.omsApiReadToken = omsApiReadToken;
	}

	public String getOmsApiWriteToken() {
		return omsApiWriteToken;
	}

	public void setOmsApiWriteToken(String omsApiWriteToken) {
		this.omsApiWriteToken = omsApiWriteToken;
	}

	public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailFromPwd() {
        return mailFromPwd;
    }

    public void setMailFromPwd(String mailFromPwd) {
        this.mailFromPwd = mailFromPwd;
    }

    public String getMailReceiveUsers() {
        return mailReceiveUsers;
    }

    public void setMailReceiveUsers(String mailReceiveUsers) {
        this.mailReceiveUsers = mailReceiveUsers;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getPwdOutOfDate() {
        return pwdOutOfDate;
    }

    public void setPwdOutOfDate(Integer pwdOutOfDate) {
        this.pwdOutOfDate = pwdOutOfDate;
    }

    public Integer getPwdNoticeDate() {
        return pwdNoticeDate;
    }

    public void setPwdNoticeDate(Integer pwdNoticeDate) {
        this.pwdNoticeDate = pwdNoticeDate;
    }

    public int getZhongJieBu() {
        return zhongJieBu;
    }

    public void setZhongJieBu(int zhongJieBu) {
        this.zhongJieBu = zhongJieBu;
    }

    public int getZuLingBu() {
        return zuLingBu;
    }

    public void setZuLingBu(int zuLingBu) {
        this.zuLingBu = zuLingBu;
    }

    public int getXinFangXiaoShouBu() {
        return xinFangXiaoShouBu;
    }

    public void setXinFangXiaoShouBu(int xinFangXiaoShouBu) {
        this.xinFangXiaoShouBu = xinFangXiaoShouBu;
    }

    public int getDaiLiXiangMuBu() {
        return daiLiXiangMuBu;
    }

    public void setDaiLiXiangMuBu(int daiLiXiangMuBu) {
        this.daiLiXiangMuBu = daiLiXiangMuBu;
    }

    public int getDaXiQu() {
        return daXiQu;
    }

    public void setDaXiQu(int daXiQu) {
        this.daXiQu = daXiQu;
    }

    public int getDaDongQu() {
        return daDongQu;
    }

    public void setDaDongQu(int daDongQu) {
        this.daDongQu = daDongQu;
    }

    public int getYingXiaoDaiLiBu() {
        return yingXiaoDaiLiBu;
    }

    public void setYingXiaoDaiLiBu(int yingXiaoDaiLiBu) {
        this.yingXiaoDaiLiBu = yingXiaoDaiLiBu;
    }

    public String getFyTransferApi() {
        return fyTransferApi;
    }

    public void setFyTransferApi(String fyTransferApi) {
        this.fyTransferApi = fyTransferApi;
    }

    public String getFyTransferUrl() {
        return fyTransferUrl;
    }

    public void setFyTransferUrl(String fyTransferUrl) {
        this.fyTransferUrl = fyTransferUrl;
    }

    public String getKyTransferApi() {
        return kyTransferApi;
    }

    public void setKyTransferApi(String kyTransferApi) {
        this.kyTransferApi = kyTransferApi;
    }

    public String getKyTransferUrl() {
        return kyTransferUrl;
    }

    public void setKyTransferUrl(String kyTransferUrl) {
        this.kyTransferUrl = kyTransferUrl;
    }

    public String getHrmsAppCode() {
        return hrmsAppCode;
    }

    public void setHrmsAppCode(String hrmsAppCode) {
        this.hrmsAppCode = hrmsAppCode;
    }

    public String getWebMsgUrl() {
        return webMsgUrl;
    }

    public void setWebMsgUrl(String webMsgUrl) {
        this.webMsgUrl = webMsgUrl;
    }

    public String getSmsApiUrl() {
        return smsApiUrl;
    }

    public void setSmsApiUrl(String smsApiUrl) {
        this.smsApiUrl = smsApiUrl;
    }

    public String getZpxxReceivers() {
        return zpxxReceivers;
    }

    public void setZpxxReceivers(String zpxxReceivers) {
        this.zpxxReceivers = zpxxReceivers;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getSendWebMsgType() {
        return sendWebMsgType;
    }

    public void setSendWebMsgType(String sendWebMsgType) {
        this.sendWebMsgType = sendWebMsgType;
    }

    public String getSendWebMsgSource() {
        return sendWebMsgSource;
    }

    public void setSendWebMsgSource(String sendWebMsgSource) {
        this.sendWebMsgSource = sendWebMsgSource;
    }

    public double getBranchEmpNum() {
        return branchEmpNum;
    }

    public void setBranchEmpNum(double branchEmpNum) {
        this.branchEmpNum = branchEmpNum;
    }

    public double getEmpNumWarning1() {
        return empNumWarning1;
    }

    public void setEmpNumWarning1(double empNumWarning1) {
        this.empNumWarning1 = empNumWarning1;
    }

    public double getEmpNumWarning2() {
        return empNumWarning2;
    }

    public void setEmpNumWarning2(double empNumWarning2) {
        this.empNumWarning2 = empNumWarning2;
    }

    public String getEmpNumWarning1Users() {
        return empNumWarning1Users;
    }

    public void setEmpNumWarning1Users(String empNumWarning1Users) {
        this.empNumWarning1Users = empNumWarning1Users;
    }

    public String getEmpNumWarning2Users() {
        return empNumWarning2Users;
    }

    public void setEmpNumWarning2Users(String empNumWarning2Users) {
        this.empNumWarning2Users = empNumWarning2Users;
    }

    public int getDaNanQu() {
        return daNanQu;
    }

    public void setDaNanQu(int daNanQu) {
        this.daNanQu = daNanQu;
    }

    public int getDaBeiQu() {
        return daBeiQu;
    }

    public void setDaBeiQu(int daBeiQu) {
        this.daBeiQu = daBeiQu;
    }

    public int getSiPageSize() {
        return siPageSize;
    }

    /**
      * @since: 3.0.1 
      * @return
      * <p>
      *  tomcat启动时间
      * </p>   
     */
    public long getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(long currentVersion) {
		this.currentVersion = currentVersion;
	}

	public synchronized static Configuration getInstance(){
		if(config==null){
			config = new Configuration();
			config.init();
		}
		return config;
	}


	private void init(){
        PropertiesConfiguration prop = new PropertiesConfiguration();
        prop.setEncoding("utf-8");
        try {
            this.setEnv(GlobalConfigUtil.getCurrentEnv());
            this.setAppCode(GlobalConfigUtil.getCurrentAppCode());
            this.setVersion(GlobalConfigUtil.getCurrentVersion());
            prop.load(env + ".properties");
            this.setCurrentVersion(System.currentTimeMillis());
            this.setDooiooConnection(prop.getString("dooiooConnection", ""));
            this.setDooiooSshUser(prop.getString("dooiooSshUser", ""));
            this.setDooiooSshPwd(prop.getString("dooiooSshPwd", ""));
            this.setIderongConnection(prop.getString("iderongConnection", ""));
            this.setIderongSshPwd(prop.getString("iderongSshPwd", ""));
            this.setIderongSshUser(prop.getString("iderongSshUser", ""));
            this.setIntervalTime(Integer.parseInt(prop.getString("intervalTime", "")));
            this.setPicWidth(Integer.parseInt(prop.getString("picWidth", "")));
            this.setPicHight(Integer.parseInt(prop.getString("picHight", "")));
            this.setRegisterOutDay(Integer.parseInt(prop.getString("registerOutDay", "")));
            this.setBirthdaySelfMsg(prop.getString("birthdaySelfMsg", ""));
            this.setBirthdayOtherMsg(prop.getString("birthdayOtherMsg", ""));
            this.setOldEmpSelfMsg(prop.getString("oldEmpSelfMsg", ""));
            this.setOldEmpOtherMsg(prop.getString("oldEmpOtherMsg", ""));
            this.setOlderEmpSelfMsg(prop.getString("olderEmpSelfMsg", ""));
            this.setOlderEmpOtherMsg(prop.getString("olderEmpOtherMsg", ""));
            this.setUserPicturePath(prop.getString("userPicturePath", ""));
            this.setMailHostName(prop.getString("mailHostName", ""));
            this.setMailFrom(prop.getString("mailFrom", ""));
            this.setMailFromPwd(prop.getString("mailFromPwd", ""));
            this.setMailReceiveUsers(prop.getString("mailReceiveUsers", ""));
            this.setMaxCount(prop.getInt("maxCount"));
            this.setPwdOutOfDate(prop.getInteger("pwdOutOfDate", 30));
            this.setPwdNoticeDate(prop.getInteger("pwdNoticeDate", 3));
            this.setNoticeUrl(prop.getString("noticeUrl"));
            

            this.setZhongJieBu(Integer.parseInt(prop.getString("zhongJieBu")));
            this.setZuLingBu(Integer.parseInt(prop.getString("zuLingBu")));
            this.setXinFangXiaoShouBu(Integer.parseInt(prop.getString("xinFangXiaoShouBu")));
            this.setDaiLiXiangMuBu(Integer.parseInt(prop.getString("daiLiXiangMuBu")));
            this.setDaXiQu(Integer.parseInt(prop.getString("daXiQu")));
            this.setDaDongQu(Integer.parseInt(prop.getString("daDongQu")));
            this.setDaNanQu(Integer.parseInt(prop.getString("daNanQu")));
            this.setDaBeiQu(Integer.parseInt(prop.getString("daBeiQu")));
            this.setYingXiaoDaiLiBu(Integer.parseInt(prop.getString("yingXiaoDaiLiBu")));

            this.setFyTransferApi(prop.getString("fyTransferApi"));
            this.setFyTransferUrl(prop.getString("fyTransferUrl"));
            this.setKyTransferApi(prop.getString("kyTransferApi"));
            this.setKyTransferUrl(prop.getString("kyTransferUrl"));

            this.setHrmsAppCode(prop.getString("hrmsAppCode"));
            this.bomsDesKey=prop.getString("bomsDesKey");
            this.setWebMsgUrl(prop.getString("webMsgUrl"));
            this.setSmsApiUrl(prop.getString("smsApiUrl"));
            this.setZpxxReceivers(prop.getString("zpxxReceivers"));
            this.setSendWebMsgType(prop.getString("sendWebMsgType"));
            this.setSendWebMsgSource(prop.getString("sendWebMsgSource"));

            this.setBranchEmpNum(prop.getDouble("branchEmpNum"));
            this.setEmpNumWarning1(prop.getDouble("empNumWarning1"));
            this.setEmpNumWarning1Users(prop.getString("empNumWarning1Users"));
            this.setEmpNumWarning2(prop.getDouble("empNumWarning2"));
            this.setEmpNumWarning2Users(prop.getString("empNumWarning2Users"));

            //设置上传路径和预览路径
            this.attachPath=prop.getString("attachPath");
            this.attachPreviewUrl=prop.getString("attachPreviewUrl");
            
            setProperty(prop);
        } catch (ConfigurationException e) {
        	e.printStackTrace();
        }
	}

	private void setProperty(PropertiesConfiguration prop){
        try {
            prop.load("dynamic.properties");
            this.setPageSize(Integer.parseInt(prop.getString("pageSize","")));
            this.setOmsApiReadToken(prop.getString("omsApiReadToken"));
            this.setOmsApiWriteToken(prop.getString("omsApiWriteToken"));

            this.siPageSize = prop.getInt("siPageSize", 30);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void initsuperAdminUserCode(Company company){
        switch (company){
            case Dooioo:
                this.setSuperAdminUserCode(90219);
                break;
            case iDerong:
                this.setSuperAdminUserCode(92761);
                break;
        }
    }
    public static void main(String[] args) {
        Configuration.getInstance().init();
    }


}

