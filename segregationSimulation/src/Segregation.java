import static java.lang.Math.*;
//Farjad and Julian

public class Segregation {

    public static int gridScale = 8;
    public static double emptyRatio = 0.3;
    public static double ratioBlue = 0.5;
    public static double ratioRed = 1 - ratioBlue;
    public static double gridSquares = 1;
    public static double satisfactionRatio = 0.5;


    public static void main ( String[] args ){

        //int gridScale = 10; // dimensions, i.e if 10, then dimensions are 10 x 10
        //double gridSquares = 1;// ratio, if there are 10, then 1 gridSquare is equal to 1

        //StdDraw.setScale(0,100);
        //double emptyRatio = 0.3; // ratio of how many empty spaces there are
        //double colourRatio = 0.5; // ratio of colours
        //double ratioBlue = 0.50; // These have to be put here as they are used by the function, modify values here if you want to change things up
        //double ratioRed = 1 - ratioBlue; // Red will just be what blue is not, so that inconsistency by the user is avoided

        //double satisfactionRatio = 0.5; // the percentage of similar neighbors required to make an agent happy

        int[][] coloursGrid = new int[ gridScale ][ gridScale ];
        assignColours(coloursGrid,emptyRatio,gridScale,ratioBlue,ratioRed); // calling the function beneath to assign colours to the given array
        makeGrid( coloursGrid , gridSquares);
        sorting(assignColours(coloursGrid,emptyRatio,gridScale,ratioBlue,ratioRed),satisfactionRatio,coloursGrid);


    }

    public static int[][] assignColours (int[][] coloursGrid, double emptyRatio, int gridScale, double ratioBlue, double ratioRed ){

        double numGrids = gridScale * gridScale; // is used to avoid writing down the assigned operation over and over

        double emptyGrids = round((emptyRatio * numGrids));

        double blueGrids = floor((numGrids - emptyGrids) * ratioBlue); // find the remaining grids and part them into red or blue

        double redGrids = floor((numGrids - emptyGrids) * ratioRed);

        System.out.println( "Empty : " + emptyGrids + "\nBlue :" + blueGrids + "\nRed :" + redGrids);

        //int[] colorNum = {(int)emptyGrids,(int)blueGrids,(int)redGrids};


        return coloursGrid;
    }
        /*for ( int i = 0 ; i < coloursGrid.length ; i++){

            for ( int j = 0 ;  j < coloursGrid.length ; j++){

                    double randomNum = StdRandom.uniform();
                    int colourRef;

                    if ( randomNum <= emptyRatio)

            }

        }*/



    public static void makeGrid ( int[][] coloursGrid , double gridSquares){

        double numGrids = gridScale * gridScale; // is used to avoid writing down the assigned operation over and over

        double emptyGrids = round((emptyRatio * numGrids));

        double blueGrids = floor((numGrids - emptyGrids) * ratioBlue); // find the remaining grids and part them into red or blue

        double redGrids = floor((numGrids - emptyGrids) * ratioRed);

        StdDraw.clear();
        StdDraw.setYscale(-0.5,gridScale - 0.5 );
        StdDraw.setXscale(-0.5, gridScale - 0.5);
        boolean taken[][]= new boolean [coloursGrid.length][coloursGrid.length];
        //int[][] colourRefArray = new int [coloursGrid.length][coloursGrid.length];
        int[] colourNumbers = {(int)emptyGrids,(int)blueGrids,(int)redGrids};
        int i=0;
        int j=0;
        int colourReference[]= {0,1,2};
        System.out.println(colourNumbers[0] + colourNumbers[1] + colourNumbers[2]);
        while (colourReference[i]<=2) {
            if (colourReference[i]==0) {
                StdDraw.setPenColor(StdDraw.WHITE);
            }
            else if (colourReference[i]==1) {
                StdDraw.setPenColor(StdDraw.BLUE);
            }
            else if (colourReference[i]==2) {
                StdDraw.setPenColor(StdDraw.RED);
            }
            j=0;
            while (j<colourNumbers[i]) {
                int x = (int)(Math.random() * gridScale);
                int y = (int)(Math.random() * gridScale);
                if (taken[x][y] == false) {
                    StdDraw.filledSquare(x, y, 0.5);
                    //colourRefArray[x][y]= i;
                    //System.out.println(colourRefArray[x][y]);
                    taken[x][y] = true;
                    j++;
                } else {
                    while (taken[x][y] == true) {
                        x = (int) (random() * gridScale);
                        y = (int) (random() * gridScale);
                    }
                    StdDraw.filledSquare(x, y, 0.5);
                    taken[x][y] = true;
                    j++;
                }
            }
            i++;
        }
    }

    public static int[] Neighbors(int[][] coloursGrid, int x, int y) {

        int i = 0;
        int[] neighbors = new int[8];

        for (int x1 = x - 1; x1 <= x + 1; x1++) {


            for (int y1 = y - 1; y1 <= y + 1; y1++) {


                if (x1 >= 0 && x1 < gridScale && y1 >= 0 && y1 < gridScale && (x1 != x || y1 != y) ) {

                    neighbors[i] = coloursGrid[x1][y1];

                    i++;

                }
            }
        }

        return neighbors;
    }

    public static void sorting(int[][] assignedColours, double satisfactionRatio, int[][] coloursGrid) {
        for (int x = 0; x < assignedColours.length; x++) {
            for (int y = 0; y < assignedColours.length; y++) {
                int same = 0, count = 0;
                int agent = assignedColours[x][y];

                if (agent == 0) continue;

                int[] neighbors = Neighbors(assignedColours, x, y);

                for (int n : neighbors) {
                    if (n != 0) {

                        count++;

                    }
                    if (n == agent) {
                        same++;
                    }
                }

                if (count != 0 && (double) (same / count) < satisfactionRatio) {
                    // Agent is dissatisfied, change location.
                    relocateAgent(assignedColours, x, y, agent);
                    makeGrid(coloursGrid, 1);
                }
            }
        }
    }

    public static void relocateAgent (int[][] assignedColours, int x, int y, int agent) {
        boolean vacant = false;
        while (! vacant) {
            int x2 = StdRandom.uniform(assignedColours.length);
            int y2 = StdRandom.uniform(assignedColours.length);
            if (assignedColours[x2][y2] == 0) {
                assignedColours[x][y] = assignedColours[x2][y2];
                assignedColours[x2][y2] = agent;
                vacant = true;
            }
        }
    }
}








/*
    public static findUnsatisfied() {
        int size = ;
        int neighborcounter=0;
        int badneighborcounter=0;
        int count = 0;
        for (int x1 = x - 1; x1 <= x + 1; ++x1) {
            for (int y1 = y - 1; y1 <= y + 1; ++y1) {
                if (x1 >= 0 && x1 < size && y1 >= 0 && y1 < size && (x1 != x || y1 != y)) {
                    if (grid[x1][y1] && ) {
                        neighborcounter++;
                        if (neighbor is opposite color) {
                            badneighborcounter++;
                        }
                    }
                }
            }
        }
    }
    public static relocateAgent () {
        agent[x][y]
        for (int x1 = x - 1; x1 <= x + 1; ++x1) {
            for (int y1 = y - 1; y1 <= y + 1; ++y1) {
                if (x1 >= 0 && x1 < size && y1 >= 0 && y1 < size && (x1 != x || y1 != y)) {
                    if (minefield[x1][y1] && colorRef==0) {
                        switch colors;
                    }
                }
            }
        }
    }

}*/
