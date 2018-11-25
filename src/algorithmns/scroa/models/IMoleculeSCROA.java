package algorithmns.scroa.models;

import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.Point;

public interface IMoleculeSCROA extends IMolecule {

    Point getVelocity();
    void setVelocity(Point velocity);
    void resetCurrentHits();
}
