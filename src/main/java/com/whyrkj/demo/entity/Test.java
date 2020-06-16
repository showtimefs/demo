package com.whyrkj.demo.entity;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author zhangsanfeng
 * @date 2020-06-01 22:55
 */
public class Test {


    public static void main(String[] args) {
        System.out.println("输入：");

        Scanner scanner = new Scanner(System.in);

        String arrStr = scanner.nextLine();

        String[] arr = arrStr.split(",");

        int[] arrResult = new int[arr.length];

        for(int i = 0;i<arr.length;i++){
            int num = binaryToDecimal(Integer.valueOf(arr[i]));
            arrResult[i] = num;
        }

        System.out.println("输出：数组转化为十进制值后为："+ Arrays.toString(arrResult));

        //冒泡排序
        int size = arrResult.length;
        for (int i = 0;i < size;i++){

            for(int j = 0;j < size-1-i;j++){
                if(arrResult[j] > arrResult[j+1]){
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

    /**
     * 二进制转十进制
     *
     * @param binaryNumber
     * @return
     */
    private static int binaryToDecimal(int binaryNumber){

        int decimal = 0;
        int p = 0;
        while(true){
            if(binaryNumber == 0){
                break;
            } else {
                int temp = binaryNumber%10;
                decimal += temp*Math.pow(2, p);
                binaryNumber = binaryNumber/10;
                p++;
            }
        }
        return decimal;
    }

}
