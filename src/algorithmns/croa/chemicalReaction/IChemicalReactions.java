package algorithmns.croa.chemicalReaction;

import algorithmns.croa.models.IMolecule;

import java.util.List;

public interface IChemicalReactions {
    void onWallIneffectivCollission(IMolecule molecule);
    List<IMolecule> decomposition(IMolecule molecule);
    void interMolecularIneffectiveCollision(IMolecule molecule , IMolecule molecule2);
    IMolecule synthesis(IMolecule molecule, IMolecule molecule2);
}
