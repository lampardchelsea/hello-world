import java.util.*;

public class PrimeSort {
    public String[] Solution(String[] logs) {
        if(logs == null || logs.length == 0) {
            return logs;
        }
        int len = logs.length;
        List<String> letterList = new ArrayList<>();
        List<String> digitList = new ArrayList<>();
        for (String log : logs) {
            boolean isDig = true;
            int sdx = log.indexOf(" ");
            String orderNum = log.substring(0, sdx);
            for(int i = 0; i < orderNum.length(); i++) {
                if(!Character.isDigit(orderNum.charAt(i))) {
                    isDig = false;
                }
            }
            if(isDig) {
                digitList.add(log);
            } else {
                letterList.add(log);
            }
        }
        Collections.sort(letterList, (o1, o2) -> {
            int sdx1 = o1.indexOf(' ');
            int sdx2 = o2.indexOf(' ');
            int com = o1.substring(sdx1 + 1).compareTo(o2.substring(sdx2 + 1));
            if(com != 0) {
                return com;
            } else {
                return o1.substring(0, sdx1).compareTo(o2.substring(1, sdx2));
            }
        });
        for(int i = 0; i < len; i++){
            if(i < letterList.size()) {
                logs[i] = letterList.get(i);
            } else {
                logs[i] = digitList.get(i - letterList.size());
            }
        }
        return logs;
    }
}