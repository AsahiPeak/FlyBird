package com.flybird.main;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 木子
 * @Date 2020/10/19
 */
/*
障碍物对象池，将屏幕中的障碍物添加到障碍物池中；
在使用的时候，将其取出，当其移除屏幕，将其收回
对象池的主要作用：防止堆内存溢出，减少了创建新对象和添加对象的内存消耗；
提高了对于程序运行时的效率
 */
public class GameObstaclePool {
    //先将障碍物放入池中
    private static List<Obstacle> obstaclesPool = new ArrayList<Obstacle>();
    //用来存放悬浮的障碍物
    private static List<MovingObstacle> Pool = new ArrayList<MovingObstacle>();
    //障碍物池中的最小数量
    private static final int MIN_OBSTACLE_POOL = 16;
    //障碍物连接池中的最大连接数
    private static final int MAX_OBSTACLR_POOL = 20;

    //初始化池中的对象
    static {
        for (int i = 0; i < MIN_OBSTACLE_POOL; i++) {
            //当对象池的障碍物数量不够时，向池中添加新的
            obstaclesPool.add(new Obstacle());
        }
        for (int i = 0; i < MIN_OBSTACLE_POOL; i++) {
            //当对象池的障碍物数量不够时，向池中添加新的
            Pool.add(new MovingObstacle());
        }

    }

    /*
        从数据池中取出数据
         */
    public static Obstacle get(String className) {
        if ("Obstacle".equals(className)) {
            int size = obstaclesPool.size();
            if (size > 0) {
                //移除并方会最后一个
                return obstaclesPool.remove(size - 1);
            } else {
                //对象池中没有对象
                return new Obstacle();
            }
        } else {
            int size = Pool.size();
            if (size > 0) {
                //移除并方会最后一个
                return Pool.remove(size - 1);
            } else {
                //对象池中没有对象
                return new MovingObstacle();
            }

        }

    }

    /*
    将用完的障碍物归还给对象池
     */
    public static void giveBack(Obstacle obstacle) {
        //当对象池未满时
        if (obstacle.getClass() == Obstacle.class) {
            if (obstaclesPool.size() < MAX_OBSTACLR_POOL) {
                obstaclesPool.add(obstacle);
            } else {
                System.out.println("对象池满了");
            }
        } else {
            if (Pool.size() < MAX_OBSTACLR_POOL) {
                Pool.add((MovingObstacle) obstacle);
            } else {
                System.out.println("对象池满了");
            }
        }

    }


}
