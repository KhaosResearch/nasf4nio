package problem;

import move.Move;
import pathState.PathState;
import solution.Solution;

public interface Problem <S extends  Solution, M extends Move,P extends PathState>{

    public S randomSolution();
    public void evaluate(S solution);
    public double getObjectiveValue(S solution);
    public M randomMove(M move,S solution);
    public M nextRandomMove(M move,P pathState);
    public P initPathTo(P pathState,S solutionFrom,S solutionTo);
    public P initPathAwayFrom(P pathState,S solution);
    public S applyMove(S solution, M move);
    public int getPathLength(P pathState);
    public S createSolution();
    public M createMove();
    public P createPathState();
    public void printSolution(S solution);

}
