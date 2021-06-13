/**
Refer to
https://leetcode.ca/2020-05-05-1618-Maximum-Font-to-Fit-a-Sentence-in-a-Screen/
You are given a string text. We want to display text on a screen of width w and height h. You can choose any font size 
from array fonts, which contains the available font sizes in ascending order.

You can use the FontInfo interface to get the width and height of any character at any available font size.

The FontInfo interface is defined as such:

interface FontInfo {
  // Returns the width of character ch on the screen using font size fontSize.
  // O(1) per call
  public int getWidth(int fontSize, char ch);

  // Returns the height of any character on the screen using font size fontSize.
  // O(1) per call
  public int getHeight(int fontSize);
}

The calculated width of text for some fontSize is the sum of every getWidth(fontSize, text[i]) call for each 0 <= i < text.length (0-indexed). 
The calculated height of text for some fontSize is getHeight(fontSize). Note that text is displayed on a single line.
 
It is guaranteed that FontInfo will return the same value if you call getHeight or getWidth with the same parameters.

It is also guaranteed that for any font size fontSize and any character ch:

getHeight(fontSize) <= getHeight(fontSize+1)
getWidth(fontSize, ch) <= getWidth(fontSize+1, ch)
Return the maximum font size you can use to display text on the screen. If text cannot fit on the display with any font size, return -1.

Example 1:
Input: text = “helloworld”, w = 80, h = 20, fonts = [6,8,10,12,14,16,18,24,36]
Output: 6

Example 2:
Input: text = “leetcode”, w = 1000, h = 50, fonts = [1,2,4]
Output: 4

Example 3:
Input: text = “easyquestion”, w = 100, h = 100, fonts = [10,15,20,25]
Output: -1

Constraints:
1 <= text.length <= 50000
text contains only lowercase English letters.
1 <= w <= 10^7
1 <= h <= 10^4
1 <= fonts.length <= 10^5
1 <= fonts[i] <= 10^5
fonts is sorted in ascending order and does not contain duplicates.
*/

// Solution 1:
// First check whether the smallest font size in fonts can display text on the screen. 
// If text cannot fit on the display with the smallest font size, return -1.
// Then use binary search to find the maximum font size.
/**
 * // This is the FontInfo's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface FontInfo {
 *     // Return the width of char ch when fontSize is used.
 *     public int getWidth(int fontSize, char ch) {}
 *     // Return Height of any char when fontSize is used.
 *     public int getHeight(int fontSize)
 * }
 */
class Solution {
    public int maxFont(String text, int w, int h, int[] fonts, FontInfo fontInfo) {
        if(canFit(text, w, h, fonts[0], fontInfo) {
            return -1;
        }
        int lo = 0;
        int hi = fonts.length - 1;
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(canFit(text, w, h, fonts[mid], fontInfo)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return lo >= 0 ? fonts[lo] : -1;
    }
  
    private boolean canFit(String text, int w, int h, int fontSize, FontInfo fontInfo) {
        int height = fontInfo.getHeight(fontSize);
        // Note that text is displayed on a single line.
        if(height > h) {
            return false;
        }
        int totalWidth = 0;
        int length = text.length();
        for(int i = 0; i < length; i++) {
            char ch = text.charAt(i);
            int width = fontInfo.getWidth(fontSize, ch);
            totalWidth += width;
            if(totalWidth > w) {
                return false;
            }
        }
        return true;
    }
}
