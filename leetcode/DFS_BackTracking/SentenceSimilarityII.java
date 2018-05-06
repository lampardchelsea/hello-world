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
*/
class Solution {
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
            
    }
}
