/**
 * Check the process of Top-Down merge sort part
 * http://algs4.cs.princeton.edu/22mergesort/
 * 
 * Refer to
 * http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 *
 */
/**
 * Refer to
 * http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 *
 */
public class Merge {
	public Merge() {}
	
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
		
	}
	
	public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		// Base case, when two pointers encounter, stop sorting
		if(hi <= lo) {
			return;
		}
		
		// Recursive steps
		int mid = (lo + hi) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	
	public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
//		if(hi <= lo) {
//			return;
//		}
		
        // Precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
		// Before we start real merge, we need to verify each smallest section is being sorted
		// Refer to 
		// http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX1.htm
		// http://pages.cs.wisc.edu/~jignesh/cs564/notes/lec07-sorting.pdf --> Pass 1(sort on current 
		// Page required before merge) 
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);
		
		
		// First copy all items into auxiliary array
		for(int k = 0; k < a.length; k++) {
			aux[k] = a[k];
		}
		
		// Set up two pointers each point to the first item in half section of input array
		int i = lo;
		int j = mid + 1;

		// Then with logic below after comparing and exchange to copy items back
		// The condition here is very important, exchange range is between index "lo" to index "hi",
		// because every time we only merge the current section declared between "lo" and "hi",
		// not the full length array.
		for(int k = lo; k <= hi; k++) {
			// First two conditions i > mid and j > hi are tricky part, because as observe debug,
			// as i++ or j++ will promote i or j over mid or hi, need to handle the case
			if(i > mid) {
				a[k] = aux[j++];
			} else if(j > hi) {
				a[k] = aux[i++];
			} else if(less(aux[j], aux[i])) {
				a[k] = aux[j++];
			} else {
				a[k] = aux[i++];
			}
		}
		
		// Postcondition: a[lo .. hi] is sorted
        assert isSorted(a, lo, hi);
	}
	
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
	
    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
       return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }
    
    public static void main(String[] args) {
        //String[] a = StdIn.readAllStrings();
    	String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        Merge.sort(a);
        show(a);
    }
}
