import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/group-anagrams/#/description
 * Given an array of strings, group anagrams together.

	For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
	Return:
	
	[
	  ["ate", "eat","tea"],
	  ["nat","tan"],
	  ["bat"]
	]
	
	Note: All inputs will be in lower-case
 *	
 * Solution
 * https://segmentfault.com/a/1190000003740929
 * 排序哈希表法
 * 复杂度
 * 时间 O(NKlogK) 空间 O(N)
 * 思路
 * 判断两个词是否是变形词，最简单的方法是将两个词按字母排序，看结果是否相同。这题中我们要将所有同为一个变形词
 * 词根的词归到一起，最快的方法则是用哈希表。所以这题就是结合哈希表和排序。我们将每个词排序后，根据这个键值，
 * 找到哈希表中相应的列表，并添加进去。为了满足题目字母顺序的要求，我们输出之前还要将每个列表按照内部的词排序
 * 一下。可以直接用Java的Collections.sort()这个API。
 * 注意
 * 将字符串排序的技巧是先将其转换成一个char数组，对数组排序后再转回字符串
 * 
 * https://discuss.leetcode.com/topic/24494/share-my-short-java-solution
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<List<String>>();
        if(strs.length == 0) {
            return result;
        }
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for(String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            List<String> list = map.get(key);
            if(list == null) {
                list = new ArrayList<String>();
            }
            list.add(str);
            map.put(key, list);
        }
        for(String key : map.keySet()) {
            List<String> curr = map.get(key);
            Collections.sort(curr);
            result.add(curr);
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    }
}

