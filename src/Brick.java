import acm.graphics.GRect;
import java.awt.*;
/**
 *
 * @author Ласкава Анастасія
 *
 */
public class Brick extends GRect {
    public int hits;
    /**
     * Створює цеглинку
     * @param x
     * @param y
     * @param width
     * @param height
     * @param hits
     */
    public Brick(double x, double y, double width, double height, int hits){
        super(x, y, width, height);
        this.hits=hits;
        setFilled(true);
        updateColor();
    }
    /**
     * змінює колір відповідно значенню ударів
     */
    public void updateColor(){
        if(hits>=3){
            setFillColor(Color.RED);
        } else if(hits==2){
            setFillColor(Color.ORANGE);
        } else{
            setFillColor(Color.GREEN);
        }
        setColor(Color.WHITE);
    }
}