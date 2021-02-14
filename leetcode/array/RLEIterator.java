/**
Refer to
https://leetcode.com/problems/rle-iterator/
Write an iterator that iterates through a run-length encoded sequence.

The iterator is initialized by RLEIterator(int[] A), where A is a run-length encoding of some sequence.  
More specifically, for all even i, A[i] tells us the number of times that the non-negative integer value A[i+1] is repeated in the sequence.

The iterator supports one function: next(int n), which exhausts the next n elements (n >= 1) and returns 
the last element exhausted in this way.  If there is no element left to exhaust, next returns -1 instead.

For example, we start with A = [3,8,0,9,2,5], which is a run-length encoding of the sequence [8,8,8,5,5].  
This is because the sequence can be read as "three eights, zero nines, two fives".

Example 1:
Input: ["RLEIterator","next","next","next","next"], [[[3,8,0,9,2,5]],[2],[1],[1],[2]]
Output: [null,8,8,5,-1]
Explanation: 
RLEIterator is initialized with RLEIterator([3,8,0,9,2,5]).
This maps to the sequence [8,8,8,5,5].
RLEIterator.next is then called 4 times:

.next(2) exhausts 2 terms of the sequence, returning 8.  The remaining sequence is now [8, 5, 5].

.next(1) exhausts 1 term of the sequence, returning 8.  The remaining sequence is now [5, 5].

.next(1) exhausts 1 term of the sequence, returning 5.  The remaining sequence is now [5].

.next(2) exhausts 2 terms, returning -1.  This is because the first term exhausted was 5,
but the second term did not exist.  Since the last term exhausted does not exist, we return -1.

Note:

0 <= A.length <= 1000
A.length is an even integer.
0 <= A[i] <= 10^9
There are at most 1000 calls to RLEIterator.next(int n) per test case.
Each call to RLEIterator.next(int n) will have 1 <= n <= 10^9.
*/

// Solution 1: Queue for actual array but MLE (Memory Limit Exceeds)
class RLEIterator {
    Queue<Integer> q;
    public RLEIterator(int[] A) {
        q = new LinkedList<Integer>();
        int n = A.length;
        for(int i = 0; i < n - 1; i += 2) {
            for(int j = 0; j < A[i]; j++) {
                q.offer(A[i + 1]);
            }
        }
    }
    
    public int next(int n) {
        int result = 0;
        int size = q.size();
        if(n <= size) {
            while(n > 0) {
                result = q.remove();
                n--;
            }
        } else {
            result = -1;
        }
        return result;
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(A);
 * int param_1 = obj.next(n);
 */
 
// Solution 2: index no need actual array
// Style 1:
class RLEIterator {
    int[] A;
    int index;
    public RLEIterator(int[] A) {
        this.A = A;
        this.index = 0;
    }
    
    public int next(int n) {
        while(index < A.length) { // all elements exhausted?
            if(n <= A[index]) { // find the corresponding value.
                A[index] -= n; // deduct n from A[index].
                return A[index + 1]; // A[index + 1] is the nth value.
            }
            n -= A[index]; // not find the value yet, deduct A[index] from n.
            index += 2; // move on to next group of same values.
        }
        return -1;
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(A);
 * int param_1 = obj.next(n);
 */

// Style 2:
class RLEIterator {
    int[] A;
    int index;
    public RLEIterator(int[] A) {
        this.A = A;
        this.index = 0;
    }
    
    public int next(int n) {
        while(index < A.length && n > A[index]) { // exhaust as many terms as possible.
            n -= A[index]; // exhaust A[idx + 1] for A[idx] times. 
            index += 2; // move to next term
        }
        if(index < A.length) { // not exhaust all terms.
            A[index] -= n;
            return A[index + 1];
        }
        return -1; // exhaust all terms but still not enough.
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(A);
 * int param_1 = obj.next(n);
 */
