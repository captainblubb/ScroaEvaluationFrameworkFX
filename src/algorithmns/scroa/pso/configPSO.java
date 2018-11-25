package algorithmns.scroa.pso;

public class configPSO {

   // Als Standard-Belegung der Variablen wird von Eberhart und Shi [ES00] c1 = c2 = 1.4962 und w = 0.72984 vorgeschlagen,
    public static final double c1 = 1.4962;
    public static final double c2 = 1.4962;
    public static final double w = 0.72984; //inertia weight w; How much % of old velocity is used to calc new
    public static final double maxVelocity = 25;
    public static final double InitialMaxLengthVelocityPerDim = 2;


    //Own experiment -> PSO UPdate
    public static final double minVelocityStep = 0.3; //Molecule makes step in direction of Velocity % element of [minVelocityStep,1]
    public static final int trysOfPSOUpdate = 10;
}