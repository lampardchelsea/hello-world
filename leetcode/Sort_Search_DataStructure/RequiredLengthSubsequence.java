/******************************************************************************
 *  Compilation:  javac Subsequence.java
 *  Execution:    java Subsequence s k
 *
 *  Print out all subsequences of the string s of length k.
 *  Subsequences of a string. Write a program Subsequence.java that takes a string 
 *  command-line argument s and an integer command-line argument k and prints out 
 *  all subsequences of s of length k.
 *
 *  % java Subsequence abcd 3
 *  abc
 *  abd
 *  acd
 *  bcd
 *
 *  % java Subsequence abcc 3
 *  abc
 *  abc
 *  acc
 *  bcc
 *
 * Refer to http://introcs.cs.princeton.edu/java/23recursion/Subsequence.java.html
 *
 * In normal case, we don't have limitaion on length k, the code and result is below:
 *
     public class Subsequence {
        public static void subsequences(String word) {
          helper("", word);
        }

        public static void helper(String partialSubsequence, String word) {
          if(word.isEmpty()) {
            System.out.println(partialSubsequence);
          } else {
            helper(partialSubsequence, word.substring(1)); 
            helper(partialSubsequence + word.charAt(0), word.substring(1));
          }
        }

        public static void main(String[] args) {
          subsequences("abcd");
        }
    }
 * 
 * The result is
    d
    c
    cd
    b
    bd
    bc
    bcd
    a
    ad
    ac
    acd
    ab
    abd
    abc
    abcd
    
 * But when we add limitaion on requirement, the length k will become an additional check
 * condition for return.
 ******************************************************************************/
// Solution 1:
public class SubsequenceWithRequiredLength {
    public static void print(String prefix, String remaining, int k) {
      // If reach the digit limitation, need to print out current constructed
      // temp string and return to previous call of print.
      if(k == 0) {
        System.out.println(prefix);
        return;
      }

      // Check wether remaining length is 0, if yes, return back
      if(remaining.isEmpty()){
        return;
      }
      
      // Not contain the first letter of remaining string
      print(prefix, remaining.substring(1), k);
      // Contain the first letter of remaining string
      print(prefix + remaining.charAt(0), remaining.substring(1), k - 1);
    }

    public static void main(String[] args) { 
		String s = "abcd";
		int k = 3;
		print("", s, k);
    }
}

// Solution 2: Same way as Solution 1, but easier to understand
// Refer to CombinationsOfSubsetOfSizeKFromGivenArray.java
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/CombinationsOfSubsetOfSizeKFromGivenArray.java
// http://algorithms.tutorialhorizon.com/print-all-combinations-of-subset-of-size-k-from-given-array/
public class SubsequenceWithRequiredLength { 
	public static void print(String remaining, int k) {
		char[] A = remaining.toCharArray();
		boolean[] B = new boolean[A.length];
		int start = 0;
		int currentLength = 0;
		print(A, B, k, start, currentLength);
	}
	
	public static void print(char[] A, boolean[] B, int k, int start, int currentLength) {
		if(currentLength == k) {
			System.out.print("[");
			for(int i = 0; i < A.length; i++) {			
				if(B[i]) {
					System.out.print(A[i]);
				}			
			}
			System.out.print("] ");
			return;
		}
		
		if(start == A.length) {
			return;
		}
		
		B[start] = true;
		print(A, B, k, start + 1, currentLength + 1);
		
		B[start] = false;
		print(A, B, k, start + 1, currentLength);
	}
	
	
    public static void main(String[] args) { 
		String s = "abcd";
		int k = 3;
		print(s, k);
    }
}
