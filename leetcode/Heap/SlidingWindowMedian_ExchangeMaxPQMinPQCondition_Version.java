class Solution {
    // We can also reverse the minPQ / maxPQ store condition 
    // as following changes
    // minPQ store larger half of input (strictly > median)
    // maxPQ store smaller half of input (<= median)
    PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>();
    PriorityQueue<Integer> maxPQ = new PriorityQueue<Integer>(new Comparator<Integer>() {
        public int compare(Integer a, Integer b) {
            return b.compareTo(a);
        } 
    });
    
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        if(n <= 0) {
            return new double[0];
        }
        double[] result = new double[n];
        for(int i = 0; i <= nums.length; i++) {
            if(i >= k) {
                result[i - k] = getMedian();
                remove(nums[i - k]);
            }
            if(i < nums.length) {
                add(nums[i]);
            }
        }
        return result;
    }
    
    private void add(int num) {
        // Change to num <= getMedian() onto maxPQ, others onto minPQ
        if(num <= getMedian()) {
            maxPQ.add(num);
        } else {
            minPQ.add(num);
        }
        // Change to minPQ.size() > maxPQ.size() + 1
        if(minPQ.size() >= maxPQ.size() + 1) {
            maxPQ.add(minPQ.poll());
        } else if(maxPQ.size() > minPQ.size() + 1) {
            minPQ.add(maxPQ.poll());
        }
    }
    
    private void remove(int num) {
        // Change to num <= getMedian() onto maxPQ, others onto minPQ
        if(num <= getMedian()) {
            maxPQ.remove(num);
        } else {
            minPQ.remove(num);
        }
        // Change to minPQ.size() > maxPQ.size() + 1
        if(minPQ.size() >= maxPQ.size() + 1) {
            maxPQ.add(minPQ.poll());
        } else if(maxPQ.size() > minPQ.size() + 1) {
            minPQ.add(maxPQ.poll());
        }
    }
    
    private double getMedian() {
        if(minPQ.size() == 0 && maxPQ.size() == 0) {
            return 0;
        }
        if(minPQ.size() == maxPQ.size()) {
            return ((double)minPQ.peek() + (double)maxPQ.peek()) / 2.0;
        } else {
            // Change to maxPQ.peek()
            return (double)maxPQ.peek();
        }
    }

}
