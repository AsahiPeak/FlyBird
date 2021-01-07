package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author 木子
 * @Date 2020/10/10
 */
/*
游戏背景类，所有的游戏背景都在这个类描绘
 */
public class GameBackground {
    //    背景需要的资源
    private BufferedImage bkimg;

    //    在构造方法中对资源初始化
    public GameBackground() {
        //加载底部的背景图
        bkimg = GameUtil.loadBufferedImages(Constant.BK_IMG_PATH);
    }

    //    定义一个绘制的方法
    //    Graphics 是系统提供的方法，绘制到界面上
    public void draw(Graphics g) {
        //   绘制背景色
        g.setColor(Constant.GAME_BK_COLOR);
        //   填充窗口
        g.fillRect(0, 0, Constant.FRAMR_WITH, Constant.FRAMR_HEIGHT);
        //        获取背景图的宽
        int imgWith = bkimg.getWidth();
        //        获取背景图的高
        int imgHeight = bkimg.getHeight();
        //        绘制循环的次数
        int count = Constant.FRAMR_WITH / imgWith + 1;
        //        通过循环来实现动态绘制
        for (int i = 0; i < count; i++) {
            g.drawImage(bkimg, imgWith * i, Constant.FRAMR_HEIGHT - imgHeight, null);
        }
    }
}
