import java.util.List;
import java.util.Map;

/**
 * Created by michal wozniak on 10/3/2016.
 */
public interface State {

    void displayCurrentState();

    boolean achievedGoal();

    boolean compare(State state);

    int pathCost();

    List<State> createSuccessors();

    Blank getBlankPosition();

    boolean slideLeft(Blank blank);

    boolean slideRight(Blank blank);

    boolean slideUp(Blank blank);

    boolean slideDown(Blank blank);
}
