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
 * Refer to
 * https://discuss.leetcode.com/topic/91430/c-clean-solution-answers-to-follow-up
 * Follow up questions:
 * 1. Imagine you are given a real file system, how will you search files? DFS or BFS ?
 *    In general, BFS will use more memory then DFS. However BFS can take advantage of the locality of files 
 *    in inside directories, and therefore will probably be faster
 * 
 * 2. If the file content is very large (GB level), how will you modify your solution?
 *    In a real life solution we will not hash the entire file content, since it's not practical. 
 *    Instead we will first map all the files according to size. Files with different sizes are guaranteed 
 *    to be different. We will than hash a small part of the files with equal sizes (using MD5 for example). 
 *    Only if the md5 is the same, we will compare the files byte by byte
 * 
 * 3. If you can only read the file by 1kb each time, how will you modify your solution?
 *    This won't change the solution. We can create the hash from the 1kb chunks, and then read the entire 
 *    file if a full byte by byte comparison is required.
 * 
 * 4. What is the time complexity of your modified solution? What is the most time consuming part and memory 
 *    consuming part of it? How to optimize?
 *    Time complexity is O(n^2 * k) since in worse case we might need to compare every file to all others. 
 *    k is the file size
 *    
 * 5. How to make sure the duplicated files you find are not false positive?
 *    We will use several filters to compare: File size, Hash and byte by byte comparisons.
 * 
 * 
 * Solution 1: Brute Force
 * https://leetcode.com/articles/find-duplicate/
 * Approach #1 Brute Force [Time Limit Exceeded]
 * Algorithm
 * For the brute force solution, firstly we obtain the directory paths, the filenames and file contents separately by 
 * appropriately splitting the elements of the paths list. While doing so, we keep on creating a list which 
 * contains the full path of every file along with the contents of the file. The list contains data in the form 
 * [[file1_full_path,file1_contents],[file2_full_path,file2_contents]...,[filen_full_path,filen_contents]].
 * Once this is done, we iterate over this list. For every element iii chosen from the list, we iterate over the whole
 *  list to find another element j whose file contents are the same as the ith element. For every such element found, 
 *  we put the jth element's file path in a temporary list l and we also mark the jth​​ element as visited so that this 
 *  element isn't considered again in the future. Thus, when we reach the end of the array for every ith​​ element, 
 *  we obtain a list of file paths in l, which have the same contents as the file corresponding to the ith element. 
 *  If this list isn't empty, it indicates that there exists content duplicate to the ith element. Thus, we also need 
 *  to put the ith element's file path in the l.
 *  At the end of each iteration, we put this list l obtained in the resultant list res and reset the list l for finding 
 *  the duplicates of the next element.
 * 
 * 
 * Solution 2: HashMap
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
	// Solution 1: Brute Force
	public List<List<String>> findDuplicate(String[] paths) {
		List<List<String>> result = new ArrayList<List<String>>();
		if(paths.length == 0) {
			return result;
		}
		List<String[]> list = new ArrayList<String[]>();
		for(String path : paths) {
			String[] sections = path.split(" ");
			for(int i = 1; i < sections.length; i++) {
				String[] values = sections[i].split("\\(");
				String content = values[1].replace(")", "");
				list.add(new String[]{sections[0] + "/" + values[0], content});
			}
		}
		boolean[] visited = new boolean[list.size()];
		List<List<String>> res = new ArrayList<List<String>>();
		for(int i = 0; i < list.size(); i++) {
			if(visited[i]) {
				continue;
			}
			List<String> l = new ArrayList<String>();
			for(int j = i + 1; j < list.size(); j++) {
				if(list.get(i)[1].equals(list.get(j)[1])) {
					visited[j] = true;
					l.add(list.get(j)[0]);
				}
			}
			if(l.size() > 0) {
				// Don't forget to put the ith element's file path in the l
				l.add(list.get(i)[0]);
				res.add(l);
			}	
		}
		return res;
	}
	
	// Solution 2: HashMap
    public List<List<String>> findDuplicate2(String[] paths) {
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
































































https://leetcode.com/problems/find-duplicate-file-in-system/description/
Given a list paths of directory info, including the directory path, and all the files with contents in this directory, return all the duplicate files in the file system in terms of their paths. You may return the answer in any order.
A group of duplicate files consists of at least two files that have the same content.
A single directory info string in the input list has the following format:
- "root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"
It means there are n files (f1.txt, f2.txt ... fn.txt) with content (f1_content, f2_content ... fn_content) respectively in the directory "root/d1/d2/.../dm". Note that n >= 1 and m >= 0. If m = 0, it means the directory is just the root directory.
The output is a list of groups of duplicate file paths. For each group, it contains all the file paths of the files that have the same content. A file path is a string that has the following format:
- "directory_path/file_name.txt"
 
Example 1:
Input: paths = ["root/a 1.txt(abcd) 2.txt(efgh)","root/c 3.txt(abcd)","root/c/d 4.txt(efgh)","root 4.txt(efgh)"]
Output: [["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]

Example 2:
Input: paths = ["root/a 1.txt(abcd) 2.txt(efgh)","root/c 3.txt(abcd)","root/c/d 4.txt(efgh)"]
Output: [["root/a/2.txt","root/c/d/4.txt"],["root/a/1.txt","root/c/3.txt"]]
 
Constraints:
- 1 <= paths.length <= 2 * 104
- 1 <= paths[i].length <= 3000
- 1 <= sum(paths[i].length) <= 5 * 105
- paths[i] consist of English letters, digits, '/', '.', '(', ')', and ' '.
- You may assume no files or directories share the same name in the same directory.
- You may assume each given directory info represents a unique directory. A single blank space separates the directory path and file info.
 
Follow up:
- Imagine you are given a real file system, how will you search files? DFS or BFS?
- If the file content is very large (GB level), how will you modify your solution?
- If you can only read the file by 1kb each time, how will you modify your solution?
- What is the time complexity of your modified solution? What is the most time-consuming part and memory-consuming part of it? How to optimize?
- How to make sure the duplicated files you find are not false positive?
--------------------------------------------------------------------------------
Attempt 1: 2026-01-14
Solution 1: Hash Table (10 min)
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        // Map to store content -> list of file paths with that content
        // <k, v> -> {content, list of full path with same content}
        Map<String, List<String>> map = new HashMap<>();
        for(String path : paths) {
            String[] parts = path.split(" ");
            // First part is the directory path
            String directory = parts[0];
            // Process each file in the directory
            for(int i = 1; i < parts.length; i++) {
                String file = parts[i];
                // Find the opening and closing parentheses
                int contentStart = file.indexOf('(');
                int contentEnd = file.indexOf(')');
                // Extract filename and content
                String fileName = file.substring(0, contentStart);
                String content = file.substring(contentStart + 1, contentEnd);
                // Build full file path
                String fullPath = directory + "/" + fileName;
                // Add to map
                map.computeIfAbsent(content, k -> new ArrayList<>()).add(fullPath);
            }
        }
        // Collect all groups that have more than one file (duplicates)
        // Equal to Stream one line solution:
        // return map.values().stream().filter(list -> list.size() > 1).collect(Collectors.toList());
        List<List<String>> result = new ArrayList<>();
        for(List<String> files : map.values()) {
            if(files.size() > 1) {
                result.add(files);
            }
        }
        return result;
    }
}

Time Complexity: O(N × M) where N = number of files, M = average file content length
We process each file once and extract its content
Space Complexity: O(N × M) to store the content-to-file mapping

Refer to QWEN
Here's the optimal Java solution for LeetCode 609. Find Duplicate File in System:
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        // Map to store content -> list of file paths with that content
        Map<String, List<String>> contentToFileMap = new HashMap<>();
        
        // Process each path string
        for (String path : paths) {
            String[] parts = path.split(" ");
            String directory = parts[0]; // First part is the directory path
            
            // Process each file in the directory
            for (int i = 1; i < parts.length; i++) {
                String file = parts[i];
                
                // Find the opening and closing parentheses
                int contentStart = file.indexOf('(');
                int contentEnd = file.indexOf(')', contentStart);
                
                // Extract filename and content
                String fileName = file.substring(0, contentStart);
                String content = file.substring(contentStart + 1, contentEnd);
                
                // Build full file path
                String fullPath = directory + "/" + fileName;
                
                // Add to map
                contentToFileMap.computeIfAbsent(content, k -> new ArrayList<>())
                                .add(fullPath);
            }
        }
        
        // Collect all groups that have more than one file (duplicates)
        List<List<String>> result = new ArrayList<>();
        for (List<String> files : contentToFileMap.values()) {
            if (files.size() > 1) {
                result.add(files);
            }
        }
        
        return result;
    }
}
Key Concepts Explained:
Input Format Understanding
- Each string in paths represents: "directory file1(content1) file2(content2) ..."
- Example: "root/a 1.txt(abcd) 2.txt(efgh)"
Parsing Strategy
1.Split by space: First token = directory, remaining tokens = files with content
2.Extract content: Find parentheses to separate filename from content
3.Build full path: directory + "/" + filename
HashMap Usage
- Key: File content (the string inside parentheses)
- Value: List of full file paths that have this content
- Duplicate detection: Any content with >1 file paths indicates duplicates
Time & Space Complexity:
- Time: O(N × M) where N = number of files, M = average file content length
- We process each file once and extract its content
- Space: O(N × M) to store the content-to-file mapping
Example Walkthrough:
Input:
["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)"]
Processing:
- "abcd" → ["root/a/1.txt", "root/c/3.txt"]
- "efgh" → ["root/a/2.txt", "root/c/d/4.txt"]
Output:
[
  ["root/a/1.txt", "root/c/3.txt"],
  ["root/a/2.txt", "root/c/d/4.txt"]
]
Edge Cases Handled:
- Multiple files in same directory
- Same content appearing in different directories
- Files with empty content (though problem constraints guarantee non-empty content)
This solution efficiently groups duplicate files by their content using a straightforward parsing and hashing approach!

