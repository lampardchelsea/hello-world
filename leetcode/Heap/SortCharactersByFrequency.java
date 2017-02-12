import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/sort-characters-by-frequency/
 * Given a string, sort it in decreasing order based on the frequency of characters.
	Example 1:	
	Input:
	"tree"
	
	Output:
	"eert"
	
	Explanation:
	'e' appears twice while 'r' and 't' both appear once.
	So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
	
	Example 2:
	Input:
	"cccaaa"
	
	Output:
	"cccaaa"
	
	Explanation:
	Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
	Note that "cacaca" is incorrect, as the same characters must be together.
	
	Example 3:
	Input:
	"Aabb"
	
	Output:
	"bbAa"
	
	Explanation:
	"bbaA" is also a valid answer, but "Aabb" is incorrect.
	Note that 'A' and 'a' are treated as two different characters.
	
 * 
 * Refer to
 * How to loop a Map in Java
 * https://www.mkyong.com/java/how-to-loop-a-map-in-java/
 * 
 * Java Autoboxing and Unboxing with examples
 * http://beginnersbook.com/2014/09/java-autoboxing-and-unboxing-with-examples/
 * 
 * O(n) Easy to understand Java Solution
 * https://discuss.leetcode.com/topic/65947/o-n-easy-to-understand-java-solution
 */
public class SortCharactersByFrequency {
    public String frequencySort(String s) {
    	// Build dictionary map based on given string
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < chars.length; i++) {
            if(!map.containsKey(chars[i])) {
                map.put(chars[i], 1);
            } else {
                map.put(chars[i], map.get(chars[i]) + 1);
            }
        }
        
        // Build up max heap with self defined Node class
        MaxPQ pq = new MaxPQ(map.size());
        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            pq.insert(node);
        }
        
        // Print out string with help of heap and Node class
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < map.size(); i++) {
            Node node = pq.delMax();
            for(int j = 0; j < node.frequency; j++) {
                sb.append(node.character);
            }
        }
        return sb.toString();
    }
    
    private class Node {
        char character;
        int frequency;
        public Node(Character c, Integer i) {
            this.character = c;
            this.frequency = i;
        }
    }
    
    private class MaxPQ {
        Node[] pq;
        int n;
        public MaxPQ(int initialCapacity) {
            pq = new Node[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(Node x) {
            pq[++n] = x;
            swim(n);
        }
        
        public Node delMax() {
            Node max = pq[1];
            exch(1, n--);
            sink(1);
            return max;
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && less(j, j + 1)) {
                    j++;
                }
                if(!less(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
        public void swim(int k) {
            while(k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
        }
        
        public void exch(int v, int w) {
            Node swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean less(int v, int w) {
            return pq[v].frequency - pq[w].frequency < 0;
        }
    }
    
    public static void main(String[] args) {
    	String s = "tree";
    	SortCharactersByFrequency scbf = new SortCharactersByFrequency();
    	String result = scbf.frequencySort(s);
    	System.out.println(result);
    }
}

