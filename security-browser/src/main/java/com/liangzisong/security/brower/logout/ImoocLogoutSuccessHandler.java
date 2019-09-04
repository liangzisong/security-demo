package com.liangzisong.security.brower.logout;//
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
import com.liangzisong.security.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.SimpleBeanInfo;
import java.io.IOException;

/**
 * FileName: ImoocLogoutSuccessHandler
 * Description: 退出
 *
 * @author 梁子松
 * @version 10.0
 * @create 2019/9/3 21:54
 */
public class ImoocLogoutSuccessHandler implements LogoutSuccessHandler {

    private String logoutUrl;

    private Logger logger = LoggerFactory.getLogger(ImoocLogoutSuccessHandler.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    public ImoocLogoutSuccessHandler(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("退出成功");

        //如果没有配置退出页面 返回json
        if(StringUtils.isBlank(logoutUrl)){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }else {
            //配置了，直接返回配置的页面
            response.sendRedirect(logoutUrl);
        }
    }
}