import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock; 
// TODO (Feature 3): import java.util.concurrent.Semaphore;

// ANSI Color Codes for enhanced terminal output (same as Assignment 1)
class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
}

// ⚠️ DO NOT REMOVE OR MODIFY THIS CLASS ⚠️
class RaceWindow {
    static void pause() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Global simulation settings
class Config {
    static final int TIME_SCALE = 5;
}

// ⚠️ SHARED STATISTICS (Feature 2 Protected) ⚠️
class SharedStats {
    static int totalProcesses = 0;            
    static int totalCPUs = 0;                     
    static int contextSwitchCount = 0;        
    static int completedProcessCount = 0;     
    static long totalServiceTime = 0;         
    static final java.util.concurrent.atomic.AtomicInteger busyWaitPolls = new java.util.concurrent.atomic.AtomicInteger(0);

    /**
     * =========================================================================
     * 📌 FEATURE 2: Mutual Exclusion for Shared Statistics
     * -------------------------------------------------------------------------
     * Description:
     * - Defined `statsLock` to ensure synchronization and protect shared statistics.
     * - Prevents race conditions when multiple CPUs update completed processes, 
     *   context switches, and total service time simultaneously.
     * =========================================================================
     */
    private static final ReentrantLock statsLock = new ReentrantLock();

    public static void incrementContextSwitch() {
        statsLock.lock(); // Acquire lock before modifying context switch counter
        try {
            int tmp = contextSwitchCount;        
            RaceWindow.pause();                      
            contextSwitchCount = tmp + 1;        
        } finally {
            statsLock.unlock(); // Always release lock safely
        }
    }

    public static void incrementCompletedProcess() {
        statsLock.lock(); // Acquire lock before modifying completed process counter
        try {
            int tmp = completedProcessCount;
            RaceWindow.pause();
            completedProcessCount = tmp + 1;
        } finally {
            statsLock.unlock(); // Release lock
        }
    }

    public static void addServiceTime(int ms) {
        statsLock.lock(); // Acquire lock before updating total service time
        try {
            long tmp = totalServiceTime;
            RaceWindow.pause();
            totalServiceTime = tmp + ms;
        } finally {
            statsLock.unlock(); // Release lock
        }
    }

    public static int getCompletedProcessCount() {
        return completedProcessCount;
    }

    public static int getContextSwitchCount() {
        return contextSwitchCount;
    }

    public static long getTotalServiceTime() {
        return totalServiceTime;
    }
}

// ⚠️ THE READY QUEUE (Feature 1 Protected) ⚠️
class ReadyQueue {
    private final LinkedList<Thread> list = new LinkedList<>();   
    private final Map<Thread, Process> map = new HashMap<>();     

    /**
     * =========================================================================
     * 📌 FEATURE 1: Mutual Exclusion for the Ready Queue
     * -------------------------------------------------------------------------
     * Description:
     * - Declared a ReentrantLock to ensure only one CPU can access 
     *   the ready queue (`list` and `map`) at a time, avoiding race conditions.
     * =========================================================================
     */
    private final ReentrantLock queueLock = new ReentrantLock();

    public void add(Process process) {
        queueLock.lock(); 
        try {
            Thread thread = new Thread(process);
            list.add(thread);
            map.put(thread, process);

            System.out.println(Colors.BLUE + "  ➕ " + Colors.BOLD + Colors.CYAN + process.getName() + Colors.RESET +
                    Colors.BLUE + " (Priority: " + process.getPriority() + ") added to ready queue" + Colors.RESET +
                    " │ Remaining: " + Colors.YELLOW + process.getRemainingTime() + "ms" + Colors.RESET +
                    " of " + process.getBurstTime() + "ms");
        } finally {
            queueLock.unlock(); 
        }
    }

    public Thread poll() {
        queueLock.lock(); 
        try {
            if (list.isEmpty()) {
                return null;
            }
            Thread head = list.getFirst();       
            RaceWindow.pause();                      
            list.removeFirst();                      
            return head;
        } finally {
            queueLock.unlock(); 
        }
    }

    public Process processOf(Thread thread) {
        queueLock.lock();
        try {
            return map.get(thread);
        } finally {
            queueLock.unlock();
        }
    }

    public String snapshot() {
        queueLock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            for (Thread t : list) {
                if (sb.length() > 0) sb.append(" → ");
                sb.append(map.get(t).getName());
            }
            return sb.length() == 0 ? "empty" : sb.toString();
        } finally {
            queueLock.unlock();
        }
    }
}

// Class representing a process
class Process implements Runnable {
    private final String name;
    private final int burstTime;              
    private final int timeQuantum;            
    private int remainingTime;
    private final int priority;               
    private final long creationTime;          
    private long completionTime = 0;
    private volatile int cpuId = 0;         

    public Process(String name, int burstTime, int timeQuantum, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime;
        this.priority = priority;
        this.creationTime = System.currentTimeMillis();
    }

    public void setCpuId(int cpuId) {
        this.cpuId = cpuId;
    }

