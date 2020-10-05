/**
 Refer to
 https://leetcode.com/problems/bus-routes/
 We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever. For example if 
 routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the sequence 1->5->7->1->5->7->1->... forever.

We start at bus stop S (initially not on a bus), and we want to go to bus stop T. Travelling by buses only, what 
is the least number of buses we must take to reach our destination? Return -1 if it is not possible.

Example:
Input: 
routes = [[1, 2, 7], [3, 6, 7]]
S = 1
T = 6
Output: 2
Explanation: 
The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
 
Constraints:
1 <= routes.length <= 500.
1 <= routes[i].length <= 10^5.
0 <= routes[i][j] < 10 ^ 6.
*/

// Solution 1: BFS + reversely mapping stop to buses pass that stop
// Refer to
// https://leetcode.com/problems/bus-routes/discuss/122712/Simple-Java-Solution-using-BFS
/**
 For each of the bus stop, we maintain all the buses (bus routes) that go through it. To do that, we use a HashMap, where bus stop number 
 is the key and all the buses (bus routes) that go through it are added to an ArrayList.

We use BFS, where we process elements in a level-wise manner. We add the Start bus stop in the queue. Next, when we enter the while loop, 
we add all the bus stops that are reachable by all the bus routes that go via the Start. Thus, if we have the input as [[1, 2, 7], [3, 6, 7]] 
and Start as 6, then upon processing bus stop 6, we would add bus stops 3 and 7.
With this approach, all the bus stops at a given level, are "equal distance" from the start node, in terms of number of buses that need to be changed.

To avoid loops, we also maintain a HashSet that stores the buses that we have already visited.
Note that while in this approach, we use each stop for doing BFS, one could also consider each bus (route) also for BFS.
*/

// https://www.cnblogs.com/grandyang/p/10293947.html
/**
 这道题给了我们一堆公交线路表，然后给了起点和终点，问最少要换乘几辆公交可以从起点到达终点。这种原本只需要使用谷歌地图或者百度地图轻松实现的事，
 现在需要自己来实现。但这毕竟是简化版，真实情况一定要复杂得多。 这题容易进的一个误区就是把 routes 直接当作邻接链表来进行图的遍历，其实是不对的，
 因为 routes 数组的含义是，某个公交所能到达的站点，而不是某个站点所能到达的其他站点。这里出现了两种不同的结点，分别是站点和公交。而 routes 
 数组建立的是公交和其站点之间的关系，那么应该将反向关系数组也建立出来，即要知道每个站点有哪些公交可以到达。由于这里站点的标号不一定是连续的，
 所以可以使用 HashMap，建立每个站点和其属于的公交数组之间的映射。由于一个站点可以被多个公交使用，所以要用个数组来保存公交。既然这里求的是最少
 使用公交的数量，那么就类似迷宫遍历求最短路径的问题，BFS 应该是首先被考虑的解法。用队列 queue 来辅助，首先将起点S排入队列中，然后还需要一个 
 HashSet 来保存已经遍历过的公交（注意这里思考一下，为啥放的是公交而不是站点，因为统计的是最少需要坐的公交个数，这里一层就相当于一辆公交，最小
 的层数就是公交数），这些都是 BFS 的标配，应当已经很熟练了。在最开头先判断一下，若起点和终点相同，那么直接返回0，因为根本不用坐公交。否则开始
 while 循环，先将结果 res 自增1，因为既然已经上了公交，那么公交个数至少为1，初始化的时候是0。这里使用 BFS 的层序遍历的写法，就是当前所有的结
 点都当作深度相同的一层，至于为何采用这种倒序遍历的 for 循环写法，是因为之后队列的大小可能变化，放在判断条件中可能会出错。在 for 循环中，先取
 出队首站点，然后要去 HashMap 中去遍历经过该站点的所有公交，若某个公交已经遍历过了，直接跳过，否则就加入 visited 中。然后去 routes 数组中取
 出该公交的所有站点，如果有终点，则直接返回结果 res，否则就将站点排入队列中继续遍历
*/
class Solution {
    public int numBusesToDestination(int[][] routes, int S, int T) {
        // Test out by intput routes = {{1,7},{3,5}}, S = 5, T = 5
        if(S == T) {
            return 0;
        }
        // key -> stop ID, value -> buses pass the stop
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < routes.length; i++) {
            for(int j = 0; j < routes[i].length; j++) {
                List<Integer> buses = map.getOrDefault(routes[i][j], new ArrayList<Integer>());
                buses.add(i);
                map.put(routes[i][j], buses);
            }
        }
        Set<Integer> usedBuses = new HashSet<Integer>();
        int busNum = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(S);
        // Level order traverse, each new level means need 1 more bus to reach target stop
        while(!q.isEmpty()) {
            busNum++;
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int curStop = q.poll();
                List<Integer> busesPassCurStop = map.get(curStop);
                for(int curBus : busesPassCurStop) {
                    if(!usedBuses.contains(curBus)) {
                        usedBuses.add(curBus);
                        // Check if current bus able to reach target stop
                        for(int j = 0; j < routes[curBus].length; j++) {
                            if(routes[curBus][j] == T) {
                                return busNum;
                            }
                            q.offer(routes[curBus][j]);
                        }
                    }
                }
            }
        }
        return -1;
    }
}
