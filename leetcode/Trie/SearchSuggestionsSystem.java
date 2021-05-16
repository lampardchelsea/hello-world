/**
Refer to
https://leetcode.com/problems/search-suggestions-system/
Given an array of strings products and a string searchWord. We want to design a system that suggests at most 
three product names from products after each character of searchWord is typed. Suggested products should have 
common prefix with the searchWord. If there are more than three products with a common prefix return the three 
lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed. 

Example 1:
Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]

Example 2:
Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]

Example 3:
Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]

Example 4:
Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]

Constraints:
1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters.
*/

// The graph explain refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Trie/Document/Search_Suggestions_System_Trie_Implementation.jpg

// Solution 1: Trie
// Style 1: Sort after insert on node level to get lexicographically minimums products, time complexity: O(nm^2 + L)
// Refer to
// https://leetcode.com/problems/search-suggestions-system/discuss/436151/JavaPython-3-Simple-Trie-and-Binary-Search-codes-w-comment-and-brief-analysis.
/**
Q & A:
Q1: In method 1, why we needs two checks on root whther it is null as here:

            if (root != null) // if current Trie is NOT null.
                root = root.sub[c - 'a'];
            ans.add(root == null ? Arrays.asList() : root.suggestion); // add it if there exist products with current prefix.
A1:
Two checks ensure neither current Trie nor next level Trie is null, otherwise, it would throw NullPointerException.

Q2: In method 2, why the space complexity of sorting and binary search algorithm is O(L*m)?
A2: It includes return list ans, which cost space O(L * M).

End of Q & A

Method 1: Trie

    class Trie {
        Trie[] sub = new Trie[26];
        LinkedList<String> suggestion = new LinkedList<>();
    }
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie root = new Trie();
        for (String p : products) { // build Trie.
            insert(p, root); // insert a product into Trie.
        }
        return search(searchWord, root);
    }
    private void insert(String p, Trie root) {
        Trie t = root;
        for (char c : p.toCharArray()) { // insert current product into Trie.
            if (t.sub[c - 'a'] == null)
                t.sub[c - 'a'] = new Trie();
            t = t.sub[c - 'a'];
            t.suggestion.offer(p); // put products with same prefix into suggestion list.
            Collections.sort(t.suggestion);
            if (t.suggestion.size() > 3) // maintain 3 lexicographically minimum strings.
                t.suggestion.pollLast();        
        }
    }
    private List<List<String>> search(String searchWord, Trie root) {
        List<List<String>> ans = new ArrayList<>();
        for (char c : searchWord.toCharArray()) { // search product.
            if (root != null) // if there exist products with current prefix.
                root = root.sub[c - 'a'];
            ans.add(root == null ? Arrays.asList() : root.suggestion); // add it if there exist products with current prefix.
        }
        return ans;
    }
*/
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Construct trie
        TrieNode root = new TrieNode();
        for(String product : products) {
            insert(product, root);
        }
        return search(searchWord, root);
    }
    
    private void insert(String p, TrieNode root) {
        TrieNode t = root;
        for(char c : p.toCharArray()) {
            if(t.next[c - 'a'] == null) {
                t.next[c - 'a'] = new TrieNode();
            }
            t = t.next[c - 'a'];
            t.list.add(p);
            // Sort on node level
            Collections.sort(t.list);
            // Only add when size under 3, remove redundant node with LinkedList initialized in node to store strings
            if(t.list.size() > 3) {
                t.list.pollLast();
            }
        }
    }
    
    public List<List<String>> search(String searchWord, TrieNode root) {
        List<List<String>> result = new ArrayList<List<String>>();
        TrieNode t = root;
        for(char c : searchWord.toCharArray()) {
            // Every time check current node(t) and its next level node(t.next[c - 'a'])
            // exist or not, if current node already not exist then not able to even
            // t = t.next[c - 'a'], in all iterators, all 't' are null, so looply add
            // empty list
            if(t != null) {
                t = t.next[c - 'a'];
            }
            if(t == null) {
                result.add(new ArrayList<String>());
            } else {
                result.add(t.list);
            }
        }
        return result;
    }
}

class TrieNode {
    TrieNode[] next = new TrieNode[26];
    LinkedList<String> list = new LinkedList<String>();
}

