/**
 Refer to
 https://leetcode.com/problems/jump-game-iv/
 Given an array of integers arr, you are initially positioned at the first index of the array.

In one step you can jump from index i to index:
i + 1 where: i + 1 < arr.length.
i - 1 where: i - 1 >= 0.
j where: arr[i] == arr[j] and i != j.
Return the minimum number of steps to reach the last index of the array.

Notice that you can not jump outside of the array at any time.

Example 1:
Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
Output: 3
Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.

Example 2:
Input: arr = [7]
Output: 0
Explanation: Start index is the last index. You don't need to jump.

Example 3:
Input: arr = [7,6,9,6,9,6,9,7]
Output: 1
Explanation: You can jump directly from index 0 to index 7 which is last index of the array.

Example 4:
Input: arr = [6,1,9]
Output: 2

Example 5:
Input: arr = [11,22,7,7,7,7,7,7,7,22,13]
Output: 3

Constraints:
1 <= arr.length <= 5 * 10^4
-10^8 <= arr[i] <= 10^8
*/

// Solution 1: BFS + list.clear() to do magic cut off traverse time from O(N^2) to O(N)
// Refer to
// https://leetcode.com/problems/jump-game-iv/discuss/502699/JavaC%2B%2B-BFS-Solution-Clean-code-O(N)
// https://leetcode.com/problems/jump-game-iv/discuss/502699/JavaC++-BFS-Solution-Clean-code-O(N)/445620
// https://stackoverflow.com/questions/6961356/list-clear-vs-list-new-arraylistinteger
/**
Refer to
https://leetcode.com/problems/jump-game-iv/discuss/502699/JavaC++-BFS-Solution-Clean-code-O(N)/445620
Expain Time O(N): In the case where each index has the same value, the algorithm goes to the neighbor (the same value) once then breaks all the edge by using: next.clear()
So the algorithm will traverse up to N edges for j and 2N edges for (i+1, i-1).
That's why time complexity is O(3N) ~ O(N)
*/
class Solution {
    public int minJumps(int[] arr) {
        int len = arr.length;
        if(len == 1) {
            return 0;
        }
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < arr.length; i++) {
            //List<Integer> list = map.getOrDefault(arr[i], new ArrayList<Integer>());
            //list.add(i);
            //map.put(arr[i], list);
            map.computeIfAbsent(arr[i], x -> new LinkedList<>()).add(i);
        }
        Set<Integer> visited = new HashSet<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(0);
        int step = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int curIndex = q.poll();
                if(curIndex == len - 1) {
                    return step;
                }
                int curVal = arr[curIndex];
                List<Integer> next = map.get(curVal);
                if(curIndex + 1 < len && !next.contains(curIndex + 1)) {
                    next.add(curIndex + 1);
                }
                if(curIndex - 1 >= 0 && !next.contains(curIndex - 1)) {
                    next.add(curIndex - 1);
                }
                for(int index : next) {
                    if(!visited.contains(index)) {
                        visited.add(index);
                        q.offer(index);
                    }
                }
                //next = new ArrayList<Integer>(); --> This will not work ?
                // Only clear() will help ?
                // Refer to
                // https://stackoverflow.com/questions/6961356/list-clear-vs-list-new-arraylistinteger
                // Refer to
                // https://leetcode.com/problems/jump-game-iv/discuss/502699/JavaC++-BFS-Solution-Clean-code-O(N)/445620
                /**
                 In the case where each index has the same value, I only go to the neighbor 
                 (the same value) once then I break all the edge by using: next.clear();.
                 So the algorithm will traverse up to N edges for jand 2N edges for (i+1, i-1).
                 That's why time complexity is O(N)
                */
                next.clear(); 
            }
            step++;
        }
        return -1;
    }
}
