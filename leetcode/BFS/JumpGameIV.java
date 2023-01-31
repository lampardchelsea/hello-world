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

// Solution 1: BFS + list.clear() to do magic cut down traverse time from O(N^2) to O(N)
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
                //next = new ArrayList<Integer>(); --> This will not work ? TLE on 50000 len arary with 49999's 7 and last one is 11 test case
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






































































https://leetcode.com/problems/jump-game-iv/description/

Given an array of integers arr, you are initially positioned at the first index of the array.

In one step you can jump from index i to index:

- i + 1 where: i + 1 < arr.length.
- i - 1 where: i - 1 >= 0.
- j where: arr[i] == arr[j] and i != j.
Return the minimum number of steps to reach the last index of the array.

Notice that you can not jump outside of the array at any time.

Example 1:
```
Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
Output: 3
Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
```

Example 2:
```
Input: arr = [7]
Output: 0
Explanation: Start index is the last index. You do not need to jump.
```

Example 3:
```
Input: arr = [7,6,9,6,9,6,9,7]
Output: 1
Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
```

Constraints:
- 1 <= arr.length <= 5 * 104
- -108 <= arr[i] <= 108
---
Attempt 1: 2023-01-28

Solution 1: DFS + Memoization (360 min, TLE, DO NOT use Memoization to return early)
```
class Solution { 
    public int minJumps(int[] arr) { 
        boolean[] visited = new boolean[arr.length]; 
        visited[0] = true; 
        // Even cannot directly return memoized result in recursion helper method 
        // like other DFS memoization problem since it requires to return minimum  
        // number of steps ==> 
        // Then why we still have to setup memo array and initialize all elements  
        // with the Integer.MAX_VALUE ? 
        // Refer to 
        // https://leetcode.com/problems/jump-game-iv/solutions/1694469/why-dp-memoization-is-not-working/comments/1229622 
        int[] memo = new int[arr.length]; 
        Arrays.fill(memo, Integer.MAX_VALUE); 
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>(); 
        for(int i = 0; i < arr.length; i++) { 
            //if (!map.containsKey(arr[i])) { 
            //    map.put(arr[i], new ArrayList<Integer>()); 
            //} 
            map.putIfAbsent(arr[i], new ArrayList<Integer>()); 
            map.get(arr[i]).add(i); 
        } 
        return helper(arr, 0, visited, map, memo); 
    } 
    // Why DFS + Memoization does not work, when all of the following 3 dependency exists: 
    // - forward dependency, i.e., mem[start] depends on mem[start + 1]; 
    // - backward dependency, i.e., mem[start] depends on mem[start - 1]; 
    // - jump dependency, i.e., mem[start] depends on mem[x]; 
    // You mark an index visited and return the value of that index if it was visited in past 
    // but, that value may not be optimal (shortest) in DFS because DFS traverses nodes in random 
    // order, and we only cache the first result it finds. 
    // So we have to use backtracking to fully roll out all possibilities 
    private int helper(int[] arr, int index, boolean[] visited, Map<Integer, List<Integer>> map, int[] memo) { 
        // Base case 
        if(index == arr.length - 1) { 
            return 0; 
        } 
        // No memoization check because it won't give optimal result always 
        int min_steps = Integer.MAX_VALUE; 
        // Case 1: jump to i + 1 where: i + 1 < arr.length 
        if(index + 1 < arr.length && !visited[index + 1]) { 
            visited[index + 1] = true; 
            min_steps = Math.min(min_steps, helper(arr, index + 1, visited, map, memo) + 1); 
            // backtrack: you need to do this for trying (index - 1) and same number indices 
            visited[index + 1] = false; 
        } 
        // Case 2: jump to i - 1 where: i - 1 >= 0 
        if(index - 1 >= 0 && !visited[index - 1]) { 
            visited[index - 1] = true; 
            min_steps = Math.min(min_steps, helper(arr, index - 1, visited, map, memo) + 1); 
            // backtrack: you need to do this for trying (index - 1) and same number indices 
            visited[index - 1] = false; 
        } 
        // Case 3: jump to j where: arr[i] == arr[j] and i != j 
        for(int idx : map.get(arr[index])) { 
            if(idx != index && !visited[idx]) { 
                visited[idx] = true; 
                min_steps = Math.min(min_steps, helper(arr, idx, visited, map, memo) + 1); 
                visited[idx] = false; 
            } 
        } 
        memo[index] = Math.min(min_steps, memo[index]); 
        return memo[index]; 
    } 
}
```

