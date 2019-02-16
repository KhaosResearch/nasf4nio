package solution.impl;

import problem.impl.Knapsack;
import solution.Solution;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolution implements Solution {
    private List<Integer> data;
    private int n;
    private int objValue;
    private int weight;
    private int profit;
    private Knapsack problem;

    public KnapsackSolution(Knapsack problem){
        this.problem = problem;
        this.data = new ArrayList<Integer>(problem.getNItems());
        this.n = problem.getNItems();
    }

    public KnapsackSolution( int n, int objValue, int weight, int profit) {
        this.n = n;
        this.objValue = objValue;
        this.weight = weight;
        this.profit = profit;
        this.data = new ArrayList<Integer>(n);
    }

    public List<Integer> getData() {
        return data;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setObjValue(int objValue) {
        this.objValue = objValue;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getN() {
        return n;
    }

    public int getObjValue() {
        return objValue;
    }

    public int getWeight() {
        return weight;
    }

    public int getProfit() {
        return profit;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public Knapsack getProblem() {
        return problem;
    }

    public void setProblem(Knapsack problem) {
        this.problem = problem;
    }

    public Solution copySolution() {
        Solution result = null;
        List<Integer> dataList= new ArrayList<Integer>();
        dataList.addAll(this.getData());
        result = new KnapsackSolution(this.getN(),this.getObjValue(),this.getWeight(),this.getProfit());
        ((KnapsackSolution) result).setData(dataList);
        ((KnapsackSolution) result).setProblem(this.getProblem());

        return result;
    }
}
