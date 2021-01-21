package engine;
import java.util.*;
public class Driver {

	public static void main(String[] args) {
        HashEngine f = new HashEngine();
		ArrayList<Frequency> p = new ArrayList<>();
		String u= "a";
		for (int i = 1; i < 26; i ++) {
            u = u+u;

            if (i == 25) {
                Frequency b = new Frequency(u, 0);
                p.add(b);
            }
            else {
                Frequency b = new Frequency(u, i+1);
                p.add(b);
            }
		}
		ArrayList<Integer> o = f.insertEnd(p);
		System.out.println(o+" ");
	}
}
