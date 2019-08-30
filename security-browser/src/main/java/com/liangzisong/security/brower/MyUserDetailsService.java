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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: MyUserDetailsService
 * <p>
 * Description:
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/27 9:01
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    /***
     * 表单登录时用的
     * @param userName 用户昵称
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("登录的表单用户用户名="+userName);
        return getSocialUserDetails(userName);

    }

    /***
     * 第三方登录的时候用的
     * @param userId 用户的唯一标识（可以是用户的主键id 或者姓名之类的，保证唯一性就行）
     * @return 用户信息
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("登录的用户id="+userId);
        return getSocialUserDetails(userId);
    }

    private SocialUserDetails getSocialUserDetails(String userId) {

        //根据用户名查询用户信息

        //根据查找到的用户信息判断用户信息是否被冻结
        //因为SocialUser继承自User所以loadUserByUsername也可以返回这个
        return new SocialUser(userId,passwordEncoder.encode("123456"),
                true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}