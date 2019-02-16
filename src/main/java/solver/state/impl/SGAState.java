package solver.state.impl;

import java.util.List;
import move.Move;
import pathState.PathState;
import problem.Problem;
import solution.Solution;
import solver.state.State;

public class SGAState
    <S extends Solution, M extends Move,PS extends PathState,P extends Problem<S,M,PS>>
    implements State<S,M,PS,P> {

  private P problem;
  private M move;
  private PS pathState;
  private List<S> parent,offspring;
  private S best;
  private double mincost;
  private List<Double> fitness;
  private List<Double> cost;
  private List<Double> rankingAux;
  private int popSize,i;
  private double px,pm,sp;


  public SGAState(){

  }
  public SGAState(P problem){
    this.problem = problem;
    this.best = problem.randomSolution();
  }

  public P getProblem() {
    return problem;
  }

  public void setProblem(P problem) {
    this.problem = problem;
  }

  public M getMove() {
    return move;
  }

  public void setMove(M move) {
    this.move = move;
  }

  public PS getPathState() {
    return pathState;
  }

  public void setPathState(PS pathState) {
    this.pathState = pathState;
  }

  public List<S> getParent() {
    return parent;
  }

  public void setParent(List<S> parent) {
    this.parent = parent;
  }

  public List<S> getOffspring() {
    return offspring;
  }

  public void setOffspring(List<S> offspring) {
    this.offspring = offspring;
  }

  public S getBest() {
    return best;
  }

  public void setBest(S best) {
    this.best = best;
  }

  public List<Double> getCost() {
    return cost;
  }

  public void setCost(List<Double> cost) {
    this.cost = cost;
  }

  public List<Double> getFitness() {
    return fitness;
  }

  public void setFitness(List<Double> fitness) {
    this.fitness = fitness;
  }

  public double getMincost() {
    return mincost;
  }

  public void setMincost(double mincost) {
    this.mincost = mincost;
  }

  public List<Double> getRankingAux() {
    return rankingAux;
  }

  public void setRankingAux(List<Double> rankingAux) {
    this.rankingAux = rankingAux;
  }

  public int getPopSize() {
    return popSize;
  }

  public void setPopSize(int popSize) {
    this.popSize = popSize;
  }

  public int getI() {
    return i;
  }

  public void setI(int i) {
    this.i = i;
  }

  public double getPx() {
    return px;
  }

  public void setPx(double px) {
    this.px = px;
  }

  public double getPm() {
    return pm;
  }

  public void setPm(double pm) {
    this.pm = pm;
  }

  public double getSp() {
    return sp;
  }

  public void setSp(double sp) {
    this.sp = sp;
  }
}
