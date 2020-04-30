package com.huawei;

import java.math.BigDecimal;

// 输入一个 10进制小数 M
// 0<M<1， 转换成 N 进制小数，小数点后保留10位小数。
// e.g 输入  0.75 2
// 输出  0.11

public class MtoNConverter {
    public static void main(String[] args) {
        // case 1
        String decimalNumber = "0.7";
        int n = 3;
        System.out.println(convert(decimalNumber, n));
        // case 2
        decimalNumber = "0.75";
        n = 2;
        System.out.println(convert(decimalNumber, n));

    }
    public static String convert(String decimalNumber, int n) {
        if (decimalNumber.equals("0") && n == 0) {
            return "";
        }
        BigDecimal targetSum = new BigDecimal(decimalNumber);
        BigDecimal sumByNow = new BigDecimal(0);
        StringBuilder resultNumber = new StringBuilder("0.");

        for (int i = -1; i >= -10; i--) {
            for (int j = n - 1; j >= 0; j--) {
                BigDecimal currentSum = sumByNow.add(new BigDecimal(j * Math.pow(n, i)));
                if (currentSum.compareTo(targetSum) < 0) {
                    sumByNow = new BigDecimal(currentSum.toString());
                    resultNumber.append(j);
                    break;
                }
            }
        }
        return resultNumber.toString();

    }
}
