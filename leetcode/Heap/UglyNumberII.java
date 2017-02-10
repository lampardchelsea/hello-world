// Wrong Solution
/**
 * Expected output is 8, real output is 6, because value stored on array
 * is (1, 2, 3, 4, 5, 6, 6, .....), the duplicate 6 is caused by 2 * 3 = 
 * 3 * 2 = 6, and no mechanism to prevent duplicate value adding
*/
import java.util.ArrayList;
import java.util.List;


public class UglyNumberII {
	public int nthUglyNumber(int n) {
        List<Integer> result = new ArrayList<Integer>();
        // 1, 2, 3, 4, 5, 6, 8, 9, 10, 12
        result.add(1);
        int idx_two = 1;
        int idx_three = 1;
        int idx_five = 1;
        int val_two = 0;
        int val_three = 0;
        int val_five = 0;
        while(result.size() <= n) {
            // Very like external merge sort
            // (1) 1×2, 2×2, 3×2, 4×2, 5×2, …
            // (2) 1×3, 2×3, 3×3, 4×3, 5×3, …
            // (3) 1×5, 2×5, 3×5, 4×5, 5×5, …
            val_two = 2 * idx_two;
            val_three = 3 * idx_three;
            val_five = 5 * idx_five;
            int min = min(val_two, val_three, val_five);
            if(min == val_two) {
                idx_two++;
            } else if(min == val_three) {
                idx_three++;
            } else {
                idx_five++;
            }
            result.add(min);
        }
        return result.get(n - 1);
    }
    
    public int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
    
    public static void main(String[] args) {
    	UglyNumberII un = new UglyNumberII();
    	int result = un.nthUglyNumber(7);
    	System.out.println(result);
    }
}

// Right Solution but time exceed
public class Solution {
    public int nthUglyNumber(int n) {
        List<Integer> result = new ArrayList<Integer>();
        // 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15
        result.add(1);
        int idx_two = 1;
        int idx_three = 1;
        int idx_five = 1;
        int val_two = 0;
        int val_three = 0;
        int val_five = 0;
        while(result.size() <= n) {
            // Very like external merge sort
            // (1) 1×2, 2×2, 3×2, 4×2, 5×2, …
            // (2) 1×3, 2×3, 3×3, 4×3, 5×3, …
            // (3) 1×5, 2×5, 3×5, 4×5, 5×5, …
            val_two = 2 * idx_two;
            val_three = 3 * idx_three;
            val_five = 5 * idx_five;
            // Skip values not ugly number because of increase
            // directly depends on index
            if(!isUgly(val_two)) {
            	idx_two++;
            	continue;
            }
            if(!isUgly(val_three)) {
            	idx_three++;
            	continue;
            }
            if(!isUgly(val_five)) {
            	idx_five++;
            	continue;
            }
            int min = min(val_two, val_three, val_five);
            if(min == val_two) {
                idx_two++;
            } else if(min == val_three) {
                idx_three++;
            } else {
                idx_five++;
            }
	    // Solve wrong solution issue, if find a same value
            // should not add into result
            if(result.contains(min)) {
                continue;
            }
            result.add(min);
        }
        return result.get(n - 1);
    }
    
    public int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
    
    public boolean isUgly(int x) {
    	while(x >= 2) {
    		if(x % 2 == 0) {
    			x = x / 2;
    		} else if(x % 3 == 0) {
    			x = x / 3;
    		} else if(x % 5 == 0) {
    			x = x / 5;
    		} else {
    			return false;
    		}
    	}
    	return x == 1;
    }
}

// Right Solution (Dynamic Programming)
/**
 * Refer to
 * https://discuss.leetcode.com/topic/21791/o-n-java-solution
 * http://www.cnblogs.com/grandyang/p/4743837.html
 * http://www.geeksforgeeks.org/ugly-numbers/
 * Example:
	Let us see how it works
	
	initialize
	   ugly[] =  | 1 |
	   i2 =  i3 = i5 = 0;

	First iteration
	   ugly[1] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
		    = Min(2, 3, 5)
		    = 2
	   ugly[] =  | 1 | 2 |
	   i2 = 1,  i3 = i5 = 0  (i2 got incremented ) 

	Second iteration
	    ugly[2] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
		     = Min(4, 3, 5)
		     = 3
	    ugly[] =  | 1 | 2 | 3 |
	    i2 = 1,  i3 =  1, i5 = 0  (i3 got incremented ) 

	Third iteration
	    ugly[3] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
		     = Min(4, 6, 5)
		     = 4
	    ugly[] =  | 1 | 2 | 3 |  4 |
	    i2 = 2,  i3 =  1, i5 = 0  (i2 got incremented )

	Fourth iteration
	    ugly[4] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
		      = Min(6, 6, 5)
		      = 5
	    ugly[] =  | 1 | 2 | 3 |  4 | 5 |
	    i2 = 2,  i3 =  1, i5 = 1  (i5 got incremented )

	Fifth iteration
	    ugly[4] = Min(ugly[i2]*2, ugly[i3]*3, ugly[i5]*5)
		      = Min(6, 6, 10)
		      = 6
	    ugly[] =  | 1 | 2 | 3 |  4 | 5 | 6 |
	    i2 = 3,  i3 =  2, i5 = 1  (i2 and i3 got incremented )

	Will continue same way till I < 150
*/
public class Solution {
	public int nthUglyNumber(int n) {
	// Declare an array for ugly numbers
        int[] ugly = new int[n];
        // Initialize first ugly number as 1
        ugly[0] = 1;
        // Initialize three array index variables point to 1st 
        // element of the ugly array
        int idx_two = 0;
        int idx_three = 0;
        int idx_five = 0;
        // Initialize 3 choices for the next ugly number
        int factor_two = 2 * ugly[idx_two];
        int factor_three = 3 * ugly[idx_three];
        int factor_five = 5 * ugly[idx_five];
        // Start loop to fill the ugly array
        for(int i = 1; i < n; i++) {
            // Be careful, no 7/11x2, 7/11x3, 7/11x5 because 7/11
            // are not ugly numbers, 6=2x3, 8=2x2x2, 9=3x3...they
            // are ugly numbers
            // Three groups
            //(1) 1×2, 2×2, 3×2, 4×2, 5×2, 6x2, 8x2, 9x2, 10x2, 12x2…
            //(2) 1×3, 2×3, 3×3, 4×3, 5×3, 6x3, 8x3, 9x3, 10x3, 12x3…
            //(3) 1×5, 2×5, 3×5, 4×5, 5×5, 6x5, 8x5, 9x5, 10x5, 12x5…
            // Find the minimum one and set to ugly[i] for current iteration
            int min = min(factor_two, factor_three, factor_five);
            ugly[i] = min;
            // Prepare for next iteration on index and factor
            // The selected group increase index by 1 and multiple
            // corresponding factor as 2, 3 or 5
            // And don't use else if, e.g value 6 = 2x3 = 3x2 must
            // update on both group
            if(min == factor_two) {
                idx_two = idx_two + 1;
                factor_two = 2 * ugly[idx_two];
            }
            if(min == factor_three) {
                idx_three = idx_three + 1;
                factor_three = 3 * ugly[idx_three];
            }
            if(min == factor_five) {
                idx_five = idx_five + 1;
                factor_five = 5 * ugly[idx_five];
            }
        }
        // Return the last number on ugly array
        return ugly[n-1];
    }
    
    public int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}

