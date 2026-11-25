import java.awt.Color;
import acm.graphics.*;
/**
 * Падл що рухається горизонтально в одному напрямку, напрямок можна змінити
 * @author Читов Олександр
 *
 */
public class Paddle extends GRect {
	public double dir = 1;
	public double speed = 12;
	public Paddle(double x, double y, double arg2, double arg3) {
		super(x, y, arg2, arg3);
		setFilled(true);
	}
	public Paddle(double x, double y) {
		super(x, y, 150, 25);
		super.setColor(Color.CYAN);
		setFilled(true);
	}
	/**
	 * Рухає падл горизонтально в напрямку дір якщо не виходить за межі вікна
	 */
	public void movePaddle(double x){
		dir = x;
	}
	/**
	 * Рухає падл горизонтально в напрямку дір якщо не виходить за межі вікна
	 */
	public void movePaddle(){
		if(getX() + getWidth() < GameManager.Scene.getWidth() && dir>0 || dir<0 &&
				getX() > 0){
			move(dir*speed, 0);
		}
	}
}