    @Override
    public void run() {
        int runTime = Math.min(timeQuantum, remainingTime);

        SharedStats.incrementContextSwitch();

        System.out.println(Colors.BRIGHT_GREEN + "  ▶ [CPU-" + cpuId + "] " + Colors.BOLD + Colors.CYAN + name +
                Colors.RESET + Colors.GREEN + " (Priority: " + priority + ") executing quantum" + Colors.RESET +
                " [" + runTime + "ms]");

        try {
            Thread.sleep(runTime / Config.TIME_SCALE);      
        } catch (InterruptedException e) {
            System.out.println(Colors.RED + "  ✗ " + name + " was interrupted." + Colors.RESET);
        }

        remainingTime -= runTime;
        SharedStats.addServiceTime(runTime);

        int overallProgress = (int) (((double) (burstTime - remainingTime) / burstTime) * 100);
        System.out.println(Colors.YELLOW + "  ⏸ [CPU-" + cpuId + "] " + Colors.CYAN + name + Colors.RESET +
                " completed quantum " + Colors.BRIGHT_YELLOW + runTime + "ms" + Colors.RESET +
                " │ Overall progress: " + createProgressBar(overallProgress, 20) +
                "  remaining: " + remainingTime + "ms" + Colors.RESET);

        if (remainingTime > 0) {
            System.out.println(Colors.BLUE + "  ↻ " + Colors.CYAN + name + Colors.RESET +
                    " yields CPU for context switch");
        } else {
            completionTime = System.currentTimeMillis();
            SharedStats.incrementCompletedProcess();
            System.out.println(Colors.BRIGHT_GREEN + "  ✓ " + Colors.BOLD + Colors.CYAN + name + Colors.RESET +
                    Colors.BRIGHT_GREEN + " finished execution!" + Colors.RESET);
        }
    }

    private String createProgressBar(int progress, int width) {
        int filled = (progress * width) / 100;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < width; i++) {
            bar.append(i < filled ? Colors.GREEN + "█" + Colors.RESET : Colors.WHITE + "░" + Colors.RESET);
        }
        bar.append("] ").append(progress).append("%");
        return bar.toString();
    }

    public String getName() { return name; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public int getPriority() { return priority; }
    public boolean isFinished() { return remainingTime <= 0; }

    public long getTurnaroundTime() {
        return completionTime == 0 ? 0 : (completionTime - creationTime) * Config.TIME_SCALE;
    }

    public long getWaitingTime() {
        return completionTime == 0 ? 0 : Math.max(0, getTurnaroundTime() - burstTime);
    }
}

// CpuWorker
class CpuWorker extends Thread {
    private final int cpuId;
    private final ReadyQueue readyQueue;

    public CpuWorker(int cpuId, ReadyQueue readyQueue) {
        super("CPU-" + cpuId);
        this.cpuId = cpuId;
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        while (SharedStats.getCompletedProcessCount() < SharedStats.totalProcesses) {

            Thread thread = readyQueue.poll();
            if (thread == null) {
                SharedStats.busyWaitPolls.incrementAndGet();      
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    return;
                }
                continue;
            }

            Process process = readyQueue.processOf(thread);
            System.out.println(Colors.MAGENTA + "  ┌─ [CPU-" + cpuId + "] picks " + Colors.BRIGHT_CYAN +
                    process.getName() + Colors.MAGENTA + " │ Ready queue: [" + Colors.BRIGHT_WHITE +
                    readyQueue.snapshot() + Colors.MAGENTA + "]" + Colors.RESET);

            process.setCpuId(cpuId);
            thread.start();                     
            try {
                thread.join();                 
            } catch (InterruptedException e) {
                System.out.println("CPU-" + cpuId + " interrupted.");
            }

            if (!process.isFinished()) {
                readyQueue.add(process);         
            }
        }
    }
}

