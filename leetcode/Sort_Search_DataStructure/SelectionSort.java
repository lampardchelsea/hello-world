import java.util.Comparator;

/**
 * Refer to
 * http://algs4.cs.princeton.edu/21elementary/
 * http://algs4.cs.princeton.edu/21elementary/Selection.java.html
 * http://quiz.geeksforgeeks.org/selection-sort/
 * 
 * One of the simplest sorting algorithms works as follows: First, find the smallest item in 
 * the array, and exchange it with the first entry. Then, find the next smallest item and 
 * exchange it with the second entry. Continue in this way until the entire array is sorted. 
 * This method is called selection sort because it works by repeatedly selecting the smallest 
 * remaining item. 
 * Proposition. Selection sort uses ~n2/2 compares and n exchanges to sort an array of length n.
 */
public class SelectionSort {
	// This class should not be instantiated
	private SelectionSort() {}
	
	public static void sort(Comparable[] a) {
		int n = a.length;
		for(int i = 0; i < n; i++) {
			int min = i;
			for(int j = i + 1; j < n; j++) {
				if(!less(a[min], a[j])) {
					min = j;
				}
			}
			exch(a, i, min);
		}
	}
	
	public static void sort(Comparable[] a, Comparator comparator) {
		int n = a.length;
		for(int i = 0; i < n; i++) {
			int min = i;
			for(int j = i + 1; j < n; j++) {
				if(!less(comparator, a[min], a[j])) {
					min = j;
				}
			}
			exch(a, i, min);
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

// Rework
// Refer to
// https://www.hackerearth.com/practice/algorithms/sorting/selection-sort/tutorial/
public class Solution {
    public void selection_sort(int A[], int n) {
        // temporary variable to store the position of minimum element
        int minimum;
        // reduces the effective size of the array by one in  each iteration.
        for (int i = 0; i < n - 1; i++) {
            // assuming the first element to be the minimum of the unsorted array .
            minimum = i;
            // gives the effective size of the unsorted  array .
            for (int j = i + 1; j < n; j++) {
                if (A[j] < A[minimum]) { //finds the minimum element
                    minimum = j;
                }
            }
            // putting minimum element on its proper position.
            swap(A, minimum, i);
        }
    }

    // Must use swap(array, i, j) style since swap(array[i], array[j]) style won't modify
    // on original array
    public void swap(int[] A, int a, int b) {
        int temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }

    public static void main(String[] args) {
        Solution q = new Solution();
        int[] arr = {9,7,8,3,2,1};
        q.selection_sort(arr, 6);
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



