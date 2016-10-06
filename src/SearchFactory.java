/**
 * Created by michal wozniak on 10/4/2016.
 */
public class SearchFactory {

    public static Search getSearch(Search.TYPE type)
    {
        switch (type)
        {
            case BFS:
                return new BreadthFirstSearch();
            case BF: return  new BestFirstSearch();
            case ASTAR: return new AStarSearch();
            case DFS: return new DepthFirstSearch();
        }
        return null;
    }

}
