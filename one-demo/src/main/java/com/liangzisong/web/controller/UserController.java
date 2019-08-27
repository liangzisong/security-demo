package com.liangzisong.web.controller;//
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


import com.liangzisong.dto.User;
import com.liangzisong.dto.UserQueryCondition;
import com.liangzisong.exception.UserNotExistException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2002-2019, 山东沃然网络科技有限公司
 * FileName: UserController
 * <p>
 * Description: 用户controller
 *
 * @author 如果这段代码非常棒就是梁子松写的
 * 如果这代码挺差劲那么我也不知道是谁写的
 * @version 1.0.0
 * @create 2019/8/26 14:30
 */
@RestController
@RequestMapping("user")
public class UserController {


    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){

        return userDetails;
    }

    @GetMapping
    @JsonView({User.UserSimpleView.class})
    public List<User> query(UserQueryCondition condition){
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());

        return list;
    }


    @GetMapping("{id:\\d+}")
    @JsonView({User.UserDetailView.class})
    public User getInfo(@PathVariable String id){
        System.out.println("id = " + id);
        User user = new User();
        user.setUsername("tom");
        return user ;
    }

    @PostMapping
    public User createUser(@Validated @RequestBody User user){
        System.out.println("user = " + user);
        user.setId("1");
        return user;
    }

    @PutMapping("{id:\\d+}")
    public User updateUser(@RequestBody User user){
        user.setId("1");
        return user ;
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println("id = " + id);
    }



}