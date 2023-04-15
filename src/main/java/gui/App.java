package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;



//*************************************TEST*********** */

//***************************************************** */

//App, fichier du jeu in-game
//Implémentation du menu pause : Fait
//Implémentation du menu de fin de jeu : fait

public class App {

    public static Pane root;
    public Scene gameScene;
    public int limite;

    App(Pane root, Scene a, int limite){
        App.root = root;
        gameScene = a;
        this.limite=limite;
    }

    App(Pane root, Scene a){
        App.root = root;
        gameScene = a;
    }

    class Player implements RacketController {
        State state = State.IDLE;
        @Override
        public State getState() {
            return state;
        }
    }

    public static String[] commandes = {"A", "Q", "P", "M", "E", "R", "J", "K"};

    public static void mettreNull(Court court, GameView gameView) {
        court.mettreScoreNull();
        court = null;
        gameView = null;
    }

    public static void setCommandes(String[] s){
        commandes[0] = s[0];
        commandes[1] = s[1];
        commandes[2] = s[2];
        commandes[3] = s[3];
        commandes[4] = s[4];
        commandes[5] = s[5];
        commandes[6] = s[6];
        commandes[7] = s[7];
    }

    public static Button Quitter = new Button("Quitter");
    public static Button Reprendre = new Button("Reprendre");
    public static Button Recommencer = new Button("Recommencer");
    public static ImageView PauseImage;
    static Image image2 = new Image(new File("src/Pictures/pause1.gif").toURI().toString());
    public static ImageView imageV = new ImageView(image2);

    public void setCommands1Player (Player playerA) {
        gameScene.setOnKeyPressed(ev -> {
            String s = ev.getCode().toString();
            if(s == commandes[0]){
                playerA.state = RacketController.State.GOING_UP;
            } else if(s == commandes[1]){
                playerA.state = RacketController.State.GOING_DOWN;
            } else if(s == "ESCAPE"){
                if(!GameView.pause && !GameView.finGame){
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    GameView.pause = true;
               }else{
                    if(!GameView.finGame){
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        GameView.pause = false ; 
                    }
                }    
            }
        });

        //Switch bouton in-game, uniquement pour les boutons de jeu. 
        gameScene.setOnKeyReleased(ev -> {
            String s = ev.getCode().toString();

            if(s == commandes[0]){
                if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
            } else if(s == commandes[1]){
                if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
            } 
        
        });
    }

    public void setCommands2Player (Player playerA , Player playerB) {
        //Switch pour les boutons de jeu, in-game.
        gameScene.setOnKeyPressed(ev -> {
            String s = ev.getCode().toString();

            if(s == commandes[0]){
                playerA.state = RacketController.State.GOING_UP;
            } else if(s == commandes[1]){
                playerA.state = RacketController.State.GOING_DOWN;
            } else if(s == commandes[2]){
                playerB.state = RacketController.State.GOING_UP;
            } else if(s == commandes[3]){
                playerB.state = RacketController.State.GOING_DOWN;
            } else if(s == "ESCAPE"){
                if(!GameView.pause && !GameView.finGame){
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    GameView.pause = true;
               }else{
                    if(!GameView.finGame){
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        GameView.pause = false ; 
                    }
                }    
            }
        });


        //Switch bouton in-game, uniquement pour les boutons de jeu. 
        gameScene.setOnKeyReleased(ev -> {
            String s = ev.getCode().toString();

            if(s == commandes[0]){
                if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
            } else if(s == commandes[1]){
                if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
            } else if(s == commandes[2]){
                if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
            } else if(s == commandes[3]){
                if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
            }
        });
    }

