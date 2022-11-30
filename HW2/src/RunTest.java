import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class RunTest extends Grid {
    public static void main(String[] args) throws InterruptedException {

        String[] passArgs = new String[2];

        String mult = "4";
        String sing = "1";

        String first = "10";
        String second = "100";

        for (int n=0; n<=11; n++){

            if (n == 0) {
                passArgs[0] = first;
                passArgs[1] = sing;
            } else if (n == 1) {
                passArgs[0] = second;
                passArgs[1] = sing;
            } else if (n == 2) {
                passArgs[0] = String.valueOf((Integer.valueOf(second) * n));
                passArgs[1] = sing;
            } else if (n == 3) {
                passArgs[0] = String.valueOf((Integer.valueOf(second) * n));
                passArgs[1] = sing;
            } else if (n == 4) {
                passArgs[0] = String.valueOf((Integer.valueOf(second) * n));
                passArgs[1] = sing;
            } else if (n == 5) {
                passArgs[0] = first;
                passArgs[1] = mult;
            } else if (n == 6) {
                passArgs[0] = second;
                passArgs[1] = mult;
            } else if (n == 7) {
                passArgs[0] = String.valueOf((Integer.valueOf(second) * (n % 5)));
                passArgs[1] = mult;
            } else if (n == 8) {
                passArgs[0] = String.valueOf((Integer.valueOf(second) * (n % 5)));
                passArgs[1] = mult;
            } else if (n == 9) {
                passArgs[0] = String.valueOf((Integer.valueOf(second) * (n % 5)));
                passArgs[1] = mult;
            } else if (n == 10) {
                passArgs[0] = "1000";
                passArgs[1] = sing;
            } else if (n == 11) {
                passArgs[0] = "1000";
                passArgs[1] = mult;
            }

            for(int i=1; i<=5; i++){

                System.out.println("=======================================================");
                System.out.println("Run: " + i);
                Grid.main(passArgs); 

                PrintStream outFile;
                try {
                    outFile = new PrintStream(new FileOutputStream(Grid.outFileName, true));
                    System.setOut(outFile);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                System.out.println("=======================================================");
            }
        }
              
    }
 }