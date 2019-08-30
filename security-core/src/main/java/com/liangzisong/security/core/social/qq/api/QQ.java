package com.liangzisong.security.core.social.qq.api;

import java.io.IOException;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: QQ
 * 让需求简单一点，心灵就会更轻松一点；
 * 让外表简单一点，本色就会更接近一点；
 * 让过程简单一点，内涵就会更丰富一点；
 * <p>
 * <p>
 * <p>
 * Description: qq
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/29 21:25
 */
public interface QQ {

    /***
      * @Author:  梁子松
      * @Date:  2019/8/29 21:32
      * @Description:  获取用户信息
      * @return QQ用户信息
      */
    QQUserInfo getUserInfo();

}
