package com.flybird.main;

import com.flybird.util.Constant;

import java.io.*;

/**
 * @Author 木子
 * @Date 2020/10/21
 */
/*
此类为游戏时间，计算玩家在游戏中的存活时间；
通过计算游戏时间，来提高游戏的可玩性
 */
public class GameTime {
    private static final GameTime GAME_TIME=new GameTime();
    // 悬浮障碍物出现的时间
    public static final int HOVER_OBSTACLE_TIME=15;
    // 移动障碍物出现的悬浮时间
    public static final int MOVING_OBSTACLE_TIME=20;
    //障碍物出现速度改变的最小时间
    public static final int SPEED_MIN_OBSTACLE_TIME=20;
    //障碍物出现速度改变的最大时间
    public static final int SPEED_MAX_OBSTACLE_TIME=120;

    //定义游戏的计时状态
    private int stateTime;
    public static final int STATE_READY = 0;
    public static final int STATE_RUN = 1;
    public static final int STATE_END = 2;
    //定义游戏的时间
    private long startTime, endTime;
    // 定义游戏时间历史最好的记录
    private long bestHeightTime;

    //无参构造方法(初始化为准备开始时间)
    public GameTime() {
        //小鸟刚开始的时间
        stateTime = STATE_READY;
        bestHeightTime = -1;
        try {
            loadBestHeightTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载游戏读取的最高游戏记录的方法
     *
     * @return
     */
    public void loadBestHeightTime() throws Exception {
        File file = new File(Constant.GAME_SCORE_FILE);
        if (file.exists()) {
            /*
            通过输入流来加载根文件中的信息
             */
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            //将读取到根目录的值赋值给bestHeightTime
            bestHeightTime = dis.readLong();
            //关闭资源
            dis.close();
        }
    }
    public static GameTime getInstance(){
        return GAME_TIME;
    }

    /**
     * 保存最好的记录
     *
     * @return
     */
    public void saveBestHeightTime(long time) throws Exception {
        // 先读取根目录下的游戏时间文件
        File file = new File(Constant.GAME_SCORE_FILE);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        dos.writeLong(time);
        dos.close();
    }

    //判断是否准备好计时
    public boolean isReady() {
        return stateTime == STATE_READY;
    }

    /**
     * 获取游戏的最高游戏时间
     *
     * @return
     */

    public long getBestHeightTime() {
        return bestHeightTime;
    }

    //将毫秒转化为秒,游戏的用时
    public long getGameTime() {
        return getTime() / 1000;
    }

    //获取游戏的时间
    public long getTime() {
        //计算在飞行时的时间
        if (STATE_RUN == stateTime) {
            return System.currentTimeMillis() - startTime;
        }
        return endTime - startTime;
    }

    /*
    设置开始计时和结束计时的方法
     */
    //开始计时的方法
    public void startTiming() {
        startTime = System.currentTimeMillis();
        stateTime = STATE_RUN;
    }

    //结束计时的方法
    public void endTiming() {
        endTime = System.currentTimeMillis();
        stateTime = STATE_END;
        /**
         * 当结束计时的时候，将本局游戏的时间存储起来
         * 使用if（判断）
         */
        Long time = getGameTime();
        if (bestHeightTime < time) {
            bestHeightTime = time;
            try {
                saveBestHeightTime(time);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 死亡后重置的时间
     */
    public void timeReset() {
        stateTime = STATE_READY;
        startTime = 0;
        endTime = 0;
    }
}
