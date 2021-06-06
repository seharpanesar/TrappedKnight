public class Main {
    final static int SIZE = 10001; // MUST BE ODD NUMBER for spiral to work. even numbers lead to incomplete boards / outofbounds exceptions
    final static int START_I = SIZE/2;
    final static int START_J = SIZE/2;
    final static int[][] board = new int[SIZE][SIZE];
    final static int ERROR = -1;
    static int trapDepth = 0;

    public static void main(String[] args) {
        makeBoard();
        Trap[] knightTraps = findTraps();
        printArray(knightTraps);
    }

    private static void printArray(Trap[] knightTraps) {
        for (Trap trap : knightTraps) {
            if (trap.trapValue == 0) {
                return;
            }
            System.out.println(trap);
        }
    }

    private static Trap[] findTraps() {
        int currentI = START_I;
        int currentJ = START_J;

        int[] valsVisited = new int[SIZE*SIZE];
        IndexPair[] indicesVisited = new IndexPair[SIZE*SIZE];
        int count = 0; // keeps track of how many vals have been placed in valsVisited

        Trap[] traps = new Trap[100];
        int trapInd = 0;

        valsVisited[count++] = board[currentI][currentJ]; // initializing value

        // will keep running until knight is trapped
        while (true) {
            int[] deltaI = {2, 1, 2, 1, -2, -1, -2, -1};
            int[] deltaJ = {1, 2, -1, -2, 1, 2, -1, -2};

            int minI = ERROR; // if it remains ERROR after for loop, knight is trapped
            int minJ = ERROR;
            int minVal = Integer.MAX_VALUE;

            for (int i = 0; i < deltaI.length; i++) {
                int potentialVal = board[currentI + deltaI[i]][currentJ + deltaJ[i]]; // gets knight move
                if ((potentialVal < minVal) && (!isVisited(potentialVal, valsVisited, count, traps))) {
                    minVal = potentialVal;
                    minI = currentI + deltaI[i];
                    minJ = currentJ + deltaJ[i];
                }
            }

            if (minI == ERROR) {
                traps[trapInd++] = new Trap(valsVisited[count - trapDepth + 1], trapDepth - 1);

                currentI = indicesVisited[count - trapDepth].getI();
                currentJ = indicesVisited[count - trapDepth++].getJ();

                //TODO: scan for double, triple traps etc

                if (trapInd == 100) {
                    return traps;
                }
            } else {
                currentI = minI;
                currentJ = minJ;
                indicesVisited[count] = new IndexPair(currentI, currentJ);
                valsVisited[count++] = minVal;
                trapDepth = 2;

            }
        }
    }

    /*
     checks if potential val is already visited. count is passed so that we can start at end of array. If potentialVal
     exists, it is most likely to be at end of visited.
     */

    private static boolean isVisited(int potentialVal, int[] visited, int count, Trap[] traps) {
        for (Trap trap : traps) {
            if (trap == null) {
                break;
            }
            if (trap.trapValue == potentialVal) {
                return true;
            }
        }

        for (int i = count - 1; i >= 0; i--) {
            if (visited[i] == potentialVal) {
                return true;
            }
        }
        return false;
    }

    private static void makeBoard() {
        int i = START_I;
        int j = START_J;

        int val = 0; // number that goes in board[][]
        int traverse = 1; // increment gets bigger as spiral increases

        board[i][j] = ++val;

        /* loop will fill in board in spiral fashion. It will fill in 1 right square, work its way up, then left,
           then down then right. Will repeat array is full.
         */

        while (val != SIZE*SIZE) {
            board[i][++j] = ++val; // filling right square


            for (int k = 0; k < traverse; k++) { // up
                board[--i][j] = ++val;
            }

            traverse++;

            for (int k = 0; k < traverse; k++) { // left
                board[i][--j] = ++val;
            }

            for (int k = 0; k < traverse; k++) { //down
                board[++i][j] = ++val;
            }

            for (int k = 0; k < traverse; k++) { //right
                board[i][++j] = ++val;
            }

            traverse++;
        }
    }
}
