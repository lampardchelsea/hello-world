// Solution 1: Customized MaxPQ
/**
 * Refer to
 * https://leetcode.com/problems/top-k-frequent-elements/
 * 
 * Given a non-empty array of integers, return the k most frequent elements.
 * For example,
   Given [1,1,1,2,2,3] and k = 2, return [1,2].
 * Note: 
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
*/
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            if(!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) + 1);
            }
        }
        MaxPQ maxPQ = new MaxPQ(map.size());
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            maxPQ.insert(node);
        }
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < k; i++) {
            result.add(maxPQ.delMax().val);
        }
        return result;
    }
    
    private class Node {
        int val;
        int freq;
        public Node(int val, int freq) {
            this.val = val;
            this.freq = freq;
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
            pq[n + 1] = null;
            return max;
        }
        
        public void swim(int k) {
            while(k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
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
        
        public boolean less(int v, int w) {
            return pq[v].freq - pq[w].freq < 0;
        }
        
        public void exch(int v, int w) {
            Node swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
    }
}

// Solution 2: PriorityQueue MaxPQ
// Refer to 
// Use a Comparator to create a PriorityQueue for messages
// http://www.java2s.com/Tutorial/Java/0140__Collections/UseaComparatortocreateaPriorityQueueformessages.htm
//
// comparator for Map.Entry<K,V>
// http://stackoverflow.com/questions/17211291/comparator-for-map-entryk-v
//
// 3 Java Solution using Array, MaxHeap, TreeMap
// https://discuss.leetcode.com/topic/48158/3-java-solution-using-array-maxheap-treemap
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            if(!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) + 1);
            }
        }
        // Initial MaxPQ with Map.Entry<Integer, Integer> as items type and comparator
        PriorityQueue<Map.Entry<Integer, Integer>> maxPQ = new PriorityQueue<Map.Entry<Integer, Integer>>(new FreqComparator<Integer, Integer>());
        // Put all map entries onto MaxPQ
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            maxPQ.add(entry);
        }
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < k; i++) {
            result.add(maxPQ.poll().getKey());
        }
        return result;
    }

    // Declare K and V as generic type parameters to FreqComparator
    // Note that V must extend Comparable<V>, in order to be able to call compareTo() on o1.getValue()
    // ScoreComparator takes two generic type arguments K and V. Map.Entry<K, V> is not a valid 
    // generic type definition, but you may well use it to bind to Comparator<T>'s T type.
    private class FreqComparator<K, V extends Comparable<V>> implements Comparator<Map.Entry<K, V>> {
        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
            // Call compareTo() on V, which is known to be a Comparable<V>
            return o2.getValue().compareTo(o1.getValue());
        }   
    }
}


// Solution 3: Java O(n) Solution - Bucket Sort
// Refer to 
// https://discuss.leetcode.com/topic/44237/java-o-n-solution-bucket-sort
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            if(!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) + 1);
            }
        }
        // Array index represent frequence, list on certain index
        // represent values happen as that frequence (range 1 to n)
        // that's why increase length with 1 to include frequence = n
        // Note: Don't write as "new List<Integer>[nums.length + 1]",
        // will throw out generic array creation
        List<Integer>[] bucket = new List[nums.length + 1];
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(bucket[entry.getValue()] == null) {
                bucket[entry.getValue()] = new ArrayList<Integer>();
            }
            bucket[entry.getValue()].add(entry.getKey());
        }
        // Loop the bucket and print all bucket not null from highest freq
        // which equals to start from right index
        // Note: Don't forget limitation on k with "result.size() < k"
        List<Integer> result = new ArrayList<Integer>();
        for(int i = bucket.length - 1; i >= 0 && result.size() < k; i--) {
            if(bucket[i] != null) {
                result.addAll(bucket[i]);
            }
        }
        return result;
    }
}
