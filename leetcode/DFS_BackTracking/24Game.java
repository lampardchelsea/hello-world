/**
 Refer to
 https://leetcode.com/problems/24-game/
 You have 4 cards each containing a number from 1 to 9. You need to judge whether they could 
 operated through *, /, +, -, (, ) to get the value of 24.

Example 1:
Input: [4, 1, 8, 7]
Output: True
Explanation: (8-4) * (7-1) = 24

Example 2:
Input: [1, 2, 1, 2]
Output: False

Note:
The division operator / represents real division, not integer division. 
For example, 4 / (1 - 2/3) = 12.
Every operation done is between two numbers. In particular, we cannot use - as 
a unary operator. For example, with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed.
You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], 
we cannot write this as 12 + 12.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/24-game/discuss/107685/679.-24-Game-C%2B%2B-Recursive
// https://www.cnblogs.com/grandyang/p/8395062.html
/**
 来看一种很不同的递归写法，这里将加减乘除操作符放到了一个数组ops中。并且没有用全局变量res，
 而是让递归函数带有bool型返回值。在递归函数中，还是要先看nums数组的长度，如果为1了，
 说明已经计算完成，直接看结果是否为0就行了。然后遍历任意两个数字，注意这里的i和j都分别
 从0到了数组长度，而上面解法的j是从0到i，这是因为上面解法将p - q, q - p, q / q, q / p都
 分别列出来了，而这里仅仅是nums[i] - nums[j], nums[i] / nums[j]，所以i和j要交换位置，
 但是为了避免加法和乘法的重复计算，我们可以做个判断，还有别忘记了除数不为零的判断，i和j不
 能相同的判断。我们建立一个临时数组t，将非i和j位置的数字都加入t，然后遍历操作符数组ops，
 每次取出一个操作符，然后将nums[i]和nums[j]的计算结果加入t，调用递归函数，如果递归函数
 返回true了，那么就直接返回true。否则移除刚加入的结果，还原t的状态
*/
// Why we don't need to care about braces ?
// Refer to
// https://leetcode.com/problems/24-game/discuss/112544/Backtracking-beats-95.29-Java
/**
 Apparently, there are limited number of combinations for cards and operators (+-*/()). 
 One idea is to search among all the possible combinations. This is what backtracking does.

Note that () play no role in this question. Say, parentheses give some operators a 
higher priority to be computed. However, the following algorithm has already considered 
priorities, thus it's of no use to take parentheses into account anymore.

arr contains value for cards and vis[i] indicates whether arr[i] has been used or not. 
Every time select 2 un-used cards arr[i] and arr[j]. Calculate the answer for arr[i] 
and arr[j] with some operator, update arr[i] with this new value and mark arr[j] as visited. 
Now we have 1 less card available. Note that we should use that new value (new arr[i]) 
in the future, thus we should NOT mark arr[i] as visited. When there is no card available, 
check whether the answer is 24 or not.

Since each time after we select 2 cards arr[i] and arr[j], we just do the calculation 
without considering the priorities for operators we use, we could think that we have 
already added a pair of () for arr[i] OPERATOR arr[j]. This contains all the possible 
considerations, thus we do not need to consider parentheses anymore.
*/
class Solution {
    double elipson = 0.001;
    char[] ops = new char[]{'+','-','*','/'};
    public boolean judgePoint24(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        List<Double> list = new ArrayList<Double>();
        for(int num : nums) {
            list.add(1.0 * num);
        }
        return helper(list);
    }
    
    private boolean helper(List<Double> list) {
        if(list.size() == 1) {
            return Math.abs(list.get(0) - 24) <= elipson;
        }
        for(int i = 0; i < list.size(); i++) {
            for(int j = 0; j < list.size(); j++) {
                if(i == j) {
                    continue;
                }
                List<Double> newList = new ArrayList<Double>();
                for(int k = 0; k < list.size(); k++) {
                    if(k != i && k != j) {
                        newList.add(list.get(k));
                    }
                }
                double a = list.get(i);
                double b = list.get(j);
                for(char c : ops) {
                    if((c == '+' || c == '*') && i > j) {
                        continue;
                    }
                    if(c == '/' && b == 0.0) {
                        continue;
                    }
                    if(c == '+') {
                        newList.add(a + b);
                    } else if(c == '-') {
                        newList.add(a - b);
                    } else if(c == '*') {
                        newList.add(a * b);
                    } else if(c == '/') {
                        newList.add(a / b);
                    }
                    if(helper(newList)) {
                        return true;
                    }
                    newList.remove(newList.size() - 1);
                }
            }
        }
        return false;
    }
}
