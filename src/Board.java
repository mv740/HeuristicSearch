import java.util.Stack;

/**
 * Created by michal wozniak on 10/3/2016.
 */
public class Board {

    public static String[][] generateBoard(String puzzle) {
        //1 2 3 8 B 4 7 6 5
        String puzzleString = puzzle.replaceAll("\\s+", "");
        String[][] puzzleBoard = new String[3][3];
        for (int i = 0; i < puzzleString.length(); i++) {
            char c = puzzleString.charAt(i);

            if (i < 3) {
                puzzleBoard[0][i] = String.valueOf(c);
            } else if (i > 2 && i < 6) {
                puzzleBoard[1][i - 3] = String.valueOf(c);
            } else {
                puzzleBoard[2][i - 6] = String.valueOf(c);
            }
        }

        return puzzleBoard;


    }

    public static void printBoard(String[][] board) {
        String row0 = board[0][0] + board[0][1] + board[0][2];
        String row1 = board[1][0] + board[1][1] + board[1][2];
        String row2 = board[2][0] + board[2][1] + board[2][2];

        System.out.println(row0);
        System.out.println(row1);
        System.out.println(row2);
    }

    public static String toString(String[][] board) {

        String boardString =
                  board[0][0] + " " + board[0][1] + " " + board[0][2] + " "
                + board[1][0] + " " + board[1][1] + " " + board[1][2] + " "
                + board[2][0] + " " + board[2][1] + " " + board[2][2];

        return boardString;
    }


    /**
     * Go from solution child to parent until we reach root node
     *
     * @param node
     * @return
     */
    private static Stack<Node> getHistoryStack(Node node)
    {
        Stack<Node> intermediarySteps = new Stack<>();
        intermediarySteps.push(node);

        node = node.getParent();

        while (node.getParent() != null)
        {
            intermediarySteps.push(node);
            node = node.getParent();
        }
        intermediarySteps.push(node);

        return intermediarySteps;
    }


    public static void printSolutionPath(Node node)
    {
        Stack<Node> intermediarySteps = getHistoryStack(node);

        for (int i = 0; i < intermediarySteps.size(); i++)
        {
            node = intermediarySteps.pop();
            PuzzleState s = (PuzzleState) node.getCurrentState();
            Board.printBoard(s.getCurrentState());
            System.out.println();
            System.out.println();
        }

        System.exit(0);
    }
}
