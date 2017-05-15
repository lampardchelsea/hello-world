import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/majority-element-ii/#/description
 * Given an integer array of size n, find all elements that appear more than flooring(n/3) times. 
 * The algorithm should run in linear time and in O(1) space.
 * 
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/MajorityElementLinearTime.java
 * http://www.cnblogs.com/grandyang/p/4606822.html
 * 这道题让我们求出现次数大于n/3的众数，而且限定了时间和空间复杂度，那么就不能排序，也不能使用哈希表，这么
 * 苛刻的限制条件只有一种方法能解了，那就是摩尔投票法 Moore Voting，这种方法在之前那道题Majority Element 
 * 求众数中也使用了。题目中给了一条很重要的提示，让我们先考虑可能会有多少个众数，经过举了很多例子分析得出，
 * 任意一个数组出现次数大于n/3的众数最多有两个，具体的证明我就不会了，我也不是数学专业的。那么有了这个信息，
 * 我们使用投票法的核心是找出两个候选众数进行投票，需要两遍遍历，第一遍历找出两个候选众数，第二遍遍历重新
 * 投票验证这两个候选众数是否为众数即可，选候选众数方法和前面那篇Majority Element 求众数一样，由于之前
 * 那题题目中限定了一定会有众数存在，故而省略了验证候选众数的步骤，这道题却没有这种限定，即满足要求的众数
 * 可能不存在，所以要有验证
 * 
 * https://segmentfault.com/a/1190000003740925
 * 投票法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 上一题中，超过一半的数只可能有一个，所以我们只要投票出一个数就行了。而这题中，超过n/3的数最多可能有两个，
 * 所以我们要记录出现最多的两个数。同样的两个candidate和对应的两个counter，如果遍历时，某个候选数和到
 * 当前数相等，则给相应计数器加1。如果两个计数器都不为0，则两个计数器都被抵消掉1。如果某个计数器为0了，
 * 则将当前数替换相应的候选数，并将计数器初始化为1。最后我们还要遍历一遍数组，确定这两个出现最多的数，
 * 是否都是众数。
 */
public class MajorityElementII {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        int n = nums.length;
        if(n == 0) {
            return result;
        }
        int k = 3;
        // Setup two counters
        int c1 = 1;
        int c2 = 0;
        // Initial two candidates
        int n1 = nums[0];
        int n2 = 0;
        for(int i = 1; i < n; i++) {
            // If same value as one candidate, counter plus 1
            if(n1 == nums[i]) {
                c1++;
            } else if(n2 == nums[i]) {
                c2++;
            } else if(c1 != 0 && c2 != 0) {
                // If not same value for both candidates and
                // both candidates not equal to 0, minus 1
                // on each
                c1--;
                c2--;
            } else {
                // Reset candidates and counters if one counter
                // drop to 0
                if(c1 == 0) {
                    n1 = nums[i];
                    c1 = 1;
                } else {
                    n2 = nums[i];
                    c2 = 1;
                }
            }
        }
        // Verify if this is majority number, in Majority element I we
        // do not do this because it has assumption on must exist
        // majority value, but here no such assumption
        c1 = 0;
        c2 = 0;
        for(int i = 0; i < n; i++) {
            if(nums[i] == n1) {
                c1++;
            } else if(nums[i] == n2) {
                c2++;
            }
        }
        if(c1 > n/3) {
            result.add(n1);
        }
        if(c2 > n/3) {
            result.add(n2);
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    }
}

