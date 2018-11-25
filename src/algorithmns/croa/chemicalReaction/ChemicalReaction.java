package algorithmns.croa.chemicalReaction;

import algorithmns.croa.chemicalReaction.decomposition.IDecomposition;
import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.IInterMolecularIneffectiveCollission;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.IOnWallIneffectiveCollission;
import algorithmns.croa.chemicalReaction.synthesis.ISynthesis;
import algorithmns.croa.models.IMolecule;

import java.util.List;

public class ChemicalReaction implements IChemicalReactions {


    IDecomposition decomposition;
    IOnWallIneffectiveCollission onWallIneffectiveCollission;
    ISynthesis synthesis;
    IInterMolecularIneffectiveCollission interMolecularIneffectiveCollission;


    public ChemicalReaction(IDecomposition decomposition, IOnWallIneffectiveCollission onWallIneffectiveCollission, ISynthesis synthesis , IInterMolecularIneffectiveCollission interMolecularIneffectiveCollission){
        this.decomposition = decomposition;
        this.onWallIneffectiveCollission = onWallIneffectiveCollission;
        this.synthesis = synthesis;
        this.interMolecularIneffectiveCollission = interMolecularIneffectiveCollission;
    }


    public void onWallIneffectivCollission(IMolecule molecule) {
        onWallIneffectiveCollission.onWallIneffectiveCollission(molecule);
    }

    public List<IMolecule> decomposition(IMolecule molecule) {

      return decomposition.decomposition(molecule);

    }

    public void interMolecularIneffectivCollission(IMolecule molecule1, IMolecule molecule2) {

        interMolecularIneffectiveCollission.interMolecularIneffectiveCollission(molecule1,molecule2);
    }


    public IMolecule synthesis(IMolecule molecule1, IMolecule molecule2) {

        return synthesis.synthesis(molecule1,molecule2);
    }
}
