package model;

import gui.App;
import gui.GameView;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Random;

public class FireMode extends Court {

    private final FPlayer playerA;
    private final FPlayer playerB;
    private GameView gameView;

    public FireMode(FPlayer playerA, FPlayer playerB, double width, double height) {
        super(playerA, playerB, width, height, -1);

        this.playerA = playerA;
        this.playerB = playerB;

        reset();
    }

    public FireMode(FPlayer playerA, FPlayer playerB, double width, double height, int limit) {
        super(playerA, playerB, width, height, limit);

        this.playerA = playerA;
        this.playerB = playerB;

        reset();
    }

    public void setKeyEvent(Scene gameScene) {
        //Switch pour les boutons de jeu, in-game.
        gameScene.setOnKeyPressed(ev -> {
            String s = ev.getCode().toString();
            if(s == App.commandes[0]){ //Player A
                playerA.setState(RacketController.State.GOING_UP);
            } else if(s == App.commandes[1]) { //Player A
                playerA.setState(RacketController.State.GOING_DOWN);
            } else if (ev.getCode() == KeyCode.D) {
                    playerA.usePower();
            } else if(s == App.commandes[2]){   //Player B
                playerB.setState(RacketController.State.GOING_UP);
            } else if(s == App.commandes[3]) {  //Player B
                playerB.setState(RacketController.State.GOING_DOWN);
            } else if (ev.getCode() == KeyCode.ENTER) {
            playerB.usePower();
            } else if(s == "ESCAPE"){
                if(!GameView.pause && !GameView.finGame){
                    App.root.getChildren().add(App.imageV);
                    App.root.getChildren().addAll(App.Quitter, App.Reprendre, App.Recommencer);
                    GameView.pause = true;
            }else{
                    if(!GameView.finGame){
                        App.root.getChildren().removeAll(App.imageV, App.Quitter, App.Reprendre, App.Recommencer);
                        GameView.pause = false ; 
                    }
                }
            }  
            ev.consume();  
        });

        //Switch bouton in-game, uniquement pour les boutons de jeu.
        gameScene.setOnKeyReleased(ev -> {
            String s = ev.getCode().toString();

            if (s == App.commandes[0] ) { //Player A
                if (playerA.getState() == RacketController.State.GOING_UP) playerA.setState(RacketController.State.IDLE);
            } else if (s == App.commandes[1]) {
                if (playerA.getState() == RacketController.State.GOING_DOWN) playerA.setState(RacketController.State.IDLE);
            } else if (s == App.commandes[2]) {
                if (playerB.getState() == RacketController.State.GOING_UP) playerB.setState(RacketController.State.IDLE);
            } else if (s == App.commandes[3]) {
                if (playerB.getState() == RacketController.State.GOING_DOWN) playerB.setState(RacketController.State.IDLE);
            }
        });
    }

    public FPlayer getPlayerA() {
        return playerA;
    }

    public FPlayer getPlayerB() {
        return playerB;
    }

    public double getRacketASize() {
        return playerA.getSize();
    }

    public double getRacketBSize() {
        return playerB.getSize();
    }

    @Override
    public double getRacketA() {
        return playerA.getPosition();
    }

