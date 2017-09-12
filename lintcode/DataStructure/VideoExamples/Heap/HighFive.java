
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-613-high-five.html
 * Description
   There are two properties in the node student id and scores, to ensure that each student 
   will have at least 5 points, find the average of 5 highest scores for each person.
   
	Example
	Given results = [[1,91],[1,92],[2,93],[2,99],[2,98],[2,97],[1,60],[1,58],[2,100],[1,61]]
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-613-high-five.html
 * 思路
	根据题意，对于每一个id，我们维护一个大小为K的min-heap。一个一个把Record放进去，如果容量超了，就把最小的踢掉。这样heap里永远是最大的K个。
	全部放完以后，对于每一个id，我们把heap里的Record拿出来算一下平均数
   https://discuss.leetcode.com/topic/61493/five-score-average/2
	Java based solution: Create a Map<Integer, PriorityQueue<Integer>> scoreMap, parse the list 
	and put each student's scores in their corresponding list. 
	Then create a Map<Integer, Double> resultMap, iterate over scoreMap and for each key fetch 
	top 5 scores, take the average, populate in resultMap and return resultMap.
 */

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
public class HighFive {
    /**
     * @param results a list of <student_id, score>
     * @return find the average of 5 highest scores for each person
     * Map<Integer, Double> (student_id, average_score)
     */
    private class Record {
    	public int id, score;
    	public Record(int id, int score) {
    		this.id = id;
    		this.score = score;
    	}
    }
    
    public Map<Integer, Double> highFive(Record[] results) {
    	Map<Integer, Double> result = new HashMap<Integer, Double>();
    	
    	// Create a Map<Integer, PriorityQueue<Integer>> scoreMap, parse the list 
    	// and put each student's scores in their corresponding list.
		int k = 5;
    	Map<Integer, PriorityQueue<Record>> scoresMap = new HashMap<Integer, PriorityQueue<Record>>();
    	for(Record record : results) {
    		if(scoresMap.containsKey(record.id)) {
    			PriorityQueue<Record> pq = scoresMap.get(record.id);
    			pq.add(record);
    			// If over required size k, based on min heap, poll out minimum one(1st position)
    			if(pq.size() > k) {
    				pq.poll();
    			}
    		} else {
    			// Create min-heap with initial size as k = 5 (Caution, k is not the limited size,
    			// when we put in more, that's why can first put in new value and then poll out
    			// redundant minimum one)
    			PriorityQueue<Record> pq = new PriorityQueue<Record>(k, new Comparator<Record>() {
    				// min-heap comparator
    				public int compare(Record a, Record b) {
    					if(a.score > b.score) {
    						return 1;
    					} else if(a.score < b.score) {
    						return -1;
    					} else {
    						return 0;
    					}
    				}
    			});
    			pq.add(record);
    			scoresMap.put(record.id, pq);
    		}
    	}
    	
    	// Then create a Map<Integer, Double> resultMap, iterate over scoreMap and for each key 
    	// fetch top 5 scores, take the average, populate in resultMap and return resultMap.
    	for(Map.Entry<Integer, PriorityQueue<Record>> entry : scoresMap.entrySet()) {
    		int id = entry.getKey();
    		PriorityQueue<Record> pq = entry.getValue();
    		double average = 0;
    		int num = pq.size();
    		while(!pq.isEmpty()) {
    			average += pq.poll().score;
    		}
    		average /= num;
    		result.put(id, average);
    	}
    	
    	return result;
    }
    
    public static void main(String[] args) {
    	HighFive h = new HighFive();
    	Record one = h.new Record(1, 1);
    	Record two = h.new Record(1, 2);
    	Record three = h.new Record(1, 3);
    	Record four = h.new Record(1, 4);
    	Record five = h.new Record(1, 10);
    	Record[] records = {one, two, three, four, five};
    	Map<Integer, Double> map = h.highFive(records);
    	for(Map.Entry<Integer, Double> entry : map.entrySet()) {
    		System.out.println(entry.getKey() + " " + entry.getValue());
    	}
    }
}
