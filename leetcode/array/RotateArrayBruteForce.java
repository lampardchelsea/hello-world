/**
 * The simplest approach is to rotate all the elements of the array in k steps 
 * by rotating the elements by 1 unit in each step.
 * 
 * Complexity Analysis
 * Time complexity : O(n*k). All the numbers are shifted by one step(O(n)) k times(O(k)).
 * Space complexity : O(1). No extra space is used.
 * 
 * Actually this method run out of time, but the rotate thought which contain two major
 * steps is very important:
 * 1. Initial condition to rotate, e,g which element to relocate initially ?
 * 2. How to update the element, e.g need another variable to store current element value ?
*/
public class Solution {
    public void rotate(int[] nums, int k) {
    	int length = nums.length;
    	int temp;
    	int previous;
    	
    	// The initial idea is very similar to exchange(a, b) {temp = a; a = b; b = temp},
    	// but the tricky part is when rotate, we need initial a start situation, in this
    	// case, the start situation is if rotate by 1 unit in each step, the last element
    	// in the array will remove and relocate to first place. Use "previous" variable 
    	// to store this element, and as there are k times relocate, loop k times for last 
    	// element relocation. 
    	for(int j = 0; j < k; j++) {
        	previous = nums[length - 1];
        	// The inner for loop will go through each element and replace with previous
        	// element, so the engagement of "previous" and inner for loop is here, very 
        	// hard to come up with. First use "temp" to store element itself, then replace
        	// this element with value store in "previous", the update "previous" with
        	// value store in "temp"(in other words: update "previous" with current element
        	// for next round use). The "previous" initial status should be outside inner
        	// for loop, which already set as retrieve last element value consistently.
        	for(int i = 0; i < length; i++) {
        	  // I already find out need to use new variable to store element value, otherwise
        	  // other element will get overrided value from this element, e.g a[i] = a[i - 1];
        	  // a[i + 1] = a[i], actually a[i + 1] get value of a[i - 1] not a[i], but stuck
        	  // at engage previous and temp update relation.
        	  // When inside for loop receive "preivous" from outside for loop, every element
        	  // should shift to right for one step.
        		temp = nums[i];
        		nums[i] = previous;
        		previous = temp;
        	}
    	}
    }
}
