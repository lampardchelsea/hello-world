// Solution 1: Heap Solution
/**
 * Refer to
 * https://leetcode.com/problems/sliding-window-maximum/?tab=Description
 * 
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the 
 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

	For example,
	Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
	
	Window position                Max
	---------------               -----
	[1  3  -1] -3  5  3  6  7       3
	 1 [3  -1  -3] 5  3  6  7       3
	 1  3 [-1  -3  5] 3  6  7       5
	 1  3  -1 [-3  5  3] 6  7       5
	 1  3  -1  -3 [5  3  6] 7       6
	 1  3  -1  -3  5 [3  6  7]      7
	Therefore, return the max sliding window as [3,3,5,5,6,7].

 * Note: 
 * You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
 * 
 * Follow up:
 * Could you solve it in linear time?
 * 
 * Hint:
 * How about using a data structure such as deque (double-ended queue)?
 * The queue size need not be the same as the window’s size.
 * Remove redundant elements and the queue should store only elements that need to be considered.
 *
 * Refer to
 * https://segmentfault.com/a/1190000003903509
 * http://articles.leetcode.com/sliding-window-maximum/
 * 
 * The important part is add a function as remove()
 * Refer to
 * http://grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/PriorityQueue.java/?v=source
 * http://algs4.cs.princeton.edu/24pq/IndexMaxPQ.java.html
 * 
 * E.g Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
    1st removeAt(int i)
	i = 2, n = 3, pq = [null, 3, 1, -1]
    after hit exch(i, n--)      pq = [null, 3, -1, 1]
	after hit swim(i)           pq = [null, 3, -1, 1]
	after hit sink(i)           pq = [null, 3, -1, 1]
	after hit pq[n + 1] = null  pq = [null, 3, -1, null]
	
	2nd removeAt(int i)
	i = 1, n = 3, pq = [null, 3, -1, -3]
	after hit exch(i, n--)      pq = [null, -3, -1, 3]
	after hit swim(i)           pq = [null, -3, -1, 3]  
	after hit sink(i)           pq = [null, -1, -3, 3]
	after hit pq[n + 1] = null  pq = [null, -1, -3, null]
	
	3rd removeAt(int i)
	i = 3, n = 3, pq = [null, 5, -3, -1]
	after pq[i] = null          pq = [null, 5, -3, null]
	
	4th removeAt(int i)
	i = 4, n = 4, pq = [null, 5, 3, null, -3, null, null, null]
	after pq[i] = null          pq = [null, 5, 3, null, null, null, null, null]
	
	5th removeAt(int i)
	i = 2, n = 5, pq = [null, 6, 5, null, null, 3, null, null]
	after hit exch(i)           pq = [null, 6, 3, null, null, 5, null, null]
	after hit swim(i)           pq = [null, 6, 3, null, null, 5, null, null]
	after hit sink(i)           pq = [null, 6, 3, null, -2147483648, 5, null, null]
	after hit pq[n + 1] = null  pq = [null, 6, 3, null, -2147483648, null, null, null] 
 * 
 */
public class MaxSlidingWindow {
	public int[] maxSlidingWindow(int[] nums, int k) {
		int len = nums.length;
		if(nums == null || len == 0) {
			return new int[0];
		}
		MaxPQ maxPQ = new MaxPQ();
		int[] result = new int[len - k + 1];
		for(int i = 0; i < len; i++) {
			if(i >= k) {		
				maxPQ.remove(nums[i - k]);
			}
			maxPQ.insert(nums[i]);
			if(i + 1 >= k) {
				result[i + 1 - k] = maxPQ.peek();
			}
		}
		return result;
    }
	
	private class MaxPQ {
        Integer[] pq;
        int n;
        public MaxPQ(int initialCapacity) {
            pq = new Integer[initialCapacity + 1];
            n = 0;
        }
        
        public MaxPQ() {
            this(1);
        }
        
        public void insert(Integer x) {
            if(n == pq.length - 1) {
                resize(2 * pq.length);
            }
            pq[++n] = x;
            swim(n);
        }
        
        public boolean remove(Integer x) {
        	int i = indexOf(x);
        	if(i == -1) {
        		return false;
        	} else {
        		removeAt(i);
        		return true;
        	}
        }
        
        public int indexOf(Integer x) {
        	if(x != null) {
        		for(int i = 1; i <= n; i++) {
        			if(x.equals(pq[i])) {
        				return i;
        			}
        		}
        	}
        	return -1;
        }
        
