import java.util.*;

/**
 * Created by michal wozniak on 10/3/2016.
 */
public class PuzzleState {

    //1 2 3 8 B 4 7 6 5
    private final String[][] GOAL = new String[][]{
            {"1", "2", "3"},
            {"8", "B", "4"},
            {"7", "6", "5"}
    };

    private enum MOVEMENT {UP, DOWN, LEFT, RIGHT}

    private String[][] currentState;
    private String currentStateString;
    private int misplacedTiles;
    private int manhattanDistance;
    private int min;
    private int euclideanDistance;
    private int sequenceScore;
    private Map<String, Position> positionMap;
    private int heuristicChoice;


    public PuzzleState(String[][] puzzle, int heuristicChoice) {
        this.currentState = puzzle;
        this.currentStateString = Board.toString(puzzle);

        this.positionMap = new HashMap<>();
        this.heuristicChoice = heuristicChoice;
        initializeHeuristicOption(heuristicChoice);


    }

    private void initializeHeuristicOption(int heuristicChoice) {
        switch (heuristicChoice) {
            case 0:
                setTilePostionMap();
                setManhattanDistance();
                break;
            case 1:
                setMisplacedTiles();
                break;
            case 2:
                setTilePostionMap();
                setManhattanDistance();
                setMisplacedTiles();
                setMin();
                break;
            case 3:
                setTilePostionMap();
                setEuclideanDistance();
                break;
            case 4:
                setTilePostionMap();
                setManhattanDistance();
                setSequenceScore();
                break;
        }
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

    public void displayCurrentState() {
        Board.printBoard(currentState);
    }

    public void displayGoalState() {
        Board.printBoard(GOAL);
    }

    public void displayCurrentStateString() {
        System.out.println(currentStateString);
    }


    public boolean achievedGoal() {
        //http://stackoverflow.com/questions/2721033/java-arrays-equals-returns-false-for-two-dimensional-arrays
        //Returns true if the two specified arrays are deeply equal to one another.

        return Arrays.deepEquals(GOAL, currentState);
    }


    /**
     * Create all possible  movements that the current state can do
     *
     * @return list of successors
     */
    public List<PuzzleState> createSuccessors() {

        Blank blankPosition = getBlankPosition();

        List<PuzzleState> successorsStates = new ArrayList<>();

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

    /**
     * This will provide the coordinate position of the "B" in the puzzle
     *
     * @return
     */
    private Blank getBlankPosition() {

        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                if (currentState[i][j].contains("B")) {
                    // System.out.println("found getBlankPosition ={"+i+","+j+"}");
                    return new Blank(i, j);
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
    private boolean slideLeft(Blank blank) {

        return blank.getCol() != 2;
    }

    /**
     * //slide tile right into blank
     *
     * @param blank
     * @return
     */
    private boolean slideRight(Blank blank) {

        return blank.getCol() !=0;
    }

    /**
     * //slide tile up into blank
     *
     * @param blank
     * @return
     */
    private boolean slideUp(Blank blank) {

        return blank.getRow() !=2;

    }

    /**
     * //slide tile left into blank
     *
     * @param blank
     * @return
     */
    private boolean slideDown(Blank blank) {

        return blank.getRow() !=0;
    }


    /**
     * Swap the Character doing the movement with "B"
     *
     * 1|B|2  we can slide 2 to the left which will produce 1|2|B
     *
     * @param option
     * @param blank
     * @param state
     */
    private void doSlidingMovement(MOVEMENT option, Blank blank, List<PuzzleState> state) {

        int i = blank.getRow();
        int j = blank.getCol();
        int pieceI = 0;
        int pieceJ = 0;


        switch (option) {
            case UP:
                pieceI = i + 1;
                pieceJ = j;
                break;


            case DOWN:
                pieceI = i - 1;
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

        PuzzleState child = new PuzzleState(tempBoard, heuristicChoice);
        state.add(child);

    }

    //http://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java
    private String[][] deepCopyPuzzle(String[][] puzzle) {
        String[][] copy = new String[puzzle.length][puzzle[0].length];
        for (int i = 0; i < copy.length; i++)
            copy[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);

        return copy;
    }

    public int getMisplacedTiles() {
        return misplacedTiles;
    }

    /**
     *  Calculated the value of misplaced tiles
     */
    public void setMisplacedTiles() {

        int misplacedTiles = 0;
        int rowLength = currentState.length;
        int colLength = currentState[0].length;
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                if (!Objects.equals(currentState[row][col], GOAL[row][col])) {
                    misplacedTiles++;
                }
            }
        }
        this.misplacedTiles = misplacedTiles;


    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    /**
     * Calculate the manhattan distance
     */
    public void setManhattanDistance() {

        int distance = 0;
        int rowLength = GOAL.length;
        int colLength = GOAL[0].length;
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                String tileValue = GOAL[row][col];
                Position position = positionMap.get(tileValue);

                distance += Math.abs(row - position.getRow()) + Math.abs(col - position.getCol());
            }
        }

        this.manhattanDistance = distance;
    }

    /**
     *  store the postion of each character in a map for faster access
     */
    public void setTilePostionMap() {
        int rowLength = currentState.length;
        int colLength = currentState[0].length;
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                String tileValue = currentState[row][col];
                positionMap.put(tileValue, new Position(row, col));
            }
        }
    }

