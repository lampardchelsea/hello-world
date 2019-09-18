/**
 * Given a pattern and a string str, find if str follows the same pattern.
 * Here follow means a full match, such that there is a bijection between a 
 * letter in pattern and a non-empty substring in str.
 * Examples: pattern = "abab", str = "redblueredblue" should return true. 
 * pattern = "aaaa", str = "asdasdasdasd" should return true. pattern = "aabb", 
 * str = "xyzabcxzyabc" should return false. Notes: You may assume both pattern 
 * and str contains only lowercase letters.
*/
// Solution Without Debugging Message
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordPattern {
	public static boolean wordPatternMatch(String pattern, String str) {
		Map<Character, String> map = new HashMap<Character, String>();
		Set<String> set = new HashSet<String>();
		
		return dfs(map, set, pattern, str, 0, 0);
	} 
	
	public static boolean dfs(Map<Character, String> map, Set<String> set, String pattern, String str, int i, int j) {
		if(i == pattern.length() && j == str.length()) {
			return true;
		}
		
		if(i == pattern.length() || j == str.length()) {
			return false;
		}
		
		char c = pattern.charAt(i);
		for(int k = j; k < str.length(); k++) {
			String subStr = str.substring(j, k + 1);
			if(!map.containsKey(c) && !set.contains(subStr)) {
				map.put(c, subStr);
				set.add(subStr);
				if(dfs(map, set, pattern, str, i + 1, k + 1)) {
					return true;
				}
				map.remove(c);
				set.remove(subStr);
			} else if(map.containsKey(c) && map.get(c).equals(subStr)) {
				if(dfs(map, set, pattern, str, i + 1, k + 1)) {
					return true;
				}
			}
		}
			
        return false;
	}
	
	public static void main(String[] args) {
		String pattern = "abab";
		String str = "redblueredblue";
//		String pattern = "aaaa";
//		String str = "asdasdasdasd";
//		String pattern = "aabb";
//		String str = "xyzabxzyabc";
//		String pattern = "itwasthebestoftimes";
//		String str = "ittwaastthhebesttoofttimesss";
		boolean result = wordPatternMatch(pattern, str);
		System.out.println(result);
	}
}


