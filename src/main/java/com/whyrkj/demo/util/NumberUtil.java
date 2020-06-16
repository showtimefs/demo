package com.whyrkj.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 说明: 运算工具类
 * @Author zhangsanfeng
 */
public abstract class NumberUtil {

    public static BigDecimal round(Float value) {
        return round(value, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal round(Float value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(scale, roundingMode);//BigDecimal.ROUND_HALF_UP
    }

    public static BigDecimal round(Double value) {
        return round(value, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal round(Double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(scale, roundingMode);//BigDecimal.ROUND_HALF_UP
    }

    public static BigDecimal round(String value) {
        return round(value, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal round(String value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(scale, roundingMode);//BigDecimal.ROUND_HALF_UP
    }

    //---------------------------------------------------------------------
    //如果你希望BigDecimal能够精确地表示你希望的数值，那么一定要使用字符串来表示小数，并传递给BigDecimal的构造函数。

    /**
     * 加法运算
     */
    public static BigDecimal add(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return new BigDecimal(0);
        }
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        return bigDecimal1.add(bigDecimal2);
    }

    /**
     * 减法运算
     */
    public static BigDecimal sub(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return new BigDecimal(0);
        }
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        return bigDecimal1.subtract(bigDecimal2);
    }

    /**
     * 乘法运算
     */
    public static BigDecimal mul(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return new BigDecimal(0);
        }
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        return bigDecimal1.multiply(bigDecimal2);
    }

    /**
     * 乘法运算
     *
     * @param scale      精度(小数位)
     * @param roundModel 舍入模式
     *
     * @return
     */
    public static BigDecimal mul(String s1, String s2, int scale, int roundModel) {
        BigDecimal decimal = mul(s1, s2);
        return decimal.setScale(scale, roundModel);
    }

    /**
     * 除法运算
     */
    public static BigDecimal div(String s1, String s2) {
        return div(s1, s2, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除法运算 指定精度和舍入模式
     *
     * @param scale      精度(小数位)
     * @param roundModel 舍入模式
     *
     * @return
     */
    public static BigDecimal div(String s1, String s2, int scale, int roundModel) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return new BigDecimal(0);
        }
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        return bigDecimal1.divide(bigDecimal2, scale, roundModel);
    }
}
