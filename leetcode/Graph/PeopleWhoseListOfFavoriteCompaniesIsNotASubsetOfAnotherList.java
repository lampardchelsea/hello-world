/**
Refer to
https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/
Given the array favoriteCompanies where favoriteCompanies[i] is the list of favorites companies for the ith person (indexed from 0).

Return the indices of people whose list of favorite companies is not a subset of any other list of favorites companies. 
You must return the indices in increasing order.

Example 1:
Input: favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],["google"],["amazon"]]
Output: [0,1,4] 
Explanation: 
Person with index=2 has favoriteCompanies[2]=["google","facebook"] which is a subset of favoriteCompanies[0]=["leetcode","google","facebook"] corresponding to the person with index 0. 
Person with index=3 has favoriteCompanies[3]=["google"] which is a subset of favoriteCompanies[0]=["leetcode","google","facebook"] and favoriteCompanies[1]=["google","microsoft"]. 
Other lists of favorite companies are not a subset of another list, therefore, the answer is [0,1,4].

Example 2:
Input: favoriteCompanies = [["leetcode","google","facebook"],["leetcode","amazon"],["facebook","google"]]
Output: [0,1] 
Explanation: In this case favoriteCompanies[2]=["facebook","google"] is a subset of favoriteCompanies[0]=["leetcode","google","facebook"], therefore, the answer is [0,1].

Example 3:
Input: favoriteCompanies = [["leetcode"],["google"],["facebook"],["amazon"]]
Output: [0,1,2,3]

Constraints:
1 <= favoriteCompanies.length <= 100
1 <= favoriteCompanies[i].length <= 500
1 <= favoriteCompanies[i][j].length <= 20
All strings in favoriteCompanies[i] are distinct.
All lists of favorite companies are distinct, that is, If we sort alphabetically each list then favoriteCompanies[i] != favoriteCompanies[j].
All strings consist of lowercase English letters only.
*/

// Solution 1: Union Find (actually no difference with brutal force since both O(n^2) time complexity)
// Refer to
// https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/discuss/636294/Java-Union-Find-37-ms-beat-100-with-detailed-explanation
/**
idea
Typical union-find
For example:
favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],["google"],["amazon"]]

it is actually a graph:

	{lgf}     {gm}    {a}
      |  
    {gf}
	  |
	 {g}
with path compression, the root of each node can be found faster:

	{lgf}     {gm}    {a}
	/   \
 {gf}   {g}
In the end, we just need to return the index of the three roots of the graph.

code
preprocess input, turn them into List<Set>, please refer to the update below
initialize father of each list as itself
for i from 0 to length
for j from i+1 to length
let a = root of list i, let b = root of list j
if a==b, it means list i and j have the same root, they are already in the same tree, do nothing
else if a contains b, let a becomes b's root
else if b contains a, let b becomes a's root
in the end, add all unique roots into a set, and return it in res
Complexity
the time complexity of union-find with path compression and without rank is between N + MlogN and N+Mlog*N, 
(according to https://www.cs.princeton.edu/~rs/AlgsDS07/01UnionFind.pdf)
where M is times of search, and N is the number of nodes in the tree

the space complexity is O(N), as we use a array and list with length N, where N is the size of input

update
Special thanks to @ekchang, he mentioned
List#containsAll is dramatically worse than Set#containsAll. The former ends up doing N^2 traversal while the latter 
does N traversal with hash code look ups.
A simple optimization to dramatically improve runtime (using List with union find barely avoids TLE!) is to preprocess 
input as List<Set> which should cut runtime down from ~1800 ms to ~50 ms.

The complexity is still O(n^2)?
https://leetcode.com/problems/people-whose-list-of-favorite-companies-is-not-a-subset-of-another-list/discuss/636294/Java-Union-Find-37-ms-beat-100-with-detailed-explanation/548887
for (int i=0; i<l; i++)
for (int j=i+1; j<l; j++)
I think Bruce method is also O(n^2)
*/
class Solution {
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        List<Integer> ans = new ArrayList<>();
        List<Set<String>> companiesSetList = new ArrayList<>();
        for (List<String> companies : favoriteCompanies) {
            companiesSetList.add(new HashSet<>(companies));
        }
        int size = companiesSetList.size();
        int[] uf = new int[size];
        for (int i = 0; i < size; i++) {
            uf[i] = i;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++){
                union(uf, i, j, companiesSetList); 
            }
        }
        Set<Integer> set = new HashSet<>();
        for (int i : uf) {
            set.add(find(uf, i));
        }
        ans.addAll(set);
        Collections.sort(ans);
        return ans;
    }
    
    public void union(int[] uf, int i, int j, List<Set<String>> companiesSetList) {
        int pi = find(uf, i);
        int pj = find(uf, j);
        // Here is a bit difference than the classic way
        if (isSubSet(companiesSetList.get(pj), companiesSetList.get(pi))) {
            uf[pj] = pi;
        } else if (isSubSet(companiesSetList.get(pi), companiesSetList.get(pj))) {
           uf[pi] = pj;
        }
    }
    
    public int find(int[] uf, int i) {
        if (uf[i] == i) {
            return i;
        }
        return uf[i] = find(uf, uf[i]);
    }
    
    public boolean isSubSet(Set<String> a, Set<String> b){
        if (a.size() >= b.size()) {
            return false;
        }
        return b.containsAll(a);
    }
}
