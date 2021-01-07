package com.flybird.util;

import java.awt.*;

/**
 * @Author 木子
 * @Date 2020/10/10
 */

/**
 * 游戏中的常量类
 */
public class Constant {
    /**
     * 设置窗口的大小
     */
    public static final int FRAMR_WITH = 1200;
    public static final int FRAMR_HEIGHT = 650;
    /**
     * 设置窗口名称
     */
    public static final String FARME_NAME = "飞翔的小鸟";
    /**
     * 设置窗口的初始化位置
     **/
    public static final int FARME_X = 95;
    public static final int FARME_Y = 60;
    /**
     * 设置背景图片的初始
     */
    public static final String BK_IMG_PATH = "img/bird_bk1.png";
    /**
     * 设置游戏小鸟图使用数组
     */
    public static final String[] BIRDS_IMG_PATH = {"img/bird_2.png", "img/bird_up.png", "img/bird_down.png", "img/bird_die.png"};
    /**
     * 当发生碰撞时游戏结束的图片
     */
    public static final String GAME_OVER = "img/GameOver.png";
    /**
     * 游戏结束的出现的计分牌
     */
    public static final String GAME_SCORE = "img/RecordScore.png";
    /**
     * 游戏的刷新间隔
     */
    public static final int GAME_INITTIMR = 160;
    /**
     * 游戏的背景颜色(0x为16进制)
     *
     */
    public static final Color GAME_BK_COLOR = new Color(0x4bc4cf);
    /**
     * 小鸟向飞行的最大值
     */
    public static final int MAX_UP_CHANGEY = 8;
    /**
     * 小鸟向下跌落的最大值
     */
    public static final int MAX_DOWN_CHANGEY = 15;
    /**
     * 定义标题栏的高度
     */
    public static final int FRAME_TOP_HIGHT = 20;
    /**
     * 设置云彩图的存放数组
     */
    public static final String[] CLOUDS_IMG_PATH = {"img/cloud0.png", "img/cloud1.png"};
    /**
     * 设置障碍存放的数组
     */
    public static final String[] OBSTACLE_IMG_PATH = {"img/obstacle.png", "img/obstacle_down.png", "img/obstacle_up.png"};
    /**
     * 定义云彩的最大个数
     */
    public static final int MAX_CLOUD_NUMBER = 4;
    /**
     * 定义云彩的最小个数
     */
    public static final int MIN_CLOUD_NUMBER = 2;
    /**
     * 定义云彩的移动运算，每100ms运算一次
     */
    public static final int CLOUD_INTERVAL = 100;
    /**
     * 定义云彩出现的概率
     */
    public static final int CLOUD_SHOW_PERCENT = 5;
    /**
     * 定义云的速度
     */
    public static final int CLOUD_SPEED = 10;
    /**
     * 定义随机概率出现的分子数
     */
    public static final int PROBABILITY_NUMERATOR = 1;
    /**
     * 定义随机概率出现的分母数
     */
    public static final int PROBABILITY_DENOMINATOR = 200;
    /**
     * 障碍物移动的速度(最大和最小)
     */
    public static final int OBSTACLE_MAX_SPEED = 4;
    public static final int OBSTACLE_MIN_SPEED = 1;
    /**
     * 定义测试中碰撞矩形的补偿
     */
    public static final int RECT_GAP = 2;
    /**
     * 障碍物的纵向相对的间隔-->障碍物之间的空隙(障碍物是以一对的形式出现)；1/4
     * 前一对出现的高度与后一对出现的高度之间的间隙距离；
     * 每一个障碍物的取值范围[1/8--5/8] +间隙等于整个屏幕对的高度
     */
    /**
     * 上下障碍物的间隙
     */
    public static final int TYPER_TOPANDBOTTOM_SPACE = Constant.FRAMR_HEIGHT >> 2;
    /**
     * 前后障碍物之间的空隙
     */
    public static final int TYPER_LEFTANDRIGHT_SPACE = Constant.FRAMR_HEIGHT >> 2;
    /**
     * 每一个障碍物之间的取值范围
     * 障碍物的最大高度
     */
    public static final int TYPER_MAX_SPACE = Constant.FRAMR_HEIGHT >> 3;
    /**
     * 障碍物的最小高度
     */
    public static final int TYPER_MIN_SPACE = (Constant.FRAMR_HEIGHT >> 3) * 5;
    /**
     * 加载游戏前的名称图
     */
    public static final String GAME_TITLE = "img/title_2.png";
    /**
     * 游戏前的开始键
     */
    public static final String GAME_START = "img/start_2.png";
    /**
     * 定义游戏时间的字体
     */
    public static final Font TIME_FONT = new Font("华文琥珀", Font.PLAIN, 32);
    /**
     * 定义游戏的最高记录的根目录
     */
    public static final String GAME_SCORE_FILE = "./GameScore";
    /**
     * 游戏重玩的图片资源
     */
    public static final String GAME_RESET = "img/reset.png";

}
