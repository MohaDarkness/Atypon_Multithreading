public class Clock implements Runnable{
    private final int numberOfCycles;
    private static int nowCycle;
    private final Object SIMULATOR_SIGNAL;

    public Clock(int numberOfCycles, Object SIMULATOR_SIGNAL){
        this.numberOfCycles = numberOfCycles;
        this.SIMULATOR_SIGNAL = SIMULATOR_SIGNAL;
        
        nowCycle = 1;
    }

    @Override
    public void run() throws RuntimeException{
        while(nowCycle <= numberOfCycles){
            synchronized (SIMULATOR_SIGNAL){
                SIMULATOR_SIGNAL.notifyAll();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            increase();
        }
        System.exit(0);
    }
    public static int getNowCycle(){
        return nowCycle;
    }
    private void increase(){
        nowCycle++;
    }


}
