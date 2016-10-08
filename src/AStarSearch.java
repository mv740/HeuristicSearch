import java.util.*;

/**
 * Created by michal wozniak on 10/4/2016.
 */
public class AStarSearch implements Search {

    //to be able to detect repeated nodes
    private Map<String, Integer> map = new HashMap<>();
    private int heuristicChoice;
    private boolean achieveGoal = false;


    @Override
    public void initiateSearch(String[][] puzzleBoard, int heuristicChoice) {
        this.heuristicChoice = heuristicChoice;

        PuzzleState puzzleState = new PuzzleState(puzzleBoard,heuristicChoice);
        Node root = new Node(puzzleState);

        //FIFO queue, new successors go at end
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>(10, new ComparatorAStar());
        nodeQueue.add(root);

        doSearch(nodeQueue);
    }

    /**
     * ignore repeated node
     * @param n
     * @return
     */
    private boolean detectRepeatedNode(Node n) {
        String puzzleString = n.getCurrentState().getCurrentStateString();
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

        while (!(nodeQueue.isEmpty() || achieveGoal)) {


            Node tempHead = nodeQueue.poll(); // get head node

            if (tempHead.getCurrentState().achievedGoal()) {
                achieveGoal = true;
                Board.printSolution(tempHead);
                //System.out.println(counter);

            } else {
                //not goal, therefore continue searching

                List<PuzzleState> childSuccessors = tempHead.getCurrentState().createSuccessors();

                for (int i = 0; i < childSuccessors.size(); i++) {
                    PuzzleState childSuccessor = childSuccessors.get(i);

                    Node node = new Node(childSuccessor, tempHead, tempHead.getPathCost() + 1, getHeuristicOptionCost(childSuccessors, i));

                    if (!detectRepeatedNode(node)) {
                        nodeQueue.add(node);
                    }

                }
                counter++;

            }
        }

    }

    private int getHeuristicOptionCost(List<PuzzleState> childSuccessors, int i) {

        int value = 0;
        switch (heuristicChoice) {
            case 0:
                value = childSuccessors.get(i).getManhattanDistance();
                break;
            case 1:
                value = childSuccessors.get(i).getMisplacedTiles();
                break;
            case 2:
                value = childSuccessors.get(i).getMin();
                break;
            case 3:
                value = childSuccessors.get(i).getEuclideanDistance();
                break;
            case 4:
                value = childSuccessors.get(i).getSequenceScore();
                break;
        }
        return value;
    }
}
