/**
* Given a non-negative number represented as an array of digits, plus one to the number.
* The digits are stored such that the most significant digit is at the head of the list.
*/
public class Solution {
    public int[] plusOne(int[] digits) {
        int[] result;
        int length = digits.length;
        boolean allDigitsNine = true;
        boolean carryOver = false;
        
        int i = 0;
        while(i < length) {
            if(digits[i] != 9) {
                allDigitsNine = false;
                break;
            }
            i++;
        }
        
        if(allDigitsNine) {
            result = new int[length + 1];
            result[0] = 1;
            for(int j = 1; j < length + 1; j++) {
                result[j] = 0;
            }
        } else {
            result = new int[length];
            for(int k = length - 1; k >= 0; k--) {
                if(k == length - 1) {
                    if(digits[k] == 9) {
                        result[k] = 0;
                        carryOver = true;
                    } else {
                        result[k] = digits[k] + 1;
                    }
                } else {
                    if(carryOver) {
                        if(digits[k] == 9) {
                            result[k] = 0;
                        } else {
                            result[k] = digits[k] + 1;
                            carryOver = false;
                        }
                    } else {
                        result[k] = digits[k];
                    }
                }
            }
        }
        
        return result;
    }
}