Refer to
https://leetcode.com/problems/jump-game-iv/solutions/1224573/c-dfs-tle-bfs-accepted-easy-comments-why-dfs-memo-won-t-work/
```
DFS+Memoization does not work, when all of the following 3 dependency exists:  
  - forward dependency, i.e., mem[start] depends on mem[start + 1]; 
  - backward dependncy, i.e., mem[start] depends on mem[start - 1]; 
  - jump dependency, i.e., mem[start] depends on mem[x]; 
You mark an index visited and return the value of that index if it was visited in past 
but, that value may not be optimal (shotest) in DFS because DFS traverses nodes in random     
order, and we only cache the first result it finds. 
Why BFS ? 
With BFS you can stop as soon as you reach the goal. With DFS you might reach the goal (or memoize any result) via a sub-optimal path. 
To be sure you get the optimal solution with DFS, you can't use memoization because you might still find a shorter path at any point in the future.

//Approach-1 (DFS - TLE) 
class Solution { 
public: 
    vector<bool> visited; 
    vector<int> t; 
    unordered_map<int, vector<int>> mp; 
    int solve_DFS(vector<int>& arr, int idx) { 
        //reached destination 
        if(idx == arr.size()-1) 
            return 0; 
         
		//No memoization check because it won't give optimal result always 
        int min_step = INT_MAX; 
        // jump to idx + 1 
        if (idx + 1 < arr.size() && !visited[idx+1]) { 
            visited[idx+1] = true; 
            min_step = min(min_step, solve_DFS(arr, idx+1) + 1); 
            visited[idx+1] = false; //you need to do this for trying (idx-1) and same number indices 
        } 
        // jump to idx - 1 
        if (idx - 1 >= 0 && !visited[idx-1]) { 
            visited[idx-1] = true; 
            min_step = min(min_step, solve_DFS(arr, idx-1) + 1); 
            visited[idx-1] = false; 
        } 
        // jump to same number with different idx 
        for (int next_idx : mp[arr[idx]]) { 
            if (next_idx == idx) {continue;} 
            if (!visited[next_idx]) { 
                visited[next_idx] = true; 
                min_step = min(min_step, solve_DFS(arr, next_idx) + 1); 
                visited[next_idx] = false; 
            } 
        } 
         
        t[idx] = min(t[idx], min_step); 
        return t[idx]; 
    } 
    int minJumps(vector<int>& arr) { 
        int n = arr.size(); 
        visited.resize(n); 
        t.resize(n, INT_MAX); 
        mp.clear(); 
        for(int i = 0; i<n; i++) { 
            mp[arr[i]].push_back(i); 
        } 
        visited[0] = true; 
        return solve_DFS(arr, 0); 
    } 
};
```

Refer to
https://leetcode.com/problems/jump-game-iv/solutions/1694469/why-dp-memoization-is-not-working/comments/1229622
The problem with the Java code above is because the code does a Depth First Search (DFS) instead of a Breadth First Search (BFS). Also, I believe the code does not use Dynamic Programming (DP), but instead uses DFS with memorization. Memorization and Dynamic Programming are concepts that can be frequently confused because they are similar, and in some cases they are the same thing.

DFS without Memoization
If DFS was used without memorization, this would find the correct answer, but would require trying ALL possible paths before knowing what the shortest path is. This method would give a Time Limit Exceeded Error (TLE) because it would take to long to evaluate ALL possible paths in a 50_000 length arr[]. To prevent paths with infinite loops, this would also require marking indexes that have already been visited on the current path while descending info the DFS, then clearing each visited flag while ascending back up from the DFS, which your code already does.

DFS with Memoization
If DFS is used with memorization, which is what the above code does, memorization will make it run faster, but will sometimes give the wrong answer. This is because, for any index into arr[], it will memorize the shortest distance to the end, but memorized only for the first path to get to that index. For another later path that gets to that same index, there may be a shorter path from that index to the end, but that shorter path will never be found because the distance to the end has already been memorized, and memorized values are never modified or updated.

