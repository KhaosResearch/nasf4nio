package solver.state;

import move.Move;
import pathState.PathState;
import problem.Problem;
import solution.Solution;

public interface State<S extends Solution, M extends Move,PS extends PathState,P extends Problem<S,M,PS>> {
  
}
