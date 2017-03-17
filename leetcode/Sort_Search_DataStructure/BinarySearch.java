/**
 * As only use below solution(add generic type on method cannot compare generic type)
 * cannot work exactly.
 * Generic Methods
 * https://docs.oracle.com/javase/tutorial/extra/generics/methods.html 
	public <T> int indexOf(T key, T[] array) {
		int lo = 0;
		int hi = array.length - 1;
		while(lo <= hi) {
			int mid = (hi - lo) / 2 + lo;
			if(array[mid] >= key) {...} -> The ">=" cannot work against generic type T 
			...
		}
	}
 * 
 * Alternatively, we have some other ways.
 * Bounded Type Parameters
 * https://docs.oracle.com/javase/tutorial/java/generics/bounded.html
 * In addition to limiting the types you can use to instantiate a generic type, 
 * bounded type parameters allow you to invoke methods defined in the bounds:
	 public class NaturalNumber<T extends Integer> {
	
	    private T n;
	
	    public NaturalNumber(T n)  { this.n = n; }
	
	    public boolean isEven() {
	        return n.intValue() % 2 == 0;
	    }
	
	    // ...
	}
 * The isEven method invokes the intValue method defined in the Integer class through n.
 * 
 * 
 * Generic binary search Java
 * http://stackoverflow.com/questions/5426316/generic-binary-search-java
 * 
 * Note 1:
 * public class BinarySearcher<T extends Comparable<T>> { ...
 * This'll allow your BinarySearcher to work for everything that can be 
 * compared with compareTo, which should be generic enough.
 * 
 * 
 * Note 2:
 * There's no way that your generic method can compare two instances of some 
 * arbitrary type T, without having any constraints on T or having information 
 * some other way about how to compare two Ts.
 * 
 * You have a few choices:
 * Add a constraint to T:
 * public class BinarySearcher<T extends Comparable<T>>
 * Or 
 * pass in a Comparator<T> to your search method that can be called to do the comparison:
 * public int search(T v, Comparator<T> comp) {
    // ...
    if (comp.compare(v, midVal < 0)) {
    // ...
   }
   
 * Side note: I would avoid calling compareTo two times in your search method; 
 * you don't know if comparing the two objects is expensive. Instead of this:
   if (v.compareTo(midVal) < 0) {
     low = mid - 1;
   } else if (v.compareTo(midVal) > 0) {
    high = mid  + 1; 
   }
   
 * Do something like this:
   int result = v.compareTo(midVal);
   if (result < 0) {
     low = mid - 1;
   } else if (result > 0) {
     high = mid  + 1; 
   }
 * 
 * 
 * Note 3:
 * Even after doing all the changes suggested above, the Conditions you are using are 
 * incorrect (your changing of low and high pointers) and you need an else clause after 
 * else if to return the index.
	public class BinarySearcher<T extends Comparable<T>> {
	    private T[] a;
	
	    public BinarySearcher(T[] words) {
	        a = words;
	    }
	
	    public int search(Comparable<T> v) {
	        int low = 0;
	        int high = a.length - 1;
	
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            T midVal = a[mid];
	            int result = v.compareTo(midVal);
	
	            if (result < 0) {
	                high = mid - 1;
	            }
	
	            else if (result > 0) {
	                low = mid + 1;
	            } 
	
	            else {
	                return mid;
	            }
	        }
	
	        return -1;
	    }
	}
 *
 *
 * Note 4: When we need override compareTo(T o) method ?
 * Also, the compareTo(T o) method defined in Comparable interface need some detail explain,
 * As its description is:
 * Compares this object with the specified object for order. Returns a negative integer, 
 * zero, or a positive integer as this object is less than, equal to, or greater than the specified object. 
 * The implementor must ensure sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y. 
 * (This implies that x.compareTo(y) must throw an exception iff y.compareTo(x) throws an exception.) 
 * The implementor must also ensure that the relation is transitive: (x.compareTo(y)>0 && y.compareTo(z)>0) implies x.compareTo(z)>0. 
 * Finally, the implementor must ensure that x.compareTo(y)==0 implies that sgn(x.compareTo(z)) == sgn(y.compareTo(z)), for all z. 
 * It is strongly recommended, but not strictly required that (x.compareTo(y)==0) == (x.equals(y)). 
 * Generally speaking, any class that implements the Comparable interface and violates this condition should clearly indicate this fact. 
 * The recommended language is "Note: this class has a natural ordering that is inconsistent with equals." 
 * In the foregoing description, the notation sgn(expression) designates the mathematical signum function, 
 * which is defined to return one of -1, 0, or 1 according to whether the value of expression is negative, zero or positive.
 * Parameters:
 * o the object to be compared.
 * Returns:
 * a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
 * 
 * As String class in java already implement compareTo(T o) method of Comparable<T> interface, 
 * The definition:
 * public final class String extends Object implements Serializable, Comparable<String>, CharSequence
 * we don't need to override compareTo(T o) method in BinarySearch class.
 * 
 * But when we create own class such as Employee.java and what to sort its instance with member variable such as empId
 * or some other attributes, we need first implement compareTo(T o) method in Employee.java first, then it can be used
 * for compare in BinarySearch class.
 * e.g
	public class Employee implements Comparable<Employee> {
		int empId;
		
		@Override
		public int compareTo(Employee emp) {
			return (this.empId - emp.empId);
		}
   	}
 * 
 * For other examples can check: 
 * Why should a Java class implement comparable?
 * http://stackoverflow.com/questions/3718383/why-should-a-java-class-implement-comparable
 * 
 * 
 * Note 5: Some important tips of compareTo(T o) method
 * http://javarevisited.blogspot.com/2011/11/how-to-override-compareto-method-in.html
 * 
 * 1.CompareTo must be in consistent with equals method
 * e.g. if two objects are equal via equals() , there compareTo() must return zero otherwise if those objects are stored in SortedSet 
 * or SortedMap they will not behave properly. Since SortedSet or SortedMap use compareTo() to check the object if two unequal object 
 * are returned equal by compareTo those will not be added into Set or Map if they are not using external Comparator.  
 * One example where compareTo is not consistent with equals in JDK is BigDecimal class. two BigDecimal number for which compareTo 
 * returns zero, equals returns false as clear from following BigDecimal comparison example:
     BigDecimal bd1 = new BigDecimal("2.0");
     BigDecimal bd2 = new BigDecimal("2.00");
     System.out.println("comparing BigDecimal using equals: " + bd1.equals(bd2));
     System.out.println("comparing BigDecimal using compareTo: " + bd1.compareTo(bd2));
     Output:
     comparing BigDecimal using equals: false
     comparing BigDecimal using compareTo: 0
  * How does it affect BigDecimal ? well if you store these two BigDecimal in HashSet you will end up with duplicates (violation of Set Contract) 
  * i.e. two elements while if you store them in TreeSet you will end up with just 1 element because HashSet uses equals to check duplicates 
  * while TreeSet uses compareTo to check duplicates. That's why its suggested to keep compareTo consistent with equals method in java.
  * 
  * 2.Don't use subtraction for comparing integral values
  * Another important point to note is don't use subtraction for comparing integral values because result of subtraction can overflow 
  * as every int operation in Java is modulo 2^32. use either Integer.compareTo() or logical operators for comparison. 
  * There is one scenario where you can use subtraction to reduce clutter and improve performance. As we know compareTo doesn't 
  * care magnitude, it just care whether result is positive or negative. While comparing two integral fields you can use subtraction 
  * if you are absolutely sure that both operands are positive integer or more precisely there different must be less than Integer.MAX_VALUE. 
  * In this case there will be no overflow and your compareTo will be concise and faster.
  *
  * Note 5: Calculating mid in binary search
  * http://stackoverflow.com/questions/6735259/calculating-mid-in-binary-search
  * https://research.googleblog.com/2006/06/extra-extra-read-all-about-it-nearly.html
  * So what's the bug? Here's a standard binary search, in Java. (It's one that I wrote for the java.util.Arrays):
	1:     public static int binarySearch(int[] a, int key) {
	2:         int low = 0;
	3:         int high = a.length - 1;
	4:
	5:         while (low <= high) {
	6:             int mid = (low + high) / 2;
	7:             int midVal = a[mid];
	8:
	9:             if (midVal < key)
	10:                 low = mid + 1
	11:             else if (midVal > key)
	12:                 high = mid - 1;
	13:             else
	14:                 return mid; // key found
	15:         }
	16:         return -(low + 1);  // key not found.
	17:     }
   * The bug is in this line:
 	6:             int mid =(low + high) / 2;
   * In Programming Pearls Bentley says that the analogous line "sets m to the average of l and u, 
   * uncated down to the nearest integer." On the face of it, this assertion might appear correct, 
   * but it fails for large values of the int variables low and high. Specifically, it fails if 
   * the sum of low and high is greater than the maximum positive int value ((2)31 - 1). The sum 
   * overflows to a negative value, and the value stays negative when divided by two. In C this 
   * causes an array index out of bounds with unpredictable results. 
   * In Java, it throws ArrayIndexOutOfBoundsException.
   * This bug can manifest itself for arrays whose length (in elements) is (2)30 or greater 
   * (roughly a billion elements). This was inconceivable back in the '80s, when Programming Pearls 
   * was written, but it is common these days at Google and other places. In Programming Pearls, 
   * Bentley says "While the first binary search was published in 1946, the first binary search 
   * that works correctly for all values of n did not appear until 1962." The truth is, very few 
   * correct versions have ever been published, at least in mainstream programming languages.
   * So what's the best way to fix the bug? Here's one way:
        6:             int mid = low + ((high - low) / 2);
   * Probably faster, and arguably as clear is:
        6:             int mid = (low + high) >>> 1; 
   * In C and C++ (where you don't have the >>> operator), you can do this:
        6:             mid = ((unsigned int)low + (unsigned int)high)) >> 1;
 */
