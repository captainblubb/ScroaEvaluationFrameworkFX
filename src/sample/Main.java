package sample;

import algorithmns.croa.CROA;
import algorithmns.croa.equations.IEquation;
import algorithmns.croa.equations.Rosenbrock;
import algorithmns.scroa.SCROA;
import gui.GUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    @Override
    public void init(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Platform.setImplicitExit( false );


    }

    @Override
    public void stop(){

    }

    public static void main(String[] args) {
        launch(args);
    }


}
