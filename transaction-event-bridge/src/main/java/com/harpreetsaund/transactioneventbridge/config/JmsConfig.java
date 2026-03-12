package com.harpreetsaund.transactioneventbridge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class JmsConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JmsConfig.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("JMS configuration enabled.");
    }
}
