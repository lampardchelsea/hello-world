/**
  * Refer to
  * https://leetcode.com/problems/maximum-gap/description/
  * Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

    Try to solve it in linear time/space.

    Return 0 if the array contains less than 2 elements.

    You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.
  *
  * Solution
  * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space
  * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space/51251
  * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space/51216
*/
class Solution {
    /**
     * Refer to
     * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space
     * Suppose there are N elements in the array, the min value is min and the max value is max. 
       Then the maximum gap will be no smaller than ceiling[(max - min ) / (N - 1)].
       Let gap = ceiling[(max - min ) / (N - 1)]. We divide all numbers in the array into n-1 buckets, 
       where k-th bucket contains all numbers in [min + (k-1)gap, min + k*gap). Since there are n-2 
       numbers that are not equal min or max and there are n-1 buckets, at least one of the buckets 
       are empty. We only need to store the largest number and the smallest number in each bucket.
       After we put all the numbers into the buckets. We can scan the buckets sequentially and get the max gap.
       
     * 
     * Refer to
     * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space/51251
     * Thanks for the nice solution. for people reading this, maybe this would help understanding how it works: 
       basically we argue that the largest gap can not be smaller than (max-min)/(N-1), so if we make the buckets 
       smaller than this number, any gaps within the same bucket is not the amount we are looking for, so we are 
       safe to look only for the inter-bucket gaps.
       so making the buckets smaller doesn’t affect the correctness. for safety we might just as well use (max-min)/2N
    */
    public int maximumGap(int[] nums) {
        if(nums == null || nums.length < 2) {
            return 0;
        }
        // get the max and min value of the array
        int min = nums[0];
        int max = nums[0];
        for(int i : nums) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }
        // the minimum possibale gap, ceiling of the integer division
        int gap = (int)Math.ceil((double)(max - min) / (nums.length - 1));
        int[] bucketsMIN = new int[nums.length - 1]; // store the min value in that bucket
        int[] bucketsMAX = new int[nums.length - 1]; // store the max value in that bucket
        Arrays.fill(bucketsMIN, Integer.MAX_VALUE);
        Arrays.fill(bucketsMAX, Integer.MIN_VALUE);
        // put numbers into buckets
        for(int i : nums) {
            if(i == min || i == max) {
                continue;
            }
            int idx = (i - min) / gap; // index of the right position in the buckets
            bucketsMIN[idx] = Math.min(i, bucketsMIN[idx]);
            bucketsMAX[idx] = Math.max(i, bucketsMAX[idx]);
        }
        // Scan the buckets for the max gap
        /**
         * Refer to
         * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space/51216
         * For example, we set the bucket size to be 10, those numbers can be put into above buckets. 
           If we set the bucket size clever(relatively small), we can ensure that the max gap cannot 
           be in same bucket. In worst case each successive numbers have same gap. For example, we 
           have 1, 3, 5 the gap and max gap is (5 - 1) / 2. Largest - Smallest, two gaps.
           Based on this, we only need to compare max number in this bucket and min number in next 
           bucket to get the relatively large gap and find out which two bucket have the largest gap
        */
        int maxGap = Integer.MIN_VALUE;
        int previous = min;
        for(int i = 0; i < nums.length - 1; i++) {
            if(bucketsMIN[i] == Integer.MAX_VALUE && bucketsMAX[i] == Integer.MIN_VALUE) {
                // empty bucket
                continue;
            }
            // min value minus the previous value is the current gap
            maxGap = Math.max(maxGap, bucketsMIN[i] - previous);
            // update previous bucket value
            previous = bucketsMAX[i];
        }
        maxGap = Math.max(maxGap, max - previous); // updata the final max value gap
        return maxGap;
    }
}
