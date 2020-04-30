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
