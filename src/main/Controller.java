package main;

import algorithmns.croa.CROA;
import algorithmns.croa.equations.IEquation;
import algorithmns.croa.equations.Rastrigin;
import algorithmns.scroa.SCROA;
import main.updateObject.IUpdateable;
import main.updateObject.UpdateObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements IUpdateable {


    @FXML
    private ComboBox<String> modeComboBox;

    @FXML ComboBox<String> directionComboBox;

    @FXML
    private Label stepCounter;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button heatMapButton;

    @FXML
    private Button pauseButton;


    @FXML
    private void startSimulation() {


        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        IEquation equation = new Rastrigin();

        Thread worker = new Thread(new CROA(equation,this,1,cyclicBarrier));
        Thread worker2 = new Thread(new SCROA(equation,this,2,cyclicBarrier));

        worker.start();
        worker2.start();

        System.out.println("Finished all threads");

    }


    @FXML
    public void stopSimulation() {}


    @FXML
    private void pauseSimulation() {}


    @Override
    public void update(UpdateObject updateObject) {

        System.out.println("Update Received Best point of algorithmn "+updateObject.getAlgorithmCounter()
                +" : Iteration: "+updateObject.getIteration()+" "+updateObject.getBestPoint().toParseFormat()
                +" Population count : "+updateObject.getPoints().size());
    }

}
