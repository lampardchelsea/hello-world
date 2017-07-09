import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/find-duplicate-file-in-system/#/description
 * Given a list of directory info including directory path, and all the files with contents in this directory, 
 * you need to find out all the groups of duplicate files in the file system in terms of their paths.
 * 
 * A group of duplicate files consists of at least two files that have exactly the same content.
 * A single directory info string in the input list has the following format:
 * "root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"
 * 
 * It means there are n files (f1.txt, f2.txt ... fn.txt with content f1_content, f2_content ... fn_content, respectively) 
 * in directory root/d1/d2/.../dm. Note that n >= 1 and m >= 0. If m = 0, it means the directory is just the root directory.
 * 
 * The output is a list of group of duplicate file paths. For each group, it contains all the file paths of 
 * the files that have the same content. A file path is a string that has the following format:
 * "directory_path/file_name.txt"
 *
 *	Example 1:
	Input:
	["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"]
	Output:  
	[["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]
 *	
 * Note:
 * No order is required for the final output.
 * (1) You may assume the directory name, file name and file content only has letters and digits, 
 *     and the length of file content is in the range of [1,50].
 * (2) The number of files given is in the range of [1,20000].
 * (3) You may assume no files or directories share the same name in the same directory.
 * (4) You may assume each given directory info represents a unique directory. Directory path and file info 
 *     are separated by a single blank space.
 *     
 * Follow-up beyond contest:
 * (1) Imagine you are given a real file system, how will you search files? DFS or BFS?
 * (2) If the file content is very large (GB level), how will you modify your solution?
 * (3) If you can only read the file by 1kb each time, how will you modify your solution?
 * (4) What is the time complexity of your modified solution? What is the most time-consuming part and memory 
 *     consuming part of it? How to optimize?
 * (5) How to make sure the duplicated files you find are not false positive?
 * 
 * Solution
 * https://leetcode.com/articles/find-duplicate/
 * Approach #2 Using HashMap [Accepted]
 * In this approach, firstly we obtain the directory paths, the file names and their contents separately by 
 * appropriately splitting each string in the given paths list. In order to find the files with 
 * duplicate contents, we make use of a HashMap map, which stores the data in the form 
 * (contents,list_of_file_paths_with_this_content). Thus, for every file's contents, we check if the same content 
 * already exist in the hashmap. If so, we add the current file's path to the list of files corresponding to 
 * the current contents. Otherwise, we create a new entry in the map, with the current contents as the key and 
 * the value being a list with only one entry(the current file's path).
 * At the end, we find out the contents corresponding to which at least two file paths exist. We obtain the 
 * resultant list res, which is a list of lists containing these file paths corresponding to the same contents.
 * Complexity Analysis
    Time complexity : O(n ∗ x). n strings of average length x is parsed.
    Space complexity : O(n ∗ x). map and res size grows up to n*x.
 */
public class FindDuplicateFileInSystem {
    public List<List<String>> findDuplicate(String[] paths) {
        List<List<String>> result = new ArrayList<List<String>>();
        if(paths.length == 0) {
            return result;
        }
        // The key is content of each file, the value is fullpath(folder + filename)
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for(int i = 0; i < paths.length; i++) {
            String path = paths[i];
            String[] sections = path.split(" ");
            for(int j = 1; j < sections.length; j++) {
                String[] values = sections[j].split("\\(");
                String content = values[1].replace(")", "");
                List<String> list;
                if(!map.containsKey(content)) {
                    list = new ArrayList<String>();
                } else {
                    list = map.get(content);
                }
                list.add(sections[0] + "/" + values[0]);
                map.put(content, list);
            }
        }
        
        // Must include check of size() > 1, otherwise, if no common content, the list should
        // be empty
        // E.g Input:["root/a 1.txt(abcd) 2.txt(efsfgh)","root/c 3.txt(abdfcd)","root/c/d 4.txt(efggdfh)"]
        //	   Expected:[]
        for(String key : map.keySet()) {
            if(map.get(key).size() > 1) {
                result.add(map.get(key));
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    }
}
