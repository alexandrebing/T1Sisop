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
		return this.finished;
	}
	
	public String getProcessID() {
		return this.processId;
	}
	
	public void setUnscheduled() {
		this.scheduled = false;
	}
	
	public void setScheduled() {
		this.scheduled = true;
	}
	
	public void setFinished() {
		this.finished = true;
	}
	
	public int getStart() {
		return this.startTime;
	}
	
	public String execute(int startTime) {
		this.startTime = startTime;
		this.timeLeft -= 1;
		
		if (timeLeft == 0) {
			return "finished";
		}
		if (hasIO()) {
			this.nextIO -= 1;
			if (this.nextIO == 0) {
				changeIOContext();
				startTime += ioTime;
				return "ioInterrupt";
			}
		}
		startTime += 1;
		return "running";
	}
	
	private boolean hasIO() {
		System.out.println(this.ioEntries.isEmpty());
		return !this.ioEntries.isEmpty();
	}
	
	public boolean willBeInterrupted() {
		
		if (hasIO()) {
			return false;
		}
		
		return ioEntries.get(currentOp) <= timeLeft;
	}
	
	private void changeIOContext() {
		int operations = this.ioEntries.size();
		
		if (operations>1) {
			if (this.currentOp < operations -1) {
				this.currentOp += 1;
			} else {
				this.currentOp = 0;
			}
		} else {
			this.currentOp = 0;
		}
		this.nextIO = this.ioEntries.get(this.currentOp);
	}

	public boolean isScheduled() {
		return this.scheduled;
	}
	
	public int compareTo(Process p0) {
		
		return startTime - p0.startTime;
		
	}
	

}
