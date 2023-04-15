package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Trail {

    protected Color color;
    protected float alpha;
    protected float life;
    protected Shape shape;
    protected TrailListener listener;

    public Trail(Shape shape, Color color, float life, TrailListener listener) {
        this.shape = shape;
        this.color = color;
        this.alpha = 0.8f;
        this.life = life;
        this.listener = listener;
    }
    public void tick() {
        if (alpha > life) {
            alpha = alpha - life;
            shape.setFill(Color.color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

        } else {
            if (listener != null) {
                listener.onRemove(this);
            }
        }
    }
    public Shape getShape() {
        return shape;
    }
    public interface TrailListener {
        void onRemove(Trail trail);
    }
}
