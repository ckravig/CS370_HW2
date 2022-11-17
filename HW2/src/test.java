import java.util.ArrayList;
import java.util.List;

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

public class test{

    //-------------------------------------------------------------------------------------------// 

    //-------------------------// 
    //  Class Level Variables  //
    //-------------------------//

    // how many times averages were calculated
    public static int calcs = 0;

    // store total error
    public static Float tError = 0.00f;

    // average grid value
    public static Float tAvg = 0.00f;

    // total grid size
    public static int tGrid = 0;

    // minimum number of "plates" in grid
    public static int tPlates = 25;

    // top row value
    public static Float gTop = 30.00f;

    // bottom row value
    public static Float gBot = 75.00f;

    // left row value
    public static Float gLeft = 15.00f;

    // right row value
    public static Float gRight = 72.00f;

    //--------------------------// 
    //  Multithreading Section  //
    //--------------------------//

    // thread count
    public static int numChild = 4;
    public static String threading = "";

    public static Float[] threadError;

    //-------------------------------------------------------------------------------------------//

    /**
     * The main function is a Java program that takes a 2D array of numbers and averages the numbers
     * in the array until the error is less than 5
     */
    public static void main(String[] args) throws InterruptedException {
        
        //-----------------------// 
        //  CREATE INITIAL GRID  //
        //-----------------------//

        // current plate count
        int cPlates = 0;

        // current rows and columns
        int cRows = 0;
        int cCols = 0;

        // figure out how large the 2D array needs to be
        // make sure grid is divisable by 4 to be optimized for 
        // 4 threads
        while ((cPlates < tPlates) || (cRows%4 != 0)) {
            cPlates = 0;
            cRows++;
            cCols++;
            cPlates = cRows * cCols;
        }

        Float[][] grid = new Float[cRows][cCols];

        // calculate thread range
        int range = cRows/numChild;

        List<Thread> threads = new ArrayList<Thread>();

        // store grid starting row for threads
        int begin = 0;

        threadError = new Float[numChild];

        // Spawn children processes
        for (int i=0; i<numChild; i++) {
            Thread t = new Thread(new Child(begin, begin+range, grid),""+i);
            threads.add(t);
            t.start();
            begin += range;
            threadError[Integer.parseInt(t.getName())] = 0.00f;
        }

        // Wait for children to finish
        for (Thread each : threads) {
            each.join();
        }
        System.out.println("All Children Done: " + numChild);

        
        //----------------------// 
        //  PRINT INITIAL GRID  //
        //----------------------//

        // System.out.println();
        // System.out.println("Starting Grid:");
        // System.out.println();

        // printGrid(grid);
        

        //--------------------// 
        //  PRINT FINAL GRID  //
        //--------------------//

        // System.out.println();
        // System.out.println("Final Grid: ");
        // System.out.println();

        printGrid(grid);

        // determine threading type
        if (numChild != 1) {
            threading = "Multiple Thread";
        }else{
            threading = "Single Thread";
        }

        //tAverage(grid);

        System.out.println("Total Calulations = " + calcs/numChild);

        System.out.println();
        System.out.print("Type: " + threading + "; Size=" + tGrid);
        System.out.print("; Average Grid Value=");
        System.out.printf("%,.1f",tAvg);
        System.out.print("; Total Error=");
        System.out.printf("%,.2f",tError);
        System.out.println();
        System.out.println();

    }


    //-------------------------------------------------------------------------------------------//

    //-----------------------------// 
    //  First Average Calculation  //
    //-----------------------------//

    /**
     * The function iterates through the 2D array, row by row, and checks for empty spots in the grid.
     * If an empty spot is found, the function calculates the average of the surrounding numbers and
     * stores the average in the empty spot
     * 
     * @param grid the 2D array that holds the data
     */

    public static void calcAvgThreadTest(Float[][] grid, int begin, int end) {

        //----------------------// 
        //  Calculate Averages  //
        //----------------------//

        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (int r = begin; r < end; r++) {

            // iterate through the given row to access the data
            for(int c = 0; c < grid.length; c++) {

                // check for empty spots in the grid
                if (grid[r][c] == null && (c != 0 && c != (grid.length - 1)) && (r != 0 && r != (grid.length - 1))) {
                    
                    // divisor for average calculation
                    Float divisor = 4.00f;

                    // average calculation temp values
                    Float top = 0.00f;
                    Float left = 0.00f;
                    Float right = 0.00f;
                    Float bot = 0.00f;

                    // check top number
                    if (r != 0 && grid[(r-1)][c] != null) {
                        top = grid[(r-1)][c];
                    }

                    // check bottom number
                    if (r != (end - 1) && grid[(r+1)][c] != null) {
                        bot = grid[(r+1)][c];
                    }

                    // check left number
                    if (c != 0 && grid[r][(c-1)] != null) {
                        left = grid[r][(c-1)];
                    }

                    // check right number
                    if (c != (grid.length - 1) && grid[r][(c+1)] != null) {
                        right = grid[r][(c+1)];
                    }

                    // check for corners to adjust divisor
                    if (c == 0 || r == 0) {
                        divisor = 2.00f;
                    }

                    Float avg = ((top + bot + left + right) / divisor);

                    // store average in array and print to new grid
                    grid[r][c] = avg;
                }
            }
        }

        calcs++;
    }

