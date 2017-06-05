/**
 * Refer to
 * https://segmentfault.com/a/1190000003761552
 * Reverse Words in a String II
 * Given an input string, reverse the string word by word. A word is defined as 
 * a sequence of non-space characters.
 * The input string does not contain leading or trailing spaces and the words are 
 * always separated by a single space.
 * For example, Given s = "the sky is blue", return "blue is sky the". Could you 
 * do it in-place without allocating extra space?
 *
 * Solution
 * https://discuss.leetcode.com/topic/8366/my-java-solution-with-explanation/2
 * https://segmentfault.com/a/1190000003761552
 * 双指针交换法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 这题就是Java版的Inplace做法了，先反转整个数组，再对每个词反转。
*/
public void reverseWords(char[] s) {
    // Three step to reverse
    // 1, reverse the whole sentence
    reverse(s, 0, s.length - 1);
    // 2, reverse each word
    int start = 0;
    int end = -1;
    for (int i = 0; i < s.length; i++) {
        if (s[i] == ' ') {
            reverse(s, start, i - 1);
            start = i + 1;
        }
    }
    // 3, reverse the last word, if there is only one word this will solve the corner case
    reverse(s, start, s.length - 1);
}

public void reverse(char[] s, int start, int end) {
    while (start < end) {
        char temp = s[start];
        s[start] = s[end];
        s[end] = temp;
        start++;
        end--;
    }
}
