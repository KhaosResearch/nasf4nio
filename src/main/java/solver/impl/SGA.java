package solver.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import move.Move;
import pathState.PathState;
import problem.Problem;
import solution.Solution;
import solver.Solver;

import java.util.List;
import solver.state.State;
import solver.state.impl.SGAState;
import util.Util;

public class SGA<S extends Solution, M extends Move,
    PS extends PathState,P extends Problem<S,M,PS>,ST extends State<S,M,PS,P>>
    implements Solver<S,M,PS,P,ST> {

    private P problem;
    private int popSize;
    private Random random;

    public SGA(P problem,int popSize){
        this.problem = problem;
        this.popSize = popSize;
        this.random = new Random();
    }



    public List<Double>  ranking(List<Double> aux,List<Double> cost,
        double sp,int n){
        List<Double> fitness = null;
        double num=0,den=0;
        int i=0,j=0,k=0;
        if(aux==null || aux.isEmpty()){
           aux= Util.fillDouble(n,0);
        }
        fitness= Util.fillDouble(n,0);
        for ( i = 0; i < n ; i++) {
            aux.set(i,cost.get(i)+i);
        }
        Collections.sort(aux);
        for ( i = 0; i < n; i=j) {
            num = sp -(sp-1.0)*2.0*i/(n-1.0);
            den = 1.0;
            for (j = i+1;j< n && aux.get(i)==aux.get(j);j++){
                num += sp -(sp-1.0)*2.0*j /(n-1.0);
                den +=1.0;
            }
            for (k = i; k<j;k++){
                int index=(int)(aux.get(k)-cost.get(0));
                if(index>=0 && index< fitness.size()) {
                    fitness.set(index, num / den);
                }
            }
        }
        return fitness;
    }

    public void sus(List<S> o, List<S> p,List<Double> f, int nSel,int n){
        S tmp;
        double x =0,cumfit=0,totfit=0;
        int i,j,lastI;
        for (i =0;i<n;i++){
            totfit +=f.get(i);
        }
        x = random.nextDouble();
        cumfit = f.get(0);
        lastI = -1;
        i=j=0;
        while (j<nSel){
            if(totfit * x < cumfit*nSel){
               if(i!=lastI){
                   tmp = o.get(j);
                   o.set(j,p.get(i));
                   p.set(i,tmp);
                   lastI = i;
               }else{
                    o.set(j,(S)o.get(j-1).copySolution());
               }
               j++;
               x++;
            }else if(i < n-1){
                cumfit += f.get(++i);
            }else{
                /* Should never *ever* happen*/
            }
        }
        /*Shuffle offspring*/
        for (i=n-1;i>0;i--){
            j=random.nextInt(i+1);
            tmp= o.get(i);
            o.set(i,o.get(j));
            o.set(j,tmp);
        }
    }
    public void singleStepMutation(List<S> population,int n,double pm,M tmpMove){
        for (int i = 0; i < n; i++) {
            if(random.nextDouble()<pm){
                problem.randomMove(tmpMove,population.get(i));
                problem.applyMove(population.get(i),tmpMove);
            }
        }
    }

    public void standardMutation(List<S> population,int n, double Pm, M tmpMove, PS tmpPath){
        double pm;
        int k=0;
        for (int i = 0; i < n; i++) {
            problem.initPathAwayFrom(tmpPath,population.get(i));
            k = problem.getPathLength(tmpPath);
            pm =1.0-Math.pow(1.0-Pm,1/k);
            k = Util.getBinomial(k,pm);
            for (int j = 0; j < k ; j++) {
                problem.nextRandomMove(tmpMove,tmpPath);
                problem.applyMove(population.get(i),tmpMove);
            }
        }
    }
    public void geometricRecombination(List<S> population,int n,double Px, M tmpMove, PS tmpPath){
        S tmpSol= null;
        int k;
        tmpSol = population.get(n-1);
        for (int i = 0; i < n ; i++) {
            if(random.nextDouble()<Px){
                problem.initPathTo(tmpPath,tmpSol,population.get(i));
                k = problem.getPathLength(tmpPath);
                if(k>1){
                    k -=random.nextInt(k/2)+1;
                    /* This depends on the current path length being updated by
                     * nextRandomMove() and assumes that calling getPathLength() is
                     * cheap. */
                    while (problem.getPathLength(tmpPath)>k){
                        tmpMove= problem.nextRandomMove(tmpMove,tmpPath);
                        tmpSol=problem.applyMove(tmpSol,tmpMove);
                    }
                }
            }
            tmpSol = population.get(i);
        }
    }
    public ST newSolver(P problem, int popSize) {
        SGAState solverState = new SGAState<S,M,PS,P>();
        solverState.setProblem(problem);
        solverState.setPopSize(popSize);
        List<S> parent = new ArrayList<S>(popSize);
       // for (int i = 0; i < popSize ; i++) {
        //    parent.add(problem.createSolution());
        //}
        solverState.setParent(parent);
        solverState.setBest(problem.createSolution());
        solverState.setCost(new ArrayList<Double>());
        /*temporary workspace*/
        List<S> offspring = new ArrayList<S>();
        for (int i = 0; i < popSize; i++) {
            offspring.add(problem.createSolution());
        }
        solverState.setOffspring(offspring);
        solverState.setMove(problem.createMove());
        solverState.setPathState(problem.createPathState());
        solverState.setRankingAux(new ArrayList<Double>());
        solverState.setFitness(new ArrayList<Double>());
        /*state initialization*/
        for (int i = 0; i < popSize; i++) {
            solverState.getParent().add(problem.randomSolution());
        }
        solverState.setMincost(Double.MAX_VALUE);
        for (int i = 0; i < popSize; i++) {
            double auxCost=problem.getObjectiveValue((S)solverState.getParent().get(i));
            solverState.getCost().add(auxCost);
            if(auxCost<solverState.getMincost()){
                solverState.setMincost(auxCost);
                solverState.setBest((((S) solverState.getParent().get(i)).copySolution()));
            }
        }
        solverState.setSp(2.0);
        solverState.setPx(0.6);
        solverState.setPm((1.0-1.0/solverState.getSp())*0.8);

        return (ST)solverState;
    }

    public ST nextSolverState(ST solverState) {
        SGAState ss = (SGAState)solverState;
        List<S> tmpPopulation;
        int popsize= ss.getPopSize();
        /*Fitness assignment*/
        ss.setFitness(this.ranking(ss.getRankingAux(),ss.getCost(),ss.getSp(),ss.getPopSize()));
        /*Parental selection (sampling)*/
        this.sus(ss.getOffspring(),ss.getParent(),ss.getFitness(),popsize,popsize);
        /*Geometric recombination (in place)*/
        this.geometricRecombination(ss.getOffspring(),popsize,ss.getPx(),(M)ss.getMove(),(PS)ss.getPathState());
        /*Single-step mutation (in place)*/
        this.singleStepMutation(ss.getOffspring(),popsize,ss.getPm(),(M)ss.getMove());
        /*Unconditional generational replacement*/
        tmpPopulation = new ArrayList<S>();
        tmpPopulation.addAll(ss.getParent());
        ss.setParent(ss.getOffspring());
        ss.setOffspring(tmpPopulation);
        for (int i = 0; i < popsize ; i++) {
            double auxCost= problem.getObjectiveValue((S)ss.getParent().get(i));
            ss.getCost().set(i,auxCost);
            if(auxCost<ss.getMincost()){
                ss.setMincost(auxCost);
                ss.setBest((((S) ss.getParent().get(i)).copySolution()));
            }
        }
        return (ST)ss;
    }

    public S getSolverSolution(ST solverState) {
        SGAState aux = (SGAState)solverState;
        return (S)aux.getBest();
    }
    public double getSelectivePressure(ST solverState) {
        SGAState aux = (SGAState) solverState;
        return aux.getSp();
    }
    public double getRecombinationRate(ST solverState) {
        SGAState aux = (SGAState) solverState;
        return aux.getPx();
    }
    public double getMutationRate(ST solverState) {
        SGAState aux = (SGAState) solverState;
        return aux.getPm();
    }
    public void setSelectivePressure(ST solverState,double SP) {
        SGAState aux = (SGAState) solverState;
        if(SP >=1.0 && SP <=2.0){
            aux.setSp(SP);
        }else{
            System.out.println("SGA: Invalid selective pressure. Unchanged");
        }
    }
    public void setRecombinationRate(ST solverState,double Px) {
        SGAState aux = (SGAState) solverState;
        if (Px >=0.0 && Px<=1.0){
            aux.setPx(Px);
        }else{
            System.out.println("SGA: Invalid recombination rate. Unchanged");
        }
    }
    public void setMutatonRate(ST solverState,double Pm){
        SGAState aux = (SGAState) solverState;
        if (Pm>=0.0 && Pm<=1.0){
            aux.setPm(Pm);
        }else{
            System.out.println("SGA: Invalid mutation rate. Unchanged");
        }
    }
}
