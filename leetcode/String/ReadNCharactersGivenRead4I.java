/**
 * The API: int read4(char *buf) reads 4 characters at a time from a file.
 * The return value is the actual number of characters read. For example, 
 * it returns 3 if there is only 3 characters left in the file.
 * 
 * By using the read4 API, implement the function int read(char *buf, int n) 
 * that reads n characters from the file.
 * 
 * Note: The read function may be called multiple times.
 */
// Refer to
// https://discuss.leetcode.com/topic/18289/another-accepted-java-solution
// https://segmentfault.com/a/1190000003794420
// http://www.cnblogs.com/grandyang/p/5174322.html
public class ReadNCharactersGivenRead4I {
	public int read(char[] buf, int n) {
		char[] tmp = new char[4];
		int total = 0;
		boolean eof = false;
		while(!eof && total < n) {
			int countFromAPI = read4(buf);
			eof = countFromAPI < 4;
			countFromAPI = Math.min(countFromAPI, n - total);
			for(int i = 0; i < countFromAPI; i++) {
				buf[total++] = tmp[i];
			}
		}
		return total;
	}
}
