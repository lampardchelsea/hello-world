/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-591-connecting-graph-iii.html
 * Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.

	You need to support the following method:
	1. connect(a, b), add an edge to connect node a and node b.
	2. query(), Returns the number of connected component in the graph
	
	
	Example
	5 // n = 5
	query() return 5
	connect(1, 2)
	query() return 4
	connect(2, 4)
	query() return 3
	connect(1, 4)
	query() return 3
 * 
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-591-connecting-graph-iii.html
 * implement一下Union Find。
           初始一共有n个component。每次union，如果发现需要连到一起了，那么component就减少了一个。最后搞完就是剩下有多少个
   connected component。
 */
public class ConnectingGraphIII {
	private int numComponent;
	private int[] father;
	
	public ConnectingGraphIII(int n) {
		numComponent = n;
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
	
	public int getComponent() {
		return numComponent;
	}
	
	public void connect(int a, int b) {
		int root_a = find(a);
		int root_b = find(b);
		if(root_a != root_b) {
			father[root_a] = root_b;
			numComponent--;
		}
	}
	
	public int query() {
		return getComponent();
	}
	
	public static void main(String[] args) {
		ConnectingGraphIII g = new ConnectingGraphIII(5);
		int q1 = g.query();
		System.out.println(q1);
		g.connect(1, 2);
		int q2 = g.query();
		System.out.println(q2);
		g.connect(2, 4);
		int q3 = g.query();
		System.out.println(q3);
		g.connect(1, 4);
		int q4 = g.query();
		System.out.println(q4);
	}
}

