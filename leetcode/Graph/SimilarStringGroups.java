/**
 Refer to
 https://leetcode.com/problems/similar-string-groups/
 Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y. 
 Also two strings X and Y are similar if they are equal.
 For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, 
 but "star" is not similar to "tars", "rats", or "arts".
 Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that 
 "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that 
 a word is in the group if and only if it is similar to at least one other word in the group.
 We are given a list A of strings.  Every string in A is an anagram of every other string in A.  
 How many groups are there?
 
 Example 1:
 Input: A = ["tars","rats","arts","star"]
 Output: 2
 
 Constraints:
 1 <= A.length <= 2000
 1 <= A[i].length <= 1000
 A.length * A[i].length <= 20000
 All words in A consist of lowercase letters only.
 All words in A have the same length and are anagrams of each other.
 The judging time limit has been increased for this question.
*/

// Solution 1: Union Find
// Refer to
// https://leetcode.com/problems/similar-string-groups/discuss/132317/Simple-Java-8-Python-Union-Find
// https://leetcode.com/problems/similar-string-groups/discuss/132318/Simple-Java-Solution-using-DFS/267794
class Solution {
    public int numSimilarGroups(String[] A) {
        int n = A.length;
        UnionFind uf = new UnionFind(n);
        for(int i = 0; i < n - 1; i++) {
            for(int j = i + 1; j < n; j++) {
                if(isSimilar(A[i], A[j])) {
                    uf.union(i, j);
                }
            }
        }
        return uf.get_count();
    }
    
    private boolean isSimilar(String a, String b) {
        int count = 0;
        int i = 0;
        while(count <= 2 && i < a.length()) {
            if(a.charAt(i) != b.charAt(i)) {
                count++;
            }
            i++;
        }
        return count == 2 || count == 0;
    }
}

class UnionFind {
    int[] parent, size;
    int count;
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        count = n;
    }
    
    public int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }
    
    public void union(int a, int b) {
        int src = find(a);
        int dst = find(b);
        if(src == dst) {
            return;
        }
        if(size[src] > size[dst]) {
            parent[dst] = src;
            size[src] += size[dst];
        } else {
            parent[src] = dst;
            size[dst] += size[src];
        }
        count--;
    }
    
    public int get_count() {
        return count;
    }
}


