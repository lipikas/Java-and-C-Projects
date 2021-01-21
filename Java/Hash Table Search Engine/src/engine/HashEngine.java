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
	public HashMap<String,Frequency> loadKey(String docFile) throws FileNotFoundException {
		if (docFile == null)	throw new FileNotFoundException();
		HashMap<String,Frequency> h = new HashMap<>();
		
		Scanner s = new Scanner(new File(docFile));
		while(s.hasNext()) {
			String key = checkKey(s.next());
			if (key!= null && h.containsKey(key)) {
				Frequency occ = h.get(key);
				occ.order++;
			}
			else if (key!= null && !h.containsKey(key)) {
				Frequency occ = new Frequency(docFile, 1);
				h.put(key, occ);
			}
		}
		s.close();
		return h;
	}

	//Merges keys for a doc into master_keys hash table.
	public void mergeKey(HashMap<String,Frequency> keyWord) {
		for (String key : keyWord.keySet()){
			ArrayList<Frequency> occ = new ArrayList<>();
			if (keyIndex.containsKey(key))	occ = keyIndex.get(key);
			occ.add(keyWord.get(key)); // adds key value to occ list
			insertEnd(occ); // runs binary search and results in descending order of occ
			keyIndex.put(key, occ); // updates and replaces old value
		}
	}

    //Checks if a words passes the key tests orele removes the filerKeys like punctations, spaces, etc and returns the new word.
	public String checkKey(String word) {
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
	public ArrayList<Integer> insertEnd(ArrayList<Frequency> occs) {
        ArrayList<Integer> midp = new ArrayList<>();
		if (occs.size()==1) return midp;
		Frequency target = occs.get(occs.size() - 1);
		int min = 0;
		int max = occs.size() - 2;
		int mid = (min + max)/2;//EXCLUDES the target element from binary search
		while (min <= max){//perform binary search
			mid = (min + max)/2;
			midp.add(mid);
			if (occs.get(mid).order == target.order){//base case that breaks out of the search
				break;
			} else if (target.order < occs.get(mid).order){
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
	public void makeIndex(String docsFile, String filterKeyFile) throws FileNotFoundException {
		// load filterkeys into hash table
        Scanner sc = new Scanner(new File(filterKeyFile));
		while (sc.hasNext()) {
			String word = sc.next();
			filterKey.add(word);
		}
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String file = sc.next();
			HashMap<String,Frequency> keys = loadKey(file);
			mergeKey(keys);
		}
		sc.close();
	}

	// Searchs 2 key in docs and returns a list of 5 docs that match the key in decreasing frequency. 
	public ArrayList<String> searchDoc(String key1, String key2) { 

		ArrayList<String> result = new ArrayList<>();
		key1 = key1.toLowerCase();
		key2 = key2.toLowerCase();
		ArrayList<Frequency> l1 = keyIndex.get(key1);
		ArrayList<Frequency> l2 = keyIndex.get(key2);
		if((key1 == null && key2 == null)||(keyIndex.isEmpty())){//both strings are not found
            return result;
        }
		if(keyIndex.containsKey(key1) && keyIndex.containsKey(key2)){
			ArrayList<Frequency> occs = new ArrayList<>();
			occs.addAll(keyIndex.get(key1));
			occs.addAll(keyIndex.get(key2));
			for(int count = 0; count < 5 && !occs.isEmpty(); count++){
				int ptr = 0;
				int prev = -1;
				for(ptr = 0; ptr < occs.size() && occs.get(ptr) != null; ptr++){
					if (prev == -1 && !result.contains(occs.get(ptr).doc)) prev = ptr;
                    else if (occs.get(ptr).order > occs.get(prev).order && !result.contains(occs.get(ptr).doc)) {
                        prev = ptr;
                    }
					else if (occs.get(ptr).order == occs.get(prev).order && keyIndex.get(key1).contains(occs.get(ptr)) && !result.contains(occs.get(ptr).doc)) {
                            prev = ptr;
						}
					}
				if (prev != -1) result.add((occs.remove(prev).doc));
			}
		}
		else if(keyIndex.containsKey(key2)){//contains key1 but not key2
			for(int i = 0; i < l2.size(); i++){
				Frequency frequency = l2.get(i);
				if(result.size() < 5){
					result.add(frequency.doc);
				}
			}
		}
		else{//contains key1 but not key2
            for(int i = 0; i < l1.size(); i++){
				Frequency frequency = l1.get(i);
				if(result.size() < 5){
					result.add(frequency.doc);
				}
            }
		}

		return result;
	}
}
