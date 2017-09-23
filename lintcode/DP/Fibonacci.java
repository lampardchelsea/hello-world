/**
 * Refer to
 * http://www.lintcode.com/en/problem/fibonacci/
 * Find the Nth number in Fibonacci sequence.

	A Fibonacci sequence is defined as follow:
	
	The first two numbers are 0 and 1.
	The i th number is the sum of i-1 th number and i-2 th number.
	The first ten numbers in Fibonacci sequence is:
	
	0, 1, 1, 2, 3, 5, 8, 13, 21, 34 ...
	
	 Notice
	
	The Nth fibonacci number won't exceed the max value of signed 32-bit integer in the test cases.
	
	Have you met this question in a real interview? Yes
	Example
	Given 1, return 0
	
	Given 2, return 1
	
	Given 10, return 34
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/fibonacci/
 * http://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
 * 
 * 
 * Follow up 1: 
 * Write a function which would check if a given number belongs to Fibonacci sequence ?
 * https://stackoverflow.com/questions/3139645/determining-whether-a-number-is-a-fibonacci-number
 * First few Fibonacci numbers are 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 141, ..

	Examples :
	
	Input : 8
	Output : Yes
	
	Input : 34
	Output : Yes
	
	Input : 41
	Output : No
 * 
 * Follow up 2:
 * How to print the fibonacci sequence in reverse order WITHOUT using a loop
 * https://stackoverflow.com/questions/25836313/how-to-print-the-fibonacci-sequence-in-reverse-order-without-using-a-loop
 * How to reverse the Fibonacci Series in c without using an Array?
 * https://stackoverflow.com/questions/35116796/how-to-reverse-the-fibonacci-series-in-c-without-using-an-array
 * 
 * http://www.geeksforgeeks.org/check-number-fibonacci-number/
 * http://commoninterview.com/Programming_Interview_Questions/fibonacci-numbers-interview-questions-1/
 */
public class Fibonacci {
	// Solution 1: Practical Solution with O(n) efficiency
    /**
     * It is easy to optimize performance fortunately if we calculate from bottom. 
     * That is to say, we get f(2) based on f(0) and f(1), and get f(3) based on 
     * f(1) and f(2). We follow this pattern till we get f(n). It is obvious that 
     * its time complexity is O(n). Its corresponding code is shown below:
     */
	// Style 1 --> DP way
	// Time Complexity: O(n)
	// Extra Space: O(n)
    public int fibonacci(int n) {
        if(n == 1) {
            return 0;
        }
        if(n == 2) {
            return 1;
        }
        int[] nums = new int[n];
        nums[0] = 0;
        nums[1] = 1;
        for(int i = 2; i < n; i++) {
            nums[i] = nums[i - 1] + nums[i - 2]; 
        }
        return nums[n - 1];
    }
    
