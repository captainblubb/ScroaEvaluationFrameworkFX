package algorithmns.scroa;

import algorithmns.IAlgorithm;
import algorithmns.bestSolutionObserver.BestSolution;
import algorithmns.bestSolutionObserver.IBestSolutionListener;
import algorithmns.croa.calculatePE.CalculateFunction;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.IInterMolecularIneffectiveCollision;
import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.InterMolecularIneffectiveCollision;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.IOnWallIneffectiveCollission;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.OnWallIneffectiveCollission;
import configuration.configuration.globalConfig;
import algorithmns.equations.IEquation;
import algorithmns.equations.boundrys.Boundary;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.Point;
import algorithmns.LocalSearcher.neighbourhoodSearch.neighbourhoodSearchSingle.INeighbourhoodSearchSingle;
import algorithmns.LocalSearcher.neighbourhoodSearch.neighbourhoodSearchSingle.MoveAlongGrade;
import algorithmns.LocalSearcher.neighbourhoodSearch.neighbourhoodSearchTwo.INeighbourhoodSearchTwo;
import algorithmns.LocalSearcher.neighbourhoodSearch.neighbourhoodSearchTwo.RandombasedSearch;
import algorithmns.scroa.chemicalReactions.ChemicalReactionSCROA;
import algorithmns.scroa.chemicalReactions.IChemicalReactionSCROA;
import algorithmns.scroa.models.IMoleculeSCROA;
import algorithmns.scroa.models.MoleculeSCROA;
import algorithmns.scroa.pso.psoUpdate.IPSOUpdate;
import algorithmns.scroa.pso.psoUpdate.PsoUpdate;
import configuration.logger.LoggerFileWriter;
import algorithmns.LocalSearcher.randomGenerator.IRandomGenerator;
import algorithmns.LocalSearcher.randomGenerator.MersenneTwisterFast;
import main.updateObject.IUpdateable;
import main.updateObject.Point3d;
import main.updateObject.UpdateObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;


public class SCROA implements IAlgorithm {


    private Thread scroaThread;

    IEquation equation;
    Buffer buffer;
    IChemicalReactionSCROA chemicalReactionsSCROA;
    ArrayList<IMoleculeSCROA> molecules;
    IRandomGenerator randomGenerator;
    ICalculatorPE calculatorPE;
    LoggerFileWriter loggerFileWriter;
    CyclicBarrier barrier;
    IUpdateable gui;



    //Observerpattern which stores the best current solution of all Molecues
    IBestSolutionListener currentBestSolution = null;

    //For update -> which algorithmn needs to be updated 1 or 2
    int algorithmCounter;

    public SCROA(IEquation equation, IUpdateable gui, int algorithmCounter,  CyclicBarrier barrier){
        this.barrier = barrier;
        this.equation = equation;
        this.gui = gui;
        this.algorithmCounter = algorithmCounter;
        if(globalConfig.loggin) {
            this.loggerFileWriter = new LoggerFileWriter("scroa",algorithmCounter);
        }
        this.currentBestSolution = new BestSolution();
        // Initialzation

        //buffer
        buffer = new Buffer(globalConfig.configurationAlgorithm.initialBuffer);
        calculatorPE = new CalculateFunction();
        // Initialize algorithmns.croa
        //random generator -> init with nanoTime for less cluster
        randomGenerator = new MersenneTwisterFast(System.nanoTime());
        //neighbourhoodSearchSingle
        INeighbourhoodSearchSingle neighbourhoodSearchSingle = new MoveAlongGrade(randomGenerator,equation);
        //neighbourhoodSearchTwo
        INeighbourhoodSearchTwo neighbourhoodSearchTwo = new RandombasedSearch(randomGenerator,equation);

        //Chemical Reactions
        IPSOUpdate ipsoUpdate = new PsoUpdate(currentBestSolution,buffer,randomGenerator);
        //onwallIneffective
        IOnWallIneffectiveCollission onWallIneffectiveCollission = new OnWallIneffectiveCollission(randomGenerator,neighbourhoodSearchSingle,buffer);
        //intermolecularCollission
        IInterMolecularIneffectiveCollision interMolecularIneffectiveCollission = new InterMolecularIneffectiveCollision(randomGenerator,neighbourhoodSearchTwo,buffer);
        //Chemical Reaction
        chemicalReactionsSCROA = new ChemicalReactionSCROA(ipsoUpdate,onWallIneffectiveCollission,interMolecularIneffectiveCollission);

        //Population
        molecules = generateRandomPopulation(equation, globalConfig.PopSize, randomGenerator);

        for (IMolecule m: molecules) {
            m.register(currentBestSolution);
        }
    }



    public ArrayList<IMoleculeSCROA> generateRandomPopulation(IEquation equation, int initialPopoSize, IRandomGenerator randomGenerator){

        ArrayList<IMoleculeSCROA> population = new ArrayList<>();

        Boundary boundary = equation.getBoundary();
        //Generate Population in Boundries

        //Check boundries start in boundrie not on boundrie
        double xRange = (boundary.getMaxX() - boundary.getMinX());
        if(xRange-2* globalConfig.distanceToBoundrys > 1){
            xRange-=(2* globalConfig.distanceToBoundrys);
        }
        double yRange = (boundary.getMaxY() - boundary.getMinY());
        if(yRange-2* globalConfig.distanceToBoundrys > 1){
            yRange-=(2* globalConfig.distanceToBoundrys);
        }


        for (int i = 0; i<initialPopoSize;i++){


            double fixInX = boundary.getMinX()+ globalConfig.distanceToBoundrys;
            double fixInY = boundary.getMinY()+ globalConfig.distanceToBoundrys;

            double randomX = randomGenerator.nextDouble()*xRange;
            double randomY = randomGenerator.nextDouble()*yRange;


            Point point = new Point(fixInX+randomX,fixInY+randomY);

            //System.out.println(point.toParseFormat());

            double velocityX = randomGenerator.nextDouble()*2* globalConfig.configurationAlgorithm.initialMaxLengthVelocityPerDim - globalConfig.configurationAlgorithm.initialMaxLengthVelocityPerDim;

            double velocityY = randomGenerator.nextDouble()*2* globalConfig.configurationAlgorithm.initialMaxLengthVelocityPerDim - globalConfig.configurationAlgorithm.initialMaxLengthVelocityPerDim;

            MoleculeSCROA molecule = new MoleculeSCROA(point, globalConfig.configurationAlgorithm.initialKE,calculatorPE,equation,new Point(velocityX,velocityY));
            population.add(molecule);

        }

        return population;
    }