// Full Solution With Debugging Model
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 1.How to print out DFS stack trace refer to
 * https://www.cis.upenn.edu/~matuszek/cit594-2012/Pages/backtracking.html
 * 
 * Debugging helps to understand dfs better, also will stop illusion on some situation,
 * e.g when pattern = "abab", str = "redblueredblue", the first assumption is 
 * 'a' <-> "red", 'b' <-> "blue", but real situation is 'a' <-> "r", 'b' <-> "edblue",
 * after print out debugging with indent, the level shows up very clear.
 * 
 *  pattern:abab, str:redblueredblue
	Entering dfs(map_content: set_content: pattern_index:0 str_index:0)
	|  Try to put in map:{a,r}, sub_str_index between:0,1
	|  Entering dfs(map_content:{a,r} set_content:{r} pattern_index:1 str_index:1)
	|  |  Try to put in map:{b,e}, sub_str_index between:1,2
	|  |  Entering dfs(map_content:{b,e}{a,r} set_content:{e}{r} pattern_index:2 str_index:2)
	|  |  |  Try to put in map:{a,d}, sub_str_index between:2,3
	|  |  |  Try to put in map:{a,db}, sub_str_index between:2,4
	|  |  |  Try to put in map:{a,dbl}, sub_str_index between:2,5
	|  |  |  Try to put in map:{a,dblu}, sub_str_index between:2,6
	|  |  |  Try to put in map:{a,dblue}, sub_str_index between:2,7
	|  |  |  Try to put in map:{a,dbluer}, sub_str_index between:2,8
	|  |  |  Try to put in map:{a,dbluere}, sub_str_index between:2,9
	|  |  |  Try to put in map:{a,dbluered}, sub_str_index between:2,10
	|  |  |  Try to put in map:{a,dblueredb}, sub_str_index between:2,11
	|  |  |  Try to put in map:{a,dblueredbl}, sub_str_index between:2,12
	|  |  |  Try to put in map:{a,dblueredblu}, sub_str_index between:2,13
	|  |  |  Try to put in map:{a,dblueredblue}, sub_str_index between:2,14
	|  |  dfs returns false: all separate cases not applicable
	|  |  map_remove:{b,e}, set_remove:{e}
	|  |  Try to put in map:{b,ed}, sub_str_index between:1,3
	|  |  Entering dfs(map_content:{b,ed}{a,r} set_content:{r}{ed} pattern_index:2 str_index:3)
	|  |  |  Try to put in map:{a,b}, sub_str_index between:3,4
	|  |  |  Try to put in map:{a,bl}, sub_str_index between:3,5
	|  |  |  Try to put in map:{a,blu}, sub_str_index between:3,6
	|  |  |  Try to put in map:{a,blue}, sub_str_index between:3,7
	|  |  |  Try to put in map:{a,bluer}, sub_str_index between:3,8
	|  |  |  Try to put in map:{a,bluere}, sub_str_index between:3,9
	|  |  |  Try to put in map:{a,bluered}, sub_str_index between:3,10
	|  |  |  Try to put in map:{a,blueredb}, sub_str_index between:3,11
	|  |  |  Try to put in map:{a,blueredbl}, sub_str_index between:3,12
	|  |  |  Try to put in map:{a,blueredblu}, sub_str_index between:3,13
	|  |  |  Try to put in map:{a,blueredblue}, sub_str_index between:3,14
	|  |  dfs returns false: all separate cases not applicable
	|  |  map_remove:{b,ed}, set_remove:{ed}
	|  |  Try to put in map:{b,edb}, sub_str_index between:1,4
	|  |  Entering dfs(map_content:{b,edb}{a,r} set_content:{r}{edb} pattern_index:2 str_index:4)
	|  |  |  Try to put in map:{a,l}, sub_str_index between:4,5
	|  |  |  Try to put in map:{a,lu}, sub_str_index between:4,6
	|  |  |  Try to put in map:{a,lue}, sub_str_index between:4,7
	|  |  |  Try to put in map:{a,luer}, sub_str_index between:4,8
	|  |  |  Try to put in map:{a,luere}, sub_str_index between:4,9
	|  |  |  Try to put in map:{a,luered}, sub_str_index between:4,10
	|  |  |  Try to put in map:{a,lueredb}, sub_str_index between:4,11
	|  |  |  Try to put in map:{a,lueredbl}, sub_str_index between:4,12
	|  |  |  Try to put in map:{a,lueredblu}, sub_str_index between:4,13
	|  |  |  Try to put in map:{a,lueredblue}, sub_str_index between:4,14
	|  |  dfs returns false: all separate cases not applicable
	|  |  map_remove:{b,edb}, set_remove:{edb}
	|  |  Try to put in map:{b,edbl}, sub_str_index between:1,5
	|  |  Entering dfs(map_content:{b,edbl}{a,r} set_content:{r}{edbl} pattern_index:2 str_index:5)
	|  |  |  Try to put in map:{a,u}, sub_str_index between:5,6
	|  |  |  Try to put in map:{a,ue}, sub_str_index between:5,7
	|  |  |  Try to put in map:{a,uer}, sub_str_index between:5,8
	|  |  |  Try to put in map:{a,uere}, sub_str_index between:5,9
	|  |  |  Try to put in map:{a,uered}, sub_str_index between:5,10
	|  |  |  Try to put in map:{a,ueredb}, sub_str_index between:5,11
	|  |  |  Try to put in map:{a,ueredbl}, sub_str_index between:5,12
	|  |  |  Try to put in map:{a,ueredblu}, sub_str_index between:5,13
	|  |  |  Try to put in map:{a,ueredblue}, sub_str_index between:5,14
	|  |  dfs returns false: all separate cases not applicable
	|  |  map_remove:{b,edbl}, set_remove:{edbl}
	|  |  Try to put in map:{b,edblu}, sub_str_index between:1,6
	|  |  Entering dfs(map_content:{b,edblu}{a,r} set_content:{r}{edblu} pattern_index:2 str_index:6)
	|  |  |  Try to put in map:{a,e}, sub_str_index between:6,7
	|  |  |  Try to put in map:{a,er}, sub_str_index between:6,8
	|  |  |  Try to put in map:{a,ere}, sub_str_index between:6,9
	|  |  |  Try to put in map:{a,ered}, sub_str_index between:6,10
	|  |  |  Try to put in map:{a,eredb}, sub_str_index between:6,11
	|  |  |  Try to put in map:{a,eredbl}, sub_str_index between:6,12
	|  |  |  Try to put in map:{a,eredblu}, sub_str_index between:6,13
	|  |  |  Try to put in map:{a,eredblue}, sub_str_index between:6,14
	|  |  dfs returns false: all separate cases not applicable
	|  |  map_remove:{b,edblu}, set_remove:{edblu}
	|  |  Try to put in map:{b,edblue}, sub_str_index between:1,7
	|  |  Entering dfs(map_content:{b,edblue}{a,r} set_content:{edblue}{r} pattern_index:2 str_index:7)
	|  |  |  Try to put in map:{a,r}, sub_str_index between:7,8
	|  |  |  Entering dfs(map_content:{b,edblue}{a,r} set_content:{edblue}{r} pattern_index:3 str_index:8)
	|  |  |  |  Try to put in map:{b,e}, sub_str_index between:8,9
	|  |  |  |  Try to put in map:{b,ed}, sub_str_index between:8,10
	|  |  |  |  Try to put in map:{b,edb}, sub_str_index between:8,11
	|  |  |  |  Try to put in map:{b,edbl}, sub_str_index between:8,12
	|  |  |  |  Try to put in map:{b,edblu}, sub_str_index between:8,13
	|  |  |  |  Try to put in map:{b,edblue}, sub_str_index between:8,14
	|  |  |  |  Entering dfs(map_content:{b,edblue}{a,r} set_content:{edblue}{r} pattern_index:4 str_index:14)
	|  |  |  |  dfs returns true
	|  |  |  dfs returns true
	|  |  dfs returns true
	|  dfs returns true
	dfs returns true
	true
 * 
 * 2.How to implement refer to
 * https://segmentfault.com/a/1190000003827151
 * 因为目标字符串可以任意划分，所以我们不得不尝试所有可能性。这里通过深度优先搜索的回溯法，对于pattern中每个字母，
 * 在str中尝试所有的划分方式，如果划分出来的子串可以用这个字母映射，或者可以建立一个新的字母和字符串的映射关系，
 * 我们就继续递归判断下一个pattern中的字母。
 * But this implementation has side effect, so in our implementation has two improvement
 * (1)Change dfs from return void to return boolean
 * (2)Every level will pass in map and set
 * 
 * The similar improvement refer to
 * http://www.chenguanghe.com/word-pattern-ii/
 * http://www.aichengxu.com/view/10021446
 * 
 * Also, some idea comes from
 * http://wangyuanvivien.blogspot.com/2016/06/leetcode-290-291-word-pattern-i-ii.html
 * 
 * 3. How print out the contents of a HashMap ?
 * http://stackoverflow.com/questions/3605237/how-print-out-the-contents-of-a-hashmapstring-string-in-ascending-order-based
 */
