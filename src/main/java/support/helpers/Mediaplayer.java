package support.helpers;

import client.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Mediaplayer {

    private static Media song;
    private static MediaPlayer player;
    public static void play () {
        song = new Media(Application.class.getResource("audio/counterstrike.mp3").toString());
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
