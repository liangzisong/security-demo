package com.liangzisong.security.brower;//
//
//
//

////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                       //
////////////////////////////////////////////////////////////////////
//
//
//
//
//让需求简单一点，心灵就会更轻松一点；
//让外表简单一点，本色就会更接近一点；
//让过程简单一点，内涵就会更丰富一点；
//
//
//


import com.liangzisong.security.brower.authentication.ImoocAuthenctiationFailureHandler;
import com.liangzisong.security.brower.authentication.ImoocAuthenticationSuccessHandler;
import com.liangzisong.security.core.authentication.AbstractChannelSecurityConfig;
import com.liangzisong.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liangzisong.security.core.properties.SecurityConstants;
import com.liangzisong.security.core.properties.SecurityProperties;
import com.liangzisong.security.core.validate.code.SmsCodeFilter;
import com.liangzisong.security.core.validate.code.ValidateCodeFilter;
import com.liangzisong.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: BrowserSecurityConfig
 * <p>
 * Description:
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/26 17:27
 */
@Component
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ImoocAuthenctiationFailureHandler imoocAuthenctiationFailureHandler;

    @Autowired
    private ImoocAuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Bean
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);


        http
                //图形验证码
                .apply(validateCodeSecurityConfig)
                //短信
                .and().apply(smsCodeAuthenticationSecurityConfig)
                //记住我
                .and().rememberMe()
                //设置 persistentTokenRepository 的实现
                .tokenRepository(persistentTokenRepository())
                //设置过期的秒数
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                //添加userDetailsService
                .userDetailsService(userDetailsService)
                //对请求授权
                .and().authorizeRequests()
                //当访问下面的登录页面不需要认证
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
                )
                //任何请求 都需要身份认证
                .permitAll()
                .anyRequest()
                .authenticated()
                //暂时把csrf 忽略掉
                .and().csrf().disable();

//        http
//                //添加短信验证码码过滤器 放在表单登录前面
//                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                //添加图形验证码过滤器 放在表单登录前面
//                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                //指定表单登录
//                .formLogin()
//        //指定httpBasic登录
//        //http.httpBasic()
//                //指定登录页面
//                .loginPage("/authentication/require")
//                //指定登录请求的地址  默认会走login 这里修改一下
//                .loginProcessingUrl("/authentication/form")
//                //自定义成功处理器
//                .successHandler(imoocAuthenticationSuccessHandler)
//                //自定义失败处理器
//                .failureHandler(imoocAuthenctiationFailureHandler)
//                //记住我
//                .and().rememberMe()
//                //设置 persistentTokenRepository 的实现
//                .tokenRepository(persistentTokenRepository())
//                //设置过期的秒数
//                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//                //添加userDetailsService
//                .userDetailsService(userDetailsService)
//              //对请求授权
//                .and().authorizeRequests()
//                //当访问下面的登录页面不需要认证
//                .antMatchers(
//                        //配置登录页面不需要权限认证
//                        //登录认证转发
//                        "/authentication/require",
//                        //登录页面
//                        securityProperties.getBrowser().getLoginPage(),
//                        //图片验证码
//                        "/code/*"
//                ).permitAll()
//                //任何请求
//                .anyRequest()
//                //都需要身份认证
//                .authenticated()
//                //暂时把csrf 忽略掉
//                .and().csrf().disable()
//                //相当于把smsCodeAuthenticationSecurityConfig内configure方法的配置加载后面了   等于接着往下写了这一段配置
//                .apply(smsCodeAuthenticationSecurityConfig);

    }
}