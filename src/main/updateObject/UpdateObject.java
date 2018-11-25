package main.updateObject;

import java.util.List;

public class UpdateObject {


    //Punkte von allen Molekülen
    List<Point3d> points;

    //beste Lösung -> Textuelle anzeige
    Point3d bestPoint;



    //Für gui -> Linkes oder Rechtes Fenster updaten
    int algorithmCounter;

    //Aktuelle Iteration
    int iteration;

    public UpdateObject(List<Point3d> points, Point3d bestPoint,int algorithmCounter,int iteration) {
        this.points = points;
        this.bestPoint=bestPoint;
        this.algorithmCounter = algorithmCounter;
        this.iteration = iteration;
    }


    public Point3d getBestPoint() {
        return bestPoint;
    }

    public List<Point3d> getPoints() {
        return points;
    }

    public int getAlgorithmCounter() {
        return algorithmCounter;
    }

    public int getIteration() {
        return iteration;
    }
}