Refer to Deepseek
Problem Understanding
Given a list of directory info (path and files with content), find all duplicate files (files with identical content).
Input format: "root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"
Solution 1: HashMap with Content as Key (Most Common)
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        // Map: content -> list of file paths
        Map<String, List<String>> contentMap = new HashMap<>();
        
        for (String path : paths) {
            // Split into directory and files
            String[] parts = path.split(" ");
            String directory = parts[0];
            
            for (int i = 1; i < parts.length; i++) {
                // Parse file name and content
                String file = parts[i];
                int contentStart = file.indexOf('(');
                int contentEnd = file.indexOf(')');
                
                String fileName = file.substring(0, contentStart);
                String content = file.substring(contentStart + 1, contentEnd);
                
                // Build full path
                String fullPath = directory + "/" + fileName;
                
                // Add to map
                contentMap.computeIfAbsent(content, k -> new ArrayList<>()).add(fullPath);
            }
        }
        
        // Collect only duplicates (content with multiple files)
        List<List<String>> result = new ArrayList<>();
        for (List<String> files : contentMap.values()) {
            if (files.size() > 1) {
                result.add(files);
            }
        }
        
        return result;
    }
}
Solution 2: Using Streams (Java 8+)
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String path : paths) {
            String[] tokens = path.split(" ");
            String dir = tokens[0];
            
            for (int i = 1; i < tokens.length; i++) {
                String token = tokens[i];
                int start = token.indexOf('(');
                String fileName = token.substring(0, start);
                String content = token.substring(start + 1, token.length() - 1);
                
                map.computeIfAbsent(content, k -> new ArrayList<>())
                   .add(dir + "/" + fileName);
            }
        }
        
        return map.values().stream()
                  .filter(list -> list.size() > 1)
                  .collect(Collectors.toList());
    }
}
Solution 3: Optimized with String Builder
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String path : paths) {
            parsePath(path, map);
        }
        
        List<List<String>> result = new ArrayList<>();
        for (List<String> files : map.values()) {
            if (files.size() > 1) {
                result.add(files);
            }
        }
        
        return result;
    }
    
    private void parsePath(String path, Map<String, List<String>> map) {
        // Find the space after directory
        int firstSpace = path.indexOf(' ');
        if (firstSpace == -1) return;
        
        String directory = path.substring(0, firstSpace);
        StringBuilder sb = new StringBuilder();
        
        // Process each file in the path
        int i = firstSpace + 1;
        while (i < path.length()) {
            char c = path.charAt(i);
            
            if (c == '(') {
                // Found content start, fileName is in sb
                String fileName = sb.toString();
                sb.setLength(0); // Reset for content
                
                // Skip '('
                i++;
                
                // Read content until ')'
                while (i < path.length() && path.charAt(i) != ')') {
                    sb.append(path.charAt(i));
                    i++;
                }
                
                String content = sb.toString();
                sb.setLength(0); // Reset for next file
                
                // Add to map
                String fullPath = directory + "/" + fileName;
                map.computeIfAbsent(content, k -> new ArrayList<>()).add(fullPath);
                
                // Skip ')' and possible space
                i++;
                if (i < path.length() && path.charAt(i) == ' ') {
                    i++;
                }
            } else {
                sb.append(c);
                i++;
            }
        }
    }
}
Solution 4: Using StringTokenizer (Old School)
import java.util.*;

