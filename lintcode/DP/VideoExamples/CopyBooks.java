https://www.lintcode.com/problem/copy-books/description

Given n books and the i-th book has pages[i] pages. There are k persons to copy these books.

These books list in a row and each person can claim a continous range of books. For example, one copier can copy the books from i-th to j-th continously, but he can not copy the 1st book, 2nd book and 4th book (without 3rd book).

They start copying books at the same time and they all cost 1 minute to copy 1 page of a book. What's the best strategy to assign books so that the slowest copier can finish at earliest time?

Return the shortest time that the slowest copier spends.

Example 1:
```
Input: pages = [3, 2, 4], k = 2 
Output: 5 
Explanation:  
    First person spends 5 minutes to copy book 1 and book 2. 
    Second person spends 4 minutes to copy book 3.
```

Example 2:
```
Input: pages = [3, 2, 4], k = 3 
Output: 4 
Explanation: Each person copies one of the books.
```

---
Attempt 1: 2022-10-04

Solution 1: 60min, Binary Search Find Lower Boundary (template based on while(lo <= hi), refer L704.Binary Search), too long since hard to come with auxiliary function to help sort out the Binary Search model
```
public class Solution { 
    /** 
     * @param pages: an array of integers 
     * @param k: An integer 
     * @return: an integer 
     */ 
    public int copyBooks(int[] pages, int k) { 
        // 完成时间的 lower bound: max(pages), 理解为一共有len(pages)个人，每个人只复印一本书 
        // 完成时间的 upper bound: sum(pages), 理解为只有一个人要独自复印所有的书 
        int lo = 0; 
        int hi = 0; 
        for(int page : pages) { 
            lo = Math.max(lo, page); 
            hi += page; 
        } 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(peopleNeeded(pages, mid, k)) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        return lo; 
    } 
    // 用二分法来做。二分可能的答案。每次判断什么呢？判断k个人能不能在这个时间里搞定。 
    // peopleNeeded()这个函数用来统计给定一个时间minTime需要多少人可以在这个时间内 
    // 搞定抄那么多书。如果>k，说明这个时间k个人搞不定，时间要再久一点才可以。如果<=k， 
    // 说明不到k个人就能在这个时间内搞定，我们可以试试更少一点的时间。 
    private boolean peopleNeeded(int[] pages, int minTimeNeeded, int k) { 
        // 至少有一个人copy，所以count从1开始 
        int people = 1; 
        int sum = 0; 
        for(int page : pages) { 
            // Must pre-check the 'sum + page > minTimeNeeded', then 'sum += page' 
            // reverse order will lead wrong calculation on 'people' 
            if(sum + page > minTimeNeeded) { 
                people++; 
                sum = 0; 
            } 
            sum += page; 
        } 
        return people <= k; 
    } 
}

Space Complexity: O(1)           
Time Complexity: O(nlogm) n:书本数目, m:总页数
```

Refer to
https://yeqiuquan.blogspot.com/2017/03/lintcode-437-copy-books.html
用二分法来做。二分可能的答案。每次判断什么呢？判断k个人能不能在这个时间里搞定。people()这个函数用来统计给定一个时间minTime需要多少人可以在这个时间内搞定抄那么多书。如果>k，说明这个时间k个人搞不定，时间要再久一点才可以。如果<k，说明不到k个人就能在这个时间内搞定，我们可以试试更少一点的时间。


```
public class Solution { 
    /** 
     * @param pages: an array of integers 
     * @param k: an integer 
     * @return: an integer 
     */ 
    public int copyBooks(int[] pages, int k) { 
        // write your code here 
         
        if (pages == null || pages.length == 0) { 
            return 0; 
        } 
         
        int start = 0; 
        int end = 0; 
        for (int i = 0; i < pages.length; i++) { 
            end += pages[i]; 
            start = Math.max(start, pages[i]); 
        } 
         
        while (start + 1 < end) { 
            int mid = start + (end - start) / 2; 
            if (people(pages, mid) > k) { 
                start = mid; 
            } 
            else { 
                end = mid; 
            } 
        } 
         
        if (people(pages, start) <= k) { 
            return start; 
        } 
        return end; 
         
    } 
     
    public int people(int[] pages, int minTime) { 
        if (pages.length == 0) { 
            return 0; 
        }
        int sum = 0; 
        int people = 1; 
        for (int i = 0; i < pages.length; i++) { 
            if (sum + pages[i] > minTime) { 
                people++; 
                sum = 0; 
            } 
            sum += pages[i]; 
        } 
         
        return people; 
    } 
}
```

