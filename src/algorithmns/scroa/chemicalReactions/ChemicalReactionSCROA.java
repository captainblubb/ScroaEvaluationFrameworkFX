package algorithmns.scroa.chemicalReactions;

import algorithmns.croa.chemicalReaction.interMolecularIneffectiveCollission.IInterMolecularIneffectiveCollision;
import algorithmns.croa.chemicalReaction.onWallIneffectiveCollission.IOnWallIneffectiveCollission;
import algorithmns.croa.models.IMolecule;
import algorithmns.scroa.models.IMoleculeSCROA;
import algorithmns.scroa.pso.psoUpdate.IPSOUpdate;

public class ChemicalReactionSCROA implements IChemicalReactionSCROA {

    IPSOUpdate psoUpdate;
    IOnWallIneffectiveCollission onWallIneffectiveCollision;
    IInterMolecularIneffectiveCollision interMolecularIneffectiveCollission;


    public ChemicalReactionSCROA(IPSOUpdate ipsoUpdate, IOnWallIneffectiveCollission onWallIneffectiveCollision, IInterMolecularIneffectiveCollision interMolecularIneffectiveCollission){
        this.psoUpdate = ipsoUpdate;
        this.onWallIneffectiveCollision = onWallIneffectiveCollision;
        this.interMolecularIneffectiveCollission = interMolecularIneffectiveCollission;
    }


    public void onWallIneffectivCollission(IMolecule molecule) {
        onWallIneffectiveCollision.onWallIneffectiveCollission(molecule);
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
