package algorithmns.equations;

import configuration.configuration.ConfigurationAlgorithm;
import algorithmns.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;

public interface IEquation {

    ConfigurationAlgorithm getConfiguration();
    double calculateValue(Point point);
    Point calculateGrade(Point point);
    Boundrys getBoundrys();
}
