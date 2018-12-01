package algorithmns.croa.chemicalReaction.synthesis;

import algorithmns.croa.calculatePE.CalculateFunction;
import algorithmns.croa.calculatePE.ICalculatorPE;
import algorithmns.croa.chemicalReaction.decomposition.Decomposition;
import algorithmns.croa.equations.IEquation;
import algorithmns.croa.equations.Rosenbrock;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.MoleculeCROA;
import algorithmns.croa.models.Point;
import configuration.globalConfig;
import configuration.randomGenerator.IRandomGenerator;
import configuration.randomGenerator.MersenneTwisterFast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SynthesisTest {

    Buffer buffer = new Buffer(0);
    Synthesis synthesis;
    IRandomGenerator randomGenerator = new MersenneTwisterFast(System.nanoTime());;
    IEquation currentEquation = new Rosenbrock();
    ICalculatorPE calculatorPE = new CalculateFunction();
    ArrayList<IMolecule> molecules = new ArrayList<>();



    @BeforeEach
    public void SetUp() {

        buffer.setBuffer(50);
        synthesis = new Synthesis(randomGenerator, buffer);

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

        IMolecule synthesisResult = this.synthesis.synthesis(molecules.get(0), molecules.get(1));

        if(synthesisResult!=null) {

            Assert.assertTrue(pe1+ke1+ke2+pe2 + bufferBefore >= synthesisResult.getPE()+synthesisResult.getKE()+buffer.getBuffer());

            boolean inBoundrysMolecule1 = !(synthesisResult.getCurrentStructure().x >= currentEquation.getBoundrys().getMaxX()
                    || synthesisResult.getCurrentStructure().y >= currentEquation.getBoundrys().getMaxY()
                    || synthesisResult.getCurrentStructure().x <= currentEquation.getBoundrys().getMinX()
                    || synthesisResult.getCurrentStructure().y <= -currentEquation.getBoundrys().getMaxY());


            Assert.assertTrue((inBoundrysMolecule1));

        }
    }
}