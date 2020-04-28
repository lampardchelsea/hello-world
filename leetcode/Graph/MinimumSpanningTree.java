/**
 Refer to
 https://www.cnblogs.com/lz87/p/7476882.html
 Given a list of Connections, which is the Connection class (the city name at both ends of the edge and 
 a cost between them), find some edges, connect all the cities and spend the least amount.
 
 Return the connects if can connect all the cities, otherwise return empty list.
 Return the connections sorted by the cost, or sorted city1 name if their cost is same, or sorted city2 
 if their city1 name is also same.

 Example
 Given the connections = ["Acity","Bcity",1], ["Acity","Ccity",2], ["Bcity","Ccity",3]
 Return ["Acity","Bcity",1], ["Acity","Ccity",2]
*/

// Solution 1: Kruskal
// Refer to
// https://www.cnblogs.com/lz87/p/7476882.html
/**
 Solution 1. Union Find with Kruskal's Greedy MST algorithm.
 The core idea of prim's MST algorithm is that as long as we have not spanned all vertices(cities), 
 we keep picking the cheapest edge e = (u, v), u is in X and v is NOT in X.
 We can use a union find data structure to simulate this process. If we pick an edge(connection), 
 we would have the following 2 cases.
 a. city1 and city2 are already connected: this means this edge does not satisfy the condition that 
    city1 is in X and city2 is NOT in X, this connection should be ignored.
 b. city1 and city2 are not connected: this means this edge satisfies the above condition, if it is 
    the cheapst edge out of all those edges that meets the condition, we should 
 
 select this connection and add it to the final result.
 
 Based on the above analysis, we have the following algorithm.
 1. sort the connections to make sure smaller cost connections are in front. (Prim MST greedy)
 
 2. create a mapping between city names and union find index as it is best to use integer as union find's index.
    use numbers from 0 to n - 1 for all cities assuming there are n different cities. 
    As we are creating the mapping, the next available integer index also represents the total number of cities 
    whose mapping are created so far. After all the mapping is done, this idx variable tells us the total number 
    of nodes(cities). This is important in checking if there is any city that is disconnected with all other cities. 
    Based on connected graph theory, if all n cities are connected, there we would include n - 1 different edges that 
    do not introduce any cycle, thus generating the MST.
    However, if there is a part of the graph that are disconnected from the rest, then it means we must only included 
    fewer than n - 1 different connections, otherwise all cities would be connected. 

 3. Iterate all connections, add to the final result each connection whose ends are not connected in the uf and 
    connect both ends' mapping in the uf. 
    
 4. Check if there are n - 1 connections in the final result. If there aren't return an empty list to indicate there 
    are disconnected cities in the given connections.
    
 Runtime/Space complexity: O(m * logm + 2 * m + n) ~ O(m * logm) runtime; O(n) space 
 Assuming there are n different cities and m different edges, 
 1. sorting: O(m * log m) runtime, O(1) space assuming in place quick sort is used.
 2. mapping: O(m) runtime, O(n) space.
 3. unionfind creation: O(n) runtime, O(n)  space.
 4. connections iteration: O(m) runtime, as both the uf find and connect operations take O(1) time on average.
*/

// Kruskal template
// Refer to
// https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/04GreedyAlgorithmsII.pdf
/**
 KRUSKAL (V, E, c) 
 SORT m edges by weight so that c(e1) ≤ c(e2) ≤ … ≤ c(em)
 S ← φ
 FOREACH v ∈ V: MAKESET(v).
 FOR i = 1 TO m
   (u, v) ← ei
   IF FINDSET(u) ≠ FINDSET(v) <- are u and v in same component?
     S ← S ∪ { ei }
     UNION(u, v) <- make u and v in same component
 RETURN S
*/

/**
 * Definition for a Connection.
 * public class Connection {
 *   public String city1, city2;
 *   public int cost;
 *   public Connection(String city1, String city2, int cost) {
 *       this.city1 = city1;
 *       this.city2 = city2;
 *       this.cost = cost;
 *   }
 * }
 */
 
 public class Solution {
    private Comparator<Connection> comp = new Comparator<Connection>() {
        public int compare(Connection c1, Connection c2) {
            if(c1.cost != c2.cost) {
                return c1.cost - c2.cost;
            }
            else if(!c1.city1.equals(c2.city1)) {
                return c1.city1.compareTo(c2.city1);
            }
            return c1.city2.compareTo(c2.city2);
        }  
    };
    
    public List<Connection> lowestCost(List<Connection> connections) {
        List<Connection> mst = new ArrayList<Connection>();
        if(connections == null || connections.size() == 0) {
            return mst;
        }
        Collections.sort(connections, comp);
        int idx = 0;
        HashMap<String, Integer> strToIdxMap = new HashMap<String, Integer>();
        for(Connection c : connections) {
            if(!strToIdxMap.containsKey(c.city1)) {
                strToIdxMap.put(c.city1, idx++);
            }
            if(!strToIdxMap.containsKey(c.city2)) {
                strToIdxMap.put(c.city2, idx++);
            }
        }
        UnionFind uf = new UnionFind(idx);
        for(Connection c : connections) {
            int city1Root = uf.find(strToIdxMap.get(c.city1));
            int city2Root = uf.find(strToIdxMap.get(c.city2));
            if(city1Root != city2Root) {
                mst.add(c);
                uf.connect(city1Root, city2Root);
            }
        }
        if(mst.size() < idx - 1) {
            return new ArrayList<Connection>();
        }
        return mst;
    }
}

class UnionFind {
    int[] father;
    UnionFind(int n) {
        father = new int[n];
        for(int i = 0; i < n; i++) {
            father[i] = i;
        }
    }
    int find(int x) {
        if(father[x] == x) {
            return x;
        }
        return father[x] = find(father[x]);
    }
    void connect(int a, int b) {
        int root_a = find(a);
        int root_b = find(b);
        if(root_a != root_b) {
            father[root_a] = root_b;
        }
    }
}
