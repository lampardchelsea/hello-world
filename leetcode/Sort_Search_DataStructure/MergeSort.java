/**
 * Check the process of Top-Down merge sort part
 * http://algs4.cs.princeton.edu/22mergesort/
 * 
 * Refer to
 * http://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 * https://www.hackerearth.com/practice/algorithms/sorting/merge-sort/tutorial/
 */
// Time Complexity
// The list of size N is divided into a max of logN parts, and the merging of 
// all sublists into a single list takes O(N) time, the worst case run time of 
// this algorithm is O(NlogN)
// Space Complexity
// Additional aux array require O(N)
public class Merge {
    public Merge() {}

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);

    }

    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        // Base case, when two pointers encounter, stop sorting
        if (hi <= lo) {
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
        // In this example, the smallest section is every 2 items in array, like first and
        // second item combine as one smallest section ("M", "E"), before used on merge, it
        // needs to sort into ("E", "M")
        // Refer to 
        // http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX1.htm
        // http://pages.cs.wisc.edu/~jignesh/cs564/notes/lec07-sorting.pdf --> Pass 1(sort on current 
        // Page required before merge) 
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);


        // First copy all items into auxiliary array
        for (int k = 0; k < a.length; k++) {
            aux[k] = a[k];
        }

        // Set up two pointers separately point to the first item in first and second half section of input array
        int i = lo;
        int j = mid + 1;

        // Then with logic below after comparing and exchange to copy items back
        // The condition here is very important, exchange range is between index "lo" to index "hi",
        // because every time we only merge the current section declared between "lo" and "hi",
        // not the full length array.
        for (int k = lo; k <= hi; k++) {
            // First two conditions i > mid and j > hi are tricky part, because as observe debug,
            // as i++ or j++ will promote i or j over mid or hi, need to handle the case
            if (i > mid) { // Checks if first part comes to an end or not
                a[k] = aux[j++];
            } else if (j > hi) { // Checks if second part comes to an end or not
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) { // Checks which part has smaller element
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
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    public static void main(String[] args) {
        //String[] a = StdIn.readAllStrings();
        String[] a = {"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        Merge.sort(a);
        show(a);
    }
}














// Another style of merge sort implementation Java
// Refer to
// https://www.programiz.com/java-programming/examples/merge-sort
import java.util.Arrays;

// Merge sort in Java
class Main {
  // Merge two sub arrays L and M into array
  void merge(int array[], int p, int q, int r) {

    int n1 = q - p + 1;
    int n2 = r - q;

    int L[] = new int[n1];
    int M[] = new int[n2];

    // fill the left and right array
    for (int i = 0; i < n1; i++)
      L[i] = array[p + i];
    for (int j = 0; j < n2; j++)
      M[j] = array[q + 1 + j];

    // Maintain current index of sub-arrays and main array
    int i, j, k;
    i = 0;
    j = 0;
    k = p;

    // Until we reach either end of either L or M, pick larger among
    // elements L and M and place them in the correct position at A[p..r]
    // for sorting in descending
    // use if(L[i] >= <[j])
    while (i < n1 && j < n2) {
      if (L[i] <= M[j]) {
        array[k] = L[i];
        i++;
      } else {
        array[k] = M[j];
        j++;
      }
      k++;
    }

    // When we run out of elements in either L or M,
    // pick up the remaining elements and put in A[p..r]
    while (i < n1) {
      array[k] = L[i];
      i++;
      k++;
    }

    while (j < n2) {
      array[k] = M[j];
      j++;
      k++;
    }
  }

  // Divide the array into two sub arrays, sort them and merge them
  void mergeSort(int array[], int left, int right) {
    if (left < right) {

      // m is the point where the array is divided into two sub arrays
      int mid = (left + right) / 2;

      // recursive call to each sub arrays
      mergeSort(array, left, mid);
      mergeSort(array, mid + 1, right);

      // Merge the sorted sub arrays
      merge(array, left, mid, right);
    }
  }

  public static void main(String args[]) {

    // created an unsorted array
    int[] array = { 6, 5, 12, 10, 9, 1 };

    Main ob = new Main();

    // call the method mergeSort()
    // pass argument: array, first index and last index
    ob.mergeSort(array, 0, array.length - 1);

    System.out.println("Sorted Array:");
    System.out.println(Arrays.toString(array));
  }
}
