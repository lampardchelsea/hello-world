/**
 * Refer to
 * https://leetcode.com/problems/factorial-trailing-zeroes/description/
 * Given an integer n, return the number of trailing zeroes in n!.
   Note: Your solution should be in logarithmic time complexity.
 *
 * Solution
 * https://discuss.leetcode.com/topic/6513/simple-c-c-solution-with-detailed-explaination
 * https://discuss.leetcode.com/topic/6513/simple-c-c-solution-with-detailed-explaination/4
 * http://www.purplemath.com/modules/factzero.htm
   You're probably reading this page because you've been assigned a seemingly impossible exercise, 
   something along the lines of "Here's a really big number; consider its much bigger factorial 
   and then figure out how many zeroes are at the end of the multiplied-out factorial."
   
   This "trailing zeroes in a factorial" exercise is pretty easy to answer once you think about it 
   the right way. I couldn't find anything much useful on the Internet, so here's a little lesson 
   on how to handle it.
   
   Find the number of trailing zeroes in the expansion of 23!
   If I try to plug this into my calculator, I'll get something in scientific notation, because 
   the answer is too big for the calculator to display. In practical terms, the calculator will 
   show me the beginning of the number, and I'm only caring about the end of the number (the 
   "trailing" part). So the calculator won't help. I'll try expanding the factorial:
   
   1×2×3×4×5×6×7×8×9×10×11×12×13×14×15×16×17×18×19×20×21×22×23
   
   I know that a number gets a zero at the end of it if the number has 10 as a factor. For instance, 
   10 is a factor of 50, 120, and 1234567890. So I need to find out how many times 10 is a factor 
   in the expansion of 23!.
   
   But since 5×2 = 10, I need to account for all the products of 5 and 2. Looking at the factors 
   in the above expansion, there are many more numbers that are multiples of 2 (2, 4, 6, 8, 10, 12, 
   14,...) than are multiples of 5 (5, 10, 15,...). That is, if I take all the numbers with 5 as a 
   factor, I'll have way more than enough even numbers to pair with them to get factors of 10 (and 
   another trailing zero on my factorial). So to find the number of times 10 is a factor, all I really 
   need to worry about is how many times 5 is a factor in all of the numbers between 1 and 23.
   
   How many multiples of 5 are between 1 and 23? There is 5, 10, 15, and 20, for four multiples of 5. 
   Paired with 2's from the even factors, this makes for four factors of 10, so:
   23! has four trailing zeroes
   
   Find the number of trailing zeroes in 101!
   Okay, how many multiples of 5 are there in the numbers from 1 to 101? There's 5, 10, 15, 20, 25,...
   Oh, heck; let's do this the short way: 100 is the closest multiple of 5 below 101, and 100 ÷ 5 = 20, 
   so there are twenty multiples of 5 between 1 and 101.
   But wait: 25 is 5×5, so each multiple of 25 has an extra factor of 5 that I need to account for. 
   How many multiples of 25 are between 1 and 101? Since 100 ÷ 25 = 4, there are four multiples of 
   25 between 1 and 101.
   Adding these, I get 20 + 4 = 24 trailing zeroes in 101!
   This reasoning extends to working with larger numbers.
   Find the number of trailing zeroes in the expansion of 1000!
   Okay, there are 1000 ÷ 5 = 200 multiples of 5 between 1 and 1000. The next power of 5, namely 25, 
   has 1000 ÷ 25 = 40 multiples between 1 and 1000. The next power of 5, namely 125, will also fit in 
   the expansion, and has 1000 ÷ 125 = 8 multiples between 1 and 1000. The next power of 5, namely 625, 
   also fits in the expansion, and has 1000 ÷ 625 = 1.6... um, okay, 625 has 1 multiple between 1 and 1000. 
   (I don't care about the 0.6 "multiples", only the one full multiple, so I truncate my division down to a whole number.)
   
   In total, I have 200 + 40 + 8 + 1 = 249 copies of the factor 5 in the expansion, and thus:
   249 trailing zeroes in the expansion of 1000!
   
   The previous example highlights the general method for answering this question, no matter what 
   factorial they give you.
   
   Take the number that you've been given the factorial of.
   Divide by 5; if you get a decimal, truncate to a whole number.
   Divide by 5^2 = 25; if you get a decimal, truncate to a whole number.
   Divide by 5^3 = 125; if you get a decimal, truncate to a whole number.
   Continue with ever-higher powers of 5, until your division results in a number less than 1. Once the 
   division is less than 1, stop.
   Sum all the whole numbers you got in your divisions. This is the number of trailing zeroes.
   Here's how the process works:
  
   How many trailing zeroes would be found in 4617!, upon expansion?
   I'll apply the procedure from above:

   5^1 :  4617 ÷ 5 = 923.4, so I get 923 factors of 5
   5^2 :  4617 ÷ 25 = 184.68, so I get 184 additional factors of 5
   5^3 :  4617 ÷ 125 = 36.936, so I get 36 additional factors of 5
   5^4 :  4617 ÷ 625 = 7.3872, so I get 7 additional factors of 5
   5^5 :  4617 ÷ 3125 = 1.47744, so I get 1 more factor of 5
   5^6 :  4617 ÷ 15625 = 0.295488, which is less than 1, so I stop here.
   Then 4617! has 923 + 184 + 36 + 7 + 1 = 1151 trailing zeroes.

   By the way, you can get the same result, if you keep track as you go, by just dividing repeatedly 
   in your calculator by 5's: 4617 ÷ 5 = 923.4 (write down 923), 923.4 ÷ 5 = 184.68 (write down 184), 
   184.68 ÷ 5 = 36.936 (write down 36), 36.936 ÷ 5 = 7.3827 (write down 7), 7.3827 ÷ 5 = 1.47744 
   (write down 1), and 1.47744 ÷ 5 is goint to be less than 1, so you're done. Turn to your scratch 
   paper were you've written down the whole numbers, and sum them to get 1151.

   Note: Software like Excel won't help with this sort of computation, any more than your calculator 
   would. Software customarily only stores fifteen or so digits of "accuracy", which is why, after a 
   number gets sufficiently large, the display switches automatically to scientific notation. Since 
   the software is only storing the first few leading digits, the remaining trailing digits have to 
   be filled in with zeroes. If you attempt the first expansion above, "23!", in Excel, you'll get 
   something with way more zeroes than is actually true. In other words, the computer will give you 
   the wrong answer. So learn the concepts; don't try to cheat with software.
*/
public class FactorialTrailingZeros {
	public int trailingZeros(int n) {
        int divisor = 5;
        int result = 0;
        // Directly use 'n', don't need to calculate n!
        while(n / divisor > 0) {
            result += n / divisor;
            // Don't use multiply, as n > 1808548329 will cause overflow issue
            // on 'divisor * 5' because of 'divisor * 5 > Integer.MAX_VALUE / 5'
            // and it is still valid.
            //divisor *= 5; 
            n = n / divisor;
        }
        return result;
	}
	
	public static void main(String[] args) {
		FactorialTrailingZeros f = new FactorialTrailingZeros();
		int n = 3;
		int result = f.trailingZeros(n);
		System.out.println(result);
	}
}
