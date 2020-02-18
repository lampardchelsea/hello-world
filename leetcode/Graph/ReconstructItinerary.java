import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://leetcode.com/problems/reconstruct-itinerary/description/
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], 
 * reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. 
 * Thus, the itinerary must begin with JFK.

	Note:
	If there are multiple valid itineraries, you should return the itinerary that has the smallest 
	lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a 
	smaller lexical order than ["JFK", "LGB"].
	All airports are represented by three capital letters (IATA code).
	You may assume all tickets form at least one valid itinerary.
	
	Example 1:
	tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
	Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
	
	Example 2:
	tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
	Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
	
	Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. 
	But it is larger in lexical order.
 *
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5183210.html
 * 这道题给我们一堆飞机票，让我们建立一个行程单，如果有多种方法，取其中字母顺序小的那种方法。
 * 这道题的本质是有向图的遍历问题，那么LeetCode关于有向图的题只有两道Course Schedule
 * 和Course Schedule II，而那两道是关于有向图的顶点的遍历的，而本题是关于有向图的边的遍历。
 * 每张机票都是有向图的一条边，我们需要找出一条经过所有边的路径，那么DFS不是我们的不二选择。
 * 先来看递归的结果，我们首先把图建立起来，通过邻接链表来建立。由于题目要求解法按字母顺序小的，
 * 那么我们考虑用multiset，可以自动排序。等我们图建立好了以后，从节点JFK开始遍历，只要当前
 * 节点映射的multiset里有节点，我们取出这个节点，将其在multiset里删掉，然后继续递归遍历这个
 * 节点，由于题目中限定了一定会有解，那么等图中所有的multiset中都没有节点的时候，我们把当前
 * 节点存入结果中，然后再一层层回溯回去，将当前节点都存入结果，那么最后我们结果中存的顺序和
 * 我们需要的相反的，我们最后再翻转一下即可
 *
 *
 * https://discuss.leetcode.com/topic/36383/share-my-solution
 * All the airports are vertices and tickets are directed edges. 
 * Then all these tickets form a directed graph.
 * 
 * The graph must be Eulerian since we know that a Eulerian path exists.
 * Thus, start from "JFK", we can apply the Hierholzer's algorithm to find a 
 * Eulerian path in the graph which is a valid reconstruction.
 * 
 * Since the problem asks for lexical order smallest solution, we can put the 
 * neighbors in a min-heap. In this way, we always visit the smallest possible 
 * neighbor first in our trip.
 * 
 * 
 * https://www.youtube.com/watch?v=n4nF2rb3LpY
 * 
 * 
 * How do I use the new computeIfAbsent function?
 * https://stackoverflow.com/questions/19278443/how-do-i-use-the-new-computeifabsent-function
 */
public class ReconstructItinerary {
/**
	 * E.g 
	 * For tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]] 
	 * We need convert to a directed graph first, JFK to SFO is one way, 
	 * JFK to ATL, ATL to SFO are both two ways
	 * 
	 *        JFK  ->  SFO
	 *           ||   ||
	 *             ATL
	 *             
	 * Time Complexity: O(nlogn)
	 * Space Complexity: O(n)           
	 */
	Map<String, PriorityQueue<String>> map;
	LinkedList<String> result;
	public List<String> findItinerary(String[][] tickets) {
        // Build directed graph
		// Since PriorityQueue naturally sort as ascending order,
		// no need to create new comparator
		map = new HashMap<String, PriorityQueue<String>>();
		// Must use linked list
		result = new LinkedList<String>();
		for(String[] ticket : tickets) {
			map.computeIfAbsent(ticket[0], k -> new PriorityQueue<String>()).add(ticket[1]);
		}
		/**
		 * OR refer to
		 * https://discuss.leetcode.com/topic/36383/share-my-solution
		 * for(String[] ticket : tickets) {
               flights.putIfAbsent(ticket[0], new PriorityQueue<>());
               flights.get(ticket[0]).add(ticket[1]);
           }
		 */
		// DFS from "JFK"
		helper("JFK");
		return result;
    }
	
	private void helper(String departure) {
		PriorityQueue<String> arrivals = map.get(departure);
		while(arrivals != null && !arrivals.isEmpty()) {
			helper(arrivals.poll());
		}
		// First 'departure' add onto result is the last airport,
		// reversely add required to get path from first airport
		result.addFirst(departure);
	}
	
	public static void main(String[] args) {
		ReconstructItinerary r = new ReconstructItinerary();
		String[][] tickets = new String[][]{{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}};
		List<String> result = r.findItinerary(tickets);
		for(String s : result) {
			System.out.print(s + " ");
		}
	}

}

