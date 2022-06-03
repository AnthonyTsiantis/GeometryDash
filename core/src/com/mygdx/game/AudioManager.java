package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music lobbyMusic, level1Music, level2Music, level3Music, level4Music, gameOverMusic;
    public Sound jumpSFX;
    private long jumpID, gameOverID;
    private Boot game;

    public AudioManager(Boot game) {
        this.game = game;
        this.lobbyMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Lobby Music.mp3"));
        this.lobbyMusic.setVolume(this.game.gameVolume);
        this.lobbyMusic.setLooping(true);
        this.lobbyMusic.play();
        this.lobbyMusic.pause();
        this.level1Music = Gdx.audio.newMusic(Gdx.files.internal("audio/Stereo Madness.mp3"));
        this.level1Music.setVolume(game.gameVolume);
        this.level1Music.setLooping(true);
        this.level1Music.play();
        this.level1Music.pause();
        this.level2Music = Gdx.audio.newMusic(Gdx.files.internal("audio/Subwoofer Lullaby.mp3"));
        this.level2Music.setVolume(this.game.gameVolume);
        this.level2Music.setLooping(true);
        this.level2Music.play();
        this.level2Music.pause();
        this.level3Music = Gdx.audio.newMusic(Gdx.files.internal("audio/Electroman adventures.mp3"));
        this.level3Music.setVolume(this.game.gameVolume);
        this.level3Music.setLooping(true);
        this.level3Music.play();
        this.level3Music.pause();
        this.level4Music = Gdx.audio.newMusic(Gdx.files.internal("audio/Time machine.mp3"));
        this.level4Music.setVolume(this.game.gameVolume);
        this.level4Music.setLooping(true);
        this.level4Music.play();
        this.level4Music.pause();
        this.gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Game Over.wav"));
        this.gameOverMusic.setVolume(this.game.gameVolume);
        this.gameOverMusic.setLooping(false);
        this.gameOverMusic.play();
        this.gameOverMusic.pause();
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
            case "Level 2":
                this.level2Music.play();
                break;
            case "Level 3":
                this.level3Music.play();
                break;
            case "Level 4":
                this.level4Music.play();
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
            case "Level 2":
                this.level2Music.stop();
                break;
            case "Level 3":
                this.level3Music.stop();
                break;
            case "Level 4":
                this.level4Music.stop();
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
        this.level2Music.stop();
        this.level2Music.dispose();
        this.level3Music.stop();
        this.level3Music.dispose();
        this.level4Music.stop();
        this.level4Music.dispose();
        this.gameOverMusic.stop();
        this.gameOverMusic.dispose();
        this.jumpSFX.dispose();
    }
}