This can be illustrated with the example arr[] of:
[10, a, b, 20, 10, c, d, 20]
The values a, b, c, d are arbitrary values that are not important to this example as long as they are NOT duplicates of 10 or 20. The wrong answer occurs because a wrong minimum path to the end is memorized for the 10 at index 4.

The code will do DFS with recursively trying the +1 jumps first. This will result in the partial path of indexes:
......+1.......+1.......+1........+1.......
0(10) --> 1(a) --> 2(b) --> 3(20) --> 4(10)
From the 10 at index 4, this will then find that the shortest path to the end is to do +1 jumps until the end, which is 3 jumps, which is the value that will be memorized for the 10 at index 4. From the 10 at index 4, doing a -1 jump back to the 20 at index 3 then doing an == jump to the end, is NOT a possible path at this time, because the partial path has already visited the 20 at index 3, so index 3 cannot be re-used when finding a path to the end.

Later, when trying an == jump to same values from the 10 at index 0, to the 10 at index 4, with the partial path:
......==.......
0(10) --> 4(10)
the code will then use the memorized value of 3 for the 10 at index 4, instead of testing possible paths to the end. Therefore using memorization blocks the code from finding the shorter path of a -1 jump back to the 20 at index 3, then a == jump to the end.

In general when using DFS with memorization to find shortest paths, when there are multiple paths to get to or from a location, and the path to a location can possibly block a path from a location, then the DFS can result in wrong answers. In these cases, a BFS should be used to get the correct answer.

This is NOT a problem to solve with DFS with memorization.

Grid Example:
An easier example to visualize the differences of when DSF with memorization can be used and where it should not be used, are the "robot walking a grid" type of problems that have been used in some of the leetcode monthly problems. Assume we have a rectangular grid with a robot in the top left corner, and we want to find the shortest path for the robot to walk to the bottom right corner, and some squares of the grid contain obstacles that the robot must walk around.

If the robot can only walk right or down, then a DFS with memorization will work, because any path to a grid square cannot effect the path from that grid square to the end. The path can never loop back on itself because the robot can ONLY walk right or down.

If the problem is changed so the robot can now walk up, down, right, or left, then the path can loop back on itself, and some paths to a grid square may possibly block some of the paths from that grid square to the end, This is when BFS should be used instead of DFS. BFS will find the shortest path to a grid square by building all possible paths at the same time, building them all one step at a time. Some of the BFS paths being built can pass through other BFS paths being built, without causing any errors. Whichever BFS path arrives at the desired grid square first is the shortest.

Other graph algorithms may also be useful for these types of problems.
---
Solution 2:  BFS (120 min)
```
class Solution {
    public int minJumps(int[] arr) {
        int level = 0;
        int len = arr.length;
        boolean[] visited = new boolean[len];
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < len; i++) {
            map.putIfAbsent(arr[i], new ArrayList<Integer>());
            map.get(arr[i]).add(i);
        }
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(0);
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int curIndex = q.poll();
                if(curIndex == len - 1) {
                    return level;
                }
                int curVal = arr[curIndex];
                if(curIndex - 1 >= 0 && !visited[curIndex - 1]) {
                    visited[curIndex - 1] = true;
                    q.offer(curIndex - 1);
                }
                if(curIndex + 1 < len && !visited[curIndex + 1]) {
                    visited[curIndex + 1] = true;
                    q.offer(curIndex + 1);
                }
                List<Integer> next = map.get(curVal);
                for(int n : next) {
                    if(!visited[n]) {
                        visited[n] = true;
                        q.offer(n);
                    }
                }
                // Most important line to avoid TLE 
                next.clear();
            }
            level++;
        }
        return level;
    }
}
```

Why we have to add "next.clear()" to avoid TLE ?

Refer to
https://leetcode.com/problems/jump-game-iv/solutions/502699/JavaC++-BFS-Solution-Clean-code-O(N)/comments/453181
If you do not clear, every time you see value in which you already perform the arr[i] == arr[j] movement, you will loop through the hashmap again and would result in possibly looping through the same list n time. (Worse case O(n^2)). After you clear the list, second time you visit the same value you will only need to consider the option +1 and -1. Hope this helps.

Debugging step by step will easily identify how are redundant calls been avoid by adding "clear adjacent list"