        /**
         * Refer to
         * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/PriorityQueue.java#PriorityQueue.removeAt%28int%29
         * Removes the ith element from queue. Normally this method leaves the elements at up to i-1, 
         * inclusive, untouched. Under these circumstances, it returns null. Occasionally, in order 
         * to maintain the heap invariant, it must swap a later element of the list with one earlier 
         * than i. Under these circumstances, this method returns the element that was previously at 
         * the end of the list and is now at some position before i. This fact is used by iterator.remove
         * so as to avoid missing traversing elements.
         *     private E removeAt(int i) {
			        assert i >= 0 && i < size;
			        modCount++;
			        int s = --size;
			        if (s == i) // removed last element
			            queue[i] = null;
			        else {
			            E moved = (E) queue[s];
			            queue[s] = null;
			            siftDown(i, moved);
			            if (queue[i] == moved) {
			                siftUp(i, moved);
			                if (queue[i] != moved)
			                    return moved;
			            }
			        }
			        return null;
			   }
         * 
         * --------------------------------------------------------------------------------------------------
         * Refer to
         * http://algs4.cs.princeton.edu/24pq/IndexMaxPQ.java.html
         * Remove the key on the priority queue associated with index {@code i}
         *     public void delete(int i) {
			        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
			        int index = qp[i];
			        exch(index, n--);
			        swim(index);
			        sink(index);
			        keys[i] = null;
			        qp[i] = -1;
			   }
         * 
         * 
         * @param i The index of element on MaxPQ need to remove
         * @return
         */
        public Integer removeAt(int i) {
        	if(i == n) {
        		// Remove last element without heapify
        		pq[i] = null;
        	} else {
        		Integer removed = pq[i];
        		exch(i, n--);
        		// Best try here !!!
        		// Don't use swim(n) or sink(1), the order of swim(i) and sink(i)
        		// can exchange, because no matter what value store at position i,
        		// it must go through both process to locate at final heapified
        		// location in MaxPQ
        		swim(i);
                sink(i);
        		pq[n + 1] = null;
        		if((n > 0) && (n == (pq.length - 1) / 4)) {
        			resize(pq.length / 2);
        		}
        		// For test, we can add assert function
        		assert isMaxHeap();
        		return removed;
        	}
        	return null;
        }
        
        // The change on less method is modified on object compare case,
        // especially with setting to Integer.MIN_VALUE when its NULL
        public boolean less(int v, int w) {
            if(pq[v] == null) {
        		pq[v] = Integer.MIN_VALUE;
        	}
        	if(pq[w] == null) {
        		pq[w] = Integer.MIN_VALUE;
        	}
        	return pq[v].compareTo(pq[w]) < 0;
        }
        
        // is pq[1..N] a max heap?
        private boolean isMaxHeap() {
            return isMaxHeap(1);
        }

        // is subtree of pq[1..n] rooted at k a max heap?
        private boolean isMaxHeap(int k) {
            if (k > n) return true;
            int left = 2 * k;
            int right = 2 * k + 1;
            if (left  <= n && less(k, left))  return false;
            if (right <= n && less(k, right)) return false;
            return isMaxHeap(left) && isMaxHeap(right);
        }
               
