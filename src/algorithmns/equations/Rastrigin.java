package algorithmns.equations;

import configuration.configuration.configurationAlgorithm;
import algorithmns.equations.boundrys.Boundary;
import algorithmns.croa.models.Point;

public class Rastrigin implements IEquation {

    Boundary boundary;
    configuration.configuration.configurationAlgorithm configurationAlgorithm;

    public Rastrigin(){
        boundary = new Boundary(-5.12,5.12,-5.12,5.12);
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
                0.0001, //MoveAlonGradeMaxStep);
                0.1); //
    }

    @Override
    public configuration.configuration.configurationAlgorithm getConfiguration() {
        return configurationAlgorithm;
    }

    @Override
    public double calculateValue(Point point) {

        double[] koordinates = {point.x,point.y};


        //could be also wrote as : -10 cos(2* π *x) - 10 cos(2 *π *y) + x^2 + y^2
        double sum = 10 * 2;
        for (int i = 0; i<2; i++){

            sum+= Math.pow(koordinates[i],2) - 10*Math.cos(2*Math.PI*koordinates[i]);
        }
        return sum;
    }

    @Override
    public Point calculateGrade(Point point) {

        return calculateRandomVector();
    }

    private Point calculateRandomVector() {
        return new Point(5.12-2*5.12*Math.random(),5.12-2*5.12*Math.random());
    }

    @Override
    public Boundary getBoundary() {
        return boundary;
    }
}
