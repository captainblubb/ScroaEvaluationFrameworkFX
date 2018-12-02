package algorithmns.croa.chemicalReaction.onWallIneffectiveCollission;

import algorithmns.croa.calculatePE.CalculateFunction;
import algorithmns.croa.calculatePE.ICalculatorPE;
import configuration.equations.IEquation;
import configuration.equations.Rosenbrock;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.MoleculeCROA;
import algorithmns.croa.models.Point;
import configuration.neighbourhoodSearch.neighbourhoodSearchSingle.INeighbourhoodSearchSingle;
import configuration.neighbourhoodSearch.neighbourhoodSearchSingle.MoveAlongGrade;
import configuration.configuration.globalConfig;
import configuration.randomGenerator.IRandomGenerator;
import configuration.randomGenerator.MersenneTwisterFast;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class OnWallIneffectiveCollissionTest {


    Buffer buffer = new Buffer(0);
    OnWallIneffectiveCollission onWallIneffectiveCollission ;
    IRandomGenerator randomGenerator = new MersenneTwisterFast(System.nanoTime());;
    IEquation currentEquation = new Rosenbrock();
    ICalculatorPE calculatorPE = new CalculateFunction();
    ArrayList<IMolecule> molecules = new ArrayList<>();



    @BeforeEach
    public void SetUp() {

        buffer.setBuffer(50);
        INeighbourhoodSearchSingle neighbourhoodSearchSingle = new MoveAlongGrade(randomGenerator,currentEquation);
        onWallIneffectiveCollission = new OnWallIneffectiveCollission(randomGenerator,neighbourhoodSearchSingle,buffer);

    }

    @Test
    public void synthesis_Test(){


        for (int i = 0; i<2; i++){
            //Generate koords
            double randomX =(randomGenerator.nextDouble() * (currentEquation.getBoundrys().getMaxX()- currentEquation.getBoundrys().getMinX())+ currentEquation.getBoundrys().getMinX());
            double randomY =(randomGenerator.nextDouble() * (currentEquation.getBoundrys().getMaxY()- currentEquation.getBoundrys().getMinY())+ currentEquation.getBoundrys().getMinY());
            IMolecule molecule = new MoleculeCROA(new Point(randomX,randomY), currentEquation.calculateValue(new Point(randomX,randomY)),calculatorPE, currentEquation);
            molecule.setKE(globalConfig.configurationAlgorithm.minimumKe-1);
            molecules.add(molecule);
        }

        double pe1 = molecules.get(0).getPE();
        double pe2 = molecules.get(1).getPE();
        double ke1 = molecules.get(0).getKE();
        double ke2 = molecules.get(1).getKE();

        double bufferBefore = buffer.getBuffer();

        this.onWallIneffectiveCollission.onWallIneffectiveCollission(molecules.get(0));

        this.onWallIneffectiveCollission.onWallIneffectiveCollission(molecules.get(1));

        //Same Energylevel
        assertEquals(ke1 + ke2 + pe1 + pe2 + bufferBefore , molecules.get(0).getKE() + molecules.get(1).getKE() + molecules.get(0).getPE() + molecules.get(1).getPE());

        boolean inBoundrysMolecule1 = !(molecules.get(0).getCurrentStructure().x >= currentEquation.getBoundrys().getMaxX()
                || molecules.get(0).getCurrentStructure().y >= currentEquation.getBoundrys().getMaxY()
                || molecules.get(0).getCurrentStructure().x <= currentEquation.getBoundrys().getMinX()
                || molecules.get(0).getCurrentStructure().y <= -currentEquation.getBoundrys().getMaxY());


        boolean inBoundrysMolecule2 = !(molecules.get(1).getCurrentStructure().x >= currentEquation.getBoundrys().getMaxX()
                || molecules.get(1).getCurrentStructure().y >= currentEquation.getBoundrys().getMaxY()
                || molecules.get(1).getCurrentStructure().x <= currentEquation.getBoundrys().getMinX()
                || molecules.get(1).getCurrentStructure().y <= -currentEquation.getBoundrys().getMaxY());

        Assert.assertTrue((inBoundrysMolecule1 && inBoundrysMolecule2));
    }
}