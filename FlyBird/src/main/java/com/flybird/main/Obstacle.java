package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author 木子
 * @Date 2020/10/16
 */
//定义游戏中障碍物的属性，设置其坐标、移动等；
public class Obstacle {
    /**
     * 障碍物坐标(相对于游戏图层的坐标，与游戏坐标不同)
     */
    int x, y;
    /**
     * 障碍图像加载
     */
    static BufferedImage[] images;
    static final int OBSTACLE_NUMBER = 3;


    //使用静态代码块 在类加载时将其初始化 加载
    static {
        images = new BufferedImage[OBSTACLE_NUMBER];
        //将障碍物通过循环取出
        for (int i = 0; i < OBSTACLE_NUMBER; i++) {
            images[i] = GameUtil.loadBufferedImages(Constant.OBSTACLE_IMG_PATH[i]);
        }
    }

    // 障碍物的宽高
    int height, width;
    /**
     * 定义障碍物移动类型
     */
    int typer;
    static final int TYPER_TOP_NORMAL = 0;
    static final int TYPER_TOP_INDENT = 1;
    static final int TYPER_BOTTOM_NORMAL = 2;
    static final int TYPER_BOTTOM_INDENT = 3;
    static final int TYPER_HOVER_NORMAL = 4;
    static final int TYPER_HOVER_DROP = 5;
    // 定义障碍物移动的速度(每个障碍物都有自己的速度)
    int speed;

    // 获取中间障碍物的宽高
    static final int OBSTACLE_WIDTH = images[0].getWidth();
    static final int OBSTACLE_HEIGHT = images[0].getHeight();
    // 获取头部障碍物宽高
    static final int OBSTACLE_HEAD_WIDTH = images[1].getWidth();
    static final int OBSTACLE_HEAD_HEIGHT = images[1].getHeight();

    //定义障碍物是否可见，当其移动出图层时，就将其收回
    boolean isVisible;
    //定义障碍物的矩形
    Rectangle rect;

    //创建无参的构造方法
    public Obstacle() {
        //添加障碍物移动的时间
        if (GameTime.getInstance().getGameTime() > GameTime.SPEED_MIN_OBSTACLE_TIME) {
            this.speed = Constant.OBSTACLE_MAX_SPEED + 5;
        } else if (GameTime.getInstance().getGameTime() > GameTime.SPEED_MAX_OBSTACLE_TIME) {
            this.speed = Constant.OBSTACLE_MAX_SPEED + 3;
        } else {
            this.speed = Constant.OBSTACLE_MAX_SPEED;
        }

        this.width = images[0].getWidth();
        //在初始方法中创建矩形
        rect = new Rectangle();
        //定义矩形的宽度
        rect.width = images[0].getWidth() - 2;
    }

    //设置各个属性的get set 方法
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTyper(int typer) {
        this.typer = typer;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }


    //设置障碍物属性
    public void setAttribute(int x, int y, int height, int typer, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.typer = typer;
        this.isVisible = isVisible;
        setRectangle(x, y, height);
    }


    public Rectangle getRect() {
        return rect;
    }


    /**
     * 障碍物绘制
     * 通过类型判断障碍物出现的种类
     */

    public void draw(Graphics g, Bird bird) {
        //将障碍物绘制在屏幕上
        /*g.drawImage();*/
        switch (typer) {
            case TYPER_TOP_NORMAL:
                drawTopNormal(g);
                break;
            case TYPER_BOTTOM_NORMAL:
                drawBottomNormal(g);
                break;
            case TYPER_HOVER_NORMAL:
                drawCenterNormal(g);
                break;
        }
        //绘制出测试的矩形

        //g.setColor(Color.red);
        // g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        if (bird.isDieLand()) {
            return;
            /**
             * 判断小鸟是否死亡；
             * 死亡后小鸟不在继续移动
             */
        }
        obstacleMoveLogic();
    }


    /**
     * 判断是否是最后一个障碍物在屏幕中
     */
    public boolean isInFrame() {
        return x + OBSTACLE_WIDTH < Constant.FRAMR_WITH;
    }

    public int getX() {
        return x;
    }

    //描述障碍物的移动逻辑
    private void obstacleMoveLogic() {
        x -= speed;
        rect.x -= speed;
        //障碍物移出了屏幕归还的条件
        if (x < -OBSTACLE_WIDTH) {
            isVisible = false;
        }

    }


    /**
     * 绘制顶端的障碍物
     */
    public void drawTopNormal(Graphics g) {
        //顶端上榜部分的拼接个数
        int topCount = (height - OBSTACLE_HEAD_HEIGHT) / OBSTACLE_HEIGHT + 1;
        for (int i = 0; i < topCount; i++) {
            g.drawImage(images[0], x, y + i * OBSTACLE_HEIGHT, null);
        }
        //绘制最后一块底部
        int y = this.y + height - OBSTACLE_HEAD_HEIGHT;
        g.drawImage(images[1], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y, null);
    }

    /**
     * 绘制中间悬浮的普通块
     */
    public void drawCenterNormal(Graphics g) {
        //绘制悬浮的顶端
        g.drawImage(images[2], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y, null);
        //利用循环绘制中间的管道(+1使其多出部分，拿下部分遮挡)
        int centerCount = (height - OBSTACLE_HEAD_HEIGHT * 2) / OBSTACLE_HEIGHT + 1;
        for (int i = 0; i < centerCount; i++) {
            g.drawImage(images[0], x, y + i * OBSTACLE_HEIGHT + OBSTACLE_HEAD_HEIGHT, null);
        }
        //绘制悬浮底端
        int y = this.y + height - OBSTACLE_HEAD_HEIGHT;
        g.drawImage(images[1], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y, null);
        System.out.println("终端");

    }

    /**
     * 绘制底端障碍物
     */
    public void drawBottomNormal(Graphics g) {
        //底端障碍物的拼接个数 让其冲出屏幕
        int bottomCount = (height - OBSTACLE_HEAD_HEIGHT) / OBSTACLE_HEIGHT + 1;
        for (int i = 0; i < bottomCount; i++) {
            g.drawImage(images[0], x, y + i * OBSTACLE_HEIGHT + OBSTACLE_HEAD_HEIGHT, null);
        }
        //绘制最后顶端
        g.drawImage(images[2], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y, null);
    }


    /**
     * 设置矩形障碍物的属性，x,y,高度，类型
     */
    public void setRectangle(int x, int y, int height) {
        rect.x = x;
        rect.y = y;
        rect.height = height;

    }
}
