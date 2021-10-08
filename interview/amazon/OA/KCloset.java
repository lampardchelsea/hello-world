import java.util.*;

public class kClosest {
    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int dist1 = o1[0] * o1[0] + o1[1] * o1[1];
                int dist2 = o2[0] * o2[0] + o2[1] * o2[1];
                if (dist1 == dist2) {
                    return 0;
                }
                return dist1 > dist2 ? 1 : -1;
            }
        });
        for(int i = 0; i < points.length; i++) {
            pq.offer(points[i]);
        }
        int[][] res = new int[K][2];
        for(int j = 0; j < K; j++) {
            int[] temp = pq.poll();
            for(int s = 0; s < 2; s++) {
                res[j][s] = temp[s];
            }
        }
        return res;
    }


    public static List<List<Integer>> Solution(List<List<Integer>> points, int k) {
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>(new Comparator<List<Integer>>(){
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                int dist1 = o1.get(0) * o1.get(0) + o1.get(1) * o1.get(1);
                int dist2 = o2.get(0) * o2.get(0) + o2.get(1) * o2.get(1);
                if(dist1 == dist2) {
                    return 0;
                }
                return dist1 > dist2 ? 1 : -1;
            }
        });
        for(List<Integer> list : points) {
            pq.offer(list);
        }
        List<List<Integer>> res = new ArrayList<>();
        for(int j = 0; j < k; j++) {
            List<Integer> temp = pq.poll();
            res.add(temp);
        }
        return res;
    }
}