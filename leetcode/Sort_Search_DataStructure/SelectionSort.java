import java.util.Comparator;

/**
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
