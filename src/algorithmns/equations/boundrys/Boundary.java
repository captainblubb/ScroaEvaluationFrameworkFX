package algorithmns.equations.boundrys;

@SuppressWarnings("SpellCheckingInspection")
public class Boundary {


    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }


    public Boundary(double minX, double maxX, double minY, double maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public boolean inBoundary(double x, double y){

        return x >= minX && x <= maxX && y >= minY && y <= maxY;

    }
}
