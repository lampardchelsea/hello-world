/**
Refer to
https://leetcode.com/problems/diagonal-traverse-ii/
Given a list of lists of integers, nums, return all elements of nums in diagonal order as shown in the below images.

Example 1:
Input: nums = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,4,2,7,5,3,8,6,9]

Example 2:
Input: nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
Output: [1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]

Example 3:
Input: nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
Output: [1,4,2,5,3,8,6,9,7,10,11]

Example 4:
Input: nums = [[1,2,3,4,5,6]]
Output: [1,2,3,4,5,6]

Constraints:
1 <= nums.length <= 10^5
1 <= nums[i].length <= 10^5
1 <= nums[i][j] <= 10^9
There at most 10^5 elements in nums.
*/

// Solution 1: Hashmap + different sub-diagonal have different i + j as key (In a 2D matrix, elements in the same diagonal have same sum of their indices)
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/HashTable/SortTheMatrixDiagonally.java
/**
Similar way as 1329. Sort The Matrix Diagonally
-----------------------------------------
e.g
1  2  3  4  5
6  7
8  9  10
11 12 13 14
-----------------------------------------
From bottom left to top right as a sub-diagonal, we have 7 sub-diagonals
same sub-diagonal formula -> row index + col index
sub-diag 1: 0 + 0                == key as 0
sub-diag 2: 1 + 0, 0 + 1         == key as 1
sub-diag 3: 2 + 0, 1 + 1, 0 + 2  == key as 2
sub-diag 4: 3 + 0, 2 + 1, 0 + 3  == key as 3
sub-diag 5: 3 + 1, 2 + 2, 0 + 4  == key as 4
sub-diag 6: 3 + 2                == key as 5
sub-diag 7: 3 + 3                == key as 6
-----------------------------------------
*/

// https://leetcode.com/problems/diagonal-traverse-ii/discuss/597741/Clean-Simple-Easiest-Explanation-with-a-picture-and-code-with-comments
// https://leetcode.com/problems/diagonal-traverse-ii/discuss/597698/JavaC%2B%2B-HashMap-with-Picture-Clean-code-O(N)
// https://leetcode.com/problems/diagonal-traverse-ii/discuss/597698/JavaC++-HashMap-with-Picture-Clean-code-O(N)/518155
/**
class Solution {
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int rows = nums.size(), maxCol = 0, cntNum = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int r=rows-1; r>=0; r--){ // traverse in this way avoid "addFirst", we take care of the sequence of output
            int col = nums.get(r).size(); // get the current col size
            maxCol = Math.max(maxCol, col); //update the max col size to compute the max key value
            cntNum += col; //update the # of num 
            for(int c=0; c<col;c++){
                map.putIfAbsent(r+c, new ArrayList<>());
                map.get(r+c).add(nums.get(r).get(c));
            }
        }
        int[] res = new int[cntNum];
        int idx = 0;
        for(int key = 0; key< rows+maxCol-1; key++){ // maxKey = rows-1+maxCol-1
            List<Integer> value = map.get(key);
            if (value == null) continue;
            for (int v : value) 
                res[idx++] = v;
        }
        return res;
    }
}
*/
class Solution {
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int max_cols = 0;
        int n = 0;
        Map<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();
        int rows = nums.size();
        for(int i = 0; i < rows; i++) {
            List<Integer> num = nums.get(i);
            int cols = num.size();
            n += cols;
            max_cols = Math.max(max_cols, cols);
            for(int j = 0; j < cols; j++) {
                int key = i + j;
                int val = num.get(j);
                map.putIfAbsent(key, new LinkedList<Integer>());
                map.get(key).addFirst(val);
            }
        }
        int[] result = new int[n];
        int max_key = rows - 1 + max_cols - 1;
        int idx = 0;
        for(int k = 0; k <= max_key; k++) {
            LinkedList<Integer> list = map.get(k);
            if(list != null) {
                for(int a : list) {
                    result[idx++] = a;
                }           
            }
        }
        return result;
    }
}

