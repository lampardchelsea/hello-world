/******************************************************************************
 *  Compilation:  javac Shuffle.java
 *  Execution:    java Shuffle < california-gov.txt
 *  Dependencies: StdIn.java
 *  
 *  Reads in all lines of text from standard input, shuffles them,
 *  and print them in random order.
 *  Uses Knuth's shuffling shuffle.
 *
 *  The file california-gov.txt contains a list of the 135
 *  candidates in the October 7, 2003 California governor's runoff
 *  election. The file cards.txt contains a list of 52 playing cards.
 *
 *
 *  % java Shuffle < california-gov.txt
 *  Randall D. Sprague
 *  Cheryl Bly-Chester
 *  Cruz M. Bustamante
 *  Darrin H. Scheidle
 *  Badi Badiozamani
 *  ...
 *
 *  % java Shuffle < cards.txt
 *  Four_of_Spades
 *  Deuce_of_Spades
 *  King_of_Spades
 *  Ace_of_Diamonds
 *  ...
 *
 * Refer to 
 * http://introcs.cs.princeton.edu/java/15inout/Shuffle.java.html
 ******************************************************************************/
public class Shuffle {
	public static void shuffle(String[] a) {
		int n = a.length;
		for(int i = 0; i < n; i++) {
			int r = (int) (i + Math.random() * (n - i));
			exchange(a, i, r);
		}
	}
	
	public static void exchange(String[] a, int x, int y) {
		String tmp = a[x];
		a[x] = a[y];
		a[y] = tmp;
	}
	
	public static void show(String[] a) {
		for(int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		String[] a = {"a", "b", "c", "d"};
		shuffle(a);
		show(a);
	}
}
