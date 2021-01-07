package com.flybird.util;

import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author 木子
 * @Date 2020/10/11
 */


public class GameUtil {
    /**
     * 游戏工具类，游戏中用到的方法都在此定义
     * 私有构造方法，其他类不能实例化
     */
    private GameUtil() {
    }

    /**
     * 装载图片
     *
     * @param imgPath 图片路径名
     * @return
     */
    public static BufferedImage loadBufferedImages(String imgPath) {
        try {
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算云彩随机出现在屏幕上的事件
     *
     * @param number 其取值范围时[1,100]
     * @return 为Boolean值，当事件发生时true，否则为false
     */
    public static boolean cloudPercent(final int number) {
        return getRandomNumber(1, 101) <= number;

    }

    /**
     * 求任意概率存在的情况，事件是否发生
     * numerator:为分子，必须大于0；
     * denominator为分母，必须大于0；
     * 如果发生则return true否则  false;
     */
    public static boolean appearProbability(int numerator, int denominator) throws Exception {
        if (numerator <= 0 || denominator <= 0) {
            throw new Exception("输入的数值不合法");

        }
        // 事件一定发生
        if (numerator >= denominator) {
            return true;
        }
        // 当求适当的概率时
        int value = denominator / numerator;
        //获得1--value的随机数
        int number = GameUtil.getRandomNumber(1, value + 1);
        return number == 1;
    }

    /**
     * 指定区间的随机数方法利用Random函数；
     * max：区间最大值不包含
     * min：包含区间最小值
     * return 该区间的随机数
     */
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * 获取指定字符串的宽度
     *
     * @param font
     * @param str
     */
    /**
     * 获取文字的宽度
     */
    public static int getStringWidth(Font font, String str) {
        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        return fm.stringWidth(str);
    }

    /**
     * 获取文字的宽度
     */
    public static int getStringHeight(Font font, String str) {
        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        return fm.getHeight();
    }

}
