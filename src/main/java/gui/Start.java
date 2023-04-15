package gui;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.event.EventHandler;
import model.TimeMode;

/*********************************************************************************************************************** */

//Classe de départ pour lancer le menu

public class Start extends Application {
  
    public void start (Stage primaryStage) {
        Pane root = new Pane() ;
        Scene gameScene = new Scene(root) ;
        Menu a = new Menu(root, gameScene);
        a.start(primaryStage);
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
            public void handle(WindowEvent window) {
                TimeMode.closeTimer();
            }
        });
    }

}
