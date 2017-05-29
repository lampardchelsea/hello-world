public class Solution {
    public String reverseWords(String s) {
        String[] array = s.split(" ");
        if(array.length == 0) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < array.length; i++) {
            sb.append(reverse(array[i]));
            if(i < array.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    
    public String reverse(String tmp) {
        char[] c = tmp.toCharArray();
        int left = 0;
        int right = c.length - 1;
        while(left < right) {
            swap(c, left, right);
            left++;
            right--;
        }
        return String.valueOf(c);
    }
    
    public void swap(char[] c, int left, int right) {
        char temp = c[left];
        c[left] = c[right];
        c[right] = temp;
    }
}
