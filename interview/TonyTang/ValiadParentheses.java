package com.huawei;

import java.util.Stack;

// 有效括号和其中字符的长度
// 以下三个 全为6

public class ValiadParentheses {
    public static void main(String[] args) {
        System.out.println(validLength("()()((00))"));
        System.out.println(validLength("()(((00))"));
        System.out.println(validLength("abcd1234()((00))"));
    }
    public static int validLength(String s) {
        char[] arr = s.toCharArray();
        Stack < String > st = new Stack < String > ();
        int maxValidLength = 0;
        int validCount = 0;
        int continousCharCount = 0;
        char lastChar = ' ';
        for (char c: arr) {
            if (c == '(') {
                if (!st.isEmpty()) {
                    st.add(continousCharCount + "");
                }
                st.add(c + "");
                continousCharCount = 0;
                validCount = 0;
                maxValidLength = Math.max(validCount, maxValidLength);
            } else if (c == ')') {
                validCount++;
                if (st.isEmpty()) { // 没有 match 的左括号
                    validCount = 0;
                    continue;
                }
                if (lastChar != ')') { // 如果是第一个右括号要加上数字
                    st.add(continousCharCount + "");
                }
                validCount = validCount + Integer.parseInt(st.pop()); // 这样栈中，第一个 是有效 字符的数目
                st.pop(); // will be "("
                validCount++;

            } else {
                continousCharCount++;
            }
            lastChar = c;
        }
        maxValidLength = Math.max(validCount, maxValidLength);
        return maxValidLength;
    }
}
