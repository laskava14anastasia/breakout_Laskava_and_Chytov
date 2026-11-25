/**
 * Клас де зберігаються всі референси на кульки створені на сцені. Потрібен щоб існували та рухались кілька одночасно без масивів
 * @author Читов Олександр
 */
public class ObjectsRepository {
	public static Ball Ball1 = null;
	public static Ball Ball2 = null;
	public static Ball Ball3 = null;
	public static Ball Ball4 = null;
	public static Ball Ball5 = null;
	public static Ball Ball6 = null;
	public static Ball Ball7 = null;
	public static Ball Ball8 = null;
	public static Ball Ball9 = null;
	public static Ball Ball10 = null;
	public static Ball Ball11 = null;
	public static Ball Ball12 = null;
	public static Ball Ball13 = null;
	public static Ball Ball14 = null;
	public static Ball Ball15 = null;
	public static Ball Ball16 = null;
	public static Ball Ball17 = null;
	public static Ball Ball18 = null;
	public static Ball Ball19 = null;
	public static Ball Ball20 = null;
	public static Paddle Paddle;
	/**
	 * Створює новий екземпляр обʼєкта (мʼяча) та зберігає його у перший доступний вільний слот
	 * @param x початкова координата X
	 * @param y початкова координата Y
	 * @param size розмір мʼяча
	 * @param damage урон, яку завдає мʼяч при зіткненні
	 * @param speed швидкість руху мʼяча
	 * @return створений екземпляр Ball або null, якщо всі 20 слотів вже зайняті
	 */
	public static Ball CreateBall(double x, double y, double size, double damage, double speed) {
		if (Ball1 == null) { Ball1 = new Ball(x, y, size, damage, speed); return Ball1; }
		if (Ball2 == null) { Ball2 = new Ball(x, y, size, damage, speed); return Ball2; }
		if (Ball3 == null) { Ball3 = new Ball(x, y, size, damage, speed); return Ball3; }
		if (Ball4 == null) { Ball4 = new Ball(x, y, size, damage, speed); return Ball4; }
		if (Ball5 == null) { Ball5 = new Ball(x, y, size, damage, speed); return Ball5; }
		if (Ball6 == null) { Ball6 = new Ball(x, y, size, damage, speed); return Ball6; }
		if (Ball7 == null) { Ball7 = new Ball(x, y, size, damage, speed); return Ball7; }
		if (Ball8 == null) { Ball8 = new Ball(x, y, size, damage, speed); return Ball8; }
		if (Ball9 == null) { Ball9 = new Ball(x, y, size, damage, speed); return Ball9; }
		if (Ball10 == null) { Ball10 = new Ball(x, y, size, damage, speed); return Ball10; }
		if (Ball11 == null) { Ball11 = new Ball(x, y, size, damage, speed); return Ball11; }
		if (Ball12 == null) { Ball12 = new Ball(x, y, size, damage, speed); return Ball12; }
		if (Ball13 == null) { Ball13 = new Ball(x, y, size, damage, speed); return Ball13; }
		if (Ball14 == null) { Ball14 = new Ball(x, y, size, damage, speed); return Ball14; }
		if (Ball15 == null) { Ball15 = new Ball(x, y, size, damage, speed); return Ball15; }
		if (Ball16 == null) { Ball16 = new Ball(x, y, size, damage, speed); return Ball16; }
		if (Ball17 == null) { Ball17 = new Ball(x, y, size, damage, speed); return Ball17; }
		if (Ball18 == null) { Ball18 = new Ball(x, y, size, damage, speed); return Ball18; }
		if (Ball19 == null) { Ball19 = new Ball(x, y, size, damage, speed); return Ball19; }
		if (Ball20 == null) { Ball20 = new Ball(x, y, size, damage, speed); return Ball20; }
		return null;
	}
	/**
	 *Знаходить у своєму складі ваІІ і прибирає його
	 * @param ball
	 * @return
	 */
	public static boolean RemoveBall(Ball ball) {
		if (Ball1 == ball) { Ball1 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball2 == ball) { Ball2 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball3 == ball) { Ball3 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball4 == ball) { Ball4 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball5 == ball) { Ball5 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball6 == ball) { Ball6 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball7 == ball) { Ball7 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball8 == ball) { Ball8 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball9 == ball) { Ball9 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball10 == ball) { Ball10 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball11 == ball) { Ball11 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball12 == ball) { Ball12 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball13 == ball) { Ball13 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball14 == ball) { Ball14 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball15 == ball) { Ball15 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball16 == ball) { Ball16 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball17 == ball) { Ball17 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball18 == ball) { Ball18 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball19 == ball) { Ball19 = null; GameManager.Scene.remove(ball); return true; }
		if (Ball20 == ball) { Ball20 = null; GameManager.Scene.remove(ball); return true; }
		return false;
	}
	/**
	 *Повертає чи існує хоча б одна кулька на сцені
	 * @return
	 */
	public static boolean hasBalls(){
		if (ObjectsRepository.Ball1 == null
				&& ObjectsRepository.Ball2 == null
				&& ObjectsRepository.Ball3 == null
				&& ObjectsRepository.Ball4 == null
				&& ObjectsRepository.Ball5 == null
				&& ObjectsRepository.Ball6 == null
				&& ObjectsRepository.Ball7 == null
				&& ObjectsRepository.Ball8 == null
				&& ObjectsRepository.Ball9 == null
				&& ObjectsRepository.Ball10 == null
				&& ObjectsRepository.Ball11 == null
				&& ObjectsRepository.Ball12 == null
				&& ObjectsRepository.Ball13 == null
				&& ObjectsRepository.Ball14 == null
				&& ObjectsRepository.Ball15 == null
				&& ObjectsRepository.Ball16 == null
				&& ObjectsRepository.Ball17 == null
				&& ObjectsRepository.Ball18 == null
				&& ObjectsRepository.Ball19 == null
				&& ObjectsRepository.Ball20 == null) {
			return false;
		}
		return true;
	}
	/**
	 *Прибирає усі референси
	 */
	public static void clearBalls(){
		Ball1 = null;
		Ball2 = null;
		Ball3 = null;
		Ball4 = null;
		Ball5 = null;
		Ball6 = null;
		Ball7 = null;
		Ball8 = null;
		Ball9 = null;
		Ball10 = null;
		Ball11 = null;
		Ball12 = null;
		Ball13 = null;
		Ball14 = null;
		Ball15 = null;
		Ball16 = null;
		Ball17 = null;
		Ball18 = null;
		Ball19 = null;
		Ball20 = null;
	}
}