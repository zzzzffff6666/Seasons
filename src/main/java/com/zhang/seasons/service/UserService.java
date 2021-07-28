package com.zhang.seasons.service;

import com.zhang.seasons.bean.User;
import com.zhang.seasons.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    // user 部分

    public boolean insertUser(User user) {
        return userMapper.insert(user) == 1;
    }

    public boolean deleteUser(int uid) {
        return userMapper.delete(uid) == 1;
    }

    public boolean updateUserName(int uid, String name) {
        return userMapper.updateName(uid, name) == 1;
    }

    public boolean updateUserPhone(int uid, String phone) {
        return userMapper.updatePhone(uid, phone) == 1;
    }

    public boolean updateUserPassword(int uid, String password, String salt) {
        return userMapper.updatePassword(uid, password, salt) == 1;
    }

    public boolean updateUserCoin(int uid, float addition) {
        return userMapper.updateCoin(uid, addition) == 1;
    }

    public boolean updateUserActive(int uid, boolean active) {
        return userMapper.updateActive(uid, active) == 1;
    }

    public float selectUserCoin(int uid) {
        return userMapper.selectCoin(uid);
    }

    public boolean isExist(String name) {
        return userMapper.selectName(name) == 1;
    }

    public User selectUserByUid(int uid, int eraseLevel) {
        User user = userMapper.selectByUid(uid);
        if (user != null) {
            if (eraseLevel == 1) user.erasePassword();
            else if (eraseLevel == 2) user.eraseInfo();
        }
        return user;
    }

    public User selectUserByName(String name, int eraseLevel) {
        User user = userMapper.selectByName(name);
        if (user != null) {
            if (eraseLevel == 1) user.erasePassword();
            else if (eraseLevel == 2) user.eraseInfo();
        }
        return user;
    }

    public User selectUserForLogin(String credential) {
        if (credential.length() < 11) {
            return userMapper.selectByName(credential);
        } else {
            return userMapper.selectByPhone(credential);
        }
    }

    public List<User> selectUserByActive(boolean active) {
        return userMapper.selectByActive(active);
    }

    public List<User> selectAllUser() {
        return userMapper.selectAll();
    }

    // user_role 部分

    public boolean insertUserRole(int uid, int rid) {
        return userRoleMapper.insert(uid, rid) == 1;
    }

    public boolean deleteUserRole(int uid, int rid) {
        return userRoleMapper.delete(uid, rid) == 1;
    }

    public boolean deleteUserRoleByUid(int uid) {
        return userRoleMapper.deleteByUid(uid) > 0;
    }

    public List<Integer> selectRoleByUid(int uid) {
        return userRoleMapper.selectByUid(uid);
    }
}
