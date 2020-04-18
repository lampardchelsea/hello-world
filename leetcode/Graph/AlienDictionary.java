/**
 Refer to
 https://www.lintcode.com/problem/alien-dictionary/description
 There is a new alien language which uses the latin alphabet. However, the order among letters are 
 unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted 
 lexicographically by the rules of this new language. Derive the order of letters in this language.
 
  Example 1:
  Given the following words in dictionary,
  [
    "wrt",
    "wrf",
    "er",
    "ett",
    "rftt"
  ]
  The correct order is: "wertf".
  
  Example 2:
  Given the following words in dictionary,
  [
    "z",
    "x"
  ]
  The correct order is: "zx".
  
  Example 3:
  Given the following words in dictionary,
  [
    "z",
    "x",
    "z"
  ]
  The order is invalid, so return "".
  
  Note:
  You may assume all letters are in lowercase.
  You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
  If the order is invalid, return an empty string.
  There may be multiple valid order of letters, return any one of them is fine.
*/

// Solution 1: Tological Sort (BFS)
// Refer to
// https://zhuhan0.blogspot.com/2017/06/leetcode-269-alien-dictionary.html
/**
 Topological sort: 
 Applicable for DAG (Directed Acycle Graph)
 1.Build graph: a map of character -> set of character.
 2.Also get in-degrees for each character. In-degrees will be a map of character -> integer.
 3.Topological sort:
 Loop through in-degrees. Offer the characters with in-degree of 0 to queue.
 While queue is not empty:
 Poll from queue. Append to character to result string.
 Decrease the in-degree of polled character's children by 1.
 If any child's in-degree decreases to 0, offer it to queue.
 At last, if result string's length is less than the number of vertices, that means there is a cycle in my graph. 
 The order is invalid.
 Say the number of characters in the dictionary (including duplicates) is n. Building the graph takes O(n). 
 Topological sort takes O(V + E). V <= n. E also can't be larger than n. So the overall time complexity is O(n).
*/

// https://www.cnblogs.com/grandyang/p/5250200.html
/**
 这道题让给了一些按“字母顺序”排列的单词，但是这个字母顺序不是我们熟知的顺序，而是另类的顺序，让根据这些“有序”的
 单词来找出新的字母顺序，这实际上是一道有向图遍历的问题，跟之前的那两道 Course Schedule II 和 Course Schedule 
 的解法很类似，我们先来看 BFS 的解法，需要一个 TreeSet 来保存可以推测出来的顺序关系，比如题目中给的例子1，
 可以推出的顺序关系有：
 
 t->f
 w->e
 r->t
 e->r
 
 这些就是有向图的边，对于有向图中的每个结点，计算其入度，然后从入度为0的结点开始 BFS 遍历这个有向图，然后将遍历路径
 保存下来返回即可。下面来看具体的做法：

 根据之前讲解，需用 TreeSet 来保存这些 pair，还需要一个 HashSet 来保存所有出现过的字母，需要一个一维数组 in 来保存
 每个字母的入度，另外还要一个 queue 来辅助拓扑遍历，我们先遍历单词集，把所有字母先存入 HashSet，然后我们每两个相邻
 的单词比较，找出顺序 pair，然后根据这些 pair 来赋度，把 HashSet 中入度为0的字母都排入 queue 中，然后开始遍历，
 如果字母在 TreeSet 中存在，则将其 pair 中对应的字母的入度减1，若此时入度减为0了，则将对应的字母排入 queue 中并且
 加入结果 res 中，直到遍历完成，看结果 res 和 ch 中的元素个数是否相同，若不相同则说明可能有环存在，返回空字符串
*/

