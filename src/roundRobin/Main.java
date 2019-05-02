package roundRobin;

import java.util.ArrayList;

public class Main {

	public static void main(String args[]) {
		System.out.println("Hello World");
		
		ArrayList<Integer> p1IO = new ArrayList<Integer>();
		p1IO.add(2);
		ArrayList<Integer> p2IO = new ArrayList<Integer>();
		ArrayList<Integer> p3IO = new ArrayList<Integer>();
		p3IO.add(1);
		p3IO.add(2);
		
		System.out.println(p2IO.isEmpty());
		
		//ID, START, EXECUTION TIME, IO LIST
		Process p1 = new Process("1", 3, 6, p1IO);
		
		Process p2 = new Process("2", 5, 8, p2IO);
		
		Process p3 = new Process("3", 9, 7, p3IO);
		
		ArrayList<Process> processList = new ArrayList<Process>();
		processList.add(p1);
		processList.add(p2);
		processList.add(p3);
		
		RoundRobin rr = new RoundRobin(4, processList);
		
		rr.runRoundRobin(1);
	}
	
}
