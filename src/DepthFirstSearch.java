import java.util.*;

/**
 * Created by michal wozniak on 10/4/2016.
 * <p>
 * https://en.wikipedia.org/wiki/Depth-first_search
 * <p>
 * using a stack
 */
public class DepthFirstSearch implements Search {

    //to be able to detect repeated nodes
    private Map<String, Integer> map = new HashMap<>();
    //http://stackoverflow.com/questions/856124/breaking-out-of-a-recursion-in-java
    private boolean achieveGoal = false; //used to stop recursions


    @Override
    public void initiateSearch(String[][] puzzleBoard) {

        PuzzleState puzzleState = new PuzzleState(puzzleBoard);
        Node root = new Node(puzzleState);

        //LIFO - last in , first out, new successors go at end
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.add(root);

        doSearch(nodeStack);
    }

    private boolean detectRepeatedNode(Node n) {
        PuzzleState s = (PuzzleState) n.getCurrentState();
        String puzzleString = s.getCurrentStateString();
        if (map.containsKey(puzzleString)) {
            return true;
        } else {
            map.put(puzzleString, 1);
        }
        return false;
    }


    public void doSearch(Stack<Node> nodeStack) {

        int counter = 1;

        while (!nodeStack.isEmpty() && !achieveGoal) {

            Node tempHead = nodeStack.pop(); // get head node

            if (tempHead.getCurrentState().achievedGoal()) {
                achieveGoal = true;
                Board.printSolutionPath(tempHead);

            } else {
                //not goal, therefore continue searching

                List<State> childSuccessors = tempHead.getCurrentState().createSuccessors();

                for (State childSuccessor : childSuccessors) {

                    Node node = new Node(childSuccessor, tempHead, tempHead.getPathCost() + 1);

                    if (!detectRepeatedNode(node)) {
                        nodeStack.add(node);
                    }

                }
                counter++;
            }
        }

    }

}
