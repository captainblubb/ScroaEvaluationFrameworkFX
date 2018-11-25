package algorithmns.scroa.chemicalReactions;

import algorithmns.croa.chemicalReaction.decomposition.IDecomposition;
import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.IInterMolecularIneffectiveCollission;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.IOnWallIneffectiveCollission;
import algorithmns.croa.chemicalReaction.synthesis.ISynthesis;
import algorithmns.croa.models.IMolecule;
import algorithmns.scroa.models.IMoleculeSCROA;
import algorithmns.scroa.pso.psoUpdate.IPSOUpdate;

import java.util.List;

public class ChemicalReactionSCROA implements IChemicalReactionSCROA {

    IPSOUpdate psoUpdate;
    IOnWallIneffectiveCollission onWallIneffectiveCollission;
    IInterMolecularIneffectiveCollission interMolecularIneffectiveCollission;


    public ChemicalReactionSCROA(IPSOUpdate ipsoUpdate, IOnWallIneffectiveCollission onWallIneffectiveCollission , IInterMolecularIneffectiveCollission interMolecularIneffectiveCollission){
        this.psoUpdate = ipsoUpdate;
        this.onWallIneffectiveCollission = onWallIneffectiveCollission;
        this.interMolecularIneffectiveCollission = interMolecularIneffectiveCollission;
    }


    public void onWallIneffectivCollission(IMolecule molecule) {
        onWallIneffectiveCollission.onWallIneffectiveCollission(molecule);
    }

    @Override
    public void interMolecularIneffectivCollission(IMolecule molecule1, IMolecule molecule2) {
        interMolecularIneffectiveCollission.interMolecularIneffectiveCollission(molecule1,molecule2);
    }

    @Override
    public void psoUpdate(IMoleculeSCROA moleculeSCROA) {
        psoUpdate.psoUpdateOnMol(moleculeSCROA);
    }
}
