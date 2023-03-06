public class PCB {

	String PID;// process ID
	int priority;
	int cpuBurst;
	int arrivalTime; //the time the process entered ready queue
	int startTime; //the time the process entered cpu
	int terminationTime;
	int turnAroundTime;
	int waitingTime;
	int responseTime;
	int cpuTime; //starts from 0 and ends at cpu burst

	public PCB() {
		PID = "";
		priority = 0;
		cpuBurst = 0;
		arrivalTime = 0;
		startTime = 0;
		terminationTime = 0;
		turnAroundTime = 0;
		waitingTime = 0;
		responseTime = 0;
		cpuTime = 0;
	}

	public String getPID() {
		return PID;
	}

	public void setPID(String pID) {
		PID = pID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getCpuBurst() {
		return cpuBurst;
	}

	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getTerminationTime() {
		return terminationTime;
	}

	public void setTerminationTime(int terminationTime) {
		this.terminationTime = terminationTime;
	}

	public int getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "PCB [PID=" + PID + ", priority=" + priority + ", cpuBurst="
				+ cpuBurst + ", arrivalTime=" + arrivalTime + ", startTime="
				+ startTime + ", terminationTime=" + terminationTime
				+ ", turnAroundTime=" + turnAroundTime + ", waitingTime="
				+ waitingTime + ", responseTime=" + responseTime + "]";
	}

}