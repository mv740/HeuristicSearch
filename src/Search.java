/**
 * Created by michal wozniak on 10/3/2016.
 *
 * http://www.sdsc.edu/~tbailey/teaching/cse151/lectures/chap03b.html
 * https://www.ics.uci.edu/~welling/teaching/271fall09/UninformedSearch271f09.pdf
 * http://www-users.cs.umn.edu/~karypis/parbook/Figures/chap11.pdf
 */
public interface Search {

    enum TYPE { BFS, DFS,BF,ASTAR}
    // create root search node;
    void initiateSearch(String[][] puzzleBoard, int heuristicChoice);

}
