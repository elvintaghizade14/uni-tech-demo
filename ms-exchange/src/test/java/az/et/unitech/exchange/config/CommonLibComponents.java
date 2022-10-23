package az.et.unitech.exchange.config;

import az.et.unitech.common.config.properties.FilterProperties;
import az.et.unitech.common.security.TokenProvider;
import az.et.unitech.common.security.handler.SecurityProblemHandler;
import az.et.unitech.common.util.MessageSourceUtil;
import az.et.unitech.common.util.WebUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TokenProvider.class, SecurityProblemHandler.class, WebUtil.class, MessageSourceUtil.class,
        FilterProperties.class})
public class CommonLibComponents {

}
