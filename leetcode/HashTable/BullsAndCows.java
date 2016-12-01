/**
 * You are playing the following Bulls and Cows game with your friend: You write down a number and 
 * ask your friend to guess what the number is. Each time your friend makes a guess, you provide 
 * a hint that indicates how many digits in said guess match your secret number exactly in both digit 
 * and position (called "bulls") and how many digits match the secret number but locate in the wrong 
 * position (called "cows"). Your friend will use successive guesses and hints to eventually derive 
 * the secret number.
 * For example:
 * Secret number:  "1807"
 * Friend's guess: "7810"
 * Hint: 1 bull and 3 cows. (The bull is 8, the cows are 0, 1 and 7.)
 * Write a function to return a hint according to the secret number and friend's guess, 
 * use A to indicate the bulls and B to indicate the cows. In the above example, your 
 * function should return "1A3B".
 * Please note that both secret number and friend's guess may contain duplicate digits, for example:
 * Secret number:  "1123"
 * Friend's guess: "0111"
 * In this case, the 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow, and your function should return "1A1B".
 * You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.
*/
// Solution 1:
// Refer to
// http://www.programcreek.com/2014/05/leetcode-bulls-and-cows-java/
public class Solution {
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        
        for(int i = 0; i < secret.length(); i++) {
            char a = secret.charAt(i);
            char b = guess.charAt(i);
            if(a == b) {
                bulls++;
            } else {
                // First filter out all digits as bulls, the remind digits
                // which NOT bulls but candidates for cows will be added
                // onto map, e.g secret = "11", bulls = "10", the first
                // digit of both strings are equal as '1', which is bull,
                // then remind part of secret is '1' will be added onto 
                // map as an dictionary for scanning String guess.
                if(map.containsKey(a)) {
                    map.put(a, map.get(a) + 1);
                } else {
                    map.put(a, 1);
                }
            }
        }
        
        for(int j = 0; j < secret.length(); j++) {
        	  char c = secret.charAt(j);
            char d = guess.charAt(j);
            // Also need to add condition as c != d check, as we still
            // scan String guess from start, which may repeat previous
            // same character at same position as String secret.
            // E.g secret = "11", guess = "10", when re-loop, the first
            // '1' on both strings if not discard will calculate again.
            if(c != d) {
            	if(map.containsKey(d)) {
                    cows++;
                    if(map.get(d) == 1) {
                        map.remove(d);
                    } else {
                        map.put(d, map.get(d) - 1);
                    }
                }
            }   
        }
        
        return bulls + "A" + cows + "B";
    }
}
