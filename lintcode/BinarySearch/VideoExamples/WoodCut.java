/**
 * Refer to
 * http://www.lintcode.com/en/problem/wood-cut/
 * Given n pieces of wood with length L[i] (integer array). Cut them into small 
 * pieces to guarantee you could have equal or more than k pieces with the same 
 * length. What is the longest length you can get from the n pieces of wood? 
 * Given L & k, return the maximum length of the small pieces.

	 Notice
	
	You couldn't cut wood into float length.
	
	If you couldn't get >= k pieces, return 0.
	
	Have you met this question in a real interview? Yes
	Example
	For L=[232, 124, 456], k=7, return 114.
 * 
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/wood-cut/
  * The template below
    int start = 1, end = max // 1. 找到可行解范围
    while(start + 1 < end) {
        int mid = start + (end - start) / 2; // 2.猜答案
      if(check(mid)) {  // 3.检验答案
          start = mid; // 4. 调查搜索范围 
      } else {
          end = mid; // 4. 调查搜索范围
      }
    }
 */
public class WoodCut {
	/*
     * @param L: Given n pieces of wood with length L[i]
     * @param k: An integer
     * @return: The maximum length of the small pieces
     */
    public int woodCut(int[] L, int k) {
        int max = 0;
        for(int i = 0; i < L.length; i++) {
            max = Math.max(max, L[i]);
        }
        int start = 1;
        int end = max;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(count(mid, L) < k) {
                end = mid;
            } else if(count(mid, L) >= k) {
                start = mid;
            }
        }
        if(count(end, L) >= k) {
            return end;
        } else if(count(start, L) >= k){
            return start;
        }
        // Caution: Don't forget about case either 'start' or
        // 'end' couldn't get >= k pieces, return 0
        return 0;
    }
    
    private int count(int val, int[] L) {
        int count = 0;
        for(int i = 0; i < L.length; i++) {
            count += L[i] / val;
        }
        return count;
    }
}
