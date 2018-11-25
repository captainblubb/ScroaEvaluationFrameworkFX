package algorithmns.croa;

import algorithmns.IAlgorithm;
import algorithmns.croa.bestSolution.BestSolution;
import algorithmns.croa.bestSolution.IBestSolutionListener;
import algorithmns.croa.calculatePE.CalculateFunction;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.croa.chemicalReaction.ChemicalReaction;
import algorithmns.croa.chemicalReaction.IChemicalReactions;
import algorithmns.croa.chemicalReaction.decomposition.Decomposition;
import algorithmns.croa.chemicalReaction.decomposition.IDecomposition;
import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.IInterMolecularIneffectiveCollission;
import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.InterMolecularIneffectiveCollission;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.IOnWallIneffectiveCollission;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.OnWallIneffectiveCollission;
import algorithmns.croa.chemicalReaction.synthesis.ISynthesis;
import algorithmns.croa.chemicalReaction.synthesis.Synthesis;
import algorithmns.croa.equations.IEquation;
import algorithmns.croa.equations.boundrys.Boundrys;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.MoleculeCROA;
import algorithmns.croa.models.Point;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchSingle.INeighbourhoodSearchSingle;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchSingle.MoveAlongGrade;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchTwo.INeighbourhoodSearchTwo;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchTwo.RandombasedSearch;
import configuration.globalConfig;
import configuration.logger.LoggerFileWriter;
import configuration.randomGenerator.IRandomGenerator;
import configuration.randomGenerator.MersenneTwisterFast;
import main.updateObject.IUpdateable;
import main.updateObject.Point3d;
import main.updateObject.UpdateObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

public class CROA implements IAlgorithm {

    IEquation equation;
    Buffer buffer;
    IChemicalReactions chemicalReactions;
    ArrayList<IMolecule> molecules;
    IRandomGenerator randomGenerator;
    ICalculatorPE calculatorPE;
    LoggerFileWriter loggerFileWriter;
    CyclicBarrier barrier;
    Thread croaThread;

    IUpdateable gui;

    //Observerpattern which stores the best current solution of all Molecues
    IBestSolutionListener currentBestSolution;

    //For update -> which algorithmn needs to be updated 1 or 2
    int algorithmCounter;

    public CROA(IEquation equation, IUpdateable gui, int algorithmCounter,  CyclicBarrier barrier){
        this.barrier = barrier;
        this.equation = equation;
        this.gui = gui;
        this.algorithmCounter = algorithmCounter;
        if(globalConfig.loggin) {
            this.loggerFileWriter = new LoggerFileWriter("croa",algorithmCounter);
        }
        this.currentBestSolution = new BestSolution();
        // Initialzation

        //buffer
        buffer = new Buffer(globalConfig.InitialBuffer);
        calculatorPE = new CalculateFunction();
        // Initialize algorithmns.croa
        //random generator -> init with nanoTime for less cluster
        randomGenerator = new MersenneTwisterFast(System.nanoTime());
        //neighbourhoodSearchSingle
        INeighbourhoodSearchSingle neighbourhoodSearchSingle = new MoveAlongGrade(randomGenerator,equation);
        //neighbourhoodSearchTwo
        INeighbourhoodSearchTwo neighbourhoodSearchTwo = new RandombasedSearch(randomGenerator,equation);

        //Chemical Reactions
        //decomposition
        IDecomposition decomposition = new Decomposition(randomGenerator,buffer);
        //synthesis
        ISynthesis synthesis = new Synthesis(randomGenerator,buffer);
        //onwallIneffective
        IOnWallIneffectiveCollission onWallIneffectiveCollission = new OnWallIneffectiveCollission(randomGenerator,neighbourhoodSearchSingle,buffer);
        //intermolecularCollission
        IInterMolecularIneffectiveCollission interMolecularIneffectiveCollission = new InterMolecularIneffectiveCollission(randomGenerator,neighbourhoodSearchTwo,buffer);
        //Chemical Reaction
        chemicalReactions = new ChemicalReaction(decomposition,onWallIneffectiveCollission,synthesis,interMolecularIneffectiveCollission);

        //Population
        molecules = generateRandomPopulation(equation, globalConfig.PopSize, randomGenerator);

        for (IMolecule m: molecules) {
            m.register(currentBestSolution);
        }
    }



    public ArrayList<IMolecule> generateRandomPopulation(IEquation equation, int initialPopoSize, IRandomGenerator randomGenerator){

        ArrayList<IMolecule> population = new ArrayList<>();

        Boundrys boundrys = equation.getBoundrys();
        //Generate Population in Boundries


        //Check boundries start in boundrie not on boundrie
        double xRange = (boundrys.getMaxX() - boundrys.getMinX());
        if(xRange-2* globalConfig.distanceToBoundrys > 1){
            xRange-=(2* globalConfig.distanceToBoundrys);
        }
        double yRange = (boundrys.getMaxY() - boundrys.getMinY());
        if(yRange-2* globalConfig.distanceToBoundrys > 1){
            yRange-=(2* globalConfig.distanceToBoundrys);
        }


        for (int i = 0; i<initialPopoSize;i++){

            double fixInX = boundrys.getMinX()+ globalConfig.distanceToBoundrys;
            double fixInY = boundrys.getMinY()+ globalConfig.distanceToBoundrys;

            double randomX = randomGenerator.nextDouble()*xRange;
            double randomY = randomGenerator.nextDouble()*yRange;

            Point point = new Point(fixInX+randomX,fixInY+randomY);
           //System.out.println(point.toParseFormat());
            MoleculeCROA molecule = new MoleculeCROA(point, globalConfig.InitialKE,calculatorPE,equation);
            population.add(molecule);

        }

        return population;
    }



