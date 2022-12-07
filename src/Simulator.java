public class Simulator {
    private final int NUMBER_OF_CYCLES;
    private final int NUMBER_OF_THREADS;
    private final TextReader READER;
    private final Object SCHEDULER_SIGNAL = new Object();
    private final Object SIMULATOR_SIGNAL = new Object();
    private static final Object TASKS_SIGNAL = new Object();
    private Scheduler scheduler;
    private Clock clock;

    public Simulator(int numberOfCycles, int numberOfThreads, TextReader reader){
        this.NUMBER_OF_CYCLES = numberOfCycles;
        this.NUMBER_OF_THREADS = numberOfThreads;
        this.READER = reader;
    }

    public void start() throws InterruptedException {
        scheduler = new Scheduler(NUMBER_OF_THREADS, READER, SCHEDULER_SIGNAL);
        clock = new Clock(NUMBER_OF_CYCLES, SIMULATOR_SIGNAL);

        Thread schedulerThread = new Thread(scheduler);
        Thread clockThread = new Thread(clock);

        schedulerThread.start();
        Thread.sleep(50);
        clockThread.start();

        while(clockThread.isAlive()){
            synchronized (SIMULATOR_SIGNAL){
                SIMULATOR_SIGNAL.wait();
            }

            System.out.println("\033[0;39m" + "_________\nCLOCK:: " + Clock.getNowCycle());
            synchronized(SCHEDULER_SIGNAL){
                SCHEDULER_SIGNAL.notifyAll();
            }

            Thread.sleep(100);

            printTasksInQueue();
            printThreadsInfo();

            Thread.sleep(100);

            synchronized (TASKS_SIGNAL){
                TASKS_SIGNAL.notifyAll();
            }

        }
    }

    public static Object getTasksSignal(){
        return TASKS_SIGNAL;
    }
    private void printTasksInQueue(){
        System.out.println(scheduler.getTasksInsideQueue());
        System.out.println();
    }
    private void printThreadsInfo(){
        System.out.println(scheduler.getThreadsInfo());
    }

}
