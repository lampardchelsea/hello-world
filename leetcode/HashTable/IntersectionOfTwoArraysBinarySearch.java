/**
 * Given two arrays, write a function to compute their intersection.
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
 * Note:
 * Each element in the result must be unique. 
 * The result can be in any order
*/
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        int length_1 = nums1.length;
        int length_2 = nums2.length;
        
        // Create temp array which filter out all duplicate
        // elements in nums1, no need to sort order, prepare
        // as targets to find in nums2.
        Set<Integer> set = new HashSet<Integer>();
        
        for(int i = 0; i < length_1; i++) {
            set.add(nums1[i]);
        }

        int size = set.size();
        int[] temp = new int[size];
        int count = 0;
        for(Integer j : set) {
            temp[count++] = j;
        }

        // Prepare nums2 as sorted for binary search because
        // binary search require sorted array as input
        Arrays.sort(nums2);
        
        // Prepare arraylist to store searched result
        List<Integer> array = new ArrayList<Integer>();

        // For each element in temp array call binary search,
        // return true if exist in nums2, return false if not
        for(int k = 0; k < size; k++) {
            if(binarySearch(nums2, temp[k])) {
                array.add(temp[k]);
            }
        }
        
        // Convert searched result into array structure to return
        int result_size = array.size();
        int[] result = new int[result_size];
        int result_count = 0;
        for(int m = 0; m < result_size; m++) {
            result[result_count++] = array.get(m);
        }
        
        return result;
    }
    
    public boolean binarySearch(int[] array, int target) {
        int lo = 0;
        int hi = array.length - 1;
        
        while(lo <= hi) {
            int mid = (lo + hi) / 2;
            if(target > array[mid]) {
                lo = mid + 1;
            } else if(target < array[mid]) {
                hi = mid - 1;
            } else {
                return true;
            }
        }
        
        return false;
    }
}
