import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/summary-ranges/#/description
 * Given a sorted integer array without duplicates, return the summary of its ranges.
 * For example, given [0,1,2,4,5,7], return ["0->2","4->5","7"]. 
 *
 * Solution
 * https://segmentfault.com/a/1190000003768803
 * 双层迭代法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 外层的while循环控制每个range的起点，内层的while循环控制range之内的递增。每当遍历完一个range，
 * 就把它记录到结果中，并更新下一个range的起点。这里的技巧是，判断一个数是否是在range内的，只要
 * nums[start + range] - nums[start] = range就行了，即值之差等于下标之差。
 */
public class SummaryRange {
    public List<String> summaryRanges(int[] nums) {
        int len = nums.length;
        List<String> result = new ArrayList<String>();
        if(len == 0) {
            return result;
        }
        int start = 0;
        // Don't put the new StringBuilder here as problem on 
        // StringBuilder will not clear its previous content
        // automatically, e.g if put out of the while loop,
        // the problem will be like {0->2, 0->24->5, 7}, the
        // 2nd element which suppose to be 4->5 is data pollution
        // because of StringBuilder keep preivous result
//        StringBuilder sb = new StringBuilder();
        while(start < len) {
        	StringBuilder sb = new StringBuilder();
            int range = 1;
            while(start + range < len && nums[start + range] - nums[start] == range) {
                range++;
            }
            if(range == 1) {
                result.add(String.valueOf(nums[start]));
            } else {
                String temp = sb.append(nums[start]).append("->").append(nums[start + range - 1]).toString();
                result.add(temp);
            }
            start += range;
        }
        return result;
    }
    
    public static void main(String[] args) {
    	int[] nums = {0,1,2,4,5,7};
    	SummaryRange sr = new SummaryRange();
    	List<String> result = sr.summaryRanges(nums);
    	for(String s : result) {
    		System.out.println(s);
    	}
    }
}
