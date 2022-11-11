public class Grid {
    public static void main(String[] args) throws Exception {
        
        // create 5x5 grid (2D array) from document provided
        Integer[][] grid = {
            {null,30,30,30,null},
            {15,null,null,null,72},
            {15,null,null,null,72},
            {15,null,null,null,72},
            {null,75,75,75,null}
        };

        // iterate through the 2D array, row by row by accessing the
        // individual arrays within the 2D array
        for (Integer[] rows: grid) {
            // iterate through the given row to access the data
            for(Integer data: rows) {
                System.out.print("|");
                if (data == null) {
                    System.out.print("--");
                }else{
                    System.out.print(data);
                }
                
            }
            System.out.print("|");
            System.out.println();
        }

    }
}
