package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		Queue<Person> q=new Queue<>();
		boolean[] visit= new boolean [g.members.length];
		int[] prev= new int[g.members.length];
		
		Person p=g.members[g.map.get(p1)];
		Person t=g.members[g.map.get(p2)];
	
		//boolean to find if t has been visited 
		boolean f=false; 
		
		//check this statement 
		for(int i=0; i<prev.length;i++){
			prev[i]=-1;
		}
		//add first person to the q 
		q.enqueue(p);
		
		//while queue is empty the t has not been found 
		while(q.isEmpty()==false){
			Person curr= q.dequeue();
			if(curr==t){
				f=true;  
				break;
			}
			int name=g.map.get(curr.name);
			visit[name]=true; 
			
			Friend ptr=g.members[name].first;
			while(ptr!=null){
				
				if(visit[ptr.fnum]==false){
					q.enqueue(g.members[ptr.fnum]);
					prev[ptr.fnum]=name;
					
					visit[ptr.fnum]=true; 
					}
					ptr=ptr.next;
				}
			}
		
		if(!f)	return null;
		
		Stack <String> stack=new Stack<>();
		int num=g.map.get(t.name);
		int prevnum=prev[num];
		stack.push(t.name);
		
		while(prevnum!=-1){
			stack.push(g.members[prevnum].name);
			prevnum=prev[prevnum];
		}
		
		
		ArrayList<String> arr=new ArrayList<>();
		
			while(!stack.isEmpty()){
				arr.add(stack.pop());
			}
		if (arr.isEmpty())	return null;
		return arr; 
		
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		ArrayList<ArrayList<String>> arr= new ArrayList<ArrayList<String>>();
		boolean[] f=new boolean[g.members.length];
		
		for(int i=0; i<g.members.length;i++){
			
			ArrayList<String> xyz= new ArrayList<String>(); 
			Person curr=g.members[i];
			int name=g.map.get(curr.name);
			
			if(curr.school==null){
				continue;
			}
			
			if(curr.school.equals(school)&&f[name]==false){
				check(g,school,curr, f, xyz);
				arr.add(xyz);
			}
		}
		if (arr.isEmpty())	return null;
		return arr; 
	}
	private static void check (Graph g, String school, Person person, boolean[] f, ArrayList<String>arr){
		Queue<Person> q=new Queue<>();
		
		q.enqueue(person);
		
		while(q.isEmpty()==false){
			
			Person curr=q.dequeue();
			arr.add(curr.name);
			
			int name=g.map.get(curr.name);
			f[name]=true; 
			
			Friend ptr= g.members[name].first;
			
			while(ptr!=null){
				
				String fschool= g.members[ptr.fnum].school;
				if(fschool==null){
				ptr=ptr.next;
				continue; 
				}
			
				if(f[ptr.fnum]==false&& fschool.equals(school)){
				
					q.enqueue(g.members[ptr.fnum]);
					
					//mark visited
					f[ptr.fnum]=true; 
				}
			
				ptr=ptr.next;
			}
		}
	}
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		ArrayList<String> arr=new ArrayList<>();
		
		int x=g.members.length;
		boolean[] f=new boolean[x];
		int[] num=new int[x];
		int[]p=new int[x];
		boolean[] tf=new boolean[x];
		
		for(int i=0;i<x;i++){
			if(!f[i])	conn(g,i, f, num, p, i, arr, tf,i);	
		}
		if (arr.isEmpty())	return null;	
		return arr;
		}
	
	private static void conn(Graph g, int curr, boolean[] f, int[] num, 
			int p[], int prev, ArrayList<String> arr, boolean[] tf, int start){

		if(f[curr])	return;
		
		f[curr]=true;
		num[curr]=num[prev]+1;
		p[curr]=num[curr];
		
		Friend ptr=g.members[curr].first;
		while(ptr!=null){
		
		if(f[ptr.fnum])	p[curr]=Math.min(p[curr],num[ptr.fnum]);
		
		//not visited
		else{
		conn(g, ptr.fnum,f,num, p,curr, arr, tf, start);
		
		if(num[curr]<=p[ptr.fnum]&&!arr.contains(g.members[curr].name)){
			if(curr!=start||tf[curr]==true) arr.add(g.members[curr].name);
		}
		
		//decrements after reversing
		if(num[curr]>p[ptr.fnum])	p[curr]=Math.min(p[curr], p[ptr.fnum]);
		
		tf[curr]=true;
		}
		
		ptr=ptr.next;
		
		}
	}
}

