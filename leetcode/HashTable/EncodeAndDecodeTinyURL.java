import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/encode-and-decode-tinyurl/#/description
 * TinyURL is a URL shortening service where you enter a URL such as 
 * https://leetcode.com/problems/design-tinyurl and it returns a short URL 
 * such as http://tinyurl.com/4e9iAk.
 * 
 * Design the encode and decode methods for the TinyURL service. There is no 
 * restriction on how your encode/decode algorithm should work. You just need 
 * to ensure that a URL can be encoded to a tiny URL and the tiny URL can be 
 * decoded to the original URL.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/81633/easy-solution-in-java-5-line-code
 * below is the tiny url solution in java, also this is the similar method in industry. 
 * In industry, most of shorten url service is by database, one auto increasing long 
 * number as primary key. whenever a long url need to be shorten, append to the database, 
 * and return the primary key number. (the database is very easy to distribute to multiple 
 * machine like HBase, or even you can use the raw file system to store data and improve 
 * performance by shard and replica).
 * Note, it's meaningless to promise the same long url to be shorten as the same short url. 
 * if you do the promise and use something like hash to check existing, the benefit is must 
 * less than the cost.
 * Note: if you want the shorted url contains '0-9a-zA-Z' instead of '0-9', then you need 
 * to use 62 number system, not 10 number system(decimal) to convert the primary key number. 
 * like 123->'123' in decimal, 123->'1Z' in 62 number system (or '0001Z' for align).
 * 
 * 
 * https://discuss.leetcode.com/topic/81637/two-solutions-and-thoughts
 * My first solution produces short URLs like http://tinyurl.com/0, http://tinyurl.com/1, etc, in that order.

	class Codec:
	
	    def __init__(self):
	        self.urls = []
	
	    def encode(self, longUrl):
	        self.urls.append(longUrl)
	        return 'http://tinyurl.com/' + str(len(self.urls) - 1)
	
	    def decode(self, shortUrl):
	        return self.urls[int(shortUrl.split('/')[-1])]

 * Using increasing numbers as codes like that is simple but has some disadvantages, which the below solution fixes:
 * (1) If I'm asked to encode the same long URL several times, it will get several entries. That wastes codes and memory.
 * (2) People can find out how many URLs have already been encoded. Not sure I want them to know.
 * (3) People might try to get special numbers by spamming me with repeated requests shortly before their desired number comes up.
 * (4) Only using digits means the codes can grow unnecessarily large. Only offers a million codes with length 6 (or smaller). 
 * (5) Using six digits or lower or upper case letters would offer (10+26*2)6 = 56,800,235,584 codes with length 6.
 * 
 * The following solution doesn't have these problems. It produces short URLs like http://tinyurl.com/KtLa2U, 
 * using a random code of six digits or letters. If a long URL is already known, the existing short URL is used 
 * and no new entry is generated.

		class Codec:
		
		    alphabet = string.ascii_letters + '0123456789'
		
		    def __init__(self):
		        self.url2code = {}
		        self.code2url = {}
		
		    def encode(self, longUrl):
		        while longUrl not in self.url2code:
		            code = ''.join(random.choice(Codec.alphabet) for _ in range(6))
		            if code not in self.code2url:
		                self.code2url[code] = longUrl
		                self.url2code[longUrl] = code
		        return 'http://tinyurl.com/' + self.url2code[longUrl]
		
		    def decode(self, shortUrl):
		        return self.code2url[shortUrl[-6:]]

 * It's possible that a randomly generated code has already been generated before. In that case, another random code is 
 * generated instead. Repeat until we have a code that's not already in use. How long can this take? Well, even if we 
 * get up to using half of the code space, which is a whopping 626/2 = 28,400,117,792 entries, then each code has a 50% 
 * chance of not having appeared yet. So the expected/average number of attempts is 2, and for example only one in a 
 * billion URLs takes more than 30 attempts. And if we ever get to an even larger number of entries and this does become 
 * a problem, then we can just use length 7. We'd need to anyway, as we'd be running out of available codes.
 * 
 * 
 * https://discuss.leetcode.com/topic/81637/two-solutions-and-thoughts/4
 * Same solution in Java
 * 
 * 
 * http://www.cnblogs.com/grandyang/p/6562209.html
 * 这道题让我们编码和解码精简URL地址，这其实很有用，因为有的链接地址特别的长，
 * 就很烦，如果能精简成固定的长度，就很清爽。最简单的一种编码就是用个计数器，
 * 当前是第几个存入的url就编码成几，然后解码的时候也能根据数字来找到原来的url
 * 上面这种方法虽然简单，但是缺点却很多，首先，如果接受到多次同一url地址，仍然会
 * 当做不同的url来处理。当然这个缺点可以通过将vector换成哈希表，每次先查找url
 * 是否已经存在。虽然这个缺点可以克服掉，但是由于是用计数器编码，那么当前服务器
 * 存了多少url就曝露出来了，也许会有安全隐患。而且计数器编码另一个缺点就是数字
 * 会不断的增大，那么编码的长度也就不是确定的了。而题目中明确推荐了使用六位随机字
 * 符来编码，那么我们只要在所有大小写字母和数字中随机产生6个字符就可以了，我们用
 * 哈希表建立6位字符和url之间的映射，如果随机生成的字符之前已经存在了，我们就继续
 * 随机生成新的字符串，直到生成了之前没有的字符串为止。下面的代码中使用了两个哈希表，
 * 目的是为了建立六位随机字符串和url之间的相互映射，这样进来大量的相同url时，就不用
 * 生成新的随机字符串了。当然，不加这个功能也能通过OJ，这道题的OJ基本上是形同虚设，
 * 两个函数分别直接返回参数字符串也能通过OJ
 */
public class EncodeAndDecodeURL {
	// Solution 1:
    List<String> urls = new ArrayList<String>();
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        urls.add(longUrl);
        return String.valueOf(urls.size()-1);
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        int index = Integer.valueOf(shortUrl);
        return (index<urls.size())?urls.get(index):"";
    }

    
    // Solution 2:
    Map<String, String> index = new HashMap<String, String>();
    Map<String, String> revIndex = new HashMap<String, String>();
    String BASE_HOST = "http://tinyurl.com/";
    public String encode_2(String longUrl) {
    	if(revIndex.containsKey(longUrl)) {
    		return BASE_HOST + revIndex.get(longUrl);
    	}
    	String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    	String key = null;
    	// Recursively check if HashMap 'index' already contains newly generated 'key'
    	// with help of do-while loop (It's possible that a randomly generated code has 
    	// already been generated before. In that case, another random code is generated 
    	// instead. Repeat until we have a code that's not already)
    	do {
    		StringBuilder sb = new StringBuilder();
    		for(int i = 0; i < 6; i++) {
    			int r = (int)(Math.random() * charset.length());
    			sb.append(charset.charAt(r));
    		}
    	} while(index.containsKey(key));
    	index.put(key, longUrl);
    	revIndex.put(longUrl, key);
    	return BASE_HOST + key;
    }
    
    public String decode_2(String shortUrl) {
    	return index.get(shortUrl.replace(BASE_HOST, ""));
    }
    

}

