/**
Refer to
https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/
The school cafeteria offers circular and square sandwiches at lunch break, referred to by numbers 0 and 1 respectively. 
All students stand in a queue. Each student either prefers square or circular sandwiches.

The number of sandwiches in the cafeteria is equal to the number of students. The sandwiches are placed in a stack. At each step:

If the student at the front of the queue prefers the sandwich on the top of the stack, they will take it and leave the queue.
Otherwise, they will leave it and go to the queue's end.
This continues until none of the queue students want to take the top sandwich and are thus unable to eat.

You are given two integer arrays students and sandwiches where sandwiches[i] is the type of the ith sandwich in the 
stack (i = 0 is the top of the stack) and students[j] is the preference of the jth student in the initial queue 
(j = 0 is the front of the queue). Return the number of students that are unable to eat.

Example 1:
Input: students = [1,1,0,0], sandwiches = [0,1,0,1]
Output: 0 
Explanation:
- Front student leaves the top sandwich and returns to the end of the line making students = [1,0,0,1].
- Front student leaves the top sandwich and returns to the end of the line making students = [0,0,1,1].
- Front student takes the top sandwich and leaves the line making students = [0,1,1] and sandwiches = [1,0,1].
- Front student leaves the top sandwich and returns to the end of the line making students = [1,1,0].
- Front student takes the top sandwich and leaves the line making students = [1,0] and sandwiches = [0,1].
- Front student leaves the top sandwich and returns to the end of the line making students = [0,1].
- Front student takes the top sandwich and leaves the line making students = [1] and sandwiches = [1].
- Front student takes the top sandwich and leaves the line making students = [] and sandwiches = [].
Hence all students are able to eat.

Example 2:
Input: students = [1,1,1,0,0,1], sandwiches = [1,0,0,0,1,1]
Output: 3

Constraints:
1 <= students.length, sandwiches.length <= 100
students.length == sandwiches.length
sandwiches[i] is 0 or 1.
students[i] is 0 or 1.
*/

// Solution 1: Queue to simulate students behavior + Find terminate condition for shift
// Simulate the given in the statement, calculate those who will eat instead of those who will not.
class Solution {
    public int countStudents(int[] students, int[] sandwiches) {
        Queue<Integer> q = new LinkedList<Integer>();
        for(int student : students) {
            q.offer(student);
        }
        int len = students.length;
        int match = 0;
        int sandwich_idx = 0;
        while(!q.isEmpty()) {
            if(q.peek() != sandwiches[sandwich_idx]) {
                // Terminate condition: if all remained students have same prefers
                // and not match current sandwich on top, then no need shift anymore
                if(allSameElementOnQueue(q)) {
                    break;
                }
                q.offer(q.remove());
            } else {
                q.remove();
                match++;
                sandwich_idx++;
            }
        }
        return len - match;
    }
    
    private boolean allSameElementOnQueue(Queue<Integer> q) {
        int val = q.peek();
        for(int a : q) {
            if(a != val) {
                return false;
            }
        }
        return true;
    }
}

// Solution 2: Improved from O(N^2) to O(N) by replace check all element same on queue of break condition with shift_count
// Refer to
// https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/discuss/987495/Beginner-friendlyfaster-than-100.00c++easy-understanding/800322
/**
public int countStudents(int[] students, int[] sandwiches) {
    Queue<Integer> q = new LinkedList<>();
    int top = 0, count = 0;

    for(int e : students)
        q.add(e);

    while(!q.isEmpty() && count != q.size()){// count keeps the track of students processed in continuous manner 
        if(q.peek() == sandwiches[top]){//if student at front can eat the sandwich at top
            count = 0;
            top++;
            q.poll();
        }
        else{
            q.add(q.poll());//move the student to the rear
            count++;
        }
    }
    return q.size();
}
*/
class Solution {
    public int countStudents(int[] students, int[] sandwiches) {
        Queue<Integer> q = new LinkedList<Integer>();
        for(int student : students) {
            q.offer(student);
        }
        int len = students.length;
        int sandwich_idx = 0;
        int shift_count = 0;
        while(!q.isEmpty() && shift_count != q.size()) {
            if(q.peek() != sandwiches[sandwich_idx]) {
                q.offer(q.poll());
                shift_count++;
            } else {
                // Reset shift_count to 0 means another round start,
                // if shift_count increas to same as q's current size,
                // that means all students have tried but not able to eat
                shift_count = 0;
                q.poll();
                sandwich_idx++;
            }
        }
        return q.size();
    }
}
