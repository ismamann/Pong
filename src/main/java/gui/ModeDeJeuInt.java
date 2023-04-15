package gui;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import java.util.Optional;

/*********************************************************************************************************************** */


//Menu pour les différents modes de jeu interne. Il reste timer mode, fire mode et un autre mode à implémenter

public class ModeDeJeuInt {

    public Pane root;
    public Scene gameScene;
    public int difficulte ; 

    ModeDeJeuInt(Pane root, Scene a , int diff){
        this.root = root;
        gameScene = a;
        this.difficulte = diff ; 
    }

    public void start(Stage primaryStage) {
     
        //Logo du pong
        Image image = new Image("file:src/Pictures/pong1.png");
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(50);
        imageView.setLayoutY(190);

        // //Bouton lifemode
        // Button lifemode = new Button("Life Mode") ;
        // lifemode.setLayoutX(820);
        // lifemode.setLayoutY(200);
        // lifemode.setEffect(new ImageInput(new Image("file:src/Pictures/lifemode.png")));
        // lifemode.setSkin(new MyButtonSkin(lifemode));

        //Bouton timermode
        Button timermode = new Button("Timer Mode");
        timermode.setLayoutX(820);
        timermode.setLayoutY(400);
        timermode.setEffect(new ImageInput(new Image("file:src/Pictures/timermode.png")));
        timermode.setSkin(new MyButtonSkin(timermode));

        //Bouton firemode
        Button firemode = new Button("Fire Mode");
        firemode.setLayoutX(660);
        firemode.setLayoutY(570);
        firemode.setEffect(new ImageInput(new Image("file:src/Pictures/firemode.png")));
        firemode.setSkin(new MyButtonSkin(firemode));

        //Bouton SpeedMode
        Button speedmode = new Button("Speed Mode");
        speedmode.setLayoutX(520);
        speedmode.setLayoutY(400);
        speedmode.setEffect(new ImageInput(new Image("file:src/Pictures/speedmode.png")));
        speedmode.setSkin(new MyButtonSkin(speedmode));

        //Bouton ScoreMode
        Button scoreMode = new Button("Score Mode");
        scoreMode.setLayoutX(520);
        scoreMode.setLayoutY(200);
        scoreMode.setEffect(new ImageInput(new Image("file:src/Pictures/scoremode.png")));
        scoreMode.setSkin(new MyButtonSkin(scoreMode));

        //Bouton Obstacle Mode
        Button obstaclemode = new Button("obstacle mode");
        obstaclemode.setLayoutX(820);
        obstaclemode.setLayoutY(200);
        obstaclemode.setEffect(new ImageInput(new Image("file:src/Pictures/obstaclemode.png")));
        obstaclemode.setSkin(new MyButtonSkin(obstaclemode));

        //Bouton retour
        Button Retour= new Button("Retour") ;
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));

        //Background
        Image img = new Image("file:src/Pictures/ModeDeJeu.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);


        root.getChildren().add(imageView);
        root.getChildren().addAll(timermode, speedmode, scoreMode, obstaclemode, Retour);
        if(difficulte == -1){
            root.getChildren().add(firemode);
        }
        

        Retour.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        });

        scoreMode.setOnAction(ev1 -> {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.initOwner(primaryStage);
            dialog.setTitle("Choix Du Score");
            dialog.setHeaderText("Vous Pouvez choisir le nombre de points à atteindre !");
            dialog.setContentText("Veuillez entrer un score valide : ");
            dialog.setResizable(false);

            int limit = 0 ;
            Optional<String> result = dialog.showAndWait() ;
            if (result.isPresent()){
                    try {
                        limit = Integer.valueOf(result.get().strip()) ;
                    } catch (NumberFormatException e) {
                       dialog.setContentText("Veuillez entrer un nombre !");
                       limit = 0 ;
                    }
                if (limit >0) {
                    Pane root1 = new Pane();
                    gameScene.setRoot(root1);
                    App a = new App(root1, gameScene, limit); //Appel de la classe App classique qui permet de lancer le mode de score (définir la limite du score au début)
                    a.Start(primaryStage , "Score" , difficulte);
                }
            }   
        });

        speedmode.setOnAction(ev1->{

            TextInputDialog dialog = new TextInputDialog("1");
            dialog.initOwner(primaryStage);
            dialog.setTitle("Choix Du Score");
            dialog.setHeaderText("Vous Pouvez choisir le nombre de points à atteindre !");
            dialog.setContentText("Veuillez entrer un score valide : ");
            dialog.setResizable(false);

            int limit = 0 ;
            Optional<String> result = dialog.showAndWait() ;
            if (result.isPresent()){
                    try {
                        limit = Integer.valueOf(result.get().strip()) ;
                    } catch (NumberFormatException e) {
                       dialog.setContentText("Veuillez entrer un nombre !");
                       limit = 0 ;
                    }
                if (limit >0) {
                    Pane root1 = new Pane();
                    gameScene.setRoot(root1);
                    App a = new App(root1, gameScene, limit); //Appel de la classe App classique qui permet de lancer le mode de score (définir la limite du score au début)
                    a.Start(primaryStage, "Speed", difficulte);
                }
            }
        });   
        


        firemode.setOnAction(ev1-> {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.initOwner(primaryStage);
            dialog.setTitle("Choix Du Score");
            dialog.setHeaderText("Vous Pouvez choisir le nombre de points à atteindre !");
            dialog.setContentText("Veuillez entrer un score valide : ");
            dialog.setResizable(false);

            int limit = 0 ;
            Optional<String> result = dialog.showAndWait() ;
            if (result.isPresent()){
                    try {
                        limit = Integer.valueOf(result.get().strip()) ;
                    } catch (NumberFormatException e) {
                       dialog.setContentText("Veuillez entrer un nombre !");
                       limit = 0 ;
                    }
                if (limit >0) {
                        Pane root1 = new Pane();
                        gameScene.setRoot(root1);
                        App a = new App(root1, gameScene, limit); //Appel de la classe App classique qui permet de lancer le mode de score (définir la limite du score au début)
                        a.startFire(primaryStage);
                        //dialog to choose
                }
            }
        });


        obstaclemode.setOnAction(ev1->{
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.initOwner(primaryStage);
            dialog.setTitle("Choix Du Score");
            dialog.setHeaderText("Vous Pouvez choisir le nombre de points à atteindre !");
            dialog.setContentText("Veuillez entrer un score valide : \n" +
            "Tapez 'infini' si vous voulez pas de limite !");
            dialog.setResizable(false);

            int limit = 0 ;
            Optional<String> result = dialog.showAndWait() ;
            if (result.isPresent()){
                if (result.get().equals("infini")){
                    limit = -1 ;
                }else{
                    try {
                        limit = Integer.valueOf(result.get().strip()) ;
                    } catch (NumberFormatException e) {
                       dialog.setContentText("Veuillez entrer un nombre !");
                       limit = 0 ;
                    }
                }
                if (limit == -1 || limit >0) {
                        Pane root1 = new Pane() ;
                        gameScene.setRoot(root1);
                        App app = new App(root1, gameScene, limit) ;
                        app.Start(primaryStage, "Obstacles", difficulte);
                    
                }
            }

            //Utilser obstaclemode de Samy
        });


        timermode.setOnAction(ev1->{

            TextInputDialog dialogManche = new TextInputDialog();
            dialogManche.initOwner(primaryStage);
            dialogManche.setTitle("Limite de la partie");
            dialogManche.setHeaderText("Veuillez choisir un nombre de manches");
            dialogManche.setContentText("Nombre : ");

            Optional<String> ecouteManche = dialogManche.showAndWait();

            ecouteManche.ifPresent(limit -> {
                boolean b = false;
                while(!b) {
                    try {
                       int c = Integer.valueOf(limit);
                       if (c > 0) b = true;
                       else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Erreur de saisie");
                        alert.setHeaderText(null);
                        alert.setContentText("Vous devez saisir un entier strictement supérieur à 0.");
                        alert.showAndWait();
                        return;
                       }
                     } catch (NumberFormatException e) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Erreur de saisie");
                        alert.setHeaderText(null);
                        alert.setContentText("Vous devez saisir un chiffre.");
                        alert.showAndWait();
                        return;
                     }
                }


                TextInputDialog dialogDuree = new TextInputDialog();
                dialogDuree.initOwner(primaryStage);
                dialogDuree.setTitle("Mode Timer");
                dialogDuree.setHeaderText("Veuillez choisir la durée de chaque manche");
                dialogDuree.setContentText("durée : ");

                Optional<String> ecouteDuree = dialogDuree.showAndWait();
                ecouteDuree.ifPresent(time -> {
                    boolean d = false;
                    while(!d) {
                        try {
                           int k = Integer.valueOf(time);
                           if (k > 0) d = true;
                           else {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Erreur de saisie");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous devez saisir un entier strictement supérieur à 0.");
                            alert.showAndWait();
                            return;
                           }
                         } catch (NumberFormatException e) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Erreur de saisie");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous devez saisir un chiffre.");
                            alert.showAndWait();
                            return;
                         }
                        }
                    Pane root1 = new Pane();
                    gameScene.setRoot(root1);
                    App a = new App(root1, gameScene);
                    a.startTimer(primaryStage, Integer.valueOf(limit), Integer.valueOf(time) , difficulte);
                });

            });

        });



    }
}
