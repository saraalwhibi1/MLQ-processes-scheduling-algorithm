public class Scheduler {

	final public static int CPU_IDLE = 0;
	final public static int CPU_BUSY = 1;

	private PCB Q1[];
	private PCB Q2[];
	private String processChart = "|";

	public Scheduler(PCB Q1[], PCB Q2[]) {
		
		// Sort Q1 by burst time.
		for (int i = 0; i < Q1.length; i++) {
			for (int j = i + 1; j < Q1.length; j++) {
				if (Q1[i].cpuBurst > Q1[j].cpuBurst) {
					PCB temp = Q1[i];
					Q1[i] = Q1[j];
					Q1[j] = temp;
				}
			}
		}

		this.Q1 = Q1;

		// Sort Q2 by arrival time.
		for (int i = 0; i < Q2.length; i++) {
			for (int j = i + 1; j < Q2.length; j++) {
				if (Q2[i].arrivalTime > Q2[j].arrivalTime) {
					PCB temp = Q2[i];
					Q2[i] = Q2[j];
					Q2[j] = temp;
				}
			}
		}

		this.Q2 = Q2;
	}

	private int nextArrived(PCB q[], int time) {
		for (int i = 0; i < q.length; i++) {
			if (q[i].arrivalTime <= time) {
				return i;
			}
		}
		return -1;
	}

	private PCB next(int clockTime) {

		for (int i = 0; i < Q1.length; i++) {
			if (Q1[i].arrivalTime <= clockTime) {
				PCB p = Q1[i];
				Q1 = remove(Q1, i);
				return p;
			}
		}

		for (int i = 0; i < Q2.length; i++) {
			if (Q2[i].arrivalTime <= clockTime) {
				PCB p = Q2[i];
				Q2 = remove(Q2, i);
				return p;
			}
		}

		return null;
	}
	
	private PCB[] add(PCB[] q, PCB p, int clockTime) {
		PCB array[] = new PCB[q.length + 1];
		int i = 0;
		for (int j = 0; j < q.length && q[j].arrivalTime <= clockTime;)
			array[j++] = q[i++];
		
		array[i] = p;
		for (int j = i; j < q.length; j++)
			array[j+1] = q[j];

		return array;
	}


	private PCB[] remove(PCB[] q, int i) {
		PCB array[] = new PCB[q.length - 1];
		int j = 0;
		for (j = 0; j < i; j++)
			array[j] = q[j];

		for (; j < array.length; j++)
			array[j] = q[j + 1];

		return array;
	}

	public void run() {
		int clockTime = 0;
		int cpuStatus = CPU_IDLE;
		PCB runningProcess = null;

		while (Q1.length != 0 || Q2.length != 0 || cpuStatus == CPU_BUSY) {

			if (cpuStatus == CPU_IDLE) {
				PCB next = next(clockTime); //returns the process that arrived at clock time or less.
				if (next != null) {
					runningProcess = cpuExcute(next, clockTime);
					cpuStatus = CPU_BUSY; //now there is a process running in cpu
				}
			} else if (runningProcess != null && runningProcess.priority == 2
					&& Q1.length != 0) { //if running process has lower priority and there's processes in Q1.
				
				int idx = nextArrived(Q1, clockTime); //index of process that arrived at clocktime or before it.
				if (idx >= 0) { //swap
					Q2 = add(Q2, runningProcess, clockTime);
					PCB p = Q1[idx];
					Q1 = remove(Q1, idx);
					runningProcess = cpuExcute(p, clockTime);
					cpuStatus = CPU_BUSY;
				}
			}

			clockTime++;

			if (cpuStatus == CPU_BUSY) {
				runningProcess.cpuTime++;
				if (runningProcess.cpuTime == runningProcess.cpuBurst) {
					cpuTerminate(runningProcess, clockTime);
					runningProcess = null;
					cpuStatus = CPU_IDLE;
				}
			}
		}
	}

	private PCB cpuExcute(PCB p, int clockTime) {
		if (p.cpuTime == 0) //if process haven't entered cpu yet
			p.startTime = clockTime; //the time the process entered cpu

		processChart +=  p.PID + " |";

		return p;
	}

	private void cpuTerminate(PCB p, int clockTime) { //calculations
		p.terminationTime = clockTime;
		p.waitingTime = p.terminationTime - p.arrivalTime - p.cpuBurst;
		p.responseTime = p.startTime - p.arrivalTime;
		p.turnAroundTime = p.terminationTime - p.arrivalTime;
	}

	public String getProcessChart() {
		return processChart;
	}
}
