/**
 * Given a pattern and a string str, find if str follows the same pattern.
 * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.
 * Examples:
 * pattern = "abba", str = "dog cat cat dog" should return true.
 * pattern = "abba", str = "dog cat cat fish" should return false.
 * pattern = "aaaa", str = "dog cat cat dog" should return false.
 * pattern = "abba", str = "dog dog dog dog" should return false.
 * Notes:
 * You may assume pattern contains only lowercase letters, and str contains lowercase letters separated by a single space.
 */

// Wrong way
public class Solution {
   public boolean wordPattern(String pattern, String str) {
      String[] a = str.split("\\s+");
      
      if(a.length != pattern.length()) {
         return false;
      }
      
      Map<Character, String> map = new HashMap<Character, String>();
      
      for(int i = 0; i < pattern.length(); i++) {
          if(!map.containsKey(pattern.charAt(i))) {
              // The wrong condition is here, if pattern = "abba", 
              // str = "dog dog dog dog", when put ('b', "dog") on
              // map, it will surely return null, but "dog" is
              // occupied by another projection ('a', "dog"), the
              // right expression should reflect miss projection
              // such as map.containsValue(a[i]) in solution 1
              if(map.put(pattern.charAt(i), a[i]) != null) {
                  return false;
              }
          } else {
              if(!map.put(pattern.charAt(i), a[i]).equals(a[i])) {
                  return false;
              }
          }
      }
      
      return true;
   }
}


// Solution 1:
// Refer to
// https://segmentfault.com/a/1190000003827151
// https://my.oschina.net/Tsybius2014/blog/514983
public class Solution {
   public boolean wordPattern(String pattern, String str) {
      String[] a = str.split("\\s+");
      
      // If pattern length not match string sections array length,
      // directly return false
      if(a.length != pattern.length()) {
         return false;
      }
      
      Map<Character, String> map = new HashMap<Character, String>();
      for(int i = 0; i < pattern.length(); i++) {
         // If not contains current key
         if(!map.containsKey(pattern.charAt(i))) {
             // But already contains the projection value of this key,
             // which means the value match another key not relate as
             // one on one projection. 
             // E.g pattern = "abba", str = "dog dog dog dog",
             // First putting ('a', "dog") on map, key = 'b' not contain 
             // in map now, when we try to put ('b', "dog") on map, we
             // find "dog" already on map and is projection of 'a', this
             // violate one(key) on one(value) projection, so return false
             if(map.containsValue(a[i])) {
                 return false;
             }
             // Otherwise put this key-value pair onto map
             map.put(pattern.charAt(i), a[i]);
         } else {
             // If already contains current key
             // But the key's projection not match the expected key,
             // E.g pattern = "abba", str = "dog cat cat fish",
             // First putting ('a', "dog") on map, projection of key = 'a'
             // should be "dog", but when try to put ('a', "fish") on map,
             // "fish" violate one(key) on one(value) projection, so return false
             if(!map.get(pattern.charAt(i)).equals(a[i])) {
                 return false;
             }
         }
      }
      return true;
   }
}

// Solution 2:
// Refer to
// https://my.oschina.net/Tsybius2014/blog/514983
public class Solution {
   public boolean wordPattern(String pattern, String str) {
      String[] a = str.split("\\s+");
      
      if(a.length != pattern.length()) {
         return false;
      }
      
      Map<Comparable, Integer> map = new HashMap<Comparable, Integer>();
      
      for(int i = 0; i < pattern.length(); i++) {
        Integer x = map.put(pattern.charAt(i), i);
        Integer y = map.put(a[i], i);
        /**
         * Returns true if the arguments are equal to each other and false otherwise. 
         * Consequently, if both arguments are null, true is returned and if exactly 
         * one argument is null, false is returned. Otherwise, equality is determined 
         * by using the equals method of the first argument. 
         * 
         *  public static boolean More ...equals(Object a, Object b) {
         *     return (a == b) || (a != null && a.equals(b));
         *  }
         */
        if(!Objects.equals(x, y)) {
            return false;
        }
        /** 
         * The above Objects.equals(x, y) equals to below section
         if(x == null && y == null) {
           // Be careful when x == null && y == null, we don't directly return true,
           // as we need looply compare other x, y pair based on for loop, so we
           // use continue
	        continue;
	      }
         if((x == null || y == null) || !x.equals(y)) {
            return false;
         }
        */
      }
      
      return true;
   }
}
