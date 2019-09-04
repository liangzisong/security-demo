/**
 * 
 */
package com.liangzisong.security.brower;

import com.liangzisong.security.brower.logout.ImoocLogoutSuccessHandler;
import com.liangzisong.security.brower.session.ImoocExpiredSessionStrategy;
import com.liangzisong.security.brower.session.ImoocInvalidSessionStrategy;
import com.liangzisong.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new ImoocInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new ImoocExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}

	//退出成功的处理器
	@Bean
	@ConditionalOnMissingBean(ImoocLogoutSuccessHandler.class)
	public ImoocLogoutSuccessHandler logoutSuccessHandler(){
		return new ImoocLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
	}
}
