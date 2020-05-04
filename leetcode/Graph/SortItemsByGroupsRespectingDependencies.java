/**
 Refer to
 https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/
 There are n items each belonging to zero or one of m groups where group[i] is the group that the i-th item belongs to and it's equal to -1 if the i-th item belongs to no group. The items and the groups are zero indexed. A group can have no item belonging to it.

Return a sorted list of the items such that:

The items that belong to the same group are next to each other in the sorted list.
There are some relations between these items where beforeItems[i] is a list containing all the items that 
should come before the i-th item in the sorted array (to the left of the i-th item).
Return any solution if there is more than one solution and return an empty list if there is no solution.

Example 1:
Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
Output: [6,3,4,1,5,2,0,7]
item        group        before
   0           -1
   1           -1             6
   2            1             5
   3            0             6
   4            0           3,6
   5            1
   6            0
   7           -1

Example 2:
Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3],[],[4],[]]
Output: []
Explanation: This is the same as example 1 except that 4 needs to be before 6 in the sorted list.
 
Constraints:
1 <= m <= n <= 3*10^4
group.length == beforeItems.length == n
-1 <= group[i] <= m-1
0 <= beforeItems[i].length <= n-1
0 <= beforeItems[i][j] <= n-1
i != beforeItems[i][j]
beforeItems[i] does not contain duplicates elements.
*/

// Solution 1: Two levels (group + item) topological sort
// Refer to
// https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/discuss/389805/JAVA-Topological-sort.-Detailed-Explanation
/**
 To briefly explain the solution approach:
Convert the problem into graph. Directed Acyclic Graph to be specific.
Perform topological sort onto graph.
The solution to the question is related to topological ordering.
Detailed Explanation:
Brief explanation to Topological Sort:
In simple terms directed acyclic graph is a directed graph with no cycles. To help in understanding conceptually or 
visually think of the graph as a dependency graph. These types of graphs have specific order known as topological 
ordering which lists the nodes of the graph in the order of dependencies. To know and practice more about these 
types of graph checkout Course Schedule.
Note: Image taken from GeeksforGeeks
As show in the image the graph is a directed acyclic graph. The given graph has mulitple topological orders: 
[5,4,2,3,1,0], [4,5,2,3,1,0], etc. As you might have noticed in the list the nodes are listed in order of their 
dependencies. To elaborate if there exists an edge from a -> b. The topological order will always have a before b.

Application to our problem:
In this problem we have two requirements.

All items belonging to the same group must be together.
Items in beforeItems for a number should always be followed by those numbers. For example if we have [1,3,4] in 
beforeItems for 5. This means that the order should always contain 1,3 & 4 before 5.
This gives us a slight idea that we can form a dependency or Directed acyclic graph using the beforeItems list. 
But it is not that simple we can have dependency between items across different groups or dependency between 
items in the same group.
Observe here that the concrete graph structure can be determined by the dependencies across different groups.

Solution approach:
To address the dependencies across different groups let's from a graph of groups. Performing topological sort of 
this graph gives us the order of the groups. Now we don't need to worry about the dependencies across different 
groups because if let's say we have group1 : [1,2,3] and group2: [4,5,6] and we have dependencies say:
1->5 & 3->4 because the topological order of the groups will always have group1 before group2 these dependencies 
are always satisfied.
Coming on to the dependencies that are in the same group we can use topological ordering of the items to form a 
relative order of items in the same group. Thus this will get us our final answer.
*/
class Solution {
    Map<Integer, List<Integer>> groupGraph;
    Map<Integer, List<Integer>> itemGraph;
    int[] groupsIndegree;
    int[] itemsIndegree;
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        groupGraph = new HashMap<Integer, List<Integer>>();
        itemGraph = new HashMap<Integer, List<Integer>>();
        // Each item belongs to a group. If an item doesn't have a group 
        // it forms it's own isolated group.
        for(int i = 0; i < group.length; i++) {
            if(group[i] == -1) {
                group[i] = m++;
            }
        }
        // The 'new' m >= original 'm' since m++
        for(int i = 0; i < m; i++) {
            groupGraph.put(i, new ArrayList<Integer>());
        }
        for(int i = 0; i < n; i++) {
            itemGraph.put(i, new ArrayList<Integer>());
        }
        groupsIndegree = new int[m];
        itemsIndegree = new int[n];
        // form graph structure across different groups and also calculate indegree.
        buildGraphForGroups(group, beforeItems);
        // form graph structure across different items and also calculate indegree.
        buildGraphForItems(beforeItems, n);
        // Topological ordering of the groups.
        List<Integer> groupsList = topologicalSortUtil(groupGraph, groupsIndegree, m);
        // Topological ordering of the items.
        List<Integer> itemsList = topologicalSortUtil(itemGraph, itemsIndegree, n);
        // Detect if there are any cycles.
        if(groupsList.size() == 0 || itemsList.size() == 0) {
            return new int[0];
        }
        // This Map holds relative order of items in the same group computed in the loop below.
        Map<Integer, List<Integer>> groupsToItems = new HashMap<Integer, List<Integer>>();
        for(int item : itemsList) {
            groupsToItems.putIfAbsent(group[item], new ArrayList<Integer>());
            groupsToItems.get(group[item]).add(item);
        }
        int[] result = new int[n];
        int index = 0;
        for(int grp : groupsList) {
            List<Integer> items = groupsToItems.getOrDefault(grp, new ArrayList<Integer>());
            for(int item : items) {
                result[index] = item;
                index++;
            }
        }
        return result;
    }
    
    // e.g
	// item 6 which belong to group 0 should before item 1 which
	// belong to group 3 (originally belong to group -1 and update
	// to it's own isolated group as 3)
    private void buildGraphForGroups(int[] group, List<List<Integer>> beforeItems) {
        for(int i = 0; i < group.length; i++) {
            int toGroup = group[i];
            List<Integer> fromItems = beforeItems.get(i);
            for(int fromItem : fromItems) {
                int fromGroup = group[fromItem];
                // If two items belong to same group then its internal relationship
                // in a group instead of two groups relationship, we can ignore
                if(toGroup != fromGroup) {
                    groupGraph.putIfAbsent(fromGroup, new ArrayList<Integer>());
                    groupGraph.get(fromGroup).add(toGroup);
                    groupsIndegree[toGroup]++;
                }
            }
        }
    }
    
    private void buildGraphForItems(List<List<Integer>> beforeItems, int n) {
        for(int toItem = 0; toItem < n; toItem++) {
            List<Integer> fromItems = beforeItems.get(toItem);
            for(int fromItem : fromItems) {
                itemGraph.putIfAbsent(fromItem, new ArrayList<Integer>());
                itemGraph.get(fromItem).add(toItem);
                itemsIndegree[toItem]++;
            }
        }
    }
    
    private List<Integer> topologicalSortUtil(Map<Integer, List<Integer>> graph, int[] indegree, int x) {
        List<Integer> list = new ArrayList<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        // Initial object on queue should be 'fromGroup' or 'fromItem' whose
        // indegree = 0
        for(int key : graph.keySet()) {
            if(indegree[key] == 0) {
                queue.offer(key);
            }
        }
        while(!queue.isEmpty()) {
            int node = queue.poll();
            x--;
            list.add(node);
            for(int neighbor : graph.get(node)) {
                indegree[neighbor]--;
                if(indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        return x == 0 ? list : new ArrayList<Integer>();
    }
    
}