import java.util.Arrays;

public class BinarySearch<T extends Comparable<T>> {
	public BinarySearch() {}
	
	public int indexOf(T key, T[] array) {
		int lo = 0;
		int hi = array.length - 1;
		while(lo <= hi) {
			int mid = (hi - lo) / 2 + lo;
			T midVal = array[mid];
			int result = key.compareTo(midVal);
			
			if(result > 0) {
				lo = mid + 1;
			} else if(result < 0) {
				hi = mid - 1;
			} else {
				return mid;
			}
		}
		
		// Return -1 as no such key in the array
		return -1;
	}
	
    public static void main(String[] args) {
        String[] words = {"Bravo", "Alpha", "Delta", "Charlie", "Echo", 
            "Foxtrot", "Golf", "Hotel", "India", "Juliet", "Kilo", "Lima", 
            "Mike", "November", "Oscar", "Papa", "Quebec", "Romeo", 
            "Sierra", "Tango", "Uniform", "Victor", "Whiskey", "X-Ray", 
            "Yankee", "Zulu"};
        
        // Implement binary search based on first sort given array
        Arrays.sort(words);
        BinarySearch<String> searcher = new BinarySearch<String>();
        System.out.println(searcher.indexOf("November", words));
        System.out.println("Expected: 13");
        System.out.println(searcher.indexOf("October", words));
        System.out.println("Expected: -1");
        System.out.println(searcher.indexOf("Kilo", words));
        System.out.println("Expected: 10");
    }
	
}
