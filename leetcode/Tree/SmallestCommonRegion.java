/**
 Refer to
 https://blog.csdn.net/xiyang0405/article/details/104747193
 https://www.cnblogs.com/Dylan-Java-NYC/p/12075993.html
 You are given some lists of regions where the first region of each list includes all other regions in that list.

Naturally, if a region X contains another region Y then X is bigger than Y. Also by definition a region X contains itself.

Given two regions region1, region2, find out the smallest region that contains both of them.

If you are given regions r1, r2 and r3 such that r1 includes r3, it is guaranteed there is no r2 such that r2 includes r3.

It's guaranteed the smallest region exists.

Example 1:

Input:
regions = [["Earth","North America","South America"],
["North America","United States","Canada"],
["United States","New York","Boston"],
["Canada","Ontario","Quebec"],
["South America","Brazil"]],
region1 = "Quebec",
region2 = "New York"
Output: "North America"
Constraints:

2 <= regions.length <= 10^4
region1 != region2
All strings consist of English letters and spaces with at most 20 letters.
*/

// Solution 1: LCA with DFS and map building
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/LowestCommonAncestorOfABinaryTree.java
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/LowestCommonAncestorOfABinarySearchTree.java
// https://blog.csdn.net/xiyang0405/article/details/104747193
// https://www.cnblogs.com/Dylan-Java-NYC/p/12075993.html
/**
 With the regions list, we could construct partent HashMap with child pointing to parent.
 Maintain all the regions used while finding ancestor of region1.
 When finding ancestor of region2, return the first occurance of region that is in used, it would be smallest common region.
 Time Complexity: O(n). n = regions.size() * average length. h is height of parent tree.
 Space: O(n).
*/

// https://www.jianshu.com/p/7bb4936a1419
/**
 因为只要单向找，所以hashmap这个key value pair，这里是表示一个方向，所以建从孩子到parent的好用，就可以从input开始查了。
 这里用了一个set记录region1的path，region2就可以一路查上去了。
*/

// Refer to
// https://blog.csdn.net/xiyang0405/article/details/104747193
/**
 【思路1】
  转化成求一个树中两个节点共同最小祖先节点问题。使用DFS及helper方程，遍历树的节点，直到找到target节点。
 【易错点】
  如果子节点中已发现满足条件的节点后，如何向上一级函数传递容易出错。
 【分析】
  1. 基于树的纵向遍历使用DFS算法和helper函数也是一类典型题目。
  2. 未保存整个树结构，以及确认是否为根节点进行了很多重复操作。此外在寻找目标节点时，也遍历了很多不相关子树和节点，造成很多多余时间开销。
class Solution {
private:
    unordered_map<string, string> par;
public:
    string findSmallestRegion(vector<vector<string>>& regions, string region1, string region2) {
        for(auto r: regions) {
            string r0 = r[0];
            for(int i = 1; i < r.size(); ++i)
                par[r[i]] = r0;
        }
        int d1 = 0, d2 = 0;
        string r1 = region1, r2 = region2;
        while(par.find(r1) != par.end()) {
            r1 = par[r1];
            d1++;
        }
        while(par.find(r2) != par.end()) {
            r2 = par[r2];
            d2++;
        }
        while(d1 > d2) {
            region1 = par[region1];
            d1--;
        }
        while(d2 > d1) {
            region2 = par[region2];
            d2--;
        }
        while(region1 != region2) {
            region1 = par[region1];
            region2 = par[region2];
        }
        return region1;
    }
};
*/
/**
	regions = [["Earth","North America","South America"],
	           ["North America","United States","Canada"],
	           ["United States","New York","Boston"],
	           ["Canada","Ontario","Quebec"],
	           ["South America","Brazil"]],
	region1 = "Quebec",
    region2 = "New York"
    Output: "North America"
    -----------------------------------------------------
                             Earth
                        /           \
             North America         South America
             /          \              /
           US           Canada       Brazil
         /     \         /     \
        NY  Boston   Ontario  Quebec

*/
class Solution {
    Map < String, List < String >> map = new HashMap < String, List < String >> ();
    public String findSmallestRegion(List < List < String >> regions, String region1, String region2) {
        Set < String > roots = new HashSet < String > ();
        for (List < String > region: regions) {
            roots.add(region.get(0));
        }
        for (List < String > region: regions) {
            for (int i = 1; i < region.size(); i++) {
                String r = region.get(i);
                map.computeIfAbsent(region.get(0), k - > new ArrayList < String > ()).add(r);
                if (roots.contains(r)) {
                    roots.remove(r);
                }
            }
        }
        for (String root: roots) {
            String lca = helper(root, region1, region2);
            if (lca != "") {
                return lca;
            }
        }
        return "";
    }

