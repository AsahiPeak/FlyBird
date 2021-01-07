package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 木子
 * @Date 2020/10/13
 */
/*
  游戏的前景类，可以设置多个云彩的出现

 */
public class GameFrontGround {
    //    通过数组存放图片资源
    private List<Cloud> clouds;
    //    图片的个数
    public static final int CLOUD_NUMBER = 2;
    //    使用到的图片资源
    private BufferedImage[] cloudImgs;
    //    定义时间的变量，每100ms的刷新时间
    private long time;
    //    定义云的方向
    private int CloudDir;

    //无参构造方法中实现实例化
    public GameFrontGround() {
        clouds = new ArrayList<Cloud>();
        cloudImgs = new BufferedImage[CLOUD_NUMBER];
        // 通过循环将图片取出
        for (int i = 0; i < CLOUD_NUMBER; i++) {
            // 加载工具类实现
            cloudImgs[i] = GameUtil.loadBufferedImages(Constant.CLOUDS_IMG_PATH[i]);
        }
        CloudDir = Cloud.DIR_RIGHT;
        // 获取当前的系统时间
        time = System.currentTimeMillis();
    }

    // 将绘制云彩
    public void draw(Graphics g) {
        logic();
        for (int i = 0; i < clouds.size(); i++) {
            // 获取云彩图片将其绘制
            clouds.get(i).draw(g);

        }
    }

    // logic方法是对云彩个数和方向 坐标的控制
    private void logic() {
        //对云彩移动的逻辑运算
        if (System.currentTimeMillis() - time > Constant.CLOUD_INTERVAL) {
            // 做一次逻辑运算（重新刷新时间）
            time = System.currentTimeMillis();
            // 当屏幕中的云彩的数量小于最大的云彩数
            if (clouds.size() < Constant.MAX_CLOUD_NUMBER) {
                // 使用随机概率添加云彩的出现数量
                if (GameUtil.cloudPercent(Constant.CLOUD_SHOW_PERCENT)) {
                    // 云彩出现的图片
                    int cloudNumber = GameUtil.getRandomNumber(0, CLOUD_NUMBER);
                    //根据云的方向，定义云移动的方向
                    int x;
                    if (CloudDir == Cloud.DIR_RIGHT) {
                        x = -cloudImgs[cloudNumber].getWidth();
                    } else {
                        x = Constant.FRAMR_WITH;
                    }

                    int y = GameUtil.getRandomNumber(Constant.FRAME_TOP_HIGHT, Constant.FRAMR_HEIGHT / 3);
                    Cloud cloud = new Cloud(cloudImgs[cloudNumber], Constant.CLOUD_SPEED, CloudDir, x, y);
                    clouds.add(cloud);
                }
            }
            // 云彩离开屏幕的处理
            for (int i = 0; i < clouds.size(); i++) {
                //获取list容器中的云
                Cloud cloud = clouds.get(i);
                if (cloud.OutFrame()) {
                    clouds.remove(i);
                    i--;
                }
            }
            //改变云彩的运动方向
            try {
                if (GameUtil.appearProbability(Constant.PROBABILITY_NUMERATOR, Constant.PROBABILITY_DENOMINATOR)) {
                    // 云彩新的运动方向
                    int newCloudDir = GameUtil.getRandomNumber(Cloud.DIR_STOP, Cloud.DIR_RIGHT + 1);
                    if (newCloudDir != CloudDir) {
                        //将新的方向赋值给云的
                        CloudDir = newCloudDir;
                        for (int i = 0; i < clouds.size(); i++) {
                            Cloud cloud = clouds.get(i);
                            //重新进行方向的改变
                            cloud.setDir(newCloudDir);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
