/**
 Refer to
 https://leetcode.com/problems/friend-circles/
 There are N students in a class. Some of them are friends, while some are not. 
 Their friendship is transitive in nature. For example, if A is a direct friend of B, 
 and B is a direct friend of C, then A is an indirect friend of C. And we defined a 
 friend circle is a group of students who are direct or indirect friends.

Given a N*N matrix M representing the friend relationship between students in the class. 
If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise 
not. And you have to output the total number of friend circles among all the students.

Example 1:
Input: 
[[1,1,0],
 [1,1,0],
 [0,0,1]]
Output: 2
Explanation:The 0th and 1st students are direct friends, so they are in a friend circle. 
The 2nd student himself is in a friend circle. So return 2.

Example 2:
Input: 
[[1,1,0],
 [1,1,1],
 [0,1,1]]
Output: 1
Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends, 
so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.

Note:
N is in range [1,200].
M[i][i] = 1 for all students.
If M[i][j] = 1, then M[j][i] = 1.
*/
// Solution 1: DFS Same as Number of Connected Components in an Undirected Graph
// Refer to
// https://leetcode.com/problems/friend-circles/discuss/101338/Neat-DFS-java-solution/105148
// https://www.cnblogs.com/grandyang/p/6686983.html
/**
 这道题让我们求朋友圈的个数，题目中对于朋友圈的定义是可以传递的，比如A和B是好友，B和C是好友，那么即使A和C不是好友，
 那么他们三人也属于一个朋友圈。那么比较直接的解法就是DFS搜索，对于某个人，遍历其好友，然后再遍历其好友的好友，
 那么我们就能把属于同一个朋友圈的人都遍历一遍，我们同时标记出已经遍历过的人，然后累积朋友圈的个数，再去对于没有
 遍历到的人在找其朋友圈的人，这样就能求出个数。其实这道题的本质是之前那道题
 Number of Connected Components in an Undirected Graph，其实许多题目的本质都是一样的
 Also same solution style as 841. Keys And Rooms
*/
class Solution {
    public int findCircleNum(int[][] M) {
        if(M == null || M.length == 0 || M[0] == null || M[0].length == 0) {
            return 0;
        }
        int count = 0;
        boolean[] visited = new boolean[M.length];
        for(int i = 0; i < M.length; i++) {
            if(!visited[i]) {
                helper(M, visited, i);
                count++;
            }
        }
        return count;
    }
    
    private void helper(int[][] M, boolean[] visited, int i) {
        for(int j = 0; j < M.length; j++) {
            if(!visited[j] && M[i][j] == 1) {
                visited[j] = true;
                helper(M, visited, j);
            }
        }
    }
}

