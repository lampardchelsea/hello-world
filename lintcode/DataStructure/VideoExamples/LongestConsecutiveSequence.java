/**
 * Refer to
 * http://www.lintcode.com/en/problem/longest-consecutive-sequence/
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
    Have you met this question in a real interview? Yes
    Clarification
    Your algorithm should run in O(n) complexity.

    Example
    Given [100, 4, 200, 1, 3, 2],
    The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
 *
 * Solution
 * http://www.cnblogs.com/springfor/p/3869981.html
 * 这道题利用HashSet的唯一性解决，能使时间复杂度达到O(n)。首先先把所有num值放入HashSet，
   然后遍历整个数组，如果HashSet中存在该值，就先向下找到边界，找的同时把找到的值一个一个
   从set中删去，然后再向上找边界，同样要把找到的值都从set中删掉。所以每个元素最多会被遍
   历两边，时间复杂度为O(n)。
*/
public class Solution {
    /*
     * @param num: A list of integers
     * @return: An integer
     */
    public int longestConsecutive(int[] num) {
        if(num == null||num.length == 0)
            return 0;
        
        HashSet<Integer> hs = new HashSet<Integer>();  
        
        for (int i = 0 ;i<num.length; i++)   
            hs.add(num[i]);  
         
        int max = 0;  
        for(int i=0; i<num.length; i++){  
            if(hs.contains(num[i])){
                int count = 1;  
                hs.remove(num[i]);
                
                int low = num[i] - 1; 
                while(hs.contains(low)){  
                    hs.remove(low);  
                    low--;  
                    count++;  
                }
                
                int high = num[i] + 1;  
                while(hs.contains(high)){  
                    hs.remove(high);  
                    high++;  
                    count++;  
                }  
                max = Math.max(max, count);  
            }  
        }  
        return max;
    }
}
