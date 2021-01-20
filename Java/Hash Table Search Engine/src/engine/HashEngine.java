package engine;
import java.io.*;
import java.util.*;


public class HashEngine {

	HashMap<String,ArrayList<Frequency>> keyIndex;

	HashSet<String> filterKey; // key that we need to avoid

	// Maintains the keyIndex and filterKey hash tables.
	 
	public HashEngine() {
		keyIndex = new HashMap<String,ArrayList<Frequency>>(1000,2.0f);
		filterKey = new HashSet<String>(100,2.0f);
	}

	//Scans the doc & maintains keywords found into a hash table of key Frequency
	 
	public HashMap<String,Frequency> loadKey(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		if (docFile == null)	throw new FileNotFoundException();
		HashMap<String,Frequency> h = new HashMap<String,Frequency>();
		//int i =0;
		Scanner s = new Scanner(new File(docFile));
		while(s.hasNext()) {
			String key = checkKey(s.next());
			/*if (key!=null && h.containsKey(key) && key.compareTo("alice")==0)  {
				Frequency occ = h.get(key);
				i = occ.Frequency +1;
			}*/
			//if (key!=null)System.out.print(key + " ");
			if (key!= null && h.containsKey(key)) {
				Frequency occ = h.get(key);
				occ.Frequency++;
			}
			else if (key!= null && !h.containsKey(key)) {
				Frequency occ = new Frequency(docFile, 1);
				h.put(key, occ);
			}
		}
		s.close();
		//System.out.println(i);
		//System.out.println();
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return h;
	}

	//Merges keys for a doc into master_keys hash table.
	 
	public void mergeKey(HashMap<String,Frequency> kws) {
		for (String key : kws.keySet())
		{
			ArrayList<Frequency> occ = new ArrayList<Frequency>();

			if (keyIndex.containsKey(key))	occ = keyIndex.get(key);

			occ.add(kws.get(key)); // adds key value to occ list
			insertLastFrequency(occ); // runs binary search and results in descending order of occ

			keyIndex.put(key, occ); // updates and replaces old value
		}
	}

    //Checks if a words passes the key tests orele removes the filerKeys like punctations, spaces, etc and returns the new word.
    
	public String checkKey(String word) {
		/** COMPLETE THIS METHOD **/
		String t = null;
		String pun = ".,?:;!";

		if (word == null || word.length()==0 )	return t;

		word = word.toLowerCase();
		for(int i = word.length()-1; i>=0; i--){
			if(pun.contains(Character.toString(word.charAt(i)))){
				word = word.substring(0,i);
			}
			else break;
		}

		if(filterKey.contains(word)) return t;

		if (word.length()==0 )	return t;
		for(int i = word.length()-1; i>=0; i--){
			if (!Character.isLetter(word.charAt(i))){ // when punc in between
				return null;
			}
		}
		return word;
	}


	//Adds last occurence of a key by determining first correct spot via binary search and returns arraylist
	
	public ArrayList<Integer> insertLast(ArrayList<Frequency> occs) {

		if (occs.size()==1) return null;
		ArrayList<Integer> midp = new ArrayList<Integer>();
		Frequency target = occs.get(occs.size() - 1);
		int min = 0;
		int max = occs.size() - 2;
		int mid = (min + max)/2;//EXCLUDES the target element from binary search
		while (min <= max){//perform binary search
			mid = (min + max)/2;
			midp.add(mid);
			if (occs.get(mid).Frequency == target.Frequency){//base case that breaks out of the search
				break;
			} else if (target.Frequency < occs.get(mid).Frequency){
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}
		occs.add(mid+1,occs.remove(occs.size()-1));
		if (max < min) occs.add(min,occs.remove(occs.size() - 1));

		return midp;
	}

	// Fills hash table with keys in desecnding key freqeuncy order.
	
	public void makeIndex(String docsFile, String filterKeyFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(filterKeyFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}

		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Frequency> kws = loadKey(docFile);
			mergeKey(kws);
		}
		sc.close();
	}

	// Searchs 2 key in docs and returns a list of 5 docs that match the key in decreasing frequency. 
	public ArrayList<String> searchDoc(String key1, String key2) { 
		/** COMPLETE THIS METHOD **/

		ArrayList<String> result = new ArrayList<String>();
		key1 = key1.toLowerCase();
		key2 = key2.toLowerCase();
		ArrayList<Frequency> l1 = keyIndex.get(key1);
		ArrayList<Frequency> l2 = keyIndex.get(key2);
		if((key1 == null && key2 == null)||(!keyIndex.containsKey(key1) && !keyIndex.containsKey(key2))||(keyIndex.isEmpty())){//both strings are not found
			//System.out.println("Both strings not found");
			return null;
		}
		else if(keyIndex.containsKey(key1) && !keyIndex.containsKey(key2)){//contains key1 but not key2
			for(int i = 0; i < l1.size(); i++){
				Frequency Frequency = l1.get(i);
				if(result.size() < 5){
					result.add(Frequency.doc);
				}
			}
		}
		else if(keyIndex.containsKey(key2) && !keyIndex.containsKey(key1)){//contains key1 but not key2
			for(int i = 0; i < l2.size(); i++){
				Frequency Frequency = l2.get(i);
				if(result.size() < 5){
					result.add(Frequency.doc);
				}
			}
		}
		else{
			ArrayList<Frequency> occs = new ArrayList<Frequency>();
			occs.addAll(keyIndex.get(key1));
			occs.addAll(keyIndex.get(key2));
			for(int count = 0; count < 5 && !occs.isEmpty(); count++){
				int ptr = 0;
				int prev = -1;
				for(ptr = 0; ptr < occs.size() && occs.get(ptr) != null; ptr++){
					if (prev == -1){
						if (!result.contains(occs.get(ptr).doc)) prev = ptr;
					} else if (occs.get(ptr).Frequency > occs.get(prev).Frequency){
						if(!result.contains(occs.get(ptr).doc)) prev = ptr;
					} else if (occs.get(ptr).Frequency == occs.get(prev).Frequency){
						if(keyIndex.get(key1).contains(occs.get(ptr))){
							if(!result.contains(occs.get(ptr).doc)) prev = ptr;
						}
					}
				}
				if (prev != -1) result.add((occs.remove(prev).doc));
			}
		}

		return result;
		}
}