        public void resize(int len) {
        	Integer[] temp = new Integer[len];
            for(int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
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
        
        public void exch(int v, int w) {
        	Integer swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
            return n == 0;
        }

        public Integer peek() {
            if(isEmpty()) {
               return Integer.MAX_VALUE; 
            }
            return pq[1];
        }
    }
	
	
	public static void main(String[] args) {		
		int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
		int k = 3;
//		int[] nums = {1, -1};
//		int k = 1;
		MaxSlidingWindow m = new MaxSlidingWindow();
		int[] result = m.maxSlidingWindow(nums, k);
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
	
}






// Same solution with generic setup
/**
 * Generic version refer to 
 * Apache commons objectutils source code
 * http://commons.apache.org/proper/commons-lang/apidocs/src-html/org/apache/commons/lang3/ObjectUtils.html
 * 
 * Priceton MaxPQ
 * http://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
 */
public class MaxSlidingWindow {
	public int[] maxSlidingWindow(int[] nums, int k) {
		int len = nums.length;
		if(nums == null || len == 0) {
			return new int[0];
		}
		MaxPQ<Integer> maxPQ = new MaxPQ<Integer>();
		int[] result = new int[len - k + 1];
		for(int i = 0; i < len; i++) {
			if(i >= k) {		
				maxPQ.remove(nums[i - k]);
			}
			maxPQ.insert(nums[i]);
			if(i + 1 >= k) {
				result[i + 1 - k] = maxPQ.peek();
			}
		}
		return result;
    }
	
	private class MaxPQ<Key> {
        private Key[] pq;
        private int n;
        @SuppressWarnings("unchecked")
		public MaxPQ(int initialCapacity) {
            pq = (Key[]) new Object[initialCapacity + 1];
            n = 0;
        }
        
        public MaxPQ() {
            this(1);
        }
        
        public void insert(Key x) {
            if(n == pq.length - 1) {
                resize(2 * pq.length);
            }
            pq[++n] = x;
            swim(n);
        }
        
        public boolean remove(Key x) {
        	int i = indexOf(x);
        	if(i == -1) {
        		return false;
        	} else {
        		removeAt(i);
        		return true;
        	}
        }
        
        public int indexOf(Key x) {
        	if(x != null) {
        		for(int i = 1; i <= n; i++) {
        			if(x.equals(pq[i])) {
        				return i;
        			}
        		}
        	}
        	return -1;
        }

        public Key removeAt(int i) {
        	if(i == n) {
        		pq[i] = null;
        	} else {
        		Key removed = pq[i];
        		exch(i, n--);
        		swim(i);
                sink(i);
        		pq[n + 1] = null;
        		if((n > 0) && (n == (pq.length - 1) / 4)) {
        			resize(pq.length / 2);
        		}
        		// For test, we can add assert function
        		assert isMaxHeap();
        		return removed;
        	}
        	return null;
        }
        
        /**
         * Refer to
         * Apache commons objectutils source code
         * http://commons.apache.org/proper/commons-lang/apidocs/src-html/org/apache/commons/lang3/ObjectUtils.html
         * ---------------------------------------------------------------------------------------------------------------
	     * <p>Null safe comparison of Comparables.
	     * {@code null} is assumed to be less than a non-{@code null} value.</p>
	     *
	     * @param <T> type of the values processed by this method
	     * @param c1  the first comparable, may be null
	     * @param c2  the second comparable, may be null
	     * @return a negative value if c1 &lt; c2, zero if c1 = c2 and a positive value if c1 &gt; c2
		    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2) {
		        return compare(c1, c2, false);
		    }
		 * ---------------------------------------------------------------------------------------------------------------
	     * <p>Null safe comparison of Comparables.</p>
	     *
	     * @param <T> type of the values processed by this method
	     * @param c1  the first comparable, may be null
	     * @param c2  the second comparable, may be null
	     * @param nullGreater if true {@code null} is considered greater
	     *  than a non-{@code null} value or if false {@code null} is
	     *  considered less than a Non-{@code null} value
	     * @return a negative value if c1 &lt; c2, zero if c1 = c2
	     *  and a positive value if c1 &gt; c2
	     * @see java.util.Comparator#compare(Object, Object)
		    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2, final boolean nullGreater) {
		        if (c1 == c2) {
		            return 0;
		        } else if (c1 == null) {
		            return nullGreater ? 1 : -1;
		        } else if (c2 == null) {
		            return nullGreater ? -1 : 1;
		        }
		        return c1.compareTo(c2);
		    }
		 * ---------------------------------------------------------------------------------------------------------------
         */
        @SuppressWarnings("unchecked")
		public boolean less(int v, int w) {
//          if(pq[v] == null) {
//        		pq[v] = Integer.MIN_VALUE;
//        	}
//        	if(pq[w] == null) {
//        		pq[w] = Integer.MIN_VALUE;
//        	}
//        	return pq[v].compareTo(pq[w]) < 0;
        	int result;
        	if(pq[v] == pq[w]) {
        		result = 0;
        	} else if(pq[v] == null) {
        		result = -1;
        	} else if(pq[w] == null) {
        		result = 1;
        	} else {
        		result = ((Comparable<Key>)pq[v]).compareTo(pq[w]);
        	}    	
        	return result < 0;
        }
        
        // is pq[1..N] a max heap?
        private boolean isMaxHeap() {
            return isMaxHeap(1);
        }

        // is subtree of pq[1..n] rooted at k a max heap?
        private boolean isMaxHeap(int k) {
            if (k > n) return true;
            int left = 2 * k;
            int right = 2 * k + 1;
            if (left  <= n && less(k, left))  return false;
            if (right <= n && less(k, right)) return false;
            return isMaxHeap(left) && isMaxHeap(right);
        }
               
        public void resize(int len) {
        	@SuppressWarnings("unchecked")
			Key[] temp = (Key[]) new Object[len];
            for(int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
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
        
        public void exch(int v, int w) {
        	Key swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
            return n == 0;
        }

        public Key peek() {
//            if(isEmpty()) {
//               return Integer.MAX_VALUE; 
//            }
        	// For change Integer into Key, change
        	// setting from Integer.MAX_VALUE to NUll
            if(isEmpty()) {
                return null; 
             }
            return pq[1];
        }
    }
	
	
	public static void main(String[] args) {		
		int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
		int k = 3;
//		int[] nums = {1, -1};
//		int k = 1;
		MaxSlidingWindow m = new MaxSlidingWindow();
		int[] result = m.maxSlidingWindow(nums, k);
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
	
}



