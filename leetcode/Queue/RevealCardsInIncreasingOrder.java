/**
Refer to
https://leetcode.com/problems/reveal-cards-in-increasing-order/
In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.

Initially, all the cards start face down (unrevealed) in one deck.

Now, you do the following steps repeatedly, until all cards are revealed:

Take the top card of the deck, reveal it, and take it out of the deck.
If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
Return an ordering of the deck that would reveal the cards in increasing order.

The first entry in the answer is considered to be the top of the deck.

Example 1:
Input: [17,13,11,2,3,5,7]
Output: [2,13,3,11,5,17,7]
Explanation: 
We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
We reveal 13, and move 17 to the bottom.  The deck is now [17].
We reveal 17.
Since all the cards revealed are in increasing order, the answer is correct.

Note:
1 <= A.length <= 1000
1 <= A[i] <= 10^6
A[i] != A[j] for all i != j
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/reveal-cards-in-increasing-order/discuss/200526/Java-Queue-Simulation-Step-by-Step-Explanation
// https://daniel-leskosky.medium.com/reveal-cards-in-increasing-order-89ba1e1be9eb
/**
Simulate the process with a queue.

Sort the deck, it is actually the "final sequence" we want to get according to the question.
Then put it back to the result array, we just need to deal with the index now!
Simulate the process with a queue (initialized with 0,1,2...(n-1)), now how do we pick the card?
We first pick the index at the top: res[q.poll()]=deck[i]
Then we put the next index to the bottom: q.add(q.poll());
Repeat it n times, and you will have the result array!
update
Let's walk through the example:
Input: [17,13,11,2,3,5,7]
Output: [2,13,3,11,5,17,7]

Sort the deck: [2,3,5,7,11,13,17], this is the increasing order we want to generate
Initialize the queue: [0,1,2,3,4,5,6], this is the index of the result array
The first card we pick is res[0], observe the deck, it should be deck[0]==2, so assign res[0]=2
Then we put res[1] to the bottom, so we re-insert 1 to the queue
The second card we pick is res[2], which should be deck[1]==3, so assign res[2]=3
Then we re-insert 3 to the queue
Each time we assign 1 value to the res, so we repeat this n times.
Hope this helps.

class Solution {
    public int[] deckRevealedIncreasing(int[] deck) {
        int n= deck.length;
        Arrays.sort(deck);
        Queue<Integer> q= new LinkedList<>();
        for (int i=0; i<n; i++) q.add(i);
        int[] res= new int[n];
        for (int i=0; i<n; i++){
            res[q.poll()]=deck[i];
            q.add(q.poll());
        }
        return res;
    }
}
*/
class Solution {
    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int n = deck.length;
        int[] result = new int[n];
        Queue<Integer> q = new LinkedList<Integer>();
        for(int i = 0; i < n; i++) {
            q.offer(i);
        }
        for(int i = 0; i < n; i++) {
            result[q.poll()] = deck[i];
            q.offer(q.poll());
        }
        return result;
    }
}

// Another explain more intuitive
// Refer to
// https://leetcode.com/problems/reveal-cards-in-increasing-order/discuss/504896/Java-solution-without-queue
/**
sort the inputed deck in ascending order.
deck = [17,13,11,2,3,5,7] => [2,3,5,7,11,13,17]
fill the empty slot of array to be returned by the following way: fill and skip the next empty slot alternatively.
[fill, skip, fill, skip, fill, skip, fill]
[2,    _,    3,    _,    5,    _,    7]
The last status is 'fill', next status should be 'skip'
    skip,     fill,     skip   
[2,  _,   3,  11,   5,   _,   7]
The last status is 'skip', so the next status should be 'fill'
	  fill,           skip          
[2,   13,   3, 11, 5,  _,   7]
The last status is 'skip', so the next status should be 'fill'
                   fill
[2, 13, 3, 11, 5,  17,   7]
class Solution {
    public int[] deckRevealedIncreasing(int[] deck) {
        Arrays.sort(deck);
        int[] res = new int[deck.length];
        boolean[] filled = new boolean[deck.length];
        boolean skip = false;
        int i = 0, j = 0;
        while(i < deck.length) {
            if(filled[j]) {
                j = (j + 1) % deck.length;
                continue;
            }

            if(skip) {
                skip = false;
            } else {
                res[j] = deck[i++];
                filled[j] = true;
                skip = true;
            }
            j = (j + 1) % deck.length;
        }

        return res;
    }
}
*/


