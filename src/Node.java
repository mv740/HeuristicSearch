/**
 * Created by michal wozniak on 10/3/2016.
 *
 * http://science.slc.edu/~jmarshall/courses/2005/fall/cs151/lectures/heuristic-search/
 */
public class Node {

    private PuzzleState currentState;
    private Node parent;
    private int pathCost;
    private int heuristicCost;
    private int f;

    //root
    public Node(PuzzleState currentState) {
        this.currentState = currentState;
        this.parent = null;
        this.pathCost = 0;
    }

    public Node(PuzzleState currentState, Node parent, int pathCost, int heuristicCost) {
        this.currentState = currentState;
        this.parent = parent;
        this.pathCost = pathCost;
        this.heuristicCost = heuristicCost;
        this.f = pathCost + heuristicCost;
    }

    public PuzzleState getCurrentState() {
        return currentState;
    }

    public Node getParent() {
        return parent;
    }

    public int getPathCost() {
        return pathCost;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public int getF() {
        return f;
    }
}
