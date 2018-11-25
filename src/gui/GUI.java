package gui;

import gui.updateObject.IUpdateable;
import gui.updateObject.UpdateObject;

public class GUI implements IUpdateable {





    @Override
    public void update(UpdateObject updateObject) {

        System.out.println("Update Received Best point of algorithmn "+updateObject.getAlgorithmCounter()
        +" : Iteration: "+updateObject.getIteration()+" "+updateObject.getBestPoint().toParseFormat()
                +" Population count : "+updateObject.getPoints().size());
    }
}
