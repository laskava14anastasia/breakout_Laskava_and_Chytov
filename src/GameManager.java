import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
public class GameManager {
	public static int score = 0;
	public static int gold = 0;
	public static int lives = 3;
	public static boolean gamePaused = false;
	public static Main Scene;
	private static final int SHOP_CHECKPOINT_SCORE = 400;
	/**
	 * Use this to check every frame and access shop if score milestone reached
	 * @return true if score has reached a constant milestone
	 */
	public static boolean checkShop(){
		return score % 	SHOP_CHECKPOINT_SCORE == 0;
	}
	/**
	 * Buys a power up if enough gold present and uses it based on PowerUps passed
	 * @param cost price of the power up
	 * @param powerUp A power up to be bought
	 * @return true if the purchase succeeded
	 */
	public static boolean buyPowerUp(int cost, PowerUps powerUp){
		if(gold >= cost){
			gold-=cost;
			switch(powerUp){
				case other:
					break;
			}

			return true;
		}
		return false;
	}
	public enum PowerUps{
		BallPowerUp,
		WiderStick,
		Explosion,
		other
	}
}