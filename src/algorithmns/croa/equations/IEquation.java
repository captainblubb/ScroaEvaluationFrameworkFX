package algorithmns.croa.equations;

import algorithmns.croa.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;

public interface IEquation {

    double calculateValue(Point point);
    Point calculateGrade(Point point);
    Boundrys getBoundrys();
}
