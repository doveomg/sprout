package com.sprout.secret;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDetailsSourceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UsernamePasswordAuthenticationFilter) {
            ((UsernamePasswordAuthenticationFilter)bean).setAuthenticationDetailsSource(
                    new WebAuthenticationDetailsSource() {
                        @Override
                        public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
                        	String parameter = context.getParameter("source");
                        	MyAuthenticationProvider.loginParameterInfo.put(Thread.currentThread(), parameter);
                            return new WebAuthenticationDetails(context);
                        }
                    }
            );
        }

        return bean;
    }

}