// Solution 2: Union find native link
// Refer to
// https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pdf/UnionFind.pdf
/**
 UNION(x, y) 
 r ← FIND(x).
 s ← FIND(y).
 parent[r] ← s.
 Theorem. Using naïve linking, a UNION or FIND operation can take Θ(n) time
 in the worst case, where n is the number of elements. 
*/
// https://leetcode.com/problems/friend-circles/discuss/101336/Java-solution-Union-Find
// Style 1:
class Solution {
    public int findCircleNum(int[][] M) {
        UnionFind uf = new UnionFind(M.length);
        int count = M.length;
        for(int i = 0; i < M.length; i++) {
            for(int j = i + 1; j < M.length; j++) {
                if(M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getCount();
    }
    
    class UnionFind {
        int[] parent;
        int count;
        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public void union(int a, int b) {
            int src = find(a);
            int dst = find(b);
            if(src != dst) {
                parent[src] = dst;
                count--;
            }
        }
        
        public int find(int x) {
            if(parent[x] == x) {
                return x;
            }
            return parent[x] = find(parent[x]);
        }
        
        public int getCount() {
            return count;
        }
    }    
}


// Style 2:
class Solution {
    int count = 0;
    public int findCircleNum(int[][] M) {
        if(M == null || M.length == 0) {
            return 0;
        }
        count = M.length;
        // initialize union find
        int[] parent = new int[M.length];
        for(int i = 0; i < M.length; i++) {
            parent[i] = i;
        }
        for(int i = 0; i < M.length; i++) {
            for(int j = i + 1; j < M.length; j++) {
                if(M[i][j] == 1) {
                    union(parent, i, j);
                }
            }
        }
        return count;
    }
    
    private void union(int[] parent, int i, int j) {
        int src = find(parent, i);
        int dst = find(parent, j);
        if(src != dst) {
            parent[src] = dst;
            count--;
        }
    }
    
    private int find(int[] parent, int x) {
        // Style 1: Use while -> Same as solution and Number of Connected Components in an Undirected Graph solution
        // https://leetcode.com/problems/friend-circles/discuss/101336/Java-solution-Union-Find
        // while(parent[x] != x) {
        //     parent[x] = parent[parent[x]]; // path compression by halving
        //     x = parent[x];
        // }
        // Style 2: Use if -> Same as what used for Most Stones Removed With Same Row Or Column solution
        if(parent[x] != x) {
            parent[x] = find(parent, find(parent, parent[x]));
        }
        return parent[x];
    }
}

// Solution 3: Union Find link by size
// Refer to
// https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pdf/UnionFind.pdf
// https://leetcode.com/problems/friend-circles/discuss/101336/Java-solution-Union-Find/266043
/**
 Link-by-size
 Maintain a tree size (number of nodes) for each root node.
 Link root of smaller tree to root of larger tree (breaking ties arbitrarily).
 Theorem. Using link-by-size, any UNION or FIND operation takes O(log n) time
 in the worst case, where n is the number of elements. 
 UNION(x, y) 
 r ← FIND(x).
 s ← FIND(y).
 IF (r = s) RETURN.
 ELSE IF (size[r] > size[s])
   parent[s] ← r.
   size[r] ← size[r] + size[s].
 ELSE
   parent[r] ← s.
   size[s] ← size[r] + size[s].
*/
class Solution {
    class UF {
        private int[] parent, size;
        private int count;
        
        public UF(int n) {
            parent = new int[n];
            size = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }

        }
        
        public int find(int p) {
            // path compression
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) 
                return;
            // union by size
            if (size[rootP] > size[rootQ]) {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            } else {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            count--;
        }
        
        public int count() { return count; }
    }
    
    public int findCircleNum(int[][] M) {
        int n = M.length;
        UF uf = new UF(n);
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (M[i][j] == 1)
                    uf.union(i, j);
        return uf.count();
    }
}

// Solution 4: Union Find link by rank
// Refer to
// https://leetcode.com/problems/friend-circles/discuss/101336/Java-solution-Union-Find
// https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pdf/UnionFind.pdf
/**
 Link-by-rank. Maintain an integer rank for each node, initially 0. Link root of
 smaller rank to root of larger rank; if tie, increase rank of larger root by 1.
 Theorem. Using link-by-rank, any UNION or FIND operation takes O(log n) time
 in the worst case, where n is the number of elements. 
 UNION(x, y)
 r ← FIND(x).
 s ← FIND(y).
 IF (r = s) RETURN.
 ELSE IF (rank[r] > rank[s])
   parent[s] ← r.
 ELSE IF (rank[r] < rank[s])
   parent[r] ← s.
 ELSE
   parent[r] ← s.
   rank[s] ← rank[s] + 1.
*/
public class Solution {
    class UnionFind {
        private int count = 0;
        private int[] parent, rank;
        
        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int p) {
        	while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }
        
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            }
            else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            count--;
        }
        
        public int count() {
            return count;
        }
    }
    
    public int findCircleNum(int[][] M) {
        int n = M.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] == 1) uf.union(i, j);
            }
        }
        return uf.count();
    }
}











































https://leetcode.com/problems/number-of-provinces/description/
There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.
A province is a group of directly or indirectly connected cities and no other cities outside of the group.
You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
Return the total number of provinces.
 
Example 1:

Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2

Example 2:

Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3
 
