import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import com.sun.org.apache.bcel.internal.generic.POP;

import java.awt.Color;
/**
 * Створює магазин з можливістю купівлі бонусів
 * @author Ласкава Анастасія
 *
 */
public class Shop {
    /** Нічого не куплено */
    public static  final int NOTHING=0;
    /** Куплено додатковий мʼячик */
    public static final int ITEM_BALL=1;
    /** Куплено ширшу паличку */
    public static final int ITEM_WIDE=2;
    /** Куплено рандомний вибух */
    public static final int ITEM_BOOM=3;
    /** Ціна додаткового мʼячика */
    public static final int PRICE_BALL=300;
    /** Ціна ширшої палички */
    public static final int PRICE_WIDE=300;
    /** Ціна рандомного вибуху */
    public static final int PRICE_BOOM=900;
    public static final double DIGIT_H=25;
    private static final double LABEL_H=25;
    private static final double OPEN_MAX_W=110;
    private static final double OPEN_MAX_H=28;
    public static final double POPUP_X=50;
    public static final double POPUP_Y=190;
    public static final double POPUP_W=350;
    public static final double POPUP_H=380;
    private static final double TITLE_TOP=12;
    private static final double COINS_TOP=62;
    private static final double STATUS_TOP=96;
    public static final double BUTTON_LEFT=25;
    public static final double BUTTON_W=240;
    public static final double BUTTON_H=42;
    public static final double PRICE_LEFT=275;
    public static final double BALL_TOP=135;
    public static final double WIDE_TOP=190;
    public static final double BOOM_TOP=245;
    public static final double CLOSE_TOP=315;
    public static boolean isOpen=false;
    public static GraphicsProgram scene;
    public static GCompound popup;
    private static GImage popupCoinsLabel;
    public static GCompound popupGold;
    public static GImage statusImage;
    private static GCompound scoreDigits;
    private static GCompound goldDigits;
    private static GCompound livesDigits;
    private static double scoreX;
    private static double goldX;
    private static double livesX;
    private static double openLeft;
    private static double openTop;
    private static double openW;
    private static double openH;
    private static int lastScore=-1;
    private static int lastGold=-1;
    private static int lastLives=-1;
    /**
     * Малює підписи, кнопку Магазин і числа.Викликається на початку рівня
     * @param program вікно гри
     */
    public static void showHud(GraphicsProgram program){
        scene=program;
        isOpen=false;
        popup=null;
        popupCoinsLabel=null;
        popupGold=null;
        statusImage=null;
        scoreDigits=null;
        goldDigits=null;
        livesDigits=null;
        lastScore=-1;
        lastGold=-1;
        lastLives=-1;
        scoreX=addLabel("images/score_text.png", 10, 5);
        goldX=addLabel("images/money_text.png", 215, 5);
        livesX=addLabel("images/lives_text.png", 10, 32);
        GImage open=loadFit("images/shop_open.png", OPEN_MAX_W, OPEN_MAX_H);
        openW=open.getWidth();
        openH=open.getHeight();
        openLeft=215;
        openTop=32;
        scene.add(open, openLeft, openTop);
        updateHud();
    }
    private static double addLabel(String file, double x, double y){
        GImage label=loadFit(file, 120, LABEL_H);
        scene.add(label, x, y);
        return x+label.getWidth()+6;
    }
    /**
     * Перемальовує числа очок, грошей, життів, якщо вони змінились
     */
    public static void updateHud(){
        if(scene==null){
            return;
        }
        if(GameManager.score!=lastScore){
            lastScore=GameManager.score;
            if(scoreDigits!=null){
                scene.remove(scoreDigits);
            }
            scoreDigits=makeNumber(lastScore);
            scene.add(scoreDigits, scoreX, 5);
        }
        if(GameManager.gold!=lastGold){
            lastGold=GameManager.gold;
            if(goldDigits!=null){
                scene.remove(goldDigits);
            }
            goldDigits=makeNumber(lastGold);
            scene.add(goldDigits, goldX, 5);
            if(isOpen && popup!=null){
                showPopupCoins();
            }
        }
        if(GameManager.lives!=lastLives){
            lastLives=GameManager.lives;
            if(livesDigits!=null){
                scene.remove(livesDigits);
            }
            livesDigits=makeNumber(lastLives);
            scene.add(livesDigits, livesX, 32);
        }
    }
    /**
     *Створює картинку
     * @param file
     * @param maxW
     * @param maxH
     * @return
     */
    private static GImage loadFit(String file, double maxW, double maxH){
        GImage image=new GImage(file);
        double w=image.getWidth();
        double h=image.getHeight();
        if(w>0 && h>0){
            double scale=Math.min(maxW/w, maxH/h);
            image.setSize(w*scale, h*scale);
        }
        return image;
    }
    /**
     * Складає число з картинок цифр
     * @param number число
     * @return шрупа зображень, що малює число
     */
    private static GCompound makeNumber(int number){
        GCompound box=new GCompound();
        String text=""+number;
        double x=0;
        int i=0;
        while(i<text.length()){
            GImage digit=new GImage("images/digit"+text.charAt(i)+".png", 16, DIGIT_H);
            digit.setSize(25, 25);
            box.add(digit, x, 0);
            x=x+digit.getWidth()+2;
            i=i+1;
        }
        return box;
    }
    /**
     * Перевіряє, чи клік потрапив на кнопку Магазин
     * @param x координата кліку по горизонталі
     * @param y координата кліку по вертикалі
     * @return true, якщо натиснули кнопку
     */
    public static boolean isOpenButton(double x, double y){
        return x>=openLeft && x<=openLeft+openW && y>=openTop && y<=openTop+openH;
    }
    /**
     * Показує вікно магазину
     * @param program вікно гри
     */
    public static void open(GraphicsProgram program){
        scene=program;
        isOpen=true;
        popup=new GCompound();
        statusImage=null;
        popupGold=null;
        GRect panel=new GRect(POPUP_W, POPUP_H);
        panel.setFilled(true);
        panel.setFillColor(new Color(30, 30, 60));
        panel.setColor(Color.WHITE);
        popup.add(panel, 0, 0);
        GImage title=loadFit("images/shop_title.png", 220, 40);
        popup.add(title, (POPUP_W-title.getWidth())/2, TITLE_TOP);
        popupCoinsLabel=loadFit("images/money_text.png", 120, LABEL_H);
        popup.add(popupCoinsLabel, 0, COINS_TOP);
        showPopupCoins();
        addRow("images/shop_ball.png", BALL_TOP, PRICE_BALL);
        addRow("images/shop_wide.png", WIDE_TOP, PRICE_WIDE);
        addRow("images/shop_boom.png", BOOM_TOP, PRICE_BOOM);
        GImage close=loadFit("images/shop_close.png", BUTTON_W, BUTTON_H);
        popup.add(close, (POPUP_W-close.getWidth())/2, CLOSE_TOP+(BUTTON_H-close.getHeight())/2);
        scene.add(popup, POPUP_X, POPUP_Y);
    }
    /**
     *
     */
    private static void showPopupCoins(){
        if(popupGold!=null){
            popup.remove(popupGold);
        }
        popupGold=makeNumber(GameManager.gold);
        double total=popupCoinsLabel.getWidth()+8+popupGold.getWidth();
        double left=(POPUP_W-total)/2;
        popupCoinsLabel.setLocation(left, COINS_TOP);
        popup.add(popupGold, left+popupCoinsLabel.getWidth()+8, COINS_TOP);
    }
    /**
     *Додає рядок кнопки
     * @param file
     * @param top
     * @param price
     */
    private static void addRow(String file, double top, int price){
        GImage button=loadFit(file, BUTTON_W, BUTTON_H);
        popup.add(button, BUTTON_LEFT, top+(BUTTON_H-button.getHeight())/2);
        GCompound priceDigits=makeNumber(price);
        popup.add(priceDigits, PRICE_LEFT, top+(BUTTON_H-DIGIT_H)/2);
    }
    /**
     * Показує у вікні напис Куплено або НЕ вистачає грошей
     * @param file шлях до картинки напису
     */
    private static void showStatus(String file){
        if(statusImage!=null){
            popup.remove(statusImage);
        }
        statusImage=loadFit(file, 300, 24);
        popup.add(statusImage, (POPUP_W-statusImage.getWidth())/2, STATUS_TOP);
    }
    /**
     * Піднімає вікно магазину над усім, що додали після нього
     */
    public static void bringToFront(){
        if(isOpen){
            popup.sendToFront();
        }
    }
    /**
     * Закриває вікно магазину
     */
    public static void close(){
        if(isOpen){
            scene.remove(popup);
            isOpen=false;
            popup=null;
            popupCoinsLabel=null;
            popupGold=null;
            statusImage=null;
        }
    }
    /**
     * Обробляє клік у вікні магазину
     * @param x координата кліку по горизонталі
     * @param y координата кліку по вертикалі
     * @return що куплено або NOTHING
     */
    public static int click(double x, double y){
        if(isOver(x, y, BUTTON_LEFT, BALL_TOP)){
            return buy(PRICE_BALL, ITEM_BALL);
        }
        if(isOver(x, y, BUTTON_LEFT, WIDE_TOP)){
            return buy(PRICE_WIDE, ITEM_WIDE);
        }
        if(isOver(x, y, BUTTON_LEFT, BOOM_TOP)){
            return buy(PRICE_BOOM, ITEM_BOOM);
        }
        if(isOver(x, y, (POPUP_H-BUTTON_W)/2, CLOSE_TOP)){
            close();
        }
        return NOTHING;
    }
    /**
     * Перевіряє, чи клік потрапив на кнопку з заданим верхом
     * @param x координата кліку по горизонталі
     * @param y координата кліку по вертикалі
     * @param top верхня координата кнопки
     * @return true, якщо клік на кнопці
     */
    private static boolean isOver(double x, double y, double left, double top){
        double l=POPUP_X+left;
        double t=POPUP_Y+top;
        return x>=l && x<=l+BUTTON_W && y>=t && y<=t+BUTTON_H;
    }
    /**
     * Купує товар, якщо вистачає грошей
     * @param price ціна
     * @param item код товару
     * @return item, якщо куплено, інакше NOTHING
     */
    private static int buy(int price, int item){
        if(GameManager.gold<price){
            showStatus("images/shop_no_money.png");
            return NOTHING;
        }
        GameManager.gold=GameManager.gold-price;
        showStatus("images/ready.png");
        updateHud();
        return item;
    }
    /**
     * Повертає гроші, якщо покупка не спрацювала
     * @param amount скільки повернути
     */
    public static void refund(int amount){
        GameManager.gold=GameManager.gold+amount;
        updateHud();
    }
}