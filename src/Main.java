import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
/**
 * Головне вікно гри
 * @author Ласкава Анастасія
 */
public class Main extends GraphicsProgram{
    private static final String MUSIC_SOUND="sounds/music.wav";
    private static final String BONK_SOUND="sounds/bonk.wav";
    private static final String WIN_SOUND= "sounds/win.wav";
    private static final String LOSE_SOUND= "sounds/falt.wav";
    private static final String WIN_IMAGE="images/win.jpg";
    private static final String LOSE_IMAGE="images/lose.jpg";
    private static final String READY_IMAGE="images/ready.png";
    private static final double BRICK_HEIGHT=20;
    private static final double BRICK_TOP=60;
    private static final double BALL_RADIUS=15;
    private static final double BALL_DAMAGE=1;
    private static final double BALL_SPEED=8;
    private static final double BONUS_BALL_SPEED=6;
    private static final int START_LIVES=3;
    private static final double EXPLOSION_RADIUS=90;
    private static final double MAX_PADDLE_WIDTH=250;
    private static final int DELAY=10;
    private static final int MENU=0;
    private static final int FIRST_LEVEL=1;
    private static final int SECOND_LEVEL=2;
    private static final int THIRD_LEVEL=3;
    private int targetsLeft=0;
    private int state=MENU;
    private boolean waitingStart=false;
    private GImage startHint;
    private GImage background;
    private GImage buttonFirstLevel;
    private GImage buttonSecondLevel;
    private GImage buttonThirdLevel;
    private Clip bouncedClip;
    private Clip winClip;
    private Clip loseClip;
    private RandomGenerator rgen=RandomGenerator.getInstance();
    public static void main(String[] args) {
        new Main().start(args);
    }
    /**
     *Початок програми
     */
    public void run(){
        this.setSize(450, 800);
        addMouseListeners();
        addKeyListeners();
        bouncedClip=loadClip(BONK_SOUND);
        winClip=loadClip(WIN_SOUND);
        loseClip=loadClip(LOSE_SOUND);
        startMusic();
        startWindow();
        while(true){
            while(state==MENU){
                pause(DELAY);
            }
            if(state==FIRST_LEVEL){
                firstLevel();
            } else if(state==SECOND_LEVEL){
                secondLevel();
            } else if(state==THIRD_LEVEL){
                thirdLevel();
            }
            startWindow();
        }
    }
    /**
     * Створює початковий скрин з візуалом та кнопками
     */
    private void startWindow(){
        removeAll();
        background();
        hello();
        buttonFirstLevel=button("images/first_level.png");
        add(buttonFirstLevel, 50, 220);
        buttonSecondLevel=button("images/second_level.png");
        add(buttonSecondLevel, 50, 280);
        buttonThirdLevel=button("images/third_level.png");
        add(buttonThirdLevel, 50, 340);
        state=MENU;
    }
    /**
     * Створює текст
     */
    private void hello(){
        GImage hello=new GImage("images/hello.png");
        hello.setSize(350, 50);
        add(hello, 50, 50);
        GImage choosing_message=new GImage("images/choosing.png");
        choosing_message.setSize(350, 50);
        add(choosing_message, 50, 110);
    }
    /**
     * Створює бекграунд
     */
    private void background(){
        background=new GImage("images/background.png");
        background.setSize(getWidth(),getHeight());
        add(background, 0, 0);
    }
    /**
     * ОБробляє всі кліки миші
     * @param e подая кліку
     */
    public void mouseClicked(MouseEvent e) {
        if(state==MENU){
            levelClick(e);
        } else if(Shop.isOpen){
            shopClick(e);
        } else if(Shop.isOpenButton(e.getX(), e.getY())){
            Shop.open(this);
        }else if(waitingStart){
            launchBall();
        }else{
            if(ObjectsRepository.Paddle!=null)ObjectsRepository.Paddle.dir*=-1;
        }
    }
    public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT){
            System.out.println("Key pressed");
            if(ObjectsRepository.Paddle!=null)ObjectsRepository.Paddle.movePaddle(1);
        }
        else if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT){
            if(ObjectsRepository.Paddle!=null)ObjectsRepository.Paddle.movePaddle(-1);
        }
    }
    public void keyReleased(KeyEvent e){
        if(ObjectsRepository.Paddle!=null)ObjectsRepository.Paddle.movePaddle(0);
    }
    /**
     *Трекає мишу для натискання по магазину
     * @param e
     */
    private void shopClick(MouseEvent e){
        int item=Shop.click(e.getX(), e.getY());
        if(item==Shop.ITEM_BALL){
            addExtraBall();
        } else if(item==Shop.ITEM_WIDE){
            widenPaddle();
        } else if(item==Shop.ITEM_BOOM){
            explosion();
        }
        Shop.bringToFront();
    }
    /**
     *Трекає мишу для вибору рівня
     * @param e
     */
    private void levelClick(MouseEvent e){
        GObject obj=getElementAt(e.getX(), e.getY());
        if(obj==buttonFirstLevel){
            state=FIRST_LEVEL;
        } else if(obj==buttonSecondLevel){
            state=SECOND_LEVEL;
        } else if(obj==buttonThirdLevel){
            state=THIRD_LEVEL;
        }
    }
    /**
     *Сторює кнопку
     * @param image картинка
     * @return обєкт
     */
    private GImage button(String image){
        GImage text=new GImage(image);
        text.setSize(350, 50);
        return text;
    }
    /**
     *Запускає рівень з відповідною лекогю складністю
     */
    private void firstLevel(){
        removeAll();
        background();
        buildBricks(9, 6, 1, 0.8, 0.1, 0.2);
        playLevel();
    }
    /**
     *Запускає рівень 2
     */
    private void secondLevel(){
        removeAll();
        background();
        buildBricks(11,7,2, 0.8, 0.1, 0.2);
        playLevel();
    }
    /**
     *Запускає рівень3
     */
    private void thirdLevel(){
        removeAll();
        background();
        buildBricks(13,8,3, 0.8, 0.1, 0.2);
        playLevel();
    }
    /**
     *Початок рівня де налаштовується сцена та створюються всі обєкти
     */
    private void playLevel(){
        GameManager.Scene=this;
        GameManager.gamePaused=false;
        ObjectsRepository.clearBalls();
        GameManager.gold = 0;
        GameManager.score = 0;
        GameManager.lives = START_LIVES;
        Paddle pad = new Paddle(225, 650);
        ObjectsRepository.Paddle = pad;
        add(pad);
        prepareStart();
        Shop.showHud(this);
        while(!GameManager.gamePaused){
            pause(DELAY);
            update();
        }
    }
    /**
     *Створює падл та перший мяч
     */
    private void addStartBall(){
        Paddle pad=ObjectsRepository.Paddle;
        Ball ball=ObjectsRepository.CreateBall(pad.getX()+pad.getWidth()/2, pad.getY()-40, BALL_RADIUS, BALL_DAMAGE, BALL_SPEED);
        if(ball!=null){
            ball.yVelocity=-1;
            add(ball);
        }
    }
    /**
     * створює картинки падл і мяч
     */
    private void prepareStart(){
        addStartBall();
        startHint=new GImage(READY_IMAGE);
        startHint.setSize(300, 80);
        add(startHint, 75, 350);
        waitingStart=true;
    }
    /**
     * Початок гри
     */
    private void launchBall(){
        waitingStart=false;
        remove(startHint);
    }
    /**
     *Показує або поразку або перемогу
     * @param imageFile Скрин
     */
    private void showMessageWinOrLose(String imageFile){
        GRect shade=new GRect(450, 800);
        shade.setColor(new Color(0, 0, 255, 128));
        shade.setFilled(true);
        add(shade, 0, 0);
        GImage message=new GImage(imageFile);
        message.setSize(300, 200);
        add(message, 75, 300);
        waitForClick();
        remove(message);
        remove(shade);
    }
    /**
     * Створює підказку що треба клікнути щоб розпочати
     * @param imageFile скрин
     */
    private void showMessage(String imageFile){
        GRect shade=new GRect(450, 800);
        shade.setColor(new Color(0, 0, 255, 128));
        shade.setFilled(true);
        add(shade, 0, 0);
        GImage message=new GImage(imageFile);
        message.setSize(300, 80);
        add(message, 75, 360);
        waitForClick();
        remove(message);
        remove(shade);
    }
    /**
     *цей метод для всіх подій, які відбуваються щомілісекунди
     */
    private void update(){
        Shop.updateHud();
        if(Shop.isOpen){
            return;
        }
        if(waitingStart){
            return;
        }
        moveBalls();
        ObjectsRepository.Paddle.movePaddle();
        checkVictory();
        if(!GameManager.gamePaused){
            checkLoss();
        }
    }
    /**
     *Перевіряє чи ще існують цілі та оголошує перемогу якщо ні
     */
    private void checkVictory() {
        if(targetsLeft<=0){
            GameManager.gamePaused = true;
            playClip(winClip);
            winEffect();
            showMessageWinOrLose(WIN_IMAGE);
        }
    }
    /**
     *Оголошує поразку якщо на екрані нема кільок і всі життя закінчились. Якщо не закінчились то мінус життя і нова кулька
     * @return
     */
    private void checkLoss(){
        if(ObjectsRepository.hasBalls()) {
            return;
        }
        GameManager.lives--;
        Shop.updateHud();
        if(GameManager.lives <= 0){
            playClip(loseClip);
            gameLostEffect();
            showMessageWinOrLose(LOSE_IMAGE);
            GameManager.gamePaused = true;
        } else{
            addStartBall();
            showMessage(READY_IMAGE);
        }
    }
    /**
     *Створює стіну цегли як дисплей поразки
     */
    private void gameLostEffect() {
        double gap=0;
        double brickWidth=(getWidth()-(6+1)*gap)/6;
        targetsLeft=0;
        int row=-10;
        while(row<50){
            int col=0;
            while(col<6){
                if(rgen.nextBoolean(1)){
                    int hits=rgen.nextInt(3, 3);
                    addBrick(row, col, brickWidth, 0, hits, 0, 0);
                    pause(1);

                }
                col=col+1;
            }
            row=row+1;
        }
        if(targetsLeft==0){
            addBrick(0,0,brickWidth,gap,1, 0, 0);
        }
    }
    /**
     * Створює ефект перемоги
     */
    private void winEffect(){
        loadClip(WIN_SOUND);
        int i=0;
        while(i<40){
            GOval dot = new GOval(10, 10);
            dot.setFilled(true);
            dot.setFillColor(rgen.nextColor());
            dot.setColor(rgen.nextColor());
            add(dot, rgen.nextDouble(0, getWidth() - 10), rgen.nextDouble(0, getHeight()-10));
            pause(40);
            i = i + 1;
        }
    }
    /**
     *Рухає кожен мяч з репозиторія якщо він існує
     */
    private void moveBalls() {
        moveOneBall(ObjectsRepository.Ball1);
        moveOneBall(ObjectsRepository.Ball2);
        moveOneBall(ObjectsRepository.Ball3);
        moveOneBall(ObjectsRepository.Ball4);
        moveOneBall(ObjectsRepository.Ball5);
        moveOneBall(ObjectsRepository.Ball6);
        moveOneBall(ObjectsRepository.Ball7);
        moveOneBall(ObjectsRepository.Ball8);
        moveOneBall(ObjectsRepository.Ball9);
        moveOneBall(ObjectsRepository.Ball10);
        moveOneBall(ObjectsRepository.Ball11);
        moveOneBall(ObjectsRepository.Ball12);
        moveOneBall(ObjectsRepository.Ball13);
        moveOneBall(ObjectsRepository.Ball14);
        moveOneBall(ObjectsRepository.Ball15);
        moveOneBall(ObjectsRepository.Ball16);
        moveOneBall(ObjectsRepository.Ball17);
        moveOneBall(ObjectsRepository.Ball18);
        moveOneBall(ObjectsRepository.Ball19);
        moveOneBall(ObjectsRepository.Ball20);
    }
    /**
     *рухає мяч якщо існує
     * @param ball
     */
    private void moveOneBall(Ball ball){
        if(ball==null){
            return;
        }
        GObject obj = getCollidingObject(ball);
        boolean bounced=ball.moveBall(getHeight(), getWidth(), obj);
        if(ball.hasFallen()){
            ObjectsRepository.RemoveBall(ball);
            return;
        }
        if(bounced){
            playClip(bouncedClip);
        }
        if(obj instanceof Brick){
            hitBrick((Brick)obj,(int) ball.damage);
        }
    }
    /**
     *Створює цеглу для рівня з шансами що: цегла буде особливою або її не буде
     * @param rows рядки
     * @param cols колонки
     * @param maxHits життя цегли максимальне для рандому
     * @param chance шанс зникнути на старті
     * @param bonusChance шанс ля цегли стати бонусною
     * @param expChance шанс стати вибухівкою
     */
    private void buildBricks(int rows, int cols, int maxHits, double chance, double bonusChance, double expChance){
        double gap=0;
        double brickWidth=(getWidth()-(cols+1)*gap)/cols;
        targetsLeft=0;
        int row=0;
        while(row<rows){
            int col=0;
            while(col<cols){
                if(rgen.nextBoolean(chance)){
                    int hits=rgen.nextInt(1, maxHits);
                    addBrick(row, col, brickWidth, gap, hits, bonusChance, expChance);
                }
                col=col+1;
            }
            row=row+1;
        }
        if(targetsLeft==0){
            addBrick(0,0,brickWidth,gap,1, 1, 1);
        }
    }
    /**
     *Створює одну цеглинку
     * @param row
     * @param col
     * @param brickWidth
     * @param gap
     * @param hits
     * @param bonusChance
     * @param expChance
     */
    private void addBrick(int row, int col, double brickWidth, double gap, int hits, double bonusChance, double expChance){
        double x=gap+col*(brickWidth+gap);
        double y=BRICK_TOP+row*(BRICK_HEIGHT+gap);
        if(rgen.nextBoolean(bonusChance)){
            add(new BonusBallBrick(x, y, brickWidth, BRICK_HEIGHT, 4));

        }
        else if(rgen.nextBoolean(expChance)){
            add(new ExplosiveBrick(x, y, brickWidth, BRICK_HEIGHT, 5));
        }
        else add(new Brick(x, y, brickWidth, BRICK_HEIGHT, hits));
        targetsLeft=targetsLeft+1;
    }
    /**
     *Повертає обєкт що дотикається до ball
     * @param ball
     * @return
     */
    private GObject getCollidingObject(GObject ball) {
        if(ball == null) return null;
        double x = ball.getX();
        double y = ball.getY();
        double d = ball.getWidth();
        GObject obj = getValidColliderAt(x, y); // top-left
        if (obj != null){
            return obj;
        }
        obj = getValidColliderAt(x + d, y);     // top-right
        if (obj != null){
            return obj;
        }
        obj = getValidColliderAt(x, y + d);     // bottom-left
        if (obj != null){
            return obj;
        }
        obj = getValidColliderAt(x + d, y + d); // bottom-right
        if (obj != null){
            return obj;
        }
        return getValidColliderAt(x+d, y+d);
    }
    /**
     *Відсює зайві обєкти
     * @param x
     * @param y
     * @return
     */
    private GObject getValidColliderAt(double x, double y) {
        GObject obj = getElementAt(x, y);
        if (obj instanceof Brick || obj instanceof Paddle){
            return obj;
        }
        return null;
    }
    /**
     *Кулька доторкнулась до цеглинки. Цеглинка зазнає пошкоджень і для різних її типів різні ефекти при смерті
     * @param brick
     * @param damage
     */
    private void hitBrick(Brick brick, int damage){
        if(brick instanceof BonusBallBrick || brick instanceof ExplosiveBrick){
            brick.hits=0;
        } else{
            brick.hits=brick.hits-damage;
        }
        if(brick.hits>0){
            brick.updateColor();
            return;
        }
        destroyBrick(brick);
    }
    /**
     *
     * @param brick
     */
    private void destroyBrick(Brick brick){
        double bx=brick.getX();
        double by=brick.getY();
        double bw=brick.getWidth();
        double bh=brick.getHeight();
        remove(brick);
        targetsLeft=targetsLeft-1;
        if(brick instanceof BonusBallBrick){
            Ball bonus=ObjectsRepository.CreateBall(bx+bw/2, by+bh, BALL_RADIUS, BALL_DAMAGE, BONUS_BALL_SPEED);
            if(bonus!=null){
                add(bonus);
            }
            GameManager.gold=GameManager.gold+30;
            GameManager.score=GameManager.score+60;
            return;
        }
        if(brick instanceof ExplosiveBrick){
            explodeNeighbours(bx+bw/2, by+bh/2, bw, bh);
        }
        GameManager.gold=GameManager.gold+20;
        GameManager.score=GameManager.score+50;
    }
    /**
     *Для різних типів цеглинки по-різному їх знищує і дає ефект
     * @param cx
     * @param cy
     * @param bw
     * @param bh
     */
    private void explodeNeighbours(double cx, double cy, double bw, double bh){
        showFlash(cx, cy, bw*1.5, 50);
        explodeAt(cx-bw, cy);
        explodeAt(cx+bw, cy);
        explodeAt(cx, cy-bh);
        explodeAt(cx, cy+bh);
        explodeAt(cx-bw, cy-bh);
        explodeAt(cx+bw, cy-bh);
        explodeAt(cx-bw, cy+bh);
        explodeAt(cx+bw, cy+bh);
    }
    /**
     *Знищує цеглу навколо
     * @param x
     * @param y
     */
    private void explodeAt(double x, double y) {
        GObject obj = getElementAt(x, y);
        if (obj instanceof Brick) {
            hitBrick((Brick) obj, 100); // High damage guarantees the surrounding brick is destroyed
        }
    }
    /**
     *Ефект вибуху з паузою
     * @param cx
     * @param cy
     * @param radius
     * @param millis пауза
     */
    private void showFlash(double cx, double cy, double radius, int millis){
        GOval flash=new GOval(cx-radius, cy-radius, radius*2, radius*2);
        flash.setFilled(true);
        flash.setFillColor(new Color(255, 165, 0, 150));
        add(flash);
        pause(millis);
        remove(flash);
    }
    /**
     *Створює додаткоу кульку
     */
    private void addExtraBall(){
        Paddle pad=ObjectsRepository.Paddle;
        Ball extra=ObjectsRepository.CreateBall(pad.getX()+pad.getWidth()/2, pad.getY()-40, 15, 1, 6);
        if(extra==null){
            Shop.refund(Shop.PRICE_BALL);
        } else{
            add(extra);
        }
    }
    /**
     *Розширює падл
     */
    private void widenPaddle(){
        Paddle pad=ObjectsRepository.Paddle;
        if(pad.getWidth()>=MAX_PADDLE_WIDTH){
            Shop.refund(Shop.PRICE_WIDE);
            return;
        }
        double newWidth= pad.getWidth()*1.3;
        if(newWidth>MAX_PADDLE_WIDTH){
            newWidth=MAX_PADDLE_WIDTH;
        }
        double newX=pad.getX()-(newWidth-pad.getWidth())/2;
        pad.setSize(newWidth, pad.getHeight());
        pad.setLocation(newX, pad.getY());
    }
    /**
     *Створює вибух
     */
    private void explosion(){
        Brick centre=randomBrick();
        if(centre==null){
            Shop.refund(Shop.PRICE_BOOM);
            return;
        }
        double cx=centre.getX()+centre.getWidth()/2;
        double cy=centre.getY()+centre.getHeight()/2;
        GOval flash=new GOval(cx-EXPLOSION_RADIUS, cy-EXPLOSION_RADIUS, EXPLOSION_RADIUS*2, EXPLOSION_RADIUS*2);
        flash.setFilled(true);
        flash.setFillColor(new Color(255, 165, 0, 150));
        add(flash);
        pause(150);
        remove(flash);
        Brick target=findBrickNear(cx, cy);
        while(target!=null){
            hitBrick(target, 100);
            target=findBrickNear(cx, cy);
        }
    }
    /**
     *Повертає рандомну цеглинку з вже існуючих
     * @return
     */
    private Brick randomBrick(){
        int count=0;
        int i=0;
        while(i<getElementCount()){
            if(getElement(i) instanceof Brick){
                count=count+1;
            }
            i=i+1;
        }
        if(count==0){
            return null;
        }
        int pick=rgen.nextInt(0, count-1);
        int number=0;
        i=0;
        while(i<getElementCount()){
            GObject obj=getElement(i);
            if(obj instanceof Brick){
                if(number==pick){
                    return (Brick) obj;
                }
                number=number+1;
            }
            i=i+1;
        }
        return null;
    }
    /**
     *Повертає найближчу цеглинку
     * @param cx
     * @param cy
     * @return
     */
    private Brick findBrickNear(double cx, double cy){
        int i=0;
        while(i<getElementCount()){
            GObject obj=getElement(i);
            if(obj instanceof Brick){
                double dx=obj.getX()+obj.getWidth()/2-cx;
                double dy=obj.getY()+obj.getHeight()/2-cy;
                if(Math.sqrt(dx*dx+dy*dy)<=EXPLOSION_RADIUS){
                    return (Brick) obj;
                }
            }
            i=i+1;
        }
        return null;
    }
    /**
     *Грає звук
     * @param fileName назва звуку
     * @return
     */
    private Clip loadClip(String fileName){
        try{
            File file=new File(fileName);
            AudioInputStream stream=AudioSystem.getAudioInputStream(file);
            Clip clip=AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch(Exception e){
            System.out.println("Не вдалося завантажити "+fileName+": "+e);
            return null;
        }
    }
    /**
     *грає звук
     * @param clip
     */
    private void playClip(Clip clip){
        if(clip==null){
            return;
        }
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
    /**
     *Грає музику
     */
    private void startMusic(){
        Clip music=loadClip(MUSIC_SOUND);
        if(music!=null){
            music.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}