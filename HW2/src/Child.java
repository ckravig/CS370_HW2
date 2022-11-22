import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * This class is a child class that extends the Thread class and implements the Runnable interface. It
 * has a constructor that takes in a 2D array, an integer, an integer, an integer, a CyclicBarrier, an
 * integer array, a float array, and a float array. It also has a run() method that calculates the
 * average of the surrounding cells and sets the new average to the cell
 */
public class Child extends java.lang.Thread implements Runnable {

    // Initialize variables to be used

    public CyclicBarrier barrier;

    int len;
    float grid[][];
    int threadNum;
    int totalThreads;
    int midLen;
    int num_of_cells;
    int row;
    int col;
    float[] totalAvg;
    float[] totalError;
    int[] iterations;

    int rowLength;
    int colLength;

   // This is a constructor that takes in a 2D array, an integer, an integer, an integer, a CyclicBarrier,
   // an integer array, a float array, and a
   //  * float array.
    public Child(float[][] grid, int len, int threadNum, int totalThreads, CyclicBarrier barrier, int[] iterations,
            float[] totalAvg, float[] totalError) {

        
        // Setting the variables to the variables passed in the constructor.
        this.grid = grid;
        this.len = len;
        this.threadNum = threadNum;
        this.totalThreads = totalThreads;
        this.barrier = barrier;
        this.midLen = len - 2;
        this.num_of_cells = midLen * midLen;
        this.iterations = iterations;
        this.totalAvg = totalAvg;
        this.totalError = totalError;

        if (totalThreads == 4) {

            switch (threadNum) {

                case 0 -> {

                    this.row = 1;
                    this.col = 1;
                    this.rowLength = midLen / 2;
                    this.colLength = midLen / 2;
                    break;

                } // End case 0

                case 1 -> {

                    this.row = 1;
                    this.col = midLen / 2 + 1;
                    this.rowLength = (midLen / 2);
                    this.colLength = col + ((midLen / 2) - 1);
                    break;

                } // End case 1

                case 2 -> {

                    this.row = midLen / 2 + 1;
                    this.col = 1;
                    this.rowLength = row + ((midLen / 2) - 1);
                    this.colLength = (midLen / 2);
                    break;

                } // End case 2

                case 3 -> {

                    this.row = midLen / 2 + 1;
                    this.col = midLen / 2 + 1;
                    this.rowLength = row + ((midLen / 2) - 1);
                    this.colLength = col + ((midLen / 2) - 1);
                    break;

                } // End case 3

            } // End switch

        } // End if

        else if (totalThreads == 1) {

            this.row = 1;
            this.col = 1;
            this.rowLength = midLen;
            this.colLength = midLen;

        } // End else if

    } // End Child()

    @Override

    public void run() {

        try {

            do {

                barrier.await();

                // Reset total error and total average for next iteration
                totalError[threadNum] = 0;
                totalAvg[threadNum] = 0;

                for (int i = row; i <= rowLength; i++) {

                    for (int j = col; j <= colLength; j++) {

                        // Reset new and previous average
                        float prevAvg = 0;
                        float newAvg = 0;

                        prevAvg = grid[i][j];

                        // Calculate average of surrounding cells
                        float average = (grid[i - 1][j] + grid[i + 1][j] + grid[i][j - 1] + grid[i][j + 1]);

                        // Set new average to cell
                        grid[i][j] = ((average / 4) * 10.0f) / 10.0f;
                        newAvg = grid[i][j];
                        totalAvg[threadNum] += newAvg;

                        float error = newAvg - prevAvg;

                        totalError[threadNum] = totalError[threadNum] + error;

                    } // End for

                } // End for

                iterations[threadNum] = iterations[threadNum] + 1;

                // Wait for threads
                barrier.await();

                // System.out.println("Current Total Error: " + (totalError[0] + totalError[1] + totalError[2] + totalError[3]));

            } while ((totalError[0] + totalError[1] + totalError[2] + totalError[3]) > 5);

        } // End try

        catch (BrokenBarrierException e) {

            barrier.reset();
            throw new RuntimeException(e);

        } // End catch

        catch (InterruptedException e) {

            throw new RuntimeException(e);

        } // End catch

    } // End run()

} // End Child()