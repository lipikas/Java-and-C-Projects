package network;

import java.util.ArrayList;
import structs.Queue;
import structs.Stack;

public class Connections {

	//Determines shortest path between 2 people in a network
	public static ArrayList<String> smallestPath(Networks g, String pearson1, String pearson2) {
		Queue<Human> q=new Queue<>();
		boolean[] visit= new boolean [g.people.length];
		int[] prev= new int[g.people.length];

		Human p=g.people[g.map.get(pearson1)];
		Human t=g.people[g.map.get(pearson2)];
		boolean f=false;
		for(int i=0; i<prev.length;i++){
			prev[i]=-1;
		}
		q.enqueue(p);
		while(!q.isEmpty()){
			Human curr= q.dequeue();
			if(curr==t){
				f=true;  
				break;
			}
			int name=g.map.get(curr.name);
			visit[name]=true; 

			Connection ptr=g.people[name].first;
			while(ptr!=null){
				if(!visit[ptr.fnum]){
					q.enqueue(g.people[ptr.fnum]);
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
			stack.push(g.people[prevnum].name);
			prevnum=prev[prevnum];
		}
		ArrayList<String> arr=new ArrayList<>();

			while(!stack.isEmpty()){
				arr.add(stack.pop());
			}
		if (arr.isEmpty())	return null;
		return arr; 
	}
	//Determines all the networks in a college
	public static ArrayList<ArrayList<String>> groups(Networks g, String college) {
		ArrayList<ArrayList<String>> arr= new ArrayList<ArrayList<String>>();
		boolean[] f=new boolean[g.people.length];

		for(int i=0; i<g.people.length;i++){
			ArrayList<String> xyz= new ArrayList<String>(); 
			Human curr=g.people[i];
			int name=g.map.get(curr.name);

			if(curr.college==null)	continue;
			if(curr.college.equals(college) && !f[name]){
				checkEmpty(g,college,curr, f, xyz);
				arr.add(xyz);
			}
		}
		if (arr.isEmpty())	return null;
		return arr; 
	}

	public static void checkEmpty (Networks g, String college, Human human, boolean[] f, ArrayList<String>arr){
		Queue<Human> q=new Queue<>();
		q.enqueue(human);

		while(!q.isEmpty()){
			Human curr=q.dequeue();
			arr.add(curr.name);
			int name=g.map.get(curr.name);
			f[name]=true; 
			Connection ptr= g.people[name].first;

			while(ptr!=null){
				String fcollege= g.people[ptr.fnum].college;
				if(fcollege==null){
				ptr=ptr.next;
				continue; 
				}
				if(!f[ptr.fnum] && fcollege.equals(college)){
					q.enqueue(g.people[ptr.fnum]);
					f[ptr.fnum]=true; 
				}
				ptr=ptr.next;
			}
		}
	}
	
	// returns the people who have common connections from one college/network group to another
	public static ArrayList<String> connectPeople(Networks g) {

		ArrayList<String> arr=new ArrayList<>();

		int x=g.people.length;
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

	private static void conn(Networks g, int curr, boolean[] f, int[] num, int[] p, int prev, ArrayList<String> arr, boolean[] tf, int start){

		if(f[curr])	return;
		f[curr]=true;
		num[curr]=num[prev]+1;
		p[curr]=num[curr];
		Connection ptr=g.people[curr].first;
		while(ptr!=null){

		if(f[ptr.fnum])	p[curr]=Math.min(p[curr],num[ptr.fnum]);
		else{
		conn(g, ptr.fnum,f,num, p,curr, arr, tf, start);

		if(num[curr]<=p[ptr.fnum] && !arr.contains(g.people[curr].name)){
			if(curr!=start||tf[curr]) arr.add(g.people[curr].name);
		}
		if(num[curr]>p[ptr.fnum])	p[curr]=Math.min(p[curr], p[ptr.fnum]);
		tf[curr]=true;
		}
		ptr=ptr.next;
		}
	}
}
