/**
Refer to
https://leetcode.com/problems/rank-teams-by-votes/
In a special ranking system, each voter gives a rank from highest to lowest to all teams participated in the competition.

The ordering of teams is decided by who received the most position-one votes. If two or more teams tie in the first position, 
we consider the second position to resolve the conflict, if they tie again, we continue this process until the ties are resolved. 
If two or more teams are still tied after considering all positions, we rank them alphabetically based on their team letter.

Given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams according to the 
ranking system described above.

Return a string of all teams sorted by the ranking system.

Example 1:
Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
Output: "ACB"
Explanation: Team A was ranked first place by 5 voters. No other team was voted as first place so team A is the first team.
Team B was ranked second by 2 voters and was ranked third by 3 voters.
Team C was ranked second by 3 voters and was ranked third by 2 voters.
As most of the voters ranked C second, team C is the second team and team B is the third.

Example 2:
Input: votes = ["WXYZ","XYZW"]
Output: "XWYZ"
Explanation: X is the winner due to tie-breaking rule. X has same votes as W for the first position but X has one vote 
as second position while W doesn't have any votes as second position. 

Example 3:
Input: votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
Output: "ZMNAGUEDSJYLBOPHRQICWFXTVK"
Explanation: Only one voter so his votes are used for the ranking.

Example 4:
Input: votes = ["BCA","CAB","CBA","ABC","ACB","BAC"]
Output: "ABC"
Explanation: 
Team A was ranked first by 2 voters, second by 2 voters and third by 2 voters.
Team B was ranked first by 2 voters, second by 2 voters and third by 2 voters.
Team C was ranked first by 2 voters, second by 2 voters and third by 2 voters.
There is a tie and we rank teams ascending by their IDs.

Example 5:
Input: votes = ["M","M","M","M"]
Output: "M"
Explanation: Only team M in the competition so it has the first rank.

Constraints:
1 <= votes.length <= 1000
1 <= votes[i].length <= 26
votes[i].length == votes[j].length for 0 <= i, j < votes.length.
votes[i][j] is an English upper-case letter.
All characters of votes[i] are unique.
All the characters that occur in votes[0] also occur in votes[j] where 1 <= j < votes.length.
*/

// Solution 1: Java, O(26n+(26^2 * log26)), Sort by high rank vote to low rank vote
// Refer to
// https://leetcode.com/problems/rank-teams-by-votes/discuss/524853/Java-O(26n%2B(262-*-log26))-Sort-by-high-rank-vote-to-low-rank-vote
class Solution {
    public String rankTeams(String[] votes) {
        // Construct frequency map based on 'A' to 'Z' as key and
        // each character's frequency on each position from 0 to length - 1
        Map<Character, int[]> freq = new HashMap<Character, int[]>();
        int len = votes[0].length();
        for(String vote : votes) {
            for(int i = 0; i < len; i++) {
                char c = vote.charAt(i);
                freq.putIfAbsent(c, new int[len]);
                freq.get(c)[i]++;
            }
        }
        List<Character> list = new ArrayList<Character>(freq.keySet());
        Collections.sort(list, (a, b) -> {
            // Scan from first position to last position if on any position 
            // find any frequency difference than return larger frequency one
            for(int i = 0; i < len; i++) {
                if(freq.get(a)[i] != freq.get(b)[i]) {
                    return freq.get(b)[i] - freq.get(a)[i];
                }
            }
            // If all positions frequency are same then return alphabetical order
            return a - b;
        });
        StringBuilder sb = new StringBuilder();
        for(char c : list) {
            sb.append(c);
        }
        return sb.toString();
    }
}


























































https://leetcode.com/problems/rank-teams-by-votes/description/
In a special ranking system, each voter gives a rank from highest to lowest to all teams participating in the competition.
The ordering of teams is decided by who received the most position-one votes. If two or more teams tie in the first position, we consider the second position to resolve the conflict, if they tie again, we continue this process until the ties are resolved. If two or more teams are still tied after considering all positions, we rank them alphabetically based on their team letter.
You are given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams according to the ranking system described above.
Return a string of all teams sorted by the ranking system.
 
Example 1:
Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
Output: "ACB"
Explanation: Team A was ranked first place by 5 voters. No other team was voted as first place, so team A is the first team.Team B was ranked second by 2 voters and ranked third by 3 voters.Team C was ranked second by 3 voters and ranked third by 2 voters.As most of the voters ranked C second, team C is the second team, and team B is the third.

Example 2:
Input: votes = ["WXYZ","XYZW"]
Output: "XWYZ"
Explanation:X is the winner due to the tie-breaking rule. X has the same votes as W for the first position, but X has one vote in the second position, while W does not have any votes in the second position. 

