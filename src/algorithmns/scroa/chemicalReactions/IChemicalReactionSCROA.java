package algorithmns.scroa.chemicalReactions;

import algorithmns.croa.models.IMolecule;
import algorithmns.scroa.models.IMoleculeSCROA;

public interface IChemicalReactionSCROA  {

    void onWallIneffectivCollission(IMolecule molecule);

    void interMolecularIneffectivCollission(IMolecule molecule, IMolecule molecule2);

    void psoUpdate(IMoleculeSCROA moleculeSCROA);
}
