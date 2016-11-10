/**
 * For example, subsequences("abc") might return "abc,ab,bc,ac,a,b,c,". 
 * Note the trailing comma preceding the empty subsequence, which is also a valid subsequence.
 * 
 * Solution:
 * This problem lends itself to an elegant recursive decomposition. Take the first letter of the word. 
 * We can form one set of subsequences that include that letter, and another set of subsequences that 
 * exclude that letter, and those two sets completely cover the set of possible subsequences.
*/
