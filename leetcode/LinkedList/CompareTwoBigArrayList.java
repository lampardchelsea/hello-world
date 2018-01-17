/**
 * Given 
   List<String> list1 = new ArrayList<String>();
   List<String> list2 = new ArrayList<String>();
   Find common and uncommon parts between two big arraylist,
   require time complexity very low
*/
// Step1: Build HashSet as filter for list1 to select out all unique Strings from list1
Set<String> set1 = new HashSet<String>();
for(String s : list1) {
      set1.add(s);
}

// Step 2: Use set1 as unique String dictionary to filter list2, and store result in a new HashSet as set2, 
// which is the mutual part of these 2 lists, others store in a new set3
Set<String> set2 = new HashSet<String>();
Set<String> set3 = new HashSet<String>();
for(String s : list2) {
      // set1.add(s) return false means set1 already contains this same string which
      // also present in list2
      if(!set1.add(s)) {
            set2.add(s);    // Common strings stored in set2
      } else {
            set3.add(s);    // Uncommon strings stored in set3
      }
}

