import java.util.*;

public class Cityconnection {
    public static int Solution (
           int numTotalAvailableCities,
           int numTotalAvailableRoads,
           List<List<Integer>> roadsAvailable,
           int numNewRoadsConstruct,
           List<List<Integer>> costNewRoadsConstruct
    ) {
        if(numTotalAvailableCities < 2 || numTotalAvailableRoads >= numTotalAvailableCities + 1) {
            return -1;
        }
        UnionFind uf = new UnionFind(numTotalAvailableCities);
        int existedRoadCount = 0;
        for(List<Integer> pair : roadsAvailable) {
            int city1 = pair.get(0);
            int city2 = pair.get(1);
            if(!uf.find(city1, city2)) {
                uf.union(city1, city2);
            }
            existedRoadCount++;
        }
        PriorityQueue<Connection> pq = new PriorityQueue<>(numNewRoadsConstruct, (a, b) -> (
            Integer.compare(a.cost, b.cost)
        ));
        for(List<Integer> newCon : costNewRoadsConstruct) {
            pq.offer(new Connection(newCon.get(0), newCon.get(1), newCon.get(2)));
        }
        List<Integer> mst = new ArrayList<>();
        while(!pq.isEmpty() && (mst.size() + existedRoadCount < numTotalAvailableCities - 1)) {
            Connection curr = pq.poll();
            int city1 = curr.city1;
            int city2 = curr.city2;
            if(!uf.find(city1, city2)) {
                uf.union(city1, city2);
                mst.add(curr.cost);
            }
        }
        if(mst.size() + existedRoadCount < numTotalAvailableCities - 1) {
            return -1;
        }
        int sum = 0;
        for(int i = 0;  i < mst.size(); i++) {
            sum += mst.get(i);
        }
        return sum;
    }
}
class UnionFind{
    int[] ids;
    public UnionFind(int size) {
        this.ids = new int[size + 1];
        for(int i = 0; i < size + 1; i++) {
            ids[i] = i;
        }
    }
    public int root(int val) {
        if(ids[val] != val) {
            val = ids[val];
        }
        return val;
    }
    public boolean find(int one, int two) {
        return root(one) == root(two);
    }
    public void union(int one, int two) {
        int root1 = root(one);
        int root2 = root(two);
        ids[root2] = root1;
    }
}
class Connection{
    int city1;
    int city2;
    int cost;
    public Connection(int city1, int city2, int cost) {
        this.city1 = city1;
        this.city2 = city2;
        this.cost = cost;
    }
}