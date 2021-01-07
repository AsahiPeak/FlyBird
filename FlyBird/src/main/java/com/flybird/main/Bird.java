package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.GameUtil;
import com.flybird.util.MusicUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author 木子
 * @Date 2020/10/12
 */


public class Bird {
    /**
     * 所有的小鸟类的功能都在此实现；
     * BufferedImage为Image的子类
     */
    //图片的张数
    public static final int IMG_COUNT = 4;
    // 定义图像类
    private BufferedImage[] images;


    // 定义小鸟的状态
    private int state;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_UP = 1;
    public static final int STATE_DOWN = 2;
    public static final int STATE_DIES = 3;
    public static final int STATE_DIE_LAND = 4;

    //定义小鸟在游戏屏幕中的坐标--->起始的坐标，游戏中的坐标
    private int x, y;
    // 定义小鸟的加速度
    private int changeY;
    //定义小鸟的矩形
    private Rectangle rect;
    //定义时间的类，根据鸟的状态改变
    private GameTime timing;
    // 定义游戏结束时的闪烁画面
    private int flash;
    // 定义游戏结束时的资源
    private BufferedImage gameOver;
    // 定义游戏结束时的计分牌的画面
    private BufferedImage gameScore;
    // 定义游戏结束时重新开启的按钮
    private BufferedImage gameReset;

    // 在构造方法中，对资源进行初始化；
    public Bird() {
        //初始化游戏时间
        timing = GameTime.getInstance();
        //实例化图像
        images = new BufferedImage[IMG_COUNT];
        //通过循环实现
        for (int i = 0; i < IMG_COUNT; i++) {
            //通过工具类加载图像
            images[i] = GameUtil.loadBufferedImages(Constant.BIRDS_IMG_PATH[i]);
        }
        //小鸟初始化坐标
        x = Constant.FRAMR_WITH >> 1;
        y = Constant.FRAMR_HEIGHT >> 1;
        //定义矩形的x，y坐标；
        int ImageW = images[state].getWidth();
        int ImageH = images[state].getHeight();
        int x = this.x - ImageW / 2;
        int y = this.y - ImageH / 2;
        //初始化定义矩形的坐标
        rect = new Rectangle(x + Constant.RECT_GAP, y + Constant.RECT_GAP, ImageW - Constant.RECT_GAP * 2, ImageH - Constant.RECT_GAP * 2);


    }

    public Rectangle getRect() {
        return rect;
    }

    // 绘制小鸟在游戏屏幕中的显示
    public void draw(Graphics g) {
        //调用飞行逻辑
        flyLogic();
        //获取小鸟图片的长和宽（利用位移）；
        int halfImgW = images[state > STATE_DIES ? STATE_DIES : state].getWidth() >> 1;
        int halfImgH = images[state > STATE_DIES ? STATE_DIES : state].getHeight() >> 1;
        //绘制小鸟的中心位置
        g.drawImage(images[state > STATE_DIES ? STATE_DIES : state], x - halfImgW, y - halfImgH, null);
        //绘制小鸟的碰撞区域，做测试
        /**g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());*/
        /**
         * 当小鸟发生碰撞死亡时，弹出游戏结束和记分牌
         */
        if (state == STATE_DIES) {
            drawGameOver(g);
        } else {
            //绘制小鸟游戏时间的显示时常
            drawGameTime(g);
        }
    }

    /**
     * 绘制小鸟碰撞时的游戏结束界面
     * 将GameOver设置为闪烁
     *
     * @param g
     */
    public void drawGameOver(Graphics g) {
        /**
         * 绘制游戏碰撞结束时的画面
         */
        if (flash++ > 3) {
            int x = Constant.FRAMR_WITH - gameOver.getWidth() >> 1;
            int y = Constant.FRAMR_HEIGHT / 4;
            g.drawImage(gameOver, x, y, null);
            if (flash == 6) {
                flash = 0;
            }
        }

        /**
         * 绘制游戏的记分牌
         */

        int x = Constant.FRAMR_WITH - gameScore.getWidth() >> 1;
        /**
         * 原先的设置：int y = gameScore.getHeight() * 3 / 2;
         */
        int y = gameScore.getHeight() * 4 / 2;
        g.drawImage(gameScore, x, y, null);
        /**
         *绘制游戏结束，记分牌的时间
         */
        //x,y的起点
        x = Constant.FRAMR_WITH - gameScore.getWidth() / 2 >> 1;
        y += gameScore.getHeight() >> 1;
        g.setColor(Color.BLUE);
        g.setFont(Constant.TIME_FONT);
        String str = Long.toString(timing.getGameTime());
        x -= GameUtil.getStringWidth(Constant.TIME_FONT, str) >> 1;
        y += GameUtil.getStringHeight(Constant.TIME_FONT, str);
        g.drawString(str, x, y);
        /**
         * 绘制游戏的最好成绩
         * 判断的getBestHeightTime值大于0，我们绘制
         */
        if (timing.getBestHeightTime() > 0) {
            str = Long.toString(timing.getBestHeightTime());
            x += gameScore.getWidth() >> 1;
            g.drawString(str, x, y);
        }
        //绘制游戏结束时的重玩界面
        x = Constant.FRAMR_WITH - gameReset.getWidth() >> 1;
        y = Constant.FRAMR_HEIGHT - 185;
        g.drawImage(gameReset, x, y, null);
    }

