package com.zhang.seasons.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.seasons.bean.User;
import com.zhang.seasons.http.APIMsg;
import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.UserService;
import com.zhang.seasons.util.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class UserController {
    private static final int userPageAmount = 30;

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
        String principal = params.get("name");
        if (UserUtil.errorPrincipal(principal)) return Result.error(APIMsg.PRINCIPAL_ERROR);
        String password = params.get("password");
        if (UserUtil.errorCredential(password)) return Result.error(APIMsg.CREDENTIAL_ERROR);
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //将前端账号和密码包装成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(principal, password);
        try {
            // 传入令牌，令牌中有前端输入的账号密码，调用shiro的配置函数与数据库的账号密码比对
            // 如果比对错误将抛出异常， 正确则继续执行
            subject.login(token);
            User user = userService.selectUserForLogin(principal);
            session.setAttribute("uid", user.getUid());
            session.setAttribute("name", user.getName());
            user.erasePassword();
            return Result.success(user);
        } catch (AuthenticationException e) {
            return Result.error(APIMsg.LOGIN_ERROR);
        }
    }

    /**
     * 注册
     * @param user 注册提供的用户信息
     * @return 是否成功
     */
    @PostMapping("/register")
    public Result register(@RequestParam User user) {
        if (UserUtil.errorName(user.getName())) return Result.error(APIMsg.NAME_ERROR);
        if (UserUtil.errorCredential(user.getPassword())) return Result.error(APIMsg.CREDENTIAL_ERROR);
        if (user.getPhone() != null && UserUtil.errorPhone(user.getPhone())) return Result.error(APIMsg.PHONE_ERROR);
        if (userService.isExist(user.getName())) return Result.error(APIMsg.NAME_EXIST_ERROR);
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
        else return Result.error(APIMsg.REGISTER_ERROR);
    }

    @DeleteMapping("/user/{uid}")
    @RequiresPermissions("user:*")
    public Result deleteUserByAdmin(@PathVariable("uid") int uid) {
        return userService.deleteUser(uid) ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @DeleteMapping("/user")
    @RequiresPermissions("user:delete")
    public Result deleteUser(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        float coin = userService.selectUserCoin(uid);
        if (coin > 0) return Result.error(APIMsg.COIN_LEFT_ERROR);
        return userService.deleteUser(uid) ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @PutMapping("/user/name/{name}")
    @RequiresPermissions("user:update")
    public Result updateUserName(@PathVariable("name") String name, HttpSession session) {
        if (UserUtil.errorName(name)) return Result.error(APIMsg.NAME_ERROR);
        int uid = (int) session.getAttribute("uid");
        return userService.updateUserName(uid, name) ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @PutMapping("/user/phone/{phone}")
    @RequiresPermissions("user:update")
    public Result updateUserPhone(@PathVariable("phone") String phone, HttpSession session) {
        if (UserUtil.errorPhone(phone)) return Result.error(APIMsg.PHONE_ERROR);
        int uid = (int) session.getAttribute("uid");
        return userService.updateUserPhone(uid, phone) ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    // 没有用的接口
    @PutMapping("/user/coin")
    public Result updateUserCoin(@RequestParam Map<String, String> params, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        float coin = Float.parseFloat(params.get("coin"));

        // 检查一下是否购买或充值

        return userService.updateUserCoin(uid, coin) ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @PutMapping("/user/password")
    @RequiresPermissions("user:update")
    public Result updateUserPassword(@RequestParam Map<String, String> params, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        String oldP = params.get("old");
        String newP = params.get("new");
        if (oldP == null || newP == null) {
            return Result.error(APIMsg.PARAMETER_ERROR);
        }
        if (UserUtil.errorCredential(newP)) {
            return Result.error(APIMsg.CREDENTIAL_ERROR);
        }
        User user = userService.selectUserByUid(uid, 0);
        if (UserUtil.equalPassword(oldP, user.getSalt(), user.getPassword())) {
            String salt = UserUtil.getSalt();
            String encrypt = UserUtil.encrypt(newP, salt);
            return userService.updateUserPassword(uid, encrypt, salt) ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
        }
        return Result.error(APIMsg.PASSWORD_ERROR_2);
    }

    @PutMapping("/user/active/{active}")
    @RequiresPermissions("user:*")
    public Result updateUserActive(@PathVariable("active") boolean active, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        return userService.updateUserActive(uid, active) ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @GetMapping("/user/uid/{uid}")
    @RequiresAuthentication
    public Result selectUserByUid(@PathVariable("uid") int uid) {
        User user = userService.selectUserByUid(uid, 2);
        return Result.success(user);
    }

    @GetMapping("/user/name/{name}")
    @RequiresAuthentication
    public Result selectUserByName(@PathVariable("name") String name) {
        User user = userService.selectUserByName(name, 2);
        return Result.success(user);
    }

    @GetMapping({"/user/active/{active}", "/user/active/{active}/{page}"})
    @RequiresPermissions("user:*")
    public Result selectUserByActive(@PathVariable("active") boolean active,
                                     @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, userPageAmount);
        PageInfo<User> list = new PageInfo<>(userService.selectUserByActive(active));
        return Result.success(list);
    }

    @GetMapping({"/user/all", "/user/all/{page}"})
    @RequiresPermissions("user:*")
    public Result selectAllUser(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, userPageAmount);
        PageInfo<User> list = new PageInfo<>(userService.selectAllUser());
        return Result.success(list);
    }
}
