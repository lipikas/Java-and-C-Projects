package friends;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
import java.util.Scanner; 
public class driver {
	public static void main(String[] args){
		Graph g=null;
		
		try{
			g=new Graph(new Scanner(new File("names.txt")));
		}catch(FileNotFoundException e){
			System.out.println("File not found");
			return; 
		}
		//System.out.println(Friends.cliques(g, "princeton"));
		//Friends f = new Friends();
		System.out.println(Friends.connectors(g));
	}
}