    public void setCommands4Player (Player playerA , Player playerB , Player playerC , Player playerD) {
        gameScene.setOnKeyPressed(ev -> {
            String s = ev.getCode().toString();
            if (s == commandes[0]) {
                playerA.state = RacketController.State.GOING_UP;
            } else if (s == commandes[1]) {
                playerA.state = RacketController.State.GOING_DOWN;
            } else if (s == commandes[2]) {
                playerB.state = RacketController.State.GOING_UP;
            } else if (s == commandes[3]) {
                playerB.state = RacketController.State.GOING_DOWN;
            } else if (s == commandes[4]) {
                playerC.state = RacketController.State.GOING_UP;
            } else if (s == commandes[5]) {
                playerC.state = RacketController.State.GOING_DOWN;
            } else if (s == commandes[6]) {
                playerD.state = RacketController.State.GOING_UP;
            } else if (s == commandes[7]) {
                playerD.state = RacketController.State.GOING_DOWN;
            } else if (s == "ESCAPE") {
                if (!GameView.pause && !GameView.finGame) {
                    root.getChildren().add(imageV);
                    root.getChildren().addAll(Quitter, Reprendre, Recommencer);
                    GameView.pause = true;
                } else {
                    if (!GameView.finGame) {
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        GameView.pause = false;
                    }
                }
            }
        });
        gameScene.setOnKeyReleased(ev -> {
            String s = ev.getCode().toString();

            //Switch bouton in-game, uniquement pour les boutons de jeu.
            if (s == commandes[0]) {
                if (playerA.state == RacketController.State.GOING_UP)
                    playerA.state = RacketController.State.IDLE;
            } else if (s == commandes[1]) {
                if (playerA.state == RacketController.State.GOING_DOWN)
                    playerA.state = RacketController.State.IDLE;
            } else if (s == commandes[2]) {
                if (playerB.state == RacketController.State.GOING_UP)
                    playerB.state = RacketController.State.IDLE;
            } else if (s == commandes[3]) {
                if (playerB.state == RacketController.State.GOING_DOWN)
                    playerB.state = RacketController.State.IDLE;
            } else if (s == commandes[4]) {
                if (playerC.state == RacketController.State.GOING_UP)
                    playerC.state = RacketController.State.IDLE;
            } else if (s == commandes[5]) {
                if (playerC.state == RacketController.State.GOING_DOWN)
                    playerC.state = RacketController.State.IDLE;
            } else if (s == commandes[6]) {
                if (playerD.state == RacketController.State.GOING_UP)
                    playerD.state = RacketController.State.IDLE;
            } else if (s == commandes[7]) {
                if (playerD.state == RacketController.State.GOING_DOWN)
                    playerD.state = RacketController.State.IDLE;
            }
        });
    }

    public void setActionButtons (Court court , GameView gameView , Stage primaryStage) {
        //Action du bouton Quitter
        Quitter.setOnAction(ev1 -> {
            mettreNull(court, gameView);
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            if (court instanceof TimeMode) {
                TimeMode.closeTimer();
                ((TimeMode)court).resetNbManche();
            }
            a.start(primaryStage);
        });

        //Action du bouton Reprendre
        Reprendre.setOnAction(ev1 ->{
            root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
            GameView.pause = false ; 
        });

        //Action du bouton Recommencer
        Recommencer.setOnAction(ev1 ->{
            Quitter.setLayoutX(320);
            Recommencer.setLayoutX(695);
            Recommencer.setLayoutY(350);
            Quitter.setLayoutY(350);
            root.getChildren().remove(imageV);
            if (GameView.finGame){
                root.getChildren().remove(root.getChildren().size()-3) ; 
                root.getChildren().remove(root.getChildren().size()-3) ;  
            }
            if (court instanceof Court4J) {
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                App a = new App(root1, gameScene);
                a.start4J(primaryStage);
            }
            root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
            court.refresh(); 
            GameView.pause = false ; 
            GameView.finGame = false;
            if (court instanceof TimeMode) ((TimeMode)court).commencerTimer();
        });			
    }

