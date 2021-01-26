package engine;
import java.util.*;
public class Driver {

	public static void main(String[] args) {
        	HashEngine f = new HashEngine();
		ArrayList<Frequency> p = new ArrayList<>();
		String name= "a";
		for (int i = 1; i < 26; i ++) {
			name += name;
			if (i == 25) {
				Frequency b = new Frequency(name, 0);
				p.add(b);
			}
			else {
				Frequency b = new Frequency(name, i+1);
				p.add(b);
		    	}
		}
		ArrayList<Integer> o = f.insertEnd(p);
		System.out.println(o+" ");
	}
}
