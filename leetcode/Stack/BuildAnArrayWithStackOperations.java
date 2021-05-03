/**
Refer to
https://leetcode.com/problems/build-an-array-with-stack-operations/
Given an array target and an integer n. In each iteration, you will read a number from  list = {1,2,3..., n}.

Build the target array using the following operations:

Push: Read a new element from the beginning list, and push it in the array.
Pop: delete the last element of the array.
If the target array is already built, stop reading more elements.
Return the operations to build the target array. You are guaranteed that the answer is unique.

Example 1:
Input: target = [1,3], n = 3
Output: ["Push","Push","Pop","Push"]
Explanation: 
Read number 1 and automatically push in the array -> [1]
Read number 2 and automatically push in the array then Pop it -> [1]
Read number 3 and automatically push in the array -> [1,3]

Example 2:
Input: target = [1,2,3], n = 3
Output: ["Push","Push","Push"]

Example 3:
Input: target = [1,2], n = 4
Output: ["Push","Push"]
Explanation: You only need to read the first 2 numbers and stop.

Example 4:
Input: target = [2,3,4], n = 4
Output: ["Push","Pop","Push","Push","Push"]

Constraints:
1 <= target.length <= 100
1 <= target[i] <= n
1 <= n <= 100
target is strictly increasing.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/build-an-array-with-stack-operations/discuss/624278/Java-Simple-Check
// https://leetcode.com/problems/build-an-array-with-stack-operations/discuss/624278/Java-Simple-Check/768804
/**
public List<String> buildArray(int[] target, int n) {
    List<String> res = new ArrayList<>();
    int curr = 1;
        
    for(int i = 0; i < target.length; i++){
        res.add("Push");//have to push every time

        if(target[i] != curr){//means we have to pop
            res.add("Pop");
            i--;//also don't move to next element in the array
        }
        curr++;
    }
    return res;
}
*/
class Solution {
    public List<String> buildArray(int[] target, int n) {
        List<String> result = new ArrayList<String>();
        int cur = 1;
        for(int i = 0; i < target.length && cur <= n; i++) {
            result.add("Push");
            if(target[i] != cur) {
                result.add("Pop");
                // The i-- used here to simulate the same operation to "pop out"
                // current value 'target[i]' which suppose to match 'cur' but
                // actually not exist, the effect is anchore target[i] since
                // here i--, later next loop i++, target[i] no change, and continue
                // increase 'cur' to compare again later
                i--;
            }
            cur++;
        }
        return result;
    }
}


