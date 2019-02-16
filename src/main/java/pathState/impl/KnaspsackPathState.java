package pathState.impl;

import pathState.PathState;
import problem.impl.Knapsack;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnaspsackPathState implements PathState {
    private List<Integer> positions;
    private int n;
    private int distance;
    private Knapsack problem;

    public KnaspsackPathState(int n){
        positions= Util.fill(n,0);
        this.n = n;
    }

    public KnaspsackPathState(int n,int distance) {
        positions=Util.fill(n,0);
        this.n = n;
        this.distance = distance;
    }

    public KnaspsackPathState(List<Integer> positions, int n, int distance) {
        this.positions = positions;
        this.n = n;
        this.distance = distance;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Knapsack getProblem() {
        return problem;
    }

    public void setProblem(Knapsack problem) {
        this.problem = problem;
    }
}
