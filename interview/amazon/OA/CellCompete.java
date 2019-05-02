/**
 Refer to
 https://ishaanone.blogspot.com/2018/07/problem-statement-there-is-colony-of-8.html
 Monday, July 16, 2018
Problem statement:
There is a colony of 8 cells arranged in a straight line where each day every cell 
competes with its adjacent cells(neighbour). Each day, for each cell, if its neighbours 
are both active or both inactive, the cell becomes inactive the next day,. otherwise 
it becomes active the next day.

Assumptions: The two cells on the ends have single adjacent cell, so the other adjacent 
cell can be assumsed to be always inactive. Even after updating the cell state. consider 
its pervious state for updating the state of other cells. Update the cell informationof 
allcells simultaneously.

Write a fuction cellCompete which takes takes one 8 element array of integers cells 
representing the current state of 8 cells and one integer days representing te number 
of days to simulate. An integer value of 1 represents an active cell and value of 0 
represents an inactive cell.

TESTCASES 1:
INPUT:
[1,0,0,0,0,1,0,0],1
EXPECTED RETURN VALUE:
[0,1,0,0,1,0,1,0]

TESTCASE 2:
INPUT:
[1,1,1,0,1,1,1,1,],2
EXPECTED RETURN VALUE:
[0,0,0,0,0,1,1,0]
*/

// Solution 1:
// Refer to
// https://ishaanone.blogspot.com/2018/07/problem-statement-there-is-colony-of-8.html
// https://www.geeksforgeeks.org/active-inactive-cells-k-days/
/**
 The only important thing is to make sure that we maintain a copy of given array because 
 we need previous values to update for next day. Below are detailed steps.

First we copy the cells[] array into temp[] array and make changes in temp[] array 
according to given condition.
In the condition, it is given that if immediate left and right cell of i’th cell either 
inactive or active the next day i becomes inactive i.e; (cells[i-1] == 0 and cells[i+1] == 0) 
or (cells[i-1] == 1 and cells[i+1] == 1) then cells[i] = 0, these conditions can be applied 
using XOR of cells[i-1] and cells[i+1].
For 0’th index cell temp[0] = 0^cells[1] and for (n-1)’th index cell temp[n-1] = 0^cells[n-2].
Now for index 1 to n-2, do the following operation temp[i] = cells[i-1] ^ cells[i+1]
Repeat the process till k days are completed.
*/
// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
import java.util.List;
import java.util.ArrayList;
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// CLASS BEGINS, THIS CLASS IS REQUIRED
public class Solution
{        
  // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public List<Integer> cellCompete(int[] states, int days)
    {
    // WRITE YOUR CODE HERE
        int len = states.length;
        // To maintain a copy of original array is important
        int[] newStates = new int[len];
        List<Integer> result = new ArrayList<Integer>();
        for(int k = 0; k < days; k++) {
            for(int i = 0; i < len; i++) {
                int nextState;
                int prevState;
                int activeNumber;
                if(i == 0) {
                    nextState = states[1];
                    prevState = 0;
                } else if(i == states.length - 1) {
                    nextState = 0;
                    prevState = states[states.length - 2];
                } else {
                    nextState = states[i + 1];
                    prevState = states[i - 1];
                }
                if(nextState == prevState) {
                    activeNumber = 0;
                } else {
                    activeNumber = 1;
                }
                newStates[i] = activeNumber;
            }
            // After one loop, we need copy back the result from
            // copy to original array for next loop
            for(int i = 0; i < len; i++) {
                states[i] = newStates[i];
            }
        }
        for(int i = 0; i < states.length; i++) {
            result.add(states[i]);
        }
        return result;
    }
  // METHOD SIGNATURE ENDS
}
