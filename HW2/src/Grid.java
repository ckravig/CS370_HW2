/**
 * Author: Collin Kravig
 * Class: CSCI 370
 * HW2
 * 
 * This program calculates the average in temperature of "plates" in a grid
 * 
 *   Possible Tests:
 *  
 *      * Test Float (Wrapper Class) vs float (primitive) for data in array as far as runtime
 * 
 *      * Test float vs Float runtime
 */

/**
 * This class is the main class that creates the grid and the threads
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CyclicBarrier;

public class Grid {

    //-------------------------// 
    //  Class Level Variables  //
    //-------------------------//

    private static int gridSize;

    private static int totalThreads;

    private static int topRow;

    private static int leftColumn;

    private static int bottomRow;

    private static int rightColumn;

    private static float[] totalErrors;

    private static float[] totalAvgs;

    private static int[] calculations;

    private static long endTime;

    private static long startTime;

    private static float[][] grid;

    //--------------------------------// 
    //  Class Level Thread Variables  //
    //--------------------------------//

    public static String threadType;

    private static CyclicBarrier barrier;

    private static Child threadOps;

    // Creating an array of threads.
    private static Thread[] threads;

    public static String outFileName;

    public static boolean output;

    /**
     * The main function creates a grid of size 10x10, sets the default values for the top, bottom,
     * left, and right rows and columns, and then creates a new thread for each of the 4 threads that
     * will be used to calculate the average of each cell in the grid
     */
    public static void main(String[] args) throws InterruptedException {

    //---------------------// 
    //  Program Variables  //
    //---------------------//

        // Setting the size of the grid. (NxN)
        gridSize = Integer.valueOf(args[0]);

        // This is the number of threads that will be used to calculate the average of each cell in the
        // grid.
        totalThreads = Integer.valueOf(args[1]);

        // These are the default values for the top, bottom, left, and right rows and columns.
        topRow = 90;
        leftColumn = 10;
        bottomRow = 80;
        rightColumn = 20;

        // output file boolean (yes or no)
        output = true;
        // output file name
        outFileName = ("src/output/Desktop/" + gridSize + "x" + gridSize + "-" + totalThreads + "Thr.txt");
        
    //---------------------//
    //---------------------//

        // Creating a 2D array of floats.
        grid = new float[gridSize][gridSize];

        // This is setting the start time of the program to the current time in milliseconds.
        startTime = System.currentTimeMillis();

        endTime = 0;

        calculations = new int[4];

        totalAvgs = new float[4];

        totalErrors = new float[4];

        //-------------// 
        //  Fill Grid  //
        //-------------//

        fillGrid();

        //-----------------------// 
        //  File Output Handler  //
        //-----------------------//

        if (output == true) {
                
            try {
                // append to output file
                PrintStream outFile = new PrintStream(new FileOutputStream(outFileName, true));

                // Assign o to output stream
                // using setOut() method
                System.setOut(outFile);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        //----------------------// 
        //  Print Initial Grid  //
        //----------------------//

        System.out.println();
        System.out.println("Grid Size: " + gridSize);
        System.out.println();
        // System.out.println("Initial Grid:");
        // printGrid();


        // -------------------- //

        barrier = null;

        if (totalThreads == 1) {

            threadType = "Type: Single thread;";

            // create barrier for 1 thread
            barrier = new CyclicBarrier(totalThreads);

            threadOps = new Child(grid, gridSize, 0, totalThreads, barrier, calculations, totalAvgs, totalErrors);

            threadOps.start();

            try {

                threadOps.join();

            } /* End try */

            catch (Exception e) {

                System.out.println(e);

            } // End catch

        } /* End if */
        else {

            threadType = "Type: Multiple thread;";

            barrier = new CyclicBarrier(totalThreads);

            threads = new Thread[totalThreads];

            for (int i = 0; i < totalThreads; i++) {

                threads[i] = new Child(grid, gridSize, i, totalThreads, barrier, calculations, totalAvgs,
                        totalErrors);

                threads[i].start();

            } // End for

            for (int i = 0; i < totalThreads; i++) {

                threads[i].join();

            } // End for

        }

            //--------------------// 
            //  Print Final Grid  //
            //--------------------//

            // System.out.println();
            // System.out.println("Final Grid:");
            // printGrid();

        
            //------------------// 
            //  Print Solution  //
            //------------------//

            System.out.println();

            System.out.println("Number of Calculations: " + (calculations[0]));

            System.out.println();

            System.out.print(threadType);

            System.out.print(" Size: "+ gridSize + "x" + gridSize);


            System.out.print("; Average Grid Value=");
            System.out.printf("%,.1f",
                    + ((totalAvgs[0] + totalAvgs[1] + totalAvgs[2] + totalAvgs[3])
                            / ((gridSize - 2) * (gridSize - 2))));

                            System.out.print("; Total Error=");
                            System.out.printf("%,.2f",(totalErrors[0] + totalErrors[1] + totalErrors[2] + totalErrors[3]));
           
            System.out.println();
            System.out.println();

            System.out.print("Middle grid value: ");
            System.out.printf("%,.2f",grid[gridSize / 2][gridSize / 2]);
            System.out.println();
            
            System.out.println();

            // get total runtime of program
            endTime = System.currentTimeMillis();

            System.out.println("Time: " + (endTime - startTime) + " ms");

            System.out.println();
            

    } 


    // Prints the grid.
    public static void printGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print("|");
                if (grid[i][j] == 0.0) {
                    System.out.print("-----");
                }else{
                    System.out.printf("%,.2f",grid[i][j]);
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }


    
    /**
     * Fills the grid with values
     */
    public static void fillGrid() {
        for (int i = 1; i < gridSize - 1; i++) {

            grid[0][i] = topRow; // Set top row to default values

        } // End for loop

        for (int i = 1; i < gridSize - 1; i++) {

            grid[i][0] = leftColumn; // Set left column to default values

        } // End for loop

        for (int i = 1; i < gridSize - 1; i++) {

            grid[i][gridSize - 1] = rightColumn; // Set right column to default values

        } // End for loop

        for (int i = 1; i < gridSize - 1; i++) {

            grid[gridSize - 1][i] = bottomRow; // Set bottom row to default values

        } // End for loop
    }

} 