// Style 2: Sort before insert to get lexicographically minimums products, time complexity: O(mnlog(n) + L)
// Refer to
// https://leetcode.com/problems/search-suggestions-system/discuss/436151/JavaPython-3-Simple-Trie-and-Binary-Search-codes-w-comment-and-brief-analysis./446524
// https://leetcode.com/problems/search-suggestions-system/discuss/436151/JavaPython-3-Simple-Trie-and-Binary-Search-codes-w-comment-and-brief-analysis./791347
/**
Time Complexity different than Style 1:
@Learnerstudent So I spent a good amount of time writing out different Trie-based approaches, and my understanding is that by having 
a PQ for each TrieNode, every time a word of length O(m) is inserted into the Trie, for every character in the word, we are doing 2 O(m) 
comparisons between the other 2 strings in the PQ at that "level"/TrieNode. So, using @rock 's approach, the runtime of adding all O(n) 
words into the Trie is actually O(nmm) = O(nm^2), not O(nm) as suggested in the above post, and the final runtime of the approach in 
the post should be O(nm^2 + L).
If we were to instead sort all the products at the very beginning, that operation would cost us O(mnlog(n) time. However, each insert 
operation would only be O(m) instead of O(m^2), and the time complexity to insert O(n) words would be O(nm), not O(nm^2). This approach 
would yield a total runtime of O(mnlog(n) + mn + L), which can be simplified to O(mnlog(n) + L). This approach is asymptotically and 
practically faster than the initial Trie approach rock suggested.

    class Trie {
        Trie[] sub = new Trie[26];
        LinkedList<String> suggestion = new LinkedList<>();
    }
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie root = new Trie();
        Arrays.sort(products);
        for (String p : products) { // build Trie.
            Trie t = root;
            for (char c : p.toCharArray()) { // insert current product into Trie.
                if (t.sub[c - 'a'] == null)
                    t.sub[c - 'a'] = new Trie();
                t = t.sub[c - 'a'];
                if (t.suggestion.size() < 3) // maintain 3 lexicographically minimum strings.
                    t.suggestion.offer(p); // put products with same prefix into suggestion list.                
            }
        }
        List<List<String>> ans = new ArrayList<>();
        for (char c : searchWord.toCharArray()) { // search product.
            if (root != null) // if current Trie is NOT null.
                root = root.sub[c - 'a'];
            ans.add(root == null ? Arrays.asList() : root.suggestion); // add it if there exist products with current prefix.
        }
        return ans;
    }  
*/
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Sort in order to add onto Trie node as lexicographically order
        Arrays.sort(products);
        // Construct trie
        TrieNode root = new TrieNode();
        for(String product : products) {
            insert(product, root);
        }
        return search(searchWord, root);
    }
    
    private void insert(String p, TrieNode root) {
        TrieNode t = root;
        for(char c : p.toCharArray()) {
            if(t.next[c - 'a'] == null) {
                t.next[c - 'a'] = new TrieNode();
            }
            t = t.next[c - 'a'];
            // Only add when size under 3, and no need initialize LinkedList
            // on node level to support remove redundant string when size over 3
            if(t.list.size() < 3) {
                t.list.add(p);
            }
        }
    }
    
    public List<List<String>> search(String searchWord, TrieNode root) {
        List<List<String>> result = new ArrayList<List<String>>();
        TrieNode t = root;
        for(char c : searchWord.toCharArray()) {
            // Every time check current node(t) and its next level node(t.next[c - 'a'])
            // exist or not, if current node already not exist then not able to even
            // t = t.next[c - 'a'], in all iterators, all 't' are null, so looply add
            // empty list
            if(t != null) {
                t = t.next[c - 'a'];
            }
            if(t == null) {
                result.add(new ArrayList<String>());
            } else {
                result.add(t.list);
            }
        }
        return result;
    }
}

class TrieNode {
    TrieNode[] next = new TrieNode[26];
    List<String> list = new ArrayList<String>();
}

// Style 3: More intuitive way to handle NullPointerException by early break rather than looply check root and its next level both as null or not
// Refer to
// https://leetcode.com/problems/search-suggestions-system/discuss/436151/JavaPython-3-Simple-Trie-and-Binary-Search-codes-w-comment-and-brief-analysis./562100
/**
You don't need to

if (root != null)
    root = root.sub[c - 'a'];
ans.add(root == null ? Arrays.asList() : root.suggestion); 
You can instead do

    for (char c : searchWord.toCharArray()) {
            root = root.elements[c - 'a'];
            if (root == null) break;
            result.add(root.suggestions);
    }
    while (result.size() < searchWord.length()) {
            result.add(new ArrayList<>());
    }
*/
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Sort in order to add onto Trie node as lexicographically order
        Arrays.sort(products);
        // Construct trie
        // 'root' is always initalized as empty, the actual characters
        // only start insert on level 1 of 'root'
        TrieNode root = new TrieNode();
        for(String product : products) {
            insert(product, root);
        }
        return search(searchWord, root);
    }
    
    private void insert(String p, TrieNode root) {
        TrieNode t = root;
        for(char c : p.toCharArray()) {
            if(t.next[c - 'a'] == null) {
                t.next[c - 'a'] = new TrieNode();
            }
            t = t.next[c - 'a'];
            // Only add when size under 3
            if(t.list.size() < 3) {
                t.list.add(p);
            }
        }
    }
    
    public List<List<String>> search(String searchWord, TrieNode root) {
        List<List<String>> result = new ArrayList<List<String>>();
        TrieNode t = root;
        for(char c : searchWord.toCharArray()) {
            // To handle NullPointerException by early break rather than 
            // looply check root and its next level both as null or not.
            // Since 't' on level 0 is 'root', but the actual characters
            // inserted before only start from level 1 of 'root', so we
            // initially go into level 1 by 't = t.next[c - 'a']', but
            // it won't guarantee the level 1 node exist, such as example:
            // Input: products = ["havana"], searchWord = "tatiana"
            // Output: [[],[],[],[],[],[],[]]
            // when build Trie, the initial level 1 node is 'h', but in
            // search word the initial level 1 node is 't', so level 1
            // already not exist, not able to go further to level 2 and
            // further depth, it will always non-exist node, so we have
            // to break out early to stop looply 't = t.next[c - 'a']',
            // to make up empty list which expected by result, just need
            // to use while loop add empty list at the end, which used
            // together with 'break'
            t = t.next[c - 'a'];
            if(t == null) {
                break;
            } else {
                result.add(t.list);
            }
        }
        while(result.size() < searchWord.length()) {
        	result.add(new ArrayList<String>());
        }
        return result;
    }
}

class TrieNode {
    TrieNode[] next = new TrieNode[26];
    List<String> list = new ArrayList<String>();
}
