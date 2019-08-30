package com.liangzisong.security.core.social;//
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


import com.liangzisong.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.lang.invoke.LambdaConversionException;
import java.security.Security;

/**
 * Copyright (C), 2002-2019
 * FileName: SocialConfig
 * <p>
 * Description:
 *
 * @author 梁子松
 * @version 1.0.0
 * @create 2019/8/30 7:29
 */
@Configuration
@EnableSocial
/**
 *QQAutoConfig，WeixinAutoConfiguration，SocialConfig 这3个都是 SocialConfigurerAdapter 的子类
 * ，但是只有 SocialConfig 覆盖了 SocialConfigurerAdapter  的  getUsersConnectionRepository 方法。
 * 如果SocialConfig 先加载 QQAutoConfig 或 WeixinAutoConfiguration 后加载，
 * 由于后加载的配置没有重写 getUsersConnectionRepository 方法，
 * 所以最终会用 SocialConfigurerAdapter 里的默认配置。
 * 在 SocialConfig 加了 @Order(10) 以后，确保了 SocialConfig 会被最后加载，
 * 所以 UsersConnectionRepository 会用最后加载的 SocialConfig 里的配置。
 */
@Order(10)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    /**如果不偷偷注册就没有这个东西*/
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     *
     * @param connectionFactoryLocator 查找当前应该用那个connectionFactoryLocator（比如qq或者微信）构建JdbcUsersConnectionRepository
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //Encryptors 加密  这里没有加，方便测试
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //设置表的前缀
        jdbcUsersConnectionRepository.setTablePrefix("liangzisong_");
        if(connectionSignUp!=null){

            //偷偷注册
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer liangzisongSocialConfig(){
        String filterProcessesUrl = securityProperties.getSocialProperties().getFilterProcessesUrl();
        LiangzisongSpringSocialConfigurer liangzisongSpringSocialConfigurer = new LiangzisongSpringSocialConfigurer(filterProcessesUrl);
        //添加注册页面
        liangzisongSpringSocialConfigurer.signupUrl(securityProperties.getBrowser().getSigUpUrl());
        return liangzisongSpringSocialConfigurer;
    }

    /***
     *
     * 配置注册utils
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){

        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }

}