package gui;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;


/*********************************************************************************************************************** */


//Menu pour les différents modes de jeu. On implémentera plus tard les modes de jeu interne aux 1vs1, 1vsBot ou 2vs2.

public class ModeDeJeu {
    public Pane root;
    public Scene gameScene;

    ModeDeJeu(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public void start (Stage primaryStage) {
        
        Image image = new Image("file:src/Pictures/pong1.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(350);
        imageView.setLayoutY(10);

        //Bouton 1vs1
        Button oneVSone = new Button("1 VS 1") ;
        oneVSone.setLayoutX(70);
        oneVSone.setLayoutY(570);
        oneVSone.setEffect(new ImageInput(new Image("file:src/Pictures/1vs1.png")));
        oneVSone.setSkin(new MyButtonSkin(oneVSone));

        //Bouton 1vsBot
        Button oneVSBot = new Button("1 VS Bot");
        oneVSBot.setLayoutX(351);
        oneVSBot.setLayoutY(570);
        oneVSBot.setEffect(new ImageInput(new Image("file:src/Pictures/1vsbot.png")));
        oneVSBot.setSkin(new MyButtonSkin(oneVSBot));

        //Bouton 2vs2
        Button twoVStwo = new Button("2 VS 2");
        twoVStwo.setLayoutX(660);
        twoVStwo.setLayoutY(570);
        twoVStwo.setEffect(new ImageInput(new Image("file:src/Pictures/2vs2.png")));
        twoVStwo.setSkin(new MyButtonSkin(twoVStwo));

        //Bouton OneVSOneVSoneVSone
        Button OneVSOneVSOneVSOne = new Button("1vs1vs1vs1");
        OneVSOneVSOneVSOne.setLayoutX(950);
        OneVSOneVSOneVSOne.setLayoutY(570);
        OneVSOneVSOneVSOne.setEffect(new ImageInput(new Image("file:src/Pictures/1vs1vs1vs1.png")));
        OneVSOneVSOneVSOne.setSkin(new MyButtonSkin(OneVSOneVSOneVSOne));

        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));

        Image img = new Image("file:src/Pictures/fond.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);

        root.getChildren().add(imageView);
        root.getChildren().addAll(oneVSone, oneVSBot, twoVStwo, Retour, OneVSOneVSOneVSOne);

        Retour.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            a.start(primaryStage);
        });

        oneVSone.setOnAction(ev1 -> {    
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeuInt a = new ModeDeJeuInt(root1, gameScene,-1);
            a.start(primaryStage);
        });

        oneVSBot.setOnAction(ev1 -> {   
            ArrayList<String> niveau = new ArrayList<>() ; 
            niveau.add("Facile") ; 
            niveau.add("Moyen") ;
            niveau.add("Difficile") ; 

            ChoiceDialog<String> difficulteBot = new ChoiceDialog<String>("Moyen", niveau) ; 
            difficulteBot.initOwner(primaryStage);
            difficulteBot.setTitle("Niveau Du Bot");
            difficulteBot.setHeaderText("Veuillez choisir la difficulté que le Bot contre qui vous allez jouer va avoir !");
            difficulteBot.setContentText("Difficulté : ");

            Optional<String> choix = difficulteBot.showAndWait() ; 

            choix.ifPresent(diff -> {
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                int res = -1 ; 
                switch (diff) {
                    case "Facile" :
                        res = 1 ; 
                        break ; 
                    case "Moyen" :
                        res = 2 ;
                        break ; 
                    default :
                        res = 3 ;
                        break ;     
                }
                ModeDeJeuInt a = new ModeDeJeuInt(root1, gameScene , res);
                a.start(primaryStage);
            });
        });
        
        twoVStwo.setOnAction(ev1 -> {    
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            App a = new App(root1, gameScene);
            a.start2C2(primaryStage);
        });

        OneVSOneVSOneVSOne.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            App a = new App(root1, gameScene);
            a.start4J(primaryStage);
        });

    }

}
