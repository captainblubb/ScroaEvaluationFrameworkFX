package algorithmns.croa.models;

import configuration.bestSolution.IBestSolutionNotifier;
import algorithmns.croa.calculatePE.ICalculatorPE;
import configuration.equations.IEquation;

public interface IMolecule extends IBestSolutionNotifier {

    ICalculatorPE getCalculatorPE();
    void increaseNumberOfHits();
    Point getCurrentStructure();
    double getKE();
    int getNumberOfHits();
    double getPE();
    IEquation getEquation();
    void setSolution(Point v);
    void setKE(double KE);
    Point getMinStructure();
    double getMinPE();
}
