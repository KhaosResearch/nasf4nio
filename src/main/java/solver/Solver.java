package solver;

import move.Move;
import pathState.PathState;
import problem.Problem;
import solution.Solution;
import solver.state.State;

public interface Solver<S extends Solution,
    M extends Move,PS extends PathState,P extends Problem<S,M,PS>, ST extends State<S,M,PS,P>> {
  public ST newSolver(P problem,int popSize);
  public ST nextSolverState(ST solverState);
  public S getSolverSolution(ST solverState);

}