    /**
     * 绘制小鸟游戏时间的显示时常
     */
    public void drawGameTime(Graphics g) {

        /**绘制游戏的时间显示*/
        g.setColor(Color.black);
        g.setFont(Constant.TIME_FONT);
        String str = Long.toString(timing.getGameTime());
        int x = Constant.FRAMR_WITH - GameUtil.getStringWidth(Constant.TIME_FONT, str) >> 1;
        g.drawString(str, x, 65);
    }

    /**
     * 改变小鸟在窗口的飞行状态
     */
    public void flyup() {
        MusicUtil.playFly();
        //向上飞行
        //如果的小鸟的状态是死亡或是不是向上时
        if (state == STATE_DIES || state == STATE_UP || state == STATE_DIE_LAND) {
            return;
        }
        state = STATE_UP;
        changeY = 1;

    }

    public void startTime() {
        //当按下空格是开始计时
        if (timing.isReady()) {
            timing.startTiming();
        }
    }

    /**
     * 向下飞行
     */
    public void flydown() {
        //如果小鸟的向下飞时，状态是死亡或是不是向下飞
        if (state == STATE_DIES || state == STATE_DOWN || state == STATE_DIE_LAND) {
            return;
        }

        state = STATE_DOWN;
        changeY = 3;
    }


    public void rest() {
        //重置小鸟的位置 死亡时,游戏重置
        state = STATE_NORMAL;
        changeY = 0;
        x = Constant.FRAMR_WITH >> 1;
        y = Constant.FRAMR_HEIGHT >> 1;
        //矩形死亡的重置
        rect.height = images[0].getHeight();
        int ImageH = images[state].getHeight();
        rect.y = this.y - ImageH / 2 + Constant.RECT_GAP;
        timing.timeReset();
        flash = 0;
        MusicUtil.playGameStart();
    }

    //小鸟碰撞后的死亡
    public void die() {
        MusicUtil.playCrash();
        state = STATE_DIES;
        //小鸟死亡后，结束计时
        timing.endTiming();
        //利用懒加载，来实现图片的加载
        if (gameOver == null) {
            //游戏结束
            gameOver = GameUtil.loadBufferedImages(Constant.GAME_OVER);
            // 游戏结束的记分牌
            gameScore = GameUtil.loadBufferedImages(Constant.GAME_SCORE);
            // 游戏结束的重玩按钮
            gameReset = GameUtil.loadBufferedImages(Constant.GAME_RESET);
        }
    }

    /**
     * 落在地上的死亡
     */
    public void died() {
        state = STATE_DIES;
        GameFrame.setReadyState(GameFrame.GAME_OVER);
    }


    //判断小鸟是否死亡
    public boolean isDieLand() {
        return state == STATE_DIES || state == STATE_DIE_LAND;
    }

    // 定义小鸟飞行的逻辑（上升和下降）
    public void flyLogic() {
        //使用switch来实现
        switch (state) {
            case STATE_NORMAL:
                break;
            case STATE_UP:
                //小鸟向上飞行
                changeY++;
                if (changeY > Constant.MAX_UP_CHANGEY) {
                    changeY = Constant.MAX_UP_CHANGEY;
                }
                y -= changeY;
                rect.y -= changeY;
                //小鸟向上飞行死亡
                if (y < Constant.FRAME_TOP_HIGHT + (images[state].getHeight() >> 1)) {
                    die();
                }
                break;
            case STATE_DOWN:
                //小鸟向下落体
                changeY++;
                if (changeY > Constant.MAX_DOWN_CHANGEY) {
                    changeY = Constant.MAX_DOWN_CHANGEY;
                }
                y += changeY;
                rect.y += changeY;
                //小鸟向下飞行死亡
                if (y > Constant.FRAMR_HEIGHT - (images[state].getHeight() >> 1)) {
                    die();
                }
                break;
            case STATE_DIES:
                changeY += 3;
                if (changeY > Constant.MAX_DOWN_CHANGEY) {
                    changeY = Constant.MAX_DOWN_CHANGEY;
                }
                y += changeY;
                if (y > Constant.FRAMR_HEIGHT - (images[state].getHeight() >> 1)) {
                    y = Constant.FRAMR_HEIGHT - (images[state].getHeight() >> 1);
                    died();
                }
                break;
            case STATE_DIE_LAND:
                break;

        }
    }


}
