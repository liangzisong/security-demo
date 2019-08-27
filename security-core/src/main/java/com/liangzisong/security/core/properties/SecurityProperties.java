package com.liangzisong.security.core.properties;//
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


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: SecurityProperties
 * <p>
 * Description: security配置
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/27 10:28
 */
@ConfigurationProperties(prefix = "liangzisong.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    /**验证码的配置*/
    private ValidateCodeProperties validateCodeProperties = new ValidateCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
    public ValidateCodeProperties getValidateCodeProperties() {

        return validateCodeProperties;
    }

    public void setValidateCodeProperties(ValidateCodeProperties validateCodeProperties) {
        this.validateCodeProperties = validateCodeProperties;
    }
}