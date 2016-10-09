import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by michal wozniak on 10/3/2016.
 */
public class Driver {

    public static void main(String[] args) {

        System.out.println("8 puzzle search");
        System.out.println();

        //test #1
        //String[][] board = Board.generateBoard("1 3 4 8 2 5 B 7 6"); //6 movement from solution

        //test #2
        String[][] board = Board.generateBoard("2 3 B 1 6 5 8 4 7"); //12 movement from solution

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("**************************** ");
        System.out.println("8 puzzle Search  : ");
        System.out.println("**************************** ");
        System.out.println("4 different algorithm");
        System.out.println("1- Breadth First");
        System.out.println("2- Depth First");
        System.out.println("3- Best First");
        System.out.println("4- A*");
        System.out.println();
        System.out.print("Enter a number: ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.
        while (n > 4 || n < 1) {
            System.out.print("Enter a number: ");
            n = reader.nextInt();
        }


        Search search = null;
        switch (n) {
            case 1:
                search = SearchFactory.getSearch(Search.TYPE.BFS);
                break;
            case 2:
                search = SearchFactory.getSearch(Search.TYPE.DFS);
                break;
            case 3:
                search = SearchFactory.getSearch(Search.TYPE.BF);
                break;
            case 4:
                search = SearchFactory.getSearch(Search.TYPE.ASTAR);
        }
        int h = 0; // heuristic choice;
        if (n == 3 || n == 4) {
            System.out.println();
            System.out.println("**************************** ");
            System.out.println("Heuristic choice : ");
            System.out.println("**************************** ");
            System.out.println("0- Manhattan distance");
            System.out.println("1- Misplaced tiles");
            System.out.println("2- Min(misplaced-tiles(n), Manhattan-distance(n))");
            System.out.println("3- Euclidean Distance");
            System.out.println("4- Nilsson's Sequence Score");
            System.out.println();
            System.out.print("Enter a number: ");
            h = reader.nextInt(); // Scans the next token of the input as an int.
            while (h > 4 || h < 0) {
                System.out.print("Enter a number: ");
                h = reader.nextInt();
            }
        }
        System.out.println();


        long startTime = System.nanoTime();

        search.initiateSearch(board, h);

        long difference = System.nanoTime() - startTime;
        difference = TimeUnit.SECONDS.convert(difference, TimeUnit.MILLISECONDS);
        System.out.println("time to find solution : " + difference + " ms");


    }
}
