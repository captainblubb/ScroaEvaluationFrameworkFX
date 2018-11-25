package algorithmns.croa.bestSolution;

import algorithmns.croa.models.Point;

public interface IBestSolutionListener {

    void getNotified(Point point, double PE);
    Point getBestSolutionPoint();
    double getBestPE();
}
