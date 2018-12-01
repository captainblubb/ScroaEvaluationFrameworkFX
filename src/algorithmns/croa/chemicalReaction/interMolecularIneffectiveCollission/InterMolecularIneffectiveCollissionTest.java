package algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission;

import algorithmns.croa.calculatePE.CalculateFunction;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.OnWallIneffectiveCollission;
import algorithmns.croa.chemicalReaction.synthesis.Synthesis;
import algorithmns.croa.equations.IEquation;
import algorithmns.croa.equations.Rosenbrock;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.MoleculeCROA;
import algorithmns.croa.models.Point;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchSingle.INeighbourhoodSearchSingle;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchTwo.INeighbourhoodSearchTwo;
import algorithmns.croa.neighbourhoodSearch.neighbourhoodSearchTwo.RandombasedSearch;
import configuration.globalConfig;
import configuration.randomGenerator.IRandomGenerator;
import configuration.randomGenerator.MersenneTwisterFast;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InterMolecularIneffectiveCollissionTest {

    Buffer buffer = new Buffer(0);
    InterMolecularIneffectiveCollission interMolecularIneffectiveCollission;
    IRandomGenerator randomGenerator = new MersenneTwisterFast(System.nanoTime());;
    IEquation currentEquation = new Rosenbrock();
    ICalculatorPE calculatorPE = new CalculateFunction();
    ArrayList<IMolecule> molecules = new ArrayList<>();



    @BeforeEach
    public void SetUp() {

        buffer.setBuffer(50);
        INeighbourhoodSearchTwo neighbourhoodSearchTwo = new RandombasedSearch(randomGenerator,currentEquation);

        interMolecularIneffectiveCollission = new InterMolecularIneffectiveCollission(randomGenerator,neighbourhoodSearchTwo,buffer);

    }

    @Test
    public void synthesis_Test(){


        for (int i = 0; i<2; i++){
            //Generate koords
            double randomX =(randomGenerator.nextDouble() * (currentEquation.getBoundrys().getMaxX()- currentEquation.getBoundrys().getMinX())+ currentEquation.getBoundrys().getMinX());
            double randomY =(randomGenerator.nextDouble() * (currentEquation.getBoundrys().getMaxY()- currentEquation.getBoundrys().getMinY())+ currentEquation.getBoundrys().getMinY());
            IMolecule molecule = new MoleculeCROA(new Point(randomX,randomY), currentEquation.calculateValue(new Point(randomX,randomY)),calculatorPE, currentEquation);
            molecule.setKE(globalConfig.minimumKe-1);
            molecules.add(molecule);
        }

        double pe1 = molecules.get(0).getPE();
        double pe2 = molecules.get(1).getPE();
        double ke1 = molecules.get(0).getKE();
        double ke2 = molecules.get(1).getKE();

        double bufferBefore = buffer.getBuffer();

        this.interMolecularIneffectiveCollission.interMolecularIneffectiveCollission(molecules.get(0), molecules.get(1));

        Assert.assertTrue(ke1+ke2+pe1+pe2+bufferBefore >= molecules.get(0).getKE()+molecules.get(1).getKE()+molecules.get(0).getPE() + molecules.get(1).getPE());

        assertEquals(pe1+pe2 +buffer.getBuffer(), molecules.get(0).getPE() + molecules.get(1).getPE());

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

}