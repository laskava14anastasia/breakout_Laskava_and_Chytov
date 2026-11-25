import java.awt.*;
/**
 * Цей тип цегли створює кульку при знищенні
 * @author Читов Олександр
 */
public class BonusBallBrick extends Brick {
    public BonusBallBrick(double x, double y, double width, double height,
                          int hits) {
        super(x, y, width, height, hits);
        super.setFillColor(Color.WHITE);
    }

}