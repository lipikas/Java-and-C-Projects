package lse;
import java.io.*;
import java.util.*;
public class Drive {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Input keyword");
		String res = sc.next();
		System.out.println("Input keyword");
		String resp = sc.next();
		sc.close();*/
		LittleSearchEngine f= new LittleSearchEngine();
		//System.out.println(f.)
		/*f.makeIndex("docs.txt", "noisewords.txt");
		ArrayList<String> t = f.top5search(res,resp);
		
		
		if (t == null)	System.out.println("Noooo");
		else {
			for (int a = 0; a < t.size(); a++) {
				
				System.out.print(t.get(a) + " ");
			}
		
	}*/
		
		ArrayList<Occurrence> p = new ArrayList<Occurrence>();
		String u= "a";
		for (int i = 1; i < 26; i ++) {
		u = u+u;
		
		if (i == 25) {
			Occurrence b = new Occurrence(u, 0);
			p.add(b);
		}
		else {
			Occurrence b = new Occurrence(u, i+1);
			p.add(b);
		}
		
		}
		ArrayList<Integer> o = f.insertLastOccurrence(p);
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
			Friend ptr = people.get(v).friends;
			while(ptr != null){
				if(v < ptr.index){
					System.out.println(people.get(v).name + "|" + nameForIndex(ptr.index));
				}
				ptr = ptr.next;
			}
			
		}
	}
}*/
