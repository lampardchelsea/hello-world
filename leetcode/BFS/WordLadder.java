/**
 Refer to
 https://leetcode.com/problems/word-ladder/
 Given two words (beginWord and endWord), and a dictionary's word list, find the length of 
 shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.

Example 1:
Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Example 2:
Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
*/

// Solution: Bi-directional BFS
// https://leetcode.com/problems/word-ladder/discuss/40711/Two-end-BFS-in-Java-31ms.
// http://theoryofprogramming.com/2018/01/21/bidirectional-search/
// http://www.noteanddata.com/leetcode-127-Word-Ladder-java-bidirectional-bfs-solution-note.html
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Check if the endWord in wordList or not, if not then no way to reach from beginWord
        if(!wordList.contains(endWord)) {
            return 0;
        }
        int len = 1;
        Set<String> set = new HashSet<String>();
        Queue<String> beginQueue = new LinkedList<String>();
        Queue<String> endQueue = new LinkedList<String>();
        beginQueue.offer(beginWord);
        endQueue.offer(endWord);
        while(!beginQueue.isEmpty() && !endQueue.isEmpty()) {
            if(beginQueue.size() > endQueue.size()) {
                Queue<String> temp = beginQueue;
                beginQueue = endQueue;
                endQueue = temp;
            }
            Queue<String> nextQueue = new LinkedList<String>();
            int size = beginQueue.size();
            for(int i = 0; i < size; i++) {
                String word = beginQueue.poll();
                for(String nextWord : getNextWord(word, wordList)) {
                    if(!set.contains(nextWord)) {
                        set.add(nextWord);
                        nextQueue.offer(nextWord);    
                    }        
                    if(endQueue.contains(nextWord)) {
                        return len + 1;
                    }
                }
            }
            beginQueue = nextQueue;
            len++;
        }
        return 0;
    }
    
    private List<String> getNextWord(String word, List<String> wordList) {
        List<String> nextWords = new ArrayList<String>();
        for(int i = 0; i < word.length(); i++) {
            for(char c = 'a'; c <= 'z'; c++) {
                if(word.charAt(i) == c) {
                    continue;
                }
                String nextWord = replace(word, c, i);
                if(wordList.contains(nextWord)) {
                    nextWords.add(nextWord);
                }
            }
        }
        return nextWords;
    }
    
    private String replace(String s, char c, int i) {
        char[] chars = s.toCharArray();
        chars[i] = c;
        return new String(chars);
    }
}

// Wrong solution here
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Check if the endWord in wordList or not, if not then no way to reach from beginWord
        if(!wordList.contains(endWord)) {
            return 0;
        }
        int len = 1;
        Set<String> set = new HashSet<String>();
        Queue<String> beginQueue = new LinkedList<String>();
        Queue<String> endQueue = new LinkedList<String>();
        beginQueue.offer(beginWord);
        endQueue.offer(endWord);
        while(!beginQueue.isEmpty() && !endQueue.isEmpty()) {
            if(beginQueue.size() > endQueue.size()) {
                Queue<String> temp = beginQueue;
                beginQueue = endQueue;
                endQueue = temp;
            }
            Queue<String> nextQueue = new LinkedList<String>();
            int size = beginQueue.size();
            for(int i = 0; i < size; i++) {
                String word = beginQueue.poll();
                for(String nextWord : getNextWord(word, wordList)) {
                    // Below section not equal to correct solution, since 'continue'
                    // will also skip the check for next statement as
                    // 'endQueue.contains(nextWord)' if set already has the nextWord
                    // and nextWord is exactly in endQueue.
                    // e.g
                    // "hit"
                    // "cog"
                    // ["hot","dot","dog","lot","log","cog"]
                    if(set.contains(nextWord)) {
                        continue; 
                    }        
                    if(endQueue.contains(nextWord)) {
                        return len + 1;
                    }
                    set.add(nextWord);
                    nextQueue.offer(nextWord);   
                }
            }
            beginQueue = nextQueue;
            len++;
        }
        return 0;
    }

