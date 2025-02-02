https://leetcode.com/problems/queue-reconstruction-by-height/description/
You are given an array of people, people, which are the attributes of some people in a queue (not necessarily in order). Each people[i] = [hi, ki] represents the ith person of height hi with exactly ki other people in front who have a height greater than or equal to hi.
Reconstruct and return the queue that is represented by the input array people. The returned queue should be formatted as an array queue, where queue[j] = [hj, kj] is the attributes of the jth person in the queue (queue[0] is the person at the front of the queue).

Example 1:
Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
Explanation:
Person 0 has height 5 with no other people taller or the same height in front.
Person 1 has height 7 with no other people taller or the same height in front.
Person 2 has height 5 with two persons taller or the same height in front, which is person 0 and 1.
Person 3 has height 6 with one person taller or the same height in front, which is person 1.
Person 4 has height 4 with four people taller or the same height in front, which are people 0, 1, 2, and 3.
Person 5 has height 7 with one person taller or the same height in front, which is person 1.
Hence [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] is the reconstructed queue.

Example 2:
Input: people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
Output: [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
 
Constraints:
- 1 <= people.length <= 2000
- 0 <= hi <= 10^6
- 0 <= ki < people.length
- It is guaranteed that the queue can be reconstructed.
--------------------------------------------------------------------------------
Attempt 1: 2025-01-31
Solution 1: Math + Greedy (180 min)
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        // Sort the people array in descending order of height (h)
        // If two people have the same height, sort them in ascending order of k
        Arrays.sort(people, (a, b) -> (a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]));
        // Use a list to reconstruct the queue
        List<int[]> result = new ArrayList<>();
        for(int[] p : people) {
            // Insert at the index specified by k
            result.add(p[1], p);
        }
        // Convert the list back to a 2D array
        return result.toArray(new int[people.length][2]);
    }
}

Time Complexity: O(nlogn + n^2) = O(n^2): We sort the array in O(nlogn) 
time and list insertion will take O(n^2) for n insertions at worst.
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/queue-reconstruction-by-height/solutions/2211641/visual-explanation-java-greedy/
Introduction
In my opinion, this is quite a tricky question since it can be hard to understand why it works even after understanding the code for the algorithm. With that being said, I'll try my best to help you understand how to solve the question over an example and a series of illustrations! We'll use the below example with input people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]:


Logic
Before we understand how the problem is solved as a whole, we have to break down each relationship individually.
Let's start with the simplest situation of two people who share the same height:
The key observation here is thattwo people who share the same height must have their k values sorted in ascending order. Since we're considering people with greater height and equal height, this is important to do.

One major thing to keep in mind here is that these people need not be right next to each other. In fact, if you put someone shorter in between them, neither of them will notice. However, if you were to put someone taller than them in between, the people behind this person must have a k value that accommodates them. In this case, we need to put someone taller in between these two individuals since the second person requires a 2nd person taller than him in front.
In fact, let's introduce two new taller people:
The same rule applies to these two people;k values sorted in ascending order. Now, what happens if we combine them?
As you can see, the people who share the same heights maintain their relative order. But now comes the question, how did we decide the order of people withdifferent heights? Now is where things get a bit strange.


The trick here is this:
We insert the tallest people into our output first. Then we insert the people of the next tallest height into the output using their k value as their index.The reason this works is because people of taller heights don't care about the positions of those who are shorter than them. So we can do this height-by-height.
Let's follow this entire process with our example:
Example:
As mentioned above, we insert the tallest first, and then the second-tallest, and so forth. The gray numbers below represent the indexes of the output array.

And there we have it; a completely reconstructed queue.
Just to be completely clear, this works because of two reasons:
1.Taller heights aren't affected by shorter heights
2.We can sort each individual height in ascending order by k
How would this work in code:
Well, at least this is quite simple. We just need to sort our array in descending order by height and in ascending order by k for people that share the same height. Then, we quite literally just insert into an array based on the k value as the index. We'll use a linked list initially for insertion before converting to a 2D array.
Code
If you have any questions, suggestions or improvements, feel free to let me know. Thanks for reading!
public int[][] reconstructQueue(int[][] people) {
    Arrays.sort(people, (a,b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
    List<int[]> ordered = new LinkedList<>();
    for (int[] p: people) ordered.add(p[1], p);
    return ordered.toArray(new int[people.length][2]);
}
Time Complexity: O(nlogn + n^2) = O(n^2): We sort the array in O(nlogn) time and list insertion will take O(n^2) for n insertions at worst.
Space Complexity: O(n)
--------------------------------------------------------------------------------
Refer to Deepseek
To solve the problem 406. Queue Reconstruction by Height in Java, we need to reconstruct the queue based on the given people's heights and the number of people in front of them who have a greater or equal height. Here's the step-by-step solution:
Problem Explanation:
- You are given a list of people, where each person is represented as [h, k], where h is the height of the person and k is the number of people in front of them who have a height greater than or equal to h.
- The goal is to reconstruct the queue such that it satisfies the given conditions.
Approach:
1.Sort the people:
- First, sort the people in descending order of height (h).
- If two people have the same height, sort them in ascending order of k.
2.Insert into the result list:
- Use a list to reconstruct the queue.
- Insert each person at the index specified by their k value. This works because all taller people have already been placed in the list, so inserting at k ensures the correct number of taller people are in front.
Java Solution:
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Solution {
    public int[][] reconstructQueue(int[][] people) {
        // Sort the people array in descending order of height (h)
        // If two people have the same height, sort them in ascending order of k
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (b[0] == a[0]) {
                    return a[1] - b[1]; // If heights are equal, sort by k in ascending order
                }
                return b[0] - a[0]; // Sort by height in descending order
            }
        });

        // Use a list to reconstruct the queue
        List<int[]> result = new ArrayList<>();
        for (int[] person : people) {
            result.add(person[1], person); // Insert at the index specified by k
        }

        // Convert the list back to a 2D array
        return result.toArray(new int[people.length][2]);
    }
}
Explanation:
1.Sorting:
- The Arrays.sort method is used with a custom comparator to sort the array. The comparator sorts by height in descending order, and if heights are equal, it sorts by k in ascending order.
2.Insertion:
- We use a List<int[]> to reconstruct the queue. For each person, we insert them at the index specified by their k value. This ensures that the number of taller people in front of them is correct.
3.Result:
- Finally, we convert the list back to a 2D array and return it.
Example:
Input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
After sorting: [[7,0], [7,1], [6,1], [5,0], [5,2], [4,4]]
Reconstructed queue: [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
Complexity:
- Time Complexity: O(n^2) due to the insertion operation in the list.
- Space Complexity: O(n) for the result list.
This solution efficiently reconstructs the queue based on the given conditions.