Example 3:
Input: votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
Output: "ZMNAGUEDSJYLBOPHRQICWFXTVK"
Explanation: Only one voter, so their votes are used for the ranking.
 
Constraints:
- 1 <= votes.length <= 1000
- 1 <= votes[i].length <= 26
- votes[i].length == votes[j].length for 0 <= i, j < votes.length.
- votes[i][j] is an English uppercase letter.
- All characters of votes[i] are unique.
- All the characters that occur in votes[0] also occur in votes[j] where 1 <= j < votes.length.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-26
Solution 1: Hash Table + Sorting (10 min)
class Solution {
    public String rankTeams(String[] votes) {
        // Map: team character -> array of vote counts per position
        // Example: map['A'][0] = number of 1st-place votes for team A
        Map<Character, int[]> map = new HashMap<>();
        int n = votes.length;
        // Number of teams (length of any vote string)
        int voteLen = votes[0].length();
        // Initialize vote count arrays for each team
        for(char team : votes[0].toCharArray()) {
            map.put(team, new int[voteLen]);
        }
        // Count votes for each position
        for(String vote : votes) {
            for(int pos = 0; pos < voteLen; pos++) {
                char team = vote.charAt(pos);
                map.get(team)[pos]++;
            }
        }
        // Prepare list of teams for sorting
        List<Character> teams = new ArrayList<>(map.keySet());
        // Custom comparator to rank teams:
        // 1. Higher vote count at position i comes first (descending)
        // 2. If all positions tie, sort alphabetically (ascending)
        teams.sort((a, b) -> {
            int[] countA = map.get(a);
            int[] countB = map.get(b);
            for(int i = 0; i < voteLen; i++) {
                if(countA[i] != countB[i]) {
                    // Descending order
                    return countB[i] - countA[i];
                }
            }
            // Alphabetical tiebreaker
            return a - b;
        });
        // Build final ranking string
        StringBuilder sb = new StringBuilder();
        for(char team : teams) {
            sb.append(team);
        }
        return sb.toString();
    }
}

Time Complexity: O(n * m + t log t)
n = number of votes
m = number of teams
t = number of teams (26 maximum)
Space Complexity: O(t * m) where t is number of teams

Refer to Deepseek
Problem Summary
We need to rank teams based on votes where each vote is a string representing the ranking of all teams. Teams are ranked from highest to lowest based on:
1.Number of first-place votes
2.If tied, number of second-place votes
3.Continue through all positions
4.If still tied, alphabetical order
Solution
import java.util.*;

class Solution {
    public String rankTeams(String[] votes) {
        if (votes.length == 0) return "";
        if (votes.length == 1) return votes[0];
        
        int numTeams = votes[0].length();
        
        // Create a map to store vote counts for each position
        // key: team character, value: array of vote counts for each position
        Map<Character, int[]> voteMap = new HashMap<>();
        
        // Initialize the map with all teams
        for (char c : votes[0].toCharArray()) {
            voteMap.put(c, new int[numTeams]);
        }
        
        // Count votes for each position
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                char team = vote.charAt(i);
                voteMap.get(team)[i]++; // Increment count for this position
            }
        }
        
        // Create a list of teams
        List<Character> teams = new ArrayList<>(voteMap.keySet());
        
        // Sort teams using custom comparator
        Collections.sort(teams, (a, b) -> {
            int[] votesA = voteMap.get(a);
            int[] votesB = voteMap.get(b);
            
            // Compare vote counts at each position
            for (int i = 0; i < numTeams; i++) {
                if (votesA[i] != votesB[i]) {
                    return votesB[i] - votesA[i]; // Descending order
                }
            }
            
            // If all positions are tied, sort alphabetically
            return a - b;
        });
        
        // Build the result string
        StringBuilder result = new StringBuilder();
        for (char team : teams) {
            result.append(team);
        }
        
        return result.toString();
    }
}
Alternative Solution with 2D Array
import java.util.*;

class Solution {
    public String rankTeams(String[] votes) {
        int n = votes[0].length();
        
        // Create a 2D array: 26 teams × n positions
        int[][] count = new int[26][n + 1];
        
        // Initialize: last column stores the team character (for alphabetical tie-break)
        for (int i = 0; i < 26; i++) {
            count[i][n] = i; // Store the team index
        }
        
        // Count votes
        for (String vote : votes) {
            for (int i = 0; i < n; i++) {
                char team = vote.charAt(i);
                count[team - 'A'][i]++;
            }
        }
        
        // Sort indices based on vote counts
        Integer[] indices = new Integer[26];
        for (int i = 0; i < 26; i++) {
            indices[i] = i;
        }
        
        Arrays.sort(indices, (a, b) -> {
            for (int i = 0; i < n; i++) {
                if (count[a][i] != count[b][i]) {
                    return count[b][i] - count[a][i]; // Descending
                }
            }
            return a - b; // Alphabetical
        });
        
        // Build result string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append((char) (indices[i] + 'A'));
        }
        
