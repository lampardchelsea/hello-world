import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Given a string, we can "shift" each of its letter to its successive letter, 
 * for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:
 * "abc" -> "bcd" -> ... -> "xyz"
 * 
 * Given a list of strings which contains only lowercase alphabets, group all 
 * strings that belong to the same shifting sequence.
 * 
 * For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"], 
 * Return:

	[
	  ["abc","bcd","xyz"],
	  ["az","ba"],
	  ["acef"],
	  ["a","z"]
	]
	
 * Note: For the return value, each inner list's elements must follow the 
 * lexicographic order.
 *
 */
 
// Refer to
// http://blog.csdn.net/pointbreak1/article/details/48780345
// https://discuss.leetcode.com/topic/20722/my-concise-java-solution
// http://www.cnblogs.com/grandyang/p/5204770.html
public class GroupShiftedStrings {	
	public List<List<String>> groupStrings(String[] strings) {  
        List<List<String>> result = new ArrayList<List<String>>();  
        Map<String, List<String>> map = new HashMap<String, List<String>>();  
        for(int i = 0; i < strings.length; i++) {  
            StringBuffer sb = new StringBuffer();  
            for(int j = 0; j < strings[i].length(); j++) {
            	// Based on character at 1st position of current string to find identification
            	// code for of string, e.g abc --> 012, bcd --> 012, bdfg --> 0245
		// Important: Don't forget to add 26 to make sure offset in 0 to 25 region, which
		// handle case like "ba", "za" ...
                sb.append(Integer.toString(((strings[i].charAt(j) - strings[i].charAt(0)) + 26) % 26)); 
            }  
            String stringID = sb.toString();
            // String has same id value will put into same collection, else will put into new collection
            // e.g abc --> 012 and bcd --> 012 will put into same collection，bdfg --> 0245 will put
            // in new collection
            if(map.containsKey(stringID)) {  
            	map.get(stringID).add(strings[i]);  
            } else {  
                List<String> strsForID = new ArrayList<>();  
                strsForID.add(strings[i]);  
                map.put(stringID, strsForID);  
            }  
        }  
          
        for(String id : map.keySet()) {
        	// Sort result array into natural sequence
        	// e.g {bdfg, acef} --> {acef, bdfg}
        	Collections.sort(map.get(id));
            result.add(map.get(id));  
        }   
        return result;  
    }
	
	public static void main(String[] args) {
		String[] s = {"abc", "bcd", "bdfg", "acef", "xyz", "az", "ba", "a", "z"};
		GroupShiftedStrings groupShiftedStrings = new GroupShiftedStrings();
		List<List<String>> result = groupShiftedStrings.groupStrings(s);
		System.out.println(result.toString());
	}	
}
