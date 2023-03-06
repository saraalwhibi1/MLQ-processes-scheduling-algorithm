# MLQ-processes-scheduling-algorithm
This project involves implementing a Multilevel Queue (MLQ) scheduling algorithm with two 
queues, system processes queue numbered as Q1 and batch processes queue numbered as Q2 with 
specific assigned priority for each queue (Q1 has a higher priority than Q2). Each process must occupy 
the respective queue with specific priority range according to its priority (1 or 2). The scheduler follows 
non-preemptive Shortest-Job-First (SJF) scheduling algorithm for processes in Q1 and First Come First 
Serve (FCFS) scheduling algorithm for processes in Q2. The multilevel queue-scheduling algorithm is
implemented as a fixed preemptive priority scheduling for each and every queue. It is assumed that 
processes arrive at different times, so the scheduler supports higher-priority processes preempting 
processes with lower priorities when process arrives at high priority queue. 
Note: If a new process arrives at the same time that a process releases the CPU, then the new process 
will be added to the ready queue first.
