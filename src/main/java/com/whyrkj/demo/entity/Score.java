package com.whyrkj.demo.entity;

/**
 * @author zhangsanfeng
 * @date 2020-06-07 22:40
 */
public class Score {

    public static void main(String[] args) {

        Mathrun mathrun = new Mathrun();
        int[] arr=new int[]{1101,1001,110,101,111,10,11};
        mathrun.Changebin(arr);

        SubMathrun subMathrun = new SubMathrun();
        subMathrun.Changebin(arr);
    }
}
