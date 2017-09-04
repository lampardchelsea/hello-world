/** 
 * Refer to
 * http://www.lintcode.com/en/problem/remove-duplicates-from-sorted-array-ii/
 * Follow up for "Remove Duplicates":
    What if duplicates are allowed at most twice?

    For example,
    Given sorted array A = [1,1,1,2,2,3],

    Your function should return length = 5, and A is now [1,1,2,2,3].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/remove-duplicates-from-sorted-array-ii/
 * https://segmentfault.com/a/1190000003752035
*/
public class Solution {
    /**
     * @param A: a array of integers
     * @return : return an integer
     */
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0;
        int j;
        int cur = 0;
        while(i < nums.length) {
            int now = nums[i];
            for(j = i; j < nums.length; j++) {
                if(nums[j] != now) {
                    break;
                }
                if(j - i < 2) {
                    nums[cur++] = nums[i];
                }
            }
            i = j;
        }
        return cur;
    }
}
