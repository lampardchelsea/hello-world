import java.util.*;

public class ReorderLogFiles {

    public String[] reorderLogFiles(String[] logs) {
        if(logs == null || logs.length == 0) {
            return logs;
        }
        int len = logs.length;
        List<String> letterList = new ArrayList<>();
        List<String> digitList = new ArrayList<>();
        for (String log : logs) {
            if(log.split(" ")[1].charAt(0) < 'a') {
                digitList.add(log);
            } else {
                letterList.add(log);
            }
        }
        Collections.sort(letterList, (o1, o2) -> {
            String[] s1 = o1.split(" ", 2);
            String[] s2 = o2.split(" ", 2);
            int com = s1[1].compareTo(s2[1]);
            if(com != 0) {
                return com;
            } else {
                return s1[0].compareTo(s2[0]);
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





    public String[] Solution(String[] logs) {
        Arrays.sort(logs, (o1, o2) -> {
           String[] s1 = o1.split(" ", 2);
           String[] s2 = o2.split(" ", 2);
           boolean isDigit1 = Character.isDigit(s1[1].charAt(0));
           boolean isDigit2 = Character.isDigit(s2[1].charAt(0));
           if(!isDigit1 && !isDigit2) {
               int com = s1[1].compareTo(s2[1]);
               if(com != 0) {
                   return com;
               } else {
                   return s1[0].compareTo(s2[0]);
               }
           }
           return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
        });
        return logs;
    }


    //indexOf and substring


    public String[] Solution1(String[] logs) {
        Arrays.sort(logs,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int s1si = o1.indexOf(' ');
                int s2si = o2.indexOf(' ');
                char s1fc = o1.charAt(s1si + 1);
                char s2fc = o2.charAt(s2si + 1);
                if(s1fc <= '9') {
                    if(s2fc <= '9') {
                        return 0;
                    } else {
                        return 1;
                    }
                }
                if(s2fc <= '9') {
                    return -1;
                }
                int com = o1.substring(s1si + 1).compareTo(o2.substring(s2si + 1));
                if(com == 0) {
                    return o1.substring(0, s1si).compareTo(o2.substring(0, s2si));
                }
                return com;
            }
        });
        return logs;
    }

}