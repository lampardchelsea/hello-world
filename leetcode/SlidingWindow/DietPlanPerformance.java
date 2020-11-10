/**
 Refer to
 https://code.dennyzhang.com/diet-plan-performance
 A dieter consumes calories[i] calories on the i-th day. For every consecutive sequence of k days, they look at T, 
 the total calories consumed during that sequence of k days:

If T < lower, they performed poorly on their diet and lose 1 point;
If T > upper, they performed well on their diet and gain 1 point;
Otherwise, they performed normally and there is no change in points.
Return the total number of points the dieter has after all calories.length days.

Note that: The total points could be negative.

Example 1:
Input: calories = [1,2,3,4,5], k = 1, lower = 3, upper = 3
Output: 0
Explaination: calories[0], calories[1] < lower and calories[3], calories[4] > upper, total points = 0.

Example 2:
Input: calories = [3,2], k = 2, lower = 0, upper = 1
Output: 1
Explaination: calories[0] + calories[1] > upper, total points = 1.

Example 3:
Input: calories = [6,5,0,0], k = 2, lower = 1, upper = 5
Output: 0
Explaination: calories[0] + calories[1] > upper, calories[2] + calories[3] < lower, total points = 0.

Constraints:
1 <= k <= calories.length <= 10^5
0 <= calories[i] <= 20000
0 <= lower <= upper
*/

// Solution 1: Sliding Window
// Refer to
// https://medium.com/@leetcodesolver/sliding-window-738403b3bd1d
/**
The sample template which would be useful to solve the above three problems are :
windowSize - a variable that defines the size of the window
arr - array consisting the list of numbers
Element - represent the element of the array - it might be an integer, string, character (based on the array type)
for(int i = 0; i < arr.length; i++) {
  Element element = arr[i];
  if( i > windowSize - 1) { 
    // perform a series of actions as required by the problem.
    // remove the first element of the window which has been visited 
    // so that we could move ahead in the array without affecting      
    // the window size
  }
}

public int dietPlanPerformance(int[] calories, int k, int lower, int upper) {
        if(k > calories.length)
            return 0;
        int points = 0, sumCal = 0;
        for(int j = 0; j < calories.length; j++) {
            sumCal += calories[j];
            if(j >= k-1) {
                if(j > k-1) {
                    sumCal -= calories[j - k];
                }
                points += sumCal < lower ? -1 : 
                          ((sumCal > upper) ? 1 : 0);    
            }
        }
        return points;
    }
    
As we can see, we have followed the template to sum up the calories as we go and add in points based on the 
window size. The window size remains constant as we go further in the array.
*/
// The solution template is very similar to Find K-Length Substrings With No Repeated Characters
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/FindKLengthSubstringsWithNoRepeatedCharacters.java
class Solution {
    public int dietPlanPerformance(int[] calories, int k, int lower, int upper) {
        if(k > calories.length) {
            return 0;
        }
        int points = 0;
        int sumCal = 0;
        for(int i = 0; i < calories.length; i++) {
            // Add new
            sumCal += calories[i];
            // Remove old
            if(i >= k) {
                sumCal -= calories[i - k];
            }
            // Collect result when we have a range as length of k
            if(i >= k - 1) {
                if(sumCal < lower) {
                    points += -1;
                }
                if(sumCal > higher) {
                    points += 1;
                }
            }
        }
        return points;
    }
}
