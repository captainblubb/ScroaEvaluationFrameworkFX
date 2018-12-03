package algorithmns.croa.chemicalReaction.decomposition;

import algorithmns.croa.calculatePE.CalculateFunction;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.equations.IEquation;
import algorithmns.equations.Rosenbrock;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.MoleculeCROA;
import algorithmns.croa.models.Point;
import configuration.configuration.globalConfig;
import algorithmns.randomGenerator.IRandomGenerator;
import algorithmns.randomGenerator.MersenneTwisterFast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DecompositionTest {

    Buffer buffer = new Buffer(0);
    Decomposition decomposition;
    IRandomGenerator randomGenerator = new MersenneTwisterFast(System.nanoTime());;
    IEquation currentEquation = new Rosenbrock();
    ICalculatorPE calculatorPE = new CalculateFunction();
    ArrayList<IMolecule> molecules = new ArrayList<>();



    @Before
    public void setUp() {

        buffer.setBuffer(50);
        decomposition = new Decomposition(randomGenerator, buffer);

    }

    @Test
    public void decomposition_Test(){


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
        double bufferBefore = buffer.getBuffer();

        double ke1 = molecules.get(0).getKE();
        double ke2 = molecules.get(1).getKE();

        List<IMolecule> decomposition = this.decomposition.decomposition(molecules.get(0));
        decomposition.addAll(this.decomposition.decomposition(molecules.get(1)));

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