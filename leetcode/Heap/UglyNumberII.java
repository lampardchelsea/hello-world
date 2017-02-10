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



