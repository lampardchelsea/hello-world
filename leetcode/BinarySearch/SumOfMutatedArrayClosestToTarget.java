/**
Refer to
https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/
Given an integer array arr and a target value target, return the integer value such that when we change all the 
integers larger than value in the given array to be equal to value, the sum of the array gets as close as possible 
(in absolute difference) to target.

In case of a tie, return the minimum such integer.

Notice that the answer is not neccesarilly a number from arr.

Example 1:
Input: arr = [4,9,3], target = 10
Output: 3
Explanation: When using 3 arr converts to [3, 3, 3] which sums 9 and that's the optimal answer.

Example 2:
Input: arr = [2,3,5], target = 10
Output: 5

Example 3:
Input: arr = [60864,25176,27249,21296,20204], target = 56803
Output: 11361

Constraints:
1 <= arr.length <= 10^4
1 <= arr[i], target <= 10^5
*/

// Solution 1: Binary Search
// Refer to
// https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/discuss/463268/JavaC%2B%2B-4ms-binary-search-short-readable-code-%2B-sorting-solution
/**
Binary search solution
See also a sorting solution below.

The value we are looking for is somewhere between 1 and maxValue (m).
Now use Binary search to speed up the process.

go up if the sum is too small
go down if the sum is too big
When we are done with binary search, l and r are equal, but it might happen that we have not exactly reached the target.
Check if l-1 (should get us below the target) leads to the sum closer to the target.

Java, 4ms:

class Solution {
    public int findBestValue(int[] arr, int target) {
        int l, r, mi, s=0, m=-1;
        for(int v:arr) { s += v; m=Math.max(m,v); }

        if(s<=target) return m; // return the max value since we will keep all nums as is

        for(l=1,r=m;l<r;) {
            mi=(l+r)/2;
            s=0;
            for(int v:arr) s += (v>mi)?mi:v;
            if(s>=target) r=mi;
            else          l=mi+1;
        }
        // check if we are 1 step off the target 
        int s1=0,s2=0;
        for(int v:arr) {
            s1 += (v>l)?(l):v;
            s2 += (v>l-1)?(l-1):v;
        }
        
        return (Math.abs(s2-target) <= Math.abs(s1-target)) ? l-1 : l;
    }
}

Q1: when to use low < high and low<=high in problems related to binary search ?
A1: I have to think about it each time.
    But I think the answer is:
    if you move one border to a mid element (l=mi+1,r=mi), then use while(l<r), 
    otherwise you will end up with endless loop selecting mid elem over and over again
    if you move both borders (l=mi+1, r=mi-1), then use while(l<=r)

Q2: About this condition :
if(s>=target) r=mi;
            else          l=mi+1;
How can we be sure, that when we reducer, our s(sum) reduces too ? Because when you reduce r, 
you're reducing mi for the next iteration. This also leads to a larger number of elements being 
replaced with the next mi. Right ?
A2: We should care only about l and r since they define the range where we look for a solution (mi).
So we reduce this range till the moment when l==r and then we have our solution.
In other words, when we reduce r, we save the knowledge that larger r gives a sum higher than the 
target and get rid of it going towards the target.
Since we start with r=max(arr), every time when we reduce r, it means that the more elements will 
be replaced with r and this value is less than max element of the array.

Q3: https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/discuss/464434/Classic-Binary-Search/678729
This solution helped me out a lot. Just want to point out to anyone else who is confused, the value that low 
ends up becoming is the minimum value that satisfies sum(arr,mid) >= target. This means that the two best choices 
are either low or low - 1, where low is the closest value that gets you a sum greater than or equal to the target, 
and low - 1 is the closest value that gets you a sum less than the target. Then, all you have to do is see which 
calculated sum is closest to the target.

You can see my solution based on this thought process below. I am using the binary search template from this post: 
https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems

Note: Some optimization can be done when checking if res >= target. You can return mid if res == target.

def findBestValue(self, arr: List[int], target: int) -> int:
   	def get_sum(v):
        return sum(v if a > v else a for a in arr)
    
    left, right = 0, max(arr)
    
    while left < right:
        mid = left + (right-left) // 2
        
        res = get_sum(mid)

        if res >= target:
            right = mid
        else:
            left = mid + 1
    
    left_diff = abs(get_sum(left) - target)
    left_minus1_diff = abs(get_sum(left-1) - target)
    
    if left_diff < left_minus1_diff:
        return left
    else:
        return left - 1
*/


