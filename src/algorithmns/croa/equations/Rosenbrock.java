package algorithmns.croa.equations;

import algorithmns.croa.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;


/*
    This Class represents the simpled Rosenbrock function
    f(x,y) = (0-x)^2 + 100(y-x^2)^2
 */
public class Rosenbrock implements IEquation{

    Boundrys boundrys;

    public Rosenbrock(){
        this.boundrys = new Boundrys(-2.5,2.5,-2.5,2.5);
    }

    //return result for f(x,y)
    public double calculateValue(Point point) {
        return (Math.pow((0.0-point.x),2.0)+ 105.0*(Math.pow(point.y-Math.pow(point.x,2),2)));
    }

    //Calculate direction to move
    public Point calculateGrade(Point point) {
        double GradInX = 2.0*point.x*(200.0 * Math.pow(point.x,2.0) - 200.0 * point.y + 1.0);
        double GradInY = 200.0*(point.y - Math.pow(point.x,2.0));
        return new Point(GradInX,GradInY);
    }

    @Override
    public Boundrys getBoundrys() {
        return boundrys;
    }


}