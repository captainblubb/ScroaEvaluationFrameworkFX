package algorithmns.croa.models;

import algorithmns.bestSolutionObserver.IBestSolutionListener;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.equations.IEquation;

import java.util.ArrayList;

public class MoleculeCROA implements IMolecule {

    public Point getCurrentStructure() {
        return currentStructure;
    }

    public double getKE() {
        return KE;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    ArrayList<IBestSolutionListener> solutionListeners = new ArrayList<>();


    public void increaseNumberOfHits() {
        numberOfHits++;
    }


    private Point currentStructure;

    public double getPE() {
        return PE;
    }

    private double PE;

    public void setKE(double KE) {
        this.KE = KE;
    }

    private double KE;

    private int numberOfHits=0;

    public IEquation getEquation() {
        return equation;
    }

    private IEquation equation;


    //Best Solution currently
    //############################
    private Point minStructure;

    public ICalculatorPE getCalculatorPE() {
        return calculatorPE;
    }

    private ICalculatorPE calculatorPE;

    public Point getMinStructure() {
        return minStructure;
    }

    public double getMinPE() {
        return minPE;
    }

    private double minPE;

    private int minHits;


    //############################

    // 
    public MoleculeCROA(Point m_StartPoint, double m_KE, ICalculatorPE m_calculatorPE, IEquation equation){

        currentStructure = m_StartPoint;
        this.KE = m_KE;
        this.equation = equation;
        this.calculatorPE = m_calculatorPE;
        calculatePE();

        //Set Best solution
        minPE = PE;
        minStructure =currentStructure;
        minHits= numberOfHits;

    }


    /*
        Calculate Z over interface 
     */
    private void calculatePE(){
        PE = calculatorPE.calculatePE(currentStructure.x,currentStructure.y,equation);
    }


    ///
    ///
    ///
    public void setSolution(Point v){

        this.currentStructure =v;
        calculatePE();

        if(PE < minPE){
            //Set Best solution
            minPE = PE;
            minStructure =currentStructure;
            minHits= numberOfHits;
            //notify
            notifyBestSolutionChanged();
        }
    }

    @Override
    public void register(IBestSolutionListener bestSolutionListener) {
        solutionListeners.add(bestSolutionListener);
    }

    @Override
    public void unregister(IBestSolutionListener bestSolutionListener) {
        solutionListeners.remove(bestSolutionListener);
    }

    @Override
    public void notifyBestSolutionChanged() {
        for (IBestSolutionListener a : solutionListeners) {
            a.getNotified(minStructure,minPE);
        }
    }
}
