package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void init(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("SCROA Evaluation Framwork");
        primaryStage.setScene(new Scene(root, 700, 500));
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
