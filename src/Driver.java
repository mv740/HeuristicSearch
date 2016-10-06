
/**
 * Created by michal wozniak on 10/3/2016.
 *
 */
public class Driver {

    public static void main(String[] args){

        System.out.println("test");

        String[][] board = Board.generateBoard("1 2 3 8 B 4 7 6 5");
        String[][] board1 = Board.generateBoard("1 2 3 B 8 4 7 6 5");




  //      System.out.println(Board.toString(board1));

        Board.printBoard(board1);
        System.out.println("&&&&&&&&&&&&&&&&&&&&");



//        PuzzleState puzzleState = new PuzzleState(board);
//        Node temp = new Node(puzzleState,null,1);
//
//
//        PuzzleState test = (PuzzleState) temp.getCurrentState();
//        if(test.achievedGoal())
//        {
//            System.out.println("yolo");
//        }


        Search search = SearchFactory.getSearch(Search.TYPE.BFS);

        search.initiateSearch(board1);

    }
}