Wrong  Solution caused by wrong calculation on people needed:
1.至少有一个人copy，所以count从1开始
2. Must pre-check the 'sum + page > minTimeNeeded', then 'sum += page', reverse order will lead wrong calculation on 'people' 
```
For wrong function peopleNeeded() analysis: 
    private boolean peopleNeeded(int[] pages, int minTime, int k) { 
        // 至少有一个人copy，所以count从1开始 
        int people = 1; 
        int sum = 0; 
        for(int page : pages) { 
            sum += page; 
            if(sum > minTime) { 
                people++; 
                sum = 0; 
            } 
        } 
        return people <= k; 
    } 
------------------------------ 
pages={3,2,4} 
k=2 
------------------------------ 
Round 1: 
lo=4,hi=9 
mid=6 -> minTimeNeeded=6 -> peopleNeeded(pages,6,2) 
-> people=2 <= k=2 -> hi=mid-1=5 
------------------------------ 
Round 2: 
lo=4,hi=5 
mid=4 -> minTimeNeeded=4 -> peopleNeeded(pages,4,2) 
-> people=2 <= k=2 -> hi=mid-1=3 
lo > hi while loop end 
Note: It is already wrong here since 2 people cannot finish {3,2,4} in 4 minutes 



=========================================== 
For correct function peopleNeeded() analysis: 
    private boolean peopleNeeded(int[] pages, int minTime, int k) { 
        // 至少有一个人copy，所以count从1开始 
        int people = 1; 
        int sum = 0; 
        for(int page : pages) { 
            if(sum + page > minTime) { 
                people++; 
                sum = 0; 
            } 
            sum += page; 
        } 
        return people <= k; 
    } 
------------------------------ 
pages={3,2,4} 
k=2 
------------------------------ 
Round 1: 
lo=4,hi=9 
mid=6 -> minTimeNeeded=6 -> peopleNeeded(pages,6,2) 
-> people=2 <= k=2 -> hi=mid-1=5 
------------------------------ 
Round 2: 
lo=4,hi=5 
mid=4 -> minTimeNeeded=4 -> peopleNeeded(pages,4,2) 
-> people=3 > k=2 -> lo=mid+1=5 
------------------------------ 
Round 3: 
lo=5,hi=5 
mid=5 -> minTimeNeeded=5 -> peopleNeeded(pages,5,2) 
-> people=2 <= k=2 -> hi=mid-1=4 
lo > hi while loop end
```

Refer to
https://www.lintcode.com/problem/copy-books/solution/17469
非常值得总结的二分答案的题目思路就是，对于 完成时间 进行二分查找，找到第一个 不多于K个人能在给定的完成时间里完成 复印len(pages)本书 这一任务的 完成时间
完成时间的lower bound: max(pages), 理解为一共有len(pages)个人，每个人只复印一本书

完成时间的upper bound: sum(pages), 理解为只有一个人要独自复印所有的书

```
class Solution: 
    """ 
    @param pages: an array of integers 
    @param k: An integer 
    @return: an integer 
    """ 
    def copyBooks(self, pages, k): 
        # write your code here 
         
        if not pages: 
             
            return 0  
             
        start = max(pages) 
        end = sum(pages) 
         
        while start + 1 < end: 
             
            mid = start + (end - start) // 2  
             
            if self.can_complete(pages, k, mid): 
                 
                end = mid  
                 
            else: 
                 
                start = mid  
                 
        if self.can_complete(pages, k, start): 
             
            return start  
             
        # Since even if there is only one person, 
        # he or she can copy all books, just return end. 
        return end  
         
    def can_complete(self, pages, k, tl): 
         
        num = 1  
         
        pageSum = 0 
         
        for page in pages: 
             
            if pageSum + page <= tl: 
                 
                pageSum += page  
                 
            else: 
                 
                num += 1  
                pageSum = page  
                 
        return num <= k
```

