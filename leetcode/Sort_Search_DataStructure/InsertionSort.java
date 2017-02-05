import java.util.Comparator;

/**
 * Refer to
 * http://algs4.cs.princeton.edu/21elementary/
 * http://algs4.cs.princeton.edu/21elementary/Insertion.java.html
 * 
 * The algorithm that people often use to sort bridge hands is to consider the cards one at a time, 
 * inserting each into its proper place among those already considered (keeping them sorted). 
 * In a computer implementation, we need to make space for the current item by moving larger items 
 * one position to the right, before inserting the current item into the vacated position
 *
 * Proposition. For randomly ordered arrays of length N with with distinct keys, insertion sort uses 
 * ~N2/4 compares and ~N2/4 exchanges on the average. The worst case is ~ N2/2 compares and ~ N2/2 
 * exchanges and the best case is N-1 compares and 0 exchanges.
 * Insertion sort works well for certain types of nonrandom arrays that often arise in practice, 
 * even if they are huge. An inversion is a pair of keys that are out of order in the array. 
 * For instance, E X A M P L E has 11 inversions: E-A, X-A, X-M, X-P, X-L, X-E, M-L, M-E, P-L, P-E, 
 * and L-E. If the number of inversions in an array is less than a constant multiple of the array 
 * size, we say that the array is partially sorted.
 * Proposition. The number of exchanges used by insertion sort is equal to the number of inversions 
 * in the array, and the number of compares is at least equal to the number of inversions and at 
 * most equal to the number of inversions plus the array size.
 * Property. For randomly ordered arrays of distinct values, the running times of insertion sort and 
 * selection sort are quadratic and within a small constant factor of one another.
 * 
 * Refer to
 * http://quiz.geeksforgeeks.org/insertion-sort/
 * E.g
 * 12, 11, 13, 5, 6
 * 
 * Let us loop for i = 1 (second element of the array) to 5 (Size of input array)
 * i = 1 --> Since 11 is smaller than 12, move 12 and insert 11 before 12
 * 11, 12, 13, 5, 6
 * 
 * i = 2 --> 13 will remain at its position as all elements in A[0..I-1] are smaller than 13
 * 11, 12, 13, 5, 6
 * 
 * i = 3 --> 5 will move to the beginning and all other elements from 11 to 13 will move one 
 * position ahead of their current position.
 * 5, 11, 12, 13, 6
 * 
 * i = 4 --> 6 will move to position after 5, and elements from 11 to 13 will move one position 
 * ahead of their current position.
 * 5, 6, 11, 12, 13
 * 
 */
public class InsertionSort {
	// This class should not be instantiated.
	private InsertionSort() {}
	
	public static void sort(Comparable[] a) {
		int n = a.length;
		for(int i = 0; i < n; i++) {
			for(int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
			// Same way: Move elements of array that great than
			// current element one position ahead of their
			// current position
//			for(int j = i; j > 0; j--) {
//				if(less(a[j], a[j - 1])) {
//					exch(a, j, j - 1);
//				}
//			}
		}
	}
	
	/**
	 * Rearranges the subarray a[lo..hi] in ascending order, using the natural order.
	 * @param a
	 * @param lo
	 * @param hi
	 */
	public static void sort(Comparable[] a, int lo, int hi) {
		for(int i = lo; i < hi; i++) {
			for(int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
		}
	}
	
	public static void sort(Object[] a, Comparator comaprator) {
		int n = a.length;
		for(int i = 0; i < n; i++) {
			for(int j = i; j > 0 && less(comaprator, a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
			// Same way: Move elements of array that great than
			// current element one position ahead of their
			// current position
//			for(int j = i; j > 0; j--) {
//				if(less(a[j], a[j - 1])) {
//					exch(a, j, j - 1);
//				}
//			}
		}		
	}
	
	public static void sort(Object[] a, Comparator comparator, int lo, int hi) {
		for(int i = lo; i < hi; i++) {
			for(int j = i; j > 0 && less(comparator, a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
		}
	}
	
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	public static boolean less(Comparator comparator, Object v, Object w) {
		return comparator.compare(v, w) < 0;
	}
	
	public static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public static void show(Comparable[] a) {
		for(int i = 0; i < a.length; i++) {
			StdOut.print(a[i]);
		}
	}
	
	public static void main(String[] args) {
		String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
		SelectionSort.sort(a);
		show(a);
	}
}

