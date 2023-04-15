package model;
import java.util.Random;

import gui.GameView;

public class CourtDeuxContreDeux extends Court{
    // instance parameters
    private final RacketController playerC, playerD;
    // instance state
    private double racketC; // m
    private double racketD; // m

    //Création d'une deuxieme balle    
    private double ballX2, ballY2; // position de la balle
    private double ballSpeedX2, ballSpeedY2; 

    public CourtDeuxContreDeux(RacketController playerA, RacketController playerB, RacketController playerC, RacketController playerD, double width, double height, int limit) {
    	super(playerA, playerB, width, height,limit);
        this.playerC = playerC;
        this.playerD = playerD;
        reset();
    }

    public CourtDeuxContreDeux(RacketController playerA, RacketController playerB, RacketController playerC, RacketController playerD, double width, double height) {
        super(playerA, playerB, width, height);
        this.playerC = playerC;
        this.playerD = playerD;
        reset();
    }
    
    public RacketController getPlayerC() {return playerC;}    
    public RacketController getPlayerD() {return playerD;}    
    public void setRacketC(double r) {this.racketC = r;}    
    public void setRacketD(double r) {this.racketD = r;}    
    public double getRacketC() {return racketC;}    
    public double getRacketD() {return racketD;}
    public double getBallX2() {return ballX2;}    
    public double getBallY2() {return ballY2;}

    public void update(double deltaT) {
    	super.update(deltaT);
        switch (playerC.getState()) {
        	case GOING_UP:
	            racketC -= getRacketSpeed() * deltaT;
	            if (racketC < 0.0) racketC = 0.0;
	            break;
        	case IDLE:
        		break;
        	case GOING_DOWN:
        		racketC += getRacketSpeed() * deltaT;
        		if (racketC + getRacketSize() > getHeight()) racketC = getHeight() - getRacketSize();
        		break;
        }
        switch (playerD.getState()) {
	        case GOING_UP:
	            racketD -= getRacketSpeed() * deltaT;
	            if (racketD < 0.0) racketD = 0.0;
	            break;
	        case IDLE:
	            break;
	        case GOING_DOWN:
	            racketD += getRacketSpeed() * deltaT;
	            if (racketD + getRacketSize() > getHeight()) racketD = getHeight() - getRacketSize();
	            break;
        }
    }


    /**
     * @return true if a player lost
     */

    public boolean updateBall2(double deltaT) {
        double nextBallX2 = ballX2 + deltaT * ballSpeedX2;
        double nextBallY2 = ballY2 + deltaT * ballSpeedY2;
        if (nextBallY2 < 0 || nextBallY2 > getHeight()) {
            ballSpeedY2 = -ballSpeedY2;
            nextBallY2 = ballY2 + deltaT * ballSpeedY2;
            nextBallX2 = ballX2 + ((ballSpeedX2<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedX2)); 
        }
        if ((nextBallX2 < 0 && nextBallY2 > getRacketA() && nextBallY2 < getRacketA() + getRacketSize())
                || (nextBallX2 < 0 && nextBallY2 > racketC && nextBallY2 < racketC + getRacketSize())
                || (nextBallX2 > getWidth() && nextBallY2 > getRacketB() && nextBallY2 < getRacketB() + getRacketSize())
                || (nextBallX2 > getWidth() && nextBallY2 > racketD && nextBallY2 < racketD + getRacketSize())) {
            ballSpeedX2 = -ballSpeedX2;
            nextBallX2 = ballX2 + deltaT * ballSpeedX2;
        }else if (getScore() != null && nextBallX2 < 0) {
            getScore().addScore1();
            if (getScore().endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(1);
            }
            return true;
        }else if (getScore() != null && nextBallX2 > getWidth()) {
            getScore().addScore2();
            if (getScore().endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(2);
            }
            return true;
        }
        ballX2 = nextBallX2;
        ballY2 = nextBallY2;
        return false;
    }

    public boolean updateBall(double deltaT) {
        if (updateBall2(deltaT)) return true;
        double nextBallX = getBallX() + deltaT * getBallSpeedX();
        double nextBallY = getBallY() + deltaT * getBallSpeedY();
        if (nextBallY < 0 || nextBallY > getHeight()) {
            setBallSpeedY(-getBallSpeedY());
            nextBallY = getBallY() + deltaT * getBallSpeedY();
            nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(getBallSpeedX())); 
        }
        if ((nextBallX < 0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketSize())
        		|| (nextBallX < 0 && nextBallY > racketC && nextBallY < racketC + getRacketSize())
                || (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketSize())
                || (nextBallX > getWidth() && nextBallY > racketD && nextBallY < racketD + getRacketSize())) {
            setBallSpeedX(-getBallSpeedX());
            nextBallX = getBallX() + deltaT * getBallSpeedX();
        }else if (getScore() != null && nextBallX < 0) {
            getScore().addScore1();
            if (getScore().endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(1);
            }
            return true;
        }else if (getScore() != null && nextBallX > getWidth()) {
            getScore().addScore2();
            if (getScore().endGame() == 1){
                GameView.finGame = true ;
                GameView.endGame(2);
            }
            return true;
        }
        setBallX(nextBallX);
        setBallY(nextBallY);
        return false;
    }

    public void reset() {
    	super.reset();
        this.racketC = getHeight() / 4;
        this.racketD = getHeight() / 4;        
        this.ballSpeedX2 = (((int)(Math.random()*10))>5)?-200:200;
        this.ballSpeedY2 = (((int)(Math.random()*10))>5)?200:-200;
        this.ballX2 = getWidth() / 2;
        this.ballY2 = getHeight() / 4;
    }
}