    // Style 2 --> Optimized Space based on DP way
    // Refer to
    // http://www.jiuzhang.com/solutions/fibonacci/
    // http://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
    /**
     * We can optimize the space used in method 1 by storing the previous 
     * two numbers only because that is all we need to get the next Fibonacci 
     * number in series
     */
    public int fibonacci_2(int n) {
        int a = 0;
        int b = 1;
        for(int i = 0; i < n - 1; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return a;
    }
    
    // Solution 3: Recursive Solution (Bad)
    // Refer to
    // http://codercareer.blogspot.com/2011/10/no-15-fibonacci-sequences.html
    /**
     * Inefficient recursive solution
     * Fibonacci sequences are taken as examples to lecture recursive functions in many C/C++ textbooks, 
     * so most of candidates are familiar with the recursive solution. They feel confident and delighted 
     * when they meet this problem during interviews, because the can write the following code in short time:
     * 
     * Our textbooks take Fibonacci sequences as examples for recursive functions does not necessarily 
     * mean that recursion is a good solution for Fibonacci sequences. Interviewers may tell candidates 
     * that the performance of this recursive solution is quite bad, and ask them to analyze root causes.
     * 
     * Let us take f(10) as an example to analyze the recursive process. We have to get f(9) and f(8) 
     * before we get f(10). Meanwhile, f(8) and f(7) are needed before we get f(9). The dependency 
     * can be visualized in a tree as shown in Figure 1:
     * 
     * It is not difficult to notice that there are many duplicate nodes in the tree in Figure 1. 
     * The number of duplicated nodes increases dramatically when n increases.  Readers may have a 
     * try on the 100th number if Fibonacci sequences to have intuitive ideas about how slow this 
     * recursive solution is.
     * 
     */
    public int fibonacci_3(int n) {
        if(n == 1) {
            return 0;
        }
        if(n == 2) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    // Follow up 1:
    // Write a function which would check if a given number belongs to Fibonacci sequence
    // Refer to
    // http://www.geeksforgeeks.org/check-number-fibonacci-number/
    // http://commoninterview.com/Programming_Interview_Questions/fibonacci-numbers-interview-questions-1/
    /**
     * A simple way is to generate Fibonacci numbers until the generated number is greater 
     * than or equal to ‘n’. Following is an interesting property about Fibonacci numbers 
     * that can also be used to check if a given number is Fibonacci or not.
     * A number is Fibonacci if and only if one or both of (5*n2 + 4) or (5*n2 – 4) is a 
     * perfect square (Source: Wiki). Following is a simple program based on this concept.
     */
    // Style 1:
    // Refer to
    // https://stackoverflow.com/questions/3139645/determining-whether-a-number-is-a-fibonacci-number
    /**
     * Given what you've done already, however, I'd probably use a more brute-force approach, 
     * such as the following:
     * Generate a fibonacci number
		(1) If it's less than the target number, generate the next fibonacci and repeat
		(2) If it is the target number, then success
		(3) If it's bigger than the target number, then failure.
		(4) I'd probably use a recursive method, passing in a current n-value 
		    (ie. so it calculates the nth fibonacci number) and the target number.
     */
    public boolean isANumberFibonacci(int num) {
    	int i = 1;
    	while(i > 0) {
    		int fibNum = generateFibonacci(i);
    		if(fibNum != num) {
    			if(fibNum > num) {
    				return false;
    			} else {
    				i++;
    			}
    		} else {
    			return true;
    		}
    	}
    	return false;
    }
    
    private int generateFibonacci(int n) {
    	int a = 0;
    	int b = 1;
    	for(int i = 0; i < n - 1; i++) {
    		int c = a + b;
    		a = b;
    		b = c;
    	}
    	return a;
    }
    
    // Style 2:
    // Returns true if n is a Fibonacci Number, else false
    public boolean isFibonacci(int n) {
        // n is Fibonacci if one of 5*n*n + 4 or 5*n*n - 4 or both
        // is a perfect square
        return isPerfectSquare(5*n*n + 4) ||
               isPerfectSquare(5*n*n - 4);
    }
    // A utility method that returns true if x is perfect square
    private boolean isPerfectSquare(int x) {
        int s = (int) Math.sqrt(x);
        return (s*s == x);
    }

    
    // Follow up 2:
    // How to print the fibonacci sequence in reverse order WITHOUT using a loop
    // How to reverse the Fibonacci Series in c without using an Array?
    public int reverseFibonacci(int n) {
    	int a = 0;
    	int b = 1;
    	for(int i = 0; i < n - 1; i++) {
    		int c = a + b;
    		a = b;
    		b = c;
    	}
    	// Inverse fibonacci
    	// The while(a >= 0) + System.out.println(a); will cause one more 1,
    	// because additional one more statement execute as a = t will assign
    	// t = 1 back to a, just use while(b > 0) + System.out.println(a);
    	// to exactly print out n numbers
    	while(b > 0) {
    		System.out.println(a);
    		int t = b - a;
    		b = a;
    		a = t;
    	}
    	return 0;
    }
    
    
    public static void main(String[] args) {
    	Fibonacci f = new Fibonacci();
    	int num = 10;
    	//boolean result = f.isANumberFibonacci(num);
        //int result = f.fibonacci_2(num);
        f.reverseFibonacci(10);
    	//System.out.print(result);
    }
}
