/** 
 Refer to
 https://www.geeksforgeeks.org/gcd-two-array-numbers/
 Given an array of numbers, find GCD of the array elements. 
 In a previous post we find GCD of two number.

Examples:
Input  : arr[] = {1, 2, 3}
Output : 1

Input  : arr[] = {2, 4, 6, 8}
Output : 2
*/
// Solution 1:
// Refer to
// https://www.geeksforgeeks.org/gcd-two-array-numbers/
/**
 The GCD of three or more numbers equals the product of the prime factors common 
 to all the numbers, but it can also be calculated by repeatedly taking the GCDs 
 of pairs of numbers.

gcd(a, b, c) = gcd(a, gcd(b, c)) 
             = gcd(gcd(a, b), c) 
             = gcd(gcd(a, c), b)

For an array of elements, we do following.
result = arr[0]
For i = 1 to n-1
   result = GCD(result, arr[i])
*/
// Java program to find GCD of two or 
// more numbers 

public class GCD { 
	// Function to return gcd of a and b 
	static int gcd(int a, int b) 
	{ 
		if (a == 0) 
			return b; 
		return gcd(b % a, a); 
	} 

	// Function to find gcd of array of 
	// numbers 
	static int findGCD(int arr[], int n) 
	{ 
		int result = arr[0]; 
		for (int i = 1; i < n; i++) 
			result = gcd(arr[i], result); 

		return result; 
	} 

	public static void main(String[] args) 
	{ 
		int arr[] = { 2, 4, 6, 8, 16 }; 
		int n = arr.length; 
		System.out.println(findGCD(arr, n)); 
	} 
} 

// This code is contributed by Saket Kumar 
