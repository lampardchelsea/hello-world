/**
 * Refer to
 * http://zxi.mytechroad.com/blog/hashtable/leetcode-734-sentence-similarity/
 * Given two sentences words1, words2 (each represented as an array of strings), and a list of similar 
   word pairs pairs, determine if two sentences are similar.

    For example, “great acting skills” and “fine drama talent” are similar, if the similar word pairs 
    are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].

    Note that the similarity relation is not transitive. For example, if “great” and “fine” are similar, 
    and “fine” and “good” are similar, “great” and “good” are not necessarily similar.

    However, similarity is symmetric. For example, “great” and “fine” being similar is the same as “fine” 
    and “great” being similar.

    Also, a word is always similar with itself. For example, the sentences words1 = ["great"], 
    words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

    Finally, sentences can only be similar if they have the same number of words. So a sentence like 
    words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

    Note:
    The length of words1 and words2 will not exceed 1000.
    The length of pairs will not exceed 2000.
    The length of each pairs[i] will be 2.
    The length of each words[i] and pairs[i][j] will be in the range [1, 20].
 *
 *
 * Solution
 * http://zxi.mytechroad.com/blog/hashtable/leetcode-734-sentence-similarity/
 * 题目大意:
    给你两个句子（由单词数组表示）和一些近义词对，问你这两个句子是否相似，即每组相对应的单词都要相似。
    注意相似性不能传递，比如给只你”great”和”fine”相似、”fine”和”good”相似，这种情况下”great”和”good”不相似。
    Idea:
    Use hashtable to store mapping from word to its similar words.
*/
class Solution {
    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
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
        for(int i = 0; i < words1.length; ++i) {
            if(words1[i].equals(words2[i])) {
                continue;
            }
            if(!similar_words.containsKey(words1[i])) {
                return false;
            }
            if(!similar_words.get(words1[i]).contains(words2[i])) {
                return false;
            }
        }
        return true;
    }
}

// Re-work
// Refer to
// https://www.lintcode.com/problem/sentence-similarity/description
// https://www.cnblogs.com/grandyang/p/8016251.html
/**
这道题给了我们两个句子，问这两个句子是否是相似的。判定的条件是两个句子的单词数要相同，而且每两个对应的单词要是相似度，
这里会给一些相似的单词对，这里说明了单词对的相似具有互逆性但是没有传递性。看到这里博主似乎已经看到了Follow up了，
加上传递性就是一个很好的拓展。那么这里没有传递性，就使得问题变得很容易了，我们只要建立一个单词和其所有相似单词的集合
的映射就可以了，比如说如果great和fine类似，且great和good类似，那么就有下面这个映射：

great -> {fine, good}

所以我们在逐个检验两个句子中对应的单词时就可以直接去映射中找，注意有可能遇到的单词对时反过来的，比如fine和great，所以
我们两个单词都要带到映射中去查找，只要有一个能查找到，就说明是相似的，反之，如果两个都没查找到，说明不相似，直接返回false
*/
public class Solution {
    /**
     * @param words1: a list of string
     * @param words2: a list of string
     * @param pairs: a list of string pairs
     * @return: return a boolean, denote whether two sentences are similar or not
     */
    public boolean isSentenceSimilarity(String[] words1, String[] words2, List<List<String>> pairs) {
        if(words1.length != words2.length) {
            return false;
        }
        Map<String, Set<String>> similar_words = new HashMap<String, Set<String>>();
        for(List<String> pair : pairs) {
            similar_words.putIfAbsent(pair.get(0), new HashSet<String>());
            similar_words.get(pair.get(0)).add(pair.get(1));
            similar_words.putIfAbsent(pair.get(1), new HashSet<String>());
            similar_words.get(pair.get(1)).add(pair.get(0));
        }
        for(int i = 0; i < words1.length; i++) {
            if(words1[i].equals(words2[i])) {
                continue;
            }
            // Below 2 validations we only validate as one end(only check from words1[i] as key
            // in map, since we initially create map as mutual add, so when we find one end not
            // exist or exist but not able to build mapping, the other end is automatically
            // same situation just reversely showing, so no need to check again)
            // e.g 
            // map.put("fine").add("good")
            // map.put("good").add("fine")
            // if 'fine' not able to find as key, then 'good' must not able to find as key
            // or even it able to find as key but not able set a mapping as good --> fine
            // so only need to check if 'fine' exist in map and 'fine' has mapping as
            // fine --> good is enough
            if(!similar_words.containsKey(words1[i])) {
                return false;
            }
            if(!similar_words.get(words1[i]).contains(words2[i])) {
                return false;
            }
        }
        return true;
    }
}
