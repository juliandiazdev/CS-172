
public class Sudoku {

    /** Prints the solution to the puzzle in the specified directory. */
    public static void main(String[] args) {
        String puzzle = new In("sudoku1.txt").readAll();
        Square[][] grid = createSquares(puzzle);
        solve(grid);
        StdOut.println(toString(grid));
    }

    /** Returns a new Location object with the specified row and column. */
    static Location createLocation(int r, int c) {
        // TODO You have to write this
        Location coord = new Location();
        coord.row = r;
        coord.column = c;
        return coord;
    }

    /** Returns an array of the eight Locations in the same row as here. */
    static Location[] findRow(Location here) {
        Location row[]=new Location[8];
        int j=0;
        for (int i=0; i<8; i++) {
            if (j==here.column) {
                j++;
            }
            row[i]=Sudoku.createLocation(here.row, j);
            j++;
        }
        return row;
    }

    /** Returns an array of the eight Locations in the same column as here. */
    static Location[] findColumn(Location here) {
        Location column[]=new Location[8];
        int j=0;
        for (int i=0; i<8; i++) {
            if (j==here.row) {
                j++;
            }
            column[i]=Sudoku.createLocation(j, here.column);
            j++;
        }
        return column;
    }

    /** Returns an array of the eight Locations in the same 3x3 block as here. */
    static Location[] findBlock(Location here) {
        // TODO You have to write this
        Location block[]=new Location[8];
        int c = here.column/3*3;
        int r = here.row/3*3;

        for ( int i = 0 ; i < block.length ; ++i){
            if (c == here.column && r == here.row){
                i--;
            }
            else{
                if (r >= 0 && r <= 8 && c >= 0 && c <= 8){
                    block[i]= Sudoku.createLocation(r,c);
                }
            }

            c++;
            if (c >= here.column/3*3+3){
                c = here.column/3*3;
                r++;
            }
        }

        return block;
    }

    /**
     * Returns a 9x9 array of Squares, each of which has value 0 and knows about the other squares in its row,
     * column, and block.
     */
    static Square[][] createSquares() {
        // TODO You have to write this
        Square[][] grid = new Square[9][9];
        for ( int r = 0 ; r < 9; r++){
            for ( int c = 0 ; c < 9; c++){
                grid[r][c]=new Square();
                grid[r][c].value = 0;
                grid[r][c].row=new Square[8];
                grid[r][c].column=new Square[8];
                grid[r][c].block=new Square[8];
            }
        }

        for ( int r = 0 ; r < 9; r++){
            for ( int c = 0 ; c < 9; c++){
                Location[] row = findRow(createLocation(r,c));
                Location[] column = findColumn(createLocation(r,c));
                Location[] block = findBlock(createLocation(r,c));
                for ( int i = 0 ; i < 8; i++){
                    grid[r][c].row[i]=grid[row[i].row][row[i].column];
                    grid[r][c].column[i]=grid[column[i].row][column[i].column];
                    grid[r][c].block[i]=grid[block[i].row][block[i].column];
                }
            }
        }
        return grid;
    }

    /** Returns a String representing grid, showing the numbers (or . for squares with value 0). */
    static String toString(Square[][] grid) {
        // TODO You have to write this

        String s = "";

        for ( int r = 0 ; r < 9; ++r){

            for ( int c = 0 ; c < 9; ++c){

                if (grid[r][c].value == 0){

                    s += ".";

                }
                else{

                    s += grid[r][c].value;

                }

                if(c==8){

                    s += "\n";

                }

            }

        }


        return s;
    }

    /**
     * Returns a 9x9 array of Squares, each of which has value 0 and knows about the other squares in its row,
     * column, and block. Any numbers in diagram are filled in to the grid; empty squares should be given as
     * periods.
     */
    static Square[][] createSquares(String diagram) {
        // TODO You have to write this
        Square[][] grid = createSquares();

        for (int r = 0; r < 9; ++r){

            for (int c = 0; c < 9; ++c){

                int value = diagram.substring(r*10, r*10 + 9).charAt(c);
                if (value == '.'){
                    grid[r][c].value = 0;
                }
                else{
                    grid [r][c].value = value - 48;
                }

            }

        }
        return grid;
    }

    /**
     * Returns a boolean array of length 10. For each digit, the corresponding entry in the array is true
     * if that number does not appear elsewhere in s's row, column, or block.
     */
    static boolean[] findValidNumbers(Square s) {
        // TODO You have to write this
        boolean[] checkArray = new boolean[10];

        for (int i = 0; i < 10 ; ++i){

            checkArray[i] = true;

            for (int j = 0; j<8 ; ++j){

                if (s.row[j].value == i || s.column[j].value == i || s.block[j].value == i) {
                    checkArray[i]=false;
                    break;
                }
            }
        }
        return checkArray;
    }

    /**
     * Returns true if grid can be solved. If so, grid is modified to fill in that solution.
     */
    static boolean solve(Square[][] grid) {
        // TODO You have to write this
        // Here's an outline of the algorithm:
        // for each square
        //     if its value is 0
        //         for each valid number that could be filled in
        //             if you can solve the rest of the grid
        //                 return true
        //         nothing worked: set value back to 0 and return false
        // no squares left to fill in: return true

        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 9; ++y) {
                Square square = grid[x][y];
                if (square.value == 0) {
                    boolean[] squareValue = findValidNumbers(square);
                    for (int k = 1; k < 10; k++) {
                        if (squareValue[k]) {
                            grid[x][y].value = k;
                            if (solve(grid)) return true;

                            grid[x][y].value = 0;
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

}

