/**
* Given a non-negative number represented as an array of digits, plus one to the number.
* The digits are stored such that the most significant digit is at the head of the list.
*/
public class Solution {
    public int[] plusOne(int[] digits) {
        int length = digits.length;
        boolean carryOver = false;
        boolean allDigitsAreNine = true;
        int j = length - 1;
        int[] result;
        
        // Check special case as all digits are 9 at first
        while(j >= 0) {
            if(digits[j] != 9) {
                allDigitsAreNine = false;
                break;
            }
            j--;
        }
        
        if(allDigitsAreNine) {
            // If all digits are 9, then return 100..0(one more digit)
            result = new int[length + 1];
            result[0] = 1;
            for(int k = 1; k < length + 1; k++) {
                result[k] = 0;
            }
        } else {
            // If not, keep original length
            result = new int[length];
            // Loop from last digit to first, which is convinenet for 
            // judging last digit carry over case
            for(int i = length - 1; i >= 0; i--) {
                // Check last digit is 9 or not
                if(i == length - 1) {
                    // If last digit is 9, carry over set on
                    if(digits[i] == 9) {
                        carryOver = true;
                        result[i] = 0;
                    } else {
                        result[i] = digits[i] + 1;
                    }
                } else {
                    if(carryOver) {
                        if(digits[i] == 9) {
                            result[i] = 0;
                        } else {
                            result[i] = digits[i] + 1;
                            carryOver = false;
                        }
                    } else {
                        result[i] = digits[i];
                    }
                }
            }
        }
        
        return result;
    }
}
