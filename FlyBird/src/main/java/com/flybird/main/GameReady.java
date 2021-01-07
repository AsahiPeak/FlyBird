package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author 木子
 * @Date 2020/10/24
 */
/*
游戏的准备界面，当点击开始前的界面
 */
public class GameReady {
    //加载图片的资源
    private BufferedImage titleImag;
    private BufferedImage startImag;
    private int flash;

    public GameReady() {
        //加载图片的资源
        titleImag = GameUtil.loadBufferedImages(Constant.GAME_TITLE);
        startImag = GameUtil.loadBufferedImages(Constant.GAME_START);

    }

    //通过绘制将图片加载到页面
    public void draw(Graphics g) {
        //定义位置
        int x = Constant.FRAMR_WITH - titleImag.getWidth() >> 1;
        int y = Constant.FRAMR_HEIGHT / 6 << 1;
        //绘制标题
        g.drawImage(titleImag, x, y, null);
        //开始按键闪烁
        if (flash++ > 3) {
            x = Constant.FRAMR_WITH - startImag.getWidth() >> 1;
            y = Constant.FRAMR_HEIGHT / 5 * 3;
            //绘制游戏的开始图
            g.drawImage(startImag, x, y, null);
            if (flash == 10) {
                flash = 0;
            }

        }

    }
}
