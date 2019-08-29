package com.liangzisong.security.core.validate.code.sms;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: SmsCodeSender
 * 让需求简单一点，心灵就会更轻松一点；
 * 让外表简单一点，本色就会更接近一点；
 * 让过程简单一点，内涵就会更丰富一点；
 * <p>
 * <p>
 * <p>
 * Description: 短信验证码发送接口
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/28 16:16
 */
public interface SmsCodeSender {
    void send(String moblie, String code);
}