    public int getMin() {
        return min;
    }

    public void setMin() {

        this.min = misplacedTiles < manhattanDistance ? misplacedTiles : manhattanDistance;

    }

    public int getEuclideanDistance() {
        return euclideanDistance;
    }

    /**
     * Sum of Eucledian distances of the tiles from their
     goal positions

     //http://cse.iitk.ac.in/users/cs365/2009/ppt/13jan_Aman.pdf slide 3

     //Admissible heuristic
     */
    public void setEuclideanDistance() {

        int distance = 0;
        int rowLength = GOAL.length;
        int colLength = GOAL[0].length;
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                String tileValue = GOAL[row][col];
                Position position = positionMap.get(tileValue);

                distance += Math.pow((row - position.getRow()),2) + Math.pow((col - position.getCol()),2);
            }
        }

        this.euclideanDistance = distance;

    }

    public int getSequenceScore() {
        return sequenceScore;
    }

    /**
     * Nilsson’s Sequence Score
     https://heuristicswiki.wikispaces.com/Nilsson%27s+Sequence+Score

     Heuristic is not admissible

     */
    public void setSequenceScore()
    {

        //h(n) = P(n)+3S(n)
        this.sequenceScore = getManhattanDistance()+3*calculateSequenceScore();
    }

    /**
     * S(n) is the sequence score obtained by checking around the non-central squares in turn, allotting 2 for every tile not followed by its proper successor and 1 in case that the center is not empty.
     * @return
     */
    private int calculateSequenceScore()
    {
        //1 2 3 8 b 4 7 6 5

        //1 2 3
        //8 B 4
        //7 6 5
        String goalState = Board.toString(GOAL);
        String  state = getCurrentStateString();

        int value = 0;
        for(int i =0; i < state.length();i++)
        {
            char blank = 'B';
            char space = ' ';

            char current = state.charAt(i);
            if(i == 8)
            {
                if(current != blank)
                {
                    //add 1 in case that the center is not empty.
                    value+=1;
                }

            }else if(current != space)
            {

                if(i != 16)
                {
                    int currentSuccessorLocation = i+2;
                    char charSuccessor = state.charAt(currentSuccessorLocation);
                    //System.out.println(charSuccessor);

                    //find current charactor in goal state
                    int currentLocationInGoal = goalState.indexOf(current);

                    //if we reached goal[2][2] = 5 , it is the last tile since we reached end
                    if(currentLocationInGoal<16)
                    {
                        char currentCharInGoalSuccessor = goalState.charAt(currentLocationInGoal+2);

                        if(charSuccessor != currentCharInGoalSuccessor)
                        {
                            value+=2;
                        }
                    }


                }
            }
        }
        return value;
    }
}
