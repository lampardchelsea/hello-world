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




Attempt 1: 2022-09-21 (30min, too long to figure out corner case:  1. Only one element return directly 2. Set j <= len instead of j < len 3. Differ on continuous section and single element section)

```
class Solution { 
    public List<String> summaryRanges(int[] nums) { 
        List<String> result = new ArrayList<String>(); 
        int len = nums.length; 
        // Only one element we must directly return, otherwise duplicate calculate 
        // e.g If not return will calculate '-1' again 
        // Input: [-1] 
        // Output: ["-1","-1"] 
        // Expected: ["-1"] 
        if(len == 1) { 
            result.add(nums[0] + ""); 
            return result; 
        } 
        // 'i' to record each section start element index, 'j' to keep check next 
        // element 
        int i = 0; 
        // Set 'j <= len' rather than 'j < len' to make sure include last element, 
        // e.g  
        // if input as nums={0,1,2,4,5,7} and not include 'j == len' in for loop, 
        // will miss last element '7' 
        // ------------------- 
        // Round 1:  
        // i=0,j=1 -> nums[1]=nums[0]+1 -> j++ -> j=2 -> nums[2]=nums[1]+1 -> j++ -> j=3 ->  nums[3]!=nums[2]+1 -> while loop end 
        // j-i=3-0=3!=1 -> result.add(nums[0]+"->"+nums[3-1]) -> "0->2" 
        // i=j=3 
        // outside for loop increase j -> j++ -> j=4 
        // ------------------- 
        // Round 2: 
        // i=3,j=4 -> nums[4]=nums[3]+1 -> j++ -> j=5 -> nums[5]!=nums[4]+1 -> while loop end 
        // j-i=5-3=2!=1 -> result.add(nums[3]+"->"+nums[5-1]) -> "4->5" 
        // i=j=5 
        // outside for loop increase j -> j++ -> j=6 
        // ------------------- 
        // Round 3: 
        // j=6 > len -> outside for loop end, not able to collect last element "7" 
        // ------------------- 
        for(int j = 1; j <= len; j++) { 
            // Add 'j < len' in while loop condition to make sure nums[j] not out  
            // of boundary when 'j == len' 
            while(j < len && nums[j] == nums[j - 1] + 1) { 
                j++; 
            } 
            // If no continuous element (section start element index and end element 
            // index is only 1 means a single element find) then record single element 
            // If continuous element find, record with "->" 
            if(j - i == 1) { 
                result.add(nums[i] + ""); 
            } else { 
                result.add(nums[i] + "->" + nums[j - 1]); 
            } 
            i = j; 
        } 
        return result; 
    } 
}

Space Complexity: O(1)       
Time Complexity: O(n)
```

