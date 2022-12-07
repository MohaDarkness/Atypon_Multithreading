import java.io.IOException;

public class Scheduler implements Runnable {
    private final int numberOfThreads;
    private final Object SCHEDULER_SIGNAL;
    private final TextReader reader;
    private final TasksPQ tasksQueue = new TasksPQ();
    private MyThread[] threads;


    public Scheduler(int numberOfThreads, TextReader reader, Object SCHEDULER_SIGNAL){
        this.numberOfThreads = numberOfThreads;
        this.reader = reader;
        this.SCHEDULER_SIGNAL = SCHEDULER_SIGNAL;
    }

    @Override
    public void run() {
        threads = new MyThread[numberOfThreads];

        while(true){
            synchronized (SCHEDULER_SIGNAL){
                try {
                    SCHEDULER_SIGNAL.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            while(reader.anyWaitingTask(Clock.getNowCycle())){
                try {
                    Task newTask = reader.getTask();
                    tasksQueue.insert(newTask);
                    System.out.println("Initiated " + newTask.getTaskName() + "...");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            for(int i = 0; i < threads.length; i++){
                if(!tasksQueue.isEmpty()){
                    if(threads[i] == null || threads[i].isFinished()){
                        Task toThreadTask = tasksQueue.pop();
                        threads[i] = new MyThread(i+1, toThreadTask);
                        threads[i].start();
                        System.out.println(toThreadTask.getTaskName() + " was added to Thread"+(i+1));
                    }
                }
            }
        }
    }

    public String getTasksInsideQueue(){
        return tasksQueue.printQueue();
    }
    public String getThreadsInfo(){
        StringBuilder info = new StringBuilder();
        for(int i = 0; i < threads.length; i++){
            if(threads[i] == null || threads[i].isFinished()){
                info.append("Thread").append(i + 1).append("\033[0;39m").append("\t::\tEmpty\n");
            }
            else{
                info.append(threads[i].getProgress()).append("\033[0;39m\n");
            }
        }
        return info.toString();
    }
}
