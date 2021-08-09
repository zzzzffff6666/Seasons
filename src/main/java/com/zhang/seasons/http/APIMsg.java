package com.zhang.seasons.http;

public interface APIMsg {
    String AUTH_ERROR = "无权限";
    String LOGIN_ERROR = "用户名户密码错误";
    String PRINCIPAL_ERROR = "用户名长度需在2-10之间，手机号长度需为11";
    String CREDENTIAL_ERROR = "密码长度需在10-20之间";
    String NAME_ERROR = "用户名长度需在2-10之间";
    String PHONE_ERROR = "手机号长度需为11";
    String REGISTER_ERROR = "注册失败";
    String ACCOUNT_ERROR = "账户已被禁用";
    String NAME_EXIST_ERROR = "用户名已存在";
    String PHONE_EXIST_ERROR = "手机号已存在";
    String PASSWORD_ERROR = "密码错误";
    String INSERT_ERROR = "插入失败";
    String DELETE_ERROR = "删除失败";
    String UPDATE_ERROR = "更新失败";
    String PARAMETER_ERROR = "参数错误";
    String UNKNOWN_ERROR = "未知错误";
    String COIN_LEFT_ERROR = "账户中有余额，请提现后再注销账户";
    String MANAGE_MAX_ERROR = "管理分区已达上限";
    String BALANCE_NOT_ENOUGH_ERROR = "余额不足";
    String COMMON_REFUND_ERROR = "非商用购买不支持退款";
    String BUSINESS_REFUND_ERROR = "商用购买7天后无法退款";
}
