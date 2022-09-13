import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/#/description
 *  You are given a string, s, and a list of words, words, that are all of the same 
 *  length. Find all starting indices of substring(s) in s that is a concatenation 
 *  of each word in words exactly once and without any intervening characters.
	For example, given:
	s: "barfoothefoobarman"
	words: ["foo", "bar"]
 * You should return the indices: [0,9].(order does not matter). 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003903467
 * 哈希表计数法
 * 复杂度
 * 时间 O(NK) 空间 O(M)
 * 思路
 * 由于数组中所有单词的长度都是一样的，我们可以像Longest Substring with At Most Two Distinct Characters
 * 中一样，把每个词当作一个字母来看待，但是要遍历K次，K是单词的长度，因为我们要分别统计从下标0开头，从下标1开
 * 头。。。直到下标K-1开头的字符串。举例来说foobarfoo，给定数组是[foo, bar]，那我们要对foo|bar|foo搜索一次，
 * 对oob|arf|oo搜索一次，对oba|rfo|o搜索一次，我们不用再对bar|foo搜索，因为其已经包含在第一种里面了。
 * 每次搜索中，我们通过哈希表维护一个窗口，比如foo|bar|foo中，我们先拿出foo。如果foo都不在数组中，那说明根本
 * 不能拼进去，则哈希表全部清零，从下一个词开始重新匹配。但是foo是在数组中的，所以给当前搜索的哈希表计数器加上1，
 * 如果发现当前搜索中foo出现的次数已经比给定数组中foo出现的次数多了，我们就要把上一次出现foo之前的所有词都从
 * 窗口中去掉，如果没有更多，则看下一个词bar，不过在这之前，我们还要看看窗口中有多少个词了，如果词的个数等于数组
 * 中词的个数，说明我们找到了一个结果。
 * 注意
 * 先判断输入是否为空，为空直接返回空结果
 */
public class SubstringWithConcatenationOfAllWords {
	public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<Integer>();
        if(s == null || s.length() == 0 || words == null || words.length == 0) {
            return result;
        }
        // 统计数组中每个词出现的次数，放入哈希表中待用
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        for(int i = 0; i < words.length; i++) {
            if(!freqMap.containsKey(words[i])) {
                freqMap.put(words[i], 1);
            } else {
                freqMap.put(words[i], freqMap.get(words[i]) + 1);
            }
        }
        // 得到每个词的长度
        int len = words[0].length();
        // 错开位来统计
        for(int i = 0; i < len; i++) {
            // start是窗口的开始，count表明窗口内有多少词
            int start = i;
            int count = 0;
            // 建一个新的哈希表，记录本轮搜索中窗口内单词出现次数
            Map<String, Integer> currFreqMap = new HashMap<String, Integer>();
            for(int j = i; j <= s.length() - len; j += len) {
                String sub = s.substring(j, j + len);
                // 看下一个词是否是给定数组中的
                if(freqMap.containsKey(sub)) {
                    // 窗口中单词出现次数加1
                    if(!currFreqMap.containsKey(sub)) {
                        currFreqMap.put(sub, 1);
                    } else {
                        currFreqMap.put(sub, currFreqMap.get(sub) + 1);
                    }
                    count++;
                    // 如果该单词出现次数已经超过给定数组中的次数了，说明多来了一个该单词，
                    // 所以要把窗口中该单词上次出现的位置及之前所有单词给去掉
                    while(currFreqMap.get(sub) > freqMap.get(sub)) {
                        String leftMost = s.substring(start, start + len);
                        currFreqMap.put(leftMost, currFreqMap.get(leftMost) - 1);
                        start += len;
                        count--;
                    }
                    // 如果窗口内单词数和总单词数一样，则找到结果
                    if(count == words.length) {
                        String leftMost = s.substring(start, start + len);
                        currFreqMap.put(leftMost, currFreqMap.get(leftMost) - 1);
                        result.add(start);
                        start += len;
                        count--;
                    }
                // 如果截出来的单词都不在数组中，前功尽弃，重新开始
                } else {
                    currFreqMap.clear();
                    start = j + len;
                    count = 0;
                }
            }
        }
        return result;
    }
	
	public static void main(String[] args) {
		
	}
}

