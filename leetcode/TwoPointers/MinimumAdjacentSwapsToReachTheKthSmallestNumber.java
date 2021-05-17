/**
Refer to
https://leetcode.com/problems/minimum-adjacent-swaps-to-reach-the-kth-smallest-number/
You are given a string num, representing a large integer, and an integer k.

We call some integer wonderful if it is a permutation of the digits in num and is greater in value than num. 
There can be many wonderful integers. However, we only care about the smallest-valued ones.

For example, when num = "5489355142":
The 1st smallest wonderful integer is "5489355214".
The 2nd smallest wonderful integer is "5489355241".
The 3rd smallest wonderful integer is "5489355412".
The 4th smallest wonderful integer is "5489355421".
Return the minimum number of adjacent digit swaps that needs to be applied to num to reach the kth smallest wonderful integer.

The tests are generated in such a way that kth smallest wonderful integer exists.

Example 1:
Input: num = "5489355142", k = 4
Output: 2
Explanation: The 4th smallest wonderful number is "5489355421". To get this number:
- Swap index 7 with index 8: "5489355142" -> "5489355412"
- Swap index 8 with index 9: "5489355412" -> "5489355421"

Example 2:
Input: num = "11112", k = 4
Output: 4
Explanation: The 4th smallest wonderful number is "21111". To get this number:
- Swap index 3 with index 4: "11112" -> "11121"
- Swap index 2 with index 3: "11121" -> "11211"
- Swap index 1 with index 2: "11211" -> "12111"
- Swap index 0 with index 1: "12111" -> "21111"

Example 3:
Input: num = "00123", k = 1
Output: 1
Explanation: The 1st smallest wonderful number is "00132". To get this number:
- Swap index 3 with index 4: "00123" -> "00132"

Constraints:
2 <= num.length <= 1000
1 <= k <= 1000
num only consists of digits.
*/

// Solution 1: next_permutation + adjacent swap
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/NextPermutation.java
// https://leetcode.com/problems/minimum-adjacent-swaps-to-reach-the-kth-smallest-number/discuss/1186818/C%2B%2B-Detailed-explanation-using-next_permutation
/**
We will first find the next permutation of the number after k steps, i.e.

while(k--) 
     next_permutation(num.begin(), num.end());
num = 5489355142
k=4: 5489355214
k=3: 5489355241
k=2: 5489355412
k=1: 5489355421
k=0

And after this we have now two strings--
original - 5489355142 and new_string (num) - 5489355421

We can find the number of swaps (min) required to make original == new_string (Refer code below)

5489355142 vs 5489355421

i is pointing at '1' and j is pointing at '4', because s1[i] != s2[j].
We do j++ till they match, which bring j to '1' in s2 : 5489355421

now, we start swapping from j to i and get following results -

5489355421 (swap 1 with 2) : 5489355412 --> j is at 1
i!=j
so swap again;
5489355412 (swap 1 with 4) : 5489355142 --> j is at 1
i==j, so stop.

Do the same for rest of the string. No of swaps = No of steps required to make them equal.

class Solution {
public:
    int CountSteps(string s1, string s2) {
        int size = s1.length();
        int i = 0, j = 0;
        int result = 0;
  
        while (i < size) {
            j = i;
            while (s1[j] != s2[i]) j++;
			
            while (i < j) {
                swap(s1[j], s1[j-1]);
                j--;
                result++;
            }
            i++;
        }
        return result;
    }
    
    int getMinSwaps(string num, int k) {
        string orig = num;
        while(k--) {
            next_permutation(num.begin(), num.end());
        }
        return CountSteps(orig, num);
    }
};
How can we know that this is the min number of steps?

First, We are not swapping untill we are getting chars at their original position.

Second, Since we are bringing character to its original position (if it was not), that was the minimum effort (steps) 
we will have to put in since there is no other way of bringing it to original position with only adjacent swaps.

And now we can apply same thing to the rest of the string. Plus we are swapping end to start, which shifts characters 
towards the end, and ultimately close to their original position, because everything before that is already in their 
original position.
*/
class Solution {
    public int getMinSwaps(String num, int k) {
        char[] val = num.toCharArray();
        while(k-- > 0) {
            next_permutation(val);
        }
        String target = new String(val);
        return steps(num, target);
    }
    
    // e.g 5489355142 --> next permutation = 5489355214
    private void next_permutation(char[] val) {
        // Scan backwards to find first drop position
        // e.g 5489355142 first drop position at 1 for 2->4->1
        int i = val.length - 2;
        while(i >= 0 && val[i] >= val[i + 1]) {
            i--;
        }
        // Find smallest bigger digit after switch position
        // e.g 5489355142 we should switch 1 with 2
        // Also check i >= 0 to make sure drop position exist
        // otherwise its already the largest number based on
        // give digits
        if(i >= 0) {
            int j = val.length - 1;
            while(j > i && val[j] <= val[i]) {
                j--;
            }
            swap(val, i, j);
        }
        // Find smallest ascending sequence after drop position
        // after swap the two digits
        // e.g After swap 5489355142 become 5489355241, the smallest
        // sequence after '2' should change from "41" to "14"
        reverse(val, i + 1, val.length - 1);
    }
    
    // Calculate steps to convert 'num' into 'target'
    private int steps(String num, String target) {
        int count = 0;
        int i = 0;
        int j = 0;
        int n = num.length();
        while(i < n) {
            j = i;
            // Scan from left to right, find variant position
            // of same digit
            // e.g num = 5489355142, target = 5489355421
            // '1' at num index = 7, but at target index = 9
            // so when i = 7, j will increase to 9
            while(num.charAt(j) != target.charAt(i)) {
                j++;
            }
            while(i < j) {
                // In each round, only swap current digit at position
                // j with adjacent previous digit at position j - 1
                num = adjacent_swap(num.toCharArray(), j, j - 1);
                // Shift j back for 1 digit means finish one adjacent swap
                // and prepare for next round
                j--;
                count++;
            }
            i++;
        }
        return count;
    }
    
    private void reverse(char[] chars, int start, int end) {
        while(start < end) {
            swap(chars, start, end);
            start++;
            end--;
        }
    }
    
    private void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }
    
    private String adjacent_swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
        return new String(chars);
    }
}


