/**
 * Created by michal wozniak on 10/3/2016.
 */
public class Node {

    private State currentState;
    private Node parent;
    private int pathCost;

    //root
    public Node(State currentState) {
        this.currentState = currentState;
        this.parent = null;
        this.pathCost = 0;
    }

    public Node(State currentState, Node parent, int pathCost) {
        this.currentState = currentState;
        this.parent = parent;
        this.pathCost = pathCost;
    }

    public State getCurrentState() {
        return currentState;
    }

    public Node getParent() {
        return parent;
    }

    public int getPathCost() {
        return pathCost;
    }
}
