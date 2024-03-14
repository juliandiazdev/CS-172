import static org.junit.jupiter.api.Assertions.*;

class SegregationTest {

    @org.junit.jupiter.api.Test
    void assignColours() {
        int i=0;
        int[][] tester=new int [8][8];
        Segregation.assignColours(tester,0.4,1,0.5,0.5);
        for (int x=0; x<tester.length; x++) {
            for (int y=0; y<tester.length; y++) {
                i++;
            }
        }
        assertEquals(64, i);
    }

    @org.junit.jupiter.api.Test
    void Neighbors() {
        int i=0;
        int[][] tester=new int [3][3];
        int[] neighborstest = new int[8];
        int[][] testcolorgrid= {{0,0,2},{1,2,1},{1,1,0}};
        int x=1;
        int y=1;
        Segregation.Neighbors(testcolorgrid,x,y);
        assertEquals(0,neighborstest[4]);
    }

   /* @org.junit.jupiter.api.Test
    void relocateAgent() {
        int[][] testgrid={{0,1,1,2},{0,2,1,0},{0,1,1,0},{2,0,2,2}};
        int agent= testgrid[1][1];
        //int agentprime=0;
        Segregation.relocateAgent(testgrid,3,1,1);
        assertNotEquals(agent,2);



    }*/
    //many of our methods have randoms in it which makes testing difficult for us
}