public class WordPattern {
	// Switch to true if need print out debugging message
	private static final boolean debugging = true;
	private static String indent = "";
	
	public static boolean wordPatternMatch(String pattern, String str) {
		// Initial a map to store projection between character and string
		Map<Character, String> map = new HashMap<Character, String>();
		
		// Initial a set to filter duplicate projection between character and string,
		// make sure string only mapping to one character
		Set<String> set = new HashSet<String>();
		
		return dfs(map, set, pattern, str, 0, 0);
	} 
	
	public static boolean dfs(Map<Character, String> map, Set<String> set, String pattern, String str, int i, int j) {
		enter(map, set, i, j);
		
		// Use two pointers i and j to search on pattern and str, if both reach the end then success
		if(i == pattern.length() && j == str.length()) {
			yes();
			return true;
		}
		
		// If either only one pointer on pattern or str reach the end then failed
		if(i == pattern.length() || j == str.length()) {
			String reason = "index on pattern or str out of boundary";
			no(reason);
			return false;
		}
		
		char c = pattern.charAt(i);
		
		// Try all separate cases from current position to the end of str
		for(int k = j; k < str.length(); k++) {
			String subStr = str.substring(j, k + 1);
			
			// Print out each key-value pair try to put on map
			tryToPutOnMap(j, k, c, subStr);
			
			// If current substring not mapped then put it on both map and set
			if(!map.containsKey(c) && !set.contains(subStr)) {
				map.put(c, subStr);
				set.add(subStr);
				// Recursively check remain part of pattern and string by 
				// increasing pointer as 1 on both with updated map and set
				if(dfs(map, set, pattern, str, i + 1, k + 1)) {
					yes();
					return true;
				}
				// If not successfully find the result, need to remove current
				// character-string pair on map and set. e.g if map contains
				// {a,r}{b,e} now, and try all cases as {a,d}{a,db}{a,dbl}....
				// {a,dblueredblue} onto map but not success because of mapping
				// conflict(such as 'a' already map to "r" here), we need to
				// remove {b,e} on map, because if we keep it on map, even we
				// try all remain string separate cases, no case success(no 
				// separate case match 'a' map to "r"), so back tracing to 
				// current level recursion and remove this pair
				remove(map, c, subStr);
				map.remove(c);
				set.remove(subStr);
			} else if(map.containsKey(c) && map.get(c).equals(subStr)) {
				// If map contains this projection but key-value pair matched
				// already existing pair on map, keep on checking
				if(dfs(map, set, pattern, str, i + 1, k + 1)) {
					yes();
					return true;
				}
			} else {
				// If map not contains this project and also key-value pair not matched
				// already existing pair on map, try next separate case if exists
				// Note: This else branch can be elimiated as not necessary, will
				// automatically go to next loop
				continue;
			}
		}
			
		String reason = "all separate cases not applicable";
		no(reason);
        return false;
	}
	
