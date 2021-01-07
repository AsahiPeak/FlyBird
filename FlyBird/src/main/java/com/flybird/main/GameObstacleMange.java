package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.GameUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 木子
 * @Date 2020/10/17
 */
/*
 主要是关于游戏中障碍物的管理
 */
public class GameObstacleMange {
    // 将障碍物类放置
    private List<Obstacle> obstacles;


    public GameObstacleMange() {
        obstacles = new ArrayList<Obstacle>();
    }

    // 通过循环将Obstacle中的图画取出
    public void draw(Graphics g, Bird bird) {
        for (int i = 0; i < obstacles.size(); i++) {
            //通过循环取出的障碍 看是否是可见的，如果不可见就归还
            Obstacle obstacle = obstacles.get(i);
            if (obstacle.isVisible) {
                obstacle.draw(g, bird);
            } else {
                //不可见的话就将其移除并归还
                Obstacle remove = obstacles.remove(i);
                GameObstaclePool.giveBack(remove);
                i--;
            }
            //碰撞检测  判断是否发生了碰撞
            collisionDetection(bird);
        }
        obstacleLogic(bird);
    }

    /**
     * 添加障碍物的碰撞层的逻辑;
     * 当第一个障碍物完全的显示在屏幕上，在添加第二个给
     */
    private void obstacleLogic(Bird bird) {
        //当鸟发生碰撞，障碍物不在添加
        if (bird.isDieLand()) {
            return;
        }
        if (obstacles.size() == 0) {
            //游戏刚开始的时候
            int obstacleHeight = GameUtil.getRandomNumber(Constant.TYPER_MIN_SPACE, Constant.TYPER_MAX_SPACE + 1);
            int typer = Obstacle.TYPER_TOP_NORMAL;
            Obstacle top = GameObstaclePool.get("Obstacle");
            top.setAttribute(Constant.FRAMR_WITH, 0, obstacleHeight, typer, true);
            obstacles.add(top);
            Obstacle bottom = GameObstaclePool.get("Obstacle");
            typer = Obstacle.TYPER_BOTTOM_NORMAL;
            bottom.setAttribute(Constant.FRAMR_WITH, obstacleHeight + Constant.TYPER_TOPANDBOTTOM_SPACE,
                    Constant.FRAMR_HEIGHT - Constant.TYPER_TOPANDBOTTOM_SPACE - obstacleHeight,
                    typer, true);
            obstacles.add(bottom);
        } else {
            //判断最后一个障碍物是否完全进入窗口中
            //取得最后一个障碍物
            Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
            if (lastObstacle.isInFrame()) {
                // 添加悬浮的障碍物判断其出现的时间
                if (GameTime.getInstance().getGameTime() < GameTime.HOVER_OBSTACLE_TIME) {
                    addNormalObstacle(lastObstacle);
                } else if (GameTime.getInstance().getGameTime() < GameTime.MOVING_OBSTACLE_TIME) {
                    try {
                        if (GameUtil.appearProbability(1, 2)) {
                            addCenterObstacle(lastObstacle);
                        } else {
                            addNormalObstacle(lastObstacle);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        int num = GameUtil.getRandomNumber(0, 3);
                        switch (num) {
                            case 0:
                                addNormalObstacle(lastObstacle);
                                break;
                            case 1:
                                addCenterObstacle(lastObstacle);
                            case 2:
                                addMovingObstacle(lastObstacle);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 添加正常的障碍物
     */
    private void addNormalObstacle(Obstacle lastObstacle) {
        int obstacleHeight = GameUtil.getRandomNumber(Constant.TYPER_MIN_SPACE, Constant.TYPER_MAX_SPACE);
        int typer = Obstacle.TYPER_TOP_NORMAL;
        int x = lastObstacle.getX() + Constant.TYPER_LEFTANDRIGHT_SPACE;
        Obstacle top = GameObstaclePool.get("Obstacle");
        top.setAttribute(x, 0, obstacleHeight, typer, true);

        typer = Obstacle.TYPER_BOTTOM_NORMAL;
        Obstacle bottom = GameObstaclePool.get("Obstacle");
        bottom.setAttribute(x, obstacleHeight + Constant.TYPER_TOPANDBOTTOM_SPACE,
                Constant.FRAMR_HEIGHT - Constant.TYPER_TOPANDBOTTOM_SPACE - obstacleHeight,
                typer, true);
        obstacles.add(top);
        obstacles.add(bottom);
    }

    /**
     * @param lastObstacle 添加悬浮移动的障碍物；
     */
    private void addMovingObstacle(Obstacle lastObstacle) {
        // 障碍物出现在屏幕中的位置(1/4,1/3)
        int obstacleHeight = GameUtil.getRandomNumber(Constant.TYPER_MIN_SPACE, Constant.TYPER_MAX_SPACE + 1);
        int typer = Obstacle.TYPER_TOP_INDENT;
        int x = lastObstacle.getX() + Constant.TYPER_LEFTANDRIGHT_SPACE;
        Obstacle top = GameObstaclePool.get("MovingObstacle");
        top.setAttribute(x, 0, obstacleHeight, typer, true);
        typer = Obstacle.TYPER_BOTTOM_INDENT;
        Obstacle bottom = GameObstaclePool.get("MovingObstacle");
        bottom.setAttribute(x, obstacleHeight + Constant.TYPER_TOPANDBOTTOM_SPACE,
                Constant.FRAMR_HEIGHT - Constant.TYPER_TOPANDBOTTOM_SPACE - obstacleHeight,
                typer, true);
        obstacles.add(top);
        obstacles.add(bottom);
    }

    /**
     * @param lastObstacle
     * @return
     */
    public void addCenterObstacle(Obstacle lastObstacle) {
        // 障碍物出现在屏幕中的位置(1/4,1/3)
        int obstacleHeight = GameUtil.getRandomNumber(Constant.FRAMR_HEIGHT >> 1, Constant.FRAMR_HEIGHT / 3);
        int typer = Obstacle.TYPER_HOVER_NORMAL;
        try {
            typer = GameUtil.appearProbability(1, 2) ? Obstacle.TYPER_HOVER_DROP : Obstacle.TYPER_BOTTOM_NORMAL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        int x = lastObstacle.getX() + Constant.TYPER_LEFTANDRIGHT_SPACE;
        int y = GameUtil.getRandomNumber(Constant.FRAMR_HEIGHT / 3, Constant.FRAMR_HEIGHT * 3 >> 3);
        Obstacle obstacle = GameObstaclePool.get(typer == Obstacle.TYPER_HOVER_NORMAL ? "Obstacle" : "MovingObstacle");
        obstacle.setAttribute(x, y, typer, obstacleHeight, true);
        obstacles.add(obstacle);
    }

    /**
     * 判断小鸟和障碍物是否发生碰撞，实现碰撞检测的方法
     * 将发生碰撞的主物体传如方法
     * .intersects()方法为检测两者的矩形是否相交，为JDK的内置方法；
     */
    public boolean collisionDetection(Bird bird) {
        //判断碰撞之后不在添加
        if (bird.isDieLand()) {
            return false;
        }
        //通过循环遍历每一个障碍物
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            if (obstacle.getRect().intersects(bird.getRect())) {
                /**
                 * 测试信息---关于小鸟和障碍物碰撞；
                 */
                //System.out.println("我们相遇了");
                bird.die();
                return true;
            }
        }
        return false;
    }

    /**
     * 将障碍物的清理并归还
     */
    public void reset() {
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            GameObstaclePool.giveBack(obstacle);
        }
        obstacles.clear();
    }

}
