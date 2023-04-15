package model;

import java.util.Random;

import gui.GameView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CourtObstacles extends Court {
    private GameView gameView ;  
    private Obstacle [] obstacles ;
    private char tireur ; 

    public CourtObstacles (RacketController playerA, RacketController playerB, double width, double height, int limit) {
        super(playerA, playerB, width, height, limit) ;
        obstacles = new Obstacle [4] ;
        for (int i = 0 ; i<4 ; i++){
            obstacles[i] = genObstacle(this, i) ; 
        }  
    }

    public void refresh () {
        for (Obstacle x : obstacles) {
            gameView.destroyObst(x);
        }
        for (int i = 0 ; i<obstacles.length ; i++) {
            obstacles[i] = genObstacle(this, i) ; 
            gameView.addObst(obstacles[i]);
        }
        reset();
        getScore().reset();
    }

    public Obstacle[] getObstacles () {
        return this.obstacles ; 
    }

    public void setGameView (GameView gameView) {
        this.gameView = gameView ; 
    }

    @Override
    public void update (double deltaT) {
        for (Obstacle x : obstacles) {
            if (x.mobile) {
                x.updateObstacle(deltaT);
                gameView.updateObstacle(x);
            }    
        }
        super.update(deltaT);
    }

    @Override
    public boolean updateBall (double deltaT) { 
        double nextBallX = getBallX() + getBallSpeedX()*deltaT ;
        double nextBallY = getBallY() + getBallSpeedY()*deltaT ; 
        Random rm = new Random() ; 
        if (nextBallY < 0 || nextBallY > getHeight()) {
            setBallSpeedY(-getBallSpeedY());
            nextBallY = getBallY() + getBallSpeedY()*deltaT ; 
            nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * rm.nextDouble(Math.abs(2*getBallSpeedX()));
        }

        for (int i = 0 ; i<obstacles.length ; i++) {
            switch (obstacles[i].ballMeetsObst(nextBallX, nextBallY)) {
                case 0 :
                    setBallSpeedY(-getBallSpeedY());
                    nextBallY = getBallY() + getBallSpeedY()*deltaT ; 
                    nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * rm.nextDouble(Math.abs(2*getBallSpeedX()));
                    obstacles[i].damageObst();
                    break ;
                case 1 :
                    setBallSpeedX(-getBallSpeedX());
                    nextBallX = getBallX() + getBallSpeedX()*deltaT ;
                    nextBallY = getBallY() + ((getBallSpeedY()<0)?-1:+1)*deltaT * rm.nextDouble(Math.abs(2*getBallSpeedY()));
                    obstacles[i].damageObst();
                    break ; 
                case 2 :
                    setBallSpeedY(-getBallSpeedY());
                    setBallSpeedX(-getBallSpeedX());
                    nextBallX = getBallX() + ((getBallSpeedX()<0)?-1:+1)*deltaT * rm.nextDouble(Math.abs(2*getBallSpeedX()));
                    nextBallY = getBallY() + ((getBallSpeedY()<0)?-1:+1)*deltaT * rm.nextDouble(Math.abs(2*getBallSpeedY()));
                    obstacles[i].damageObst();
                    break ; 
            }
            if (!obstacles[i].isDestroyable() && Math.abs(obstacles[i].pv)%5 == 0) {
                obstacles[i].activeBonus(i);
            }
            if (obstacles[i].pv == 0) {
                Obstacle obs = genObstacle(this , i) ;  
                obstacles[i].activeBonus(i);   
                gameView.destroyObst(obstacles[i]) ;
                obstacles[i] = obs ; 
                gameView.addObst (obstacles[i]) ; 
            }
        }

        if ( (nextBallX <0 && nextBallY > getRacketA() && nextBallY < getRacketA() + getRacketSize()) ||
             (nextBallX > getWidth() && nextBallY > getRacketB() && nextBallY < getRacketB() + getRacketSize()) ) {
                tireur = (nextBallX <0)?'g':'d' ; 
                setBallSpeedX(-getBallSpeedX());
                nextBallX = getBallX() + getBallSpeedX()*deltaT ;
                nextBallY = getBallY() + ((getBallSpeedY()<0)?-1:+1)*deltaT * rm.nextDouble(Math.abs(2*getBallSpeedY()));
            }else if (getScore() != null && nextBallX < 0) {
                    getScore().addScore1();
                    if (getScore().endGame() != -1) {
                        GameView.finGame = true ; 
                        GameView.endGame(1);
                    }
                    return true ; 
            }else if (getScore() != null && nextBallX > getWidth()) {
                    getScore().addScore2();
                    if (getScore().endGame() != -1) {
                        GameView.finGame = true ; 
                        GameView.endGame(2);
                    }
                    return true ; 
            }
            setBallX(nextBallX);
            setBallY(nextBallY);    
            return false ; 
    }

    public static Obstacle genObstacle (CourtObstacles court , int posTab) { // oki
            // parametres
            Random rm = new Random() ;
            Shape shape = null ; 
            boolean mobile ; 
            double posX =0, posY =0; 

            // generation des fomres et tailles
            switch (rm.nextInt(2)) { 
                case 0 : 
                    Rectangle rec = new Rectangle() ; 
                    rec.setWidth(20+rm.nextInt(11));
                    rec.setHeight(rm.nextInt(71)+130);
                    posY = rm.nextDouble(court.getHeight()-rec.getHeight()) ; 
                    shape = rec ; 
                    break ; 
                case 1 :
                    Circle cir = new Circle() ; 
                    cir.setRadius(30+rm.nextInt(21));
                    posY = cir.getRadius() + rm.nextDouble(court.getHeight()-2*cir.getRadius()+1) ; 
                    shape = cir ;
                    break ;      
            }

            // generatin de la mobilité
            mobile = (rm.nextInt(2) == 0)?true:false ; 

            // generation de la position X
            double widthOrRadiusPrev = 0 ; 
            if (posTab != 0) widthOrRadiusPrev = ((court.obstacles[posTab-1].id == 0)?((Rectangle)court.obstacles[posTab-1].shape).getWidth():((Circle)court.obstacles[posTab-1].shape).getRadius()) ;
            double widthOrRadiusNow = ((shape instanceof Rectangle))?((Rectangle)shape).getWidth():((Circle)shape).getRadius() ; 
            switch(posTab) {
                case 0 : 
                    posX = 200+rm.nextDouble(125-widthOrRadiusNow/2+1) ; 
                    break ; 
                case 1 :
                    posX = court.obstacles[0].posX + 100 + widthOrRadiusPrev + rm.nextDouble(125-widthOrRadiusNow/2+1) ;   
                    break ; 
                case 2 :
                    posX = 550 + rm.nextDouble(150-widthOrRadiusPrev/2+1) ; 
                    break ; 
                case 3 : 
                    posX = court.obstacles[2].posX + 100 + rm.nextDouble(150 - widthOrRadiusNow/2+1) ;
                    break ;   
            }

            // creation de l'obstacle
            CourtObstacles.Obstacle a = court.new Obstacle(shape, mobile, (rm.nextInt(2) == 1)?-1:5+rm.nextInt(10) , posX , posY) ; 
           
           // reglage de la vitesse
            a.vitesseObst = ((rm.nextInt(2) == 0)?-1:1)*(((rm.nextDouble(76)))+75) ;

            // configuration des bonus
            switch (rm.nextInt(6)) {
                case 0 :
                    a.bonus = "+1" ; 
                    break ; 
                case 1 :
                    a.bonus = "-1" ; 
                    break ; 
                case 2 :
                    a.bonus = "+V" ; 
                    break ; 
                case 3 :
                    a.bonus = "+O" ; 
                    break ; 
                case 4 :
                    a.bonus = "+VO" ; 
                    break ;     
                case 5 :
                    a.bonus = "null" ; 
                    break ;               
            } 
            return a ; 
        }
    
    public class Obstacle {
        private Shape shape ; // rectangle || circle 
        private boolean mobile ; // true , false if not
        private double posX , posY ;
        private int pv ; // 4 pv , if (pv == 0) disappear , (-1 if undestroyable)
        private double vitesseObst ; 
        private int id ; // if (id == 0) rectangle , else circle  
        private String bonus ; 


        public Obstacle (Shape shape , Boolean mobile , int pv , Double posX , Double posY) {
            this.shape = shape ;
            this.mobile = mobile ; 
            this.posX = posX ; 
            this.posY = posY ; 
            this.pv = pv ;
            this.id = (shape instanceof Rectangle)?0:1 ;  
        }
        
        public double getPosY() {
            return posY;
        }

        public boolean isDestroyable () {
            return pv != -1 ; 
        }

        public double getPosX() {
            return posX;
        }

        public Shape getShape () {
            return this.shape ; 
        }

        public int getId () {
            return this.id ; 
        }

        public void damageObst () {
            pv-- ; 
        }

        private void activeBonus (int posTab) {
            switch (bonus) {

                case "+1":// ajoute 1pts 
                    if (tireur == 'd') {
                        getScore().addScore2() ; 
                    }else{
                        getScore().addScore1();
                    }
                break ; 
                
                case "-1" ://fait perdre 1pts  
                    if (tireur == 'd') {
                        getScore().descreaseScore2();
                    }else{
                        getScore().descreaseScore1();
                    }
                break ; 
                
                case "+V" :// accelere la balle
                    setBallSpeedX(getBallSpeedX()*1.5);
                    setBallSpeedY(getBallSpeedY()*1.5);
                break ; 
                
                case "+O" :// genere un nouvel objet aleatoirement
                    int pos ; 
                    do{
                        pos = ((int)Math.random()*10)%4 ; 
                    }while(pos == posTab) ; 
                    Obstacle tmp = genObstacle(CourtObstacles.this, pos) ; 
                    gameView.destroyObst(obstacles[pos]);
                    obstacles[pos] = tmp ; 
                    gameView.addObst(tmp);
                break ; 

                case "+VO" :// accelere la vitesse des obstacles
                    for (Obstacle i : obstacles) {
                        i.vitesseObst*=1.25 ;
                    }
                break ;     
                    
                default :
                break ; 
            }
        }

        private int ballMeetsObst (double nextBallX , double nextBallY) { 
            if (id == 0) {
                Rectangle rec = (Rectangle)shape ;
                // on check s'il y a colision
                if (( nextBallX >= posX && nextBallX <= posX + rec.getWidth() && nextBallY >= posY && nextBallY <= posY + rec.getHeight())){
                    if (getBallX() <= posX || getBallX() >= posX + rec.getWidth()) return 1 ;   
                    if (getBallY() <= posY || getBallY() >= posY + rec.getHeight()) return 0 ; 
                    return ((int)(Math.random()*10))%3 ; 
                } 
            }else{
                Circle circ = (Circle)shape ; 
                if ( Math.pow(nextBallX - posX, 2) + Math.pow(nextBallY - posY, 2) 
                <= Math.pow(circ.getRadius(), 2)) return ((int)(Math.random()*10))%3;
            }
            return -1 ; 
        }

        private void updateObstacle (double deltaT) {
            double nextPosY = posY + vitesseObst*deltaT ; 
            if (id == 0) {
                if (nextPosY <0 || nextPosY + ((Rectangle)shape).getHeight() > getHeight()) {
                    vitesseObst = - vitesseObst ; 
                    nextPosY = posY + vitesseObst*deltaT ; 
                } 
            }else{
                if (nextPosY - ((Circle)shape).getRadius() < 0 || nextPosY + ((Circle)shape).getRadius() > getHeight() ) {
                    vitesseObst = - vitesseObst ; 
                    nextPosY = posY + vitesseObst*deltaT ; 
                }
            }
            posY = nextPosY ; 
        }
    }
}