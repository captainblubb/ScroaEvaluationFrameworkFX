package configuration.configuration;

//Holding Parameters
public class globalConfig {

    //Configuration for Algorithm
    public static ConfigurationAlgorithm configurationAlgorithm = new ConfigurationAlgorithm(); //Default config.. Set when new Equation loaded

    public static int updateAfterIterations = 500;

    public static int PopSize = 250; //Initial Population size
    public static int Iterations = 40000;

    //public static int initialStepsize = 1; //Steps between Moles @ Init
    public static double distanceToBoundrys = 0.05; // Init distance to boundry
    public static boolean loggin = false;

    //round after , in ToString in Point
    public static double positionsAfterKomma = 7;
}
