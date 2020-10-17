/**
 Refer to
 https://leetcode.com/problems/accounts-merge/
 Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, 
 and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that 
is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as 
people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely 
have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, 
and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.

Example 1:
Input: 
accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], 
["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  
["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
Explanation: 
The first and third John's are the same person as they have the common email "johnsmith@mail.com".
The second John and Mary are different people as none of their email addresses are used by other accounts.
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.

Note:
The length of accounts will be in the range [1, 1000].
The length of accounts[i] will be in the range [1, 10].
The length of accounts[i][j] will be in the range [1, 30].
*/

// Solution 1: Union-Find
// Refer to
// https://leetcode.com/problems/accounts-merge/discuss/109157/JavaC%2B%2B-Union-Find
/**
The key task here is to connect those emails, and this is a perfect use case for union find.
to group these emails, each group need to have a representative, or parent.
At the beginning, set each email as its own representative.
Emails in each account naturally belong to a same group, and should be joined by assigning to the same parent 
(let's use the parent of first email in that list);
Simple Example:
a b c // now b, c have parent a
d e f // now e, f have parent d
g a d // now abc, def all merged to group g

parents populated after parsing 1st account: a b c
a->a
b->a
c->a

parents populated after parsing 2nd account: d e f
d->d
e->d
f->d

parents populated after parsing 3rd account: g a d
g->g
a->g
d->g
*/

