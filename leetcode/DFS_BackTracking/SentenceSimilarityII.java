/**
 * Refer to
 * http://zxi.mytechroad.com/blog/hashtable/leetcode-737-sentence-similarity-ii/
 * Given two sentences words1, words2 (each represented as an array of strings), and a list of 
   similar word pairs pairs, determine if two sentences are similar.

    For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] 
    are similar, if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], 
    ["acting","drama"], ["skills","talent"]].

    Note that the similarity relation is transitive. For example, if “great” and “good” are 
    similar, and “fine” and “good” are similar, then “great” and “fine” are similar.

    Similarity is also symmetric. For example, “great” and “fine” being similar is the same as 
    “fine” and “great” being similar.

    Also, a word is always similar with itself. For example, the sentences words1 = ["great"], 
    words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

    Finally, sentences can only be similar if they have the same number of words. So a sentence 
    like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

    Note:
    The length of words1 and words2 will not exceed 1000.
    The length of pairs will not exceed 2000.
    The length of each pairs[i] will be 2.
    The length of each words[i] and pairs[i][j] will be in the range [1, 20].
 *
 * Solution
 * http://zxi.mytechroad.com/blog/hashtable/leetcode-737-sentence-similarity-ii/
 * 题目大意：
    给你两个句子（由单词数组表示）和一些近义词对，问你这两个句子是否相似，即每组相对应的单词都要相似。
    注意相似性可以传递，比如只给你”great”和”fine”相似、”fine”和”good”相似，能推断”great”和”good”也相似。
    Idea:
    DFS / Union Find
* https://medium.com/@rebeccahezhang/leetcode-737-sentence-similarity-ii-2ca213f10115
  This is more complicated than Sentence Similarity I. To check if two words are similar given the transitivity 
  (for example If “A” = “B”, “B” = “C”, then “A” = “C”), we can use a graph to help us connect all similar words 
  together. Then for each word pairs, we start from the source word, using DFS to find the destination word. 
  In case of we do DFS to the same node twice, we can create a set to keep a record of visited nodes.

   Let’s walk through a simple example:

   words1 = [“A”, “B”, “C”], words2 = [“D”, “E”, “F”]

   pairs[][] = [“A”, “G”],[“D”, “G”],[“B”, “E”],[“C”, “F”]

   We construct the graph using a map to represent it. 
   String |set<String>
   A | [G]
   G | [A, D] 
   D | [G]
   B | [E]
   E | [B]
   C | [F]
   F | [C]

   Now we begin to check A and D. A is the source, D is the target.

   We go to the entry where the key is A, and check if this set contains D. It doesn’t, but contains G! It’s 
   possible G has a set contains D, so we change our source from A to G and keep finding out. We get the set [A, D], 
   and we need to check each word here. The first one is A again, oh we just checked this! We don’t want to go to 
   an endless loop. So we need to skip this, and we see D. It’s equal to the target! We are done. Well, on the 
   opposite, if we are not this lucky, we need to keep finding. After we go through the entire map we still can’t 
   find the target, we failed.
*/
class Solution {   
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if(words1.length != words2.length) {
            return false;   
        }
        Map<String, Set<String>> similar_words = new HashMap<>();
        for(String[] pair : pairs) {
            if(!similar_words.containsKey(pair[0])) {
                similar_words.put(pair[0], new HashSet<>());            
            }
            if(!similar_words.containsKey(pair[1])) {
                similar_words.put(pair[1], new HashSet<>());
            }
            similar_words.get(pair[0]).add(pair[1]);
            similar_words.get(pair[1]).add(pair[0]);
        }
        for(int i = 0; i < words1.length; i++) {
            if(words1[i].equals(words2[i])) {
                continue;
            }
            if(!simiar_words.containsKey(words1[i]) || !simiar_words.containsKey(words2[i])) {
                return false;
            }
            if(dfs(words1[i], words2[i], similar_words, new HashSet<String>())) {
                return false;
            }
        }
        return true;
    }
   
    private boolean dfs(String source, String target, Map<String, Set<String>> similar_words, Set<String> visited) {
        if(similar_words.get(source).contains(target)) {
            return true;
        }
        // Mark as visited
        visited.add(source);
        for(String next : similar_words.get(source)) {
            // DFS other connected node, except the mirrowed string
            if(!visited.contains(next) && hext.equals(target) || !visited.contains(next) && dfs(next, target, similar_words, visited)) {
                return true;
            }
        }
        // We've done dfs still can't find the target
        return false;
    }
}

