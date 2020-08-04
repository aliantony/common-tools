package com.boot.leecode;
/**
 * @program common-tools
 * @description 
 * @author wq
 * created on 2020-08-04
 * @version  1.0.0
 */
public class FeiBo {
    public static void main(String[] args) {
        System.out.println(fun(40));
    }
    public static Integer fun(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i == 1 || i == 2) {
            return 1;
        }
        int first = 1;
        int second = 1;
        int temp = 0;
        for (int j = 3;j <=i; j++) {
            temp = first + second;
            first = second;
            second = temp;
        }
        //return fun(i-1) + fun(i-2);
        return temp;
    }
}
