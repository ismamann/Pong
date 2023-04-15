package gui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;


/* ------------------------------------------------------------------------------------------------------*/

//Test pour le menu Commande, à refaire en entier. 

public class MenuTheme extends Application{

    public Pane root;
    public Scene gameScene;
    Theme[] themeListe = {Theme.t0, Theme.t1, Theme.t2, Theme.t3, Theme.t4};
    static int current = 0;
   
    MenuTheme(Pane root, Scene a){
        this.root = root;
        gameScene = a;
    }

    public void start (Stage primaryStage) {

        primaryStage.setScene(gameScene);
        primaryStage.show();

        Button Retour= new Button("Retour") ;
        Retour.setOnAction(ev1 -> {
            Pane root1 = new Pane();
            gameScene.setRoot(root1);
            Menu a = new Menu(root1, gameScene);
            a.start(primaryStage);
        });
        Retour.setLayoutX(1100);
        Retour.setLayoutY(25);
        Retour.setEffect(new ImageInput(new Image("file:src/Pictures/retour.png")));
        Retour.setSkin(new MyButtonSkin(Retour));

        ImageView preview = new ImageView(new Image(themeListe[current].preview));
        preview.setX(300);
        preview.setY(70);

        Text nom = new Text(themeListe[current].nom);
        nom.setStyle("-fx-font: 30 arial;");
        nom.setX(400);
        nom.setY(510);

        Rectangle r = new Rectangle();
        r.setWidth(500);
        r.setHeight(49);
        r.setX(350);
        r.setY(470);
        r.setFill(Color.valueOf("#cdd1cf"));

        Button prev = new Button("prev") ;
        prev.setOnAction(evl -> {
            if(current == 0){
                current = themeListe.length -1;
            } else {
                current --;
            }
            nom.setText(themeListe[current].nom);
            preview.setImage(new Image(themeListe[current].preview));
        });
        prev.setLayoutX(300);
        prev.setLayoutY(470);
        prev.setEffect(new ImageInput(new Image("file:src/Pictures/prev.png")));
        prev.setSkin(new MyButtonSkin(prev));

        Button next = new Button("next") ;
        next.setOnAction(evl -> {
            if(current == themeListe.length -1){
                current = 0;
            } else {
                current ++;
            }
            nom.setText(themeListe[current].nom);
            preview.setImage(new Image(themeListe[current].preview));
        });
        next.setLayoutX(875);
        next.setLayoutY(470);
        next.setEffect(new ImageInput(new Image("file:src/Pictures/next.png")));
        next.setSkin(new MyButtonSkin(next));


        Button save = new Button("Sauvegarder");
        save.setOnAction(evl -> {
            GameView.theme = themeListe[current];
            Pane root1 = new Pane();
                    gameScene.setRoot(root1);
                    Menu a = new Menu(root1, gameScene);
                    a.start(primaryStage);
        });
        save.setLayoutX(550);
        save.setLayoutY(570);
        save.setEffect(new ImageInput(new Image("file:src/Pictures/enregistrer.png")));
        save.setSkin(new MyButtonSkin(save));

        root.getChildren().addAll(Retour, preview, r, nom, prev, next, save);
    }

}
