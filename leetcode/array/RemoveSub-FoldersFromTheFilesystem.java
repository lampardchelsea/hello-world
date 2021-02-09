/**
Refer to
https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/
Given a list of folders, remove all sub-folders in those folders and return in any order the folders after removing.

If a folder[i] is located within another folder[j], it is called a sub-folder of it.

The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English letters. 
For example, /leetcode and /leetcode/problems are valid paths while an empty string and / are not.

Example 1:
Input: folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
Output: ["/a","/c/d","/c/f"]
Explanation: Folders "/a/b/" is a subfolder of "/a" and "/c/d/e" is inside of folder "/c/d" in our filesystem.

Example 2:
Input: folder = ["/a","/a/b/c","/a/b/d"]
Output: ["/a"]
Explanation: Folders "/a/b/c" and "/a/b/d/" will be removed because they are subfolders of "/a".

Example 3:
Input: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
Output: ["/a/b/c","/a/b/ca","/a/b/d"]

Constraints:
1 <= folder.length <= 4 * 10^4
2 <= folder[i].length <= 100
folder[i] contains only lowercase letters and '/'
folder[i] always starts with character '/'
Each folder name is unique.
*/

// Solution 1: Sort and check if start with
// Refer to
// https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/discuss/849776/Short-Java-Solution-O(nLogn)-with-O(1)-space-(Easy-to-understand)
class Solution {
    public List<String> removeSubfolders(String[] folder) {
        // In ASCII, '/' is before 'a': e.g., '/a', '/a/b', '/aa'
	    // After sorting the folder array, we only need to consider 
        // if the current folder is a subfolder of the previous one or not.
        List<String> result = new ArrayList<String>();
        Arrays.sort(folder);
        for(String f : folder) {
            // Need '/' to ensure a parent.
            if(result.isEmpty() || !f.startsWith(result.get(result.size() - 1) + "/")) {
                result.add(f);
            }
        }
        return result;
    }
}
