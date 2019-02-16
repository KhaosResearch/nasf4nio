package move.impl;

import move.Move;
import problem.impl.Knapsack;

public class KnapsackMove implements Move {
    private int data;
    private Knapsack problem;
    public KnapsackMove() {
        this.data = data;
    }

    public KnapsackMove(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Knapsack getProblem() {
        return problem;
    }

    public void setProblem(Knapsack problem) {
        this.problem = problem;
    }
}
