/**
Refer to
https://code.dennyzhang.com/high-five
Given a list of scores of different students, return the average score of each student's top five 
scores in the order of each student's id.
Each entry items[i] has items[i][0] the student's id, and items[i][1] the student's score.  
The average score is calculated using integer division.

Example 1:
Input: [[1,91],[1,92],[2,93],[2,97],[1,60],[2,77],[1,65],[1,87],[1,100],[2,100],[2,76]]
Output: [[1,87],[2,88]]
Explanation: 
The average of the student with id = 1 is 87.
The average of the student with id = 2 is 88.6. But with integer division their average converts to 88.

Note:
1 <= items.length <= 1000
items[i].length == 2
The IDs of the students is between 1 to 1000
The score of the students is between 1 to 100
For each student, there are at least 5 scores
*/

// Solution 1: HashMap + PriorityQueue
// Refer to
// http://buttercola.blogspot.com/2019/05/lintcode-613-high-five.html
/**
 * Definition for a Record
 * class Record {
 *     public int id, score;
 *     public Record(int id, int score){
 *         this.id = id;
 *         this.score = score;
 *     }
 * }
 */
public class Solution {
    /**
     * @param results a list of <student_id, score>
     * @return find the average of 5 highest scores for each person
     * Map<Integer, Double> (student_id, average_score)
     */
    public Map<Integer, Double> highFive(Record[] results) {
        // Write your code here
        if (results == null || results.length == 0) {
            return new HashMap<Integer, Double>();
        }
         
        Map<Integer, PriorityQueue<Integer>> studentToScoreMap = new HashMap<>();
        for (Record record : results) {
            PriorityQueue<Integer> pq = studentToScoreMap.getOrDefault(record.id, new PriorityQueue<>());
            pq.offer(record.score);
            if (pq.size() > 5) {
                pq.poll();
            }
            studentToScoreMap.put(record.id, pq);
        }
         
        Map<Integer, Double> ans = new HashMap<>();
        for (Integer id : studentToScoreMap.keySet()) {
            PriorityQueue<Integer> scores = studentToScoreMap.get(id);
            double ave = getAverageScore(scores);
            ans.put(id, ave);
        }
         
        return ans;
    }
     
    private double getAverageScore(PriorityQueue<Integer> scores) {
        int sum = 0;
        int numScores = scores.size();
        while (!scores.isEmpty()) {
            sum += scores.poll();
        }
         
        return (double) sum / numScores;
    }
}
