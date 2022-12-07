public class MyThread extends Thread{
    private final Task thisTask;
    private final int id;
    private boolean finished = false;

    public MyThread(int id, Task task){
        this.id = id;
        thisTask = task;
    }

    public void run(){
        thisTask.run();
        finished = true;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getProgress(){
        return "\033[0;42m" +"Thread" + id + "\033[0;39m" + "\t::\t" + thisTask.getTaskProgress();
    }
    public boolean isFinished() {
        return finished;
    }
}