// https://massivealgorithms.blogspot.com/2019/04/leetcode-269-alien-dictionary.html
// https://www.youtube.com/watch?v=LA0X_N-dEsg
public class Solution {
    /**
     * @param words: a list of words
     * @return: a string which is correct order
     */
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
        int[] indegree = new int[26];
        buildGraph(words, graph, indegree);
        String order = topologicalSort(graph, indegree);
        return order.length() == graph.size() ? order : "";
    }
    
    private void buildGraph(String[] words, Map<Character, Set<Character>> graph, int[] indegree) {
        for(String word : words) {
            for(Character c : word.toCharArray()) {
                graph.put(c, new HashSet<Character>());
            }
        }
        // Compare the first different character between two words
        // e.g a -> b, a has an out edge point to b
        for(int i = 1; i < words.length; i++) {
            String first = words[i - 1];
            String second = words[i];
            int length = Math.min(first.length(), second.length());
            for(int j = 0; j < length; j++) {
                char parent = first.charAt(j);
                char child = second.charAt(j);
                if(parent != child) {
                    if(!graph.get(parent).contains(child)) {
                        graph.get(parent).add(child);
                        indegree[child - 'a']++;
                    }
                    break;
                }
            }
        }
    }
    
    private String topologicalSort(Map<Character, Set<Character>> graph, int[] indegree) {
        Queue<Character> queue = new LinkedList<Character>();
        for(Character c : graph.keySet()) {
            if(indegree[c - 'a'] == 0) {
                queue.offer(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!queue.isEmpty()) {
            Character c = queue.poll();
            sb.append(c);
            if(graph.get(c) == null || graph.get(c).size() == 0) {
                continue;
            }
            for(char neighbor : graph.get(c)) {
                indegree[neighbor - 'a']--;
                if(indegree[neighbor - 'a'] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        return sb.toString();
    }
}

// If want to pass test case on LintCode
// Input
// Show Difference
// ["zy","zx"]
// Output
// "yzx"
// Expected
// "yxz"
// There may be multiple valid order of letters, return the smallest in lexicographical order
// we have to use PriorityQueue instead of LinkedList to guarantee lexicological order
// Refer to
// https://www.lintcode.com/problem/alien-dictionary/discuss
public class Solution {
    /**
     * @param words: a list of words
     * @return: a string which is correct order
     */
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
        int[] indegree = new int[26];
        buildGraph(words, graph, indegree);
        String order = topologicalSort(graph, indegree);
        return order.length() == graph.size() ? order : "";
    }
    
    private void buildGraph(String[] words, Map<Character, Set<Character>> graph, int[] indegree) {
        for(String word : words) {
            for(Character c : word.toCharArray()) {
                graph.putIfAbsent(c, new HashSet<Character>());
            }
        }
        for(int i = 1; i < words.length; i++) {
            String first = words[i - 1];
            String second = words[i];
            int length = Math.min(first.length(), second.length());
            for(int j = 0; j < length; j++) {
                char parent = first.charAt(j);
                char child = second.charAt(j);
                if(parent != child) {
                    if(!graph.get(parent).contains(child)) {
                        graph.get(parent).add(child);
                        indegree[child - 'a']++;
                    }
                    break;
                }
            }
        }
    }
    
    private String topologicalSort(Map<Character, Set<Character>> graph, int[] indegree) {
        Queue<Character> queue = new PriorityQueue<Character>();
        StringBuilder sb = new StringBuilder();
        for(Character c : graph.keySet()) {
            if(indegree[c - 'a'] == 0) {
                queue.offer(c);
            }
        }
        while(!queue.isEmpty()) {
            Character c = queue.poll();
            sb.append(c);
            if(graph.get(c) == null || graph.get(c).size() == 0) {
                continue;
            }
            for(char neighbor : graph.get(c)) {
                indegree[neighbor - 'a']--;
                if(indegree[neighbor - 'a'] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        return sb.toString();
    }
}

// Solution 2: Topological Sort (DFS)
// Refer to
// https://www.cnblogs.com/jcliblogger/p/4758761.html
// https://www.cnblogs.com/grandyang/p/5250200.html
/**
 下面来看一种 DFS 的解法，思路和 BFS 的很类似，需要建立一个二维的 bool 数组g，为了节省空间，
 不必像上面的解法中一样使用一个 HashSet 来记录所有出现过的字母，可以直接用这个二维数组来保存这个信息，
 只要 g[i][i] = true，即表示位置为i的字母存在。同时，这个二维数组还可以保存顺序对儿的信息，只要 
 g[i][j] = true，就知道位置为i的字母顺序在位置为j的字母前面。找顺序对儿的方法跟上面的解法完全相同，
 之后就可以进行 DFS 遍历了。由于 DFS 遍历需要标记遍历结点，那么就用一个 visited 数组，由于是深度优先
 的遍历，并不需要一定要从入度为0的结点开始遍历，而是从任意一个结点开始都可以，DFS 会遍历到出度为0的
 结点为止，加入结果 res，然后回溯加上整条路径到结果 res 即可
*/
public class Solution {
    /**
     * @param words: a list of words
     * @return: a string which is correct order
     */
    public String alienOrder(String[] words) {
        Map<Character, List<Character>> graph = new HashMap<Character, List<Character>>();
        buildGraph(words, graph);
        String order = topologicalSort(graph);
        return order.length() == graph.size() ? order : "";
    }
    
    private void buildGraph(String[] words, Map<Character, List<Character>> graph) {
        for(String word : words) {
            for(Character c : word.toCharArray()) {
                graph.put(c, new ArrayList<Character>());
            }
        }
        for(int i = 1; i < words.length; i++) {
            String first = words[i - 1];
            String second = words[i];
            int length = Math.min(first.length(), second.length());
            for(int j = 0; j < length; j++) {
                char parent = first.charAt(j);
                char child = second.charAt(j);
                if(parent != child) {
                    if(!graph.get(parent).contains(child)) {
                        graph.get(parent).add(child);
                    }
                    break;
                }
            }
        }
    }
    
    private String topologicalSort(Map<Character, List<Character>> graph) {
        List<Character> path = new ArrayList<Character>();
        boolean[] visited = new boolean[26];
        boolean[] dp = new boolean[26];
        // Scan index from 0 to 25 which mapping to 'a' to 'z'
        for(int i = 0; i < 26; i++) {
            if(!helper(path, i, graph, visited, dp)) {
                return "";
            }
        }
        // Reverse the path based on topological sort DFS style definition
        // Refer to
        // https://efficientcodeblog.wordpress.com/2017/11/28/topological-sort-dfs-bfs-and-dag/
        // https://www.cnblogs.com/grandyang/p/5250200.html
        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
        }
        return sb.toString();
    }
    
    private boolean helper(List<Character> path, int i, Map<Character, List<Character>> graph, boolean[] visited, boolean[] dp) {
        if(visited[i]) {
            return true;
        }
        if(dp[i]) {
            return false;
        }
        // Convert back from 0 to 25 index number to alphabetic char
        char c = (char)(i + 'a');
        // Check if graph contains this char or not first
        if(graph.containsKey(c)) {
            dp[i] = true;
            for(int j = 0; j < graph.get(c).size(); j++) {
                int neighbor = graph.get(c).get(j) - 'a';
                if(!helper(path, neighbor, graph, visited, dp)) {
                    return false;
                }
            }
            dp[i] = false;
            visited[i] = true;
            path.add(c);
        }
        return true;
    }
}

