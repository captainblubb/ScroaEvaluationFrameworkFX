package configuration;

//Holding Parameters
public class globalConfig {

    public static int PopSize = 250; //Initial Population size
    public static int Iterations = 40000;

    //public static int initialStepsize = 1; //Steps between Moles @ Init
    public static double distanceToBoundrys = 0.05; // Init distance to boundry
    public static boolean loggin = false;
    public static int updateAfterIterations = 500;

    //High impact on CROA Algorithm
    public static double KEminLossRate = 0.1; //min Ke that is lost in onWallIneffective in %
    public static double MoleColl = 0.6; //Percentage of propability -> unimol or intermol reaction ::: MoleColl Higher results in more intermol coll
    public static double InitialKE = 50.0; // Initial KE of each MoleculeCROA
    public static double minimumKe = 1.0; //Minimum of KE -> if Ke is less -> synthesis
    public static double InitialBuffer = 0.0; // Initial Buffer Energy
    public static int numberOfHitsForDecomposition = 60; //hits in molecule which leads to decomposition

    //NeighboursearchSingle
    public static double MoveAlongGradeMaxStep = 0.0001;

    //round after , in ToString in Point
    public static double positionsAfterKomma = 7;
}