    /*
        Es wird CROA verwendet jedoch Decomop und Synthesis durch PsoUpdate ersetzt

        In PSOUpdate wird anhand der besten Lösung global und anhand der besten Lösung des Moleculs ein neuer Punkt berechnet


     */

    @Override
    public void run() {

        /*
        try {
            scroaThread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
                */

                            int onWallCollHappend = 0;
                            int interMolColHappend = 0;
                            int psoHappendInsteadOfDecomp = 0;
                            int psoHappendInsteadOfSynthesis =0;

                            double energyStart = molecules.stream().map(m -> m.getKE() + m.getPE()).collect(Collectors.summingDouble(d -> d)) + buffer.getBuffer();

                            try {

                                int currentIteration = 0;


                                while (currentIteration < globalConfig.Iterations) {


                                    currentIteration++;
                                    double randomCollission = randomGenerator.nextDouble();


                                    //unimolekular reaction
                                    if (randomCollission >= globalConfig.configurationAlgorithm.moleColl || molecules.size() == 1) {

                                        int randomIndex = randomGenerator.nextInt(0, molecules.size() - 1);

                                        IMoleculeSCROA selectedMolecule = molecules.get(randomIndex);

                                        if (selectedMolecule.getNumberOfHits() >= globalConfig.configurationAlgorithm.numberOfHitsForDecomposition) {
                                            //PSO
                                            psoHappendInsteadOfDecomp++;
                                            chemicalReactionsSCROA.psoUpdate(selectedMolecule);

                                        } else {
                                            //onWall
                                            // System.out.println("--------onWallIneffectivce------------");
                                            onWallCollHappend++;
                                            chemicalReactionsSCROA.onWallIneffectivCollission(selectedMolecule);
                                        }

                                        //intermolekular reaction
                                    } else {

                                        int randomIndex1 = randomGenerator.nextInt(0, molecules.size() - 1);
                                        int randomIndex2 = randomGenerator.nextInt(0, molecules.size() - 1);

                                        while (randomIndex1 == randomIndex2) {
                                            randomIndex2 = randomGenerator.nextInt(0, molecules.size() - 1);
                                        }

                                        IMoleculeSCROA molecule1 = molecules.get(randomIndex1);
                                        IMoleculeSCROA molecule2 = molecules.get(randomIndex2);

                                        if (molecule1.getKE() <= globalConfig.configurationAlgorithm.minimumKe && molecule2.getKE() <= globalConfig.configurationAlgorithm.minimumKe) {
                                            //PSO
                                            psoHappendInsteadOfSynthesis++;
                                            chemicalReactionsSCROA.psoUpdate(molecule1);
                                            chemicalReactionsSCROA.psoUpdate(molecule2);

                                        } else {
                                            //intermolcol
                                            // System.out.println("--------interMolIneffectivce------------");
                                            interMolColHappend++;

                                            chemicalReactionsSCROA.interMolecularIneffectivCollission(molecule1, molecule2);
                                        }

                                    }


                                    if (globalConfig.loggin) {
                                        loggerFileWriter.logBestSolution("croa", currentIteration, currentBestSolution.getBestSolutionPoint(), currentBestSolution.getBestPE());
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
                                if (globalConfig.loggin) {
                                    loggerFileWriter.logInformation("croa Thread ended in an Exception: " + exp);
                                } else {
                                    System.out.println(exp.toString()+" "+exp.getMessage());
                                }
                            }

                            try {
                                barrier.await();
                            }catch (Exception exp){

                            }
                            double energyEnd = molecules.stream().map(m -> m.getKE() + m.getPE()).collect(Collectors.summingDouble(d -> d)) + buffer.getBuffer();
                            System.out.println("SCROA algorithm ("+algorithmCounter+") Best Point"+(new Point3d(currentBestSolution.getBestSolutionPoint().x,currentBestSolution.getBestSolutionPoint().y,currentBestSolution.getBestPE()).toParseFormat()));
                            System.out.println("SCROA algorithm ("+algorithmCounter+") Engieblinazstart : " + energyStart + "Engieblinazende : " + energyEnd + " start - end" + (energyStart - energyEnd));
                            System.out.println("SCROA algorithm ("+algorithmCounter+") interMol: " + interMolColHappend + " onWallin " + onWallCollHappend + " PSO instead of Synthesis " +psoHappendInsteadOfSynthesis +" PSO instead of Decomp "+ psoHappendInsteadOfDecomp);
                            System.out.println("SCROA algorithm ("+algorithmCounter+") Average KE end " + molecules.stream().map(m -> m.getKE()).collect(Collectors.averagingDouble(d -> d)));
                            System.out.println("SCROA algorithm ("+algorithmCounter+") buffer at end " + buffer.getBuffer());

                            /*
                });
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            stop();
        }
                */
    }

    @Override
    public void stop(){

        scroaThread.interrupt();

    }

}

