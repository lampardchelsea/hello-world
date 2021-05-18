/**
Refer to
https://leetcode.com/problems/rearrange-words-in-a-sentence/
Given a sentence text (A sentence is a string of space-separated words) in the following format:

First letter is in upper case.
Each word in text are separated by a single space.
Your task is to rearrange the words in text such that all words are rearranged in an increasing order of their lengths. 
If two words have the same length, arrange them in their original order.

Return the new text following the format shown above.

Example 1:
Input: text = "Leetcode is cool"
Output: "Is cool leetcode"
Explanation: There are 3 words, "Leetcode" of length 8, "is" of length 2 and "cool" of length 4.
Output is ordered by length and the new first word starts with capital letter.

Example 2:
Input: text = "Keep calm and code on"
Output: "On and keep calm code"
Explanation: Output is ordered as follows:
"On" 2 letters.
"and" 3 letters.
"keep" 4 letters in case of tie order by position in original text.
"calm" 4 letters.
"code" 4 letters.

Example 3:
Input: text = "To be or not to be"
Output: "To be or to be not"

Constraints:
text begins with a capital letter and then contains lowercase letters and single space between words.
1 <= text.length <= 10^5
*/

// Solution 1: Customized comparator
// Refer to
// https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
/**
Since Date implements Comparable, it has a compareTo method just like String does.

So your custom Comparator could look like this:

public class CustomComparator implements Comparator<MyObject> {
    @Override
    public int compare(MyObject o1, MyObject o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
    }
}
The compare() method must return an int, so you couldn't directly return a boolean like you were planning to anyway.

Your sorting code would be just about like you wrote:

Collections.sort(Database.arrayList, new CustomComparator());
A slightly shorter way to write all this, if you don't need to reuse your comparator, is to write it as an inline anonymous class:

Collections.sort(Database.arrayList, new Comparator<MyObject>() {
    @Override
    public int compare(MyObject o1, MyObject o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
    }
});
*/
class Solution {
    public String arrangeWords(String text) {
        String[] strs = text.split("\\s+");
        strs[0] = strs[0].substring(0, 1).toLowerCase() + strs[0].substring(1);
        int n = strs.length;
        List<Node> nodes = new ArrayList<Node>();
        for(int i = 0; i < n; i++) {
            nodes.add(new Node(i, strs[i]));
        }
        Collections.sort(nodes, new WordComparator());
        StringBuilder sb = new StringBuilder();
        for(Node node : nodes) {
            sb.append(node.str).append(" ");
        }
        sb.setLength(sb.length() - 1);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}

class Node {
    int index;
    String str;
    public Node(int index, String str) {
        this.index = index;
        this.str = str;
    }
}

class WordComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2){
        if(n1.str.length() != n2.str.length()) {
            return n1.str.length() - n2.str.length();
        } else {
            return n1.index - n2.index;
        }
    }
}

// Solution 2: Use Arrays.sort() since Java sort backend is merge sort and its stable
// Refer to
// https://leetcode.com/problems/rearrange-words-in-a-sentence/discuss/636286/Java-4-line
/**
    public String arrangeWords(String text) {
        String []s = text.toLowerCase().split(" ");
        Arrays.sort(s, (a,b) ->  a.length() - b.length());
        String ans = String.join(" ", s);
        return Character.toUpperCase(ans.charAt(0)) + ans.substring(1);
    }
*/

// How do you guarantee If two words have the same length, arrange them in their original order.? Does Arrays.sort() do that?
// https://leetcode.com/problems/rearrange-words-in-a-sentence/discuss/636286/Java-4-line/543156
// https://leetcode.com/problems/rearrange-words-in-a-sentence/discuss/636286/Java-4-line/544355
/**
Yes, it will. Since Java system sort uses merge sort for object and as we know, merge sort is STABLE. 
So, if the comparator gives 0, it will maintain the original order.
*/
// Stability in sorting algorithms
/**
Stability is mainly important when we have key value pairs with duplicate keys possible (like people names as keys and their 
details as values). And we wish to sort these objects by keys.
What is it?
A sorting algorithm is said to be stable if two objects with equal keys appear in the same order in sorted output as they 
appear in the input array to be sorted
*/
class Solution {
    public String arrangeWords(String text) {
        String []s = text.toLowerCase().split(" ");
        Arrays.sort(s, (a,b) ->  a.length() - b.length());
        String ans = String.join(" ", s);
        return Character.toUpperCase(ans.charAt(0)) + ans.substring(1);
    }
}