public class SchedulerSimulationSync {
    public static void main(String[] args) {
        int studentID = 445052389;  

        Random random = new Random(studentID);

        int timeQuantum = 2000 + random.nextInt(4) * 1000;        
        int numProcesses = 10 + random.nextInt(11);                 
        int[] burstTimes = new int[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            burstTimes[i] = timeQuantum / 2 + random.nextInt(2 * timeQuantum + 1);
        }
        int numCPUs = 2 + random.nextInt(3);
        Random priorityRandom = new Random(studentID + 1L);

        SharedStats.totalProcesses = numProcesses;
        SharedStats.totalCPUs = numCPUs;
        ReadyQueue readyQueue = new ReadyQueue();
        List<Process> allProcesses = new ArrayList<>();

        printHeader(studentID, numProcesses, timeQuantum, numCPUs);

        for (int i = 0; i < numProcesses; i++) {
            Process process = new Process("P" + (i + 1), burstTimes[i], timeQuantum, 1 + priorityRandom.nextInt(10));
            allProcesses.add(process);
            readyQueue.add(process);
        }

        System.out.println("\n" + Colors.BOLD + Colors.GREEN + "╔════════════════════════════════════════════════════════════════════════════════╗" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.GREEN + "║" + Colors.RESET + Colors.BG_GREEN + Colors.WHITE + Colors.BOLD +
                "                    ▶ " + numCPUs + " CPUs STARTING (CONCURRENT SCHEDULER)  ◀                    " +
                Colors.RESET + Colors.BOLD + Colors.GREEN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.GREEN + "╚════════════════════════════════════════════════════════════════════════════════╝" + Colors.RESET + "\n");

        List<CpuWorker> cpus = new ArrayList<>();
        for (int i = 1; i <= numCPUs; i++) {
            CpuWorker cpu = new CpuWorker(i, readyQueue);
            cpus.add(cpu);
            cpu.start();
        }

        long expectedMs = 0;
        for (int b : burstTimes) expectedMs += b / Config.TIME_SCALE;
        long timeoutMs = 3 * expectedMs / numCPUs + 15000;
        long deadline = System.currentTimeMillis() + timeoutMs;
        boolean stuck = false;
        try {
            for (CpuWorker cpu : cpus) {
                long left = deadline - System.currentTimeMillis();
                if (left > 0) cpu.join(left);
                if (cpu.isAlive()) { stuck = true; }
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        int expectedQuanta = 0;
        long expectedService = 0;
        for (int b : burstTimes) {
            expectedQuanta += (b + timeQuantum - 1) / timeQuantum;      
            expectedService += b;
        }

        if (stuck) {
            System.out.println("\n" + Colors.BOLD + Colors.RED + "⛔ WATCHDOG: the simulation did not finish (" + timeoutMs / 1000 +
                    " s). A process was lost, or a CPU is blocked forever, or the 'completed' counter lost an update." + Colors.RESET);
        }
        printResults(allProcesses, expectedQuanta, expectedService, numProcesses);
        if (stuck) {
            System.exit(1);
        }
    }

    private static void printHeader(int studentID, int numProcesses, int timeQuantum, int numCPUs) {
        String line = "═".repeat(87);
        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_CYAN + "╔" + line + "╗" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + Colors.BG_BLUE + Colors.BRIGHT_WHITE + Colors.BOLD +
                "            CONCURRENT CPU SCHEDULER SIMULATION (ASSIGNMENT 3 - SYNCHRONIZATION)            " +
                Colors.RESET + Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "╠" + line + "╣" + Colors.RESET);
        printHeaderRow("  Processes:         ", String.valueOf(numProcesses));
        printHeaderRow("  Time Quantum:    ", timeQuantum + "ms (nominal, simulated " + Config.TIME_SCALE + "x faster)");
        printHeaderRow("  CPUs:              ", String.valueOf(numCPUs));
        printHeaderRow("  Student ID:        ", String.valueOf(studentID));
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "╚" + line + "╝" + Colors.RESET + "\n");
    }

    private static void printHeaderRow(String label, String value) {
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + Colors.YELLOW + label + Colors.RESET +
                Colors.BRIGHT_YELLOW + String.format("%-68s", value) + Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
    }

    private static void printResults(List<Process> processes, int expectedQuanta, long expectedService, int numProcesses) {
        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_GREEN + "═══ VERIFICATION (expected values are computed from YOUR seed) ═══" + Colors.RESET);
        boolean ok = true;
        ok &= verifyLine("Context switches (quanta executed)", expectedQuanta, SharedStats.getContextSwitchCount());
        ok &= verifyLine("Completed processes", numProcesses, SharedStats.getCompletedProcessCount());
        ok &= verifyLine("Total service time (ms)", expectedService, SharedStats.getTotalServiceTime());
        ok &= verifyLine("Busy-waiting polls (must be 0)", 0, SharedStats.busyWaitPolls.get());
        System.out.println(ok
                ? Colors.BOLD + Colors.BRIGHT_GREEN + "  ✔ All shared values are consistent." + Colors.RESET
                : Colors.BOLD + Colors.RED + "  ✘ At least one check failed (see the ✘ lines above): a race condition or busy waiting is still present." + Colors.RESET);

        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_CYAN + "═══ Process Summary Table (nominal ms) ═══" + Colors.RESET);
        System.out.println(Colors.BOLD + String.format("%-9s %-9s %-11s %-13s %-13s", "Process", "Priority", "Burst", "Waiting", "Turnaround") + Colors.RESET);
        System.out.println("─".repeat(58));
        for (Process p : processes) {
            System.out.println(String.format("%-9s %-9d %-11d %-13d %-13d",
                    p.getName(), p.getPriority(), p.getBurstTime(), p.getWaitingTime(), p.getTurnaroundTime()));
        }
        System.out.println();
    }

    private static boolean verifyLine(String label, long expected, long observed) {
        boolean good = expected == observed;
        System.out.println(String.format("  %-38s expected %-8d observed %-8d ", label, expected, observed) +
                (good ? Colors.BRIGHT_GREEN + "✔" : Colors.RED + "✘ MISMATCH") + Colors.RESET);
        return good;
    }
}