Without "clear adjacent list"
```


public class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return 0;
        }
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.computeIfAbsent(arr[i], v -> new LinkedList<>()).add(i);
        }
        List<Integer> curs = new LinkedList<>(); // store current layer
        curs.add(0);
        Set<Integer> visited = new HashSet<>();
        int step = 0;
        // when current layer exists
        while (!curs.isEmpty()) {
            List<Integer> nex = new LinkedList<>();
            // iterate the layer
            for (int node : curs) {
                // check if reached end
                if (node == n - 1) {
                    return step;
                }
                // check same value
                for (int child : graph.get(arr[node])) {
                    System.out.println("node=" + node + ", arr[node]=" + arr[node] + ", child=" + child);
                    if (!visited.contains(child)) {
                        System.out.println("child=" + child + " not visited before, add to visited and nex");
                        visited.add(child);
                        nex.add(child);
                    }
                }
                // clear the list to prevent redundant search
                //graph.get(arr[node]).clear();
                System.out.println("node=" + node + ", arr[node]=" + arr[node] + ", graph cleared");
                // check neighbors
                if (node + 1 < n && !visited.contains(node + 1)) {
                    System.out.println("node+1=" + (node + 1) + " not visited before, add to visited and nex");
                    visited.add(node + 1);
                    nex.add(node + 1);
                }
                if (node - 1 >= 0 && !visited.contains(node - 1)) {
                    System.out.println("node-1=" + (node - 1) + " not visited before, add to visited and nex");
                    visited.add(node - 1);
                    nex.add(node - 1);
                }
            }
            curs = nex;
            step++;
            System.out.println("End current level=" + step);
            System.out.println("---------------------------------");
        }
        return -1;
    }



    public static void main(String[] args) {
        Solution s = new Solution();
        //int result = s.minJumps(new int[] {100,-23,-23,404,100,23,23,23,3,404});
        int result = s.minJumps(new int[] {7,7,7,7,7,1,2,3});
        System.out.println(result);
    }
}

Print log:
==================================================================================
node=0, arr[node]=7, child=0
child=0 not visited before, add to visited and nex
node=0, arr[node]=7, child=1
child=1 not visited before, add to visited and nex
node=0, arr[node]=7, child=2
child=2 not visited before, add to visited and nex
node=0, arr[node]=7, child=3
child=3 not visited before, add to visited and nex
node=0, arr[node]=7, child=4
child=4 not visited before, add to visited and nex
node=0, arr[node]=7, graph cleared
End current level=1
--------------------------------- 
node=0, arr[node]=7, child=0    (below are the redundant calls avoid by adding "clear adjacent list") 
node=0, arr[node]=7, child=1
node=0, arr[node]=7, child=2
node=0, arr[node]=7, child=3
node=0, arr[node]=7, child=4
node=0, arr[node]=7, graph cleared
node=1, arr[node]=7, child=0
node=1, arr[node]=7, child=1
node=1, arr[node]=7, child=2
node=1, arr[node]=7, child=3
node=1, arr[node]=7, child=4
node=1, arr[node]=7, graph cleared
node=2, arr[node]=7, child=0
node=2, arr[node]=7, child=1
node=2, arr[node]=7, child=2
node=2, arr[node]=7, child=3
node=2, arr[node]=7, child=4
node=2, arr[node]=7, graph cleared
node=3, arr[node]=7, child=0
node=3, arr[node]=7, child=1
node=3, arr[node]=7, child=2
node=3, arr[node]=7, child=3
node=3, arr[node]=7, child=4
node=3, arr[node]=7, graph cleared
node=4, arr[node]=7, child=0
node=4, arr[node]=7, child=1
node=4, arr[node]=7, child=2
node=4, arr[node]=7, child=3
node=4, arr[node]=7, child=4
node=4, arr[node]=7, graph cleared
node+1=5 not visited before, add to visited and nex
End current level=2
---------------------------------
node=5, arr[node]=1, child=5
node=5, arr[node]=1, graph cleared
node+1=6 not visited before, add to visited and nex
End current level=3
---------------------------------
node=6, arr[node]=2, child=6
node=6, arr[node]=2, graph cleared
node+1=7 not visited before, add to visited and nex
End current level=4
---------------------------------
4

Process finished with exit code 0
```

