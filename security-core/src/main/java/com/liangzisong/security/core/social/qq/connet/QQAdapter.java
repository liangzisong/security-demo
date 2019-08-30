package com.liangzisong.security.core.social.qq.connet;//
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


import com.liangzisong.security.core.social.qq.api.QQ;
import com.liangzisong.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Copyright (C), 2002-2019
 * FileName: QQAdapter
 * <p>
 * Description: qq适配器(泛型参数指的是要适配的类型)
 *
 * @author 梁子松
 * @version 1.0.0
 * @create 2019/8/30 7:12
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /***
     * 测试当前的api是否可用(这里先不做测试)
     * @param api
     * @return true 可用
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * 实际适配逻辑
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        //获取用户信息
        QQUserInfo userInfo = api.getUserInfo();
        //设置用户昵称
        values.setDisplayName(userInfo.getNickname());
        //设置用户头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        //设置用户个人主页
        values.setProfileUrl(null);
        //设置openid
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //do noting
    }
}