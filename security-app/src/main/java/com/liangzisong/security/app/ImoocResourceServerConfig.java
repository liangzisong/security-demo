package com.liangzisong.security.app;//
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


import com.liangzisong.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liangzisong.security.core.properties.SecurityConstants;
import com.liangzisong.security.core.properties.SecurityProperties;
import com.liangzisong.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 * FileName: ImoocAuthorizationServerConfig
 * Description:
 *
 * @author 梁子松
 * @version 1.0.0
 * @create 2019/9/4 21:32
 */
@Component
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer liangzisongSocialConfig;

    @Autowired
    private SecurityProperties securityProperties;

    //安全配置
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler);


        http
                //图形验证码
//                .apply(validateCodeSecurityConfig)
                //短信
//                .and().apply(smsCodeAuthenticationSecurityConfig)
                //第三方认证
//                .and().apply(liangzisongSocialConfig)
                //对请求授权
//                .and()
                .authorizeRequests()
                //当访问下面的登录页面不需要认证
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        //登录
                        securityProperties.getBrowser().getLoginPage(),
                        //注册页面
                        securityProperties.getBrowser().getSigUpUrl(),
                        //验证码
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        //session失效链接
                        SecurityConstants.DEFAULT_SESSION_INVALID_URL,
                        //退出页面
                        securityProperties.getBrowser().getSignOutUrl(),
                        //真正注册逻辑
                        "/user/regist"

                )
                //任何请求 都需要身份认证
                .permitAll()
                .anyRequest()
                .authenticated()
                //暂时把csrf 忽略掉
                .and().csrf().disable();
    }
}