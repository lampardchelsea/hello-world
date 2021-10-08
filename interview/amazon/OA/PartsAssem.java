import laioffer.ListNode;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

{public class PartsAssem {

    public static int Solution(LinkedList<Integer> list) {
        if (list == null) {
            return -1;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int i = 0; i < list.size(); i++) {
            queue.add(list.get(i));
        }
        int count = 0;
        while(queue.size() > 1) {
            int Smallest = queue.poll();
            int secSmallest = queue.poll();
            int temp = Smallest+ secSmallest;
            count += temp;
            queue.add(temp);
        }
        return count;
    }
}