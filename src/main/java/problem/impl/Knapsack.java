package problem.impl;

import move.impl.KnapsackMove;
import pathState.impl.KnaspsackPathState;
import problem.Problem;
import solution.Solution;
import solution.impl.KnapsackSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Knapsack implements Problem<KnapsackSolution, KnapsackMove, KnaspsackPathState> {

    private List<Integer> weights;
    private List<Integer> profits;
    private int nItems;
    private int capacity;
    private Random random;

    public Knapsack(int nItems,int capacity,List<Integer> weights,List<Integer> profits){
        this.nItems = nItems;
        this.capacity = capacity;
        this.weights = weights;
        this.profits = profits;
        this.random = new Random();
    }

    public KnapsackSolution randomSolution() {
        KnapsackSolution solution = this.createSolution();
        int n = solution.getN();
        List<Integer> aux= new ArrayList<Integer>();
        for (int i =0;i<n;i++){
            aux.add(random.nextInt(2));
        }
        solution.setData(aux);
        evaluate(solution);
        if(solution.getObjValue()<=0){
            solution.setObjValue(-solution.getProfit());
        }
        return solution;
    }

    public void evaluate(KnapsackSolution solution) {
        solution.setProfit(0);;
        solution.setWeight(0);
        List<Integer> data = solution.getData();
        for (int i = 0; i <data.size() ; i++) {
            if(data.get(i)!=null){
                solution.setProfit(solution.getProfit()+getProfits().get(i));
                solution.setWeight(solution.getWeight()+getWeights().get(i));
            }
        }
        solution.setObjValue(solution.getWeight()-getCapacity());
    }

    public double getObjectiveValue(KnapsackSolution solution) {
        return solution.getObjValue();
    }

    public KnapsackMove randomMove(KnapsackMove move, KnapsackSolution solution) {
        move.setData(random.nextInt(solution.getN()));
        return move;
    }

    public KnapsackMove nextRandomMove(KnapsackMove move, KnaspsackPathState pathState) {
        int r;
        if(pathState.getDistance()==0){
            move.setData(-1);
        }else{
            int aux=pathState.getDistance();
            r= random.nextInt(--aux);
            pathState.setDistance(aux);
            move.setData(pathState.getPositions().get(r));
            List<Integer> auxL =pathState.getPositions();
            int auxDist=pathState.getPositions().get(pathState.getDistance());
            auxL.set(r,auxDist);
            pathState.setPositions(auxL);

            //pathState.getPositions().set(r,pathState.getPositions().get(pathState.getDistance()));
        }
        return move;
    }



    public KnaspsackPathState initPathTo(KnaspsackPathState pathState, KnapsackSolution solutionFrom, KnapsackSolution solutionTo) {
        int k = 0;
        int n = solutionFrom.getN();
        for (int i=0; i<n; i++){
            if(solutionFrom.getData().get(i).intValue() !=solutionTo.getData().get(i).intValue()){
                pathState.getPositions().set(k++,i);
            }
            pathState.setDistance(k);
        }
        return pathState;
    }

    public KnaspsackPathState initPathAwayFrom(KnaspsackPathState pathState, KnapsackSolution solution) {
        int n = pathState.getN();
        for (int i = 0; i < n; i++) {
            pathState.getPositions().set(i,i);
        }
        pathState.setDistance(n);
        return pathState;
    }



    public KnapsackSolution applyMove(KnapsackSolution solution, KnapsackMove move) {
        int i= move.getData();
        if(i<0 || i>=solution.getN()){
            //nothing
        }else{
            if(solution.getData().get(i)==0){
                solution.getData().set(i,1);
                solution.setWeight(solution.getWeight()+getWeights().get(i));
                solution.setProfit(solution.getProfit()+getProfits().get(i));
            }else{
                solution.getData().set(i,0);
                solution.setWeight(solution.getWeight()-getWeights().get(i));
                solution.setProfit(solution.getProfit()-getProfits().get(i));
            }
            solution.setObjValue(solution.getWeight()-getCapacity());
            if(solution.getObjValue() <=0){
                solution.setObjValue(-solution.getProfit());
            }
        }
        return solution;
    }

    public int getPathLength(KnaspsackPathState pathState) {
        return pathState.getDistance();
    }

    public KnapsackSolution createSolution() {
        KnapsackSolution solution = new KnapsackSolution(this);
        return solution;
    }

    public KnapsackMove createMove() {
        KnapsackMove move = new KnapsackMove();
        move.setProblem(this);
        return move;
    }

    public KnaspsackPathState createPathState() {
        KnaspsackPathState knaspsackPathState = new KnaspsackPathState(this.getNItems());
        knaspsackPathState.setProblem(this);
        return knaspsackPathState;
    }

    public void printSolution(KnapsackSolution solution) {
        System.out.println("Knapsack solution x:");
        for (int i = 0; i <solution.getN() ; i++) {
            System.out.println(solution.getData().get(i));
        }
    }


    public List<Integer> getWeights() {
        return weights;
    }

    public List<Integer> getProfits() {
        return profits;
    }

    public int getNItems() {
        return nItems;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        String result="Knapsack problem instance \n" +
                " Number of items: "+ getNItems() +" \n"+
                " Capacity: "+ getCapacity() +"\n"+
                " Weights: ";
        for (int weight: getWeights()) {
            result+=weight+" ";
        }
        result+="\n Profits: \n";
        for (int profit: getProfits()) {
            result+=profit+" ";
        }

        return result;
    }
}
