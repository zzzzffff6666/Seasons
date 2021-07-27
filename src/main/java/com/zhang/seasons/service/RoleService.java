package com.zhang.seasons.service;

import com.zhang.seasons.bean.Role;
import com.zhang.seasons.mapper.PermissionMapper;
import com.zhang.seasons.mapper.RoleMapper;
import com.zhang.seasons.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    // role 部分

    public List<String> selectRoleNameByList(List<Integer> list) {
        return roleMapper.selectNameByList(list);
    }

    public List<Role> selectRoleByList(List<Integer> list) {
        return roleMapper.selectByList(list);
    }

    public List<Role> selectAllRole() {
        return roleMapper.selectAll();
    }

    // role_permission 部分

    public List<Integer> selectPermissionByRid(int rid) {
        return rolePermissionMapper.selectByRid(rid);
    }

    public List<Integer> selectPermissionByRidList(List<Integer> list) {
        return rolePermissionMapper.selectByList(list);
    }

    // permission 部分

    public List<String> selectPermissionNameByList(List<Integer> list) {
        return permissionMapper.selectByList(list);
    }

}
