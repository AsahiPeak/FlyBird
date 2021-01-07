package com.flybird.main;

/**
 * *******时刻保持学习之心******
 *
 * @author 木子
 * @package IntelliJ IDEA
 * @date 2021/1/4
 * *******方能成就不世功业******
 */

import java.awt.*;

/**
 * 移动和悬浮的障碍物，他将继承障碍物的的类；
 * 实现父类方法的重写；
 */
public class MovingObstacle extends Obstacle {
    public MovingObstacle() {
        super();
    }

    /**
     * 定义移动障碍物的坐标
     */
    private int dealtY;
    //定义最大的改变方向值
    public static final int MAX_DEALTY = 55;
    //定义障碍物是否向下移动
    int dir;
    private static final int DIR_UP = 0;
    private static final int DIR_DOWN = 1;


    /**
     * 障碍物绘制
     */
    @Override
    public void draw(Graphics g, Bird bird) {
        //将障碍物绘制在屏幕上
        //g.drawImage();
        switch (typer) {
            case TYPER_HOVER_DROP:
                drawCenterMoving(g);
                break;
            case TYPER_BOTTOM_INDENT:
                drawBottomMoving(g);
                break;
            case TYPER_TOP_INDENT:
                drawTopMoving(g);
                break;
        }
        //鸟死亡之后障碍物不在移动
        if (bird.isDieLand()) {
            return;
        }
        obstacleMoveLogic();
    }

    /**
     * 绘制顶端的障碍物
     */
    public void drawTopMoving(Graphics g) {
        //顶端上榜部分的拼接个数
        int topCount = (height - OBSTACLE_HEAD_HEIGHT) / OBSTACLE_HEIGHT + 1;
        for (int i = 0; i < topCount; i++) {
            g.drawImage(images[0], x, y + dealtY + i * OBSTACLE_HEIGHT, null);
        }
        //绘制最后一块底部
        int y = this.y + height - OBSTACLE_HEAD_HEIGHT;
        g.drawImage(images[1], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y + dealtY, null);
    }

    /**
     * 绘制底端障碍物
     */
    public void drawBottomMoving(Graphics g) {
        //底端障碍物的拼接个数 让其冲出屏幕
        int bottomCount = (height - OBSTACLE_HEAD_HEIGHT) / OBSTACLE_HEIGHT + 1;
        for (int i = 0; i < bottomCount; i++) {
            g.drawImage(images[0], x, y + dealtY + i * OBSTACLE_HEIGHT + OBSTACLE_HEAD_HEIGHT, null);
        }
        //绘制最后顶端
        g.drawImage(images[2], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y + dealtY, null);
    }

    /**
     * 绘制中间悬浮的普通块
     */
    public void drawCenterMoving(Graphics g) {
        //绘制悬浮的顶端
        g.drawImage(images[2], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y + dealtY, null);
        //利用循环绘制中间的管道(+1使其多出部分，拿下部分遮挡)
        int centerCount = (height - OBSTACLE_HEAD_HEIGHT * 2) / OBSTACLE_HEIGHT + 1;
        for (int i = 0; i < centerCount; i++) {
            g.drawImage(images[0], x, y + dealtY + i * OBSTACLE_HEIGHT + OBSTACLE_HEAD_HEIGHT, null);
        }
        //绘制悬浮底端
        int y = this.y + height - OBSTACLE_HEAD_HEIGHT;
        g.drawImage(images[1], x - (OBSTACLE_HEAD_WIDTH - OBSTACLE_WIDTH >> 1), y + dealtY, null);
        System.out.println("终端");
    }

    //描述障碍物的移动逻辑
    public void obstacleMoveLogic() {
        x -= speed;
        rect.x -= speed;
        //障碍物移出了屏幕归还的条件
        if (x < -OBSTACLE_WIDTH) {
            isVisible = false;
        }
        if (typer == TYPER_TOP_INDENT) {
            if (dir == DIR_DOWN) {
                dealtY++;
                if (dealtY > 0) {
                    dir = DIR_UP;
                }
            } else {
                dealtY--;
                if (dealtY < -MAX_DEALTY) {
                    dir = DIR_DOWN;

                }
            }
        } else {
            if (dir == DIR_DOWN) {
                dealtY++;
                if (dealtY > MAX_DEALTY) {
                    dir = DIR_UP;
                }
            } else {
                dealtY--;
                if (dealtY <= 0) {
                    dir = DIR_DOWN;
                }
            }

        }
        rect.y = this.y + dealtY;

    }


    /**
     * 所有属性的集合
     */
    @Override
    public void setAttribute(int x, int y, int height, int typer, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.typer = typer;
        this.isVisible = isVisible;
        setRectangle(x, y, height);
        // 悬浮的初始坐标
        dealtY = 0;
        // 定义悬浮时候的默认移动方向
        dir = DIR_DOWN;
        if (typer == TYPER_TOP_INDENT) {
            dir = DIR_UP;

        }

    }
}
