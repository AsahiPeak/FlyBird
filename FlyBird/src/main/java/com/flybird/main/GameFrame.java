package com.flybird.main;

import com.flybird.util.Constant;
import com.flybird.util.MusicUtil;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * @Author 木子
 * @Date 2020/10/10
 */
/*
游戏的主窗口类，所有的游戏绘制界面 都在此类中完成；
在游戏开发中：都将使用低级界面的绘制，不将使用jdk提供的组件
 */
//需要继承jdk为我们提供的窗口类
public class GameFrame extends Frame implements Runnable {
    //创建游戏的状态机制
    private static int readyState;
    public static final int GAME_STATE_READY = 0;
    public static final int GAME_STATE_PLAYING = 1;
    public static final int GAME_OVER = 2;

    //    创建游戏背景图
    private GameBackground background;
    //    创建云彩前背景
    private GameFrontGround gameFrontGround;
    //     创建障碍物
    private GameObstacleMange gameObstacleMange;
    //    创建小鸟类的实例
    private Bird bird;
    //   创建游戏开始前的图像
    private GameReady gameReady;
    /**
     * 利用双缓存机制解决屏幕的闪烁问题，使用BufferedImage创建新的一张图片；
     * 设定本图片的宽度和高度与屏幕一致，其颜色的获取是否透明
     * TYPE_CUSTOM         =  0,
     * TYPE_INT_RGB        =  1,
     * TYPE_INT_ARGB       =  2,
     * TYPE_INT_ARGB_PRE   =  3,
     * TYPE_INT_BGR        =  4,
     * TYPE_3BYTE_BGR      =  5,
     * TYPE_4BYTE_ABGR     =  6,
     * TYPE_4BYTE_ABGR_PRE =  7,
     * TYPE_USHORT_565_RGB =  8,
     * TYPE_USHORT_555_RGB =  9,
     * TYPE_BYTE_GRAY      = 10,
     * TYPE_USHORT_GRAY    = 11,
     * TYPE_BYTE_BINARY    = 12,
     * TYPE_BYTE_INDEXED   = 13;
     * 先将要绘制的内容，在内存中集中到本图片中；
     * 在将本图一次性的绘制到窗口中；
     */
    private BufferedImage buffededBackground = new BufferedImage(Constant.FRAMR_WITH, Constant.FRAMR_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);


    // 定义构造函数，实现游戏窗口的初始化
    public GameFrame() {
        //  设置窗口可见（默认不可见）
        setVisible(true);
        //  设置窗口的初始大小
        initFrame();
        //  调用游戏背景初始话的方法
        initGame();
        // 加载音效
        //加载音乐
        MusicUtil.playGameStart();


    }

    //    对窗口进行初始化设置
    private void initFrame() {
        // 设置窗口的大小
        setSize(Constant.FRAMR_WITH, Constant.FRAMR_HEIGHT);
        // 设置窗口的标题
        setTitle(Constant.FARME_NAME);
        // 设置窗口的初始位置
        setLocation(Constant.FARME_X, Constant.FARME_Y);
        //  设置窗口大小不可改变(实际中并不常用)
        setResizable(false);
        //  给窗口添加关闭事件（窗口监听事件）,当事件发生时会派发给你参数对象，其调用对应的方法
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 结束程序
                System.exit(0);
            }
        });
        // 对小鸟的类添加键盘监听事件
        addKeyListener(new MyKeyListener());
        // 对鼠标事件监听
        addMouseListener(new MyMouseListener());
    }


    /**
     * 通过鼠标的监听类可以实现对时间的改变，通过实现MoudeListend
     */
    class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int button = e.getButton();
            if (button == 1) {
                if (readyState == GAME_OVER) {
                    resetGame();
                }

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


    /**
     * 重新开始游戏
     */
    private void resetGame() {
        //重置障碍物
        gameObstacleMange.reset();
        //修改游戏的状态
        setReadyState(GAME_STATE_READY);
        bird.rest();
    }


    // 小鸟的键盘监听类---->通过继承KeyListener接口，实现方法的重写
    class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        // 当触发键盘按下键，激活此方法
        @Override
        public void keyPressed(KeyEvent e) {
            //接收系统传来的虚拟键值
            int keyCode = e.getKeyCode();
            //按下空格键时改变游戏的状态-->切换
            switch (readyState) {
                case GAME_STATE_READY:

                    if (keyCode == KeyEvent.VK_SPACE) {
                        readyState = GAME_STATE_PLAYING;
                        bird.startTime();
                    }
                    break;
                case GAME_STATE_PLAYING:
                    // 通过判断来实现小鸟状态的改变
                    if (keyCode == KeyEvent.VK_SPACE) {
                        //向上飞行
                        bird.flyup();
                    }
                    break;
                /**
                 * 第二种重置的方法，也可以通过空格键来进行游戏的重置；
                 */
//                case GAME_OVER:
//                    if(keyCode == KeyEvent.VK_SPACE){
//                        resetGame();
//                    }
//                    break;
            }

        }

        // 当抬起键盘时，激活此方法
        @Override
        public void keyReleased(KeyEvent e) {
            // 与上述的代码同理
            int keyCode = e.getKeyCode();
            //按下空格键时改变游戏的状态-->切换
            switch (readyState) {
                case GAME_STATE_READY:
                    break;
                case GAME_STATE_PLAYING:
                    if (keyCode == KeyEvent.VK_SPACE) {
                        //向下飞行
                        bird.flydown();
                    }
                    break;
            }

        }

    }

    //    定义游戏背景初始化的方法
    public void initGame() {
        //游戏的初始状态
        readyState = GAME_STATE_READY;
        // 设置背景图加载
        background = new GameBackground();
        // 前背景的加载
        gameFrontGround = new GameFrontGround();
        // 障碍物绘制
        gameObstacleMange = new GameObstacleMange();
        // 小鸟类的加载
        bird = new Bird();
        //准备开始时图片加载
        gameReady = new GameReady();
        setReadyState(GAME_STATE_READY);
        new Thread(this).start();
        // 加载音效
        MusicUtil.load();


    }

    /*
      update方法继承来自父类，绘制的内容需要再次完成，被jvm调用--->当repaint()方法被调用时；
      repaint()方法调用，单独启动一个线程，让线程去不断调
     */
    @Override
//    g是系统提供的画笔
    public void update(Graphics g) {
        //将缓存之后的背景统一绘制(使用缓存背景图，获得画笔然后)
        Graphics bufBackg = buffededBackground.getGraphics();
        //将获得的画笔，一次性的绘制到缓存图片上
        //绘制游戏的背景
        background.draw(bufBackg);
        if (readyState == GAME_STATE_READY) {
            //游戏准备开始阶段
            //绘制游戏准备的前景
            gameReady.draw(bufBackg);
            //绘制游戏的主教
            bird.draw(bufBackg);
        } else {//游戏开始之后
            //绘制障碍物
            gameObstacleMange.draw(bufBackg, bird);
            //绘制主角
            bird.draw(bufBackg);
            //绘制游戏的前景
            gameFrontGround.draw(bufBackg);

        }


        //将缓存图绘制到屏幕中
        g.drawImage(buffededBackground, 0, 0, null);
    }

    @Override
    public void run() {
        while (true) {
//            无限调用此方法，通过它调用update
            repaint();
            try {
                Thread.sleep(Constant.GAME_INITTIMR);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 定义游戏重置状态的方法
     */
    public static int getReadyState() {
        return readyState;
    }

    public static void setReadyState(int readyState) {
        GameFrame.readyState = readyState;
    }
}
