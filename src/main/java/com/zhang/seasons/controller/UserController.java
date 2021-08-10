package com.zhang.seasons.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.seasons.bean.Subscribe;
import com.zhang.seasons.bean.User;
import com.zhang.seasons.http.APIMsg;
import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.RoleService;
import com.zhang.seasons.service.SubscribeService;
import com.zhang.seasons.service.UserService;
import com.zhang.seasons.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private static final int USER_PAGE_AMOUNT = 20;
    private static final int SUBSCRIBE_PAGE_AMOUNT = 20;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SubscribeService subService;

    //User 部分

    /**
     * 登陆
     * @param params 登陆的表单参数
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/login")
    @RequiresGuest
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
            userService.updateUserLoginTime(user.getUid(), new Timestamp(System.currentTimeMillis()));
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
    @RequiresGuest
    public Result register(@RequestParam User user) {
        if (UserUtil.errorName(user.getName())) return Result.error(APIMsg.NAME_ERROR);
        if (userService.isNameExist(user.getName())) return Result.error(APIMsg.NAME_EXIST_ERROR);
        if (UserUtil.errorCredential(user.getPassword())) return Result.error(APIMsg.CREDENTIAL_ERROR);
        if (user.getPhone() != null) {
            if (UserUtil.errorPhone(user.getPhone())) return Result.error(APIMsg.PHONE_ERROR);
            if (userService.isPhoneExist(user.getPhone())) return Result.error(APIMsg.PHONE_EXIST_ERROR);
        }
        String salt = UserUtil.getSalt();
        String encodedPassword = UserUtil.encrypt(user.getPassword(), user.getSalt());
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        // 注册的同时给该用户分配普通用户角色
        boolean suc = userService.insertUser(user);
        return suc ? Result.success() : Result.error(APIMsg.REGISTER_ERROR);
    }

    @DeleteMapping("/user-admin")
    @RequiresPermissions("user:*")
    public Result deleteUserAsAdmin(@RequestParam("uid") int uid) {
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

    @PutMapping("/user/info")
    @RequiresPermissions("user:update")
    public Result updateUserName(@RequestParam("name") String name, @RequestParam("phone") String phone, HttpSession session) {
        if (UserUtil.errorName(name)) return Result.error(APIMsg.NAME_ERROR);
        if (userService.isNameExist(name)) return Result.error(APIMsg.NAME_EXIST_ERROR);
        if (UserUtil.errorPhone(phone)) return Result.error(APIMsg.PHONE_ERROR);
        if (userService.isPhoneExist(phone)) return Result.error(APIMsg.PHONE_EXIST_ERROR);
        int uid = (int) session.getAttribute("uid");
        return userService.updateUserInfo(uid, name, phone) ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
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
        return Result.error(APIMsg.PASSWORD_ERROR);
    }

    @PutMapping("/user/active")
    @RequiresPermissions("user:*")
    public Result updateUserActive(@RequestParam("uid") int uid, @RequestParam("active") boolean active,
                                   HttpSession session) {
        boolean suc = userService.updateUserActive(uid, active);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
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

    @GetMapping({"/user/list/active/{active}", "/user/list/active/{active}/{page}"})
    @RequiresPermissions("user:*")
    public Result selectUserByActive(@PathVariable("active") boolean active,
                                     @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, USER_PAGE_AMOUNT);
        PageInfo<User> list = new PageInfo<>(userService.selectUserByActive(active));
        return Result.success(list);
    }

    @GetMapping({"/user/all", "/user/all/{page}"})
    @RequiresPermissions("user:*")
    public Result selectAllUser(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, USER_PAGE_AMOUNT);
        PageInfo<User> list = new PageInfo<>(userService.selectAllUser());
        return Result.success(list);
    }

    // User_Role 部分

    @PostMapping("/user_role")
    public Result insertUserRole(@RequestParam("uid") int uid, @RequestParam("rid") int rid) {
        boolean suc = userService.insertUserRole(uid, rid);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    @DeleteMapping("/user_role")
    public Result deleteUserRole(@RequestParam("uid") int uid, @RequestParam("rid") int rid) {
        boolean suc = userService.deleteUserRole(uid, rid);
        return suc ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @GetMapping("/user_role/{uid}")
    public Result selectRoleByUid(@PathVariable("uid") int uid) {
        List<Integer> roleIdList = userService.selectRoleByUid(uid);
        return Result.success(roleService.selectRoleByList(roleIdList));
    }

    // Subscription 部分

    @PostMapping("/subscribe")
    public Result insertSubscribe(@RequestParam("publisher") int publisher,
                                     @RequestParam("level") int level, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Subscribe subscribe = new Subscribe();
        subscribe.setSubscriber(uid);
        subscribe.setPublisher(publisher);
        subscribe.setLevel(level);
        subscribe.setCreated(new Timestamp(System.currentTimeMillis()));
        boolean suc = subService.insertSubscribe(subscribe);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    @DeleteMapping("/subscribe")
    public Result deleteSubscribe(@RequestParam("publisher") int publisher, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        boolean suc = subService.deleteSubscribe(uid, publisher);
        return suc ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @PutMapping("/subscribe/level")
    public Result updateSubscribeLevel(@RequestParam("publisher") int publisher,
                                       @RequestParam("level") int level, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Subscribe subscribe = new Subscribe();
        subscribe.setSubscriber(uid);
        subscribe.setPublisher(publisher);
        subscribe.setLevel(level);
        boolean suc = subService.updateSubscribeLevel(subscribe);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @GetMapping("/subscribe/{publisher}")
    public Result selectSubscribe(@PathVariable("publisher") int publisher, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Subscribe subscribe = subService.selectSubscribe(uid, publisher);
        return Result.success(subscribe);
    }

    @GetMapping({"/subscribe/list/publisher", "/subscribe/list/publisher/{page}"})
    public Result selectSubscribeByPublisher(@PathVariable(value = "page", required = false) Integer page,
                                             HttpSession session) {
        if (page == null) page = 1;
        int uid = (int) session.getAttribute("uid");
        PageHelper.startPage(page, SUBSCRIBE_PAGE_AMOUNT);
        PageInfo<Subscribe> list = new PageInfo<>(subService.selectSubscribeByPublisher(uid));
        return Result.success(list);
    }

    @GetMapping({"/subscribe/list/subscriber", "/subscribe/list/subscriber/{page}"})
    public Result selectSubscribeBySubscriber(@PathVariable(value = "page", required = false) Integer page,
                                              HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (page == null) page = 1;
        PageHelper.startPage(page, SUBSCRIBE_PAGE_AMOUNT);
        PageInfo<Subscribe> list = new PageInfo<>(subService.selectSubscribeBySubscriber(uid));
        return Result.success(list);
    }

    @GetMapping({"/subscribe/all", "/subscribe/all/{page}"})
    public Result selectAllSubscribe(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, SUBSCRIBE_PAGE_AMOUNT);
        PageInfo<Subscribe> list = new PageInfo<>(subService.selectAllSubscribe());
        return Result.success(list);
    }
}
