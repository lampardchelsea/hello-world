/**
 * Write an algorithm to determine if a number is "happy".
 * A happy number is a number defined by the following process: Starting with any positive integer, 
 * replace the number by the sum of the squares of its digits, and repeat the process until the 
 * number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. 
 * Those numbers for which this process ends in 1 are happy numbers.
 * Example: 19 is a happy number
    12 + 92 = 82
    82 + 22 = 68
    62 + 82 = 100
    12 + 02 + 02 = 1
*/

//
// Refer to
// https://segmentfault.com/a/1190000003481340
//
// 集合法
// 复杂度
// 时间 待定 空间 待定
// 思路
// 根据快乐数的计算方法，我们很难在有限步骤内确定一个数是否是快乐数，但使用排除法的话，我们可以尝试确定一个数不是快乐数。
// 根据题意，当计算出现无限循环的时候就不是快乐数。出现无限循环的说明产生了相同的结果，而判断相同结果只要用Set就行了。

