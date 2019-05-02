package roundRobin;

import java.util.ArrayList;

public class Process {
	
	public ArrayList<Integer> ioEntries = new ArrayList<Integer>();
	private String processId;
	private int executionTime;
	private int startTime;
	private boolean scheduled = false;
	private boolean finished = false;
	private int ioTime = 3;
	private int timeLeft;
	private int nextIO = 0;
	private int currentOp = 0;
	
	
	public Process(String id, int at, int executionTime, ArrayList<Integer> entries) {
		this.processId = id;
		this.startTime = at;
		this.executionTime = executionTime;
		this.ioEntries = entries;
		this.timeLeft = executionTime;
		if(hasIO()) {
			this.nextIO = this.ioEntries.get(0);
		}
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public String getProcessID() {
		return processId;
	}
	
	public void setUnscheduled() {
		scheduled = false;
	}
	
	public void setScheduled() {
		scheduled = true;
	}
	
	public void setFinished() {
		finished = true;
	}
	
	public int getStart() {
		return startTime;
	}
	
	public String execute(int startingAt) {
		startTime = startingAt;
		timeLeft -= 1;
		
		if (timeLeft == 0) {
			return "finished";
		}
		if (hasIO()) {
			nextIO -= 1;
			if (nextIO == 0) {
				changeIOContext();
				startTime += ioTime;
				return "ioInterrupt";
			}
		}
		startTime += 1;
		return "running";
	}
	
	private boolean hasIO() {
		System.out.println(ioEntries.isEmpty());
		return !ioEntries.isEmpty();
	}
	
	public boolean willBeInterrupted() {
		
		if (hasIO()) {
			return false;
		}
		
		return ioEntries.get(currentOp) <= timeLeft;
	}
	
	private void changeIOContext() {
		int operations = ioEntries.size();
		
		if (operations>1) {
			if (currentOp < operations -1) {
				currentOp += 1;
			} else {
				currentOp = 0;
			}
		} else {
			currentOp = 0;
		}
		nextIO = ioEntries.get(currentOp);
	}

	public boolean isScheduled() {
		return scheduled;
	}
	
	public int compareTo(Process p0) {
		
		return startTime - p0.startTime;
		
	}
	

}