class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> contentToPaths = new HashMap<>();
        
        for (String path : paths) {
            StringTokenizer tokenizer = new StringTokenizer(path);
            String directory = tokenizer.nextToken();
            
            while (tokenizer.hasMoreTokens()) {
                String fileToken = tokenizer.nextToken();
                int parenIndex = fileToken.indexOf('(');
                
                String fileName = fileToken.substring(0, parenIndex);
                String content = fileToken.substring(parenIndex + 1, fileToken.length() - 1);
                
                String fullPath = directory + "/" + fileName;
                contentToPaths.computeIfAbsent(content, k -> new ArrayList<>())
                              .add(fullPath);
            }
        }
        
        // Filter for duplicates only
        List<List<String>> result = new ArrayList<>();
        for (List<String> pathsList : contentToPaths.values()) {
            if (pathsList.size() > 1) {
                result.add(pathsList);
            }
        }
        
        return result;
    }
}
Solution 5: With Content Hash (Memory Efficient for Large Files)
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        // Use a two-level map to avoid storing large content strings
        Map<Integer, Map<String, List<String>>> hashMap = new HashMap<>();
        
        for (String path : paths) {
            String[] parts = path.split(" ");
            String directory = parts[0];
            
            for (int i = 1; i < parts.length; i++) {
                String file = parts[i];
                int contentStart = file.indexOf('(');
                String fileName = file.substring(0, contentStart);
                String content = file.substring(contentStart + 1, file.length() - 1);
                
                // Use hash code as first-level key
                int hashCode = content.hashCode();
                String fullPath = directory + "/" + fileName;
                
                hashMap.computeIfAbsent(hashCode, k -> new HashMap<>())
                       .computeIfAbsent(content, k -> new ArrayList<>())
                       .add(fullPath);
            }
        }
        
        // Collect results
        List<List<String>> result = new ArrayList<>();
        for (Map<String, List<String>> innerMap : hashMap.values()) {
            for (List<String> files : innerMap.values()) {
                if (files.size() > 1) {
                    result.add(files);
                }
            }
        }
        
        return result;
    }
}
Complexity Analysis
Solution 1 (HashMap with Content as Key):
- Time Complexity: O(n * m)
- n = number of paths
- m = average number of files per directory
- Each file processed once
- Space Complexity: O(k)
- k = total unique content strings
- Stores all file paths grouped by content
Examples
Example 1:
Input: paths = [
  "root/a 1.txt(abcd) 2.txt(efgh)",
  "root/c 3.txt(abcd)",
  "root/c/d 4.txt(efgh)",
  "root 4.txt(efgh)"
]

