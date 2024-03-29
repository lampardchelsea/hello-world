/**
 * Given an index k, return the kth row of the Pascal's triangle.
 * For example, given k = 3,
 * Return [1,3,3,1].
 * Note:
 * Could you optimize your algorithm to use only O(k) extra space?
 * 
 * Analyze
 * In-place iterator from left to right, every loop will replace
 * the original row into new value, the array length is initially set
 * to rowIndex
 * e.g rowIndex = 3, use i to iterate
 * [1,0,0,0] --> i = 0 (set most left element at index = 0 as 1)
 * [1,1,0,0] --> i = 1 (set most left element at index = 1 as 1)
 * [1,1,1,0] --> i = 2 (set most left element at index = 2 as 1)
 * [1,2,1,0] 
 * [1,2,1,1] --> i = 3 (set most left element at index = 3 as 1)
 * [1,2,3,1]
 * [1,3,3,1] 
 * This is the process to looply replace original array element to
 * create final array
*/
public class Solution {
    public List<Integer> getRow(int rowIndex) {
        // No need to create every row, just looply modify original
        // row, like an in-place iteration of original value, the
        // result will be the new row
        List<Integer> result = new ArrayList<Integer>();
        
        // Initial rowIndex length elements as 0, otherwise no target
        // for ArrayList set method to replace, will throw IndexOutofBound
        // exception as result is empty, e.g Input as rowIndex = 3, need
        // to initial [0,0,0,0].
        for(int i = 0; i <= rowIndex; i++) {
            result.add(0);
        }
        
        // From left to right set element one by one, first set most
        // left element as 1 corresponding to current row, e.g
        // Input as rowIndex = 3, and now for loop i = 0, the first
        // time, then set the 1st element as 1, [0,0,0,0] -> [1,0,0,0].
        for(int i = 0; i <= rowIndex; i++) {
            result.set(i, 1);
            
            // In-place iterator the original value to new value
            // from right to left.
            for(int j = i - 1; j > 0; j--) {
                result.set(j, result.get(j) + result.get(j - 1));
            }
        }
        
        return result;
    }
}
