import java.awt.*;

public class SegregationAttempt {

    public static void main(String args[]) {

        int scaleGrid = 10;// change this to choose the number of grid element/boxes you would like, currently is 30 x 30
        double gridSquares = 1;  // This is to set the relative scale of a box from the grid to 1 unit = 1 box, it has to be here because else it will not be taken as a parameter


        double percentageEmpty = 0.3; // self-explanatory
        double ratioBlue = 0.50; // These have to be put here as they are used by the function, modify values here if you want to change things up
        double ratioRed = 1 - ratioBlue; // Red will just be what blue is not, so that inconsistency by the user is avoided

        drawGrid(scaleGrid, gridSquares); // calling the draw grid function, because first things have to come first

        String[][] colourArray = new String[scaleGrid][scaleGrid];
        findColourValues(scaleGrid, percentageEmpty, ratioBlue, ratioRed, gridSquares, colourArray); // this function finds how much is blue and red and empty
        System.out.println(colourArray[0][0]);
    }

    static void drawGrid(double scaleGrid, double gridSquares) {

        StdDraw.setXscale(-1, scaleGrid + 3); // adjusting the size of the grid
        StdDraw.setYscale(-1, scaleGrid + 3); // also adding a little space, so it's easier to count and test

        // This part just gives a reference to the outermost square of the grid, to show where things are

        StdDraw.setPenColor(Color.BLACK);
        //StdDraw.square(scaleGrid/2,scaleGrid/2,scaleGrid/2 );


        for (int y = 1; y < scaleGrid; y++) {    // I know starting at 1 is not convention, but it was the easiest way that was traceable and that did not risk messing up too much in the design process

            for (int x = 1; x < scaleGrid; x++) {  // inner nested loop, scaleGrid is the same because we're looking at a square, therefore the for loops have the same parameters

                StdDraw.square(x + 1, y + 1, gridSquares); // finally, we start off 1 from the very edge and have a space on the x[<scaleGrid>] and y[<scaleGrid>] coordinates

            }

        }


    }


    static void findColourValues(double scaleGrid, double percentageEmpty, double ratioBlue, double ratioRed, double gridSquares, String[][] colourArray) {

        //String[][] colourArray = new String[(int) scaleGrid][(int) scaleGrid];

        double numGrids = scaleGrid * scaleGrid; // is used to avoid writing down the assigned operation over and over

        double emptyGrids = Math.round((percentageEmpty * numGrids));

        double blueGrids = Math.floor((numGrids - emptyGrids) * ratioBlue); // find the remaining grids and part them into red or blue

        double redGrids = Math.floor((numGrids - emptyGrids) * ratioRed);


        if ((redGrids + blueGrids + emptyGrids) < numGrids) {      // This is put in place because the number of grid spaces will be integers
            emptyGrids++;      // Because they are integers, and Empty is more significant in terms of priority, I decided that it should be the one that receives the decimal places
        }                       // If the values are changed, this should still work


        // Checking the values

        ///*
        System.out.println("Empty grids: " + emptyGrids);
        System.out.println("Blue Grids: " + blueGrids);
        System.out.println("Red Grids: " + redGrids);
        //*/



        /*
            The loops below check the conditions for distribution of colour and colour assignment.


            This is to make sure the value in main are consistent with the code and the visual look


            It's very long, but it's really to track down all the possible things that could go wrong
            and make sure they basically don't
         */

        double randomNum;

        while ( ( emptyGrids > 0 ) || ( blueGrids > 0 ) || ( redGrids > 0 ) ) {
            for (int y = 1 ; y < scaleGrid; y++) {



                for (int x = 1; x < scaleGrid; x++) {

                    boolean isEmpty = false;
                    boolean isBlue = false;
                    boolean isRed = false;

                    randomNum = Math.random();

                    if ((randomNum > 0.6) && (emptyGrids > 0)) {

                        emptyGrids--;
                        isEmpty = true;

                    } else if ((randomNum < 0.3) && (blueGrids > 0)) {

                        blueGrids--;
                        isBlue = true;

                    } else if ((randomNum >= 3.0) && (randomNum <= 0.6) && (redGrids > 0)) {

                        redGrids--;
                        isRed = true;

                    } else if ((emptyGrids == 0) && (randomNum > 0.6)) {

                        randomNum = Math.random();

                        if (randomNum >= 0.5 && blueGrids > 0) {

                            blueGrids--;
                            isBlue = true;

                        } else if (redGrids > 0) {

                            redGrids--;
                            isRed = true;

                        }

                    } else if ((randomNum < 0.3) && (blueGrids == 0)) {

                        randomNum = Math.random();

                        if (randomNum > 0.5 && redGrids > 0) {

                            redGrids--;
                            isRed = true;

                        } else if (emptyGrids > 0) {

                            emptyGrids--;
                            isEmpty = true;

                        }

                    } else if ((redGrids == 0) && (randomNum >= 3.0) && (randomNum <= 0.6)) {

                        randomNum = Math.random();

                        if (randomNum > 0.5 && blueGrids > 0) {

                            blueGrids--;
                            isBlue = true;

                        } else {

                            emptyGrids--;
                            isEmpty = true;

                        }

                    } else if (emptyGrids > 0 && redGrids == 0 && blueGrids == 0) {

                        emptyGrids--;
                        isEmpty = true;

                    } else if (emptyGrids == 0 && redGrids > 0 && blueGrids == 0) {

                        redGrids--;
                        isRed = true;

                    } else if (emptyGrids == 0 && redGrids == 0 && blueGrids > 0) {

                        blueGrids--;
                        isBlue = true;

                    }

                    /*

                    This next if statement takes the value at [y][x] and stores the colours in form of
                    a string in <variable colourArray[][]>

                    */

                    if ( isEmpty == true){

                        colourArray[y][x]= "empty";

                    }

                    if ( isBlue == true){

                        colourArray[y][x]= "blue";

                    }
                    if ( isRed == true){

                        colourArray[y][x]= "red";

                    }



                }
            }
        }


        // This was written to make sure that the increments were used and that the negative incrementation is proper and works. if works, they should all be 0;

        System.out.println( "Empty : " + emptyGrids + " \nBlue : " + blueGrids + "\nRed : " + redGrids );

        //return colourArray;
    }
}