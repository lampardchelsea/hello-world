/**
Refer to
https://leetcode.com/discuss/interview-question/124823/dot-product-of-sparse-vector
Suppose we have very large sparse vectors (most of the elements in vector are zeros)

Find a data structure to store them
Compute the Dot Product.
Follow-up:
What if one of the vectors is very small?
a = [(1,2),(2,3),(100,5)]
b = [(0,5),(1,1),(100,6)]

https://leetcode.com/discuss/interview-question/124823/Facebook-or-Onsite-or-Dot-product-of-sparse-vectors/760251
how does
a = [(1,2),(2,3),(100,5)]
represent a sparse vector?

in each pair the first number is the index of the value and the second is the value itself. 
You can imagine that logically (0, 0), (3, 0), ..., and (99, 0) are in a, but omitted.
*/

// Solution 1: Two Pointers
// Refer to
// https://leetcode.com/discuss/interview-question/124823/Facebook-or-Onsite-or-Dot-product-of-sparse-vectors/147738
class Solution {
    public boolean dotProduct(int[][] a, int[][] b) {
	    int i = 0;
		int j = 0;
		int result = 0;
		while(i < a.length && j < b.length) {
		    if(a[i][0] == b[i][0]) {
			    result += a[i][1] * b[j][1];
				i++;
				j++;
			} else if(a[i][0] < b[j][0]) {
			    i++;
			} else {
			    j++;
			}
		}
		return result;
    }
}

// Solution 2: HashMap
// Refer to
// https://leetcode.com/discuss/interview-question/124823/Facebook-or-Onsite-or-Dot-product-of-sparse-vectors/341472
public int dotProduct(Map<Integer, Integer> vec1, Map<Integer, Integer> vec2) {
    if (vec2.size() < vec1.size()) {
        var tmp = vec1;
        vec1 = vec2;
        vec2 = tmp;
    }
    int product = 0;
    for (var entry : vec1.entrySet()) {
        product += vec2.getOrDefault(entry.getKey(), 0) * entry.getValue();
    }
    return product;
}
If one is big, one is small, iterate over small, and do a binary search in the big one.

/**
Refer to
https://leetcode.jp/problemdetail.php?id=1570
Given two sparse vectors, compute their dot product.

Implement class SparseVector:
SparseVector(nums) Initializes the object with the vector nums
dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute 
the dot product between two SparseVector.

Follow up: What if only one of the vectors is sparse?

Example 1:
Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
Output: 8
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8

Example 2:
Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
Output: 0
Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0

Example 3:
Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
Output: 6

Constraints:
n == nums1.length == nums2.length
1 <= n <= 10^5
0 <= nums1[i], nums2[i] <= 100
*/

// Solution 1: Two Pointers
// Refer to
// https://walkccc.me/LeetCode/problems/1570/
class T {
    public int index;
    public int num;
    public T(int index, int num) {
        this.index = index;
        this.num = num;
    }
}

class SparseVector {
    private List<T> v = new ArrayList<>(); // [(index, num)]
    
    SparseVector(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                v.add(new T(i, nums[i]));   
            }           
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int result = 0;
        for(int i = 0, j = 0; i < v.size() && j < vec.v.size();) {
            if(v.get(i).index == vec.v.get(j).index) {
                result += v.get(i++).num * vec.v.get(j++).num;
            } else if(v.get(i).index < vec.v.get(j).index) {
                i++;
            } else {
                j++; 
            }        
        }
        return result;
    }
}

// Solution 2: HashMap
// Refer to
// https://walkccc.me/LeetCode/problems/1570/
class T {
    public int index;
    public int num;
    public T(int index, int num) {
        this.index = index;
        this.num = num;
    }
}

class SparseVector {
    private Map<Integer, Integer> map = new HashMap<>();
    
    SparseVector(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                map.put(new T(i, nums[i]));   
            }           
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int result = 0;
        // Assign smaller map to vec.map
        if(vec.map.size() > this.map.size()) {
            Map<Integer, Integer> temp = vec.map;
            vec.map = this.map;
            this.map = temp;
        }
        // Use smaller map to scan
        for(Map.Entry<Integer, Integer> e : vec.map.entrySet()) {
            result += this.map.getOrDefault(e.getKey(), 0) * e.getValue();
        }
        return result;
    }
}
