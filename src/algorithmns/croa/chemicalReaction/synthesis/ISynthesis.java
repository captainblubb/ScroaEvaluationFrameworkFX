package algorithmns.croa.chemicalReaction.synthesis;

import algorithmns.croa.models.IMolecule;

public interface ISynthesis {
    IMolecule synthesis(IMolecule molecule1, IMolecule molecule2);
}
