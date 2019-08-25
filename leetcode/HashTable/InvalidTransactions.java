/**
 Refer to
 https://leetcode.com/problems/invalid-transactions/
 A transaction is possibly invalid if:

the amount exceeds $1000, or;
if it occurs within (and including) 60 minutes of another transaction with the same name in a different city.
Each transaction string transactions[i] consists of comma separated values representing the name, time (in minutes), 
amount, and city of the transaction.

Given a list of transactions, return a list of transactions that are possibly invalid.  You may return the answer in any order.

Example 1:
Input: transactions = ["alice,20,800,mtv","alice,50,100,beijing"]
Output: ["alice,20,800,mtv","alice,50,100,beijing"]
Explanation: The first transaction is invalid because the second transaction occurs within a difference of 60 minutes, 
have the same name and is in a different city. Similarly the second one is invalid too.

Example 2:
Input: transactions = ["alice,20,800,mtv","alice,50,1200,mtv"]
Output: ["alice,50,1200,mtv"]

Example 3:
Input: transactions = ["alice,20,800,mtv","bob,50,1200,mtv"]
Output: ["bob,50,1200,mtv"]

Constraints:
transactions.length <= 1000
Each transactions[i] takes the form "{name},{time},{amount},{city}"
Each {name} and {city} consist of lowercase English letters, and have lengths between 1 and 10.
Each {time} consist of digits, and represent an integer between 0 and 1000.
Each {amount} consist of digits, and represent an integer between 0 and 2000.
*/

// Solution 1: HashMap + PQ but with O(n^2) brute force scan
class Solution {
    public List<String> invalidTransactions(String[] transactions) {
        Set<String> result = new HashSet<String>();
        if(transactions.length == 0) {
            return new ArrayList<String>(result);
        }
        Map<String, List<Token>> map = new HashMap<String, List<Token>>();
        PriorityQueue<Token> pq = new PriorityQueue<Token>((a, b) -> a.time - b.time);
        for(String transaction : transactions) {
            String[] slips = transaction.split(",");
            Token t = new Token(slips[0], Integer.valueOf(slips[1]), Integer.valueOf(slips[2]), slips[3], transaction);
            if(!map.containsKey(t.name)) {
                map.put(t.name, new ArrayList<Token>());
            }
            map.get(t.name).add(t);
        }
        for(Map.Entry<String, List<Token>> entry : map.entrySet()) {
            List<Token> list = entry.getValue();
            for(Token token : list) {
                pq.add(token);
            }
            List<Token> list1 = new ArrayList<Token>();
            while(!pq.isEmpty()) {
            	list1.add(pq.poll());
            }        
            for(int i = 0; i < list1.size(); i++) {
            	Token t = list1.get(i);
            	if(t.amount > 1000) {
            		result.add(t.str);
            	}
            	for(int j = i + 1; j < list1.size(); j++) {
            		Token t1 = list1.get(j);
            		if(t1.time <= t.time + 60 && !t.city.equals(t1.city)) {
            			if(!result.contains(t.str)) {
            				result.add(t.str);
            			}
            			if(!result.contains(t1.str)) {
            				result.add(t1.str);
            			}
            		}
            	}
            }
        }      
        return new ArrayList<String>(result);
    }
    
    class Token {
        String name;
        int time;
        int amount;
        String city;
        String str;
        public Token(String name, int time, int amount, String city, String str) {
            this.name = name;
            this.time = time;
            this.amount = amount;
            this.city = city;
            this.str = str;
        }
    }
}