---
Solution 2 (360min, too long to come up with dp[i][j] = k definition and dp equation)
```
public class Solution { 
    /** 
     * @param pages: an array of integers 
     * @param k: An integer 
     * @return: an integer 
     */ 
    public int copyBooks(int[] pages, int k) { 
        int len = pages.length; 
        int[] preSum = new int[len + 1]; 
        for(int i = 1; i <= len; i++) { 
            preSum[i] = preSum[i - 1] + pages[i - 1]; 
        } 
        int[][] dp = new int[k + 1][len + 1]; 
        dp[0][0] = 0; 
        // 如果i本书给0个人去抄，一辈子都抄不完 
        for(int i = 1; i <= len; i++) { 
            dp[0][i] = Integer.MAX_VALUE; 
        } 
        // 如果0本书给i个人去抄写，花费0的时间 
        for(int i = 1; i <= k; i++) { 
            dp[i][0] = 0; 
        } 
        for(int i = 1; i <= k; i++) { // 枚举人从 1 到 k (一共k个人) 
            for(int j = 1; j <= len; j++) { // 枚举书从 1 到 n (一共n本书) 
                dp[i][j] = Integer.MAX_VALUE; 
                for(int pre = 0; pre < j; pre++) { 
                    // 最后一个人抄剩下(pre, pre+1..., j)本书所需时间preSum[j] - preSum[pre],  
                    // 对比前i-1个人抄(0, 1, 2..., pre)本书的所需时间dp[i - 1][pre], 两者取最大值 
                    int cost = preSum[j] - preSum[pre]; 
                    int curMaxCost = Math.max(cost, dp[i - 1][pre]); 
                    // 更新当前最小值 
                    dp[i][j] = Math.min(dp[i][j], curMaxCost); 
                } 
            } 
        } 
        return dp[k][len]; 
    } 
}

Space Complexity: O(1)          
Time Complexity: O(n^3)
```