With "clear adjacent list"
```


public class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return 0;
        }
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.computeIfAbsent(arr[i], v -> new LinkedList<>()).add(i);
        }
        List<Integer> curs = new LinkedList<>(); // store current layer
        curs.add(0);
        Set<Integer> visited = new HashSet<>();
        int step = 0;
        // when current layer exists
        while (!curs.isEmpty()) {
            List<Integer> nex = new LinkedList<>();
            // iterate the layer
            for (int node : curs) {
                // check if reached end
                if (node == n - 1) {
                    return step;
                }
                // check same value
                for (int child : graph.get(arr[node])) {
                    System.out.println("node=" + node + ", arr[node]=" + arr[node] + ", child=" + child);
                    if (!visited.contains(child)) {
                        System.out.println("child=" + child + " not visited before, add to visited and nex");
                        visited.add(child);
                        nex.add(child);
                    }
                }
                // clear the list to prevent redundant search
                graph.get(arr[node]).clear();
                System.out.println("node=" + node + ", arr[node]=" + arr[node] + ", graph cleared");
                // check neighbors
                if (node + 1 < n && !visited.contains(node + 1)) {
                    System.out.println("node+1=" + (node + 1) + " not visited before, add to visited and nex");
                    visited.add(node + 1);
                    nex.add(node + 1);
                }
                if (node - 1 >= 0 && !visited.contains(node - 1)) {
                    System.out.println("node-1=" + (node - 1) + " not visited before, add to visited and nex");
                    visited.add(node - 1);
                    nex.add(node - 1);
                }
            }
            curs = nex;
            step++;
            System.out.println("End current level=" + step);
            System.out.println("---------------------------------");
        }
        return -1;
    }



    public static void main(String[] args) {
        Solution s = new Solution();
        //int result = s.minJumps(new int[] {100,-23,-23,404,100,23,23,23,3,404});
        int result = s.minJumps(new int[] {7,7,7,7,7,1,2,3});
        System.out.println(result);
    }
}

Print log:
==================================================================================
node=0, arr[node]=7, child=0
child=0 not visited before, add to visited and nex
node=0, arr[node]=7, child=1
child=1 not visited before, add to visited and nex
node=0, arr[node]=7, child=2
child=2 not visited before, add to visited and nex
node=0, arr[node]=7, child=3
child=3 not visited before, add to visited and nex
node=0, arr[node]=7, child=4
child=4 not visited before, add to visited and nex
node=0, arr[node]=7, graph cleared
End current level=1
---------------------------------
node=0, arr[node]=7, graph cleared
node=1, arr[node]=7, graph cleared
node=2, arr[node]=7, graph cleared
node=3, arr[node]=7, graph cleared
node=4, arr[node]=7, graph cleared
node+1=5 not visited before, add to visited and nex
End current level=2
---------------------------------
node=5, arr[node]=1, child=5
node=5, arr[node]=1, graph cleared
node+1=6 not visited before, add to visited and nex
End current level=3
---------------------------------
node=6, arr[node]=2, child=6
node=6, arr[node]=2, graph cleared
node+1=7 not visited before, add to visited and nex
End current level=4
---------------------------------
4

Process finished with exit code 0
```

Refer to
https://leetcode.com/problems/jump-game-iv/solutions/1690813/best-explanation-ever-possible-for-this-question/
```
Let's understood with an example - [100,-23,-23,404,100,23,23,23,3,404] and using this example we create a map. 
Where Key is the number present and the value as an the index in which it is present. 
So, we would get something like this:
```


As we see the key is 100 and value part 0,4 which would give us the indexes where this value is found. So, that whenever we want to jump we can found at 4 index also we have 100 we can jump directly on that apart from going to i + 1, i - 1. So, now with that will take a queue, where we will going to store the element that we are going to process at each step.

Since we are positioned at index 0 will add 0 in the queue and the steps would be 0 as we have not taken any of these steps till now. Now from here we can go to different places.


To state all the possible jumps at each step. I had marked a vertical line in the queue. So, starting is 0 from here we can jump to some other steps.

So, now in the first step we can jump to idx - 1, idx + 1 or values in map. Which gives us -1, 1 & the value in map 0, 4. In this one 0 is current index and 4th one is this 100.


