package algorithmns.croa.models;

import algorithmns.bestSolutionObserver.IBestSolutionNotifier;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.equations.IEquation;

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
