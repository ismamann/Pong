package gui;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.CourtDeuxContreDeux;

public class GameViewDeuxContreDeux extends GameView {
	
	private CourtDeuxContreDeux court;
    private final Rectangle racketC, racketD;
    private final Circle ball2;

    public GameViewDeuxContreDeux(CourtDeuxContreDeux court, Pane root, double scale) {
        super(court,root,scale);
        this.court = court;
		racketC = new Rectangle();
		racketC.setHeight(court.getRacketSize() * scale);
		racketC.setWidth(getRacketThickness());
		
		racketC.setX(getMargin() - getRacketThickness());
		racketC.setY(court.getRacketC() * scale + getInTerface() + getMargin()/2);
		    
		racketD = new Rectangle();
		racketD.setHeight(court.getRacketSize() * scale);
		racketD.setWidth(getRacketThickness());
		
		racketD.setX(court.getWidth() * scale + getMargin());
		racketD.setY(court.getRacketD() * scale + getInTerface() + getMargin()/2);

        ball2 = new Circle();
        ball2.setRadius(court.getBallRadius());

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

        ball2.setCenterX(court.getBallX() * scale + getMargin());
        ball2.setCenterY(court.getBallY() * scale + getInTerface() +  getMargin()/2);
		    
        getGameRoot().getChildren().addAll(racketC, racketD, ball2);
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
                    racketC.setY(court.getRacketC() * getScale() + getMargin()/2 + getInTerface());
                    racketD.setY(court.getRacketD() * getScale() + getMargin()/2 + getInTerface());
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
