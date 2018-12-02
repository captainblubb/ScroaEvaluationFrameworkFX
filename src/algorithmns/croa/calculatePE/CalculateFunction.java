package algorithmns.croa.calculatePE;

import configuration.equations.IEquation;
import algorithmns.croa.models.Point;

public class CalculateFunction implements ICalculatorPE {

    public double calculatePE(double x, double y, IEquation equation) {
        return equation.calculateValue(new Point(x,y));
    }
}
