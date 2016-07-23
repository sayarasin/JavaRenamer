package Renamer;

import Renamer.Core.FileNamer;
import Renamer.Core.Rule;
import Renamer.Core.Renamer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        primaryStage.setTitle("Renamer");
        primaryStage.setScene(new Scene(root, 520, 160));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
