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
 *      * Test Double vs Float runtime
 */

/**
 * This class is the main class that creates the grid and the threads
 */


import java.util.concurrent.CyclicBarrier;

public class Grid {

    // Initialize variables to be used

    // Declaring a variable called gridSize and setting it to private and static.
    private static int gridSize;
    private static int totalThreads;
    private static int topRow;
    private static int leftColumn;
    private static int bottomRow;
    private static int rightColumn;
    private static double[] totalErrors;
    private static double[] totalAvgs;
    private static int[] calculations;
    private static long endTime;
    private static long startTime;
    private static double[][] grid;
    private static CyclicBarrier barrier;
    private static Child threadOps;
    // Creating an array of threads.
    private static Thread[] threads;

    /**
     * The main function creates a grid of size 10x10, sets the default values for the top, bottom,
     * left, and right rows and columns, and then creates a new thread for each of the 4 threads that
     * will be used to calculate the average of each cell in the grid
     */
    public static void main(String[] args) throws InterruptedException {

        gridSize = 1000;
        totalThreads = 4;
        topRow = 90;
        leftColumn = 10;
        bottomRow = 80;
        rightColumn = 20;

        grid = new double[gridSize][gridSize];

        startTime = System.currentTimeMillis();

        endTime = 0;

        calculations = new int[4];

        totalAvgs = new double[4];

        totalErrors = new double[4];

        // Fill grid with default border values

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

        grid[0][0] = (grid[0][1] + grid[1][0]) / 2;

        grid[0][gridSize - 1] = (grid[0][gridSize - 2] + grid[1][gridSize - 1]) / 2;

        grid[gridSize - 1][0] = (grid[gridSize - 2][0] + grid[gridSize - 1][1]) / 2;

        grid[gridSize - 1][gridSize - 1] = (grid[gridSize - 2][gridSize - 1] + grid[gridSize - 1][gridSize - 2]) / 2;


        //----------------------// 
        //  Print Initial Grid  //
        //----------------------//

        // printGrid();


        // -------------------- //

        barrier = null;

        if (totalThreads == 1) {

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

            // print out results of calculations
            System.out.println("Statistics:");

            System.out.println("Iterations: " + (calculations[0]));

            if (totalThreads == 1) {

                System.out.println("Type: Single thread");

            } // End if

            if (totalThreads != 1) {

                System.out.println("Type: Multi thread (" + totalThreads + ")");

            } // End if

            System.out.println("Size: (" + gridSize + "x" + gridSize + ")");

            System.out.println("Total grid average: "
                    + ((totalAvgs[0] + totalAvgs[1] + totalAvgs[2] + totalAvgs[3])
                            / ((gridSize - 2) * (gridSize - 2))));

            System.out.println("Total error: " + ((totalErrors[0] + totalErrors[1] + totalErrors[2] + totalErrors[3])));

            // get total runtime of program

            endTime = System.currentTimeMillis();

            System.out.println("Time is " + (endTime - startTime) + " ms");

        } /* End if */
        else {

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

            //--------------------// 
            //  Print Final Grid  //
            //--------------------//

            // System.out.println();
            // printGrid();


            //------------------// 
            //  Print Solution  //
            //------------------//

            System.out.println();

            System.out.println("Number of Calculations: " + (calculations[0]));

            System.out.println();

            if (totalThreads == 1) {

                System.out.print("Type: Single thread;");

            } 

            if (totalThreads != 1) {

                System.out.print("Type: Multi thread;");

            }

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

} 