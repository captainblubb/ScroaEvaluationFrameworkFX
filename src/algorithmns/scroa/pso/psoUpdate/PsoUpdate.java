package algorithmns.scroa.pso.psoUpdate;

import algorithmns.bestSolutionObserver.IBestSolutionListener;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.Point;
import algorithmns.scroa.models.IMoleculeSCROA;
import configuration.configuration.globalConfig;
import algorithmns.LocalSearcher.randomGenerator.IRandomGenerator;

public class PsoUpdate implements IPSOUpdate{


    Buffer buffer;
    IBestSolutionListener bestSolutionListener;
    IRandomGenerator randomGenerator;

    public PsoUpdate(IBestSolutionListener bestSolutionListener, Buffer buffer, IRandomGenerator randomGenerator){
        this.randomGenerator = randomGenerator;
        this.buffer=buffer;
        this.bestSolutionListener=bestSolutionListener;
    }

    //1. molecule SCROA geschwindigkeitsvektor wird geupdated
    //2. moleculeSCROA neue position wird berechnet

    public void psoUpdateOnMol(IMoleculeSCROA moleculeSCROA) {

        updateVelocityMol(moleculeSCROA);

        moveMoleculeByVelocity(moleculeSCROA, globalConfig.configurationAlgorithm.trysOfPSOUpdate);

    }


    private void updateVelocityMol(IMoleculeSCROA moleculeSCROA) {


        // NewVelocity = w*CurrentVelocity + c1*r1*(molBest -molCurrent) + c2*r2*(bestGlobal-currentStructure)

        double r1 = randomGenerator.nextDouble();
        double r2 = randomGenerator.nextDouble();

                            // w*CurrentVelocity
        double newVeolcityX = globalConfig.configurationAlgorithm.w*moleculeSCROA.getVelocity().x
                            //+ c1*r1*(molBest -molCurrent)
                            +globalConfig.configurationAlgorithm.c1*r1*(moleculeSCROA.getMinStructure().x - moleculeSCROA.getCurrentStructure().x)
                            //+ c2*r2*(bestGlobal-currentStructure)
                            +globalConfig.configurationAlgorithm.c2*r2*(bestSolutionListener.getBestSolutionPoint().x- moleculeSCROA.getCurrentStructure().x);


                              // w*CurrentVelocity
        double newVelocityY = globalConfig.configurationAlgorithm.w*moleculeSCROA.getVelocity().y
                               //+ c1*r1*(molBest -molCurrent)
                               +globalConfig.configurationAlgorithm.c1*r1*(moleculeSCROA.getMinStructure().y - moleculeSCROA.getCurrentStructure().y)
                               //+ c2*r2*(bestGlobal-currentStructure)
                               +globalConfig.configurationAlgorithm.c2*r2*(bestSolutionListener.getBestSolutionPoint().y- moleculeSCROA.getCurrentStructure().y);


            //TODO: Check Length of Velocity -> MaxVelocity????
            //TODO: Wenn zulang -> normalisieren * maxVelocity

            moleculeSCROA.setVelocity(new Point(newVeolcityX,newVelocityY));

    }


    private void moveMoleculeByVelocity(IMoleculeSCROA moleculeSCROA, int trys) {


        if(trys > 0) {
            //zufällig stark in die RIchtung -> neue Pos generieren

            double stepPercentage = randomGenerator.nextDouble() * (1 - globalConfig.configurationAlgorithm.minVelocityStep) + globalConfig.configurationAlgorithm.minVelocityStep;


            // neue position -> molCurrentPos + mol.Velocity*stepPercentage
            double newPointX = moleculeSCROA.getCurrentStructure().x + moleculeSCROA.getVelocity().x * stepPercentage;
            double newPointY = moleculeSCROA.getCurrentStructure().y + moleculeSCROA.getVelocity().y * stepPercentage;

            Point newPoint = new Point(newPointX, newPointY);

            boolean inBoundrys = moleculeSCROA.getEquation().getBoundary().inBoundary(newPointX, newPointY);

            //randomBuffer =[0...1] * [0....1]
            double randomBuffer = randomGenerator.nextDouble() * randomGenerator.nextDouble();

            //wenn (neuePosition.PE <= mol.CurrentPE+mol.CurrentKE)

            if (inBoundrys && (moleculeSCROA.getCalculatorPE().calculatePE(newPointX, newPointY, moleculeSCROA.getEquation()) <= moleculeSCROA.getPE() + moleculeSCROA.getKE())) {

                double newKE =  (moleculeSCROA.getPE() + moleculeSCROA.getKE()) - moleculeSCROA.getCalculatorPE().calculatePE(newPointX, newPointY, moleculeSCROA.getEquation());
                moleculeSCROA.setSolution(newPoint);
                moleculeSCROA.setKE(newKE);
                moleculeSCROA.resetCurrentHits();

            } else if (inBoundrys && (moleculeSCROA.getCalculatorPE().calculatePE(newPointX, newPointY, moleculeSCROA.getEquation()) <= randomBuffer * buffer.getBuffer() + moleculeSCROA.getPE() + moleculeSCROA.getKE())) {
                //else if neuePosition <= random*buffer + mol.CurrentPE+mol.CurrentKE

                double newKE =  (randomBuffer * buffer.getBuffer() + moleculeSCROA.getPE() + moleculeSCROA.getKE()) - moleculeSCROA.getCalculatorPE().calculatePE(newPointX, newPointY, moleculeSCROA.getEquation());
                buffer.setBuffer(buffer.getBuffer()*(1-randomBuffer));
                moleculeSCROA.setSolution(newPoint);
                moleculeSCROA.setKE(newKE);
                moleculeSCROA.resetCurrentHits();

            } else {

                //else ... try again with reduced try
                moveMoleculeByVelocity(moleculeSCROA, --trys);

            }
        }


    }


}
