import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by michal wozniak on 10/3/2016.
 */
public class PuzzleState implements State {

    //1 2 3 8 B 4 7 6 5
    private final String[][] GOAL = new String[][]{
            {"1", "2", "3"}, {"8", "B", "4"}, {"7", "6", "5"}
    };

    private enum MOVEMENT {UP, DOWN, LEFT, RIGHT}

    private String[][] currentState;
    private String currentStateString;

    public PuzzleState(String[][] puzzle) {
        this.currentState = puzzle;
        this.currentStateString = Board.toString(puzzle);
    }

    public String[][] getGOAL() {
        return GOAL;
    }

    public String[][] getCurrentState() {
        return currentState;
    }

    public String getCurrentStateString() {
        return currentStateString;
    }

    @Override
    public void displayCurrentState() {
        Board.printBoard(currentState);
    }

    public void displayGoalState() {
        Board.printBoard(GOAL);
    }

    public void displayCurrentStateString() {
        System.out.println(currentStateString);
    }



    @Override
    public boolean achievedGoal() {
        //http://stackoverflow.com/questions/2721033/java-arrays-equals-returns-false-for-two-dimensional-arrays
        //Returns true if the two specified arrays are deeply equal to one another.

        System.out.println("is this > " +currentStateString + " equal to this --> " + Board.toString(GOAL));
        boolean result = Arrays.deepEquals(GOAL, currentState);
        System.out.println("result -> " +result);


        return result;
    }

    @Override
    public boolean compare(State state) {
        return false;
    }

    @Override
    public int pathCost() {
        return 0;
    }

    @Override
    public List<State> createSuccessors() {

        Blank blankPosition = getBlankPosition();

        //System.out.println("found B ={"+blankPosition.getI()+","+blankPosition.getJ()+"}");
        List<State> successorsStates = new ArrayList<>();

        //find all possible movement with the blank position

        if (slideLeft(blankPosition)) {
            doSlidingMovement(MOVEMENT.LEFT, blankPosition, successorsStates);
        }
        if (slideUp(blankPosition)) {
            doSlidingMovement(MOVEMENT.UP, blankPosition, successorsStates);
        }
        if (slideDown(blankPosition)) {
            doSlidingMovement(MOVEMENT.DOWN, blankPosition, successorsStates);
        }
        if (slideRight(blankPosition)) {
            doSlidingMovement(MOVEMENT.RIGHT, blankPosition, successorsStates);
        }


        return successorsStates;
    }

    @Override
    public Blank getBlankPosition() {

        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                if (currentState[i][j].contains("B")) {
                   // System.out.println("found getBlankPosition ={"+i+","+j+"}");
                    return new Blank(i,j);
                }
            }
        }


        return null;
    }

    /**
     * //slide tile left into blank
     *
     * @param blank
     * @return
     */
    @Override
    public boolean slideLeft(Blank blank) {


        return blank.getJ() != 2;

    }

    /**
     * //slide tile right into blank
     * @param blank
     * @return
     */
    @Override
    public boolean slideRight(Blank blank) {

        return blank.getJ() != 0;
    }

    /**
     * //slide tile up into blank
     * @param blank
     * @return
     */
    @Override
    public boolean slideUp(Blank blank) {

        return blank.getI() != 2;

    }

    /**
     * //slide tile left into blank
     * @param blank
     * @return
     */
    @Override
    public boolean slideDown(Blank blank) {

        if (blank.getI() != 0) {
            return true;
        }
        return false;
    }


    /**
     *  1|B|2  we can slide 2 to the left which will produce 1|2|B
     *
     *
     * @param option
     * @param blank
     * @param state
     */
    public void doSlidingMovement(MOVEMENT option, Blank blank, List<State> state) {

        int i = blank.getI();
        int j = blank.getJ();
        int pieceI = 0;
        int pieceJ = 0;



        switch (option) {
            case UP:
                pieceI = i + 1;
                pieceJ = j;
                break;


            case DOWN:
                pieceI = i-1;
                pieceJ = j;
                break;

            case LEFT:
                pieceI = i;
                pieceJ = j + 1;
                break;

            case RIGHT:
                pieceI = i;
                pieceJ = j - 1;
                break;

        }


        String[][] tempBoard = deepCopyPuzzle(getCurrentState());
        String movingPiece = tempBoard[pieceI][pieceJ];
        tempBoard[i][j] = movingPiece;
        tempBoard[pieceI][pieceJ] = blank.getName();

        PuzzleState child = new PuzzleState(tempBoard);
        state.add(child);

        System.out.println("+++++++++++++++++++");
        System.out.println(option);
        Board.printBoard(tempBoard);
        System.out.println("+++++++++++++++++++");

    }

    //http://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java
    private String[][] deepCopyPuzzle(String[][] puzzle)
    {
        String[][] copy = new String[puzzle.length][puzzle[0].length];
        for (int i = 0; i < copy.length; i++)
            copy[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);

        return copy;
    }
}
