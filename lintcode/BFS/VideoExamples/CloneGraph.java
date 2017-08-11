import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
 *
 */

public class CloneGraph {

	 // Definition for undirected graph.
	private class UndirectedGraphNode {
	    int label;
	    ArrayList<UndirectedGraphNode> neighbors;
        UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
	}
    /**
     * @param node: A undirected graph node
     * @return: A undirected graph node
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node == null) {
            return node;
        }
        
        // Use bfs algorithm to traverse the graph and get all nodes.
        ArrayList<UndirectedGraphNode> nodes = getNodes(node);
        
        // Deep copy 
        // Copy nodes, store the old->new mapping information in a hash map
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        for(UndirectedGraphNode n : nodes) {
            map.put(n, new UndirectedGraphNode(n.label));
        }
        
        // Copy neighbors(edges)
        for(UndirectedGraphNode n : nodes) {
            // Retrieve newly deep copied nodes
            UndirectedGraphNode newNode = map.get(n);
            // Copy edges
            for(UndirectedGraphNode neighbor : n.neighbors) {
                // Retrieve newly created neighbor node from hash map
                UndirectedGraphNode newNeighbor = map.get(neighbor);
                // Add node to newly create node's neighbors
                newNode.neighbors.add(newNeighbor);
            }
        } 
        
        return map.get(node);
    }
    
    private ArrayList<UndirectedGraphNode> getNodes(UndirectedGraphNode node) {
        Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
        Set<UndirectedGraphNode> set = new HashSet<UndirectedGraphNode>();

        queue.offer(node);
        set.add(node);
        while(!queue.isEmpty()) {
            UndirectedGraphNode head = queue.poll();
            for(UndirectedGraphNode neighbor : head.neighbors) {
                if(!set.contains(neighbor)) {
                    set.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        // Refer to
        // Convert Set to List without creating new List
        // https://stackoverflow.com/a/19077309/6706875
        return new ArrayList<UndirectedGraphNode>(set);
    }

}