// Wrong solution
// Can be test out by below test case:
/**
 Input:
 ["bob,649,842,prague","alex,175,1127,mexico","iris,164,119,paris","lee,991,1570,mexico","lee,895,1876,taipei","iris,716,754,moscow","chalicefy,19,592,singapore","chalicefy,820,71,newdelhi","maybe,231,1790,paris","lee,158,987,mexico","chalicefy,415,22,montreal","iris,803,691,milan","xnova,786,804,guangzhou","lee,734,1915,prague","bob,836,1904,dubai","iris,666,231,chicago","iris,677,1451,milan","maybe,860,517,toronto","iris,344,1452,bangkok","lee,664,463,frankfurt","chalicefy,95,1222,montreal","lee,293,1102,istanbul","maybe,874,36,hongkong","maybe,457,1802,montreal","xnova,535,270,munich","iris,39,264,istanbul","chalicefy,548,363,barcelona","lee,373,184,munich","xnova,405,957,mexico","chalicefy,517,266,luxembourg","iris,25,657,singapore","bob,688,451,beijing","bob,263,1258,tokyo","maybe,140,222,amsterdam","xnova,852,330,barcelona","xnova,589,837,budapest","lee,152,981,mexico","alex,893,1976,shenzhen","xnova,560,825,prague","chalicefy,283,399,zurich","iris,967,1119,guangzhou","alex,924,223,milan","chalicefy,212,1865,chicago","alex,443,537,taipei","maybe,390,5,shanghai","bob,510,1923,madrid","bob,798,343,hongkong","iris,643,1703,madrid","bob,478,928,barcelona","maybe,75,1980,shanghai","xnova,293,24,newdelhi","iris,176,268,milan","alex,783,81,moscow","maybe,560,587,milan","alex,406,776,istanbul","lee,558,727,paris","maybe,481,1504,munich","maybe,685,602,madrid","iris,678,788,madrid","xnova,704,274,newdelhi","chalicefy,36,1984,paris","iris,749,200,amsterdam","lee,21,119,taipei","iris,406,433,bangkok","bob,777,542,taipei","maybe,230,1434,barcelona","iris,420,1818,zurich","lee,622,194,amsterdam","maybe,545,608,shanghai","xnova,201,1375,madrid","lee,432,520,dubai","bob,150,1634,singapore","maybe,467,1178,munich","iris,45,904,beijing","maybe,607,1953,tokyo","bob,901,815,tokyo","maybe,636,558,milan","bob,568,1674,toronto","iris,825,484,madrid","iris,951,930,dubai","bob,465,1080,taipei","bob,337,593,chicago","chalicefy,16,176,rome","chalicefy,671,583,singapore","iris,268,391,chicago","xnova,836,153,jakarta","bob,436,530,warsaw","alex,354,1328,luxembourg","iris,928,1565,paris","xnova,627,834,budapest","xnova,640,513,jakarta","alex,119,16,toronto","xnova,443,1687,taipei","chalicefy,867,1520,montreal","alex,456,889,newdelhi","lee,166,3,madrid","bob,65,1559,zurich","alex,628,861,moscow","maybe,668,572,mexico","bob,402,922,montreal"]
 Wrong solution output:
 ["chalicefy,16,176,rome","chalicefy,19,592,singapore","chalicefy,36,1984,paris","chalicefy,95,1222,montreal","chalicefy,212,1865,chicago","chalicefy,517,266,luxembourg","chalicefy,548,363,barcelona","chalicefy,820,71,newdelhi","chalicefy,867,1520,montreal","iris,25,657,singapore","iris,39,264,istanbul","iris,45,904,beijing","iris,164,119,paris","iris,176,268,milan","iris,344,1452,bangkok","iris,406,433,bangkok","iris,420,1818,zurich","iris,643,1703,madrid","iris,666,231,chicago","iris,677,1451,milan","iris,678,788,madrid","iris,716,754,moscow","iris,749,200,amsterdam","iris,803,691,milan","iris,825,484,madrid","iris,928,1565,paris","iris,951,930,dubai","iris,967,1119,guangzhou","alex,119,16,toronto","alex,175,1127,mexico","alex,354,1328,luxembourg","alex,406,776,istanbul","alex,443,537,taipei","alex,456,889,newdelhi","alex,893,1976,shenzhen","alex,924,223,milan","bob,65,1559,zurich","bob,150,1634,singapore","bob,263,1258,tokyo","bob,402,922,montreal","bob,436,530,warsaw","bob,465,1080,taipei","bob,478,928,barcelona","bob,510,1923,madrid","bob,568,1674,toronto","bob,649,842,prague","bob,688,451,beijing","bob,777,542,taipei","bob,798,343,hongkong","bob,836,1904,dubai","maybe,75,1980,shanghai","maybe,230,1434,barcelona","maybe,231,1790,paris","maybe,457,1802,montreal","maybe,467,1178,munich","maybe,481,1504,munich","maybe,545,608,shanghai","maybe,560,587,milan","maybe,607,1953,tokyo","maybe,636,558,milan","maybe,668,572,mexico","maybe,685,602,madrid","maybe,860,517,toronto","maybe,874,36,hongkong","xnova,201,1375,madrid","xnova,405,957,mexico","xnova,443,1687,taipei","xnova,535,270,munich","xnova,560,825,prague","xnova,589,837,budapest","xnova,627,834,budapest","xnova,640,513,jakarta","xnova,786,804,guangzhou","xnova,836,153,jakarta","xnova,852,330,barcelona","lee,158,987,mexico","lee,166,3,madrid","lee,293,1102,istanbul","lee,373,184,munich","lee,432,520,dubai","lee,622,194,amsterdam","lee,664,463,frankfurt","lee,734,1915,prague","lee,895,1876,taipei","lee,991,1570,mexico"]
 Expected:
 ["bob,649,842,prague","alex,175,1127,mexico","iris,164,119,paris","lee,991,1570,mexico","lee,895,1876,taipei","iris,716,754,moscow","chalicefy,19,592,singapore","chalicefy,820,71,newdelhi","maybe,231,1790,paris","lee,158,987,mexico","iris,803,691,milan","xnova,786,804,guangzhou","lee,734,1915,prague","bob,836,1904,dubai","iris,666,231,chicago","iris,677,1451,milan","maybe,860,517,toronto","iris,344,1452,bangkok","lee,664,463,frankfurt","chalicefy,95,1222,montreal","lee,293,1102,istanbul","maybe,874,36,hongkong","maybe,457,1802,montreal","xnova,535,270,munich","iris,39,264,istanbul","chalicefy,548,363,barcelona","lee,373,184,munich","xnova,405,957,mexico","chalicefy,517,266,luxembourg","iris,25,657,singapore","bob,688,451,beijing","bob,263,1258,tokyo","xnova,852,330,barcelona","xnova,589,837,budapest","lee,152,981,mexico","alex,893,1976,shenzhen","xnova,560,825,prague","iris,967,1119,guangzhou","alex,924,223,milan","chalicefy,212,1865,chicago","alex,443,537,taipei","bob,510,1923,madrid","bob,798,343,hongkong","iris,643,1703,madrid","bob,478,928,barcelona","maybe,75,1980,shanghai","iris,176,268,milan","maybe,560,587,milan","alex,406,776,istanbul","maybe,481,1504,munich","maybe,685,602,madrid","iris,678,788,madrid","chalicefy,36,1984,paris","iris,749,200,amsterdam","iris,406,433,bangkok","bob,777,542,taipei","maybe,230,1434,barcelona","iris,420,1818,zurich","lee,622,194,amsterdam","maybe,545,608,shanghai","xnova,201,1375,madrid","lee,432,520,dubai","bob,150,1634,singapore","maybe,467,1178,munich","iris,45,904,beijing","maybe,607,1953,tokyo","maybe,636,558,milan","bob,568,1674,toronto","iris,825,484,madrid","iris,951,930,dubai","bob,465,1080,taipei","chalicefy,16,176,rome","xnova,836,153,jakarta","bob,436,530,warsaw","alex,354,1328,luxembourg","iris,928,1565,paris","xnova,627,834,budapest","xnova,640,513,jakarta","alex,119,16,toronto","xnova,443,1687,taipei","chalicefy,867,1520,montreal","alex,456,889,newdelhi","lee,166,3,madrid","bob,65,1559,zurich","maybe,668,572,mexico","bob,402,922,montreal"]
 The difference is:
 "lee,152,981,mexico"
 
 Why ?
 Because when key name = lee, there has 3 initial tokens as "lee,152,981,mexico", "lee,158,987,mexico", "lee,166,3,madrid",
 If only use iterator on update pre token with cur token, when move out "lee,152,981,mexico", only "lee,158,987,mexico", "lee,166,3,madrid"
 will stay on PQ, yes, you will catch "lee,158,987,mexico", but you will miss "lee,152,981,mexico", since "lee,152,981,mexico"
 has same city as "lee,158,987,mexico", but different city as "lee,166,3,madrid", but time range still less than 60
*/
class Solution {
    public List<String> invalidTransactions(String[] transactions) {
        List<String> result = new ArrayList<String>();
        if(transactions.length == 0) {
            return result;
        }
        Map<String, List<Token>> map = new HashMap<String, List<Token>>();
        PriorityQueue<Token> pq = new PriorityQueue<Token>((a, b) -> a.time - b.time);
        for(String transaction : transactions) {
            String[] slips = transaction.split(",");
            Token t = new Token(slips[0], Integer.valueOf(slips[1]), Integer.valueOf(slips[2]), slips[3], transaction);
            if(!map.containsKey(t.name)) {
                map.put(t.name, new ArrayList<Token>());
            }
            map.get(t.name).add(t);
        }
        for(Map.Entry<String, List<Token>> entry : map.entrySet()) {
            List<Token> list = entry.getValue();
            for(Token token : list) {
                pq.add(token);
            }
            Token pre = pq.poll();
            if(pre.amount > 1000) {
                result.add(pre.str);
            }
            while(!pq.isEmpty()) {
                Token cur = pq.peek();
                if((cur.time - pre.time <= 60 && !cur.city.equals(pre.city))) {
                    if(!result.contains(pre.str)) {
                        result.add(pre.str);                    
                    }
                    if(!result.contains(cur.str)) {
                        result.add(cur.str);                    
                    }
                }
                if(cur.amount > 1000) {
                    if(!result.contains(cur.str)) {
                        result.add(cur.str);
                    }
                }
                pre = pq.poll();
            }
        }      
        return result;
    }
    
    class Token {
        String name;
        int time;
        int amount;
        String city;
        String str;
        public Token(String name, int time, int amount, String city, String str) {
            this.name = name;
            this.time = time;
            this.amount = amount;
            this.city = city;
            this.str = str;
        }
    }
}
