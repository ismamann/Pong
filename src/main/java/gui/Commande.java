package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


import javafx.scene.control.TextField;

/* ----------------------------------------------------------------------------------------------------------------------------------*/

public class Commande extends Application{

    public Pane root;
    public Scene gameScene;

    Commande(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public static String[] com = App.commandes;

    public void start (Stage primaryStage) {
        
        primaryStage.setScene(gameScene);
        primaryStage.show();


        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));

        Retour.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            a.start(primaryStage);
        });
        
        
        TextField bUp1 = new TextField(App.commandes[0]);
        bUp1.setEditable(false);
        bUp1.setOnKeyPressed(ev -> {
            com[0] = ev.getCode().toString();
            bUp1.setText(com[0]);
        });
        bUp1.setLayoutX(50);
        bUp1.setLayoutY(600);
        
        TextField bDown1 = new TextField(App.commandes[1]);
        bDown1.setEditable(false);
        bDown1.setOnKeyPressed(ev -> {
            com[1] = ev.getCode().toString();
            bDown1.setText(com[1]);
        });
        bDown1.setLayoutX(250);
        bDown1.setLayoutY(600);
        
  
        TextField bUp2 = new TextField(App.commandes[2]);
        bUp2.setEditable(false);
        bUp2.setOnKeyPressed(ev -> {
            com[2] = ev.getCode().toString();
            bUp2.setText(com[2]);
        });
        bUp2.setLayoutX(780);
        bUp2.setLayoutY(600);
        
    
        TextField bDown2 = new TextField(App.commandes[3]);
        bDown2.setEditable(false);
        bDown2.setOnKeyPressed(ev -> {
            com[3] = ev.getCode().toString();
            bDown2.setText(com[3]);
        });
        bDown2.setLayoutX(980);
        bDown2.setLayoutY(600);
        

        Button save = new Button("Sauvegarder");
        save.setOnAction(evl -> {
            App.setCommandes(com);
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            a.start(primaryStage);
        });
        save.setLayoutX(550);
        save.setLayoutY(570);
        save.setEffect(new ImageInput(new Image("file:src/Pictures/enregistrer.png")));
        save.setSkin(new MyButtonSkin(save));

        //Set Background
        root.setStyle("-fx-background-image: url('file:src/Pictures/CommandeFond.gif');");
        root.getStyleClass().addAll("root");

        root.getChildren().addAll(Retour, save, bUp1, bDown1, bUp2, bDown2);

    }
}