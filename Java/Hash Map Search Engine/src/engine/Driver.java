package engine;
import java.util.*;
public class Driver {

	public static void main(String[] args) {
        	HashEngine hash = new HashEngine();
		ArrayList<Frequency>list = new ArrayList<>();
		String name= "a";
		for (int i = 1; i < 26; i ++) {
			name += name;
			if (i == 25) {
				Frequency b = new Frequency(name, 0);
				list.add(b);
			}
			else {
				Frequency b = new Frequency(name, i+1);
				list.add(b);
		    	}
		}
		ArrayList<Integer> list2 = hash.insertEnd(list);
		System.out.println(list2+" ");
	}
}
