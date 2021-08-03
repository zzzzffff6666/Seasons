package com.zhang.seasons.controller;

import com.zhang.seasons.service.AuditService;
import com.zhang.seasons.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdminController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuditService auditService;
}
