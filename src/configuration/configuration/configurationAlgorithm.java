package configuration.configuration;

public class configurationAlgorithm {

    public configurationAlgorithm(double c1, double c2, double w, double maxVelocity, double InitialMaxLengthVelocityPerDim,
                                  double minVelocityStep, int trysOfPSOUpdate, double distanceToBoundrys, double keMinLossRate,
                                  double MoleColl, double InitialKE, double minimumKe, double InitialBuffer, int numberOfHitsForDecomposition,
                                  double MoveAlongGradeMaxStep, double impactOfOtherMolecule){

        this.c1 = c1;
        this.c2=c2;
        this.w = w;
        this.maxVelocity = maxVelocity;
        this.initialMaxLengthVelocityPerDim = InitialMaxLengthVelocityPerDim;
        this.minVelocityStep = minVelocityStep;
        this.trysOfPSOUpdate = trysOfPSOUpdate;
        this.distanceToBoundrys = distanceToBoundrys;
        this.keMinLossRate = keMinLossRate;
        this.moleColl = MoleColl;
        this.initialKE = InitialKE;
        this.minimumKe = minimumKe;
        this.initialBuffer = InitialBuffer;
        this.numberOfHitsForDecomposition = numberOfHitsForDecomposition;
        this.moveAlongGradeMaxStep = MoveAlongGradeMaxStep;
        this.impactOfOtherMolecule = impactOfOtherMolecule;
    }


    //defaultconfiguration
    public configurationAlgorithm(){

    }


    // Als Standard-Belegung der Variablen wird von Eberhart und Shi [ES00] c1 = c2 = 1.4962 und w = 0.72984 vorgeschlagen,
    public double c1 = 1.4962;
    public double c2 = 1.4962;
    public double w = 0.52984; //inertia weight w; How much % of old velocity is used to calc new
    public double maxVelocity = 5;
    public double initialMaxLengthVelocityPerDim = 2;

    //PSO UPdate
    public double minVelocityStep = 0.3; //Molecule makes step in direction of Velocity % element of [minVelocityStep,1]
    public int trysOfPSOUpdate = 10;

    //public static int initialStepsize = 1; //Steps between Moles @ Init
    public double distanceToBoundrys = 0.05; // Init distance to boundry

    //High impact on CROA Algorithm
    public double keMinLossRate = 0.1; //min Ke that is lost in onWallIneffective in %
    public double moleColl = 0.6; //Percentage of propability -> unimol or intermol reaction ::: moleColl Higher results in more intermol coll
    public double initialKE = 50.0; // Initial KE of each MoleculeCROA
    public double minimumKe = 1.0; //Minimum of KE -> if Ke is less -> synthesis
    public double initialBuffer = 200.0; // Initial Buffer Energy
    public int numberOfHitsForDecomposition = 60; //hits in molecule which leads to decomposition

    //NeighboursearchSingle
    public double moveAlongGradeMaxStep = 0.0001;

    //NeighbourghhoodsearchTwo
    public double impactOfOtherMolecule = 0.1; //Impact of Molecule to other Molecule in intermolecular coll

}
