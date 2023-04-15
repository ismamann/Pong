package model;
import java.util.Random;

import gui.GameView;
import gui.GameView4J;

public class Court4J extends Court{
    // instance parameters
    private final RacketController playerC, playerD;
    private GameView4J gm;
    // instance state
    private double racketC; // m
    private double racketD; // m

    //Création d'une deuxieme balle    
    private double ballX2, ballY2; // position de la balle
    private double ballSpeedX2, ballSpeedY2;
    private boolean joueuru, joueurd, joueurt, joueurq;

    public Court4J(RacketController playerA, RacketController playerB, RacketController playerC, RacketController playerD, double width, double height, int limit) {
    	super(playerA, playerB, width, height,limit);
        getScore().load4J();
        this.playerC = playerC;
        this.playerD = playerD;
        joueuru = true;
        joueurd = true;
        joueurt = true;
        joueurq = true;
        reset();
    }

    public Court4J(RacketController playerA, RacketController playerB, RacketController playerC, RacketController playerD, double width, double height) {
        super(playerA, playerB, width, height);
        getScore().load4J();
        this.playerC = playerC;
        this.playerD = playerD;
        joueuru = true;
        joueurd = true;
        joueurt = true;
        joueurq = true;
        reset();
    }
    
    public void setGameView(GameView4J game) {gm = game;}
    public RacketController getPlayerC() {return playerC;}    
    public RacketController getPlayerD() {return playerD;} 
    public double getRacketC() {return racketC;}    
    public double getRacketD() {return racketD;}
    public double getBallX2() {return ballX2;}    
    public double getBallY2() {return ballY2;}

    public void update(double deltaT) {
    	super.update(deltaT);
        switch (playerC.getState()) {
        	case GOING_UP:
	            racketC -= getRacketSpeed() * deltaT;
	            if (racketC < 0.00) racketC = 0.00;
	            break;
        	case IDLE:
        		break;
        	case GOING_DOWN:
        		racketC += getRacketSpeed() * deltaT;
        		if (racketC + getRacketSize() > getWidth()) racketC = getWidth() - getRacketSize();
        		break;
        }
        switch (playerD.getState()) {
	        case GOING_UP:
	            racketD -= getRacketSpeed() * deltaT;
	            if (racketD < 0.00) racketD = 0.00;
	            break;
	        case IDLE:
	            break;
	        case GOING_DOWN:
	            racketD += getRacketSpeed() * deltaT;
	            if (racketD + getRacketSize() > getWidth()) racketD = getWidth() - getRacketSize();
	            break;
        }
    }


    /**
     * @return true if a player lost
     */

