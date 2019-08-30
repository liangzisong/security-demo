package com.liangzisong.security.core.social.qq.api;//
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


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * Copyright (C), 2002-2019
 * FileName: QQImpi
 * <p>
 * Description: qq的实现
 *
 * @author 梁子松
 * @version 1.0.0
 * @create 2019/8/29 21:37
 */
public class QQImpi extends AbstractOAuth2ApiBinding implements QQ {

    public static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    public static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpi(String accessToken, String openId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.openId = openId;

        String url = String.format(URL_GET_OPENID, accessToken);
        //返回的是 callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println("result = " + result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println("result = " + result);
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
        }catch (Exception e){
            throw new RuntimeException("获取用户信息失败", e);
        }
        return qqUserInfo;
    }
}