// Re-work
// https://www.cnblogs.com/grandyang/p/8053934.html
/**
这道题是之前那道 Sentence Similarity 的拓展，那道题说单词之间不可传递，于是乎这道题就变成可以传递了，难度就增加了。不过没有关系，
还是用经典老三样来解，BFS，DFS，和 Union Find。先来看 BFS 的解法，其实这道题的本质是无向连通图的问题，首先要做的就是建立这个连通图
的数据结构，对于每个结点来说，要记录所有和其相连的结点，建立每个结点和其所有相连结点集合之间的映射，比如对于这三个相似对 
(a, b), (b, c)，和(c, d)，我们有如下的映射关系：

a -> {b}

b -> {a, c}

c -> {b, d}

d -> {c}

那么如果要验证a和d是否相似，就需要用到传递关系，a只能找到b，b可以找到a，c，为了不陷入死循环，将访问过的结点加入一个集合 visited，
那么此时b只能去，c只能去d，那么说明a和d是相似的了。用for循环来比较对应位置上的两个单词，如果二者相同，那么直接跳过去比较接下来的。
否则就建一个访问即可 visited，建一个队列 queue，然后把 words1 中的单词放入 queue，建一个布尔型变量 succ，标记是否找到，然后就是
传统的 BFS 遍历的写法了，从队列中取元素，如果和其相连的结点中有 words2 中的对应单词，标记 succ 为 true，并 break 掉。否则就将取
出的结点加入队列 queue，并且遍历其所有相连结点，将其中未访问过的结点加入队列 queue 继续循环

class Solution {
public:
    bool areSentencesSimilarTwo(vector<string>& words1, vector<string>& words2, vector<pair<string, string>> pairs) {
        if (words1.size() != words2.size()) return false;
        unordered_map<string, unordered_set<string>> m;
        for (auto pair : pairs) {
            m[pair.first].insert(pair.second);
            m[pair.second].insert(pair.first);
        }    
        for (int i = 0; i < words1.size(); ++i) {
            if (words1[i] == words2[i]) continue;
            unordered_set<string> visited;
            queue<string> q{{words1[i]}};
            bool succ = false;
            while (!q.empty()) {
                auto t = q.front(); q.pop();
                if (m[t].count(words2[i])) {
                    succ = true; break;
                }
                visited.insert(t);
                for (auto a : m[t]) {
                    if (!visited.count(a)) q.push(a);
                }
            }
            if (!succ) return false;
        }    
        return true;
    }
};

下面来看递归的写法，解题思路跟上面的完全一样，把主要操作都放到了一个递归函数中来写，参见代码如下：

class Solution {
public:
    bool areSentencesSimilarTwo(vector<string>& words1, vector<string>& words2, vector<pair<string, string>> pairs) {
        if (words1.size() != words2.size()) return false;
        unordered_map<string, unordered_set<string>> m;
        for (auto pair : pairs) {
            m[pair.first].insert(pair.second);
            m[pair.second].insert(pair.first);
        }
        for (int i = 0; i < words1.size(); ++i) {
            unordered_set<string> visited;
            if (!helper(m, words1[i], words2[i], visited)) return false;
        }
        return true;
    }
    bool helper(unordered_map<string, unordered_set<string>>& m, string& cur, string& target, unordered_set<string>& visited) {
        if (cur == target) return true;
        visited.insert(cur);
        for (string word : m[cur]) {
            if (!visited.count(word) && helper(m, word, target, visited)) return true;
        }
        return false;
    }
};

下面这种解法就是碉堡了的联合查找 Union Find 了，这种解法的核心是一个 getRoot 函数，如果两个元素属于同一个群组的话，调用 getRoot 
函数会返回相同的值。主要分为两部，第一步是建立群组关系，suppose 开始时每一个元素都是独立的个体，各自属于不同的群组。然后对于每一个
给定的关系对，对两个单词分别调用 getRoot 函数，找到二者的祖先结点，如果从未建立过联系的话，那么二者的祖先结点时不同的，此时就要建立
二者的关系。等所有的关系都建立好了以后，第二步就是验证两个任意的元素是否属于同一个群组，就只需要比较二者的祖先结点都否相同啦。是不是
有点深度学习的赶脚，先建立模型 training，然后再 test。哈哈，博主乱扯的，二者并没有什么联系。这里保存群组关系的数据结构，有时用数组，
有时用 HashMap，看输入的数据类型吧，如果输入元素的整型数的话，用 root 数组就可以了，如果是像本题这种的字符串的话，需要用 HashMap 
来建立映射，建立每一个结点和其祖先结点的映射。注意这里的祖先结点不一定是最终祖先结点，而最终祖先结点的映射一定是最重祖先结点，所以 
getRoot 函数的设计思路就是要找到最终祖先结点，那么就是当结点和其映射结点相同时返回，否则继续循环，可以递归写，也可以迭代写，这无所谓。
注意这里第一行判空是相当于初始化，这个操作可以在外面写，就是要让初始时每个元素属于不同的群组
class Solution {
public:
    bool areSentencesSimilarTwo(vector<string>& words1, vector<string>& words2, vector<pair<string, string>> pairs) {
        if (words1.size() != words2.size()) return false;
        unordered_map<string, string> m;       
        for (auto pair : pairs) {
            string x = getRoot(pair.first, m), y = getRoot(pair.second, m);
            if (x != y) m[x] = y;
        }
        for (int i = 0; i < words1.size(); ++i) {
            if (getRoot(words1[i], m) != getRoot(words2[i], m)) return false;
        }
        return true;
    }
    string getRoot(string word, unordered_map<string, string>& m) {
        if (!m.count(word)) m[word] = word;
        return word == m[word] ? word : getRoot(m[word], m);
    }
};
*/