From these -1 & 0 are not reachable & we can just reach to 1 & 4. We put these in the queue. And that line mark the end of the position we can reach in 1st step. Now we had already process the 100 we will removing from the map. So, that we do not process this again, there will be cases where we can get a tle if we keep the processes index in map for example if all these are 100. We'll keep on processing the i - 1 i + 1 i - 1 i + 1 and every other index multiple no of times and we'll get a tle. So, for that reason we are just removing from our map.

Now, in this we know that in the first step we can reach to 1 & 4. So, in the next step where can we reach. So we will be popping the element out of queue and calculating jumps from that particular index.So, for that since we are calculating 2nd step. We do step = step + 1 and we find the step from the 1st index which is -23 : we can go to 0 or 2 or 1 but 0 & 1 are not reachable. So, we will add 2 in the queue.

Now we will again remove -23 from our Map.Next we will go to 4th index. So, from 4th index we can go to -1, +1 & we could have also gone to index 0 but since 100 is not present in map. We couldn't be going to 100 or 0 sice it is already remove from the map. So here will add 5 & 3 in our queue. Now we have already got places where we can jump in 2. We are finding where will be ending up in step 3. Will do a step++ and start processing our indexes.

From 2 we can reach to 3. So, we add 3. But from 5 we can reach to 6 & 7 so we add these two. And from 3 we can reach to 9. Now here we can see 9 is indeed our last index. So, here we will be returning 3.Which is minimum step we would be needed to reach the index 9 or last index. So, we return 3.

Now let's code it up
code each line explained
- Step 1 :
```
{
        // creating variable
        int n = arr.length;
        if(n == 1) return 0; // base case
        
        // craeted map holding integer & list
        Map<Integer, List<Integer>> map = new HashMap<>();
        int step = 0; // intial step is 0
        
        // Our 1st job is "fill the map"
        for(int i = 0; i < n; i++){
            // so, using this function it will check is arr[i] is present or not, if it's not present it would create a new arraylist
            // and if it's already present we will add index in it
            map.computeIfAbsent(arr[i], v -> new ArrayList()).add(i);
        }
```
- Step 2 :
```
// next we need a queue.
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);// in queue we will add our 1st index which is 0;
        
        while(!q.isEmpty()){ // looping until queue is not empty
            step++; // incrementing our step
            int size = q.size(); // taking queue size
            for(int i = 0; i < size; i++){ // now for each element in this queue for this particulart size running a loop
                // so, here we will perform 3 steps
                int j = q.poll(); // getting element from queue
                
                // Jump to j - 1
                if(j - 1 >= 0 && map.containsKey(arr[j - 1])){
                    q.offer(j - 1);
                }
                
                // Jump to j + 1
                if(j + 1 < n && map.containsKey(arr[j + 1])){
                    // there could be 2 conditions
                    if(j + 1 == n - 1) return step; // if j+1 is equals to last element
                    q.offer(j + 1); // otherwise add in queue
                }
                
                // Jump to k --> arr[j] == arr[k]
                if(map.containsKey(arr[j])){ // if this particular element hasn't processed
                    for(int k : map.get(arr[j])){ // so, we will iterate over each k
                        if(k != j){ // in this we first check if they are not equal, positions are not same
                            if(k == n - 1) return step;
                            q.offer(k);
                        }
                    }
                }
                map.remove(arr[j]); // removing from map
            }
        }
```
- Final Step :
```
return step;
```
Java
```
class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        
        if(n == 1) return 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        int step = 0;
        
        // fill the map
        for(int i = 0; i < n; i++){
            map.computeIfAbsent(arr[i], v -> new ArrayList()).add(i);
        }
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        
        while(!q.isEmpty()){
            step++;
            int size = q.size();
            for(int i = 0; i < size; i++){
                int j = q.poll();
            
                if(j - 1 >= 0 && map.containsKey(arr[j - 1])){
                    q.offer(j - 1);
                }

                if(j + 1 < n && map.containsKey(arr[j + 1])){
                    if(j + 1 == n - 1) return step;
                    q.offer(j + 1);
                }

                if(map.containsKey(arr[j])){
                    for(int k : map.get(arr[j])){
                        if(k != j){
                            if(k == n - 1) return step;
                            q.offer(k);
                        }
                    }
                }
                map.remove(arr[j]);
            }
        }
        
        return step;
    }
}
```
