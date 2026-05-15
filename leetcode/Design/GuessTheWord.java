https://leetcode.com/problems/guess-the-word/description/
You are given an array of unique strings words where words[i] is six letters long. One word of words was chosen as a secret word.
You are also given the helper object Master. You may call Master.guess(word) where word is a six-letter-long string, and it must be from words. Master.guess(word) returns:
- -1 if word is not from words, or
- an integer representing the number of exact matches (value and position) of your guess to the secret word.
There is a parameter allowedGuesses for each test case where allowedGuesses is the maximum number of times you can call Master.guess(word).
For each test case, you should call Master.guess with the secret word without exceeding the maximum number of allowed guesses. You will get:
- "Either you took too many guesses, or you did not find the secret word." if you called Master.guess more than allowedGuesses times or if you did not call Master.guess with the secret word, or
- "You guessed the secret word correctly." if you called Master.guess with the secret word with the number of calls to Master.guess less than or equal to allowedGuesses.
The test cases are generated such that you can guess the secret word with a reasonable strategy (other than using the bruteforce method).
 
Example 1:
Input: secret = "acckzz", words = ["acckzz","ccbazz","eiowzz","abcczz"], allowedGuesses = 10
Output: You guessed the secret word correctly.
Explanation:
master.guess("aaaaaa") returns -1, because "aaaaaa" is not in words.
master.guess("acckzz") returns 6, because "acckzz" is secret and has all 6 matches.
master.guess("ccbazz") returns 3, because "ccbazz" has 3 matches.
master.guess("eiowzz") returns 2, because "eiowzz" has 2 matches.
master.guess("abcczz") returns 4, because "abcczz" has 4 matches.
We made 5 calls to master.guess, and one of them was the secret, so we pass the test case.

Example 2:
Input: secret = "hamada", words = ["hamada","khaled"], allowedGuesses = 10
Output: You guessed the secret word correctly.
Explanation: Since there are two words, you can guess both.
 
Constraints:
- 1 <= words.length <= 100
- words[i].length == 6
- words[i] consist of lowercase English letters.
- All the strings of words are unique.
- secret exists in words.
- 10 <= allowedGuesses <= 30
--------------------------------------------------------------------------------
Attempt 1: 2026-05-14
Solution 1: Design + Math (360 min)
import java.util.*;

class Solution {
    public void findSecretWord(String[] wordlist, Master master) {
        List<String> candidates = new ArrayList<>(Arrays.asList(wordlist));
        int len = wordlist[0].length(); // 所有单词长度相同
        
        // 最多猜测 30 次（题目保证存在解法）
        for (int guessCount = 0; guessCount < 30; guessCount++) {
            // 如果只剩一个候选，直接猜它
            if (candidates.size() == 1) {
                master.guess(candidates.get(0));
                return;
            }
            
            // 选择最佳猜测词：使最大分组大小最小
            String bestGuess = null;
            int bestMaxGroup = Integer.MAX_VALUE;
            
            for (String guessWord : candidates) {
                // 统计当前候选集里，与 guessWord 的匹配数分布
                int[] bucket = new int[len + 1]; // 匹配数范围 0 ~ len
                for (String cand : candidates) {
                    int match = countMatch(guessWord, cand);
                    bucket[match]++;
                }
                // 找出最大分组大小
                int maxGroup = 0;
                for (int cnt : bucket) {
                    maxGroup = Math.max(maxGroup, cnt);
                }
                // 选择使最大分组最小的单词
                if (maxGroup < bestMaxGroup) {
                    bestMaxGroup = maxGroup;
                    bestGuess = guessWord;
                }
            }
            
            // 用选出的单词进行猜测
            int result = master.guess(bestGuess);
            if (result == len) return; // 猜中了
            
            // 过滤候选集：只保留与 bestGuess 匹配数等于 result 的单词
            List<String> newCandidates = new ArrayList<>();
            for (String cand : candidates) {
                if (countMatch(bestGuess, cand) == result) {
                    newCandidates.add(cand);
                }
            }
            candidates = newCandidates;
        }
    }
    
