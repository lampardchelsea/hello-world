/**
 Refer to
 https://www.hackerearth.com/practice/algorithms/sorting/bubble-sort/tutorial/
*/
public class Solution {
    public void bubble_sort(int A[], int n) {
        int temp;
        for (int k = 0; k < n - 1; k++) {
            // (n - k - 1) is for ignoring comparisons of elements which have already been compared in earlier iterations
            for (int i = 0; i < n - k - 1; i++) {
                if (A[i] > A[i + 1]) {
                    // here swapping of positions is being done.
                    temp = A[i];
                    A[i] = A[i + 1];
                    A[i + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution q = new Solution();
        int[] arr = {9,7,8,3,2,1};
        q.bubble_sort(arr, 6);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                sb.append(arr[i]);
            } else {
                sb.append(arr[i]).append("->");
            }
        }
        System.out.println(sb.toString());
    }
}
