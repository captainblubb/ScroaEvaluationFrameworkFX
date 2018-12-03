package algorithmns.equations;

import configuration.configuration.ConfigurationAlgorithm;
import algorithmns.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;

public class Rastrigin implements IEquation {

    Boundrys boundry;
    ConfigurationAlgorithm configurationAlgorithm;

    public Rastrigin(){
        boundry = new Boundrys(-5.12,5.12,-5.12,5.12);
        configurationAlgorithm = new ConfigurationAlgorithm(
                1.4962, //c1
                1.4962, //c2
                0.72984,//w
                5,    //maxVelocity
                2,     //InitialMaxLengthVelocityPerDim
                0.3,   //minVelocityStepInPerCent
                10,    //trysOfPSOUpdate
                0.05,  //distaneToBoundrys
                0.1,   //KEminLossRate
                0.6,   //MoleColl %
                50.0,  //InitialKE
                5.0,   //minimumKE
                0.0,   //InitialBuffer
                60,    //numberOfHitsForDecomposition
                0.0001, //MoveAlonGradeMaxStep);
                0.1); //
    }

    @Override
    public ConfigurationAlgorithm getConfiguration() {
        return configurationAlgorithm;
    }

    @Override
    public double calculateValue(Point point) {

        double[] koordinates = {point.x,point.y};

        double sum = 10 * 2;
        for (int i = 0; i<2; i++){

            sum+= Math.pow(koordinates[i],2) - 10*Math.cos(2*Math.PI*koordinates[i]);
        }
        return sum;
    }

    @Override
    public Point calculateGrade(Point point) {
        //TODO: REIMPLEMENT -> NOT Grade its RANDOM
        return calculateRandomVector();
    }

    private Point calculateRandomVector() {
        return new Point(5.12-2*5.12*Math.random(),5.12-2*5.12*Math.random());
    }

    @Override
    public Boundrys getBoundrys() {
        return boundry;
    }
}