    @Override
    public void stop(){
        croaThread.interrupt();
    }

    @Override
    public void run() {

        /*
        try {
            croaThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
            */

                    int onWallCollHappend = 0;
                    int interMolColHappend = 0;
                    int decompositionTrys = 0;
                    int decompositionHappend = 0;
                    int synthesisHappend = 0;
                    int synthesisTrys = 0;
                    double energyStart = molecules.stream().map(m -> m.getKE() + m.getPE()).collect(Collectors.summingDouble(d -> d)) + buffer.getBuffer();

                    try {

                        int currentIteration = 0;


                        while (currentIteration < globalConfig.Iterations) {


                            currentIteration++;
                            double randomCollission = randomGenerator.nextDouble();


                            //unimolekular reaction
                            if (randomCollission >= globalConfig.MoleColl || molecules.size() == 1) {

                                int randomIndex = randomGenerator.nextInt(0, molecules.size() - 1);

                                IMolecule selectedMolecule = molecules.get(randomIndex);

                                if (selectedMolecule.getNumberOfHits() >= globalConfig.numberOfHitsForDecomposition) {
                                    //decomposition
                                    decompositionTrys++;
                                    List<IMolecule> decompositionResult = chemicalReactions.decomposition(selectedMolecule);
                                    if (decompositionResult.size() == 2) {

                                        // System.out.println("--------Decomposition------------");
                                        decompositionHappend++;
                                        molecules.remove(randomIndex);
                                        molecules.add(decompositionResult.get(0));
                                        molecules.add(decompositionResult.get(1));
                                    }

                                } else {
                                    //onWall
                                    // System.out.println("--------onWallIneffectivce------------");
                                    onWallCollHappend++;
                                    chemicalReactions.onWallIneffectivCollission(selectedMolecule);
                                }

                                //intermolekular reaction
                            } else {

                                int randomIndex1 = randomGenerator.nextInt(0, molecules.size() - 1);
                                int randomIndex2 = randomGenerator.nextInt(0, molecules.size() - 1);

                                while (randomIndex1 == randomIndex2) {
                                    randomIndex2 = randomGenerator.nextInt(0, molecules.size() - 1);
                                }

                                IMolecule molecule1 = molecules.get(randomIndex1);
                                IMolecule molecule2 = molecules.get(randomIndex2);

                                if (molecule1.getKE() <= globalConfig.minimumKe && molecule2.getKE() <= globalConfig.minimumKe) {
                                    //synthesis
                                    synthesisTrys++;

                                    IMolecule synthesisResult = chemicalReactions.synthesis(molecule1, molecule2);
                                    if (synthesisResult != null) {
                                        // System.out.println("--------Synthesis------------");
                                        synthesisHappend++;
                                        molecules.remove(molecule1);
                                        molecules.remove(molecule2);
                                        molecules.add(synthesisResult);
                                    }

                                } else {
                                    //intermolcol
                                    // System.out.println("--------interMolIneffectivce------------");
                                    interMolColHappend++;

                                    chemicalReactions.interMolecularIneffectivCollission(molecule1, molecule2);
                                }

                            }


                            if (globalConfig.loggin) {
                                loggerFileWriter.logBestSolution("scroa", currentIteration, currentBestSolution.getBestSolutionPoint(), currentBestSolution.getBestPE());
                            }

                            if (currentIteration % globalConfig.updateAfterIterations == 0) {
                                List<Point3d> collect = molecules.stream().map(m -> new Point3d(m.getCurrentStructure().x, m.getCurrentStructure().y, m.getPE())).collect(Collectors.toList());
                                Point bestSolution = currentBestSolution.getBestSolutionPoint();
                                gui.update(new UpdateObject(collect, new Point3d(bestSolution.x, bestSolution.y, currentBestSolution.getBestPE()), algorithmCounter, currentIteration));
                            }

                            //Wait for other Thread to Synchronize
                            barrier.await();
                        }
                    } catch (Exception exp) {
                        loggerFileWriter.logInformation("croa Thread ended in an Exception: " + exp);
                    }


                    double energyEnd = molecules.stream().map(m -> m.getKE() + m.getPE()).collect(Collectors.summingDouble(d -> d)) + buffer.getBuffer();
                    System.out.println("CROA  algorithm (" +algorithmCounter+ ") Engieblinazstart : " + energyStart + "Engieblinazende : " + energyEnd + " start - end" + (energyStart - energyEnd));
                    System.out.println("CROA  algorithm (" +algorithmCounter+ ") interMol: " + interMolColHappend + " onWallin " + onWallCollHappend + " synthesis trys: "+synthesisTrys+" synthesis happend : " + synthesisHappend +" decomposition trys: "+ decompositionTrys+" decomposition happend " + decompositionHappend);
                    System.out.println("CROA  algorithm (" +algorithmCounter+ ") Average KE end " + molecules.stream().map(m -> m.getKE()).collect(Collectors.averagingDouble(d -> d)));
                    System.out.println("CROA  algorithm (" +algorithmCounter+ ") buffer at end " + buffer.getBuffer());


                    /*

                });
            }
            //wait??
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            stop();
        }

        */

    }



}