Constraints:
- 1 <= n <= 200
- n == isConnected.length
- n == isConnected[i].length
- isConnected[i][j] is 1 or 0.
- isConnected[i][i] == 1
- isConnected[i][j] == isConnected[j][i]
--------------------------------------------------------------------------------
Attempt 1: 2022-12-16
Solution 1:  DFS (10 min)
class Solution { 
    public int findCircleNum(int[][] isConnected) { 
        int n = isConnected.length; 
        boolean[] visited = new boolean[n]; 
        int count = 0; 
        for(int i = 0; i < n; i++) { 
            if(!visited[i]) { 
                helper(isConnected, visited, i); 
                count++; 
            } 
        } 
        return count; 
    }
    
    private void helper(int[][] isConnected, boolean[] visited, int i) { 
        for(int j = 0; j < isConnected.length; j++) { 
            if(!visited[j] && isConnected[i][j] == 1) { 
                visited[j] = true; 
                helper(isConnected, visited, j); 
            } 
        } 
    } 
}

Time Complexity : O(N^2)  
Space Complexity : O(N)

Solution 2:  Union Find using adjacent matrix (10 min)
Style 1: Simple Union Find 
class Solution { 
    public int findCircleNum(int[][] isConnected) { 
        int n = isConnected.length; 
        int[] parent = new int[n]; 
        for(int i = 0; i < n; i++) { 
            parent[i] = i; 
        } 
        int count = n; 
        for(int i = 0; i < n; i++) { 
            for(int j = 0; j < n; j++) { 
                if(isConnected[i][j] == 1) { 
                    int rootA = find(i, parent); 
                    int rootB = find(j, parent); 
                    if(rootA != rootB) { 
                        parent[rootA] = rootB; 
                        count--; 
                    } 
                } 
            } 
        } 
        return count; 
    } 

    private int find(int x, int[] parent) { 
        if(x == parent[x]) { 
            return x; 
        } 
        return parent[x] = find(parent[x], parent); 
    } 

    // Alternative find style
    private int find2(int x, int[] parent) { 
        while(x != parent[x]) { 
            parent[x] = parent[parent[x]]; 
            x = parent[x]; 
        } 
        return x; 
    } 
}

Time Complexity : O(N^2 * logN)  
Space Complexity : O(N)

Style 2: Union Find with weighted union and path compression
class Solution { 
    public int findCircleNum(int[][] isConnected) { 
        int n = isConnected.length; 
        int[] parent = new int[n]; 
        int[] rank = new int[n]; 
        for(int i = 0; i < n; i++) { 
            parent[i] = i; 
            rank[i] = 1; 
        } 
        int count = n; 
        for(int i = 0; i < n; i++) { 
            for(int j = 0; j < n; j++) { 
                if(isConnected[i][j] == 1) { 
                    int rootA = find(i, parent); 
                    int rootB = find(j, parent); 
                    // Weighted union 
                    if(rootA != rootB) { 
                        if(rank[rootA] > rank[rootB]) { 
                            parent[rootB] = rootA; 
                            rank[rootA] += rank[rootB];  
                        } else { 
                            parent[rootA] = rootB; 
                            rank[rootB] += rank[rootA]; 
                        } 
                        count--; 
                    } 
                } 
            } 
        } 
        return count; 
    } 

    private int find(int x, int[] parent) { 
        if(x == parent[x]) { 
            return x; 
        } 
        return parent[x] = find(parent[x], parent); 
    } 

    private int find2(int x, int[] parent) { 
        while(x != parent[x]) { 
            parent[x] = parent[parent[x]]; 
            x = parent[x]; 
        } 
        return x; 
    } 
}

Time Complexity : O(N^2 * α(N)) ~ O(N^2)
Space Complexity : O(N)

