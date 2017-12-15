/**
 * Refer to
 * https://leetcode.com/problems/largest-number/description/
 * Given a list of non negative integers, arrange them such that they form the largest number.
    For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
    Note: The result may be very large, so you need to return a string instead of an integer.
 *
 *
 * Solution
 * https://www.youtube.com/watch?v=3Ku0hxpASJ4
 * https://discuss.leetcode.com/topic/8018/my-java-solution-to-share
 * The idea here is basically implement a String comparator to decide which String should come   
   first during concatenation. Because when you have 2 numbers (let's convert them into String), 
   you'll face only 2 cases:
    For example:

    String s1 = "9";
    String s2 = "31";

    String case1 =  s1 + s2; // 931
    String case2 = s2 + s1; // 319

    Apparently, case1 is greater than case2 in terms of value.
    So, we should always put s1 in front of s2.
*/
class Solution {
    public String largestNumber(int[] nums) {
        if(nums == null || nums.length == 0) {
            return null;
        }
        String[] result = new String[nums.length];
        for(int i = 0; i < nums.length; i++) {
            result[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(result, new Comparator<String>() {
             public int compare(String a, String b) {
                 String r1 = a + b;
                 String r2 = b + a;
                 return r2.compareTo(r1);
             } 
        });
        // In case of input as [0, 0], we should keep as 0 to return
        if(result[0].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < result.length; i++) {
            sb.append(result[i]);
        }
        return sb.toString();
    }
}
