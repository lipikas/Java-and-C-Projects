package network;
import java.io.*;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args){
		Evaluator g=null;
		try{
			g=new Evaluator(new Scanner(new File("1.txt")));
		}catch(FileNotFoundException e){
			System.out.println("File not found");
		}
		System.out.println(Networks.connectors(g));
	}
}
