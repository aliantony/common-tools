package com.boot.leecode;
/**
 * @program common-tools
 * @description s1变换后能否成为s2
 * @author wq
 * created on 2020-07-28
 * @version  1.0.0
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(checkPermutation("uhwpykcr", "wcpcytuk"));
    }

    public static boolean checkPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int check1 = 0;
        int check2 = 0;
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            char c1 = s2.charAt(i);
            check1 |= 1 << c - 64;
            check2 |= 1 << c1 - 64;
        }
        return check1 == check2;
    }
}
