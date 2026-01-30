https://leetcode.com/problems/online-election/description/
You are given two integer arrays persons and times. In an election, the ith vote was cast for persons[i] at time times[i].
For each query at a time t, find the person that was leading the election at time t. Votes cast at time t will count towards our query. In the case of a tie, the most recent vote (among tied candidates) wins.
Implement the TopVotedCandidate class:
- TopVotedCandidate(int[] persons, int[] times) Initializes the object with the persons and times arrays.
- int q(int t) Returns the number of the person that was leading the election at time t according to the mentioned rules.
 
Example 1:
Input
["TopVotedCandidate", "q", "q", "q", "q", "q", "q"][[[0, 1, 1, 0, 0, 1, 0], [0, 5, 10, 15, 20, 25, 30]], [3], [12], [25], [15], [24], [8]]
Output
[null, 0, 1, 1, 0, 0, 1]
Explanation
TopVotedCandidate topVotedCandidate = new TopVotedCandidate([0, 1, 1, 0, 0, 1, 0], [0, 5, 10, 15, 20, 25, 30]);
topVotedCandidate.q(3); // return 0, At time 3, the votes are [0], and 0 is leading.
topVotedCandidate.q(12); // return 1, At time 12, the votes are [0,1,1], and 1 is leading.
topVotedCandidate.q(25); // return 1, At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent vote.)
topVotedCandidate.q(15); // return 0topVotedCandidate.q(24); // return 0topVotedCandidate.q(8); // return 1
 
Constraints:
- 1 <= persons.length <= 5000
- times.length == persons.length
- 0 <= persons[i] < persons.length
- 0 <= times[i] <= 109
- times is sorted in a strictly increasing order.
- times[0] <= t <= 109
- At most 104 calls will be made to q.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-29
Solution 1: Hash Table + Binary Search (30 min)
class TopVotedCandidate {
    // Timestamps when votes were cast (sorted increasingly)
    int[] times;
    // Precomputed leaders at each vote timestamp
    int[] leaders;
    public TopVotedCandidate(int[] persons, int[] times) {
        int n = times.length;
        this.times = times;
        leaders = new int[n];
        // Track vote counts per candidate
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int curLeader = -1;
        // Precompute leader after each vote
        for(int i = 0; i < n; i++) {
            int person = persons[i];
            int curVote = map.getOrDefault(person, 0) + 1;
            map.put(person, curVote);
            // Tie-breaking: most recent candidate with max votes wins
            // Using >= ensures current person wins ties
            if(curVote >= max) {
                max = curVote;
                curLeader = person;
            }
            leaders[i] = curLeader;
        }
    }
    
    // Find Upper Boundary: Uses binary search to find the latest timestamp <= t.
    public int q(int t) {
        int lo = 0;
        int hi = this.times.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(times[mid] > t) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return leaders[hi];
    }
}

/**
 * Your TopVotedCandidate object will be instantiated and called as such:
 * TopVotedCandidate obj = new TopVotedCandidate(persons, times);
 * int param_1 = obj.q(t);
 */
 
