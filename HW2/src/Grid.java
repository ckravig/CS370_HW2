public class Grid {

    // how many times averages were calculated
    static int calcs = 0;


    public static void main(String[] args) throws Exception {
        
        // create 5x5 grid (2D array) from document provided
        Double[][] grid = {
            {null,30.00,30.00,30.00,null},
            {15.00,null,null,null,72.00},
            {15.00,null,null,null,72.00},
            {15.00,null,null,null,72.00},
            {null,75.00,75.00,75.00,null}
        };
        System.out.println("Grid Lenght: " + grid.length);
        
        //----------------------// 
        //  PRINT INITIAL GRID  //
        //----------------------//

        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (Double[] rows: grid) {
            // iterate through the given row to access the data
            for(Double data: rows) {
                System.out.print("|");
                if (data == null) {
                    System.out.print("----");
                }else{
                    System.out.print(data);
                }
                
            }
            System.out.print("|");
            System.out.println();
        }

        // first grid average
        calcAvg(grid);

        // grid average recalculation
        reCalcAvg(grid);

        System.out.println();

    }

    public static void calcAvg(Double[][] grid) {
        
        //----------------------// 
        //  Calculate Averages  //
        //----------------------//

        System.out.println();
        System.out.println("Calculate averages: ");
        System.out.println();

        // current column and row
        int c = 0; // column
        int r = 0; // row


        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (Double[] rows: grid) {

            // reset current column
            c = 0;

            // iterate through the given row to access the data
            for(Double data: rows) {

                System.out.print("|");

                // check for empty spots in the grid
                if (data == null) {
                    
                    // divisor for average calculation
                    Double divisor = 4.00;

                    // average calculation temp values
                    Double top = 0.00;
                    Double left = 0.00;
                    Double right = 0.00;
                    Double bot = 0.00;

                    // check top number
                    if (r != 0 && grid[(r-1)][c] != null) {
                        top = grid[(r-1)][c];
                    }

                    // check bottom number
                    if (r != (rows.length - 1) && grid[(r+1)][c] != null) {
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
                        divisor = 2.00;
                    }

                    Double avg = ((top + bot + left + right) / divisor);

                    // store average in array and print to new grid
                    grid[r][c] = avg;
                    System.out.printf("%,.2f", grid[r][c]);

                }else{
                    System.out.printf("%,.2f",data);
                }
                c++;
            }
            System.out.print("|");
            System.out.println();
            r++;
        }

        calcs++;
    }

    public static void reCalcAvg(Double[][] grid) {
        
        //----------------------// 
        //  Calculate Averages  //
        //----------------------//

        System.out.println();
        System.out.println("Recalculate averages: ");
        System.out.println();

        // current column and row
        int c = 0; // column
        int r = 0; // row


        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (Double[] rows: grid) {

            // reset current column
            c = 0;

            // iterate through the given row to access the data
            for(Double data: rows) {

                System.out.print("|");

                // check that we aren't on the sides of the grid
                if ((c != 0 && c != (grid.length - 1)) && (r != 0 && r != (rows.length - 1))) {
                    
                    // divisor for average calculation
                    Double divisor = 4.00;

                    // average calculation temp values
                    Double top = 0.00;
                    Double left = 0.00;
                    Double right = 0.00;
                    Double bot = 0.00;

                    // check top number
                    if (r != 0) {
                        top = grid[(r-1)][c];
                    }

                    // check bottom number
                    if (r != (rows.length - 1)) {
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

                    Double avg = ((top + bot + left + right) / divisor);

                    // store average in array and print to new grid
                    grid[r][c] = avg;
                    System.out.printf("%,.2f", grid[r][c]);

                }else{
                    System.out.printf("%,.2f",data);
                }
                c++;
            }
            System.out.print("|");
            System.out.println();
            r++;
        }

        calcs++;
    }
}