Output: [
  ["root/a/1.txt","root/c/3.txt"],
  ["root/a/2.txt","root/c/d/4.txt","root/4.txt"]
]
Example 2:
Input: paths = [
  "root/a 1.txt(abcd) 2.txt(efgh)",
  "root/c 3.txt(abcd)",
  "root/c/d 4.txt(efgh)"
]

Output: [
  ["root/a/1.txt","root/c/3.txt"],
  ["root/a/2.txt","root/c/d/4.txt"]
]
Detailed Explanation of Solution 1
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        // Key insight: Group files by their CONTENT, not by name
        Map<String, List<String>> contentToFiles = new HashMap<>();
        
        for (String path : paths) {
            // Step 1: Split "directory file1(content1) file2(content2)"
            String[] parts = path.split(" ");
            // parts[0] = directory path
            // parts[1...] = files with content
            
            String directory = parts[0];
            
            // Step 2: Process each file in this directory
            for (int i = 1; i < parts.length; i++) {
                String fileWithContent = parts[i];
                
                // Step 3: Extract fileName and content
                // Format: "filename(content)"
                int openParen = fileWithContent.indexOf('(');
                int closeParen = fileWithContent.indexOf(')');
                
                String fileName = fileWithContent.substring(0, openParen);
                String content = fileWithContent.substring(openParen + 1, closeParen);
                
                // Step 4: Build full path
                String fullPath = directory + "/" + fileName;
                
                // Step 5: Group by content
                // If content not in map, create new list
                // If content exists, add to existing list
                contentToFiles.computeIfAbsent(content, k -> new ArrayList<>())
                              .add(fullPath);
            }
        }
        
        // Step 6: Filter to only duplicate contents
        List<List<String>> result = new ArrayList<>();
        for (List<String> files : contentToFiles.values()) {
            if (files.size() > 1) {
                result.add(files);
            }
        }
        
        return result;
    }
}
Recommended Solution
Solution 1 is the best for most cases:
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String path : paths) {
            String[] tokens = path.split(" ");
            String dir = tokens[0];
            
            for (int i = 1; i < tokens.length; i++) {
                String file = tokens[i];
                int start = file.indexOf('(');
                String fileName = file.substring(0, start);
                String content = file.substring(start + 1, file.length() - 1);
                
                String fullPath = dir + "/" + fileName;
                map.computeIfAbsent(content, k -> new ArrayList<>()).add(fullPath);
            }
        }
        
        // Return only groups with more than 1 file
        List<List<String>> result = new ArrayList<>();
        for (List<String> group : map.values()) {
            if (group.size() > 1) {
                result.add(group);
            }
        }
        return result;
    }
}
Why this is the best:
1.Clean and readable - Easy to understand
2.Efficient - O(n) time complexity
3.Uses standard Java collections - No external dependencies
4.Handles edge cases well - Empty paths, single files, etc.
Edge Cases to Consider
1.Empty input: paths = [] → return []
2.No duplicates: All files have unique content → return []
3.Multiple duplicates: Same content appears in many files
4.Same directory: Files in same directory can be duplicates
5.Special characters in file names or content
6.Very large content: Could use MD5 hash instead of storing full content


Refer to
L1948.Delete Duplicate Folders in System (Ref.L609,L652)
