package com.zhang.seasons.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

public class UserUtil {
    public static String getSalt() {
        return RandomStringUtils.randomAlphanumeric(20);
    }

    public static String encrypt(String original, String salt) {
        return new SimpleHash("MD5", original, salt, 4).toString();
    }

    public static boolean equalPassword(String password, String salt, String encrypt) {
        return encrypt(password, salt).equals(encrypt);
    }

    public static boolean errorName(String str) {
        return str == null || str.length() < 2 || str.length() > 10;
    }

    public static boolean errorPhone(String str) {
        return str == null || str.length() != 11;
    }

    public static boolean errorPrincipal(String str) {
        return str == null || str.length() < 2 || str.length() > 11;
    }

    public static boolean errorCredential(String str) {
        return str == null || str.length() < 10 || str.length() > 20;
    }
}
