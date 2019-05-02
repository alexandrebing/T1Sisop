package roundRobin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RoundRobin {
	
	private int timeQuantum = 0;
	private ArrayList<Process> requestQueue = new ArrayList<Process>();
	private ArrayList<Process> processList = new ArrayList<Process>();
	private int timer = 0;
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
			this.requestQueue = addToQueue(timer);
			timer += 1;
			// No request for CPU
			if (requestQueue.isEmpty()) {
				this.currentProcessStatus = "-";
				checkChangeOfContext();
				mountResponse("-");
				this.previousProcessStatus = this.currentProcessStatus;
				runRoundRobin(timer);
			} else { //at least one CPU request
				Process currentProcess = this.requestQueue.get(0);
				this.currentProcessStatus = currentProcess.getProcessID();
				checkChangeOfContext();
				String status = currentProcess.execute(timer);
				switch (status){
				case "running":
					this.executionTime += 1;
					this.mountResponse(currentProcess.getProcessID());
					if(this.executionTime >= this.timeQuantum) {
						this.requestQueue.remove(0);
						currentProcess.setUnscheduled();
						this.executionTime = 0;
					}
					this.previousProcessStatus = this.currentProcessStatus;
					runRoundRobin(timer);
					break;
				case "ioInterrupt":
					this.requestQueue.remove(0);
					this.mountResponse(currentProcess.getProcessID());
					currentProcess.setUnscheduled();
					this.executionTime = 0;					
					this.previousProcessStatus = this.currentProcessStatus;
					runRoundRobin(timer);
					break;
				case "finished":
					currentProcess.setFinished();
					this.requestQueue.remove(0);
					this.mountResponse(currentProcess.getProcessID());
					this.executionTime = 0;
					this.previousProcessStatus = this.currentProcessStatus;
					this.runRoundRobin(timer);
					break;
				}
			}
		}
		
		
		
	}
	
	private void checkChangeOfContext() {
		
		if (this.currentProcessStatus != this.previousProcessStatus) {
			this.mountResponse("C");
			this.timer += 1;
		}
		
	}
	
	private ArrayList<Process> addToQueue(int atTime) {
		
		//PROBLEMA AQUI AO ORDENAR A FILA CONFORME A PRIORIDADE, ACHO.
		System.out.println(processList);
		System.out.println("Then:");
		Comparator<Process> compareByStart = (Process p1, Process p2) -> Integer.compare(p1.getStart(), p2.getStart());
		Collections.sort(processList, compareByStart);
		System.out.println(processList);
		ArrayList <Process> queue = this.requestQueue;
		for(Process p: processList) {
			if(p.getStart() <= atTime && !p.isScheduled() && p.isFinished()) {
				p.isScheduled();
				queue.add(p);		
			}
		}
		return queue;
		
	}

	private void mountResponse(String res) {
		
		this.response += res;
		
		System.out.println(this.response);
	
	}
	
	private void printResponse() {
		
		System.out.println(this.response);
		
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
