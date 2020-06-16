package com.whyrkj.demo.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * 说明: 密码工具类
 * @Author zhangsanfeng
 */
public class PasswordUtil {

    /**
     * @param @return 参数
     *
     * @return String 返回类型
     *
     * @throws
     * @Title: generateSalt
     * @Description: (获取密码盐)
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes().toHex();
        return hex;
    }

    /**
     * @param @param  password 原始密码
     * @param @param  salt 密码盐
     * @param @return 参数
     *
     * @return String 返回类型
     *
     * @throws
     * @Title: getPassword
     * @Description: (获取加密后的密文)
     */
    public static String getPassword(String password, String salt) {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hashedPasswordBase64 = new Sha256Hash(password, salt, 1024).toBase64();
        return hashedPasswordBase64;
    }
}
