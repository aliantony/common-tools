package com.boot.leecode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program common-tools
 * @description s1变换后能否成为s2
 * @author wq
 * created on 2020-07-28
 * @version  1.0.0
 */
public class Solution2 {
    public static void main(String[] args) {
        System.out.println(test("uh wp ", 6));
    }

    public static String test(String S, int length) {
        int j = 0;
        Pattern p = Pattern.compile("(\\s)");
        Matcher m = p.matcher(S);
        while(m.find()) {
            j++;
        }
        char[] newa = new char[length + j * 2];
        int k = 0;
        for (int i = 0; i < length ; i++) {
            char c = S.charAt(i);
            if (c == ' ') {
                newa[k++] = '%';
                newa[k++] = '2';
                newa[k++] = '0';
            } else {
                newa[k++] = c;
            }
        }
        String s = new String(newa);
        return s.trim();
    }
}
