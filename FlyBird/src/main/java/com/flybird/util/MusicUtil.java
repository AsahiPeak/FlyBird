package com.flybird.util;

/**
 * *******时刻保持学习之心******
 *
 * @author 木子
 * @package IntelliJ IDEA
 * @date 2020/12/30
 * *******方能成就不世功业******
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;


public class MusicUtil {
    /**
     * 这是游戏的音效类；
     * 将游戏的所用到的音效进行方法的封装；
     * wav:可以通过jdk自带的方法进行加载
     */
    /**
     * 定义游戏音效的属性；
     */
    private static AudioClip fly;
    private static AudioClip crash;
    private static AudioClip sound;


    public static void load() {
        //加载游戏的音效
        try {
            // 加载飞翔的音效
            File fly_path = new File("Music/start.aiff");
            fly = Applet.newAudioClip(fly_path.toURL());
            // 加载碰撞的音效
            File crash_path = new File("Music/crash.wav");
            crash = Applet.newAudioClip(crash_path.toURL());
            // 加载开始音效
            File gameStart_path = new File("Music/backsound.wav");
            sound = Applet.newAudioClip(gameStart_path.toURL());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public static void playFly() {
        // 播放飞翔的音效
        fly.play();
    }


    public static void playCrash() {
        // 撞击的音效
        crash.play();
    }


    public static void playGameStart() {
        // 撞击的音效
        sound.play();
    }


}
