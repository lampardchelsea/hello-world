/**
 * Refer to http://algorithms.tutorialhorizon.com/print-all-combinations-of-subset-of-size-k-from-given-array/
 * Objective: Given an array of integers of size N, print all the subsets of size k. (k<=N)
 * Example:
 * Generate all subsets of a fixed size k of a given set [1,2,3…n]. e.g, if n=5 and k=3, the output will look like
    1 2 3     1 2 4     1 2 5
    1 3 4     1 3 5     1 4 5
    2 3 4     2 3 5     2 4 5
    3 4 5
 *    
 * Solution:
 * 1. Create an boolean array of the same size as the given array.
 * 2. Now for every integer we have two options, whether to select it or ignore it.
 * 3. Now if we select it, we will put TRUE in the boolean array at the corresponding index or if we ignore it, put FALSE at that index.
 * 4. We will start with currentLength =0 and do the recursive calls for both the options mentioned in the previous step.
 * 5. If we select an integer to print, make currentLength +1 and if we ignore, dont change currentLength.
 * 6. Another important factor is from which index you will start making the subset of size k. Initialize start = 0, and with every 
 *    recursive call, make start + 1 ( for both the scenarios mentioned in the steps above).
 * 7. Print the elements when currentLength = k.
 * 
 * And this is exactly same way how to handle required length subsequence of a string
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/RequiredLengthSubsequence.java
*/

public class CombinationsOfSubsetOfSizeKFromGivenArray {
	public static void subset(int[] A, boolean[] B, int k) {
		int currentLength = 0;
		int start = 0;
		
		subset(A, B, k, start, currentLength);
	}
	
	public static void subset(int[] A, boolean[] B, int k, int start, int currentLength) {
		if(currentLength == k) {
			System.out.print("[");
			for(int i = 0; i < A.length; i++) {
				if(B[i]) {
					System.out.print(A[i] + " ");
				}
			}
			
			System.out.print("]");
			return;
		}
		
		// Another important factor is from which index you will start making the subset of size k.
		// Initialize start = 0, and with every recursive call, make start + 1 ( for both the scenarios)
		// e.g Initially, int[] A = {1, 2, 3}, corresponding int[] B = {false, false, false}, start = 0,
		// currentLength = 0, when we try to judge the first item of A as 1(either select it or not), we
		// change our "start"(final array construction index) from 0 to 1, when it goes into next iteration
		// as judge on second item of A as 2, we will change "start" from 1 to 2. So, "start" actually
		// represent the item index we make judgement on. When "start" equals to A's length means we finish
		// scanning, should return.
		if(start == A.length) {
			return;
		}
		
		// For every index we have two options,
		// Either we select it, means put true in B[] and make currentLength + 1
		B[start] = true;
		subset(A, B, k, start + 1, currentLength + 1);
		
		// OR we dont select it, means put false in B[] and dont increase currentLength
		B[start] = false;
		subset(A, B, k, start + 1, currentLength);
	}
	
	public static void main(String[] args) {
		int[] A = {1, 2, 3, 4, 5};
		boolean[] B = new boolean[A.length];
		int k = 2;
		
		subset(A, B, k);
 	}
}
