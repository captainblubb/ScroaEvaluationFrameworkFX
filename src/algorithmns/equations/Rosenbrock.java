package algorithmns.equations;

import configuration.configuration.configurationAlgorithm;
import algorithmns.equations.boundrys.Boundary;
import algorithmns.croa.models.Point;


/*
    This Class represents the simpled Rosenbrock function
    f(x,y) = (0-x)^2 + 100(y-x^2)^2
 */
public class Rosenbrock implements IEquation{

    Boundary boundary;
    configuration.configuration.configurationAlgorithm configurationAlgorithm;

    public Rosenbrock(){
        this.boundary = new Boundary(-2.5,2.5,-2.5,2.5);
        configurationAlgorithm = new configurationAlgorithm(
                1.4962, //c1
                1.4962, //c2
                0.72984,//w
                5,    //maxVelocity
                2,     //initialMaxLengthVelocityPerDim
                0.3,   //minVelocityStepInPerCent
                10,    //trysOfPSOUpdate
                0.05,  //distaneToBoundrys
                0.1,   //keMinLossRate
                0.6,   //moleColl %
                50.0,  //initialKE
                5.0,   //minimumKE
                0.0,   //initialBuffer
                60,    //numberOfHitsForDecomposition
                0.0001,//MoveAlonGradeMaxStep);
                0.1); //Impact of Molecule to other Molecule in intermolecular coll
    }

    @Override
    public configuration.configuration.configurationAlgorithm getConfiguration() {
        return configurationAlgorithm;
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
    public Boundary getBoundary() {
        return boundary;
    }


}