        return result.toString();
    }
}
Explanation
Time Complexity: O(n * m + t log t)
- n = number of votes
- m = number of teams
- t = number of teams (26 maximum)
Space Complexity: O(t * m) where t is number of teams
Key Points:
1.Data Structure Choice:
- The first solution uses HashMap for flexibility
- The second uses a 2D array which is more efficient for fixed 26 teams
2.Sorting Logic:
- We compare teams by their vote counts at each position
- If all positions have equal votes, we use alphabetical order
- The comparator returns negative, zero, or positive based on comparison rules
3.Edge Cases:
- Single vote: just return that vote
- All votes the same: ranking will be consistent
- Empty votes: return empty string
Example Walkthrough
Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
1.Count votes:
- A: position 0: 5 votes (always first)
- B: position 1: 2 votes, position 2: 3 votes
- C: position 1: 3 votes, position 2: 2 votes
2.Compare B vs C:
- Both have 0 first-place votes
- Second place: C has 3, B has 2 → C ranks higher
- Result: "ACB"
This solution efficiently handles the ranking logic with custom sorting.


Refer to QWEN
Here's an efficient and well-commented Java solution for LeetCode 1366: "Rank Teams by Votes":
import java.util.*;

class Solution {
    public String rankTeams(String[] votes) {
        // Edge case: no votes or empty first vote
        if (votes == null || votes.length == 0 || votes[0].isEmpty()) {
            return "";
        }
        
        // Number of teams (length of any vote string)
        int numTeams = votes[0].length();
        
        // Map: team character -> array of vote counts per position
        // Example: voteCounts['A'][0] = number of 1st-place votes for team A
        Map<Character, int[]> voteCounts = new HashMap<>();
        
        // Initialize vote count arrays for each team
        for (char team : votes[0].toCharArray()) {
            voteCounts.put(team, new int[numTeams]);
        }
        
        // Count votes for each position
        for (String vote : votes) {
            for (int pos = 0; pos < numTeams; pos++) {
                char team = vote.charAt(pos);
                voteCounts.get(team)[pos]++;
            }
        }
        
        // Prepare list of teams for sorting
        List<Character> teams = new ArrayList<>(voteCounts.keySet());
        
        // Custom comparator to rank teams:
        // 1. Higher vote count at position i comes first (descending)
        // 2. If all positions tie, sort alphabetically (ascending)
        teams.sort((a, b) -> {
            int[] countsA = voteCounts.get(a);
            int[] countsB = voteCounts.get(b);
            
            for (int i = 0; i < numTeams; i++) {
                if (countsA[i] != countsB[i]) {
                    return countsB[i] - countsA[i]; // descending order
                }
            }
            return a - b; // alphabetical tiebreaker
        });
        
        // Build final ranking string
        StringBuilder result = new StringBuilder();
        for (char team : teams) {
            result.append(team);
        }
        return result.toString();
    }
}
Key Points:
1.Vote Tracking: Uses a Map<Character, int[]> where each team maps to an array counting votes received at each rank position.
2.Sorting Logic:
- Primary: Compare vote counts from 1st position onward (higher count = better rank)
- Tiebreaker: Alphabetical order if all vote counts are identical
3.Edge Cases Handled:
- Empty input → returns empty string
- Single vote → returns the vote string directly
- All teams tied → sorts alphabetically
Complexity Analysis:
- Time Complexity: O(N·M + M²·log M)
- N = number of votes, M = number of teams
- Counting votes: O(N·M)
- Sorting teams: O(M·log M) comparisons × O(M) per comparison = O(M²·log M)
- Space Complexity: O(M²) for storing vote counts (M teams × M positions)
Example:
Input: votes = ["ABC","ACB","ABC","ACB","ACB"]
Output: "ACB"

Explanation:
- Team A: [5, 0, 0] → 5 first-place votes
- Team B: [0, 2, 3] → 2 second-place, 3 third-place
- Team C: [0, 3, 2] → 3 second-place, 2 third-place
Ranking: A (most 1st-place) → C (more 2nd-place than B) → B
This solution efficiently implements the ranking rules specified in the problem and handles all edge cases correctly.

Refer to
L911.Online Election (Ref.L1366)