--------------------------------------------------------------------------------
Refer to
Complete analysis and solutions for this question, DFS/BFS/UnionFind.
https://leetcode.com/problems/number-of-provinces/solutions/112286/complete-analysis-and-solutions-for-this-question-dfs-bfs-unionfind/
Solution1: DFS or BFS
We can reduce abstract this problem into finding connected groups in a undirected graph represented as an adjacency matrix.
Since we want to treat the input M as a adjacency matrix, we treated each row from 0 to n - 1 as n nodes. Hence we use a boolean[] to store the visited status.
Therefore, a normal graph traversal algorithms can be utilized to find the number of connected groups in this undirected graph.
DFS solution:
Since the input matrix M is n*n in size
Time complexity: O(n^2)
Space complexity: O(n)
class Solution { 
    public int findCircleNum(int[][] M) { 
        if (M == null || M.length == 0 || M[0].length == 0) return 0; 
        boolean[] visited = new boolean[M.length]; 
        int count = 0; 
        for (int i = 0; i < M.length; i++) { 
            if (!visited[i]) { 
                count++; 
                dfs(M, i, visited); 
            } 
        } 
         
        return count; 
    } 
     
    private void dfs(int[][] M, int i, boolean[] visited) { 
        for (int j = 0; j < M[i].length; j++) { 
            if (M[i][j] == 1 && !visited[j]) { 
                visited[j] = true; 
                dfs(M, j, visited); 
            } 
        } 
    } 
}
BFS solution:
The same idea, but used a Queue to perform the BFS process.
Time complexity: O(n^2)
Space complexity: O(n)
class Solution { 
    public int findCircleNum(int[][] M) { 
        if (M == null || M.length == 0 || M[0].length == 0) return 0; 
        boolean[] visited = new boolean[M.length]; 
        int count = 0; 
        for (int i = 0; i < M.length; i++) { 
            if (!visited[i]) { 
                bfs(M, i, visited); 
                count++; 
            } 
        } 
         
        return count; 
    } 
     
    private void bfs(int[][] M, int i, boolean[] visited) { 
        Queue<Integer> queue = new LinkedList<>(); 
        queue.offer(i); 
        visited[i] = true; 
        while (!queue.isEmpty()) { 
            int curr = queue.poll(); 
            for (int j = 0; j < M[curr].length; j++) { 
                if (M[curr][j] == 1 && !visited[j]) { 
                    queue.offer(j); 
                    visited[j] = true; 
                } 
            } 
        } 
    } 
}
Solution2: Union-find
Since we've already reduced the question into a connectivity problem, union-find algorithm seems to be appliable to this question, for it's suitable to be used for dynamic connectivity problem.
For this question, specifically, we still treat the input M as a adjacency matrix. And row index 0 to n-1 as n nodes. We check each edge (M[i][j]) between each node pairs, and union i and j. After we unioned each edge, we check the number of roots, i.e. where i == id[i], and return it as the number of connected components.
Note that we have 2 optimization for the union-find algorithm:
1.During the union() process, we check the size of each connected component and union the smaller one to the greater one. This is called weighed union and can flatten the depth of the connected component and improve the efficiency of the union-find algorithm.
2.During the findRoot() process, we used path compression to flatten the depth of the connected component, also improved the efficiency of the algorithm.
By utilizing this 2 improvements, the time complexity of calling union() for M times is O(n + Mlg*n), which can be viewed as O(n), because lg*n can be viewed as a constant.
class Solution { 
    // weighed quick union with path compression 
    public int findCircleNum(int[][] M) { 
        int[] size = new int[M.length]; 
        int[] id = new int[M.length]; 
        for (int i = 0; i < M.length; i++) { 
            id[i] = i; 
            size[i] = 1; 
        } 
        for (int i = 0; i < M.length; i++) { 
            for (int j = 0; j < M[i].length; j++) { 
                if (M[i][j] == 1) { 
                    union(id, size, i, j); 
                } 
            } 
        } 
         
        int count = 0; 
        for (int i = 0; i < id.length; i++) { 
            if (i == id[i]) { 
                count++; 
            } 
        } 
         
        return count; 
    } 
     
    private void union(int[] id, int[] size, int i, int j) { 
        int rootI = findRoot(id, i); 
        int rootJ = findRoot(id, j); 
         
        // weighed quick union 
        if (size[rootI] >= size[rootJ]) { 
            id[rootJ] = rootI; 
            size[rootI] += size[rootJ]; 
        } else { 
            id[rootI] = rootJ; 
            size[rootJ] += size[rootI]; 
        } 
    } 
     
