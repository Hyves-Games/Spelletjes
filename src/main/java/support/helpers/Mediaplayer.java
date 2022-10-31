package support.helpers;

import client.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Mediaplayer {

    private Media song;
    private static MediaPlayer player;
    public static void play () {
        Media song = new Media(Application.class.getResource("audio/counterstrike.mp3").toString());
        MediaPlayer player = new MediaPlayer(song);
        player.play();
        System.out.println(player.getVolume());
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
