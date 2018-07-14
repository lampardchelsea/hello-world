/**
 * Refer to
 * leetcode.com/problems/majority-element-ii/discuss/63500/JAVA-Easy-Version-To-Understand!!!!!!!!!!!!/64926
 * What if the frquency becomes 5/n or 6/n or...? Then do we need to define 5 or 6 counts? Is there a more applicative way?
 * 
 * Solution
 * leetcode.com/problems/majority-element-ii/discuss/63500/JAVA-Easy-Version-To-Understand!!!!!!!!!!!!/64925
*/
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        int k = 3;
        int[] majors = new int[k - 1];
        int[] counts = new int[k - 1];
        Arrays.fill(majors, nums[0]);
        Arrays.fill(counts, 0);
        for(int i = 0; i < nums.length; i++) {
            // Refer to
            // leetcode.com/problems/majority-element-ii/discuss/63500/JAVA-Easy-Version-To-Understand!!!!!!!!!!!!/145303
            // This boolean flag is same way as 'else if' to avoid duplicate
            // assignment on multiple majors, will only update 1 major
            // ---------------------------------------------------------
            // Below section same effort as
            // if(nums[i] == major1) {
            //     count1++;
            // } else if(nums[i] == major2) {
            //     count2++;
            // }
            boolean settled = false; 
            for(int j = 0; j < k - 1; j++) {
                if(nums[i] == majors[j]) {
                    counts[j]++;
                    settled = true;
                    break;
                }
            }
            // If already assign 1 major, skip to next round
            if(settled) {
                continue;
            }
            // ---------------------------------------------------------
            // ---------------------------------------------------------
            // Below section same effort as
            // if(count1 == 0) {
            //     major1 = nums[i];
            //     count1++;
            // } else if(count2 == 0) {
            //     major2 = nums[i];
            //     count2++;
            // }
            for(int j = 0; j < k - 1; j++) {
                if(counts[j] == 0) {
                    majors[j] = nums[i];
                    counts[j]++;
                    settled = true;
                    break;
                }
            }
            if(settled) {
                continue;
            }
            // ---------------------------------------------------------
            // ---------------------------------------------------------
            // Below section same effort as
            // {
            //     count1--;
            //     count2--;
            // }
            for(int j = 0; j < k - 1; j++) {
                counts[j] = counts[j] > 0 ? (counts[j] - 1) : 0;
            }
        }
        Arrays.fill(counts, 0);
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j < k - 1; j++) {
                if(majors[j] == nums[i]) {
                    counts[j]++;
                    break;
                }
            }
        }
        for(int i = 0; i < k - 1; i++) {
            if(counts[i] > nums.length / k) {
                result.add(majors[i]);
            } 
        }
        return result;
    }
}
