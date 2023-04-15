package gui;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;

import javafx.scene.image.ImageView;
/* ------------------------------------------------------------------------------------------------------*/

//Menu principale du Jeu 


public class Menu {

    public Pane root;
    public Scene gameScene;

    Menu(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }
    
    public void start (Stage primaryStage) {

        //Logo du millieu
        Image image = new Image("file:src/Pictures/pong1.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(350);
        imageView.setLayoutY(10);

        //Bouton Play
        Button play = new Button("play") ;
        play.setLayoutX(487);
        play.setLayoutY(554);
        play.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
        play.setSkin(new MyButtonSkin(play));

        //Bouton Option
        Button option = new Button("option");
        option.setLayoutX(421);
        option.setLayoutY(600);
        option.setEffect(new ImageInput(new Image("file:src/Pictures/option.png")));
        option.setSkin(new MyButtonSkin(option));

        //Bouton quitter
        Button quitter = new Button("quitter");
        quitter.setLayoutX(704);
        quitter.setLayoutY(600);
        quitter.setEffect(new ImageInput(new Image("file:src/Pictures/exit.png")));
        quitter.setSkin(new MyButtonSkin(quitter));

        //Bouton pour le easter egg intégré à la page d'accueil
        Button Easter = new Button("Easter");
        Easter.setLayoutX(10);
        Easter.setLayoutY(700);
        Easter.setMinSize(100, 100);
        Easter.setOpacity(0);

        //Mise en place du background animé

        root.setStyle("-fx-background-image: url('file:src/Pictures/fond1.gif');");
        root.getStyleClass().addAll("root");

       //Setting du Stage
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/Pictures/pong1.png")) ;
        primaryStage.setTitle("Pong");

        //Ajout des boutons sur le stage
        
        root.getChildren().addAll(imageView, play, option, quitter, Easter) ;
        primaryStage.setScene(gameScene);
        primaryStage.show(); 

        //Action du bouton option
        option.setOnAction(ev2 ->{
            root.getChildren().removeAll(play, option, quitter);
            
            Button Theme = new Button("Theme");
            Theme.setLayoutX(615);
            Theme.setLayoutY(590);
            Theme.setEffect(new ImageInput(new Image("file:src/Pictures/Boutontheme.png")));
            Theme.setSkin(new MyButtonSkin(Theme));
            Theme.setOnAction(ev1->{
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                MenuTheme a = new MenuTheme(root1, gameScene);
                a.start(primaryStage);

            });


            //Creation des boutons stats et commande pour les menus
            Button Commande= new Button("play") ;
            Commande.setLayoutX(438);
            Commande.setLayoutY(570);
            Commande.setEffect(new ImageInput(new Image("file:src/Pictures/commande2.png")));
            Commande.setSkin(new MyButtonSkin(Commande));
            Commande.setOnAction(ev1 -> {
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                Commande a = new Commande(root1, gameScene);
                a.start(primaryStage);
            });


            Button Retour = new Button("quitter");
            Retour.setLayoutX(1100);
            Retour.setLayoutY(25);
            Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
            Retour.setSkin(new MyButtonSkin(Retour));

            root.getChildren().addAll(Commande, Retour, Theme) ;

            Retour.setOnAction(ev3 ->{
                root.getChildren().removeAll(Commande, Retour, Theme);
                root.getChildren().addAll(play, option, quitter);
            });

            //ici, on ne fait que supprimer les anciens boutons pour en placer de nouveau. Pas de changement de root. 

        });



        //Action du bouton Play 
        play.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        });
        
        //Action du bouton Quitter
        quitter.setOnAction(ev3 ->{
            System.exit(0);
        });
       
        //Implémentation de l'easter-egg
        Easter.setOnAction(ev8 ->{
            Button Easter2 = new Button("Easter");
            Easter2.setLayoutX(1100);
            Easter2.setLayoutY(700);
            Easter2.setMinSize(100, 100);
            Easter2.setOpacity(0);
            root.getChildren().add(Easter2);
            Easter2.setOnAction(ev7 ->{
                Button Easter3 = new Button("Easter");
                Easter3.setLayoutX(1100);
                Easter3.setLayoutY(10);
                Easter3.setMinSize(100, 100);
                Easter3.setOpacity(0);
                root.getChildren().add(Easter3);
                Easter3.setOnAction(ev5 ->{
                    Image image2 = new Image("file:src/Pictures/Easter1.jpg");
                    ImageView imageView2 = new ImageView(image2);
                    imageView2.setLayoutX(350);
                    imageView2.setLayoutY(10);
                    ImageView imageView3 = new ImageView(image2);
                    ImageView imageView4 = new ImageView(image2);
                    imageView3.setLayoutX(150);
                    imageView3.setLayoutY(10);
                    imageView4.setLayoutX(650);
                    imageView4.setLayoutY(10);
                    root.getChildren().addAll(imageView2, imageView3, imageView4);
                });
            });
        });


    }
}


