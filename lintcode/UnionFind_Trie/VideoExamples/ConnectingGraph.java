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
 * 这题就是implement一下Union Find
 * 
 * 并查集的两个优化（秩优化+路径压缩）
 * http://blog.csdn.net/kalilili/article/details/43014623
 * 路径压缩
 * 寻找祖先时采用递归，但是一旦元素一多起来，或退化成一条链，每次GetFather都将会使用O（n）的复杂度，这显然不是我们想要的。
 * 对此，我们必须要进行路径压缩，即我们找到最久远的祖先时“顺便”把它的子孙直接连接到它上面。这就是路径压缩了。使用路径压缩的
 * 代码如下，时间复杂度基
 * 本可以认为是常数的。
 * 路径压缩可以采用迭代和递归方式递归方式实现简单但是有些题目会爆栈的。
 * 
 * 并查集路径压缩方法
 * http://www.cnblogs.com/vongang/archive/2011/07/31/2122763.html
 * 
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
