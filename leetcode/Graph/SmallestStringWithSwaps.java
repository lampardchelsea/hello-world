/**
Refer to
https://leetcode.com/problems/smallest-string-with-swaps/
You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.

You can swap the characters at any pair of indices in the given pairs any number of times.

Return the lexicographically smallest string that s can be changed to after using the swaps.

Example 1:
Input: s = "dcab", pairs = [[0,3],[1,2]]
Output: "bacd"
Explaination: 
Swap s[0] and s[3], s = "bcad"
Swap s[1] and s[2], s = "bacd"

Example 2:
Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
Output: "abcd"
Explaination: 
Swap s[0] and s[3], s = "bcad"
Swap s[0] and s[2], s = "acbd"
Swap s[1] and s[2], s = "abcd"

Example 3:
Input: s = "cba", pairs = [[0,1],[1,2]]
Output: "abc"
Explaination: 
Swap s[0] and s[1], s = "bca"
Swap s[1] and s[2], s = "bac"
Swap s[0] and s[1], s = "abc"

Constraints:
1 <= s.length <= 10^5
0 <= pairs.length <= 10^5
0 <= pairs[i][0], pairs[i][1] < s.length
s only contains lower case English letters.
*/

// Solution 1: Union-find + PriorityQueue
// Refer to
// https://leetcode.com/problems/smallest-string-with-swaps/discuss/388257/C%2B%2B-with-picture-union-find
// --> Save as doc: https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Graph/Documents/Smallest_String_With_Swaps.docx
// https://leetcode.com/problems/smallest-string-with-swaps/discuss/388055/Java-Union-find-%2B-PriorityQueue.-Easy-to-understand.
/**
Problem abstract: Sort the characters within each connected group.

For each the given pairs, create connected groups using union-find. Always mark the smaller index as parent;
For each character in s, create mapping from root -> a list of candidate char. Since we want to use the smallest one every time we pick from them, use PriorityQueue.
Finally, for each index, choose the first char in the associated pq and append into result.
class Solution {
    private int[] parent;
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        if (s == null || s.length() == 0) {
            return null;
        }
        parent = new int[s.length()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        
        for (List<Integer> pair : pairs) {
            union(pair.get(0), pair.get(1));
        }
        
        Map<Integer, PriorityQueue<Character>> map = new HashMap<>();
        char[] sChar = s.toCharArray();
        for (int i = 0; i < sChar.length; i++) {
            int root = find(i);
            map.putIfAbsent(root, new PriorityQueue<>());
            map.get(root).offer(sChar[i]);
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sChar.length; i++) {
            sb.append(map.get(find(i)).poll());
        }
        return sb.toString();
    }
    private int find(int index) {
        while (parent[index] != index) {
            parent[index] = parent[parent[index]];
            index = parent[index];
        }
        return index;
    }
    private void union(int a, int b) {
        int aParent = find(a);
        int bParent = find(b);
        if (aParent < bParent) {
            parent[bParent] = aParent;
        } else {
            parent[aParent] = bParent;
        }
    }
}
*/
class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n = s.length();
        int[] parent = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for(List<Integer> pair : pairs) {
            union(parent, pair);
        }
        Map<Integer, PriorityQueue<Character>> map = new HashMap<Integer, PriorityQueue<Character>>();
        for(int i = 0; i < n; i++) {
            int root = find(parent, i);
            map.putIfAbsent(root, new PriorityQueue<Character>());
            map.get(root).offer(s.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(map.get(find(parent, i)).poll());
        }
        return sb.toString();
    }
    
    private void union(int[] parent, List<Integer> pair) {
        int a = pair.get(0);
        int b = pair.get(1);
        int src = find(parent, a);
        int dst = find(parent, b);
        if(src != dst) {
            parent[src] = dst;
        }
    }
    
    private int find(int[] parent, int x) {
        if(x == parent[x]) {
            return x;
        }
        return parent[x] = find(parent, parent[x]);
    }
}