    private String helper(String root, String region1, String region2) {
        if (root.equals(region1) || root.equals(region2)) {
            return root;
        }
        if (!map.containsKey(root)) {
            return "";
        }
        boolean rg1 = false;
        boolean rg2 = false;
        for (String child: map.get(root)) {
            String temp = helper(child, region1, region2);
            if (temp.equals(region1)) {
                rg1 = true;
            }
            if (temp.equals(region2)) {
                rg2 = true;
            }
            if (rg1 && rg2) {
                return root;
            }
            if (temp != "" && !temp.equals(region1) && !temp.equals(region2)) {
                return temp;
            }
        }
        if (rg1) {
            return region1;
        }
        if (rg2) {
            return region2;
        }
        return "";
    }

    public static void main(String[] args) {
        List < List < String >> regions = new ArrayList < List < String >> ();
        List < String > one = new ArrayList < String > ();
        one.add("Earth");
        one.add("North America");
        one.add("South America");
        List < String > two = new ArrayList < String > ();
        two.add("North America");
        two.add("United States");
        two.add("Canada");
        List < String > three = new ArrayList < String > ();
        three.add("United States");
        three.add("New York");
        three.add("Boston");
        List < String > four = new ArrayList < String > ();
        four.add("Canada");
        four.add("Ontario");
        four.add("Quebec");
        List < String > five = new ArrayList < String > ();
        five.add("South America");
        five.add("Brazil");
        regions.add(one);
        regions.add(two);
        regions.add(three);
        regions.add(four);
        regions.add(five);
        String region1 = "Quebec";
        String region2 = "New York";
        String result = q.findSmallestRegion(regions, region1, region2);
        System.out.print(result);
    }
}

// Solution 2: LCA + Hashmap
// Refer to
// https://www.jianshu.com/p/7bb4936a1419
// https://blog.csdn.net/xiyang0405/article/details/104747193
/**
转化成求链表中两个节点相交的节点问题。
1. 先建立每一个节点与其上一层节点对应关系，找到每一个节点的父节点；
2. 求出两个目标节点深度d1, d2;
3. 将两节点移动到同一深度位置后，查看他们是否相等，若相等则返回此节点值，否则继续向根节点移动。
【分析】
1. 类似的链表题还有：查看一个链表是否存在环（快慢指针）；若存在环寻找环的入口（快慢指针一个断掉，变成寻找两个节点相交的第一个节点，同上题）
2. 对字符串的存储占据了很多空间，或许可以更优化一些。
*/
class Solution {
    public String findSmallestRegion(List < List < String >> regions, String region1, String region2) {
        HashMap < String, String > map = new HashMap < > ();
        //build tree
        for (List < String > region: regions) {
            String value = region.get(0);
            for (int i = 1; i < region.size(); i++) {
                String key = region.get(i);
                // this map is about the child point to the parent
                map.put(key, value);
            }
        }
        //start from region1, use a hashset to store the parent path
        HashSet < String > parents = new HashSet < > ();
        while (region1 != null) {
            //add it to the set for future search
            parents.add(region1);
            // get region1's parent
            String parent = map.get(region1);
            // update region1 to parent
            region1 = parent;
        }
        while (!parents.contains(region2)) {
            String parent = map.get(region2);
            region2 = parent;
        }
        return region2;
    }
}