Time Complexity:
Constructor: O(n) where n is number of votes
Query: O(log n) for binary search (or O(1) for TreeMap's floorKey)

Space Complexity: O(n)

Refer to QWEN
Here's a complete, optimized Java solution for LeetCode 911: Online Election, with detailed explanation and edge-case handling:
import java.util.*;

class TopVotedCandidate {
    // Precomputed leaders at each vote timestamp
    private final int[] leaders;
    // Timestamps when votes were cast (sorted increasingly)
    private final int[] times;

    public TopVotedCandidate(int[] persons, int[] times) {
        this.times = times;
        int n = persons.length;
        this.leaders = new int[n];
        
        // Track vote counts per candidate
        Map<Integer, Integer> voteCount = new HashMap<>();
        int maxVotes = 0;
        int currentLeader = -1;
        
        // Precompute leader after each vote
        for (int i = 0; i < n; i++) {
            int person = persons[i];
            int votes = voteCount.getOrDefault(person, 0) + 1;
            voteCount.put(person, votes);
            
            // Tie-breaking: most recent candidate with max votes wins
            // Using >= ensures current person wins ties
            if (votes >= maxVotes) {
                maxVotes = votes;
                currentLeader = person;
            }
            leaders[i] = currentLeader;
        }
    }
    
    /**
     * Returns the leading candidate at time t.
     * Uses binary search to find the latest timestamp <= t.
     */
    public int q(int t) {
        // Built-in binary search (returns insertion point if not found)
        int idx = Arrays.binarySearch(times, t);
        
        if (idx < 0) {
            // t not found: insertion point = -(idx + 1)
            // We want the element just before insertion point
            idx = -idx - 2;
        }
        // idx now points to largest time <= t
        return leaders[idx];
    }
}
Problem Summary
- Input:
- persons[i] = candidate receiving vote at time times[i]
- times is strictly increasing
- Query q(t): Return the leading candidate at time t
- Tie-breaking rule: Most recent candidate with the highest vote count wins
Key Strategy
1.Preprocessing (Constructor - O(n)):
- Maintain running vote counts
- After each vote, determine current leader (handling ties via recency)
- Store leader at each timestamp in leaders[] array
2.Query Handling (O(log n)):
- Binary search times[] to find latest timestamp ≤ t
- Return precomputed leader at that index
Why >= for Tie-Breaking?
When two candidates have equal votes, the most recent one becomes leader. Since we process votes sequentially, using if (votes >= maxVotes) ensures the current voter wins ties automatically.
Complexity Analysis
OperationTimeSpaceConstructorO(n)O(n)Query q(t)O(log n)O(1)
- n = number of votes (≤ 5000 per constraints)
Example Walkthrough
persons = [0, 1, 1, 0, 0, 1, 0]
times   = [0, 5, 10, 15, 20, 25, 30]

Vote progression:
Time 0:  votes={0:1}           → leader=0  (leaders[0]=0)
Time 5:  votes={0:1,1:1}       → leader=1  (tie → 1 is recent)
Time 10: votes={0:1,1:2}       → leader=1
Time 15: votes={0:2,1:2}       → leader=0  (tie → 0 is recent)
Time 20: votes={0:3,1:2}       → leader=0
Time 25: votes={0:3,1:3}       → leader=1  (tie → 1 is recent)
Time 30: votes={0:4,1:3}       → leader=0

leaders = [0, 1, 1, 0, 0, 1, 0]

Queries:
q(3)   → times[0]=0 ≤ 3 → leader=0
q(12)  → times[2]=10 ≤ 12 → leader=1
q(25)  → times[5]=25 → leader=1
q(15)  → times[3]=15 → leader=0
Alternative Binary Search Implementation
If you prefer manual binary search instead of Arrays.binarySearch:
public int q(int t) {
    int left = 0, right = times.length - 1;
    while (left < right) {
        int mid = left + (right - left + 1) / 2; // upper mid to avoid infinite loop
        if (times[mid] <= t) {
            left = mid;
        } else {
            right = mid - 1;
        }
    }
    return leaders[left];
}
Usage Example
TopVotedCandidate tvc = new TopVotedCandidate(
    new int[]{0, 1, 1, 0, 0, 1, 0},
    new int[]{0, 5, 10, 15, 20, 25, 30}
);

System.out.println(tvc.q(3));   // Output: 0
System.out.println(tvc.q(12));  // Output: 1
System.out.println(tvc.q(25));  // Output: 1
System.out.println(tvc.q(15));  // Output: 0
System.out.println(tvc.q(24));  // Output: 0 (last time ≤24 is 20 → leader=0)
System.out.println(tvc.q(30));  // Output: 0
This solution efficiently handles up to 5,000 votes and 10,000 queries within problem constraints, leveraging preprocessing and binary search for optimal performance.

Refer to Deepseek
Here's a Java solution for LeetCode 911 - Online Election using binary search and precomputation:
Problem Summary
We need to implement a TopVotedCandidate class that:
1.Records votes at specific times
2.Returns who was leading at any given time t
3.If there's a tie, the most recent vote wins
Solution
class TopVotedCandidate {
    private int[] times;
    private int[] leaders;
    
    public TopVotedCandidate(int[] persons, int[] times) {
        this.times = times;
        this.leaders = new int[times.length];
        
        // Map to store vote count for each person
        int[] votes = new int[5000]; // Max persons according to constraints
        int currentLeader = -1;
        int maxVotes = 0;
        
        for (int i = 0; i < persons.length; i++) {
            int person = persons[i];
            votes[person]++;
            
            // Update leader
            // If tie (votes[person] == maxVotes), most recent vote wins
            if (votes[person] >= maxVotes) {
                maxVotes = votes[person];
                currentLeader = person;
            }
            
            leaders[i] = currentLeader;
        }
    }
    
    public int q(int t) {
        // Binary search to find the right time index
        int left = 0, right = times.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (times[mid] == t) {
                return leaders[mid];
            } else if (times[mid] < t) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // When we exit the loop, 'right' is the index of the largest time <= t
        return leaders[right];
    }
}
Alternative Solution with TreeMap
import java.util.*;

class TopVotedCandidate {
    private TreeMap<Integer, Integer> timeToLeader;
    
    public TopVotedCandidate(int[] persons, int[] times) {
        timeToLeader = new TreeMap<>();
        
        int n = persons.length;
        int[] votes = new int[5000];
        int currentLeader = -1;
        int maxVotes = 0;
        
        for (int i = 0; i < n; i++) {
            int person = persons[i];
            votes[person]++;
            
            // Update leader if this person has >= maxVotes
            // The ">=" handles ties (most recent wins)
            if (votes[person] >= maxVotes) {
                maxVotes = votes[person];
                currentLeader = person;
            }
            
            timeToLeader.put(times[i], currentLeader);
        }
    }
    
    public int q(int t) {
        // floorKey() returns the greatest key less than or equal to t
        Integer time = timeToLeader.floorKey(t);
        return timeToLeader.get(time);
    }
}
Optimized Solution with Binary Search (Most Efficient)
class TopVotedCandidate {
    private int[] times;
    private int[] leaders;
    
    public TopVotedCandidate(int[] persons, int[] times) {
        this.times = times;
        this.leaders = new int[times.length];
        
        int n = persons.length;
        int[] votes = new int[n + 1]; // persons are 0-indexed
        
        int currentLeader = persons[0];
        int maxVotes = 0;
        
        for (int i = 0; i < n; i++) {
            int person = persons[i];
            votes[person]++;
            
            // Update leader (>= handles ties with most recent)
            if (votes[person] >= maxVotes) {
                maxVotes = votes[person];
                currentLeader = person;
            }
            
            leaders[i] = currentLeader;
        }
    }
    
    public int q(int t) {
        // Binary search to find the largest time <= t
        int left = 0, right = times.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (times[mid] == t) {
                return leaders[mid];
            } else if (times[mid] < t) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // After loop, right points to largest time <= t
        return leaders[right];
    }
}
Explanation
Time Complexity:
- Constructor: O(n) where n is number of votes
- Query: O(log n) for binary search (or O(1) for TreeMap's floorKey)
Space Complexity: O(n)
Key Points:
1.Precomputation Strategy:
- We calculate the leader at each time point during construction
- Store results in arrays for O(1) lookup during queries
2.Handling Ties:
- The condition if (votes[person] >= maxVotes) ensures that when there's a tie, the most recent vote wins
- >= instead of > gives preference to the current person in case of equal votes
3.Binary Search Approach:
- We need to find the largest time that is ≤ query time t
- When binary search ends, right points to the correct index
- Example: times = [0, 5, 10, 15, 20], t = 12
- Binary search finds index 2 (time 10) as the largest ≤ 12
4.TreeMap Alternative:
- floorKey(t) does the binary search internally
- Cleaner code but slightly slower due to boxing/unboxing
Example Walkthrough
Input:
persons = [0, 1, 1, 0, 0, 1, 0]
times =   [0, 5, 10, 15, 20, 25, 30]

Vote Counting:
Time 0:  Person 0 → votes: 0:1, leader: 0
Time 5:  Person 1 → votes: 1:1, leader: 0 (tie, but 0 was more recent)
Time 10: Person 1 → votes: 1:2, leader: 1
Time 15: Person 0 → votes: 0:2, leader: 0 (tie, 0 more recent)
Time 20: Person 0 → votes: 0:3, leader: 0
Time 25: Person 1 → votes: 1:3, leader: 0 (tie, 0 more recent)
Time 30: Person 0 → votes: 0:4, leader: 0

Query q(3) → returns leader at time 0 → 0
Query q(12) → returns leader at time 10 → 1
Query q(25) → returns leader at time 25 → 0
Query q(15) → returns leader at time 15 → 0
Query q(24) → returns leader at time 20 → 0
Query q(8) → returns leader at time 5 → 0
The solution efficiently handles queries by precomputing leaders at each time point and using binary search to find the appropriate leader for any given query time.

Refer to
L704.Binary Search
L1366.Rank Teams by Votes (Ref.L911)
