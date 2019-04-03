package test9;

import java.util.ArrayList;
import java.util.List;

public class Post {
	public static void main(String[] args) throws Exception {
////		String s = " to be or not to be";
//////		String s = " \"s\" ";
////		System.out.println(Chapter19.stutter(s));
//		Integer three = new Integer(3); 
//		Integer seven = new Integer(7);
//		three.compareTo(seven);
		String s = "Ab,c1,de!$";
		System.out.println(reverseVowels(s));
		
		StoreManagement storeManagement = StoreManagement.getInstance();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		String result = storeManagement.getOverExpirationDateMerchandiseDetails(list);
		System.out.println(result);
	}
	
	public static String reverseVowels(String s) {
        int length = s.length();
        int i = 0;
        int j = length - 1;
        char[] chars = s.toCharArray();
        while(i < j) {
            if(isVowel(chars[i]) && isVowel(chars[j])) {
                char temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
                i++;
                j--;
            } else if(isVowel(chars[i]) && !isVowel(chars[j])) {
                j--;
            } else if(!isVowel(chars[i]) && isVowel(chars[j])) {
                i++;
            } else {
                i++;
                j--;
            }
        }
        return new String(chars);
    }
    
    public static boolean isVowel(char c) {
//        if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' 
//        		|| c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
//            return true;
//        }
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
            return true;
        }
        return false;
    }
}