    public boolean updateBall2(double deltaT) {
        double nextBallX2 = ballX2 + deltaT * ballSpeedX2 * 0.75;
        double nextBallY2 = ballY2 + deltaT * ballSpeedY2 * 0.75;
        if((nextBallY2 < 0 && !joueurt) || (nextBallY2 > getHeight() && !joueurq)){
            ballSpeedY2 = -ballSpeedY2;
            nextBallY2 = ballY2 + deltaT * ballSpeedY2;
            nextBallX2 = ballX2 + ((ballSpeedX2<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedX2)); 
        }else if((nextBallX2 < 0 && !joueurd) || (nextBallX2 > getWidth() && !joueuru)){
            ballSpeedX2 = -ballSpeedX2;
            nextBallY2 = ballY2 + deltaT * ballSpeedY2;
            nextBallX2 = ballX2 + ((ballSpeedX2<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedX2)); 
        }else if ((nextBallY2 < 0 && nextBallX2 > racketC && nextBallX2 < racketC + getRacketSize()) 
        || (nextBallY2 > getHeight() && nextBallX2 > racketD && nextBallX2 < racketD + getRacketSize())){
            ballSpeedX2 = -ballSpeedX2;
            nextBallX2 = ballX2 + deltaT * ballSpeedX2; 
        }else if ((nextBallX2 < 0 && nextBallY2 > getRacketA() && nextBallY2 < getRacketA() + getRacketSize())
                || (nextBallX2 < 0 && nextBallY2 > racketC && nextBallY2 < racketC + getRacketSize())
                || (nextBallX2 > getWidth() && nextBallY2 > getRacketB() && nextBallY2 < getRacketB() + getRacketSize())
                || (nextBallX2 > getWidth() && nextBallY2 > racketD && nextBallY2 < racketD + getRacketSize())) {
            ballSpeedX2 = -ballSpeedX2;
            nextBallX2 = ballX2 + deltaT * ballSpeedX2;
        }else if (nextBallY2 < 0) {
            getScore().removeScore3();
            if (getScore().elimination(3)){ // 3 Haut 4 Bas
                joueurt = false;
                isItOver();
                gm.getGameRoot().getChildren().remove(gm.getRacketC());
            }
            return true;
        }else if (nextBallY2 > getHeight()) {
            getScore().removeScore4();
            if (getScore().elimination(4)){
                joueurq = false;
                isItOver();
                gm.getGameRoot().getChildren().remove(gm.getRacketD());
            }
            return true;
        }else if (nextBallX2 < 0) {
            getScore().removeScore2();
            if (getScore().elimination(2)){
                joueurd = false;
                isItOver();
                gm.getGameRoot().getChildren().remove(gm.getRacketA());
            }
            return true;
        }else if (nextBallX2 > getWidth()) {
            getScore().removeScore1();
            if (getScore().elimination(1)){
                joueuru = false;
                isItOver();
                gm.getGameRoot().getChildren().remove(gm.getRacketB());
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
        if((nextBallY < 0 && !joueurt) || (nextBallY > getHeight() && !joueurq)){
            setBallSpeedY(-getBallSpeedY());
            nextBallY = getBallY() + deltaT * getBallSpeedY();
            nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(getBallSpeedX()));
        }else if((nextBallX < 0 && !joueurd) || (nextBallX > getWidth() && !joueuru)){
            setBallSpeedX(-getBallSpeedX());
            nextBallX = getBallX() + deltaT * getBallSpeedX();
        }else if ((nextBallY < 0 && nextBallX > racketC && nextBallX < racketC + getRacketSize()) 
        || (nextBallY > getHeight() && nextBallX > racketD && nextBallX < racketD + getRacketSize())){
            setBallSpeedY(-getBallSpeedY());
            nextBallY = getBallY() + deltaT * getBallSpeedY();
            nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(getBallSpeedX())); 
        }else if ((nextBallX < 0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketSize())
                || (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketSize())) {
            setBallSpeedX(-getBallSpeedX());
            nextBallX = getBallX() + deltaT * getBallSpeedX();
        }else if (nextBallY < 0) {
            getScore().removeScore3();
            if (getScore().elimination(3)){ // 3 Haut 4 Bas
                joueurt = false;
                gm.getGameRoot().getChildren().remove(gm.getRacketC());
                isItOver();
            }
            return true;
        }else if (nextBallY > getHeight()) {
            getScore().removeScore4();
            if (getScore().elimination(4)){
                gm.getGameRoot().getChildren().remove(gm.getRacketD());
                joueurq = false;
                isItOver();
            }
            return true;
        }else if (nextBallX < 0) {
            getScore().removeScore2();
            if (getScore().elimination(2)){
                gm.getGameRoot().getChildren().remove(gm.getRacketA());
                joueurd = false;
                isItOver();
            }
            return true;
        }else if (nextBallX > getWidth()) {
            getScore().removeScore1();
            if (getScore().elimination(1)){
                joueuru = false;
                isItOver();
                gm.getGameRoot().getChildren().remove(gm.getRacketB());
            }
            return true;
        }
        setBallX(nextBallX);
        setBallY(nextBallY);
        return false;
    }

    private void isItOver(){
        if (!joueuru && !joueurd && !joueurt) fin(4);
        if (!joueuru && !joueurd && !joueurq) fin(3);
        if (!joueuru && !joueurt && !joueurq) fin(1);
        if (!joueurd && !joueurt && !joueurq) fin(2);
    }

    private void fin(int n){
        System.out.println("Test 2");
        gm.finGame = true ;
        gm.endGame(n);
    }

    public void reset() {
    	super.reset();
        this.racketC = getWidth()/2 - getRacketSize()/2;
        this.racketD = getWidth()/2 - getRacketSize()/2;
        this.ballSpeedX2 = 200;
        this.ballSpeedY2 = -200;
        this.ballX2 = getWidth() / 2;
        this.ballY2 = getHeight() / 2;
    }
}
