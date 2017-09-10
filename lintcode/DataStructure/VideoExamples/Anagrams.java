/**
 * Refer to
 * http://www.lintcode.com/en/problem/anagrams/
 * Given an array of strings, return all groups of strings that are anagrams.

     Notice

    All inputs will be in lower-case

    Have you met this question in a real interview? Yes
    Example
    Given ["lint", "intl", "inlt", "code"], return ["lint", "inlt", "intl"].

    Given ["ab", "ba", "cd", "dc", "e"], return ["ab", "ba", "cd", "dc"].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/anagrams/
*/
public class Solution {
    /**
     * @param strs: A list of strings
     * @return: A list of strings
     */
    public List<String> anagrams(String[] strs) {
        List<String> result = new ArrayList<String>();
        if(strs == null || strs.length == 0) {
            return result;
        }
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for(int i = 0; i < strs.length; i++) {
            char[] chars = strs[i].toCharArray();
            // Based on sort to find unique key for each string,
            // all anagrams will have the same key
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            if(!map.containsKey(key)) {
                List<String> list = new ArrayList<String>();
                map.put(key, list);
            }
            map.get(key).add(strs[i]);
        }
        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            if(entry.getValue().size() >= 2) {
                result.addAll(entry.getValue());
            }
        }
        return result;
    }
}
