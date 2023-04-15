package model;
import java.util.Random;

import gui.GameView;
import model.Court;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    private final double width, height; // size of the application
    private double racketSpeed = 300.0; // m/s
    private double racketSize = 100.0; // m
    private double ballRadius = 10.0; // ball radius/ size
    // instance state
    private double racketA; // playerOnePos.Y
    private double racketB; // playerOnePos.Y
    private double ballX, ballY; // position de la balle
    private double ballSpeedX , ballSpeedY ;
    private Score score;

    public Score getScore(){
        return this.score;
    }

    public Court(RacketController playerA, RacketController playerB, double width, double height, int limit) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        this.score = new Score(limit);
        try {
            ((Bot)playerB).setCourt(this);
        } catch (Exception e) {}
        reset();
    }

    public Court(RacketController playerA, RacketController playerB, double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        this.score = new Score(-1);
        try {
            ((Bot)playerB).setCourt(this);
        } catch (Exception e) {}
        reset();
    }

    public void mettreScoreNull() {
        score = null;
    }

    public void setBallX (double ballX) {
        this.ballX = ballX ;
    }

    public void setBallY (double ballY) {
        this.ballY = ballY ;
    }

    public double getBallSpeedX () {
        return ballSpeedX ;
    }

    public double getBallSpeedY () {
        return ballSpeedY ;
    }

    public void setBallSpeedX (double ballSpeedX) {
        this.ballSpeedX = ballSpeedX ;
    }

    public void setBallSpeedY (double ballSpeedY) {
        this.ballSpeedY = ballSpeedY ;
    }

    public double getRacketSpeed () {
        return this.racketSpeed ;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRacketSize() {
        return racketSize;
    }

    public double getRacketA() {
        return racketA;
    }

    public double getRacketB() {
        return racketB;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public void update(double deltaT) {

        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketSpeed * deltaT;
                if (racketA < 0.0) racketA = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height) racketA = height - racketSize;
                break;
        }
        switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketSpeed * deltaT;
                if (racketB < 0.0) racketB = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketB += racketSpeed * deltaT;
                if (racketB + racketSize > height) racketB = height - racketSize;
                break;
        }
        if (updateBall(deltaT)) reset();
    }


    /**
     * @return true if a player lost
     */

    public boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) {
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY ;
            nextBallX = ballX + ((ballSpeedX<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedX));
        }

        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize)  || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX ;
            nextBallY = ballY +  ((ballSpeedY<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedY));
        }else if (score != null && nextBallX < 0) {
            score.addScore1();
            if (score.endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(1);
            }
            return true;
        }else if (score != null && nextBallX > width) {
            score.addScore2();
            if (score.endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(2);
            }
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }

    public double getBallRadius() {
        return ballRadius;
    }

    public void refresh () {
        score.reset();
        reset();
    }

    public void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.ballSpeedX = (((int)(Math.random()*10))>5)?-200:200;
        this.ballSpeedY = (((int)(Math.random()*10))>5)?200:-200;
        this.ballX = width / 2;
        this.ballY = height / 2;
    }
}