    @Override
    public double getRacketB() {
        return playerB.getPosition();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void update(double deltaT) {
        switch (playerA.getState()) {
            case GOING_UP:
                playerA.setPosition(playerA.getPosition() - playerA.getSpeed() * deltaT);
                if (playerA.getPosition() < 0.0) {
                    playerA.setPosition(0.0);
                }
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                playerA.setPosition(playerA.getPosition() + playerA.getSpeed() * deltaT);
                if (playerA.getPosition() + playerA.getSize() > getHeight()) {
                    playerA.setPosition(getHeight() - playerA.getSize());
                }
                break;
        }

        switch (playerB.getState()) {
            case GOING_UP:
                playerB.setPosition(playerB.getPosition() - playerB.getSpeed() * deltaT);
                if (playerB.getPosition() < 0.0) {
                    playerB.setPosition(0.0);
                }
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                playerB.setPosition(playerB.getPosition() + playerB.getSpeed() * deltaT);
                if (playerB.getPosition() + playerB.getSize() > getHeight()) {
                    playerB.setPosition(getHeight() - playerB.getSize());
                }
                break;
        }

        if (updateBall(deltaT)) {
            if (gameView != null) {
                gameView.upgradeRacket();
            }

            reset();
        }
    }

    @Override
    public boolean updateBall(double deltaT) {
        double nextBallX = getBallX() + deltaT * getBallSpeedX();
        double nextBallY = getBallY() + deltaT * getBallSpeedY();

        if (nextBallY < 0 || nextBallY > getHeight()) {
            setBallSpeedY(-getBallSpeedY()); // Rebond sur les bords
            nextBallY = getBallY() + deltaT * getBallSpeedY();
            nextBallX = getBallX() + ((getBallSpeedX() < 0) ? -1 : +1) * deltaT * (new Random()).nextDouble(Math.abs(getBallSpeedX()));
        }

        if ((nextBallX < 0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketASize()) ||
                (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketBSize())) { // collision
            setBallSpeedX(-getBallSpeedX());
            increaseBallSpeed(18);
            nextBallX = getBallX() + deltaT * getBallSpeedX();
            nextBallY = getBallY() + ((getBallSpeedY() < 0) ? -1 : +1) * deltaT * (new Random()).nextDouble(Math.abs(getBallSpeedY()));

            if (nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketASize()) {
                playerA.increasePoint();

                if (playerA.isPower()) {
                    increaseBallSpeed(99);
                    playerA.setPower(false);
                    playerA.setPowering(true);
                }

                if (playerB.isPowering()) {
                    decreaseBallSpeed(99);
                    playerB.setPowering(false);
                }
            } else if (nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketBSize()) {
                playerB.increasePoint();

                if (playerB.isPower()) {
                    increaseBallSpeed(99);
                    playerB.setPower(false);
                    playerB.setPowering(true);
                }

                if (playerA.isPowering()) {
                    decreaseBallSpeed(99);
                    playerA.setPowering(false);
                }
            }
        } else if (nextBallX < 0) {
            getScore().addScore1();
            playerB.increasePoint(5);

            if (getScore().endGame() == 1) {
                GameView.finGame = true ;
                GameView.endGame(1);
            }

            return true;
        } else if (nextBallX > getWidth()) {
            getScore().addScore2();
            playerA.increasePoint(5);

            if (getScore().endGame() == 1) {
                GameView.finGame = true ;
                GameView.endGame(2);
            }

            return true;
        }

        setBallX(nextBallX);
        setBallY(nextBallY);
        return false;
    }

    @Override
    public void refresh() {
        getScore().reset();

        playerA.reset();
        playerB.reset();

        reset();
    }

    @Override
    public void reset() {
        if (playerA != null) {
            playerA.setPosition((getHeight() - playerA.getSize()) / 2);
            playerA.setState(RacketController.State.IDLE);
        }

        if (playerB != null) {
            playerB.setPosition((getHeight() - playerB.getSize()) / 2);
            playerB.setState(RacketController.State.IDLE);
        }

        setBallSpeedX((((int) (Math.random() * 15)) > 5) ? -140 : 140);
        setBallSpeedY((((int) (Math.random() * 15)) > 5) ? 100 : -100);
        setBallX(getWidth() / 2);
        setBallY(getHeight() / 2);
    }

    private void increaseBallSpeed(int n) {
        setBallSpeedX(getBallSpeedX() + Math.signum(getBallSpeedX()) * n);
        setBallSpeedY(getBallSpeedY() + Math.signum(getBallSpeedY()) * n);
    }

    private void decreaseBallSpeed(int n) {
        setBallSpeedX(getBallSpeedX() - Math.signum(getBallSpeedX()) * n);
        setBallSpeedY(getBallSpeedY() - Math.signum(getBallSpeedY()) * n);
    }

}
