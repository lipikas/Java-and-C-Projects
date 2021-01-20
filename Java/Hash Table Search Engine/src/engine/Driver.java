package engine;
import java.io.*;
import java.util.*;
public class Driver {

	public static void main(String[] args) throws FileNotFoundException {

		ArrayList<Frequency> p = new ArrayList<Frequency>();
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
		ArrayList<Integer> o = f.insertLast(p);
		System.out.println(o+" ");

	}
}
	/*public void printGraph(){
		System.out.println(people.size());
		for(int v = 0; v < people.size(); v++){
			if(people.get(v).school != null){
				System.out.println(people.get(v).name + "|y|" + people.get(v).school);
			}
			else{
				System.out.println(people.get(v).name + "|n");
			}
		}
		for(int v = 0; v < people.size(); v++){
			Network ptr = people.get(v).networks;
			while(ptr != null){
				if(v < ptr.index){
					System.out.println(people.get(v).name + "|" + nameForIndex(ptr.index));
				}
				ptr = ptr.next;
			}
			
		}
	}
}*/
