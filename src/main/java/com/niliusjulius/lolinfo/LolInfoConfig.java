package com.niliusjulius.lolinfo;

import com.niliusjulius.lolinfo.component.Messages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class LolInfoConfig {

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(Messages.getMessageSource());
        return bean;
    }
}
