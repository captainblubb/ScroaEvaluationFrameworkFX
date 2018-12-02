package configuration.neighbourhoodSearch.neighbourhoodSearchSingle;

import configuration.configuration.globalConfig;
import configuration.equations.IEquation;
import algorithmns.croa.models.Point;
import configuration.randomGenerator.IRandomGenerator;

public class MoveAlongGrade implements INeighbourhoodSearchSingle {

    IRandomGenerator randomGenerator;
    IEquation equation;

    public MoveAlongGrade(IRandomGenerator randomGenerator, IEquation equation){

        this.randomGenerator = randomGenerator;
        this.equation=equation;
    }


    /*
        In Richtung der Steigung einen normalisierten Vektor berechnen
        -> in Richtung der Steigung 0-100% x und y in diese Richtung verändern
     */
    public Point findNeighbour(Point point) {
        double newX = point.x;
        double newY = point.y;

        //Steigung an eingegebenen Punkt
        Point grade = equation.calculateGrade(point);


        //Vector durch Länge = Normalisierung auf Länge 1 Dafür wird hier die Länge berchnet
        double vectorLength = Math.sqrt( Math.pow(grade.x,2.0)  + Math.pow(grade.y,2.0));

        //Wenn der Vector kleiner 1 ist soll er nicht normalisiert werden => Länge auf 1 gesetzt werden
        if(vectorLength<1.0) {
            vectorLength = 1;
        }

        // 0...1
        double factor = randomGenerator.nextDouble()* globalConfig.configurationAlgorithm.MoveAlongGradeMaxStep;

        //Faktor * Richtung der Steigung
        newX += grade.x * (1 / vectorLength) * factor;
        newY += grade.y * (1 / vectorLength) * factor;


        if (equation.getBoundrys().inBoundry(newX,newY)){
            return new Point(newX,newY);
        }

        return null;
    }

}