Step by step analysis how state equation works
```
初始化 
pages={3,2,4} k=2 
dp=[k+1][pages.length+1]=[3][4] // dp[i][j] -> i个copier，抄完j本书需要最短时间 
0,0,0,0 
0,0,0,0 
0,0,0,0 
----------------------------------------------------------- 
边界初始化 
if 0 copier, 无穷大时间, if 0 本书，不管几个copier耗时都是0 
0,INF,INF,INF 
0,  0,  0,  0 
0,  0,  0,  0 
----------------------------------------------------------- 
枚举分析具体情况，找到递归方程 
pages={3,2,4} k=2 
1个copier抄完1本书 
dp[1][1]=pages[0]=3 
1个copier抄完2本书 
dp[1][2]=pages[0]+pages[1]=3+2=5 
1个copier抄完3本书 
dp[1][3]=pages[0]+pages[1]+pages[2]=3+2+4=9 
2个copier抄完1本书 
dp[2][1]=pages[0]=3 
2个copier抄完2本书 
dp[2][2]=Math.max(pages[0],pages[1])=3 
2个copier抄完3本书 
dp[2][3]=Math.max(pages[0]+pages[1],pages[2])=5 
0,INF,INF,INF 
0,  3,  5,  9 
0,  3,  3,  5 
----------------------------------------------------------- 
根据dp[1][3],dp[2][3]的累加模式发现类似preSum 
Define a preSum array based on pages={3,2,4} 
int[] preSum = new int[len + 1]=new int[4] 
for(int i = 1; i < 4; i++) { 
    preSum[i] = preSum[i - 1] + pages[i - 1]; 
} 
==> preSum={0,3,5,9} 
for(int i = 1; i <= k; i++) { // 枚举人从 1 到 k (一共k个人) 
    for(int j = 1; j <= n; j++) { // 枚举书从 1 到 n (一共n本书) 
        dp[i][j] = Integer.MAX_VALUE; 
        for(int pre = 0; pre < j; pre++) { 
             // 最后一个人抄剩下(pre, pre+1..., j)本书所需时间preSum[j] - preSum[pre], 对比前i-1个人  
抄(0, 1, 2..., pre)本书的所需时间dp[i - 1][pre], 两者取最大值 
             int cost = preSum[j] - preSum[pre]; 
             int curMaxCost = Math.max(cost, dp[i - 1][pre]); 
             dp[i][j] = Math.min(dp[i][j], curMaxCost); 
        } 
    } 
} 
----------------------------------------------------------- 
preSum={0,3,5,9} 
0,INF,INF,INF 
0,  0,  0,  0 
0,  0,  0,  0 
----------------------------------------------------------- 
Round 1: 
i=1 
j=1 -> dp[1][1]=INF 
0,INF,INF,INF 
0,INF,  0,  0 
0,  0,  0,  0 
pre=0 -> cost=preSum[1]-preSum[0]=3-0=3,curMaxCost=Math.max(3,dp[1-1][0])=Math.max(3,0)=3, 最后一个人  
抄剩下1本书所需时间preSum[1]-preSum[0]=3, 对比前1-1=0个人抄0本书的所需时间dp[1-1][0]=0, 两者取最大值3   
-> 再刷新当前最小记录dp[1][1]=Math.min(INF,3)=3, 1个人抄完1本书最少需要3分钟 
0,INF,INF,INF 
0,  3,  0,  0 
0,  0,  0,  0 
----------------------------------------------------------- 
Round 2: 
i=1 
j=2 -> dp[1][2]=INF 
0,INF,INF,INF 
0,  3,INF,  0 
0,  0,  0,  0 
pre=0 -> cost=preSum[2]-preSum[0]=5-0=5,curMaxCost=Math.max(5,dp[1-1][0])=Math.max(5,0)=5, 最后一个人  
抄剩下2本书所需时间preSum[2]-preSum[0]=5, 对比前1-1=0个人抄0本书的所需时间dp[1-1][0]=0, 两者取最大值5   
-> 再刷新当前最小记录dp[1][2]=Math.min(INF,5)=5, 1个人抄完2本书最少需要5分钟 
pre=1 -> cost=preSum[2]-preSum[1]=5-3=2,curMaxCost=Math.max(2,dp[1-1][1])=Math.max(2,INF)=INF, 最后一  
个人抄剩下1本书所需时间preSum[2]-preSum[1]=2, 对比前1-1=0个人抄1本书的所需时间dp[1-1][1]=INF, 两者取  
最大值INF -> 再刷新当前最小记录dp[1][2]=Math.min(5,INF)=5, 1个人抄完2本书最少需要5分钟(因为前一本没人  
抄,在前一本没人炒的情况下1个人抄后一本的可能性为0,总耗时时间继承于1个人抄完2本书最少需要5分钟) 
0,INF,INF,INF 
0,  3,  5,  0 
0,  0,  0,  0 
----------------------------------------------------------- 
Round 3: 
i=1 
j=3 -> dp[1][3]=INF 
0,INF,INF,INF 
0,  3,  5,INF 
0,  0,  0,  0 
pre=0 -> cost=preSum[3]-preSum[0]=9-0=9,curMaxCost=Math.max(9,dp[1-1][0])=Math.max(9,0)=9, 最后一个人  
抄剩下3本书所需时间preSum[3]-preSum[0]=9, 对比前1-1=0个人抄0本书的所需时间dp[1-1][0]=0, 两者取最大值9   
-> 再刷新当前最小记录dp[1][3]=Math.min(INF,9)=9, 1个人抄完3本书最少需要9分钟 
pre=1 -> cost=preSum[3]-preSum[1]=9-3=6,curMaxCost=Math.max(6,dp[1-1][1])=Math.max(6,INF)=INF, 最后一  
个人抄剩下2本书所需时间preSum[3]-preSum[1]=6, 对比前1-1=0个人抄1本书的所需时间dp[1-1][1]=INF, 两者取  
最大值INF -> 再刷新当前最小记录dp[1][3]=Math.min(9,INF)=9, 1个人抄完2本书最少需要9分钟(因为前一本没人  
抄,在前一本没人炒的情况下1个人抄后两本的可能性为0,总耗时时间继承于1个人抄完3本书最少需要9分钟) 
pre=2 -> cost=preSum[3]-preSum[2]=9-5=4,curMaxCost=Math.max(4,dp[1-1][2])=Math.max(4,INF)=INF, 最后一  
个人抄剩下1本书所需时间preSum[3]-preSum[2]=4, 对比前1-1=0个人抄2本书的所需时间dp[1-1][2]=INF, 两者取  
最大值INF -> 再刷新当前最小记录dp[1][3]=Math.min(9,INF)=9, 1个人抄完1本书最少需要9分钟(因为前两本没人  
抄,在前两本没人炒的情况下1个人抄后一本的可能性为0,总耗时时间继承于1个人抄完3本书最少需要9分钟) 
0,INF,INF,INF 
0,  3,  5,  9 
0,  0,  0,  0 
----------------------------------------------------------- 
Round 4: 
i=2 
j=1 -> dp[2][1]=INF 
0,INF,INF,INF 
0,  3,  5,  9 
0,INF,  0,  0 
pre=0 -> cost=preSum[1]-preSum[0]=3-0=3,curMaxCost=Math.max(3,dp[2-1][0])=Math.max(3,0)=3, 最后一个人  
抄剩下1本书所需时间preSum0[1]-preSum[0]=3, 对比前2-1=1个人抄0本书的所需时间dp[2-1][0]=0, 两者取最大值  
3 -> 再刷新当前最小记录dp[2][1]=Math.min(INF,3)=3, 2个人抄完1本书最少需要3分钟 
0,INF,INF,INF 
0,  3,  5,  9 
0,  3,  0,  0 
----------------------------------------------------------- 
Round 5: 
i=2 
j=2 -> dp[2][2]=INF 
0,INF,INF,INF 
0,  3,  5,  9 
0,  3,INF,  0 
pre=0 -> cost=preSum[2]-preSum[0]=5-0=5,curMaxCost=Math.max(5,dp[2-1][0])=Math.max(5,0)=5, 最后一个人  
抄剩下2本书所需时间preSum0[2]-preSum[0]=5, 对比前2-1=1个人抄0本书的所需时间dp[2-1][0]=0, 两者取最大值  
5 -> 再刷新当前最小记录dp[2][2]=Math.min(INF,5)=5, 2个人抄完2本书最少需要5分钟 
pre=1 -> cost=preSum[2]-preSum[1]=5-3=2,curMaxCost=Math.max(2,dp[2-1][1])=Math.max(2,3)=3, 最后一个人  
抄剩下1本书所需时间preSum0[2]-preSum[1]=2, 对比前2-1=1个人抄1本书的所需时间dp[2-1][1]=3, 两者取最大值  
3 -> 再刷新当前最小记录dp[2][2]=Math.min(5,3)=3, 2个人抄完2本书最少需要3分钟 
0,INF,INF,INF 
0,  3,  5,  9 
0,  3,  3,  0 
----------------------------------------------------------- 
Round 6: 
i=2 
j=3 -> dp[2][3]=INF 
0,INF,INF,INF 
0,  3,  5,  9 
0,  3,  3,INF 
pre=0 -> cost=preSum[3]-preSum[0]=9-0=9,curMaxCost=Math.max(9,dp[2-1][0])=Math.max(9,0)=9, 最后一个人  
抄剩下3本书所需时间preSum0[3]-preSum[0]=9, 对比前2-1=1个人抄0本书的所需时间dp[2-1][0]=0, 两者取最大值  
9 -> 再刷新当前最小记录dp[2][2]=Math.min(INF,9)=9, 2个人抄完3本书最少需要9分钟 
pre=1 -> cost=preSum[3]-preSum[1]=9-3=6,curMaxCost=Math.max(6,dp[2-1][1])=Math.max(9,3)=6, 最后一个人  
抄剩下2本书所需时间preSum0[3]-preSum[1]=6, 对比前2-1=1个人抄1本书的所需时间dp[2-1][1]=3, 两者取最大值  
6 -> 再刷新当前最小记录dp[2][2]=Math.min(9,6)=6, 2个人抄完3本书最少需要6分钟 
pre=2 -> cost=preSum[3]-preSum[2]=9-5=4,curMaxCost=Math.max(4,dp[2-1][2])=Math.max(4,5)=5, 最后一个人  
抄剩下1本书所需时间preSum0[3]-preSum[2]=4, 对比前2-1=1个人抄2本书的所需时间dp[2-1][2]=5, 两者取最大值  
5 -> 再刷新当前最小记录dp[2][2]=Math.min(6,5)=5, 2个人抄完3本书最少需要5分钟 
0,INF,INF,INF 
0,  3,  5,  9 
0,  3,  3,  5 
----------------------------------------------------------- 
dp[k][n]=dp[2][3]=5 -> 2个人抄完3本书最少需要5分钟
```

