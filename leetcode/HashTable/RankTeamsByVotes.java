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
