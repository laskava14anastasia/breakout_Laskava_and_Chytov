import java.awt.Color;
import acm.graphics.*;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.util.RandomGenerator;
/**
 *@author Читов Олександр
 */
public class Ball extends GOval{
	/**   швидкість м'яча */
	public double speed = 2;
	/**  сила удару по цеглі  */
	public double damage = 1;
	/**  рух вправо або вліво на секунду  */
	public double xVelocity = 1;
	/**  рух вниз/вгору  */
	public double yVelocity = 1;
	/**  чи м'яч впав */
	private boolean fell=false;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	/**  Створює м'яч, що рухається і відстрибує від поверхнь */
	public Ball(double x, double y, double radius) {
		super(x, y, radius*2, radius*2);
		launch();
	}
	/** Створює м'яч, що рухається і відстрибує від поверхнь, з усіма параметрами   */
	public Ball(double x, double y, double radius, double damage, double speed) {
		super(x, y, radius*2, radius*2);
		this.damage = damage;
		this.speed = speed;
		xVelocity = rgen.nextDouble(-1, 1);
		super.setColor(Color.WHITE);
		super.setFilled(true);

	}
	/**
	 * Задає рандомний напрямок
	 */
	private void launch(){
		setColor(Color.WHITE);
		setFilled(true);
		xVelocity=rgen.nextDouble(0.3, 1.0);
		if(rgen.nextBoolean(0.5)){
			xVelocity=-xVelocity;
		}
	}
	/**
	 * Рухає м'яч у напрямку та відстрибує від меж вікна і прямокуників. Знищується, якщо вилітає знизу за межі вікна
	 * @param floorY висота вікна
	 * @param rightWallX ширина вікна
	 * @param obj об'якт, що доторкається
	 */
	public boolean moveBall(double floorY, double rightWallX, GObject obj){
		move(xVelocity*speed, yVelocity*speed);
		if(getY()+getHeight()>=floorY){
			fell=true;
			return false;
		} else if(checkForCollisionWindow(rightWallX)){
			return true;
		}
		return checkForCollisionObject(obj);
	}
	/**
	 * повертає чи м'яч на підлозі
	 */
	public boolean hasFallen(){
		return fell;
	}
	/**
	 * Перевіряє чи вдарився у межі вікна та відстрибує або знищується якщо вдарився об низ вікна
	 * @param rightWallX ширина вікна
	 */
	private boolean checkForCollisionWindow(double rightWallX) {
		if (getY() <= 0) {
			yVelocity = Math.abs(yVelocity);
			setLocation(getX(), 0);
			return true;
		}
		if (getX() + getWidth() >= rightWallX) {
			xVelocity = -Math.abs(xVelocity);
			setLocation(rightWallX - getWidth(), getY());
			return true;
		}
		if (getX() <= 0) {
			xVelocity = Math.abs(xVelocity);
			setLocation(0, getY());
			return true;
		}
		return false;
	}
	/**
	 *Перевіряє чи стикнувся з іншим об'єктом та відстрибує якщо це правильний об'єкт
	 */
	private boolean checkForCollisionObject(GObject obj) {
		if (obj == null) return false;
		if (!(obj instanceof Brick) && !(obj instanceof Paddle)) return false;
		if (!getBounds().intersects(obj.getBounds())) return false;
		double overlapTop = (getY() + getHeight()) - obj.getY();
		double overlapBottom = (obj.getY() + obj.getHeight()) - getY();
		double overlapLeft = (getX() + getWidth()) - obj.getX();
		double overlapRight = (obj.getX() + obj.getWidth()) - getX();
		double minOverlap = Math.min(Math.min(overlapTop, overlapBottom), Math.min(overlapLeft, overlapRight));
		if (minOverlap == overlapTop) {
			yVelocity = -Math.abs(yVelocity);
			setLocation(getX(), obj.getY() - getHeight());
		} else if (minOverlap == overlapBottom) {
			yVelocity = Math.abs(yVelocity);
			setLocation(getX(), obj.getY() + obj.getHeight());
		} else if (minOverlap == overlapLeft) {
			xVelocity = -Math.abs(xVelocity);
			setLocation(obj.getX() - getWidth(), getY());
		} else if (minOverlap == overlapRight) {
			xVelocity = Math.abs(xVelocity);
			setLocation(obj.getX() + obj.getWidth(), getY());
		}
		return true;
	}
}