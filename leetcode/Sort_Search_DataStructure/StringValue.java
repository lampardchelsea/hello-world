/**
 * Let’s look at another example. Suppose we want to convert an integer to a string representation with a given base, 
 * following this spec:
 * @param n integer to convert to string
 * @param base base for the representation. Requires 2<=base<=10.
 * @return n represented as a string of digits in the specified base, with 
 *           a minus sign if n<0.
   public static String stringValue(int n, int base)

 * For example, stringValue(16, 10) should return "16", and stringValue(16, 2) should return "10000".
 * Let’s develop a recursive implementation of this method. One recursive step here is straightforward: 
 * we can handle negative integers simply by recursively calling for the representation of the corresponding positive integer:
   if (n < 0) return "-" + stringValue(-n, base);

 * This shows that the recursive subproblem can be smaller or simpler in more subtle ways than just the value of a numeric 
 * parameter or the size of a string or list parameter. We have still effectively reduced the problem by reducing it to 
 * positive integers.
 
 * The next question is, given that we have a positive n, say n=829 in base 10, how should we decompose it into a recursive 
 * subproblem? Thinking about the number as we would write it down on paper, we could either start with 8 (the leftmost or 
 * highest-order digit), or 9 (the rightmost, lower-order digit). Starting at the left end seems natural, because that’s 
 * the direction we write, but it’s harder in this case, because we would need to first find the number of digits in the 
 * number to figure out how to extract the leftmost digit. Instead, a better way to decompose n is to take its remainder 
 * modulo base (which gives the rightmost digit) and also divide by base (which gives the subproblem, the remaining 
 * higher-order digits):
   return stringValue(n/base, base) + "0123456789".charAt(n%base);

 * Think about several ways to break down the problem, and try to write the recursive steps. You want to find the one that 
 * produces the simplest, most natural recursive step.
 
 * It remains to figure out what the base case is, and include an if statement that distinguishes the base case from 
 * this recursive step.
 
 * Problem 1:
 * Implementing stringValue
 * Here is the recursive implementation of stringValue() with the recursive steps brought together but with the base 
 * case still missing:
   public static String stringValue(int n, int base) {
       if (n < 0) {
           return "-" + stringValue(-n, base);
       } else if (BASE CONDITION) {
           BASE CASE
       } else {
           return stringValue(n/base, base) + "0123456789".charAt(n%base);
       }
   }
 * Which of the following can be substituted for the BASE CONDITION and BASE CASE to make the code correct?
   else if (n == 0) { return "0"; }
   else if (n < base) { return "" + n; }
   else if (n == 0) { return ""; }
   else if (n < base) { return "0123456789".substring(n,n+1); }
 * The first choice is wrong because it will add a leading 0 to single-digit numbers, i.e. making stringValue(3, 10) 
 * return "03" instead of just "3".
 * The second choice works. return "" + n is shorthand for converting the single-digit number n into a string.
 * The third choice is wrong because stringValue(0, 10) will return "" instead of "0".
 * The fourth choice works.
 * 
 * 
 * 
 * Problem 2:
 * Assuming the code is completed with one of the base cases identified in the previous problem, what does stringValue(170, 16) do?
   returns "AA"
   returns "170"
   returns "1010"
   throws StringIndexOutOfBoundsException
   doesn’t compile, static error
   StackOverflowError
   infinite loop
 * Note first that using base=16 violates the precondition of this method, so it doesn’t have to satisfy the postcondition. 
 * A valid implementation can do anything. The question is what this particular valid implementation will do.
 * The recursive step will be invoked, which will split the number 170 by computing 170/16=10 and 170%16=10. The charAt() call 
 * will attempt to get the 11th character of "0123456789", which is past the end of the string. A StringIndexOutOfBoundsException 
 * will result.
*/

