import java.util.*;

/**
 * Created by michal wozniak on 10/3/2016.
 * <p>
 * https://www.ics.uci.edu/~welling/teaching/271fall09/UninformedSearch271f09.pdf
 * slide 3
 */
public class BreadthFirstSearch implements Search{

    //to be able to detect repeated nodes
    private Map<String, Integer> exploredMap = new HashMap<>();
    private boolean achieveGoal = false;


    @Override
    public void initiateSearch(String[][] puzzleBoard, int heuristicChoice) {
        //ignoring heuristic
        PuzzleState puzzleState = new PuzzleState(puzzleBoard,-1);
        Node root = new Node(puzzleState);

        //FIFO queue, new successors go at end
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        doSearch(nodeQueue);
    }

    private boolean detectRepeatedNode(Node n) {
        PuzzleState s = n.getCurrentState();
        String puzzleString = s.getCurrentStateString();
        if (exploredMap.containsKey(puzzleString)) {
            return true;
        }else
        {
            exploredMap.put(puzzleString,1);
        }
        return false;
    }


    public void doSearch(Queue<Node> nodeQueue) {

        int counter = 1;

        while (!nodeQueue.isEmpty() && !achieveGoal) {

            Node tempHead = nodeQueue.poll(); // get head node

            if (tempHead.getCurrentState().achievedGoal()) {
                achieveGoal = true;
                Board.printSolution(tempHead);
                System.out.println(counter);


            } else {
                //not goal, therefore continue searching

                List<PuzzleState> childSuccessors = tempHead.getCurrentState().createSuccessors();

                for (PuzzleState childSuccessor : childSuccessors) {

                    Node node = new Node(childSuccessor, tempHead, tempHead.getPathCost() + 1, 0);

                    if (!detectRepeatedNode(node)) {
                        nodeQueue.add(node);
                    }

                }
                counter++;

            }
        }

    }

}
