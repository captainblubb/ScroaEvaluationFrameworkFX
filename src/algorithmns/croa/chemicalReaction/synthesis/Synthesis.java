package algorithmns.croa.chemicalReaction.synthesis;

import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.MoleculeCROA;
import algorithmns.croa.models.Point;
import algorithmns.randomGenerator.IRandomGenerator;

public class Synthesis implements ISynthesis {

    IRandomGenerator randomGenerator;
    Buffer buffer;

    public Synthesis(IRandomGenerator randomGenerator, Buffer buffer){
        this.randomGenerator = randomGenerator;
        this.buffer = buffer;
    }

    @Override
    public IMolecule synthesis(IMolecule molecule1, IMolecule molecule2) {

        double spreadX = randomGenerator.nextDouble();
        double newX = molecule1.getCurrentStructure().x *spreadX + molecule2.getCurrentStructure().x*(1-spreadX);


        double spreadY = randomGenerator.nextDouble();
        double newY = molecule1.getCurrentStructure().y *spreadY + molecule2.getCurrentStructure().y*(1-spreadY);

        Point newPoint = new Point(newX,newY);

        if(molecule1.getEquation().getBoundrys().inBoundry(newPoint.x,newPoint.y)){

           double newPE = molecule1.getCalculatorPE().calculatePE(newPoint.x,newPoint.y,molecule1.getEquation());

            // (PE1 +PE2 +KE1 +KE2 ) >= newPE
           if( (molecule1.getPE()+molecule2.getPE()+molecule1.getKE()+molecule2.getKE()) >= newPE ){

                double newKE = molecule1.getPE()+molecule2.getPE()+molecule1.getKE()+molecule2.getKE() - newPE;

                return new MoleculeCROA(newPoint,newKE,molecule1.getCalculatorPE(),molecule1.getEquation());
           }


        }

        return null;

    }

}
