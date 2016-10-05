import java.util.*;

/**
 * Created by michal wozniak on 10/3/2016.
 * <p>
 * https://www.ics.uci.edu/~welling/teaching/271fall09/UninformedSearch271f09.pdf
 * slide 3
 */
public class BreadthFirstSearch implements Search{

    //to be able to detect repeated nodes
    private Map<String, Integer> map = new HashMap<>();



    @Override
    public void initiateSearch(String[][] puzzleBoard) {

        PuzzleState puzzleState = new PuzzleState(puzzleBoard);
        Node root = new Node(puzzleState);

        //FIFO queue, new successors go at end
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        doSearch(nodeQueue);
    }

    private boolean detectRepeatedNode(Node n) {
        PuzzleState s = (PuzzleState) n.getCurrentState();
        String puzzleString = s.getCurrentStateString();
        if (map.containsKey(puzzleString)) {
            return true;
        }else
        {
            map.put(puzzleString,1);
        }
        return false;
    }


    public void doSearch(Queue<Node> nodeQueue) {

        int counter = 1;

        while (!nodeQueue.isEmpty()) {
            System.out.println("counter -->"+counter);
            System.out.println();

            Node tempHead = nodeQueue.remove(); // get head node

            PuzzleState testing1 = (PuzzleState) tempHead.getCurrentState();
            System.out.println("#########################");
            testing1.displayCurrentStateString();
            System.out.println("#########################");


            if (!tempHead.getCurrentState().achievedGoal()) {
                //not goal, therefore continue searching

                List<State> childSuccessors = tempHead.getCurrentState().createSuccessors();

                for (State childSuccessor : childSuccessors) {

                    Node node = new Node(childSuccessor, tempHead, tempHead.getPathCost() + 1);

                    if (!detectRepeatedNode(node)) {
                        nodeQueue.add(node);
                    }

                }
                counter++;

            } else {

                Board.printSolutionPath(tempHead);
                System.exit(0);
            }
        }

    }

}
