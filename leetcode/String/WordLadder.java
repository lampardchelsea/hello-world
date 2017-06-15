/**
 * Refer to
 * https://leetcode.com/problems/word-ladder/#/description
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length 
 * of shortest transformation sequence from beginWord to endWord, such that:
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 
   For example,
    Given:
    beginWord = "hit"
    endWord = "cog"
    wordList = ["hot","dot","dog","lot","log","cog"]
    As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
    return its length 5.
    
 * Note:
    Return 0 if there is no such transformation sequence.
    All words have the same length.
    All words contain only lowercase alphabetic characters.
    You may assume no duplicates in the word list.
    You may assume beginWord and endWord are non-empty and are not the same.
 * UPDATE (2017/1/20):
 * The wordList parameter had been changed to a list of strings (instead of a set of strings). 
 * Please reload the code definition to get the latest changes. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003698569
 * 广度优先搜索
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 因为要求最短路径，如果我们用深度优先搜索的话必须遍历所有的路径才能确定哪个是最短的，而用广度优先搜索的话，
 * 一旦搜到目标就可以提前终止了，而且根据广度优先的性质，我们肯定是先通过较短的路径搜到目标。另外，为了避免
 * 产生环路和重复计算，我们找到一个存在于字典的新的词时，就要把它从字典中移去。这么做是因为根据广度优先，
 * 我们第一次发现词A的路径一定是从初始词到词A最短的路径，对于其他可能再经过词A的路径，我们都没有必要再计算了。
 * 
 * https://discuss.leetcode.com/topic/17890/another-accepted-java-solution-bfs
*/
public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int step = 2;
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        while(!queue.isEmpty()) {
            // 控制size来确保一次while循环只计算同一层的节点，有点像二叉树level order遍历
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                String currWord = queue.poll();
                for(int j = 0; j < currWord.length(); j++) {
                    for(char letter = 'a'; letter <= 'z'; letter++) {
                        StringBuilder sb = new StringBuilder(currWord);
                        sb.setCharAt(j, letter);
                        // Refer to
                        // https://discuss.leetcode.com/topic/17890/another-accepted-java-solution-bfs/16
                        // First, very thanks for your solution, it makes me easier to understand this question/solution. 
                        // I found two things: 1. The line "if (word.equals(end)) return level + 1; " has a bug, need to 
                        // check if word in dict also. 2. Plus the OJ give time limit exceeded error after I fix #1. 
                        // Add one more important check here
                        // wordList.contains(sb.toString())
                        // "hit" -> "hot" -> "dot" -> "dog" -> "cog"
                        // ["hot","dot","dog","lot","log"]
                        if(endWord.equals(sb.toString()) && wordList.contains(sb.toString())) {
                            return step;
                        } else if(wordList.contains(sb.toString())) {
                            wordList.remove(sb.toString());
                            queue.offer(sb.toString());
                        }
                    }
                }
            }
            step++;
        }
        return 0;
    }
}
