package gui.updateObject;

import static configuration.globalConfig.positionsAfterKomma;

public class Point3d {


    private double roundTo = Math.pow(10,positionsAfterKomma);

        public double x,y,z;

        public Point3d(double x, double y,double z){

            this.x = x;
            this.y = y;
            this.z = z;

        }

        @Override
        public String toString(){
            return "("+Math.round(this.x*roundTo)/roundTo +","+Math.round(this.y*roundTo)/roundTo+","+Math.round(this.z*roundTo)/roundTo+")";
        }


        public String toParseFormat(){

            //format point(-3|4)
            return "point("+Math.round(this.x*roundTo)/roundTo +"|"+Math.round(this.y*roundTo)/roundTo+"|"+Math.round(this.z*roundTo)/roundTo+")";
        }



}
