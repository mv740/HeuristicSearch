import java.util.Arrays;
import java.util.Stack;

/**
 * Created by michal wozniak on 10/3/2016.
 * <p>
 * static methods for printing boards's information
 */
public class Board {

    /**
     * Create a 2d array puzzle from a string
     *
     * @param puzzle each variable must be separated by a space
     * @return
     */
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

    /**
     * print a puzzle board
     *
     * @param board
     */
    public static void printBoard(String[][] board) {
        String row0 = board[0][0] +"|"+ board[0][1] +"|"+ board[0][2];
        String row1 = board[1][0] +"|"+ board[1][1] +"|"+ board[1][2];
        String row2 = board[2][0] +"|"+ board[2][1] +"|"+ board[2][2];

        System.out.println(row0);
        System.out.println("-----");
        System.out.println(row1);
        System.out.println("-----");
        System.out.println(row2);
    }

    /**
     * Transform puzzle matrix in a string
     *
     * @param board
     * @return
     */
    public static String toString(String[][] board) {

        return    board[0][0] + " " + board[0][1] + " " + board[0][2] + " "
                + board[1][0] + " " + board[1][1] + " " + board[1][2] + " "
                + board[2][0] + " " + board[2][1] + " " + board[2][2];
    }


    /**
     * Print path to reach solution state
     *
     * @param temp
     */
    public static void printSolution(Node temp) {

        Stack<Node> solutionPath = getHistoryNodes(temp);

        int size = solutionPath.size();
        for (int i = 0; i < size; i++) {
            temp = solutionPath.pop();
            System.out.println("--------------------------------------");
            printBoard(temp.getCurrentState().getCurrentState());
            System.out.println();
            if(i!=0)
            {
                printValues(temp);
            }
            else {
                System.out.println("initial state");
            }

        }

        printCost(temp);
    }


    public static void printCost(Node node) {

        System.out.println();
        System.out.println("***************DONE******************");
        System.out.println("cost = " + node.getPathCost());
    }

    private static void printValues(Node node)
    {
        System.out.println("{Heuristic,Path,F} = {" +node.getHeuristicCost() +"," +node.getPathCost()+","+node.getF()+"}");
        System.out.println();
    }


    private static Stack<Node> getHistoryNodes(Node tempHead) {

        Stack<Node> pathToReachSolution = new Stack<>();
        pathToReachSolution.push(tempHead);
        tempHead = tempHead.getParent();

        while (tempHead.getParent() != null) {
            pathToReachSolution.push(tempHead);
            tempHead = tempHead.getParent();
        }
        pathToReachSolution.push(tempHead);

        return pathToReachSolution;
    }

}
