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

    public static boolean checkCredential(String str) {
        return str.length() >= 2 && str.length() <= 11;
    }

    public static boolean checkPassword(String str) {
        return str.length() >= 10 && str.length() <= 20;
    }
}
