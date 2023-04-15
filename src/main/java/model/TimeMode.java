package model;

import java.util.*;
import gui.GameView;
import javafx.scene.text.*;


public class TimeMode extends Court {

  private Text tmp;
  private static Timer timer;
  private int limit;
  private Score scoreFinal;
  private Score scoreManche;
  private Text nbManche;
  private int nbMancheInitial;
 


  public TimeMode(RacketController playerA, RacketController playerB, double width, double height, int nbManche, int t) {
    super(playerA, playerB, width, height);
    tmp = new Text(String.valueOf(t));
    limit = t;
    timer = new Timer();
   
    this.nbManche = new Text("1");
    this.nbMancheInitial = nbManche;
   
    scoreManche = this.getScore();
    scoreFinal = new Score();
    commencerTimer();
  }
  
@Override
public boolean updateBall(double deltaT) {
          if (Integer.valueOf(nbManche.getText()) == nbMancheInitial+1) {
            GameView.finGame = true;
            GameView.endGame(winner());
            
            timer.cancel();
          }
          // first, compute possible next position if nothing stands in the way
          double nextBallX = getBallX() + deltaT * getBallSpeedX();
          double nextBallY = getBallY() + deltaT * getBallSpeedY();
          double ballX = getBallX() ; 
          double ballY = getBallY() ; 
          double ballSpeedX = getBallSpeedX() ; 
          double ballSpeedY = getBallSpeedY() ; 

          // next, see if the ball would meet some obstacle
          if (nextBallY < 0 || nextBallY > getHeight()) {
              ballSpeedY = -ballSpeedY ;
              setBallSpeedY(ballSpeedY);
              nextBallY = ballY + deltaT * ballSpeedY ;
              nextBallX = ballX + ((ballSpeedX<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedX)); 
          }
  
          if ((nextBallX < 0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketSize())  || (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketSize())) { 
              ballSpeedX = -ballSpeedX; 
              setBallSpeedX(ballSpeedX);
              nextBallX = ballX + deltaT * ballSpeedX ;
              nextBallY = ballY +  ((ballSpeedY<0)?-1:+1)*deltaT * (new Random()).nextDouble(Math.abs(ballSpeedY)); 
          }else if (getScore() != null && nextBallX < 0) { 
              getScore().addScore1();
              return true;
          }else if (getScore() != null && nextBallX > getWidth()) { 
              getScore().addScore2();
              return true;
          }
          setBallX(nextBallX);
          setBallY(nextBallY);
          return false;
}

  public void commencerTimer() {
    timer.cancel();
    tmp.setText(String.valueOf(limit));
    timer = new Timer();
    resetNbManche();
    TimerTask a = new TimerTask() { 
      public void run() {
        int n = Integer.valueOf(tmp.getText());
         if (!GameView.finGame) {
            if (!GameView.pause) {
              if (n != 0) tmp.setText(String.valueOf(n-1));
              else {
                tmp.setText(String.valueOf(limit));
                reset();
                finManche();
              }
            }
          }
        }
          
      };

   timer.scheduleAtFixedRate(a, 1000, 1000);
      
  }

  

  public void resetNbManche() {
    nbManche.setText(String.valueOf("1"));
  }


  public int getLimit() {
    return limit;
  }

  public Text getNbManche() {
   return nbManche;
  }


  public static void closeTimer() {
    if (timer != null) timer.cancel();
  }

  public Text getTmp() {
    return tmp;
  }

  public int winner() {
    int s1 = Integer.valueOf(scoreFinal.getS1().getText());
    int s2 = Integer.valueOf(scoreFinal.getS2().getText());
    if (s1 == s2) return 0;

    return (Math.max(s1, s2)==s1)?1:2; 
    
  }



  public void finManche() {
    int s1 = Integer.valueOf(scoreManche.getS1().getText());
    int s2 = Integer.valueOf(scoreManche.getS2().getText());

    if (s1 > s2) scoreFinal.addScore1();
    else if (s1 < s2) scoreFinal.addScore2();
    else {
      scoreFinal.addScore1();
      scoreFinal.addScore2();
    }
    scoreManche.reset();
    nbManche.setText(String.valueOf(Integer.valueOf(nbManche.getText()) +1));

    
  }


}