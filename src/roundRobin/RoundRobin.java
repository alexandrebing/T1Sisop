package roundRobin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RoundRobin {
	
	private int timeQuantum = 0;
	private ArrayList<Process> requestQueue = new ArrayList<Process>();
	private ArrayList<Process> processList = new ArrayList<Process>();
	private int timer = 1;
	private String currentProcessStatus = "-";
	private String previousProcessStatus = "-";
	private int executionTime = 0;
	private String response = "";
	
	public RoundRobin(int burstTime, ArrayList<Process> processList) {
		this.timeQuantum = burstTime;
		this.processList = processList;
	}
	
	public void runRoundRobin(int time) {
		
		if (allProcessesFinished()) {
			System.out.println("Processes finished:");
			printResponse();
		} else {
			requestQueue = addToQueue(timer);
			timer += 1;
			// No request for CPU
			if (requestQueue.isEmpty()) {
				currentProcessStatus = "-";
				checkChangeOfContext();
				mountResponse("-");
				previousProcessStatus = currentProcessStatus;
				runRoundRobin(timer);
			} else { //at least one CPU request
				Process currentProcess = requestQueue.get(0);
				currentProcessStatus = currentProcess.getProcessID();
				checkChangeOfContext();
				String status = currentProcess.execute(timer);
				switch (status){
				case "running":
					executionTime += 1;
					mountResponse(currentProcess.getProcessID());
					if(executionTime >= timeQuantum) {
						requestQueue.remove(0);
						currentProcess.setUnscheduled();
						executionTime = 0;
					}
					previousProcessStatus = currentProcessStatus;
					runRoundRobin(timer);
					break;
				case "ioInterrupt":
					requestQueue.remove(0);
					mountResponse(currentProcess.getProcessID());
					currentProcess.setUnscheduled();
					executionTime = 0;					
					previousProcessStatus = currentProcessStatus;
					runRoundRobin(timer);
					break;
				case "finished":
					currentProcess.setFinished();
					requestQueue.remove(0);
					mountResponse(currentProcess.getProcessID());
					executionTime = 0;
					previousProcessStatus = currentProcessStatus;
					runRoundRobin(timer);
					break;
				}
			}
		}
		
		
		
	}
	
	private void checkChangeOfContext() {
		
		if (currentProcessStatus != previousProcessStatus) {
			mountResponse("C");
			timer += 1;
		}
		
	}
	
	private ArrayList<Process> addToQueue(int atTime) {
		
		//PROBLEMA AQUI AO ORDENAR A FILA CONFORME A PRIORIDADE, ACHO.
		Comparator<Process> compareByStart = (Process p1, Process p2) -> Integer.compare(p1.getStart(), p2.getStart());
		Collections.sort(processList, compareByStart);
		ArrayList <Process> queue = requestQueue;
		for(Process p: processList) {
			if(p.getStart() <= atTime && !p.isScheduled() && !p.isFinished()) {
				p.isScheduled();
				queue.add(p);		
			}
		}
		return queue;
		
	}

	private void mountResponse(String res) {
		
		response += res;
		
		System.out.println(response);
	
	}
	
	private void printResponse() {
		
		System.out.println(response);
		
	}
	
	private boolean allProcessesFinished() {
		
		for(Process p: processList) {
			if (!p.isFinished()) {
				return false;
			}
		}
		
		return true;
	}
}
