/**
 Refer to
 https://leetcode.com/problems/replace-words/
 In English, we have a concept called root, which can be followed by some other words to form 
 another longer word - let's call this word successor. For example, the root an, followed by 
 other, which can form another word another.

Now, given a dictionary consisting of many roots and a sentence. You need to replace all the 
successor in the sentence with the root forming it. If a successor has many roots can form it, 
replace it with the root with the shortest length.

You need to output the sentence after the replacement.

Example 1:
Input: dict = ["cat", "bat", "rat"]
sentence = "the cattle was rattled by the battery"
Output: "the cat was rat by the bat"

Note:
The input will only have lower-case letters.
1 <= dict words number <= 1000
1 <= sentence words number <= 1000
1 <= root length <= 100
1 <= sentence words length <= 1000
*/
// Solution 1: Trie
// Refer to
// https://leetcode.com/problems/replace-words/discuss/105767/Java-SimpleClassical-Trie-questionsolution-(Beat-96)
class Solution {
    // Put all the roots in a trie (prefix tree). 
    // Then for any query word, we can find the smallest root that 
    // was a prefix in linear time.
    public String replaceWords(List<String> dict, String sentence) {
        Trie trie = new Trie();
        for(String s : dict) {
            trie.insert(s);
        }
        StringBuilder sb = new StringBuilder();
        for(String token : sentence.split(" ")) {
            sb.append(getShortestReplacement(token, trie.root)).append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }
    
    // To judge if token start with some roots, need go through token as 
    // one by one character on trie (which build based on all roots)
    // If encounter a full string root (isEnd == true), and it absolutely
    // will be the first encounter, then we directly found the shortest
    // root which as prefix on this token
    public String getShortestReplacement(String token, TrieNode root) {
        TrieNode temp = root;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < token.length(); i++) {
            sb.append(token.charAt(i));
            int index = token.charAt(i) - 'a';
            if(temp.array[index] != null) {
                if(temp.array[index].isEnd) {
                    return sb.toString();
                }
                temp = temp.array[index];
            } else {
                return token;
            }
        }
        return token;
    }
}

class Trie {
    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    // No need methods: startWith, searchNode, search
    // just keep insert method as required to build trie
    public void insert(String word) {
        TrieNode p = root;
        for(int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if(p.array[index] == null) {
                TrieNode node = new TrieNode();
                p.array[index] = node;
                p = node;
            } else {
                p = p.array[index];
            }
        }
        p.isEnd = true;
    }
}

class TrieNode {
    TrieNode[] array;
    boolean isEnd;
    public TrieNode() {
        this.array = new TrieNode[26];
        this.isEnd = false;
    }
}



// New try
class Solution {
    public String replaceWords(List<String> dict, String sentence) {
        String[] tokens = sentence.split("\\s+");
        Trie trie = new Trie();
        for(String word : dict) {
            trie.insert(word);
        }
        String result = "";
        for(String token : tokens) {
            int len = trie.searchWord(token);
            if(len != 0) {
                result += token.substring(0, len) + " ";
            } else {
                result += token + " ";
            }
        }
        return result.substring(0, result.length() - 1);
    }
    
    class TrieNode {
        boolean isEnd;
        TrieNode[] array;
        public TrieNode() {
            this.isEnd = false;
            this.array = new TrieNode[26];
        }
    }
    
    class Trie {
        TrieNode root;
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String s) {
            TrieNode p = root;
            for(int i = 0; i < s.length(); i++) {
                int index = s.charAt(i) - 'a';
                if(p.array[index] == null) {
                    TrieNode node = new TrieNode();
                    p.array[index] = node;
                    p = node;
                } else {
                    p = p.array[index];
                }
            }
            p.isEnd = true;
        }
        
        public int searchWord(String s) {
            int count = 0;
            TrieNode p = root;
            for(int i = 0; i < s.length(); i++) {
                int index = s.charAt(i) - 'a';
                if(p.array[index] != null) {
                    if(p.array[index].isEnd) {
                        return count + 1;
                    } else {
                        p = p.array[index];
                        count++;
                    }
                } else {
                    // Else means not able to find any string in dict
                    // as prefix for current token, return 0 as not found
                    // e.g search 'by' in 'bat'
                    return 0;
                }
            }
            return count;
        }
    }
}