    //-------------------------------------------------------------------------------------------// 

    //----------------------------------// 
    //  Repeated Average Calculations   //
    //----------------------------------//

    /**
     * The function takes a 2D array of Floats and calculates the average of the four surrounding
     * cells and replaces the current cell with the average
     * 
     * @param grid the 2D array that holds the data
     */

    public static void reCalcAvgThreadTest(Float[][] grid, int begin, int end) {
        
        // zFloat cError = 0.00f;

        //----------------------// 
        //  Calculate Averages  //
        //----------------------//

        

            // add to total repeated calculations total
            test.calcs++;

            // iterate through the 2D array, row by row by accessing the
            // individual arrays within the 2D array
            for (int r = begin; r < end; r++) {

                // iterate through the given row to access the data
                for(int c = 0; c < grid.length; c++) {


                    // check that we aren't on the sides of the grid
                    if ((c != 0 && c != (grid.length - 1)) && (r != 0 && r != (grid.length - 1))) {
                        
                        // divisor for average calculation
                        Float divisor = 4.00f;

                        // average calculation temp values
                        Float top = 0.00f;
                        Float left = 0.00f;
                        Float right = 0.00f;
                        Float bot = 0.00f;

                        // check top number
                        if (r != 0 && (grid[(r-1)][c]) != null) {
                            top = grid[(r-1)][c];
                        }

                        // check bottom number
                        if (r != (end - 1)) {
                            bot = grid[(r+1)][c];
                        }

                        // check left number
                        if (c != 0) {
                            left = grid[r][(c-1)];
                        }

                        // check right number
                        if (c != (grid.length - 1)) {
                            right = grid[r][(c+1)];
                        }

                        Float avg = ((top + bot + left + right) / divisor);

                        // calculate error (|new avg - old avg|)
                        Float error = Math.abs(avg - grid[r][c]);

                        // append error to total error

                        threadError[Integer.parseInt(Thread.currentThread().getName())] += error;

                        // store average in array and print to new grid
                        grid[r][c] = avg;
                    }
                }
            }
    }

    //-------------------------------------------------------------------------------------------//

    public static void tAverage(Float[][] grid){

        // calculates the total average of the insides

        // store current total
        Float total = 0.00f;

        // current column and row
        int c = 0; // column
        int r = 0; // row

        int insideGrid = 0;

        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (Float[] rows: grid) {
            // reset current column
            c = 0;
            // iterate through the given row to access the data
            for(Float data: rows) {
                if ((c != 0 && c != (grid.length - 1)) && (r != 0 && r != (rows.length - 1))) {
                    total += data;
                    insideGrid++;
                }
                c++;
                tGrid++;
            }
            r++;
        }

        // possibly change for multithreading
        tAvg = total / (insideGrid);
    }

    //-------------------------------------------------------------------------------------------//

    public static void fillGridThreadTest(Float[][] grid, int begin, int end) {

        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (int r = begin; r < end; r++) {

            
            // iterate through the given row to access the data
            for(int c = 0; c < grid.length; c++) {
                        
                // populate top row
                if(r == 0 && (c != 0 && c != (grid.length - 1))){
                    grid[r][c] = gTop;
                }

                // populate bottom row
                if(r == (grid.length - 1) && (c != 0 && c != (grid.length - 1))){
                    grid[r][c] = gBot;
                }

                // populate left row
                if(c == 0 && (r != 0 && r != (grid.length - 1))){
                    grid[r][c] = gLeft;
                }

                // populate right row
                if(c == (grid.length - 1) && (r != 0 && r != (grid.length - 1))){
                    grid[r][c] = gRight;
                }

                
            }
            
        }

    }

    //-------------------------------------------------------------------------------------------//

    public static void printGrid(Float[][] grid) {
        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (Float[] rows: grid) {
            // iterate through the given row to access the data
            for(Float data: rows) {
                System.out.print("|");
                if (data == null) {
                    System.out.print("-----");
                }else{
                    System.out.printf("%,.2f",data);
                }
                
            }
            System.out.print("|");
            System.out.println();
        }
    }

    
}


class Child implements Runnable {

    private int begin;
    private int end;

    private Float[][] tGrid;

    public Child(int begin, int end, Float[][] grid) {
        this.begin = begin;
        this.end = end;
        this.tGrid = grid;
    }
    

    @Override
    public void run() {
                
        test.fillGridThreadTest(tGrid, begin, end);

        

        test.calcAvgThreadTest(tGrid, begin, end);


        System.out.println("Current Thread: " + Thread.currentThread().getName());

        
        test.reCalcAvgThreadTest(tGrid, begin, end);


        
        

        while (test.threadError[Integer.parseInt(Thread.currentThread().getName())] > 5) {
            // System.out.println("Current Total Error: " + test.tError);
            test.threadError[Integer.parseInt(Thread.currentThread().getName())] = 0.00f;
            test.tError = 0.00f;
            test.reCalcAvgThreadTest(tGrid, begin, end);
            test.tError += test.threadError[Integer.parseInt(Thread.currentThread().getName())];
            System.out.println("Thread: " + Thread.currentThread().getName() + " Total Error: " + test.threadError[Integer.parseInt(Thread.currentThread().getName())]);
        }
        
        
        
        
        
    }

    
}