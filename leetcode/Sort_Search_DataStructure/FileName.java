/**
 * http://web.mit.edu/6.005/www/fa15/classes/10-recursion/
 * Recursive Problems vs. Recursive Data
 * The examples we’ve seen so far have been cases where the problem structure lends itself naturally to 
 * a recursive definition. Factorial is easy to define in terms of smaller subproblems. Having a recursive 
 * problem like this is one cue that you should pull a recursive solution out of your toolbox.
 
 * Another cue is when the data you are operating on is inherently recursive in structure. We’ll see many 
 * examples of recursive data, a few classes from now, but for now let’s look at the recursive data found 
 * in every laptop computer: its filesystem. A filesystem consists of named files. Some files are folders, 
 * which can contain other files. So a filesystem is recursive: folders contain other folders can contain 
 * other folders, until finally at the bottom of the recursion are plain (non-folder) files.
 
 * The Java library represents the file system using java.io.File. This is a recursive data type, in the 
 * sense that f.getParentFile() returns the parent folder of a file f, which is a File object as well, and 
 * f.listFiles() returns the files contained by f, which is an array of other File objects.
 
 * For recursive data, it’s natural to write recursive implementations:
    * @param f a file in the filesystem
    * @return the full pathname of f from the root of the filesystem
    public static String fullPathname(File f) {
        if (f.getParentFile() == null) {
            // base case: f is at the root of the filesystem
            return f.getName();  
        } else {
            // recursive step
            return fullPathname(f.getParentFile()) + "/" + f.getName();
        }
    }
* Recent versions of Java have added a new API, java.nio.Files and java.nio.Path, which offer a cleaner separation 
* between the filesystem and the pathnames used to name files in it. But the data structure is still fundamentally 
* recursive.
*/
public static String fullPathName(File f) {
   if(f.getParentFile() == null) {
      return f.getName();
   } else {
      return fullPathName(f.getParentFile()) + "/" + f.getName();
   }
}
