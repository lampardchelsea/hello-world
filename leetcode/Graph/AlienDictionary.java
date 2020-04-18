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
