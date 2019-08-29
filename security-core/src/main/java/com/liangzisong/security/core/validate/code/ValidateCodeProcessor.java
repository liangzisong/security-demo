package com.liangzisong.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: ValidateCodeProcessor
 * 让需求简单一点，心灵就会更轻松一点；
 * 让外表简单一点，本色就会更接近一点；
 * 让过程简单一点，内涵就会更丰富一点；
 * <p>
 * <p>
 * <p>
 * Description: 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/28 16:52
 */
public interface ValidateCodeProcessor {

    /**
     *@创建人:  如果这段代码非常棒就是梁子松写的
     *         如果这代码挺差劲那么我也不知道是谁写的
     *@创建时间:  2019/8/28 16:53
     *@描述:  验证码放入session时的前缀
     *@参数: 
     *@返回值: 
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     *  *@创建人:  如果这段代码非常棒就是梁子松写的
     * 如果这代码挺差劲那么我也不知道是谁写的
     *  *@创建时间:  2019/8/28 16:57
     *  *@描述:  创建校验码
     *  *@参数: 
     *  *@返回值: 
     *  
     */
    void create(ServletWebRequest request) throws Exception;

    void validate(ServletWebRequest request);
}
