package algorithmns.croa.models;

import static configuration.configuration.globalConfig.positionsAfterKomma;

public class Point {

    private double roundTo = Math.pow(10,positionsAfterKomma);

    public double x,y;

    public Point(double x, double y){

        this.x = x;
        this.y = y;

    }

    @Override
    public String toString(){
        return "("+Math.round(this.x*roundTo)/roundTo +","+Math.round(this.y*roundTo)/roundTo+")";
    }


    public String toParseFormat(){

        //format point(-3|4)
        return "point("+Math.round(this.x*roundTo)/roundTo +"|"+Math.round(this.y*roundTo)/roundTo+")";
    }

}
