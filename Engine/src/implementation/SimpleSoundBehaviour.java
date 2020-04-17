package implementation;

import Engine.MediaPlayer;
import core.ISoundingBehaviour;

import java.io.File;

public class SimpleSoundBehaviour implements ISoundingBehaviour {

    File soundFile;
    float volume;

    public SimpleSoundBehaviour(File soundFile, float volume) {
        this.soundFile = soundFile;
        this.volume = volume;
    }

    @Override
    public void produceSound() {
        new Thread(getPlayer()).start();
    }

    private MediaPlayer getPlayer() {
        MediaPlayer media = new MediaPlayer(soundFile.getAbsolutePath());
        media.setVolume(volume);
        return media;
    }
}
