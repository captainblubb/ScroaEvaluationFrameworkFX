package algorithmns.croa.chemicalReaction.decomposition;

import algorithmns.croa.models.IMolecule;

import java.util.List;

public interface IDecomposition {

    List<IMolecule> decomposition(IMolecule molecule);
}
