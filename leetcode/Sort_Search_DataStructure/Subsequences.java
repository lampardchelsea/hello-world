/**
 * http://web.mit.edu/6.005/www/fa15/classes/10-recursion/
 * For example, subsequences("abc") might return "abc,ab,bc,ac,a,b,c,". 
 * Note the trailing comma preceding the empty subsequence, which is also a valid subsequence.
 * 
 * Solution 1:
 * This problem lends itself to an elegant recursive decomposition. Take the first letter of the word. 
 * We can form one set of subsequences that include that letter, and another set of subsequences that 
 * exclude that letter, and those two sets completely cover the set of possible subsequences.
 * 
 1 public static String subsequences(String word) {
 2     if (word.isEmpty()) {
 3         return ""; // base case
 4     } else {
 5         char firstLetter = word.charAt(0);
 6         String restOfWord = word.substring(1);
 7         
 8         String subsequencesOfRest = subsequences(restOfWord);
 9         
10         String result = "";
11         for (String subsequence : subsequencesOfRest.split(",", -1)) {
12             result += "," + subsequence;
13             result += "," + firstLetter + subsequence;
14         }
15         result = result.substring(1); // remove extra leading comma
16         return result;
17     }
18 }

 * Two examples:
    1. What does subsequences("c") return?
    subsequences("c") first calls subsequences(""), which just returns "".
    We then split this empty string on ",", which returns an array of one element, the empty string.
    
    The for loop then iterates over this array and constructs the two ways that subsequences of "c" can be formed 
    (with or without the letter ‘c’). It appends each new subsequence to result, starting it with a comma. 
    This means we’ll end up with an extra comma at the beginning of result, which we have to remove after the for loop.
    result = "" (line 10)
    result = "," (line 12)
    result = ",,c" (line 13)
    result = ",c" (line 15)
    Finally subsequences("c") returns ",c" representing the empty string and the string "c", the two possible 
    subsequences of the one-character string "c".
    
    2. What does subsequences("gc") return?
    subsequences("gc") first calls subsequences("c"), which returns ",c" as we saw in the previous question.
    We then split this string on ",", which produces an array of two elements, "" and "c".
    
    The for loop then iterates over this array, producing two new subsequences from each element:
    result = "" (line 10)
    result = "," (line 12)
    result = ",,g" (line 13)
    result = ",,g,c" (line 12)
    result = ",,g,c,gc" (line 13)
    result = ",g,c,gc" (line 15)
    This final result is returned from subsequences("gc").
 *    
 * The important tips of design recursive algorithm
 * Structure of Recursive Implementations
 * A recursive implementation always has two parts:
 * base case, which is the simplest, smallest instance of the problem, that can’t be decomposed any further. 
 * Base cases often correspond to emptiness – the empty string, the empty list, the empty set, the empty tree, zero, etc.
 * recursive step, which decomposes a larger instance of the problem into one or more simpler or smaller instances 
 * that can be solved by recursive calls, and then recombines the results of those subproblems to produce the solution 
 * to the original problem.
 * It’s important for the recursive step to transform the problem instance into something smaller, otherwise the 
 * recursion may never end. If every recursive step shrinks the problem, and the base case lies at the bottom, 
 * then the recursion is guaranteed to be finite.
 * A recursive implementation may have more than one base case, or more than one recursive step. For example, 
 * the Fibonacci function has two base cases, n=0 and n=1.
 *
 * 
 * 
 * Solution 2:
 * Helper Methods
 * The recursive implementation we just saw for subsequences() is one possible recursive decomposition of the problem. 
 * We took a solution to a subproblem – the subsequences of the remainder of the string after removing the first character – 
 * and used it to construct solutions to the original problem, by taking each subsequence and adding the first character 
 * or omitting it. This is in a sense a direct recursive implementation, where we are using the existing specification of 
 * the recursive method to solve the subproblems.
 * 
 * In some cases, it’s useful to require a stronger (or different) specification for the recursive steps, to make the 
 * recursive decomposition simpler or more elegant. In this case, what if we built up a partial subsequence using the 
 * intial letters of the word, and used the recursive calls to complete that partial subsequence using the remaining 
 * letters of the word? For example, suppose the original word is “orange”. At first, we would select “o” to be in the 
 * partial subsequence, and recursively extend it with all subsequences of “range”. Then, we would use “” as the partial 
 * subsequence, and again recursively extend it with all subsequences of “range”.
 * 
 * Using this approach, our code now looks much simpler:
   private static String subsequencesAfter(String partialSubsequence, String word) {
       if (word.isEmpty()) {
           // base case
           return partialSubsequence;
       } else {
           // recursive step
           return subsequencesAfter(partialSubsequence, word.substring(1))
                + ","
                + subsequencesAfter(partialSubsequence + word.charAt(0), word.substring(1));
       }
   }
 * This subsequencesAfter method is called a helper method. It satisfies a different spec from the original subsequences, 
 * because it has a new parameter partialSubsequence. This parameter fills a similar role that a local variable would in 
 * an iterative implementation. It holds temporary state during the evolution of the computation. The recursive calls 
 * steadily extend this partial subsequence, selecting or ignoring each letter in the word, until finally reaching the 
 * end of the word (the base case), at which point the partial subsequence is returned as the only result. Then the 
 * recursion backtracks and fills in other possible subsequences.
 * 
 * To finish the implementation, we need to implement the original subsequences spec, which gets the ball rolling by 
 * calling the helper method with an initial value for the partial subsequence parameter:
 * 
   public static String subsequences(String word) {
       return subsequencesAfter("", word);
   }
 * 
 * Don’t expose the helper method to your clients. Your decision to decompose the recursion this way instead of another 
 * way is entirely implementation-specific. In particular, if you discover that you need temporary variables like 
 * partialSubsequence in your recursion, don’t change the original spec of your method, and don’t force your clients 
 * to correctly initialize those parameters. That exposes your implementation to the client and reduces your ability to 
 * change it in the future. Use a private helper function for the recursion, and have your public method call it with 
 * the correct initializations, as shown above.
 *
 * Also need to be careful on base case checking, 
 * must use word.equals(""), word.isEmpty() or word.length() == 0,
 * must not use word == "",
 * Refer to 
 * Difference between String.isEmpty() and String.equals(“”)
 * http://stackoverflow.com/questions/6828362/difference-between-string-isempty-and-string-equals
 * http://stackoverflow.com/a/6828406/6706875
 * 
 * What is the difference between == vs equals() in Java?
 * http://stackoverflow.com/questions/7520432/what-is-the-difference-between-vs-equals-in-java
 * 
 * Should I use string.isEmpty() or “”.equals(string)? 
 * http://stackoverflow.com/questions/3321526/should-i-use-string-isempty-or-equalsstring
 *
 *
 *
 *
 * Louis Reasoner doesn’t want to use a helper method, so he tries to implement subsequences() by storing 
 * partialSubsequence as a static variable instead of a parameter. Here is his implementation:
 * 
   private static String partialSubsequence = "";
   public static String subsequencesLouis(String word) {
       if (word.isEmpty()) {
           // base case
           return partialSubsequence;
       } else {
           // recursive step
           String withoutFirstLetter = subsequencesLouis(word.substring(1));
           partialSubsequence += word.charAt(0);
           String withFirstLetter = subsequencesLouis(word.substring(1));
           return withoutFirstLetter + "," + withFirstLetter;
       }
   }
 * Suppose we call subsequencesLouis("c") followed by subsequencesLouis("a").
 * What does subsequencesLouis("c") return? (",c")
 * What does subsequencesLouis("a") return? ("c,ca")
 * The static variable maintains its value across calls to subsequencesLouis(), so it still has the final value
 * "c" from the call to subsequencesLouis("c") when subsequencesLouis("a") starts. As a result, every subsequence 
 * of that second call will have an extra c before it.
 * 
*/