	public static void enter(Map<Character, String> map, Set<String> set, int i, int j) {
		if(debugging) {
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<Character, String> entry : map.entrySet()) {
				sb.append("{" + entry.getKey() + "," + entry.getValue() + "}");
			}
			String mapContent = sb.toString();
			StringBuilder sb1 = new StringBuilder();
			for(String s : set) {
				sb1.append("{" + s + "}");
			}
			String setContent = sb1.toString();
			System.out.println(indent + "Entering dfs(" + "map_content:" + mapContent + " set_content:" + setContent + " pattern_index:" + i + " str_index:" + j + ")");
			indent = indent + "|  ";
		}
	}
	
	public static void tryToPutOnMap(int j, int k, Character c, String subStr) {
		if(debugging) {
			System.out.println(indent + "Try to put in map:{" + c + "," + subStr + "}, sub_str_index between:" + j + "," + (k + 1));
		}
	}
	
	public static void remove(Map<Character, String> map, Character c, String subStr) {
		if(debugging) {
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<Character, String> entry : map.entrySet()) {
				if(entry.getKey() == c) {
					sb.append("{" + entry.getKey() + "," + entry.getValue() + "}");
				}
			}
			String entryToRemove = sb.toString();
			System.out.println(indent + "map_remove:" + entryToRemove + ", set_remove:" + "{" + subStr + "}");
		}
	}
	
	public static void yes() {
		if(debugging) {
			indent = indent.substring(3);
			System.out.println(indent + "dfs returns true");
		}
	}
	
	public static void no(String reason) {
		if(debugging) {
			indent = indent.substring(3);
			System.out.println(indent + "dfs returns false: " + reason);
		}
	}
	
	public static void main(String[] args) {
		String pattern = "abab";
		String str = "redblueredblue";
//		String pattern = "aaaa";
//		String str = "asdasdasdasd";
//		String pattern = "aabb";
//		String str = "xyzabxzyabc";
//		String pattern = "itwasthebestoftimes";
//		String str = "ittwaastthhebesttoofttimesss";
		
		System.out.println("pattern:" + pattern + ", " + "str:" + str);
		boolean result = wordPatternMatch(pattern, str);
		System.out.println(result);
	}
}


