/**
 * Refer to
 * http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 *
 */
public class QuanMerge {
	public QuanMerge() {}
	
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
	}
	
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
	
    public static void main(String[] args) {
        //String[] a = StdIn.readAllStrings();
    	String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        QuanMerge.sort(a);
        show(a);
    }
}

