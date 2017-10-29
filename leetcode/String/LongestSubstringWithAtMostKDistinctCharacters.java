/**
 * Refer to
 * https://zhengyang2015.gitbooks.io/lintcode/longest_substring_with_at_most_k_distinct_characte.html
 * Given a string s, find the length of the longest substring T that contains at most k distinct characters.
    Example
    For example, Given s = "eceba", k = 3,
    T is "eceb" which its length is 4.
    Challenge
    O(n), n is the size of the string s.
 *
 * Solution
 * https://zhengyang2015.gitbooks.io/lintcode/longest_substring_with_at_most_k_distinct_characte.html
 * 用双指针解题。用一个hashmap记录遇到的字符及其出现次数。
   1. 指针left和right都从0开始，right向后遍历数组。
   2. 当right遇到字符是之前出现过的，则直接在hashmap中将其数量加1；若right遇到字符是之前没有出现过的，则分情况讨论
      1）若目前遇到过字符种类不足k个，则直接加入；
      2）若目前遇到过的字符种类大于k个，则移动左边界减少字符，直到字符种类少于k个后再加入。
   3. 每次改变左边界时更新max的值。但是最后一次当right到数组尾部时，这一次不会改变左边界，所以要在循环结束后最后一次更新max。
 *
 * https://discuss.leetcode.com/topic/41671/15-lines-java-solution-using-slide-window/2
*/
public class Solution {
    /**
     * @param s : A string
     * @return : The length of the longest substring 
     *           that contains at most k distinct characters.
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        
    }
}
