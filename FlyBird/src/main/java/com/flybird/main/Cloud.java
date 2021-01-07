package com.flybird.main;

import com.flybird.util.Constant;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author 木子
 * @Date 2020/10/13
 */
/*
将云彩的类进行定义，只包括一片云彩的；
 定义云彩的属性（漂移的--->方向、速度、以及其出现的坐标）；
 */
public class Cloud {
    // 将云彩图片的资源加载进来
    private BufferedImage img;
    //  移动的速度；
    private int speed;
    //  移动的方向
    private int dir;
    public static final int DIR_STOP = 0;
    public static final int DIR_LEFT = 1;
    public static final int DIR_RIGHT = 2;
    // 云彩的坐标
    private int x, y;
    // 云彩的缩放(随机的放大1-2倍)
    private double Scale;
    // 缩放后云彩的大小
    private int scaleCloudWith;
    private int scaleCloudHeight;

    // 云彩类的有参构造方法
    public Cloud(BufferedImage img, int speed, int dir, int x, int y) {
        this.img = img;
        this.speed = speed;
        this.dir = dir;
        this.x = x;
        this.y = y;
        // 获取云彩的随机倍数
        int Scale = (int) (Math.random() + 1);
        //缩放后云彩的宽度
        scaleCloudWith = Scale * img.getWidth();
        //缩放后云彩的高度
        scaleCloudHeight = Scale * img.getHeight();
    }

    // 将云绘制出来
    public void draw(Graphics g) {
//        三目运算，通过云的方向，对速度做出改变
        int speed = this.speed;
        if (dir == DIR_STOP) {
            speed = 0;
        }
        x = (dir == DIR_LEFT) ? x - speed : x + speed;
        g.drawImage(img, x, y, scaleCloudWith, scaleCloudHeight, null);
    }

    //重新定义云彩的方向
    public void setDir(int dir) {
        this.dir = dir;
    }

    /*
        如果云彩移除屏幕外的计算；计算一朵云通过循环改变其他，
        当云彩飘出时，return --->true,否则 return --->false;
         */
    public boolean OutFrame() {
        if (dir == DIR_LEFT) {
            if (x < -img.getWidth()) {
                return true;
            }
        } else if (dir == DIR_RIGHT) {
            if (x > Constant.FRAMR_WITH) {
                return true;
            }
        }
        return false;
    }
}
