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
/**
http://www.noteanddata.com/leetcode-127-Word-Ladder-java-bidirectional-bfs-solution-note.html
 题目 leetcode 127 Word Ladder
输入一个开始单词和结束单词， 然后在输入一个单词数组，
定义一个transform操作是从一个单词变换到另外一个单词， 然后这个变换是每次只能替换单词里面的一个字符到另外一个字符
问，最少需要经过多少个变换才可以从开始的单词到结束的单词， 返回的长度是变换列表的长度
解题思路分析
这个题目前面写了一个单方向的BFS, http://www.noteanddata.com/leetcode-127-Word-Ladder-java-bfs-solution-note.html， 
但是运行时间比较慢， 更好的做法是双向BFS， 这次是第一次学写双向BFS
双向BFS的思路是
a. 从开始的节点走一层
b. 从结束的节点走一层
c. 交替上面两个步骤， 所以就是每次都走一层，看看走到这一层的时候是否会遇到对方的节点
双向BFS的步骤具体如下:
a. 把开始和结束的节点都加入到两个queue里面
b. 每次选择一个queue， 然后遍历一层, 如果遍历的下一层有对方节点，就退出
c. 选择另外一个queue， 然后遍历一层， 如果遍历的下一层有对方节点，就退出
d. 如果任何一个queue都空了，也就是没有办法继续向下一层，这时候如果还没有遇到可以结束的，就结束

java题解代码
这里用一个set来表示已经访问过的节点来去重
每次遍历的时候，用来一个小优化，就是每次从queue.size比较小的queue开始遍历，没有严格按照一个方向一层的方法来遍历，这样效果会更好一点
小细节要注意的是，双向BFS，每次还是要完整的做一层遍历，不能只做一个元素的遍历，否则就不对了
具体例子可以参考http://www.cppblog.com/Yuan/archive/2011/02/23/140553.html
*/
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

