import java.io.*;
import java.util.Arrays;
public class TextReader {
    private final BufferedReader txtBufferReader;
    private int[] nextTask;

    public TextReader(String fileName) throws IOException {
        String absolutePath = new File("").getAbsolutePath();
        File txtFile = new File(absolutePath + "/examples/"+fileName+".txt");
        txtBufferReader = new BufferedReader(new FileReader(txtFile));

        txtBufferReader.readLine(); //We don't need the first line in a .txt :: number of tasks
        nextTask = Arrays.stream(txtBufferReader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
    }

    public boolean anyWaitingTask(int cycleNumber){
        if(nextTask[0] == -1)
            return false;
        return nextTask[0] == cycleNumber;
    }

    public Task getTask() throws IOException {
        Task newTask = new Task(nextTask[1], nextTask[2]);
        String st = txtBufferReader.readLine();
        if(st == null)
            nextTask = new int[] {-1, -1, -1};
        else
            nextTask = Arrays.stream(st.split("\\s")).mapToInt(Integer::parseInt).toArray();

        return newTask;
    }

}
