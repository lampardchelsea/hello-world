/**
Refer to
https://leetcode.com/problems/asteroid-collision/
We are given an array asteroids of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, 
negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are 
the same size, both will explode. Two asteroids moving in the same direction will never meet.

Example 1:
Input: asteroids = [5,10,-5]
Output: [5,10]
Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.

Example 2:
Input: asteroids = [8,-8]
Output: []
Explanation: The 8 and -8 collide exploding each other.

Example 3:
Input: asteroids = [10,2,-5]
Output: [10]
Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.

Example 4:
Input: asteroids = [-2,-1,1,2]
Output: [-2,-1,1,2]
Explanation: The -2 and -1 are moving left, while the 1 and 2 are moving right. Asteroids moving the same direction never meet, so no asteroids will meet each other.

Constraints:
2 <= asteroids.length <= 104
-1000 <= asteroids[i] <= 1000
asteroids[i] != 0
*/

// Wrong Solution, PASS:244/275
// Failed on Input [1,-2,-2,-2], Output [-2,-2], Expected [-2,-2,-2]
/**
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<Integer>();
        for(int a : asteroids) {
            if(stack.isEmpty()) {
                stack.push(a);
            } else {
                // Add flag test out by [-2,-2,1,-1]
                boolean flag = true;
                while(!stack.isEmpty()) {
                    if(stack.peek() > 0 && a < 0) {
                        if(stack.peek() <= Math.abs(a)) {
                            // Separately handle equal and non-equal case test out by [-2,-2,1,-2]
                            // Only 1 time pop out add break test out by [-2,1,1,-1]
                            if(stack.peek() == Math.abs(a)) {
                                flag = false;
                                stack.pop();
                                break;
                            } else {
                                stack.pop();
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if(!stack.isEmpty() && flag) {
                    if(stack.peek() * a > 0 || (stack.peek() < 0 && a > 0)) {
                        stack.push(a);
                    }
                }
            }
        }
        int n = stack.size();
        int i = n - 1;
        int[] result = new int[n];
        while(!stack.isEmpty()) {
            result[i--] = stack.pop();
        }
        return result;
    }
}


Input
[1,-2,-2,-2]
Output
[-2,-2]
Expected
[-2,-2,-2]
*/

// Solution 1: Category as always push positive, handle negative in different situation, similar to open / close brackets handling
// Refer to
// https://leetcode.com/problems/asteroid-collision/discuss/193403/Java-easy-to-understand-solution
/**
public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> s = new Stack<>();
        for(int i: asteroids){
            if(i > 0){
                s.push(i);
            }else{// i is negative
                while(!s.isEmpty() && s.peek() > 0 && s.peek() < Math.abs(i)){
                    s.pop();
                }
                if(s.isEmpty() || s.peek() < 0){
                    s.push(i);
                }else if(i + s.peek() == 0){
                    s.pop(); //equal
                }
            }
        }
        int[] res = new int[s.size()];   
        for(int i = res.length - 1; i >= 0; i--){
            res[i] = s.pop();
        }
        return res;
    }
*/
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<Integer>();
        for(int a : asteroids) {
            if(a > 0) {
                stack.push(a);
            } else {
                // There is only one case need to looply calculate collide case
                // current peek as positive and coming value as negative
                // Based on collide rules, have to pop out peek value if its less
                // than absolute value of coming negative
                while(!stack.isEmpty() && stack.peek() > 0 && stack.peek() < Math.abs(a)) {
                    stack.pop();
                }
                // After pop out all collided positive if stack becomes empty or 
                // stack peek is also negative we can push current negative
                if(stack.isEmpty() || stack.peek() < 0) {
                    stack.push(a);
                } else if(stack.peek() + a == 0) {
                    stack.pop(); // Handle equal case, e.g [8,-8]
                }
            }
        }
        int n = stack.size();
        int i = n - 1;
        int[] result = new int[n];
        while(!stack.isEmpty()) {
            result[i--] = stack.pop();
        }
        return result;
    }
}
