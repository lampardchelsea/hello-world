/**
Refer to
https://leetcode.com/problems/find-the-winner-of-an-array-game/
Given an integer array arr of distinct integers and an integer k.

A game will be played between the first two elements of the array (i.e. arr[0] and arr[1]). In each round of the game, 
we compare arr[0] with arr[1], the larger integer wins and remains at position 0 and the smaller integer moves to the 
end of the array. The game ends when an integer wins k consecutive rounds.

Return the integer which will win the game.

It is guaranteed that there will be a winner of the game.

Example 1:
Input: arr = [2,1,3,5,4,6,7], k = 2
Output: 5
Explanation: Let's see the rounds of the game:
Round |       arr       | winner | win_count
  1   | [2,1,3,5,4,6,7] | 2      | 1
  2   | [2,3,5,4,6,7,1] | 3      | 1
  3   | [3,5,4,6,7,1,2] | 5      | 1
  4   | [5,4,6,7,1,2,3] | 5      | 2
So we can see that 4 rounds will be played and 5 is the winner because it wins 2 consecutive games.

Example 2:
Input: arr = [3,2,1], k = 10
Output: 3
Explanation: 3 will win the first 10 rounds consecutively.

Example 3:
Input: arr = [1,9,8,2,3,7,6,4,5], k = 7
Output: 9

Example 4:
Input: arr = [1,11,22,33,44,55,66,77,88,99], k = 1000000000
Output: 99

Constraints:
2 <= arr.length <= 10^5
1 <= arr[i] <= 10^6
arr contains distinct integers.
1 <= k <= 10^9
*/

// Solution 1: Use LinkedList to simulate the process
// Note: Use index to identify if the first element changed or keep there
class Solution {
    public int getWinner(int[] arr, int k) {
        int len = arr.length;
        LinkedList<int[]> q = new LinkedList<int[]>();
        int max = 0;
        for(int i = 0; i < arr.length; i++) {
            q.add(new int[] {arr[i], i}); // {num, pre_index}
            max = Math.max(max, arr[i]);
        }
        int win_count = 0;
        while(win_count < k) {
            int[] first = q.removeFirst();
            int[] second = q.removeFirst();
            // Test case: arr = [1,11,22,33,44,55,66,77,88,99], k =1000000000
            // in case k very large, if after certain move the maximum element
            // already at index = 0, then no need additional rounds required,
            // and we actually ignore k's value, this code will resolve TLE
            if(first[0] == max) {
                return first[0];
            }
            if(first[0] > second[0]) {
                // Since current first element's value larger, if its position
                // still at 0, which means no change on winner, count increase,
                // if its index not as 0, which means it previously as the
                // winner between second and third element and now as new first
                // element so its index won't be 0, count reset to 1
                if(first[1] == 0) {
                    win_count++;
                } else {
                    win_count = 1;
                }
                // If winner count equal to k game finish
                if(win_count == k) {
                    return first[0];
                }
                // Add winner as first element and record its current index 0
                q.addFirst(new int[] {first[0], 0});
                // Add loser as last element and update its index to len - 1
                // Note: actually after certain rounds except first element
                // all other element's index will set to len - 1
                q.addLast(new int[] {second[0], len - 1});
            } else {
                if(second[1] == 0) {
                    win_count++;
                } else {
                    win_count = 1;
                }
                if(win_count == k) {
                    return second[0];
                }
                q.addFirst(new int[] {second[0], 0});
                q.addLast(new int[] {first[0], len - 1});               
            }
        }
        return 0;
    }
}

// Solution 2: One Pass, O(1) Space without Queue simulation
// Refer to
// https://leetcode.com/problems/find-the-winner-of-an-array-game/discuss/768007/JavaC%2B%2BPython-One-Pass-O(1)-Space
/**
Intuition
Don't need a queue or push the elements.

If you don't find the winner after one pass,
the winner will be max(A),
bacause no one will beats him anymore.

Explanation
cur is the current winner, wich is the current biggest element.
win is the number of games it has won.
If max(A) conpetes, it will be the winner.

Complexity
Time O(N)
Space O(1)

Java:
    public int getWinner(int[] A, int k) {
        int cur = A[0], win = 0;
        for (int i = 1; i < A.length; ++i) {
            if (A[i] > cur) {
                cur = A[i];
                win = 0;
            }
            if (++win == k) break;
        }
        return cur;
    }
*/
class Solution {
    public int getWinner(int[] arr, int k) {
        int win_count = 0;
        int cur = arr[0];
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] > cur) {
                cur = arr[i];
                win_count = 1;
            } else {
                win_count++;
            }
            if(win_count == k) {
                break;
            }
        }
        return cur;
    }
}