    // 计算两个字符串相同位置相同字符的数量
    private int countMatch(String a, String b) {
        int match = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) match++;
        }
        return match;
    }
}

Refer to Deepseek
问题背景
这道题是一个“猜单词”的游戏，但规则比较特殊：
- 系统事先选定了一个秘密单词（secret word），这个单词一定存在于给你的一份单词列表（wordlist）中。
- 你不知道秘密单词是什么，但你可以猜一个单词（也必须来自列表）。
- 每次猜测后，系统会告诉你：你猜的单词与秘密单词有多少个位置上的字母完全相同（即逐字母匹配的数量，不是字母出现次数，而是位置一致）。
例如：
- 秘密单词是 "abcde"
- 你猜 "abfde"逐位比较：位置0：a==a ✓；位置1：b==b ✓；位置2：f≠c；位置3：d==d ✓；位置4：e==e ✓ → 匹配数 = 4。
目标和限制
- 你需要在最多 10 次猜测之内，准确猜出秘密单词。
- 你可以任意选择猜测顺序，利用每次返回的匹配数来排除不可能的单词，缩小范围。
- 最后必须调用 guess(word) 并让系统判断你猜对了（如果最后猜的是正确单词，API 会返回 6（假如单词长度是6）或直接通过）。
为什么需要策略？
如果你随便乱猜，可能 10 次机会根本不够。因为单词列表长度可能很大（比如 100 个单词），你需要通过信息量最大的猜测来快速减少候选集合。
核心思路（常见解法）
1.维护一个候选列表，初始为所有单词。
2.每次选择一个单词作为猜测（不一定是当前候选里的，可以选任意单词，但通常选候选里的更好）。
3.调用 guess(word) 得到匹配数 match。
4.根据 match，过滤候选列表：
只保留那些与本次猜的词恰好有 match 个位置相同的单词。
因为如果秘密单词与猜的词匹配数为 match，那么它必须满足这个条件。
5.重复上述过程，直到候选列表只剩下一个单词（那就是秘密单词），或者你直接猜中了。
如何选择最优的猜测词？
不是随便选一个词，而是选一个最能分割剩余候选集的词。常用策略：
- 对当前候选列表中的每个单词，模拟如果用它作为猜测，会得到多少种不同的 match 值（0 到 L，L 是单词长度），以及每种 match 对应多少个候选词。
- 我们希望这个猜测能尽可能平均地把候选集分成很多小组，这样最坏情况下剩余候选数最少。
- 通常选择“最大组大小最小化”的那个单词作为下一次猜测。即：对于每个单词，计算它可能产生的“最坏情况下的剩余候选数”，然后选这个数值最小的单词。
- 如果候选列表只有一个单词，直接猜它。
例子
假设单词列表：["acckzz", "ccbazz", "eiowzz", "abcczz"]，长度均为 6，秘密单词是 "acckzz"。
- 第一次猜 "acckzz"（如果你运气好直接猜中，返回 6，游戏结束）。
- 如果猜别的，比如猜 "eiowzz"，得到匹配数可能是 2（因为位置 3,4,5 的 zz 匹配？实际要看具体）。然后只保留与 "eiowzz" 匹配数为 2 的单词，排除其他。
注意事项
- 题目提供的 Master 类有 guess(String word) 方法，你需要调用它。
- 你实现的函数 findSecretWord(String[] wordlist, Master master) 不需要返回值，只需在猜中时（通过 guess 得知）或某次猜测正确时结束即可。
- 通常平台上会保证存在一种策略能在 10 次内猜中。
总结一句话
给你一个单词列表，你可以猜单词并获知与秘密单词的“逐字母匹配数”，在 10 次内通过不断缩小候选集合来猜出秘密单词。
这道题考察的是交互式猜词 + 信息论最小化最坏情况的算法设计。