    public void setActionRestartButton (Court court , GameView gameView ,FPlayer playerA , FPlayer playerB) {
        Recommencer.setOnAction(ev1 -> {
            Quitter.setLayoutX(320);
            Recommencer.setLayoutX(695);
            Recommencer.setLayoutY(350);
            Quitter.setLayoutY(350);
            root.getChildren().remove(imageV);
            if (GameView.finGame) {
                root.getChildren().remove(root.getChildren().size() - 3);
                root.getChildren().remove(root.getChildren().size() - 3);
            }
            playerA.reset();
            playerB.reset();
            //Reset powerAmount
            playerA.getPowerAmountText().setText("0");
            playerB.getPowerAmountText().setText("0");

            //reset racketA image in GameView
            gameView.getRacketA().setFill(Color.valueOf("#ff5252"));
            gameView.getRacketB().setFill(Color.valueOf("#189ad3"));

            root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
            court.refresh();
            GameView.pause = false;
            GameView.finGame = false;
        });
    }

    public void setStyleButtons () {
        imageV.setX(290);
        imageV.setY(200);
        
        Quitter.setLayoutX(320);
        Quitter.setLayoutY(350);
        Quitter.setMinSize(80, 80);
        Quitter.setEffect(new ImageInput(new Image("file:src/Pictures/retourM.png")));
        Quitter.setSkin(new MyButtonSkin(Quitter));
      

        Reprendre.setLayoutX(485);
        Reprendre.setLayoutY(350);
        Reprendre.setMinSize(80, 80);
        Reprendre.setEffect(new ImageInput(new Image("file:src/Pictures/play.png")));
        Reprendre.setSkin(new MyButtonSkin(Reprendre));
       

        Recommencer.setLayoutX(695);
        Recommencer.setLayoutY(350);
        Recommencer.setMinSize(80, 80);
        Recommencer.setEffect(new ImageInput(new Image("file:src/Pictures/recommencer.png")));
        Recommencer.setSkin(new MyButtonSkin(Recommencer));
    }

