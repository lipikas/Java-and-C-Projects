package network;
import java.util.*;

class Connection {
	int fnum;
	Connection next;
	Connection(int fnum, Connection next) {
		this.fnum = fnum;
		this.next = next;
	}
}

class Human {
	String name;
	boolean student;
	String college;
	Connection first;
}


class Edge {
	int v1, v2;
	Edge(int v1, int v2) {
		this.v1 = v1; this.v2 = v2;
	}
}

public class Networks {
	Human[] people;
	HashMap<String,Integer> map;
	public Networks(Scanner sc) {
		int n = Integer.parseInt(sc.nextLine());
		people = new Human[n];
		map = new HashMap<String,Integer>(n*2);
		for (int i=0; i < n; i++) {
			String info = sc.nextLine();
			StringTokenizer st = new StringTokenizer(info,"|");
			Human human = new Human();
			human.name = st.nextToken();
			String yn = st.nextToken(); // student or not
			human.student = false;
			human.college = null;
			if (yn.toLowerCase().charAt(0) == 'y') {
				human.student = true;
				human.college = st.nextToken();
			}
			human.first = null;
			people[i] = human;
			map.put(human.name,i);
		}
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			StringTokenizer st = new StringTokenizer(line,"|");
			String p1 = st.nextToken();
			String p2 = st.nextToken();
			int i = map.get(p1);
			int j = map.get(p2);
			people[i].first = new Connection(j,people[i].first);
			people[j].first = new Connection(i,people[j].first);
		}
	}
}
