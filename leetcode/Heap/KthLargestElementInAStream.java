/**
 * Refer to
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/description/
 * Design a class to find the kth largest element in a stream. Note that it is the kth largest element 
   in the sorted order, not the kth distinct element.

    Your KthLargest class will have a constructor which accepts an integer k and an integer array nums, 
    which contains initial elements from the stream. For each call to the method KthLargest.add, 
    return the element representing the kth largest element in the stream.

    Example:

    int k = 3;
    int[] arr = [4,5,8,2];
    KthLargest kthLargest = new KthLargest(3, arr);
    kthLargest.add(3);   // returns 4
    kthLargest.add(5);   // returns 5
    kthLargest.add(10);  // returns 5
    kthLargest.add(9);   // returns 8
    kthLargest.add(4);   // returns 8
    Note: 
    You may assume that nums' length ≥ k-1 and k ≥ 1.
 *
 * Solution
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/discuss/150400/MinHeap-solution
 * leetcode.com/problems/kth-largest-element-in-a-stream/discuss/150400/MinHeap-solution/156979
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/discuss/152588/Explanation-of-MinHeap-solution-(NO-CODE)
   We can build a MinHeap that contains only k largest elements.
    On add:
    compare a new element x with min to decide if we should pop min and insert x
    take into account a case when heap_size is less than k
    Construction is simply calling the add function N times.

    Time complexity:
    Construction: O(N * logK)
    Adding: O(logK)
    Additional memory: O(K) (can be reduced to O(1) by reusing memory of the existing array)
*/
class KthLargest {
    // Use minimum heap and only keep heap's size as k,
    // the top element will be the result
    PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>();
    int size;
    public KthLargest(int k, int[] nums) {
        this.size = k;
        for(int num : nums) {
            add(num); // call add() method repeatly
        }
    }
    
    public int add(int val) {
        if(minPQ.size() < size) {
            minPQ.offer(val);
        } else if(minPQ.peek() < val) {
            minPQ.poll();
            minPQ.offer(val);
        }
        return minPQ.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */



