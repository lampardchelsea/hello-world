/**
Refer to
https://leetcode.com/problems/maximum-frequency-stack/
Design a stack-like data structure to push elements to the stack and pop the most frequent element from the stack.

Implement the FreqStack class:

FreqStack() constructs an empty frequency stack.
void push(int val) pushes an integer val onto the top of the stack.
int pop() removes and returns the most frequent element in the stack.
If there is a tie for the most frequent element, the element closest to the stack's top is removed and returned.
 

Example 1:
Input
["FreqStack", "push", "push", "push", "push", "push", "push", "pop", "pop", "pop", "pop"]
[[], [5], [7], [5], [7], [4], [5], [], [], [], []]
Output
[null, null, null, null, null, null, null, 5, 7, 5, 4]

Explanation
FreqStack freqStack = new FreqStack();
freqStack.push(5); // The stack is [5]
freqStack.push(7); // The stack is [5,7]
freqStack.push(5); // The stack is [5,7,5]
freqStack.push(7); // The stack is [5,7,5,7]
freqStack.push(4); // The stack is [5,7,5,7,4]
freqStack.push(5); // The stack is [5,7,5,7,4,5]
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].
 
Constraints:
0 <= val <= 109
At most 2 * 104 calls will be made to push and pop.
It is guaranteed that there will be at least one element in the stack before calling pop.
*/

// Solution 1: Freq map + Freq stack map
// Refer to
// https://leetcode.com/problems/maximum-frequency-stack/discuss/163410/C%2B%2BJavaPython-O(1)
/**
Hash map freq will count the frequence of elements.
Hash map m is a map of stack.
If element x has n frequence, we will push x n times in m[1], m[2] .. m[n]
maxfreq records the maximum frequence.

push(x) will push x tom[++freq[x]]
pop() will pop from the m[maxfreq]

Java:
class FreqStack {
    HashMap<Integer, Integer> freq = new HashMap<>();
    HashMap<Integer, Stack<Integer>> m = new HashMap<>();
    int maxfreq = 0;

    public void push(int x) {
        int f = freq.getOrDefault(x, 0) + 1;
        freq.put(x, f);
        maxfreq = Math.max(maxfreq, f);
        if (!m.containsKey(f)) m.put(f, new Stack<Integer>());
        m.get(f).add(x);
    }

    public int pop() {
        int x = m.get(maxfreq).pop();
        freq.put(x, maxfreq - 1);
        if (m.get(maxfreq).size() == 0) maxfreq--;
        return x;
    }
}

For those who are looking for the explanation of if (m.get(maxfreq).size() == 0) maxfreq--;. First, pay attention 
to the meaning of map m, it stores <frequency, a stack contains all elements which was pushed frequency times>, 
therefore, it's guaranteed that the keys of map m will be consecutively increasing, from 1 to maxfreq without gap. 
For example, when all elements that appeared 3 times have been popped out, we start checking the stack for all 
elements that appeared 2 times. In short, an element will appear in all stacks which have a key freq <= element's freq.

Take given sequence as test, 
FreqStack freqStack = new FreqStack();
freqStack.push(5); // The stack is [5]
freqStack.push(7); // The stack is [5,7]
freqStack.push(5); // The stack is [5,7,5]
freqStack.push(7); // The stack is [5,7,5,7]
freqStack.push(4); // The stack is [5,7,5,7,4]
freqStack.push(5); // The stack is [5,7,5,7,4,5]
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].

after all push it will be:
map = {1=[5, 7, 4], 2=[5, 7], 3=[5]}
Note: Since we use stack to make sure the insert onto stack order keep same as push order, so when any value reach to a frequency,
the order when the value reach to that frequency will align with push order
e.g 
reach to frequency = 1 order will be same as 5 -> 7 -> 4, since we push first 5 before first 7, push first 7 before first 4
reach to frequency = 2 order will be same as 5 -> 7, since we push second 5 before second 7, and no second 4
reach to frequency = 3 order will be same as 5, since we push third 5, and no third 7 or 4
*/
class FreqStack {
    int maxFreq;
    // {freq, stack --> value add as same order as push order for each frequency}
    Map<Integer, Stack<Integer>> map;
    Map<Integer, Integer> freq;
    public FreqStack() {
        maxFreq = 0;
        map = new HashMap<Integer, Stack<Integer>>();
        freq = new HashMap<Integer, Integer>();
    }
    
    public void push(int val) {
        int curValFreq = freq.getOrDefault(val, 0) + 1;
        freq.put(val, curValFreq);
        maxFreq = Math.max(maxFreq, curValFreq);
        map.putIfAbsent(curValFreq, new Stack<Integer>());
        map.get(curValFreq).push(val);
    }
    
    public int pop() {
        int maxFreqVal = map.get(maxFreq).pop();
        freq.put(maxFreqVal, maxFreq - 1);
        if(map.get(maxFreq).size() == 0) {
            maxFreq--;
        }
        return maxFreqVal;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
