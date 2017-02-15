
// Solution 1: Brute Force
// Refer to
// https://discuss.leetcode.com/topic/50450/slow-1-liner-to-fast-solutions
// Need to fully scan length1 * length2 times of all pairs and add onto MinPQ,
// then print out required k pairs
public class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int pairs = length1 * length2;
        List<int[]> result = new ArrayList<int[]>();
        if(length1 == 0 || length2 == 0 || k == 0) {
            return result;
        }
        MinPQ minPQ = new MinPQ(pairs);
        for(int i = 0; i < length1; i++) {
            for(int j = 0; j < length2; j++) {
                int sum = nums1[i] + nums2[j];
                Pair pair = new Pair(sum, i, j);
                minPQ.insert(pair);
            }
        }
        if(k <= pairs) {
            for(int i = 0; i < k; i++) {
                Pair pair = minPQ.delMin();
                int[] temp = {nums1[pair.index1], nums2[pair.index2]};
                result.add(temp);
            }
        } else {
            for(int i = 0; i < pairs; i++) {
                Pair pair = minPQ.delMin();
                int[] temp = {nums1[pair.index1], nums2[pair.index2]};
                result.add(temp);
            }
        }
        return result;
    }
    
    private class Pair {
        int sum;
        int index1;
        int index2;
        public Pair(int sum, int index1, int index2) {
            this.sum = sum;
            this.index1 = index1;
            this.index2 = index2;
        }
    }
    
    private class MinPQ {
        Pair[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new Pair[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(Pair x) {
            pq[++n] = x;
            swim(n);
        }
        
        public Pair delMin() {
            Pair min = pq[1];
            exch(1, n--);
            sink(1);
            return min;
        }
        
        public void swim(int k) {
            while(k > 1 && greater(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && greater(j, j + 1)) {
                    j++;
                }
                if(!greater(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
        public boolean greater(int v, int w) {
            return pq[v].sum - pq[w].sum > 0;
        }
        
        public void exch(int v, int w) {
            Pair swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
    }
}
