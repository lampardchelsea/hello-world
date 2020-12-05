/**
Refer to
https://leetcode.com/problems/most-profit-assigning-work/
We have jobs: difficulty[i] is the difficulty of the ith job, and profit[i] is the profit of the ith job. 

Now we have some workers. worker[i] is the ability of the ith worker, which means that this worker can only complete a job with difficulty at most worker[i]. 

Every worker can be assigned at most one job, but one job can be completed multiple times.

For example, if 3 people attempt the same job that pays $1, then the total profit will be $3.  If a worker cannot complete any job, his profit is $0.

What is the most profit we can make?

Example 1:
Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
Output: 100 
Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get profit of [20,20,30,30] seperately.

Notes:
1 <= difficulty.length = profit.length <= 10000
1 <= worker.length <= 10000
difficulty[i], profit[i], worker[i]  are in range [1, 10^5]
*/

// Solution 1: Two Pointers
// Refer to
// https://www.cnblogs.com/grandyang/p/10264877.html
/**
我们也可以不用TreeMap，而是将难度和利润组成pair，加入到一个数组中，那么就算相同的难度对应不同的利润，也不会丢失数据。我们还是根据难度给所有的工作排个序，
同时，我们按能力也给工人排个序，从能力低的工人开始分配工作，这样我们只需要遍历一次所有的工作，因为当能力低的工人分配了某个工作时，后面能力高的工人不需要
再考虑之前的工作，因为工作已经按难度排好序了，只需要从当前位置继续往后面匹配工作，我们可以使用一个全局变量i，当工人的能力值大于等于某个工作的难度时，
我们用其来更新curMax，这里的curMax记录当前工人能做的工作的最大利润，这样当工作遍历完了，或者当前工作难度已经大于工人的能力值了时就停止，这样curMax就是
该工人能获得的利润，加入结果res，参见代码如下：
class Solution {
public:
    int maxProfitAssignment(vector<int>& difficulty, vector<int>& profit, vector<int>& worker) {
        int res = 0, n = profit.size(), curMax = 0, i = 0;
        vector<pair<int, int>> jobs;
        for (int j = 0; j < n; ++j) {
            jobs.push_back({difficulty[j], profit[j]});
        }
        sort(jobs.begin(), jobs.end());
        sort(worker.begin(), worker.end());
        for (int ability : worker) {
            while (i < n && ability >= jobs[i].first) {
                curMax = max(curMax, jobs[i++].second);
            }
            res += curMax;
        }
        return res;
    }
};
*/

// Refer to
// https://leetcode.com/problems/most-profit-assigning-work/discuss/127031/C%2B%2BJavaPython-Sort-and-Two-pointer
/**
Solution 1
zip difficulty and profit as jobs.
sort jobs and sort 'worker'.
Use 2 pointers. For each worker, find his maximum profit best he can make under his ability.

Because we have sorted jobs and worker,
we will go through two lists only once.
this will be only O(D + W).
O(DlogD + WlogW), as we sort jobs.

Java:
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        List<Pair<Integer, Integer>> jobs = new ArrayList<>();
        int N = profit.length, res = 0, i = 0, best = 0;
        for (int j = 0; j < N; ++j)
            jobs.add(new Pair<Integer, Integer>(difficulty[j], profit[j]));
        Collections.sort(jobs, Comparator.comparing(Pair::getKey));
        Arrays.sort(worker);
        for (int ability : worker) {
            while (i < N && ability >= jobs.get(i).getKey())
                best = Math.max(jobs.get(i++).getValue(), best);
            res += best;
        }
        return res;
    }
*/
class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        // Sort work by ability
        Arrays.sort(worker);
        // Linking difficulty and profit
        List<Node> jobs = new ArrayList<Node>();
        for(int i = 0; i < difficulty.length; i++) {
            jobs.add(new Node(difficulty[i], profit[i]));
        }
        // Sort jobs by difficulty
        Collections.sort(jobs, new CustomComparator());
        int maxProfit = 0;
        int best = 0;
        // Index i holding last maximum difficulty's corresponding job's position in jobs array
        // and because we have sort the worker's ability already, if i stopped somewhere, next
        // time j will point to even higher ability worker, so i no need to restart again from
        // index 0 but keep moving forward based on last position
        int i = 0;
        int N = worker.length;
        for(int j = 0; j < N; j++) {
            int ability = worker[j];
            while(i < N && ability >= jobs.get(i).difficulty) {
                // we should compare always between current job's profit 
                // and historical maximum profit, since higher difficulty
                // doesn't mean higher profit
                //best = jobs.get(i).profit;
                // E.g output 204, expected 324
                // difficulty= [68,35,52,47,86]
                // profit = [67,17,1,81,3]
                // worker = [92,10,85,84,82]
                best = Math.max(best, jobs.get(i).profit);
                i++;
            }
            maxProfit += best;
        }
        return maxProfit;
    }
}

class CustomComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return o1.difficulty - o2.difficulty;
    }
}

class Node {
    int difficulty;
    int profit;
    public Node(int difficulty, int profit) {
        this.difficulty = difficulty;
        this.profit = profit;
    }
}
