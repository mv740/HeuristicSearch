import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by michal wozniak on 10/3/2016.
 *
 */
public class Driver {

    public static void main(String[] args){

        System.out.println("8 puzzle search");
        System.out.println();

        String[][] board = Board.generateBoard("1 3 4 8 2 B 7 6 5"); //3 movement from solution


        Search search = SearchFactory.getSearch(Search.TYPE.ASTAR);


        long startTime = System.nanoTime();



        search.initiateSearch(board,0);

        long difference = System.nanoTime() - startTime;
        difference = TimeUnit.SECONDS.convert(difference, TimeUnit.MILLISECONDS);
        System.out.println("time to find solution : " +difference + " ms");



    }
}
