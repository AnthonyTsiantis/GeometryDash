package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music lobbyMusic, level1Music, gameOverMusic;
    public Sound jumpSFX;
    private long jumpID, gameOverID;
    private Boot game;

    public AudioManager(Boot game) {
        this.game = game;
        this.lobbyMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Lobby Music.mp3"));
        this.lobbyMusic.setVolume(game.gameVolume);
        this.lobbyMusic.setLooping(true);
        this.lobbyMusic.play();
        this.lobbyMusic.pause();
        this.level1Music = Gdx.audio.newMusic(Gdx.files.internal("audio/Stereo Madness.mp3"));
        this.level1Music.setVolume(game.gameVolume);
        this.level1Music.setLooping(true);
        this.level1Music.play();
        this.level1Music.pause();
        this.gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Game Over.wav"));
        this.level1Music.setVolume(game.gameVolume);
        this.level1Music.setLooping(false);
        this.level1Music.play();
        this.level1Music.pause();
        this.jumpSFX = Gdx.audio.newSound(Gdx.files.internal("audio/JumpSFX.wav"));
    }

    public void playMusic(String currentScreen) {
        switch (currentScreen) {
            case "Menu Screen":
                this.lobbyMusic.play();
                break;
            case "Level 1":
                this.level1Music.play();
                break;
            case "Game Over":
                this.gameOverMusic.play();
                break;
        }
    }

    public void stopMusic(String currentScreen) {
        switch (currentScreen) {
            case "Menu Screen":
                this.lobbyMusic.stop();
                break;
            case "Level 1":
                this.level1Music.stop();
                break;
            case "Game Over":
                this.gameOverMusic.stop();
        }
    }

    public void playSFX(String currentSFX) {
        switch (currentSFX) {
            case "Jump":
                this.jumpID = this.jumpSFX.play(0.25f);
                this.jumpSFX.setLooping(this.jumpID, false);
                break;
        }
    }

    public void dispose() {
        this.lobbyMusic.stop();
        this.lobbyMusic.dispose();
        this.level1Music.stop();
        this.level1Music.dispose();
        this.gameOverMusic.stop();
        this.gameOverMusic.dispose();
        this.jumpSFX.dispose();
    }
}
