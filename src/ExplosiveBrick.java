import acm.graphics.GRect;
import java.awt.*;
/**
 * Цей тип цегли вибухає при знищенні
 * @author Читов Олександр
 *
 */
public class ExplosiveBrick extends Brick {
    public ExplosiveBrick(double x, double y, double width, double height,
                          int hits) {
        super(x, y, width, height, hits);
        super.setFillColor(Color.MAGENTA);

    }
}