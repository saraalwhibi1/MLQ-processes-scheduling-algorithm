import java.io.*;
import java.util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OSprojectPh2 {

	static Scanner input = new Scanner(System.in);
	static PCB Q1[];
	static PCB Q2[];
	static PCB pcb[]; // array of all processes
	static int pNO = 0; // number Of Processes
	static int prio; // priority
	static int arrivalT; // arrival time
	static int cpuBur; // CPU burst
	static int counterPrio1 = 0;
	static int counterPrio2 = 0;
	static int ch; // choice from menu

	static Scheduler scheduler;

	public static void main(String[] args) {
		options();
	}

	public static void options() { // provide 4 menu options to the user.
		do {
			System.out.println("1. Enter processes information. ");
			System.out
					.println("2. Report detailed information about each process. ");
			System.out
					.println("3. Report the average turn around time, waiting time, and response time. ");
			System.out.println("4. Exit the program ");

			System.out.print("Enter your choice number (1 to 4) : ");
			ch = input.nextInt();

		} while (ch > 4 || ch < 1);
		switchCh(ch);
	}// END of options METHOD

	public static void switchCh(int ch) {

		switch (ch) {
		case 1: // Enter processes information.
			creation();
			options();
			break;

		case 2: // Report detailed information about each process.
			if (pcb == null) {
				System.out
						.println("You should enter option 1 before option 2!");
				options();
				break;
			} else {
				printConsole();
				printTxt();
				options();
				break;
			}

		case 3: // Report the average turn around time, waiting time, and
				// response time.
			if (pcb == null) {
				System.out
						.println("You should enter option 1 before option 3!");
				options();
				break;
			} else {
				printConsole2();
				printTxt2();
				options();
				break;
			}

		case 4: // Exit the program
			System.out.println("Thank You!");
			break;

		default:
			System.out.println("Invalid choice!");

		}
	}// END of switchCh METHOD

	public static void creation() {
		counterPrio1 = 0;
		counterPrio2 = 0;

		do { // prompt the user to enter the number of processes pNO.
			System.out.println("please Enter the number of processes:");

			try {
				pNO = input.nextInt();
				if (pNO > 0)
					break;
				else
					System.out.println("Enter a valid number");

			} catch (InputMismatchException e) {
				System.out.println("wrong input");
				input.next();
			}
		} while (true);

		// Create a process list and read it from user.
		pcb = new PCB[pNO];

		// prompt the user to enter the arrival time, CPU burst, and the
		// priority of each process.
		for (int i = 0; i < pNO; i++) {
			System.out
					.println("\nplease Enter the following information about process "
							+ (i + 1) + ":");

			// arrival time
			do {

				try {
					System.out.print("Arrival Time:");
					arrivalT = input.nextInt();
					if (arrivalT >= 0)
						break;
					else
						System.out.println("Enter a valid number");

				} catch (InputMismatchException e) {
					System.out.println("wrong input");
					input.next();
				}
			} while (true);

			// CPU burst
			do {

				try {
					System.out.print("CPU burst:");
					cpuBur = input.nextInt();
					if (cpuBur > 0)
						break;
					else
						System.out.println("Enter a valid number");

				} catch (InputMismatchException e) {
					System.out.println("wrong input");
					input.next();
				}
			} while (true);

			// priority
			do {
				try {
					System.out.print("priority(from 1 to 2):");
					prio = input.nextInt();
					if (prio == 1 || prio == 2)
						break;
					else
						System.out.println("Enter a valid number");

				} catch (InputMismatchException e) {
					System.out.println("wrong input");
					input.next();
				}

			} while (true);

			if (prio == 1)
				counterPrio1++;
			if (prio == 2)
				counterPrio2++;

			// Initialize each process
			pcb[i] = new PCB();
			pcb[i].setArrivalTime(arrivalT);
			pcb[i].setCpuBurst(cpuBur);
			pcb[i].setPriority(prio);
			pcb[i].setPID("P" + i);

		} // end for loop

		// Create 2 arrays of PCB representing the Q1 and Q2.
		Q1 = new PCB[counterPrio1];
		Q2 = new PCB[counterPrio2];
		int pointerQ1 = 0;
		int pointerQ2 = 0;

		// assign objects from PCB to Q1 or Q2 correctly.
		for (int i = 0; i < pNO; i++) {
			if (pcb[i].getPriority() == 1) {
				Q1[pointerQ1] = pcb[i];
				pointerQ1++;
			}
			if (pcb[i].getPriority() == 2) {
				Q2[pointerQ2] = pcb[i];
				pointerQ2++;
			}
		}

	}// END creation METHOD

	public static void printConsole() {// to output on the console a report of
										// each process in the PCB array
		schedule();
		System.out.println("Q1 report:");
		for (int i = 0; i < Q1.length; i++) {
			System.out.println(Q1[i].toString());
		}

		System.out.println("Q2 report:");
		for (int i = 0; i < Q2.length; i++) {

			System.out.println(Q2[i].toString());
		}
		System.out.println();
		System.out.println("Processes order chart: "
				+ scheduler.getProcessChart());
		System.out.println();
	}// END of printConsole METHOD

	public static void printTxt() { // Display report 1.

		try {
			schedule();

			String PInfo = "";

			// Q1 printing
			PInfo = "Q1 processes which has higher priority:\n";
			for (int i = 0; i < counterPrio1; i++) {
				PInfo += " PID:" + Q1[i].getPID() + "\t  cpuBurst:"
						+ Q1[i].getCpuBurst() + "\tpriority:"
						+ Q1[i].getPriority() + "\tstartTime:"
						+ Q1[i].getStartTime() + "\tterminationTime"
						+ Q1[i].getTerminationTime() + "\tturnAroundTime:"
						+ Q1[i].getTurnAroundTime() + "\twaitingTime:"
						+ Q1[i].getWaitingTime() + "\tresponseTime:"
						+ Q1[i].getResponseTime() + "\n";
			}

			// Q2 printing
			PInfo += "Q2 processes which has second priority:\n";
			for (int i = 0; i < counterPrio2; i++) {
				PInfo += " PID:" + Q2[i].getPID() + "\t  cpuBurst:"
						+ Q2[i].getCpuBurst() + "\tpriority:"
						+ Q2[i].getPriority() + "\tstartTime:"
						+ Q2[i].getStartTime() + "\tterminationTime:"
						+ Q2[i].getTerminationTime() + "\tturnAroundTime:"
						+ Q2[i].getTurnAroundTime() + "\twaitingTime:"
						+ Q2[i].getWaitingTime() + "\tresponseTime:"
						+ Q2[i].getResponseTime() + "\n";
			}

			PInfo += "\nProcesses order chart: " + scheduler.getProcessChart();

			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"Report1.txt"));
			writer.write(PInfo);
			writer.flush();
			writer.close();

		} catch (IOException ex) {
			Logger.getLogger(OSprojectPh2.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}// END of printTxt METHOD

	public static void printConsole2() {// to output on the console a report of
										// each process in the PCB array
		schedule();

		System.out.println();
		System.out.println("Repor2:");

		int size = Q1.length + Q2.length;
		double totalTurnAround = 0, totalWait = 0, totalResponse = 0;

		for (int i = 0; i < Q1.length; i++) {
			totalWait += Q1[i].waitingTime;
			totalTurnAround += Q1[i].turnAroundTime;
			totalResponse += Q1[i].responseTime;
		}

		for (int i = 0; i < Q2.length; i++) {
			totalWait += Q2[i].waitingTime;
			totalTurnAround += Q2[i].turnAroundTime;
			totalResponse += Q2[i].responseTime;
		}

		System.out.println("Avg turnaround time = " + totalTurnAround / size);
		System.out.println("Avg waiting time    = " + totalWait / size);
		System.out.println("Avg response time   = " + totalResponse / size);
		System.out.println();

	}// END of printConsole2 METHOD

	public static void printTxt2() { // Display report 2.

		try {
			schedule();

			String PInfo = "";

			PInfo = "Report2:\n";
			int size = Q1.length + Q2.length;
			double totalTurnAround = 0, totalWait = 0, totalResponse = 0;

			for (int i = 0; i < Q1.length; i++) {
				totalWait += Q1[i].waitingTime;
				totalTurnAround += Q1[i].turnAroundTime;
				totalResponse += Q1[i].responseTime;
			}

			for (int i = 0; i < Q2.length; i++) {
				totalWait += Q2[i].waitingTime;
				totalTurnAround += Q2[i].turnAroundTime;
				totalResponse += Q2[i].responseTime;
			}

			PInfo += "Avg turnaround time = " + totalTurnAround / size + "\n";
			PInfo += "Avg waiting time    = " + totalWait / size + "\n";
			PInfo += "Avg response time   = " + totalResponse / size + "\n";
			PInfo += "\n";

			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"Report2.txt"));
			writer.write(PInfo);
			writer.flush();
			writer.close();

		} catch (IOException ex) {
			Logger.getLogger(OSprojectPh2.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}// END of printTxt2 METHOD

	private static void schedule() {
		for (int i = 0; i < Q1.length; i++) {
			Q1[i].cpuTime = 0;
			Q1[i].startTime = 0;
			Q1[i].terminationTime = 0;
			Q1[i].turnAroundTime = 0;
			Q1[i].waitingTime = 0;
			Q1[i].responseTime = 0;
		}

		for (int i = 0; i < Q2.length; i++) {
			Q2[i].cpuTime = 0;
			Q2[i].startTime = 0;
			Q2[i].terminationTime = 0;
			Q2[i].turnAroundTime = 0;
			Q2[i].waitingTime = 0;
			Q2[i].responseTime = 0;
		}

		scheduler = new Scheduler(Q1, Q2);
		scheduler.run();
	}

} // end of Class OSprojectPH1

