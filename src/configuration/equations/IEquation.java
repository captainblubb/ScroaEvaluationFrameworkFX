package configuration.equations;

import configuration.configuration.ConfigurationAlgorithm;
import configuration.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;

public interface IEquation {

    ConfigurationAlgorithm getConfiguration();
    double calculateValue(Point point);
    Point calculateGrade(Point point);
    Boundrys getBoundrys();
}
