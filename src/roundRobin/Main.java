package roundRobin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String args[]) throws FileNotFoundException, IOException {

		int processNumber = 1;
		
		ArrayList<Process> processList = new ArrayList<Process>();
		
		
		//READING FROM FILE
		Scanner s = new Scanner(System.in);
    	System.out.println("Digite o nome do arquivo de entrada (sem extensão .txt)");
    	String enter = s.nextLine();
    	System.out.println();
    	System.out.println("====================================================================================");
    	File file = new File("src/"+ enter + ".txt");
    	BufferedReader in = new BufferedReader(new FileReader(file));
        int procCount = 0;
        
        System.out.println("Entrada lida:");
        String line = in.readLine();
        int procNumber = Integer.parseInt(line);
        System.out.println(line);
        if(procNumber <= 0) System.exit(0);
        
        line = in.readLine();
        System.out.println(line);
        int cpuTime = Integer.parseInt(line);
        
        for (int i = 0; i < procNumber; i++) {
        	line = in.readLine();
        	System.out.println(line);
        	String data [] = line.split(" ");
        	String processId = Integer.toString(processNumber);
        	processNumber += 1;
        	int start = Integer.parseInt(data[0]);
        	int duration = Integer.parseInt(data[1]);
        	ArrayList<Integer> ioList = new ArrayList();
        	for(int j = 2; j < data.length; j++ ) {
        		ioList.add(Integer.parseInt(data[j]));
        	}
        	
        	Process p = new Process(processId, start, duration, ioList);
        	
        	processList.add(p);
        }
        
        System.out.println();
		
		RoundRobin rr = new RoundRobin(cpuTime, processList);
		
		rr.runRoundRobin(1);
	}
	
}