Attempt 1: 2022-09-11 

Solution 1 (30min, intuitive solution by using map.equals comparison, but Time Complexity: O(s_len * num_of_words * word_len) sub-optimal)

class Solution { 
    public List<Integer> findSubstring(String s, String[] words) { 
        Map<String, Integer> freq = new HashMap<String, Integer>(); 
        for(String word : words) { 
            freq.put(word, freq.getOrDefault(word, 0) + 1); 
        } 
        int wordLen = words[0].length(); 
        int wordNum = words.length; 
        int len = s.length(); 
        List<Integer> result = new ArrayList<Integer>(); 
        for(int i = 0; i <= len - wordLen * wordNum; i++) { 
            String sub = s.substring(i, i + wordLen * wordNum); 
            if(isValid(sub, freq, wordLen)) { 
                result.add(i); 
            } 
        } 
        return result; 
    } 
     
    private boolean isValid(String sub, Map<String, Integer> freq, int wordLen) { 
        Map<String, Integer> subFreq = new HashMap<String, Integer>(); 
        int subLen = sub.length(); 
        for(int i = 0; i < subLen; i += wordLen) { 
            String subWord = sub.substring(i, i + wordLen); 
            subFreq.put(subWord, subFreq.getOrDefault(subWord, 0) + 1); 
        } 
        return subFreq.equals(freq); 
    } 
}

Space Complexity: O(num_of_words + word_len)
Time Complexity: O(s_len * num_of_words * word_len) sub-optimal

Refer to
https://leetcode.com/problems/substring-with-concatenation-of-all-words/discuss/13658/Easy-Two-Map-Solution-(C++Java)/299685
https://leetcode.com/problems/substring-with-concatenation-of-all-words/discuss/13658/Easy-Two-Map-Solution-(C++Java)/398675
HashMap.contains is O(word_len) as it uses equals method internally which is character by character comparison. 
So O(s_len * num_of_words * word_len) is correct. 
Space complexity is O(num_of_words + word_len) due to HashMap(words) and substring method(creates a new copy of sub array)

Solution 2 (360min, too long, not understand till now)

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        // 统计数组中每个词出现的次数，放入哈希表中待用
        for(String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        List<Integer> result = new ArrayList<Integer>();
        // 得到每个词的长度
        int len = s.length();
        int wordLen = words[0].length();
        // 以单词的每一位为起始位置分成wordLen种情况错开位来统计
        for(int i = 0; i < wordLen; i++) {
            // start标记当前滑动窗口的起始位置
            int start = i;
            // count表明当前滑动窗口内有多少词
            int count = 0;
            // 建一个新的哈希表，记录本轮搜索中滑动窗口内单词出现次数
            Map<String, Integer> currFreqMap = new HashMap<String, Integer>();
            for(int j = i; j <= len - wordLen; j += wordLen) {
                String sub = s.substring(j, j + wordLen);
                // 判断当前词是否是给定数组中的
                if(freqMap.containsKey(sub)) {
                    currFreqMap.put(sub, currFreqMap.getOrDefault(sub, 0) + 1);
                    count++;
                    // 如果该单词出现次数已经超过给定数组中的次数了，说明多来了一个该单词，
                    // 所以要把滑动窗口中该单词上次出现的位置及之前所有单词给去掉
                    while(currFreqMap.get(sub) > freqMap.get(sub)) {
                        String leftMost = s.substring(start, start + wordLen);
                        currFreqMap.put(leftMost, currFreqMap.get(leftMost) - 1);
                        start += wordLen;
                        count--;
                    }
                    // 如果滑动窗口内单词数和总单词数一样，则找到结果
                    if(count == words.length) {
                        result.add(start);
                        // 找到结果以后把当前滑动窗口最左边的单词去掉，更新滑动窗口起始位置，
                        // 为可能的下一次找到结果预留位置(这一步比较困难，不太自然) 
                        String leftMost = s.substring(start, start + wordLen);
                        currFreqMap.put(leftMost, currFreqMap.get(leftMost) - 1);
                        start += wordLen;
                        count--;
                    }
                // 如果截出来的单词都不在数组中，前功尽弃，重新开始
                } else {
                    currFreqMap.clear();
                    start = j + wordLen;
                    count = 0;
                }
            }
        }
        return result;
    }
}

