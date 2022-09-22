https://leetcode.com/problems/find-smallest-letter-greater-than-target/

Given a characters array letters that is sorted in non-decreasing order and a character target, return the smallest character in the array that is larger than target.

Note that the letters wrap around.
- For example, if target == 'z' and letters == ['a', 'b'], the answer is 'a'.
 
Example 1:
```
Input: letters = ["c","f","j"], target = "a"
Output: "c"
```

Example 2:
```
Input: letters = ["c","f","j"], target = "c"
Output: "f"
```

Example 3:
```
Input: letters = ["c","f","j"], target = "d"
Output: "f"
```

Constraints:
- 2 <= letters.length <= 104
- letters[i] is a lowercase English letter.
- letters is sorted in non-decreasing order.
- letters contains at least two different characters.
- target is a lowercase English letter.
---
Attempt 1: 2022-09-22 (30min, too long to figure out corner case:  1. 如果要找满足 x > target 的第一个元素，那么只需改为 if nums[m] > target 2.  2. if the target is greater than or equal to the last character in the input list, then  search for the first character in the list)

```
class Solution { 
    // Binary search for the character which comes immediately after  
    // character target in the alphabets, or if the target is greater  
    // than or equal to the last character in the input list, then  
    // search for the first character in the list 
    public char nextGreatestLetter(char[] letters, char target) { 
        int len = letters.length; 
        if(target >= letters[len - 1]) { 
            return letters[0]; 
        } 
        // Find lower boundary 
        int lo = 0; 
        int hi = len - 1; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            // Since have to return the smallest character in the array that  
            // is larger than target -> "strictly" larger than target 
            // Refer to:  
            // 如果要找满足 x > target 的第一个元素，那么只需改为 if nums[m] > target。 
            if(letters[mid] > target) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        return letters[lo]; 
    } 
}

Space Complexity: O(1)         
Time Complexity: O(logn)
```

Refer to
https://leetcode.com/problems/find-smallest-letter-greater-than-target/discuss/110005/Easy-Binary-Search-in-Java-O(log(n))-time/111739
```
 public char nextGreatestLetter(char[] letters, char target) {
    	if (letters[0] > target) return letters[0];
    	if (letters[letters.length - 1] <= target) return letters[0];
    	
    	return binarySearch(letters, target);
    }
    
    private char binarySearch(char[] letters, char target) {
    	int start = 0;
    	int end = letters.length - 1;
    	while (start <= end) {
    		int mid = start + (end - start) / 2;
    		if (letters[mid] <= target) {
    			start = mid + 1;
    		}
    		else if (letters[mid] > target) {
    			end = mid - 1;
    		}
    	}
    	return letters[start];	
    }
```

https://leetcode.com/problems/find-smallest-letter-greater-than-target/discuss/110005/Easy-Binary-Search-in-Java-O(log(n))-time
Binary search for the character which comes immediately after character target in the alphabets, or if the target is greater than or equal to the last character in the input list, then search for the first character in the list.
