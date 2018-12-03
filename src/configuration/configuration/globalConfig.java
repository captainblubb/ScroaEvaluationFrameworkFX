package configuration.configuration;

//Holding Parameters
public class globalConfig {

    //Configuration for Algorithm
    public static configuration.configuration.configurationAlgorithm configurationAlgorithm = new configurationAlgorithm(); //Default config.. Set when new Equation loaded

    public static int updateAfterIterations = 500;

    public static final int PopSize = 250; //Initial Population size
    public static final int Iterations = 40000;

    //public static int initialStepsize = 1; //Steps between Moles @ Init
    public static final double distanceToBoundrys = 0.05; // Init distance to boundry
    public static final boolean loggin = false;

    //round after , in ToString in Point
    public static double positionsAfterKomma = 8;
}
