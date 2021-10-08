import java.util.*;

public class TwoSumLeast {
    public List<List<Integer>> Solution(List<List<Integer>> forwardShippingRout, List<List<Integer>> backwardShippingRout, int maxTravelTime) {
        List<List<Integer>> res = new ArrayList<>();
        //int len1 = forwardShippingRout.size();
        //int len2 = backwardShippingRout.size();
        //PriorityQueue<Travel> pq = new PriorityQueue<>((o1, o2) -> (
        //        Integer.compare(o1.traveltime, o2.traveltime)
        //));
        HashMap<Integer, List<List<Integer>>> map = new HashMap<>();
        int globalMax = 0;
        for(List<Integer> forward : forwardShippingRout) {
            for(List<Integer> backward : backwardShippingRout) {
                List<Integer> pair = new ArrayList<>();
                pair.add(forward.get(0));
                pair.add(backward.get(0));
                int time = forward.get(1) + backward.get(1);
                if(time <= maxTravelTime) {
                    //如何直接输出和maxTravelTime的list
                    globalMax = Math.max(globalMax, time);
                    if(map.containsKey(time)) {
                        map.get(time).add(pair);
                    } else {
                        List<List<Integer>> temp = new ArrayList<>();
                        temp.add(pair);
                        map.put(time, temp);
                    }
                }
            }
        }
        if(map.isEmpty()) {
            return res;
        } else {
            return map.get(globalMax);
        }
    }
}

class Travel{
    List<Integer> route;
    int traveltime;
    public Travel(List<Integer> route, int traveltime){
        this.route = route;
        this.traveltime = traveltime;
    }
}