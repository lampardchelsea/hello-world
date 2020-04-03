/**
 Refer to
 https://leetcode.com/problems/satisfiability-of-equality-equations/
 Given an array equations of strings that represent relationships between variables, 
 each string equations[i] has length 4 and takes one of two different forms: "a==b" 
 or "a!=b".  Here, a and b are lowercase letters (not necessarily different) that 
 represent one-letter variable names.

Return true if and only if it is possible to assign integers to variable names so as 
to satisfy all the given equations.

Example 1:
Input: ["a==b","b!=a"]
Output: false
Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the second.  
There is no way to assign the variables to satisfy both equations.

Example 2:
Input: ["b==a","a==b"]
Output: true
Explanation: We could assign a = 1 and b = 1 to satisfy both equations.

Example 3:
Input: ["a==b","b==c","a==c"]
Output: true

Example 4:
Input: ["a==b","b!=c","c==a"]
Output: false

Example 5:
Input: ["c==c","b==d","x!=z"]
Output: true
 
Note:
1 <= equations.length <= 500
equations[i].length == 4
equations[i][0] and equations[i][3] are lowercase letters
equations[i][1] is either '=' or '!'
equations[i][2] is '='
*/

// Solution 1: Union Find
// Refer to
// https://leetcode.com/problems/satisfiability-of-equality-equations/discuss/234486/JavaC%2B%2BPython-Easy-Union-Find
/**
 Intuition:
We have 26 nodes in the graph.
All "==" equations actually represent the connection in the graph.
The connected nodes should be in the same color/union/set.

Then we check all inequations.
Two inequal nodes should be in the different color/union/set.

Explanation
We can solve this problem by DFS or Union Find.

Firt pass all "==" equations.
Union equal letters together
Now we know which letters must equal to the others.

Second pass all "!=" inequations,
Check if there are any contradict happens.

Time Complexity:
Union Find Operation, amortized O(1)
First pass all equations, O(N)
Second pass all inequations, O(N)
*/
class Solution {
    public boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind(26);
        for(String e : equations) {
            if(e.charAt(1) == '=') {
                uf.union(e.charAt(0) - 'a', e.charAt(3) - 'a'); 
            }
        }
        for(String e : equations) {
            if(e.charAt(1) == '!') {
                if(uf.find(e.charAt(0) - 'a') == uf.find(e.charAt(3) - 'a')) {
                    return false;
                }
            }
        }
        return true;
    }
}

class UnionFind {
    int[] parent;
    int count;
    public UnionFind(int n) {
        parent = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
        count = 0;
    }
    
    public int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }
    
    public void union(int a, int b) {
        int src = find(a);
        int dst = find(b);
        if(src != dst) {
            parent[src] = dst;
            count--;
        }
    }
    
    public int get_count() {
        return count;
    }
}
