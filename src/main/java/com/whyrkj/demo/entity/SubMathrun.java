package com.whyrkj.demo.entity;

import java.util.Arrays;

/**
 * @author zhangsanfeng
 * @date 2020-06-07 22:42
 */
public class SubMathrun extends Mathrun {

    @Override
    void Changebin(int[] passarr){
        int[] arrResult = new int[passarr.length];

        for(int i = 0;i<passarr.length;i++){
            int num = binaryToDecimal(passarr[i]);
            arrResult[i] = num;
        }

        System.out.println("输出：数组转化为十进制值后为："+ Arrays.toString(arrResult));

        //冒泡排序
        int size = arrResult.length;
        for (int i = 0;i < size;i++){

            for(int j = 0;j < size-1-i;j++){
                if(arrResult[j] < arrResult[j+1]){
                    int temp = arrResult[j];
                    arrResult[j] = arrResult[j+1];
                    arrResult[j+1] = temp;
                }
            }
        }

        System.out.println("数组排序后打印：");
        for (int i = 0; i< arrResult.length;i++){
            System.out.println(arrResult[i]);
        }



    }

}
