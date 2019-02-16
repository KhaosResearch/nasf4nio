package runner;

import move.impl.KnapsackMove;
import pathState.impl.KnaspsackPathState;
import problem.Problem;
import problem.impl.Knapsack;
import solution.impl.KnapsackSolution;
import solver.Solver;
import solver.impl.SGA;
import solver.state.State;
import solver.state.impl.SGAState;

import java.util.ArrayList;
import java.util.List;

public class KnapsackRunner {

    public static void main(String [] args){

        List<Integer> weights = new ArrayList<Integer>();
        weights.add(1);
        weights.add(4);
        weights.add(4);
        weights.add(2);
        weights.add(4);

        List<Integer> profits = new ArrayList<Integer>();
        profits.add(3);
        profits.add(1);
        profits.add(1);
        profits.add(3);
        profits.add(2);
        Problem<KnapsackSolution, KnapsackMove, KnaspsackPathState> knapsack= new Knapsack(5,7,weights,profits);
        int popSize=100;
        int maxIter = 25000;
        double cost =0.0;
        Solver<KnapsackSolution,KnapsackMove,KnaspsackPathState,Knapsack, SGAState
                <KnapsackSolution, KnapsackMove,KnaspsackPathState,Knapsack>> solver =
                new SGA<KnapsackSolution, KnapsackMove, KnaspsackPathState,
                        Knapsack, SGAState<KnapsackSolution, KnapsackMove, KnaspsackPathState, Knapsack>>((Knapsack)knapsack,popSize);

        
        State<KnapsackSolution,KnapsackMove,KnaspsackPathState,Knapsack> state =
                new SGAState<KnapsackSolution, KnapsackMove, KnaspsackPathState, Knapsack>((Knapsack)knapsack);
        state = solver.newSolver((Knapsack)knapsack,popSize);
        double minCost = knapsack.getObjectiveValue(solver.
                getSolverSolution((SGAState<KnapsackSolution, KnapsackMove, KnaspsackPathState, Knapsack>) state));
        System.out.println("iter = 0 , mincost = "+minCost);
        for (int i = 0; i < maxIter ; i++) {
            state=solver.nextSolverState((SGAState<KnapsackSolution, KnapsackMove, KnaspsackPathState, Knapsack>) state);
            cost = knapsack.getObjectiveValue(solver.
                    getSolverSolution((SGAState<KnapsackSolution, KnapsackMove, KnaspsackPathState, Knapsack>) state));
            if(cost<minCost){
                minCost =cost;
                System.out.println("iter = "+ i +" , mincost = "+ minCost);
            }
        }
        knapsack.printSolution(solver.
                getSolverSolution((SGAState<KnapsackSolution, KnapsackMove, KnaspsackPathState, Knapsack>) state));

    }
}