// https://www.cnblogs.com/grandyang/p/7829169.html
/**
这道题给了一堆人名和邮箱，一个名字可能有多个邮箱，但是一个邮箱只属于一个人，让我们把同一个人的邮箱都合并到一起，名字相同不一定是同一个人，
只有当两个名字有相同的邮箱，才能确定是同一个人，题目中的例子很好说明了这个问题，输入有三个 John，最后合并之后就只有两个了。这道题博主
最开始尝试使用贪婪算法来做，结果发现对于下面这个例子不适用：

["John", "a@gmail.com", "b@gmail.com"]
["John", "c@gmail.com", "d@gmail.com"]
["John", "a@gmail.com", "c@gmail.com"]

可以看到其实这三个 John 是同一个人，但是贪婪算法遍历完前两个 John，还是认为其是两个不同的人，当遍历第三个 John 时，就直接加到第一个 
John 中了，而没有同时把第二个 John 加进来，也可能博主写的是假的贪婪算法，反正不管了，还是参考大神们的解法吧。这个归组类的问题，最典型
的就是岛屿问题(例如 Number of Islands II)，很适合使用 Union Find 来做，LeetCode 中有很多道可以使用这个方法来做的题，比如 
Friend Circles，Graph Valid Tree，Number of Connected Components in an Undirected Graph，和 Redundant Connection 等等。
都是要用一个 root 数组，每个点开始初始化为不同的值，如果两个点属于相同的组，就将其中一个点的 root 值赋值为另一个点的位置，这样只要是
相同组里的两点，通过 find 函数得到相同的值。在这里，由于邮件是字符串不是数字，所以 root 可以用 HashMap 来代替，还需要一个 HashMap 
映射owner，建立每个邮箱和其所有者姓名之前的映射，另外用一个 HashMap 来建立用户和其所有的邮箱之间的映射，也就是合并后的结果。

首先遍历每个账户和其中的所有邮箱，先将每个邮箱的 root 映射为其自身，然后将 owner 赋值为用户名。然后开始另一个循环，遍历每一个账号，
首先对帐号的第一个邮箱调用 find 函数，得到其父串p，然后遍历之后的邮箱，对每个遍历到的邮箱先调用 find 函数，将其父串的 root 值赋值为p，
这样做相当于将相同账号内的所有邮箱都链接起来了。接下来要做的就是再次遍历每个账户内的所有邮箱，先对该邮箱调用 find 函数，找到父串，
然后将该邮箱加入该父串映射的集合汇总，这样就就完成了合并。最后只需要将集合转为字符串数组，加入结果 res 中，通过 owner 映射找到父串
的用户名，加入字符串数组的首位置

class Solution {
    // {owner, email1, email2}
    // list1: {a,b,c}
    // list2: {d,e,f}
    // list3: {g,a,d}
    public List < List < String >> accountsMerge(List < List < String >> acts) {
        // 建立每个邮箱和其所有者姓名之前的映射 HashMap owner
        Map < String, String > owner = new HashMap < String, String > ();
        // 这个归组类的问题, 都是要用一个 root 数组，每个点开始初始化为不同的值，
        // 如果两个点属于相同的组，就将其中一个点的 root 值赋值为另一个点的位置，
        // 这样只要是相同组里的两点，通过 find 函数得到相同的值。在这里，由于邮件
        // 是字符串不是数字，所以 root 可以用 HashMap parents来代替
        Map < String, String > parents = new HashMap < String, String > ();
        // 建立用户和其所有的邮箱之间的映射，也就是合并后的结果 HashMap unions
        Map < String, TreeSet < String >> unions = new HashMap < String, TreeSet < String >> ();
        // 首先遍历每个账户和其中的所有邮箱，先将每个邮箱的 root 映射为其自身，
        // 然后将 owner 赋值为用户名
        // After first for loop:
        // parents: {a=a, b=b, c=c, d=d, e=e, f=f}
        // owner: {a=g, b=a, c=a, d=g, e=d, f=d}
        for (List < String > act: acts) {
            for (int i = 1; i < act.size(); i++) {
                parents.put(act.get(i), act.get(i));
                owner.put(act.get(i), act.get(0));
            }
        }
        // 然后开始另一个循环，遍历每一个账号，首先对帐号的第一个邮箱调用 find 函数，
        // 得到其父串p，然后遍历之后的邮箱，对每个遍历到的邮箱先调用 find 函数，将其
        // 父串的 root 值赋值为p，这样做相当于将相同账号内的所有邮箱都链接起来了
        // After second for loop:
        // parents: {a=a, b=b, c=b, d=a, e=e, f=e}
        for (List < String > act: acts) {
            String p = find(act.get(1), parents);
            for (int i = 2; i < act.size(); i++) {
                parents.put(find(act.get(i), parents), p);
            }
        }
        // 接下来要做的就是再次遍历每个账户内的所有邮箱，先对该邮箱调用 find 函数，
        // 找到父串，然后将该邮箱加入该父串映射的集合汇总(key为父串p)，这样就完成了合并
        // After third for loop:
        // unions: {a=[a, d], b=[b, c], e=[e, f]}
        for (List < String > act: acts) {
            String p = find(act.get(1), parents);
            if (!unions.containsKey(p)) {
                unions.put(p, new TreeSet < String > ());
            }
            for (int i = 1; i < act.size(); i++) {
                unions.get(p).add(act.get(i));
            }
        }
        // 最后只需要将集合转为字符串数组，加入结果 res 中，通过 owner 映射找到父串
        // 的用户名，加入字符串数组的首位置
        List < List < String >> res = new ArrayList < > ();
        for (String p: unions.keySet()) {
            List < String > emails = new ArrayList(unions.get(p));
            emails.add(0, owner.get(p));
            res.add(emails);
        }
        return res;
    }

    private String find(String s, Map < String, String > p) {
        return p.get(s) == s ? s : find(p.get(s), p);
    }
}
*/
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> result = new ArrayList<List<String>>();
        Map<String, String> owners = new HashMap<String, String>();
        Map<String, String> parents = new HashMap<String, String>();
        Map<String, TreeSet<String>> unions = new HashMap<String, TreeSet<String>>();
        for(List<String> account : accounts) {
            for(int i = 1; i < account.size(); i++) {
                parents.put(account.get(i), account.get(i));
                owners.put(account.get(i), account.get(0));
            }
        }
        for(List<String> account : accounts) {
            String p = find(account.get(1), parents);
            for(int i = 2; i < account.size(); i++) {
                // Important: must recursively trace back the root of ith account and connect to p
                // test out by: 
                /**
                 Input:[["David","David0@m.co","David4@m.co","David3@m.co"],["David","David5@m.co","David5@m.co","David0@m.co"],["David","David1@m.co","David4@m.co","David0@m.co"],["David","David0@m.co","David1@m.co","David3@m.co"],["David","David4@m.co","David1@m.co","David3@m.co"]]
                 Output:[["David","David0@m.co","David1@m.co","David3@m.co","David4@m.co"],["David","David0@m.co","David5@m.co"]]
                 Expected:[["David","David0@m.co","David1@m.co","David3@m.co","David4@m.co","David5@m.co"]]
                */
                // This not link the actual root of ith account to p
                // it just connect ith account's parent as p
                //parents.put(account.get(i), p);
                parents.put(find(account.get(i), parents), p);
            }
        }
        for(List<String> account : accounts) {
            String p = find(account.get(1), parents);
            if(!unions.containsKey(p)) {
                unions.put(p, new TreeSet<String>());
            }
            for(int i = 1; i < account.size(); i++) {
                unions.get(p).add(account.get(i));
            }
        }
        for(String p : unions.keySet()) {
            List<String> emails = new ArrayList<String>(unions.get(p));
            emails.add(0, owners.get(p));
            result.add(emails);
        }
        return result;
    }
    
    private String find(String s, Map<String, String> p) {
        if(p.get(s) == s) {
            return s;
        } else {
            return find(p.get(s), p);
        }
    }
}

