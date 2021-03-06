/**
 * Refer to
 * https://leetcode.com/problems/reverse-bits/description/
 * Reverse bits of a given 32 bits unsigned integer.
    For example, given input 43261596 (represented in binary as 00000010100101000001111010011100), 
    return 964176192 (represented in binary as 00111001011110000010100101000000).

    Follow up:
    If this function is called many times, how would you optimize it?
 *
 * Solution
 * https://discuss.leetcode.com/topic/9764/java-solution-and-optimization
 * https://stackoverflow.com/questions/3312853/how-does-bitshifting-work-in-java
*/
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int result = 0;
        int mask = 1;
        for(int i = 0; i < 32; i++) {
            // Not like 'Number of 1 Bits', don't need
            // to judge on whether its '1' or '0',
            // just need add to result is fine, then
            // shift n to rightward for next digit
            result += (n & mask);
            n >>= 1;
            // Don't do the leftward shift as 'mask <<= 1',
            // because we should consider when i = 30,
            // mask = -2147483648, this is not same as
            // 'Number of 1 Bits' because that only consider
            // '1' appearance, but here we should consider
            // overflow
            //mask <<= 1;
            // For the last time shift we don't need to
            // move to leftward
            if(i < 31) {
                result <<= 1; 
            }
        }
        return result;
    }
}

// For follow up
// How to optimize if this function is called multiple times? We can divide an int into 4 bytes, 
// and reverse each byte then combine into an int. For each byte, we can use cache to improve performance.
public class Solution {
    // you need treat n as an unsigned value
    Map<Byte, Integer> cache = new HashMap<Byte, Integer>();
    public int reverseBits(int n) {
        byte[] bytes = new byte[4];
        for(int i = 0; i < 4; i++) {
            // Refer to explanation on reverseByte 'b'
            bytes[i] = (byte)((n >>> 8 * i) & 0xFF);
        }
        int result = 0;
        for(int i = 0; i < 4; i++) {
            result += reverseByte(bytes[i]);
            if(i < 3) {
                result <<= 8;
            }
        }
        return result;
    }
    
    private int reverseByte(byte b) {
        // first look up from cache
        Integer value = cache.get(b);
        if(value != null) {
            return value;
        }
        // reverse by bit
        value = 0;
        for(int i = 0; i < 8; i++) {
            // As we didn't assgin result of 'b >>> i' back to 'b',
            // the value of 'b' will not change after each loop,
            // that's why we can use 'b >>> i' to mask with 1 and
            // get the original bit value on that position
            // e.g if b = 2, and i = 1, 2 >>> 1 = 1, but b = 2 still
            value += ((b >>> i) & 1);
            if(i < 7) {
                value <<= 1;
            }
        }
        cache.put(b, value);
        return value;
    }
}




