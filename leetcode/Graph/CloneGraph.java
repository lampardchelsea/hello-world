/** 
 * Refer to
 * http://www.lintcode.com/en/problem/clone-graph/
 * Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
 * How we serialize an undirected graph:
 * Nodes are labeled uniquely.
 * We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
 * As an example, consider the serialized graph {0,1,2#1,2#2,2}.
 * The graph has a total of three nodes, and therefore contains three parts as separated by #.
 * First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
 * Second node is labeled as 1. Connect node 1 to node 2.
 * Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
 * Visually, the graph looks like the following:
		   1
		  / \
		 /   \
		0 --- 2
		     / \
		     \_/
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/clone-graph/
*/

/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node == null) {
            return node;
        }
            
        // Find all nodes with BFS
        List<UndirectedGraphNode> nodes = getNodes(node);
        
        // Deep copy all original nodes into map, one original to one new
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        for(UndirectedGraphNode n : nodes) {
            map.put(n, new UndirectedGraphNode(n.label));
        }
        
        // Copy edges
        for(UndirectedGraphNode n : nodes) {
            UndirectedGraphNode newNode = map.get(n);
            // Copy original node's neighbors to new node's neighbors
            for(UndirectedGraphNode neighbor : n.neighbors) {
                UndirectedGraphNode newNeighbor = map.get(neighbor);
                newNode.neighbors.add(newNeighbor);
            }
        }
      
        // Should not return original node directly, must return map.get(node)
        // as newly created copy
        // Refer to
        // Convert Set to List without creating new List
        // https://stackoverflow.com/a/19077309/6706875
        return map.get(node);
    }
    
    public List<UndirectedGraphNode> getNodes(UndirectedGraphNode node) {
        Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        Set<UndirectedGraphNode> set = new HashSet<UndirectedGraphNode>();
        queue.offer(node);
        set.add(node);
        while(!queue.isEmpty()) {
            UndirectedGraphNode head = queue.poll();
            for(UndirectedGraphNode n : head.neighbors) {
                if(!set.contains(n)) {
                    set.add(n);

                    queue.offer(n);
                }
            }
        }
        return new ArrayList<UndirectedGraphNode>(set);
    }
}
