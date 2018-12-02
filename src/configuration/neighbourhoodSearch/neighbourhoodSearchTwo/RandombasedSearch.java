package configuration.neighbourhoodSearch.neighbourhoodSearchTwo;

import configuration.equations.IEquation;
import configuration.equations.boundrys.Boundrys;
import algorithmns.croa.models.Point;
import configuration.randomGenerator.IRandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class RandombasedSearch implements INeighbourhoodSearchTwo {


    IRandomGenerator randomGenerator;
    IEquation equation;

    public RandombasedSearch(IRandomGenerator randomGenerator, IEquation equation){
        this.randomGenerator = randomGenerator;
        this.equation = equation;
    }

    public List<Point> randomGenerateRandomCombination(Point point1, Point point2) {

        ArrayList<Point> newPoints = new ArrayList<>();

        Point newPoint1=null;

        int counter1 = 0;
        while (newPoint1==null && counter1 < 20){
            counter1++;
            newPoint1 = generatePointWithOtherPoint(point1,point2);
        }

        Point newPoint2= null;
        int counter2 = 0;
        while (newPoint2==null || counter2 < 20) {
            counter2++;
            newPoint2 = generatePointWithOtherPoint(point1, point2);
        }


        Boundrys boundrys = equation.getBoundrys();

        if (newPoint1 != null && boundrys.inBoundry(newPoint1.x,newPoint1.y)){
            newPoints.add(newPoint1);
        }else {
            newPoints.add(point1);
        }


        if (newPoint2 != null && boundrys.inBoundry(newPoint2.x,newPoint2.y)){
            newPoints.add(newPoint2);
        }else {
            newPoints.add(point2);
        }

        return newPoints;

    }


    //Generate new point with a slitly impact of a other point
    public Point generatePointWithOtherPoint(Point point1, Point impactPoint){

        double impactX = (randomGenerator.nextDouble()*0.2-0.1)*impactPoint.x;
        double impactY = (randomGenerator.nextDouble()*0.2-0.1)*impactPoint.y;

        return new Point(point1.x+impactX,point1.y+impactY);

    }
}
