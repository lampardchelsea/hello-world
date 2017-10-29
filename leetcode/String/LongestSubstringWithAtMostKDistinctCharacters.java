/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5351347.html
 * Given a string, find the length of the longest substring T that contains at most k distinct characters.
   For example, Given s = “eceba” and k = 2,
   T is "ece" which its length is 3.
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
   A more generic solution as follows, can be solution for Unicode string:

      public int lengthOfLongestSubstringKDistinct(String s, int k) {
          Map<Character, Integer> map = new HashMap<>();
          int left = 0;
          int best = 0;
          for(int i = 0; i < s.length(); i++) {
              char c = s.charAt(i);
              map.put(c, map.getOrDefault(c, 0) + 1);
              while (map.size() > k) {
                  char leftChar = s.charAt(left);
                  if (map.containsKey(leftChar)) {
                      map.put(leftChar, map.get(leftChar) - 1);                     
                      if (map.get(leftChar) == 0) { 
                          map.remove(leftChar);
                      }
                  }
                  left++;
              }
              best = Math.max(best, i - left + 1);
          }
          return best;
      } 
*/
public class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        
    }
}
