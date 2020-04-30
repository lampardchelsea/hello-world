/**
 Refer to
 https://leetcode.com/problems/number-of-operations-to-make-network-connected/
 There are n computers numbered from 0 to n-1 connected by ethernet cables connections forming a network 
 where connections[i] = [a, b] represents a connection between computers a and b. Any computer can reach 
 any other computer directly or indirectly through the network.
 
 Given an initial computer network connections. You can extract certain cables between two directly connected 
 computers, and place them between any pair of disconnected computers to make them directly connected. 
 Return the minimum number of times you need to do this in order to make all the computers connected. 
 If it's not possible, return -1. 
 
 Example 1:
 Input: n = 4, connections = [[0,1],[0,2],[1,2]]
 Output: 1
 Explanation: Remove cable between computer 1 and 2 and place between computers 1 and 3.

 Example 2:
 Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
 Output: 2
 
 Example 3:
 Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]
 Output: -1
 Explanation: There are not enough cables.

 Example 4:
 Input: n = 5, connections = [[0,1],[0,2],[3,4],[2,3]]
 Output: 0
 
 Constraints:
 1 <= n <= 10^5
 1 <= connections.length <= min(n*(n-1)/2, 10^5)
 connections[i].length == 2
 0 <= connections[i][0], connections[i][1] < n
 connections[i][0] != connections[i][1]
 There are no repeated connections.
 No two computers are connected by more than one cable.
*/

// Solution 1: Union Find native link
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
// https://leetcode.com/problems/number-of-operations-to-make-network-connected/discuss/477677/Java-Union-Find-(count-components-and-extra-edges)
class Solution {
    public int makeConnected(int n, int[][] connections) {
        UnionFind uf = new UnionFind(n);
        int extraEdges = 0;
        for(int i = 0; i < connections.length; i++) {
            int p1 = uf.find(connections[i][0]);
            int p2 = uf.find(connections[i][1]);
            if(p1 == p2) {
                extraEdges++;
            } else {
                uf.union(connections[i][0], connections[i][1]);
            }
        }
        return extraEdges >= uf.get_count() - 1 ? uf.get_count() - 1 : -1;
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
        count = n;
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

// Solution 2: Union Find link by size
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
    public int makeConnected(int n, int[][] connections) {
        UnionFind uf = new UnionFind(n);
        int extraEdges = 0;
        for(int i = 0; i < connections.length; i++) {
            int p1 = uf.find(connections[i][0]);
            int p2 = uf.find(connections[i][1]);
            if(p1 == p2) {
                extraEdges++;
            } else {
                uf.union(connections[i][0], connections[i][1]);
            }
        }
        return extraEdges >= uf.get_count() - 1 ? uf.get_count() - 1 : -1;
    }
}

class UnionFind {
    int[] parent;
    int[] size;
    int count;
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        count = n;
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
            if(size[src] > size[dst]) {
                parent[dst] = src;
                size[src] += size[dst];
            } else {
                parent[src] = dst;
                size[dst] += size[src];
            }
        }
        count--;
    }
    
    public int get_count() {
        return count;
    }
}

// Solution 3: Union Find link by rank
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
class Solution {
    public int makeConnected(int n, int[][] connections) {
        UnionFind uf = new UnionFind(n);
        int extraEdges = 0;
        for(int i = 0; i < connections.length; i++) {
            int p1 = uf.find(connections[i][0]);
            int p2 = uf.find(connections[i][1]);
            if(p1 == p2) {
                extraEdges++;
            } else {
                uf.union(connections[i][0], connections[i][1]);
            }
        }
        return extraEdges >= uf.get_count() - 1 ? uf.get_count() - 1 : -1;
    }
}

class UnionFind {
    int[] parent;
    int[] rank;
    int count;
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
        count = n;
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
            if(rank[src] > rank[dst]) {
                parent[dst] = src;
            } else {
                parent[src] = dst;
                if(rank[src] == rank[dst]) {
                    rank[src]++;
                }
            }
        }
        count--;
    }
    
    public int get_count() {
        return count;
    }
}
