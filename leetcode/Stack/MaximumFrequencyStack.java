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


// Intuitive Solution: MaxPQ + HashMap
// Refer to
// https://leetcode.com/problems/maximum-frequency-stack/discuss/163416/Java-Priority-Queue-easy-understand/278547
/**
My shorter version and hopefully easier to understand.

    int index; //used to deal with frequency tie. might overflow, but question says we will only have 10^4 push operations
    Map<Integer, Integer> map = new HashMap<>(); //val->freq
    PriorityQueue<int[]> pq; //val, freq, index

    public FreqStack() {
        index = 0;
        pq = new PriorityQueue<>((a,b)->(a[1]!=b[1]?b[1]-a[1]:b[2]-a[2])); //dealing with tie
    }
    
    //O(logn)
    public void push(int x) {
        map.put(x, map.getOrDefault(x, 0)+1);
        pq.offer(new int[]{x, map.get(x), index++});
    }
    
    //O(1)
    public int pop() {
        int x = pq.poll()[0];
        map.put(x, map.get(x)-1); 
        if(map.get(x)==0) map.remove(x);
        return x;
    }
*/
class FreqStack {
    // Used to deal with frequency tie. might overflow, since when pop we will
    // not decrease the index to make sure not overwrite previous value on
    // same index when index increase first then decrease back, and question says 
    // we will only have 10^4 push operations
    int index; 
    Map<Integer, Integer> map; // {val -> freq}
    PriorityQueue<int[]> maxPQ; // {val, freq, index}
    public FreqStack() {
        index = 0;
        map = new HashMap<Integer, Integer>();
        maxPQ = new PriorityQueue<int[]>((a, b) -> (a[1] != b[1] ? b[1] - a[1] : b[2] - a[2])); // dealing with tie
    }
    
    public void push(int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
        maxPQ.offer(new int[] {val, map.get(val), index++});
    }
    
    public int pop() {
        int x = maxPQ.poll()[0];
        map.put(x, map.get(x) - 1);
        return x;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */




































































































https://leetcode.com/problems/maximum-frequency-stack/description/

Design a stack-like data structure to push elements to the stack and pop the most frequent element from the stack.

Implement the FreqStack class:
- FreqStack() constructs an empty frequency stack.
- void push(int val) pushes an integer val onto the top of the stack.
- int pop() removes and returns the most frequent element in the stack.
	- If there is a tie for the most frequent element, the element closest to the stack's top is removed and returned.

Example 1:
```
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
```

Constraints:
- 0 <= val <= 109
- At most 2 * 104 calls will be made to push and pop.
- It is guaranteed that there will be at least one element in the stack before calling pop.
---
Attempt 1: 2023-11-08

Solution 1: Priority Queue + Hash Table (60 min)
```
class FreqStack {
    Map<Integer, Stack<Integer>> map;
    Map<Integer, Integer> freq;
    int maxFreq;
    public FreqStack() {
        map = new HashMap<>();
        freq = new HashMap<>();
        maxFreq = 0;
    }
    
    public void push(int val) {
        int f = freq.getOrDefault(val, 0) + 1;
        if(f > maxFreq) {
            maxFreq = f;
        }
        freq.put(val, f);
        if(!map.containsKey(f)) {
            map.put(f, new Stack<>());
        }
        map.get(f).add(val);
    }
    
    public int pop() {
        int val = map.get(maxFreq).pop();
        freq.put(val, maxFreq - 1);
        if(map.get(maxFreq).size() == 0) {
            maxFreq--;
        }
        return val;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */

Time Complexity : O(1) FOR PUSH AND POP
Space Complexity : O(N)
```

Refer to
https://leetcode.com/problems/maximum-frequency-stack/solutions/163410/c-java-python-o-1/
Hash map freq will count the frequency of elements.
Hash map m is a map of stack. 
If element x has n frequency, we will push x n times in m[1], m[2] .. m[n]maxfreq records the maximum frequency.
push(x) will push x tom[++freq[x]]
pop() will pop from the m[maxfreq]
```
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
```

Refer to
https://leetcode.com/problems/maximum-frequency-stack/solutions/1862015/best-explanation-visually/
Let's understand what we have given is 2 operations of push() & pop().
```
And what the problem statement is saying `pop` the maximum frequency element from the stack. 
```
Okay, now you know that Stack use a LIFO order. So, it'll be a little hard for us to pop() the maximum frequency element out from the stack, if it's in the middle or somewhere. First we have to empty the stack until our element is on the top of the stack & store those element's somewhere.

So, it's not a good approach!!

Then, what to do?? We can use multiple stacks & i'll say why don't we create a frequency stack as well, now you say. What do you mean by that!!It's sort of a stack like that if same element's re-appear we'll put them into a new stack!

Well, let's understand with an example:-Let's say we have given these value's

E.g :- 12, 14, 12, 13, 14, 13, 14

	If, you are removing it & stack becomes empty remove it from frequency & reduce the maxFreq by 1

But there is one issue, for example if you say's let's add 14 into it. Then how can you say that this is 14th 3rd occurence & put it into a new stack.

Now you'll say, we gonna traverse over the stack & check>> Smart enough. But why you are increasing time complexity!!

Okay, for this one, we'll create one HashMap of Integer, Integer i.e. a frequency map.

Let's understand it back again with same example, but now visually :-

Till Now we have understand how push() operation is going on. let's see how pop() operations will look's like

And Guy's remember when we are poping out store your answer in a variable as at the end, we'll return it.
```
Sum Up:-
Craete 2 HashMap & one variable
```
I hope ladie's n gentlemen approach is absolute clear, Let's Code it, up

```
class FreqStack {
    Map<Integer, LinkedList<Integer>> st;
    Map<Integer, Integer> map = new HashMap<>();
    int maxFreq;
    public FreqStack() {
        st = new HashMap<>();
        map = new HashMap<>();
        maxFreq = 0;
    }
    
    public void push(int val) {
        int currFreq = map.getOrDefault(val, 0);
        currFreq++;
        map.put(val, currFreq);
        
        if(st.containsKey(currFreq) == false){
            st.put(currFreq, new LinkedList<Integer>());
        }
        st.get(currFreq).addFirst(val);
        maxFreq = Math.max(maxFreq, currFreq);
    }
    
    public int pop() {
        int ans = st.get(maxFreq).removeFirst();
        
        int currFreq = map.get(ans);
        currFreq--;
        map.put(ans, currFreq);
        if(st.get(maxFreq).size() == 0){
            maxFreq--;
        }
        return ans;
    }
}
```
