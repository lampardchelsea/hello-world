
/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-589-connecting-graph.html
 * Description
	Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.
	
	You need to support the following method:
	1. connect(a, b), add an edge to connect node a and node b.
	2. query(a, b), check if two nodes are connected
	
	
	Example
	5 // n = 5
	query(1, 2) return false
	connect(1, 2)
	query(1, 3) return false
	connect(2, 4)
	query(1, 4) return true
 * 
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-589-connecting-graph.html
 * 
 * 并查集完整模板
	public class UnionFind {
	    private int[] father = null;
		public int find(int x) {
		    if(father[x] == x) {
			    return x;
			}
			return find(father[x]);
		}
		
		public void union(int a, int b) {
		    int root_a = find(a);
			int root_b = find(b);
			if(root_a != root_b) {
			    father[root_a] = root_b;
			}
		}
	}
 * 
 */
public class ConnectingGraph {
	private int[] father;
	
	public ConnectingGraph(int n) {
		father = new int[n + 1];
		for(int i = 1; i < n + 1; i++) {
			father[i] = i;
		}
	}
	
	public int find(int x) {
		if(father[x] == x) {
			return x;
		}
		return find(father[x]);
	}
	
	public void union(int a, int b) {
		int root_a = find(a);
		int root_b = find(b);
		if(root_a != root_b) {
			father[root_a] = root_b;
		}
	}
	
	public boolean query(int a, int b) {
		return find(a) == find(b);
	}
	
	public static void main(String[] args) {
		ConnectingGraph c = new ConnectingGraph(5);
		boolean q1 = c.query(1, 2);
		System.out.println(q1);
		c.union(1, 2);
		boolean q2 = c.query(1, 3);
		System.out.println(q2);
		c.union(2,4);
		boolean q3 = c.query(1, 4);
		System.out.println(q3);
	}
}