    public void start(Stage primaryStage)  {
        var playerA = new Player();
        var playerB = new Player() ;
        Image img = new Image("file:src/Pictures/fond.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        var court = new Court(playerA, playerB, 1000, 600, limite);
        var gameView = new GameView(court, root, 1);
        
        setStyleButtons();
        setCommands2Player(playerA, playerB);
        setActionButtons(court, gameView, primaryStage);
        gameView.animate();
    }

    //Start pour le 2 contre 2
    public void start4J(Stage primaryStage) {
        var playerA = new Player();
        var playerB = new Player();
        var playerC = new Player();
        var playerD = new Player();
        Image img = new Image("file:src/Pictures/fond.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        ArrayList<Integer> limiteS = new ArrayList<Integer>();
        limiteS.add(2);
        limiteS.add(4);
        limiteS.add(6);
        limiteS.add(8);

        ChoiceDialog<Integer> limiteScore = new ChoiceDialog<Integer>(2, limiteS);
        limiteScore.initOwner(primaryStage);
        limiteScore.setTitle("Nombre de Vie");
        limiteScore.setHeaderText("Veuillez choisir le nombre de vie");
        limiteScore.setContentText("Nombre : ");

        Optional<Integer> limitScore = limiteScore.showAndWait();
        limitScore.ifPresent(limite -> {
            this.limite = limite;
        });

        ArrayList<String> pop = new ArrayList<String>();
        pop.add("COMPRIS");
        pop.add("PAS COMPRIS");
        ChoiceDialog<String> pop2 = new ChoiceDialog<String>("COMPRIS", pop);
        pop2.initOwner(primaryStage);
        pop2.setTitle("Touches du Joueur 3 & 4:");
        pop2.setHeaderText("TOUCHE DU J3 : E et R  |  TOUCHE DU J4: J et K");
        pop2.setResizable(false);
        Optional<String> popOk = pop2.showAndWait();
        if (popOk.isEmpty()) {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        }
        if (popOk.isPresent()) {
            if (popOk.get().equals("COMPRIS")) {
                var court = new Court4J(playerA, playerB, playerC, playerD, 1000, 600, limite);
                var gameView = new GameView4J(court, root, 1);
                court.setGameView(gameView);
                setStyleButtons();
                setCommands4Player(playerA, playerB, playerC, playerD);
                setActionButtons(court, gameView, primaryStage);
                gameView.animate();
            }
        }
    }

    //Start pour le 2 contre 2
    public void start2C2(Stage primaryStage) {
        var playerA = new Player();
        var playerB = new Player();
        var playerC = new Player();
        var playerD = new Player();
        Image img = new Image("file:src/Pictures/fond.png");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        ArrayList<Integer> limiteS = new ArrayList<Integer>();
        limiteS.add(2);
        limiteS.add(4);
        limiteS.add(6);
        limiteS.add(8);

        ChoiceDialog<Integer> limiteScore = new ChoiceDialog<Integer>(2, limiteS);
        limiteScore.initOwner(primaryStage);
        limiteScore.setTitle("Limite de Score");
        limiteScore.setHeaderText("Veuillez choisir un nombre de points maximum");
        limiteScore.setContentText("Nombre : ");

        Optional<Integer> limitScore = limiteScore.showAndWait();
        limitScore.ifPresent(limite -> {
            this.limite = limite;
        });

        ArrayList<String> pop = new ArrayList<String>();
        pop.add("COMPRIS");
        pop.add("PAS COMPRIS");
        ChoiceDialog<String> pop2 = new ChoiceDialog<String>("COMPRIS", pop);
        pop2.initOwner(primaryStage);
        pop2.setTitle("Touches du Joueur 3 & 4:");
        pop2.setHeaderText("TOUCHE DU J3 : E et R  |  TOUCHE DU J4: J et K");
        pop2.setResizable(false);
        Optional<String> popOk = pop2.showAndWait();
        if (popOk.isEmpty()) {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        }
        if (popOk.isPresent()) {
            if (popOk.get().equals("COMPRIS")) {
                var court = new CourtDeuxContreDeux(playerA, playerB, playerC, playerD, 1000, 600, limite);
                var gameView = new GameViewDeuxContreDeux(court, root, 1);
                setStyleButtons();
                setCommands4Player(playerA, playerB, playerC, playerD);
                setActionButtons(court, gameView, primaryStage);
                gameView.animate();
            }
        }
    }

   public void Start (Stage primaryStage , String gameMode , int diff) {
        var playerA = new Player() ; 
        var playerB = (diff == -1)?(new Player()):(new Bot(3)) ; 
        Court court = null ; 
        GameView gameView = null; 
        switch (gameMode) {
            case "Speed" :
                if (diff != -1) {
                    court = new CourtSpeed(playerA, playerB, 1000, 600, limite) {
                        @Override
                        public void update(double deltaT) {
                            ((Bot)playerB).play(deltaT*getBallSpeedY()+getBallY());
                            super.update(deltaT);
                        };
                    } ; 
                }else {
                    court = new CourtSpeed(playerA, playerB, 1000, 600, limite);
                }
                break ; 
            case "Obstacles" :
                if (diff != -1) {
                    court = new CourtObstacles(playerA, playerB, 1000, 600, this.limite){
                        @Override
                        public void update(double deltaT) {
                            ((Bot)playerB).play(deltaT*getBallSpeedY()+getBallY());
                            super.update(deltaT);
                        };
                    } ; 
                }else{
                    court = new CourtObstacles(playerA, playerB, 1000, 600, this.limite) ;
                }
                break ; 
            case "Score" :
                if (diff != -1) {
                    court = new Court(playerA, playerB, 1000, 600, limite) {
                        @Override
                        public void update(double deltaT) {
                            ((Bot)playerB).play(deltaT*getBallSpeedY()+getBallY());
                            super.update(deltaT);
                        };
                    }; 
                }else{
                    court = new Court(playerA, playerB, 1000, 600, limite) ;
                }   
                break ;  
        }
        gameView = new GameView(court, root, 1);
        setStyleButtons();
        if (diff != -1) {
            setCommands1Player(playerA);
        }else{
            setCommands2Player(playerA, ((Player)playerB));
        }
        setActionButtons(court, gameView, primaryStage);
        gameView.animate();
   }

    public void startTimer(Stage primaryStage, int nbManche, int t , int diff){
        var playerA = new Player();
        var playerB = (diff != -1)?new Bot(diff):new Player() ; 
        Court court = null ; 
        if (diff != -1) {
            court = new TimeMode(playerA, playerB, 1000, 600, nbManche, t){
                @Override
                public void update(double deltaT) {
                    ((Bot)playerB).play(deltaT*getBallSpeedY()+getBallY());
                    super.update(deltaT);
                }
            };
        }else{
            court = new TimeMode(playerA, playerB, 1000, 600, nbManche, t);
        }    
        var gameView = new GameView(court, root, 1);
    
        setStyleButtons();
        if (diff != -1) {
            setCommands1Player(playerA);
        }else{
            setCommands2Player(playerA, ((Player)playerB));
        }
        setActionButtons(court, gameView, primaryStage);
        gameView.animate();
    }

    public void startFire(Stage primaryStage) {
        ArrayList<String> firePopA = new ArrayList<String>();
        firePopA.add("COMPRIS");
        firePopA.add("PAS COMPRIS");
        ChoiceDialog<String> firePop = new ChoiceDialog<String>("COMPRIS", firePopA);
        firePop.initOwner(primaryStage);
        firePop.setTitle("Touche du Fire Mode");
        firePop.setHeaderText("TOUCHE DU J1 : A , Q et D (D = Acheter + Activer Skill)  |  TOUCHE DU J2 : P , M et Entrer (Enter = Acheter + Activer Skill )");
        firePop.setContentText("Vous allez devoir jouer pour obtenir des points, \n Quand la balle touche votre raquette c'est 1 points \n Quand vous marquez un goal, c'est 5 points \n");
        firePop.setResizable(false);
        Optional<String> fireok = firePop.showAndWait();
        if(fireok.isEmpty()){
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            ModeDeJeu a = new ModeDeJeu(root1, gameScene);
            a.start(primaryStage);
        }
        if (fireok.isPresent()){
            if(fireok.get().equals("COMPRIS")){
                var playerA = new FPlayer();
                var playerB = new FPlayer();
                Image img = new Image("file:src/Pictures/fondFire.png");
                BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                Background bGround = new Background(bImg);
                root.setBackground(bGround);
                var court = new FireMode(playerA, playerB, 1000, 600 , limite);
                var gameView = new GameView(court, root, 1);
                setStyleButtons();
                gameView.animate();
                //Action du bouton Quitter
                Quitter.setOnAction(ev1 -> {
                        Pane root1 = new Pane();
                        gameScene.setRoot(root1);
                        Menu a = new Menu(root1, gameScene);
                        a.start(primaryStage);
                    });

                    //Action du bouton Reprendre
                Reprendre.setOnAction(ev1 -> {
                        root.getChildren().removeAll(imageV, Quitter, Reprendre, Recommencer);
                        GameView.pause = false;
                    });

                    //Action du bouton Recommencer
                Recommencer.setOnAction(ev1 -> {
                        Quitter.setLayoutX(320);
                        Recommencer.setLayoutX(695);
                        Recommencer.setLayoutY(350);
                        Quitter.setLayoutY(350);
                        root.getChildren().remove(imageV);
                        if (GameView.finGame) {
                            root.getChildren().remove(root.getChildren().size() - 3);
                            root.getChildren().remove(root.getChildren().size() - 3);
                        }
                        playerA.reset();
                        playerB.reset();
                        //Reset powerAmount
                        playerA.getPowerAmountText().setText("0");
                        playerB.getPowerAmountText().setText("0");

                        //reset racketA image in GameView
                        gameView.getRacketA().setFill(Color.valueOf("#ff5252"));
                        gameView.getRacketB().setFill(Color.valueOf("#189ad3"));

                        root.getChildren().removeAll(Quitter, Reprendre, Recommencer);
                        court.refresh();
                        GameView.pause = false;
                        GameView.finGame = false;
                    });

                court.setGameView(gameView);
                court.setKeyEvent(gameScene);
            }
            if(fireok.get().equals("PAS COMPRIS")){
                Pane root1 = new Pane();
                gameScene.setRoot(root1);
                ModeDeJeu a = new ModeDeJeu(root1, gameScene);
                a.start(primaryStage);
            }
        }
    }
}