package gui;
import javafx.scene.text.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import model.*;

public class GameView4J extends GameView {
	
	private Court4J court;
    private final Rectangle racketC, racketD;
    private final Circle ball2;

    public Rectangle getRacketC(){return racketC;}
    public Rectangle getRacketD(){return racketD;}

    public GameView4J(Court4J court, Pane root, double scale) {
        super(court,root,scale);
        this.court = court;

        court.getScore().getS3().setStyle("-fx-font: 60 arial;");
        court.getScore().getS3().setX(400);
        court.getScore().getS3().setY(95);

        court.getScore().getS4().setStyle("-fx-font: 60 arial;");
        court.getScore().getS4().setX(775);
        court.getScore().getS4().setY(95);
        court.getScore().getS1().setFill(Color.RED);
        court.getScore().getS2().setFill(Color.BLUE);
        court.getScore().getS3().setFill(Color.GREEN);
        court.getScore().getS4().setFill(Color.YELLOW);

		racketC = new Rectangle();
		racketC.setWidth(court.getRacketSize() * scale);
		racketC.setHeight(getRacketThickness());
		
		racketC.setX(court.getRacketC() * scale + getMargin()/2);
		racketC.setY(getInTerface() + getMargin()/2 - getRacketThickness());
		    
		racketD = new Rectangle();
		racketD.setWidth(court.getRacketSize() * scale);
		racketD.setHeight(getRacketThickness());
		
		racketD.setX(court.getRacketD() * scale + getMargin()/2);
		racketD.setY(getInTerface() + getMargin()/2 + court.getHeight() + getRacketThickness());

        ball2 = new Circle();
        ball2.setRadius(court.getBallRadius());

        ball2.setCenterX(court.getBallX() * scale + getMargin());
        ball2.setCenterY(court.getBallY() * scale + getInTerface() +  getMargin()/2);

        switch(theme.nom){
            case "Défaut" :
                racketC.setFill(Color.LIGHTBLUE);
                racketD.setFill(Color.LIGHTBLUE);
                ball2.setFill(Color.LIGHTBLUE);
                break;

            case "Night étoilée":
                racketC.setFill(Color.valueOf("#d2dbfc"));
                racketD.setFill(Color.valueOf("#d2dbfc"));
                ball2.setFill(Color.valueOf("#d2dbfc"));
                break;

            case "Aurores Boeréales":
                racketC.setFill(Color.valueOf("#99ffb9"));
                racketD.setFill(Color.valueOf("#99ffb9"));
                ball2.setFill(Color.valueOf("#99ffb9"));
                break; 

            case "Forêt" :
                racketC.setFill(Color.valueOf("#aad3e3"));
                racketD.setFill(Color.valueOf("#aad3e3"));
                ball2.setFill(Color.valueOf("#aad3e3"));
                break;
            
            case "Rétro" : 
                racketC.setStroke(Color.valueOf("#FC4855"));
                racketC.setFill(Color.valueOf("#290543"));

                racketD.setStroke(Color.valueOf("#FC4855"));
                racketD.setFill(Color.valueOf("#290543"));

                ball2.setStroke(Color.valueOf("#FC4855"));
                ball2.setFill(Color.valueOf("#290543"));
                
                break;
        }
		    
        getGameRoot().getChildren().addAll(racketC, racketD, ball2, court.getScore().getS3(), court.getScore().getS4());
    }

    public static void endGame (int player) {
        Image fin = new Image((player==2)?"file:src/Pictures/WinJ22.png":(player ==1)?"file:src/Pictures/WinJ11.png":(player ==3)?"file:src/Pictures/WinJ33.png":(player ==4)?"file:src/Pictures/WinJ44.png":"file:src/Pictures/egalite.png");
        ImageView finJ = new ImageView(fin);
        Image smoke = new Image("file:src/Pictures/whitesmoke.png");
        ImageView whitesmoke = new ImageView(smoke);
        App.root.getChildren().add(whitesmoke);
        App.root.getChildren().add(finJ);
        App.Quitter.setLayoutX(370);
        App.Recommencer.setLayoutX(695);
        App.Recommencer.setLayoutY(400);
        App.Quitter.setLayoutY(390);
        App.root.getChildren().addAll(gui.App.Quitter, gui.App.Recommencer);
    }
    public void animate() {
        new AnimationTimer() {
            long last = 0;
            @Override
            public void handle(long now) {
                if(!pause && !finGame){
                    if (last == 0) { // ignore the first tick, just compute the first deltaT
                        last = now;
                        return;
                    }
                    getCourt().update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                    last = now;
                    getRacketA().setY(getCourt().getRacketA() * getScale() + getMargin()/2 + getInTerface());
                    getRacketB().setY(getCourt().getRacketB() * getScale() + getMargin()/2 + getInTerface());
                    racketC.setX(getMargin() + court.getRacketC() * getScale());
                    racketD.setX(getMargin() + court.getRacketD() * getScale());
                    getBall().setCenterX(getCourt().getBallX() * getScale() + getMargin());
                    getBall().setCenterY(getCourt().getBallY() * getScale() + getMargin()/2 + getInTerface());
                    ball2.setCenterX(court.getBallX2() * getScale() + getMargin());
                    ball2.setCenterY(court.getBallY2() * getScale() + getMargin()/2 + getInTerface());
                }else{
                    last = 0;
                }    
                Timer--;
            }
        }.start();
    }



}
