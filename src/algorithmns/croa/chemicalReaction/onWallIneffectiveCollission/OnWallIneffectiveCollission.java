package algorithmns.croa.chemicalReaction.onWallIneffectiveCollission;

import algorithmns.croa.models.IMolecule;
import algorithmns.LocalSearcher.neighbourhoodSearch.neighbourhoodSearchSingle.INeighbourhoodSearchSingle;
import configuration.configuration.globalConfig;
import algorithmns.croa.models.Buffer;
import algorithmns.croa.models.Point;
import algorithmns.LocalSearcher.randomGenerator.IRandomGenerator;

public class OnWallIneffectiveCollission implements IOnWallIneffectiveCollission {


    IRandomGenerator randomGenerator;
    INeighbourhoodSearchSingle neighbourhoodSearchSingle;
    Buffer buffer;

    public OnWallIneffectiveCollission(IRandomGenerator randomGenerator, INeighbourhoodSearchSingle neighbourhoodSearchSingle, Buffer buffer){
        this.randomGenerator = randomGenerator;
        this.neighbourhoodSearchSingle = neighbourhoodSearchSingle;
        this.buffer = buffer;
    }


    @Override
    public void onWallIneffectiveCollission(IMolecule molecule) {


      //  double startEnergie = molecule.getKE()+molecule.getPE();

        Point newPoint =  neighbourhoodSearchSingle.findNeighbour(molecule.getCurrentStructure());

        int counter = 0;
        while (newPoint == null && counter < 20){
            newPoint =  neighbourhoodSearchSingle.findNeighbour(molecule.getCurrentStructure());
            counter++;
        }

        if (newPoint!=null) {


            if(molecule.getPE()+molecule.getKE() > molecule.getEquation().calculateValue(newPoint)) {
                //der neue Punkt entspreicht den vorraussetzungen :
                //neues PE ist besser als aktuelles PE+KE
                //Generate a -> Losrate of KE to buffer
                double a = randomGenerator.nextDouble() * (1 - globalConfig.configurationAlgorithm.keMinLossRate) + globalConfig.configurationAlgorithm.keMinLossRate;

                //KE = (PE - PE' + KE ) * a
                double newKE = (molecule.getPE() - (molecule.getCalculatorPE().calculatePE(newPoint.x, newPoint.y, molecule.getEquation())) + molecule.getKE()) * (1-a);

                //buffer = buffer + (PE-PE'+KE)*(1-a)
                buffer.setBuffer(buffer.getBuffer() + (molecule.getPE() - (molecule.getCalculatorPE().calculatePE(newPoint.x, newPoint.y, molecule.getEquation())) + molecule.getKE()) * (a));

                molecule.setSolution(newPoint);
                molecule.setKE(newKE);

               // double endEnergie = molecule.getKE()+molecule.getPE();

              //  System.out.println(startEnergie-endEnergie);



            }
        }

        //double endEnergie = molecule.getKE()+molecule.getPE()+buffer.getBuffer();

    }
}
