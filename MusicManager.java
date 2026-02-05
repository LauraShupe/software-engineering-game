import greenfoot.*;

public class MusicManager {

    private static GreenfootSound bgm;

    /** Start looping background music. */
    public static void playMusic(String filename, int volume) {
        stopMusic(); // ensure only one loop at a time

        bgm = new GreenfootSound(filename);
        bgm.setVolume(volume);
        bgm.playLoop();
    }

    /** Stop the currently playing background music. */
    public static void stopMusic() {
        if (bgm != null) {
            bgm.stop();
            bgm = null;
        }
    }

    /** Play a one-time sound effect (not looped). */
    public static void playSFX(String filename, int volume) {
        GreenfootSound s = new GreenfootSound(filename);
        s.setVolume(volume);
        s.play();
    }
}