// More clear solution from DFS + Backtracking
// Refer to
// https://leetcode.com/problems/reconstruct-itinerary/discuss/78799/Very-Straightforward-DFS-Solution-with-Detailed-Explanations
/**
 The nice thing about DFS is it tries a path, and if that's wrong (i.e. path does not lead to solution), 
 DFS goes one step back and tries another path. It continues to do so until we've found the correct path 
 (which leads to the solution). You need to always bear this nice feature in mind when utilizing DFS to 
 solve problems.
 
 In this problem, the path we are going to find is an itinerary which:
 (1) uses all tickets to travel among airports
 (2) preferably in ascending lexical order of airport code

 Keep in mind that requirement 1 must be satisfied before we consider 2. If we always choose the airport 
 with the smallest lexical order, this would lead to a perfectly lexical-ordered itinerary, but pay attention 
 that when doing so, there can be a "dead end" somewhere in the tickets such that we are not able visit all 
 airports (or we can't use all our tickets), which is bad because it fails to satisfy requirement 1 of this 
 problem. Thus we need to take a step back and try other possible airports, which might not give us a perfectly 
 ordered solution, but will use all tickets and cover all airports.

 Thus it's natural to think about the "backtracking" feature of DFS. We start by building a graph and then sorting 
 vertices in the adjacency list so that when we traverse the graph later, we can guarantee the lexical order of 
 the itinerary can be as good as possible. When we have generated an itinerary, we check if we have used all our 
 airline tickets. If not, we revert the change and try another ticket. We keep trying until we have used all our tickets.
*/
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        // Build graph
        Map<String, List<String>> graph = new HashMap<String, List<String>>();
        for(int i = 0; i < tickets.size(); i++) {
            String from = tickets.get(i).get(0);
            String to = tickets.get(i).get(1);
            if(!graph.containsKey(from)) {
                graph.put(from, new ArrayList<String>());
            }
            graph.get(from).add(to);
        }
        for(String airport: graph.keySet()) {
            Collections.sort(graph.get(airport));
        }
        // DFS
        List<String> result = new ArrayList<String>();
        helper("JFK", graph, result);
        return result;
    }
    
    private boolean helper(String curr, Map<String, List<String>> graph, List<String> result) {
        // Base case
        // When you have no airports in the graph
        if(graph.keySet().size() == 0) {
            result.add(curr);
            return true;
        }
        // Airport is visited or this airport is not a "from" airport
        //if(!graph.containsKey(curr)) {
        //    return false;
        //}
        List<String> to_list = graph.get(curr);
        if(to_list != null) {
            for(int i = 0; i < to_list.size(); i++) {
                String to = to_list.get(i);
                result.add(curr);
                // remove "to" airport from curr airport's list
                graph.get(curr).remove(i);
                if(graph.get(curr).size() == 0) {
                    graph.remove(curr);
                }
                if(helper(to, graph, result)) {
                    return true;
                }
                // backtracking since if current path not work, then restore graph
                graph.putIfAbsent(curr, new ArrayList<String>());
                graph.get(curr).add(i, to);
                result.remove(result.size() - 1);
            }            
        }
        return false;
    }
}



// Wrong solution with PriorityQueue + Backtracking
// Should not use PriorityQueue to do backtracking, because when you find the current path not good and try to do backtracking 
// by add back the removed airport, the issue for PriorityQueue is it will always restore to original order since its sort automatically,
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        // Build graph
        Map<String, PriorityQueue<String>> graph = new HashMap<String, PriorityQueue<String>>();
        for(int i = 0; i < tickets.size(); i++) {
            String from = tickets.get(i).get(0);
            String to = tickets.get(i).get(1);
            if(!graph.containsKey(from)) {
                graph.put(from, new PriorityQueue<String>());
            }
            graph.get(from).add(to);
        }
        // DFS
        List<String> result = new ArrayList<String>();
        helper("JFK", graph, result);
        return result;
    }
    
    private boolean helper(String curr, Map<String, PriorityQueue<String>> graph, List<String> result) {
        // Base case
        // When you have no airports in the graph
        if(graph.keySet().size() == 0) {
            result.add(curr);
            return true;
        }
        // Airport is visited or this airport is not a "from" airport
        //if(!graph.containsKey(curr)) {
        //    return false;
        //}
        PriorityQueue<String> to_list = graph.get(curr);
        if(to_list != null) {
            for(int i = 0; i < to_list.size(); i++) {
                String to = to_list.poll();
                result.add(curr);
                //graph.get(curr).remove(to); --> no need to remove again since already polled from PQ
                if(graph.get(curr).size() == 0) {
                    graph.remove(curr);
                }
                if(helper(to, graph, result)) {
                    return true;
                }
                graph.putIfAbsent(curr, new PriorityQueue<String>());
                graph.get(curr).add(to); // here is the wrong place, when add back to PQ, it restore to original order and deadly loop start
                result.remove(result.size() - 1);
            }            
        }
        return false;
    }
}





