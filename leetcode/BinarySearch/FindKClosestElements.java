https://leetcode.com/problems/find-k-closest-elements/

Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array. The result should also be sorted in ascending order.

An integer a is closer to x than an integer b if:

- |a - x| < |b - x|, or
- |a - x| == |b - x| and a < b
 
Example 1:
```
Input: arr = [1,2,3,4,5], k = 4, x = 3
Output: [1,2,3,4]
```

Example 2:
```
Input: arr = [1,2,3,4,5], k = 4, x = -1
Output: [1,2,3,4]
```

Constraints:
- 1 <= k <= arr.length
- 1 <= arr.length <= 104
- arr is sorted in ascending order.
- -104 <= arr[i], x <= 104
---
Attempt 1: 2022-10-03

Solution 1: 10min, Binary Search Find Target Occurrence (template based on while(lo <= hi), refer L704.Binary Search)
```
class Solution { 
    public List<Integer> findClosestElements(int[] arr, int k, int x) { 
        int len = arr.length;  
        int lo = 0;  
        int hi = len - k;  
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2;  
            if(mid == len - k || arr[mid] + arr[mid + k] >= 2 * x) {  
                hi = mid - 1;  
            } else {  
                lo = mid + 1;  
            }  
        }  
        List<Integer> result = new ArrayList<Integer>();  
        for(int i = lo; i < lo + k; i++) {  
            result.add(arr[i]);  
        }  
        return result;  
    } 
}

Space Complexity: O(1)         
Time Complexity: O(log(n - k)) to binary research and find result
```

Potential  Wrong Solution since not able to explain why hi = len - 1 - k works, ideally hi = len - k only, but when hi = len - 1 - k the answer is correct in coincidence 
```
class Solution { 
    public List<Integer> findClosestElements(int[] arr, int k, int x) { 
        int len = arr.length; 
        int lo = 0; 
        // Ideally 'hi' should be (len - k) only, not (len - 1 - k)
        int hi = len - 1 - k;
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(arr[mid] + arr[mid + k] >= 2 * x) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        List<Integer> result = new ArrayList<Integer>(); 
        for(int i = lo; i < lo + k; i++) { 
            result.add(arr[i]); 
        } 
        return result; 
    } 
}
```

Refer to
https://leetcode.com/problems/find-k-closest-elements/discuss/1311191/C%2B%2BJavaPython-Binary-Search-With-Diagram-explain-Clean-and-Concise

Idea
- Let f[i] = abs(x - arr[i]), where 0 <= i < n, we have chart like the following picture.
- We need to choose the staring index startIdx so that f[startIdx...startIdx+k-1] is the most minimum numbers among all numbers.
- We can binary search to find the left most startIdx where f[startIndx] <= f[startIdx+k].


Implementation

```
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int n = arr.length;
        int left = 0, right = n - k, startIdx = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (mid + k == n || x - arr[mid] <= arr[mid+k] - x) {
                startIdx = mid; // Found a valid answer -> Update
                right = mid - 1; // Try to find the left most answer in the left side
            } else {
                left = mid + 1; // Find in the right side
            }
        }
        return Arrays.stream(arr, startIdx, startIdx + k).boxed().collect(Collectors.toList());
    }
}
```

---
Solution 2: 10min, Binary Search Find Target Occurrence (template based on while(lo < hi), refer L34.Find First and Last Position of Element in Sorted Array)
```
class Solution { 
    public List<Integer> findClosestElements(int[] arr, int k, int x) { 
        int len = arr.length; 
        int lo = 0; 
        int hi = len - k;
        while(lo < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(arr[mid] + arr[mid + k] < 2 * x) { 
                lo = mid + 1; 
            } else { 
                hi = mid; 
            } 
        } 
        List<Integer> result = new ArrayList<Integer>(); 
        for(int i = lo; i < lo + k; i++) { 
            result.add(arr[i]); 
        } 
        return result; 
    } 
}

Space Complexity: O(1)         
Time Complexity: O(log(n - k)) to binary research and find result
```

---
Refer to
https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC%2B%2BPython-Binary-Search-O(log(N-K)-%2B-K)

Intuition

The array is sorted. If we want find the one number closest to x,we don't have to check one by one, its straightforward to use binary research.
Now we want the k closest, the logic should be similar.

Explanation

Assume we are taking A[i] ~ A[i + k -1].We can binary research iWe compare the distance between x - A[mid] and A[mid + k] - x

Listed the following cases: Assume A[mid] ~ A[mid + k] is sliding window
case 1: x - A[mid] < A[mid + k] - x, need to move window go left-------x----A[mid]-----------------A[mid + k]----------
case 2: x - A[mid] < A[mid + k] - x, need to move window go left again-------A[mid]----x-----------------A[mid + k]----------
case 3: x - A[mid] > A[mid + k] - x, need to move window go right-------A[mid]------------------x---A[mid + k]----------
case 4: x - A[mid] > A[mid + k] - x, need to move window go right-------A[mid]---------------------A[mid + k]----x------

If x - A[mid] > A[mid + k] - x,it means A[mid + 1] ~ A[mid + k] is better than A[mid] ~ A[mid + k - 1],and we have mid smaller than the right i.So assign left = mid + 1.


Important

Note that, you SHOULD NOT compare the absolute value of abs(x - A[mid]) and abs(A[mid + k] - x).It fails at cases like A = [1,1,2,2,2,2,2,3,3], x = 3, k = 3

