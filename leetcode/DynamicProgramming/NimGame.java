/**
 * Refer to
 * https://leetcode.com/problems/nim-game/description/
 * You are playing the following Nim Game with your friend: There is a heap of stones 
   on the table, each time one of you take turns to remove 1 to 3 stones. The one who 
   removes the last stone will be the winner. You will take the first turn to remove the stones.
   Both of you are very clever and have optimal strategies for the game. Write a function 
   to determine whether you can win the game given the number of stones in the heap.
   For example, if there are 4 stones in the heap, then you will never win the game: 
   no matter 1, 2, or 3 stones you remove, the last stone will always be removed by your friend.
 *
 * Solution
 * https://leetcode.com/articles/nim-game/
 * Solution
   You can always win a Nim game if the number of stones nn in the pile is not divisible by 4.
   
   Reasoning
   Let us think of the small cases. It is clear that if there are only one, two, or three stones 
   in the pile, and it is your turn, you can win the game by taking all of them. Like the problem 
   description says, if there are exactly four stones in the pile, you will lose. Because no matter 
   how many you take, you will leave some stones behind for your opponent to take and win the game. 
   So in order to win, you have to ensure that you never reach the situation where there are exactly
   four stones on the pile on your turn.
   
   Similarly, if there are five, six, or seven stones you can win by taking just enough to leave 
   four stones for your opponent so that they lose. But if there are eight stones on the pile, 
   you will inevitably lose, because regardless whether you pick one, two or three stones from 
   the pile, your opponent can pick three, two or one stone to ensure that, again, four stones 
   will be left to you on your turn.
   
   It is obvious that the same pattern repeats itself for n=4,8,12,16,… basically 
   all multiples of 4.
 *
 * http://www.cnblogs.com/grandyang/p/4873248.html
 * 有史以来最少代码量的解法，虽然解法很简单，但是题目还是蛮有意思的，题目说给我们一堆石子，每次可以拿一个两个或三个，
   两个人轮流拿，拿到最后一个石子的人获胜，现在给我们一堆石子的个数，问我们能不能赢。那么我们就从最开始分析，
   由于是我们先拿，那么3个以内(包括3个)的石子，我们直接赢，如果共4个，那么我们一定输，因为不管我们取几个，
   下一个人一次都能取完。如果共5个，我们赢，因为我们可以取一个，然后变成4个让别人取，根据上面的分析我们赢，
   所以我们列出1到10个的情况如下：

    1    Win

    2    Win

    3    Win

    4    Lost

    5    Win

    6    Win

    7    Win

    8    Lost

    9    Win

    10   Win
    
    由此我们可以发现规律，只要是4的倍数个，我们一定会输，所以对4取余即可
*/
class Solution {
    public boolean canWinNim(int n) {
        return n % 4 != 0;   
    }
}


