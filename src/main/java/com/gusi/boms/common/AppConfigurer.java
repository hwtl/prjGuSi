package com.gusi.boms.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.dooioo.dbpm.config.DyPlusConfig;
import com.dooioo.plus.util.GlobalConfigUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * @Package AppConfigurer
 * @Description: 启动类
 * @author Jail    E -Mail:86455@ dooioo.com
 * @date 2012-12-4 下午3:33:32
 * @version V1.0
 */
public class AppConfigurer extends PropertyPlaceholderConfigurer {
    public static final Log log = LogFactory.getLog(AppConfigurer.class);
	@Override
    protected Properties mergeProperties() throws IOException {
        Properties superProps = super.mergeProperties();
        String env = GlobalConfigUtil.getCurrentEnv();
        superProps.put("env", env);
        log.info("运行环境====>>"+env);
        DyPlusConfig.getInstance().setEnv(env);
        return superProps;
    }
}