Space Complexity: O(num_of_words + word_len)
Time Complexity: O(s_len * num_of_words * word_len) sub-optimal

Below is an explicit example how above code running:
Refer to:
https://segmentfault.com/a/1190000003903467
https://leetcode.wang/leetCode-30-Substring-with-Concatenation-of-All-Words.html

s="foobarfoobarfoothebarman"
words = ["foo","bar","foo","the"]
用这个来跑一遍代码
=====================================
freqMap={foo,2}{bar,1}{the,1}
words.length=4
wordLen=3
=====================================
s="foobarfoobarfoothebarman"
i=0 foo|bar|foo
start=0 ==> 'start' used for recording window start index
-------------------------------------
j=0 -> j=3 -> j=6 -> j=9
sub="foo" -> sub="bar" -> sub="foo" -> sub="bar"
currFreqMap={foo,1} -> {foo,1}{bar,1} -> {foo,2}{bar,1} -> {foo,2}{bar,2}
count=0 -> count=1 -> count=2 -> count=3 -> count=4
-------------------------------------
When j=9, sub="bar", currFreqMap has {bar,2} > freqMap {bar,1} trigger while loop to remove previous "bar" and all words of this previous "bar"
while loop round 1:
leftMost=s.subtring(0,0+3)="foo"
currFreqMap={foo,1}{bar,2}
start=3
count=3
while loop round 2:
leftMost=s.subtring(3,3+3)="bar"
currFreqMap={foo,1}{bar,1}
start=6
count=2
Now currFreqMap has {bar,1} == freqMap {bar,1}, while loop end
-------------------------------------
Since count < words.length=4, directly back to inner for loop for next iteration
j=12 -> j=15
sub="foo" -> sub="the"
currFreqMap={foo,2}{bar,1} -> {foo,2}{bar,1}{the,1}
count=3 -> count=4
Now count == words.length=4, find a solution subtring as "foobarfoothe", its start index record by 'start=6', then go into if(count == words.length) block
leftMost=s.subtring(6,6+3)="foo"
currFreqMap={foo,1}{bar,1}{the,1}
result.add(6)
start=9
count=3
The if block remove the left most word as "foo" of solution substring "foobarfoothe", update 'start' to 9, update 'count' to 3, operation as update 'start' and 'count' is for preparing next iteration, especially update 'start' from 6 to 9 is preparing the next potential solution substring start index, then if block end, go to next iteration of inner for loop
-------------------------------------
j=18
sub="bar"
currFreqMap={foo,1}{bar,2}{the,1}
count=4
When j=18, sub="bar", currFreqMap has {bar,2} > freqMap {bar,1} trigger while loop to remove previous "bar" and all words of previous "bar" (but different than while loop triggered before, current previous "bar" is the one start at index=9 recorded and updated by 'start=9' in previous iteration's if block, not the already removed one start at index=3)
while loop round 1:
leftMost=s.subtring(9,9+3)="bar"
currFreqMap={foo,1}{bar,1}{the,1}
start=12
count=3
Now currFreqMap has {bar,1} == freqMap {bar,1}, while loop end
-------------------------------------
Since count < words.length=4, directly back to inner for loop for next iteration
j=21
sub="man"
since freqMap not contains "man", all remain stuff in currFreqMap(for example here as {foo,1}{bar,1}{the,1}) are in vain, we have to skip this word "man" to start a fresh lookup
currFreqMap.clear() to clean up remain stuff
start=j+wordLen=21+3=24 to skip "man" on original string and record as a new substring start index
count=0 to reset matched word count in window
After reset then go back to inner for loop for next itearation
-------------------------------------
j=24 is the end of original string s="foobarfoobarfoothebarman", so first outside for loop done, we only find a solution subtring as "foobarfoothe" start at index=6
=====================================
s="foobarfoobarfoothebarman"
oob|arf|oo
=====================================
s="barfoothefoobarman"
oba|rfo|o
=====================================
For the late 2 secanrio we can use outside for loop to do the same



Solution 3 (20min, the MOST intuitive solution and use same template as not fixed length sliding window, 
	    literally it is a fixed length sliding window problem, since we know in the end the candidate 
	    window size should be same as wordLen * wordNum (words from input array), but practically when 
	    we create sliding window it is more similar to not fxied length sliding window, since we always 
	    try to expand right end pointer when not find a window and shrink left end pointer when encounter 
	    extra frequency words)

class Solution {
    // We can treat a word in words like a single character, then the problem
    // more close to a traditional not fixed length sliding window style, before
    // we find a candidate window exactly match same given characters(words)
    // permutation, the right end pointer keep moving forward, when encounter 
    // extra frequency characters(words) we shrink the left end pointer till
    // remove extra frequency characters(words) from the candidate window
    public List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> freqMap = new HashMap<String, Integer>();
        for(String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        List<Integer> result = new ArrayList<Integer>();
        int len = s.length();
        int wordLen = words[0].length();
        // Gategory into 'wordLen' kinds of starting position
        for(int m = 0; m < wordLen; m++) {
            // 'i' is left end pointer, 'j' is right end pointer
            // s[i,j) presents the sliding window to find the required substring
            int i = m;
            // 'count' is how many words in current window
            int count = 0;
            Map<String, Integer> currFreqMap = new HashMap<String, Integer>(); 
            for(int j = m; j <= len - wordLen; j += wordLen) {
                String sub = s.substring(j, j + wordLen);
                currFreqMap.put(sub, currFreqMap.getOrDefault(sub, 0) + 1);
                count++;
                // Remove extra frequency characters(words) from the current window
                // by shrink left end pointer, each shrink step length is a word length,
                // during shrink we have to update current frequency and words count
                while(currFreqMap.getOrDefault(sub, 0) > freqMap.getOrDefault(sub, 0)) {
                    String leftMost = s.substring(i, i + wordLen);
                    currFreqMap.put(leftMost, currFreqMap.get(leftMost) - 1);
                    i += wordLen;
                    count--;
                }
                // Find one solution when count of words in current window match total words
                if(count == words.length) {
                    result.add(i);
                }
            }
        }
        return result;
    }
}

Space Complexity: O(num_of_words + word_len) 
Time Complexity: O(s_len * num_of_words)

Refer to
https://leetcode.com/problems/substring-with-concatenation-of-all-words/discuss/13656/An-O(N)-solution-with-detailed-explanation/204718
the idea to apply sliding window to this question is cool. post a more sliding window like code

    // sliding window
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (words.length == 0) return ans;

        Map<String, Integer> cnt = new HashMap<>();
        for (String w : words) cnt.put(w, 1 + cnt.getOrDefault(w, 0));

        final int wl = words[0].length();
        for (int i = 0; i < wl; i++) { // run sliding window wl times
            int l = i;
            int r = i; // s[l,r) contains only words
            Map<String, Integer> window = new HashMap<>();
            int windowCnt = 0;

            while (r + wl <= s.length()) {
                final String w = s.substring(r, r + wl);
                r += wl;
                window.put(w, 1 + window.getOrDefault(w, 0));
                windowCnt++;

                while (window.getOrDefault(w, 0) > cnt.getOrDefault(w, 0)) {
                    final String leadingWord = s.substring(l, l + wl);
                    window.put(leadingWord, window.get(leadingWord) - 1);
                    l = l + wl;
                    windowCnt--;
                }

                if (windowCnt == words.length) ans.add(l);
            }
        }

        return ans;
    }
