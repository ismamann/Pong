package model;

import javafx.scene.text.Text;

public class Score {
    private Text s1;
    private Text s2;
    private Text s3;
    private Text s4;

    private int limitePoint;

    public Text getS1(){
        return this.s1;
    }

    public Text getS2(){
        return this.s2;
    }

    public Text getS3(){
        return this.s3;
    }

    public Text getS4(){
        return this.s4;
    }

    Score(){
        s1 = new Text("0");
        s2 = new Text("0");
    }

    Score (int limit) {
        this() ;
        this.limitePoint = limit ; 
    }

    public int getLimit(){
        return this.limitePoint;
    }

    public void addScore1(){
        s1.setText(String.valueOf(Integer.valueOf(s1.getText())+1));
    }

    public void descreaseScore2() { // added
        if(!s2.getText().equals("0")) s2.setText(String.valueOf(Integer.valueOf(s1.getText())-1));
    }

    public void addScore2(){
        s2.setText(String.valueOf(Integer.valueOf(s2.getText())+1));
    }

    public void descreaseScore1() {//done
        if (!s1.getText().equals("0")) s1.setText(String.valueOf(Integer.valueOf(s1.getText())-1));
    }

    public int endGame() {
        if (Integer.valueOf(s1.getText()) == limitePoint) return 1 ; 
        if (Integer.valueOf(s2.getText()) == limitePoint) return 1 ; 
        return -1 ; 
    }

    public void reset(){
        s1.setText("0");
        s2.setText("0");
    }

    //Spécial 4J

    public void load4J(){  
        s1 = new Text(String.valueOf(limitePoint));
        s2 = new Text(String.valueOf(limitePoint));
        s4 = new Text(String.valueOf(limitePoint));
        s3 = new Text(String.valueOf(limitePoint));
    }

    public void reset4J(){
        s3.setText("0");
        s4.setText("0");
    }

    public void removeScore1(){
        s1.setText(String.valueOf(Integer.valueOf(s1.getText())-1));
    }
    public void removeScore2(){
        s2.setText(String.valueOf(Integer.valueOf(s2.getText())-1));
    }
    public void removeScore3(){
        s3.setText(String.valueOf(Integer.valueOf(s3.getText())-1));
    }
    public void removeScore4(){
        s4.setText(String.valueOf(Integer.valueOf(s4.getText())-1));
    }

    public boolean elimination(int n){
        switch(n){
            case 1:
                if (Integer.valueOf(s1.getText()) == 0) return true; 
                return false;
            case 2:
                if (Integer.valueOf(s2.getText()) == 0) return true;
                return false;
            case 3:
                if (Integer.valueOf(s3.getText()) == 0) return true;
                return false;
            case 4:
                if (Integer.valueOf(s4.getText()) == 0) return true;
                return false;
        }
        return false;
    }

}