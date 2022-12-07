import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException, IOException {

        int numberOfThreads = takeNumberOfThreads();
        int numberOfClockCycles = takeNumberOfCycles();
        TextReader reader = getReader();

        Simulator simulator = new Simulator(numberOfClockCycles, numberOfThreads, reader);
        simulator.start();
    }

    private static int takeNumberOfThreads(){
        int numberOfThreads;
        do{
            try{
                System.out.print("\033[0;39m" + "Enter the number of processors: ");
                String input = sc.next();
                numberOfThreads = Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.out.print("\033[1;31m" +"""
                
                /-----------------------------Error----------------------------\\
                |@@@ Please you have to enter an integer number.. try again @@@|
                \\--------------------------------------------------------------/
                
                """);
                continue;
            }
            break;
        }while(true);

        return numberOfThreads;
    }

    private static int takeNumberOfCycles(){
        int numberOfCycles;
        do{
            try{
                System.out.print("\033[0;39m" + "Enter the number of clock cycles: ");
                String input = sc.next();
                numberOfCycles = Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.out.print("\033[1;31m" +"""
                
                /-----------------------------Error----------------------------\\
                |@@@ Please you have to enter an integer number.. try again @@@|
                \\--------------------------------------------------------------/
                
                """);
                continue;
            }
            break;
        }while(true);

        return numberOfCycles;
    }

    private static TextReader getReader(){
        String txtFileName;
        TextReader reader;
        do{
            try{
                System.out.print("\033[0;39m" + "Enter the text file path: ");
                txtFileName = sc.next();
                if(txtFileName.equals("-1"))
                    System.exit(0);
                reader = new TextReader(txtFileName);
            }catch(IOException e){
                String ANSI_BOLD = "\033[1m";
                System.out.print("\033[1;31m" +"""
                        
                        /---------------------------------------Error---------------------------------------\\
                        |This .txt file does not exist..                                                    |
                        |-Please make sure you typed the name correctly.                                    |
                        |-Please make sure that the .txt file is inside <AbsolutePath>/examples/ directory. |
                        |Please Try Again (Or Enter -1 To Exit)..                                           |
                        \\-----------------------------------------------------------------------------------/
                        
                        """);
                continue;
            }
            break;
        }while(true);
        return reader;
    }
}
