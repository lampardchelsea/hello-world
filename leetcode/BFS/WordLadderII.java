import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Refer to
 * https://leetcode.com/problems/word-ladder-ii/description/
 * Given two words (beginWord and endWord), and a dictionary's word list, find all shortest 
 * transformation sequence(s) from beginWord to endWord, such that:
 * Only one letter can be changed at a time
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
	For example,
	Given:
	beginWord = "hit"
	endWord = "cog"
	wordList = ["hot","dot","dog","lot","log","cog"]
	Return
	  [
	    ["hit","hot","dot","dog","cog"],
	    ["hit","hot","lot","log","cog"]
	  ]
 * 
 * Note:
 * Return an empty list if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 * UPDATE (2017/1/20):
 * The wordList parameter had been changed to a list of strings (instead of a set of strings). 
 * Please reload the code definition to get the latest changes.
 *
 */
public class WordLadderII {
    public List < List < String >> findLadders(String beginWord, String endWord, List < String > wordList) {
        List < List < String >> results = new ArrayList < List < String >> ();
        Set < String > dictionary = new HashSet < String > (wordList);
        // Neighbors of every node
        Map < String, List < String >> nodeNeighbors = new HashMap < String, List < String >> ();
        // Distance of every node from start node
        Map < String, Integer > distance = new HashMap < String, Integer > ();
        List < String > solution = new ArrayList < String > ();
        // Add 'beginWord' into dictionary, otherwise will throw NullPointException
        // on line81 because of no entry for 'beginWord' in map
        dictionary.add(beginWord);
        bfs(beginWord, endWord, dictionary, nodeNeighbors, distance);
        dfs(beginWord, endWord, nodeNeighbors, distance, solution, results);
        return results;
    }

    // BFS: Trace every node's distance from the start node(level by level) in BFS,
    // we can be sure that the distance of each node is the shortest one, because 
    // once we have visited a node, we update the distance, if we first met one 
    // node, it must be the shortest distance, if we met the node again, its 
    // distance must not be less than the distance we first met and set.
    private void bfs(String start, String end, Set < String > dictionary, Map < String, List < String >> nodeNeighbors, Map < String, Integer > distance) {
        // For each word in dictionary set up hashmap to store all of its adjacent neighbors
        for (String str: dictionary) {
            nodeNeighbors.put(str, new ArrayList < String > ());
        }
        Queue < String > queue = new LinkedList < String > ();
        // Add very start of beginWord to start BFS.
        queue.offer(start);
        // Distance from start to itself is 0
        distance.put(start, 0);
        while (!queue.isEmpty()) {
            // You just want to loop each level's neighbors which is just the size 
            // of the queue at the current level.
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                // Get the current node's neighbors
                List < String > currentNodesNeighbors = getNeighbors(cur, dictionary);
                // Loop through current node's neighbors
                for (String neighbor: currentNodesNeighbors) {
                    // Add all the neighbors into map's current node entry
                    nodeNeighbors.get(cur).add(neighbor);
                    // If we have not previously visited this node
                    if (!distance.containsKey(neighbor)) {
                        // Increment distance we have traveled
                        distance.put(neighbor, distance.get(cur) + 1);
                        // If reach the end
                        if (end.equals(neighbor)) {
                            // We don't want to return as there may well be more paths from 
                            // start to end. So we need to break instead.
                            break;
                        } else {
                            // Add neighbor for next level
                            queue.offer(neighbor);
                        }
                    }
                }
            }
        }
    }

    // DFS: output all paths with the shortest distance.
    // This is backtracking
    private void dfs(String beginWord, String endWord, Map < String, List < String >> nodeNeighbors, Map < String, Integer > distance, List < String > individualSequence, List < List < String >> results) {
        individualSequence.add(beginWord);
        // Base case for beginWord that has reached endWord and individualSequence has the complete 
        // path from the beginWord to endWord. So add to the final results list.
        if (endWord.equals(beginWord)) {
            // Deep copy
            results.add(new ArrayList < String > (individualSequence));
        }
        // We have not reached the end so we must perform DFS on the neighbors.
        for (String neighbor: nodeNeighbors.get(beginWord)) {
            // This is just in case that the next node is the next level of current node， otherwise 
            // it can be one of the parent nodes of current node，or it is not the shortest node 
            // Since in BFS, we record both the next level nodes and the parent node as neighbors 
            // of current node. use distance.get(beginWord)+1 we can make sure the path is the 
            // shortest one.
            if (distance.get(neighbor) == distance.get(beginWord) + 1) {
                dfs(neighbor, endWord, nodeNeighbors, distance, individualSequence, results);
            }
        }

        // Back tracking
        // You keep on going down the 'tree' until you get to 'cog'. Add the sequence to the results.
        // Then you need to backtrack the neighbors and travel down the tree on a different path. 
        // In this example go to '3' then travel down that path to 'cog' again.
        // Keep removing until you backtrack to the very start of beginWord which in this case is "hit".
        individualSequence.remove(individualSequence.size() - 1);
    }


    // Find all next level nodes
    private List < String > getNeighbors(String node, Set < String > dictionary) {
        List < String > neighbors = new ArrayList < String > ();
        for (int i = 0; i < node.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == node.charAt(i)) {
                    continue;
                }
                String neighbor = replace(node, i, c);
                if (dictionary.contains(neighbor)) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    private String replace(String node, int index, char c) {
        char[] chars = node.toCharArray();
        chars[index] = c;
        return new String(chars);
    }

    public static void main(String[] args) {
        WordLadderII w = new WordLadderII();
        String beginWord = "hit";
        String endWord = "cog";
        String[] list = {"hot","dot","dog","lot","log","cog"};
        List < String > wordList = Arrays.asList(list);
        List < List < String >> result = w.findLadders(beginWord, endWord, wordList);
        for (List < String > path: result) {
            for (String word: path) {
                System.out.print(word + "->");
            }
            System.out.println(" ");
        }
    }
}
