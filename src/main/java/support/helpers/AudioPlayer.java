package support.helpers;

import client.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class AudioPlayer {

    private static MediaPlayer player;
    public static void play () {
        Media song = new Media(Application.class.getResource("assets/audio/counterstrike.mp3").toString());
        player = new MediaPlayer(song);
        player.play();
    }

    public static void stop() {
        player.stop();
    }

    public static double getVolume() {
        return player.getVolume();
    }

    public static void setVolume(double volume) {
        player.setVolume(volume);
    }
}
