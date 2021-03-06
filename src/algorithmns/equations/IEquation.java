package algorithmns.equations;

import configuration.configuration.configurationAlgorithm;
import algorithmns.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;

public interface IEquation {

    configurationAlgorithm getConfiguration();
    double calculateValue(Point point);
    Point calculateGrade(Point point);
    Boundrys getBoundrys();
}