// New try
// Refer to
// https://segmentfault.com/a/1190000003827151
/**
 因为目标字符串可以任意划分，所以我们不得不尝试所有可能性。这里通过深度优先搜索的回溯法，对于pattern中每个字母，
 在str中尝试所有的划分方式，如果划分出来的子串可以用这个字母映射，或者可以建立一个新的字母和字符串的映射关系，
 我们就继续递归判断下一个pattern中的字母。
*/
public class Solution {
    
    Map<Character, String> map;
    Set<String> set;
    boolean res;
    
    public boolean wordPatternMatch(String pattern, String str) {
        // 和I中一样，Map用来记录字符和字符串的映射关系
        map = new HashMap<Character, String>();
        // Set用来记录哪些字符串被映射了，防止多对一映射
        set = new HashSet<String>();
        res = false;
        // 递归回溯
        helper(pattern, str, 0, 0);
        return res;
    }
    
    public void helper(String pattern, String str, int i, int j){
        // 如果pattern匹配完了而且str也正好匹配完了，说明有解
        if(i == pattern.length() && j == str.length()){
            res = true;
            return;
        }
        // 如果某个匹配超界了，则结束递归
        if(i >= pattern.length() || j >= str.length()){
            return;
        }
        char c = pattern.charAt(i);
        // 尝试从当前位置到结尾的所有划分方式
        for(int cut = j + 1; cut <= str.length(); cut++){
            // 拆出一个子串
            String substr = str.substring(j, cut);
            // 如果这个子串没有被映射过，而且当前pattern的字符也没有产生过映射
            // 则新建一对映射，并且继续递归求解
            if(!set.contains(substr) && !map.containsKey(c)){
                map.put(c, substr);
                set.add(substr);
                helper(pattern, str, i + 1, cut);
                map.remove(c);
                set.remove(substr);
            // 如果已经有映射了，但是是匹配的，也继续求解
            } else if(map.containsKey(c) && map.get(c).equals(substr)){
                helper(pattern, str, i + 1, cut);
            }
            // 否则跳过该子串，尝试下一种拆分
        }
    }
}

// Wrong solution without Set
// If not adding the set to avoid duplicates, the problem is duplicate mapping, multiple keys for one value
// test with
// "bdpbibletwuwbvh"
// "aaaaaaaaaaaaaaa"
// the code as below:
public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> map = new HashMap<Character, String>();
        return helper(map, pattern, str, 0, 0);
    }
    
    private boolean helper(Map<Character, String> map, String pattern, String str, int i, int j) {
        if(i == pattern.length() && j == str.length()) {
            return true;
        }
        if(i >= pattern.length() || j >= str.length()) {
            return false;
        }
        char c = pattern.charAt(i);
        for(int k = j; k < str.length(); k++) {
            String token = str.substring(j, k + 1);
            if(!map.containsKey(c)) {
                map.put(c, token);
                if(helper(map, pattern, str, i + 1, k + 1)) {
                    return true;
                }
                map.remove(c);
            } else if(map.containsKey(c) && map.get(c).equals(token)) {
                 if(helper(map, pattern, str, i + 1, k + 1)) {
                    return true;
                }               
            }
        }
        return false;
    }


