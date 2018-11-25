package algorithmns.scroa.models;

import algorithmns.croa.models.IMolecule;
import algorithmns.croa.models.Point;
import gui.updateObject.Point3d;

public interface IMoleculeSCROA extends IMolecule {

    Point getVelocity();
    void setVelocity(Point velocity);
    void resetCurrentHits();
}
