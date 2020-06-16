package com.whyrkj.demo.util;

import com.whyrkj.demo.config.excetion.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明: 校验工具类
 *
 * @Author zhangsanfeng
 */
public class CheckUtil {

    /**
     * 校验手机号
     *
     * @param mobile 手机号
     *
     * @return
     */
    public static boolean checkMobile(String mobile) {
        String reg = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
        if (StringUtils.isBlank(mobile)) {
            throw new ServiceException("手机号不能为空");
        }
        return mobile.matches(reg);
    }

    /**
     * 校验身份证
     */
    public static boolean checkIDCord(String IDCOrd) {
        if (StringUtils.isBlank(IDCOrd)) {
            throw new ServiceException("身份证号不能为空");
        }
        String reg = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";
        return IDCOrd.matches(reg);
    }

    /**
     * 校验密码 6-20位字母 数字 字符
     */
    public static boolean checkPwd(String pwd) {
//        String reg = "^[A-Za-z][\\w]{5,19}$";
        String reg = "[\\w]{6,20}$";
        if (null == pwd || "" == pwd) {
            throw new ServiceException("密码不能为空");
        }
        return pwd.matches(reg);
    }

    /**
     * 验证用户名 支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）
     * 长度为2-20位,中文按二位计数
     *
     * @param name
     *
     * @return
     */
    public static boolean checkName(String name) {
        String reg = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";
        if (null == name || "" == name) {
            throw new ServiceException("名字不能为空");
        }
        boolean flag = false;
        flag = matcher(reg, name);
        if (flag) {
            int strLenth = getStrLength(name);
            if (strLenth < 2 || strLenth > 20) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 获取字符串的长度，对双字符（包括汉字）按两位计数
     *
     * @param value
     *
     * @return
     */
    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    private static boolean matcher(String reg, String string) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);
        tem = matcher.matches();
        return tem;
    }
}
