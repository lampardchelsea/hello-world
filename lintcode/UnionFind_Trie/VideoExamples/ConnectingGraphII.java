/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-590-connecting-graph-ii.html
 * Given n nodes in a graph labeled from 1 to n. There is no edges in the graph at beginning.

	You need to support the following method:
	1. connect(a, b), add an edge to connect node a and node b.
	2. query(a), Returns the number of connected component nodes which include node a.
	
	
	Example
	5 // n = 5
	query(1) return 1
	connect(1, 2)
	query(1) return 2
	connect(2, 4)
	query(1) return 3
	connect(1, 4)
	query(1) return 3
 * 
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-590-connecting-graph-ii.html
 * implement一下Union Find weighted + compression。对于每一个node我们要存一下和他连通
 * 的个数并且在每次union的时候更新。
 */
public class ConnectingGraphII {
    private int[] father;
    private int[] size;
    public ConnectingGraphII(int n) {
    	father = new int[n + 1];
    	size = new int[n + 1];
    	for(int i = 1; i < n + 1; i++) {
    		father[i] = i;
    		size[i] = 1;
    	}
    }
    
    public int find(int x) {
    	if(father[x] == x) {
    		return x;
    	}
    	return find(father[x]);
    }
    
    public void connect(int a, int b) {
    	int root_a = find(a);
    	int root_b = find(b);
    	if(root_a != root_b) {
    		father[root_a] = root_b;
    		// Add a's size to b
    		size[root_b] += size[root_a];
    	}
    }
    
    public boolean query(int a, int b) {
    	return find(a) == find(b);
    }
    
    public int getSize(int a) {
    	return size[a];
    }
    
    public int query(int a) {
    	// Should not write as getSize(a), because on background,
    	// we finally retrieve the given a's father's corresponding
    	// size, this also can be understand by all nodes share
    	// one parent treat as 1 component and share their common
    	// father's size
    	return getSize(find(a));
    }
    
    public static void main(String[] args) {
    	ConnectingGraphII g = new ConnectingGraphII(5);
    	int q1 = g.query(1);
    	System.out.println(q1);
    	g.connect(1, 2);
    	int q2 = g.query(1);
    	System.out.println(q2);
    	g.connect(2, 4);
    	int q3 = g.query(1);
    	System.out.println(q3);
    	g.connect(1, 4);
    	int q4 = g.query(1);
    	System.out.println(q4);
    }
}

