package com.zhang.seasons.controller;

import com.zhang.seasons.bean.User;
import com.zhang.seasons.http.ApiMsg;
import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.UserService;
import com.zhang.seasons.util.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登陆
     * @param params 登陆的表单参数
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/login")
    public Result login(@RequestParam Map<String, String> params, HttpSession session) {
        String credential = params.get("name");
        if (!UserUtil.checkCredential(credential)) return Result.error(ApiMsg.LOGIN_ERROR2);
        String password = params.get("password");
        if (!UserUtil.checkPassword(password)) return Result.error(ApiMsg.LOGIN_ERROR3);
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //将前端账号和密码包装成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(credential, password);
        try {
            // 传入令牌，令牌中有前端输入的账号密码，调用shiro的配置函数与数据库的账号密码比对
            // 如果比对错误将抛出异常， 正确则继续执行
            subject.login(token);
            User user = userService.selectUserForLogin(credential);
            session.setAttribute("uid", user.getUid());
            session.setAttribute("name", user.getName());
            user.erasePassword();
            return Result.success(user);
        } catch (AuthenticationException e) {
            return Result.error(ApiMsg.LOGIN_ERROR1);
        }
    }

    /**
     * 注册
     * @param user 注册提供的用户信息
     * @return 是否成功
     */
    @PostMapping("/register")
    public Result register(@RequestParam User user) {
        if (userService.isExist(user.getName())) return Result.error(ApiMsg.NAME_ERROR);
        String salt = UserUtil.getSalt();
        String encodedPassword = UserUtil.encrypt(user.getPassword(), user.getSalt());
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        boolean suc = userService.insertUser(user);
        if (suc) {
            // 注册成功给该用户分配普通用户角色
            userService.insertUserRole(user.getUid(), 1);
            return Result.success();
        }
        else return Result.error(ApiMsg.REGISTER_ERROR);
    }

}