The problem is interesting and good. Unfortunately the test cases is terrible. The worst part of Leetcode test cases is that, you submit a wrong solution but get accepted. You didn't read my post and up-vote carefully, then you miss this key point.

Complexity

Time O(log(N - K)) to binary research and find result. 
Space O(K) to create the returned list.

Style 1:
```
    public List<Integer> findClosestElements(int[] A, int k, int x) { 
        int left = 0, right = A.length - k; 
        while (left < right) { 
            int mid = (left + right) / 2; 
            if (x - A[mid] > A[mid + k] - x) 
                left = mid + 1; 
            else 
                right = mid; 
        } 
        return Arrays.stream(A, left, left + k).boxed().collect(Collectors.toList()); 
    }
    
    // With comments
    public List<Integer> findClosestElements(int[] arr, int k, int x) { 
        List<Integer> res = new ArrayList<>(); 
	int lo = 0, hi = arr.length - k; // did not +1 because of boundary of arr[intervalEnd + 1] 
	// lo, hi and mid are all in respect of intervalStart 
	while (lo < hi) { 
	    int mid = (lo + hi) / 2; 
	    int intervalStart = mid;
	    int intervalEnd = mid + k - 1;
	    if (arr[intervalEnd + 1] - x < x - arr[intervalStart]) // compare intervalEnd +1 with intervalStart 
		lo = mid + 1; // strict move right, because mid could not be intervalStart 
	    else 
		hi = mid; // move left, because mid could be intervalStart 
	} 
	while (res.size() < k) 
	    res.add(arr[lo++]); 
	return res; 
    }
```
Style 2:
```
    public List<Integer> findClosestElements(int[] arr, int k, int x) { 
        int low = 0; 
        int high = arr.length - 1 - k; 
        while (low <= high) { 
            int mid = low + (high - low) / 2; 
             
            if (x - arr[mid] <= arr[mid + k] - x) { 
                high = mid - 1; 
            } else { 
                low = mid + 1; 
            } 
        } 
        List<Integer> res = new ArrayList<>(); 
        for (int i = low; i < low + k; i++) { 
            res.add(arr[i]); 
        } 
        return res; 
    } 
```

Refer to
https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC++Python-Binary-Search-O(log(N-K)-+-K)/819479
So the main idea of it is to find out the lower bound of that k length range. The numbers between "left" and "right" are the candidates of the lower bound.

The if condition "x - A[mid] > A[mid + k] - x" is used to compare A[mid] and A[mid+k], see which is closer to x.

If A[mid] is closer to x, then A[mid+k] can never be in the k length range. So we can confidently remove all (A[mid+1], A[mid+2], A[mid+3]...) from the candidates list by set right=mid.

If A[mid+k] is closer to x, then A[mid] can never be in the k length range. So we can confidently remove all (...A[mid-2], A[mid-1], A[mid]) from the candidates list by set left=mid+1.

Once we remain only one candidate, that is left==right, we got our final lower bound.

For those who are finding it hard to understand x - A[mid] > A[mid + k] - x think in terms of midpoint of the two values x > (A[mid + k] + A[mid])/2.

---
Why we compare x - A[i] with A[i + k] - x instead of A[i + k - 1] - x ?

Refer to
https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC++Python-Binary-Search-O(log(N-K)-+-K)/308767
One tricky part worth call out is, A[mid + k] is the right open boundary, i.e. given the current range is [i, i+k-1], we compare x - A[i] with A[i+k] - x instead of A[i+k-1] - x.
Why? 
An example is [...3,3,4,7...], k = 3, x = 5, if the range is [3, 3, 4], compare 5-3 and 4-5 will move the range right, get a wrong answer [3,4,7]. So the comparison is to answer the question: if we move the range right, can we get a better option?

https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC++Python-Binary-Search-O(log(N-K)-+-K)/884078
If x - A[mid] > A[mid + k] - x, it means A[mid + 1] ~ A[mid + k] is better than A[mid] ~ A[mid + k - 1]
This means we compare below 2 windows to decided which window is 'better'. The only difference between these 2 windows are element A[mid] and A[mid + k], so we only need check who is closer to x, so its window would be 'better', i.e. all elements are closer to x compared to all elements in the other window.
Because you are comparing these two set: <A[mid] ~ A[mid + k - 1]> vs. <A[mid + 1] ~ A[mid + k]>. The only two different numbers in these two sets are A[mid] and A[mid + k].
```
A[mid], A[mid + 1], ..., A[mid + k - 1]
        A[mid + 1], ..., A[mid + k - 1], A[mid + k]
```
The above solution is comparing the 2 windows, starting at mid and mid + 1. So we should be able to come to a similar implementation by comparing the windows starting at mid and mid - 1:
```
           A[mid], ..., A[mid + k - 1], A[mid + k - 1]
A[mid -1], A[mid], ..., A[mid + k - 2]
```

https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC++Python-Binary-Search-O(log(N-K)-+-K)/995310
It was easy on thinking the comparison of a[mid] and a[mid+k] happens in a single window but it is actually about 2 different windows and elements b/w mid and mid+k (common elements) won't affect .. for example
```
Input:[1,3,3,4,5,7,7,8,8,8] k=6 x=6
Output:[5,7,7,8,8,8]
Expected:[4,5,7,7,8,8]
window-1: [5,7,7,8,8,8]
window-2: [4,5,7,7,8,8]
```
leftmost '4' is better closer than rightmost '8' ...while common elements wont affect decision to move left or right.