    private int findRoot(int[] id, int curr) { 
        while (curr != id[curr]) { 
            // path compression 
            id[curr] = id[id[curr]]; 
            curr = id[curr]; 
        } 
        return curr; 
    } 
}

--------------------------------------------------------------------------------
Refer to
3 different Union Find Time & Space Complexity evolution
https://leetcode.com/problems/number-of-provinces/solutions/1461633/python-union-find-clean-concise/
✔️ Solution 1: Union Find (Naive)
class UnionFind: 
    def __init__(self, n): 
        self.parent = [i for i in range(n)] 
         
    def find(self, u): 
        if u != self.parent[u]: 
            u = self.find(self.parent[u]) 
        return u 
     
    def union(self, u, v): 
        pu, pv = self.find(u), self.find(v) 
        if pu == pv: return False 
        self.parent[pu] = pv 
        return True

class Solution: 
    def findCircleNum(self, isConnected: List[List[int]]) -> int: 
        n = len(isConnected) 
         
        component = n 
        uf = UnionFind(n) 
        for i in range(n): 
            for j in range(i+1, n): 
                if isConnected[i][j] == 1 and uf.union(i, j): 
                    component -= 1 
        return component
Complexity:
- Time: O(N^3), where N <= 200 is number of nodes
- Space: O(N)

✔️ Solution 2: Union Find (Path Compression)
class UnionFind: 
    def __init__(self, n): 
        self.parent = [i for i in range(n)] 
         
    def find(self, u): 
        if u != self.parent[u]: 
            self.parent[u] = self.find(self.parent[u])  # Path compression 
        return self.parent[u] 
     
    def union(self, u, v): 
        pu, pv = self.find(u), self.find(v) 
        if pu == pv: return False 
        self.parent[pu] = pv 
        return True

class Solution: 
    def findCircleNum(self, isConnected: List[List[int]]) -> int: 
        n = len(isConnected) 
         
        component = n 
        uf = UnionFind(n) 
        for i in range(n): 
            for j in range(i+1, n): 
                if isConnected[i][j] == 1 and uf.union(i, j): 
                    component -= 1 
        return component
Complexity:
- Time: O(N^2 * logN), where N <= 200 is number of nodes
- Space: O(N)  

✔️ Solution 3: Union Find (Union by Size & Path Compression)
class UnionFind: 
    def __init__(self, n): 
        self.parent = [i for i in range(n)] 
        self.size = [1] * n 
         
    def find(self, u): 
        if u != self.parent[u]: 
            self.parent[u] = self.find(self.parent[u])  # Path compression 
        return self.parent[u] 
     
    def union(self, u, v): 
        pu, pv = self.find(u), self.find(v) 
        if pu == pv: return False 
        if self.size[pu] < self.size[pv]:  # Merge pu to pv 
            self.size[pv] += self.size[pu] 
            self.parent[pu] = pv 
        else: 
            self.size[pu] += self.size[pv] 
            self.parent[pv] = pu 
        return True

class Solution: 
    def findCircleNum(self, isConnected: List[List[int]]) -> int: 
        n = len(isConnected) 
         
        component = n 
        uf = UnionFind(n) 
        for i in range(n): 
            for j in range(i+1, n): 
                if isConnected[i][j] == 1 and uf.union(i, j): 
                    component -= 1 
        return component
Complexity:
- Time: O(N^2 * α(N)) ~ O(N^2), where N <= 200 is number of nodesExplanation: Using both path compression and union by size ensures that the amortized time per Union Find operation is only α(n), which is optimal, where α(n) is the inverse Ackermann function. This function has a value α(n) < 5 for any value of n that can be written in this physical universe, so the disjoint-set operations take place in essentially constant time.
Reference: https://en.wikipedia.org/wiki/Disjoint-set_data_structure or https://www.slideshare.net/WeiLi73/time-complexity-of-union-find-55858534 for more information.
- Space: O(N)
Refer to
Union Find (并查集) 的四种方法
L323.Lint431.Number of Connected Components in an Undirected Graph (Ref.L2421)
L2421.Number of Good Paths (Ref.L2506)