Refer to
https://www.lintcode.com/problem/437/solution/17324
https://www.lintcode.com/problem/437/solution/64218
```
public class Solution { 
    /** 
     * @param pages: an array of integers 
     * @param k: An integer 
     * @return: an integer 
     */ 
    public int copyBooks(int[] pages, int k) { 
       int n = pages.length; 
        int[] preSum = new int[n+1]; 
        for (int i = 1; i <= n ; i++) { 
            preSum[i] = preSum[i-1] + pages[i-1]; 
        } 
        //dp state: 前i本书分给j个人去 抄写，最好需要多少时间。 
        int[][] dp = new int[n+1][k+1]; 
        dp[0][0] = 0; 
        for (int i = 1; i <= n; i++) {//i本书给 0个人去抄，一辈子都抄不完。 
            dp[i][0] = Integer.MAX_VALUE; 
        } 
        for (int j = 1; j <= k ; j++) { 
            dp[0][j] = 0;//0本书给 j 个人去抄写，花费0的时间。 
        } 
        for (int i = 1; i <= n ; i++) { 
            for (int j = 1; j <= k ; j++) { 
                dp[i][j] = Integer.MAX_VALUE; 
                for (int pre = 0; pre < i; pre++) { 
                    int cost = preSum[i] - preSum[pre];//最后一个人抄的时间。 
                    //最后一个人花费 cost， 前j-1个人（0，1，2。。。pre)本书的 cost，两者取最大值。 
                    int curMaxCost = Math.max(dp[pre][j-1], cost); 
                    dp[i][j] = Math.min(dp[i][j], curMaxCost); 
                } 
            } 
        } 
        return dp[n][k]; 
    } 
}
```
