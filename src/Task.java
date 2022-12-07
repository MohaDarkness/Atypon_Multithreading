public class Task implements Runnable{
    private final Object TASKS_SIGNAL;
    private static int unique = 1;
    private final int id;
    private final int priority;
    private final int durationTime;
    private int nowProgress = 1;
    private final String TaskColor;

    public Task(int durationTime, int priority){
        id = unique++;
        this.priority = priority;
        this.durationTime = durationTime;
        this.TASKS_SIGNAL = Simulator.getTasksSignal();

        if((id/5) % 2 == 0)
            this.TaskColor = "\033[1;3"+((id%5)+2)+"m";
        else
            this.TaskColor = "\033[0;3"+((id%5)+2)+"m";
    }
    @Override
    public void run() {
        while(true){
            synchronized (TASKS_SIGNAL){
                try {
                    TASKS_SIGNAL.wait();
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(nowProgress == durationTime) {
                break;
            }
            nowProgress++;
        }
        System.out.println(getTaskName() + " Is Completed..");
    }

    public int getPriority() {
        return priority;
    }

    public int getDurationTime() {
        return durationTime;
    }
    public String getTaskName(){
        return TaskColor + "Task" + id + "\033[0;39m";
    }
    public String getTaskProgress(){
        return TaskColor +"Task" + id + " " + nowProgress +"/"+ durationTime;
    }

}
