/**
 * Refer to
 * https://leetcode.com/problems/top-k-frequent-words/description/
 * Given a non-empty list of words, return the k most frequent elements.

    Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, 
    then the word with the lower alphabetical order comes first.

    Example 1:
    Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
    Output: ["i", "love"]
    Explanation: "i" and "love" are the two most frequent words.
        Note that "i" comes before "love" due to a lower alphabetical order.
    Example 2:
    Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
    Output: ["the", "is", "sunny", "day"]
    Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
        with the number of occurrence being 4, 3, 2 and 1 respectively.
    Note:
    You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
    Input words contain only lowercase letters.
    Follow up:
    Try to solve it in O(n log k) time and O(n) extra space.
 *
 * Solution
 * https://stackoverflow.com/questions/6203411/comparing-strings-by-their-alphabetical-order
*/
class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> result = new ArrayList<String>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<Node> maxPQ = new PriorityQueue<Node>(new Comparator<Node>() {
            public int compare(Node a, Node b) {
                // https://stackoverflow.com/questions/6203411/comparing-strings-by-their-alphabetical-order
                if(a.freq == b.freq) {                     
                    return a.str.compareTo(b.str);
                }
                return b.freq - a.freq;
            } 
        });
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            maxPQ.offer(new Node(entry.getKey(), entry.getValue()));
        }
        for(int i = 0; i < k; i++) {
            result.add(maxPQ.poll().str);
        }
        return result;
    }
    
    private class Node {
        String str;
        int freq;
        public Node(String str, int freq) {
            this.str = str;
            this.freq = freq;
        }
    }
}