// Solution 2: DFS (Connect all emails as mutual way as graph + group another map for mapping onwers only)
// Refer to
// https://leetcode.com/problems/accounts-merge/discuss/109158/Java-Solution-(Build-graph-%2B-DFS-search)
/**
I have tried my best to make my code clean. Hope the basic idea below may help you. Happy coding!

Basicly, this is a graph problem. Notice that each account[ i ] tells us some edges. What we have to do is as follows:

Use these edges to build some components. Common email addresses are like the intersections that connect each single component for each account.
Because each component represents a merged account, do DFS search for each components and add into a list. 
Before add the name into this list, sort the emails. Then add name string into it.

Examples: Assume we have three accounts, we connect them like this in order to use DFS.
{Name, 1, 2, 3} => Name -- 1 -- 2 -- 3
{Name, 2, 4, 5} => Name -- 2 -- 4 -- 5 (The same graph node 2 appears)
{Name, 6, 7, 8} => Name -- 6 -- 7 -- 8
(Where numbers represent email addresses).
*/
class Solution {
    // Test with 3 lists:
    // {a,b,c} -> owner = a, 1st email = b, 2nd email = c
    // {d,e,f} -> owner = d, 1st email = e, 2nd email = f
    // {g,a,d} -> owner = g, 1st email = a, 2nd email = b
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Set<String>> graph = new HashMap<>();    // <email node, neighbor nodes>
        Map<String, String> owners = new HashMap<>();        // <email, owner>
        // Build the graph;
        // After building:
        // owners: {a=g, b=a, c=a, d=g, e=d, f=d}
        // graph: {a=[d], b=[c], c=[b], d=[a], e=[f], f=[e]}
        for (List<String> account : accounts) {
            String owner = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                if (!graph.containsKey(account.get(i))) {
                    graph.put(account.get(i), new HashSet<>());
                }
                owners.put(account.get(i), owner);
                // No need to connect owner with first email
                // e.g in our test we have 3 lists, after connect only on all emails:
                // a: b -- c (not directed, mutual way)
                // d: e -- f (not directed, mutual way)
                // g: a -- d (not directed, mutual way)
                if (i == 1) continue;
                graph.get(account.get(i)).add(account.get(i - 1));
                graph.get(account.get(i - 1)).add(account.get(i));
            }
        }
        
        Set<String> visited = new HashSet<>();
        List<List<String>> res = new LinkedList<>();
        // DFS search the graph;
        // owners.keySet() --> get all unique email values
        for (String email : owners.keySet()) {
            List<String> list = new LinkedList<>();
            if (visited.add(email)) {
                // The 'email' is the link to search between different groups
                // note: one group means --> in map <email node, neighbor nodes>
                // the same 'email' value can present on different group,
                // and since we connect all emails in a group and store all groups
                // as a graph, which means if we find one email in different group,
                // we will able to group them into one single combination as a list
                dfs(graph, email, visited, list);
                Collections.sort(list);
                list.add(0, owners.get(email));
                res.add(list);
            }
        }
        return res;
    }
    
    public void dfs(Map<String, Set<String>> graph, String email, Set<String> visited, List<String> list) {
        list.add(email);
        // Use 'email' to find all its neighbor (connected) emails
        // Since mutual connected, use 'visited' to prevent trace
        // back on previous email
        for (String next : graph.get(email)) {
            if (visited.add(next)) {
                dfs(graph, next, visited, list);
            }
        }
    }
}


